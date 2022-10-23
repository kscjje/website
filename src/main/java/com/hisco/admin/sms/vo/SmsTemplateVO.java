package com.hisco.admin.sms.vo;

import java.sql.Timestamp;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;

@Data
public class SmsTemplateVO extends ComDefaultVO {

    private int msgcd;
    private int msgno;
    private int oldMsgno;

    private String msgName;
    private String sendmsg;

    private String msgTypenm;

    private String msgSendmethod;
    private String kkoMessageTemplateId;
    private String sendTelno;

    private String msgnm;

    /**
     * 등록자 id
     */
    private String reguser;

    /**
     * 등록일
     */
    private Timestamp regdate;

    /**
     * 변경자 id
     */
    private String moduser;

    /**
     * 변경일
     */
    private Timestamp moddate;

}
