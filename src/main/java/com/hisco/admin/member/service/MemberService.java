package com.hisco.admin.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.member.mapper.MemberMapper;
import com.hisco.admin.member.vo.MemberUserVO;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.StringUtil;
import com.hisco.user.member.mapper.UserJoinMapper;
import com.hisco.user.member.vo.MemberVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import lombok.extern.slf4j.Slf4j;

/**
 * 회원관리 Service 구현 클래스
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 *          진수진 2021.10.18 패키지명 변경 WebMemberService -> MemberService
 */
@Slf4j
@Service("memberService")
public class MemberService extends EgovAbstractServiceImpl {

    @Resource(name = "memberMapper")
    private MemberMapper memberMapper;

    @Resource(name = "egovEnvCryptoService")
    private EgovEnvCryptoService cryptoService;

    @Resource(name = "userJoinMapper")
    private UserJoinMapper userJoinMapper;

    /**
     * 공통 코드 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectCotGrpCdList(Map<String, Object> paramMap) throws Exception {
        return memberMapper.selectCotGrpCdList(paramMap);
    }

    /**
     * 개인 교육 신청 현황 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectMemberList(MemberUserVO paramMap) throws Exception {
        return memberMapper.selectMemberList(paramMap);
    }

    public List<?> selectMemberList(Map<String, Object> paramMap) throws Exception {
        return memberMapper.selectMemberList(paramMap);
    }

    /**
     * 회원 상세 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public MemberUserVO selectMemberDetail(MemberUserVO paramMap) throws Exception {
        MemberUserVO memberVO = memberMapper.selectMemberDetail(paramMap);
        return memberVO;
    }

    /**
     * 회원 상세 정보를 조회한다.(ID로)
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public MemberUserVO selectMemberDetailById(MemberUserVO paramMap) throws Exception {
        MemberUserVO memberVO = memberMapper.selectMemberDetailById(paramMap);
        return memberVO;
    }

    /**
     * 회원가입 저장
     *
     * @param vo
     *            MemberVO
     * @return Map
     * @exception Exception
     */
    public Map<String, Object> insertMemberInfo(MemberVO memberVO) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 이름 / 생년월일 / 핸드폰 번호로 중복 key를 만든다
        memberVO.setBirthDate(memberVO.getBirthDate().replaceAll("-", ""));
        memberVO.setHp(memberVO.getHp().replaceAll("-", ""));
        String dupcheckKey = memberVO.getMemNm() + memberVO.getBirthDate() + memberVO.getHp();
        memberVO.setDupcheckKey(dupcheckKey);

        // 이름/핸드폰/생년월일 체크
        MemberVO memberVO2 = userJoinMapper.selectMemberDetailOff(memberVO);

        /*
        int cnt = userJoinMapper.selectMemberDupCheck(memberVO);
         */

        if (memberVO2 != null && !StringUtil.IsEmpty(memberVO2.getMemNo())) {
            returnMap.put("result", Config.FAIL);
            returnMap.put("resultMsg", "이름 / 생년월일 / 핸드폰 번호 가 같은 회원이 존재합니다.");
        } else {
            String memNo = userJoinMapper.selectSeqNextval();
            memberVO.setMemNo(memNo);
            memberVO.setSnsRegistyn("N");
            memberVO.setBirthMmdd(memberVO.getBirthDate().substring(4));

            String ssn = memberVO.getBirthDate().substring(2) + (memberVO.getGender().equals("M") ? "1" : "2");
            if (memberVO.getBirthDate().startsWith("2")) {
                ssn = memberVO.getBirthDate().substring(2) + (memberVO.getGender().equals("F") ? "3" : "4");
            }
            memberVO.setSsn(ssn);

            userJoinMapper.insertMemberDetail(memberVO);
            // 회원 카드
            userJoinMapper.insertMemCard(memberVO);

            returnMap.put("result", Config.SUCCESS);
        }

        return returnMap;
    }

}
