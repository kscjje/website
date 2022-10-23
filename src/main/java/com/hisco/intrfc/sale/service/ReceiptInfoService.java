package com.hisco.intrfc.sale.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.sale.mapper.ReceiptInfoMapper;
import com.hisco.intrfc.sale.vo.ReceiptInfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : ReceiptInfoService.java
 * @Description : 영수증
 * @author woojinp@legitsys.co.kr
 * @since 2021. 12. 7.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Service("receiptInfoService")
public class ReceiptInfoService extends EgovAbstractServiceImpl {

    @Resource(name = "receiptInfoMapper")
    private ReceiptInfoMapper receiptInfoMapper;

    public int insertReceiptInfo(ReceiptInfoVO vo) throws Exception {
        return receiptInfoMapper.insertReceiptInfo(vo);
    }

    public ReceiptInfoVO selectReceiptInfo(ReceiptInfoVO vo) throws Exception {
        return receiptInfoMapper.selectReceiptInfo(vo);
    }

    public List<ReceiptInfoVO> selectReceiptInfoList(ReceiptInfoVO vo) throws Exception {
        return receiptInfoMapper.selectReceiptInfoList(vo);
    }

}
