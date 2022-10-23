package com.hisco.admin.calendar.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.hisco.admin.calendar.mapper.CalendarManageMapper;
import com.hisco.admin.calendar.vo.CalendarInfoVO;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.DateUtil;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 공통코드 조회 및 등록 구현클래스
 *
 * @author 진수진
 * @since 2021.03.24
 * @version 1.0, 2021.03.24
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.03.24 최초작성
 */
@Service("calendarManageService")
public class CalendarManageService extends EgovAbstractServiceImpl {

    @Resource(name = "calendarManageMapper")
    private CalendarManageMapper calendarManageMapper;

    /**
     * 기준월력 조회한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return List
     * @exception Exception
     */
    public List<?> selectCalendarList(CalendarInfoVO vo) throws Exception {
        return calendarManageMapper.selectCalendarList(vo);
    }

    /**
     * 기준월력 상세를 조회한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return List
     * @exception Exception
     */
    public CalendarInfoVO selectCalendarDetail(CalendarInfoVO vo) throws Exception {
        return (CalendarInfoVO) calendarManageMapper.selectCalendarDetail(vo);
    }

    /**
     * 기준 월력을 신규 생성한다
     *
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public String insertCalendarDetail(CalendarInfoVO vo) throws Exception {
        CalendarInfoVO data = (CalendarInfoVO) calendarManageMapper.selectCalendarDetail(vo);
        String result = "";

        if (data != null) {
            result = "ER|기준월력 데이터가 존재합니다.";
        } else {
            int n = calendarManageMapper.insertCalendarDetail(vo);

            if (n > 0) {
                result = "OK";
            } else {
                result = "ER|데이타입력 오류";
            }
        }

        return result;
    }

    /**
     * 기준 월력 을 삭제한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return int
     * @exception Exception
     */
    public int deleteCalendarDetail(CalendarInfoVO vo) throws Exception {
        return calendarManageMapper.deleteCalendarDetail(vo);
    }

