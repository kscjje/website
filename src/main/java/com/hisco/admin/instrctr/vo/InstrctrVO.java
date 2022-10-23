/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.instrctr.vo;

import java.sql.Timestamp;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.service.EgovProperties;
import lombok.Data;

/**
 * @Class Name : InstrctrVO.java
 * @Description : 강사관리 VO
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 5
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class InstrctrVO extends ComDefaultVO {
    private int instrctrNo;
    private int orgNo;
    private String instrctrTye;
    private String instrctrImgid;
    private String nameKor;
    private String nameEng;
    private String genderGbn;
    private String email;
    private String addr;
    private String homeTel;
    private String officeTel;
    private String hpNo;
    private String lastAcdmcr;
    private String lastShname;
    private String psitnOrgname;
    private String careerInfo;
    private String selfintrcn;
    private String etc;
    private String bankNm;
    private String bankAccNo;
    private String bankAccNm;
    private String useyn;
    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;
    private int totCnt;

    private String comnm;
    private String orgNm;
    private String typeNm;
    private String imgFilenm;
    private String imgOrigin;
    private String imgPath;

    private String edcPrgmnm;
    private String edcSdate;
    private String edcEdate;

    //private String searchOrgNo;

    /* 디비 암호화 key */
    private String dbEncKey = EgovProperties.getProperty("Globals.DbEncKey");
}
