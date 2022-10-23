package com.hisco.user.member.service;

import java.util.Map;

import com.hisco.cmm.object.CamelMap;

import egovframework.com.cmm.LoginVO;

/**
 * 일반사용자 로그인 처리
 *
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.05 최초작성
 *          진수진 2020.08.11 함수 추가
 */
public interface UserLoginService {

    /**
     * 일반 로그인을 처리한다
     *
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    LoginVO actionLogin(LoginVO vo) throws Exception;

    /**
     * 아이디를 찾는다.
     *
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    LoginVO searchId(LoginVO vo) throws Exception;

    /**
     * 비밀번호를 찾는다.
     *
     * @param vo
     *            LoginVO
     * @return boolean
     * @exception Exception
     */
    boolean searchPassword(LoginVO vo) throws Exception;

    /**
     * 로그인인증제한을 처리한다.
     *
     * @param vo
     *            LoginVO
     * @param snsYn
     *            String
     * @return boolean
     * @exception Exception
     */
    String processLoginIncorrect(LoginVO vo, String snsYn) throws Exception;
    
    /**
     * 마이페이지 진입시 패스워드 체크를 한다.
     *
     * @param vo
     *            LoginVO
     * @param snsYn
     *            String
     * @return boolean
     * @exception Exception
     */
    String myInfoPasswordCheck(LoginVO vo, String snsYn) throws Exception;

    /**
     * 로그인인증제한을 조회한다.
     *
     * @param vo
     *            LoginVO
     * @return boolean
     * @exception Exception
     */
    Map<?, ?> selectSnsLoginIncorrect(LoginVO vo) throws Exception;

    /**
     * 로그인인증제한을 조회한다.
     *
     * @param vo
     *            LoginVO
     * @param Map
     *            mapLockUserInfo
     * @return boolean
     * @exception Exception
     */
    Map<?, ?> selectLoginIncorrect(LoginVO vo) throws Exception;

    /**
     * 로그인 로그를 남긴다
     *
     * @param vo
     *            LoginVO
     * @param errorCode
     * @return
     * @exception Exception
     */
    void insertLoginLog(LoginVO vo, String errorCode) throws Exception;

    /**
     * 로그인 로그를 남긴다
     *
     * @param vo
     *            LoginVO
     * @param errorCode
     * @return
     * @exception Exception
     */
    void insertLoginLog(LoginVO vo, String errorCode, String errorMsg) throws Exception;

    /**
     * SNS 연결 정보를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return CamelMap
     * @exception Exception
     */
    CamelMap selectSnsKind(LoginVO vo) throws Exception;

    /**
     * SNS 연결정보를 등록한다
     *
     * @param map
     * @return
     * @exception Exception
     */
    String insertSnsConnection(Map<?, ?> map) throws Exception;

    /**
     * SNS 연결정보를 취소한다
     *
     * @param map
     * @return
     * @exception Exception
     */
    void updateSnsConnection(Map<?, ?> map) throws Exception;

    /**
     * 이름 / 이메일로 아이디를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    LoginVO selectFindId(LoginVO vo) throws Exception;

    /**
     * 이름 / 이메일로 아이디를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    LoginVO selectFindIdByCerti(LoginVO vo) throws Exception;

    /**
     * 휴면상태를 해제한다
     *
     * @param vo
     * @return
     * @exception Exception
     */
    void updateMemberWakeup(LoginVO vo) throws Exception;

    /**
     * SNS 연결정보를 조회한다
     *
     * @param map
     * @return
     * @exception Exception
     */
    int selectSnsConnection(Map<?, ?> map) throws Exception;
    
  //비밀번호 암호화
    public String passwordEncryption(String password , LoginVO loginUser ) throws Exception;
}
