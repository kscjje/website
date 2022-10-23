package com.hisco.user.evtedcrsvn.service;

import java.util.List;
import java.util.Map;

import com.hisco.cmm.object.CamelMap;

/**
 * 강연/행사/영화 프로그램 정보 조회
 * 
 * @author 진수진
 * @since 2020.08.25
 * @version 1.0, 2020.08.25
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.08.25 최초작성
 */
public interface EvtEdcrsvnSMainService {

    /**
     * 강연/행사/영화 기본 데이타를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    CamelMap selectProgramData(Map<?, ?> vo) throws Exception;

    /**
     * 파일 아이디를 수정한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    void updateProgramData(Map<?, ?> vo) throws Exception;

    /**
     * 강연/행사/영화 카테고리를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    List<ComCtgrVO> selectEvtEdcCategList(ComCtgrVO vo) throws Exception;

    /**
     * 강연/행사/영화 리스트를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return map
     * @exception Exception
     */
    Map<String, Object> selectEvtEdcProgList(EventProgramVO vo) throws Exception;

    /**
     * 강연/행사/영화 상세를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return map
     * @exception Exception
     */
    EventProgramVO selectEvtEdcPrgDetail(EventProgramVO vo) throws Exception;

    /**
     * 강연/행사/영화 회차를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return map
     * @exception Exception
     */
    List<CamelMap> selectEvtEdcTimeDetailList(EvtEdcStdmngVO vo) throws Exception;

    /**
     * 강연/행사/영화 회차를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return map
     * @exception Exception
     */
    CamelMap selectEvtEdcTimeDetail(EvtEdcStdmngVO vo) throws Exception;

    /**
     * 선택날짜 요일을 조회한다
     * 
     * @param vo
     *            Map
     * @return String
     * @exception Exception
     */
    String selectEvtEdcWeek(Map<?, ?> vo) throws Exception;

    /**
     * 강연/행사/영화 예약가능 정보를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return map
     * @exception Exception
     */
    List<CalendarVO> selectEvtEdcRsvnList(Map<String, Object> map) throws Exception;

}
