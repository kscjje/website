package com.hisco.user.mypage.web;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgOptinfoVO;
import com.hisco.cmm.exception.MyException;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.ResponseUtil;
import com.hisco.intrfc.charge.service.ChargeService;
import com.hisco.intrfc.charge.vo.OrderIdVO;
import com.hisco.intrfc.sale.service.SaleChargeService;
import com.hisco.intrfc.sale.service.TotalCancelService;
import com.hisco.intrfc.sale.vo.PaySummaryVO;
import com.hisco.intrfc.sms.service.BizMsgService;
import com.hisco.intrfc.sms.service.SmsService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;
import com.hisco.user.edcatnlc.vo.EdcRsvnMstVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcrsvnService;
import com.hisco.user.evtrsvn.service.EvtItemAmountVO;
import com.hisco.user.evtrsvn.service.EvtStdmngVO;
import com.hisco.user.evtrsvn.service.EvtrsvnMstVO;
import com.hisco.user.evtrsvn.service.EvtrsvnService;
import com.hisco.user.exbtrsvn.service.DspyDsService;
import com.hisco.user.exbtrsvn.service.ExbtChargeVO;
import com.hisco.user.exbtrsvn.service.RsvnMasterVO;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.service.MyInforService;
import com.hisco.user.mypage.service.MyRsvnService;
import com.hisco.user.mypage.service.RsvnCommService;
import com.hisco.user.mypage.vo.MyRsvnVO;
import com.hisco.user.mypage.vo.RsvnCommVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
/* import lgdacom.XPayClient.XPayClient; 2021.06.06 JYS */
import lombok.extern.slf4j.Slf4j;

