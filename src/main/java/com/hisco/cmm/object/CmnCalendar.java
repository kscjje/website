package com.hisco.cmm.object;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hisco.user.evtrsvn.service.CalendarVO;

/**
 * @Class Name : CmnCalendar.java
 * @Description : 사용자 교육페이지 캘린더 구성 소스
 * @version 1.0
 * @see
 * 
 *      <pre>
 *      수정일 수정자 수정내용
 *      ------------- ------ -------------------------------
 *      2019.11.14 kdm 최초생성
 * @author 두어시스템 개발팀
 * @since 2018.07.27
 * @version 1.0
 *          Copyright (C) by DOASYSTEM All right reserved.
 */
public class CmnCalendar {

    /**
     * getCalendar
     * 
     * @param request
     * @return rMap
     * @exception Exception
     */
    public Map<String, Object> getCalendar(HttpServletRequest request, String startYmd) throws Exception {

        Map<String, Object> rMap = new HashMap<>();

        int currYear = 0;
        int currMonth = 0;

        Calendar c = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        currMonth = c.get(Calendar.MONTH);
        currYear = c.get(Calendar.YEAR);

        String ymd = "";

        if (request.getParameter("ymd") != null && request.getParameter("ymd").length() == 8) {
            ymd = request.getParameter("ymd");

        } else if (!startYmd.equals("")) {
            int year = Integer.parseInt(startYmd.substring(0, 4));
            int month = Integer.parseInt(startYmd.substring(4, 6)) - 1;

            if (currYear < year || (currYear == year && currMonth < month)) {
                ymd = startYmd;
            }
        }
        if (!ymd.equals("")) {
            currYear = Integer.parseInt(ymd.substring(0, 4));
            currMonth = Integer.parseInt(ymd.substring(4, 6)) - 1;
        }

        cal.set(currYear, currMonth, 1);

        if (request.getParameter("val") != null) {
            if (Integer.parseInt(request.getParameter("val")) == 1) {
                currMonth = Integer.parseInt(request.getParameter("currMonth"));
                currYear = Integer.parseInt(request.getParameter("currYear"));
                cal.set(currYear, currMonth, 1);
                cal.add(Calendar.MONTH, 1); // 다음달
                currMonth = cal.get(Calendar.MONTH);
                currYear = cal.get(Calendar.YEAR);
            } else {
                currMonth = Integer.parseInt(request.getParameter("currMonth"));
                currYear = Integer.parseInt(request.getParameter("currYear"));
                cal.set(currYear, currMonth, 1);
                cal.add(Calendar.MONTH, -1); // 이전달
                currMonth = cal.get(Calendar.MONTH);
                currYear = cal.get(Calendar.YEAR);
            }
        }

        int count = 1;
        int dispDay = 1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM", Locale.KOREA);
        String calTitle = sdf.format(cal.getTime());
        StringBuffer calStr = new StringBuffer();

        for (int w = 1; w < 7; w++) {

            calStr.append("<tr>");

            for (int d = 1; d < 8; d++) {
                if (!(count >= cal.get(Calendar.DAY_OF_WEEK))) {
                    calStr.append("<td>&nbsp;</td>");
                    count += 1;
                } else {
                    if (isDate(currYear, currMonth + 1, dispDay)) {

                        String dateYmd = currYear + (currMonth < 9 ? "0" : "") + (currMonth + 1) + (dispDay < 10
                                ? "0" : "") + dispDay;
                        calStr.append("<td id='CAL_" + dateYmd + "'><a><dl><dt>" + dispDay + "</dt><dd></dd></dl></a></td>");
                        count += 1;
                        dispDay += 1;
                    } else {
                        calStr.append("<td>&nbsp;</td>");
                    }
                }
            }
        }

        rMap.put("currYear", currYear);
        rMap.put("currMonth", currMonth);
        rMap.put("calTitle", calTitle);
        rMap.put("calStr", calStr.toString());

        return rMap;
    }

    /**
     * isDate 날짜검증
     * 
     * @param
     * @return boolean
     * @exception Exception
     */
    public boolean isDate(int y, int m, int d) {
        Calendar c = Calendar.getInstance();
        c.setLenient(false);

        try {

            c.set(y, m - 1, d);
            Date dt = c.getTime();

        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * getCalendar
     * 
     * @param request
     * @return rMap
     * @exception Exception
     */
    public Map<String, Object> getCalendarList(HttpServletRequest request) throws Exception {
        Map<String, Object> rMap = new HashMap<>();

        int currYear = 0;
        int currMonth = 0;
        String actionFn = request.getParameter("fn") == null ? "fn_show_prgm" : request.getParameter("fn");

        Calendar c = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        currMonth = c.get(Calendar.MONTH);
        currYear = c.get(Calendar.YEAR);
        cal.set(currYear, currMonth, 1);

        if (request.getParameter("val") != null) {
            if (Integer.parseInt(request.getParameter("val")) == 1) {
                currMonth = Integer.parseInt(request.getParameter("currMonth"));
                currYear = Integer.parseInt(request.getParameter("currYear"));
                cal.set(currYear, currMonth, 1);
                cal.add(Calendar.MONTH, 1); // 다음달
                currMonth = cal.get(Calendar.MONTH);
                currYear = cal.get(Calendar.YEAR);
            } else {
                currMonth = Integer.parseInt(request.getParameter("currMonth"));
                currYear = Integer.parseInt(request.getParameter("currYear"));
                cal.set(currYear, currMonth, 1);
                cal.add(Calendar.MONTH, -1); // 이전달
                currMonth = cal.get(Calendar.MONTH);
                currYear = cal.get(Calendar.YEAR);
            }
        }
        int count = 1;
        int dispDay = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM", Locale.KOREA);
        String calTitle = sdf.format(cal.getTime());
        StringBuffer calStr = new StringBuffer();
        List<CalendarVO> calList = new ArrayList<CalendarVO>();

        for (int w = 1; w < 7; w++) {
            calStr.append("<tr>");
            for (int d = 1; d < 8; d++) {
                CalendarVO calVO = new CalendarVO();
                calVO.setWeek(String.valueOf(w));
                if (!(count >= cal.get(Calendar.DAY_OF_WEEK))) {
                    calStr.append("<td>&nbsp;</td>");
                    calVO.setDd("");
                    calVO.setCalDate("");
                    count += 1;
                } else {
                    if (isDate(currYear, currMonth + 1, dispDay)) {
                        String ymd = currYear + (currMonth < 9 ? "0" : "") + (currMonth + 1) + (dispDay < 10
                                ? "0" : "") + dispDay;
                        calStr.append("<td id='CAL_" + ymd + "'><a href=\"javascript:" + actionFn + "('" + ymd + "')\"><dl><dt>" + dispDay + "</dt><dd></dd></dl></a></td>");
                        calVO.setCalDate(ymd);
                        calVO.setDd(String.valueOf(dispDay));
                        count += 1;
                        dispDay += 1;
                    } else {
                        calStr.append("<td>&nbsp;</td>");
                        calVO.setDd("");
                        calVO.setCalDate("");
                    }
                }
                calList.add(calVO);
            }
        }
        rMap.put("currYear", currYear);
        rMap.put("currMonth", currMonth);
        rMap.put("calTitle", calTitle);
        rMap.put("calList", calList);
        rMap.put("calStr", calStr.toString());
        return rMap;
    }

}
