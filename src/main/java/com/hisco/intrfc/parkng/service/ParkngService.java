package com.hisco.intrfc.parkng.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.parkng.mapper.ParkngMapper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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
@Service("parkngService")
public class ParkngService extends EgovAbstractServiceImpl {

    @Resource(name = "parkngMapper")
    private ParkngMapper parkngMapper;

    /**
     * 차량 번호에 속한 회원 정보 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectMemerNo(Map<String, Object> paramMap) throws Exception {
        return parkngMapper.selectMemerNo(paramMap);
    }

    /**
     * 차량 번호에 속한 회원 정보 건수 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public int selectMemerRow(Map<String, Object> paramMap) throws Exception {
        return parkngMapper.selectMemerRow(paramMap);
    }

    /**
     * 연회원 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectMemerYearInfor(Map<String, Object> paramMap) throws Exception {
        return parkngMapper.selectMemerYearInfor(paramMap);
    }

    /**
     * 관람회원 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectExbtRsvnInfor(Map<String, Object> paramMap) throws Exception {
        return parkngMapper.selectExbtRsvnInfor(paramMap);
    }

    /**
     * 교육수강자 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectTrainInfor(Map<String, Object> paramMap) throws Exception {
        return parkngMapper.selectTrainInfor(paramMap);
    }

    /**
     * 강연/행사/영화 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectEvtRsvnInfor(Map<String, Object> paramMap) throws Exception {
        return parkngMapper.selectEvtRsvnInfor(paramMap);
    }

    /**
     * 특별회원 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectSpeclInfor(Map<String, Object> paramMap) throws Exception {
        return parkngMapper.selectSpeclInfor(paramMap);
    }

    /**
     * I/F 로그 저장(Req)
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public void insertParkingReqInfor(Map<String, Object> paramMap) throws Exception {
        parkngMapper.insertParkingReqInfor(paramMap);
    }

    /**
     * I/F 로그 저장(Res)
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public void insertParkingResInfor(Map<String, Object> paramMap) throws Exception {
        parkngMapper.insertParkingResInfor(paramMap);
    }

    /**
     * 교육 회원 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectEdcInfor(Map<String, Object> paramMap) throws Exception {
        return parkngMapper.selectEdcInfor(paramMap);
    }

    /**
     * 특별 회원 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectSpeInfor(Map<String, Object> paramMap) throws Exception {
        return parkngMapper.selectSpeInfor(paramMap);
    }

    /**
     * 유료 회원 여부 조회
     * 
     * @param Map
     * @return List<Map>
     * @exception Exception
     */
    public List<?> selectPayInfor(Map<String, Object> paramMap) throws Exception {
        return parkngMapper.selectPayInfor(paramMap);
    }
}
