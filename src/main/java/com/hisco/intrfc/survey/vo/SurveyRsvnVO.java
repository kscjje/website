package com.hisco.intrfc.survey.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @Class Name : SurveyRsvnVO.java
 * @Description : 설문조사 예약정보 VO
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
public class SurveyRsvnVO implements Serializable {

    private String comcd;
    private String rsvnId;// 예약번호
    private String rsvnCustNm;// 고객명
    private String rsvnMembgbn;// 고객명
    private String rsvnTelno;// 고객명
    private String rsvnPartcd;// 고객명
    private String programId;// 고객명
    private String webId;// webid
    private String telNo;// 전화번호
    private String memNo;// 고객번호
    private String regdate;// 등록일자

    // param
    private String dbEncKey;

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getRsvnId() {
        return rsvnId;
    }

    public void setRsvnId(String rsvnId) {
        this.rsvnId = rsvnId;
    }

    public String getRsvnCustNm() {
        return rsvnCustNm;
    }

    public void setRsvnCustNm(String rsvnCustNm) {
        this.rsvnCustNm = rsvnCustNm;
    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getMemNo() {
        return memNo;
    }

    public void setMemNo(String memNo) {
        this.memNo = memNo;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getRsvnMembgbn() {
        return rsvnMembgbn;
    }

    public void setRsvnMembgbn(String rsvnMembgbn) {
        this.rsvnMembgbn = rsvnMembgbn;
    }

    public String getRsvnTelno() {
        return rsvnTelno;
    }

    public void setRsvnTelno(String rsvnTelno) {
        this.rsvnTelno = rsvnTelno;
    }

    public String getRsvnPartcd() {
        return rsvnPartcd;
    }

    public void setRsvnPartcd(String rsvnPartcd) {
        this.rsvnPartcd = rsvnPartcd;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getDbEncKey() {
        return dbEncKey;
    }

    public void setDbEncKey(String dbEncKey) {
        this.dbEncKey = dbEncKey;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
