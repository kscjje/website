package com.hisco.admin.edcrsvn.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.admin.member.service.MemberService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.annotation.PageActionType;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.intrfc.sale.service.SaleChargeService;
import com.hisco.intrfc.sale.service.TotalCancelService;
import com.hisco.intrfc.sale.service.TotalPartialCancelService;
import com.hisco.intrfc.sale.service.TotalSaleService;
import com.hisco.intrfc.sale.vo.SaleFormItemVO;
import com.hisco.intrfc.sale.vo.SaleFormMemberVO;
import com.hisco.intrfc.sale.vo.SaleFormPaymentVO;
import com.hisco.intrfc.sale.vo.SaleFormVO;
import com.hisco.intrfc.sms.service.SmsService;
import com.hisco.user.edcatnlc.service.EdcProgramService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;
import com.hisco.user.mypage.service.MyRsvnService;
import com.hisco.user.mypage.vo.MyRsvnVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 배정대기목록
 *
 * @author
 * @since
 * @version
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}/edcrsvn", "#{dynamicConfig.managerRoot}/edcrsvn" })
public class EdcAdmRsvnPayController {

    private String adminRoot = Config.ADMIN_ROOT;

    @Resource(name = "totalSaleService")
    private TotalSaleService totalSaleService;

    @Resource(name = "totalCancelService")
    private TotalCancelService totalCancelService;

