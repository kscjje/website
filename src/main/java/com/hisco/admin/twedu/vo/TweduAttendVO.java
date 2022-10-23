/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.twedu.vo;

import lombok.Data;

/**
 * @Class Name : TweduAttendVO.java
 * @Description : 마을배움터 출석정보 VO
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 24
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class TweduAttendVO {
    private String comcd;
    private String memNo;
    private String memNm;
    private String memHp;

    private int edcPrgmid;
    private int edcRsvnComptid;
    private int edcRsvnReqid;

    private String edcAtendDate;
    private String edcAtendGbn;
    private String regdate;
    private String reguser;
    private String moddate;
    private String moduser;

    private int passCnt;
    private int nopassCnt;
    private int passRate;
    private String edcComplstat;
}
