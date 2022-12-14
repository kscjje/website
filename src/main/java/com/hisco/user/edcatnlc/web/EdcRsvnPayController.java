package com.hisco.user.edcatnlc.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgMemberDcVO;
import com.hisco.admin.orginfo.vo.OrgOptinfoVO;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.ExceptionUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.cmm.util.RequestUtil;
import com.hisco.cmm.util.SessionUtil;
import com.hisco.cmm.util.TossUtil;
import com.hisco.cmm.util.WebEncDecUtil;
import com.hisco.intrfc.sale.service.NwPayService;
import com.hisco.intrfc.sale.service.PayListService;
import com.hisco.intrfc.sale.service.SaleChargeService;
import com.hisco.intrfc.sale.service.TotalSaleService;
import com.hisco.intrfc.sale.vo.NwPayApiType;
import com.hisco.intrfc.sale.vo.NwPayVO;
import com.hisco.intrfc.sale.vo.PayMethodVO;
import com.hisco.intrfc.sale.vo.PayRequestVO;
import com.hisco.intrfc.sale.vo.SaleFormItemVO;
import com.hisco.intrfc.sale.vo.SaleFormMemberVO;
import com.hisco.intrfc.sale.vo.SaleFormPaymentVO;
import com.hisco.intrfc.sale.vo.SaleFormVO;
import com.hisco.intrfc.sale.vo.TossResponseVO;
import com.hisco.intrfc.sms.service.SmsService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import egovframework.com.cmm.LoginVO;
import lgdacom.XPayClient.XPayClient;
import lombok.extern.slf4j.Slf4j;

/**
 * ???????????? ?????? ?????? ??????
 *
 * @Class Name : EdcRsvnPayController.java
 * @Description : ????????? ????????? ??????
 * @author woojinp@legitsys.co.kr
 * @since 2021. 11. 11.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */

@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/edc/rsvn")
public class EdcRsvnPayController {

    // ???????????????key
    final static private String NW_PAY_BALANCE = "NW_PAY_BALANCE";

    @Value("${tosspayments.platform}")
    private String CST_PLATFORM;

    @Value("${tosspayments.cstmid}")
    private String CST_MID;

    @Value("${tosspayments.conf.path}")
    private String configPath;

    @Value("${Globals.Domain}")
    private String domain;

    // ?????? ?????? ????????? ??????
    @Value("${Globals.SpowiseCms.Key}")
    String SpowiseCmsKey;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    @Resource(name = "smsService")
    private SmsService smsService;

    @Resource(name = "totalSaleService")
    private TotalSaleService totalSaleService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    @Resource(name = "saleChargeService")
    SaleChargeService saleChargeService;

    @Resource(name = "payListService")
    private PayListService payListService;

    @Resource(name = "nwPayService")
    private NwPayService nwPayService;

    /**
     * ??????(edc)??????(rsvn) ??? : ??????????????????
     */
    @GetMapping(value = "/pay/{edcRsvnNo}")
    public String pay(@PathVariable("edcRsvnNo") String edcRsvnNo,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model, Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        EdcRsvnInfoVO param = new EdcRsvnInfoVO();
        param.setEdcRsvnNo(edcRsvnNo);
        List<EdcRsvnInfoVO> rsvnInfoList = edcRsvnInfoService.selectRsvnListForPay(param);

        LoginVO loginVO = commandMap.getUserInfo();
        if (loginVO == null || StringUtils.isBlank(loginVO.getName())){
            HttpUtility.sendRedirect(request, response, "????????? ??? ???????????????.", Config.USER_ROOT + "/main");
            return null;
        }

        if (rsvnInfoList == null || rsvnInfoList.isEmpty() || rsvnInfoList.size() < 1){
            HttpUtility.sendRedirect(request, response, "???????????? ?????? ??????????????????.", Config.USER_ROOT + "/mypage/myRsvn/myRsvnList");
            return null;
        }

        EdcRsvnInfoVO rsvnInfo = rsvnInfoList.get(0);
        if (!Constant.SM_RSVN_STAT_????????????.equals(rsvnInfo.getEdcStat())) {
            HttpUtility.sendRedirect(request, response, "????????????????????? ????????????.", Config.USER_ROOT + "/mypage/myRsvn/myRsvnList");
            return null;
        }

        if (Config.YES.equals(rsvnInfo.getCloseYn())) {
            HttpUtility.sendRedirect(request, response, "??????????????????????????? ?????????????????????.", Config.USER_ROOT + "/mypage/myRsvn/myRsvnList");
            return null;
        }

        if (Config.NO.equals(rsvnInfo.getVbankCloseYn()) && Constant.SM_VBANK_PAYMENT_STATUS_????????????.equals(rsvnInfo.getVbankStatus())) {
            HttpUtility.sendRedirect(request, response, "???????????? ????????? ???????????????.", Config.USER_ROOT + "/mypage/myRsvn/myRsvnDetail?edcRsvnReqid=" + rsvnInfo.getEdcRsvnReqid());
            return null;
        }

        if (Config.NO.equals(rsvnInfo.getExclDcyn())) { // ????????????????????? ????????????
            String memNo = null;
            if (commandMap.getUserInfo() != null) {
                memNo = commandMap.getUserInfo().getUniqId();
            }

            OrgMemberDcVO orgMemberDcVO = new OrgMemberDcVO();
            orgMemberDcVO.setComcd(rsvnInfo.getComcd());
            orgMemberDcVO.setOrgNo(rsvnInfo.getOrgNo());
            orgMemberDcVO.setMemNo(memNo);
            model.addAttribute("dcList", orgInfoService.selectOrgMemberDcList(orgMemberDcVO));
        }

        OrgOptinfoVO orgOptinfoParam = new OrgOptinfoVO();
        orgOptinfoParam.setOrgNo(rsvnInfo.getOrgNo());
        OrgOptinfoVO orgOptinfo = orgInfoService.selectOrgOptinfo(orgOptinfoParam);

        if (StringUtils.isNotBlank(orgOptinfo.getWebpaymentid()))
            CST_MID = orgOptinfo.getWebpaymentid(); // properties -> db?????? ????????? ??????

        // TOSS parameter
        String requestContext = request.getContextPath();
        paramMap.put("CST_WINDOW_TYPE", (RequestUtil.isMobile(request) ? "submit" : "iframe"));
        paramMap.put("LGD_CUSTOM_SWITCHINGTYPE", (RequestUtil.isMobile(request) ? "SUBMIT" : "IFRAME"));
        paramMap.put("CST_MID", CST_MID);
        paramMap.put("CST_PLATFORM", CST_PLATFORM);
        paramMap.put("LGD_MID", ("test".equals(CST_PLATFORM.trim()) ? "t" : "") + CST_MID);
        paramMap.put("LGD_RETURNURL", domain + requestContext + "/web/edc/rsvn/pay/toss/returnUrl");
        paramMap.put("LGD_CASNOTEURL", domain + requestContext + "/web/edc/rsvn/pay/toss/casNoteUrl");
        paramMap.put("LGD_OID", saleChargeService.selectNextOid());
        // SessionUtil.setAttribute(TOSS_PARAMETER, paramMap);

        model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("rsvnInfo", rsvnInfo);
        model.addAttribute("paramMap", paramMap);
        model.addAttribute("paymentMethods", orgOptinfo.getWebPaymentMethod()); // ?????? ,??? ???????????? ??????

        PayMethodVO pmParam = new PayMethodVO();
        pmParam.setPComcd(Constant.PG_TOSS);
        pmParam.setPType(Constant.SITE_P_TYPE_?????????????????????);
        model.addAttribute("bankList", payListService.selectPayMethodList(pmParam));

        // ???????????? 3??????(?????????????????????+?????????????????????+???????????????) ??????????????????
        EdcRsvnInfoVO limitVO = new EdcRsvnInfoVO();
        limitVO.setEdcRsvnCustnm(loginVO.getName());
        limitVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
        limitVO.setEdcReqSdate(rsvnInfoList.get(0).getEdcReqSdate());
        limitVO.setEdcPrgmNo(rsvnInfoList.get(0).getEdcPrgmNo());
        limitVO.setEdcRsvnNo(rsvnInfoList.get(0).getEdcRsvnNo());
        List<EdcRsvnInfoVO> limitList = edcRsvnInfoService.selectNowon3BasicOrgRegiList(limitVO);

        if (limitList == null || limitList.isEmpty()) {
            model.addAttribute("dcLimitYn", Config.NO);
        } else {
            model.addAttribute("dcLimitYn", Config.YES);
        }

        return Config.USER_ROOT + "/edc/rsvn/rsvnPay";
    }

