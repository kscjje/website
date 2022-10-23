package com.hisco.intrfc.sale.mapper;

import com.hisco.intrfc.sale.vo.SelngInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("selngInfoMapper")
public interface SelngInfoMapper {

    public int insertSelngInfo(SelngInfoVO vo);

    public int cancelSelngInfo(SelngInfoVO vo);

    public int selectNextSelngId();
}
