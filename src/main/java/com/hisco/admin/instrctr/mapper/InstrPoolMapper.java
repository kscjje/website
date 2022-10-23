/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.instrctr.mapper;

import java.util.List;

import com.hisco.admin.instrctr.vo.InstrPoolVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : InstrPoolMapper.java
 * @Description : 강사Pool관리 Mapper
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 9
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Mapper("instrPoolMapper")
public interface InstrPoolMapper {
    List<InstrPoolVO> selectList(InstrPoolVO vo);

    InstrPoolVO select(InstrPoolVO vo);

    int insert(InstrPoolVO vo);

    int update(InstrPoolVO vo);

    int delete(InstrPoolVO vo);

    int updateState(InstrPoolVO vo);

    int updateStateCheck(InstrPoolVO vo);
}
