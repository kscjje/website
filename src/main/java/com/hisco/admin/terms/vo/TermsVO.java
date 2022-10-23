package com.hisco.admin.terms.vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 약관 VO
 * 
 * @author 진수진
 * @since 2020.07.29
 * @version 1.0, 2020.07.29
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.29 최초작성
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class TermsVO extends ComDefaultVO {

    /**
     * 약관고유번호
     */
    private String stplatId = "";

    private int orgNo;

    /**
     * 약관명
     */
    private String stplatNm = "";

    /**
     * 약관내용
     */
    private String stplatCnts = "";

    /**
     * 생성일시
     */
    private Timestamp regdate = null;

    /**
     * 생성자
     */
    private String reguser = "";

    /**
     * 변경일자
     */
    private Timestamp moddate = null;

    /**
     * 변경자
     */
    private String moduser = "";

    /**
     * 사용여부
     */
    private String useYn = "";

    /**
     * 기존 약관고유번호
     */
    private String oldStplatId = "";

    private String creator = "";

    private String updator = "";

    private Date createDate = null;

    private Date updatedate = null;

    // 검색용
    private List<String> stplatIdList = null;

}
