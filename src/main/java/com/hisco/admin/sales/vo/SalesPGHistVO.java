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
 * @Class Name : SalesPGHistVO.java
 * @Description : PC거래현황 VO
 * @author vivasoul@legitsys.co.kr
 * @since 2022. 1. 12
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class SalesPGHistVO extends ComDefaultVO {
    private int rnum;
    private String appDate;
    private String orgNm;
    private String memNo;
    private String memNm;
    private String appNo;
    private String appGbn;
    private String cnlDate;
    private String forceYn;
    private String payType;
    private String methodCd;
    private String appAmt;

    private String payListYn;
    private String cardNum;
    private String mid;
    private String tid;
    private String oid;

    private int totCnt;

    private String orgKind;
    private int orgNo;
}
