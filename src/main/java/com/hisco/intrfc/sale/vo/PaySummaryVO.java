package com.hisco.intrfc.sale.vo;

import lombok.Data;

@Data
public class PaySummaryVO {
    private String comcd;
    private int edcRsvnReqid;

    private String receiptNo; // 영수증번호
    private String onoffPayGbnNm; // 온라인결제 or오프라인결제
    private String payComcd; // 결제사코드
    private String payMethod; // 결제방법(cash, bank, card, vbank)
    private String payMethodNm; // 결제방법명
    private String financeCd; // 은행-카드사코드
    private String financeNm; // 은행-카드사명
    private String appNo; // 승인번호
    private String payDate; // 년월일
    private String payTime; // 시분초
    private String oid; // 주문번호
    private int payAmt; // 결제금액
    private int totPayAmt; // 결제전체금액(최초결제금액 - 취소금액)
    private String refDipositorNm; // 환불예금자명
    private String refBankCd; // 환불은행코드
    private String refBankNm; // 환불은행명
    private String refAcntNo; // 환불계좌번호
    private int cancelAmt; // 취소금액
    private String cancelDtime; // 취소일시(년월일시분초)
    private int cancelDcAmt; // 할인취소금액

    /* 할인정보 */
    private int discountrate;
    private int discountamount;
    private String discountcd;
    private String discountNm;

    // 가상계좌
    private String vbankAccountNo;
    private String vbankName;
    private String retDpstrNm;
    private String retBankNm;
    private String retBankCd;
    private String retAcountNum;

    // 신용카드
    private String cardNo;
    private String tid; // pg사 trasaction id

    private String dbEncKey;
}
