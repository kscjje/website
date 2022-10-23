package com.hisco.intrfc.sale.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CancelVO {
    private String comcd; // 운영사업자고유번호
    private int saleSeq; // 매출고유번호
    private String retRegdate; // 등록일자
    private String memNo; // 회원번호
    private String cancelCd; // 환불사유코드. COT_GRPCD.GRPCD = 'CM_RETREASON_CD'
    private String cancelDate; // 환불일자. 환불정산기준일자를 기록한다.
    private int returnAmt; // 환불금액
    private int vatAmt; // 부가세
    private int breakAmt; // 위약금
    private int useAmt; // 이용금액
    private int rateAmt; // 카드수수료
    private String remark; // 비고
    private int returnSelngid; // 환불매출고유번호
    private String oldItemEdate; // 환불이전매출상품이용종료일자
    private int totalBasedDaycnt; // 총이용기준일수
    private int usedDaysCount; // 정산이용일수
    private String updownAmtUnit; // 위약금및이용금액절상절사금액단위
    private String updownAmtGbn; // 위약금및이용금액절상절사구분
    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자
}
