package com.hisco.user.member.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.user.member.service.MemYearService;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.member.vo.MemberYearVO;
import com.hisco.user.mypage.vo.MyRsvnVO;

import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 연간회원 관련 구현 service
 * 
 * @author 진수진
 * @since 2020.09.08
 * @version 1.0, 2020.09.08
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.09.08 최초생성
 */
@Slf4j
@Service("memYearService")
public class MemYearServiceImpl extends EgovAbstractServiceImpl implements MemYearService {

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    /**
     * 연간회원 상품을 조회한다
     * 
     * @param vo
     *            Map
     * @return MemberYearVO
     * @exception Exception
     */
    public MemberYearVO selectProgramItemData(Map<?, ?> vo) throws Exception {
        return (MemberYearVO) commonDAO.queryForObject("MemYearDAO.selectProgramItemData", vo);
    }

    /**
     * 연회원 결제 후 저장한다
     *
     * @param Map
     * @throws Exception
     */
    public boolean insertMemberYear(Map<String, Object> paramMap, MemberYearVO vo, MyRsvnVO myRsvnVO) throws Exception {

        log.debug("insertMemberYear :: paramMap 1 => " + paramMap);

        myRsvnVO.setRetCd("0000"); // 데이터 처리전 상태값
        int n = 0;

        if (myRsvnVO.getMemYearSaleamt() > 0) {
            // 카드 사별 수수료 구한다
            CamelMap map = (CamelMap) commonDAO.queryForObject("ChargeDAO.selectPaymethodRate", paramMap);

            if (map != null && map.get("CARD_RATE") != null) {
                double rate = (double) map.get("CARD_RATE");
                double rateAmt = Math.round(Double.parseDouble((String) paramMap.get("LGD_AMOUNT")) * rate) / 100.0;
                paramMap.put("CARDANDBANK_RATE_AMT", Double.toString(rateAmt)); // 수수료금액
                paramMap.put("CARDANDBANK_RATE", Double.toString(rate)); // 수수료율
            } else {
                paramMap.put("CARDANDBANK_RATE_AMT", "0"); // 수수료금액
                paramMap.put("CARDANDBANK_RATE", "0"); // 수수료율
            }

            log.debug("insertMemberYear :: paramMap 2 => " + paramMap);

            // PG 거래 승인내역을 저장한다
            n = commonDAO.getExecuteResult("ChargeDAO.insertCardAppHistory", paramMap);
        } else {
            n = 1;
        }

        if (n > 0) {
            // 연간회원 이력 저장
            vo.setReguser((String) paramMap.get("REGUSER"));

            n = commonDAO.getExecuteResult("MemYearDAO.insertMemYearHist", vo);
            int cardCnt = (Integer) commonDAO.queryForObject("UserJoinDAO.selectMemCardCount", vo);

            /*
             * 노원결제없음 막음 JYS 2021.03.17
             * //회원카드 등록
             * if (cardCnt < 1) {
             * n = commonDAO.getExecuteResult("UserJoinDAO.insertMemCard", vo);
             * }
             * if (n > 0) {
             * myRsvnVO.setComcd(vo.getComcd());
             * myRsvnVO.setLgdBUYERID(vo.getLgdBUYERID());
             * myRsvnVO.setLgdOID(vo.getLgdOID());
             * myRsvnVO.setMemYearSeq(String.valueOf(vo.getMemYearSeq()));
             * myRsvnVO.setPartGbn(vo.getPartGbn());
             * myRsvnVO.setTerminalType((String)paramMap.get("TERMINAL_TYPE"));
             * commonDAO.getExecuteResult("ChargeDAO.insertPaymentProc", myRsvnVO);
             * if ("OK".equals(myRsvnVO.getRetCd())) {
             * n = 1;
             * } else {
             * n = 0;
             * }
             * }
             */

            n = 1; // 하드 코딩 JYS 2021.03.17

        }

        return n > 0;
    }

    /**
     * 연간회원 가입 정보를 조회한다
     * 
     * @return MemberYearVO
     * @exception Exception
     */
    public MemberYearVO selectMemYearData(MemberYearVO vo) throws Exception {
        return (MemberYearVO) commonDAO.queryForObject("MemYearDAO.selectMemYearData", vo);
    }

    /**
     * 연간회원 마지막 가입 정보를 조회한다
     * 
     * @return MemberYearVO
     * @exception Exception
     */
    public MemberYearVO selectMemYearResult(MemberYearVO vo) throws Exception {
        return (MemberYearVO) commonDAO.queryForObject("MemYearDAO.selectMemYearResult", vo);
    }

