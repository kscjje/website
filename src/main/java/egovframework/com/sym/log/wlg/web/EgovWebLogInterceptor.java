package egovframework.com.sym.log.wlg.web;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.hisco.admin.manager.service.SysUserService;
import com.hisco.cmm.config.DynamicConfigUtil;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.RedirectUtil;
import com.hisco.cmm.util.SessionUtil;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.log.wlg.service.EgovWebLogService;
import egovframework.com.sym.log.wlg.service.WebLog;
import egovframework.com.sym.mnu.mpm.service.EgovMenuManageService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : EgovWebLogInterceptor.java
 * @Description : 웹로그 생성을 위한 인터셉터 클래스
 * @Modification Information
 *               수정일 수정자 수정내용 ------- ------- ------------------- 2009. 3. 9.
 *               이삼섭 최초생성 2011. 7. 1. 이기하 패키지 분리(sym.log -> sym.log.wlg) 2020.
 *               7. 1. 진수진 메뉴코드 추가
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 9.
 * @version
 * @see
 */
@Slf4j
public class EgovWebLogInterceptor extends HandlerInterceptorAdapter {

    @Resource(name = "EgovWebLogService")
    private EgovWebLogService webLogService;

    /** EgovMenuManageService */
    @Resource(name = "meunManageService")
    private EgovMenuManageService menuManageService;

    @Resource(name = "sysUserService")
    private SysUserService sysUserService;

    @Resource(name = "dynamicConfigUtil")
    private DynamicConfigUtil configUtil;

    @Value("#{dynamicConfig.managerRoot}")
    private String managerRoot;

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        log.info("EgovWebLogInterceptor :: preHandle");

        String contextPath = request.getContextPath();
        String strReqURL = request.getRequestURI();
        if (contextPath.length() > 1)
            strReqURL = strReqURL.replaceFirst(contextPath, "");

        String strUniqId = "";
        long llMenuNo = 0;
        int groupId = -1;

        // 모바일 여부 체크
        Device device = (Device) request.getAttribute("currentDevice");
        if (device == null) {
            device = DeviceUtils.getCurrentDevice(request);
        } else {
            request.setAttribute("IS_MOBILE", device.isMobile());
            // log.debug("=============currentDevice=====>" + device.isMobile());
        }

        log.debug("Config.ADMIN_ROOT = " + Config.ADMIN_ROOT);
        log.debug("Config.USER_ROOT  = " + Config.USER_ROOT);
        log.debug("strReqURL         = " + strReqURL);

        boolean isAdmin = configUtil.isAdmin(strReqURL);
        boolean isUser = configUtil.isUser(strReqURL);

        // 사용자도 관리자도 아닌 경우 패스
        if (!isAdmin && !isUser) {

            log.debug("사용자도 관리자도 아닌 경우 패스");
            return true;

        }

        // 리턴 URL 셋팅
        HttpRequest httpRequest = new ServletServerHttpRequest(request);// http 인지 https 인지 확인
        UriComponents uriComponents = UriComponentsBuilder.fromHttpRequest(httpRequest).build();
        String strScheme = uriComponents.getScheme();
        String strReturnURL = uriComponents.toString();

        /* Authenticated */
        HttpSession session = request.getSession();
        UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
        if (userSession == null) {
            userSession = new UserSession();
        }

        if (strReturnURL.startsWith("http://")) {
            strReturnURL = strReturnURL.replace("http://", "");
            strReturnURL = strReturnURL.substring(strReturnURL.indexOf("/"));
        } else if (strReturnURL.startsWith("https://")) {
            strReturnURL = strReturnURL.replace("https://", "");
            strReturnURL = strReturnURL.substring(strReturnURL.indexOf("/"));
        }

        log.debug("strReturnURL = " + strReturnURL);
        log.debug("LoginVO = {}", EgovUserDetailsHelper.getAuthenticatedUser());

        // ------------------------------------------------------------관리자와 일반사용자 세션 공유 가능 여부 검사------------------.S
        //// log.debug("관리자와 일반사용자 세션 공유 가능 여부 검사-------------------------시작");
        if (EgovUserDetailsHelper.getAuthenticatedUser() != null) {

            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

            //// log.debug("EgovUserDetailsHelper.getAuthenticatedUser() => getThisObjAllValue = " +
            //// user.getThisObjAllValue());

            strUniqId = (user == null || user.getUniqId() == null) ? "" : user.getUniqId();
            if (user != null)
                groupId = user.getGroupId();

        }
        if (userSession != null) {
            LoginVO user = userSession.getUserInfo();
            strUniqId = (user == null || user.getUniqId() == null) ? "" : user.getUniqId();

            //// log.debug("strReturnURL = " + strReturnURL);
            //// log.debug("일반사용자, strUniqId = " + strUniqId);
        }
        //// log.debug("관리자와 일반사용자 세션 공유 가능 여부 검사-------------------------종료");
        // ------------------------------------------------------------관리자와 일반사용자 세션 공유 가능 여부 검사------------------.E

