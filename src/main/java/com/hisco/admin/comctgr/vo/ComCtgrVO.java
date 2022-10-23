package com.hisco.admin.comctgr.vo;

import java.sql.Timestamp;
import java.util.List;

import com.hisco.admin.area.vo.AreaCdVO;

import lombok.Data;

@Data
public class ComCtgrVO {

	private String comcd;
	private String CtgCd;
	private String CtgNm;
	private String CtgDesc;
	private int ctgLvl;
	private String parentCtgCd;
	private String parentCtgNm;
	private String topCtgCd;
	private String topCtgNm;
	private int sortOrder;
	private String UseYn;
	private Timestamp regdate;
	private String reguser;
	private Timestamp moddate;
	private String moduser;

	private List<ComCtgrVO> subCtgrList;
}
