package com.hisco.intrfc.sms.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class SmsVO implements Serializable {

    private String comcd;
    private int msgno;
    private int msgcd;
    private String msgName;

    private String sendMsg; // 발송메시지
    private String senderTelno; // 발신자전화번호

    private String rsvnSendYn; // 예약전송여부:Y(예약전송), N(즉시발송)
    private String rsvnDtime; // 예약전송일시

    List<SmsTargetVO> targetList;
}
