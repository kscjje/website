package com.hisco.intrfc.pubdata.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.pubdata.mapper.PubDataMapper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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
@Service("pubDataService")
public class PubDataService extends EgovAbstractServiceImpl {

    @Resource(name = "pubDataMapper")
    private PubDataMapper pubDataMapper;

    /**
     * 교육 예약 예약 안내 정보를 상세 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectEdcRsvnInfoList(Map<String, Object> paramMap) throws Exception {
        return pubDataMapper.selectEdcRsvnInfoList(paramMap);
    }

    /**
     * 교육 예약 Master 정보 실적을 조회한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectEdcRsvnInfoCnt(Map<String, Object> paramMap) throws Exception {
        return pubDataMapper.selectEdcRsvnInfoCnt(paramMap);
    }

    /**
     * 교육 예약 Master 정보 실적을 조회한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectEvtRsvnInfoCnt(Map<String, Object> paramMap) throws Exception {
        return pubDataMapper.selectEvtRsvnInfoCnt(paramMap);
    }

    /**
     * 교육 예약 예약 안내 정보를 상세 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectEdcarsvnList(Map<String, Object> paramMap) throws Exception {
        return pubDataMapper.selectEdcarsvnList(paramMap);
    }

    /**
     * 강연/행사/영화 예약 정보를 정보를 상세 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectEvtRsvnList(Map<String, Object> paramMap) throws Exception {
        return pubDataMapper.selectEvtRsvnList(paramMap);
    }

    /**
     * 관람 예약 정보를 정보를 상세 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectExbtRsvnList(Map<String, Object> paramMap) throws Exception {
        return pubDataMapper.selectExbtRsvnList(paramMap);
    }

}
