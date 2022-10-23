package com.hisco.admin.partcd.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.partcd.mapper.PartCdMapper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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
@Service("partCdService")
public class PartCdService extends EgovAbstractServiceImpl {

    @Resource(name = "partCdMapper")
    private PartCdMapper partCdMapper;

    /**
     * 사업장 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectPartCdList(Map<String, Object> paramMap) throws Exception {
        return partCdMapper.selectPartCdList(paramMap);
    }

    /**
     * 사업장 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectPartCdAllList(Map<String, Object> paramMap) throws Exception {
        return partCdMapper.selectPartCdAllList(paramMap);
    }

    /**
     * 사업장 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectPartCdAllListByParm(Map<String, Object> paramMap) throws Exception {
        return partCdMapper.selectPartCdAllListByParm(paramMap);
    }

    /**
     * 사업장 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectPartCdDetail(Map<String, Object> paramMap) throws Exception {
        return partCdMapper.selectPartCdDetail(paramMap);
    }

    /**
     * 공통 코드 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectCotGrpCdList(Map<String, Object> paramMap) throws Exception {
        return partCdMapper.selectCotGrpCdList(paramMap);
    }

    /**
     * 사업장 관리 정보를 등록한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int insertPartCd(Map<String, Object> paramMap) throws Exception {
        return partCdMapper.insertPartCd(paramMap);
    }

    /**
     * 사업장 관리 정보를 수정한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int updatePartCd(Map<String, Object> paramMap) throws Exception {
        return partCdMapper.updatePartCd(paramMap);
    }

    /**
     * 사업장 관리 정보를 삭제한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int deletePartCd(Map<String, Object> paramMap) throws Exception {
        return partCdMapper.deletePartCd(paramMap);
    }

    /**
     * 사업장 관리 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectPartCdAllListRuleOut(Map<String, Object> paramMap) throws Exception {
        return partCdMapper.selectPartCdAllListRuleOut(paramMap);
    }
}
