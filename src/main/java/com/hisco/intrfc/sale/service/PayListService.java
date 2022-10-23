package com.hisco.intrfc.sale.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.sale.mapper.PayListMapper;
import com.hisco.intrfc.sale.vo.PayListVO;
import com.hisco.intrfc.sale.vo.PayMethodVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : PayListService.java
 * @Description : 지불내역
 * @author woojinp@legitsys.co.kr
 * @since 2021. 12. 7.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Service("payListService")
public class PayListService extends EgovAbstractServiceImpl {

    @Resource(name = "payListMapper")
    private PayListMapper payListMapper;

    public int insertPayList(PayListVO vo) throws Exception {
        return payListMapper.insertPayList(vo);
    }

    public int cancelPayList(PayListVO vo) throws Exception {
        return payListMapper.cancelPayList(vo);
    }

    public PayListVO selectPayList(PayListVO vo) throws Exception {
        return payListMapper.selectPayList(vo);
    }

    public List<PayListVO> selectPayListList(PayListVO vo) throws Exception {
        return payListMapper.selectPayListList(vo);
    }

    public List<PayMethodVO> selectPayMethodList(PayMethodVO vo) throws Exception {
        return payListMapper.selectPayMethodList(vo);
    }

}
