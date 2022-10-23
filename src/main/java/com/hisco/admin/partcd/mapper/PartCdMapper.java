package com.hisco.admin.partcd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 사업장 관리 Service 구현 클래스
 * 
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 */
@Mapper("partCdMapper")
public interface PartCdMapper {

    /**
     * 사업장 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectPartCdList(Map<String, Object> paramMap);

    /**
     * 사업장 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectPartCdAllList(Map<String, Object> paramMap);

    /**
     * 사업장 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectPartCdAllListByParm(Map<String, Object> paramMap);

    /**
     * 사업장 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectPartCdDetail(Map<String, Object> paramMap);

    /**
     * 공통 코드 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectCotGrpCdList(Map<String, Object> paramMap);

    /**
     * 사업장 관리 정보를 등록한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int insertPartCd(Map<String, Object> paramMap);

    /**
     * 사업장 관리 정보를 수정한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int updatePartCd(Map<String, Object> paramMap);

    /**
     * 사업장 관리 정보를 삭제한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int deletePartCd(Map<String, Object> paramMap);

    /**
     * 사업장 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectPartCdAllListRuleOut(Map<String, Object> paramMap);
}
