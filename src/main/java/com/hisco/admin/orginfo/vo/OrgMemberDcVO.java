package com.hisco.admin.orginfo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrgMemberDcVO extends OrgDcVO {
    private String memNo;
    private String discountCd;
    private String dcconfirmYn;
    private String dcconfSdate;
    private String dcconfEdate;
}
