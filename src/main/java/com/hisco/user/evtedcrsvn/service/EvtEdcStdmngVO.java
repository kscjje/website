package com.hisco.user.evtedcrsvn.service;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

/**
 * @Class Name : EvtStdmngVO.java
 * @Description : 강연/행사/영화 카테고리를 위한 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 8. 28 김희택
 * @author 김희택
 * @since 2020. 8. 28
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class EvtEdcStdmngVO implements Serializable {

    private int evtTimestdSeq;// 기준관리고유번호
    private String comcd;// 기관고유번호
    private String evtNo;// 강연/행사/영화고유번호
    private String evtPartcd;// 강연/행사/영화사업장고유번호
    private String evtTimestddate;// 기준등록일자
    private String evtTimestdname;// 기준구분명
    private String evtBeftime;// 관람회차시작전매표가능시간(분)
    private String evtAftime;// 관람회차시작후매표가능시간(분)
    private String evtUsedays;// 관람시간기준적용요일
    private String evtPdbetenyn;// 관람시간적용기간사용유무
    private String evtTimeStdate;// 관람시간적용기간시작일
    private String evtTimeEnddate;// 관람시간적용기간종료일
    private String useYn;// 기준사용여부
    private String dayWeek; // 선택요일
    private Date regdate;// 등록일시
    private String reguser;// 등록자
    private Date moddate;// 수정일시
    private String moduser;// 수정자

    // parameter
    private String evtTime;
    private String evtTimeseq;
    private String sTime;
    private String eTime;
    private String evtRsvnIdx;
    private String evtPersnGbn; // 개인 단체 구분 10/20

    public int getEvtTimestdSeq() {
        return evtTimestdSeq;
    }

    public void setEvtTimestdSeq(int evtTimestdSeq) {
        this.evtTimestdSeq = evtTimestdSeq;
    }

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getEvtNo() {
        return evtNo;
    }

    public void setEvtNo(String evtNo) {
        this.evtNo = evtNo;
    }

    public String getEvtPartcd() {
        return evtPartcd;
    }

    public void setEvtPartcd(String evtPartcd) {
        this.evtPartcd = evtPartcd;
    }

    public String getEvtTimestddate() {
        return evtTimestddate;
    }

    public void setEvtTimestddate(String evtTimestddate) {
        this.evtTimestddate = evtTimestddate;
    }

    public String getEvtTimestdname() {
        return evtTimestdname;
    }

    public void setEvtTimestdname(String evtTimestdname) {
        this.evtTimestdname = evtTimestdname;
    }

    public String getEvtBeftime() {
        return evtBeftime;
    }

    public void setEvtBeftime(String evtBeftime) {
        this.evtBeftime = evtBeftime;
    }

    public String getEvtAftime() {
        return evtAftime;
    }

    public void setEvtAftime(String evtAftime) {
        this.evtAftime = evtAftime;
    }

    public String getEvtUsedays() {
        return evtUsedays;
    }

    public void setEvtUsedays(String evtUsedays) {
        this.evtUsedays = evtUsedays;
    }

    public String getEvtPdbetenyn() {
        return evtPdbetenyn;
    }

    public void setEvtPdbetenyn(String evtPdbetenyn) {
        this.evtPdbetenyn = evtPdbetenyn;
    }

    public String getEvtTimeStdate() {
        return evtTimeStdate;
    }

    public void setEvtTimeStdate(String evtTimeStdate) {
        this.evtTimeStdate = evtTimeStdate;
    }

    public String getEvtTimeEnddate() {
        return evtTimeEnddate;
    }

    public void setEvtTimeEnddate(String evtTimeEnddate) {
        this.evtTimeEnddate = evtTimeEnddate;
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

    public String getEvtTime() {
        return evtTime;
    }

    public void setEvtTime(String evtTime) {
        this.evtTime = evtTime;
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

    public String getEvtTimeseq() {
        return evtTimeseq;
    }

    public void setEvtTimeseq(String evtTimeseq) {
        this.evtTimeseq = evtTimeseq;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    public String getEvtRsvnIdx() {
        return evtRsvnIdx;
    }

    public void setEvtRsvnIdx(String evtRsvnIdx) {
        this.evtRsvnIdx = evtRsvnIdx;
    }

    public String getEvtPersnGbn() {
        return evtPersnGbn;
    }

    public void setEvtPersnGbn(String evtPersnGbn) {
        this.evtPersnGbn = evtPersnGbn;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
