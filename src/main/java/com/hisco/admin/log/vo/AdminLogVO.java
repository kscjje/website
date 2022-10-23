package com.hisco.admin.log.vo;

import java.sql.Date;
import java.sql.Timestamp;

import org.apache.commons.lang3.builder.ToStringBuilder;

import egovframework.com.cmm.ComDefaultVO;

/**
 * @Class Name : AdminLogVO.java
 * @Description : 관리자 action 로그관리를 위한 VO 클래스를 정의한다.
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 7. 16. 진수진 최초생성
 * @author 진수진
 * @since 2020. 7. 16.
 * @version
 * @see
 */

public class AdminLogVO extends ComDefaultVO {

    private static final long serialVersionUID = 540569951549295059L;

    /**
     * idkey
     */
    private int logSeq = 0;

    /**
     * 접속시스템구분
     */
    private String connctAppgbn = "2002";

    /**
     * 요청아이디
     */
    private String conectId = "";

    /**
     * 요청아이피
     */
    private String conectIp = "";

    /**
     * 메뉴 코드
     */
    private long menuid = 0;

    /**
     * 메서드명
     */
    private String methodGubun = "";

    /**
     * 오류발생여부
     */
    private String erroryn = "";

    /**
     * 에러코드
     */
    private String errorcode = "";

    /**
     * 에러 메시지
     */
    private String errormsg = "";

    /**
     * 발생일자
     */
    private Timestamp regdate;

    /**
     * 사용자 이름
     */
    private String userName = "";

    /**
     * 메뉴명
     */
    private String menuNm = "";

    /**
     * 접속 URL
     */
    private String targetUrl = "";

   private String inqryMemberinfo;

   private String authorCode;


    public int getLogSeq() {
        return logSeq;
    }

    public void setLogSeq(int logSeq) {
        this.logSeq = logSeq;
    }

    public String getConnctAppgbn() {
        return connctAppgbn;
    }

    public void setConnctAppgbn(String connctAppgbn) {
        this.connctAppgbn = connctAppgbn;
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

    public long getMenuid() {
        return menuid;
    }

    public void setMenuid(long menuid) {
        this.menuid = menuid;
    }

    public String getMethodGubun() {
        return methodGubun;
    }

    public void setMethodGubun(String methodGubun) {
        this.methodGubun = methodGubun;
    }

    public String getErroryn() {
        return erroryn;
    }

    public void setErroryn(String erroryn) {
        this.erroryn = erroryn;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public Timestamp getRegdate() {
        return regdate;
    }

    public void setRegdate(Timestamp regdate) {
        this.regdate = regdate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }



    public String getInqryMemberinfo() {
		return inqryMemberinfo;
	}

	public void setInqryMemberinfo(String inqryMemberinfo) {
		this.inqryMemberinfo = inqryMemberinfo;
	}


	public String getAuthorCode() {
		return authorCode;
	}

	public void setAuthorCode(String authorCode) {
		this.authorCode = authorCode;
	}

	/**
     *
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
