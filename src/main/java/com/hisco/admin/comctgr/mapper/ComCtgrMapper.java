package com.hisco.admin.comctgr.mapper;

import java.util.List;
import java.util.Map;

import com.hisco.admin.comctgr.vo.ComCtgrVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 카테고리 관리 Mapper
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 *          진수진 2021.10.21 남양주시청 주민자치센터 평생학습관 수정
 */
@Mapper("comCtgrMapper")
public interface ComCtgrMapper {

    /**
     * 카테고리 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectComCtgrList(Map<String, Object> paramMap);

    /**
     * 카테고리 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectComCtgrListForTree(ComCtgrVO paramMap);

    /**
     * 카테고리 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectComCtgrListForTreeWParm(Map<String, Object> paramMap);

    /**
     * 카테고리 상세 데이타를 조회한다.
     *
     * @param ComCtgrVO
     * @return ComCtgrVO
     * @throws Exception
     */
    public ComCtgrVO selectComCtgrDetail(ComCtgrVO comCtgrVO);


    /**
     * 카테고리 관리 정보를 등록한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int insertComCtgr(ComCtgrVO comCtgrVO);



    /**
     * 카테고리 관리 정보를 수정한다.
     *
     * @param ComCtgrVO
     * @return int
     * @throws Exception
     */
    public int updateComCtgr(ComCtgrVO comCtgrVO);

    /**
     * 카테고리  정보를 삭제한다.
     *
     * @param ComCtgrVO
     * @return ㅍ
     * @throws Exception
     */
    public int deleteComCtgr(ComCtgrVO comCtgrVO);

    /**
     * 최상위 카테고리  정보를 삭제한다.
     *
     * @param ComCtgrVO
     * @return ㅍ
     * @throws Exception
     */
    public int deleteComCtgrTop(ComCtgrVO comCtgrVO);


    /**
     * 부모 카테고리 관리 정보를 삭제한다.
     *
     * @param ComCtgrVO
     * @return ㅍ
     * @throws Exception
     */
    public int deleteComCtgrPrnct(ComCtgrVO comCtgrVO);



    /**
     * 카테고리 정렬 순서를 수정한다
     *
     * @param ComCtgrVO
     * @return int
     * @throws Exception
     */
    public int updateComCtgrSortDefault(ComCtgrVO comCtgrVO);


    /**
     * 카테고리 정렬 순서를 자동 수정한다
     *
     * @param ComCtgrVO
     * @return int
     * @throws Exception
     */
    public int updateComCtgrSortAuto(ComCtgrVO comCtgrVO);




}
