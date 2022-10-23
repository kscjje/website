package com.hisco.user.exbtrsvn.service;

/**
 * 관람 일정 VO
 * 
 * @author 진수진
 * @since 2020.08.31
 * @version 1.0, 2020.08.31
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.08.31 최초작성
 */
public class CalendarVO {
    /* 년월일 */
    private String ymd;

    /* 예약가능 , 휴관 여부 체크 */
    private String scheType;

    private String chkData;

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getScheType() {
        return scheType;
    }

    public void setScheType(String scheType) {
        this.scheType = scheType;
    }

    public String getChkData() {
        return chkData;
    }

    public void setChkData(String chkData) {
        this.chkData = chkData;
    }

}