    @Resource(name = "totalPartialCancelService")
    private TotalPartialCancelService totalPartialCancelService;

    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "memberService")
    private MemberService memberService;

    @Resource(name = "saleChargeService")
    private SaleChargeService saleChargeService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    @Resource(name = "smsService")
    private SmsService smsService;

    @Resource(name = "myRsvnService")
    private MyRsvnService myRsvnService;

    /*
     * @Resource(name = "totalRefundService")
     * private TotalRefundService totalRefundService;
     */

    @PageActionInfo(title = "입금확인(현금입금처리)", action = PageActionType.UPDATE, ajax = true)
    @PostMapping("/edcRsvnPay.json")
    public String edcRsvnPayJson(EdcRsvnInfoVO rsvnParam, CommandMap commandMap, ModelMap model,
            HttpServletRequest request)
            throws Exception {

        ResultInfo resultInfo = new ResultInfo(Config.SUCCESS, "처리를 완료하였습니다.");

        try {
            int saveCnt;

            List<EdcRsvnInfoVO> rsvnInfoList = edcRsvnInfoService.selectRsvnListForPay(rsvnParam);

            if (rsvnInfoList == null || rsvnInfoList.isEmpty())
                throw new RuntimeException("유효하지 않는 예약건입니다.");

            EdcRsvnInfoVO rsvnInfo = rsvnInfoList.get(0);
            if (!Constant.SM_RSVN_STAT_입금대기.equals(rsvnInfo.getEdcStat()))
                throw new RuntimeException("입금대기상태가 아닙니다.");

            // 입금대기 -> 등록완료
            SaleFormVO saleFormVO = new SaleFormVO();
            saleFormVO.setRsvnStat(Constant.SM_RSVN_STAT_등록완료);

            SaleFormMemberVO member = new SaleFormMemberVO();
            member.setMemNo(rsvnInfo.getEdcRsvnMemno());
            if (StringUtils.isBlank(rsvnInfo.getEdcRsvnMemno())) {
                member.setMemNm(rsvnInfo.getEdcRsvnCustnm());
                member.setMemHp(rsvnInfo.getEdcRsvnMoblphon());
                member.setMemBirthdate(rsvnInfo.getEdcRsvnBirthdate());
            }

            SaleFormItemVO item = new SaleFormItemVO();
            item.setComcd(rsvnInfo.getComcd());
            item.setEdcRsvnReqid(rsvnInfo.getEdcRsvnReqid());
            item.setOrgNo(rsvnInfo.getOrgNo());
            item.setItemCd(rsvnInfo.getEdcReqItemCd());
            item.setCostAmt(rsvnInfo.getCostAmt());
            item.setSalamt(rsvnInfo.getEdcProgmCost());
            item.setDcAmt(rsvnInfo.getEdcDcamt());
            item.setDiscountCd(rsvnInfo.getEdcReasondc());
            item.setDiscountRate((rsvnInfo.getEdcDcamt() / rsvnInfo.getEdcProgmCost()) * 100);
            item.setMonthCnt(rsvnInfo.getEdcMonthcnt());
            item.setEdcPrgmNo(rsvnInfo.getEdcPrgmNo());
            item.setEdcSdate(rsvnInfo.getEdcReqSdate());
            item.setEdcEdate(rsvnInfo.getEdcReqEdate());
            item.setEdcRsvnsetSeq(rsvnInfo.getEdcRsvnsetSeq());

            SaleFormPaymentVO payment = new SaleFormPaymentVO();
            payment.setOnoff(Constant.EDC_ONOFFIN_TYPE_OFF);
            payment.setRergistGbn(Constant.SM_REGIST_GBN_신규등록);
            payment.setPayComcd(Constant.PG_SELF);
            payment.setPayMethod(Constant.SITE_P_TYPE_현금);
            payment.setFinanceCd(Constant.SITE_DEFAULT_FINANCECD);
            payment.setPayAmt(rsvnInfo.getEdcTotamt());
            payment.setCashAmt(rsvnInfo.getEdcTotamt());
            payment.setTerminalType(rsvnInfo.getEdcTrmnlType());
            payment.setRsvnNo(rsvnInfo.getEdcRsvnNo());

            saleFormVO.setMember(member);
            saleFormVO.setItemList(Arrays.asList(item));
            saleFormVO.setPayment(payment);

            log.debug("saleFormVO = {}", JsonUtil.toPrettyJson(saleFormVO));

            saveCnt = totalSaleService.register(saleFormVO);
            log.debug("saveCnt = {}", saveCnt);
        } catch (Exception ex) {
            resultInfo = new ResultInfo(Config.FAIL, ex.getMessage());
        }

        model.clear();
        model.addAttribute("result", resultInfo);

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "신청취소/환불취소", action = PageActionType.UPDATE, ajax = true)
    @PostMapping("/edcRsvnCancel.json")
    public String edcRsvnCancelJson(MyRsvnVO myRsvnParam, CommandMap commandMap, ModelMap model,
            HttpServletRequest request)
            throws Exception {

        ResultInfo resultInfo = new ResultInfo(Config.SUCCESS, "취소처리가 완료되었습니다.");

        try {

            if (myRsvnParam.getEdcRsvnReqid() < 1 || StringUtils.isBlank(myRsvnParam.getEdcRsvnNo())) {
                throw new RuntimeException("취소를 위한 파라미터 값이 존재하지 않습니다.");
            }

            EdcRsvnInfoVO rsvnInfo = myRsvnService.selectMyEdcRsvnDtl(myRsvnParam);

            if (rsvnInfo == null)
                throw new RuntimeException("수강신청 현황이 존재하지 않습니다.");

            /*
             * 관리자는 어떤 조건에서건 취소가능
             * if (Config.NO.equals(rsvnInfo.getEditYn())) {
             * throw new RuntimeException("취소할 수 있는 조건이 아닙니다.");
             * }
             */

            /*
             * PG사에서 알아서 대응하므로 취소처리 가능(commented by 박명서이사)
             * if (Config.YES.equals(rsvnInfo.getEditYn()) && Constant.SM_RSVN_STAT_등록완료.equals(rsvnInfo.getEdcStat())
             * && rsvnInfo.getVbankSeq() > 0) { // 가상계좌
             * // 입금완료&등록완료일경우
             * throw new RuntimeException("(가상계좌) 취소할 수 있는 조건이 아닙니다.");
             * }
             */

            if (Constant.SM_RSVN_STAT_신청취소.equals(rsvnInfo.getEdcStat())) {
                throw new RuntimeException("이미 취소된 건 입니다.");
            }

            if (Constant.SM_RSVN_STAT_환불취소.equals(rsvnInfo.getEdcStat())) {
                throw new RuntimeException("이미 환불된 건 입니다.");
            }

            int insertOrUpdateCnt = 0;
            myRsvnParam.setChannel(Constant.CM_CHANNEL_BO);
            List<EdcRsvnInfoVO> rsvnList = myRsvnService.selectMyEdcPagingList(myRsvnParam);

            if (Constant.ING_RSVN_STAT_LIST.contains(rsvnInfo.getEdcStat())) { // 1000, 1002, 2001
                insertOrUpdateCnt = myRsvnService.updateReserveCancel(rsvnList);
            } else {
                insertOrUpdateCnt = totalCancelService.cancel(rsvnList);
            }

            if (insertOrUpdateCnt > 0) {
                if (rsvnList != null && rsvnList.size() > 0) {
                    for (EdcRsvnInfoVO vo : ((List<EdcRsvnInfoVO>) rsvnList)) {
                        try {
                            Map<String, String> smsParam = new HashMap<String, String>();
                            smsParam.put("msgcd", "4");
                            smsParam.put("msgno", "39");
                            smsParam.put("sndHp", vo.getGuideTelno());
                            smsParam.put("hp", vo.getEdcReqMoblphon());
                            smsParam.put("예약자명", vo.getEdcReqCustnm());
                            smsParam.put("예약일시", CommonUtil.getDateString(vo.getRegdate()));
                            smsParam.put("교육프로그램명", vo.getEdcPrgmnm());
                            smsParam.put("예약번호", vo.getEdcRsvnNo());
                            smsParam.put("문의전화", vo.getGuideTelno());
                            smsParam.put("예약일시", CommonUtil.getDateString(vo.getRegdate()));

                            // smsService.sendMessage(smsParam, loginVO);
                        } catch (Exception e) {
                            log.error("교육 결제취소 SMS 전송 오류 :" + e.getMessage());
                        }
                    }
                }
            } else {
                resultInfo.setMsg("취소가능한 예약 데이타가 없습니다.");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            resultInfo.setCode(Config.FAIL);
            resultInfo.setMsg(ex.getMessage());
        }

        model.clear();
        model.addAttribute("result", resultInfo);
        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "(부분)환불취소", action = PageActionType.UPDATE, ajax = true)
    @PostMapping("/edcRsvnPartialCancel.json")
    public String edcRsvnPartialCancelJson(@Valid @RequestBody SaleFormVO cancelFormVO, CommandMap commandMap,
            ModelMap model,
            HttpServletRequest request)
            throws Exception {

        ResultInfo resultInfo = new ResultInfo(Config.SUCCESS, "환불등록이 완료되었습니다.");
        List<SaleFormItemVO> cancelItemList = cancelFormVO.getItemList();

        try {

            EdcRsvnInfoVO searchVO = new EdcRsvnInfoVO();
            searchVO.setChannel(Constant.CM_CHANNEL_BO);
            searchVO.setEdcStat(null);
            searchVO.setEdcRsvnNo(cancelFormVO.getPayment().getRsvnNo());
            List<EdcRsvnInfoVO> rsvnList = edcRsvnInfoService.selectRsvnList(searchVO);

            if (rsvnList == null)
                throw new RuntimeException("등록내역이 존재하지 않습니다.");

            // 1. 취소가능금액 추출
            int dbTotFirstPayAmt = 0; // DB최초결제금액
            int dbTotCancelAmt = 0; // DB총취소금액(-)
            int dbTotRemainAmt = 0; // DB전체남아있는금액
            int paramTotCancelAmt = 0; // 취소요청전체금액

            for (EdcRsvnInfoVO rsvnInfo : rsvnList) {
                int dbPayAmt = rsvnInfo.getEdcTotamt(); // +금액
                int dbCancelAmt = rsvnInfo.getCancelAmt(); // -금액
                int dbRemainAmt = dbPayAmt + dbCancelAmt;

                dbTotFirstPayAmt += rsvnInfo.getEdcTotamt();
                dbTotCancelAmt += dbCancelAmt;
                dbTotRemainAmt += dbRemainAmt;
            }

            for (EdcRsvnInfoVO rsvnInfo : rsvnList) {
                for (SaleFormItemVO cancelItem : cancelItemList) {
                    if (rsvnInfo.getEdcRsvnReqid() == cancelItem.getEdcRsvnReqid()) {
                        int dbPayAmt = rsvnInfo.getEdcTotamt(); // +금액
                        int dbCancelAmt = rsvnInfo.getCancelAmt(); // -금액
                        int dbRemainAmt = dbPayAmt + dbCancelAmt;

                        paramTotCancelAmt += cancelItem.getCancelAmt();

                        if (dbRemainAmt < cancelItem.getCancelAmt()) {
                            throw new RuntimeException(String.format("[%s] 남아있는 금액(%s)이 취소할 금액(%s)보다 적습니다.", rsvnInfo.getEdcPrgmnm(), dbRemainAmt, cancelItem.getCancelAmt()));
                        }
                    }
                }
            }

            log.debug("dbTotFirstPayAmt:dbTotRemainAmt|paramTotCancelAmt = {}:{}:{}", dbTotFirstPayAmt, dbTotRemainAmt, paramTotCancelAmt);

            // 2. 전체취소, 부분취소 판단
            boolean totalCancel = false;
            if (dbTotCancelAmt == 0) { // 취소한적없음
                if (dbTotFirstPayAmt == paramTotCancelAmt) { // 최초결제금액과 취소할금액이 동일하면
                    totalCancel = true;
                    // totalCancelService.cancel(rsvnList); // 전체취소
                } else { // 부분취소
                    // totalPartialCancelService.cancel(rsvnList);
                }
            } else { // 부분취소
                // totalPartialCancelService.cancel(rsvnList);
            }

            // 3. 전체취소, 부분취소 진행
            if (totalCancel) {
                log.debug("전체취소 진행합니다.");
                totalCancelService.cancel(rsvnList); // 전체취소
            } else {
                log.debug("부분취소 진행합니다.");
                totalPartialCancelService.cancel(rsvnList, cancelFormVO);
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
            resultInfo.setCode(Config.FAIL);
            resultInfo.setMsg(ex.getMessage());
        }

        model.clear();
        model.addAttribute("result", resultInfo);

        return HttpUtility.getViewUrl(request);
    }

}
