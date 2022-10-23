package com.hisco.intrfc.dashboard.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * Dashboard
 * 
 * @author 전영석
 * @since 2020.10.18
 * @version 1.0, 2020.10.18
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.10.18 최초작성
 */
@Mapper("dashboardMapper")
public interface DashboardMapper {

    /**
     * 오늘 일자를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectToday(Map<String, Object> paramMap);

    /**
     * 어제 일자를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectYesterday(Map<String, Object> paramMap);

    /**
     * 선택 일자를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectDate(Map<String, Object> paramMap);

    /**
     * Dashboard 회원정보 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt1(Map<String, Object> paramMap);

    /**
     * Dashboard Stt2 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt2(Map<String, Object> paramMap);

    /**
     * Dashboard Stt22 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt22(Map<String, Object> paramMap);

    /**
     * Dashboard Stt3 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt3(Map<String, Object> paramMap);

    /**
     * Dashboard Stt4 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt4(Map<String, Object> paramMap);

    /**
     * Dashboard Stt5 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt5(Map<String, Object> paramMap);

    /**
     * Dashboard Stt52 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt52(Map<String, Object> paramMap);

    /**
     * Dashboard Stt6 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt6(Map<String, Object> paramMap);

}
