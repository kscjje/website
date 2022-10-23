/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.twedu.vo;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;

/**
 * @Class Name : TweduVO.java
 * @Description : 마을배움터 관리 VO
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 3
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
@SuppressWarnings("serial")
public class TweduVO extends ComDefaultVO {
    private int rnum;
    private int edcPrgmNo;
    private String edcPrgmNm;
    private String edcProgmDate;
    private String edcSdate;
    private String edcEdate;
    private String edcPrg;
    private String edcPrgcdnm;
    private String memNo;
    private String memNm;
    private String memHp;
    private int edcClcnt;
    private int curEdcClcnt;
    private String edcOpenyn;
    private String edcDays;

    private String regdate;
    private String reguser;
    private String moddate;
    private String moduser;
    /* 디비 암호화 key */
    private String dbEncKey;

    private int totCnt;

    private int curStuCnt; // 수강인원
    private int edcPncpa;

    private String searchDate;
    private String edcDaygbnNm;

    private String edcRsvnSdate;
    private String edcRsvnStime;

}
