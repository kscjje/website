package com.hisco.intrfc.sale.vo;

import lombok.Data;

@Data
public class PayRequestVO {
    private String comcd;
    private String rsvnNo;
    private int saleAmt; // 판매금액
    private int dcAmt; // 할인금액
    private String dcCd; // 할인코드
    private int dcRate; // 할인율
    private int payAmt; // 결제금액
}