    /**
     *  월력을 전체 삭제한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return int
     * @exception Exception
     */
    public void deleteCalendarDetailAll(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        CalendarInfoVO calendarInfoVO = new CalendarInfoVO();
        calendarInfoVO.setComcd(Config.COM_CD);
        calendarInfoVO.setReguser((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

        String yyyy = commandMap.getString("yyyy");
        calendarInfoVO.setCalDate(yyyy);

        calendarManageMapper.deleteCalendarDetailAll(calendarInfoVO);
    }

    /**
     * 기준 월력을 수정한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return int
     * @exception Exception
     */
    public int updateCalendarDetail(CalendarInfoVO vo) throws Exception {
        return calendarManageMapper.updateCalendarDetail(vo);
    }

    /**
     * 기준 월력 년도 정보를 추출
     *
     * @param vo
     *            calendarInfoVO
     * @return List<?>
     * @exception Exception
     */
    public List<?> selectCalendarYearGroup(CalendarInfoVO vo)
            throws Exception {

        return calendarManageMapper.selectCalendarYearGroup(vo);
    }

    /**
     * 기관별월력 조회한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return List
     * @exception Exception
     */
    public List<?> selectOrgCalendarList(CalendarInfoVO vo) throws Exception {
        return calendarManageMapper.selectOrgCalendarList(vo);
    }

    /**
     * 기관별월력 상세를 조회한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return List
     * @exception Exception
     */
    public CalendarInfoVO selectOrgCalendarDetail(CalendarInfoVO vo) throws Exception {
        return (CalendarInfoVO) calendarManageMapper.selectOrgCalendarDetail(vo);
    }

    /**
     * 기관별 월력을 신규 생성한다
     *
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public String insertOrgCalendarDetail(CalendarInfoVO vo) throws Exception {
        CalendarInfoVO data = (CalendarInfoVO) calendarManageMapper.selectOrgCalendarDetail(vo);
        String result = "";

        if (data != null) {
            result = "ER|기관별월력 데이터가 존재합니다.";
        } else {
            int n = calendarManageMapper.insertOrgCalendarDetail(vo);

            if (n > 0) {
                result = "OK";
            } else {
                result = "ER|데이타입력 오류";
            }
        }

        return result;
    }

    /**
     * 기관별 월력 을 삭제한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return int
     * @exception Exception
     */
    public int deleteOrgCalendarDetail(CalendarInfoVO vo) throws Exception {
        return calendarManageMapper.deleteOrgCalendarDetail(vo);
    }

    /**
     * 기관별 월력을 수정한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return int
     * @exception Exception
     */
    public int updateOrgCalendarDetail(CalendarInfoVO vo) throws Exception {
        return calendarManageMapper.updateOrgCalendarDetail(vo);
    }

    /**
     * 기관별 월력을 전체 삭제한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return int
     * @exception Exception
     */
    public void deleteOrgCalendarDetailAll(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        CalendarInfoVO calendarInfoVO = new CalendarInfoVO();
        calendarInfoVO.setComcd(Config.COM_CD);
        calendarInfoVO.setReguser((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

        String yyyy = commandMap.getString("yyyy");
        calendarInfoVO.setCalDate(yyyy);

        int orgNo = StringUtil.String2Int(request.getParameter("orgNo"), 0);
        calendarInfoVO.setOrgNo(orgNo);

        calendarManageMapper.deleteOrgCalendarDetailAll(calendarInfoVO);
    }

    /**
     * 기관별 복사
     *
     * @param
     * @return int
     * @exception Exception
     */
    public void copyCalendarDetailAll(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        HashMap mapData = new HashMap();
        mapData.put("reguser", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());
        mapData.put("comcd", Config.COM_CD);
        mapData.put("source", 0);
        mapData.put("target", commandMap.getString("orgNo"));
        mapData.put("yyyy", commandMap.getString("yyyy"));

        calendarManageMapper.copyCalendarDetailAll(mapData);
    }

    /**
     * 기관별 월력 일괄 등록
     *
     * @param
     * @return int
     * @exception Exception
     */
    public void groupCalendarDetailAll(CalendarInfoVO calendarInfoVO, CommandMap commandMap, HttpServletRequest request,
            ModelMap model)
            throws Exception {
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        calendarInfoVO.setComcd(Config.COM_CD);
        calendarInfoVO.setReguser((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

        String[] weekArr = request.getParameterValues("week");
        String sdate = (request.getParameter("sdate")).replace(".", "").replace("-", "");
        String edate = (request.getParameter("edate")).replace(".", "").replace("-", "");

        Date start = DateUtil.string2date(sdate);
        Date end = DateUtil.string2date(edate);

        int idx = 0;
        for (Date i = start; DateUtil.calcDayMicro(end, i) >= 0; i = DateUtil.DateAddDay(i, 1)) {
            String day = DateUtil.printDatetime(i, "yyyyMMdd");
            String week = StringUtil.Int2String(DateUtil.getWeek(i), "");
            if (Arrays.asList(weekArr).contains(week)) {
                calendarInfoVO.setCalDate(day);
                calendarInfoVO.setSearchStartDts(day);
                calendarInfoVO.setSearchEndDts(day);
                List<?> list = selectOrgCalendarList(calendarInfoVO);
                if(list.size() > 0) {
                	continue;
                }
                int n = calendarManageMapper.insertOrgCalendarDetail(calendarInfoVO);

                if (n < 0) {
                    throw new Exception("입력 실패");
                }
            }
        }

        // calendarManageMapper.copyCalendarDetailAll(mapData);
    }


    /**
     *  월력 일괄 등록
     *
     * @param
     * @return int
     * @exception Exception
     */
    public void groupComCalendarDetailAll(CalendarInfoVO calendarInfoVO, CommandMap commandMap, HttpServletRequest request,
            ModelMap model)
            throws Exception {
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        calendarInfoVO.setComcd(Config.COM_CD);
        calendarInfoVO.setReguser((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

        String[] weekArr = request.getParameterValues("week");
        String sdate = (request.getParameter("sdate")).replace(".", "").replace("-", "");
        String edate = (request.getParameter("edate")).replace(".", "").replace("-", "");

        Date start = DateUtil.string2date(sdate);
        Date end = DateUtil.string2date(edate);
        
        int idx = 0;
        for (Date i = start; DateUtil.calcDayMicro(end, i) >= 0; i = DateUtil.DateAddDay(i, 1)) {
            String day = DateUtil.printDatetime(i, "yyyyMMdd");
            String week = StringUtil.Int2String(DateUtil.getWeek(i), "");
            if (Arrays.asList(weekArr).contains(week)) {
            	calendarInfoVO.setSearchStartDts(day);
                calendarInfoVO.setSearchEndDts(day);
                List<?> list = selectCalendarList(calendarInfoVO);
                if(list.size() > 0) {
                	continue;
                }
                calendarInfoVO.setCalDate(day);
                int n = calendarManageMapper.insertCalendarDetail(calendarInfoVO);

                if (n < 0) {
                    throw new Exception("입력 실패");
                }
            }
        }

        // calendarManageMapper.copyCalendarDetailAll(mapData);
    }
}
