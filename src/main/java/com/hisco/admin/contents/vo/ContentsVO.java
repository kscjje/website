package com.hisco.admin.contents.vo;

import java.sql.Timestamp;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;

@Data
public class ContentsVO extends ComDefaultVO {

    private int contentsSeq;
    private int menuNo;
    private String cntsType;
    private String title;
    private String cnts;
    private String fileId;
    private String fileId2;

    private String useYn;

    private String menuNm;
    private String menuUrl;
    private String menuDc;

    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;

    private int totCnt;
}
