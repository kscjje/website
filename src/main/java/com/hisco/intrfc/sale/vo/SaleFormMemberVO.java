package com.hisco.intrfc.sale.vo;

import lombok.Data;

@Data
public class SaleFormMemberVO {
    private String memNo; // 비회원일경우 정보 없음
    private String memNm; // 회원일경우 회원정보이용 아닌경우 입력값이용
    private String memHp;
    private String memBirthdate;
    private String memBirthSec;
    private String memGender;
}
