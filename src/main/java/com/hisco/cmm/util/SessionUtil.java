package com.hisco.cmm.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hisco.cmm.object.UserSession;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;

public class SessionUtil {

    /**
     * attribute 값을 가져 오기 위한 method
     *
     * @param String
     *            attribute key name
     * @return Object attribute obj
     */
    public static Object getAttribute(String name) {
        return (Object) RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * attribute 설정 method
     *
     * @param String
     *            attribute key name
     * @param Object
     *            attribute obj
     * @return void
     */
    public static void setAttribute(String name, Object object) {
        RequestContextHolder.getRequestAttributes().setAttribute(name, object, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 설정한 attribute 삭제
     *
     * @param String
     *            attribute key name
     * @return void
     */
    public static void removeAttribute(String name) throws Exception {
        RequestContextHolder.getRequestAttributes().removeAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * session id
     *
     * @param void
     * @return String SessionId 값
     */
    public static String getSessionId() throws Exception {
        return RequestContextHolder.getRequestAttributes().getSessionId();
    }

    public static String getLoginId() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();

        String id = null;
        if (uri.contains(Config.USER_ROOT + "/")) {
            UserSession userSession = (UserSession) RequestContextHolder.getRequestAttributes().getAttribute(Config.USER_SESSION, RequestAttributes.SCOPE_SESSION);
            if (userSession != null) {
                LoginVO loginVO = userSession.getUserInfo();
                if (loginVO != null)
                    id = loginVO.getId();
            }
        } else {
            if (StringUtils.isBlank(id)) {
                LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
                if (loginVO != null)
                    id = loginVO.getId();
            }
        }

        return id;
    }

    public static List<String> getMyOrgList() {

        UserSession userSession = (UserSession) RequestContextHolder.getRequestAttributes().getAttribute(Config.USER_SESSION, RequestAttributes.SCOPE_SESSION);

        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        if (loginVO != null && userSession != null) {
            return userSession.getMyOrgList();
        } else {
            return null;
        }

    }
    
    /**
	 * Path 가져오기
	 */
	public static String getPath(HttpServletRequest request) {

		String path = (String) request.getAttribute("javax.servlet.forward.servlet_path");
		if (StringUtil.isEmpty(path)) {
			path = request.getRequestURI();
			if(path.indexOf(';') > 0) {
				path = path.substring(0, path.indexOf(';') -1);
			}
		}
		return path;
	}
}
