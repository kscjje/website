package com.hisco.user.mypage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.vo.NamefactVO;
import com.hisco.user.member.service.impl.UserLoginDAO;
import com.hisco.user.member.vo.MemberCarVO;
import com.hisco.user.member.vo.MemberInstVO;
import com.hisco.user.member.vo.MemberSnsVO;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.mapper.MyInforMapper;
import com.hisco.user.mypage.mapper.MyRsvnMapper;

import egovframework.com.cmm.LoginVO;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;

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
@Service("myInforService")
public class MyInforService extends EgovAbstractServiceImpl {

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    @Resource(name = "myRsvnMapper")
    private MyRsvnMapper myRsvnMapper;

    @Resource(name = "myInforMapper")
    private MyInforMapper myInforMapper;

    @Resource(name = "userLoginDAO")
    private UserLoginDAO loginDAO;

    @Resource(name = "egovEnvCryptoService")
    private EgovEnvCryptoService cryptoService;

    /**
     * 로그인 정보로 회원 데이타를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberVO selectMemberData(LoginVO vo) throws Exception {
        return (MemberVO) myInforMapper.selectMemberData(vo);
    }

    /**
     * 로그인 정보로 sns 계정연결 정보를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return List<MemberSnsVO>
     * @exception Exception
     */
    public List<MemberSnsVO> selectSnsCnncList(LoginVO vo) throws Exception {
        return (List<MemberSnsVO>) myInforMapper.selectSnsCnncList(vo);
    }

    /**
     * 로그인 정보로 관심분야 정보를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return List<MemberInstVO>
     * @exception Exception
     */
    public List<MemberInstVO> selectIntrstList(LoginVO vo) throws Exception {
        return (List<MemberInstVO>) myInforMapper.selectIntrstList(vo);
    }

    /**
     * 회원정보를 수정한다
     *
     * @param vo
     *            MemberVO
     * @return int
     * @exception Exception
     */
    public int updateMemberData(MemberVO vo) throws Exception {
        // 관리자에서 수정하는 경우 생일 정보도 수정할 수 있다
        if (vo.getBirthDate() != null && !"".equals(vo.getBirthDate())) {

            vo.setHp(vo.getHp().replaceAll("-", ""));
            vo.setBirthDate(vo.getBirthDate().replaceAll("-", ""));
            String dupcheckKey = vo.getMemNm() + vo.getBirthDate() + vo.getHp();
            vo.setBirthMmdd(vo.getBirthDate().substring(4));

            String ssn = vo.getBirthDate().substring(2) + (vo.getGender().equals("1") ? "1" : "2");
            if (vo.getBirthDate().startsWith("2")) {
                ssn = vo.getBirthDate().substring(2) + (vo.getGender().equals("2") ? "3" : "4");
            }
            vo.setSsn(ssn);
            vo.setDupcheckKey(dupcheckKey);
        }

        int cnt = myInforMapper.updateMemberData(vo);

        return cnt;
    }

    /**
     * 본인인증 정보로 회원 코드를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return String
     * @exception Exception
     */
    public MemberVO selectMemberSearchByName(NamefactVO vo) throws Exception {
        return (MemberVO) myInforMapper.selectMemberSearchByName(vo);
    }

    /**
     * 본인인증 정보로 회원 코드를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return String
     * @exception Exception
     */
    public MemberVO selectMemberSearchById(NamefactVO vo) throws Exception {
        return (MemberVO) myInforMapper.selectMemberSearchById(vo);
    }

    /**
     * 본인인증 정보로 회원 코드를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return String
     * @exception Exception
     */
    public MemberVO selectMemberSearchNewById(Map<String, Object> paramMap) throws Exception {
        return (MemberVO) myInforMapper.selectMemberSearchNewById(paramMap);
    }

