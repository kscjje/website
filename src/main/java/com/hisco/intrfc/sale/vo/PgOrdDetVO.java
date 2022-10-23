package com.hisco.intrfc.sale.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PgOrdDetVO {
    private String comcd; // 운영사업자고유번호
    private String oid; // 거래주문번호
    private int oidSeq; // 거래내역순번. OID별 일련번호부여(MAX+1)
    private String oidRsvnNo; // 주문예약번호
    private int oidItemcd; // 품목고유번호. 품목고유번호는 예약번호가 없는 거래상품에 대하여 품목고유번호를 기록함.. 예시> 연회원권 결제 거래
    private String oidPcancelNo; // 부분취소요청번호. . 거래당 부분 거래취소요청된 번호순번을 . 3자리 '001' 로 번호룰 부여한다.
    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자
}
