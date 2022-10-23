package com.hisco.admin.orginfo.vo;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class ComInfoVO {

	private String comcd;
	private String comnm;
	private String tel1;
	private String tel2;
	private String fax;
	private String addr;
	private String email;
	private String bossNm;
	private String bizNo;
	private String charger;
	private String chargertel;
	private int menuOrder;
	private Timestamp regdate;
	private String reguser;
	private Timestamp moddate;
	private String moduser;
	private String sortOrder;

	// OPT
	private String smsUseyn;
	private String smsProvider;
	private String lmsServiceyn;
	private String mmsServiceyn;
	private String smsId;
	private String smsPw;
	private String mailServer;
	private String pcompany;
	private String onlinePaymentcorp;
	private String mbPwdcycle;
	private String mbPwdcycleValue;

}
