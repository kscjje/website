package com.hisco.user.member.vo;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

/**
 * @Class Name : MemberSnsVO.java
 * @Description : SNS정보 처리를 위한 VO 클래스
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
public class MemberSnsVO implements Serializable {

    /* 회원번호 */
    private String memNo;
    /* 계정연결등록순번 */
    private int snsRegistSeq;
    /* 계정연결가입sns종류 */
    private String snsRegistkind;
    /* 계정연결가입sns종류 이름 */
    private String snsRegistkindNm;
    /* 계정연결snsid */
    private String snsid;
    /* 계정연결상태 */
    private String snsCnncStat;
    /* 계정연결log */
    private String snsCnnclog;
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

    public int getSnsRegistSeq() {
        return snsRegistSeq;
    }

    public String getSnsRegistkind() {
        return snsRegistkind;
    }

    public String getSnsid() {
        return snsid;
    }

    public String getSnsCnncStat() {
        return snsCnncStat;
    }

    public String getSnsCnnclog() {
        return snsCnnclog;
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

    public void setSnsRegistSeq(int snsRegistSeq) {
        this.snsRegistSeq = snsRegistSeq;
    }

    public void setSnsRegistkind(String snsRegistkind) {
        this.snsRegistkind = snsRegistkind;
    }

    public String getSnsRegistkindNm() {
        return snsRegistkindNm;
    }

    public void setSnsRegistkindNm(String snsRegistkindNm) {
        this.snsRegistkindNm = snsRegistkindNm;
    }

    public void setSnsid(String snsid) {
        this.snsid = snsid;
    }

    public void setSnsCnncStat(String snsCnncStat) {
        this.snsCnncStat = snsCnncStat;
    }

    public void setSnsCnnclog(String snsCnnclog) {
        this.snsCnnclog = snsCnnclog;
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
