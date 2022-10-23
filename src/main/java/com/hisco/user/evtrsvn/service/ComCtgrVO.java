package com.hisco.user.evtrsvn.service;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

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
@SuppressWarnings("serial")
public class ComCtgrVO implements Serializable {

    private String comCtgcd;// 분류코드
    private String comCtgnm;// 분류명
    private String comCtgdesc;// 분류설명
    private String comCtglvl;// 분류레벨
    private String comPrnctgcd;// 상위분류
    private String comTopctgcd;// 최상위분류
    private String comToppartgbn;// 최상위분류대상사업장구분
    private String comPartcd;// 최상위분류대상사업장
    private String comSortOrder;// 정렬번호
    private String comUseYn;// 사용여부
    private Date regdate;// 등록일시
    private String reguser;// 등록자
    private Date moddate;// 수정일시
    private String moduser;// 수정자

    public String getComCtgcd() {
        return comCtgcd;
    }

    public void setComCtgcd(String comCtgcd) {
        this.comCtgcd = comCtgcd;
    }

    public String getComCtgnm() {
        return comCtgnm;
    }

    public void setComCtgnm(String comCtgnm) {
        this.comCtgnm = comCtgnm;
    }

    public String getComCtgdesc() {
        return comCtgdesc;
    }

    public void setComCtgdesc(String comCtgdesc) {
        this.comCtgdesc = comCtgdesc;
    }

    public String getComCtglvl() {
        return comCtglvl;
    }

    public void setComCtglvl(String comCtglvl) {
        this.comCtglvl = comCtglvl;
    }

    public String getComPrnctgcd() {
        return comPrnctgcd;
    }

    public void setComPrnctgcd(String comPrnctgcd) {
        this.comPrnctgcd = comPrnctgcd;
    }

    public String getComTopctgcd() {
        return comTopctgcd;
    }

    public void setComTopctgcd(String comTopctgcd) {
        this.comTopctgcd = comTopctgcd;
    }

    public String getComToppartgbn() {
        return comToppartgbn;
    }

    public void setComToppartgbn(String comToppartgbn) {
        this.comToppartgbn = comToppartgbn;
    }

    public String getComPartcd() {
        return comPartcd;
    }

    public void setComPartcd(String comPartcd) {
        this.comPartcd = comPartcd;
    }


    public String getComUseYn() {
        return comUseYn;
    }

    public void setComUseYn(String comUseYn) {
        this.comUseYn = comUseYn;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public String getReguser() {
        return reguser;
    }

    public void setReguser(String reguser) {
        this.reguser = reguser;
    }

    public Date getModdate() {
        return moddate;
    }

    public void setModdate(Date moddate) {
        this.moddate = moddate;
    }

    public String getModuser() {
        return moduser;
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
