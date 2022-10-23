package com.hisco.intrfc.sale.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.sale.mapper.SelngInfoMapper;
import com.hisco.intrfc.sale.vo.SelngInfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : SelngInfoService.java
 * @Description : 통합매출
 * @author woojinp@legitsys.co.kr
 * @since 2021. 12. 7.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Service("selngInfoService")
public class SelngInfoService extends EgovAbstractServiceImpl {

    @Resource(name = "selngInfoMapper")
    private SelngInfoMapper selngInfoMapper;

    public int insertSelngInfo(SelngInfoVO vo) throws Exception {
        return selngInfoMapper.insertSelngInfo(vo);
    }

    public int cancelSelngInfo(SelngInfoVO vo) throws Exception {
        return selngInfoMapper.cancelSelngInfo(vo);
    }

    public int selectNextSelngId() throws Exception {
        return selngInfoMapper.selectNextSelngId();
    }
}
