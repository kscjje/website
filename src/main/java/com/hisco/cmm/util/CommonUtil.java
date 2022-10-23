package com.hisco.cmm.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hisco.cmm.object.CamelMap;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;
import com.hisco.user.edcatnlc.vo.EdcRsvnMstVO;

import egovframework.com.cmm.util.EgovUserDetailsHelper;

/**
 * 편의를 위한 공통유틸
 *
 * @author 진수진
 * @since 2020.07.01
 * @version 1.0, 2020.07.01
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.01 최초작성
 */
public class CommonUtil {

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final Locale LOCALE = java.util.Locale.KOREAN;

    public static String enKey = "dlQmsdldutlsslaWkdrnldua";

    /**
     * 비밀번호 암호화
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptPassword(String data) throws Exception {
        if (data == null) {
            return "";
        } else {
            byte plainText[] = null;
            byte hashValue[] = null;
            plainText = data.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            hashValue = md.digest(plainText);
            return new String(Base64.encodeBase64(hashValue));
        }
    }

    /**
     * 숫자인지 체크하여 숫자이면 콤마까지 찍어서 리턴
     *
     * @param str
     * @return
     */
    public static String getCommaNumber(String str, String format) {
        try {

            DecimalFormat df = new DecimalFormat(format);

            return df.format(Double.parseDouble(str));
        } catch (NumberFormatException e) {
            return str;
        }
    }

    /**
     * 숫자인지 체크하여 숫자이면 콤마까지 찍어서 리턴
     *
     * @param str
     * @return
     */
    public static String getCommaNumber(Object str, String format) {
        return getCommaNumber(CommonUtil.getString(str), format);
    }

    /**
     * parameter 셋팅시 null 인경우 '' 를 리턴한다.
     *
     * @param param
     * @return
     */
    public static String nullToBlank(String param) {
        if (param == null || "null".equals(param)) {
            return "";
        }

        return param;
    }

