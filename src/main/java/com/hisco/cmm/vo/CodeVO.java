package com.hisco.cmm.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.hisco.cmm.util.Config;

import egovframework.com.cmm.ComDefaultVO;

/**
 * @Class Name : CodeVO.java
 * @Description : 공통코드 처리를 위한 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 8. 21. 진수진
 * @author 진수진
 * @since 2020. 8. 21.
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class CodeVO extends ComDefaultVO implements Serializable {

    /**
     * 사업자코드
     */
    private String comcd = Config.COM_CD;

    /**
     * 기관번호
     */
    private int orgNo;

    /**
     * 그룹코드
     */
    private String grpCd = "";

    /**
     * 그룹코드명
     */
    private String grpNm = "";

    /**
     * 항목코드
     */
    @Expose
    private String cd = "";

    /**
     * 항목명
     */
    @Expose
    private String cdNm = "";

    /**
     * 정렬순서
     */
    @Expose
    private long sortOrder = 0;

    /**
     * 항목1
     */
    @Expose
    private String item1 = "";
    /**
     * 항목2
     */
    @Expose
    private String item2 = "";
    /**
     * 항목3
     */
    @Expose
    private String item3 = "";
    /**
     * 항목4
     */
    private int item4;
    /**
     * 항목5
     */
    private int item5;

    /**
     * 항목6
     */
    @Expose
    private String item6 = "";
    /**
     * 항목7
     */
    @Expose
    private String item7 = "";
    /**
     * 항목8
     */
    @Expose
    private String item8 = "";
    /**
     * 항목9
     */
    private int item9;
    /**
     * 항목10
     */
    private int item10;
    /**
     * 비고
     */
    @Expose
    private String remark = "";

    /** 등록자 */
    private String reguser;

    /** 등록일 */
    private Timestamp regdate;

    /** 수정자 */
    private String moduser;

    /** 수정일 */
    private Timestamp moddate;

    /** 사용여부 */
    @Expose
    private String useYn;

    /** 수정 가능여부 */
    private String updYn;

    /** 삭제 가능여부 */
    private String delYn;

    /** 이용기관별 코드 구분 */
    private String orgGrpcdyn;

    private String orgGrpCdYn;
    
    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public int getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(int orgNo) {
        this.orgNo = orgNo;
    }

    public String getGrpCd() {
        return grpCd;
    }

    public void setGrpCd(String grpCd) {
        this.grpCd = grpCd;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getCdNm() {
        return cdNm;
    }

    public void setCdNm(String cdNm) {
        this.cdNm = cdNm;
    }

    public long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public int getItem4() {
        return item4;
    }

    public void setItem4(int item4) {
        this.item4 = item4;
    }

    public int getItem5() {
        return item5;
    }

    public void setItem5(int item5) {
        this.item5 = item5;
    }

    public String getItem6() {
        return item6;
    }

    public void setItem6(String item6) {
        this.item6 = item6;
    }

    public String getItem7() {
        return item7;
    }

    public void setItem7(String item7) {
        this.item7 = item7;
    }

    public String getItem8() {
        return item8;
    }

    public void setItem8(String item8) {
        this.item8 = item8;
    }

    public int getItem9() {
        return item9;
    }

    public void setItem9(int item9) {
        this.item9 = item9;
    }

    public int getItem10() {
        return item10;
    }

    public void setItem10(int item10) {
        this.item10 = item10;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReguser() {
        return reguser;
    }

    public void setReguser(String reguser) {
        this.reguser = reguser;
    }

    public Timestamp getRegdate() {
        return regdate;
    }

    public void setRegdate(Timestamp regdate) {
        this.regdate = regdate;
    }

    public String getModuser() {
        return moduser;
    }

    public void setModuser(String moduser) {
        this.moduser = moduser;
    }

    public Timestamp getModdate() {
        return moddate;
    }

    public void setModdate(Timestamp moddate) {
        this.moddate = moddate;
    }

    public String getGrpNm() {
        return grpNm;
    }

    public void setGrpNm(String grpNm) {
        this.grpNm = grpNm;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getUpdYn() {
        return updYn;
    }

    public void setUpdYn(String updYn) {
        this.updYn = updYn;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getOrgGrpcdyn() {
        return orgGrpcdyn;
    }

    public void setOrgGrpcdyn(String orgGrpcdyn) {
        this.orgGrpcdyn = orgGrpcdyn;
    }

    public String getOrgGrpCdYn() {
		return orgGrpCdYn;
	}

	public void setOrgGrpCdYn(String orgGrpCdYn) {
		this.orgGrpCdYn = orgGrpCdYn;
	}

	/**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
