/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.instrctr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hisco.admin.instrctr.vo.InstrctrVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : InstrctrMapper.java
 * @Description : 강사관리 Mapper
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 5
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Mapper("instrctrMapper")
public interface InstrctrMapper {

    List<InstrctrVO> selectList(InstrctrVO vo);

    InstrctrVO select(InstrctrVO vo);

    int insert(InstrctrVO vo);

    int update(InstrctrVO vo);

    int updateSimple(InstrctrVO vo);

    int delete(InstrctrVO vo);

    int updateFileid(@Param("comcd") String comcd, @Param("instrctrNo") int instrctrNo,
            @Param("inputid") String inputid, @Param("atchFileId") String atchFileId, @Param("moduser") String moduser);

    Integer selectLastSeq();

    List<InstrctrVO> selectEdcList(InstrctrVO vo);
}