    /**
     * 회원 비밀번호를 를 수정한다
     *
     * @param vo
     *            LoginVO
     * @return int
     * @exception Exception
     */
    public int updateMemberPassword(LoginVO vo) throws Exception {
        int cnt = 0;

        if ("0000".equals(vo.getStatus())) {
            cnt = myInforMapper.updateMemberPassword(vo);
        } else {
            // 휴면계정 비번 수정
            cnt = myInforMapper.updateMemberPassword2(vo);
            // 잠금 풀기
            Map<String, String> mapParam = new HashMap<String, String>();
            mapParam.put("id", EgovStringUtil.isNullToString(vo.getId()));
            mapParam.put("uniqId", EgovStringUtil.isNullToString(vo.getUniqId()));
            mapParam.put("updateAt", "E");
            loginDAO.updateLoginIncorrect(mapParam);

        }
        return cnt;
    }
    
    /**
     * 회원의 개인정보및이용약관동의여부를 갱신한다
     *
     * @param vo
     *            LoginVO
     * @return int
     * @exception Exception
     */
    public int memberReAgreeProc(LoginVO vo) throws Exception {
    	int cnt = 0;
    	System.out.println("memberReAgreeProc()");
    	//cnt = myInforMapper.updateMemberPassword(vo);
    	cnt = myInforMapper.memberReAgreeProc(vo);
    	return cnt;
    }

    /**
     * 로그인 정보로 차량 정보를조회한다
     *
     * @param vo
     *            LoginVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberCarVO selectMemberCarData(LoginVO vo) throws Exception {
        return (MemberCarVO) myInforMapper.selectMemberCarData(vo);
    }

    /**
     * 회원 차량정보를 등록한다
     *
     * @param vo
     *            MemberCarVO
     * @return int
     * @exception Exception
     */
    public int insertMemberCarinfo(MemberCarVO vo) throws Exception {
        // 당일 등록 데이타 확인
        int cnt = (Integer) myInforMapper.selectMemberCarCheck(vo);

        if (cnt > 0) {
            cnt = myInforMapper.updateMemberCarinfo(vo);
        } else {
            // 기존 차량 사용안함으로 수정
            myInforMapper.updateMemberCarinfoUseN(vo);
            // 신규입력
            cnt = myInforMapper.insertMemberCarinfo(vo);
        }

        return cnt;
    }

    /**
     * 회원 탈퇴 조건 조회
     *
     * @param vo
     *            MemberVO
     * @return int
     * @exception Exception
     */
    public String selectMemberCurrent(LoginVO vo) throws Exception {
        MemberVO memberVO = (MemberVO) myInforMapper.selectMemberData(vo);
        String msg = "OK";

        if (memberVO == null) {
            msg = "회원정보가 없습니다.";
        } else if ("Y".equals(memberVO.getSpecialYn())) {
            msg = "특별 회원은 탈퇴하실 수 없습니다.";
        } else if ("Y".equals(memberVO.getYearYn())) {
            msg = "유료회원 기간 만료 후 탈퇴하실 수 있습니다.<br>유료회원 만료일 : " + CommonUtil.getDateFormat(memberVO.getAnlmbEdate(), "yyyyMMdd", "yyyy.MM.dd");
        } else {
        	/*
            int cnt = (Integer) commonDAO.queryForObject("DspyDsDAO.selectMyReserveCnt", vo); // 관람 예약 내역
            int cnt2 = (Integer) commonDAO.queryForObject("EvtrsvnSMainDAO.selectMyReserveCnt", vo); // 강연,행사,영화 예약 내역
            int cnt3 = (Integer) myRsvnMapper.selectEdcReserveCnt(vo); // 교육 예약 내역
            if (cnt > 0) {
                msg = "아직 진행중인 예약 내역이 있습니다.<br/>관람일 다음날 부터 회원탈퇴 하실 수 있습니다.";
            } else if (cnt2 > 0) {
                msg = "아직 진행중인 예약 내역이 있습니다.<br/>관람일 다음날 부터 회원탈퇴 하실 수 있습니다.";
            } else if (cnt3 > 0) {
                msg = "교육프로그램 이용 내역이 있습니다.<br/>이용하시는 교육프로그램 교육 종료일 다음날 부터 탈퇴 할 수 있습니다.";
            }
            */
        }

        return msg;
    }

