package com.hisco.intrfc.showcounter.mapper;

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
@Mapper("showCounterMapper")
public interface ShowCounterMapper {

    public void showCounterMove(Map<String, Object> paramMap);

}
