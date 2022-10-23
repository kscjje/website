package com.hisco.cmm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import egovframework.com.cmm.ComDefaultCodeVO;

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
        Date adjustDate;
        String adjustStyle;

        if (date == null)
            adjustDate = new Date();
        else
            adjustDate = date;

        if (CommonUtil.isEmpty(style))
            adjustStyle = "yyyy-MM-dd HH:mm:ss";
        else
            adjustStyle = style;

        try {
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(adjustStyle, (locale == null)
                    ? Locale.KOREA : locale);
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

            return dateFormat.format(adjustDate);
        } catch (Exception e) {
            return adjustDate.toString();
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
        Date tempDate = new Date();
        if (date != null)
            tempDate = date;

        String ret;
        if (addTimeInfo) {
            ret = printDatetime(tempDate, "yyyyMMddHHmmss");
        } else {
            ret = printDatetime(tempDate, "yyyyMMdd");
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
    public static boolean isEquals(Date d1, Date d2, String strCheck) {
        if (d1 == null || d2 == null || CommonUtil.isEmpty(strCheck))
            return false;

        boolean ret = true;
        String check = strCheck.toLowerCase();

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(d1);
        cal2.setTime(d2);

        if (ret && check.indexOf("year") > -1) {
            if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR))
                ret = false;
        }
        if (ret && check.indexOf("month") > -1) {
            if (cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH))
                ret = false;
        }
        if (ret && (check.indexOf("day") > -1 || check.indexOf("date") > -1)) {
            if (cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH))
                ret = false;
        }
        if (ret && check.indexOf("hour") > -1) {
            if (cal1.get(Calendar.HOUR_OF_DAY) != cal2.get(Calendar.HOUR_OF_DAY))
                ret = false;
        }
        if (ret && check.indexOf("minute") > -1) {
            if (cal1.get(Calendar.MINUTE) != cal2.get(Calendar.MINUTE))
                ret = false;
        }
        if (ret && check.indexOf("second") > -1) {
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
        if (CommonUtil.isEmpty(text))
            return null;

        Date ret = null;

        SimpleDateFormat dateFormat;

        if (text.trim().matches("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})$")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
        } else if (text.trim().matches("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2})$")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN);
        } else if (text.trim().matches("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2})$")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
        } else if (text.trim().matches("^([0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2})$")) {
            dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
        } else if (text.trim().matches("^([0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2})$")) {
            dateFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.KOREAN);
        } else if (text.trim().matches("^([0-9]{4}[0-9]{2}[0-9]{2})$")) {
            dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREAN);
        } else if (text.trim().matches("^([0-9]{2}[0-9]{2})$")) {
            dateFormat = new SimpleDateFormat("HHmm", Locale.KOREAN);
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
        if (CommonUtil.isEmpty(text))
            return null;

        Date ret = null;

        SimpleDateFormat dateFormat;

        if (text.trim().matches("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})$")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREAN);
        }
        if (text.trim().matches("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2})$")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREAN);
        } else if (text.trim().matches("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2})$")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
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
        if (CommonUtil.isEmpty(text) || CommonUtil.isEmpty(style))
            return null;

        Date ret = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(style, Locale.KOREA);

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
        if (CommonUtil.isEmpty(date))
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
    public static Calendar MinHHmmssms(Calendar cal) {
        Calendar val = Calendar.getInstance();
        if (cal != null)
            val = cal;

        val.set(Calendar.HOUR_OF_DAY, 0);
        val.set(Calendar.MINUTE, 0);
        val.set(Calendar.SECOND, 0);
        val.set(Calendar.MILLISECOND, 0);

        return val;
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
    public static Calendar MaxHHmmssms(Calendar cal) {
        Calendar val = Calendar.getInstance();
        if (cal != null)
            val = cal;

        val.set(Calendar.HOUR_OF_DAY, 23);
        val.set(Calendar.MINUTE, 59);
        val.set(Calendar.SECOND, 59);
        val.set(Calendar.MILLISECOND, 999);

        return val;
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
     * 날짜 더하기 (날짜형태로 받아서 날짜형태로 리턴)
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Date DateAddDay(Date day, int adddDay) {

        Calendar cal_dsp = Calendar.getInstance();
        cal_dsp.setTime(day);
        cal_dsp.add(Calendar.DATE, adddDay);

        return cal_dsp.getTime();
    }

    /**
     * 요일 구하기 (String 날짜형태로 리턴)
     *
     * @param date1
     * @return
     */
    public static int getWeekByString(String ymd) {

        Date day = string2date(ymd);

        Calendar cal_dsp = Calendar.getInstance();
        cal_dsp.setTime(day);

        return cal_dsp.get(Calendar.DAY_OF_WEEK);

    }

    /**
     * 요일 구하기 (날짜형태로 받아서 Calendar.DAY_OF_WEEK 리턴)
     *
     * @param date1
     * @return
     */
    public static int getWeek(Date day) {

        Calendar cal_dsp = Calendar.getInstance();
        cal_dsp.setTime(day);
        return cal_dsp.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 요일 구하기 (Calendar.DAY_OF_WEEK 값을 받아서 요일로 리턴)
     *
     * @param Calendar.DAY_OF_WEEK
     * @param lang
     * @return
     */
    public static String printWeek(int week, String lang) {

        String weekName = "";
        if (CommonUtil.isEmpty(lang)) {
            lang = "ko";
        }

        String[] ko = { "일", "월", "화", "수", "목", "금", "토" };
        String[] en = { "Sun", "Mon", "The", "Wed", "Thu", "Fri", "Sat" };
        switch (lang) {
        case "ko":
        case "kr":
            weekName = ko[week - 1];
            break;
        case "en":
            weekName = en[week - 1];
            break;
        }

        return weekName;
    }

    /**
     * 달력 호출 할때 필요한 Object들 한번에 구해서 Map 형태로 리턴 (달력 배열, 시작일, 종료일, 이전달, 다음달 정보를 일괄로 가져옴)
     *
     * @param Calendar.DAY_OF_WEEK
     * @param lang
     * @return
     */
    public static HashMap calendarGroup(String ym, String lang) {

        HashMap mapData = new HashMap();

        if (CommonUtil.isEmpty(ym)) {
            ym = printDatetime(null, "yyyyMM");
        }
        if (CommonUtil.isEmpty(lang)) {
            lang = "ko";
        }

        // 이번달 시작일
        String startMonthDd = ym + "01";

        // 이번달 종료일
        String endMonthDd = "";
        try {
            endMonthDd = CommonUtil.getLastDayOfMonth(startMonthDd);
        } catch (Exception e) {
            endMonthDd = ym + "30";
        }

        // 달력 시작일
        String startCalendarDd = "";
        try {
            startCalendarDd = CommonUtil.getFirstDayOfWeek(startMonthDd);
        } catch (Exception e) {
            startCalendarDd = startMonthDd;
        }

        // 달력 종료일
        String endCalendarDd = "";
        try {
            endCalendarDd = CommonUtil.getLastDayOfWeek(endMonthDd);
        } catch (Exception e) {
            endCalendarDd = endMonthDd;
        }

        ArrayList cal = new ArrayList();
        ArrayList week = new ArrayList();

        Date start = string2date(startCalendarDd);
        Date end = string2date(endCalendarDd);

        String prev = printDatetime(DateAddDay(start, -1), "yyyyMM"); // 이전달
        String next = printDatetime(DateAddDay(end, 1), "yyyyMM"); // 다음달

        int idx = 0;
        for (Date i = start; calcDayMicro(end, i) >= 0; i = DateAddDay(i, 1)) {
            String day = printDatetime(i, "yyyyMMdd");

            HashMap data = new HashMap();
            data.put("day", day);
            data.put("yyyy", printDatetime(i, "yyyy"));
            data.put("mm", printDatetime(i, "MM"));
            data.put("ym", printDatetime(i, "yyyyMM"));
            data.put("dd", printDatetime(i, "dd"));
            data.put("w", printWeek(getWeek(i), "ko"));
            week.add(data);
            if (getWeek(i) == 7) {
                cal.add(week);
                week = new ArrayList();
            }
        }

        ArrayList weekNames = new ArrayList();
        for (int i = 1; i <= 7; i++) {
            weekNames.add(printWeek(i, "en"));
        }

        mapData.put("ym", ym);
        mapData.put("prev", prev);
        mapData.put("next", next);
        mapData.put("cal", cal);
        mapData.put("weekNames", weekNames);

        mapData.put("startMonthDd", startMonthDd);
        mapData.put("endMonthDd", endMonthDd);
        mapData.put("startCalendarDd", startCalendarDd);
        mapData.put("endCalendarDd", endCalendarDd);
        return mapData;
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

    /**
     * 시간을 LIST를 반환한다.
     * @return List
     * @throws
     */
    public static List<ComDefaultCodeVO> getTimeHH() {
        ArrayList<ComDefaultCodeVO> listHH = new ArrayList<ComDefaultCodeVO>();
        HashMap<?, ?> hmHHMM;
        for (int i = 0; i < 24; i++) {
            String sHH = "";
            String strI = String.valueOf(i);
            if (i < 10) {
                sHH = "0" + strI;
            } else {
                sHH = strI;
            }

            ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
            codeVO.setCode(sHH);
            codeVO.setCodeNm(sHH + "시");

            listHH.add(codeVO);
        }

        return listHH;
    }

    /**
     * 분을 LIST를 반환한다.
     * @return List
     * @throws
     */
    public static List<ComDefaultCodeVO> getTimeMM() {
        ArrayList<ComDefaultCodeVO> listMM = new ArrayList<ComDefaultCodeVO>();
        HashMap<?, ?> hmHHMM;
        for (int i = 0; i < 60; i++) {

            String sMM = "";
            String strI = String.valueOf(i);
            if (i < 10) {
                sMM = "0" + strI;
            } else {
                sMM = strI;
            }

            ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
            codeVO.setCode(sMM);
            codeVO.setCodeNm(sMM + "분");

            listMM.add(codeVO);
        }
        return listMM;
    }

    /**
     * 0을 붙여 반환
     * @return String
     * @throws
     */
    public static String dateTypeIntForString(int iInput) {
        String sOutput = "";
        if (Integer.toString(iInput).length() == 1) {
            sOutput = "0" + Integer.toString(iInput);
        } else {
            sOutput = Integer.toString(iInput);
        }

        return sOutput;
    }
}
