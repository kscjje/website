package com.hisco.user.evtedcrsvn.service;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Data;

/**
 * @Class Name : ComCtgrVO.java
 * @Description : 강연/행사/영화 카테고리를 위한 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 8. 26 김희택
 * @author 김희택
 * @since 2020. 8. 26
 * @version
 * @see
 */
@Data
@SuppressWarnings("serial")
public class ComCtgrVO implements Serializable {

    private String ctgCd;// 분류코드
    private String ctgNm;// 분류명
    private String ctgDesc;// 분류설명
    private String ctgLvl;// 분류레벨
    private String parentCtgCd;// 상위분류
    private String topCtgCd;// 최상위분류
    private String topPartGbn;// 최상위분류대상사업장구분
    private String partCd;// 최상위분류대상사업장
    private String portOrder;// 정렬번호
    private String useYn;// 사용여부
    private Date regdate;// 등록일시
    private String reguser;// 등록자
    private Date moddate;// 수정일시
    private String moduser;// 수정자

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
