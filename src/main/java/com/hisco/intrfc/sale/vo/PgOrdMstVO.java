package com.hisco.intrfc.sale.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PgOrdMstVO {
    private String comcd; // 운영사업자고유번호
    private String oid; // 거래주문번호
    private int oidAmt; // 거래요청금액
    private String oidStat; // 거래요청상태. . 1001 : 거래요청. 2001 : 거래완료. 3001 : 거래취소. 4001 : 거래실패
    private int oidDetRowcnt; // 거래요청상세건수
    private String requestResult; // 거래요청및응답정보. 거래요청응답정보 및 실패정보를 기록한다.
    private String oidCnlDate; // 거래취소일자
    private String oidCnlTime; // 거래취소시간
    private String oidCnlResult; // 거래취소응답정보
    private String oidAppNo; // 거래승인번호
    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자

}
