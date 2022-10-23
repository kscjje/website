package com.hisco.user.evtedcrsvn.service;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

/**
 * @Class Name : CalendarVO.java
 * @Description : 회원정보 처리를 위한 VO 클래스
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
public class CalendarVO implements Serializable {

    private String comcd;
    private String gubun;
    private String calDate;
    private String dateType;
    private String cardpayCloseyn;
    private String calendarEtc;
    private String regdate;
    private String reguser;
    private String moddate;
    private String moduser;

    // binding
    private String yyyy;
    private String mm;
    private String dd;
    private String week;
    private int cnt;

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getCardpayCloseyn() {
        return cardpayCloseyn;
    }

    public void setCardpayCloseyn(String cardpayCloseyn) {
        this.cardpayCloseyn = cardpayCloseyn;
    }

    public String getCalendarEtc() {
        return calendarEtc;
    }

    public void setCalendarEtc(String calendarEtc) {
        this.calendarEtc = calendarEtc;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getReguser() {
        return reguser;
    }

    public void setReguser(String reguser) {
        this.reguser = reguser;
    }

    public String getModdate() {
        return moddate;
    }

    public void setModdate(String moddate) {
        this.moddate = moddate;
    }

    public String getModuser() {
        return moduser;
    }

    public void setModuser(String moduser) {
        this.moduser = moduser;
    }

    public String getYyyy() {
        return yyyy;
    }

    public void setYyyy(String yyyy) {
        this.yyyy = yyyy;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
