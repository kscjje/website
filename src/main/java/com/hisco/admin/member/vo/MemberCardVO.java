package com.hisco.admin.member.vo;

import java.sql.Timestamp;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;

@Data
@SuppressWarnings("serial")
public class MemberCardVO extends ComDefaultVO {
    /* 회원번호 */
    private String memNo;

    private String cardNo;

    private String issueDate;
    private String cardGbn;
    private String cardGbnNm;
    private String useYn;
    private String bigo;

    /* 등록일시 */
    private Timestamp regdate;
    /* 등록자 */
    private String reguser;
    /* 수정일시 */
    private Timestamp moddate;
    /* 수정자 */
    private String moduser;

    private int totalCnt;
}