        // 관리자 세션
        if (isAdmin) {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            strUniqId = (user == null || user.getUniqId() == null) ? "" : user.getUniqId();
            if (user != null)
                groupId = user.getGroupId();

        } else {
            // 사용자 세션
            LoginVO user = userSession.getUserInfo();
            strUniqId = (user == null || user.getUniqId() == null) ? "" : user.getUniqId();

            // 마이페이지에서 로그인 여부 체크
            if (strReqURL.indexOf("/myRsvnListSave") > 0 || strReqURL.indexOf("/myRsvnResult") > 0) {
                // 결제 저장페이지이므로 여기는 패스
                strReturnURL = "";
            } else if ((strReqURL.indexOf("/myRsvn/") > 0 && user == null) || (strReqURL.indexOf("/myRsvn/") < 0 && strReqURL.indexOf("/mypage/") > 0 && strUniqId.equals(""))) {
                if (strReturnURL.endsWith("Save")) {
                    strReturnURL = strReturnURL.substring(0, strReturnURL.length() - 4);
                }
                if (strReturnURL.endsWith("Action")) {
                    strReturnURL = strReturnURL.substring(0, strReturnURL.length() - 6);
                }
                
                response.sendRedirect(contextPath + Config.USER_ROOT + "/loginmsg2?returnURL=" + URLEncoder.encode(strReturnURL, "UTF-8"));
    			
                return false;
            }

        }

        if (strReturnURL.indexOf(Config.USER_ROOT + "/member/") >= 0 || request.getMethod().equals("POST")) {
            strReturnURL = "";
        }

        request.setAttribute("returnURL", URLEncoder.encode(strReturnURL, "UTF-8"));

        List<MenuManageVO> menuList = isAdmin
                ? userSession.getAdminMenuList() : userSession.getUserMenuList();

        if (menuList == null) {
            log.debug("menuList is null");
        } else {
            log.debug("menuList Size = " + menuList.size());
        }

        log.debug("menuList = {}", menuList);
        log.debug("groupId = {}", groupId);

        if ((menuList == null || menuList.size() < 1) && ((groupId >= 0 && isAdmin) || !isAdmin)) {

            MenuManageVO vo = new MenuManageVO();
            vo.setSiteGubun(isAdmin ? "ADMIN" : "USER");
            vo.setTmpId(strUniqId);
            vo.setGroupId(groupId);
            if (isAdmin) {
                vo.setFrontGnbmenuyn(configUtil.getAdminPath(request).equals(managerRoot) ? "N" : ""); // 외부관리자 는 N 인것만/
                                                                                                       // 내부관리자
            }

            try {
                log.debug("menuList = {}", menuList);
                menuList = (List<MenuManageVO>) menuManageService.selectMenuListSite(vo);

                if (isAdmin) {
                    userSession.setAdminMenuList(menuList);
                    // 할당된 기업 목록 추가
                    if (groupId > 0) {
                    	LoginVO adminInfo = userSession.getAdminInfo();
                    	if(adminInfo != null && !adminInfo.getAuthorCode().contentEquals("ROLE_SUPER")) {
                    		userSession.setMyOrgList(sysUserService.selectMyOrgList(Config.COM_CD, strUniqId.replaceAll(Config.COM_CD + "_", "")));	
                    	}
                    }

                } else {
                    userSession.setUserMenuList(menuList);
                }

            } catch (ClassCastException e) {
                log.error("List<MenuManageVO> cast exception");
            }
        }
        // 관리자는 Config.ADMIN_ROOT 값으로 변경 (메뉴 권한을 위한 패턴체크를 위해)
        if (configUtil.isAdmin(strReqURL)) {
            strReqURL = strReqURL.replaceFirst(configUtil.getAdminPath(request), Config.ADMIN_ROOT);
        }

