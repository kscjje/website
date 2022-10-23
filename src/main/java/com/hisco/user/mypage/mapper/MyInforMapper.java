package com.hisco.user.mypage.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hisco.cmm.vo.NamefactVO;
import com.hisco.user.member.vo.MemberCarVO;
import com.hisco.user.member.vo.MemberInstVO;
import com.hisco.user.member.vo.MemberSnsVO;
import com.hisco.user.member.vo.MemberVO;

import egovframework.com.cmm.LoginVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 회원정보 수정 처리 구현 클래스
 *
 * @author 진수진
 * @since 2020.08.19
 * @version 1.0, 2020.08.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.08.19 최초작성
 */
@Mapper("myInforMapper")
public interface MyInforMapper {

    /**
     * 로그인 정보로 회원 데이타를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberVO selectMemberData(LoginVO vo);

    /**
     * 로그인 정보로 sns 계정연결 정보를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return List<MemberSnsVO>
     * @exception Exception
     */
    public List<MemberSnsVO> selectSnsCnncList(LoginVO vo);

    /**
     * 로그인 정보로 관심분야 정보를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return List<MemberInstVO>
     * @exception Exception
     */
    public List<MemberInstVO> selectIntrstList(LoginVO vo);;

    /**
     * 회원정보를 수정한다
     *
     * @param vo
     *            MemberVO
     * @return int
     * @exception Exception
     */
    public int updateMemberData(MemberVO vo);

    /**
     * 본인인증 정보로 회원 코드를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return String
     * @exception Exception
     */
    public MemberVO selectMemberSearchByName(NamefactVO vo);

    /**
     * 본인인증 정보로 회원 코드를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return String
     * @exception Exception
     */
    public MemberVO selectMemberSearchById(NamefactVO vo);

    /**
     * 본인인증 정보로 회원 코드를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return String
     * @exception Exception
     */
    public MemberVO selectMemberSearchNewById(Map<String, Object> paramMap);

    /**
     * 회원 비밀번호를 를 수정한다
     *
     * @param vo
     *            LoginVO
     * @return int
     * @exception Exception
     */
    public int updateMemberPassword(LoginVO vo);

    public int updateMemberPassword2(LoginVO vo);

    /**
     * 로그인 정보로 차량 정보를조회한다
     *
     * @param vo
     *            LoginVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberCarVO selectMemberCarData(LoginVO vo);

    /**
     * 회원 차량정보를 등록한다
     *
     * @param vo
     *            MemberCarVO
     * @return int
     * @exception Exception
     */
    public int selectMemberCarCheck(MemberCarVO vo);

    public int updateMemberCarinfo(MemberCarVO vo);

    public int updateMemberCarinfoUseN(MemberCarVO vo);

    public int insertMemberCarinfo(MemberCarVO vo);

    /**
     * 회원 탈퇴처리
     *
     * @param vo
     *            MemberVO
     * @return int
     * @exception Exception
     */
    public int updateMemberOut(MemberVO vo);

    public int deleteMemberSNS(MemberVO vo);

    public int deleteMemberCAR(MemberVO vo);

    public int deleteMemberCARD(MemberVO vo);

    /**
     * 개인정보 재동의
     *
     * @param vo
     *            LoginVO
     * @return int
     * @exception Exception
     */
    public int updateMemberRegree(LoginVO vo);

    /**
     * 본인인증 정보로 기존 회원정보 일치여부 체크
     *
     * @param vo
     *            NamefactVO
     * @return String
     * @exception Exception
     */
    public MemberVO selectOldMemberSearch(NamefactVO vo);

    /**
     * 기존 회원정보를 수정한다
     *
     * @param vo
     *            MemberVO
     * @return int
     * @exception Exception
     */
    public int updateMemberDataOld(MemberVO vo);

    public int deleteMemberIntrst(MemberVO vo);

    public int insertMemberIntrst(MemberInstVO tmpInst);

    /**
     * 이메일 정보를 수정한다
     *
     * @param vo
     *            MemberVO
     * @return int
     * @exception Exception
     */

    public int updateMemberEmail(MemberVO vo);

    /**
     * 본인인증 정보로 회원 코드를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return String
     * @exception Exception
     */
    public MemberVO selectMemberSearchByMemNo(Map<String, Object> paramMap);
    
    
    /**
     * 회원의 개인정보및이용약관동의여부를 갱신한다
     *
     * @param vo
     *            LoginVO
     * @return int
     * @exception Exception
     */
    public int memberReAgreeProc(LoginVO vo);


}
