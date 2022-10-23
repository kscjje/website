package com.hisco.admin.terms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.terms.mapper.TermsMapper;
import com.hisco.admin.terms.vo.TermsVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 약관 조회 및 등록 구현 클래스
 * 
 * @author 진수진
 * @since 2020.07.29
 * @version 1.0, 2020.07.29
 *          ------------------------------------------------------ ------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.29 최초작성
 */
@Service("termsService")
public class TermsService extends EgovAbstractServiceImpl {

    @Resource(name = "termsMapper")
    private TermsMapper termsMapper;

    /**
     * 약관 목록을 조회한다
     * 
     * @param vo
     *            TermsVO
     * @return List
     * @exception Exception
     */
    public List<?> selectTermsList(TermsVO vo) throws Exception {
        return termsMapper.selectTermsList(vo);
    }

    /**
     * 약관 목록의 갯수를 조회한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public int selectTermsListCnt(TermsVO vo) throws Exception {
        return (Integer) termsMapper.selectTermsCount(vo);
    }

    /**
     * 약관 상세 정보를 조회한다
     * 
     * @param vo
     *            TermsVO
     * @return TermsVO
     * @exception Exception
     */
    public TermsVO selectTermsDetail(TermsVO vo) throws Exception {
        return (TermsVO) termsMapper.selectTermsDetail(vo);
    }

    /**
     * 약관 정보를 신규 생성한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public String insertTerms(TermsVO vo) throws Exception {
        TermsVO data = (TermsVO) termsMapper.selectTermsDetail(vo);
        String result = "";

        if (data != null) {
            result = "ER|동일한 약관코드가 존재합니다.";
        } else {
            int n = termsMapper.insertTerms(vo);

            if (n > 0) {
                result = "OK";
            } else {
                result = "ER|데이타입력 오류";
            }
        }
        return result;
    }

    /**
     * 약관 을 삭제한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public int deleteTerms(TermsVO vo) throws Exception {
        return termsMapper.deleteTerms(vo);
    }

    /**
     * 약관을 수정한다
     * 
     * @param vo
     *            TermsVO
     * @return int
     * @exception Exception
     */
    public int updateTerms(TermsVO vo) throws Exception {
        return termsMapper.updateTerms(vo);
    }
}
