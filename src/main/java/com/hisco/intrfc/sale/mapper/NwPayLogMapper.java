package com.hisco.intrfc.sale.mapper;

import java.util.List;

import com.hisco.intrfc.sale.vo.NwPayLogVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("nwPayLogMapper")
public interface NwPayLogMapper {

    public int insertNwPayLog(NwPayLogVO vo);

    public List<NwPayLogVO> selectNwPayLogList(NwPayLogVO vo);
}
