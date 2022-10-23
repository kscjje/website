package com.hisco.intrfc.survey.vo;

import java.io.Serializable;
import java.util.List;

import com.hisco.cmm.util.Config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Class Name : SurveyMstVO.java
 * @Description : 설문조사 질문 VO
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
@EqualsAndHashCode(callSuper = false)
@ToString
public class SurveyQstVO implements Serializable {

    private String comcd = Config.COM_CD;// 운영사업자고유번호
    private int qestnarId; // 설문주제고유번호
    private int qestnsSeq; // 설문조사질문순번. 설문주제별 항목순번(MAX+1) 를 기록한다.
    private String qestnsName; // 설문조사질문명
    private String qestnsType; // 질문유형. 설문유형을 관리하는 시스템 분류코드 . COT_GRPCD.GRP_CD 'SM_QESTNAR_TYPE'.
    private int qestnarScore; // 설문항목기준점수

    private String regdate; // 등록일시
    private String reguser; // 등록자
    private String moddate; // 수정일시
    private String moduser; // 수정자

    // 설문조사질문선택항목리스트
    private List<SurveyQstItemVO> itemList;
}