    /**
     * 현재일시를 특정형식(yyyyMMddHHmmss)으로 반환
     *
     * @param as_format
     * @return String
     */
    public static String getDateTime(String format) {
        // 변수선언 및 초기화.
        Calendar oCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.KOREA);
        return dateFormat.format(oCalendar.getTime());
    }

    /**
     * 현재 시간을 특정형식으로 반환
     *
     * @param str
     * @return
     */
    public static String getTimeStr(Object timeObj) {
        return getTimeStr(timeObj.toString());
    }

    /**
     * 시간 형태로 변환 tt:mm:ss
     *
     * @param as_format
     * @return String
     */
    public static String getTimeStr(String timeStr) {
        String rtnStr = timeStr;

        if (rtnStr.length() == 4) {
            rtnStr = rtnStr.substring(0, 2) + ":" + rtnStr.substring(2, 4);
        } else if (rtnStr.length() == 6) {
            rtnStr = rtnStr.substring(0, 2) + ":" + rtnStr.substring(2, 4) + ":" + rtnStr.substring(4, 6);
        }

        return rtnStr;
    }

    /**
     * 어제일시를 특정형식(yyyyMMddHHmmss)으로 반환
     *
     * @param as_format
     * @return String
     */
    public static String getYesterday(String format) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, -1);

        // log.debug("어제 년: " + cal.get(Calendar.YEAR));
        // log.debug("어제 월: " + (cal.get(Calendar.MONTH) + 1));
        // log.debug("어제 일: " + cal.get(Calendar.DAY_OF_MONTH));

        // 24시간 전의 날짜, 시간, 시간대를
        // Sun Dec 10 13:50:52 KST 2006 이런 식으로 한 줄로 출력
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, LOCALE);
        return dateFormat.format(cal.getTime());
    }

    /**
     * 오늘로 부터 특정날짜를 더한 일시를 특정형식(yyyyMMddHHmmss)으로 반환
     *
     * @param as_format
     * @return String
     */
    public static String getDayfromToday(String format, int ii) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, ii);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, LOCALE);
        return dateFormat.format(cal.getTime());
    }

    /**
     * json 형식 문자열로 반환
     *
     * @param o
     * @return String
     */
    public static String rr(Object o) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        // Gson gson = new GsonBuilder().create();
        return gson.toJson(o).replace("\\\\", "\\");
    }

    /**
     * null, trim 처리 후 문자열 반환
     *
     * @param obj
     * @return String
     */
    public static String getString(Object obj) {
        if (obj == null) {
            return "";
        }

        return obj.toString().trim();
    }

    /**
     * null, trim 처리 후 특정 길이 이상이 되면 ... 추가 하여 문자열 반환
     *
     * @param obj
     * @param len
     * @return String
     */
    public static String getString(Object obj, int len) {
        if (obj == null) {
            return "";
        }

        return obj.toString().trim().length() > len
                ? obj.toString().trim().substring(0, len) + "..."
                : obj.toString().trim();
    }

    /**
     * null 인 경우 대체 문자열 반환
     *
     * @param obj
     * @param def
     * @return String
     */
    public static String getString(Object obj, String def) {
        if (obj == null) {
            return def;
        }

        return obj.toString();
    }

    /**
     * null 또는 빈값인 경우 대체 문자열 반환
     *
     * @param obj
     * @param def
     * @return String
     */
    public static String getStringEmpty(Object obj, String def) {
        if (obj == null || "".equals(obj.toString().trim())) {
            return def;
        }

        return obj.toString();
    }

    /**
     * object 를 숫자형식으로 반환
     *
     * @param obj
     * @return String
     **/
    public static int getInt(Object obj) {

        return getInt(obj, 0);
    }

    /**
     * object 를 숫자형식으로 반환
     *
     * @param obj
     * @return String
     **/
    public static int getInt(Object obj, int defaultNo) {
        if (obj == null || "".equals(obj.toString().trim())) {
            return defaultNo;
        }

        return Integer.parseInt(obj.toString().trim());
    }

    /**
     * object 를 숫자형식으로 반환
     *
     * @param obj
     * @return String
     **/
    public static long getLong(Object obj) {
        if (obj == null || "".equals(obj.toString().trim())) {
            return 0;
        }

        return Long.parseLong(obj.toString().trim());
    }

    /**
     * 문자열중 지정한 문자열을 찾아서 새로운 문자열로 바꾸는 함수 origianl 대상 문자열 oldstr 찾을 문자열 newstr 바꿀 문자열
     *
     * @param original
     * @param oldstr
     * @param newstr
     *            return 바뀐 결과
     */
    public static String replace(String original, String oldstr, String newstr) {
        String convert = "";
        int pos = 0;
        int begin = 0;
        pos = original.indexOf(oldstr);

        if (pos == -1) {
            return original;
        }
        while (pos != -1) {
            convert = convert + original.substring(begin, pos) + newstr;
            begin = pos + oldstr.length();
            pos = original.indexOf(oldstr, begin);
        }
        convert = convert + original.substring(begin);
        return convert;
    }

    /**
     * 내용중 HTML 툭수기호인 문자를 HTML 특수기호 형식으로 변환합니다. htmlstr 바꿀 대상인 문자열 return 바뀐 결과 PHP의
     * htmlspecialchars와 유사한 결과를 반환합니다.
     *
     * @param htmlstr
     */
    public static String convertHtmlchars(String htmlstr)

    {
        String tempHtml = htmlstr;
        tempHtml = tempHtml.replaceAll("\\<", "&lt;");
        tempHtml = tempHtml.replaceAll("\\>", "&gt;");
        tempHtml = tempHtml.replaceAll("\\\"", "&quot;");
        tempHtml = tempHtml.replaceAll("&nbsp;", "&amp;nbsp;");
        return tempHtml;

    }

    /**
     * html 태그를 제외한 문자열 반환
     * return String
     *
     * @param Object
     */
    @SuppressWarnings("deprecation")
    public static String unscript(Object obj) {
        return unscript(ObjectUtils.toString(obj));
    }

    /**
     * html 태그를 제외한 문자열 반환
     * return String
     *
     * @param data
     */
    public static String unscript(String data) {
        if ((data == null) || (data.trim().equals(""))) {
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

    /**
     * 현재 파일의 물리적 위치 반환
     * return String
     */
    public static String getPhysicalWebrootPath() throws Exception {
        String webRootPath = CommonUtil.class.getResource("/").getPath();
        webRootPath = webRootPath.substring(0, webRootPath.indexOf("WEB-INF") - 1);

        return webRootPath;
    }

    /**
     * 날짜 형식에 맞는 Calendar 를 리턴
     *
     * @param dt
     * @return Calendar
     */
    private static Calendar getCalendar(String dt) {
        Calendar cal = Calendar.getInstance();

        int yyyy = 0;
        int smm = 0;
        int dd = 0;

        if (dt.length() == 10) {
            yyyy = Integer.parseInt(dt.substring(0, 4));
            smm = Integer.parseInt(dt.substring(5, 7)) - 1;
            dd = Integer.parseInt(dt.substring(8, 10));
        } else {
            yyyy = Integer.parseInt(dt.substring(0, 4));
            smm = Integer.parseInt(dt.substring(4, 6)) - 1;
            dd = Integer.parseInt(dt.substring(6, 8));
        }

        int hh = 0;
        int mm = 0;
        int ss = 0;

        if (dt.length() == 8 || dt.length() == 10) {
            cal.set(yyyy, smm, dd);
        } else if (dt.length() == 12) {
            hh = Integer.parseInt(dt.substring(8, 10));
            mm = Integer.parseInt(dt.substring(10, 12));
            cal.set(yyyy, smm, dd, hh, mm);
        } else if (dt.length() == 14) {
            hh = Integer.parseInt(dt.substring(8, 10));
            mm = Integer.parseInt(dt.substring(10, 12));
            ss = Integer.parseInt(dt.substring(12, 14));
            cal.set(yyyy, smm, dd, hh, mm, ss);
        }

        return cal;
    }

    /**
     * 오늘날짜 : yyyyMMdd
     *
     * @return
     */
    public static String getDate() {
        return getDate(YYYYMMDD);
    }

    /**
     * 오늘날짜
     *
     * @param dateFormat
     *            포멧
     * @return
     */
    public static String getDate(String dateFormat) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, LOCALE);
        return sdf.format(c.getTime());
    }

    /**
     * 조건일 주의 시작일
     *
     * <pre>
     * 예제)
     * String sampleDate = getFirstDayOfWeek(&quot;20081010&quot;);
     * </pre>
     *
     * @param dt
     * @return
     * @throws Exception
     */
    public static String getFirstDayOfWeek(String dt) throws Exception {
        String dateFormat = getDateFormat(dt);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, LOCALE);

        Calendar c = getCalendar(dt);
        c.add(Calendar.DATE, (c.get(Calendar.DAY_OF_WEEK) - 1) * -1);

        return sdf.format(c.getTime());
    }

    /**
     * 조건일 주의 종료일
     *
     * <pre>
     * 예제)
     * String sampleDate = getLastDayOfWeek(&quot;20081010&quot;);
     * </pre>
     *
     * @param dt
     *            날짜
     * @return
     * @throws Exception
     */
    public static String getLastDayOfWeek(String dt) throws Exception {
        String dateFormat = getDateFormat(dt);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, LOCALE);

        Calendar c = getCalendar(dt);
        c.add(Calendar.DATE, 7 - c.get(Calendar.DAY_OF_WEEK));

        return sdf.format(c.getTime());
    }

    /**
     * 기본포멧 일 더하기 세가지 포멧만 허용(yyyyMMdd, yyyyMMddHHmm, yyyyMMddHHmmss)
     *
     * <pre>
     * 예제)
     * String sampleDate = addDate(&quot;20081010&quot;, 1);
     * </pre>
     *
     * @param dt
     *            날짜
     * @param addNum
     *            더한 날
     * @return
     */
    public static String addDate(String dt, int addNum) throws Exception {
        return addDate(dt, addNum, Calendar.DATE);
    }

    /**
     * 날짜 더하기 세가지 포멧만 허용(yyyyMMdd, yyyyMMddHHmm, yyyyMMddHHmmss)
     *
     * <pre>
     * 예제)
     * String sampleDate = addDate(&quot;20081010&quot;, 1);
     * </pre>
     *
     * @param dt
     *            날짜
     * @param addNum
     *            더한 날
     * @param calConst
     *            Calendar.DATE, Calendar.MONTH
     * @return
     * @throws Exception
     */
    public static String addDate(String dt, int addNum, int calConst) throws Exception {
        String dateFormat = getDateFormat(dt);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, LOCALE);

        Calendar c = getCalendar(dt);
        c.add(calConst, addNum);

        return sdf.format(c.getTime());
    }

    /**
     * 조건일 월의 시작일
     *
     * <pre>
     * 예제)
     * String sampleDate = getFirstDayOfMonth(&quot;20081010&quot;);
     * </pre>
     *
     * @param dt
     *            날짜
     * @return
     * @throws Exception
     */
    public static String getFirstDayOfMonth(String dt) throws Exception {
        String dateFormat = getDateFormat(dt);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, LOCALE);

        String dt2 = dt.substring(0, 6) + "01" + dt.substring(8);
        Calendar c = getCalendar(dt2);

        return sdf.format(c.getTime());
    }

    /**
     * 조건일 월의 종료일
     *
     * <pre>
     * 예제)
     * String sampleDate = getLastDayOfMonth(&quot;20081010&quot;);
     * </pre>
     *
     * @param dt
     *            날짜
     * @return
     * @throws Exception
     */
    public static String getLastDayOfMonth(String dt) throws Exception {
        String dateFormat = getDateFormat(dt);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, LOCALE);

        Calendar c = getCalendar(dt);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));

        return sdf.format(c.getTime());
    }

    /**
     * 날짜에 해당하는 포멧을 반환 날짜문자열 길이로 세가지중 한가지 타입선택 (yyyyMMdd, yyyyMMddHHmm,
     * yyyyMMddHHmmss)
     *
     * @param dt
     * @return
     * @throws Exception
     */
    private static String getDateFormat(String dt) throws Exception {
        int dtlen = dt.length();
        String dateFormat = "";
        if (dtlen == 8) {
            dateFormat = YYYYMMDD;
        } else if (dtlen == 10) {
            dateFormat = "yyyy-MM-dd";
        } else if (dtlen == 12) {
            dateFormat = YYYYMMDDHHMM;
        } else if (dtlen == 14) {
            dateFormat = YYYYMMDDHHMMSS;
        }

        return dateFormat;
    }

    /**
     * String 과 infmt, outfmt 을 받아 날짜를 출력해준다. ex)
     * getDateFormat("20080901","yyyyMMdd","yyyy-MM-dd") -> 2008-09-01
     *
     * @param date
     * @param infmt
     * @param outfmt
     * @return
     */
    public static String getDateFormat(String date, String infmt, String outfmt) {
        try {
            // date 형식이 infmt 에 맞지 않다면 date 를 infmt 에 맞추어줌.
            StringBuffer sDate = new StringBuffer(date);

            int len = date.length();

            if (infmt.equals("yyyyMMddHHmm") && len < 12) {
                for (int i = len; i < 12; i++) {
                    sDate.append("0");
                }
            } else if (infmt.equals("yyyyMMddHHmmss") && len < 14) {
                for (int i = len; i < 14; i++) {
                    sDate.append("0");
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat(infmt, LOCALE);
            Date d = sdf.parse(sDate.toString());
            sdf.applyPattern(outfmt);

            return sdf.format(d);
        } catch (ParseException e) {
            return date;
        }
    }

    /**
     * 날짜에 해당하는 포멧을 반환 날짜문자열 길이로 세가지중 한가지 타입선택 (yyyyMMdd, yyyyMMddHHmm,
     * yyyyMMddHHmmss)
     *
     * @param dt
     * @return
     * @throws Exception
     */
    public static String getDateKorFormat(String dt) {
        int dtlen = dt.length();
        String dateFormat = "";
        if (dtlen == 8) {
            dateFormat = dt.substring(0, 4) + "." + dt.substring(4, 6) + "." + dt.substring(6, 8);
        } else if (dtlen == 10) {
            dateFormat = "yyyy-MM-dd";
        } else if (dtlen == 12) {
            dateFormat = YYYYMMDDHHMM;
        } else if (dtlen == 14) {
            dateFormat = YYYYMMDDHHMMSS;
        }

        return dateFormat;
    }

    /**
     * null 여부 체크
     *
     * @param Object
     * @return boolean
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            String v = (String) obj;
            if (v == null || v.isEmpty() || "".equals(v.trim()))
                return true;
            else
                return false;
        }
        return true;
    }

    /**
     * 쿠키 객체 생성
     *
     * @param response
     * @param sCookieName
     * @param sCookieValue
     * @return Cookie
     */
    public static Cookie setCookie(HttpServletResponse response, String sCookieValue)
            throws Exception {

        String tempVal = sCookieValue;
        if (StringUtils.isNotEmpty(sCookieValue)) {
            tempVal = URLEncoder.encode(sCookieValue, "UTF-8");
        }

        // 쿠키명은 config 에서 설정 하는걸로..
        String sCookieName = "";
        Cookie oCookie = new Cookie(sCookieName, tempVal);
        oCookie.setPath("/");
        oCookie.setSecure(true);
        oCookie.setHttpOnly(true);
        return oCookie;
    }

    /**
     * 쿠키 객체 생성
     *
     * @param response
     * @param sCookieName
     * @param sCookieValue
     * @return
     */
    public static void setRpCookie(HttpServletResponse response, String sCookieValue)
            throws Exception {
        Cookie oCookie = setCookie(response, sCookieValue);
        response.addCookie(oCookie);
    }

    /**
     * 쿠키 객체 생성
     *
     * @param response
     * @param sCookieName
     * @param sCookieValue
     * @param iMaxAge
     * @return
     */
    public static void setRpCookie(HttpServletResponse response, String sCookieValue, int iMaxAge)
            throws Exception {
        Cookie oCookie = setCookie(response, sCookieValue);
        oCookie.setMaxAge(iMaxAge);
        response.addCookie(oCookie);
    }

    /**
     * 쿠키 객체 생성
     *
     * @param response
     * @param sCookieName
     * @param sCookieValue
     * @param sDomain
     * @return
     */
    public static void setRpCookie(HttpServletResponse response, String sCookieValue,
            String sDomain) throws Exception {
        Cookie oCookie = setCookie(response, sCookieValue);
        oCookie.setDomain(sDomain);
        response.addCookie(oCookie);
    }

    /**
     * 쿠키 객체 생성
     *
     * @param response
     * @param sCookieName
     * @param sCookieValue
     * @param iMaxAge
     * @param sDomain
     * @return
     */
    public static void setRpCookie(HttpServletResponse response, String sCookieValue, int iMaxAge,
            String sDomain) throws Exception {
        Cookie oCookie = setCookie(response, sCookieValue);
        oCookie.setMaxAge(iMaxAge);
        oCookie.setDomain(sDomain);
        response.addCookie(oCookie);
    }

    /**
     * 쿠키 객체 반환
     *
     * @param request
     * @param sCookieName
     * @return String
     */
    public static String getRqCookie(HttpServletRequest request, String sCookieName) throws Exception {
        String sCookieValue = "";

        Cookie[] aCookies = request.getCookies();

        if (aCookies != null) {
            for (int i = 0; i < aCookies.length; i++) {
                // log.debug("### "+aCookies[i].getName() + ":" +
                // aCookies[i].getValue());
                if (sCookieName.equals(aCookies[i].getName())) {
                    // sCookieValue = java.net.URLDecoder.decode(aCookies[i].getValue()); //JDK1.4
                    // 이하 버전
                    sCookieValue = URLDecoder.decode(aCookies[i].getValue(), "UTF-8"); // JDK1.4 이상 버전
                    break;
                }
            }
        }
        return sCookieValue;
    }

    /**
     * 쿠키 삭제
     *
     * @param request
     * @param sCookieName
     * @return
     */
    public static void setClearCookie(HttpServletResponse response, String sCookieName) throws Exception {
        setRpCookie(response, "", 0);
    }

    /**
     * json 문자열 반환
     *
     * @param Object
     * @return
     */
    public static String printPretty(Object o) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        return gson.toJson(o).replace("\\\\", "\\");
    }

    /**
     * 모바일 디바이스 여부 체크
     *
     * @param userAgentHeader
     * @return String
     */
    public static String getMobileDeviceFlag(String userAgentHeader) {
        String mobileCheckHeader = "";
        // log.debug("userAgent"+userAgent);
        String[] browserHeader = { "iPhone", "iPad", "iPod", "Android", "Windows CE", "BlackBerry", "Symbian", "Windows Phone", "webOS", "Opera Mini", "Opera Mobi", "POLARIS", "IEMobile", "lgtelecom", "nokia", "SonyEricsson" };
        for (int i = 0; i < browserHeader.length; i++) {
            if (userAgentHeader.matches(".*" + browserHeader[i] + ".*")) {
                mobileCheckHeader = "Y";
                break;
            }
        }

        return mobileCheckHeader;
    }

    /**
     * Get First Exception
     *
     * @param StackTraceElement
     * @return
     */
    public static String[] getExcepFirstValue(StackTraceElement[] stackElemt) {

        String strResultArr[] = new String[4];

        strResultArr[0] = stackElemt[0].getClassName();
        strResultArr[1] = stackElemt[0].getFileName();
        strResultArr[2] = stackElemt[0].getMethodName();
        strResultArr[3] = Integer.toString(stackElemt[0].getLineNumber());

        return strResultArr;

    }

    /**
     * <pre>
     * null일 경우 처리
     * </pre>
     *
     * @param void
     * @return
     *         author : mysoftman@daum.net , 2020. 09. 06.
     */
    public static String ClearStrNull2Space(Object astrObj) {

        String strReturnVal = "";
        String strObjValue = "";

        if (isEmpty(astrObj)) {
            strReturnVal = "";
        } else {

            strObjValue = astrObj.toString();
            if ("null".equals(strObjValue)) {
                strReturnVal = "";
            } else {
                strReturnVal = strObjValue;
            }
        }

        return strReturnVal;

    }

    /**
     * <pre>
     * null일 경우 처리
     * </pre>
     *
     * @param void
     * @return
     *         author : mysoftman@daum.net , 2020. 09. 06.
     */
    public static long DoubleToLongCalc(double amount, String unit, String gbn) {

        /**
         * unit : 1010 : 원단위
         * 1020 : 십단위
         * 1030 : 백단위
         * gbn : 1000 : 없음
         * 1010 : 절상
         * 1020 : 절사
         */
        long dcAmountLong = 0;
        double tempAmount = amount;
        if ("1000".equals(gbn)) {
            // 없음 소숫점만 없앰
            dcAmountLong = Math.round(amount);
        } else if ("1010".equals(unit)) {
            tempAmount = amount / 10.0;
            if ("1010".equals(gbn)) {
                dcAmountLong = (long) Math.ceil(tempAmount);
            } else if ("1020".equals(gbn)) {
                dcAmountLong = (long) Math.floor(tempAmount);
            }

            dcAmountLong = dcAmountLong * 10;
        } else if ("1020".equals(unit)) {
            tempAmount = amount / 100.0;

            if ("1010".equals(gbn)) {
                dcAmountLong = (long) Math.ceil(tempAmount);
            } else if ("1020".equals(gbn)) {
                dcAmountLong = (long) Math.floor(tempAmount);
            }
            dcAmountLong = dcAmountLong * 100;
        } else if ("1030".equals(unit)) {
            tempAmount = amount / 1000.0;
            if ("1010".equals(gbn)) {
                dcAmountLong = (long) Math.ceil(tempAmount);
            } else if ("1020".equals(gbn)) {
                dcAmountLong = (long) Math.floor(tempAmount);
            }
            dcAmountLong = dcAmountLong * 1000;

        } else {
            dcAmountLong = Math.round(tempAmount);
        }

        return dcAmountLong;

    }

    /**
     * <pre>
     * 접근 Device 종류 조회
     * </pre>
     *
     * @param void
     * @return
     *         author : mysoftman@daum.net , 2020. 09. 26.
     */
    public static String fn_isDevice(HttpServletRequest req) {

        String strIsDevice = "";
        String strUserAgent = req.getHeader("User-Agent").toUpperCase();

        if (strUserAgent.indexOf("MOBILE") > -1) {

            if (strUserAgent.indexOf("PHONE") == -1) {
                strIsDevice = "MOBILE";
            } else {
                strIsDevice = "TABLET";
            }

        } else {
            strIsDevice = "PC";
        }

        return strIsDevice;

    }

    /**
     * <pre>
     * 숫자 콤마 추가
     * </pre>
     *
     * @param void
     * @return
     *         author : mysoftman@daum.net , 2020. 09. 26.
     */
    public static String AddComma(Object strVal) {

        String returnVal = String.valueOf(strVal);
        if (returnVal == null) {
            return "";
        } else {
            DecimalFormat formatter = new DecimalFormat("###,###");

            returnVal = formatter.format(Integer.parseInt(returnVal));

            return returnVal;
        }
    }

    /**
     * <pre>
     * 환불 정보 text  표기
     * </pre>
     *
     * @param CamelMap
     * @return
     *         author : mysoftman@daum.net , 2020. 09. 26.
     */
    public static String RuleText(CamelMap ruleMap) {
        if (ruleMap == null) {
            return "";
        } else {
            int rfndNofday = 0;
            if (ruleMap.get("rfndNofday") != null) {
                rfndNofday = Integer.parseInt(String.valueOf(ruleMap.get("rfndNofday")));
            }
            String rfndRate = String.valueOf(ruleMap.get("rfndRate"));
            if (rfndRate == null || rfndRate.equals(""))
                rfndRate = "100";

            String endTime = CommonUtil.getString(ruleMap.get("rfndEtime"));
            if (endTime != null && !endTime.equals("")) {
                endTime = endTime.substring(0, 2) + "시 " + endTime.substring(2, 4) + "분";
            }

            if (rfndNofday < 1) {
                return "(당일 시작 시간 전까지 취소 가능)";
            } else {
                return "(" + rfndNofday + "일 전 " + endTime + " 까지 " + rfndRate + "% 환불 가능)";
            }
        }

    }

    /**
     * <pre>
     * 교육시간 text  표기
     * </pre>
     *
     * @param CamelMap
     * @return
     *         author : mysoftman@daum.net , 2020. 09. 26.
     */
    public static String EduPeriodTime(CamelMap camelMap) {
        if (camelMap == null) {
            return "";
        } else {
            String strEdcProgmType = String.valueOf(camelMap.get("edcProgmType"));
            if (strEdcProgmType.equals("1001")) {

                String time = CommonUtil.getString(camelMap.get("edcSdatetime"));
                String time2 = CommonUtil.getString(camelMap.get("edcEdatetime"));

                return time.substring(0, 10) + " ~ " + time2.substring(0, 10) + " (" + time.substring(11, 16) + " ~ " + time2.substring(11, 16) + ")";

            } else if (strEdcProgmType.equals("3001")) {
                String time = CommonUtil.getString(camelMap.get("edcEtime"));
                if (time.length() == 4)
                    time = time.substring(0, 2) + ":" + time.substring(2, 4);
                return camelMap.get("edcSdatetime") + " ~ " + time;
            } else {
                return camelMap.get("edcSdatetime") + " ~ " + camelMap.get("edcEdatetime");
            }
        }

    }

    public static String EduPeriodTime(EdcProgramVO vo) {
        if (vo == null) {
            return "";
        } else {
            String strEdcProgmType = String.valueOf(vo.getEdcProgmType());
            if (strEdcProgmType.equals("1001")) {

                String time = CommonUtil.getString(vo.getEdcSdatetime());
                String time2 = CommonUtil.getString(vo.getEdcEdatetime());

                return time.substring(0, 10) + " ~ " + time2.substring(0, 10) + " (" + time.substring(11, 16) + " ~ " + time2.substring(11, 16) + ")";

            } else if (strEdcProgmType.equals("3001")) {
                String time = CommonUtil.getString(vo.getEdcEtime());
                if (time.length() == 4)
                    time = time.substring(0, 2) + ":" + time.substring(2, 4);
                return vo.getEdcSdatetime() + " ~ " + time;
            } else {
                return vo.getEdcSdatetime() + " ~ " + vo.getEdcEdatetime();
            }
        }

    }

    /**
     * <pre>
     * 교육기간 text  표기
     * </pre>
     *
     * @param CamelMap
     * @return
     *         author : mysoftman@daum.net , 2020. 09. 26.
     */
    public static String EduPeriodDate(EdcRsvnMstVO camelMap, String timeYn) {
        if (camelMap == null) {
            return "";
        } else {
            String strEdcProgmType = String.valueOf(camelMap.getEdcProgmType());
            String time1 = CommonUtil.getString(camelMap.getEdcReqStime());
            String time2 = CommonUtil.getString(camelMap.getEdcReqEtime());

            String date1 = CommonUtil.getString(camelMap.getEdcReqSdate());
            String date2 = CommonUtil.getString(camelMap.getEdcReqEdate());

            date1 = getDateFormat(date1, "yyyyMMdd", "yyyy.MM.dd");
            date2 = getDateFormat(date2, "yyyyMMdd", "yyyy.MM.dd");

            if (strEdcProgmType.equals("1001")) {
                if (timeYn.equals("Y")) {
                    return date1 + " ~ " + date2 + " (" + time1.substring(0, 2) + ":" + time1.substring(2, 4) + " ~ " + time2.substring(0, 2) + ":" + time2.substring(2, 4) + ")";
                } else {
                    return date1 + " ~ " + date2;
                }
            } else if (strEdcProgmType.equals("3001")) {

                return date1 + " " + time1.substring(0, 2) + ":" + time1.substring(2, 4) + " ~ " + time2.substring(0, 2) + ":" + time2.substring(2, 4);
            } else {
                return date1 + " " + time1.substring(0, 2) + ":" + time1.substring(2, 4) + " ~ " + date2 + " " + time2.substring(0, 2) + ":" + time2.substring(2, 4);
            }
        }

    }

    /**
     * <pre>
     * 교육기간 text  표기
     * </pre>
     *
     * @param CamelMap
     * @return
     *         author : mysoftman@daum.net , 2020. 09. 26.
     */
    public static String EduPeriodDate(EdcRsvnMstVO camelMap) {
        return EduPeriodDate(camelMap, "Y");

    }

    /**
     * 날짜를 yyyy.MM.dd HH:mi 포맷으로
     *
     * @param as_format
     * @return String
     */
    public static String getDateString(java.sql.Date regDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", LOCALE);
        return dateFormat.format(regDate.getTime());
    }

    /**
     * 날짜를 yyyy.MM.dd HH:mi 포맷으로
     *
     * @param as_format
     * @return String
     */
    public static String getDateString(java.sql.Timestamp regDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", LOCALE);
        return dateFormat.format(regDate.getTime());
    }

    /**
     * 날짜를 yyyyMMddHHmiss 포맷으로
     *
     * @param as_format
     * @return String
     */
    public static String getDateString2(java.sql.Date regDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", LOCALE);
        return dateFormat.format(regDate.getTime());
    }

    public static String getProfile() {
        String profile = StringUtils.defaultString(System.getenv("spring.profiles.myactive"), System.getProperty("spring.profiles.myactive"));

        if (StringUtils.isNotEmpty(profile)) {
            profile = profile.toLowerCase();
        }

        return profile;
    }

    /**
     * 관리자 hasRole
     *
     * @param as_format
     * @return String
     */
    public static boolean hasRole(String role) {
        List<String> authList = EgovUserDetailsHelper.getAuthorities();
        boolean flag = false;
        if (authList != null) {
            for (String auth : authList) {
                if (auth.equals(role))
                    flag = true;
            }
        }
        return flag;
    }

}
