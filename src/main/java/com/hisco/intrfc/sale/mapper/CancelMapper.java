package com.hisco.intrfc.sale.mapper;

import java.util.List;

import com.hisco.intrfc.sale.vo.CancelVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("cancelMapper")
public interface CancelMapper {

    public int insertCancel(CancelVO vo);

    public CancelVO selectCancel(CancelVO vo);

    public List<CancelVO> selectCancelList(CancelVO vo);
}
