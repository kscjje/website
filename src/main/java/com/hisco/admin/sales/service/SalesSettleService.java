/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.sales.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.sales.mapper.SalesMapper;
import com.hisco.admin.sales.vo.SalesDiscountVO;
import com.hisco.admin.sales.vo.SalesPGHistVO;
import com.hisco.admin.sales.vo.SalesReceiptVO;
import com.hisco.admin.sales.vo.SalesRefundVO;
import com.hisco.admin.sales.vo.SalesSettleDetailVO;
import com.hisco.admin.sales.vo.SalesSettleVO;

/**
 * @Class Name : SalesSettleService.java
 * @Description : 수입금(정산) 관련 서비스
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 12. 24
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Service("salesSettleService")
public class SalesSettleService {
    @Resource(name = "salesMapper")
    private SalesMapper salesMapper;

    public List<SalesSettleVO> selectSettleList(SalesSettleVO vo) {
        return salesMapper.selectSettleList(vo);
    }

    public SalesSettleVO selectSettleTotal(SalesSettleVO vo) {
        return salesMapper.selectSettleTotal(vo);
    }

    public List<SalesSettleDetailVO> selectSettlePayList(SalesSettleVO vo) {
        return salesMapper.selectSettlePayList(vo);
    }

    public SalesSettleDetailVO selectSettlePayTotal(SalesSettleVO vo) {
        return salesMapper.selectSettlePayTotal(vo);
    }

    public List<SalesReceiptVO> selectReceiptList(SalesReceiptVO vo) {
        return salesMapper.selectReceiptList(vo);
    }


    public SalesReceiptVO selectReceiptDetail(SalesReceiptVO vo) {
        return salesMapper.selectReceiptDetail(vo);
    }

    public List<SalesPGHistVO> selectPGHistory(SalesPGHistVO vo) {
        return salesMapper.selectPGHistory(vo);
    }

    public List<SalesDiscountVO> selectDiscounted(SalesDiscountVO vo) {
        return salesMapper.selectDiscounted(vo);
    }

    public SalesDiscountVO selectDiscountedTotal(SalesDiscountVO vo) {
        return salesMapper.selectDiscountedTotal(vo);
    }

    public List<SalesRefundVO> selectRefundList(SalesRefundVO vo) {
        return salesMapper.selectRefundList(vo);
    }

    public SalesRefundVO selectRefundTotal(SalesRefundVO vo) {
        return salesMapper.selectRefundTotal(vo);
    }
}
