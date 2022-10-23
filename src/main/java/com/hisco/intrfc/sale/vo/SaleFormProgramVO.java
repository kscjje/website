package com.hisco.intrfc.sale.vo;

import lombok.Data;

@Data
public class SaleFormProgramVO {
    // table:edc_program
    private int edcPrgmid; // 프로그램id
    private String CtgCd; // 카테고리
    private String edcSdate; // 프로그램 시작일
    private String edcEdate; // 프로그램 종료일
    private int edcRsvnsetSeq; // 프로그램 차수
}
