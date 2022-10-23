package com.hisco.cmm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hisco.cmm.vo.CodeVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : CodeMapper.java
 * @Description : 공통 코드 사용 mapper
 * @author 진수진
 * @since 2021. 10. 27
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Mapper("codeMapper")
public interface CodeMapper {

    public List<CodeVO> selectCodeList(@Param("comcd") String comcd, @Param("grpCd") String grpCd) throws Exception;

    /**
     * 코드상세에 대한 정보(속성 및 상세)를 조회한다.
     */
    public CodeVO selectCodeDetail(@Param("comcd") String comcd, @Param("grpCd") String grpCd, @Param("cd") String cd)
            throws Exception;

    /**
     * 다음 seq 값을 가져온디
     *
     * @param String
     *            tableName
     * @return int
     * @throws Exception
     */
    public int selectNextSeq(@Param("tableName") String tableName);

    /**
     * 다음 Seq 값을 저장한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int updateNextSeq(@Param("tableName") String tableName, @Param("nextId") int nextId);

    /**
     * 다음 Seq 값을 저장한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int insertNextSeq(@Param("tableName") String tableName, @Param("nextId") int nextId);

}
