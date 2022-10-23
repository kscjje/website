package com.hisco.intrfc.parkng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * Parking 연계 처리
 * 
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.05 최초작성
 */
@Mapper("parkngMapper")
public interface ParkngMapper {

    /**
     * 차량 번호에 속한 회원 정보 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectMemerNo(Map<String, Object> paramMap);

    /**
     * 차량 번호에 속한 회원 정보 건수 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public int selectMemerRow(Map<String, Object> paramMap);

    /**
     * 연회원 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectMemerYearInfor(Map<String, Object> paramMap);

    /**
     * 관람회원 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectExbtRsvnInfor(Map<String, Object> paramMap);

    /**
     * 교육수강자 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectTrainInfor(Map<String, Object> paramMap);

    /**
     * 강연/행사/영화 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectEvtRsvnInfor(Map<String, Object> paramMap);

    /**
     * 특별회원 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectSpeclInfor(Map<String, Object> paramMap);

    /**
     * I/F 로그 저장(Req)
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public void insertParkingReqInfor(Map<String, Object> paramMap);

    /**
     * I/F 로그 저장(Res)
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public void insertParkingResInfor(Map<String, Object> paramMap);

    /**
     * 교육 회원 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectEdcInfor(Map<String, Object> paramMap);

    /**
     * 특별 회원 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectSpeInfor(Map<String, Object> paramMap);

    /**
     * 유료 회원 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectPayInfor(Map<String, Object> paramMap);
}
