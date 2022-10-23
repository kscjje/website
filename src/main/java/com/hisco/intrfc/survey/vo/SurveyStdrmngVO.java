package com.hisco.intrfc.survey.vo;

import java.io.Serializable;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class SurveyStdrmngVO extends ComDefaultVO implements Serializable {
    // private String comcd; // 운영사업자고유번호
    private int qestnarId; // 설문주제고유번호
    private int qestnarStdno; // 설문조사기준고유번호
    private String qestnarStdrnm; // 설문조사기준명
    private String qestnarOpertype; // 설문조사기간운영방법. . 설문조사운영기간방법을 분류하는 시스템 분류코드 . . 코드구성 : 9999 (1001 - 9999 까지 사용).
                                    // 코드부여규칙 : 시스템엔지니어에 의한 수동부여 . 코드목록. 1001 : 상시운영. 2001 : 기간설정
    private String qestnarMethod; // 설문조사방법. 설문조사운영방법을 분류하는 시스템 분류코드 . COT_GRPCD.GPR_CD = 'SM_QESTNAR_METHOD
    private String qestnarSendLimitpdtype; // 설문조사운영방법이 발송대상 상품이용 자동발송인경우 . 동일 설문조사내용을 대상 이용기간에 상품이용 예약 회원에게 발송 횟수기준을
                                           // 설정한다.. . 교육사업장예약상품 : 교육종료일 기준으로발송. 전시관람예약상품 : 관람일 기준으로 발송. 공연행사예약상품 : 관람일
                                           // 기준으로 발송 . . . 1001 : 일별1회 발송. 2001 : 월별1회 발송. 3001 : 년간1회 발송
    private String qestnarOpenDate; // 설문조사오픈시작일
    private String qestnarOpenTime; // 설문조사오픈시간
    private String qestnarOpenTimeHH; // 설문조사오픈시간
    private String qestnarOpenTimeMM; // 설문조사오픈시간
    private String qestnarOpersdate; // 설문조사운영시작일. 설문운영시작일은 조사기간 운영방법이 기간지정운영 인경우 기록한다.
    private String qestnarOperedate; // 설문조사운영종료일. 설문운영종료일은 조사기간 운영방법이 기간지정운영 인경우 기록한다.
    private String openYn; // 공개여부
    private String useYn; // 사용여부
    private String regdate; // 등록일시
    private String reguser; // 등록자
    private String moddate; // 수정일시
    private String moduser; // 수정자
    private String activeYn; // 활성화여부

    private int submitCnt; // 제출수, TODO: table에 존재하지 않는 칼럼
    private int totCnt;
}
