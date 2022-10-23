package com.hisco.intrfc.pubdata.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 공공 데이터 연계 처리
 * 
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.05 최초작성
 */
@Mapper("pubDataMapper")
public interface PubDataMapper {

    /**
     * 교육 예약 예약 안내 정보를 상세 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectEdcRsvnInfoList(Map<String, Object> paramMap);

    /**
     * 교육 예약 Master 정보 실적을 조회한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectEdcRsvnInfoCnt(Map<String, Object> paramMap);

    /**
     * 교육 예약 Master 정보 실적을 조회한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectEvtRsvnInfoCnt(Map<String, Object> paramMap);

    /**
     * 교육 예약 예약 안내 정보를 상세 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectEdcarsvnList(Map<String, Object> paramMap);

    /**
     * 강연/행사/영화 예약 정보를 정보를 상세 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectEvtRsvnList(Map<String, Object> paramMap);

    /**
     * 관람 예약 정보를 정보를 상세 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectExbtRsvnList(Map<String, Object> paramMap);

}
