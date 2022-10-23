package egovframework.com.sym.log.wlg.service;

import java.io.Serializable;

/**
 * @Class Name : ErrorWebLog.java
 * @Description : 오류 웹 로그 관리를 위한 VO 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ---------- ------- -------------------
 *               2020.10.08. 전영석 최초생성
 * @author 공통 서비스 전영석
 * @since 2010. 10. 08.
 * @version
 * @see
 */
public class ErrorWebLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String comcd;
    private String errorcode;
    private String methodGubun;
    private String connctAppgbn;
    private String conectIp;
    private String menuid;
    private String connectUrl;
    private String erroryn;
    private String conectId;
    private String errormsg;
    private String pconnectUrl;

    public String getPconnectUrl() {
        return pconnectUrl;
    }

    public void setPconnectUrl(String pconnectUrl) {
        this.pconnectUrl = pconnectUrl;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getComcd() {
        return comcd;
    }

    public void setComcd(String comcd) {
        this.comcd = comcd;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMethodGubun() {
        return methodGubun;
    }

    public void setMethodGubun(String methodGubun) {
        this.methodGubun = methodGubun;
    }

    public String getConnctAppgbn() {
        return connctAppgbn;
    }

    public void setConnctAppgbn(String connctAppgbn) {
        this.connctAppgbn = connctAppgbn;
    }

    public String getConectIp() {
        return conectIp;
    }

    public void setConectIp(String conectIp) {
        this.conectIp = conectIp;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getConnectUrl() {
        return connectUrl;
    }

    public void setConnectUrl(String connectUrl) {
        this.connectUrl = connectUrl;
    }

    public String getErroryn() {
        return erroryn;
    }

    public void setErroryn(String erroryn) {
        this.erroryn = erroryn;
    }

    public String getConectId() {
        return conectId;
    }

    public void setConectId(String conectId) {
        this.conectId = conectId;
    }

}
