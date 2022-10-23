package com.hisco.user.evtedcrsvn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.user.evtedcrsvn.service.CalendarVO;
import com.hisco.user.evtedcrsvn.service.ComCtgrVO;
import com.hisco.user.evtedcrsvn.service.EventProgramVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcStdmngVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcrsvnMstVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcrsvnSMainService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 강연/행사/영화 정보 조회 구현 클래스
 * 
 * @author 진수진
 * @since 2020.08.25
 * @version 1.0, 2020.08.25
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.08.25 최초작성
 */
@Service("evtEdcrsvnSMainService")
public class EvtEdcrsvnSMainServiceImpl extends EgovAbstractServiceImpl implements EvtEdcrsvnSMainService {

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    /**
     * 기준설정 데이타를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    public CamelMap selectProgramData(Map<?, ?> vo) throws Exception {
        return (CamelMap) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectProgramData", vo);
    }

    /**
     * 파일 아이디를 수정한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    public void updateProgramData(Map<?, ?> vo) throws Exception {
        commonDAO.getExecuteResult("EvtEdcrsvnSMainDAO.updateProgramData", vo);
    }

    /**
     * 카테고리 리스트를 가져온다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<ComCtgrVO> selectEvtEdcCategList(ComCtgrVO vo) throws Exception {
        List<ComCtgrVO> cList = (List<ComCtgrVO>) commonDAO.queryForList("EvtEdcrsvnSMainDAO.selectCtgrList", vo);
        return cList;
    }

    /**
     * 강연/행사/영화 리스트를 가져온다
     * 
     * @param vo
     *            EventProgramVO
     * @return List
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectEvtEdcProgList(EventProgramVO vo) throws Exception {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<CamelMap> rList = (List<CamelMap>) commonDAO.queryForList("EvtEdcrsvnSMainDAO.selectEventProgram", vo);

        int intSize = 0;
        // int size = commonDAO.queryForInt("EvtEdcrsvnSMainDAO.countEventProgram", vo);

        if (rList != null) {
            if (rList.size() >= 1) {
                intSize = Integer.parseInt(String.valueOf(((CamelMap) rList.get(0)).get("tbAllCount")));
            }
        }

        resultMap.put("list", rList);
        resultMap.put("size", intSize);

        return resultMap;
    }

    /**
     * 강연/행사/영화 상세 를 가져온다
     * 
     * @param vo
     *            EventProgramVO
     * @return Map
     * @exception Exception
     */
    public EventProgramVO selectEvtEdcPrgDetail(EventProgramVO vo) throws Exception {
        EventProgramVO result = (EventProgramVO) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectEventPrgDetail", vo);
        return result;
    }

    /**
     * 강연/행사/영화 회차정보를 가져온다
     * 
     * @param vo
     *            EventProgramVO
     * @return List
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<CamelMap> selectEvtEdcTimeDetailList(EvtEdcStdmngVO vo) throws Exception {
        List<CamelMap> rList = (List<CamelMap>) commonDAO.queryForList("EvtEdcrsvnSMainDAO.selectEvtEdcTimeList", vo);
        return rList;
    }

    /**
     * 강연/행사/영화 회차를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return map
     * @exception Exception
     */
    public CamelMap selectEvtEdcTimeDetail(EvtEdcStdmngVO vo) throws Exception {
        return (CamelMap) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectEvtEdcTimeList", vo);
    }

    /**
     * 선택날짜 요일을 조회한다
     * 
     * @param vo
     *            Map
     * @return String
     * @exception Exception
     */
    public String selectEvtEdcWeek(Map<?, ?> vo) throws Exception {
        String dayWeek = (String) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectEvtEdcTimeDay", vo);
        return dayWeek;
    }

    /**
     * 강연/행사/영화 예약가능정보를 가져온다
     * 
     * @param vo
     *            EventProgramVO
     * @return List
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<CalendarVO> selectEvtEdcRsvnList(Map<String, Object> map) throws Exception {
        List<CalendarVO> rList = (List<CalendarVO>) commonDAO.queryForList("EvtEdcrsvnSMainDAO.selectEvtEdcCalendar", map);
        return rList;
    }

}
