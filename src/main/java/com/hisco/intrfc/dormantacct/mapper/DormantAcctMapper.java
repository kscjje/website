package com.hisco.intrfc.dormantacct.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

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
@Mapper("dormantAcctMapper")
public interface DormantAcctMapper {

    /**
     * 휴면계정 전환 처리 관련 정보를 조회한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectDormantAcctExeList(Map<String, Object> paramMap);

    /**
     * 휴면계정 전환 관련 계정 정보를 조회를 한다
     *
     * @param Map
     * @return List
     * @throws Exception+
     */
    public List<?> selectDormantAcctMainList(Map<String, Object> paramMap);

}
