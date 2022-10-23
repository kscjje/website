package com.hisco.user.edcatnlc.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hisco.admin.eduadm.vo.EdcDaysVO;
import com.hisco.admin.eduadm.vo.EdcNoticeVO;
import com.hisco.admin.eduadm.vo.EdcTargetAgeVO;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 교육프로그램신청관리 Mapper
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 */
@Mapper("edcProgramMapper")
public interface EdcProgramMapper {

    /**
     * 교육프로그램 목록을 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<EdcProgramVO> selectProgramList(EdcProgramVO vo);

    public List<HashMap<String, Object>> getProgramList(HashMap<String, Object> pram);
    
    public List<EdcDaysVO> selectProgramDays(EdcProgramVO vo);

    public EdcProgramVO selectProgramDetail(EdcProgramVO vo);

    public EdcNoticeVO selectProgramNotice(EdcProgramVO vo);

    public EdcProgramVO selectProgramRsvn(EdcProgramVO vo);

    public List<EdcTargetAgeVO> selectTargetAgeList(EdcProgramVO vo);

    public List<EdcProgramVO> selectProgramRsvnSetList(EdcProgramVO vo);

    public String selectProgramRsvnSetMax(EdcProgramVO vo);

    public EdcProgramVO selectProgramRsvnSetDetail(EdcProgramVO vo);

    public List<String> selectRsvnSetNmList(@Param("orgNo") int orgNo);

}
