package com.hisco.admin.eduadm.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class EdcDaysVO {
    private String comcd;
    private int edcPrgmid;
    private int edcPrgmNo;
    private String edcDaygbn;
    private String edcDaygbnNm;
    private String dayChk;

    private String targetCtgcd;
    private String targetOrgNo;

    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;
}
