package com.hisco.intrfc.survey.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * @Class Name : SurveyRsvnVO.java
 * @Description : 설문조사 결과상세정보 VO
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
public class SurveyResultDetailVO implements Serializable {

    private String comcd;// 기관고유번호
    private int qestnarId;// 설문주제고유번호
    private int qestnarStdno;// 설문조사기준고유번호
    private String qestnarResltNo;// 설문조사결과순번
    private int qestnsSeq;// 설문조사질문순번
    private int resltItemseq;// 설문조사응답선택항목
    private int resltItemscore;// 설문조사응답선택항목점수
    private String resltSbjct;// 설문조사주관식결과내용
    private Date regdate;// 등록일시
    private String reguser;// 등록자

    private String qestnsName;
    private String qestnsType;
    private int qestnarScore;
    private String qestnarItemnm;
    private int qestnarItemScore;
}
