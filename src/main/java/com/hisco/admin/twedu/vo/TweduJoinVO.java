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
 * @Class Name : TweduJoinVO.java
 * @Description : 마을배움터 참여현황 VO
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 12. 23
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class TweduJoinVO extends ComDefaultVO {
    private int rnum;

    private int edcRsvnReqid;
    private int edcPrgmid;
    private String comcd;
    private String edcMemNo;
    private String edcReqDate;
    private String edcPrgmnm;
    private String instrctrName;
    private String edcSdate;
    private String edcEdate;
    private String edcComplstat;
    private String edcComplyn;
    private String edcCtfhvyn;
    private String edcConfirmDate;

    private String edcRetyn;
    private String retDate;
    private String edcStat;

    private int totCnt;
}
