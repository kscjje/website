package com.hisco.intrfc.sale.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VbankPaymentInfoVO {
    private String comcd;
    private int vbankSeq; // 시퀀스(SEQ_VBANK_IDX) 순번부여
    private String vbankMemNo;
    private String vbankReqtype;
    private String vbankAccountNo;
    private String vbankReqDate;
    private String vbankReqTime;
    private String vbankName;
    private String vbankPname;
    private String vbankPtel;
    private String vbankPemail;

    private String vbankPayWaitdate; // 가상계좌가 소멸되는 마감일자를 기록한다.
    private String vbankPayWaittime; // 가상계좌가 소멸되는 마감시간을 기록한다.

    private String vbankStatus;
    private String vbankBankcd;
    private int vbankAmount;
    private int vbankPayamt;
    private int vbankOveramt;

    private String vbankPaymentDate;
    private String vbankPaymentTime;

    private String vbankMid;
    private String vbankTid;
    private String vbankOid;
    private String vbankEtc;

    private String retDpstrNm;
    private String retBankCd;
    private String retBankNm;
    private String retAcountNum;

    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자

    private String oid; // 주문번호.

    private String dbEncKey;
}
