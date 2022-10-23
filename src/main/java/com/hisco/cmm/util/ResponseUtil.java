package com.hisco.cmm.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {
    /**
     * JSON 출력 처리
     * 
     * @param response
     * @param request
     * @param data
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static void PrintJson(HttpServletResponse response, HttpServletRequest request, Object data)
            throws IOException {

        if (response == null || request == null)
            return;

        String callback = request.getParameter("callback");

        if (data instanceof String) {
            PrintJson(response, callback, (String) data);
        } else if (data instanceof List) {
            PrintJson(response, callback, (List) data);
        } else if (data instanceof Map) {
            PrintJson(response, callback, (Map) data);
        } else {
            PrintJson(response, callback, data);
        }
    }

    /**
     * JSON 출력 처리
     * 
     * @param response
     * @param callback
     * @param list
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static void PrintJson(HttpServletResponse response, String callback, List list) throws IOException {
        PrintJson(response, callback, JsonUtil.List2String(list));
    }

    /**
     * JSON 출력 처리
     * 
     * @param response
     * @param callback
     * @param map
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void PrintJson(HttpServletResponse response, String callback, Map map) throws IOException {
        PrintJson(response, callback, JsonUtil.Map2String(map));
    }

    /**
     * JSON 출력 처리
     * 
     * @param response
     * @param callback
     * @param obj
     * @throws IOException
     */
    public static void PrintJson(HttpServletResponse response, String callback, Object obj) throws IOException {

        // PrintJson(response, callback, JsonUtil.Object2String(obj));
        if (response == null)
            return;

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        if (!StringUtil.IsEmpty(callback)) {
            pw.print(callback);
            pw.print("(");
        }

        if (obj instanceof String)
            pw.print(String.valueOf(obj));
        else
            pw.print(JsonUtil.Object2String(obj));

        if (!StringUtil.IsEmpty(callback))
            pw.print(")");
        pw.flush();
        pw.close();
    }

    /**
     * JSON 출력 처리
     * 
     * @param response
     * @param callback
     * @param value
     * @throws IOException
     */
    /*
     * public static void PrintJson(HttpServletResponse response, String callback, String value) throws IOException
     * {
     * if (response == null) return;
     * response.setCharacterEncoding("UTF-8");
     * response.setContentType("application/json");
     * PrintWriter pw = response.getWriter();
     * if (!StringUtil.IsEmpty(callback))
     * {
     * pw.print(callback);
     * pw.print("(");
     * }
     * pw.print(value);
     * if (!StringUtil.IsEmpty(callback)) pw.print(")");
     * pw.flush();
     * pw.close();
     * }
     */

    /**
     * 스크립트 메시지 출력 처리
     * 
     * @param response
     * @param message
     * @param url
     * @throws IOException
     */
    public static void SendMessage(HttpServletRequest request, HttpServletResponse response, String message, String url)
            throws IOException {
        SendMessage(false, request, response, message, url, null);
    }

    public static void SendMessage(final boolean isConfirm, final HttpServletRequest request,
            final HttpServletResponse response, final String message, final String urlTrue, final String urlFalse)
            throws IOException {
        SendMessage(false, request, response, message, urlTrue, urlFalse, null, null);
    }

    public static void SendMessage(HttpServletRequest request, HttpServletResponse response, String message, String url,
            final String windowWidth, final String windowHeight) throws IOException {
        SendMessage(false, request, response, message, url, null, windowWidth, windowHeight);
    }

    /**
     * 스크립트 확인창 메시지 출력 처리
     * 
     * @param request
     * @param response
     * @param message
     * @param url
     * @throws IOException
     */
    public static void SendMessageConfirm(HttpServletRequest request, HttpServletResponse response, String message,
            String urlTrue, String urlFalse) throws IOException {
        SendMessage(true, request, response, message, urlTrue, urlFalse);
    }

    public static void SendMessage(final boolean isConfirm, final HttpServletRequest request,
            final HttpServletResponse response, final String message, final String urlTrue, final String urlFalse,
            final String windowWidth, final String windowHeight) throws IOException {

        if (request == null || response == null)
            return;

        if (response.isCommitted())
            return;

        String print_message = null;
        String url_true = null;
        String url_false = null;

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter pw = response.getWriter();

        pw.print("<!DOCTYPE html>\n<html lang=\"ko\">\n<head>\n<meta charset=\"utf-8\" />\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n<title>Processing</title>\n</head>\n<body>\n<script type=\"text/javascript\">\n//<![CDATA[\n");

        // resize width 가 있다면
        if (windowWidth != null && !StringUtil.IsEmpty(windowWidth)) {
            pw.print("window.resizeTo(" + windowWidth + ",window.outerHeight );");
        }
        // resize height 가 있다면
        if (windowHeight != null && !StringUtil.IsEmpty(windowHeight)) {
            pw.print("window.resizeTo(window.outerWidth," + windowHeight + ");");
        }

        // 미리 지정된 메시지 확인
        if ("tokenError".equals(message)) {
            print_message = "요청하신 작업을 처리하기 위한 정보가 누락되었습니다.\n페이지를 새로고침 하시고 다시 이용해 주세요.";
            if (StringUtil.IsEmpty(urlTrue))
                url_true = "history.back()";
        }

        // 이전 주소
        if ("referrer".equals(urlTrue)) {
            url_true = "if (document.referrer) { location.href = document.referrer; } else { history.back(); }";
        }

        if ("referrer".equals(urlFalse)) {
            url_false = "if (document.referrer) { location.href = document.referrer; } else { history.back(); }";
        }

        if (!StringUtil.IsEmpty(message)) {
            print_message = message.replaceAll("\"", "\\\\\"").replaceAll("\'", "\\\\\'").replaceAll("\n", "\\\\n");

            if (isConfirm) {
                pw.println("if (confirm('" + print_message + "'))");
                pw.println("{");
            } else {
                pw.println("alert('" + print_message + "');");
            }
        }

        if (url_true == null)
            url_true = urlTrue;
        if (url_false == null)
            url_false = urlFalse;
        if (print_message == null)
            print_message = message;

        if (!StringUtil.IsEmpty(url_true)) {
            if (url_true.indexOf("history.") > -1 || url_true.indexOf("location.") > -1 || url_true.indexOf(";") > 0)
                pw.println(url_true);
            else {
                RequestUtil req = RequestUtil.getInstance(request);
                pw.println("location.href = '" + req.MakeUrl(url_true) + "';");
            }
        }

        if (isConfirm) {

            pw.println("}");
            if (!StringUtil.IsEmpty(url_false)) {
                pw.println("else {");
                if (url_false.indexOf("history.") > -1 || url_false.indexOf("location.") > -1 || url_false.indexOf("self.") > -1 || url_false.indexOf(";") > 0) {
                    pw.println(url_false);
                } else {
                    RequestUtil req = RequestUtil.getInstance(request);
                    pw.println("location.href = '" + req.MakeUrl(url_false) + "';");
                }
                pw.println("}");
            }
        }

        pw.print("//]]>\n</script>\n");

        pw.println("<noscript>");
        if (!StringUtil.IsEmpty(print_message)) {
            pw.println("<p>" + print_message + "</p>");
            if (!StringUtil.IsEmpty(url_true) && url_true.indexOf("history.") < 0 && url_true.indexOf("location.") < 0 && url_true.indexOf(";") < 0) {
                RequestUtil req = RequestUtil.getInstance(request);
                pw.println("<p><a href=\"" + StringUtil.HTMLEncode(req.MakeUrl(url_true)) + "\">페이지 이동하기</a></p>");
            }
        }

        pw.println("</noscript>");

        pw.print("</body>\n</html>");
        pw.flush();
        pw.close();
        pw = null;
    }

    /**
     * URL 정보
     * 
     * @param request
     * @param url
     * @return
     */
    public static String Url(HttpServletRequest request, String url) {

        if (request == null)
            return null;

        StringBuffer ret = new StringBuffer();

        if (!StringUtil.IsEmpty(url) && url.toLowerCase().indexOf("http://") < 0 && url.toLowerCase().indexOf("https://") < 0 && url.indexOf("?") != 0) {

            String contextPath = request.getContextPath();

            if (url.indexOf("/" + contextPath) != 0) {
                ret.append("/").append(contextPath).append("/").append(url);
            } else {
                ret.append(url);
            }
        } else {
            ret.append(url);
        }

        return ret.toString().replaceAll("(/) {1,}", "/");
    }

    /**
     * URL 생성 현재 URL 그대로 리턴
     * 
     * @param request
     * @return
     */
    public static String MakeUrl(HttpServletRequest request, boolean encode) {
        return MakeUrl(request, null, null, null, false, encode);
    }

    /**
     * URL 생성
     * 
     * @param request
     * @param path
     *            경로
     * @param param
     *            파라미터 ( 'name' : 'value', 'name2' : 123 )
     * @param clear
     *            초기화 여부 (true : 현재 url 무시)
     * @param encode
     *            html 인코딩 여부
     * @return
     */
    public static String MakeUrl(HttpServletRequest request, String context, String path, String param, boolean clear,
            boolean encode) {
        if (request == null)
            return null;

        RequestUtil requestData = RequestUtil.getInstance(request);

        Map<String, Object> params = null;
        String queryString = requestData.getQueryString();

        /*
         * if (context == null || context.isEmpty() || "".equals(context.trim()))
         * {
         * context = requestData.getContextPath();
         * }
         * if (context.indexOf("/") != 0) context = "/"+ context;
         * if (path == null || path.isEmpty() || "".equals(path.trim()))
         * {
         * path = requestData.getUri();
         * if (context != null && !context.isEmpty() && !"".equals(context))
         * {
         * path = path.substring(context.length());
         * }
         * }
         */

        if (!StringUtil.IsEmpty(param)) {
            params = JsonUtil.String2Map(("{" + param + "}").replaceAll(",[ ]{0,}:", ", '':"));
        }

        Map<String, String> queryStringMap = new HashMap<String, String>();
        if (clear == false && !StringUtil.IsEmpty(queryString)) {
            String[] arrQueryString = queryString.split("&");
            for (int i = 0, length = arrQueryString.length; i < length; i++) {
                // 파리미터 이름만 있고"=" 도 없고 값도 없을 경우
                if (arrQueryString[i].indexOf("=") < 0)
                    queryStringMap.put(arrQueryString[i], "");
                else {
                    int idx = arrQueryString[i].indexOf("=");
                    String name = arrQueryString[i].substring(0, idx);
                    String value = arrQueryString[i].substring(idx + 1);

                    try {
                        name = java.net.URLDecoder.decode(name, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                    }
                    try {
                        value = java.net.URLDecoder.decode(value, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                    }

                    queryStringMap.put(name, value);
                }
            }
        }

        if (params != null && params.size() > 0) {
            Iterator<String> itr = params.keySet().iterator();
            String key, value;
            while (itr.hasNext()) {
                key = (String) itr.next();
                value = String.valueOf(params.get(key));

                queryStringMap.put(key, value);
            }
        }

        StringBuffer sb = new StringBuffer("");
        Iterator<String> iterator = queryStringMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = (String) queryStringMap.get(key);

            if (!StringUtil.IsEmpty(value)) {
                if (sb.length() > 0)
                    sb.append("&");

                try {
                    key = java.net.URLEncoder.encode(key, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                }
                try {
                    value = java.net.URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                }

                sb.append(key.trim());
                sb.append("=");
                sb.append(value.trim());
            }
        }

        if (sb.length() > 0)
            sb.insert(0, "?");
        else
            sb.append("?");

        if (!StringUtil.IsEmpty(path)) {
            String[] arrPath = path.split("/");
            int check = 0;
            for (int i = arrPath.length - 1; i >= 0; i--) {
                if (check > 0)
                    sb.insert(0, "/");
                try {
                    sb.insert(0, java.net.URLEncoder.encode(arrPath[i], "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    sb.insert(0, arrPath[i]);
                }
                check++;
            }

            if (!StringUtil.IsEmpty(context)) {
                if (!"/".equals(context.substring(context.length() - 1)))
                    sb.insert(0, "/");
                sb.insert(0, context);
                if (!"/".equals(context.substring(0, 1)))
                    sb.insert(0, "/");
            }
        }

        if (encode)
            return Url(request, StringUtil.HTMLEncode(sb.toString()));
        else
            return Url(request, sb.toString());
    }

    /*
     * URL 생성
     */
    public static String MakeUrlAction(HttpServletRequest request, String moduleAction, String moduleValue,
            String subModuleAction, String subModuleValue, boolean clear, boolean encode) {
        if (request == null)
            return null;

        StringBuffer param = new StringBuffer();
        if (moduleAction != null) {
            if (param.length() > 0)
                param.append(",");
            param.append("'").append(RequestUtil.PARAM_MODULE_ACTION).append("':'").append(moduleAction).append("'");
        }
        if (moduleValue != null) {
            if (param.length() > 0)
                param.append(",");
            param.append("'").append(RequestUtil.PARAM_MODULE_VALUE).append("':'").append(moduleValue).append("'");
        }
        if (subModuleAction != null) {
            if (param.length() > 0)
                param.append(",");
            param.append("'").append(RequestUtil.PARAM_SUB_MODULE_ACTION).append("':'").append(subModuleAction).append("'");
        }
        if (subModuleValue != null) {
            if (param.length() > 0)
                param.append(",");
            param.append("'").append(RequestUtil.PARAM_SUB_MODULE_VALUE).append("':'").append(subModuleValue).append("'");
        }

        return MakeUrl(request, null, null, param.toString(), clear, encode);
    }

    /*
     * URL 생성
     */
    public static String MakeUrlAction(HttpServletRequest request, String moduleAction, String moduleValue,
            String param, boolean clear, boolean encode) {
        if (request == null)
            return null;

        StringBuffer sbParam = new StringBuffer();

        if (!StringUtil.IsEmpty(param))
            sbParam.append(param);

        if (moduleAction != null) {
            if (sbParam.length() > 0)
                sbParam.append(",");
            sbParam.append("'").append(RequestUtil.PARAM_MODULE_ACTION).append("':'").append(moduleAction).append("'");
        }
        if (moduleValue != null) {
            if (sbParam.length() > 0)
                sbParam.append(",");
            sbParam.append("'").append(RequestUtil.PARAM_MODULE_VALUE).append("':'").append(moduleValue).append("'");
        }

        return MakeUrl(request, null, null, sbParam.toString(), clear, encode);
    }

    /*
     * URL 생성
     */
    public static String MakeUrlAction(HttpServletRequest request, String moduleAction, String moduleValue,
            boolean clear, boolean encode) {
        return MakeUrlAction(request, moduleAction, moduleValue, null, null, clear, encode);
    }
}
