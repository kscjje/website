package com.hisco.user.edcatnlc.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgMemberDcVO;
import com.hisco.admin.orginfo.vo.OrgOptinfoVO;
import com.hisco.admin.terms.service.TermsService;
import com.hisco.admin.terms.vo.TermsVO;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.ExceptionUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.RequestUtil;
import com.hisco.cmm.util.SessionUtil;
import com.hisco.intrfc.sale.service.SaleChargeService;
import com.hisco.intrfc.sale.service.TotalSaleService;
import com.hisco.intrfc.sale.vo.PaySummaryVO;
import com.hisco.intrfc.sale.vo.SaleFormItemVO;
import com.hisco.intrfc.sale.vo.SaleFormMemberVO;
import com.hisco.intrfc.sale.vo.SaleFormPaymentVO;
import com.hisco.intrfc.sale.vo.SaleFormVO;
import com.hisco.intrfc.sms.service.BizMsgService;
import com.hisco.user.edcatnlc.service.EdcProgramService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;
import com.hisco.user.mypage.service.MyRsvnService;
import com.hisco.user.mypage.vo.MyRsvnVO;

import egovframework.com.cmm.LoginVO;
import lombok.extern.slf4j.Slf4j;

/**
 * ?????????????????? ??????/?????? ??????
 *
 * @Class Name : EducationController.java
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
public class EdcRsvnInfoController {

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    // woojinp added
    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "termsService")
    private TermsService termsService;

    @Resource(name = "totalSaleService")
    private TotalSaleService totalSaleService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    @Resource(name = "myRsvnService")
    private MyRsvnService myRsvnService;

    @Resource(name = "bizMsgService")
    private BizMsgService bizMsgService;

    @Resource(name = "saleChargeService")
    private SaleChargeService saleChargeService;
    
    @GetMapping(value = { "/termsAgree/{edcPrgmid}/{edcRsvnsetSeq}", "/termsAgreeAjax/{edcPrgmNo}/{edcRsvnsetSeq}" })
    public String termsAgree(@PathVariable("edcPrgmNo") int edcPrgmid, @PathVariable("edcRsvnsetSeq") int edcRsvnsetSeq,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model, Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        EdcProgramVO param = new EdcProgramVO();
        param.setEdcPrgmNo(edcPrgmid);
        param.setEdcRsvnsetSeq(edcRsvnsetSeq);
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(param);

        if (detailVO == null)
            HttpUtility.sendRedirect(request, response, "???????????? ?????? ???????????????.", Config.USER_ROOT + "/edc/program/list");

        model.addAttribute("detailVO", detailVO);

        TermsVO termsParam = new TermsVO();
        // termsParam.setOrgNo(detailVO.getOrgNo());
        termsParam.setStplatIdList(Arrays.asList(Constant.CM_TERMS_ID_??????????????????????????????, Constant.CM_TERMS_ID_??????????????????????????????));
        model.addAttribute("termsList", termsService.selectTermsList(termsParam));

        // TODO:Constant.CM_TERMS_ID_????????????????????????????????? ????????? ????????? ???????????? ???????????? ???. ??????????????? ???????????? ???????????????
        // ????????? edcProgramService.selectProgramDetail?????? ???????????? ????????? ?????? ????????? ?????????. 2012.11.18

        LoginVO loginVO = commandMap.getUserInfo();
        if (loginVO == null) {
            loginVO = new LoginVO();
        }
        
        if(detailVO.getEdcRsvnRectype().equals("2001")) {
        	// ???????????? 3??????(?????????????????????+?????????????????????+???????????????) ??????????????????
            EdcRsvnInfoVO limitVO = new EdcRsvnInfoVO();
            limitVO.setEdcRsvnCustnm(loginVO.getName());
            limitVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
            limitVO.setEdcReqSdate(detailVO.getEdcSdate());
            limitVO.setEdcPrgmNo(detailVO.getEdcPrgmNo());
            limitVO.setEdcRsvnNo("");
            List<EdcRsvnInfoVO> limitList = edcRsvnInfoService.selectNowon3BasicOrgRegiList(limitVO);

            if (limitList == null || limitList.isEmpty()) {
                model.addAttribute("dcLimitYn", Config.NO);
            } else {
                model.addAttribute("dcLimitYn", Config.YES);
            }
            
            if (Config.NO.equals(detailVO.getExclDcyn())) { // ????????????????????? ????????????
                String memNo = null;
                if (commandMap.getUserInfo() != null) {
                    memNo = commandMap.getUserInfo().getUniqId();
                }

                OrgMemberDcVO orgMemberDcVO = new OrgMemberDcVO();
                orgMemberDcVO.setComcd(detailVO.getComcd());
                orgMemberDcVO.setOrgNo(Integer.parseInt(detailVO.getOrgNo()));
                orgMemberDcVO.setMemNo(memNo);
                model.addAttribute("dcList", orgInfoService.selectOrgMemberDcList(orgMemberDcVO));
            }
        }
        

        model.addAttribute("userInfo", commandMap.getUserInfo());

        String requestUri = request.getRequestURI();

        if (requestUri.indexOf("termsAgreeAjax") >= 0) {
            // ????????? ?????? ???????????? ???????????? layerpop ?????? ?????????
            // ?????? / ?????? ??? ?????? ?????? ?????? ??????
            String checkStr = edcRsvnInfoService.checkApply(detailVO, loginVO);

            if (!checkStr.equals("OK")) {
                // ??????????????? ?????? ?????? ?????????
                model.addAttribute("errorMsg", "ERR|" + checkStr);
            }

            return Config.USER_ROOT + "/edc/rsvn/rsvnTermsAgreeAjax";
        } else {
            return Config.USER_ROOT + "/edc/rsvn/rsvnTermsAgree";
        }

    }
    
    @GetMapping(value = { "/termsDcAjax/{edcPrgmid}/{edcRsvnsetSeq}" })
    public String termsDc(EdcRsvnInfoVO rsvnInfoParam, @PathVariable("edcPrgmid") int edcPrgmid, @PathVariable("edcRsvnsetSeq") int edcRsvnsetSeq,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model, Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        EdcProgramVO param = new EdcProgramVO();
        param.setEdcPrgmNo(edcPrgmid);
        param.setEdcRsvnsetSeq(edcRsvnsetSeq);
        EdcProgramVO detailVO = edcProgramService.selectProgramDetail(param);

        if (detailVO == null)
            HttpUtility.sendRedirect(request, response, "???????????? ?????? ???????????????.", Config.USER_ROOT + "/edc/program/list");

        System.out.println(commandMap.getParam().get("dccd"));
        model.addAttribute("dccd", commandMap.getParam().get("dccd"));
        
       
        LoginVO loginVO = commandMap.getUserInfo();
        // ?????????????????? ????????????
        if (loginVO == null || !loginVO.isMember() || StringUtils.isBlank(loginVO.getName())) {
            loginVO = new LoginVO();
            loginVO.setGender(rsvnInfoParam.getEdcRsvnGender());
            loginVO.setBirthDate(rsvnInfoParam.getEdcRsvnBirthdate());
            loginVO.setName(rsvnInfoParam.getEdcRsvnCustnm());
            loginVO.setIhidNum(rsvnInfoParam.getEdcRsvnMoblphon());

            UserSession userSession = new UserSession();
            userSession.setUserInfo(loginVO);
            SessionUtil.setAttribute(Config.USER_SESSION, userSession);
        }
        
        
        if(detailVO.getEdcRsvnRectype().equals("2001")) {
        	// ???????????? 3??????(?????????????????????+?????????????????????+???????????????) ??????????????????
            EdcRsvnInfoVO limitVO = new EdcRsvnInfoVO();
            limitVO.setEdcRsvnCustnm(loginVO.getName());
            limitVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
            limitVO.setEdcReqSdate(detailVO.getEdcSdate());
            limitVO.setEdcPrgmNo(detailVO.getEdcPrgmNo());
            limitVO.setEdcRsvnNo("");
            List<EdcRsvnInfoVO> limitList = edcRsvnInfoService.selectNowon3BasicOrgRegiList(limitVO);

            if (limitList == null || limitList.isEmpty()) {
                model.addAttribute("dcLimitYn", Config.NO);
            } else {
                model.addAttribute("dcLimitYn", Config.YES);
            }
            
            if (Config.NO.equals(detailVO.getExclDcyn())) { // ????????????????????? ????????????
                String memNo = null;
                if (commandMap.getUserInfo() != null) {
                    memNo = commandMap.getUserInfo().getUniqId();
                }

                OrgMemberDcVO orgMemberDcVO = new OrgMemberDcVO();
                orgMemberDcVO.setComcd(detailVO.getComcd());
                orgMemberDcVO.setOrgNo(Integer.parseInt(detailVO.getOrgNo()));
                orgMemberDcVO.setMemNo(memNo);
                model.addAttribute("dcList", orgInfoService.selectOrgMemberDcList(orgMemberDcVO));
            }
            
        }
        

        model.addAttribute("userInfo", commandMap.getUserInfo());

        String requestUri = request.getRequestURI();

       
        // ????????? ?????? ???????????? ???????????? layerpop ?????? ?????????
        // ?????? / ?????? ??? ?????? ?????? ?????? ??????
        String checkStr = edcRsvnInfoService.checkApply(detailVO, loginVO);

        if (!checkStr.equals("OK")) {
            // ??????????????? ?????? ?????? ?????????
            model.addAttribute("errorMsg", "ERR|" + checkStr);
        }

        return Config.USER_ROOT + "/edc/rsvn/rsvnTermsDcAjax";
        

    }

    /**
     * ????????????: ?????? ??? ????????????
     * ????????????: ????????????
     */
    @PostMapping(value = "/apply.json")
    public String applyJson(EdcRsvnInfoVO rsvnInfoParam,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model, Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        ResultInfo result = new ResultInfo(Config.SUCCESS, "??????????????? ????????? ?????????????????????");

        LoginVO loginVO = commandMap.getUserInfo();

        int saveCnt = 0;
        String payYn = "N";// ?????? ???????????? ?????? ??????

        try {
            EdcProgramVO param = new EdcProgramVO();
            param.setEdcPrgmNo(rsvnInfoParam.getEdcPrgmNo());
            param.setEdcRsvnsetSeq(rsvnInfoParam.getEdcRsvnsetSeq());
            EdcProgramVO programDetailVO = edcProgramService.selectProgramDetail(param);

            // ?????? ?????? ?????? ????????????
            OrgOptinfoVO orgOptinfoVO = new OrgOptinfoVO();
            orgOptinfoVO.setOrgNo(Integer.parseInt(programDetailVO.getOrgNo()));
            orgOptinfoVO = orgInfoService.selectOrgOptinfo(orgOptinfoVO);

            // 1??? ?????? ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            // 2???????????? trigger??? ??????
            if (!RequestUtil.getReferer(request).contains(Config.USER_ROOT + "/edc/rsvn/termsAgree/" + rsvnInfoParam.getEdcPrgmNo()) && !RequestUtil.getReferer(request).contains(Config.USER_ROOT + "/edc/program")) {
                // HttpUtility.sendRedirect(request, response, "????????? ???????????????.", Config.USER_ROOT + "/edc/program/list");
                throw new RuntimeException("????????? ???????????????.");
            }

            // ?????????????????? ????????????
            if (loginVO == null || !loginVO.isMember() || StringUtils.isBlank(loginVO.getName())) {
                loginVO = new LoginVO();
                loginVO.setGender(rsvnInfoParam.getEdcRsvnGender());
                loginVO.setBirthDate(rsvnInfoParam.getEdcRsvnBirthdate());
                loginVO.setName(rsvnInfoParam.getEdcRsvnCustnm());
                loginVO.setIhidNum(rsvnInfoParam.getEdcRsvnMoblphon());

                UserSession userSession = new UserSession();
                userSession.setUserInfo(loginVO);
                SessionUtil.setAttribute(Config.USER_SESSION, userSession);
            }

            // ?????? / ?????? ??? ?????? ?????? ?????? ??????
            String checkStr = edcRsvnInfoService.checkApply(programDetailVO, loginVO);

            if (!checkStr.equals("OK")) {
                throw new RuntimeException(checkStr);
            }
            // ---------------??????/?????? ???????????? ?????? ???

            // ???????????????????????? ??? ???????????????????????? (TRIGGER?????? ??????)
            /*
             * if (!Constant.EDC_PROGRAM_STATUS_??????.equals(programDetailVO.getEdcStatus())) {// ????????????
             * if (Constant.SM_LEREC_TYPE_????????????.equals(programDetailVO.getEdcRsvnRectype())) {
             * int edcEndWaitCapa = programDetailVO.getEdcEndwaitCapa();// ??????????????????
             * int statAssignWaitCnt = programDetailVO.getStatAssignWaitCnt(); // ??????????????????
             * if (statAssignWaitCnt >= edcEndWaitCapa) {
             * throw new RuntimeException("??????????????? ?????????????????????.");
             * }
             * } else {
             * throw new RuntimeException("?????? ?????? ????????? ????????????.");
             * }
             * }
             */

            // ?????? ??? ?????? ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            if (!loginVO.isMember()) {// ????????????????????? ??????????????? ??????
                rsvnInfoParam.setEdcRsvnMemtype(Constant.EDC_RSVN_MEMTYPE_?????????);
            } else {
                // ID????????? ??????????????? memno??? ???????????? ??????
                rsvnInfoParam.setEdcRsvnMemtype(Constant.EDC_RSVN_MEMTYPE_??????);
                rsvnInfoParam.setEdcRsvnMemno(loginVO.getUniqId());
                rsvnInfoParam.setEdcMemNo(loginVO.getUniqId());
            }

            /*
             * TODO:??????
             * if (RequestUtil.isMobile(request)) { // default PC
             * rsvnInfoParam.setEdcTrmnlType(Constant.EDC_TRMNL_TYPE_MO);
             * }
             */

            /*
             * ???????????? DB???????????? ??????
             * if (StringUtils.isNotBlank(rsvnInfoParam.getEdcRsvnMoblphon())) {
             * String nonMemberHp = rsvnInfoParam.getEdcRsvnMoblphon();
             * String encryptedHp = WebEncDecUtil.fn_encrypt(nonMemberHp, SpowiseCmsKey);
             * rsvnInfoParam.setEdcRsvnMoblphon(StringUtils.isBlank(encryptedHp) ? nonMemberHp : encryptedHp);
             * }
             */

            // ??????. (????????? trigger??? ?????? > ????????????, ???????????? ?????? ??????)
            try {
                String rsvnStat = Constant.SM_RSVN_STAT_????????????; // ????????????, ??????????????? ?????????????????? "????????????"

                if (programDetailVO.getSaleAmt() == 0) { // ????????????
                    rsvnStat = Constant.SM_RSVN_STAT_????????????;
                }

                if (Constant.SM_LEREC_TYPE_???????????????.equals(programDetailVO.getEdcRsvnRectype())) {// ????????????
                    rsvnStat = Constant.SM_RSVN_STAT_????????????;

                } else if (Constant.SM_LEREC_TYPE_????????????.equals(programDetailVO.getEdcRsvnRectype())) {
                    if (programDetailVO.getStatAssignWaitCnt() > 0 || programDetailVO.getStatApplyCnt() >= programDetailVO.getEdcPncpa() || ("Y".equals(programDetailVO.getEdcCapaDvdyn()) && programDetailVO.getStatApplyOnCnt() >= programDetailVO.getEdcOncapa())) { // ????????????
                        rsvnStat = Constant.SM_RSVN_STAT_????????????;
                    }
                }

                if (rsvnStat.equals(Constant.SM_RSVN_STAT_????????????)) {
                    payYn = "Y"; // ???????????? ??????????????? ?????????????????? ????????????
                }

                String edcTrmnlType = Constant.SM_TERMINAL_TYPE_?????????WEB;
                if (request.getAttribute("IS_MOBILE") != null) {
                    boolean isMobile = (boolean) request.getAttribute("IS_MOBILE");
                    if (isMobile)
                        edcTrmnlType = Constant.SM_TERMINAL_TYPE_?????????;
                }

                if (rsvnStat.equals(Constant.SM_RSVN_STAT_????????????)) {// ??????????????? ???????????????(6???) ????????? insert
                    SaleFormVO saleFormVO = new SaleFormVO();
                    saleFormVO.setRsvnStat(rsvnStat);

                    SaleFormMemberVO member = new SaleFormMemberVO();
                    member.setMemNo(rsvnInfoParam.getEdcMemNo());
                    member.setMemNm(rsvnInfoParam.getEdcRsvnCustnm());
                    member.setMemHp(rsvnInfoParam.getEdcRsvnMoblphon());
                    member.setMemBirthdate(rsvnInfoParam.getEdcRsvnBirthdate());

                    SaleFormItemVO item = new SaleFormItemVO();
                    item.setComcd(rsvnInfoParam.getComcd());
                    item.setOrgNo(Integer.parseInt(programDetailVO.getOrgNo()));
                    item.setItemCd(programDetailVO.getItemCd());
                    item.setCostAmt(programDetailVO.getCostAmt());
                    item.setSalamt(programDetailVO.getSalamt());
                    item.setSaleAmt(programDetailVO.getSaleAmt());
                    item.setMonthCnt(programDetailVO.getMonthCnt());
                    item.setEdcPrgmNo(programDetailVO.getEdcPrgmNo());
                    item.setEdcSdate(programDetailVO.getEdcSdate());
                    item.setEdcEdate(programDetailVO.getEdcEdate());
                    item.setEdcRsvnsetSeq(programDetailVO.getEdcRsvnsetSeq());
                    item.setVatYn(Config.NO);

                    SaleFormPaymentVO payment = new SaleFormPaymentVO();
                    payment.setOnoff(Constant.EDC_ONOFFIN_TYPE_ON);
                    payment.setRergistGbn(Constant.SM_REGIST_GBN_????????????);
                    payment.setPayAmt(0);
                    payment.setDcAmt(0);
                    payment.setPayMethod("CASH"); // ??????????????? ???????????? ??????
                    payment.setPayComcd(Constant.PG_SELF);
                    payment.setFinanceCd("CH");

                    payment.setTerminalType(edcTrmnlType);

                    saleFormVO.setMember(member);
                    saleFormVO.setItemList(Arrays.asList(item));
                    saleFormVO.setPayment(payment);

                    saveCnt = totalSaleService.register(saleFormVO);
                    rsvnInfoParam.setEdcRsvnNo(saleFormVO.getPayment().getRsvnNo());
                } else {
                    rsvnInfoParam.setEdcMonthcnt(programDetailVO.getMonthCnt());
                    rsvnInfoParam.setEdcOnoffintype(Constant.EDC_ONOFFIN_TYPE_ON);
                    rsvnInfoParam.setEdcTrmnlType(edcTrmnlType);
                    rsvnInfoParam.setEdcStat(rsvnStat);
                    saveCnt = edcRsvnInfoService.saveRsvnInfo(rsvnInfoParam);

                    bizMsgService.sendRsvnMessage(rsvnInfoParam.getEdcRsvnNo());
                }

                // ???????????? ?????? ????????? ?????? ??????
                if (payYn.equals("Y") && "0".equals(orgOptinfoVO.getWebPaymentMethod())) {
                    payYn = "N";
                }
            } catch (TransientDataAccessResourceException ex) {
                throw new RuntimeException(ExceptionUtil.getSQLErrorMessage(ex, "RSVN"));
            } catch (RuntimeException ex) {
                throw ex;
            }
        } catch (Exception ex) {
            rsvnInfoParam.setEdcRsvnNo("");
            result.setCode(Config.FAIL);
            result.setMsg(ex.getMessage());
            log.error(ExceptionUtil.getErrorLine(ex));
        }

        model.clear();

        if (result.getMsg() != null && result.getMsg().contains("|")) {// "???????????????????????????????????????.|???????????????|????????????"
            String[] arr = StringUtils.split(result.getMsg(), "|");
            result.setMsg(arr[0]);
            rsvnInfoParam.setEdcRsvnNo(arr[1]);
            rsvnInfoParam.setEdcStat(arr[2]);
        }

        model.addAttribute("result", result);
        model.addAttribute("PAY_YN", payYn);
        model.addAttribute("EDC_RSVN_NO", rsvnInfoParam.getEdcRsvnNo());
        model.addAttribute("EDC_PRGMID", rsvnInfoParam.getEdcPrgmNo());
        model.addAttribute("EDC_STAT", rsvnInfoParam.getEdcStat());

        // ????????? ??????????????? ?????????????????? ????????? ????????? ????????? ?????????????????? ???????????????
        if ((saveCnt > 0 || !StringUtil.IsEmpty(rsvnInfoParam.getEdcRsvnNo())) && !loginVO.isMember()) {
            HttpSession session = request.getSession();
            UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
            userSession.setUserInfo(loginVO);
            session.setAttribute(Config.USER_SESSION, userSession);
        }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ??????(edc)??????(rsvn)??????(complete) : ??????????????????
     * ???????????????: ????????????(~/termsAgree/edcPrgmid)
     */
    @GetMapping(value = "/complete/{edcRsvnNo}")
    public String complete(@PathVariable("edcRsvnNo") String edcRsvnNo,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model, Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        MyRsvnVO myRsvnVO = new MyRsvnVO();
        myRsvnVO.setEdcRsvnNo(edcRsvnNo);
        EdcRsvnInfoVO rsvnInfo = myRsvnService.selectMyEdcRsvnDtl(myRsvnVO);

        if (rsvnInfo == null)
            HttpUtility.sendRedirect(request, response, "???????????? ?????? ??????????????????.", Config.USER_ROOT + "/edc/program/list");

        // ?????? ?????? ?????? ????????????
        OrgOptinfoVO orgOptinfoVO = new OrgOptinfoVO();
        orgOptinfoVO.setOrgNo(rsvnInfo.getOrgNo());
        orgOptinfoVO = orgInfoService.selectOrgOptinfo(orgOptinfoVO);

        PaySummaryVO paySummary = new PaySummaryVO();
        paySummary.setComcd(rsvnInfo.getComcd());
        paySummary.setEdcRsvnReqid(rsvnInfo.getEdcRsvnReqid());
        paySummary = saleChargeService.selectPaySummary(paySummary);

        model.addAttribute("rsvnInfo", rsvnInfo);
        model.addAttribute("orgOptinfoVO", orgOptinfoVO);
        model.addAttribute("paySummary", paySummary);

        return Config.USER_ROOT + "/edc/rsvn/rsvnComplete";
    }

}