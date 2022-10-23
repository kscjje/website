package com.hisco.intrfc.sale.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleDiscountVO {
    private String comcd; // 운영사업자고유번호
    private int saleSeq; // 매출고유번호. 회원인경우 :
    private int orgSaleSeq;
    private int seq; // 등록순번. 회원별매출내역에따른 순번을 부여한다.
    private int orgSeq;
    private String discountdate; // 적용일자
    private String discountcd; // 할인종류코드
    private int beforeamount; // 할인전금액. 할인적용되기전 금액을 기록한다.
    private int discountrate; // 할인율
    private int discountamount; // 할인금액
    private String nwpayOrdid; // 노원페이를 적용할인된경우 노원페이 적용 주문번호를 기록한다.
    private String etc; // 비고
    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자
}