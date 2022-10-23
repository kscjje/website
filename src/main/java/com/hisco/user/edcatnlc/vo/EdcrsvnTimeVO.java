package com.hisco.user.edcatnlc.vo;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.hisco.cmm.util.Config;

/**
 * @Class Name : EdcrsvnTimeVO.java
 * @Description : 회원정보 처리를 위한 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020.10.06 전영석 최초생성
 * @author 전영석
 * @since 2020.10.06
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class EdcrsvnTimeVO implements Serializable {

    private String edcRsvnIdx;// 강연,행사,영화 예약고유번호
    private String edcRsvnTimeidx;// 매표예약관람회차등록순번
    private String edcTimeseq;// 강연,행사,영화 관람회차고유번호
    private String edcStime;// 관람회차시작시간
    private String edcEtime;// 관람회차종료시간
    private String edcTimeetc;// 비고사항
    private String regdate;// 등록일시
    private String reguser;// 등록자
    private String moddate;// 수정일시
    private String moduser;// 수정자

    public String getEdcRsvnIdx() {
        return edcRsvnIdx;
    }

    public void setEdcRsvnIdx(String edcRsvnIdx) {
        this.edcRsvnIdx = edcRsvnIdx;
    }

    public String getEdcRsvnTimeidx() {
        return edcRsvnTimeidx;
    }

    public void setEdcRsvnTimeidx(String edcRsvnTimeidx) {
        this.edcRsvnTimeidx = edcRsvnTimeidx;
    }

    public String getEdcTimeseq() {
        return edcTimeseq;
    }

    public void setEdcTimeseq(String edcTimeseq) {
        this.edcTimeseq = edcTimeseq;
    }

    public String getEdcStime() {
        return edcStime;
    }

    public void setEdcStime(String edcStime) {
        this.edcStime = edcStime;
    }

    public String getEdcEtime() {
        return edcEtime;
    }

    public void setEdcEtime(String edcEtime) {
        this.edcEtime = edcEtime;
    }

    public String getEdcTimeetc() {
        return edcTimeetc;
    }

    public void setEdcTimeetc(String edcTimeetc) {
        this.edcTimeetc = edcTimeetc;
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
