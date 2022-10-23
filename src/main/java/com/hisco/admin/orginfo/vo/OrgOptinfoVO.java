package com.hisco.admin.orginfo.vo;

import java.sql.Timestamp;

import com.hisco.cmm.util.Config;

import egovframework.com.cmm.service.EgovProperties;
import lombok.Data;

@Data
public class OrgOptinfoVO {
    private String comcd = Config.COM_CD;
    private int orgNo;
    private String receiptType;
    private String prtCompany;
    private String prtPresident;
    private String prtBusinessno;
    private String prtPhoneno;
    private String prtFaxno;
    private String prtOfficeaddr;
    private String prtBcontext;
    private String memberCardType;
    private String ageAppgbn;
    private String paymentUpdownUnit;
    private String paymentUpdown;
    private String refundUpdownUnit;
    private String refundUpdown;
    private String orgStampimgFfinnb;
    private int receiptPrncnt;
    private String mobilePaymentMethod;
    private String webPaymentMethod;
    private String paymentserverip;
    private String paymentserverport;
    private String paymentid;
    private String paymentpw;
    private String webpaymentserverip;
    private String webpaymentserverport;
    private String webpaymentid;
    private String webpaymentpwd;
    private String webpayapiKey;
    private String areaPayId;
    private String areaPayPwd;
    private String areaPayapiKey;

    private String edcPrnmAlarmHpno;
    private String orgPayAccno;
    private String orgPayAccname;
    private String orgPayBanknm;

    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;

    private String stampFileName;
    private String stampFilePath;
    private String stampFileOrigin;

    private String alarmHpno; // 디코딩

    public String getEdcPrnmAlarmHpno() {
        if (alarmHpno == null || alarmHpno.equals("")) {
            return edcPrnmAlarmHpno;
        } else {
            return alarmHpno;
        }
    }

    /* 디비 암호화 key */
    private String dbEncKey = EgovProperties.getProperty("Globals.DbEncKey");
}
