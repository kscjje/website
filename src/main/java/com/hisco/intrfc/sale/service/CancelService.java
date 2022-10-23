package com.hisco.intrfc.sale.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.sale.mapper.CancelMapper;
import com.hisco.intrfc.sale.vo.CancelVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : CancelService.java
 * @Description : 수강신청취소
 * @author woojinp@legitsys.co.kr
 * @since 2021. 12. 7.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Service("cancelService")
public class CancelService extends EgovAbstractServiceImpl {

    @Resource(name = "cancelMapper")
    private CancelMapper cancelMapper;

    public int insertCancel(CancelVO vo) throws Exception {
        return cancelMapper.insertCancel(vo);
    }

    public CancelVO selectCancel(CancelVO vo) throws Exception {
        return cancelMapper.selectCancel(vo);
    }

    public List<CancelVO> selectCancelList(CancelVO vo) throws Exception {
        return cancelMapper.selectCancelList(vo);
    }

}
