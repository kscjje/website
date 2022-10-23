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
 * @Class Name : InstrPoolVO.java
 * @Description : 강사Pool관리 VO
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 9
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class InstrPoolVO extends ComDefaultVO {

    private String memNo;
    private String memNm;
    private String targetArea;
    private String lectrField;
    private String lectrTarget;
    private String lectrType;
    private String selfintrcn;
    private String eduinfoOpenyn;
    private String careerOpenyn;
    private String licenseEtc;
    private String proflImageid;
    private String proflEtc;
    private String state;
    private String stateNm;
    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;
    private int totCnt;

    private String id;
    private String genderGbn;
    private String birthDate;
    private String hp;
    private String email;

    private String fileName;
    private String fileOrigin;
    private String filePath;

    private String[] checkedMem;

    /* 디비 암호화 key */
    private String dbEncKey = EgovProperties.getProperty("Globals.DbEncKey");
}
