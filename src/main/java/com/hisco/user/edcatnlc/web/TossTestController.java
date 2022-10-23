package com.hisco.user.edcatnlc.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.ExceptionUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.cmm.util.RequestUtil;
import com.hisco.cmm.util.SessionUtil;
import com.hisco.cmm.util.TossUtil;
import com.hisco.intrfc.sale.service.SaleChargeService;
import com.hisco.intrfc.sale.service.TotalSaleService;
import com.hisco.intrfc.sale.vo.SaleFormItemVO;
import com.hisco.intrfc.sale.vo.SaleFormMemberVO;
import com.hisco.intrfc.sale.vo.SaleFormPaymentVO;
import com.hisco.intrfc.sale.vo.SaleFormVO;
import com.hisco.intrfc.sale.vo.TossResponseVO;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import egovframework.com.cmm.service.EgovProperties;
import lgdacom.XPayClient.XPayClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : TossTestController.java
 * @Description : 자세한 클래스 설명
 * @author woojinp@legitsys.co.kr
 * @since 2021. 12. 9.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */

@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/edc/toss")
public class TossTestController {

    // 세션저장용key
    private final static String TOSS_PARAMETER = "TOSS_PARAMETER";
    private final static String TOSS_RETURN_PARAMETER = "TOSS_RETURN_PARAMETER";

    @Value("${tosspayments.platform}")
    private String CST_PLATFORM;

    @Value("${tosspayments.conf.path}")
    private String configPath;

    @Value("${Globals.Domain}")
    private String domain;

    @Resource(name = "saleChargeService")
    SaleChargeService saleChargeService;

