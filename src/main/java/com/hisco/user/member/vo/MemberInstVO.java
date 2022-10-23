package com.hisco.user.member.vo;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

/**
 * @Class Name : MemberVO.java
 * @Description : 관심분야정보 처리를 위한 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 8. 14 김희택
 * @author 김희택
 * @since 2020. 8. 14
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class MemberInstVO implements Serializable {

    /* 회원번호 */
    private String memNo;
    /* 관심분야 */
    private String intrstKind;
    /* 관심분야 명칭 */
    private String intrstKindNm;
    /* 등록일시 */
    private Date regdate;
    /* 등록자 */
    private String reguser;
    /* 수정일시 */
    private Date moddate;
    /* 수정자 */
    private String moduser;

    public String getMemNo() {
        return memNo;
    }

    public String getIntrstKind() {
        return intrstKind;
    }

    public Date getRegdate() {
        return regdate;
    }

    public String getReguser() {
        return reguser;
    }

    public Date getModdate() {
        return moddate;
    }

    public String getModuser() {
        return moduser;
    }

    public void setMemNo(String memNo) {
        this.memNo = memNo;
    }

    public void setIntrstKind(String intrstKind) {
        this.intrstKind = intrstKind;
    }

    public String getIntrstKindNm() {
        return intrstKindNm;
    }

    public void setIntrstKindNm(String intrstKindNm) {
        this.intrstKindNm = intrstKindNm;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public void setReguser(String reguser) {
        this.reguser = reguser;
    }

    public void setModdate(Date moddate) {
        this.moddate = moddate;
    }

    public void setModuser(String moduser) {
        this.moduser = moduser;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
