package com.hisco.user.member.vo;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

/**
 * @Class Name : MemberCarVO.java
 * @Description : 주차정보 처리를 위한 VO 클래스
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
public class MemberCarVO implements Serializable {

    private String comcd /* 기관고유번호 */;
    private String memNo /* 회원번호 */;
    private int carSeq /* 등록순번 */;
    private String carNo /* 차량번호 */;
    private String etc /* 비고 */;
    private Date regdate /* 등록일시 */;
    private String reguser /* 등록자 */;
    private Date moddate /* 수정일시 */;
    private String moduser /* 수정자 */;

    public String getComcd() {
        return comcd;
    }

    public String getMemNo() {
        return memNo;
    }

    public int getCarSeq() {
        return carSeq;
    }

    public String getCarNo() {
        return carNo;
    }

    public String getEtc() {
        return etc;
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

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public void setMemNo(String memNo) {
        this.memNo = memNo;
    }

    public void setCarSeq(int carSeq) {
        this.carSeq = carSeq;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public void setEtc(String etc) {
        this.etc = etc;
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
