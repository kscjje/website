/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.instrctr.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.instrctr.mapper.InstrPoolMapper;
import com.hisco.admin.instrctr.vo.InstrPoolVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : InstrPoolService.java
 * @Description : 강사Pool관리 Service
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 9
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Service("instrPoolService")
public class InstrPoolService extends EgovAbstractServiceImpl {

    @Resource(name = "instrPoolMapper")
    private InstrPoolMapper instrPoolMapper;

    public List<InstrPoolVO> list(InstrPoolVO vo) {
        return instrPoolMapper.selectList(vo);
    }

    public InstrPoolVO detail(InstrPoolVO vo) {
        return instrPoolMapper.select(vo);
    }

    public int create(InstrPoolVO vo) {

        return instrPoolMapper.insert(vo);
    }

    public int update(InstrPoolVO vo) {
        return instrPoolMapper.update(vo);
    }

    public int delete(InstrPoolVO vo) {
        return instrPoolMapper.delete(vo);
    }

    public int updateState(InstrPoolVO vo) {
        return instrPoolMapper.updateState(vo);
    }

    public List<String> updateStateCheckAll(InstrPoolVO vo) {
        ArrayList<String> updateMember = new ArrayList<String>();

        for(String memNo : vo.getCheckedMem()){
        	vo.setMemNo(memNo);
        	int no = instrPoolMapper.updateStateCheck(vo);
        	if(no > 0){
        		updateMember.add(memNo);
        	}
        }

    	return updateMember;
    }
}
