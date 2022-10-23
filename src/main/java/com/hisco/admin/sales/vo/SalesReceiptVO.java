/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.sales.vo;

import java.sql.Timestamp;

import egovframework.com.cmm.ComDefaultVO;
import lombok.Data;

/**
 * @Class Name : SalesReceiptVO.java
 * @Description : 자세한 클래스 설명
 * @author vivasoul@legitsys.co.kr
 * @since 2022. 1. 6
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class SalesReceiptVO extends ComDefaultVO {
    private int rnum;
    private String payDate;
    private String orgNm;
    private String receiptNo;
    private String memNo;
    private String memNm;
    private String edcPrgmnm;
    private String itemNm;
    private int salnum;
    private int costAmt;
    private int dcAmt;
    private String dcTypeNm;
    private String dcType;
    private String payMethodNm;
    private String payLocNm;
    private int payAmt;
    private String cashier;
    private String oid;
    private String mid;
    private String tid;
    private String appNo;
    private String rsvnNo;
    private int totCnt;

    private String slipType;
    private String orgKind;
    private int orgNo;

    private Timestamp regdate;
}
