package com.hisco.cmm.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.hisco.cmm.modules.CryptoUtil;
import com.hisco.cmm.modules.DateUtil;
import com.hisco.cmm.modules.RequestUtil;
import com.hisco.cmm.modules.StringUtil;

public class SecurityToken {
    private static final String TOKEN_PREFIX = "SecurityToken_";
    public static final String TOKEN_NAME = "SecurityToken";

    /**
     * 토큰 생성
     * 
     * @param request
     * @param name
     */
    public static String Make(HttpServletRequest request, String name) {
        if (request == null)
            return null;

        String sessionId = request.getSession().getId();
        String thisTime = DateUtil.printDatetime(new Date(), "yyyyMMddHHmmssms");
        String key = TOKEN_PREFIX + name;
        String value = CryptoUtil.getMD5Hash(sessionId + name + thisTime).toUpperCase();

        request.getSession().setAttribute(key, value);

        return value;
    }

    /**
     * 토큰 검사
     * 
     * @param request
     * @param name
     * @param clear
     * @return
     */
    public static boolean Check(HttpServletRequest request, String name, boolean clear) {
        if (request == null)
            return false;

        boolean ret = Check(request, name);
        if (clear)
            Clear(request, name);
        return ret;
    }

    /**
     * 토큰 검사
     * 
     * @param request
     * @param name
     * @return
     */
    public static boolean Check(HttpServletRequest request, String name) {
        if (request == null)
            return false;
        RequestUtil req = RequestUtil.getInstance(request);

        String key = TOKEN_PREFIX + name;
        String value = (String) request.getSession().getAttribute(key);
        String paramToken = req.getParam(TOKEN_NAME);

        if (StringUtil.IsEmpty(value))
            return false;
        else if (StringUtil.IsEmpty(paramToken))
            return false;
        else if (!value.equals(paramToken))
            return false;
        else
            return true;
    }

    /**
     * 토큰 오류 메시지
     * 
     * @param request
     * @param name
     * @return
     */
    public static String CheckMessage(HttpServletRequest request, String name) {
        if (request == null) {
            return "리퀘스트 객체가 없습니다.";
        }

        RequestUtil req = RequestUtil.getInstance(request);

        String key = TOKEN_PREFIX + name;
        String value = (String) request.getSession().getAttribute(key);
        String paramToken = req.getParam(TOKEN_NAME);

        if (StringUtil.IsEmpty(value))
            return "페이지 보안 토큰 세션이 만료되었습니다.";
        else if (StringUtil.IsEmpty(paramToken))
            return "수신된 보안 토큰 정보가 없습니다.";
        else if (!value.equals(paramToken))
            return "페이지 보안 토큰 정보가 일치하지 않습니다.";
        else
            return null;
    }

    /**
     * 토큰 제거
     * 
     * @param request
     * @param name
     */
    public static void Clear(HttpServletRequest request, String name) {
        if (request == null)
            return;
        String key = TOKEN_PREFIX + name;
        request.getSession().removeAttribute(key);
    }
}