package com.hisco.admin.orginfo.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrgDcVO {
    private String comcd;
    private int orgNo;
    private String dcReasonCd;
    private String dcNm;
    private int dcRate;
    private String dcLimityn;
    private int dcLimitCnt;
    private String dcLimitPolicy;
    private String dcYn;
    private String dcType;

    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;

}
