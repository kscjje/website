package com.hisco.cmm.modules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    /**
     * 날짜 문자형으로 변경
     *
     * @param date
     *            날짜 정보
     * @param style
     *            날짜 출력 패턴 정보 예) "yyyy-MM-dd HH:mm:ss"
     * @return 변경된 날짜 문자열
     */
    public static String printDatetime(Date date, String style, Locale locale, TimeZone timezone) {

        Locale thisLocale = locale;
        Date thisDate = date;
        TimeZone thisTimeZone = timezone;

        Date adjust_date;
        String adjust_style;

        if (thisDate == null) {
            adjust_date = new Date();
        } else {
            adjust_date = thisDate;
        }

        if (StringUtil.IsEmpty(style)) {
            adjust_style = "yyyy-MM-dd HH:mm:ss";
        } else {
            adjust_style = style;
        }

        if (thisLocale == null) {
            thisLocale = Locale.KOREA;
        }

        if (thisTimeZone == null) {
            thisTimeZone = TimeZone.getTimeZone("Asia/Seoul");
        }

        try {

            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(adjust_style, thisLocale);
            dateFormat.setTimeZone(thisTimeZone);

            return dateFormat.format(adjust_date);

        } catch (Exception e) {

            return adjust_date.toString();
        }
    }

    public static String printDatetime(Date date, String style, Locale locale) {
        return printDatetime(date, style, locale, TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static String printDatetime(Date date, String style) {
        return printDatetime(date, style, Locale.KOREA);
    }

    public static String printDatetime(Date date) {
        return printDatetime(date, null);
    }

    /**
     * 날짜를 숫자형으로 변경
     *
     * @param date
     *            날짜 정보
     * @param addTimeInfo
     *            시, 분, 초 추가 여부
     * @return long // yyyyMMdd : addTimeInfo = false, yyyyMMddHHmmss : addTimeInfo = true
     */
    public static long printDatetime(Date date, boolean addTimeInfo) {

        Date thisDate = date;

        if (thisDate == null) {
            thisDate = new Date();
        }

        String ret;
        if (addTimeInfo == false) {
            ret = printDatetime(thisDate, "yyyyMMdd");
        } else {
            ret = printDatetime(thisDate, "yyyyMMddHHmmss");
        }

        return Long.valueOf(ret);
    }

    /**
     * 날짜 설정
     *
     * @param year
     *            int
     * @param month
     *            int
     * @param day
     *            int
     * @return Date
     */
    public static Date setDate(int year, int month, int day) {
        return setDate(year, month, day, 0, 0, 0);
    }

    /**
     * 날짜 설정
     *
     * @param year
     *            int
     * @param month
     *            int
     * @param day
     *            int
     * @param hour
     *            int
     * @param minute
     *            int
     * @return Date
     */
    public static Date setDate(int year, int month, int day, int hour, int minute) {
        return setDate(year, month, day, hour, minute, 0);
    }

    /**
     * 날짜 설정
     *
     * @param year
     *            int
     * @param month
     *            int
     * @param day
     *            int
     * @param hour
     *            int
     * @param minute
     *            int
     * @param second
     *            int
     * @return Date
     */
    public static Date setDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);

        return cal.getTime();
    }

    /**
     * 날짜 비교
     *
     * @param d1
     *            Date
     * @param d2
     *            Date
     * @param check
     *            String : year, month, day, hour, minute, second 를 ,로 구분하여 비교할 정보를 삽입
     * @return boolean
     */
    public static boolean isEquals(Date d1, Date d2, String check) {

        String strThisCheck = check;

        if (d1 == null || d2 == null || StringUtil.IsEmpty(strThisCheck)) {
            return false;
        }

        boolean ret = true;
        strThisCheck = strThisCheck.toLowerCase();

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(d1);
        cal2.setTime(d2);

        if (ret == true && strThisCheck.indexOf("year") > -1) {
            if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR))
                ret = false;
        }

        if (ret == true && strThisCheck.indexOf("month") > -1) {
            if (cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH))
                ret = false;
        }

        if (ret == true && (strThisCheck.indexOf("day") > -1 || strThisCheck.indexOf("date") > -1)) {
            if (cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH))
                ret = false;
        }

        if (ret == true && strThisCheck.indexOf("hour") > -1) {
            if (cal1.get(Calendar.HOUR_OF_DAY) != cal2.get(Calendar.HOUR_OF_DAY))
                ret = false;
        }

        if (ret == true && strThisCheck.indexOf("minute") > -1) {
            if (cal1.get(Calendar.MINUTE) != cal2.get(Calendar.MINUTE))
                ret = false;
        }

        if (ret == true && strThisCheck.indexOf("second") > -1) {
            if (cal1.get(Calendar.SECOND) != cal2.get(Calendar.SECOND))
                ret = false;
        }

        return ret;
    }

    /**
     * 문자를 날짜형으로 형 변환
     *
     * @param text
     *            String
     * @return Date
     */
    public static Date string2date(String text) {
        if (StringUtil.IsEmpty(text))
            return null;

        Date ret = null;

        SimpleDateFormat dateFormat;

        if (text.trim().matches("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})$")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (text.trim().matches("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2})$")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else if (text.trim().matches("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2})$")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else if (text.trim().matches("^([0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2})$")) {
            dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        } else if (text.trim().matches("^([0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2})$")) {
            dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        } else if (text.trim().matches("^([0-9]{4}[0-9]{2}[0-9]{2})$")) {
            dateFormat = new SimpleDateFormat("yyyyMMdd");
        } else {
            dateFormat = new SimpleDateFormat();
        }

        try {
            ret = dateFormat.parse(text);
        } catch (ParseException ex) {
            ret = (Date) null;
        } catch (Exception e) {
            ret = (Date) null;
        }

        return ret;
    }

    public static Date string2dateTight(String text) {
        if (StringUtil.IsEmpty(text))
            return null;

        Date ret = null;

        SimpleDateFormat dateFormat;

        if (text.trim().matches("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})$")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        if (text.trim().matches("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2})$")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else if (text.trim().matches("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2})$")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            dateFormat = new SimpleDateFormat();
        }

        try {
            ret = dateFormat.parse(text);
        } catch (ParseException ex) {
            ret = (Date) null;
        } catch (Exception e) {
            ret = (Date) null;
        }

        return ret;
    }

    /**
     * 문자를 날짜형으로 형 변환
     *
     * @param text
     *            String 문자열 날
     * @param style
     *            String 날짜 형식
     * @return Date
     */
    public static Date string2date(String text, String style) {
        if (StringUtil.IsEmpty(text) || StringUtil.IsEmpty(style))
            return null;

        Date ret = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(style);

        try {
            ret = dateFormat.parse(text);
        } catch (ParseException ex) {
            ret = (Date) null;
        } catch (Exception e) {
            ret = (Date) null;
        }

        return ret;
    }

    /**
     * 한국식 만나이 계산하기
     *
     * @param birthday
     *            생년월일
     * @return 나이
     */
    public static int calcAge(Date birthday) {
        if (birthday == null)
            return 0;

        Calendar now = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        date.setTime(birthday);

        int age = now.get(Calendar.YEAR) - date.get(Calendar.YEAR);
        // 생일이 안지난경우
        if (((now.get(Calendar.MONTH) + 1) * 100 + now.get(Calendar.DATE)) < ((date.get(Calendar.MONTH) + 1) * 100 + date.get(Calendar.DATE))) {
            age--;
        }

        return age;
    }

    /**
     * 두 날짜 사이의 일수 계산(d1 - d2)
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int calcDay(Date d1, Date d2) {
        if (d1 == null || d2 == null)
            return 0;

        long gap = d1.getTime() - d2.getTime();
        // if (gap < 0) gap = gap * (-1);

        Double ret = Math.ceil(((double) gap) / ((double) (1000 * 60 * 60 * 24)));
        return ret.intValue();
    }

    /**
     * 날자 체크 후 형식에 맞게 출력
     *
     * @param date
     * @param style
     * @return
     */
    public static String DateCheck(String date, String style) {
        if (StringUtil.IsEmpty(date))
            return date;

        try {
            Date d = string2date(date);
            if (d == null)
                return date;
            else
                return printDatetime(d, style);
        } catch (Exception e) {
            return date;
        }
    }

    /**
     * 날짜 차이 구하기
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int DateDiff(Date date1, Date date2) {
        if (date1 == null || date2 == null)
            return 0;

        long time1 = date1.getTime();
        long time2 = date2.getTime();
        long day1 = time1 / (24 * 60 * 60 * 1000);
        long day2 = time2 / (24 * 60 * 60 * 1000);

        Long ret = (day1 > day2 ? day1 - day2 : day2 - day1) + 1;

        return ret.intValue();
    }

    /**
     * 날짜 차이 구하기 (String 로 받음)
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int DateDiffStr(String date1, String date2) {
        if (date1 == null || date2 == null)
            return 0;

        Date day1 = DateUtil.string2date(date1);
        Date day2 = DateUtil.string2date(date2);
        int diff = DateDiff(day1, day2);

        return diff;
    }

    /**
     * 날짜 시, 분, 초, 미리초 초기화
     *
     * @param val
     *            null 일경우 현재 날짜
     * @return
     */
    public static Date MinHHmmssms(Date val) {
        Calendar cal = Calendar.getInstance();
        if (val != null)
            cal.setTime(val);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * 날짜 시, 분, 초, 미리초 초기화
     *
     * @param val
     *            null 일경우 현재 날짜
     * @return
     */
    public static Calendar MinHHmmssms(Calendar val) {

        Calendar thisCalendar = val;

        if (thisCalendar == null) {
            thisCalendar = Calendar.getInstance();
        }

        thisCalendar.set(Calendar.HOUR_OF_DAY, 0);
        thisCalendar.set(Calendar.MINUTE, 0);
        thisCalendar.set(Calendar.SECOND, 0);
        thisCalendar.set(Calendar.MILLISECOND, 0);

        return thisCalendar;
    }

    /**
     * 날짜 시, 분, 초, 미리초 최대화
     *
     * @param val
     *            null 일경우 현재 날짜
     * @return
     */
    public static Date MaxHHmmssms(Date val) {
        Calendar cal = Calendar.getInstance();
        if (val != null)
            cal.setTime(val);

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }

    /**
     * 날짜 시, 분, 초, 미리초 최대화
     *
     * @param val
     *            null 일경우 현재 날짜
     * @return
     */
    public static Calendar MaxHHmmssms(Calendar val) {

        Calendar thisVal = val;

        if (thisVal == null) {
            thisVal = Calendar.getInstance();
        }

        thisVal.set(Calendar.HOUR_OF_DAY, 23);
        thisVal.set(Calendar.MINUTE, 59);
        thisVal.set(Calendar.SECOND, 59);
        thisVal.set(Calendar.MILLISECOND, 999);

        return thisVal;
    }

    /**
     * 날짜 더하기 (스트링 형태로 받아서 스트링 형태로 반납)
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String DateAddStr(String ymd, int adddDay, String dateFormat) {

        String strThisDateFormat = dateFormat;

        String reYmd = ymd;

        if (strThisDateFormat == null || StringUtil.IsEmpty(strThisDateFormat)) {
            strThisDateFormat = "yyyy-MM-dd";
        }

        Date day = DateUtil.string2date(ymd);

        Calendar cal_dsp = Calendar.getInstance();
        cal_dsp.setTime(day);
        cal_dsp.add(Calendar.DATE, adddDay);
        reYmd = DateUtil.printDatetime(cal_dsp.getTime(), strThisDateFormat);

        return reYmd;
    }

    /**
     * 두 날짜 마이크로 초 비교
     *
     * @param d1
     * @param d2
     * @return
     */
    public static long calcDayMicro(Date d1, Date d2) {
        if (d1 == null || d2 == null)
            return 0;

        long gap = d1.getTime() - d2.getTime();

        return gap;
    }

    /**
     * 오늘날짜를 style 형태로 변환
     *
     * @param style
     *            String 날짜 형식
     * @return Date
     */
    public static String getTodate(String style) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(style);

        return dateFormat.format(new Date());
    }
}
