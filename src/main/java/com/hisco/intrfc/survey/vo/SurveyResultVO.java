package com.hisco.intrfc.survey.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * @Class Name : SurveyMstVO.java
 * @Description : 설문조사 결과 VO
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 10. 08 김희택
 * @author 김희택
 * @since 2020. 10. 08
 * @version
 * @see
 */
@SuppressWarnings("serial")
@Data
@ToString
public class SurveyResultVO implements Serializable {

    private String comcd;// 기관고유번호
    private int qestnarId;// 설문주제고유번호
    private int qestnarStdno; // 설문조사기준고유번호
    private String qestnarResltNo;// 설문조사결과순번
    private String qenstarSendseq;// 피드백설문발송고유번호
    private String qestnarResltdate;// 설문조사응답일자
    private String qestnarReslttime;// 설문조사응답시간
    private String qestnarMembgbn;// 설문조사응답회원구분
    private String qestnarMemno;// 설문조사응답회원번호
    private String qestnarMembWebid;// 설문조사응답웹ID
    private String qestnarUsername;// 설문조사비회원응답자이용자명
    private String qestnarUsertelno;// 설문조사비회원응답자연락처
    private String qestnarUsepartcd;// 설문조사비회원응답이용사업장
    private String qestnarProgrmid;// 설문조사비회원응답이용사업장
    private String qestnarTerminalType;// 설문조사응답경로구분
    private Date regdate;// 등록일시
    private String reguser;// 등록자

    // param
    public List<SurveyResultDetailVO> dtlList;

}
