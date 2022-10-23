package com.hisco.cmm.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hisco.cmm.config.DynamicConfigUtil;
import com.hisco.cmm.object.ResultInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * http 관련 util 모음
 *
 * @author 진수진
 * @since 2020.07.01
 * @version 1.0, 2020.07.01
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.01 최초작성
 */
@Slf4j
public class HttpUtility {

    /**
     * 접속 uri 로 context 제외한 uri 리턴
     *
     * @param HttpServletRequest
     * @return String
     * @throws Exception
     */
    public static String getViewUrl(HttpServletRequest request) throws Exception {
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        DynamicConfigUtil configUtil = (DynamicConfigUtil) wac.getBean("dynamicConfigUtil");

        String contextPath = request.getContextPath();
        String retrul = request.getRequestURI().replace(contextPath, "");

        if (configUtil.isAdmin(request)) {
            String adminPath = configUtil.getAdminPath(request);
            retrul = Config.ADMIN_ROOT + retrul.replace(adminPath, "");
        }

        // log.debug("GET-VIEW-URL=" + retrul);
        return retrul;
    }

    /**
     * 접속 uri 로 context 제외한 관리자 uri 리턴
     *
     * @param HttpServletRequest
     * @return String
     * @throws Exception
     */
    public static String getViewUrl(String root, String path) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(root);
        sb.append(path);
        return sb.toString();
    }

    /**
     * 접속 uri 로 context 제외한 관리자 uri 리턴
     *
     * @param String
     * @param HttpServletRequest
     * @return String
     * @throws Exception
     */
    public static String getViewUrl(String webRoot, HttpServletRequest request) throws Exception {
        String path = getViewUrl(request);
        path = path.replaceFirst(webRoot, Config.ADMIN_ROOT);
        return path;
    }

    /**
     * 리다이렉트 스크립트 생성
     *
     * @param request
     * @param response
     * @param msg
     * @param url
     * @return
     * @throws Exception
     */
    public static void sendRedirect(HttpServletRequest request, HttpServletResponse response, String msg, String url)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter pw = response.getWriter();

        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        DynamicConfigUtil configUtil = (DynamicConfigUtil) wac.getBean("dynamicConfigUtil");

        url = configUtil.getAdminDynamicPath(request, url); // 관리자 주소가 여러개 인경우 접속 url 체크

        pw.print("<!DOCTYPE html>\n<html lang=\"ko\">\n<head>\n<meta charset=\"utf-8\" />\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n<title>Processing</title>\n</head>\n<body>\n<script type=\"text/javascript\">\n");

        if (msg != null && msg.length() > 0) {
            String printMessage = msg.replaceAll("\"", "\\\\\"").replaceAll("\'", "\\\\\'").replaceAll("\n", "\\\\n");
            pw.println("alert('" + Unscript(printMessage) + "');");
        }

        if (url.startsWith("http")) {
            pw.println("location.replace('" + url + "');");
        } else {
            pw.println("location.replace('" + request.getContextPath() + CommonUtil.unscript(url) + "');");
        }
        pw.print("\n</script>\n");
        pw.print("</body>\n</html>");
        pw.flush();
        pw.close();
        pw = null;
    }

    /**
     * 리다이렉트 스크립트 생성
     *
     * @param request
     * @param response
     * @param msg
     * @param url
     * @return
     * @throws Exception
     */
    public static String getAdminPath(HttpServletRequest request)
            throws IOException {
        if (request.getRequestURI().startsWith(Config.MNG_ROOT)) {
            return Config.MNG_ROOT;
        } else {
            return Config.ADMIN_ROOT;
        }
    }

    /**
     * history.back 스크립트 생성
     *
     * @param request
     * @param response
     * @param msg
     * @return
     * @throws Exception
     */
    public static void sendBack(HttpServletRequest request, HttpServletResponse response, String msg)
            throws IOException {

        log.debug("call sendBack");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter pw = response.getWriter();

        pw.print("<!DOCTYPE html>\n<html lang=\"ko\">\n<head>\n<meta charset=\"utf-8\" />\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n<title>Processing</title>\n</head>\n<body>\n<script type=\"text/javascript\">\n//<![CDATA[\n");

        if (msg != null && msg.length() > 0) {
            String printMessage = msg.replaceAll("\"", "\\\\\"").replaceAll("\'", "\\\\\'").replaceAll("\n", "\\\\n");

            log.debug(printMessage);

            pw.println("alert('" + Unscript(printMessage) + "');");
        }

        pw.println("history.back();");
        pw.print("//]]>\n</script>\n");
        pw.print("</body>\n</html>");
        pw.flush();
        pw.close();
        pw = null;

    }

    /**
     * close 스크립트 생성
     *
     * @param request
     * @param response
     * @param msg
     * @return
     * @throws Exception
     */
    public static void sendClose(HttpServletRequest request, HttpServletResponse response, String msg)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter pw = response.getWriter();

        pw.print("<!DOCTYPE html>\n<html lang=\"ko\">\n<head>\n<meta charset=\"utf-8\" />\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n<title>Processing</title>\n</head>\n<body>\n<script type=\"text/javascript\">\n//<![CDATA[\n");

        if (msg != null && msg.length() > 0) {
            String printMessage = msg.replaceAll("\"", "\\\\\"").replaceAll("\'", "\\\\\'").replaceAll("\n", "\\\\n");
            pw.println("alert('" + Unscript(printMessage) + "');");
        }

        pw.println("self.close();");
        pw.print("//]]>\n</script>\n");
        pw.print("</body>\n</html>");
        pw.flush();
        pw.close();
        pw = null;
    }

    /**
     * ResultInfo object 리턴
     * @param String
     * @return ResultInfo
     * @throws
     */
    public static ResultInfo getSuccessResultInfo(String msg) {
        return new ResultInfo("SUCCESS", msg);
    }

    /**
     * ResultInfo object 리턴
     * @param String
     * @return ResultInfo
     * @throws
     */
    public static ResultInfo getErrorResultInfo(String msg) {
        return new ResultInfo("ERROR", msg);
    }

    /**
     * ResultInfo object 리턴
     * @param String
     * @param id
     * @return ResultInfo
     * @throws
     */
    public static ResultInfo getErrorResultInfo(String msg, String id) {
        return new ResultInfo("ERROR", msg, id);
    }

    /**
     * XSS 방지 처리.
     *
     * @param data
     * @return
     */
    public static String Unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }

        String ret = data;

        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");

        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");

        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");

        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");

        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

        return ret;
    }

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
