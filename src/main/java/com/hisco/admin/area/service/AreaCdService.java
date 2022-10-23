package com.hisco.admin.area.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.area.mapper.AreaCdMapper;
import com.hisco.admin.area.vo.AreaCdVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 지역 관리 Service 구현 클래스
 *
 * @author 진수진
 * @since 2021.10.21
 * @version 1.0, 2021.10.21
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.10.21 최초작성
 */
@Service("areaCdService")
public class AreaCdService extends EgovAbstractServiceImpl {

    @Resource(name = "areaCdMapper")
    private AreaCdMapper areaCdMapper;

    public List<?> selectAreaCdList(AreaCdVO areaCdVO) {
    	return areaCdMapper.selectAreaCdList(areaCdVO);
    }

    public List<?> selectAreaCdSubList(int parentAreaCd) {
    	return areaCdMapper.selectAreaCdSubList(parentAreaCd);
    }

    public void insertAreaCd(AreaCdVO areaCdVO) throws Exception {

    	areaCdMapper.insertAreaCd(areaCdVO);
    }

    public void updateAreaCd(AreaCdVO areaCdVO) throws Exception {
    	areaCdMapper.updateAreaCd(areaCdVO);
    }

    public AreaCdVO selectAreaCdDetail(AreaCdVO areaCdVO) throws Exception {
        return areaCdMapper.selectAreaCdDetail(areaCdVO);
    }

    public void deleteAreaCd(AreaCdVO areaCdVO) throws Exception {
    	areaCdMapper.deleteAreaCd(areaCdVO);
    }



}
