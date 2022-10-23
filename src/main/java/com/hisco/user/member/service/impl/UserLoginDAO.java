package com.hisco.user.member.service.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hisco.cmm.object.CamelMap;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

/**
 * 사용자 로그인을 처리하는 DAO 클래스
 * 
 * @author 진수진
 * @since 2020.08.11
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2020.08.11  진수진          최초 생성
 *      </pre>
 */
@Repository("userLoginDAO")
public class UserLoginDAO extends EgovComAbstractDAO {

    /**
     * 로그인 계정 잠김 설정 횟수
     * 
     * @param vo
     *            Map
     * @return int
     * @exception Exception
     */
    public int selectLockCntConfig(Map<String, String> vo) throws Exception {
        return (Integer) selectOne("UserLoginDAO.selectLockCntConfig", vo);
    }

    /**
     * 로그인 계정 잠김 설정 횟수
     * 
     * @param vo
     *            Map
     * @return int
     * @exception Exception
     */
    public int selectPasswdChangeConfig(Map<String, String> vo) throws Exception {
        return (Integer) selectOne("UserLoginDAO.selectPasswdChangeConfig", vo);
    }

    /**
     * 일반 로그인을 처리한다
     * 
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    public LoginVO actionLogin(LoginVO vo) throws Exception {
        if ("9002".equals(vo.getStatus())) {
            return (LoginVO) selectOne("UserLoginDAO.actionLogin2", vo);
        } else {
            return (LoginVO) selectOne("UserLoginDAO.actionLogin", vo);
        }

    }

    /**
     * 아이디를 찾는다.
     * 
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    public LoginVO searchId(LoginVO vo) throws Exception {

        return (LoginVO) selectOne("UserLoginDAO.searchId", vo);
    }

    /**
     * 비밀번호를 찾는다.
     * 
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    public LoginVO searchPassword(LoginVO vo) throws Exception {

        return (LoginVO) selectOne("UserLoginDAO.searchPassword", vo);
    }

    /**
     * 변경된 비밀번호를 저장한다.
     * 
     * @param vo
     *            LoginVO
     * @exception Exception
     */
    public void updatePassword(LoginVO vo) throws Exception {
        update("UserLoginDAO.updatePassword", vo);
    }

    /**
     * 로그인인증제한 내역을 조회한다.
     * 
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    public Map<?, ?> selectLoginIncorrect(LoginVO vo) throws Exception {
        return (Map<?, ?>) selectOne("UserLoginDAO.selectLoginIncorrect", vo);
    }

    /**
     * SNS ID 기준 로그인인증제한 내역을 조회한다.
     * 
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    public Map<?, ?> selectSnsLoginIncorrect(LoginVO vo) throws Exception {
        return (Map<?, ?>) selectOne("UserLoginDAO.selectSnsLoginIncorrect", vo);
    }

    /**
     * 로그인인증제한 내역을 업데이트 한다.
     * 
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    public void updateLoginIncorrect(Map<?, ?> map) throws Exception {
        update("UserLoginDAO.updateLoginIncorrect", map);
    }

    /**
     * SNS 연결 정보를 조회한다
     * 
     * @param vo
     *            LoginVO
     * @return Map
     * @exception Exception
     */
    public CamelMap selectSnsKind(LoginVO vo) throws Exception {
        return (CamelMap) selectOne("UserLoginDAO.selectSnsKind", vo);
    }

    /**
     * SNS 연결정보를 등록한다
     * 
     * @param map
     * @return
     * @exception Exception
     */
    public void insertSnsConnection(Map<?, ?> map) throws Exception {
        insert("UserLoginDAO.insertSnsConnection", map);
    }

    /**
     * SNS 연결정보를 취소한다
     * 
     * @param map
     * @return
     * @exception Exception
     */
    public void updateSnsConnection(Map<?, ?> map) throws Exception {
        update("UserLoginDAO.updateSnsConnection", map);
    }

    /**
     * SNS 연결 여부를 수정한다
     * 
     * @param map
     * @return
     * @exception Exception
     */
    public void updateSnsRegistStatus(Map<?, ?> map) throws Exception {
        update("MyInforDAO.updateMemberSnsRegist", map);
    }

    /**
     * 이름 / 이메일로 아이디를 조회한다
     * 
     * @param vo
     *            LoginVO
     * @return Map
     * @exception Exception
     */
    public LoginVO selectFindId(LoginVO vo) throws Exception {
        return (LoginVO) selectOne("UserLoginDAO.selectFindId", vo);
    }

    /**
     * 이름 / 이메일로 아이디를 조회한다
     * 
     * @param vo
     *            LoginVO
     * @return Map
     * @exception Exception
     */
    public LoginVO selectFindIdByCerti(LoginVO vo) throws Exception {
        return (LoginVO) selectOne("UserLoginDAO.selectFindIdByCerti", vo);
    }

    /**
     * 로그인날짜 수정
     * 
     * @param map
     * @return
     * @exception Exception
     */
    public void updateLastLogin(Map<?, ?> map) throws Exception {
        update("UserLoginDAO.updateLastLogin", map);
    }

}