package com.hisco.intrfc.sale.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SaleFormVO {
    @NotNull
    private String rsvnStat; // SALE, REFUND, CANCEL. TOBE예약상태
    private int edcRsvnReqid; // admin > 수강신청등록 > 등록후 결제정보를 보여주기위한 변수. 설정용이 아닌 조회용

    // member info
    @NotNull
    SaleFormMemberVO member;

    // program_item
    @NotNull
    List<SaleFormItemVO> itemList;

    // payment
    @NotNull
    SaleFormPaymentVO payment;
}