    /**
     * 연간회원 재가입 할인율 조회
     * 
     * @return CamelMap
     * @exception Exception
     */
    public CamelMap selectDiscountValue(MemberYearVO vo) throws Exception {
        return (CamelMap) commonDAO.queryForObject("MemYearDAO.selectDiscountValue", vo);
    }

    /**
     * 오늘날짜 조회
     * 
     * @return String
     * @exception Exception
     */
    public String selectToday() throws Exception {
        return (String) commonDAO.queryForObject("MemYearDAO.selectToday", null);
    }

    /**
     * 가족회원조회
     * 
     * @param vo
     *            MemberVO
     * @return map
     * @exception Exception
     */
    public CamelMap selectFamilyMember(MemberVO vo) throws Exception {
        return (CamelMap) commonDAO.queryForObject("UserJoinDAO.selectFamilyMember", vo);
    }

    /**
     * 가족유료회원 정보 입력
     * 
     * @param vo
     *            MemberYearVO
     * @return int
     * @exception Exception
     */
    public String insertFamilyMember(MemberVO meVO, MemberVO vo, String oid, String terminalType) throws Exception {
        // 대표자 정보 조회
        CamelMap map = (CamelMap) commonDAO.queryForObject("MemYearDAO.selectFamilyMember", vo);
        long memberSeq = 0;

        if (map == null) {
            return "입력하신 정보와 일치하는 대표자 정보를 찾을 수 없습니다.";
        } else {
            int cnt = CommonUtil.getInt(map.get("cnt"));
            int familyCnt = CommonUtil.getInt(map.get("oldMemFamilycnt"));

            if (familyCnt < 1) {
                return "해당 회원은 가족 대표자로 등록되어 있지 않습니다.";
            } else if (meVO.getMemNm().equals(vo.getMemNm()) && meVO.getHp().equals(vo.getHp())) {
                return "가족대표자 회원분은 가족회원으로 등록이 불가합니다";
            } else if (cnt >= familyCnt) {
                return "기존 가족회원으로 등록된 인원수만큼 등록이 완료되었습니다";
            } else {
                int result = 0;
                Map<String, String> param = new HashMap<String, String>();
                param.put("comcd", vo.getComcd());
                MemberYearVO memberYearVO = (MemberYearVO) commonDAO.queryForObject("MemYearDAO.selectProgramItemData", param);

                if (memberYearVO == null) {
                    return "유료회원 상품정보가 없습니다.";
                } else {
                    memberYearVO.setComcd(Config.COM_CD);
                    memberYearVO.setMemNo(vo.getMemNo());
                    memberYearVO.setId(vo.getId());
                    memberYearVO.setReguser(vo.getId());

                    if (CommonUtil.getString(map.get("memGbn")).equals("2001")) {
                        // 특별회원
                        String startYmd = (String) commonDAO.queryForObject("MemYearDAO.selectToday", null);
                        memberYearVO.setRgistGbn("1001");
                        memberYearVO.setStartYmd(startYmd);
                        memberYearVO.setEndYmd(EgovDateUtil.addDay(EgovDateUtil.addMonth(startYmd, memberYearVO.getMonthCnt()), -1));
                        memberYearVO.setFamilyAddYn("Y");
                        memberYearVO.setFamilyMemNo(CommonUtil.getString(map.get("memNo")));

                        result = commonDAO.getExecuteResult("MemYearDAO.insertMemYearHist", memberYearVO);
                        memberSeq = memberYearVO.getMemYearSeq();

                    } else if (CommonUtil.getString(map.get("itemCd")).equals("")) {
                        return "해당 대표자는 유료회원으로 등록되어 있지 않습니다.";
                    } else {
                        memberYearVO.setFamilyMemNo(CommonUtil.getString(map.get("memNo")));
                        result = commonDAO.getExecuteResult("MemYearDAO.insertMemYearFamily", memberYearVO);
                        memberSeq = memberYearVO.getMemYearSeq();
                    }

                }

                if (result > 0) {
                    MyRsvnVO myRsvnVO = new MyRsvnVO();
                    myRsvnVO.setRetCd("0000"); // 데이터 처리전 상태값
                    myRsvnVO.setComcd(vo.getComcd());
                    myRsvnVO.setLgdBUYERID(vo.getId());
                    myRsvnVO.setLgdOID(oid);
                    myRsvnVO.setMemYearSeq(String.valueOf(memberSeq));
                    myRsvnVO.setPartGbn(memberYearVO.getPartGbn());
                    myRsvnVO.setTerminalType(terminalType);

                    /*
                     * 노원결제없음 막음 JYS 2021.03.17
                     * commonDAO.getExecuteResult("ChargeDAO.insertPaymentProc", myRsvnVO);
                     */

                    return "OK";
                } else {
                    return "등록된 데이타가 없습니다.";
                }
            }
        }
    }
}
