package com.hisco.intrfc.sms.vo;

import com.hisco.cmm.object.CodeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BizMsgType implements CodeEnum {
    SMS("0"), LMS("5"), AT("6"), FT("7"); // AT:알림톡, FT:친구톡

    @Getter
    final private String code;
}