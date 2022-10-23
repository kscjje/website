package com.hisco.user.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hisco.admin.terms.vo.TermsVO;
import com.hisco.cmm.util.Config;
import com.hisco.user.member.mapper.UserJoinMapper;
import com.hisco.user.member.vo.MemberCarVO;
import com.hisco.user.member.vo.MemberInstVO;
import com.hisco.user.member.vo.MemberSnsVO;
import com.hisco.user.member.vo.MemberVO;

import egovframework.com.cmm.config.EgovLoginConfig;
import egovframework.com.uss.ion.bnr.service.BannerVO;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

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
 *          진수진 2021.10.19 DAO -> mapper 로 변경
 */
@Slf4j
@Service("userJoinService")
public class UserJoinService extends EgovAbstractServiceImpl {

    @Resource(name = "egovLoginConfig")
    EgovLoginConfig egovLoginConfig;

    @Resource(name = "userJoinMapper")
    private UserJoinMapper userJoinMapper;

    /**
     * 약관 목록을 조회한다
     *
     * @param vo
     *            TermsVO
     * @return List
     * @exception Exception
     */
    public List<?> selectTermsList(TermsVO vo) throws Exception {
        return userJoinMapper.selectTermsList(vo);
    }

    /**
     * 아이디 중복을 체크한다.
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberVO selectMemberDetail(MemberVO vo) throws Exception {
        return userJoinMapper.selectMemberDetail(vo);
    }

    public MemberVO selectMemberByAuthkey(MemberVO vo){
    	 return userJoinMapper.selectMemberByAuthkey(vo);
    }

    public MemberVO selectMemberDetailOff(MemberVO vo) throws Exception {
        return userJoinMapper.selectMemberDetailOff(vo);
    }

    /**
     * 이메일 중복을 체크한다.
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public int selectMemberEmailCheck(MemberVO vo) throws Exception {
        return userJoinMapper.selectMemberEmailCheck(vo);
    }

    /**
     * SNS 사용여부를 체크한다.
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberSnsVO selectSnsDetail(MemberSnsVO vo) throws Exception {
        return userJoinMapper.selectSnsDetail(vo);
    }

    /**
     * 차량번호 중복을 체크한다.
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberCarVO selectCarDetail(MemberCarVO vo) throws Exception {
        return userJoinMapper.selectCarDetail(vo);
    }

    /**
     * 배너정보를 조회한다.
     *
     * @param vo
     *            BannerVO
     * @return list
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<BannerVO> selectBannerList(BannerVO vo) throws Exception {
        return userJoinMapper.selectBannerList(vo);
    }

    /**
     * 회원가입 저장
     *
     * @param vo
     *            MemberVO
     * @return Map
     * @exception Exception
     */
    @Transactional
    public Map<String, Object> insertMemberInfo(MemberVO memberVO) throws Exception {

        Map<String, Object> returnMap = new HashMap<String, Object>();
        MemberInstVO ivo = memberVO.getInstVO();
        MemberCarVO cvo = memberVO.getCarVO();
        MemberSnsVO svo = memberVO.getSnsVO();

        // 패스워드 암호화
        /*
        String enpassword = EgovFileScrty.encryptPassword(memberVO.getPw(), memberVO.getId());
        memberVO.setPw(enpassword);
        */

        // 아이디 유무 확인
        MemberVO memberVO2 = userJoinMapper.selectMemberDetail(memberVO);

        // 본인인증 정보 중복 확인
        Map<String, String> param = new HashMap<String, String>();

        param.put("name", memberVO.getMemNm());
        param.put("crc_data_di", memberVO.getPiAuthkey());
        if(memberVO.getPiAuthkey() == null || "".equals(memberVO.getPiAuthkey())) {
        	param.put("crc_data_di", memberVO.getPiPAuthkey());
        }
        

        MemberVO memberVO3 = userJoinMapper.selectMemberSearchByName(param);
        
        
        boolean hasAuthkey = false;
        
        if(memberVO.getPiAuthkey() != null && !"".equals(memberVO.getPiAuthkey())) {
        	hasAuthkey = true;
        }
        
        if(memberVO.getPiPAuthkey() != null && !"".equals(memberVO.getPiPAuthkey())) {
        	hasAuthkey = true;
        }
        
        if( !hasAuthkey) {
        	returnMap.put("result", Config.FAIL);
            returnMap.put("resultMsg", "인증정보가 없습니다.");
        } else if (memberVO2 != null) {
            returnMap.put("result", Config.FAIL);
            returnMap.put("resultMsg", "이미 존재 하는 아이디입니다.");
        } else if (memberVO3 != null) {
            returnMap.put("result", Config.FAIL);
            returnMap.put("resultMsg", "이미 가입된 회원입니다.");
        } else {
            String memNo = userJoinMapper.selectSeqNextval();
            memberVO.setMemNo(memNo);
            // 입력
            if (svo != null && !StringUtils.isEmpty(svo.getSnsid())) {
                memberVO.setSnsRegistyn("Y");
            } else {
                memberVO.setSnsRegistyn("N");
            }
            memberVO.setBirthDate(memberVO.getBirthDate().replaceAll("-", ""));
            memberVO.setBirthMmdd(memberVO.getBirthDate().substring(4));
            String ssn = memberVO.getBirthDate().substring(2) + (memberVO.getGender().equals("M") ? "1" : "2");
            if (memberVO.getBirthDate().startsWith("2")) {
                ssn = memberVO.getBirthDate().substring(2) + (memberVO.getGender().equals("M") ? "3" : "4");
            }
            memberVO.setSsn(ssn);

            userJoinMapper.insertMemberDetail(memberVO);
            userJoinMapper.insertMemCard(memberVO); // 회원 카드

            /*
             * //차량정보
             * if (!StringUtils.isEmpty(cvo.getCarNo())) {
             * cvo.setReguser(memberVO.getId());
             * cvo.setMemNo(memNo);
             * cvo.setComcd(Config.COM_CD);
             * commonDAO.getExecuteResult("UserJoinDAO.insertCarInfo", cvo);
             * }
             * //관심정보
             * if (ivo != null && !StringUtils.isEmpty(ivo.getIntrstKind())) {
             * MemberInstVO ivo2 = new MemberInstVO();
             * ivo2.setReguser(memberVO.getId());
             * ivo2.setMemNo(memNo);
             * String[] inst = ivo.getIntrstKind().split(",");
             * for (String ins : inst) {
             * ivo2.setIntrstKind(ins);
             * commonDAO.getExecuteResult("UserJoinDAO.insertInstInfo", ivo2);
             * }
             * }
             * //sns정보
             * if (svo != null && !StringUtils.isEmpty(svo.getSnsid())) {
             * svo.setMemNo(memNo);
             * commonDAO.getExecuteResult("UserJoinDAO.insertSnsInfo", svo);
             * }
             */

            returnMap.put("result", Config.SUCCESS);
        }

        return returnMap;
    }

    /**
     * 비밀번호찾기 회원정보 조회
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public MemberVO selectFindPasswd(MemberVO vo) throws Exception {
        return userJoinMapper.selectFindPasswd(vo);
    }

    /**
     * 회원정보조회
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public List<?> selectMemberByCertiId(Map<String, Object> paramMap) throws Exception {
        return userJoinMapper.selectMemberByCertiId(paramMap);
    }
    
    /**
     * 자식, 부모 같은 인증키값을 가지고 있는 회원정보들을조회한다
     *
     * @param vo
     *            MemberVO
     * @return MemberVO
     * @exception Exception
     */
    public List<?> findmemberList(MemberVO memberVo) throws Exception {
        return userJoinMapper.findmemberList(memberVo);
    	//return null;
    }

}
