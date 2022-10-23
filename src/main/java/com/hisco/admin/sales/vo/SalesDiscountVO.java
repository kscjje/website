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
 * @Class Name : SalesDiscountVO.java
 * @Description : 자세한 클래스 설명
 * @author vivasoul@legitsys.co.kr
 * @since 2022. 1. 20
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class SalesDiscountVO extends ComDefaultVO {
    private String discountcd;
    private String dcReason;
    private String dcRate;
    private String orgNm;
    private int freeCnt;
    private int payCnt;
    private int cashAmt;
    private int cardAmt;
    private int bankAmt;
    private int vbankAmt;
    private int refundCnt;
    private int refundAmt;
    private int totAmt;
    private int prvAmt;
    private int vatAmt;

    private int orgNo;
    private String orgKind;
    private String itemCtgd;
    private String comCtgnm;

    private int totCnt;
}
