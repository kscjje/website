package com.hisco.cmm.modules;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hisco.extend.AbstractController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestUtil extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public static final String SLASH = "／";
    public static final String CHARSET = "UTF-8";

    public static final String PARAM_MODULE_ACTION = "action";
    public static final String PARAM_MODULE_VALUE = "action-value";

    public static final String PARAM_SUB_MODULE_ACTION = "sub-action";
    public static final String PARAM_SUB_MODULE_VALUE = "sub-action-value";

    private static final int HttpsPort = 443;

    private HttpServletRequest request;

    @SuppressWarnings("unchecked")
    public RequestUtil(HttpServletRequest request) {
        if (log.isDebugEnabled())
            log.debug("INIT");

        if (request == null)
            return;

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
        }

        this.request = request;

        // static 정보 삽입
        super.put("SLASH", SLASH);
        super.put("PARAM_MODULE_ACTION", PARAM_MODULE_ACTION);
        super.put("PARAM_MODULE_VALUE", PARAM_MODULE_VALUE);
        super.put("PARAM_SUB_MODULE_ACTION", PARAM_SUB_MODULE_ACTION);
        super.put("PARAM_SUB_MODULE_VALUE", PARAM_SUB_MODULE_VALUE);
        super.put("HttpsPort", HttpsPort);

        // request 파라미터 정보 삽입
        super.putAll(request.getParameterMap());

        super.put(PARAM_MODULE_ACTION, this.getParamModuleAction());
        super.put(PARAM_MODULE_VALUE, this.getParamModuleValue());
        super.put(PARAM_SUB_MODULE_ACTION, this.getParamSubModuleAction());
        super.put(PARAM_SUB_MODULE_VALUE, this.getParamSubModuleValue());

        // 메뉴 정보 삽입
        this.initMenuInfo();
    }

    @Override
    public String toString() {
        return ObjectUtil.ConvertJsonString(this);
    }

    /**
     * Request 유틸 인스턴스 생성
     *
     * @param request
     * @return
     */
    public static RequestUtil getInstance(HttpServletRequest request) {
        if (request == null) {
            RequestUtil req = new RequestUtil(null);
            return req;
        } else if (request.getAttribute("Kntool_RequestUtil") != null) {
            RequestUtil req = (RequestUtil) request.getAttribute("Kntool_RequestUtil");
            return req;
        } else {
            RequestUtil req = new RequestUtil(request);
            request.setAttribute("Kntool_RequestUtil", req);
            return req;
        }
    }

    /**
     * 메뉴 정보 처리
     */
    private void initMenuInfo() {
        if (IsKntoolMenu())
            return;
        KntoolMenuPath(0);
    }

    /**
     * 메뉴명 인코딩
     *
     * @param val
     * @return
     */
    public static String EncodeMenuName(String val) {
        if (StringUtil.IsEmpty(val))
            return null;

        String ret = val.replaceAll("/", SLASH).replaceAll("/", SLASH);

        return StringUtil.URLEncode(ret, CHARSET);
    }

    /**
     * 메뉴명 디코딩
     *
     * @param val
     * @return
     */
    public static String DecodeMenuName(String val) {
        if (StringUtil.IsEmpty(val))
            return null;

        return StringUtil.URLDecode(val, CHARSET).replaceAll(SLASH, "/").replaceAll(SLASH, "/");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * reqeust Method
     *
     * @return GET / POST / PUT ...
     */
    public String getMethod() {
        return request.getMethod().toUpperCase();
    }

    /**
     * 파라미터 값 문자로 받기
     *
     * @param name
     * @return
     */
    public String getParam(String name) {
        try {
            Object val = super.get(name);

            if (val == null)
                return null;
            else if (val instanceof String[])
                return StringUtil.StringJoin((String[]) val, ",");
            else if (val instanceof String)
                return (String) val;
            else
                return String.valueOf(val);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 파라미터 값 문자로 받기 비어있을 경우 기본값 삽입
     *
     * @param name
     * @param def
     * @return
     */
    public String getParam(String name, String def) {
        String ret = getParam(name);
        if (StringUtil.IsEmpty(ret))
            return def;
        return ret;
    }

    /**
     * 파라미터 값 문자 배열로 받기
     *
     * @param name
     * @return
     */
    public String[] getParams(String name) {
        try {
            Object val = super.get(name);

            if (val == null)
                return null;
            else if (val instanceof String[])
                return (String[]) val;
            else if (val instanceof String) {
                String[] ret = new String[1];
                ret[0] = (String) val;
                return ret;
            } else {
                String[] ret = new String[1];
                ret[0] = String.valueOf(val);
                return ret;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 파라미터 값 int로 받기
     *
     * @param name
     * @return
     */
    public int getParamInteger(String name) {
        return StringUtil.String2Int(getParam(name), 0);
    }

    /**
     * 파라미터 값 long로 받기
     *
     * @param name
     * @return
     */
    public long getParamLong(String name) {
        return StringUtil.String2Long(getParam(name), 0);
    }

    /**
     * 파라미터 값 float로 받기
     *
     * @param name
     * @return
     */
    public float getParamFloat(String name) {
        return StringUtil.String2Float(getParam(name), 0f);
    }

    /**
     * 파라미터 값 Date로 받기
     *
     * @param name
     * @return
     */
    public Date getParamDate(String name) {
        return DateUtil.string2date(getParam(name));
    }

    /**
     * 모듈 액션 파라미터명
     *
     * @return
     */
    public String getParamModuleAction() {
        return StringUtil.ToLowerCase(this.getParam(PARAM_MODULE_ACTION));
    }

    /**
     * 모듈 액션 값
     *
     * @return
     */
    public String getParamModuleValue() {
        return this.getParam(PARAM_MODULE_VALUE);
    }

    /**
     * 모듈 서브 액션 파라미터명
     *
     * @return
     */
    public String getParamSubModuleAction() {
        return StringUtil.ToLowerCase(this.getParam(PARAM_SUB_MODULE_ACTION));
    }

    /**
     * 모듈 서브 액션 값
     *
     * @return
     */
    public String getParamSubModuleValue() {
        return this.getParam(PARAM_SUB_MODULE_VALUE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 이전 주소 체크
     *
     * @return
     */
    public boolean CheckReferer() {
        String referer = getReferer();
        String domain = getDomain();

        if (log.isDebugEnabled()) {
            log.info("## domain : ".concat(domain == null ? "null" : domain));
            log.info("## referer : ".concat(referer == null ? "null" : referer));
        }

        if (referer == null || domain == null) {
            return false;
        } else if (referer.indexOf(domain) < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 이전 주소 체크
     *
     * @param request
     * @return
     */
    public static boolean CheckReferer(HttpServletRequest request) {
        String referer = request.getHeader("referer");
        String domain = request.getServerName();

        if (referer == null || domain == null) {
            return false;
        } else if (referer.indexOf(domain) < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 이전 주소 확인
     *
     * @return
     */
    public String getReferer() {
        return request.getHeader("referer");
    }

    /**
     * 이전 주소 확인
     *
     * @return
     */
    public static String getReferer(HttpServletRequest request) {
        return request.getHeader("referer");
    }

    ////////////////////////////////////////////////
    /**
     * 현재 브라우저 url 정보
     *
     * @return
     * @throws MalformedURLException
     * @throws URISyntaxException
     */
    public String getFullUrl() throws MalformedURLException, URISyntaxException {
        if (super.get("@FULLURL") == null) {
            java.net.URL url = new java.net.URL(request.getRequestURL().toString());
            String host = url.getHost();
            String userInfo = url.getUserInfo();
            String scheme = url.getProtocol();
            int port = url.getPort();
            String path = (String) request.getAttribute("javax.servlet.forward.request_uri");
            String query = (String) request.getAttribute("javax.servlet.forward.query_string");
            java.net.URI uri = new java.net.URI(scheme, userInfo, host, port, path, query, null);

            super.put("@FULLURL", uri.toString());
        }
        return (String) super.get("@FULLURL");
    }

    /**
     * 도메인 정보
     *
     * @return
     */
    public String getDomain() {
        if (super.get("@DOMAIN") == null) {
            super.put("@DOMAIN", request.getServerName());
        }
        return (String) super.get("@DOMAIN");
    }

    /**
     * 현재 URL 경로(도메인, 컨텍스트경로 미포함)
     *
     * @return
     */
    public String getUri() {
        if (super.get("@URI") == null) {
            super.put("@URI", request.getRequestURI());
        }
        return (String) super.get("@URI");
    }

    /**
     * 현재 URL 풀 경로(http://xxx.com/xxx/xxx)
     *
     * @return
     */
    public String getUrl() {
        if (super.get("@URL") == null) {
            String url = null;

            if (StringUtil.IsEmpty(url))
                url = request.getHeader("X-URL");
            if (StringUtil.IsEmpty(url))
                url = request.getRequestURL().toString();

            if (url.indexOf("?") > 0) {
                super.put("@URL", url.substring(0, url.indexOf("?") - 1));
            } else {
                super.put("@URL", url);
            }
        }
        return (String) super.get("@URL");
    }

    /**
     * ? 다음 파라미터 정보
     *
     * @return
     */
    public String getQueryString() {
        if (super.get("@QUERYSTRING") == null) {
            super.put("@QUERYSTRING", request.getQueryString());
        }
        return (String) super.get("@QUERYSTRING");
    }

    /**
     * 현재 경로 출력(ContextPath 제거)
     *
     * @return
     */
    public String getPath() {
        if (super.get("@PATH") == null) {
            String uri = getUri();
            String contextPath = getContextPath();

            if (StringUtil.IsEmpty(contextPath))
                super.put("@PATH", uri);
            else
                super.put("@PATH", uri.substring(contextPath.length() + 1));
        }
        return (String) super.get("@PATH");
    }

    /**
     * 컨텍스트경로
     *
     * @return
     */
    public String getContextPath() {
        if (super.get("@CONTEXTPATH") == null) {
            String context_path = request.getContextPath();
            if (context_path.startsWith("/"))
                context_path = context_path.substring(1);
            if (context_path.endsWith("/"))
                context_path = context_path.substring(0, context_path.length() - 1);

            super.put("@CONTEXTPATH", context_path);
        }
        return (String) super.get("@CONTEXTPATH");
    }

    /**
     * URL 루트 경로(컨텍스트 포함. http://xxx.com/xxx/)
     *
     * @return
     */
    public String getLocationRoot() {
        return getLocationRoot(true);
    }

    public String getLocationRoot(boolean context) {
        String key = "@LOCATIONROOT_".concat(context ? "T" : "F");
        if (super.get(key) == null) {
            String url = getUrl();
            String[] urls = url.split("/");
            StringBuffer locationRoot = new StringBuffer();

            if (context && !StringUtil.IsEmpty(getContextPath())) {
                for (int i = 0; i <= 3; i++) {
                    locationRoot.append(urls[i]);
                    locationRoot.append("/");
                }
            } else {
                for (int i = 0; i <= 2; i++) {
                    locationRoot.append(urls[i]);
                    locationRoot.append("/");
                }
            }

            super.put(key, locationRoot.toString());
        }
        return (String) super.get(key);
    }

    /**
     * HTTP URL 루트 경로(컨텍스트 포함. http://xxx.com/xxx/)
     *
     * @return
     */
    public String getLocationRootHttp() {
        return getLocationRootHttp(true);
    }

    public String getLocationRootHttp(boolean context) {
        if (super.get("@LOCATIONROOTHTTP") == null) {
            super.put("@LOCATIONROOTHTTP", getLocationRoot(context).replace("HTTPS://", "HTTP://").replace("https://", "http://"));
        }
        return (String) super.get("@LOCATIONROOTHTTP");
    }

    /**
     * HTTPS URL 루트 경로(컨텍스트 포함. https://xxx.com/xxx/)
     *
     * @return
     */
    public String getLocationRootHttps() {
        return getLocationRootHttps(true);
    }

    public String getLocationRootHttps(boolean context) {
        if (super.get("@LOCATIONROOTHTTPS") == null) {
            StringBuffer locationRoot = new StringBuffer(getLocationRoot(context).replace("HTTP://", "HTTPS://").replace("http://", "https://"));

            if (!Integer.valueOf(443).equals(HttpsPort)) {
                String[] buf = locationRoot.toString().split("/");
                if (buf[2].indexOf(":") > -1)
                    buf[2] = buf[2].substring(0, buf[2].indexOf(":") + 1) + HttpsPort;
                else
                    buf[2] = buf[2] + ":" + HttpsPort;

                locationRoot.setLength(0);
                for (int i = 0; i < buf.length; i++) {
                    locationRoot.append(buf[i]);
                    locationRoot.append("/");
                }
            } else {
                String[] buf = locationRoot.toString().split("/");
                if (buf[2].indexOf(":") > -1) {
                    buf[2] = buf[2].substring(0, buf[2].indexOf(":"));
                }

                locationRoot.setLength(0);
                for (int i = 0; i < buf.length; i++) {
                    locationRoot.append(buf[i]);
                    locationRoot.append("/");
                }
            }

            super.put("@LOCATIONROOTHTTPS", locationRoot.toString());
        }
        return (String) super.get("@LOCATIONROOTHTTPS");
    }

    /**
     * 프로토콜 방식(http, https)
     *
     * @return
     */
    public String getProtocol() {
        if (super.get("@PROTOCOL") == null) {
            String protocol = null;
            if (protocol == null || protocol.isEmpty() || protocol.length() == 0 || "".equalsIgnoreCase(protocol) || "unknown".equalsIgnoreCase(protocol))
                protocol = request.getHeader("x-url ");
            if (protocol == null || protocol.isEmpty() || protocol.length() == 0 || "".equalsIgnoreCase(protocol) || "unknown".equalsIgnoreCase(protocol))
                protocol = request.getHeader("X-protocol");
            if (protocol == null || protocol.isEmpty() || protocol.length() == 0 || "".equalsIgnoreCase(protocol) || "unknown".equalsIgnoreCase(protocol))
                protocol = request.getHeader("X-PROTOCOL");
            if (protocol == null || protocol.isEmpty() || protocol.length() == 0 || "".equalsIgnoreCase(protocol) || "unknown".equalsIgnoreCase(protocol))
                protocol = request.getHeader("protocol");
            if (protocol == null || protocol.isEmpty() || protocol.length() == 0 || "".equalsIgnoreCase(protocol) || "unknown".equalsIgnoreCase(protocol))
                protocol = request.getHeader("PROTOCOL");

            if (protocol == null || protocol.isEmpty() || protocol.length() == 0 || "".equalsIgnoreCase(protocol) || "unknown".equalsIgnoreCase(protocol)) {
                if (getUrl().toLowerCase().startsWith("https://"))
                    protocol = "HTTPS";
                else if (getUrl().toLowerCase().startsWith("http://"))
                    protocol = "HTTP";
            }
            super.put("@PROTOCOL", protocol);
        }
        return (String) super.get("@PROTOCOL");
    }

    /**
     * 클라이언트 아이피 주소
     *
     * @return
     */
    public String getClientIP() {
        if (super.get("@CLIENTIPADDRESS") == null) {
            String ip = null;

            if (ip == null || ip.isEmpty() || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip))
                ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty() || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip))
                ip = request.getHeader("X-Real-IP");
            if (ip == null || ip.isEmpty() || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip))
                ip = request.getHeader("Proxy-Client-IP");
            if (ip == null || ip.isEmpty() || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip))
                ip = request.getHeader("WL-Proxy-Client-IP");
            if (ip == null || ip.isEmpty() || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip))
                ip = request.getHeader("HTTP_CLIENT_IP");
            if (ip == null || ip.isEmpty() || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip))
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            if (ip == null || ip.isEmpty() || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip))
                ip = request.getRemoteAddr();

            super.put("@CLIENTIPADDRESS", ip);
        }
        return (String) super.get("@CLIENTIPADDRESS");
    }

    /**
     * 사용자 정보
     *
     * @return
     */
    public String getUserAgent() {
        return StringUtil.IsEmpty(request.getHeader("USER-AGENT")) ? "" : request.getHeader("USER-AGENT");
    }

    /**
     * 실제 URL
     *
     * @param url
     * @return
     */
    public String getRealUrl(String url) {
        String contextpath = getContextPath();
        if (!StringUtil.IsEmpty(contextpath) && !url.startsWith("/".concat(contextpath).concat("/")))
            return "/".concat(contextpath).concat(url);
        else
            return url;
    }

    /**
     * 셋업 페이지 경로 여부
     *
     * @return
     */
    public boolean isSetupUrl() {
        String path = KntoolMenuPath(1);
        if (StringUtil.IsEmpty(path))
            path = getPath();

        return path.replaceAll(SLASH, "").indexOf(HiscoWebTool.SETUP_ROOT) == 0;
    }

    /**
     * 관리자 페이지 경로 여부
     *
     * @return
     */
    public boolean isAdminUrl() {
        String path = KntoolMenuPath(1);
        if (StringUtil.IsEmpty(path))
            path = getPath();

        return path.replaceAll(SLASH, "").indexOf(HiscoWebTool.ADMIN_ROOT) == 0;
    }

    /**
     * Cookie 값 가지고 오기
     *
     * @param request
     *            request 객체
     * @param name
     *            쿠키 이름
     * @return 쿠키 저장 값
     */
    public String getCookie(String name) {
        return getCookie(this.request, name);
    }

    /**
     * Cookie 값 가지고 오기
     *
     * @param request
     *            request 객체
     * @param name
     *            쿠키 이름
     * @return 쿠키 저장 값
     */
    public static String getCookie(HttpServletRequest request, String name) {
        if (request == null)
            return null;

        try {
            Cookie[] cookies = request.getCookies();

            if (cookies == null)
                return null;

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * Cookie 저장 하기
     *
     * @param response
     *            객체
     * @param domain
     *            도메인명
     * @param name
     *            쿠키 이름
     * @param value
     *            쿠키 값
     * @param maxage
     *            쿠키 저장 기간(초) : 0보다 켜야 하며 0보다 작거나 같을 경우 휘발성 쿠키로 처리
     * @return 쿠키 저장 성공 여부
     */
    public static boolean setCookie(HttpServletResponse response, String domain, String name, String value,
            int maxage) {
        if (response == null)
            return false;
        if (StringUtil.IsEmpty(name))
            return false;

        try {
            Cookie cookie = new Cookie(name.trim(), value);
            // cookie.setPath("/");
            if (maxage > 0)
                cookie.setMaxAge(maxage);

            if (!StringUtil.IsEmpty(domain))
                cookie.setDomain(domain);

            // cookie.setSecure(true);
            response.addCookie(cookie);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Cookie 저장 하기
     *
     * @param response
     *            객체
     * @param name
     *            쿠키 이름
     * @param value
     *            쿠키 값
     * @param maxage
     *            쿠키 저장 기간(초) : 0보다 켜야 하며 0보다 작거나 같을 경우 휘발성 쿠키로 처리
     * @return 쿠키 저장 성공 여부
     */
    public static boolean setCookie(HttpServletResponse response, String name, String value, int maxage) {
        return setCookie(response, null, name, value, maxage);
    }

    /**
     * Cookie 만료 처리하기
     *
     * @param response
     *            객체
     * @param domain
     *            도메인명
     * @param name
     *            쿠키 이름
     * @return 쿠키 만료 성공 여부
     */
    public static boolean destoryCookie(HttpServletResponse response, String domain, String name) {
        if (response == null)
            return false;
        if (StringUtil.IsEmpty(name))
            return false;

        try {
            Cookie cookie = new Cookie(name.trim(), "");
            // cookie.setPath("/");
            cookie.setMaxAge(0);
            if (!StringUtil.IsEmpty(domain))
                cookie.setDomain(domain.trim());
            // cookie.setSecure(true);
            response.addCookie(cookie);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Cookie 만료 처리하기
     *
     * @param response
     *            객체
     * @param name
     *            쿠키 이름
     * @return 쿠키 만료 성공 여부
     */
    public static boolean destoryCookie(HttpServletResponse response, String name) {
        return destoryCookie(response, null, name);
    }

    /**
     * URL 주소 중 / 중복 제거 처리
     *
     * @param url
     * @return
     */
    public static String CheckUrl(String url) {
        // String ret = url.replaceAll("[/]{1,}", "/");
        String ret = url.replace("://", "^-_-^").replaceAll("[/]{1,}", "/").replace("^-_-^", "://");

        if (ret.indexOf("/?") > -1)
            return ret.replace("/?", "?");
        else if (ret.length() > 1 && ret.endsWith("/"))
            return ret.substring(0, ret.length() - 1);
        else
            return ret;
    }

    /**
     * URL 생성
     *
     * @param path
     * @return
     */
    public String MakeUrl(String path) {

        String strThisPath = path;

        if (StringUtil.IsEmpty(strThisPath)) {
            return "";
        }

        if (strThisPath.indexOf("http://") == 0 || strThisPath.indexOf("https://") == 0 || strThisPath.indexOf("ftp://") == 0 || strThisPath.indexOf("://") > 0 || strThisPath.indexOf("?") == 0) {
            return strThisPath;
        }

        if (!strThisPath.startsWith("/".concat(getContextPath()).concat("/"))) {
            strThisPath = "/".concat(getContextPath()).concat("/").concat(strThisPath);
        }

        if (strThisPath.indexOf("?") > -1 || strThisPath.indexOf("#") > -1) {
            return CheckUrl(strThisPath);
        } else {
            return CheckUrl(strThisPath.concat("/"));
        }

    }

    /**
     * URL 생성
     *
     * @param request
     *            리퀘스트
     * @param path
     *            경로
     * @return
     */
    public static String MakeUrl(HttpServletRequest request, String path) {

        String strThisPath = path;

        if (request == null) {
            return null;
        }

        if (StringUtil.IsEmpty(strThisPath)) {
            return "";
        }

        if (strThisPath.indexOf("http://") == 0 || strThisPath.indexOf("https://") == 0 || strThisPath.indexOf("ftp://") == 0 || strThisPath.indexOf("://") > 0 || strThisPath.indexOf("?") == 0) {
            return strThisPath;
        }

        String context_path = request.getContextPath();
        if (context_path.startsWith("/"))
            context_path = context_path.substring(1);
        if (context_path.endsWith("/"))
            context_path = context_path.substring(0, context_path.length() - 1);

        if (!strThisPath.startsWith("/".concat(context_path).concat("/"))) {
            strThisPath = "/".concat(context_path).concat("/").concat(strThisPath);
        }

        if (strThisPath.indexOf("?") > -1 || strThisPath.indexOf("#") > -1) {
            return CheckUrl(strThisPath);
        } else {
            return CheckUrl(strThisPath + "/");
        }

    }

	/**
	 * 큰툴 뷰 종류 정보
	 * @return
	 */
	public String getViewType()
	{
		String ret = (String) super.get("@KNTOOL_DATA__VIEW_TYPE");
		if (StringUtil.IsEmpty(ret)) return AbstractController.view_popup;
		return ret;
	}
	
    /**
     * 큰툴 뷰 종류 정보 저장
     *
     * @param type
     */
    public void setViewType(String type) {
        super.put("@KNTOOL_DATA__VIEW_TYPE", type);
    }

    /**
     * 큰툴 메뉴 여부
     *
     * @return
     */
    public boolean IsKntoolMenu() {
        if (super.get("@KNTOOL_MENU") == null) {
            String val = (String) request.getAttribute("@KNTOOL_MENU");
            super.put("@KNTOOL_MENU", "Y".equals(val));
        }
        return (Boolean) super.get("@KNTOOL_MENU");
    }

    /**
     * 큰툴 메뉴 경로 처리
     *
     * @param depth
     * @return
     */
    public void KntoolMenuPath() {
        if (super.get("@KNTOOL_MENU_PATH") == null) {
            /*
             * String val = null;
             * int idx = 1;
             * do
             * {
             * val = (String) request.getAttribute("@KNTOOL_MENU_PATH_"+ idx);
             * val = StringUtil.IsEmpty(val) ? "" : DecodeMenuName(val);
             * super.put("@KNTOOL_MENU_PATH_"+ idx, val);
             * idx++;
             * }while(!StringUtil.IsEmpty(val));
             * super.put("@KNTOOL_MENU_PATH", "Y");
             */
            int idx = 1;
            do {
                if (StringUtil.IsEmpty(request.getAttribute("@KNTOOL_MENU_PATH_" + idx)))
                    break;
                super.put("@KNTOOL_MENU_PATH_" + idx, DecodeMenuName((String) request.getAttribute("@KNTOOL_MENU_PATH_" + idx)));
                idx++;
            } while (idx < 100);

            super.put("@KNTOOL_MENU_PATH", "Y");
        }
    }

    /**
     * 큰툴 메뉴 뒤로 한칸 이동
     *
     * @param rootPath
     */
    public void KntoolMenuPathMoveNext(String rootPath) {
        String val1 = rootPath;
        String val2 = null;
        int idx = 1;

        do {
            val2 = KntoolMenuPath(idx);
            super.put("@KNTOOL_MENU_PATH_" + idx, val1);
            val1 = val2;
            idx++;
        } while (!StringUtil.IsEmpty(val1));
    }

    /**
     * 큰툴 메뉴 경로 호출
     *
     * @param depth
     * @return
     */
    public String KntoolMenuPath(int depth) {
        if (super.get("@KNTOOL_MENU_PATH") == null)
            KntoolMenuPath();

        return (String) super.get("@KNTOOL_MENU_PATH_" + depth);
    }

    /**
     * 큰툴 메뉴 합친 경로
     *
     * @return
     */
    public String KntoolJoinMenuPath() {
        return KntoolJoinMenuPath(1);
    }

    /**
     * 큰툴 메뉴 합친 경로
     *
     * @param printSitePath
     *            사이트 경로 출력 여부
     * @return
     */
    public String KntoolJoinMenuPath(boolean printSitePath) {
        if (printSitePath)
            return KntoolJoinMenuPath(1);
        else
            return KntoolJoinMenuPath(2);
    }

    /**
     * 큰툴 메뉴 합친 경로
     *
     * @param startDepth
     *            시작깊이(1부터)
     * @return
     */
    public String KntoolJoinMenuPath(int startDepth) {
        String key = "@KNTOOL_JOIN_MENU_PATH_".concat(String.valueOf(startDepth));
        if (super.get(key) == null) {
            String val = null;
            StringBuffer joinPath = new StringBuffer();
            int idx = startDepth;
            if (idx < 1)
                idx = 1;
            do {
                val = KntoolMenuPath(idx);
                if (StringUtil.IsEmpty(val))
                    break;

                joinPath.append("/").append(val);

                idx++;
            } while (!StringUtil.IsEmpty(val));

            super.put(key, joinPath.toString());
        }

        return (String) super.get(key);
    }

    /**
     * 큰툴 메뉴 합친 경로 인코딩 처리
     *
     * @param printSitePath
     *            사이트 경로 출력 여부
     * @return
     */
    public String KntoolJoinMenuPathEnc() {
        return KntoolJoinMenuPathEnc(1);
    }

    /**
     * 큰툴 메뉴 합친 경로 인코딩 처리
     *
     * @param printSitePath
     *            사이트 경로 출력 여부
     * @return
     */
    public String KntoolJoinMenuPathEnc(boolean printSitePath) {
        if (printSitePath)
            return KntoolJoinMenuPathEnc(1);
        else
            return KntoolJoinMenuPathEnc(2);
    }

    /**
     * 큰툴 메뉴 합친 경로 인코딩 처리
     *
     * @param startDepth
     * @return
     */
    public String KntoolJoinMenuPathEnc(int startDepth) {
        String key = "@KNTOOL_JOIN_MENU_PATH_ENC_".concat(String.valueOf(startDepth));
        if (super.get(key) == null) {
            String val = null;
            StringBuffer joinPath = new StringBuffer();
            int idx = startDepth;
            if (idx < 1)
                idx = 1;
            do {
                val = KntoolMenuPath(idx);
                if (StringUtil.IsEmpty(val))
                    break;

                joinPath.append("/").append(EncodeMenuName(val));

                idx++;
            } while (!StringUtil.IsEmpty(val));

            super.put(key, joinPath.toString());
        }

        return (String) super.get(key);
    }

    /**
     * 큰툴 사이트 정보 저장 키 값
     */
    public static final String KEY_KNTOOL_SITE_INFO = "@KNTOOL_SITE_INFO";

    /**
     * 큰툴 사이트 정보
     *
     * @return SiteVo 로 캐스팅해서 사용하세요
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T> T KntoolSiteInfo() {
        return (T) super.get(KEY_KNTOOL_SITE_INFO);
    }

    /**
     * 큰툴 사이트 정보 저장
     *
     * @param siteData
     */
    public void setKntoolSiteInfo(Object siteData) {
        super.put(KEY_KNTOOL_SITE_INFO, siteData);
    }

    /**
     * 큰툴 메뉴 정보 저장 키 값
     */
    public static final String KEY_KNTOOL_MENU_INFO = "@KNTOOL_MENU_INFO";

    /**
     * 큰툴 메뉴 정보
     *
     * @return MenuVo 로 캐스팅해서 사용하세요
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T> T KntoolMenuInfo() {
        return (T) super.get(KEY_KNTOOL_MENU_INFO);
    }

    /**
     * 큰툴 메뉴 정보 저장
     *
     * @param siteData
     */
    public void setKntoolMenuInfo(Object menuData) {
        super.put(KEY_KNTOOL_MENU_INFO, menuData);
    }

    /**
     * 큰툴 모듈 설정 정보 저장 키 값
     */
    public static final String KEY_KNTOOL_MODULE_CONFIG_INFO = "@KNTOOL_MODULE_CONFIG_INFO";

    /**
     * 큰툴 모듈 설정 정보
     *
     * @return ModuleConfigVo 로 캐스팅해서 사용하세요
     */
    @SuppressWarnings("unchecked")
    public <T> T HiscoModuleConfigInfo() {
        return (T) super.get(KEY_KNTOOL_MODULE_CONFIG_INFO);
    }

    /**
     * 큰툴 모듈 설정 정보 저장
     *
     * @param siteData
     */
    public void setKntoolModuleConfigInfo(Object moduleConfigData) {
        if (moduleConfigData == null)
            return;
        super.put("KEY_KNTOOL_MODULE_CONFIG_INFO", KEY_KNTOOL_MODULE_CONFIG_INFO);
        super.put(KEY_KNTOOL_MODULE_CONFIG_INFO, moduleConfigData);
    }

    /**
     * 대상에 파라미터 정보 삽입
     *
     * @param target
     */
    public void pushData(Object target) {
        if (target == null)
            return;

        Method[] methods = target.getClass().getMethods();
        String strMethodName;
        String strParamName;
        Object value;

        for (int i = 0; i < methods.length; i++) {
            strMethodName = methods[i].getName();

            if (strMethodName.startsWith("set")) {
                strParamName = strMethodName.substring(3);
                value = super.get(strParamName);

                if (value == null) {
                    strParamName = strParamName.substring(0, 1).toLowerCase() + strParamName.substring(1);
                    value = super.get(strParamName);
                }

                try {
                    target.getClass().getMethod(strMethodName, methods[i].getReturnType()).invoke(target, value);
                } catch (NoSuchMethodException e) {
                } catch (IllegalArgumentException e) {
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 권한 정보 저장
     *
     * @param key
     *            권한 명 키 정보
     * @param val
     *            권한 여부
     */
    public void setPermission(String key, Boolean val) {
        super.put("PERMISSION__" + key, val);
        request.setAttribute("PERMISSION__" + key, val);
    }

    /**
     * 권한 정보 불러오기
     *
     * @param key
     *            권한 명 키 정보
     * @return
     */
    public boolean getPermission(String key) {
        try {
            Boolean ret = (Boolean) super.get("PERMISSION__" + key);
            return ret;
        } catch (Exception e) {
            return false;
        }
    }

    // 모바일 여부 확인
    public boolean isMobile() {
        if (request.getAttribute("IsMobile") != null) {
            try {
                Boolean IsMobile = (Boolean) request.getAttribute("IsMobile");
                if (IsMobile != null)
                    return IsMobile;
            } catch (Exception e) {
            }
        }

        if (super.get("@IS_MOBILE") == null) {
            String UserAgent = getUserAgent().toLowerCase();

            if (UserAgent.indexOf("mobile") > -1 || UserAgent.indexOf("iphone") > -1 || UserAgent.indexOf("ipod") > -1 || UserAgent.indexOf("ipad") > -1 || UserAgent.indexOf("android") > -1 || UserAgent.indexOf("blackberry") > -1 || UserAgent.indexOf("iemobile") > -1 || UserAgent.indexOf("htc") > -1 || UserAgent.indexOf("server_ko_skt") > -1 || UserAgent.indexOf("sonyericssonx") > -1 || UserAgent.indexOf("skt") > -1)
                super.put("@IS_MOBILE", true);
            else
                super.put("@IS_MOBILE", false);
        }
        return (Boolean) super.get("@IS_MOBILE");
    }
}
