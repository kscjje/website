package com.hisco.admin.edcrsvn.mapper;

import java.util.List;

import com.hisco.admin.edcrsvn.vo.EdcProgramReceptionVO;

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
@Mapper("edcProgramReceptionMapper")
public interface EdcProgramReceptionMapper {

    /**
     * 교육프로그램 목록을 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectEdcProgramReceptionList(EdcProgramReceptionVO paramMap);
    //
    // public List<EdcDaysVO> selectProgramDays(EdcProgramVO paramMap);
    //
    // public int insertEdcProgram(EdcProgramVO paramMap);
    //
    // public String selectProgramSetSeq(EdcProgramVO paramMap);
    //
    // public int insertEdcProgramSetInfo(EdcProgramVO paramMap);
    //
    // public int insertProgramItem(EdcProgramVO paramMap);
    //
    // public int insertEdcProgramItem(EdcProgramVO paramMap);
    //
    // public int insertEdcProgramTargetAge(EdcTargetAgeVO targetVO);
    //
    // public int insertEdcProgramLimit(EdcProgmLimitVO limitVO);
    //
    // public int insertEdcDays(EdcDaysVO daysVO);
    //
    // public int insertEdcProgramNotice(EdcNoticeVO noticeVO);
    //
    // public EdcProgramVO selectEdcProgramDetail(EdcProgramVO paramMap);
    //
    // public EdcNoticeVO selectEdcNotice(EdcProgramVO paramMap);
    //
    // public List<EdcTargetAgeVO> selectEdcTargetAge(EdcProgramVO paramMap);
    //
    // public List<EdcProgramVO> selectProgramRsvnSetList(EdcProgramVO paramMap);
    //
    // public String selectProgramRsvnSetMax(EdcProgramVO paramMap);
    //
    // public EdcProgramVO selectProgramRsvnSetDetail(EdcProgramVO paramMap);
    //
    // // 강좌 이미지 수정
    // public void updateEdcProgramFileid(@Param("comcd") String comcd, @Param("edcPrgmNo") int edcPrgmNo,
    // @Param("inputid") String inputid, @Param("atchFileId") String atchFileId, @Param("moduser") String moduser);
    //
    // public int updateEdcProgram(EdcProgramVO paramMap);
    //
    // public int updateEdcProgramIntro(EdcProgramVO paramMap);
    //
    // public int updateEdcProgramNotice(EdcNoticeVO paramMap);
    //
    // public int updateEdcProgramTarget(EdcTargetAgeVO paramMap);
    //
    // public int updateEdcProgramGender(EdcProgramVO paramMap);
    //
    // public int updateProgramItem(EdcProgramVO paramMap);
    //
    // // 신청 나이 제한 삭제
    // public void deleteEdcProgramTarget(@Param("comcd") String comcd, @Param("edcPrgmNo") int edcPrgmNo,
    // @Param("targetSeq") List<String> targetSeq);
    //
    // public int updateEdcProgramRsvn(EdcProgramVO paramMap);
    //
    // public int updateEdcProgramRsvnSetInfo(EdcProgramVO paramMap);
    //
    // public void deleteEdcProgaramWeek(EdcProgramVO paramMap);
    //
    // // 상태변경
    // public void updateProgramStatus(@Param("comcd") String comcd, @Param("prgmId") int prgmId,
    // @Param("type") String type, @Param("val") String val, @Param("moduser") String moduser);

}
