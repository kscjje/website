package com.hisco.intrfc.sale.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelngInfoVO {
    private String comcd; // 운영사업자고유번호
    private int selngId; // 매출고유번호
    private int orgSelngId; // 원천매출고유번호. 취소시 select insert 시 사용
    private String memNo; // 회원번호. 비회원인경우 NULL 기록
    private String registGbn; // 등록구분
    private String registPartcd; // 매출등록사업장고유번호
    private int orgNo; // 매출기준이용기관고유번호. 매출수입을 정산하는 이용기관고유번호로 매출상품의 등록운영 이용기관을 지정함
    private String selngPartCd; // 매출기준사업장. (매출수입을 정산기준이 되는 사업장 고유번호를 기록함 매출품목 등록운영되는 사업장 고유번호를 기록함). 매출사업장이 없는 경우는 NULL
                                // 기록한다.
    private String itemCtgd; // 품목분류
    private int itemCd; // 품목고유번호
    private String selngDate; // 매출일자
    private String selngTime; // 매출시간
    private String slipType; // 전표구분
    private String slipKind; // 전표종류(확장성)

    private int useMonthcnt = 1; // 품목이용개월수
    private String useItemSdate; // 품목이용시작일
    private String useItemEdate; // 품목이용종료일

    private int costAmt; // 원가. program_item.costAmt
    private int unitAmt; // 단가. program_item.salamt
    private int salnum; // 판매수량. 1
    private int dcAmt; // 할인총액
    private int salamt; // 매출금액 unitAmt - dcAmt

    private String salamtUpdnUnit; // 매출금액절상절사적용금액단위
    private String salamtUpdnGbn; // 매출금액절상절사적용구분
    private String vatYn; // 과세여부. Y: 과세. N: 비과세
    private int vatAmt; // 부가세
    private String setleMemno; // 결제회원번호

    private String midReturnyn; // 중도환불여부
    private String changeYn; // 변경여부(확장성). . N:변경없음， B:변경전 매출， Y:변경후 매출 .
    private String returnYn; // 환불여부. Y:환불. N:정상매출
    private String orgSelngDate; // 환불취소 전표발생시 원거래전표의 거래일자를 기록한다.
    private String delYn; // 거래삭제여부
    private String recessYn; // 기간연장여부
    private String remark; // 비고

    private String terminalType; // 등록터미널타입
    private String oid; // 거래주문번호를 기록한다..
    private String receiptNo; // 영수증번
    private String slipState; // 전표상태
    private String parentcomcd; // 상위전표기관고유번호. 매출거래내역의 환불 마이너스 매출 발생시 원거래의 PK 기록
    private int parentselngid; // 상위전표정산번호. 매출거래내역의 환불 마이너스 매출 발생시 원거래의 PK 기록
    private int useCnt; // 품목이용가능횟수(확장성)
    private String acountInDate; // 환불입금예정일자(확장성)

    private String dpstrNm; // 환불계좌예금주(확장성)
    private String bankCd; // 환불은행코드(확장성). 계좌로 환불된 내역일 경우 계좌환불 은행코드 를 기록한다.
    private String bankNm; // 환불은행명(확장성). 계좌로 환불될경우 환불은행명을 기록한다.
    private String acountNum; // 환불계좌번호(확장성)
    private String dbEncKey;

    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자
}
