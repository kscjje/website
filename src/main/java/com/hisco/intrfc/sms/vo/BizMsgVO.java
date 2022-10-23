package com.hisco.intrfc.sms.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BizMsgVO extends ComDefaultVO {
    private String cmid;
    private BizMsgType msgType;
    private String sendTime;

    private String destPhone;
    private String destName;
    private String sendPhone;
    private String sendName;
    private String subject;
    private String msgBody;

    private int msgNo;
    private String kakaoSenderKey;

    private String reguser;
}
