package com.hisco.user.evtrsvn.service;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

/**
 * @Class Name : EvtrsvnTimeVO.java
 * @Description : 회원정보 처리를 위한 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 09. 01 김희택 최초생성
 * @author 김희택
 * @since 2020. 09. 01
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class EvtrsvnTimeVO implements Serializable {

    private String evtRsvnIdx;// 강연/행사/영화예약고유번호
    private String evtRsvnTimeidx;// 매표예약관람회차등록순번
    private String evtTimeseq;// 강연/행사/영화관람회차고유번호
    private String evtStime;// 관람회차시작시간
    private String evtEtime;// 관람회차종료시간
    private String evtTimeetc;// 비고사항
    private String regdate;// 등록일시
    private String reguser;// 등록자
    private String moddate;// 수정일시
    private String moduser;// 수정자

    public String getEvtRsvnIdx() {
        return evtRsvnIdx;
    }

    public void setEvtRsvnIdx(String evtRsvnIdx) {
        this.evtRsvnIdx = evtRsvnIdx;
    }

    public String getEvtRsvnTimeidx() {
        return evtRsvnTimeidx;
    }

    public void setEvtRsvnTimeidx(String evtRsvnTimeidx) {
        this.evtRsvnTimeidx = evtRsvnTimeidx;
    }

    public String getEvtTimeseq() {
        return evtTimeseq;
    }

    public void setEvtTimeseq(String evtTimeseq) {
        this.evtTimeseq = evtTimeseq;
    }

    public String getEvtStime() {
        return evtStime;
    }

    public void setEvtStime(String evtStime) {
        this.evtStime = evtStime;
    }

    public String getEvtEtime() {
        return evtEtime;
    }

    public void setEvtEtime(String evtEtime) {
        this.evtEtime = evtEtime;
    }

    public String getEvtTimeetc() {
        return evtTimeetc;
    }

    public void setEvtTimeetc(String evtTimeetc) {
        this.evtTimeetc = evtTimeetc;
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

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
