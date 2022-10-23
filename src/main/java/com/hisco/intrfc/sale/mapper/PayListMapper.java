package com.hisco.intrfc.sale.mapper;

import java.util.List;

import com.hisco.intrfc.sale.vo.PayListVO;
import com.hisco.intrfc.sale.vo.PayMethodVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("payListMapper")
public interface PayListMapper {

    public int insertPayList(PayListVO vo);

    public int cancelPayList(PayListVO vo);

    public PayListVO selectPayList(PayListVO vo);

    public List<PayListVO> selectPayListList(PayListVO vo);

    public List<PayMethodVO> selectPayMethodList(PayMethodVO vo);
}
