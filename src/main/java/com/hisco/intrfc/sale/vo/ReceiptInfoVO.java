package com.hisco.intrfc.sale.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReceiptInfoVO {
    private String comcd; // 운영사업자고유번호
    private String receiptNo; // 영수증번(거래일자(6)+일련번호(7). 일련번호는 seq_receipt_no 이용)
    private int orgNo; // 평생학습포털을 강좌를 운영하는 등록된 기관의 고유번호로. 시퀀스(SEQ_ORGNO) 를 사용
    private String rptDate; // 매출일자
    private String rptTime; // 매출시간
    private int payAmt; // 총결제금액
    private int cashAmt; // 현금결제금액
    private int cardAmt; // 카드결제금액
    private String remark; // 비고
    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자
}
