package com.hisco.intrfc.sale.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayListVO {
    private String comcd; // 운영사업자고유번호
    private int selngId; // 매출고유번호
    private int orgSelngId; // 환불시 원장의 매출고유번호
    private int paySeq; // 납부순번
    private int orgPaySeq; // 환불시 원장의 납부순번
    private String slipType; // 전표구분. 1: 정상 2: 환불 3: 취소
    private String receiptNo; // 영수증번호
    private String payDate; // 결제일자
    private String payTime; // 결제시간
    private int payAmt; // 결제금액
    private String appDate; // 거래승인일시
    private String appNo; // 거래승인번호
    private String cashier; // 수납자
    private String inDate; // 입금예정일자
    private String pComcd; // 결제대행사분류
    private String pType; // 결제수단구분
    private String methodCd; // 결제수단코드 . 결제구분별 지불수단코드를 기록한다.. . - 카드 결제시 해당 카드승인응답전문 내역의 카드사코드를 참조하여 해당 결제업체별 결제수단코드테이블
                             // 목록중 . 동일한 결제코드를 기록한다. . 동일한 코드가 없는경우 카드사코드미등록 'XX' 를 기록함 . - 실시간계좌이체， 해당 PG 사 BANK 코드를
                             // 기록한다.. 해당 카드승인응답전문 내역의 은행코드를 참조하여 해당 결제업체별 결제수단코드테이블 실시간계좌이체 코드 목록중 . 동일한 결제코드를 기록한다. .
                             // 동일한 코드가 없는경우 은행코드 미등록 'XX' 를 기록함 . - 가상계좌이체， 해당 PG 사 BANK 코드를 기록한다.. 해당 카드승인응답전문 내역의
                             // 은행코드를 참조하여 해당 결제업체별 결제수단코드테이블 가상계좌이체 코드 목록중 . 동일한 결제코드를 기록한다. . 동일한 코드가 없는경우 은행코드 미등록
                             // 'XX' 를 기록함 . . - 현금결제수단은 'CH' 현금영수증은 'CV， 무통장계좌입금 'CA'.
    private String methodNm;
    private String checkGbn; // 체크카드구분.
    private String oid; // 거래주문번호
    private String cancelYn; // 취소여부
    private String terminalType; // 등록터미널타입
    private String remark; // 비고
    private String slipState; // 전표상태
    private String orgPayDate; // 환불취소원거래일자. 환불취소전표인경우 원전표의 거래일자를 기록한다.
    private String parentcomcd; // 상위결제내역기관고유번호
    private String parentselngid; // 상위결제내역매출고유번호
    private int parentpayseq; // 상위결제내역납부순
    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자

    private String financeBrandCd; // 신용카드 브랜드코드(100, 200,,)
}
