package com.hisco.intrfc.dashboard.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.dashboard.mapper.DashboardMapper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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
@Service("dashboardService")
public class DashboardService extends EgovAbstractServiceImpl {

    @Resource(name = "dashboardMapper")
    private DashboardMapper dashboardMapper;

    /**
     * 오늘 일자를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectToday(Map<String, Object> paramMap) throws Exception {
        return dashboardMapper.selectToday(paramMap);
    }

    /**
     * 어제 일자를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectYesterday(Map<String, Object> paramMap) throws Exception {
        return dashboardMapper.selectYesterday(paramMap);
    }

    /**
     * 선택 일자를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectDate(Map<String, Object> paramMap) throws Exception {
        return dashboardMapper.selectDate(paramMap);
    }

    /**
     * Dashboard 회원정보 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt1(Map<String, Object> paramMap) throws Exception {
        return dashboardMapper.selectStt1(paramMap);
    }

    /**
     * Dashboard Stt2 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt2(Map<String, Object> paramMap) throws Exception {
        return dashboardMapper.selectStt2(paramMap);
    }

    /**
     * Dashboard Stt22 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt22(Map<String, Object> paramMap) throws Exception {
        return dashboardMapper.selectStt22(paramMap);
    }

    /**
     * Dashboard Stt3 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt3(Map<String, Object> paramMap) throws Exception {
        return dashboardMapper.selectStt3(paramMap);
    }

    /**
     * Dashboard Stt4 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt4(Map<String, Object> paramMap) throws Exception {
        return dashboardMapper.selectStt4(paramMap);
    }

    /**
     * Dashboard Stt5 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt5(Map<String, Object> paramMap) throws Exception {
        return dashboardMapper.selectStt5(paramMap);
    }

    /**
     * Dashboard Stt52 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt52(Map<String, Object> paramMap) throws Exception {
        return dashboardMapper.selectStt52(paramMap);
    }

    /**
     * Dashboard Stt6 통계를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectStt6(Map<String, Object> paramMap) throws Exception {
        return dashboardMapper.selectStt6(paramMap);
    }

}
