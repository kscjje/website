package com.hisco.intrfc.sale.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.sale.mapper.SaleDiscountMapper;
import com.hisco.intrfc.sale.vo.SaleDiscountVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : SaleDiscountService.java
 * @Description : 할인정보CRUD
 * @author woojinp@legitsys.co.kr
 * @since 2021. 12. 7.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Service("saleDiscountService")
public class SaleDiscountService extends EgovAbstractServiceImpl {

    @Resource(name = "saleDiscountMapper")
    private SaleDiscountMapper saleDiscountMapper;

    public int insertSaleDiscount(SaleDiscountVO vo) throws Exception {
        return saleDiscountMapper.insertSaleDiscount(vo);
    }

    public int cancelSaleDiscount(SaleDiscountVO vo) throws Exception {
        return saleDiscountMapper.cancelSaleDiscount(vo);
    }

    public SaleDiscountVO selectSaleDiscount(SaleDiscountVO vo) throws Exception {
        return saleDiscountMapper.selectSaleDiscount(vo);
    }

    public List<SaleDiscountVO> selectSaleDiscountList(SaleDiscountVO vo) throws Exception {
        return saleDiscountMapper.selectSaleDiscountList(vo);
    }

}
