package com.hisco.admin.log.vo;

import java.sql.Timestamp;

import egovframework.com.cmm.ComDefaultVO;

/**
 * @Class Name : ErrorLogVO.java
 * @Description : 에러 로그관리를 위한 VO 클래스를 정의한다.
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2021. 3. 25. 진수진 최초생성
 * @author 진수진
 * @since 2021. 3. 25.
 * @version
 * @see
 */

public class ErrorLogVO extends ComDefaultVO {

    private static final long serialVersionUID = 540569951549295059L;

    private int logSeq;

    private String comcd;

    private String conectId;

    private String conectIp;

    private String connectUrl;

    private String errormsg;

    private String paramVal;

    private String refUrl;

    private Timestamp regdate;

    public int getLogSeq() {
        return logSeq;
    }

    public void setLogSeq(int logSeq) {
        this.logSeq = logSeq;
    }

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getConectId() {
        return conectId;
    }

    public void setConectId(String conectId) {
        this.conectId = conectId;
    }

    public String getConectIp() {
        return conectIp;
    }

    public void setConectIp(String conectIp) {
        this.conectIp = conectIp;
    }

    public String getConnectUrl() {
        return connectUrl;
    }

    public void setConnectUrl(String connectUrl) {
        this.connectUrl = connectUrl;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getParamVal() {
        return paramVal;
    }

    public void setParamVal(String paramVal) {
        this.paramVal = paramVal;
    }

    public String getRefUrl() {
        return refUrl;
    }

    public void setRefUrl(String refUrl) {
        this.refUrl = refUrl;
    }

    public Timestamp getRegdate() {
        return regdate;
    }

    public void setRegdate(Timestamp regdate) {
        this.regdate = regdate;
    }

}
