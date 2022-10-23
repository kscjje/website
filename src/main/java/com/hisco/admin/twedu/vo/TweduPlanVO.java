/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.twedu.vo;

import lombok.Data;

/**
 * @Class Name : TweduPlanVO.java
 * @Description : 마을배움터 학습계획서 VO
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 12. 1
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class TweduPlanVO {
    private int rnum;
    private String comcd;
    private int edcPrgmNo;
    private int lectSeq;
    private int edcClassNo;
    private String edcDate;
    private String edcTitle;
    private String edcCnts;
    private String edcEtccnts;

    private String lectFileid;

    private String memNm;
    private String instrctrName;
    private String lectImgFilenm;
    private String lectImgOrigin;
    private String lectImgPath;

    private String weekName;
    private int passCnt;
    private int missCnt;
    private int totalMemCnt;

    private String passYn;

    private String regdate;
    private String moddate;
    private String reguser;
    private String moduser;

    private int totCnt;
}
