/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.twedu.vo;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @Class Name : TweduStudentVO.java
 * @Description : 마을배움터 학생현황 VO
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 12. 8
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class TweduStudentVO {
    private int rnum;
    private String comcd;
    private int edcRsvnComptid;
    private int edcRsvnReqid;
    private int edcPrgmid;
    private String edcReqDate;
    private String memId;
    private String memNm;
    private String birthDate;
    private String gender;
    private String memHp;

    private String edcComplstat;

    private Timestamp regdate;
    private String reguser;
    private Timestamp moddate;
    private String moduser;

    private String edcRetyn;
    private String retDate;
    private String edcEdate;
    private String edcStat;

    private int attendCnt;

    private int totCnt;
}
