package com.hisco.admin.board.vo;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Data;

/**
 * 게시판 카테고리 관리 VO
 * 
 * @author 진수진
 * @since 2020.07.22
 * @version 1.0, 2020.07.22
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.22 최초작성
 */
@SuppressWarnings("serial")
@Data
public class BoardCtgVO implements Serializable {

    /** 게시판 ID */
    private String bbsId = "";

    /** 카테고리 코드 */
    private String ctgId = "";

    /** 카테고리 이름 */
    private String ctgNm = "";

    /** 정렬 순서 */
    private String ctgSort;

    /** 사용여부 */
    private String useAt = "";

    /** 최초등록자 아이디 */
    private String frstRegisterId = "";

    /** 최초 등록자명 */
    private String frstRegisterNm = "";

    /** 최초등록시점 */
    private Date frstRegisterPnttm = null;

    /** 최종수정자 아이디 */
    private String lastUpdusrId = "";

    /** 최종수정시점 */
    private Date lastUpdusrPnttm = null;

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
