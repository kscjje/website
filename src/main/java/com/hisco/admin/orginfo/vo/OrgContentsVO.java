package com.hisco.admin.orginfo.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrgContentsVO {
    private String comcd;
    private int orgNo;
    private String orgPlaceurl;
    private String orgFileid;
    private String orgGuide;
    private String orgClassmngGuide;
    private String orgSubwayGuide;
    private String orgBusGuide;
    private String orgRetdcGuide;

    private String orgFileName;
    private String orgFileOrigin;
    private String orgFilePath;

    private String pplFileName;
    private String pplFileOrigin;
    private String pplFilePath;

    private String guideFileName;
    private String guideFileOrigin;
    private String guideFilePath;

    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;

}
