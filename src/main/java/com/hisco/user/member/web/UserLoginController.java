package com.hisco.user.member.web;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.terms.vo.TermsVO;
import com.hisco.cmm.modules.ErrorMessageInfo;
import com.hisco.cmm.modules.RequestUtil;
import com.hisco.cmm.modules.ResponseUtil;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.modules.pg.nice.NiceNamefactDto;
import com.hisco.cmm.modules.site.thissite.module_config.ModuleConfigVo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.service.TossNamefactService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.WebEncDecUtil;
import com.hisco.user.member.service.UserJoinService;
import com.hisco.user.member.service.UserLoginService;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.service.MyInforService;
import com.hisco.user.nice.service.NiceNamefactService;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.sym.log.wlg.service.EgovWebLogService;
import egovframework.com.sym.log.wlg.service.WebLog;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.com.utl.sim.service.EgovFileScrty;
import lombok.extern.slf4j.Slf4j;;

/**
 * ??????????????? ????????? ?????? ????????????
 *
 * @author ?????????
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          ????????? ?????? ??????
 *          ------------------------------------------------------------------------
 *          ????????? 2020.08.05 ????????????
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/member")
public class UserLoginController {

    // ???????????? ????????? ???????????????
    public static final String namefact_design_param = "design";

    // ???????????? ?????? ???????????????
    public static final String namefact_type_param = "type";

    // ???????????? ?????? URL ???????????????
    public static final String namefact_end_url_param = "end";

    // ???????????? ?????? ????????? ?????? ?????? ???????????????
    public static final String namefact_message_param = "message";

    @Resource(name = "userLoginService")
    private UserLoginService userLoginService;

    @Resource(name = "tossNamefactService")
    private TossNamefactService tossNamefactService;

    @Resource(name = "niceNamefactService")
    private NiceNamefactService niceNamefactService;

    @Resource(name = "myInforService")
    private MyInforService myInforService;

    @Resource(name = "EgovWebLogService")
    private EgovWebLogService egovWebLogService;

    @Resource(name = "userJoinService")
    private UserJoinService userJoinService;

    /**
     * ??????????????? ????????? ?????? ??????.
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/login")
    public String userLogin(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @ModelAttribute("memberVO") MemberVO memberVO) throws Exception {

        String currentDomain = EgovProperties.getProperty("Globals.Domain");
        model.addAttribute("userLoginInfo", commandMap.getUserInfo());
        model.addAttribute("currentDomain", currentDomain);
        model.addAttribute("kakaoKey", EgovProperties.getProperty("SNS.kakaoKey"));
        model.addAttribute("naverKey", EgovProperties.getProperty("SNS.naverKey"));
        model.addAttribute("googleKey", EgovProperties.getProperty("SNS.googleKey"));

        String returnURL = commandMap.getString("returnURL");

        if (commandMap.getString("member_yn").equals("") && returnURL.indexOf("myinfor") >= 0) {
            commandMap.put("member_yn", "Y");
        }

        model.addAttribute("NONMEMBER_YN", commandMap.getString("member_yn").equals("Y") ? "N" : "Y");

        if (commandMap.getUserInfo() != null && commandMap.getUserInfo().isMember()) {
            String msg = "???????????? ?????? ?????????.";
            HttpUtility.sendRedirect(request, response, msg, Config.USER_ROOT + "/main");
            return null;
        } else {
            // ?????? ?????????
            HttpSession session = request.getSession();
            UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
            userSession.setUserInfo(null);
            session.setAttribute(Config.USER_SESSION, userSession);

            String loginReturnURL = commandMap.getString("returnURL");
            if (loginReturnURL.equals(""))
                loginReturnURL = Config.USER_ROOT + "/main";
            if (loginReturnURL.startsWith("http") && !loginReturnURL.startsWith(currentDomain)) {
                loginReturnURL = Config.USER_ROOT + "/main";
            }

            String contextPath = request.getContextPath();
            if (contextPath.length() > 1)
                loginReturnURL = loginReturnURL.replaceFirst(contextPath, "");

            model.addAttribute("loginReturnURL", loginReturnURL);
            return HttpUtility.getViewUrl(request);
        }

    }

    /**
     * ??????????????? ????????? ?????? ??????.
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/nonLogin")
    public String userNonLogin(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {

        // ?????? ?????????
        if (commandMap.getUserInfo() != null && commandMap.getUserInfo().isMember()) {
            String msg = "???????????? ?????? ?????????.";
            HttpUtility.sendRedirect(request, response, msg, Config.USER_ROOT + "/main");
            return null;
        } else {
            HttpSession session = request.getSession();
            UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
            userSession.setUserInfo(null);
            session.setAttribute(Config.USER_SESSION, userSession);

            return HttpUtility.getViewUrl(request);
        }
    }

    /**
     * ????????? SNS ?????? ?????? url
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/snsNaverPop")
    public String snsNaverCallback(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        model.addAttribute("currentDomain", EgovProperties.getProperty("Globals.Domain"));
        model.addAttribute("naverKey", EgovProperties.getProperty("SNS.naverKey"));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ??????(??????) ???????????? ????????????
     *
     * @param vo
     *            - ?????????, ??????????????? ?????? LoginVO
     * @param request
     *            - ??????????????? ?????? HttpServletRequest
     * @return result - ???????????????(????????????)
     * @exception Exception
     */
    @PostMapping(value = "actionLogin.json")
    @ResponseBody
    public ModelAndView actionLogin(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request,
            ModelMap model) throws Exception {

        log.debug("call " + Config.USER_ROOT + " actionLogin();");

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        boolean loginFlag = true;
        // ????????????????????? ??????
        loginVO.setIp(request.getRemoteAddr());
        String loginMsg = userLoginService.processLoginIncorrect(loginVO, "N");

        if (loginMsg.equals("E") || loginMsg.equals("sleep") || loginMsg.equals("agree") || loginMsg.equals("mustChangePw")) {

            // 2. ????????? ??????
            if (loginMsg.equals("sleep")) {
                loginVO.setStatus("9002"); // ????????????
            }

            loginVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
            LoginVO resultVO = userLoginService.actionLogin(loginVO);

            // 3. ?????? ????????? ??????
            if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
                HttpSession session = request.getSession();

                if (loginMsg.equals("sleep")) {
                    // ?????? ?????? ?????? ??????
                    session.setAttribute("SLEEP_USER", resultVO);
                    loginFlag = false;

                } else {
                    // 3-1. ????????? ????????? ????????? ??????
                    /* Authenticated */
                    UserSession userSession = new UserSession();
                    userSession.setUserInfo(resultVO);
                    session.setAttribute(Config.USER_SESSION, userSession);

                    loginFlag = true;

                    /*
                     * if ("mustChangePw".equals(loginMsg)) { // ???????????? ???????????? ??????
                     * session.setAttribute("PASSWORD_CHANGE", resultVO);
                     * }
                     */

                }
            } else {
                loginFlag = false;
                loginMsg = "????????? ?????? ??????????????? ?????? ????????????.";
            }

        } else {
            loginFlag = false;
        }

        resultInfo.setSuccess(loginFlag);
        resultInfo.setMsg(loginMsg);

        mav.addObject("loginVO", null);
        mav.addObject("result", resultInfo);

        return mav;
    }

    /**
     * ????????? ???????????? ????????????
     *
     * @param vo
     *            - ?????????, ??????????????? ?????? LoginVO
     * @param request
     *            - ??????????????? ?????? HttpServletRequest
     * @return result - ???????????????(????????????)
     * @exception Exception
     */
    @PostMapping(value = "nonLoginAction.json")
    @ResponseBody
    public ModelAndView nonLoginAction(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request,
            ModelMap model) throws Exception {

        log.debug("call " + Config.USER_ROOT + " nonLoginAction();");

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        boolean loginFlag = true;
        // ????????????????????? ??????
        loginVO.setIp(request.getRemoteAddr());
        loginVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        // 3. ?????? ????????? ??????
        if (loginVO != null && !StringUtil.IsEmpty(loginVO.getName()) && !StringUtil.IsEmpty(loginVO.getIhidNum()) && !StringUtil.IsEmpty(loginVO.getBirthDate())) {
            HttpSession session = request.getSession();

            // 3-1. ????????? ????????? ????????? ??????
            /* Authenticated */
            UserSession userSession = new UserSession();
            userSession.setUserInfo(loginVO);
            session.setAttribute(Config.USER_SESSION, userSession);

            loginFlag = true;

        } else {
            loginFlag = false;
            resultInfo.setMsg("????????? ????????? ??????");
        }

        resultInfo.setSuccess(loginFlag);
        mav.addObject("result", resultInfo);

        return mav;
    }

    /**
     * ????????? SNS ???????????? ????????????
     *
     * @param vo
     *            - ?????????, ??????????????? ?????? LoginVO
     * @param request
     *            - ??????????????? ?????? HttpServletRequest
     * @return result - ???????????????(????????????)
     * @exception Exception
     */
    @PostMapping(value = "snsUserLogin")
    @ResponseBody
    public ModelAndView selectSnsUserLogin(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request,
            ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        boolean loginFlag = true;

        loginVO.setIp(request.getRemoteAddr());
        String loginMsg = userLoginService.processLoginIncorrect(loginVO, "Y");

        if (loginMsg.equals("E") || loginMsg.equals("sleep") || loginMsg.equals("agree")) {
            // 2. ????????? ??????
            if (loginMsg.equals("sleep")) {
                loginVO.setStatus("9002"); // ????????????
            }
            loginVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
            LoginVO resultVO = userLoginService.actionLogin(loginVO);

            // 3. ?????? ????????? ??????
            if (resultVO != null && resultVO.getUniqId() != null) {
                resultVO.setSnsId(loginVO.getSnsId());
                // 3-1. ????????? ????????? ????????? ??????
                /* Authenticated */
                HttpSession session = request.getSession();
                if (loginMsg.equals("sleep")) {
                    // ?????? ?????? ?????? ??????
                    session.setAttribute("SLEEP_USER", resultVO);
                    loginFlag = false;
                } else {
                    UserSession userSession = new UserSession();
                    userSession.setUserInfo(resultVO);
                    session.setAttribute(Config.USER_SESSION, userSession);
                    loginFlag = true;
                }
            } else {
                loginFlag = false;
                loginMsg = "?????? ????????? ???????????? ????????? ????????????.";
            }
        } else {
            loginFlag = false;
        }

        resultInfo.setSuccess(loginFlag);
        resultInfo.setMsg(loginMsg);

        mav.addObject("loginVO", null);
        mav.addObject("result", resultInfo);

        return mav;
    }

    /**
     * ??????(??????) ?????? ??????
     *
     * @param request
     *            - ??????????????? ?????? HttpServletRequest
     * @return String
     * @exception Exception
     */
    @GetMapping(value = "logout")
    public void logout(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        String strReqURL = request.getRequestURI();

        // log.debug(request.getContextPath());
        String strIpAdress = commandMap.getIp();

        // System. out .println("IP address: " + strIpAdress);

        String currentDomain = EgovProperties.getProperty("Globals.Domain");
        String loginReturnURL = commandMap.getString("returnURL");

        // -------------------------------------------------------------Log ??????
        HttpSession session = request.getSession();
        UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
        LoginVO user = userSession.getUserInfo();

        String webId = (user == null || user.getUniqId() == null) ? "" : user.getId();

        WebLog webLog = new WebLog();
        webLog.setUrl(strReqURL);
        webLog.setRqesterId(webId);
        // webLog.setRqesterIp(request.getRemoteAddr());
        webLog.setRqesterIp(strIpAdress);

        // webLog.setMenuNo("Logout"); // ????????????

        egovWebLogService.logInsertWebLog(webLog);
        // -------------------------------------------------------------Log ??????

        session.removeAttribute(Config.USER_SESSION);
        session.invalidate();

        if (loginReturnURL.equals(""))
            loginReturnURL = Config.USER_ROOT + "/main";
        if (loginReturnURL.startsWith("http") && !loginReturnURL.startsWith(currentDomain)) {
            loginReturnURL = Config.USER_ROOT + "/main";
        }

        response.setHeader("rUrl", loginReturnURL);
        response.sendRedirect(request.getContextPath() + loginReturnURL);

    }
    
    
    /**
     * ????????? ??????(????????? ??????)
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findIdOrPasswd")
    public String findIdOrPasswd(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        return HttpUtility.getViewUrl(request);
    }
    

    /**
     * ????????? ??????(????????? ??????)
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findId")
    public String selectUserFindId(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        return HttpUtility.getViewUrl(request);
    }

    /**
     * ????????? ??????(????????????)
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findIdByCerti")
    public String selectUserFindIdByCerti(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {
        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ?????? ??????
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findPwByCertiResult")
    public String findPwByCertiResult(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {
        return HttpUtility.getViewUrl(request);
    }

    /**
     * ????????? ??????
     *
     * @param loginVO
     *            - ?????? ????????? ????????? ?????? LoginVO
     * @return result - ??????
     * @exception Exception
     */
    @PostMapping(value = "findIdResult")
    public String findIdAction(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap) throws Exception {

        ResultInfo resultInfo = new ResultInfo();
        boolean loginFlag = false;
        String loginMsg = "";

        String strBirthYYYY = String.valueOf(paramMap.get("birthYYYY"));
        String strBirthMM = String.valueOf(paramMap.get("birthMM"));
        String strBirthDD = String.valueOf(paramMap.get("birthDD"));

        String strBirthDate = strBirthYYYY + strBirthMM + strBirthDD;

        if (strBirthDate.length() != 8) {
            loginMsg = "???????????? ??????????????? ????????????.";
        } else {

            loginVO.setBirthDate(strBirthDate);
            //// loginVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

            // -------------------------------------------------------------------?????? ?????? ????????? ??????
            String strSpowiseCmsKey = EgovProperties.getProperty("Globals.SpowiseCms.Key");

            String strIhidNum = String.valueOf(loginVO.getIhidNum()).replace("-", "");

            String strEncIhidNum = WebEncDecUtil.fn_encrypt(strIhidNum, strSpowiseCmsKey);
            loginVO.setIhidNum(strEncIhidNum);
            // -------------------------------------------------------------------?????? ?????? ????????? ??????

            LoginVO resultVO = userLoginService.selectFindId(loginVO);

            if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
                loginFlag = true;
                loginMsg = resultVO.getId();
            } else {
                loginFlag = false;
                loginMsg = "???????????? ??????????????? ????????????.";
            }

            resultInfo.setSuccess(loginFlag);
            resultInfo.setMsg(loginMsg);

            model.addAttribute("loginVO", null);
            model.addAttribute("result", resultInfo);
        }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ????????? ?????? ????????? ?????? ??????
     *
     * @param loginVO
     *            - ?????? ????????? ????????? ?????? LoginVO
     * @return result - ??????
     * @exception Exception
     */
    //@RequestMapping(value = "/findIdByCertiResult", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    //@RequestMapping(value = "/findIdByCertiResult")
    @PostMapping(value = "/findIdByCertiResult")
    public String findIdByCertiAction(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request,
            HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> paramMap) throws Exception {

        ResultInfo resultInfo = new ResultInfo();

        Boolean boolSuccess = false;
        String strMessage = "";

        String strBirthDate = String.valueOf(request.getSession().getAttribute("birstDate"));
        log.debug("strBirthDate = " + strBirthDate);

        NiceNamefactDto niceNamefactDto = niceNamefactService.getData(request, "myConfirmCerti_" + strBirthDate);
        if (niceNamefactDto == null) {

            log.debug("---------------------------------------------------------------------------------.S");
            log.debug("niceNamefactDto is null");
            log.debug("---------------------------------------------------------------------------------.E");
            HttpUtility.sendBack(request, response, "???????????? ????????? ????????????.");
            return null;

        } else {

            log.debug("---------------------------------------------------------------------------------.S");
            log.debug("niceNamefactDto = " + niceNamefactDto.toMap());
            log.debug("---------------------------------------------------------------------------------.E");

        }

        String strCrcDataDi = niceNamefactDto.getCrc_data_di();
        String strUserName = niceNamefactDto.getName();

        log.debug("-------------------------------------------.S");
        log.debug("strCrcDataDi = " + strCrcDataDi);
        log.debug("strUserName  = " + strUserName);
        log.debug("-------------------------------------------.E");

        loginVO.setBirthDate(strBirthDate);
        loginVO.setDn(strCrcDataDi);
        loginVO.setName(strUserName);
        LoginVO resultVO = userLoginService.selectFindIdByCerti(loginVO);
        //???????????? ????????????
        //???????????? ???????????? ???????????????...
        String  id = (String)paramMap.get("id");
        String  name = (String)paramMap.get("name");
        String birthDate = (String)paramMap.get("birthDate");
        
        MemberVO memberVo = new MemberVO();
        memberVo.setBirthDate(birthDate);
        memberVo.setMemNm(name);
        memberVo.setPiAuthkey(strCrcDataDi);
        memberVo.setId(id);
        //List<?> resultList = termsService.selectTermsList(termsVO);
        List<?> resultList = userJoinService.findmemberList(memberVo);
        niceNamefactService.clear(request); // ???????????? ???????????? ??????
        

        String strUserId = "";
        if (resultVO == null) {
            boolSuccess = false;
            log.debug("resultVO is null");
        } else {
            strUserId = resultVO.getId();
            boolSuccess = true;
            log.debug("strUserId = " + strUserId);
        }

        strMessage = strUserId;

        resultInfo.setSuccess(boolSuccess);
        resultInfo.setMsg(strMessage);
        model.addAttribute("result", resultInfo);
        model.addAttribute("resultList", resultList);
        System.out.println("getViewUrl :: "+HttpUtility.getViewUrl(request));
        
        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ??????
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findPasswd")
    public String selectUserFindPasswd(@ModelAttribute("memberVO") MemberVO memberVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.info("-------------------------call UserLoginController :: selectUserFindPasswd :: ------------ ");

        RequestUtil requestData = RequestUtil.getInstance(request);
        ModuleConfigVo moduleConfigData = requestData.HiscoModuleConfigInfo();

        if (moduleConfigData == null) {
            log.info("moduleConfigData is null");
        } else {
            log.info("moduleConfigData = " + moduleConfigData.toMap());
        }

        // SiteVo siteData = requestData.KntoolSiteInfo();
        // boolean printSitePath = siteData == null ? true : siteData.UseSitepath();

        StringBuffer next_url = new StringBuffer();

        if (!StringUtil.Equals("/", requestData.getContextPath()) && !StringUtil.IsEmpty(requestData.getContextPath()))
            next_url.append("/").append(requestData.getContextPath());

        //// next_url.append(requestData.KntoolJoinMenuPathEnc(printSitePath));
        next_url.append(ResponseUtil.MakeUrlAction(request, requestData.getParamSubModuleAction(), null, "", null, true, false));

        log.info("next_url.toString() = " + next_url.toString());

        model.addAttribute("next_url", next_url.toString());
        // ******************************************************************************************************************************************************.E.

        log.debug("model = " + model);

        return HttpUtility.getViewUrl(request);
    }
    
    
    @PostMapping(value = "/findPasswdCheck")
    public String findPasswdCheck(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request,
            HttpServletResponse response, ModelMap model, @RequestParam Map<String, Object> paramMap) throws Exception {

        ResultInfo resultInfo = new ResultInfo();

        Boolean boolSuccess = false;
        String strMessage = "";

        String strBirthDate = String.valueOf(request.getSession().getAttribute("birstDate"));
        log.debug("strBirthDate = " + strBirthDate);

        NiceNamefactDto niceNamefactDto = niceNamefactService.getData(request, "myConfirmCerti_" + strBirthDate);
        if (niceNamefactDto == null) {

            log.debug("---------------------------------------------------------------------------------.S");
            log.debug("niceNamefactDto is null");
            log.debug("---------------------------------------------------------------------------------.E");
            HttpUtility.sendBack(request, response, "???????????? ????????? ????????????.");
            return null;

        } else {

            log.debug("---------------------------------------------------------------------------------.S");
            log.debug("niceNamefactDto = " + niceNamefactDto.toMap());
            log.debug("---------------------------------------------------------------------------------.E");

        }

        String strCrcDataDi = niceNamefactDto.getCrc_data_di();
        String strUserName = niceNamefactDto.getName();

        log.debug("-------------------------------------------.S");
        log.debug("strCrcDataDi = " + strCrcDataDi);
        log.debug("strUserName  = " + strUserName);
        log.debug("-------------------------------------------.E");

        loginVO.setBirthDate(strBirthDate);
        loginVO.setDn(strCrcDataDi);
        loginVO.setName(strUserName);
        LoginVO resultVO = userLoginService.selectFindIdByCerti(loginVO);
        //???????????? ????????????
        //???????????? ???????????? ???????????????...
        String  id = (String)paramMap.get("id");
        String  name = (String)paramMap.get("name");
        String birthDate = (String)paramMap.get("birthDate");
        
        MemberVO memberVo = new MemberVO();
        memberVo.setBirthDate(birthDate);
        memberVo.setMemNm(name);
        memberVo.setPiAuthkey(strCrcDataDi);
        memberVo.setId(id);
        List<?> resultList = userJoinService.findmemberList(memberVo);
        
        

        String strUserId = "";
        if (resultVO == null) {
            boolSuccess = false;
            log.debug("resultVO is null");
        } else {
            strUserId = resultVO.getId();
            boolSuccess = true;
            log.debug("strUserId = " + strUserId);
        }

        strMessage = strUserId;

        resultInfo.setSuccess(boolSuccess);
        resultInfo.setMsg(strMessage);
        model.addAttribute("result", resultInfo);
        model.addAttribute("resultList", resultList);
        System.out.println("getViewUrl :: "+HttpUtility.getViewUrl(request));
        
        if(resultList == null || resultList.size() <=0) {
        	HttpUtility.sendBack(request, response, "????????? ????????? ???????????? ??????????????? ????????????.");
        	return null;
        }else {
        	model.addAttribute("id", ((MemberVO)resultList.get(0)).getId());
        	model.addAttribute("memberVo", (MemberVO)resultList.get(0));
        	model.addAttribute("strCrcDataDi", strCrcDataDi);
        	return "/web/member/findPasswdSet";
        }
        
        
       
        
        //return "/web/member/findPasswd";
    }
    
    

    /**
     * ?????? ??????
     *
     * @param loginVO
     *            - ?????? ????????? ????????? ?????? memberVO
     * @return result - ??????
     * @exception Exception
     *//*
    @PostMapping(value = "findPasswdCheck")
    @ResponseBody
    public ModelAndView findPasswdCheck(@ModelAttribute("memberVO") MemberVO memberVO, HttpServletRequest request,
            ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        memberVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        MemberVO member = userJoinService.selectFindPasswd(memberVO);
        if (member != null) {
            resultInfo.setSuccess(true);
        } else {
            resultInfo.setSuccess(false);
        }

        mav.addObject("memberVO", null);
        mav.addObject("result", resultInfo);

        return mav;
    }*/

    /**
     * ???????????? ????????? ?????????
     *
     * @param model
     * @return
     * @throws Exception
     */
    //@RequestMapping(value = "/findPasswdSet", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @PostMapping(value = "findPasswdSet")
    public String selecFindPasswdSet(@ModelAttribute("memberVO") MemberVO memberVO,@ModelAttribute("loginVO") LoginVO loginVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.debug("call selecFindPasswdSet()");

        String strBirthDate = String.valueOf(request.getSession().getAttribute("birstDate"));
        log.debug("strBirthDate = " + strBirthDate);

        NiceNamefactDto niceNamefactDto = niceNamefactService.getData(request, "myConfirmCerti_" + strBirthDate);
        if (niceNamefactDto == null) {

            log.debug("---------------------------------------------------------------------------------.S");
            log.debug("niceNamefactDto is null");
            log.debug("---------------------------------------------------------------------------------.E");

            HttpUtility.sendBack(request, response, "???????????? ????????? ????????????.");
            return null;

        } else {

            log.debug("---------------------------------------------------------------------------------.S");
            log.debug("niceNamefactDto = " + niceNamefactDto.toMap());
            log.debug("---------------------------------------------------------------------------------.E");

        }

        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        String strBirthDay = format1.format(niceNamefactDto.getBirthday());

        log.debug("strBirthDay = " + strBirthDay);

        model.addAttribute("birthDay", strBirthDay);
        model.addAttribute("niceNamefactDto", niceNamefactDto);

        log.debug("model = " + model);
        
        
        
        int cnt = 0;
        Map<String, Object> param = commandMap.getParam();
        
        String id = (String)param.get("id") +"";
        String pw1 = (String)param.get("pw1") +"";
        String pw2 = (String)param.get("pw2") + "";
        String password = (String)param.get("password") + "";
        
       String strIpAdress = commandMap.getIp();
       
       if(!pw1.equals(pw2)) {
    	   HttpUtility.sendBack(request, response, "????????? ??????????????? ????????????????????? ???????????? ????????????.");
       }else {
    	
        	/*loginVO.setId( id  );
            loginVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
            loginVO.setStatus(loginUser.getStatus());
            
            LoginVO resultVO = userLoginService.actionLogin(loginVO);*/
            
            // ?????? ???????????? ??????
            LoginVO newLoginVo = new LoginVO();
        	newLoginVo.setId(loginVO.getId());
        	newLoginVo.setPassword(password);
        	String loginMsg = userLoginService.myInfoPasswordCheck(newLoginVo, "N");
            
            if (loginMsg.equals("E") || loginMsg.equals("sleep") || loginMsg.equals("agree") || loginMsg.equals("mustChangePw")) {
            	log.debug("passwdCheck Pass ()!!!");
            }else {
            	//?????? ???????????? ?????? ?????????
            	log.debug("passwdCheck Fail ()!!!");
            }
            
            
                loginVO.setUniqId(newLoginVo.getUniqId());
                loginVO.setId(id);
                loginVO.setIp(strIpAdress);
                loginVO.setStatus(newLoginVo.getStatus());

                //String enpassword = EgovFileScrty.encryptPassword(commandMap.getString("new_password"), EgovStringUtil.isNullToString(loginVO.getId()));
                String enpassword = userLoginService.passwordEncryption(pw1,newLoginVo);
                loginVO.setPassword(enpassword);

                cnt = myInforService.updateMemberPassword(loginVO);
            }
       
       niceNamefactService.clear(request); // ???????????? ???????????? ??????
        if (cnt > 0) {
        	String showMsg = "??????????????? ?????????????????????. ?????? ?????????????????????";
            HttpUtility.sendRedirect(request, response, "??????????????? ?????????????????????. ?????? ?????????????????????", Config.USER_ROOT + "/member/login");
            return null;
        } else {
            HttpUtility.sendBack(request, response, "??????????????? ????????? ??????????????? ???????????? ???????????????.");
            return null;
        }
    }

    /**
     * ???????????? ?????? ??????
     *
     * @param memberVO
     * @return
     * @exception Exception
     */
    @PostMapping(value = "/findPasswdSave")
    public void updateChangePasswdSave(@ModelAttribute("loginVO") LoginVO loginVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model,
            @RequestParam Map<String, Object> paramMap) throws Exception {

        // NamefactVO namefactData = tossNamefactService.getData(request, "pwchange_"+commandMap.getString("id") ); =>
        // old

        String strBirthDate = String.valueOf(request.getSession().getAttribute("birstDate"));
        log.debug("strBirthDate = " + strBirthDate);

        String strIpAdress = commandMap.getIp();

        NiceNamefactDto niceNamefactDto = niceNamefactService.getData(request, "myConfirmCerti_" + strBirthDate);

        int cnt = 0;

        log.debug("niceNamefactDto = " + niceNamefactDto);

        if (niceNamefactDto == null) {
            HttpUtility.sendBack(request, response, "???????????? ??? ??????????????? ????????? ?????? ??? ????????????.");
            return;

        } else {

            paramMap.put("crc_data_di", niceNamefactDto.getCrc_data_di());

            log.debug("final paramMap = " + paramMap);

            MemberVO member = myInforService.selectMemberSearchNewById(paramMap);

            if (member != null) {
                loginVO.setUniqId(member.getMemNo());
                loginVO.setId(member.getId());
                loginVO.setIp(strIpAdress);
                loginVO.setStatus(member.getStatus());

                String enpassword = EgovFileScrty.encryptPassword(commandMap.getString("new_password"), EgovStringUtil.isNullToString(loginVO.getId()));
                loginVO.setPassword(enpassword);

                cnt = myInforService.updateMemberPassword(loginVO);
            } else {
                HttpUtility.sendBack(request, response, "???????????? ????????? ???????????? ????????????.");
                return;
            }
        }

        if (cnt > 0) {
            HttpUtility.sendRedirect(request, response, "??????????????? ?????????????????????.", Config.USER_ROOT + "/member/findPwByCertiResult");
        } else {
            HttpUtility.sendBack(request, response, "?????????????????? ????????????. ???????????? ?????? ?????? ????????? ?????????.");
        }
    }

    /**
     * ???????????? ??? ?????? ?????? ????????????
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/loginCertCheck")
    public String loginCertCheck(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @ModelAttribute("memberVO") MemberVO memberVO) throws Exception {

        /*
         * JYS ??????
         * NiceNamefactVO namefactData = tossNamefactService.getCertResult(commandMap.getString("LGD_MID"),
         * commandMap.getString("LGD_AUTHONLYKEY"), commandMap.getString("LGD_PAYTYPE"));
         * String currentDomain = EgovProperties.getProperty("Globals.Domain");
         * model.addAttribute("userLoginInfo", commandMap.getUserInfo());
         * model.addAttribute("currentDomain", currentDomain);
         * model.addAttribute("kakaoKey", EgovProperties.getProperty("SNS.kakaoKey"));
         * model.addAttribute("naverKey", EgovProperties.getProperty("SNS.naverKey"));
         * model.addAttribute("googleKey", EgovProperties.getProperty("SNS.googleKey"));
         * String returnURL = commandMap.getString("returnURL");
         * if (returnURL == null || returnURL.equals("") || returnURL.indexOf("/member/") > 0 ||
         * returnURL.indexOf("/mypage/") > 0) {
         * returnURL = Config.USER_ROOT + "/main";
         * }
         * model.addAttribute("loginReturnURL", returnURL);
         * if (namefactData == null ) {
         * model.addAttribute("alertMsg", "???????????? ????????? ????????????." );
         * return HttpUtility.getViewUrl(Config.USER_ROOT, "/member/login");
         * } else if (!"0000".equals(namefactData.getRespCode())) {
         * model.addAttribute("alertMsg", namefactData.getRespMsg());
         * model.addAttribute("NONMEMBER_YN", "Y");
         * return HttpUtility.getViewUrl(Config.USER_ROOT, "/member/login");
         * } else {
         * //???????????? ????????? ??????
         * // namefactService.saveData(request, type, namefactData);
         * LoginVO resultVO = new LoginVO();// ?????????
         * resultVO.setName(namefactData.getName());
         * resultVO.setIhidNum(namefactData.getTel());
         * resultVO.setHpcertno(namefactData.getCrc_data_di());
         * resultVO.setId("NONMEMBER");
         * resultVO.setGender(namefactData.getSex().equals("M")?"1":"2"); // ??????
         * HttpSession session = request.getSession();
         * UserSession userSession = new UserSession();
         * userSession.setUserInfo(resultVO);
         * session.setAttribute(Config.USER_SESSION, userSession);
         * HttpUtility.sendRedirect(request, response, "" , returnURL);
         * return null;
         * }
         */

        return null;

    }

    /**
     * ???????????? ?????? ?????????
     *
     * @param memberVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/loginSleep")
    public String loginSleep(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {
        HttpSession session = request.getSession();
        LoginVO loginVO = (LoginVO) session.getAttribute("SLEEP_USER");

        model.addAttribute("loginVO", loginVO);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ??????????????? ?????????????????? ?????? ?????????
     *
     * @param memberVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/passwdChange", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    public String passwdChange(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {

        HttpSession session = request.getSession();
        LoginVO loginVO = (LoginVO) session.getAttribute("PASSWORD_CHANGE");

        model.addAttribute("loginVO", loginVO);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ????????? ?????? ??????
     *
     * @param vo
     *            - ?????????, ??????????????? ?????? LoginVO
     * @param request
     *            - ??????????????? ?????? HttpServletRequest
     * @exception Exception
     */
    @PostMapping(value = "sleepAction")
    @ResponseBody
    public ModelAndView sleepAction(CommandMap commandMap, @ModelAttribute("loginVO") LoginVO loginVO,
            HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        String strIpAdress = commandMap.getIp();

        loginVO.setIp(strIpAdress);
        userLoginService.updateMemberWakeup(loginVO);

        resultInfo.setSuccess(true);

        mav.addObject("loginVO", null);
        mav.addObject("result", resultInfo);

        return mav;
    }

    /**
     * ???????????? ?????? ?????????
     *
     * @param memberVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/loginSleepResult")
    public String loginSleepResult(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @ModelAttribute("loginVO") LoginVO loginVO) throws Exception {
        HttpSession session = request.getSession();
        LoginVO resultVO = (LoginVO) session.getAttribute("SLEEP_USER");

        if (resultVO != null) {
            // 3-1. ????????? ????????? ????????? ??????
            resultVO.setStatus("0000");
            UserSession userSession = new UserSession();
            userSession.setUserInfo(resultVO);
            session.setAttribute(Config.USER_SESSION, userSession);
            session.removeAttribute("SLEEP_USER");
        } else {
            resultVO = commandMap.getUserInfo();
        }

        model.addAttribute("resultVO", resultVO);
        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ?????? ????????? ?????????
     *
     * @param termsVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/loginReagree")
    public String loginSleepResult(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, TermsVO termsVO) throws Exception {

        model.addAttribute("termsList", userJoinService.selectTermsList(termsVO));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ???????????? ????????????
     *
     * @param vo
     *            - ?????????, ??????????????? ?????? LoginVO
     * @param request
     *            - ??????????????? ?????? HttpServletRequest
     * @exception Exception
     */
    @PostMapping(value = "loginReagreeSave")
    @ResponseBody
    public ModelAndView loginReagreeSave(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();
        LoginVO loginVO = commandMap.getUserInfo();

        if (loginVO == null) {
            resultInfo.setSuccess(false);
            resultInfo.setMsg("????????? ??? ????????? ?????????.");
        } else {
            myInforService.updateMemberRegree(loginVO);
            resultInfo.setSuccess(true);
        }

        mav.addObject("loginVO", null);
        mav.addObject("result", resultInfo);

        return mav;
    }

    // ?????? ?????? ??????
    private ErrorMessageInfo MODULE_PRE_LOAD(HttpServletRequest request) throws Exception {

        log.debug("call FmcsMemberController() :: ErrorMessageInfo() :: ***********************************************************************************");

        ErrorMessageInfo ret = new ErrorMessageInfo();
        RequestUtil requestData = RequestUtil.getInstance(request);

        // ?????? ?????? ??????
        ModuleConfigVo moduleConfigData = requestData.HiscoModuleConfigInfo();

        if (moduleConfigData == null) {

            if (requestData.getParamLong("module_config_srl") > 0) {
                moduleConfigData = niceNamefactService.CacheSelect(requestData.getParamLong("module_config_srl"));
            }

            if (moduleConfigData != null) {
                requestData.setKntoolModuleConfigInfo(moduleConfigData);
            }
        }

        if (moduleConfigData == null) {
            ret.setError(true);
            ret.setMessage("?????? ?????? ?????? ????????? ?????? ??? ????????????.");
            ret.setUrl("forward:/Error");

            request.setAttribute("error_title", "?????? ?????? ?????? ??????");
            request.setAttribute("error_message", ret.getMessage());

            return ret;
        }

        request.setAttribute("ModuleConfigData", moduleConfigData);

        return ret;
    }

    /**
     * ???????????? ?????? ??????
     *
     * @param memberVO
     * @return
     * @exception Exception
     */
    @PostMapping(value = "/changePasswdSave")
    public void changePasswdSave(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @RequestParam Map<String, Object> paramMap) throws Exception {

        LoginVO loginVO = (LoginVO) request.getSession().getAttribute("PASSWORD_CHANGE");

        String strIpAdress = commandMap.getIp();

        log.debug("Old Password : " + loginVO.getPassword());

        String strMemNo = loginVO.getUniqId();
        String strUserId = loginVO.getId();
        String strCurrentPass = loginVO.getPassword();

        String strOldPassword = String.valueOf(paramMap.get("confirm_password"));
        String strNewPassword = String.valueOf(paramMap.get("new_password"));

        String strOldPasswordEnc = EgovFileScrty.encryptPassword(strOldPassword, EgovStringUtil.isNullToString(strUserId));
        String strNewPasswordEnc = EgovFileScrty.encryptPassword(strNewPassword, EgovStringUtil.isNullToString(strUserId));

        log.debug("strCurrentPass    = " + strCurrentPass);
        log.debug("strOldPasswordEnc = " + strOldPasswordEnc);
        log.debug("strNewPasswordEnc = " + strNewPasswordEnc);

        if (!strCurrentPass.equals(strOldPasswordEnc)) {
            HttpUtility.sendBack(request, response, "?????? ??????????????? ???????????? ????????????.");
            return;
        }

        int intCnt = 0;

        paramMap.put("memNo", strMemNo);
        MemberVO member = myInforService.selectMemberSearchByMemNo(paramMap);
        if (member != null) {
            loginVO.setUniqId(member.getMemNo());
            loginVO.setId(member.getId());
            loginVO.setIp(strIpAdress);
            loginVO.setStatus(member.getStatus());

            String enpassword = EgovFileScrty.encryptPassword(commandMap.getString("new_password"), EgovStringUtil.isNullToString(loginVO.getId()));
            loginVO.setPassword(enpassword);

            intCnt = myInforService.updateMemberPassword(loginVO);

        } else {
            HttpUtility.sendBack(request, response, "????????? ?????? ????????? ?????? ??? ????????????.");
            return;
        }

        if (intCnt > 0) {
            HttpUtility.sendRedirect(request, response, "??????????????? ?????????????????????.", "/");
        } else {
            HttpUtility.sendBack(request, response, "?????? ???????????? ????????????. ?????? ?????? ???????????? ????????? ??????????????????.");
        }

    }
    
}