package com.hisco.intrfc.sale.vo;

import javax.validation.constraints.NotNull;

import com.hisco.cmm.util.Config;

import lombok.Data;

@Data
public class SaleFormItemVO {
    // table: program_item
    @NotNull
    private String comcd = Config.COM_CD;
    private int edcRsvnReqid; // 일련번호
    private int selngId; // selng_info, pay_list, edc_rsvn_compitinfo > selng_id
    private int paySeq; // pay_list > pay_seq

    @NotNull
    private int orgNo;
    private String partCd;
    @NotNull
    private int itemCd;// edc_program.edc_prgmid과 연결됨. edc_prgm_item 매핑테이블 참고
    private int costAmt = 0; // 원가
    private int salamt = 0; // 판매금액(단가)
    private int saleAmt = 0; // 판매금액(단가)

    private int monthCnt; // 상품이용개월수
    private String vatYn;// 과세여부

    private int nwpayAmt = 0; // 노원페이금액
    private String nwpayId;
    private int dcAmt = 0; // 할인금액
    private String discountCd; // 할인코드
    private int discountRate; // 할인율

    private String nwpayOrdid;// 노원페이거래번호(트랜젝션번호)

    // (부분)취소 관련 항목
    private int useDateCnt = 0; // 이용일수
    private int remainDateCnt = 0; // 남은 수업일수
    private int cancelDcAmt = 0; // 환불(취소) 할인금액
    private int cancelAmt = 0; // 환불(취소) 결제금액
    private String cancelMethod; // CANCEL_METHOD_PGVAN
    private String cancelPayComcd;
    private String cancelPayMethod;
    private String cancelFinanceCd;
    private String cancelFinanceNm;
    private String cancelDate; // 취소일자

    @NotNull
    private int edcPrgmNo; // 프로그램id
    private String CtgCd; // 카테고리
    @NotNull
    private String edcSdate; // 프로그램 시작일
    @NotNull
    private String edcEdate; // 프로그램 종료일
    @NotNull
    private int edcRsvnsetSeq; // 프로그램 차수

    private String skipRsvnInfoTableYn = Config.NO;
}
