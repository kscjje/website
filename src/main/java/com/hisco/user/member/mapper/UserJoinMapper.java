package com.hisco.user.member.mapper;

import java.util.List;
import java.util.Map;

import com.hisco.admin.terms.vo.TermsVO;
import com.hisco.user.member.vo.MemberCarVO;
import com.hisco.user.member.vo.MemberSnsVO;
import com.hisco.user.member.vo.MemberVO;

import egovframework.com.uss.ion.bnr.service.BannerVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 카테고리 관리 Service 구현 클래스
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 */
@Mapper("userJoinMapper")
public interface UserJoinMapper {

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
     * 아이디 중복을 체크한다.
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberVO selectMemberDetail(MemberVO vo);


    /**
     * 아이디 중복을 체크한다.
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberVO selectMemberByAuthkey(MemberVO vo);

    /**
     * 이름/핸드폰/생년월일 중복 체크한다.
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberVO selectMemberDetailOff(MemberVO vo);

    /**
     * 이메일 중복을 체크한다.
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public int selectMemberEmailCheck(MemberVO vo);

    /**
     * SNS 사용여부를 체크한다.
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberSnsVO selectSnsDetail(MemberSnsVO vo);

    /**
     * 차량번호 중복을 체크한다.
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberCarVO selectCarDetail(MemberCarVO vo);

    /**
     * 배너정보를 조회한다.
     *
     * @param vo
     *            BannerVO
     * @return list
     * @exception Exception
     */
    public List<BannerVO> selectBannerList(BannerVO vo);

    /**
     * 다음 회원번호 가져오기
     *
     * @return String
     * @exception Exception
     */
    public String selectSeqNextval();

    /**
     * 회원정보 입력
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public int insertMemberDetail(MemberVO vo);

    /**
     * 회원카드 입력
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public int insertMemCard(MemberVO vo);

    /**
     * 비밀번호찾기 회원정보 조회
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberVO selectFindPasswd(MemberVO vo);

    /**
     * 비밀번호찾기 회원정보 조회
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public List<?> selectMemberByCertiId(Map<String, Object> paramMap);

    /**
     * 본인인증 정보로 회원정보 조회
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberVO selectMemberSearchByName(Map<String, String> param);

    /**
     * 중복키로 회원가입 여부 체크
     *
     * @param vo
     *            MemberVO
     * @return int
     * @exception Exception
     */
    public int selectMemberDupCheck(MemberVO vo);
    
    /**
    * 자식, 부모 같은 인증키값을 가지고 있는 회원정보들을조회한다
    *
    * @param vo
    *            MemberVO
    * @return MemberVO
    * @exception Exception
    */
    public List<?> findmemberList(MemberVO memberVo);

}
