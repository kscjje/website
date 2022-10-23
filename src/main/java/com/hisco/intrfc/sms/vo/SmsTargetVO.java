package com.hisco.intrfc.sms.vo;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class SmsTargetVO implements Serializable {
    private String memnm;
    private String memhp;
}
