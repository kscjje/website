package com.hisco.cmm.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.mapper.CodeMapper;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.vo.CodeVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : CodeService.java
 * @Description : 공통코드를 위한 서비스 인터페이스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 8. 21. 진수진 최초생성
 * @author 진수진
 * @since 2020. 8. 21.
 * @version
 * @see
 */
@Service("codeService")
public class CodeService extends EgovAbstractServiceImpl{

    @Resource(name = "codeMapper")
    private CodeMapper codeMapper;

    /**
     * 그룹코드로 코드 목록을 조회한다.
     *
     * @param fvo
     * @return
     * @throws Exception
     */
    public List<CodeVO> selectCodeList(String comcd ,String grpCd) throws Exception{
    	return codeMapper.selectCodeList( comcd ,grpCd);
    }

    /**
     * 코드상세에 대한 정보(속성 및 상세)를 조회한다.
     *
     * @param fvo
     * @throws Exception
     */
    public CodeVO selectCodeDetail(String comcd ,String grpCd, String cd) throws Exception{
    	return codeMapper.selectCodeDetail(comcd, grpCd,  cd);
    }


    /**
     * 그룹코드로 코드 목록을 조회한다.
     *
     * @param fvo
     * @return
     * @throws Exception
     */
    public List<CodeVO> selectCodeList(String grpCd) throws Exception{
    	return codeMapper.selectCodeList( Config.COM_CD ,grpCd);
    }

    /**
     * 코드상세에 대한 정보(속성 및 상세)를 조회한다.
     *
     * @param fvo
     * @throws Exception
     */
    public CodeVO selectCodeDetail(String grpCd, String cd) throws Exception{
    	return codeMapper.selectCodeDetail(Config.COM_CD, grpCd,  cd);
    }


    /**
     * NextSEQ
     *
     * @param String tableName
     * @return int
     * @throws Exception
     */
    public int selectNextseq(String tableName) throws Exception {
    	int nextId = codeMapper.selectNextSeq(tableName);
    	int cnt = codeMapper.updateNextSeq(tableName , nextId);
    	if(cnt < 1){
    		codeMapper.insertNextSeq(tableName , nextId);
    	}

    	return nextId;
    }

}
