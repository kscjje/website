package com.hisco.intrfc.sale.mapper;

import java.util.List;

import com.hisco.intrfc.sale.vo.SaleDiscountVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("saleDiscountMapper")
public interface SaleDiscountMapper {

    public int insertSaleDiscount(SaleDiscountVO vo);

    public int cancelSaleDiscount(SaleDiscountVO vo);

    public SaleDiscountVO selectSaleDiscount(SaleDiscountVO vo);

    public List<SaleDiscountVO> selectSaleDiscountList(SaleDiscountVO vo);
}
