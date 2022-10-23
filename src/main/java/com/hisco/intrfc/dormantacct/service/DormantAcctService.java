package com.hisco.intrfc.dormantacct.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.dormantacct.mapper.DormantAcctMapper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 설문 대상 전송
 * 
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.05 최초작성
 */
@Service("dormantAcctService")
public class DormantAcctService extends EgovAbstractServiceImpl {

    @Resource(name = "dormantAcctMapper")
    private DormantAcctMapper dormantAcctMapper;

    /**
     * 휴면계정 전환 처리 관련 정보를 조회한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectDormantAcctExeList(Map<String, Object> paramMap) throws Exception {
        return dormantAcctMapper.selectDormantAcctExeList(paramMap);
    }

    /**
     * 휴면계정 전환 관련 계정 정보를 조회를 한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectDormantAcctMainList(Map<String, Object> paramMap) throws Exception {
        return dormantAcctMapper.selectDormantAcctMainList(paramMap);
    }

}
