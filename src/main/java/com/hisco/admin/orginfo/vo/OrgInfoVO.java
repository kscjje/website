package com.hisco.admin.orginfo.vo;

import java.sql.Timestamp;
import java.util.List;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrgInfoVO extends ComDefaultVO {
    private static final long serialVersionUID = 1L;

    private int orgNo;
    private String parentComcd;
    private int parentOrgNo;
    private String parentOrgNm;
    private int areaCd;
    private String orgKind;
    private String orgLtype;
    private String orgMtype;
    private String orgNm;
    private String orgTel;
    private String fax;
    private String addr;
    private String email;
    private String bossNm;
    private String bizNo;
    private String uptae;
    private String jongmok;
    private String orgUrl;
    private String charger;
    private String chargertel;
    private int sortOrder;
    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;

    private int totCnt;

    private String level;

    private String orgLtypeNm;
    private String orgMtypeNm;

    private String orgFileName;
    private String orgFileOrigin;
    private String orgFilePath;

    private String areaNm;
    private String orgPlaceurl;
    private String orgCntsactiveyn;

    private OrgContentsVO orgContents;
    private OrgOptinfoVO orgOptinfo;
    private List<OrgDcVO> orgDcList;

    private String edcRsvnlimitYn;

    public String getOrgKindNm() {
        if (this.orgKind != null && this.orgKind.equals("1001")) {
            return "산하기관";
        } else if (this.orgKind != null && this.orgKind.equals("2001")) {
            return "유관기관";
        } else {
            return "";
        }
    }
}
