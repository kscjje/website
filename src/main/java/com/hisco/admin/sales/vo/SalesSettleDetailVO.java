/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.sales.vo;

import lombok.Data;

/**
 * @Class Name : SalesSettleDetailVO.java
 * @Description : 자세한 클래스 설명
 * @author vivasoul@legitsys.co.kr
 * @since 2022. 1. 5
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class SalesSettleDetailVO extends SalesSettleVO {
    private String payDate;
    private String memNo;
    private String memNm;

    private int nretCnt;
    private int nretCashAmt;
    private int nretCardAmt;
    private int nretBankAmt;
    private int nretVbankAmt;
}
