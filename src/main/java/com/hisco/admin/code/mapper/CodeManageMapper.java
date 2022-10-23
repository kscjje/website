package com.hisco.admin.code.mapper;

import java.util.HashMap;
import java.util.List;

import com.hisco.cmm.vo.CodeVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 공통코드 조회 및 등록 구현클래스
 * 
 * @author 진수진
 * @since 2021.03.24
 * @version 1.0, 2021.03.24
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.03.24 최초작성
 */
@Mapper("codeManageMapper")
public interface CodeManageMapper {

    /**
     * 공통코드 그룹을 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return List
     * @exception Exception
     */
    public List<?> selectCodeGrpList(CodeVO vo);

    
    public List<HashMap<String, Object>> selectCodeGrpList2(HashMap<String, Object> parameter);

    /**
     * 공통코드 그룹의 갯수를 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int selectCodeGrpCount(CodeVO vo);

    /**
     * 공통코드 그룹 상세를 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return List
     * @exception Exception
     */
    public CodeVO selectCodeGrpDetail(CodeVO vo);

    /**
     * 공통코드 그룹을 신규 생성한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public int insertCodeGrp(CodeVO vo);

    /**
     * 공통코드 그룹 을 삭제한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int deleteCodeGrp(CodeVO vo);

    /**
     * 공통코드 그룹을 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int updateCodeGrp(CodeVO vo);

    /**
     * 공통코드 상세목록을 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return List
     * @exception Exception
     */
    public List<?> selectCodeDetailList(CodeVO vo);

    /**
     * 공통코드 상세를 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return List
     * @exception Exception
     */
    public CodeVO selectCodeDetail(CodeVO vo);

    /**
     * 공통코드 상세데이타를 신규 생성한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public int insertCodeDetail(CodeVO vo);

    /**
     * 공통코드 상세를 삭제한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int deleteCodeDetail(CodeVO vo);

    /**
     * 공통코드 상세 데이타를 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int updateCodeDetail(CodeVO vo);

    /**
     * 공통코드 상세 데이타 사영여부만 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int useCodeDetail(CodeVO vo);

    /**
     * 공통코드 상세 순서를 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int sortCodeDetail(CodeVO vo);

    /**
     * 기관별코드 상세목록을 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return List
     * @exception Exception
     */
    public List<?> selectOrgCodeDetailList(CodeVO vo);

    /**
     * 기관별코드 상세를 조회한다
     * 
     * @param vo
     *            CodeVO
     * @return List
     * @exception Exception
     */
    public CodeVO selectOrgCodeDetail(CodeVO vo);

    /**
     * 기관별코드 상세데이타를 신규 생성한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public int insertOrgCodeDetail(CodeVO vo);

    /**
     * 기관별코드 상세를 삭제한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int deleteOrgCodeDetail(CodeVO vo);

    /**
     * 기관별코드 상세 데이타를 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int updateOrgCodeDetail(CodeVO vo);

    /**
     * 기관별코드 상세 사용여부만 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int useOrgCodeDetail(CodeVO vo);

    /**
     * 기관별코드 순서를 수정한다
     * 
     * @param vo
     *            CodeVO
     * @return int
     * @exception Exception
     */
    public int sortOrgCodeDetail(CodeVO vo);
}
