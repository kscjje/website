package com.hisco.user.main.service;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

import egovframework.com.cmm.ComDefaultVO;

/**
 * @Class Name : MainSearchVO.java
 * @Description : 메인화면 검색 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 9. 23 김희택
 * @author 김희택
 * @since 2020. 9. 23
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class MainSearchVO extends ComDefaultVO implements Serializable {

    private String idx;
    private String title;
    private String cn;
    private String ctgr;
    private String dateinfo;
    private String timeinfo;
    private String feetype;
    private String ctgId;
    private String prnno;
    private String partcd;
    private Date regdate;

    // param
    private String rn;
    private String searchType;
    private String orderType;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCtgr() {
        return ctgr;
    }

    public void setCtgr(String ctgr) {
        this.ctgr = ctgr;
    }

    public String getPartcd() {
        return partcd;
    }

    public void setPartcd(String partcd) {
        this.partcd = partcd;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public String getRn() {
        return rn;
    }

    public void setRn(String rn) {
        this.rn = rn;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getCtgId() {
        return ctgId;
    }

    public void setCtgId(String ctgId) {
        this.ctgId = ctgId;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getDateinfo() {
        return dateinfo;
    }

    public void setDateinfo(String dateinfo) {
        this.dateinfo = dateinfo;
    }

    public String getTimeinfo() {
        return timeinfo;
    }

    public void setTimeinfo(String timeinfo) {
        this.timeinfo = timeinfo;
    }

    public String getFeetype() {
        return feetype;
    }

    public void setFeetype(String feetype) {
        this.feetype = feetype;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPrnno() {
        return prnno;
    }

    public void setPrnno(String prnno) {
        this.prnno = prnno;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
