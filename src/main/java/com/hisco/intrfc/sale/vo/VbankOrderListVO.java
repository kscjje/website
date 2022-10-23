package com.hisco.intrfc.sale.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VbankOrderListVO {
    private String comcd;
    private int vbankSeq;
    private int vbankOrdseq; // 가상계좌신청정보별 MAX(순번) 을 기록한다.
    private String vbankReqtype;
    private int trNo; // 가상계좌입금에 대한 교육예약신청 고유번호를 기록한다
    private int costAmt;
    private int dcAmt;
    private int totalAmt;
    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자
}