    @Resource(name = "totalSaleService")
    private TotalSaleService totalSaleService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    @RequestMapping(value = "/sample")
    public String sample(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {
        model.addAttribute("LGD_TIMESTAMP", DateUtil.printDatetime(null, "yyyyMMddHHmmss"));
        model.addAttribute("LGD_OID", saleChargeService.selectNextOid()); // db생성
        return HttpUtility.getViewUrl(request);
    }

    @RequestMapping(value = "/payRequest")
    public String payRequest(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        boolean isMobile = RequestUtil.isMobile(request);

        try {
            String CST_PLATFORM = (String) paramMap.get("CST_PLATFORM");
            String CST_MID = (String) paramMap.get("CST_MID");
            String LGD_TIMESTAMP = (String) paramMap.get("LGD_TIMESTAMP");

            paramMap.put("mertkey", EgovProperties.getProperty("tosspayments.mertkey." + (String) paramMap.get("CST_MID")));
            paramMap.put("CST_WINDOW_TYPE", (isMobile ? "submit" : "iframe"));
            paramMap.put("LGD_CUSTOM_SWITCHINGTYPE", (isMobile ? "SUBMIT" : "IFRAME"));
            paramMap.put("LGD_MID", ("test".equals(CST_PLATFORM.trim()) ? "t" : "") + CST_MID);
            paramMap.put("LGD_HASHDATA", TossUtil.getHashData(paramMap));
            paramMap.put("LGD_TIMESTAMP", LGD_TIMESTAMP);
            paramMap.put("LGD_RETURNURL", domain + "/web/edc/toss/returnUrl");
            paramMap.put("LGD_CASNOTEURL", domain + "/web/edc/toss/casNoteUrl");

            SessionUtil.setAttribute(TOSS_PARAMETER, paramMap);
        } catch (Exception e) {
            log.error(e.toString());
        }

        model.addAttribute("paramMap", paramMap);

        return HttpUtility.getViewUrl(request);
    }

    @RequestMapping(value = "/returnUrl")
    public String returnUrl(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {
        log.debug("paramMap = {}", paramMap);

        model.addAttribute("paramMap", paramMap);
        SessionUtil.setAttribute(TOSS_RETURN_PARAMETER, paramMap);

        return HttpUtility.getViewUrl(request);
    }

    @RequestMapping(value = "/payResponse.json")
    public String payResponse(TossResponseVO paramVO, HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        log.debug(JsonUtil.toPrettyJson(paramVO));

        ResultInfo resultInfo = HttpUtility.getSuccessResultInfo("강좌신청이 완료되었습니다.");

        StringBuffer sb = new StringBuffer();

        String edcRsvnNo = request.getParameter("edcRsvnNo");
        String tid;
        String mid;
        String oid;

        /*************************************************
         * 1.최종결제 요청 - BEGIN
         *************************************************/
        String CST_PLATFORM = request.getParameter("CST_PLATFORM");
        String CST_MID = request.getParameter("CST_MID");
        String LGD_MID = ("test".equals(CST_PLATFORM.trim()) ? "t" : "") + CST_MID;
        String LGD_PAYKEY = request.getParameter("LGD_PAYKEY");

        XPayClient xpay = new XPayClient();
        boolean isInitOK = xpay.Init(configPath, CST_PLATFORM);

        try {
            if (!isInitOK) {
                // API 초기화 실패 화면처리
                sb.append("결제요청을 초기화 하는데 실패하였습니다.\n");
                sb.append("LG유플러스에서 제공한 환경파일이 정상적으로 설치 되었는지 확인하시기 바랍니다.\n");
                sb.append("mall.conf에는 Mert ID = Mert Key 가 반드시 등록되어 있어야 합니다.\n\n");
                sb.append("문의전화 LG유플러스 1544-7772\n");
                throw new RuntimeException(sb.toString());
            }

            // (3) Init_TX: 메모리에 mall.conf, lgdacom.conf 할당 및 트랜잭션의 고유한 키 TXID 생성
            xpay.Init_TX(LGD_MID);
            xpay.Set("LGD_TXNAME", "PaymentByKey");
            xpay.Set("LGD_PAYKEY", LGD_PAYKEY);

            if ("service".equals(CST_PLATFORM)) {
                Map<String, Object> tossParamMap = (Map<String, Object>) SessionUtil.getAttribute(TOSS_PARAMETER);
                String DB_AMOUNT = (String) tossParamMap.get("LGD_AMOUNT");
                xpay.Set("LGD_AMOUNTCHECKYN", "Y");
                xpay.Set("LGD_AMOUNT", DB_AMOUNT);
            }

            /*************************************************
             * 2. 최종결제 요청 결과처리
             * TX: lgdacom.conf에 설정된 URL로 소켓 통신하여 최종취소요청, 결과값으로 true, false 리턴
             *************************************************/
            if (!xpay.TX()) {
                // 2)API 요청실패 화면처리
                sb.append("결제요청이 실패하였습니다.  \n");
                sb.append("TX 결제요청 Response_code = " + xpay.m_szResCode + "\n");
                sb.append("TX 결제요청 Response_msg = " + xpay.m_szResMsg + "\n");
                throw new RuntimeException(sb.toString());
            }

            StringBuffer resSb = new StringBuffer();
            for (int i = 0; i < xpay.ResponseNameCount(); i++) {
                resSb.append(xpay.ResponseName(i) + ":");
                for (int j = 0; j < xpay.ResponseCount(); j++) {
                    resSb.append(xpay.Response(xpay.ResponseName(i), j)).append("\n");
                }
            }
            log.debug("toss spay Response = {}", resSb.toString());

            tid = xpay.Response("LGD_TID", 0);
            mid = xpay.Response("LGD_MID", 0);
            oid = xpay.Response("LGD_OID", 0);

            log.debug("tid:mid:oid = {}:{}:{}", tid, mid, oid);
            log.debug("m_szResCode:m_szResMsg = {}:{}", xpay.m_szResCode, xpay.m_szResMsg);

            if (!"0000".equals(xpay.m_szResCode)) {
                sb.append("최종결제요청 결과 실패 DB처리하시기 바랍니다.\n");
                throw new RuntimeException(sb.toString());
            }

            // (5)DB에 요청 결과 처리
            /*
             * 최종결제요청 결과를 DB처리합니다. (결제성공 또는 실패 모두 DB처리 가능)
             * 상점내 DB에 어떠한 이유로 처리를 하지 못한경우 false로 변경해 주세요.
             * 만약 DB처리 실패시 Rollback 처리, isDBOK 파라미터를 false 로 변경
             */
            boolean isDBOK = false;
            SaleFormVO saleFormVO = new SaleFormVO();

            try {
                EdcRsvnInfoVO rsvnInfoParam = new EdcRsvnInfoVO();
                rsvnInfoParam.setEdcRsvnNo(edcRsvnNo);
                List<EdcRsvnInfoVO> rsvnInfoList = edcRsvnInfoService.selectRsvnListForPay(rsvnInfoParam);
                if (rsvnInfoList == null || rsvnInfoList.isEmpty()) {
                    throw new RuntimeException("예약건이 존재하지 않습니다. edcRsvnNo = " + edcRsvnNo);
                }

                if (!Constant.SM_RSVN_STAT_입금대기.equals(rsvnInfoList.get(0).getEdcRsvnRectype())) {
                    throw new RuntimeException("현재 예약건은 입금대기 상태가  아닙니다. edcRsvnNo = " + edcRsvnNo);
                }

                Map<String, Object> tossReturnParamMap = (Map<String, Object>) SessionUtil.getAttribute(TOSS_RETURN_PARAMETER);

                SaleFormMemberVO member = new SaleFormMemberVO();
                member.setMemNo(rsvnInfoParam.getEdcMemNo());
                member.setMemNm(rsvnInfoParam.getEdcRsvnCustnm());
                member.setMemHp(rsvnInfoParam.getEdcRsvnMoblphon());
                member.setMemBirthdate(rsvnInfoParam.getEdcRsvnBirthdate());

                int totalSaleAmt = 0;
                int totalDcAmt = 0;
                int totalPayAmt = 0;
                String closeDate = ""; // yyyymmddhhmmss

                List<SaleFormItemVO> itemList = new ArrayList<SaleFormItemVO>();
                for (EdcRsvnInfoVO vo : rsvnInfoList) {
                    SaleFormItemVO item = new SaleFormItemVO();
                    item.setComcd(vo.getComcd());
                    item.setEdcRsvnReqid(vo.getEdcRsvnReqid());
                    item.setOrgNo(vo.getOrgNo());
                    item.setItemCd(vo.getEdcReqItemCd());

                    item.setCostAmt(vo.getEdcProgmCost());
                    item.setSalamt(vo.getEdcProgmCost());
                    item.setMonthCnt(vo.getEdcMonthcnt());
                    item.setEdcPrgmNo(vo.getEdcPrgmNo());

                    item.setEdcSdate(vo.getEdcReqSdate());
                    item.setEdcEdate(vo.getEdcReqEdate());
                    item.setEdcRsvnsetSeq(vo.getEdcRsvnsetSeq());
                    item.setVatYn(Config.NO);
                    itemList.add(item);

                    totalSaleAmt += item.getSalamt();
                    totalDcAmt += item.getDcAmt();
                    closeDate = vo.getCloseDate();
                }
                totalPayAmt = totalSaleAmt - totalDcAmt;

                SaleFormPaymentVO payment = new SaleFormPaymentVO();
                payment.setOnoff(Constant.EDC_ONOFFIN_TYPE_ON);
                payment.setRergistGbn(Constant.SM_REGIST_GBN_신규등록);
                payment.setRsvnNo(edcRsvnNo);
                payment.setPayAmt(totalPayAmt);
                payment.setDcAmt(totalDcAmt);

                String LGD_PAYTYPE = xpay.Response("LGD_PAYTYPE", 0);
                if (Constant.TOSS_P_TYPE_카드.equals(LGD_PAYTYPE)) {// 신용카드
                    payment.setPayMethod(Constant.SITE_P_TYPE_카드);
                    payment.setCardAmt(totalPayAmt);
                    String cardNum = xpay.Response("LGD_CARDNUM", 0);
                    payment.setCardNo1(cardNum.substring(0, 4));
                    payment.setCardNo2(cardNum.substring(4, 8));
                    payment.setCardNo3(cardNum.substring(8, 12));
                    payment.setCertNo(xpay.Response("LGD_FINANCEAUTHNUM", 0)); // 카드승인번호
                    payment.setHalbuCnt(Integer.parseInt(xpay.Response("LGD_CARDINSTALLMONTH", 0)));
                } else if (Constant.TOSS_P_TYPE_실시간계좌이체.equals(LGD_PAYTYPE)) {// 실시간
                    payment.setPayMethod(Constant.SITE_P_TYPE_실시간계좌이체);
                    payment.setCashAmt(totalPayAmt);
                    payment.setCertNo(xpay.Response("LGD_CASHRECEIPTNUM", 0)); // 현금영수증 승인번호
                    payment.setCashReceiptKind(xpay.Response("LGD_CASHRECEIPTKIND", 0));
                } else if (Constant.TOSS_P_TYPE_가상계좌.equals(LGD_PAYTYPE)) {// 가상계좌
                    payment.setPayMethod(Constant.SITE_P_TYPE_가상계좌);
                    payment.setCashAmt(totalPayAmt);
                    payment.setCertNo(xpay.Response("LGD_CASHRECEIPTNUM", 0)); // 현금영수증 승인번호
                    payment.setCashReceiptKind(xpay.Response("LGD_CASHRECEIPTKIND", 0));

                    // 발급계좌정보
                    payment.setVbankAccountNo(xpay.Response("LGD_ACCOUNTNUM", 0));
                    payment.setVbankBankcd((String) tossReturnParamMap.get("LGD_FINANCECODE"));
                    payment.setVbankName((String) tossReturnParamMap.get("LGD_FINANCENAME"));
                    payment.setVbankPname(xpay.Response("LGD_BUYER:", 0));
                    // payment.setVbankPtel(xpay.Response("LGD_CASHRECEIPTNUM", 0));
                    payment.setVbankPemail(xpay.Response("LGD_BUYEREMAIL", 0));
                    payment.setVbankPayWaitdate(closeDate.substring(0, 8));
                    payment.setVbankPayWaittime(closeDate.substring(8));

                    // 환불용고객계좌정보
                    payment.setDpstrNm(paramVO.getDpstrNm());
                    payment.setBankCd(paramVO.getBankCd());
                    payment.setBankNm(paramVO.getBankNm());
                    payment.setAccountNum(paramVO.getAccountNum());
                }

                payment.setIp((String) tossReturnParamMap.get("LGD_BUYERIP"));
                payment.setFinanceCd((String) tossReturnParamMap.get("LGD_FINANCECODE"));
                payment.setFinanceNm((String) tossReturnParamMap.get("LGD_FINANCENAME"));

                payment.setResultMsg(xpay.Response("LGD_RESPMSG", 0));
                payment.setResultCd(xpay.Response("LGD_RESPCODE", 0));
                payment.setCertDtime(xpay.Response("LGD_PAYDATE", 0));

                payment.setTid(xpay.Response("LGD_TID", 0));
                payment.setMid(xpay.Response("LGD_MID", 0));
                payment.setOid(xpay.Response("LGD_OID", 0));

                payment.setIp((String) tossReturnParamMap.get("LGD_BUYERIP"));

                payment.setTerminalType(getTerminalType(request));

                saleFormVO.setMember(member);
                saleFormVO.setItemList(itemList);
                saleFormVO.setPayment(payment);

                log.debug("slaeFormVO = {}", JsonUtil.toPrettyJson(saleFormVO));
                /*
                 * int saveCnt = totalSaleService.register(saleFormVO);
                 * if (saveCnt > 2)
                 * isDBOK = true;
                 */

                // TODO:현급영수증발행: 마이페이지에서 발급하도록
                /*
                 * if (Constant.TOSS_P_TYPE_실시간계좌이체.equals(LGD_PAYTYPE) ||
                 * Constant.TOSS_P_TYPE_가상계좌.equals(LGD_PAYTYPE)) {
                 * }
                 */

            } catch (Exception ex) {
                log.error(ex.getMessage());
            } finally {
                if (!isDBOK) {
                    xpay.Rollback("상점 DB처리 실패로 인하여 Rollback 처리 [TID:" + tid + ",MID:" + mid + ",OID:" + oid + "]");
                    sb.append("TX Rollback Response_code = " + xpay.Response("LGD_RESPCODE", 0) + "\n");
                    sb.append("TX Rollback Response_msg = " + xpay.Response("LGD_RESPMSG", 0) + "\n");
                    if ("0000".equals(xpay.m_szResCode)) {
                        sb.append("자동취소가 정상적으로 완료 되었습니다.\n");
                    } else {
                        sb.append("자동취소가 정상적으로 처리되지 않았습니다.\n");
                    }
                    resultInfo = HttpUtility.getErrorResultInfo("상점 DB처리 실패로 인하여 Rollback 처리 [TID:" + tid + ",MID:" + mid + ",OID:" + oid + "]");

                    totalSaleService.saveTossTransactionInfo(saleFormVO);
                }

                log.debug(sb.toString());
            }

        } catch (Exception ex) {
            log.error(ExceptionUtil.getErrorLine(ex));
            resultInfo = HttpUtility.getErrorResultInfo(ex.getMessage());
        }

        model.clear();
        model.addAttribute("result", resultInfo);

        return HttpUtility.getViewUrl(request);
    }

    private String getTerminalType(HttpServletRequest request) {
        String edcTrmnlType = Constant.SM_TERMINAL_TYPE_온라인WEB;
        if (request.getAttribute("IS_MOBILE") != null) {
            boolean isMobile = (boolean) request.getAttribute("IS_MOBILE");
            if (isMobile)
                edcTrmnlType = Constant.SM_TERMINAL_TYPE_모바일;
        }
        return edcTrmnlType;
    }

}