package com.hisco.admin.terms.mapper;

import java.util.List;

import com.hisco.admin.terms.vo.TermsVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 약관 조회 및 등록 Mapper
 * 
 * @author 진수진
 * @since 2020.07.29
 * @version 1.0, 2020.07.29
 *          ------------------------------------------------------ ------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.29 최초작성
 */
@Mapper("termsMapper")
public interface TermsMapper {

    /**
     * 약관 목록을 조회한다
     * 
     * @param vo
     *            TermsVO
     * @return List
     * @exception Exception
     */
    public List<?> selectTermsList(TermsVO vo);

    /**
     * 약관 목록의 갯수를 조회한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public int selectTermsCount(TermsVO vo);

    /**
     * 약관 상세 정보를 조회한다
     * 
     * @param vo
     *            TermsVO
     * @return TermsVO
     * @exception Exception
     */
    public TermsVO selectTermsDetail(TermsVO vo);

    /**
     * 약관 정보를 신규 생성한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public int insertTerms(TermsVO vo);

    /**
     * 약관 을 삭제한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public int deleteTerms(TermsVO vo);

    /**
     * 약관을 수정한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public int updateTerms(TermsVO vo);
}
