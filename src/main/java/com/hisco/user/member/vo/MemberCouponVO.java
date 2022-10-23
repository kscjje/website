package com.hisco.user.member.vo;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

import egovframework.com.cmm.ComDefaultVO;

/**
 * @Class Name : MemberCouponVO.java
 * @Description : 쿠폰정보 처리를 위한 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 9. 09 김희택
 * @author 김희택
 * @since 2020. 9. 09
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class MemberCouponVO extends ComDefaultVO implements Serializable {

    private String comcd;// 기관고유번호
    private String cpnId;// 쿠폰아이디
    private String cpnType;// 쿠폰종류
    private String cpnName;// 쿠폰명
    private String cpnStdt;// 쿠폰유효시작일자
    private String cpnSttime;// 쿠폰유효시작시간
    private String cpnEddt;// 쿠폰유효종료일자
    private String cpnEdtime;// 쿠폰유효종료시간
    private String cpnDcrate;// 쿠폰할인적용요율
    private String cpnExpsrSumry;// 쿠폰노출한줄요약
    private String cpnTargetGbn;// 쿠폰대상적용기준
    private String cpnGuide;// 쿠폰사용안내
    private String useYn;// 사용여부
    private Date regdate;// 등록일시
    private String reguser;// 등록자
    private Date moddate;// 수정일시
    private String moduser;// 수정자

    // parameter
    private String cpnUid;// 쿠폰번호
    private String cpnPymntdate;// 기관고유번호
    private String cpnPymntmemno;// 쿠폰아이디
    private String cpnUseyn;// 쿠폰사용완료여부
    private String cpnPymntMesg;// 쿠폰지급메시지
    private String openyn;// 공개여부
    private String memNo;// 회원번호
    private String rn;// rownum(in)
    private String rownum;
    private String searchKey; // 검색
    private String endtime;

    // stdmng
    private String cpnTargetPartcd;
    private String cpnTargetCtgcd;

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getCpnId() {
        return cpnId;
    }

    public void setCpnId(String cpnId) {
        this.cpnId = cpnId;
    }

    public String getCpnType() {
        return cpnType;
    }

    public void setCpnType(String cpnType) {
        this.cpnType = cpnType;
    }

    public String getCpnName() {
        return cpnName;
    }

    public void setCpnName(String cpnName) {
        this.cpnName = cpnName;
    }

    public String getCpnStdt() {
        return cpnStdt;
    }

    public void setCpnStdt(String cpnStdt) {
        this.cpnStdt = cpnStdt;
    }

    public String getCpnSttime() {
        return cpnSttime;
    }

    public void setCpnSttime(String cpnSttime) {
        this.cpnSttime = cpnSttime;
    }

    public String getCpnEddt() {
        return cpnEddt;
    }

    public void setCpnEddt(String cpnEddt) {
        this.cpnEddt = cpnEddt;
    }

    public String getCpnEdtime() {
        return cpnEdtime;
    }

    public void setCpnEdtime(String cpnEdtime) {
        this.cpnEdtime = cpnEdtime;
    }

    public String getCpnDcrate() {
        return cpnDcrate;
    }

    public void setCpnDcrate(String cpnDcrate) {
        this.cpnDcrate = cpnDcrate;
    }

    public String getCpnExpsrSumry() {
        return cpnExpsrSumry;
    }

    public void setCpnExpsrSumry(String cpnExpsrSumry) {
        this.cpnExpsrSumry = cpnExpsrSumry;
    }

    public String getCpnTargetGbn() {
        return cpnTargetGbn;
    }

    public void setCpnTargetGbn(String cpnTargetGbn) {
        this.cpnTargetGbn = cpnTargetGbn;
    }

    public String getCpnGuide() {
        return cpnGuide;
    }

    public void setCpnGuide(String cpnGuide) {
        this.cpnGuide = cpnGuide;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
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

    public String getCpnUid() {
        return cpnUid;
    }

    public void setCpnUid(String cpnUid) {
        this.cpnUid = cpnUid;
    }

    public String getCpnPymntdate() {
        return cpnPymntdate;
    }

    public void setCpnPymntdate(String cpnPymntdate) {
        this.cpnPymntdate = cpnPymntdate;
    }

    public String getCpnPymntmemno() {
        return cpnPymntmemno;
    }

    public void setCpnPymntmemno(String cpnPymntmemno) {
        this.cpnPymntmemno = cpnPymntmemno;
    }

    public String getCpnUseyn() {
        return cpnUseyn;
    }

    public void setCpnUseyn(String cpnUseyn) {
        this.cpnUseyn = cpnUseyn;
    }

    public String getCpnPymntMesg() {
        return cpnPymntMesg;
    }

    public void setCpnPymntMesg(String cpnPymntMesg) {
        this.cpnPymntMesg = cpnPymntMesg;
    }

    public String getOpenyn() {
        return openyn;
    }

    public void setOpenyn(String openyn) {
        this.openyn = openyn;
    }

    public String getMemNo() {
        return memNo;
    }

    public void setMemNo(String memNo) {
        this.memNo = memNo;
    }

    public String getCpnTargetPartcd() {
        return cpnTargetPartcd;
    }

    public void setCpnTargetPartcd(String cpnTargetPartcd) {
        this.cpnTargetPartcd = cpnTargetPartcd;
    }

    public String getCpnTargetCtgcd() {
        return cpnTargetCtgcd;
    }

    public void setCpnTargetCtgcd(String cpnTargetCtgcd) {
        this.cpnTargetCtgcd = cpnTargetCtgcd;
    }

    public String getRn() {
        return rn;
    }

    public void setRn(String rn) {
        this.rn = rn;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getRownum() {
        return rownum;
    }

    public void setRownum(String rownum) {
        this.rownum = rownum;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