/**
 * ??????????????? ???????????? ????????????
 *
 * @author ?????????
 * @since 2020.09.14
 * @version 1.0, 2020.09.14
 *          ------------------------------------------------------------------------
 *          ????????? ?????? ??????
 *          ------------------------------------------------------------------------
 *          ????????? 2020.08.19 ????????????
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/mypage/myRsvn")
public class MyRsvnController {

    @Resource(name = "myRsvnService")
    private MyRsvnService myRsvnService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "chargeService")
    private ChargeService chargeService;

    @Resource(name = "dspyDsService")
    private DspyDsService dspyDsService;

    @Resource(name = "rsvnCommService")
    private RsvnCommService rsvnCommService;

    @Resource(name = "evtrsvnService")
    private EvtrsvnService evtrsvnService;

    @Resource(name = "evtEdcrsvnService")
    private EvtEdcrsvnService evtEdcrsvnService;

    @Resource(name = "smsService")
    private SmsService smsService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnService;

    @Resource(name = "myInforService")
    private MyInforService myInforService;

    @Resource(name = "totalCancelService")
    private TotalCancelService totalCancelService;

    @Resource(name = "saleChargeService")
    private SaleChargeService saleChargeService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    @Resource(name = "bizMsgService")
    private BizMsgService bizMsgService;

    /**
     * ?????? ??????
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/myRsvnList")
    public String selecRsvnList(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        LoginVO loginVO = commandMap.getUserInfo();

        myRsvnVO.setEdcRsvnMemno(loginVO.getUniqId());
        myRsvnVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
        myRsvnVO.setMemNm(loginVO.getName());
        myRsvnVO.setEdcRsvnBirthdate(loginVO.getBirthDate());
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        myRsvnVO.setPaginationInfo(paginationInfo); // ????????? ???????????? ??????
        List<EdcRsvnInfoVO> rsvnList = myRsvnService.selectMyEdcPagingList(myRsvnVO);

        int totCount = 0;
        if (rsvnList != null && rsvnList.size() >= 1) {
            totCount = ((EdcRsvnInfoVO) rsvnList.get(0)).getTotCount();
        }

        paginationInfo.setTotalRecordCount(totCount);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("list", rsvnList);
        model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ????????????
     * @param myRsvnVO
     * @param commandMap
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/myRefundList")
    public String selectMyRefundList(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        LoginVO loginVO = commandMap.getUserInfo();

        /*????????????*/
        myRsvnVO.setAppStatusNew("CANCEL");
        
        myRsvnVO.setEdcRsvnMemno(loginVO.getUniqId());
        myRsvnVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
        myRsvnVO.setMemNm(loginVO.getName());
        myRsvnVO.setEdcRsvnBirthdate(loginVO.getBirthDate());
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        myRsvnVO.setPaginationInfo(paginationInfo); // ????????? ???????????? ??????
        List<EdcRsvnInfoVO> rsvnList = myRsvnService.selectMyEdcPagingList(myRsvnVO);

        int totCount = 0;
        if (rsvnList != null && rsvnList.size() >= 1) {
            totCount = ((EdcRsvnInfoVO) rsvnList.get(0)).getTotCount();
        }

        paginationInfo.setTotalRecordCount(totCount);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("list", rsvnList);
        model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }
    
    /**
     * ??????????????????
     * @param myRsvnVO
     * @param commandMap
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/myRsvnDetail")
    public String myRsvnDetail(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        LoginVO loginVO = commandMap.getUserInfo();
        myRsvnVO.setEdcRsvnMemno(loginVO.getUniqId());
        // ????????????
        myRsvnVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
        myRsvnVO.setMemNm(loginVO.getName());
        myRsvnVO.setEdcRsvnBirthdate(loginVO.getBirthDate());
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        EdcRsvnInfoVO rsvnInfo = myRsvnService.selectMyEdcRsvnDtl(myRsvnVO);
        if (rsvnInfo == null)
            ResponseUtil.SendMessage(request, response, "??????????????? ???????????? ????????????.", "history.back()");

        // ?????? ?????? ?????? ????????????
        OrgOptinfoVO orgOptinfoVO = new OrgOptinfoVO();
        orgOptinfoVO.setOrgNo(rsvnInfo.getOrgNo());
        orgOptinfoVO = orgInfoService.selectOrgOptinfo(orgOptinfoVO);

        PaySummaryVO paySummary = new PaySummaryVO();
        paySummary.setComcd(rsvnInfo.getComcd());
        paySummary.setEdcRsvnReqid(rsvnInfo.getEdcRsvnReqid());
        paySummary = saleChargeService.selectPaySummary(paySummary);

        model.addAttribute("rsvnInfo", rsvnInfo);
        model.addAttribute("paySummary", paySummary);
        model.addAttribute("orgOptinfoVO", orgOptinfoVO);
        model.addAttribute("queryString", commandMap.getSearchQuery("edcRsvnReqid"));
        model.addAttribute("mypage", "Y");

        return Config.USER_ROOT + "/edc/rsvn/rsvnComplete";
    }

    /**
     * ??????????????????
     * @param myRsvnVO
     * @param commandMap
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/myCancelDetail")
    public String myCancelDetail(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        LoginVO loginVO = commandMap.getUserInfo();
        myRsvnVO.setEdcRsvnMemno(loginVO.getUniqId());
        // ????????????
        myRsvnVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
        myRsvnVO.setMemNm(loginVO.getName());
        myRsvnVO.setEdcRsvnBirthdate(loginVO.getBirthDate());
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        EdcRsvnInfoVO rsvnInfo = myRsvnService.selectMyEdcRsvnDtl(myRsvnVO);
        if (rsvnInfo == null)
            ResponseUtil.SendMessage(request, response, "??????????????? ???????????? ????????????.", "history.back()");

        // ?????? ?????? ?????? ????????????
        OrgOptinfoVO orgOptinfoVO = new OrgOptinfoVO();
        orgOptinfoVO.setOrgNo(rsvnInfo.getOrgNo());
        orgOptinfoVO = orgInfoService.selectOrgOptinfo(orgOptinfoVO);

        PaySummaryVO paySummary = new PaySummaryVO();
        paySummary.setComcd(rsvnInfo.getComcd());
        paySummary.setEdcRsvnReqid(rsvnInfo.getEdcRsvnReqid());
        paySummary = saleChargeService.selectPaySummary(paySummary);

        model.addAttribute("rsvnInfo", rsvnInfo);
        model.addAttribute("paySummary", paySummary);
        model.addAttribute("orgOptinfoVO", orgOptinfoVO);
        model.addAttribute("queryString", commandMap.getSearchQuery("edcRsvnReqid"));
        model.addAttribute("mypage", "Y");

        return Config.USER_ROOT + "/edc/rsvn/rsvnCancel";
    }
    
    /**
     * ?????? ??????
     *
     * @param
     * @return result - ??????
     * @exception Exception
     */
    @PostMapping(value = "/rsvnCancel.json")
    public String rsvnCancelJson(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        ResultInfo resultInfo = new ResultInfo(Config.SUCCESS, "?????? ????????? ?????????????????????.");
        resultInfo.setSuccess(true);

        LoginVO loginVO = commandMap.getUserInfo();

        try {
            if (loginVO == null) {
                throw new RuntimeException("????????? ????????? ????????????.");
            }

            if (myRsvnVO.getEdcRsvnReqid() < 1 || StringUtils.isBlank(myRsvnVO.getEdcRsvnNo())) {
                throw new RuntimeException("????????? ?????? ???????????? ?????? ???????????? ????????????.");
            }

            myRsvnVO.setEdcRsvnMemno(loginVO.getUniqId());
            // ????????????
            myRsvnVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
            myRsvnVO.setMemNm(loginVO.getName());
            myRsvnVO.setEdcRsvnBirthdate(loginVO.getBirthDate());

            EdcRsvnInfoVO rsvnInfo = myRsvnService.selectMyEdcRsvnDtl(myRsvnVO);

            if (rsvnInfo == null)
                throw new RuntimeException("???????????? ????????? ???????????? ????????????.");

            if (Config.NO.equals(rsvnInfo.getEditYn()) && Constant.SM_RSVN_STAT_????????????.equals(rsvnInfo.getEdcStat())) {
                throw new RuntimeException("????????? ??? ?????? ????????? ????????????.");
            }

            /*
             * PG????????? ????????? ??????????????? ???????????? ??????(commented by ???????????????)
             * if (Config.YES.equals(rsvnInfo.getEditYn()) && Constant.SM_RSVN_STAT_????????????.equals(rsvnInfo.getEdcStat())
             * && rsvnInfo.getVbankSeq() > 0) { // ????????????
             * // ????????????&?????????????????????
             * throw new RuntimeException("(????????????) ????????? ??? ?????? ????????? ????????????.");
             * }
             */

            if (Constant.SM_RSVN_STAT_????????????.equals(rsvnInfo.getEdcStat())) {
                throw new RuntimeException("?????? ????????? ??? ?????????.");
            }

            if (Constant.SM_RSVN_STAT_????????????.equals(rsvnInfo.getEdcStat())) {
                throw new RuntimeException("?????? ????????? ??? ?????????.");
            }

            int insertOrUpdateCnt = 0;
            List<EdcRsvnInfoVO> rsvnList = myRsvnService.selectMyEdcPagingList(myRsvnVO);

            if (Constant.ING_RSVN_STAT_LIST.contains(rsvnInfo.getEdcStat())) { // 1000, 1002, 2001
                insertOrUpdateCnt = myRsvnService.updateReserveCancel(rsvnList);
            } else {
                insertOrUpdateCnt = totalCancelService.cancel(rsvnList);
                if (insertOrUpdateCnt > 0) {
                    for (EdcRsvnInfoVO rsvnVO : rsvnList) {
                        bizMsgService.sendMessageToAdmin(rsvnVO);
                    }
                }
            }

            if (insertOrUpdateCnt < 1) {
                resultInfo.setMsg("??????????????? ?????? ???????????? ????????????.");
            }
        } catch (Exception ex) {
            resultInfo.setSuccess(false);
            resultInfo.setMsg(ex.getMessage());
        }

        model.clear();
        model.addAttribute("result", resultInfo);
        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ??????
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnPay")
    public String selecRsvnPay(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        String gubun = myRsvnVO.getGubun();
        if (gubun == null)
            gubun = "";

        String cstPLATFORM = EgovProperties.getProperty("tosspayments.platform");

        myRsvnVO.setCstPLATFORM(cstPLATFORM);

        boolean isMobile = false;
        if (request.getAttribute("IS_MOBILE") != null) {
            isMobile = (boolean) request.getAttribute("IS_MOBILE");
        }

        String returnURL = EgovProperties.getProperty("Globals.Domain") + Config.INTRFC_ROOT + (isMobile
                ? "/charge/return/reCallNsmMbUrl" : "/charge/return/reCallNsmUrl");

        myRsvnVO.setLgdRETURNURL(returnURL);
        myRsvnVO.setLgdCASNOTEURL(EgovProperties.getProperty("Globals.Domain") + Config.INTRFC_ROOT + "/charge/return/casNoteUrl");

        model.addAttribute("CstWINDOW_TYPE", (isMobile ? "submit" : "iframe"));
        model.addAttribute("LgdCUSTOM_SWITCHINGTYPE", (isMobile ? "SUBMIT" : "IFRAME"));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ??????
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnPayAjax")
    public String selecRsvnPayList(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        String gubun = myRsvnVO.getGubun();
        if (gubun == null)
            gubun = "";

        LoginVO loginVO = commandMap.getUserInfo();
        loginVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        myRsvnVO.setComcd(Config.COM_CD);
        myRsvnVO.setUniqId(loginVO.getUniqId());
        myRsvnVO.setHpcertno(loginVO.getHpcertno());
        myRsvnVO.setMode("pay");
        myRsvnVO.setReguser(loginVO.getId() == null ? "NONMEMBER" : loginVO.getId()); // ?????????????
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        String partCd = ""; // ????????? ??????
        long payAmount = 0; // ??? ?????? ??????
        long totalAmount = 0; // ?????? ?????? ??????
        long cnt = 0;
        String productInfo = "";// ?????????
        String rsvnNo = ""; // oid ????????? ?????? ???????????? , ??? ??????

        request.getSession().setAttribute("LgdPRODUCTCODE", gubun);

        List<EdcRsvnInfoVO> list = myRsvnService.selectEdcPaylist(myRsvnVO, "");

        if (list != null && list.size() > 0) {
            for (EdcRsvnInfoVO vo : list) {
                cnt++;

                productInfo = vo.getEdcPrgmnm();

                totalAmount += vo.getEdcTotamt(); // ???????????? ?????????
                payAmount += (vo.getEdcTotamt() + vo.getEdcDcamt()); // ???????????? ?????????

                if (!rsvnNo.equals(""))
                    rsvnNo += ",";
                rsvnNo += vo.getEdcRsvnNo();

                myRsvnVO.setLgdBUYER(vo.getEdcReqCustnm());
                myRsvnVO.setLgdBUYERID(vo.getId());
                myRsvnVO.setLgdBUYEREMAIL(vo.getEdcEmail());
            }

            if (list.size() > 1) {
                productInfo += (" ??? " + (cnt - 1) + "???");
            }
        }

        model.addAttribute("list", list);

        model.addAttribute("payAmount", payAmount);

        myRsvnVO.setPartCd(partCd);
        // ?????? ????????? ??????
        MyRsvnVO systemInfo = myRsvnService.selectPartSystem(myRsvnVO);
        if (systemInfo != null) {
            myRsvnVO.setPaymentId(systemInfo.getPaymentId());
            myRsvnVO.setPaymentPw(systemInfo.getPaymentPw());
        }

        String cstPLATFORM = myRsvnVO.getCstPLATFORM();
        // ????????? ????????????
        if (loginVO.isMember()) {
            myRsvnVO.setLgdBUYER(loginVO.getName());
            myRsvnVO.setLgdBUYERID(loginVO.getId());
            myRsvnVO.setLgdBUYEREMAIL(loginVO.getEmail());
        }

        myRsvnVO.setLgdAMOUNT(Long.toString(totalAmount));
        myRsvnVO.setLgdPRODUCTINFO(productInfo);
        myRsvnVO.setLgdPRODUCTCODE(gubun);
        myRsvnVO.setCstMID(myRsvnVO.getPaymentId());
        myRsvnVO.setLgdMID(("test".equals(cstPLATFORM.trim()) ? "t" : "") + myRsvnVO.getPaymentId());

        String lgdMERTKEY = EgovProperties.getProperty("tosspayments.mertkey." + myRsvnVO.getPaymentId()); // ?????????
        String lgdTIMESTAMP = DateUtil.printDatetime(null, "yyyyMMddHHmmss");

        /* ???????????? ?????? ?????? */
        String lgdOID = "";
        if (!rsvnNo.equals("") && rsvnNo.length() > 0) {
            OrderIdVO orderVO = new OrderIdVO();
            orderVO.setComcd(myRsvnVO.getComcd());
            orderVO.setUserId(myRsvnVO.getReguser());
            orderVO.setRsvnNo(rsvnNo); // ????????????
            orderVO.setRsvnCnt(cnt);
            orderVO.setRsvnAmt(Long.parseLong(myRsvnVO.getLgdAMOUNT()));

            chargeService.insertDbprocLog(orderVO);
            //// JYS 2021.05.18 chargeService.selectOrderId(orderVO);

            // OID ?????????
            lgdOID = orderVO.getRetOid(); // ?????? ?????? ?????????????????? ??????
            /* ???????????? ?????? ??? */

            myRsvnVO.setLgdOID(lgdOID);
            myRsvnVO.setLgdTIMESTAMP(lgdTIMESTAMP);

            try {
                StringBuffer sb = new StringBuffer();
                sb.append(myRsvnVO.getLgdMID());
                sb.append(myRsvnVO.getLgdOID());
                sb.append(myRsvnVO.getLgdAMOUNT());
                sb.append(lgdTIMESTAMP);
                sb.append(lgdMERTKEY);

                byte[] bNoti = sb.toString().getBytes();
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] digest = md.digest(bNoti);

                StringBuffer strBuf = new StringBuffer();
                for (int i = 0; i < digest.length; i++) {
                    int c = digest[i] & 0xff;
                    if (c <= 15) {
                        strBuf.append("0");
                    }
                    strBuf.append(Integer.toHexString(c));
                }

                String lgdHASHDATA = strBuf.toString();
                myRsvnVO.setLgdHASHDATA(lgdHASHDATA);

            } catch (Exception e) {
                myRsvnVO.setLgdTIMESTAMP("");
                myRsvnVO.setLgdHASHDATA("");
            }
        }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ?????? ajax
     *
     * @param commandMap
     * @return String - jsp ?????????
     * @exception Exception
     */
    @GetMapping(value = "/detailAjax")
    public String myRsvnDetailAjax(MyRsvnVO rsvnVO, HttpServletRequest request, ModelMap model) throws Exception {

        EdcRsvnInfoVO resultVO = myRsvnService.selectMyEdcRsvnDtl(rsvnVO);
        // String strEncEdcRsvnfmTelno = com.hisco.cmm.util.WebEncDecUtil.fn_decrypt((String)
        // resultVO.get("edcRsvnfmTelnoDec"), strSpowiseCmsKey);

        model.addAttribute("resultVO", resultVO);
        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/myRsvn/edc/detailAjax");
    }

    /**
     * ?????? ?????? ?????? ?????????
     *
     * @param
     * @return callback page
     * @exception Exception
     */
    @PostMapping(value = "/myRsvnListSave")
    public String myRsvnSave(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO) throws Exception {
        myRsvnVO.setComcd(Config.COM_CD);
        myRsvnVO.setMode("save");
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        String configPath = FileMngUtil.GetRealRootPath().concat("WEB-INF/lgdacom"); // LG?????????????????? ?????????
                                                                                     // ????????????("/conf/lgdacom.conf,/conf/mall.conf")
                                                                                     // ?????? ??????.

        long totalAmount = 0; // ??????????????????

        List<?> list = null;

        if (myRsvnVO.getLgdPRODUCTCODE().equals("EXBT")) {
            list = myRsvnService.selectExbtPayList(myRsvnVO, "");
            if (list != null && list.size() > 0) {
                for (RsvnMasterVO vo : ((List<RsvnMasterVO>) list)) {
                    totalAmount += vo.getPayamt();
                }
            }
        } else if (myRsvnVO.getLgdPRODUCTCODE().equals("EVT")) {
            list = myRsvnService.selectEvtPaylist(myRsvnVO, "");
            if (list != null && list.size() > 0) {
                for (EvtrsvnMstVO vo : ((List<EvtrsvnMstVO>) list)) {
                    totalAmount += Integer.parseInt(vo.getEvtRsvnPayamt());
                }
            }
        } else if (myRsvnVO.getLgdPRODUCTCODE().equals("EDC")) {
            list = myRsvnService.selectEdcPaylist(myRsvnVO, "");
            if (list != null && list.size() > 0) {
                for (EdcRsvnMstVO vo : ((List<EdcRsvnMstVO>) list)) {
                    totalAmount += vo.getEdcTotamt(); // ???????????? ?????????
                }
            }
        }
        /*
         ************************************************* 1.???????????? ?????? - BEGIN
         * (???, ?????? ??????????????? ???????????? ?????? ???????????? ?????? ????????? ?????? ????????? ?????????.)
         */

        String cstPLATFORM = EgovProperties.getProperty("tosspayments.platform");
        String lgdMID = request.getParameter("LgdMID");
        String lgdPAYKEY = request.getParameter("LgdPAYKEY");
        String lgdRESPCODE = request.getParameter("LgdRESPCODE");
        String lgdRESPMSG = request.getParameter("LgdRESPMSG");

        StringBuffer resultMsg = new StringBuffer();
        boolean isDBOK = false;
        boolean isInitOK = false;

        if (lgdRESPCODE != null && !lgdRESPCODE.equals("0000")) {
            resultMsg.append(lgdRESPMSG);
        } else {
            isInitOK = true;
        }

        commandMap.put("APP_GBN", "1"); // ???????????? ?????? ?????? 1
        commandMap.put("P_COMCD", "DACOM"); // ???????????????

        if (totalAmount == 0) {
            try {

                log.debug("myRsvnSave 1 :: commandMap = " + commandMap);
                log.debug("myRsvnSave 1 :: commandMap.getParam() = " + commandMap.getParam());
                log.debug("myRsvnSave 1 :: myRsvnVO = " + myRsvnVO);

                isDBOK = myRsvnService.insertPaymentInfo(commandMap.getParam(), myRsvnVO);
            } catch (Exception e) {
                isDBOK = false;
            }
            if (!isDBOK) {
                resultMsg.append("?????? ?????? ?????? : ");
                resultMsg.append(myRsvnVO.getRetCd());
                resultMsg.append("<br>");
                resultMsg.append("?????? ?????? ????????? : ");
                resultMsg.append(myRsvnVO.getRetMsg());
                resultMsg.append("<br>");
            }

        } else {

            // ?????? API??? ???????????? ?????? WEB-INF/lib/XPayClient.jar ??? Classpath ??? ??????????????? ?????????.
            // (1) XpayClient??? ????????? ?????? xpay ?????? ??????

            /***
             * XPayClient xpay = new XPayClient(); 2021.06.06 JYS
             * // (2) Init: XPayClient ?????????(???????????? ?????? ??????)
             * // configPath: ????????????
             * // CstPLATFORM: - test, service ?????? ?????? lgdacom.conf??? test_url(test) ?????? url(srvice) ??????
             * // - test, service ?????? ?????? ???????????? ?????? ???????????? ????????? ??????
             * if (isInitOK) {
             * isInitOK = xpay.Init(configPath, cstPLATFORM);
             * }
             * if ( !isInitOK ) {
             * //API ????????? ?????? ????????????
             * resultMsg.append( "??????????????? ????????? ????????? ?????????????????????.<br>
             * ");
             * resultMsg.append( "LG?????????????????? ????????? ??????????????? ??????????????? ?????? ???????????? ??????????????? ????????????.<br>
             * ");
             * resultMsg.append( "mall.conf?????? Mert ID = Mert Key ??? ????????? ???????????? ????????? ?????????.<br>
             * <br>
             * ");
             * resultMsg.append( "???????????? LG???????????? 1544-7772<br>
             * ");
             * } else {
             * try {
             * // (3) Init_TX: ???????????? mall.conf, lgdacom.conf ?????? ??? ??????????????? ????????? ??? TXID ??????
             * xpay.Init_TX(lgdMID);
             * xpay.Set("LgdTXNAME", "PaymentByKey");
             * xpay.Set("LgdPAYKEY", lgdPAYKEY);
             * //????????? ??????????????? ????????? ?????? ?????? ????????? ????????? ??????????????????.
             * //String DB_AMOUNT = "DB??? ???????????? ????????? ??????"; //????????? ???????????? ???????????? ???(DB??? ??????)?????? ????????? ??????????????????.
             * xpay.Set("LgdAMOUNTCHECKYN", "Y");
             * xpay.Set("LgdAMOUNT", String.valueOf(totalAmount));
             * } catch (Exception e) {
             * // LG U+ API ?????? ??????, ???????????? ?????? ??? ??????(????????????)
             * resultMsg.append("LG???????????? ?????? API??? ????????? ??? ????????????. ???????????? ????????? ????????? ????????? ????????????. ");
             * resultMsg.append(e.getMessage());
             * isInitOK = false;
             * }
             * }
             */

            /*
             ************************************************* 1.???????????? ??????(???????????? ?????????) - END
             */

            /*
             * 2. ???????????? ?????? ????????????
             * ?????? ???????????? ?????? ?????? ??????????????? ?????????????????? ??????????????? ????????????.
             */
            // (4) TX: lgdacom.conf??? ????????? URL??? ?????? ???????????? ??????????????????, ??????????????? true, false ??????

            /*
             * 2021.06.06 JYS
             * if (isInitOK) {
             * if ( xpay.TX() ) {
             * //1)???????????? ????????????(??????,?????? ?????? ????????? ????????? ????????????.)
             * resultMsg.append( "??????????????? ?????????????????????.  <br>");
             * resultMsg.append( "TX ???????????? ?????? ???????????? = "); //?????? ????????????("0000" ??? ??? ?????? ??????)
             * resultMsg.append( xpay.m_szResCode);
             * resultMsg.append( "<br>");
             * resultMsg.append( "TX ???????????? ?????? ??????????????? = " );
             * resultMsg.append( xpay.m_szResMsg);
             * log.debug("???????????? : " + xpay.Response("LgdTID",0) + "<br>");
             * log.debug("??????????????? : " + xpay.Response("LgdMID",0) + "<br>");
             * log.debug("?????????????????? : " + xpay.Response("LgdOID",0) + "<br>");
             * log.debug("???????????? : " + xpay.Response("LgdAMOUNT",0) + "<br>");
             * log.debug("???????????? : " + xpay.Response("LgdRESPCODE",0) + "<br>"); //LgdRESPCODE ???????????? ????????????
             * log.debug("??????????????? : " + xpay.Response("LgdRESPMSG",0) + "<p>");
             * for (int i = 0; i < xpay.ResponseNameCount(); i++)
             * {
             * //out.println(xpay.ResponseName(i) + " = ");
             * for (int j = 0; j < xpay.ResponseCount(); j++)
             * {
             * //out.println("\t" + xpay.Response(xpay.ResponseName(i), j) + "<br>");
             * commandMap.put(xpay.ResponseName(i) , xpay.Response(xpay.ResponseName(i), j));
             * }
             * }
             * lgdRESPCODE = xpay.Response("LgdRESPCODE",0) ;
             * lgdRESPMSG = xpay.Response("LgdRESPMSG",0) ;
             * String lgdPAYTYPE = xpay.Response("LgdPAYTYPE", 0);
             * //?????? ???????????? ?????? ???????????? ??????
             * Map<String, Object> cancelMap = new HashMap<String,Object>();
             * cancelMap.put("COMCD", myRsvnVO.getComcd());
             * cancelMap.put("LgdOID", xpay.Response("LgdOID",0));
             * cancelMap.put("OID_STAT", "4001");
             * cancelMap.put("MODUSER",xpay.Response("LgdBUYERID",0));
             * // (5)DB??? ?????? ?????? ??????
             * if ( "0000".equals( xpay.m_szResCode ) && "0000".equals(lgdRESPCODE)) {
             * // ???????????? ????????? ?????????
             * // ?????????????????? ?????? ?????? DB??????(LgdRESPCODE ?????? ?????? ????????? ????????????, ???????????? DB??????)
             * // ?????????????????? ????????? DB???????????????. (???????????? ?????? ?????? ?????? DB?????? ??????)
             * // ????????? DB??? ????????? ????????? ????????? ?????? ???????????? false??? ????????? ?????????.
             * // ?????? DB?????? ????????? Rollback ??????, isDBOK ??????????????? false ??? ??????
             * //
             * commandMap.put("P_TYPE", "SC0010".equals(lgdPAYTYPE)?"CARD":"BANK"); // ????????????
             * commandMap.put("PG_VAN" , "PG");
             * if ("SC0010".equals(lgdPAYTYPE)) {
             * commandMap.put("CARD_CD", xpay.Response("LgdFINANCECODE", 0) != null && xpay.Response("LgdFINANCECODE",
             * 0).length() > 2 ? xpay.Response("LgdFINANCECODE", 0).substring(0, 2) : xpay.Response("LgdFINANCECODE",
             * 0));
             * commandMap.put("BANK_CD", xpay.Response("LgdFINANCECODE", 0) != null && xpay.Response("LgdFINANCECODE",
             * 0).length() > 2 ? xpay.Response("LgdFINANCECODE", 0).substring(0, 2) : xpay.Response("LgdFINANCECODE",
             * 0));
             * } else {
             * commandMap.put("CARD_CD", xpay.Response("LgdFINANCECODE", 0) != null && xpay.Response("LgdFINANCECODE",
             * 0).length() > 3 ? xpay.Response("LgdFINANCECODE", 0).substring(0, 3) : xpay.Response("LgdFINANCECODE",
             * 0));
             * commandMap.put("BANK_CD", xpay.Response("LgdFINANCECODE", 0) != null && xpay.Response("LgdFINANCECODE",
             * 0).length() > 3 ? xpay.Response("LgdFINANCECODE", 0).substring(0, 3) : xpay.Response("LgdFINANCECODE",
             * 0));
             * }
             * commandMap.put("UIP", commandMap.getIp());
             * if (request.getAttribute("IS_MOBILE") == null) {
             * commandMap.put("TERMINAL_TYPE", "2001");
             * } else {
             * boolean isMobile = (boolean)request.getAttribute("IS_MOBILE");
             * commandMap.put("TERMINAL_TYPE", (isMobile?"2002":"2001"));
             * }
             * commandMap.put("REGUSER", xpay.Response("LgdBUYERID", 0));
             * commandMap.put("comcd", myRsvnVO.getComcd());
             * try {
             * log.debug("myRsvnSave 2 :: commandMap = " + commandMap);
             * log.debug("myRsvnSave 2 :: commandMap.getParam() = " + commandMap.getParam());
             * log.debug("myRsvnSave 2 :: myRsvnVO = " + myRsvnVO);
             * isDBOK = myRsvnService.insertPaymentInfo(commandMap.getParam() , myRsvnVO);
             * } catch (Exception e) {
             * isDBOK = false;
             * }finally{
             * //?????? ??????
             * try {
             * chargeService.insertDbprocLog(myRsvnVO);
             * } catch (Exception e) {
             * e.printStackTrace();
             * }
             * }
             * if ( !isDBOK ) {
             * xpay.Rollback("?????? DB?????? ????????? ????????? Rollback ?????? [TID:" +xpay.Response("LgdTID",0)+",MID:" +
             * xpay.Response("LgdMID",0)+",OID:"+xpay.Response("LgdOID",0)+"]");
             * resultMsg.setLength(0);
             * //resultMsg.append( "TX Rollback Response_code = " + xpay.Response("LgdRESPCODE",0) + "<br>");
             * //resultMsg.append( "TX Rollback Response_msg = " + xpay.Response("LgdRESPMSG",0) + "<p>");
             * resultMsg.append( "?????? ?????? ?????? : " +myRsvnVO.getRetCd() + "<br>");
             * resultMsg.append( "?????? ?????? ????????? : " +myRsvnVO.getRetMsg() + "<br>");
             * if ( "0000".equals( xpay.m_szResCode ) ) {
             * resultMsg.append("??????????????? ??????????????? ?????? ???????????????.<br>");
             * } else {
             * resultMsg.append("??????????????? ??????????????? ???????????? ???????????????. ??????????????? ????????? ????????? ????????????.<br>");
             * }
             * try {
             * cancelMap.put("REQUEST_RESULT", myRsvnVO.getRetMsg());
             * chargeService.updatePgOrdMst(cancelMap);
             * } catch (Exception e) {
             * e.printStackTrace();
             * }
             * }
             * } else {
             * //?????????????????? ?????? ?????? DB??????
             * resultMsg.append( "??????????????? ?????????????????????(1).  <br>");
             * resultMsg.append( "TX ???????????? Response_code = " + lgdRESPCODE + "<br>");
             * resultMsg.append( "TX ???????????? Response_msg = " + lgdRESPMSG + "<p>");
             * try {
             * cancelMap.put("REQUEST_RESULT", lgdRESPMSG);
             * chargeService.updatePgOrdMst(cancelMap);
             * } catch (Exception e) {
             * e.printStackTrace();
             * }
             * }
             * } else {
             * //2)API ???????????? ????????????
             * resultMsg.append( "??????????????? ?????????????????????(2).  <br>");
             * resultMsg.append( "TX ???????????? Response_code = " + xpay.m_szResCode + "<br>");
             * resultMsg.append( "TX ???????????? Response_msg = " + xpay.m_szResMsg + "<p>");
             * //?????????????????? ?????? ?????? DB??????
             * }
             * }
             */
        }
        // ?????? ??????
        if (isDBOK) {
            LoginVO loginVO = commandMap.getUserInfo();

            if (list != null && list.size() > 0) {
                for (EdcRsvnMstVO vo : ((List<EdcRsvnMstVO>) list)) {
                    try {
                        Map<String, String> smsParam = new HashMap<String, String>();
                        smsParam.put("msgcd", "4");
                        smsParam.put("msgno", "44");
                        smsParam.put("sndHp", vo.getGuideTelno());
                        smsParam.put("hp", vo.getEdcReqMoblphon());
                        smsParam.put("????????????", vo.getEdcReqCustnm());
                        smsParam.put("?????????????????????", vo.getEdcPrgmnm());
                        smsParam.put("????????????", vo.getGuideTelno());
                        smsParam.put("????????????", CommonUtil.AddComma(vo.getEdcTotamt()) + "???");
                        if (vo.getFamilyCnt() > 0) {
                            smsParam.put("????????????", vo.getFamilyCnt() + "???");
                        } else {
                            smsParam.put("????????????", vo.getEdcVistnmpr() + "???");
                        }

                        smsParam.put("????????????", vo.getEdcRsvnno());
                        String period = CommonUtil.EduPeriodDate(vo);

                        String strEdcProgmType = vo.getEdcProgmType();

                        if (strEdcProgmType.equals("1001")) {
                            period = period + "\n??? ???????????? : " + vo.getEdcDaygbnNm();
                        }

                        commandMap.put("COMCD", Config.COM_CD);
                        commandMap.put("PART_CD", vo.getEdcPartcd());

                        smsParam.put("????????????", period);
                        smsParam.put("????????????", vo.getEdcPlacenm());
                        smsParam.put("????????????", vo.getGuideTelno());
                        smsParam.put("????????????", chargeService.selectRefundRule(commandMap));

                        if (loginVO == null) {
                            loginVO = new LoginVO();
                            loginVO.setUniqId(vo.getEdcRsvnMemno());
                            loginVO.setName(vo.getEdcRsvnCustnm());
                        }

                        smsService.sendMessage(smsParam, loginVO);
                    } catch (Exception e) {
                        log.error("?????? ???????????? SMS ?????? ?????? :" + e.getMessage());
                    }
                }
            }

        }

        model.addAttribute("resultUrl", Config.USER_ROOT + "/mypage/myRsvn/myRsvnResult");
        model.addAttribute("resultMsg", resultMsg.toString());
        model.addAttribute("resultFlag", isDBOK);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ?????? ?????? ?????????
     *
     * @param
     * @return callback page
     * @exception Exception
     */
    @RequestMapping(value = "/myRsvnResult")
    public String myRsvnResult(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO) throws Exception {
        myRsvnVO.setComcd(Config.COM_CD);
        myRsvnVO.setOrderId(myRsvnVO.getLgdOID());
        model.addAttribute("pgData", myRsvnService.selectCardAppHistData(myRsvnVO));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? or ?????? ?????? ?????????
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/myRsvnDetailX")
    public String selecRsvnDetailX(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        myRsvnVO.setComcd(Config.COM_CD);
        // myRsvnVO.setUniqId(commandMap.);
        Map payInfo = myRsvnService.selectCardAppHistData(myRsvnVO);
        model.addAttribute("payInfo", payInfo);

        String cstPLATFORM = "service";
        String mid = CommonUtil.getString(payInfo.get("mid"));
        if (mid.startsWith("t")) {
            mid = mid.substring(1, mid.length()); // ????????? ???????????? t??? ??????
            cstPLATFORM = "test";
        }
        myRsvnVO.setCstPLATFORM(cstPLATFORM);

        try {
            String lgdMERTKEY = EgovProperties.getProperty("tosspayments.mertkey." + mid); // ?????????

            StringBuffer sb = new StringBuffer();
            sb.append(CommonUtil.getString(payInfo.get("mid")));
            sb.append(CommonUtil.getString(payInfo.get("tid")));
            sb.append(lgdMERTKEY);

            byte[] bNoti = sb.toString().getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bNoti);

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                int c = digest[i] & 0xff;
                if (c <= 15) {
                    strBuf.append("0");
                }
                strBuf.append(Integer.toHexString(c));
            }

            String lgdHASHDATA = strBuf.toString();
            model.addAttribute("LgdHASHDATA", lgdHASHDATA);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ?????? ?????? include
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = { "/incPayDetail" })
    public String selecPayDetail(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        LoginVO loginVO = commandMap.getUserInfo();
        if (loginVO != null) {
            myRsvnVO.setUniqId(loginVO.getUniqId());
            myRsvnVO.setHpcertno(loginVO.getHpcertno());
        }

        String gubun = commandMap.getString("gubun");
        myRsvnVO.setMode("complete");
        myRsvnVO.setComcd(Config.COM_CD);

        int cnt = 0;

        List<EdcRsvnInfoVO> list = myRsvnService.selectEdcPaylist(myRsvnVO, commandMap.getString("appGbn"));
        model.addAttribute("list", list);

        cnt = (list == null ? 0 : list.size());

        if (cnt < 1) {
            HttpUtility.sendRedirect(request, response, "?????? ????????? ???????????? ????????????.", Config.USER_ROOT + "/mypage/myRsvn/myRsvnCompList?gubun=" + gubun);
            return null;
        }
        model.addAttribute("appGbn", commandMap.getString("appGbn"));
        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/myRsvn/" + gubun + "/incPayDetail");
    }

    /**
     * ?????????????????? ??????
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = { "/cancelDetailAjax" })
    public String cancelDetailAjax(@ModelAttribute("cancelVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        LoginVO loginVO = commandMap.getUserInfo();
        if (loginVO != null) {
            myRsvnVO.setUniqId(loginVO.getUniqId());
            myRsvnVO.setHpcertno(loginVO.getHpcertno());
        }

        Map payInfo = myRsvnService.selectCardAppHistData(myRsvnVO);

        String gubun = commandMap.getString("gubun");
        myRsvnVO.setMode("complete");
        myRsvnVO.setComcd(Config.COM_CD);

        myRsvnVO.setRsvnIdx(Arrays.asList(commandMap.getString("rsvnIdx").split("[,]")));

        List<EdcRsvnInfoVO> list = myRsvnService.selectEdcPaylist(myRsvnVO, CommonUtil.getString(payInfo.get("appGbn")));
        model.addAttribute("list", list);

        model.addAttribute("payInfo", payInfo);
        model.addAttribute("reasonList", codeService.selectCodeList("CM_RET_REASON"));

        model.addAttribute("cancelVO", myRsvnVO);
        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ??????
     *
     * @param
     * @return result - ??????
     * @exception Exception
     */
    @PostMapping(value = "/payCancel")
    @ResponseBody
    public ModelAndView payCancel(@ModelAttribute("myRsvnVO") MyRsvnVO myRsvnVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();
        LoginVO loginVO = commandMap.getUserInfo();

        if (loginVO == null) {
            resultInfo.setSuccess(false);
            resultInfo.setMsg("????????? ????????? ????????????.");
        } else {
            if (loginVO != null) {
                myRsvnVO.setUniqId(loginVO.getUniqId());
                myRsvnVO.setHpcertno(loginVO.getHpcertno());
                myRsvnVO.setModuser(loginVO.getId());
            }
            myRsvnVO.setComcd(Config.COM_CD);
            myRsvnVO.setRsvnNoList(Arrays.asList(commandMap.getString("rsvnNo").split(",")));

            Map<String, Object> payInfo = myRsvnService.selectCardAppHistData(myRsvnVO);

            // ?????? ?????? ??????
            Map<String, Object> payAmtInfo = new HashMap<String, Object>();

            if ("EVT".equals(myRsvnVO.getGubun())) {
                myRsvnVO.setPartGbn("3001");
                payAmtInfo = myRsvnService.selectEvtCancelInfo(myRsvnVO, CommonUtil.getString(payInfo.get("appGbn")));
            } else if ("EXBT".equals(myRsvnVO.getGubun())) {
                myRsvnVO.setPartGbn("1001");
                payAmtInfo = myRsvnService.selectExbtCancelInfo(myRsvnVO, CommonUtil.getString(payInfo.get("appGbn")));
            } else if ("EDC".equals(myRsvnVO.getGubun())) {
                myRsvnVO.setPartGbn("2001");
                // payAmtInfo = myRsvnService.selectEdcCancelInfo(myRsvnVO,
                // CommonUtil.getString(payInfo.get("appGbn")));
            }

            if (CommonUtil.getString(payAmtInfo.get("totCnt")).equals(CommonUtil.getString(payAmtInfo.get("cancelCnt")))) {
                myRsvnVO.setCancelType("F"); // ????????????
            } else {
                myRsvnVO.setCancelType("P"); // ????????????
            }

            long cancelAmt = myRsvnVO.getCancelAmt(); // ???????????????
            int cancelCnt = myRsvnVO.getRsvnNoList().size(); // ?????? ??? ??????

            StringBuffer resultMsg = new StringBuffer();

            if (cancelCnt != CommonUtil.getInt(payAmtInfo.get("validCnt"))) {
                resultMsg.append("????????? ?????????????????? ?????????????????? ???????????? ????????????. ???????????? ????????? ?????? ????????? ?????? ????????? ?????????.");
                resultInfo.setSuccess(false);
                resultInfo.setMsg(resultMsg.toString());

            } else if (cancelAmt != CommonUtil.getLong(payAmtInfo.get("cancelAmt"))) {
                resultMsg.append("?????? ????????? ?????? ????????????. ???????????? ????????? ?????? ?????? ????????? ?????? ????????? ?????????.");
                resultInfo.setSuccess(false);
                resultInfo.setMsg(resultMsg.toString());

            } else if (cancelAmt == 0) {
                resultMsg.append("?????? ????????? 0??? ?????????. ???????????? ????????? ?????? ?????? ????????? ?????? ????????? ?????????.");
                resultInfo.setSuccess(false);
                resultInfo.setMsg(resultMsg.toString());
            } else {

                /*
                 * [???????????? ?????? ?????????]
                 * ????????? "6. ?????? ????????? ?????? ????????????(API)"??? "?????? 3. ?????? ?????? ?????? ??? ?????? ?????? ??????" ??????
                 * LG?????????????????? ?????? ???????????? ????????????(LgdTID)??? ????????? ?????? ????????? ?????????.(???????????? ????????? POST??? ???????????????)
                 * (????????? LG?????????????????? ?????? ???????????? PAYKEY??? ???????????? ?????????.)
                 */

                String appGbn = CommonUtil.getString(payInfo.get("appGbn"));
                String lgdMID = String.valueOf(payInfo.get("mid")); // ????????? ???????????? 't'??? ???????????? ???????????????.
                String lgdTID = String.valueOf(payInfo.get("tid")); // LG?????????????????? ?????? ???????????? ????????????(LgdTID)
                String cstPLATFORM = lgdMID.startsWith("t") ? "test" : "service"; // LG???????????? ??????????????? ??????(test:?????????,
                                                                                  // service:?????????)
                String configPath = FileMngUtil.GetRealRootPath().concat("WEB-INF/lgdacom");// LG?????????????????? ?????????
                                                                                            // ????????????("/conf/lgdacom.conf")
                                                                                            // ?????? ??????.
                String lgdPcancelCnt = CommonUtil.getString(payInfo.get("pcancelNo")); // ???????????? seq ?????? ????????? ?????? ??????
                int pcancelCnt = CommonUtil.getInt(payInfo.get("pcancelCnt")); // ?????? ???????????? ??????

                lgdTID = (lgdTID == null) ? "" : lgdTID;

                // ?????? ?????? ?????? ?????? ???????????? ?????? ????????? ?????? ?????? ??????
                if (!lgdPcancelCnt.equals("") && pcancelCnt != myRsvnVO.getRsvnIdx().size()) {
                    resultInfo.setSuccess(false);

                    if (pcancelCnt == 1) {
                        resultInfo.setMsg("?????? ?????? ?????? ???????????????. ?????? ????????? ???????????????. ?????? ?????? ????????? ??????????????? ????????? ?????????.");
                    } else {
                        resultInfo.setMsg("?????? ?????? ?????? ???????????????. ?????? ????????? ???????????????.  ?????? ?????? ????????? ??????????????? ????????? ?????????.");
                    }

                } else {

                    /*
                     * // (1) XpayClient??? ????????? ?????? xpay ?????? ??????
                     * XPayClient xpay = new XPayClient();
                     * // (2) Init: XPayClient ?????????(???????????? ?????? ??????)
                     * // configPath: ????????????
                     * // CstPLATFORM: - test, service ?????? ?????? lgdacom.conf??? test_url(test) ?????? url(srvice) ??????
                     * // - test, service ?????? ?????? ???????????? ?????? ???????????? ????????? ??????
                     * xpay.Init(configPath, cstPLATFORM);
                     * // (3) Init_TX: ???????????? mall.conf, lgdacom.conf ?????? ??? ??????????????? ????????? ??? TXID ??????
                     * xpay.Init_TX(lgdMID);
                     * //???????????? ???????????? ( ???????????? ????????? ?????? ??????)
                     * int appAmt = CommonUtil.getInt(payInfo.get("appAmt")) -
                     * CommonUtil.getInt(payInfo.get("cancelAmt2"));
                     * if (CommonUtil.getInt(payInfo.get("cancelAmt2")) > 0 || "P".equals(myRsvnVO.getCancelType())) {
                     * myRsvnVO.setPartialYn("Y"); // ?????? ???????????? ????????? ?????? ??????
                     * } else {
                     * myRsvnVO.setPartialYn("N");
                     * }
                     * myRsvnVO.setAppDate(String.valueOf(payInfo.get("appDate"))); // ????????????
                     * myRsvnVO.setCancelAmt(cancelAmt); // ????????????
                     * myRsvnVO.setIp(commandMap.getIp());
                     * myRsvnVO.setLgdOID(myRsvnVO.getOrderId());
                     * myRsvnVO.setOldPcancelNo(lgdPcancelCnt); //????????? SEQ
                     * if (request.getAttribute("IS_MOBILE") == null) {
                     * myRsvnVO.setTerminalType("2001");
                     * } else {
                     * boolean isMobile = (boolean)request.getAttribute("IS_MOBILE");
                     * myRsvnVO.setTerminalType(isMobile?"2002":"2001");
                     * }
                     * //?????? ????????? ?????? ??????
                     * if (lgdPcancelCnt.equals("")) {
                     * lgdPcancelCnt = CommonUtil.getString(payInfo.get("pcancelNoMax")) ; //?????? seq
                     * }
                     * myRsvnVO.setPcancelNo(lgdPcancelCnt);
                     * if ((myRsvnVO.getPartialYn().equals("N") && cancelAmt >= appAmt) || appGbn.equals("2")) {
                     * xpay.Set("LgdTXNAME", "Cancel");
                     * log.debug("-------------------?????? ??????--------------------");
                     * } else {
                     * //?????? ??????
                     * myRsvnVO.setPartialYn("Y");
                     * xpay.Set("LgdTXNAME", "PartialCancel");
                     * xpay.Set("LgdCANCELAMOUNT",String.valueOf(cancelAmt));
                     * xpay.Set("LgdREMAINAMOUNT", String.valueOf(appAmt));
                     * xpay.Set("LgdPCANCELCNT", lgdPcancelCnt); //???????????? ?????? SEQ
                     * log.debug("-------------------?????? ??????--------------------");
                     * log.debug("-------------cancelAmt------" +String.valueOf(cancelAmt)+
                     * "--------------------");
                     * log.debug("-------------appAmt------" +String.valueOf(appAmt)+ "--------------------");
                     * log.debug("-------------lgdPcancelCnt------" +lgdPcancelCnt+ "--------------------");
                     * log.debug("-------------------?????? ??????--------------------");
                     * }
                     * xpay.Set("LgdTID", lgdTID);
                     * //
                     * // 1. ???????????? ?????? ????????????
                     * //
                     * // ???????????? ?????? ??????????????? ?????????????????? ??????????????? ????????????.
                     * // * [[[??????]]] ??????????????? ???????????? ??????????????? ????????????
                     * // 1. ???????????? : 0000, AV11
                     * // 2. ???????????? : 0000, RF00, RF10, RF09, RF15, RF19, RF23, RF25 (??????????????? ?????????-> ??????????????????.xls ??????)
                     * // 3. ????????? ??????????????? ?????? 0000(??????) ??? ???????????? ??????
                     * //
                     * //
                     * // (4) TX: lgdacom.conf??? ????????? URL??? ?????? ???????????? ?????? ????????????, ??????????????? true, false ??????
                     * if (xpay.TX()) {
                     * // (5) ?????????????????? ?????? ??????
                     * //1)?????????????????? ????????????(??????,?????? ?????? ????????? ????????? ????????????.)
                     * for (int i = 0; i < xpay.ResponseNameCount(); i++)
                     * {
                     * log.debug(xpay.ResponseName(i) + " = ");
                     * for (int j = 0; j < xpay.ResponseCount(); j++)
                     * {
                     * log.debug("\t" + xpay.Response(xpay.ResponseName(i), j) + "<br>");
                     * }
                     * }
                     * String resCode = xpay.m_szResCode;
                     * if
                     * (resCode.equals("0000")||resCode.equals("S020")||resCode.equals("AV11")||resCode.equals("RF00")||
                     * resCode.equals("RF10")||resCode.equals("RF09")||resCode.equals("RF15")||resCode.equals("RF19")||
                     * resCode.equals("RF23")||resCode.equals("RF25")) {
                     * int n = 0;
                     * try {
                     * //myRsvnVO.setCancelAppNo(xpay.Response("LgdPARTIALCANCEL_SEQNO_SUB", 0));
                     * myRsvnVO.setCancelDate(xpay.Response("LgdCANREQDATE", 0) ); // ????????????
                     * n = myRsvnService.updateCardAppCancel(myRsvnVO);
                     * if ( n > 0) {
                     * String cancelDate = CommonUtil.getString(payInfo.get("cancelDate"));
                     * if (myRsvnVO.getPartialYn().equals("N")) {
                     * myRsvnVO.setCancelDate( CommonUtil.getString(payInfo.get("appDate")) );
                     * } else if (!cancelDate.equals("") ) {
                     * myRsvnVO.setCancelDate(cancelDate ); // ????????????
                     * } else if (myRsvnVO.getCancelDate() == null || myRsvnVO.getCancelDate().equals("")) {
                     * myRsvnVO.setCancelDate( CommonUtil.getString(payInfo.get("appDate")) );
                     * }
                     * // ??? ????????????
                     * if (myRsvnVO.getCancelAppNo() == null || myRsvnVO.getCancelAppNo().equals("")) {
                     * myRsvnVO.setCancelAppNo(String.valueOf(payInfo.get("cancelAppno")) ); // ????????????
                     * }
                     * //????????? PG??? ???????????? ?????? 2021.03.17
                     * //n = myRsvnService.updatePayCancel(myRsvnVO);
                     * //log.debug("================msg:" +myRsvnVO.getRetMsg());
                     * //if (!"OK".equals(myRsvnVO.getRetCd())) {
                     * //resultMsg.append( "???????????? ?????? ?????? : " +myRsvnVO.getRetCd() + "\n");
                     * //resultMsg.append( "???????????? ?????? ????????? : " +myRsvnVO.getRetMsg());
                     * //}
                     * } else {
                     * resultMsg.append( "???????????? ??????.");
                     * }
                     * } catch (Exception e) {
                     * resultMsg.append( "???????????? ?????? ?????? : " +myRsvnVO.getRetCd() + "<br>");
                     * resultMsg.append( "???????????? ?????? ????????? : " +myRsvnVO.getRetMsg() + "<br>");
                     * n = 0;
                     * }finally{
                     * try {
                     * chargeService.insertCancelDbprocLog(myRsvnVO);
                     * } catch (Exception e) {
                     * e.printStackTrace();
                     * }
                     * //?????? ?????? SEQ ????????????
                     * if (!lgdPcancelCnt.equals("")) {
                     * try {
                     * chargeService.updatePartialNo(myRsvnVO);
                     * } catch (Exception e) {
                     * e.printStackTrace();
                     * }
                     * }
                     * }
                     * if (n > 0) {
                     * resultInfo.setSuccess(true);
                     * resultInfo.setMsg("?????? ????????? ?????????????????????.");
                     * } else {
                     * resultInfo.setSuccess(false);
                     * resultInfo.setMsg(resultMsg.toString());
                     * }
                     * } else {
                     * resultInfo.setSuccess(false);
                     * resultInfo.setMsg("?????? ??????????????? ????????????????????? ( TX Response_msg : " +
                     * xpay.m_szResMsg+" , resCode: "+resCode+" )");
                     * }
                     * } else {
                     * //2)API ?????? ?????? ????????????
                     * //out.println( "TX Response_code = " + xpay.m_szResCode + "<br>");
                     * //out.println( "TX Response_msg = " + xpay.m_szResMsg + "<p>");
                     * resultInfo.setSuccess(false);
                     * resultInfo.setMsg("?????? ??????????????? ????????????????????? ( TX Response_msg : " + xpay.m_szResMsg+" )");
                     * }
                     */
                }

            }
        }

        if (resultInfo.isSuccess()) {
            // ????????? ????????? ???????????? ?????? ??????
            try {
                String gubun = commandMap.getString("gubun");
                myRsvnVO.setMode("complete");
                myRsvnVO.setComcd(Config.COM_CD);
                myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

                List<EdcRsvnInfoVO> list = myRsvnService.selectEdcPaylist(myRsvnVO, "1");
                if (list != null && list.size() > 0) {
                    for (EdcRsvnInfoVO vo : ((List<EdcRsvnInfoVO>) list)) {
                        try {
                            Map<String, String> smsParam = new HashMap<String, String>();
                            smsParam.put("msgcd", "4");
                            smsParam.put("msgno", "41");
                            smsParam.put("sndHp", vo.getGuideTelno());
                            smsParam.put("????????????", CommonUtil.getDateString(vo.getRegdate()));
                            smsParam.put("hp", vo.getEdcReqMoblphon());
                            smsParam.put("????????????", vo.getEdcReqCustnm());
                            smsParam.put("?????????????????????", vo.getEdcPrgmnm());
                            smsParam.put("????????????", CommonUtil.AddComma(vo.getEdcTotamt()) + "???");
                            smsParam.put("????????????", vo.getEdcRsvnNo());
                            smsParam.put("????????????", CommonUtil.AddComma(vo.getCancelAmt()) + "???");
                            smsParam.put("????????????", vo.getGuideTelno());

                            smsService.sendMessage(smsParam, loginVO);
                        } catch (Exception e) {
                            log.error("?????? ???????????? SMS ?????? ?????? :" + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * ?????? ?????? ?????? ajax
     *
     * @param commandMap
     * @return String - jsp ?????????
     * @exception Exception
     */
    @PostMapping(value = "/myRsvnCouponAjax")
    public String myRsvnCouponAjax(@ModelAttribute("couponForm") MyRsvnVO couponForm, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        String strGubun = commandMap.getString("gubun");

        LoginVO userVO = commandMap.getUserInfo();
        couponForm.setComcd(userVO.getComcd());
        couponForm.setUniqId(userVO.getUniqId());

        CamelMap data = rsvnCommService.selectReserveData(couponForm);

        if (data != null) {
            couponForm.setPartCd(CommonUtil.getString(data.get("partcd")));
            couponForm.setProgramCd(CommonUtil.getString(data.get("programCd")));
            couponForm.setYmd(CommonUtil.getString(data.get("ymd")));
            couponForm.setTarget(CommonUtil.getString(data.get("target")));
            couponForm.setAppStatus(CommonUtil.getString(data.get("appStatus")));
        }
        // ????????? ?????? ?????? ??????
        commandMap.put("COMCD", Config.COM_CD);
        commandMap.put("YMD", couponForm.getYmd());
        commandMap.put("PART_CD", couponForm.getPartCd());
        commandMap.put("PGM_CD", couponForm.getProgramCd());
        commandMap.put("PGM_GUBUN", strGubun);

        List<RsvnCommVO> discList = rsvnCommService.selectEventStdmngList(commandMap.getParam());
        List<RsvnCommVO> newDiscList = new ArrayList<RsvnCommVO>();

        if (discList != null) {
            for (RsvnCommVO discVO : discList) {
                if (userVO.isMember() && "Y".equals(discVO.getMemberyn())) {
                    newDiscList.add(discVO);
                } else if (!userVO.isMember() && "Y".equals(discVO.getNonmebyn())) {
                    newDiscList.add(discVO);
                }
            }
        }

        // ???????????? or ???????????? ??? ?????? ?????? ??????????????? ????????????
        if ("Y".equals(userVO.getYearYn()) || "Y".equals(userVO.getSpecialYn())) {
            if ("Y".equals(userVO.getYearYn())) {
                couponForm.setMemberGbn("1"); // ????????????
            } else {
                couponForm.setMemberGbn("2"); // ????????????
            }
            RsvnCommVO annualData = rsvnCommService.selectAnnualDcData(couponForm);
            model.addAttribute("annualData", annualData);
        }

        if ("Y".equals((String) data.get("cpnYn"))) {
            // ?????? ?????? ????????????
            model.addAttribute("couponList", rsvnCommService.selectCouponList(couponForm));
        }
        model.addAttribute("discList", newDiscList);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ??? ?????? ????????? ????????????
     *
     * @param commandMap
     * @return ModelAndView
     * @exception Exception
     */
    @RequestMapping(value = "myRsvnCouponSave")
    @ResponseBody
    public ModelAndView myRsvnCouponSave(@ModelAttribute("couponForm") MyRsvnVO couponForm, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO userVO = commandMap.getUserInfo();
        ResultInfo resultInfo = new ResultInfo();
        couponForm.setComcd(Config.COM_CD);
        couponForm.setUniqId(userVO.getUniqId());

        if (request.getAttribute("IS_MOBILE") == null) {
            couponForm.setTerminalType("2001");
        } else {
            boolean isMobile = (boolean) request.getAttribute("IS_MOBILE");
            couponForm.setTerminalType(isMobile ? "2002" : "2001");
        }

        String returnMsg = "";
        if ("EXBT".equals(couponForm.getGubun())) {
            returnMsg = rsvnCommService.insertExbtRsvnItem(couponForm);
        } else if ("EVT".equals(couponForm.getGubun())) {
            returnMsg = rsvnCommService.insertEvtRsvnItem(couponForm);
        } else if ("EDC".equals(couponForm.getGubun())) {
            returnMsg = rsvnCommService.insertEdcRsvnItem(couponForm, userVO);
        }
        resultInfo.setMsg(returnMsg);
        resultInfo.setSuccess(true);
        mav.addObject("result", resultInfo);

        return mav;
    }

    /**
     * ?????? ??? ?????? ????????? ????????????
     *
     * @param commandMap
     * @return ModelAndView
     * @exception Exception
     */
    @RequestMapping(value = "myRsvnCouponCancel")
    @ResponseBody
    public ModelAndView myRsvnCouponCancel(@ModelAttribute("couponForm") MyRsvnVO couponForm, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();
        couponForm.setComcd(Config.COM_CD);
        if (request.getAttribute("IS_MOBILE") == null) {
            couponForm.setTerminalType("2001");
        } else {
            boolean isMobile = (boolean) request.getAttribute("IS_MOBILE");
            couponForm.setTerminalType(isMobile ? "2002" : "2001");
        }

        CamelMap data = rsvnCommService.selectReserveData(couponForm);

        if (data != null) {
            couponForm.setPartCd(CommonUtil.getString(data.get("partcd")));
            couponForm.setProgramCd(CommonUtil.getString(data.get("programCd")));
            couponForm.setYmd(CommonUtil.getString(data.get("ymd")));
            couponForm.setTarget(CommonUtil.getString(data.get("target")));
            couponForm.setAppStatus(CommonUtil.getString(data.get("appStatus")));
        }

        // ????????? ?????? ?????? ??????
        commandMap.put("COMCD", Config.COM_CD);
        commandMap.put("YMD", couponForm.getYmd());
        commandMap.put("PART_CD", couponForm.getPartCd());
        commandMap.put("PGM_CD", couponForm.getProgramCd());
        commandMap.put("PGM_GUBUN", commandMap.getString("gubun"));

        LoginVO userVO = commandMap.getUserInfo();
        couponForm.setUniqId(userVO.getUniqId());

        List<RsvnCommVO> discList = rsvnCommService.selectEventStdmngList(commandMap.getParam());

        // ?????? ??????
        RsvnCommVO eventDcInfo = null;

        if (discList != null) {
            for (RsvnCommVO discVO : discList) {
                if (userVO.isMember() && "Y".equals(discVO.getMemberyn())) {
                    if (eventDcInfo == null || eventDcInfo.getDcRate() > discVO.getDcRate()) {
                        eventDcInfo = discVO;
                    }
                } else if (!userVO.isMember() && "Y".equals(discVO.getNonmebyn())) {
                    if (eventDcInfo == null || eventDcInfo.getDcRate() > discVO.getDcRate()) {
                        eventDcInfo = discVO;
                    }
                }
            }
        }
        String annualLimitYn = "";
        // ???????????? or ???????????? ??? ?????? ?????? ??????????????? ????????????
        if ("Y".equals(userVO.getYearYn()) || "Y".equals(userVO.getSpecialYn())) {
            if ("Y".equals(userVO.getYearYn())) {
                couponForm.setMemberGbn("1"); // ????????????
            } else {
                couponForm.setMemberGbn("2"); // ????????????
            }
            RsvnCommVO annualData = rsvnCommService.selectAnnualDcData(couponForm);
            if (annualData != null && annualData.getLimitQty() == 0) {
                if (annualData.getLimitCnt() == 0 || (annualData.getLimitCnt() > 0 && annualData.getLimitCnt() > annualData.getApplyCnt())) {
                    annualLimitYn = "N";
                }
            }
        }

        if ("EXBT".equals(couponForm.getGubun())) {
            rsvnCommService.deleteExbtRsvnItem(couponForm, eventDcInfo, annualLimitYn);
        } else if ("EVT".equals(couponForm.getGubun())) {
            rsvnCommService.deleteEvtRsvnItem(couponForm, eventDcInfo, annualLimitYn);
        } else if ("EDC".equals(couponForm.getGubun())) {
            rsvnCommService.deleteEdcRsvnItem(couponForm, eventDcInfo);
        }

        resultInfo.setSuccess(true);
        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * ?????? ???????????????
     *
     * @param commandMap
     * @return ModelAndView
     * @exception Exception
     */
    @RequestMapping(value = "/EXBT/exbtPop")
    public String myRsvnExbtPop(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        // ??????
        RsvnMasterVO rsvnMasterVO = new RsvnMasterVO();
        rsvnMasterVO.setRsvnIdx(commandMap.getString("rsvnIdx"));
        rsvnMasterVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        RsvnMasterVO resultVO = dspyDsService.selectRegistMst(rsvnMasterVO);
        resultVO.setChargeList(dspyDsService.selectRegistItemList(rsvnMasterVO));
        model.addAttribute("resultVO", resultVO);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/myRsvn/EXBT/exbtPop");
    }

    /**
     * ?????? ????????? ?????? / ?????? ????????? ?????? (??????)
     *
     * @param commandMap
     * @return ModelAndView
     * @exception Exception
     */
    @RequestMapping(value = { "/edcCompPopup" })
    public String myRsvnEdcPop(MyRsvnVO myRsvnVO, CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        LoginVO loginVO = commandMap.getUserInfo();
        myRsvnVO.setEdcRsvnMemno(loginVO.getUniqId());
        // ????????????
        myRsvnVO.setEdcRsvnMoblphon(loginVO.getIhidNum());
        myRsvnVO.setMemNm(loginVO.getName());
        myRsvnVO.setEdcRsvnBirthdate(loginVO.getBirthDate());
        myRsvnVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        model.addAttribute("resultVO", myRsvnService.selectMyEdcRsvnDtl(myRsvnVO));
        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????????????????? ??????
     *
     * @param commandMap
     * @return String - jsp ?????????
     * @exception Exception
     */
    @GetMapping(value = "/myRsvnEdcModify")
    public String myRsvnEdcModify(MyRsvnVO myRsvnVO, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {

        EdcRsvnInfoVO resultVO = myRsvnService.selectMyEdcRsvnDtl(myRsvnVO);
        if (resultVO != null) {
            if (Config.NO.equals(resultVO.getEditYn())) {
                HttpUtility.sendBack(request, response, "?????? ????????? ?????? ?????? ????????? ??? ????????????.");
                return null;
            }
        }

        /*
         * List<?> itemList = edcRsvnService.selectProgmMngiteminfoList2(commandMap.getParam());
         * model.addAttribute("edcarsvnProgMItemCnt", itemList != null ? itemList.size() : 0);
         * model.addAttribute("detailVO", resultVO);
         * model.addAttribute("email", commandMap.getUserInfo().getEmail());
         * model.addAttribute("edcarsvnFamlyinfoList",
         * edcRsvnService.selectEdcRsvnFamlyinfoList(commandMap.getParam()));
         * model.addAttribute("itemList", itemList);
         */

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ?????? ?????? ?????? ????????? ????????????
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnEdcSave", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectEdcarsvnRegSave(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {
        paramMap.put("comCd", Config.COM_CD);
        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();
        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            throw new MyException("????????? ????????? ?????? ??? ????????????.", -3);
        }

        paramMap.put("uniqId", commandMap.getUserInfo().getUniqId());
        paramMap.put("modUser", loginUser.getId());
        paramMap.put("edcNonmembCertno", loginUser.getHpcertno());
        paramMap.put("dbEncKey", EgovProperties.getProperty("Globals.DbEncKey"));

        int intEdcRsvnReqid = edcRsvnService.saveEduRsvnModify(paramMap, commandMap);
        finalResult.put("EXE_YN", "Y");
        finalResult.put("RSVN_REQ_ID", intEdcRsvnReqid);
        finalResult.put("DB_DATA", "{DB_YN : Y}");
        finalResult.put("DB_DROW", 1);

        mav.addObject("RESULT", finalResult);

        return mav;

    }

    /**
     * ?????? ?????? ?????? ?????? ????????? ????????????
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnEdcNumSave", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView myRsvnEdcNumSave(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {
        paramMap.put("comCd", Config.COM_CD);
        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();
        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            throw new MyException("????????? ????????? ?????? ??? ????????????.", -3);
        }

        paramMap.put("uniqId", commandMap.getUserInfo().getUniqId());
        paramMap.put("modUser", loginUser.getId());
        paramMap.put("edcNonmembCertno", loginUser.getHpcertno());

        EdcRsvnInfoVO rsvnInfoParam = new EdcRsvnInfoVO();
        // int intEdcRsvnReqid = edcRsvnService.updateRsvnInfoPerson(paramMap, commandMap);
        int intEdcRsvnReqid = edcRsvnService.updateRsvnInfoPerson(rsvnInfoParam);
        finalResult.put("EXE_YN", "Y");
        finalResult.put("NUM_SAVE", "Y");
        finalResult.put("RSVN_REQ_ID", intEdcRsvnReqid);
        finalResult.put("DB_DATA", "{DB_YN : Y}");
        finalResult.put("DB_DROW", 1);

        mav.addObject("RESULT", finalResult);

        return mav;

    }

    /**
     * ?????????????????? ??????
     *
     * @param commandMap
     * @return String - jsp ?????????
     * @exception Exception
     */
    @GetMapping(value = "/myRsvnExbtModify")
    public String myRsvnExbtModify(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {
        // ??????
        commandMap.put("comcd", Config.COM_CD);
        RsvnMasterVO rsvnMasterVO = new RsvnMasterVO();
        rsvnMasterVO.setRsvnIdx(commandMap.getString("rsvnIdx"));
        rsvnMasterVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        RsvnMasterVO resultVO = dspyDsService.selectRegistMst(rsvnMasterVO);
        commandMap.put("exbtSeq", resultVO.getExbtSeq());
        commandMap.put("target", resultVO.getTarget());
        List<ExbtChargeVO> chargeList = dspyDsService.selectExbtChargeList(commandMap.getParam());

        LoginVO userVO = commandMap.getUserInfo();

        // ????????? ?????? ?????? ??????
        commandMap.put("COMCD", Config.COM_CD);
        commandMap.put("YMD", resultVO.getYmd());
        commandMap.put("PART_CD", resultVO.getExbtPartcd());
        commandMap.put("PGM_CD", resultVO.getExbtSeq());
        commandMap.put("PGM_GUBUN", "EXBT");

        RsvnCommVO discAnnualVO = null;
        RsvnCommVO discInfoVO = null;
        List<RsvnCommVO> discList = rsvnCommService.selectEventStdmngList(commandMap.getParam());

        if (discList != null) {
            for (RsvnCommVO discVO : discList) {
                if (discInfoVO != null) {
                    // ?????? ????????? ?????????
                    break;
                } else if (userVO.isMember() && "Y".equals(discVO.getMemberyn())) {
                    discInfoVO = discVO;
                } else if (!userVO.isMember() && "Y".equals(discVO.getNonmebyn())) {
                    discInfoVO = discVO;
                }
            }
        }

        // ????????????
        /*
         * if (userVO.isMember() ) {
         * userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
         * MemberVO memberVO = myInforService.selectMemberData(userVO);
         * if (memberVO.getSpecialYn().equals("Y") || memberVO.getYearYn().equals("Y")) {
         * MyRsvnVO rsvnVO = new MyRsvnVO();
         * rsvnVO.setComcd(Config.COM_CD);
         * rsvnVO.setUniqId(memberVO.getMemNo());
         * rsvnVO.setRsvnIdxOne("0");
         * rsvnVO.setGubun("EXBT");
         * rsvnVO.setProgramCd(resultVO.getExbtSeq());
         * if ("Y".equals(memberVO.getYearYn())) {
         * rsvnVO.setMemberGbn("1"); // ????????????
         * } else {
         * rsvnVO.setMemberGbn("2"); // ????????????
         * }
         * RsvnCommVO annualData = rsvnCommService.selectAnnualDcData(rsvnVO);
         * if (annualData != null && annualData.getLimitCnt() == 0 && (discInfoVO == null || discInfoVO.getEventDcRate()
         * < annualData.getEventDcRate())) {
         * //?????? ??????
         * discAnnualVO = annualData;
         * resultVO.setDcAnnualLimit(annualData.getLimitQty());
         * }
         * }
         * }
         */

        CamelMap groupdisc = dspyDsService.selectGroupDiscount(resultVO);

        for (ExbtChargeVO itemVO : chargeList) {
            // ?????? ?????? ??????
            if (groupdisc != null && !CommonUtil.getString(groupdisc.get("dcReasonCd")).equals("") && "Y".equals(itemVO.getGroupdcYn())) {
                itemVO.setDcKindCd("6001");
                itemVO.setDcType(CommonUtil.getString(groupdisc.get("dcReasonCd")));
                itemVO.setDcRate(Long.parseLong(CommonUtil.getString(groupdisc.get("dcRate"))));
                itemVO.setDcName("????????????");
            } else {
                if (discInfoVO != null) {
                    itemVO.setDcKindCd("4010");
                    itemVO.setDcType(discInfoVO.getDcReasonCd());
                    itemVO.setDcRate(discInfoVO.getDcRate());
                    itemVO.setDcName(discInfoVO.getDcName());
                }
                if (discAnnualVO != null) {
                    itemVO.setDcAnnualCd(discAnnualVO.getDcReasonCd());
                    itemVO.setDcAnnualRate(discAnnualVO.getDcRate());
                    itemVO.setDcAnnualNm("???????????? ??????");
                }
            }
        }

        resultVO.setChargeList(chargeList);
        model.addAttribute("rsvnMasterVO", resultVO);
        model.addAttribute("optData", rsvnCommService.selectOptData(commandMap.getParam())); // ???????????? ??????

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ?????? ???????????? ????????????
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnExbtSave", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView myRsvnExbtSave(@ModelAttribute("rsvnMasterVO") RsvnMasterVO rsvnMasterVO,
            HttpServletRequest request, ModelMap model, CommandMap commandMap) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            throw new MyException("????????? ????????? ?????? ??? ????????????.", -3);
        }
        rsvnMasterVO.setComcd(Config.COM_CD);
        rsvnMasterVO.setMemNo(commandMap.getUserInfo().getUniqId());
        rsvnMasterVO.setHpcertno(loginUser.getHpcertno());
        rsvnMasterVO.setReguser(loginUser.getId());
        rsvnMasterVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        int cnt = dspyDsService.updateReserveMaster(rsvnMasterVO);

        if (cnt > 0) {
            mav.addObject("RESULT", "OK");
        } else {
            mav.addObject("RESULT", "????????? ???????????? ????????????");
        }

        return mav;

    }

    /**
     * ?????? ?????? ????????? ????????????
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnExbtItemSave", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView myRsvnExbtItemSave(@ModelAttribute("rsvnMasterVO") RsvnMasterVO rsvnMasterVO,
            HttpServletRequest request, ModelMap model, CommandMap commandMap) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            throw new MyException("????????? ????????? ?????? ??? ????????????.", -3);
        }
        rsvnMasterVO.setComcd(Config.COM_CD);
        rsvnMasterVO.setMemNo(commandMap.getUserInfo().getUniqId());
        rsvnMasterVO.setHpcertno(loginUser.getHpcertno());
        rsvnMasterVO.setReguser(loginUser.getId());
        rsvnMasterVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        MemberVO memberVO = null;

        if (loginUser.isMember()) {
            loginUser.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
            memberVO = myInforService.selectMemberData(loginUser);
        }

        String resultMsg = dspyDsService.updateReserveItem(memberVO, rsvnMasterVO);

        mav.addObject("RESULT", resultMsg);

        return mav;

    }

    /**
     * ??????/??????/?????? ???????????? ??????
     *
     * @param commandMap
     * @return String - jsp ?????????
     * @exception Exception
     */
    @GetMapping(value = "/myRsvnEvtModify")
    public String myRsvnEvtModify(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {
        // ??????
        commandMap.put("comcd", Config.COM_CD);

        // ?????? ??????
        EvtrsvnMstVO evtrsvnMstVO = new EvtrsvnMstVO();
        evtrsvnMstVO.setEvtRsvnIdx(commandMap.getString("evtRsvnIdx"));
        evtrsvnMstVO.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));
        evtrsvnMstVO.setComcd(Config.COM_CD);
        // ?????? ??????
        evtrsvnMstVO = evtrsvnService.selectEvtrsvnDetail(evtrsvnMstVO);

        // ????????????
        EvtStdmngVO tempVO = new EvtStdmngVO();
        tempVO.setComcd(Config.COM_CD);
        tempVO.setEvtNo(evtrsvnMstVO.getEvtNo());
        tempVO.setEvtRsvnIdx(evtrsvnMstVO.getEvtRsvnIdx());
        tempVO.setEvtPersnGbn(evtrsvnMstVO.getEvtPersnGbn());

        List<EvtItemAmountVO> chargeList = evtrsvnService.selectEvtChargeList(tempVO);

        LoginVO userVO = commandMap.getUserInfo();
        // ????????????
        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        MemberVO memberVO = myInforService.selectMemberData(userVO);

        // ????????? ?????? ?????? ??????
        commandMap.put("COMCD", Config.COM_CD);
        commandMap.put("YMD", evtrsvnMstVO.getEvtVeingdate());
        commandMap.put("PART_CD", evtrsvnMstVO.getEvtPartcd());
        commandMap.put("PGM_CD", evtrsvnMstVO.getEvtNo());
        commandMap.put("PGM_GUBUN", "EVT");

        RsvnCommVO discAnnualVO = null;
        RsvnCommVO discInfoVO = null;
        List<RsvnCommVO> discList = rsvnCommService.selectEventStdmngList(commandMap.getParam());
        if (discList != null) {
            for (RsvnCommVO discVO : discList) {
                if (discInfoVO != null) {
                    // ?????? ????????? ?????????
                    break;
                } else if (userVO.isMember() && "Y".equals(discVO.getMemberyn())) {
                    discInfoVO = discVO;
                } else if (!userVO.isMember() && "Y".equals(discVO.getNonmebyn())) {
                    discInfoVO = discVO;
                }
            }
        }

        // ????????????
        /*
         * if (memberVO != null) {
         * if (memberVO.getSpecialYn().equals("Y") || memberVO.getYearYn().equals("Y")) {
         * MyRsvnVO rsvnVO = new MyRsvnVO();
         * rsvnVO.setComcd(Config.COM_CD);
         * rsvnVO.setUniqId(memberVO.getMemNo());
         * rsvnVO.setRsvnIdxOne("0");
         * rsvnVO.setGubun("EVT");
         * rsvnVO.setProgramCd(evtrsvnMstVO.getEvtNo());
         * if ("Y".equals(memberVO.getYearYn())) {
         * rsvnVO.setMemberGbn("1"); // ????????????
         * } else {
         * rsvnVO.setMemberGbn("2"); // ????????????
         * }
         * RsvnCommVO annualData = rsvnCommService.selectAnnualDcData(rsvnVO);
         * if (annualData != null && annualData.getLimitCnt() == 0 && (discInfoVO == null || discInfoVO.getEventDcRate()
         * < annualData.getEventDcRate())) {
         * //?????? ??????
         * discAnnualVO = annualData;
         * evtrsvnMstVO.setDcAnnualLimit(annualData.getLimitQty());
         * }
         * }
         * }
         */
        CamelMap groupdisc = evtrsvnService.selectEvtGrpDscnt(evtrsvnMstVO);
        for (EvtItemAmountVO itemVO : chargeList) {
            // ?????? ?????? ??????
            if (groupdisc != null && !CommonUtil.getString(groupdisc.get("dcReasonCd")).equals("")) {
                if ("Y".equals(itemVO.getGroupdcyn())) {
                    itemVO.setDcRate(Integer.parseInt(groupdisc.get("dcRate").toString() != null
                            ? groupdisc.get("dcRate").toString() : "0"));
                    itemVO.setDcName("????????????");
                }
            } else {
                if (discInfoVO != null) {
                    itemVO.setDcRate(discInfoVO.getDcRate());
                    itemVO.setDcName(discInfoVO.getDcName());
                }
                if (discAnnualVO != null) {
                    itemVO.setDcAnnualCd(discAnnualVO.getDcReasonCd());
                    itemVO.setDcAnnualRate(discAnnualVO.getDcRate());
                    itemVO.setDcAnnualNm("???????????? ??????");
                }
            }
        }

        evtrsvnMstVO.setChargeList(chargeList);
        model.addAttribute("email", userVO.getEmail());
        model.addAttribute("mstVO", evtrsvnMstVO);
        model.addAttribute("optData", rsvnCommService.selectOptData(commandMap.getParam())); // ???????????? ??????

        log.debug("end- rsvnDetailCall");

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ??????/??????/?????? ??????????????? ????????????
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnEvtSave", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView myRsvnEvtSave(@ModelAttribute EvtrsvnMstVO mstVO, HttpServletRequest request, ModelMap model,
            CommandMap commandMap) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        log.debug("myrsvnevtitemsave ??????");
        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            throw new MyException("????????? ????????? ?????? ??? ????????????.", -3);
        }
        mstVO.setComcd(Config.COM_CD);
        mstVO.setEvtRsvnMemno(commandMap.getUserInfo().getUniqId());
        mstVO.setEvtNonmembCertno(loginUser.getHpcertno());
        mstVO.setReguser(loginUser.getId());
        mstVO.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));

        String resultMsg = evtrsvnService.updateEvtrsvnData(mstVO);

        mav.addObject("RESULT", resultMsg);

        return mav;

    }

    /**
     * ??????/??????/?????? ??????????????? ????????????
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/myRsvnEvtItemSave", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView myRsvnEvtItemSave(HttpServletRequest request, ModelMap model, CommandMap commandMap,
            EvtrsvnMstVO mstVO) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        log.debug("myrsvnevtitemsave ??????");
        LoginVO loginUser = commandMap.getUserInfo();
        if (loginUser == null) {
            throw new MyException("????????? ????????? ?????? ??? ????????????.", -3);
        }
        mstVO.setComcd(Config.COM_CD);
        mstVO.setEvtRsvnMemno(commandMap.getUserInfo().getUniqId());
        mstVO.setEvtNonmembCertno(loginUser.getHpcertno());
        mstVO.setReguser(loginUser.getId());
        mstVO.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));

        // ????????????
        loginUser.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        MemberVO memberVO = myInforService.selectMemberData(loginUser);

        String resultMsg = evtrsvnService.updateEvtrsvnItem(memberVO, mstVO);

        mav.addObject("RESULT", resultMsg);

        return mav;

    }
}
