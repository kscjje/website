package com.hisco.intrfc.sms.mapper;

import java.util.Map;

import com.hisco.intrfc.sms.vo.SmsVO;

import egovframework.com.cmm.LoginVO;
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
@Mapper("smsMapper")
public interface SmsMapper {

    public int insertSmsPK();

    public int getSmsPK();

    public int sendSmsMessage(Map<String, String> paramMap);

    public int sendLmsMessage(Map<String, String> paramMap);

    public int sendMmsMessage(Map<String, String> paramMap);

    public int sendMessage(Map<String, String> paramMap, LoginVO userVO);

    public Map<String, String> insertMmsContentInfo(Map<String, String> paramMap);

    public SmsVO selectMsgTemplate(Map<String, String> paramMap);
}
