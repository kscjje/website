package com.hisco.user.main.service;

import java.util.List;
import java.util.Map;

import com.hisco.cmm.object.CamelMap;

import egovframework.com.cmm.LoginVO;

/**
 * 일반사용자 회원가입 처리
 * 
 * @author 김희택
 * @since 2020.08.13
 * @version 1.0, 2020.08.13
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          김희택 2020.08.13 최초생성
 */
public interface MainService {

    /**
     * 약관 목록을 조회한다
     * 
     * @param vo
     *            TermsVO
     * @return List
     * @exception Exception
     */
    public List<CamelMap> selectMainPopupList() throws Exception;

    /**
     * 검색 데이타
     * 
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public Map<String, Object> selectMainSearch(MainSearchVO vo) throws Exception;

    /**
     * 오늘의 티켓목록을 조회한다
     * 
     * @param vo
     *            TermsVO
     * @return List
     * @exception Exception
     */
    public List<CamelMap> selectTodayTicketList(LoginVO loginVO) throws Exception;

    /**
     * 관람 선택 1개
     * 
     * @param
     * @return String
     * @exception Exception
     */
    public String selectDspyPartCd() throws Exception;

    /**
     * 공지사항, 수학문화관소식, Q&A
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectBBSList(Map<String, Object> paramMap) throws Exception;

}
