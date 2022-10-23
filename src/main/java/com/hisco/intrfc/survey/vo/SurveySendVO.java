package com.hisco.intrfc.survey.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * @Class Name : SurveyRsvnVO.java
 * @Description : 설문조사 발송목록 VO
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 10. 12 김희택
 * @author 김희택
 * @since 2020. 10. 12
 * @version
 * @see
 */
@SuppressWarnings("serial")
@Data
@ToString
public class SurveySendVO implements Serializable {

    private String qenstarSendseq;// 피드백설문발송고유번호
    private String qenstarSenddate;// 피드백설문발송일시
    private String comcd;// 기관고유번호
    private int qestnarId;// 설문주제고유번호
    private String qestnarStdno;// 설문조사기준고유번호

    private String qestnarMembWebid;// 설문발송회원웹ID
    private String qestnarMembgbn;// 회원구분
    private String qestnarUsertelno;// 설문조사발송자휴대폰번호(암호화)
    private String qestnarUsername;// 설문조사대상고객명
    private String qestnarMemno;// 설문발송회원번호

    private String qestnarUsepartcd;// 설문조사대상이용사업장
    private String qestnarRsvnNo;// 설문조사대상예약번호
    private String qestnarProgrmid;// 설문조사대상상품고유번호
    private String qestnarGuide;
}
