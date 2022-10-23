package com.hisco.admin.eduadm.mapper;

import java.util.List;

import com.hisco.admin.comctgr.vo.ComCtgrVO;
import com.hisco.admin.eduadm.vo.EdcLimitVO;
import com.hisco.admin.eduadm.vo.EdcProgramVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 기관별 교육프로그램예약제한설정 Mapper
 *
 * @author 진수진
 * @since 2021.11.15
 * @version 1.0, 2021.11.15
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.11.15 최초작성
 */
@Mapper("eduLimitMapper")
public interface EduLimitMapper {

    /**
     * 예약제한 설정 상세를 조회한다.
     *
     * @param EdcLimitVO
     * @return EdcLimitVO
     * @throws Exception
     */
    public EdcLimitVO selectEdcLimitRecord(EdcLimitVO paramMap);

    public int selectCountEdcLimit(EdcLimitVO paramMap);

    public int insertEdcLimit(EdcLimitVO paramMap);

    public int updateEdcLimit(EdcLimitVO paramMap);

    public int insertEdcLimitCtgcd(EdcLimitVO paramMap);

    public int insertEdcLimitProgram(EdcLimitVO paramMap);

    public int deleteEdcLimitCtgcd(EdcLimitVO paramMap);

    public int deleteEdcLimitProgram(EdcLimitVO paramMap);

    public List<ComCtgrVO> selectEdcLimitComctgList(EdcLimitVO paramMap);

    public List<EdcProgramVO> selectEdcLimitProgramList(EdcLimitVO paramMap);

}
