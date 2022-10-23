package egovframework.com.cop.bbs.service;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Data;

/**
 * 게시판 추가입력항목 VO
 * 
 * @author 이윤호
 * @since 2021.11.08
 * @version 1.0, 2021.11.08
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          이윤호 2021.11.08 최초작성
 */
@SuppressWarnings("serial")
@Data
public class BoardItemVO implements Serializable {

    /** 게시판 ID */
    private String bbsId = "";

    /** 항목순번 */
    private int bbsEtcseq = 0;

    /** 항목타입유형 */
    private String bbsItemType = "";

    /** 항목영문고유ID */
    private String bbsItemEnid;

    /** 항목타이틀명 */
    private String bbsItemNm = "";

    /** 항목순서 */
    private String bbsItemSort = "";

    // 아래 부터는 게시글 읽어 올때 사용
    /** 게시글 ID */
    private long nttId = 0;

    /** Svalue */
    private String bbsItemSvalue = "";

    /** Lvalue */
    private String bbsItemLvalue = "";

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
