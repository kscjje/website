package com.hisco.user.edcatnlc.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.eduadm.vo.EdcDaysVO;
import com.hisco.admin.eduadm.vo.EdcNoticeVO;
import com.hisco.admin.eduadm.vo.EdcTargetAgeVO;
import com.hisco.user.edcatnlc.mapper.EdcProgramMapper;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 교육프로그램신청관리 Service 구현 클래스
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 */
@Service("edcProgramService")
public class EdcProgramService extends EgovAbstractServiceImpl {

    @Resource(name = "edcProgramMapper")
    private EdcProgramMapper edcProgramMapper;

    /**
     * 교육프로그램 목록을 조회한다.
     *
     * @param EdcProgramVO
     * @return List
     * @throws Exception
     */
    public List<EdcProgramVO> selectProgramList(EdcProgramVO param) throws Exception {
        return edcProgramMapper.selectProgramList(param);
    }

    
    public List<HashMap<String, Object>> getProgramList(HashMap<String, Object> param) throws Exception {
        return edcProgramMapper.getProgramList(param);
    }
    
    
    
    public List<EdcDaysVO> selectProgramDays(EdcProgramVO param) {
        return edcProgramMapper.selectProgramDays(param);
    }

    public List<EdcProgramVO> selectProgramRsvnSetList(EdcProgramVO param) {
        return edcProgramMapper.selectProgramRsvnSetList(param);
    }

    /**
     * 프로그램목록상세
     */
    public EdcProgramVO selectProgramDetail(EdcProgramVO param) {
        return edcProgramMapper.selectProgramDetail(param);
    }

    /** 프로그램공지사항 */
    public EdcNoticeVO selectProgramNotice(EdcProgramVO param) {
        return edcProgramMapper.selectProgramNotice(param);
    }

    /** 프로그램 예약정보 */
    public EdcProgramVO selectProgramRsvn(EdcProgramVO paramVO) {
        return edcProgramMapper.selectProgramRsvn(paramVO);
    }

    public List<EdcTargetAgeVO> selectTargetAgeList(EdcProgramVO paramMap) {
        return edcProgramMapper.selectTargetAgeList(paramMap);
    }

    public String selectProgramRsvnSetMax(EdcProgramVO vo) {
        return edcProgramMapper.selectProgramRsvnSetMax(vo);
    }

    public List<String> selectRsvnSetNmList(int orgNo) {
        return edcProgramMapper.selectRsvnSetNmList(orgNo);
    }
}
