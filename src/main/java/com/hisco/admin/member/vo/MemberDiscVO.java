package com.hisco.admin.member.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class MemberDiscVO {

    private String comcd;
    /* 회원번호 */
    private String memNo;

    private int orgNo;
    private String orgNm;

    private String dcNm;
    private int dcRate;

    private int discountSeq;
    private String discountCd;
    private String dcconfirmYn;
    private String dcconfSdate;
    private String dcconfEdate;

    /* 등록일시 */
    private Timestamp certDate;
}
