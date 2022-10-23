package com.hisco.admin.calendar.vo;

import java.sql.Timestamp;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;

@Data
public class CalendarInfoVO extends ComDefaultVO {
    private String comcd;
    private int orgNo;
    private String gubun;
    private String gubunOri;
    private String calDate;
    private String dateType;
    private String dateTypeOri;
    private String cardpayCloseyn;
    private String calendarEtc;
    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;
}
