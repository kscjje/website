package com.hisco.intrfc.email.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * EMail 송신 처리
 * 
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.05 최초작성
 */
@Mapper("eMailMapper")
public interface EMailMapper {

    public void insertSeokLogMyBatis1(Map<String, Object> paramMap);

    public void insertSeokLogMyBatis2(Map<String, Object> paramMap);

    public List<?> selectSeokLogMyBatis(Map<String, Object> paramMap);

    public List<?> selectCallReferProc(Map<String, Object> paramMap);

    public List<?> selectCallReferProc2(Map<String, Object> paramMap);
}