    @PostMapping(value = "/nwpay/balance.json")
    public String nwpayBalanceJson(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap,
            CommandMap commandMap) throws Exception {

        ResultInfo result = new ResultInfo(Config.SUCCESS, "??????");
        NwPayVO resPayVO = null;

        try {
            String nwpayId = (String) paramMap.get("nwpayId");

            if (StringUtils.isBlank(nwpayId)) {
                throw new RuntimeException("???????????? ???????????? ?????????????????????.");
            }

            NwPayVO reqPayVO = new NwPayVO();
            reqPayVO.setApiType(NwPayApiType.BALANCE);
            reqPayVO.setUseid(nwpayId);
            reqPayVO.setOrgNo(Integer.parseInt((String) paramMap.get("orgNo")));

            resPayVO = nwPayService.call(reqPayVO);

            if ("200".equals(resPayVO.getStatus())) {
                SessionUtil.setAttribute(NW_PAY_BALANCE, Integer.toString(resPayVO.getAmount()));
            }
        } catch (Exception ex) {
            log.error(ExceptionUtil.getErrorLine(ex));
            result = new ResultInfo(Config.FAIL, ex.getMessage());
        }

        model.clear();
        model.addAttribute("result", result);
        model.addAttribute("nwPay", resPayVO);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * TOSS????????? ?????? ??????
     */
    @PostMapping(value = "/pay/toss/prepare.json")
    public String prepareJson(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap,
            CommandMap commandMap) throws Exception {

        ResultInfo result = new ResultInfo(Config.SUCCESS, "??????");

        try {
            int orgNo = Integer.parseInt((String) paramMap.get("orgNo"));
            String rsvnNo = (String) paramMap.get("rsvnNo");
            String LGD_OID = (String) paramMap.get("LGD_OID");
            String CST_MID = (String) paramMap.get("CST_MID");
            String LGD_MID = (String) paramMap.get("LGD_MID");
            String LGD_AMOUNT = (String) paramMap.get("LGD_AMOUNT");
            BigDecimal nwpayAmt = new BigDecimal((String) paramMap.get("nwpayAmt"));

            if (StringUtils.isBlank(CST_MID) || StringUtils.isBlank(LGD_MID) || StringUtils.isBlank(LGD_AMOUNT)) {
                throw new RuntimeException("?????? ??????????????? ?????????????????????.");
            }

            if (nwpayAmt.compareTo(BigDecimal.ZERO) > 0) { // ??????????????????
                BigDecimal balance = new BigDecimal(StringUtils.defaultIfEmpty((String) SessionUtil.getAttribute(NW_PAY_BALANCE), "0"));
                if (balance.compareTo(nwpayAmt) < 0) { // balance??? ?????????????????? ?????????
                    throw new RuntimeException(String.format("??????PAY????????????(%s)??? ???????????? ?????? ??????PAY??????(%s)?????? ?????????.", nwpayAmt, balance));
                }
            }

            OrgOptinfoVO orgOptinfoParam = new OrgOptinfoVO();
            orgOptinfoParam.setOrgNo(orgNo);
            OrgOptinfoVO orgOptinfo = orgInfoService.selectOrgOptinfo(orgOptinfoParam);
            paramMap.put("mertkey", orgOptinfo.getWebpayapiKey());
            if (StringUtils.isBlank(orgOptinfo.getWebpayapiKey()))
                throw new RuntimeException("orgNo(" + orgNo + ")'s mertkey is null");

            paramMap.put("LGD_TIMESTAMP", DateUtil.printDatetime(null, "yyyyMMddHHmmss"));
            paramMap.put("LGD_HASHDATA", TossUtil.getHashData(paramMap));

            // SessionUtil.setAttribute(TOSS_PARAMETER, paramMap); // toss ????????? ??? ????????? ??? ??? ?????? ???????????? ??????

            EdcRsvnInfoVO param = new EdcRsvnInfoVO();
            param.setEdcRsvnNo(rsvnNo);
            List<EdcRsvnInfoVO> rsvnInfoList = edcRsvnInfoService.selectRsvnListForPay(param);

            /** PG_ORD_MT, PG_ORD_DET ?????? */
            SaleFormVO saleFormVO = new SaleFormVO();
            List<SaleFormItemVO> itemList = new ArrayList<SaleFormItemVO>();
            SaleFormPaymentVO payment = new SaleFormPaymentVO();

            for (EdcRsvnInfoVO vo : rsvnInfoList) {
                SaleFormItemVO item = new SaleFormItemVO();
                item.setComcd(vo.getComcd());
                item.setItemCd(vo.getEdcReqItemCd());
                item.setEdcRsvnReqid(vo.getEdcRsvnReqid());
                item.setCostAmt(vo.getEdcProgmCost());
                item.setSalamt(vo.getEdcProgmCost());
                item.setDiscountCd((String) paramMap.get("dcCd"));
                item.setDiscountRate(new BigDecimal((String) paramMap.get("dcRate")).intValue());
                item.setDcAmt(new BigDecimal((String) paramMap.get("dcAmt")).intValue());
                item.setNwpayAmt(new BigDecimal((String) paramMap.get("nwpayAmt")).intValue());
                item.setNwpayId((String) paramMap.get("nwpayId"));
                itemList.add(item);

                if (StringUtils.isBlank(item.getDiscountCd()) || "NWPY".equals(item.getDiscountCd()))
                    continue;

                // ???????????? 3??????(?????????????????????+?????????????????????+???????????????) ??????????????????
                LoginVO loginVO = commandMap.getUserInfo();
                EdcRsvnInfoVO limitVO = new EdcRsvnInfoVO();
                limitVO.setEdcRsvnCustnm(loginVO.getName());
                limitVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
                limitVO.setEdcReqSdate(vo.getEdcReqSdate());
                limitVO.setEdcPrgmNo(vo.getEdcPrgmNo());
                limitVO.setEdcRsvnNo(vo.getEdcRsvnNo());
                List<EdcRsvnInfoVO> limitList = edcRsvnInfoService.selectNowon3BasicOrgRegiList(limitVO);

                if (limitList != null && !limitList.isEmpty()) {
                    throw new RuntimeException("?????????????????????, ?????????????????????, ?????????????????? ?????? ??? 1?????? ???????????? ?????? ??? ????????????. ?????????????????? ?????? ???????????? ???????????????, ???????????? ??? ????????????.");
                }
            }

            payment.setOid(LGD_OID);
            payment.setPayAmt(Integer.parseInt(LGD_AMOUNT));
            payment.setOidStat(Constant.SM_OID_STAT_????????????);
            payment.setRsvnNo(rsvnNo);
            payment.setPayMethod((String) paramMap.get("payMethod"));

            payment.setDpstrNm((String) paramMap.get("dpstrNm"));
            payment.setBankCd((String) paramMap.get("bankCd"));
            payment.setBankNm((String) paramMap.get("bankNm"));
            payment.setAccountNum((String) paramMap.get("accountNum"));

            saleFormVO.setItemList(itemList);
            saleFormVO.setPayment(payment);
            saleFormVO.setMember(this.setSaleFormMemberVO(commandMap.getUserInfo()));

            totalSaleService.save1StepTablesForPG(saleFormVO);
        } catch (Exception ex) {
            log.error(ExceptionUtil.getErrorLine(ex));
            result = new ResultInfo(Config.FAIL, ex.getMessage());
        }

        model.clear();
        model.addAttribute("result", result);
        model.addAttribute("paramMap", paramMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ????????????: ?????????100% => ??????????????????
     * ????????????: (?????????xx%) + PG?????? => ??????????????????(??????, ?????????)
     * ????????????: (?????????xx%) + PG?????? => pg????????????(???????????????)
     */
    @PostMapping(value = "/pay.json")
    public String payJson(PayRequestVO payRequestVO,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model, Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        ResultInfo result = new ResultInfo(Config.SUCCESS, "??????????????? ????????? ?????????????????????");

        LoginVO loginVO = commandMap.getUserInfo();

        try {
            int saveCnt;

            EdcRsvnInfoVO rsvnParam = new EdcRsvnInfoVO();
            rsvnParam.setEdcRsvnNo(payRequestVO.getRsvnNo());
            List<EdcRsvnInfoVO> rsvnInfoList = edcRsvnInfoService.selectRsvnListForPay(rsvnParam);

            if (rsvnInfoList == null || rsvnInfoList.isEmpty())
                HttpUtility.sendRedirect(request, response, "???????????? ?????? ??????????????????.", Config.USER_ROOT + "/edc/program/list");

            EdcRsvnInfoVO rsvnInfo = rsvnInfoList.get(0);
            if (!Constant.SM_RSVN_STAT_????????????.equals(rsvnInfo.getEdcStat())) {
                HttpUtility.sendRedirect(request, response, "????????????????????? ????????????.", Config.USER_ROOT + "/mypage/myRsvn/myRsvnList");
            }

            if (Config.YES.equals(rsvnInfo.getCloseYn())) {
                HttpUtility.sendRedirect(request, response, "??????????????????????????? ?????????????????????.", Config.USER_ROOT + "/mypage/myRsvn/myRsvnList");
            }

            // ???????????? -> ????????????
            SaleFormVO saleFormVO = new SaleFormVO();
            saleFormVO.setRsvnStat(Constant.SM_RSVN_STAT_????????????);

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
            item.setCostAmt(rsvnInfo.getEdcProgmCost());
            item.setSalamt(rsvnInfo.getEdcProgmCost());
            item.setDcAmt(payRequestVO.getDcAmt());
            item.setDiscountCd(payRequestVO.getDcCd());
            item.setDiscountRate(payRequestVO.getDcRate());
            item.setMonthCnt(rsvnInfo.getEdcMonthcnt());
            item.setEdcPrgmNo(rsvnInfo.getEdcPrgmNo());
            item.setEdcSdate(rsvnInfo.getEdcReqSdate());
            item.setEdcEdate(rsvnInfo.getEdcReqEdate());
            item.setEdcRsvnsetSeq(rsvnInfo.getEdcRsvnsetSeq());

            SaleFormPaymentVO payment = new SaleFormPaymentVO();
            payment.setOnoff(Constant.EDC_ONOFFIN_TYPE_ON);
            payment.setRergistGbn(Constant.SM_REGIST_GBN_????????????);
            payment.setPayAmt(payRequestVO.getPayAmt());
            payment.setTerminalType(rsvnInfo.getEdcTrmnlType());
            payment.setRsvnNo(payRequestVO.getRsvnNo());

			if (payRequestVO.getPayAmt() == 0) {
				payment.setPayComcd(Constant.PG_SELF);
				payment.setPayMethod(Constant.SITE_P_TYPE_??????);
				payment.setFinanceCd(Constant.SITE_DEFAULT_FINANCECD);
			}

            saleFormVO.setMember(member);
            saleFormVO.setItemList(Arrays.asList(item));
            saleFormVO.setPayment(payment);

            log.debug("{}", JsonUtil.toPrettyJson(saleFormVO));

            saveCnt = totalSaleService.register(saleFormVO);
            log.debug("saveCnt = {}", saveCnt);

            /*
             * if (saveCnt > 0)
             * this.sendMessage(rsvnInfoParam, programDetailVO, loginVO);
             */

        } catch (Exception ex) {
            result.setCode(Config.FAIL);
            result.setMsg(ex.getMessage());
            log.error(ExceptionUtil.getErrorLine(ex));
        }

        model.clear();
        model.addAttribute("result", result);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * PG????????? ???????????? url??? session ?????? ?????? ??????
     */
    @PostMapping(value = "/pay/toss/returnUrl")
    public String tossReturnUrl(TossResponseVO tossResponseVO, HttpServletRequest request, HttpServletResponse response,
            ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        if (RequestUtil.isMobile(request)) { // MOBILE
            EdcRsvnInfoVO rsvnInfoParam = new EdcRsvnInfoVO();
            rsvnInfoParam.setOid(tossResponseVO.getLGD_OID());
            EdcRsvnInfoVO rsvnInfoVO = edcRsvnInfoService.selectRsvnInfoMember(rsvnInfoParam);
            if (rsvnInfoVO == null || StringUtils.isBlank(rsvnInfoVO.getEdcRsvnCustnm())) {
                HttpUtility.sendRedirect(request, response, "??????????????? ???????????? ????????????.", Config.USER_ROOT + "/mypage/myRsvn/myRsvnList");
            }

            // ??????????????? ???????????? ????????? ????????? ??? ???????????? ??? ??????????????? ??????
            LoginVO loginVO = doLoginVOWithRsvnInfoMember(tossResponseVO, request, response, rsvnInfoVO, commandMap.getUserInfo());

            // ????????? ?????? ????????????
            tossResponseVO.setRsvnNo(rsvnInfoVO.getEdcRsvnNo());
            ResultInfo resultInfo = tossCommonCompleteProcess(tossResponseVO, request, loginVO);
            if (Config.FAIL.equals(resultInfo.getCode())) {
                HttpUtility.sendRedirect(request, response, resultInfo.getMsg(), Config.USER_ROOT + "/mypage/myRsvn/myRsvnList");
            } else {
                response.sendRedirect(request.getContextPath() + Config.USER_ROOT + "/edc/rsvn/complete/" + rsvnInfoVO.getEdcRsvnNo());
            }
        } else { // PC
            paramMap.put("LGD_PARAMS", WebEncDecUtil.fn_encrypt(JsonUtil.Object2String(tossResponseVO), SpowiseCmsKey));
            model.addAttribute("paramMap", paramMap);
        }
        return Config.USER_ROOT + "/edc/rsvn/tossReturnUrl";
    }

    @PostMapping(value = "/pay/toss/complete.json")
    public String tossCompleteJson(TossResponseVO paramVO, HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        LoginVO userInfo = commandMap.getUserInfo();

        ResultInfo resultInfo = tossCommonCompleteProcess(paramVO, request, userInfo);

        model.clear();
        model.addAttribute("result", resultInfo);

        return HttpUtility.getViewUrl(request);
    }

    private ResultInfo tossCommonCompleteProcess(TossResponseVO paramVO, HttpServletRequest request, LoginVO userInfo) {

        log.debug("TossResponseVO => {}", JsonUtil.toPrettyJson(paramVO));

        ResultInfo resultInfo = new ResultInfo(Config.SUCCESS, "??????");

        StringBuffer sb = new StringBuffer();

        String tid;
        String mid;
        String oid;
        String LGD_PAYTYPE = "";

        boolean isDBOK = false;
        SaleFormVO saleFormVO = new SaleFormVO();

        /*************************************************
         * 1.???????????? ?????? - BEGIN
         *************************************************/
        // String CST_PLATFORM = request.getParameter("CST_PLATFORM");
        // String CST_MID = request.getParameter("CST_MID");
        String LGD_MID = ("test".equals(CST_PLATFORM.trim()) ? "t" : "") + CST_MID;
        String LGD_PAYKEY = paramVO.getLGD_PAYKEY(); // String LGD_PAYKEY = request.getParameter("LGD_PAYKEY");

        XPayClient xpay = new XPayClient();
        boolean isInitOK = xpay.Init(configPath, CST_PLATFORM);

        int orgNo = 0;

        try {

            if (userInfo == null) {
                throw new RuntimeException("??????????????? ???????????? ????????????.");
            }

            if (!isInitOK) {
                // API ????????? ?????? ????????????
                sb.append("??????????????? ????????? ????????? ?????????????????????.\n");
                sb.append("LG?????????????????? ????????? ??????????????? ??????????????? ?????? ???????????? ??????????????? ????????????.\n");
                sb.append("mall.conf?????? Mert ID = Mert Key ??? ????????? ???????????? ????????? ?????????.\n\n");
                sb.append("???????????? LG???????????? 1544-7772\n");
                throw new RuntimeException(sb.toString());
            }

            // (3) Init_TX: ???????????? mall.conf, lgdacom.conf ?????? ??? ??????????????? ????????? ??? TXID ??????
            xpay.Init_TX(LGD_MID);
            xpay.Set("LGD_TXNAME", "PaymentByKey");
            xpay.Set("LGD_PAYKEY", LGD_PAYKEY);

            /*
             * if ("service".equals(CST_PLATFORM)) {
             * Map<String, Object> tossParamMap = (Map<String, Object>) SessionUtil.getAttribute(TOSS_PARAMETER);
             * String DB_AMOUNT = (String) tossParamMap.get("LGD_AMOUNT"); // ??????????????? ?????? ??????
             * xpay.Set("LGD_AMOUNTCHECKYN", "Y");
             * xpay.Set("LGD_AMOUNT", DB_AMOUNT);
             * }
             */
            // xpay.Set("LGD_AMOUNT", "0"); // ????????? ?????? TEST

            /*************************************************
             * 2. ???????????? ?????? ????????????
             * TX: lgdacom.conf??? ????????? URL??? ?????? ???????????? ??????????????????, ??????????????? true, false ??????
             *************************************************/
            if (!xpay.TX()) {
                // 2)API ???????????? ????????????
                sb.append("??????????????? ?????????????????????.  \n");
                sb.append("TX ???????????? Response_code = " + xpay.m_szResCode + "\n");
                sb.append("TX ???????????? Response_msg = " + xpay.m_szResMsg + "\n");
                throw new RuntimeException(sb.toString());
            }

            if (log.isDebugEnabled()) {
                Map<String, String> xpayResponseMap = new HashMap<String, String>();
                for (int i = 0; i < xpay.ResponseNameCount(); i++) {
                    String value = "";
                    for (int j = 0; j < xpay.ResponseCount(); j++) {
                        if (j > 0)
                            value += ",";
                        value += xpay.Response(xpay.ResponseName(i), j);
                    }
                    xpayResponseMap.put(xpay.ResponseName(i), value);
                }
                log.debug("[toss final transaction] XpayResponseMap => {}", JsonUtil.toPrettyJson(xpayResponseMap));
            }

            tid = xpay.Response("LGD_TID", 0);
            mid = xpay.Response("LGD_MID", 0);
            oid = xpay.Response("LGD_OID", 0);
            LGD_PAYTYPE = xpay.Response("LGD_PAYTYPE", 0);

            log.debug(String.format("tid:mid:oid = %s:%s:%s", tid, mid, oid));
            log.debug(String.format("m_szResCode:m_szResMsg = %s:%s", xpay.m_szResCode, xpay.m_szResMsg));

            // (5)DB??? ?????? ?????? ??????
            /*
             * ?????????????????? ????????? DB???????????????. (???????????? ?????? ?????? ?????? DB?????? ??????)
             * ????????? DB??? ????????? ????????? ????????? ?????? ???????????? false??? ????????? ?????????.
             * ?????? DB?????? ????????? Rollback ??????, isDBOK ??????????????? false ??? ??????
             */

            try {
                EdcRsvnInfoVO rsvnInfoParam = new EdcRsvnInfoVO();
                rsvnInfoParam.setComcd(paramVO.getComcd());
                rsvnInfoParam.setEdcRsvnNo(paramVO.getRsvnNo());
                List<EdcRsvnInfoVO> rsvnInfoList = edcRsvnInfoService.selectRsvnListForPay(rsvnInfoParam);

                if (rsvnInfoList == null || rsvnInfoList.isEmpty()) {
                    throw new RuntimeException("???????????? ???????????? ????????????. edcRsvnNo = " + paramVO.getRsvnNo());
                }

                if (!Constant.SM_RSVN_STAT_????????????.equals(rsvnInfoList.get(0).getEdcStat())) {
                    throw new RuntimeException("?????? ???????????? ???????????? ?????????  ????????????. edcRsvnNo = " + paramVO.getRsvnNo());
                }

                orgNo = rsvnInfoList.get(0).getOrgNo();

                List<String> OK_LIST = Arrays.asList("0000", "A016");
                if (!OK_LIST.contains(xpay.m_szResCode)) {
                    sb.append("[").append(xpay.m_szResCode).append("] ").append(xpay.m_szResMsg);
                    if (Constant.TOSS_P_TYPE_??????.equals(LGD_PAYTYPE) || Constant.TOSS_P_TYPE_?????????????????????.equals(LGD_PAYTYPE)) {
                        sb.append("?????????????????? ?????? ?????? DB??????????????? ????????????.\n");
                        this.saveCardAppHist(paramVO, request, userInfo, xpay, rsvnInfoList);
                    }
                    isDBOK = true;
                    throw new RuntimeException("[????????????] " + xpay.m_szResMsg);
                }

                this.setSaleFormVO(paramVO, request, userInfo, xpay, saleFormVO, rsvnInfoList);

                if (Constant.TOSS_P_TYPE_??????.equals(LGD_PAYTYPE) || Constant.TOSS_P_TYPE_?????????????????????.equals(LGD_PAYTYPE)) {
                    int saveCnt = totalSaleService.register(saleFormVO);
                    if (saveCnt > 2)
                        isDBOK = true;
                } else if (Constant.TOSS_P_TYPE_????????????.equals(LGD_PAYTYPE)) { // ??????????????? ????????? ?????? ??????
                    int saveCnt = totalSaleService.updateVBankInfo(saleFormVO);
                    if (saveCnt >= 1)
                        isDBOK = true;
                }

                // ?????????????????????, ??????????????? ?????????????????????
            } catch (Exception ex) {
                log.error(ExceptionUtil.getErrorLine(ex));
                resultInfo = new ResultInfo(Config.FAIL, ex.getMessage());
            } finally {
                if (!isDBOK) {
                    xpay.Rollback("?????? DB?????? ????????? ????????? Rollback ?????? [TID:" + tid + ",MID:" + mid + ",OID:" + oid + "]");
                    sb.append("TX Rollback Response_code:Response_msg = " + xpay.Response("LGD_RESPCODE", 0) + ":" + xpay.Response("LGD_RESPMSG", 0) + "\n");
                    if ("0000".equals(xpay.m_szResCode)) {
                        sb.append("??????????????? ??????????????? ?????? ???????????????.\n");
                    } else {
                        sb.append("??????????????? ??????????????? ???????????? ???????????????.\n");
                    }
                    log.error(sb.toString());
                    log.error("?????? DB?????? ????????? ????????? Rollback ?????? [TID:" + tid + ",MID:" + mid + ",OID:" + oid + "]");

                    resultInfo = new ResultInfo(Config.FAIL, "DB????????? ???????????? ?????? ???????????? ???????????????.");

                    if (Constant.TOSS_P_TYPE_??????.equals(LGD_PAYTYPE) || Constant.TOSS_P_TYPE_?????????????????????.equals(LGD_PAYTYPE)) {
                        saleFormVO.getPayment().setResultCd(xpay.Response("LGD_RESPCODE", 0));
                        saleFormVO.getPayment().setResultMsg(xpay.Response("LGD_RESPMSG", 0) + "|" + resultInfo.getMsg() + "|" + sb.toString());
                        saleFormVO.getPayment().setPayListYn(Config.NO);
                        totalSaleService.saveTossTransactionInfo(saleFormVO);
                    }

                    String nwpayOrderid = (String) SessionUtil.getAttribute(Constant.NWPAY_ORDERID_SESSION_KEY);
                    if (StringUtils.isNotBlank(nwpayOrderid)) {
                        NwPayVO reqPayVO = new NwPayVO();
                        reqPayVO.setApiType(NwPayApiType.WITHDRAWAL);
                        reqPayVO.setOrderID(nwpayOrderid);
                        reqPayVO.setOrgNo(orgNo);
                        NwPayVO resPayVO = nwPayService.call(reqPayVO);
                        if (!resPayVO.success()) {
                            log.error("NWPAY ?????? reqPayVO = {}", JsonUtil.toPrettyJson(reqPayVO));
                            log.error("NWPAY ?????? resPayVO = {}", JsonUtil.toPrettyJson(resPayVO));
                            throw new RuntimeException("??????PAY > " + resPayVO.getStatusDesc());
                        } else {
                            SessionUtil.removeAttribute(Constant.NWPAY_ORDERID_SESSION_KEY);
                            log.info("NWPAY ?????? resPayVO = {}", JsonUtil.toPrettyJson(resPayVO));
                        }
                    }
                }
            }

        } catch (Exception ex) {
            log.error(ExceptionUtil.getErrorLine(ex));
            resultInfo = new ResultInfo(Config.FAIL, ex.getMessage());
        }

        return resultInfo;
    }

    /**
     * ?????? ????????? ????????? ?????? ??? ??????
     */
    private void setSaleFormVO(TossResponseVO paramVO, HttpServletRequest request, LoginVO userInfo, XPayClient xpay,
            SaleFormVO saleFormVO, List<EdcRsvnInfoVO> rsvnInfoList) {

        TossResponseVO tossReturnVO = paramVO.getLGD_PARAMS(SpowiseCmsKey);

        if (tossReturnVO == null) {
            tossReturnVO = paramVO;
        }

        log.debug("tossReturnVO = {}", JsonUtil.toPrettyJson(tossReturnVO));

        SaleFormMemberVO member = this.setSaleFormMemberVO(userInfo);

        int totalSaleAmt = 0;
        int totalDcAmt = 0;
        int totalPayAmt = 0;

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

            if ("NWPY".equals(vo.getEdcReasondc())) {
                item.setNwpayAmt(vo.getEdcDcamt());
                item.setNwpayId(vo.getNwpayUserid());
            }

            item.setMonthCnt(vo.getEdcMonthcnt());
            item.setEdcPrgmNo(vo.getEdcPrgmNo());

            item.setEdcSdate(vo.getEdcReqSdate());
            item.setEdcEdate(vo.getEdcReqEdate());
            item.setEdcRsvnsetSeq(vo.getEdcRsvnsetSeq());
            item.setVatYn(Config.NO);
            itemList.add(item);

            totalSaleAmt += item.getSalamt();
            totalDcAmt += item.getDcAmt();
        }
        totalPayAmt = totalSaleAmt - totalDcAmt;

        SaleFormPaymentVO payment = new SaleFormPaymentVO();
        payment.setOnoff(Constant.EDC_ONOFFIN_TYPE_ON);
        payment.setRergistGbn(Constant.SM_REGIST_GBN_????????????);
        payment.setRsvnNo(paramVO.getRsvnNo());
        payment.setPayAmt(totalPayAmt);
        payment.setDcAmt(totalDcAmt);

        payment.setIp(tossReturnVO.getLGD_BUYERIP());
        payment.setFinanceCd(tossReturnVO.getLGD_FINANCECODE()); // ??????????????? returnUrl????????? 2???????????? return. but xpayclient?????????
                                                                 // 5??????(cardcode + brancode)??? ?????????. ???)51100
        payment.setFinanceNm(tossReturnVO.getLGD_FINANCENAME()); // ??????????????? returnUrl????????? ???????????? return. but xpayclient?????????
                                                                 // ????????????+??????????????? ?????????. ???)??????VISA

        if (StringUtils.isBlank(payment.getFinanceCd())) {
            payment.setFinanceCd(xpay.Response("LGD_FINANCECODE", 0));
        }
        if (StringUtils.isBlank(payment.getFinanceNm())) {
            payment.setFinanceNm(xpay.Response("LGD_FINANCENAME", 0));
        }

        String LGD_PAYTYPE = xpay.Response("LGD_PAYTYPE", 0);
        if (Constant.TOSS_P_TYPE_??????.equals(LGD_PAYTYPE)) {// ????????????
            String financeCd = xpay.Response("LGD_FINANCECODE", 0); // ex) 51100
            String financeNm = xpay.Response("LGD_FINANCENAME", 0); // ex) ??????visia

            if (financeCd.length() == 5) {
                payment.setFinanceCd(financeCd.substring(0, 2)); // 51
                payment.setFinanceBrandCd(financeCd.substring(2, 5)); // 100
                payment.setFinanceBrandNm(financeNm.replace(payment.getFinanceNm(), "")); // visa
            }

            if (!payment.getFinanceNm().contains("??????")) {
                payment.setFinanceNm(payment.getFinanceNm() + "??????");
            }

            payment.setPayMethod(Constant.SITE_P_TYPE_??????);
            payment.setCardAmt(totalPayAmt);
            String cardNum = xpay.Response("LGD_CARDNUM", 0);
            payment.setCardNo1(cardNum.substring(0, 4));
            payment.setCardNo2(cardNum.substring(4, 8));
            payment.setCardNo3(cardNum.substring(8, 12));
            payment.setCertNo(xpay.Response("LGD_FINANCEAUTHNUM", 0)); // ??????????????????
            payment.setHalbuCnt(Integer.parseInt(xpay.Response("LGD_CARDINSTALLMONTH", 0)));
        } else if (Constant.TOSS_P_TYPE_?????????????????????.equals(LGD_PAYTYPE)) {// ?????????
            payment.setPayMethod(Constant.SITE_P_TYPE_?????????????????????);
            payment.setCashAmt(totalPayAmt);
            payment.setCertNo(xpay.Response("LGD_CASHRECEIPTNUM", 0)); // ??????????????? ????????????
            payment.setCashReceiptKind(xpay.Response("LGD_CASHRECEIPTKIND", 0));
        } else if (Constant.TOSS_P_TYPE_????????????.equals(LGD_PAYTYPE)) {// ????????????
            payment.setPayMethod(Constant.SITE_P_TYPE_????????????);
            payment.setCashAmt(totalPayAmt);
            payment.setCertNo(xpay.Response("LGD_CASHRECEIPTNUM", 0)); // ??????????????? ????????????
            payment.setCashReceiptKind(xpay.Response("LGD_CASHRECEIPTKIND", 0));

            payment.setVbankStatus(Constant.SM_VBANK_PAYMENT_STATUS_????????????);

            // ??????????????????
            payment.setVbankAccountNo(xpay.Response("LGD_ACCOUNTNUM", 0));
            payment.setVbankBankcd(xpay.Response("LGD_FINANCECODE", 0));
            payment.setVbankName(xpay.Response("LGD_FINANCENAME", 0));
            payment.setVbankPname(xpay.Response("LGD_PAYER", 0));
            payment.setVbankPtel(xpay.Response("LGD_BUYERPHONE", 0));
            payment.setVbankPemail(xpay.Response("LGD_BUYEREMAIL", 0));
            payment.setVbankPayWaitdate(tossReturnVO.getLGD_CLOSEDATE().substring(0, 8));
            payment.setVbankPayWaittime(tossReturnVO.getLGD_CLOSEDATE().substring(8));

            // ???????????????????????????
            payment.setDpstrNm(paramVO.getDpstrNm());
            payment.setBankCd(paramVO.getBankCd());
            payment.setBankNm(paramVO.getBankNm());
            payment.setAccountNum(paramVO.getAccountNum());

            String paydate = xpay.Response("LGD_PAYDATE", 0);
            payment.setSaleDate(paydate.substring(0, 8));
            payment.setSaleTime(paydate.substring(8));
        }

        payment.setResultMsg(xpay.Response("LGD_RESPMSG", 0));
        payment.setResultCd(xpay.Response("LGD_RESPCODE", 0));
        payment.setCertDtime(xpay.Response("LGD_PAYDATE", 0));

        payment.setTid(xpay.Response("LGD_TID", 0));
        payment.setMid(xpay.Response("LGD_MID", 0));
        payment.setOid(xpay.Response("LGD_OID", 0));

        payment.setTerminalType(getTerminalType(request));
        payment.setAppGbn(Constant.SM_APP_GBN_????????????);

        saleFormVO.setMember(member);
        saleFormVO.setItemList(itemList);
        saleFormVO.setPayment(payment);
    }

    /**
     * ?????? ????????? CARD_APP_HIST?????? ??????
     */
    private void saveCardAppHist(TossResponseVO paramVO, HttpServletRequest request, LoginVO userInfo,
            XPayClient xpay, List<EdcRsvnInfoVO> rsvnList) {

        if ("0000".equals(xpay.m_szResCode)) { // ??????
            return;
        }

        SaleFormVO saleFormVO = new SaleFormVO();
        List<SaleFormItemVO> itemList = new ArrayList<SaleFormItemVO>();

        String edcRsvnNo = null;
        if (rsvnList != null && !rsvnList.isEmpty()) {
            edcRsvnNo = rsvnList.get(0).getEdcRsvnNo();

            SaleFormItemVO item = new SaleFormItemVO();
            item.setComcd(rsvnList.get(0).getComcd());
            item.setOrgNo(rsvnList.get(0).getOrgNo());
            itemList.add(item);
        }

        TossResponseVO tossReturnVO = paramVO.getLGD_PARAMS(SpowiseCmsKey);
        SaleFormPaymentVO payment = new SaleFormPaymentVO();

        String LGD_PAYTYPE = xpay.Response("LGD_PAYTYPE", 0);

        if (Constant.TOSS_P_TYPE_??????.equals(LGD_PAYTYPE)) {// ????????????
            payment.setPayMethod(Constant.SITE_P_TYPE_??????);
            String cardNum = xpay.Response("LGD_CARDNUM", 0);
            if (StringUtils.isNotBlank(cardNum) && cardNum.length() > 12) {
                payment.setCardNo1(cardNum.substring(0, 4));
                payment.setCardNo2(cardNum.substring(4, 8));
                payment.setCardNo3(cardNum.substring(8, 12));
            }
            payment.setCertNo(xpay.Response("LGD_FINANCEAUTHNUM", 0)); // ??????????????????
            payment.setHalbuCnt(Integer.parseInt(xpay.Response("LGD_CARDINSTALLMONTH", 0)));
        } else if (Constant.TOSS_P_TYPE_?????????????????????.equals(LGD_PAYTYPE)) {// ?????????
            payment.setPayMethod(Constant.SITE_P_TYPE_?????????????????????);
            payment.setCertNo(xpay.Response("LGD_CASHRECEIPTNUM", 0)); // ??????????????? ????????????
            payment.setCashReceiptKind(xpay.Response("LGD_CASHRECEIPTKIND", 0));
        } else if (Constant.TOSS_P_TYPE_????????????.equals(LGD_PAYTYPE)) {// ????????????
            payment.setPayMethod(Constant.SITE_P_TYPE_????????????);
            payment.setCertNo(xpay.Response("LGD_CASHRECEIPTNUM", 0)); // ??????????????? ????????????
            payment.setCashReceiptKind(xpay.Response("LGD_CASHRECEIPTKIND", 0));
        }

        if (StringUtils.isBlank(payment.getCertNo())) {
            payment.setCertNo(edcRsvnNo); // ????????? ???????????? ???????????? ??????????????? ??????
        }

        payment.setCertDtime(xpay.Response("LGD_PAYDATE", 0));
        if (StringUtils.isBlank(payment.getCertDtime())) {
            payment.setCertDtime(xpay.Response("LGD_TIMESTAMP", 0));
        }

        payment.setFinanceCd(tossReturnVO.getLGD_FINANCECODE());
        payment.setFinanceNm(tossReturnVO.getLGD_FINANCENAME());
        payment.setPayAmt(Integer.parseInt(xpay.Response("LGD_AMOUNT", 0)));
        payment.setPayListYn(Config.NO);

        payment.setMid(xpay.Response("LGD_MID", 0));
        payment.setOid(xpay.Response("LGD_OID", 0));
        payment.setTid(xpay.Response("LGD_TID", 0));
        payment.setIp(tossReturnVO.getLGD_BUYERIP());
        payment.setTerminalType(this.getTerminalType(request));

        payment.setResultCd(xpay.m_szResCode);
        payment.setResultMsg(xpay.m_szResMsg);
        payment.setAppGbn(Constant.SM_APP_GBN_????????????);

        saleFormVO.setMember(this.setSaleFormMemberVO(userInfo));
        saleFormVO.setItemList(itemList);
        saleFormVO.setPayment(payment);

        try {
            totalSaleService.saveTossTransactionInfo(saleFormVO);
        } catch (Exception e) {
            log.error(ExceptionUtil.getErrorLine(e));
        }
    }

    private String getTerminalType(HttpServletRequest request) {
        String edcTrmnlType = Constant.SM_TERMINAL_TYPE_?????????WEB;
        if (request.getAttribute("IS_MOBILE") != null) {
            boolean isMobile = (boolean) request.getAttribute("IS_MOBILE");
            if (isMobile)
                edcTrmnlType = Constant.SM_TERMINAL_TYPE_?????????;
        }
        return edcTrmnlType;
    }

    private SaleFormMemberVO setSaleFormMemberVO(LoginVO userInfo) {
        SaleFormMemberVO member = new SaleFormMemberVO();
        member.setMemNo(userInfo.getUniqId());
        member.setMemNm(userInfo.getName());
        member.setMemHp(userInfo.getIhidNum());
        member.setMemBirthdate(userInfo.getBirthDate());
        return member;
    }

    private LoginVO doLoginVOWithRsvnInfoMember(TossResponseVO tossResponseVO, HttpServletRequest request,
            HttpServletResponse response, EdcRsvnInfoVO rsvnInfoVO, LoginVO loginVO) throws Exception, IOException {

        if (loginVO == null || !loginVO.isMember() || StringUtils.isBlank(loginVO.getName())) {
            loginVO = new LoginVO();
            loginVO.setGender(rsvnInfoVO.getEdcRsvnGender());
            loginVO.setBirthDate(rsvnInfoVO.getEdcRsvnBirthdate());
            loginVO.setName(rsvnInfoVO.getEdcRsvnCustnm());
            loginVO.setIhidNum(rsvnInfoVO.getEdcRsvnMoblphon());
            loginVO.setUniqId(rsvnInfoVO.getEdcRsvnMemno());
            if (StringUtils.isNotBlank(rsvnInfoVO.getId())) {
                loginVO.setId(rsvnInfoVO.getId());
            }

            UserSession userSession = new UserSession();
            userSession.setUserInfo(loginVO);
            SessionUtil.setAttribute(Config.USER_SESSION, userSession);
        }

        return loginVO;
    }

}