    /**
     * 회원 탈퇴처리
     *
     * @param vo
     *            MemberVO
     * @return int
     * @exception Exception
     */
    public int updateMemberOut(MemberVO vo) throws Exception {
        int cnt = myInforMapper.updateMemberOut(vo);
        myInforMapper.deleteMemberSNS(vo);
        myInforMapper.deleteMemberCAR(vo);
        myInforMapper.deleteMemberCARD(vo);

        return cnt;
    }

    /**
     * 개인정보 재동의
     *
     * @param vo
     *            LoginVO
     * @return int
     * @exception Exception
     */
    public int updateMemberRegree(LoginVO vo) throws Exception {
        int cnt = myInforMapper.updateMemberRegree(vo);

        return cnt;
    }

    /**
     * 본인인증 정보로 기존 회원정보 일치여부 체크
     *
     * @param vo
     *            NamefactVO
     * @return String
     * @exception Exception
     */
    public MemberVO selectOldMemberSearch(NamefactVO vo) throws Exception {
        return (MemberVO) myInforMapper.selectOldMemberSearch(vo);
    }

    /**
     * 기존 회원정보를 수정한다
     *
     * @param vo
     *            MemberVO
     * @return int
     * @exception Exception
     */
    public int updateMemberDataOld(MemberVO vo) throws Exception {
        int cnt = myInforMapper.updateMemberDataOld(vo);
        // 관심분야 삭제
        myInforMapper.deleteMemberIntrst(vo);
        // 관심분야 등록
        MemberInstVO instVO = vo.getInstVO();
        if (instVO != null) {
            String intrstKind = instVO.getIntrstKind();
            if (intrstKind != null) {
                for (String kind : intrstKind.split(",")) {
                    MemberInstVO tmpInst = new MemberInstVO();
                    tmpInst.setIntrstKind(kind);
                    tmpInst.setMemNo(vo.getMemNo());
                    tmpInst.setReguser(vo.getMemNo());

                    myInforMapper.insertMemberIntrst(tmpInst);
                }
            }
        }
        LoginVO loginVo = new LoginVO();
        loginVo.setUniqId(vo.getMemNo());
        // 휴면 계정 삭제
        commonDAO.getExecuteResult("UserLoginDAO.deleteDormantMember", loginVo);

        int cardCnt = (Integer) commonDAO.queryForObject("UserJoinDAO.selectMemCardCount", vo);
        // 회원카드 등록
        if (cardCnt < 1) {
            commonDAO.getExecuteResult("UserJoinDAO.insertMemCard", vo);
        }

        return cnt;
    }

    /**
     * 이메일 정보를 수정한다
     *
     * @param vo
     *            MemberVO
     * @return int
     * @exception Exception
     */
    public int updateMemberEmail(MemberVO vo, String edcRsvnReqid) throws Exception {
        if (edcRsvnReqid != null && !edcRsvnReqid.equals("")) {
            HashMap<String, String> param = new HashMap<String, String>();
            param.put("edcEmail", vo.getEmail());
            param.put("edcRsvnReqid", edcRsvnReqid);
            param.put("dbEncKey", vo.getDbEncKey());
            param.put("comcd", vo.getComcd());
            commonDAO.getExecuteResult("EdcarsvnDAO.updateEduRsvnEmail", param);
        }

        return myInforMapper.updateMemberEmail(vo);
    }

    /**
     * 본인인증 정보로 회원 코드를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return String
     * @exception Exception
     */
    public MemberVO selectMemberSearchByMemNo(Map<String, Object> paramMap) throws Exception {
        return (MemberVO) myInforMapper.selectMemberSearchByMemNo(paramMap);
    }

}
