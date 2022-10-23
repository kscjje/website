package com.hisco.user.edcatnlc.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgOptinfoVO;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.ExceptionUtil;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.cmm.util.TossUtil;
import com.hisco.intrfc.sale.service.SaleChargeService;
import com.hisco.intrfc.sale.service.TotalSaleService;
import com.hisco.intrfc.sale.vo.SaleFormItemVO;
import com.hisco.intrfc.sale.vo.SaleFormMemberVO;
import com.hisco.intrfc.sale.vo.SaleFormPaymentVO;
import com.hisco.intrfc.sale.vo.SaleFormVO;
import com.hisco.intrfc.sale.vo.TossResponseVO;
import com.hisco.intrfc.sms.service.SmsService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 가상계좌에 돈이 입금되면 호출되는 URL
 * 호출되면 수강등록 완료처리 진행
 *
 * @Class Name : EdcRsvnVbankCastController.java
 * @Description : 자세한 클래스 설명
 * @author woojinp@legitsys.co.kr
 * @since 2021. 12. 24.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */

@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/edc/rsvn")
public class EdcRsvnVbankCastController {

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    @Resource(name = "smsService")
    private SmsService smsService;

    @Resource(name = "totalSaleService")
    private TotalSaleService totalSaleService;

    @Resource(name = "saleChargeService")
    SaleChargeService saleChargeService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    /**
     * 가상계좌 전용 URL
     * 고객입금시 호출
     */
    @PostMapping(value = { "/pay/toss/casNoteUrl", "/pay/toss/casNoteUrl.json" })
    public void tossCasNoteUrl(TossResponseVO tossResponseVO, HttpServletResponse response, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        StringBuffer sb = new StringBuffer("[가상계좌 입금처리]===============================================================\n");
        sb.append("tossResponseVO => ").append(JsonUtil.toPrettyJson(tossResponseVO)).append("\n\n");

        /*
         * 상점 처리결과 리턴메세지
         * 결제결과 상점 DB처리(LGD_CASNOTEURL) 결과값을 입력해 주시기 바랍니다.
         * OK : 상점 처리결과 성공
         * 그외 : 상점 처리결과 실패
         * ※ 주의사항 : 성공시 'OK' 문자이외의 다른문자열이 포함되면 실패처리 되오니 주의하시기 바랍니다.
         */
        boolean result = false;

       // try {
			String LGD_CASFLAG = tossResponseVO.getLGD_CASFLAG().trim();
			if ("R".equals(LGD_CASFLAG)) { // 무통장 할당 성공. DB에 예약 등록된 정보가 들어가기 전에 pg사에서 무통장할당성공 정보가 날아오면 DB에 값이 없기때문에 오류가
			                               // 발생, 하지만 정상적인 것으로 OK를 리턴할 수 있도록 처리
				log.error("무통장 할당 성공 - {}", tossResponseVO.getLGD_TID());
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("OK");
				response.getWriter().write("\r\n");
				response.flushBuffer();
				return;
			}

            EdcRsvnInfoVO rsvnInfoParam = new EdcRsvnInfoVO();
            rsvnInfoParam.setPayMethod(Constant.SITE_P_TYPE_가상계좌);
            rsvnInfoParam.setTid(tossResponseVO.getLGD_TID());
            rsvnInfoParam.setAcountNum(tossResponseVO.getLGD_ACCOUNTNUM());
            List<EdcRsvnInfoVO> rsvnInfoList = edcRsvnInfoService.selectRsvnListForPay(rsvnInfoParam);
            sb.append("rsvnInfoParam => ").append(JsonUtil.toPrettyJson(rsvnInfoParam)).append("\n");
            sb.append("rsvnInfoList => ").append(JsonUtil.toPrettyJson(rsvnInfoList)).append("\n");

            if (rsvnInfoList == null || rsvnInfoList.isEmpty()) {
                throw new RuntimeException("예약건이 존재하지 않습니다.");
            }

            if (!Constant.SM_RSVN_STAT_입금대기.equals(rsvnInfoList.get(0).getEdcStat())) {
                throw new RuntimeException("현재 예약건은 입금대기 상태가  아닙니다.");
            }

            OrgOptinfoVO orgOptinfoParam = new OrgOptinfoVO();
            orgOptinfoParam.setOrgNo(rsvnInfoList.get(0).getOrgNo());
            OrgOptinfoVO orgOptinfo = orgInfoService.selectOrgOptinfo(orgOptinfoParam);
            paramMap.put("mertkey", orgOptinfo.getWebpayapiKey());
            if (StringUtils.isBlank(orgOptinfo.getWebpayapiKey()))
                throw new RuntimeException("orgNo(" + rsvnInfoList.get(0).getOrgNo() + ")'s mertkey is null");

            String hashData = TossUtil.getHashDataWithRespCode(paramMap);
            sb.append("hashData => ").append(hashData).append("\n");

            log.error("{}\n\n", sb.toString());  // 로그를 남기기 위해

            if (!hashData.trim().equals(tossResponseVO.getLGD_HASHDATA())) { // 해쉬값이 검증이 실패이면
                throw new RuntimeException("결제결과 해쉬값 검증이 실패하였습니다.");
            }

            if (!"0000".equals(tossResponseVO.getLGD_RESPCODE().trim())) { // 결제 성공코드가 아니면
                throw new RuntimeException("결제결과 코드값이 0000이 아닙니다.");
            }

            if (!Constant.TOSS_P_TYPE_가상계좌.equals(tossResponseVO.getLGD_PAYTYPE())) {// 가상계좌
                throw new RuntimeException("결제결과 PAYTYPE이 가상계좌(SC0040)가 아닙니다.");
            }

            if ("R".equals(LGD_CASFLAG)) { // 무통장 할당 성공
                log.error("무통장 할당 성공 - {}", tossResponseVO.getLGD_TID());
            } else if ("C".equals(LGD_CASFLAG)) { // 무통장 입금취소
                log.error("무통장 입금 취소 - {}", tossResponseVO.getLGD_TID());
            } else if ("I".equals(LGD_CASFLAG)) { // 무통장 입금 성공
                SaleFormVO saleFormVO = new SaleFormVO();
                this.setSaleFormVO(tossResponseVO, saleFormVO, rsvnInfoList);

                totalSaleService.register(saleFormVO);
            }

            result = true;

            /*
        } catch (Exception ex) {
            log.error(ExceptionUtil.getErrorLine(ex));
            sb.append("Exception => ").append(ExceptionUtil.getErrorLine(ex)).append("\n");
            result = false;
			log.error("{}\n\n", sb.toString());
        }
        */

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result ? "OK" : "FAIL");
        response.getWriter().write("\r\n");
    }

    /**
     * 연관 테이블 저장을 위한 값 설정
     */
    private void setSaleFormVO(TossResponseVO tossResponseVO, SaleFormVO saleFormVO, List<EdcRsvnInfoVO> rsvnInfoList) {

        int totalDcAmt = 0;

        EdcRsvnInfoVO rsvnVO = rsvnInfoList.get(0);

        List<SaleFormItemVO> itemList = new ArrayList<SaleFormItemVO>();
        for (EdcRsvnInfoVO vo : rsvnInfoList) {
            SaleFormItemVO item = new SaleFormItemVO();
            item.setComcd(vo.getComcd());
            item.setEdcRsvnReqid(vo.getEdcRsvnReqid());
            item.setOrgNo(vo.getOrgNo());
            item.setItemCd(vo.getEdcReqItemCd());

            item.setCostAmt(vo.getEdcProgmCost());
            item.setSalamt(vo.getEdcProgmCost());
            item.setDcAmt(vo.getEdcDcamt());
            item.setDiscountCd(vo.getEdcReasondc());
            item.setDiscountRate(vo.getEdcDcrate());

            item.setMonthCnt(vo.getEdcMonthcnt());
            item.setEdcPrgmNo(vo.getEdcPrgmNo());

            item.setEdcSdate(vo.getEdcReqSdate());
            item.setEdcEdate(vo.getEdcReqEdate());
            item.setEdcRsvnsetSeq(vo.getEdcRsvnsetSeq());
            item.setVatYn(Config.NO);
            itemList.add(item);

            totalDcAmt += item.getDcAmt();
        }

        SaleFormPaymentVO payment = new SaleFormPaymentVO();
        payment.setOnoff(Constant.EDC_ONOFFIN_TYPE_ON);
        payment.setRergistGbn(Constant.SM_REGIST_GBN_신규등록);
        payment.setRsvnNo(rsvnVO.getEdcRsvnNo());
        payment.setPayAmt(Integer.parseInt(tossResponseVO.getLGD_CASTAMOUNT()));// 입금누적금액
        payment.setDcAmt(totalDcAmt);

        payment.setFinanceCd(tossResponseVO.getLGD_FINANCECODE());
        payment.setFinanceNm(tossResponseVO.getLGD_FINANCENAME());

        payment.setPayMethod(Constant.SITE_P_TYPE_가상계좌);
        payment.setCashAmt(payment.getPayAmt());
        payment.setCertNo(tossResponseVO.getLGD_CASHRECEIPTNUM()); // 현금영수증 승인번호
        payment.setCashReceiptKind(tossResponseVO.getLGD_CASHRECEIPTKIND());

        payment.setVbankSeq(rsvnVO.getVbankSeq());
        payment.setVbankStatus(Constant.SM_VBANK_PAYMENT_STATUS_입금완료);

        // 발급계좌정보
        payment.setVbankAccountNo(tossResponseVO.getLGD_ACCOUNTNUM());
        payment.setVbankBankcd(tossResponseVO.getLGD_FINANCECODE());
        payment.setVbankName(tossResponseVO.getLGD_FINANCENAME());
        payment.setVbankPname(tossResponseVO.getLGD_PAYER());
        payment.setVbankPtel(tossResponseVO.getLGD_BUYERPHONE());
        payment.setVbankPemail(tossResponseVO.getLGD_BUYEREMAIL());

        // 결과코드, 메시지
        payment.setResultCd(tossResponseVO.getLGD_RESPCODE());
        payment.setResultMsg(tossResponseVO.getLGD_RESPMSG());

        // 환불용고객계좌정보
        payment.setDpstrNm(rsvnVO.getRetDpstrNm());
        payment.setBankCd(rsvnVO.getRetBankCd());
        payment.setBankNm(rsvnVO.getRetBankNm());
        payment.setAccountNum(rsvnVO.getRetAcountNum());

        String paydate = tossResponseVO.getLGD_PAYDATE();
        payment.setSaleDate(paydate.substring(0, 8));
        payment.setSaleTime(paydate.substring(8));

        payment.setResultMsg(tossResponseVO.getLGD_RESPMSG());
        payment.setResultCd(tossResponseVO.getLGD_RESPCODE());
        payment.setCertDtime(tossResponseVO.getLGD_PAYDATE());

        payment.setTid(tossResponseVO.getLGD_TID());
        payment.setMid(tossResponseVO.getLGD_MID());
        payment.setOid(tossResponseVO.getLGD_OID());

        payment.setTerminalType(rsvnVO.getEdcTrmnlType());
        // payment.setAppGbn(Constant.SM_APP_GBN_현금영수증승인); //card_app_hist에 들어갈 값

        SaleFormMemberVO member = new SaleFormMemberVO();
        member.setMemNo(rsvnVO.getEdcRsvnMemno());
        member.setMemNm(rsvnVO.getEdcRsvnCustnm());
        member.setMemHp(rsvnVO.getEdcRsvnMoblphon());
        member.setMemBirthdate(rsvnVO.getEdcRsvnBirthdate());

        saleFormVO.setMember(member);
        saleFormVO.setItemList(itemList);
        saleFormVO.setPayment(payment);

        log.debug("saleFormVO = {}", JsonUtil.toPrettyJson(saleFormVO));
    }

}