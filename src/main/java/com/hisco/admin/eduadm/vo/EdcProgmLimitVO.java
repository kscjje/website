package com.hisco.admin.eduadm.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class EdcProgmLimitVO {
    private String comcd;
    private int edcPrgmid;
    private int edcPrgmNo;
    private String CtgCd;

    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;
}
