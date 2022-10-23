package com.hisco.cmm.modules;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtil {

    /**
     * SQL Injection 방지 코드
     *
     * @param val
     *            문자열
     * @return 코드 제거된 문자열
     */
    public static String SQLInjection(String val) {

        if (IsEmpty(val)) {
            return "";
        }

        return val.replaceAll("-{1,}", "-").replaceAll("%", "").replaceAll("'", "") // &#39;
                .replaceAll("\"", "") // &quot;
                .replaceAll(",", "") // &#44;
                .replaceAll("\\|", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\r\n", "")
                // .replaceAll("'","′") // 작은 따옴표를 ′ 로 치환
                .replaceAll("\\\\", "");
    }

    /**
     * long 를 문자열로 변환
     *
     * @param l
     *            숫자
     * @param def
     *            실패시 기본값
     * @return 변환된 문자열
     */
    public static String Long2String(long l, String def) {

        try {
            return Long.toString(l);
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * int 를 문자열로 변환
     *
     * @param n
     *            숫자
     * @param def
     *            실패시 기본값
     * @return 변환된 문자열
     */
    public static String Int2String(int n, String def) {

        try {
            return Integer.toString(n);
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * float 를 문자열로 변환
     *
     * @param f
     *            실수
     * @param def
     *            실패시 기본값
     * @return 변환된 문자열
     */
    public static String Float2String(float f, String def) {
        try {
            return Float.toString(f);
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * 문자열을 int(숫자)로 변환
     *
     * @param str
     *            문자열
     * @param def
     *            실패시 기본값
     * @return 변환된 숫자
     */
    public static int String2Int(String str, int def) {

        String strThisVal = str;

        if (IsEmpty(strThisVal)) {
            return def;
        }

        try {
            try {
                return Integer.parseInt(strThisVal, 10);
            } catch (Exception e) {
                try {
                    if (strThisVal.indexOf(".") > -1) {
                        strThisVal = strThisVal.substring(0, strThisVal.indexOf("."));
                    }
                    return Integer.valueOf(strThisVal.replaceAll("\\D", ""));
                } catch (Exception e2) {
                    return def;
                }
            }
        } catch (Exception e2) {
            return def;
        }
    }

    /**
     * 문자열을 long(숫자)로 변환
     *
     * @param str
     *            String
     * @param def
     *            long
     * @return long
     */
    public static long String2Long(String str, long def) {

        String strThisVal = str;

        if (IsEmpty(strThisVal)) {
            return def;
        }

        try {

            try {
                return Long.parseLong(strThisVal, 10);
            } catch (Exception e) {

                if (strThisVal.indexOf(".") > -1) {
                    strThisVal = strThisVal.substring(0, strThisVal.indexOf("."));
                }

                return Long.valueOf(strThisVal.replaceAll("[^\\-0-9]", ""));
            }

        } catch (Exception e) {
            return def;
        }
    }

    /**
     * 문자열을 long(숫자)로 변환
     *
     * @param str
     *            String
     * @param def
     *            long
     * @return long
     */
    public static long String2LongCheckLength(String str, long def) {
        if (IsEmpty(str))
            return def;
        if (str.length() > 20)
            return def;
        return String2Long(str, def);
    }

    /**
     * 문자열을 float(실수)로 변환
     *
     * @param str
     *            String
     * @param def
     *            long
     * @return long
     */
    public static float String2Float(String str, float def) {
        if (IsEmpty(str))
            return def;

        try {
            try {
                return Float.parseFloat(str);
            } catch (Exception e) {
                return Float.valueOf(str.replaceAll("[^\\-0-9\\.]", ""));
            }
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * byte 를 문자열로 변환
     *
     * @param byt
     *            byte 데이터
     * @return 변환된 문자열
     */
    public static String Byte2String(byte[] byt) {
        return Byte2String(byt, "UTF-8");
    }

    /**
     * byte 를 문자열로 변환
     *
     * @param byt
     * @param charset
     * @return
     */
    public static String Byte2String(byte[] byt, String charset) {
        if (byt == null || byt.length <= 0)
            return null;
        try {
            return new String(byt, charset);
        } catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
            return null;
        }
    }

    /**
     * Url 인코딩
     *
     * @param value
     * @param charSet
     * @return
     */
    public static String URLEncode(String value, String charSet) {
        if (IsEmpty(value))
            return null;

        try {
            return java.net.URLEncoder.encode(value.trim(), IsEmpty(charSet) ? "UTF-8" : charSet);
        } catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
            return value;
        }
    }

    /**
     * Url 디코딩
     *
     * @param value
     * @param charSet
     * @return
     */
    public static String URLDecode(String value, String charSet) {
        if (IsEmpty(value))
            return null;

        try {
            if (value.startsWith("%u")) {
                StringBuffer sb = new StringBuffer();
                String[] values = value.split("\\%u");
                for (int i = 0; i < values.length; i++) {
                    if (values[i].length() > 0) {
                        sb.append((char) Integer.parseInt(values[i], 16));
                    }
                }

                return sb.toString();
            } else {
                return java.net.URLDecoder.decode(value.trim(), IsEmpty(charSet) ? "UTF-8" : charSet);
            }
        } catch (Exception e2) {
            // e2.printStackTrace();
            return value;
        }
    }

    /**
     * Url 디코딩
     *
     * @param url
     *            주소값
     * @return String 디코딩된 주소값
     */
    public static String URLDecode(String url) {
        if (url == null || url.isEmpty())
            return "";

        if (url.indexOf("?") > -1) {
            int nSplit = url.indexOf("?");
            StringBuffer ret = new StringBuffer(url.substring(0, nSplit + 1));
            String[] preURL = url.substring(0, nSplit - 1).split("/");
            String[] nextURL = url.substring(nSplit + 1).split("&");

            if (preURL.length > 0) {
                ret.setLength(0);
                for (int i = 0; i < preURL.length; i++) {
                    try {
                        ret.append(java.net.URLDecoder.decode(preURL[i], "UTF-8"));
                        ret.append("/");
                    } catch (Exception e) {
                        ret.append(preURL[i]);
                        ret.append("/");
                    }
                }

                ret.append("?");
            }

            if (nextURL.length > 0) {
                for (int i = 0; i < nextURL.length; i++) {
                    if (nextURL[i].indexOf("=") > -1) {
                        String method = nextURL[i].substring(0, nextURL[i].indexOf("=") + 1);
                        String value = nextURL[i].substring(nextURL[i].indexOf("=") + 1);

                        try {
                            if (i > 0)
                                ret.append("&");
                            ret.append(method);
                            ret.append(java.net.URLDecoder.decode(value, "UTF-8")); // .replaceAll("\\+", "%20");
                        } catch (Exception e) {
                            if (i > 0)
                                ret.append("&");
                            ret.append(method);
                            ret.append(value);
                        }
                    } else {
                        if (i > 0)
                            ret.append("&");
                        ret.append(nextURL[i]);
                    }
                }
            }
            return ret.toString();
        } else if (url.indexOf("/") > -1) {
            StringBuffer ret = new StringBuffer();

            if (url.indexOf("/") > -1) {
                String[] arrUrl = url.split("/");
                ret.setLength(0);

                for (int i = 0; i < arrUrl.length; i++) {
                    try {
                        ret.append(java.net.URLDecoder.decode(arrUrl[i], "UTF-8"));
                        ret.append("/");
                    } catch (Exception e) {
                        ret.append(arrUrl[i]);
                        ret.append("/");
                    }
                }
            }

            return ret.toString();
        } else {
            try {
                return java.net.URLDecoder.decode(url, "UTF-8");
            } catch (Exception e) {
                return url;
            }
        }
    }

    /**
     * Html 인코딩
     *
     * @param source
     * @return
     */
    public static String HTMLEncode(String source) {
        if (IsEmpty(source))
            return null;
        else
            return HtmlUtils.htmlEscape(source.trim());
    }

    /**
     * Html 디코딩
     *
     * @param source
     * @return
     */
    public static String HTMLDecode(String source) {
        if (IsEmpty(source))
            return null;
        else
            return HtmlUtils.htmlUnescape(source);
    }

    /**
     * Html Tag 제거
     *
     * @param html
     * @return
     */
    public static String NoTag(String html) {
        if (IsEmpty(html))
            return "";
        // return html.trim().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").trim();
        return html.trim().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").replaceAll("<!(--|──|─)(\\s[a-zA-Z]*=[^>]*)?(--|──|─)>", "").replaceAll("<!(--|──|─)StartFragment(--|──|─)>", "").trim();
    }

    /**
     * 문자열 쪼개기
     *
     * @param string
     * @param split
     * @return
     */
    public static String[] StringSplit(String string, String split) {
        if (IsEmpty(string))
            return null;
        return string.split("[ ]{0,}".concat(split).concat("[ ]{0,}"));
    }

    /**
     * 문자열 배열 합치기
     *
     * @param arrString
     *            문자열 배열
     * @param strSplit
     *            구분 문자
     * @return 합친 문자열
     */
    public static String StringJoin(String[] arrString, String strSplit) {
        return StringJoin(arrString, strSplit, 0, 0);
    }

    /**
     * 문자열 배열 합치기
     *
     * @param arrString
     *            문자열 배열
     * @param strSplit
     *            구분 문자
     * @param minDepth
     *            최소 첨자
     * @param maxDepth
     *            최대 첨자
     * @return
     */
    public static String StringJoin(String[] arrString, String strSplit, int minDepth, int maxDepth) {

        int intThisMinDepth = minDepth;
        int intThisMaxDepth = maxDepth;

        if (arrString == null || arrString.length <= 0) {
            return "";
        }

        if (intThisMinDepth <= 0) {
            intThisMinDepth = 0;
        }

        if (intThisMaxDepth <= 0 || intThisMaxDepth >= arrString.length) {
            intThisMaxDepth = arrString.length - 1;
        }

        StringBuffer strBuffer = new StringBuffer();
        for (int i = intThisMinDepth; i <= intThisMaxDepth; i++) {

            if (!IsEmpty(arrString[i])) {

                if (strBuffer.length() > 0) {
                    strBuffer.append(strSplit);
                }

                strBuffer.append(arrString[i].trim());
            }
        }

        return strBuffer.toString();
    }

    /**
     * 문자열 배열 역순으로 합치기
     *
     * @param arrString
     *            문자열 배열
     * @param strSplit
     *            구분 문자
     * @return 합친 문자열
     */
    public static String StringJoinReverse(String[] arrString, String strSplit) {
        return StringJoinReverse(arrString, strSplit, 0, 0);
    }

    /**
     * 문자열 배열 역순으로 합치기
     *
     * @param arrString
     *            문자열 배열
     * @param strSplit
     *            구분 문자
     * @param minDepth
     *            최소 첨자
     * @param maxDepth
     *            최대 첨자
     * @return
     */
    public static String StringJoinReverse(String[] arrString, String strSplit, int minDepth, int maxDepth) {

        int intThisMinDepth = minDepth;
        int intThisMaxDepth = maxDepth;

        if (arrString == null || arrString.length <= 0) {
            return "";
        }

        if (intThisMinDepth <= 0) {
            intThisMinDepth = 0;
        }

        if (intThisMaxDepth <= 0 || intThisMaxDepth >= arrString.length) {
            intThisMaxDepth = arrString.length - 1;
        }

        StringBuffer strBuffer = new StringBuffer();
        for (int i = intThisMaxDepth; i >= intThisMinDepth; i--) {
            if (!IsEmpty(arrString[i])) {
                if (strBuffer.length() > 0) {
                    strBuffer.append(strSplit);
                }
                strBuffer.append(arrString[i].trim());
            }
        }

        return strBuffer.toString();
    }

    /**
     * 랜덤 문자열 출력(숫자, 영문 대소문)
     *
     * @param length
     *            int 문자열 길이
     * @return String
     */
    public static String RandomString(int length) {
        String pattern = "012345678901234567890123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer ret = new StringBuffer();
        int index = 0;
        int len = pattern.length();

        for (int i = 0; i < length; i++) {
            // index = (int)(Math.random() * len);
            java.security.SecureRandom tmpRandom = new java.security.SecureRandom();
            tmpRandom.setSeed(new java.util.Date().getTime());
            index = (int) (tmpRandom.nextDouble() * len);
            if (index > len)
                index -= len;
            ret.append(pattern.charAt(index));
        }

        return ret.toString();
    }

    /**
     * 랜덤 문자열 출력(숫자, 영문 대소문) - I 관련 제거용
     *
     * @param length
     *            int 문자열 길이
     * @return String
     */
    public static String RandomString_no_i(int length) {
        String pattern = "023456789023456789023456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ";
        StringBuffer ret = new StringBuffer();
        int index = 0;
        int len = pattern.length();

        for (int i = 0; i < length; i++) {
            // index = (int)(Math.random() * len);
            java.security.SecureRandom tmpRandom = new java.security.SecureRandom();
            tmpRandom.setSeed(new java.util.Date().getTime());
            index = (int) (tmpRandom.nextDouble() * len);
            if (index > len)
                index -= len;
            ret.append(pattern.charAt(index));
        }

        return ret.toString();
    }

    /**
     * 랜덤 숫자 출력
     *
     * @param length
     *            int 문자열 길이
     * @return String
     */
    public static String RandomStringNo(int length) {
        String pattern = "01234567890";
        StringBuffer ret = new StringBuffer();
        int index = 0;
        int len = pattern.length();

        for (int i = 0; i < length; i++) {
            Sleep.Call(1);
            // index = (int)(Math.random() * len);
            java.security.SecureRandom tmpRandom = new java.security.SecureRandom();
            tmpRandom.setSeed(new java.util.Date().getTime());
            index = (int) (tmpRandom.nextDouble() * len);
            if (index > len)
                index -= len;
            ret.append(pattern.charAt(index));
        }

        return ret.toString();
    }

    /**
     * 문자열 자르기
     *
     * @param str
     * @param length
     * @return
     */
    public static String CutString(String str, int length) {
        if (IsEmpty(str))
            return null;
        else if (str.length() > length)
            return str.substring(0, length);
        else
            return str;
    }

    /**
     * 문자열 빈값 여부
     *
     * @param val
     * @return
     */
    public static boolean IsEmpty(Object val) {
        if (val == null) {
            return true;
        } else if (val instanceof String) {
            String v = (String) val;
            if (v == null || v.isEmpty() || "".equals(v.trim()))
                return true;
            else
                return false;
        }
        return true;
    }

    /**
     * 널값 및 좌우 공백 제거
     *
     * @param val
     *            입력값
     * @return
     */
    public static String NotNull(String val) {
        return NotNull(val, "");
    }

    /**
     * 널값 및 좌우 공백 제거
     *
     * @param val
     *            입력값
     * @param def
     *            기본값
     * @return
     */
    public static String NotNull(String val, String def) {
        if (IsEmpty(val))
            return def;
        else
            return val.trim();
    }

    /**
     * 숫자로만 이루어져있는지 체크
     *
     * @param val
     * @return
     */
    public static boolean IsOnlyNumber(String val) {
        if (IsEmpty(val))
            return false;
        return IsEmpty(val.replaceAll("[0-9,.]", ""));
    }

    /**
     * 문자열 두개 동일 여부 확인
     *
     * @param val1
     * @param val2
     * @return
     */
    public static boolean Equals(String val1, String val2) {
        if (IsEmpty(val1) && IsEmpty(val2))
            return true;
        else if (IsEmpty(val1) || IsEmpty(val2))
            return false;
        else if (val1.trim().equals(val2.trim()))
            return true;
        else
            return false;
    }

    /**
     * 문자열 두개 동일 여부 확인(대소문 안가림)
     *
     * @param val1
     * @param val2
     * @return
     */
    public static boolean EqualsNotCase(String val1, String val2) {
        if (IsEmpty(val1) && IsEmpty(val2))
            return true;
        else if (IsEmpty(val1) || IsEmpty(val2))
            return false;
        else if (val1.trim().toLowerCase().equals(val2.trim().toLowerCase()))
            return true;
        else
            return false;
    }

    /**
     * 문자열 두개 중 시작 문자열 동일 여부 확인
     *
     * @param val1
     * @param val2
     * @return
     */
    public static boolean StartsWith(String val1, String val2) {
        if (IsEmpty(val1) && IsEmpty(val2))
            return true;
        else if (IsEmpty(val1) || IsEmpty(val2))
            return false;
        else if (val1.trim().startsWith(val2.trim()) || val2.trim().startsWith(val1.trim()))
            return true;
        else
            return false;
    }

    /**
     * 문자열 두개 중 시작 문자열 동일 여부 확인(대소문 안가림)
     *
     * @param val1
     * @param val2
     * @return
     */
    public static boolean StartsWithNotCase(String val1, String val2) {
        if (IsEmpty(val1) && IsEmpty(val2))
            return true;
        else if (IsEmpty(val1) || IsEmpty(val2))
            return false;
        else if (val1.trim().toLowerCase().startsWith(val2.trim().toLowerCase()) || val2.trim().toLowerCase().startsWith(val1.trim().toLowerCase()))
            return true;
        else
            return false;
    }

    /**
     * 맵에서 값 불러와서 해당 문자열에 동일한 값 여부 확인
     *
     * @param map
     *            맵 데이터
     * @param mapKey
     *            맵 키
     * @param split
     *            문자열 구분자
     * @param val
     *            확인 문자
     * @return
     */
    public static boolean ContainsValue(Map<String, Object> map, String mapKey, String split, String val) {
        return ContainsValue(map, mapKey, split, val, false);
    }

    /**
     * 맵에서 값 불러와서 해당 문자열에 동일한 값 여부 확인
     *
     * @param map
     *            맵 데이터
     * @param mapKey
     *            맵 키
     * @param split
     *            문자열 구분자
     * @param val
     *            확인 문자
     * @param notEmptyForceTrue
     *            비어 있지 않을때 강제로 true 반환 여부
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean ContainsValue(Map<String, Object> map, String mapKey, String split, String val,
            boolean notEmptyForceTrue) {
        if (map == null || map.size() <= 0 || !map.containsKey(mapKey) || IsEmpty(split) || IsEmpty(val))
            return false;

        if (notEmptyForceTrue) {
            if (map.get(mapKey) instanceof String) {
                if (!IsEmpty(map.get(mapKey)))
                    return true;
            } else if (map.get(mapKey) instanceof Map) {
                // if (map.get(mapKey) == null || ((Map) map.get(mapKey)).size() <= 0) return true;
                if (map.get(mapKey) != null && ((Map) map.get(mapKey)).size() > 0)
                    return true;
            } else if (map.get(mapKey) instanceof List) {
                // if (map.get(mapKey) == null || ((List) map.get(mapKey)).size() <= 0) return true;
                if (map.get(mapKey) != null && ((List) map.get(mapKey)).size() > 0)
                    return true;
            } else {
                if (map.get(mapKey) == null)
                    return true;
            }
        }

        if (map.get(mapKey) instanceof String) {
            return ContainsValue((String) map.get(mapKey), split, val);
        } else if (map.get(mapKey) instanceof Map) {
            return ((Map) map.get(mapKey)).containsValue(val);
        } else if (map.get(mapKey) instanceof List) {
            return ((List) map.get(mapKey)).contains(val);
        } else {
            return ContainsValue(map.get(mapKey).toString(), split, val);
        }
    }

    /**
     * 배열형 문자열 안에 동일한 값 여부 확인
     *
     * @param arrString
     *            배열화가 가능한 문자열
     * @param split
     *            문자열 구분자
     * @param val
     *            확인 문자
     * @return
     */
    public static boolean ContainsValue(String arrString, String split, String val) {
        if (IsEmpty(arrString) || IsEmpty(split) || IsEmpty(val))
            return false;

        return ContainsValue(arrString.split(split), val);
    }

    /**
     * 배열 안에 동일한 값 여부 확인
     *
     * @param arr
     *            문자 배열
     * @param val
     *            확인 문자
     * @return
     */
    public static boolean ContainsValue(String[] arr, String val) {

        String strThisVal = val;

        boolean ret = false;

        if (arr != null && arr.length > 0 && !IsEmpty(strThisVal)) {

            strThisVal = strThisVal.trim();

            for (int i = 0, length = arr.length; i < length; i++) {
                if (!IsEmpty(arr[i]) && Equals(arr[i].trim(), strThisVal)) {
                    ret = true;
                    break;
                }
            }
        }

        return ret;
    }

    /**
     * 배열 안에 동일한 값 여부 확인
     *
     * @param arr
     *            문자 배열
     * @param val
     *            확인 문자
     * @return
     */
    public static boolean ContainsValueNocase(String[] arr, String val) {

        String[] thisStrArr = arr;
        String strThisVal = val;

        boolean ret = false;

        if (thisStrArr != null && thisStrArr.length > 0 && !IsEmpty(strThisVal)) {

            strThisVal = strThisVal.trim();

            for (int i = 0, length = thisStrArr.length; i < length; i++) {
                if (!IsEmpty(thisStrArr[i]) && EqualsNotCase(thisStrArr[i], strThisVal)) {
                    ret = true;
                    break;
                }
            }
        }

        return ret;
    }

    /**
     * 문자열 배열 자르기
     *
     * @param arr
     * @param cutIndex
     * @return
     */
    public static String[] StringArrayCut(String[] arr, int cutIndex) {
        if (arr == null || arr.length - 1 < cutIndex)
            return arr;

        String[] ret = new String[cutIndex + 1];
        for (int i = 0; i <= cutIndex; i++)
            ret[i] = "" + arr[i];

        return ret;
    }

    /**
     * 문자열 배열 자르기
     *
     * @param arr
     * @param cutIndex
     * @return
     */
    public static long[] LongArrayCut(long[] arr, int cutIndex) {
        if (arr == null || arr.length - 1 < cutIndex)
            return arr;

        long[] ret = new long[cutIndex + 1];

        /*
         * for (int i = 0; i <= cutIndex; i++) {
         * ret[i] = arr[i];
         * }
         */

        System.arraycopy(arr, 0, ret, 0, arr.length);

        return ret;
    }

    /**
     * 소문자로 변경
     *
     * @param val
     * @return
     */
    public static String ToLowerCase(String val) {
        if (IsEmpty(val))
            return val;
        return val.toLowerCase();
    }

    /**
     * 대문자로 변경
     *
     * @param val
     * @return
     */
    public static String ToUpperCase(String val) {
        if (IsEmpty(val))
            return val;
        return val.toUpperCase();
    }

    /**
     * StringBuffer replaceAll
     *
     * @param buffer
     * @param find
     * @param replacement
     * @return
     */
    public static StringBuffer replaceAll(StringBuffer buffer, String find, String replacement) {
        if (buffer == null || buffer.length() <= 0 || find == null || replacement == null)
            return null;

        int bufidx = buffer.length() - 1;
        int offset = find.length();
        int findidx = 0;

        while (bufidx > -1) {
            findidx = offset - 1;
            while (findidx > -1) {
                if (bufidx == -1) {
                    // Done
                    return buffer;
                }

                if (buffer.charAt(bufidx) == find.charAt(findidx)) {
                    findidx--; // Look for next char
                    bufidx--;
                } else {
                    findidx = offset - 1; // Start looking again
                    bufidx--;

                    if (bufidx == -1) {
                        // Done
                        return buffer;
                    }
                    continue;
                }
            }

            buffer.replace(bufidx + 1, bufidx + 1 + offset, replacement);
            // start looking again
        }

        // No more matches
        return buffer;
    }

    public static String replaceAll(String string, String find, String replacement) {
        if (StringUtil.IsEmpty(string))
            return string;

        return string.replaceAll(find, replacement);
    }

    /**
     * 전화번호 체크
     *
     * @param tel
     * @return
     */
    public static String CheckTelNo(String tel) {
        if (IsEmpty(tel))
            return tel;

        try {
            return tel.replaceAll("\\D", "").replaceAll("^([0-9]{2,3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
        } catch (Exception e) {
            return tel;
        }
    }

    /**
     * 숫자 세자리마다 , 찍기
     *
     * @param val
     * @return
     */
    public static String PrintMoney(Object val) {
        if (val == null)
            return null;

        String val_str = String.valueOf(val);
        String format = "#,###";
        DecimalFormat df = new DecimalFormat(format);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setGroupingSeparator(',');
        df.setGroupingSize(3);
        df.setDecimalFormatSymbols(dfs);

        return (df.format(Double.parseDouble(val_str))).toString();
    }

    public static String EscapeJava(String val) {
        if (StringUtil.IsEmpty(val))
            return val;

        return StringEscapeUtils.escapeJava(val);
    }

    public static String EscapeJavaScript(String val) {
        if (StringUtil.IsEmpty(val))
            return val;

        // return StringEscapeUtils.escapeJavaScript(val); JYS 2021.06.06
        return StringEscapeUtils.escapeJava(val);
    }

    /*
     * public static String EscapeSql(String val) {
     * if (StringUtil.IsEmpty(val)) return val;
     * return StringEscapeUtils.escapeSql(val);
     * } JYS 2021.06.06
     */

    /*
     * JYS 2021.06.06
     * public static String EscapeXml(String val) {
     * if (StringUtil.IsEmpty(val)) return val;
     * return StringEscapeUtils.escapeXml(val);
     * }
     */

    /*
     * JYS 2021.06.06
     * public static String EescapeHtml(String val) {
     * if (StringUtil.IsEmpty(val)) return val;
     * return StringEscapeUtils.escapeHtml(val);
     * }
     */

    public static String EescapeCsv(String val) {
        if (StringUtil.IsEmpty(val))
            return val;

        return StringEscapeUtils.escapeCsv(val);
    }

    /**
     * Url 인코딩
     *
     * @param url
     *            주소값
     * @return 인코딩된 문자열
     */
    public static String URLEncode(String url) {

        StringBuffer ret = new StringBuffer();

        if (url.indexOf("?") > -1) {

            log.debug("URLEncode 1 = " + url);

            int nSplit = url.indexOf("?");
            String[] preURL = url.substring(0, nSplit).split("/");
            String[] nextURL = url.substring(nSplit + 1).split("&");

            ret.append(url.substring(0, nSplit + 1));

            if (preURL.length > 0) {

                ret.setLength(0);

                for (int i = 0; i < preURL.length; i++) {
                    if (i <= 2) {
                        ret.append(preURL[i]).append("/");
                    } else {
                        try {
                            ret.append(java.net.URLEncoder.encode(preURL[i], "UTF-8")).append("/");
                        } catch (Exception e) {
                            ret.append(preURL[i]).append("/");
                        }
                    }
                }

                ret.append("?");
            }

            if (nextURL.length > 0) {
                for (int i = 0; i < nextURL.length; i++) {
                    if (nextURL[i].indexOf("=") > -1) {
                        String method = nextURL[i].substring(0, nextURL[i].indexOf("=") + 1);
                        String value = nextURL[i].substring(nextURL[i].indexOf("=") + 1);

                        try {
                            if (i > 0)
                                ret.append("&");
                            ret.append(method);
                            ret.append(java.net.URLEncoder.encode(value, "UTF-8")); // .replaceAll("\\+", "%20");
                        } catch (Exception e) {
                            if (i > 0)
                                ret.append("&");
                            ret.append(method);
                            ret.append(value);
                        }
                    } else {
                        if (i > 0)
                            ret.append("&");
                        ret.append(nextURL[i]);
                    }
                }
            }

            if (ret.indexOf("/?") > -1) {
                ret.replace(ret.indexOf("/?"), ret.indexOf("/?") + 2, "?");
            }

            return ret.toString();

        } else if (url.indexOf("/") > -1) {

            ret.append(url);

            if (url.indexOf("https://") > -1) {
                ret.append("https://").append(url.replace("https://", ""));
            } else if (url.indexOf("http://") > -1) {
                ret.append("http://").append(url.replace("http://", ""));
            } else if (url.indexOf("ftp://") > -1) {
                ret.append("ftp://").append(url.replace("ftp://", ""));
            } else if (url.indexOf("mailto://") > -1) {
                ret.append("mailto://").append(url.replace("mailto://", ""));
            }

            String[] arrUrl = ret.toString().split("/");
            ret.setLength(0);

            for (int i = 0; i < arrUrl.length; i++) {
                if (i <= 2) {
                    ret.append(arrUrl[i]).append("/");
                } else {
                    try {
                        ret.append(java.net.URLEncoder.encode(arrUrl[i], "UTF-8")).append("/");
                    } catch (Exception e) {
                        ret.append(arrUrl[i]).append("/");
                    }
                }
            }

            if ("/".equals(ret.substring(ret.length() - 1, ret.length()))) {
                ret.setLength(ret.length() - 1);
            }

            return ret.toString();

        } else {

            log.debug("URLEncode 3 = " + url);

            try {
                return java.net.URLEncoder.encode(url, "UTF-8");
            } catch (Exception e) {
                return url;
            }

        }
    }
}