        MenuManageVO selectedMenuVo = null;
        String strReqURLWithQuery = strReqURL;
        if (request.getQueryString() != null && !request.getQueryString().equals("")) {
            strReqURLWithQuery = strReqURLWithQuery + "?" + request.getQueryString();
        }
        log.debug("REQUEST URI WITH QUERY STRING =" + strReqURLWithQuery);
        if (menuList != null) {
            // URL 과 일치 하는 메뉴 검색 1차
            for (MenuManageVO vo : menuList) {
                String menuUrl = vo.getMenuUrl();
                log.debug("menuUrl==>" + menuUrl);
                if (!StringUtil.IsEmpty(menuUrl) && menuUrl.equals(strReqURL)) {
                    log.debug("Find Menu ----EgovWebLogInterceptor :: preHandle--현재 메뉴 찾았음 1 !--------");

                    selectedMenuVo = vo;
                    llMenuNo = vo.getMenuNo();
                    session.setAttribute("SELECTED_MENU_NO", llMenuNo);

                    log.debug("EgovWebLogInterceptor : 메뉴 일치 정보 => " + llMenuNo);
                    break;
                }
            }
            // 2차 url 패턴 검색
            if (selectedMenuVo == null) {
                for (MenuManageVO vo : menuList) {
                    if (llMenuNo < 1) {
                        /*
                         * log.
                         * debug("-----------------------EgovWebLogInterceptor :: preHandle---------------------------------------------S"
                         * );
                         * log.debug("vo.getMenuNm() ==> " + vo.getMenuNm());
                         * log.debug("vo.getRolePttrn() ==> " + vo.getRolePttrn());
                         * log.debug("vo.getUpperMenuNm() ==> " + vo.getUpperMenuNm());
                         * log.debug("vo.getMenuDc() ==> " + vo.getMenuDc());
                         * log.debug("--------------------------------------------------------------------E");
                         */
                        String strRolePttrn = (vo.getRolePttrn() == null
                                ? "" : vo.getRolePttrn()) + (vo.getMenuDc() != null
                                        ? ((vo.getRolePttrn() != null && vo.getRolePttrn().length() > 0
                                                ? "||" : "") + vo.getMenuDc())
                                        : "");

                        // rolePttrn = "\\A/web/rsvguidcntr/notice/.*\\Z";
                        List<String> patternList = Arrays.asList(strRolePttrn.split("[||]"));

                        // log.debug("patternList ==>" + patternList);

                        for (String strRegExp : patternList) {
                            log.debug("strRegExp ==>" + strRegExp);
                            if (strReqURL.matches(strRegExp)) {

                                log.debug("Find Menu ----EgovWebLogInterceptor :: preHandle--현재 메뉴 찾았음 2 !--------");
                                log.debug("strReqURL = " + strReqURL);
                                log.debug("strRegExp = " + strRegExp);

                                selectedMenuVo = vo;
                                llMenuNo = vo.getMenuNo();

                                log.debug("EgovWebLogInterceptor : 메뉴 일치 정보 => " + llMenuNo);

                                session.setAttribute("SELECTED_MENU_NO", llMenuNo);
                                break;
                            }
                        }
                    }
                }
            }
        }

        session.setAttribute(Config.USER_SESSION, userSession);
        request.setAttribute("SELECTED_MENU_OBJ", selectedMenuVo);
        request.setAttribute("SELECTED_MENU_NO", String.valueOf(llMenuNo));
        request.setAttribute("REQUEST_URL", URLEncoder.encode(strReqURLWithQuery, "UTF-8"));

        return true;

    }

    /**
     * 웹 로그정보를 생성한다.
     *
     * @param HttpServletRequest
     *            request, HttpServletResponse response, Object
     *            handler
     * @return
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modeAndView) throws Exception {

        if ("local".equals(System.getProperty("spring.profiles.myactive")))
            return;

        WebLog webLog = new WebLog();
        String contextPath = request.getContextPath();
        String strReqURL = request.getRequestURI();
        if (contextPath.length() > 1)
            strReqURL = strReqURL.replaceFirst(contextPath, "");

        String uniqId = "";
        String menuNo = String.valueOf(request.getAttribute("SELECTED_MENU_NO"));

        // log.debug("웹 로그정보 저장 => URL : {} MENU_NO : {} " + strReqURL + " " + menuNo);
        boolean isAdmin = configUtil.isAdmin(strReqURL);
        boolean isUser = configUtil.isUser(strReqURL);

        // 사용자도 관리자도 아닌 경우 로그를 남기지 않는다
        if (!isAdmin && !isUser) {
            return;
        }

        if ((Config.USER_ROOT + "/member/logout").equals(strReqURL)) {
            return;
        } else {
            if (isAdmin) {
                LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
                uniqId = (user == null || user.getId() == null) ? "" : user.getId();
            } else {
                HttpSession session = request.getSession();
                UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
                LoginVO user = userSession.getUserInfo();

                String strIpAdress = HttpUtility.getClientIP(request);

                uniqId = (user == null || user.getUniqId() == null) ? "" : user.getId();

                if (strReqURL.indexOf("Ajax") < 0 && strReqURL.indexOf("/file/view/") < 0 && (strReqURL.indexOf("/member/sso/") > 0 ||strReqURL.indexOf(".json") < 0 )) {
                    if (request.getQueryString() != null && !request.getQueryString().equals("")) {
                        strReqURL += "?" + request.getQueryString();
                    }
                    if (strReqURL.length() > 200) {
                        strReqURL = strReqURL.substring(0, 200);
                    }
                    webLog.setUrl(strReqURL);
                    webLog.setRqesterId(uniqId);

                    // webLog.setRqesterIp(request.getRemoteAddr());
                    webLog.setRqesterIp(strIpAdress);
                    webLog.setMenuNo(menuNo);

                    webLogService.logInsertWebLog(webLog);
                }

            }

        }

    }
}
