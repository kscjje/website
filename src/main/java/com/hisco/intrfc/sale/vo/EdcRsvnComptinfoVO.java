package com.hisco.intrfc.sale.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EdcRsvnComptinfoVO {
    private String comcd; // 운영사업자고유번호
    private int edcRsvnReqid; // 교육신청고유번호. 시퀀스 : SEQ_EDC_REQID
    private int edcRsvnComptid; // 교육신청등록고유번호
    private String edcRsvnMemtype; // 교육신청회원구분. 1001 : 회원. 2001 : 비회원. .
    private String edcMemNo; // 교육대상회원번호
    private String edcReqMemNo; // 교육신청회원번호
    private int edcPrgmNo; // 교육프로그램고유번호
    private String edcRsvnCustnm; // 비회원교육대상고객명
    private String edcRsvnBirthdate;
    private String edcRsvnGender;
    private String edcRsvnMoblphon; // 비회원교육신청자휴대폰번호_암호화. . 비회원인경우 필수적으로 입력받는다.
    private String edcRsvnEmail; // 비회원교육신청자이메일정보_암호화
    private String edcNonmembCertno; // 비회원교육신청자휴대폰본인인증KEY
    private int selngId; // 매출고유번호
    private String edcSdate; // 교육시작일자
    private String edcEdate; // 교육종료일자
    private String edcStime; // 교육시작시간
    private String edcEtime; // 교육종료시간
    private String edcComplstat;
    private String edcComplyn; // 교육수료여부
    private Timestamp edcConfirmDate; // 교육수료확정일시
    private String edcComplUserid; // 교육수료처리담당자
    private String edcRetyn; // 환불여부
    private int retSelngId; // 매출정보의 고유번호를 기록한다. 시퀀스 : SEQ_SELNGID
    private Timestamp regdate; // 등록일시
    private String reguser; // 등록자
    private Timestamp moddate; // 수정일시
    private String moduser; // 수정자
}
