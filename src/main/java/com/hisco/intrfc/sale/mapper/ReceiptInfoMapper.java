package com.hisco.intrfc.sale.mapper;

import java.util.List;

import com.hisco.intrfc.sale.vo.ReceiptInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("receiptInfoMapper")
public interface ReceiptInfoMapper {

    public int insertReceiptInfo(ReceiptInfoVO vo);

    public ReceiptInfoVO selectReceiptInfo(ReceiptInfoVO vo);

    public List<ReceiptInfoVO> selectReceiptInfoList(ReceiptInfoVO vo);
}
