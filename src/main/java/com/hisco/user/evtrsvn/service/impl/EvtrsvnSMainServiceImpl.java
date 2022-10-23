package com.hisco.user.evtrsvn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.user.evtrsvn.service.CalendarVO;
import com.hisco.user.evtrsvn.service.ComCtgrVO;
import com.hisco.user.evtrsvn.service.EventProgramVO;
import com.hisco.user.evtrsvn.service.EvtStdmngVO;
import com.hisco.user.evtrsvn.service.EvtrsvnMstVO;
import com.hisco.user.evtrsvn.service.EvtrsvnSMainService;

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
@Service("evtrsvnSMainService")
public class EvtrsvnSMainServiceImpl extends EgovAbstractServiceImpl implements EvtrsvnSMainService {

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
        return (CamelMap) commonDAO.queryForObject("EvtrsvnSMainDAO.selectProgramData", vo);
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
        commonDAO.getExecuteResult("EvtrsvnSMainDAO.updateProgramData", vo);
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
    public List<ComCtgrVO> selectEvtCategList(ComCtgrVO vo) throws Exception {
        List<ComCtgrVO> cList = (List<ComCtgrVO>) commonDAO.queryForList("EvtrsvnSMainDAO.selectCtgrList", vo);
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
    public Map<String, Object> selectEvtProgList(EventProgramVO vo) throws Exception {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<CamelMap> rList = (List<CamelMap>) commonDAO.queryForList("EvtrsvnSMainDAO.selectEventProgram", vo);

        int intSize = 0;
        // int size = commonDAO.queryForInt("EvtrsvnSMainDAO.countEventProgram", vo);

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
    public EventProgramVO selectEvtPrgDetail(EventProgramVO vo) throws Exception {
        EventProgramVO result = (EventProgramVO) commonDAO.queryForObject("EvtrsvnSMainDAO.selectEventPrgDetail", vo);
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
    public List<CamelMap> selectEvtTimeDetailList(EvtStdmngVO vo) throws Exception {
        List<CamelMap> rList = (List<CamelMap>) commonDAO.queryForList("EvtrsvnSMainDAO.selectEvtTimeList", vo);
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
    public CamelMap selectEvtTimeDetail(EvtStdmngVO vo) throws Exception {
        return (CamelMap) commonDAO.queryForObject("EvtrsvnSMainDAO.selectEvtTimeList", vo);
    }

    /**
     * 선택날짜 요일을 조회한다
     * 
     * @param vo
     *            Map
     * @return String
     * @exception Exception
     */
    public String selectEvtWeek(Map<?, ?> vo) throws Exception {
        String dayWeek = (String) commonDAO.queryForObject("EvtrsvnSMainDAO.selectEvtTimeDay", vo);
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
    public List<CalendarVO> selectEvtRsvnList(Map<String, Object> map) throws Exception {
        List<CalendarVO> rList = (List<CalendarVO>) commonDAO.queryForList("EvtrsvnSMainDAO.selectEvtCalendar", map);
        return rList;
    }

}
