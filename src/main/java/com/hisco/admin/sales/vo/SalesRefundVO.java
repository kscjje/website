/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.sales.vo;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;

/**
 * @Class Name : SalesRefundVO.java
 * @Description : 자세한 클래스 설명
 * @author vivasoul@legitsys.co.kr
 * @since 2022. 1. 5
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class SalesRefundVO extends ComDefaultVO {
    private int rnum;
    private String orgNm;
    private int orgNo;
    private String cancelDate;
    private String memNo;
    private String memNm;
    private String hpNo;
    private String edcPrgmnm;
    private int memCnt;
    private String methodNm;
    private int payAmt;
    private String payType;
    private String payPath;
    private String retReason;
    private int useAmt;
    private int returnAmt;
    private String retDpstrNm;
    private String retBankNm;
    private String retAcountNum;

    private String comCtgnm;
    private String itemCtgd;

    private String dbEncKey;
    private String orgKind;
    private String refundMethod;
    private String receiptNo;

    private String oid;
    private String mid;
    private String tid;
    private String appNo;
    private String rsvnNo;

    private int totCnt;
}
