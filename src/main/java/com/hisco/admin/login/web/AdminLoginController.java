package com.hisco.admin.login.web;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.manager.service.SysUserService;
import com.hisco.admin.manager.vo.SysUserIpVO;
import com.hisco.admin.manager.vo.SysUserVO;
import com.hisco.admin.menu.util.SiteGubun;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.config.DynamicConfig;
import com.hisco.cmm.config.DynamicConfigUtil;
import com.hisco.cmm.modules.DateUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.config.EgovLoginConfig;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sec.ram.service.AuthorManageVO;
import egovframework.com.sec.ram.service.EgovAuthorManageService;
import egovframework.com.sym.mnu.mpm.service.EgovMenuManageService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 관리자 로그인 관련 controller
 *
 * @author 진수진
 * @since 2020.07.14
 * @version 1.0, 2020.07.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.14 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class AdminLoginController {

    /** EgovLoginService */
    //// @Resource(name = "loginService")
    //// private EgovLoginService loginService;

    /** EgovMessageSource */
    @Resource(name = "egovMessageSource")
    EgovMessageSource egovMessageSource;

    @Resource(name = "egovLoginConfig")
    EgovLoginConfig egovLoginConfig;

    /** EgovMenuManageService */
    @Resource(name = "meunManageService")
    private EgovMenuManageService menuManageService;

    @Resource(name = "sysUserService")
    private SysUserService sysUserService;

    @Resource(name = "egovAuthorManageService")
    private EgovAuthorManageService egovAuthorManageService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Value("#{dynamicConfig.managerRoot}")
    private String managerRoot;

    @Value("#{dynamicConfig.adminRoot}")
    private String adminRoot;


    @Resource(name = "dynamicConfig")
    private DynamicConfig config;

    @Resource(name = "dynamicConfigUtil")
    private DynamicConfigUtil configUtil;

    @GetMapping({ "", "/" })
    public String index(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) throws Exception {

        return "redirect:" + configUtil.getAdminPath(request) + "/login";
    }

    @GetMapping({ "/login" })
    public String login(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request,
            HttpServletResponse response, ModelMap model, CommandMap commandMap) throws Exception {
        log.debug("CONTEXT_PATH=" + request.getContextPath());
        String fmtTime = DateUtil.printDatetime(Calendar.getInstance().getTime(), "yyyy년 MM월dd일 HH시mm분", Locale.KOREA);

        // 권한체크시 에러 페이지 이동
        String authError = request.getParameter("auth_error") == null
                ? "" : (String) request.getParameter("auth_error");
        // if (authError != null && authError.equals("1")) {
        if ("1".equals(authError)) {
            return Config.ADMIN_ROOT + "/accessDenied";
        }

        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if (isAuthenticated) {
            // 이미 로그인 상태인경우
            return "redirect:" + configUtil.getAdminPath(request) + "/actionMain";
        }
        String message = (String) request.getSession().getAttribute("errorMessage");
        log.debug("message=" + message);
        if (message != null) {
            model.addAttribute("message", message);
            request.getSession().removeAttribute("errorMessage");
        }

        model.addAttribute("isManager", configUtil.isAdminEX(request));
        model.addAttribute("managerRoot", managerRoot);
        model.addAttribute("userType", configUtil.getAdminPath(request).equals(managerRoot) ? "2001" : "1001");// 외부관리자 는 2001

        model.addAttribute("sysNow", fmtTime);
        return Config.ADMIN_ROOT + "/login";
    }

    /**
     * 로그인 후 메인화면으로 들어간다
     *
     * @param
     * @return 로그인 페이지
     * @exception Exception
     */
    @GetMapping("actionMain")
    public String actionMain(ModelMap model, HttpServletRequest request) throws Exception {

        log.debug("call " + configUtil.getAdminPath(request) + " actionMain();");

        // 1. Spring Security 사용자권한 처리
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if (!isAuthenticated) {
            // model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
            return "redirect:" + configUtil.getAdminPath(request) + "/login";
        }
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        log.debug("User Id : {}", user == null ? "" : EgovStringUtil.isNullToString(user.getId()));

        String firstMenuUrl = "";

        MenuManageVO vo = new MenuManageVO();
         vo.setSiteGubun(configUtil.getAdminPath(request).equals(config.getAdminRoot()) ? SiteGubun.ADMIN : SiteGubun.MANAGER);
        //vo.setSiteGubun(SiteGubun.ADMIN);
        vo.setTmpId(user.getUniqId());
        vo.setGroupId(user.getGroupId()); // 관리권한 그룹( 슈퍼관리자는 0 그 외는 1)
        vo.setFrontGnbmenuyn(configUtil.getAdminPath(request).equals(managerRoot) ? "N" : ""); // 외부관리자 는 N 인 것만/ 내부관리자

        try {
            List<MenuManageVO> menuList = (List<MenuManageVO>) menuManageService.selectMenuListSite(vo);
            if (menuList != null) {
                for (MenuManageVO menu : menuList) {
                    if (firstMenuUrl == null || firstMenuUrl.equals("")) {
                        firstMenuUrl = configUtil.getAdminDynamicPath(request, menu.getMenuUrl());
                    }
                }
            }
        } catch (ClassCastException e) {
            log.error("List<MenuManageVO> cast exception");
        }
        /* 관리자 설정된 메뉴의 첫번째로 이동 */
        /*
         * if (!firstMenuUrl.startsWith(adminRoot)) {
         * firstMenuUrl.replaceFirst(Config.ADMIN_ROOT, adminRoot);
         * }
         */
        return "redirect:" + firstMenuUrl;

        /*
         * if (main_page != null && !main_page.equals("")) {
         * // 3-1. 설정된 메인화면이 있는 경우
         * return main_page;
         * } else {
         * // 3-2. 설정된 메인화면이 없는 경우
         * if (user.getUserSe().equals("USR")) {
         * return "egovframework/com/EgovMainView";
         * } else {
         * return "egovframework/com/EgovMainViewG";
         * }
         * }
         */
    }

    /**
     * 로그아웃한다.
     *
     * @return String
     * @exception Exception
     */
    @GetMapping(value = "actionLogout")
    public void actionLogout(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        log.debug("call actionLogout()");

        request.getSession().removeAttribute(com.hisco.cmm.util.Config.USER_SESSION);
        request.getSession().invalidate();
        log.debug("REDIRECT TO " + configUtil.getAdminPath(request) + "/login");
        response.sendRedirect(configUtil.getAdminPath(request, true) + "/login");
    }

    /**
     * 권한 없음 페이지
     *
     * @param
     * @return 권한 없음 페이지
     * @exception Exception
     */
    @PostMapping("accessDenied")
    public String accessDenied(ModelMap model) throws Exception {

        return Config.ADMIN_ROOT + "/accessDenied";

    }

    /**
     * 권한 없음 페이지
     *
     * @param
     * @return 권한 없음 페이지
     * @exception Exception
     */
    @GetMapping("accessDenied")
    public String accessGetDenied(ModelMap model) throws Exception {

        return Config.ADMIN_ROOT + "/accessDenied";

    }

    @PostMapping("/egov_security_logout")
    public String egov_security_logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.debug("call EgovComUtlController :: egov_security_logout()");

        ((HttpServletRequest) request).getSession().setAttribute(Config.USER_SESSION, null);

        // HttpUtility.sendRedirect(request, response, "", request.getContextPath() + adminRoot + "/actionLogout");
        HttpUtility.sendRedirect(request, response, "", configUtil.getAdminPath(request) + "/actionLogout");
        return null;

    }

    /**
     * 디자인 가이드
     */
    @GetMapping(value = "/design/guide")
    public String designGuide( CommandMap commandMap,HttpServletRequest request, ModelMap model) throws Exception {
        return HttpUtility.getViewUrl(request);
    }

    /**
     * 내정보 관리
     */
    @GetMapping(value = "/myinfo/detail")
    public String myinfo(@ModelAttribute("sysUserVO") SysUserVO sysUserVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

    	// 수정 화면
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        sysUserVO.setUserId(user.getId());
        SysUserVO userVO = sysUserService.selectSysUserDetail(sysUserVO);

        if (userVO.getUseYn() == null) {
            userVO.setUseYn("Y");
        }
        if (userVO.getAcntStats() == null) {
            userVO.setAcntStats("1000");
        }

        if (userVO.getUseYn().equals("N")) {
            userVO.setAcntStats("N");
        }
        sysUserVO = userVO;

        // 관리자유형
        model.addAttribute("userType", codeService.selectCodeList("SM_ADMINUSER_TYPE"));
        // 관리 기관 목록
        model.addAttribute("orgList", sysUserService.selectMstOrgList(userVO));

        // 접속 제한 IP 목록
        model.addAttribute("ipList", sysUserService.selectSysUserIpList(userVO));

        // 관리권한 목록
        AuthorManageVO authorVo = new AuthorManageVO();
        authorVo.setSearchCondition("3"); // 권한 부모 코드가 ROLE_ADMIN 인것만...
        List<?> authorList = egovAuthorManageService.selectAuthorList(authorVo);
        model.addAttribute("authorList", authorList);
        model.addAttribute("sysUserVO", sysUserVO);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/myinfo");
    }

    /**
     * 관리자 계정을 수정한다.
     *
     * @param sysUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/myinfo/update.json")
    @PageActionInfo(title = "관리자 계정 수정", action = "U", ajax = true)
    @ResponseBody
    public ModelAndView updateSysUser(@ModelAttribute("sysUserVO") SysUserVO sysUserVO, HttpServletRequest request,
            CommandMap commandMap,
            ModelMap model) throws Exception {

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        sysUserVO.setUserId(user.getId()); //로그인한 아이디로 셋팅
        sysUserVO.setComcd(Config.COM_CD);
        sysUserVO.setIp(commandMap.getIp());

        int n = sysUserService.updateMyInfo(sysUserVO);

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        if (n > 0) {
            resultInfo = HttpUtility.getSuccessResultInfo(commandMap.getString("userId"));
        } else {
            resultInfo = HttpUtility.getErrorResultInfo("수정에 실패하였습니다");
        }

        mav.clear();
        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 비밀번호 변경
     *
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/myinfo/passwdUpdt.json")
    @PageActionInfo(title = "관리자 비밀번호 변경", action = "U", ajax = true)
    @ResponseBody
    public ModelAndView updateSysUserPassword(@ModelAttribute("sysUserVO") SysUserVO sysUserVO, CommandMap commandMap,
            ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        sysUserVO.setUserId(user.getId()); //로그인한 아이디로 셋팅
        sysUserVO.setComcd(Config.COM_CD);
        sysUserVO.setIp(commandMap.getIp());
        //sysUserVO.setPassword(commandMap.getString("new_passwd"));

        String raw = commandMap.getString("new_passwd");
        
        String hex = "";
		
		// "SHA1PRNG"은 알고리즘 이름
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);
		// SALT 생성
		String salt = new String(Base64.getEncoder().encode(bytes));
		String rawAndSalt = raw+salt;
		
		System.out.println("raw : "+raw);
		System.out.println("salt : "+salt);
		
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		// 평문 암호화
		md.update(raw.getBytes());
		hex = String.format("%064x", new BigInteger(1, md.digest()));
		System.out.println("raw의 해시값 : "+hex);
		
		// 평문+salt 암호화
		md.update(rawAndSalt.getBytes());
		hex = String.format("%064x", new BigInteger(1, md.digest()));
		System.out.println("raw+salt의 해시값 : "+hex);
		
		//String password = EgovFileScrty.getSHA512(vo.getPassword());
		//vo.setPassword(password);
		sysUserVO.setUserSalt(salt);
		sysUserVO.setPassword(hex);
        
        
        if (commandMap.getString("new_passwd").equals("") || commandMap.getString("new_passwd2").equals("") || commandMap.getString("userId").equals("")) {
            resultInfo = HttpUtility.getErrorResultInfo("값이 충분하지 않습니다.");
        } else {
            // 수정
            sysUserService.updateSysUserPassword(sysUserVO);
            resultInfo = HttpUtility.getSuccessResultInfo("비밀번호가 변경 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

}
