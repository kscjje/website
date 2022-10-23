package com.hisco.admin.orginfo.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.orginfo.mapper.ComInfoMapper;
import com.hisco.admin.orginfo.vo.ComInfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;


 /**
  * @Class Name : ComInfoService.java
  * @Description :  운영사업자 정보 관리 Service
  * @author 진수진
  * @since 2021. 10. 26.
  * @version 1.0
  * @see
  *      Copyright(c) 2021 HISCO. All rights reserved
  */
@Slf4j
@Service("comInfoService")
public class ComInfoService extends EgovAbstractServiceImpl{

    @Resource(name = "comInfoMapper")
    private ComInfoMapper comInfoMapper;


	public List<?> selectComInfoList(ComInfoVO comInfoVO){
		return comInfoMapper.selectComInfoList(comInfoVO);
	}

    public ComInfoVO selectComInfoDetail(ComInfoVO comInfoVO){
    	return comInfoMapper.selectComInfoDetail(comInfoVO);
    }

    public void insertComInfo(ComInfoVO comInfoVO){
    	comInfoMapper.insertComInfo(comInfoVO);
    	comInfoMapper.insertComOpt(comInfoVO);
    }

    public void updateComInfo(ComInfoVO comInfoVO){
    	comInfoMapper.updateComInfo(comInfoVO);

    	ComInfoVO optData = comInfoMapper.selectComOptDetail(comInfoVO);

    	//운영환경 설정 데이타 저장
    	if(optData == null){
    		comInfoMapper.insertComOpt(comInfoVO);
    	}else{
    		comInfoMapper.updateComOpt(comInfoVO);
    	}
    }

    public void deleteComInfo(ComInfoVO comInfoVO){
    	comInfoMapper.deleteComOpt(comInfoVO);
    	comInfoMapper.deleteComInfo(comInfoVO);
    }

    public ComInfoVO selectComInfoDetail(String comcd){
    	return comInfoMapper.selectComInfoDetail(comcd);
    }

    public ComInfoVO selectComOptDetail(String comcd){
    	return comInfoMapper.selectComOptDetail(comcd);
    }

    public ComInfoVO selectComOptDetail(ComInfoVO comInfoVO){
    	return comInfoMapper.selectComOptDetail(comInfoVO);
    }

    public void insertComOpt(ComInfoVO comInfoVO){
    	comInfoMapper.insertComOpt(comInfoVO);
    }

    public void updateComOpt(ComInfoVO comInfoVO){
    	comInfoMapper.updateComOpt(comInfoVO);
    }

    public void deleteComOpt(ComInfoVO comInfoVO){
    	comInfoMapper.updateComOpt(comInfoVO);
    }
}
