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
 * @Class Name : SalesSettleVO.java
 * @Description : 수입금정산 VO
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 12. 24
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Data
public class SalesSettleVO extends ComDefaultVO {
    private int rnum;
    private String comNm;
    private int orgNo;
    private String itemNm;
    private int itemCd;
    private String comCtgnm;
    private String itemCtgd;
    private int cnt;
    private int cashAmt;
    private int cardAmt;
    private int bankAmt;
    private int vbankAmt;
    private int totAmt;
    private int prvAmt;
    private int vatAmt;
    private int retCnt;
    private int returnAmt;

    private String orgNm;
    
    private String orgLtype;
    private String orgMtype;
    private String orgKind;

    private int totCnt;
}
