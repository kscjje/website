package com.hisco.admin.calendar.mapper;

import java.util.HashMap;
import java.util.List;

import com.hisco.admin.calendar.vo.CalendarInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 월력관리조회 및 등록 구현클래스
 *
 * @author 이윤호
 * @since 2021.10.31
 * @version 1.0, 2021.10.31
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          이윤호 2021.10.31 최초작성
 */
@Mapper("calendarManageMapper")
public interface CalendarManageMapper {

    /**
     * 기준 월력을 조회한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return List
     * @exception Exception
     */
    public List<?> selectCalendarList(CalendarInfoVO vo);

    /**
     * 기준 월력 상세를 조회한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return List
     * @exception Exception
     */
    public CalendarInfoVO selectCalendarDetail(CalendarInfoVO vo);

    /**
     * 기준 월력을 신규 생성한다
     *
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public int insertCalendarDetail(CalendarInfoVO vo);

    /**
     * 기준 월력 을 삭제한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return int
     * @exception Exception
     */
    public int deleteCalendarDetail(CalendarInfoVO vo);

    /**
     * 기관별 월력 일괄 삭제한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return int
     * @exception Exception
     */
    public int deleteCalendarDetailAll(CalendarInfoVO vo);

    /**
     * 기준 월력을 수정한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return int
     * @exception Exception
     */
    public int updateCalendarDetail(CalendarInfoVO vo);

    /**
     * 기준 월력 년도 정보를 추출
     *
     * @param vo
     *            CalendarInfoVO
     * @return List<?>
     * @exception Exception
     */
    public List<?> selectCalendarYearGroup(CalendarInfoVO vo);

    /**
     * 기관별 월력을 조회한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return List
     * @exception Exception
     */
    public List<?> selectOrgCalendarList(CalendarInfoVO vo);

    /**
     * 기관별 월력 상세를 조회한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return List
     * @exception Exception
     */
    public CalendarInfoVO selectOrgCalendarDetail(CalendarInfoVO vo);

    /**
     * 기관별 월력을 신규 생성한다
     *
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public int insertOrgCalendarDetail(CalendarInfoVO vo);

    /**
     * 기관별 월력 을 삭제한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return int
     * @exception Exception
     */
    public int deleteOrgCalendarDetail(CalendarInfoVO vo);

    /**
     * 기관별 월력을 수정한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return int
     * @exception Exception
     */
    public int updateOrgCalendarDetail(CalendarInfoVO vo);

    /**
     * 기관별 월력 일괄 삭제한다
     *
     * @param vo
     *            CalendarInfoVO
     * @return int
     * @exception Exception
     */
    public int deleteOrgCalendarDetailAll(CalendarInfoVO vo);

    /**
     * 기관별 복사
     *
     * @param vo
     *            Hashmap
     * @return List<?>
     * @exception Exception
     */
    public void copyCalendarDetailAll(HashMap vo);
}
