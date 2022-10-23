/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.sales.mapper;

import java.util.List;

import com.hisco.admin.sales.vo.SalesDiscountVO;
import com.hisco.admin.sales.vo.SalesPGHistVO;
import com.hisco.admin.sales.vo.SalesReceiptVO;
import com.hisco.admin.sales.vo.SalesRefundVO;
import com.hisco.admin.sales.vo.SalesSettleDetailVO;
import com.hisco.admin.sales.vo.SalesSettleVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : SalesSettleMapper.java
 * @Description : 자세한 클래스 설명
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 12. 24
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Mapper("salesMapper")
public interface SalesMapper {

    List<SalesSettleVO> selectSettleList(SalesSettleVO vo);

    SalesSettleVO selectSettleTotal(SalesSettleVO vo);

    List<SalesSettleDetailVO> selectSettlePayList(SalesSettleVO vo);

    SalesSettleDetailVO selectSettlePayTotal(SalesSettleVO vo);

    List<SalesReceiptVO> selectReceiptList(SalesReceiptVO vo);

    SalesReceiptVO selectReceiptDetail(SalesReceiptVO vo);

    List<SalesPGHistVO> selectPGHistory(SalesPGHistVO vo);

    List<SalesDiscountVO> selectDiscounted(SalesDiscountVO vo);

    SalesDiscountVO selectDiscountedTotal(SalesDiscountVO vo);

    List<SalesRefundVO> selectRefundList(SalesRefundVO vo);

    SalesRefundVO selectRefundTotal(SalesRefundVO vo);
}
