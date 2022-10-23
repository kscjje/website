package com.hisco.admin.eduadm.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class EdcLimitVO {
    private String comcd;
    private int orgNo;
    private String edcRsvnlimitYn;
    private String edcRsvnlmitGbn;
    private String edcRsvnlimitPd;
    private int edcRsvnlimitCnt;

    private String CtgCd;
    private String CtgNm;

    private int edcPrgmNo;
    private String edcPrgmNm;

    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;
}
