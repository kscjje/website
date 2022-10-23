package com.hisco.intrfc.sale.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.sale.mapper.NwPayLogMapper;
import com.hisco.intrfc.sale.vo.NwPayLogVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 노원페이 interface 시 로깅
 * 
 * @Class Name : NwpayLogService.java
 * @Description : 자세한 클래스 설명
 * @author woojinp@legitsys.co.kr
 * @since 2022. 1. 6.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Service("nwPayLogService")
public class NwPayLogService extends EgovAbstractServiceImpl {

    @Resource(name = "nwPayLogMapper")
    private NwPayLogMapper nwPayLogMapper;

    public int newTxInsertNwPayLog(NwPayLogVO vo) throws Exception {
        return nwPayLogMapper.insertNwPayLog(vo);
    }

    public List<NwPayLogVO> selectNwPayLogList(NwPayLogVO vo) throws Exception {
        return nwPayLogMapper.selectNwPayLogList(vo);
    }
}
