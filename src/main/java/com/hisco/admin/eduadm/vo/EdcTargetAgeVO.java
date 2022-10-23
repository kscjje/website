package com.hisco.admin.eduadm.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class EdcTargetAgeVO {
    private String comcd;
    private int edcPrgmid;
    private int edcPrgmNo;
    private int edcAgeTargetSeq;
    private int edcAgeItemcd;
    private int edcTargetCnt;
    private String edcTargetAgeNm;
    private int edcTargetSage;
    private int edcTargetEage;

    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;
}
