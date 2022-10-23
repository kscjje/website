package com.hisco.admin.admcmm.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 관리자 공통 Mapper
 * 
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 */
@Mapper("admCmmMapper")
public interface AdmCmmMapper {

    /**
     * 공통 코드 정보를 조회한다.
     */
    public List<?> selectCotGrpCdList(Map<String, Object> paramMap);

    /**
     * 공통 코드 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectCotGrpCdListByParm(Map<String, Object> paramMap);

    /**
     * DB 시간을 가져온다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectDBTime(Map<String, Object> paramMap);

    /**
     * DB 시간을 가져온다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectComtecopseq(Map<String, Object> paramMap);

    /**
     * File Seq를 저장한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int updateComtecopseq(Map<String, Object> paramMap);

    /**
     * File Grp 정보를 저장한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int insertCmFileGrp(Map<String, Object> paramMap);

    /**
     * File Lst 정보를 저장한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int insertCmFileLst(Map<String, Object> paramMap);

    /**
     * 시컨스로 GRPINNB를 알아낸다
     *
     * @param Map
     * @return String
     * @throws Exception
     */
    public String getCmFileGrpId(Map<String, Object> paramMap);

}
