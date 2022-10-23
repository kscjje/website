package com.hisco.intrfc.sms.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * Sms 송신 처리
 * 
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.20
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.20 최초작성
 */
@Mapper("smsUmsMapper")
public interface SmsUmsMapper {

    public int sendSmsMessage(Map<String, String> paramMap);

}
