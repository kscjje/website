package com.hisco.intrfc.sale.mapper;

import java.util.List;

import com.hisco.intrfc.sale.vo.EdcRsvnComptinfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("edcRsvnComptinfoMapper")
public interface EdcRsvnComptinfoMapper {

    public int insertEdcRsvnComptinfo(EdcRsvnComptinfoVO vo);

    public int cancelEdcRsvnComptinfo(EdcRsvnComptinfoVO vo);

    public EdcRsvnComptinfoVO selectEdcRsvnComptinfo(EdcRsvnComptinfoVO vo);

    public List<EdcRsvnComptinfoVO> selectEdcRsvnComptinfoList(EdcRsvnComptinfoVO vo);
}
