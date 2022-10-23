package com.hisco.user.member.service;

import java.util.Map;

import com.hisco.cmm.object.CamelMap;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.member.vo.MemberYearVO;
import com.hisco.user.mypage.vo.MyRsvnVO;

/**
 * 연간회원 관련 service
 * 
 * @author 진수진
 * @since 2020.09.08
 * @version 1.0, 2020.09.08
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.09.08 최초생성
 */
public interface MemYearService {

    /**
     * 연간회원 상품을 조회한다
     * 
     * @param vo
     *            Map
     * @return MemberYearVO
     * @exception Exception
     */
    public MemberYearVO selectProgramItemData(Map<?, ?> vo) throws Exception;

    /**
     * 연회원 결제 후 저장한다
     *
     * @param Map
     * @throws Exception
     */
    public boolean insertMemberYear(Map<String, Object> paramMap, MemberYearVO vo, MyRsvnVO myRsvnVO) throws Exception;

    /**
     * 연간회원 가입 정보를 조회한다
     * 
     * @return MemberYearVO
     * @exception Exception
     */
    public MemberYearVO selectMemYearData(MemberYearVO vo) throws Exception;

    /**
     * 연간회원 마지막 가입 정보를 조회한다
     * 
     * @return MemberYearVO
     * @exception Exception
     */
    public MemberYearVO selectMemYearResult(MemberYearVO vo) throws Exception;

    /**
     * 연간회원 재가입 할인율 조회
     * 
     * @return String
     * @exception Exception
     */
    public CamelMap selectDiscountValue(MemberYearVO vo) throws Exception;

    /**
     * 오늘날짜 조회
     * 
     * @return String
     * @exception Exception
     */
    public String selectToday() throws Exception;

    /**
     * 가족회원 정보 조회
     * 
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public CamelMap selectFamilyMember(MemberVO vo) throws Exception;

    /**
     * 가족 유료회원 가입
     * 
     * @param vo
     *            MemberVO
     * @return String
     * @exception Exception
     */
    public String insertFamilyMember(MemberVO meVO, MemberVO vo, String oid, String terminalType) throws Exception;

}
