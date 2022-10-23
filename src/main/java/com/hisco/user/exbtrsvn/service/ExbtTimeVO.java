package com.hisco.user.exbtrsvn.service;

/**
 * 관람 상세 회차 VO
 * 
 * @author 진수진
 * @since 2020.08.31
 * @version 1.0, 2020.08.31
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.08.31 최초작성
 */
public class ExbtTimeVO {
    /* 기관코드 */
    private String comcd;

    /* 기준 코드 */
    private String exbtSeq;

    /* 일자 */
    private String ymd;

    /* 요일 */
    private String dayWeek;

    /* 회차 기준 코드 */
    private String timeStdSeq;

    /* 회차명 */
    private String timeName;

    /* 제목 */
    private String timeTitle;

    /* 관람회차시작전매표가능시간(분) */
    private String exbtBeftime;

    /* 관람회차시작후매표가능시간(분) */
    private String exbtAftime;

    /* 관람회차고유번호 */
    private String exbtTimeseq;

    /* 시작 시간 */
    private String sTime;

    /* 종료 시간 */
    private String eTime;

    /* 개인예약가능여부 */
    private String personyn;

    /* 단체예약가능여부 */
    private String groupyn;

    /* 정원 */
    private long totCapa;

    /* 예약 인원 */
    private long reserveCnt;

    /* 예약가능여부 */
    private String reserveAbleYn;

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getExbtSeq() {
        return exbtSeq;
    }

    public void setExbtSeq(String exbtSeq) {
        this.exbtSeq = exbtSeq;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    public String getTimeStdSeq() {
        return timeStdSeq;
    }

    public void setTimeStdSeq(String timeStdSeq) {
        this.timeStdSeq = timeStdSeq;
    }

    public String getTimeName() {
        return timeName;
    }

    public void setTimeStdName(String timeName) {
        this.timeName = timeName;
    }

    public String getExbtBeftime() {
        return exbtBeftime;
    }

    public void setExbtBeftime(String exbtBeftime) {
        this.exbtBeftime = exbtBeftime;
    }

    public String getExbtAftime() {
        return exbtAftime;
    }

    public void setExbtAftime(String exbtAftime) {
        this.exbtAftime = exbtAftime;
    }

    public String getExbtTimeseq() {
        return exbtTimeseq;
    }

    public void setExbtTimeseq(String exbtTimeseq) {
        this.exbtTimeseq = exbtTimeseq;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public long getTotCapa() {
        return totCapa;
    }

    public void setTotCapa(long totCapa) {
        this.totCapa = totCapa;
    }

    public String getTimeTitle() {
        return timeTitle;
    }

    public void setTimeTitle(String timeTitle) {
        this.timeTitle = timeTitle;
    }

    public void setTimeName(String timeName) {
        this.timeName = timeName;
    }

    public String getPersonyn() {
        return personyn;
    }

    public void setPersonyn(String personyn) {
        this.personyn = personyn;
    }

    public String getGroupyn() {
        return groupyn;
    }

    public void setGroupyn(String groupyn) {
        this.groupyn = groupyn;
    }

    public long getReserveCnt() {
        return reserveCnt;
    }

    public void setReserveCnt(long reserveCnt) {
        this.reserveCnt = reserveCnt;
    }

    public String getReserveAbleYn() {
        return reserveAbleYn;
    }

    public void setReserveAbleYn(String reserveAbleYn) {
        this.reserveAbleYn = reserveAbleYn;
    }

}
