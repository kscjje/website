package com.hisco.admin.orginfo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hisco.admin.orginfo.vo.ComInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


 /**
  * @Class Name : ComInfoMapper.java
  * @Description : 운영사업자 정보 관리 mapper
  * @author user
  * @since 2021. 10. 26.
  * @version 1.0
  * @see
  *      Copyright(c) 2021 HISCO. All rights reserved
  */
@Mapper("comInfoMapper")
public interface ComInfoMapper {

	public List<?> selectComInfoList(ComInfoVO comInfoVO);

    public ComInfoVO selectComInfoDetail(ComInfoVO comInfoVO);

    public void insertComInfo(ComInfoVO comInfoVO);

    public void updateComInfo(ComInfoVO comInfoVO);

    public void deleteComInfo(ComInfoVO comInfoVO);

    public ComInfoVO selectComInfoDetail(@Param("comcd") String comcd);

    public ComInfoVO selectComOptDetail(ComInfoVO comInfoVO);

    public ComInfoVO selectComOptDetail(@Param("comcd") String comcd);

    public void insertComOpt(ComInfoVO comInfoVO);

    public void updateComOpt(ComInfoVO comInfoVO);

    public void deleteComOpt(ComInfoVO comInfoVO);
}
