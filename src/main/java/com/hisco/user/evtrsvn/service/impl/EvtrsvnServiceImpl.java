package com.hisco.user.evtrsvn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.terms.vo.TermsVO;
import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.WebEncDecUtil;
import com.hisco.user.evtrsvn.service.EventProgramVO;
import com.hisco.user.evtrsvn.service.EvtItemAmountVO;
import com.hisco.user.evtrsvn.service.EvtRsvnItemVO;
import com.hisco.user.evtrsvn.service.EvtStdmngVO;
import com.hisco.user.evtrsvn.service.EvtrsvnMstVO;
import com.hisco.user.evtrsvn.service.EvtrsvnService;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.vo.MyRsvnVO;
import com.hisco.user.mypage.vo.RsvnCommVO;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 강연/행사/영화 예약 서비 구현
 * 
 * @author 진수진
 * @since 2020.09.01
 * @version 1.0, 2020.09.01
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          김희택 2020.09.01 최초작성
 */
@Slf4j
@Service("evtrsvnService")
public class EvtrsvnServiceImpl extends EgovAbstractServiceImpl implements EvtrsvnService {

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    //// @Resource(name = "smsService")
    //// private SmsService smsService;

    /**
     * 기준설정 데이타를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    public CamelMap selectProgramData(Map<?, ?> vo) throws Exception {
        return (CamelMap) commonDAO.queryForObject("EvtrsvnSMainDAO.selectProgramData", vo);
    }

    /**
     * 파일 아이디를 수정한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    public void updateProgramData(Map<?, ?> vo) throws Exception {
        commonDAO.getExecuteResult("EvtrsvnSMainDAO.updateProgramData", vo);
    }

    /**
     * 강연/행사/영화 요금정보를 가져온다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<EvtItemAmountVO> selectEvtChargeList(EvtStdmngVO vo) throws Exception {
        List<EvtItemAmountVO> cList = (List<EvtItemAmountVO>) commonDAO.queryForList("EvtrsvnSMainDAO.selectChrgList", vo);
        return cList;
    }

    /**
     * 예약 번호 가져오기
     * 
     * @param
     * @return String
     * @exception Exception
     */
    public String selectRsvnNumber() throws Exception {
        return (String) commonDAO.queryForObject("EvtrsvnSMainDAO.selectRsvnNumber", null);
    }

    /**
     * 강연,행사,영화 예약정보를 입력한다.
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> insertEvtrsvnInfo(MemberVO memberVO, EvtrsvnMstVO evtrsvnMstVO, EventProgramVO detailVO,
            MyRsvnVO myRsvnVO) throws Exception {

        // Declare
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<String, String> param = new HashMap<String, String>();

        int visitCnt = 0;
        long saleamt = 0;
        long payamt = 0;

        // set parameter
        evtrsvnMstVO.setEvtTime(evtrsvnMstVO.getEvtVeingdate());
        String dayWeek = (String) commonDAO.queryForObject("EvtrsvnSMainDAO.selectEvtTimeDay", evtrsvnMstVO);
        evtrsvnMstVO.setDayWeek(dayWeek);
        CamelMap timeVO = (CamelMap) commonDAO.queryForObject("EvtrsvnSMainDAO.selectEvtTimeList", evtrsvnMstVO);

        long totalCnt = 0;

        /*
         * JYS 2021.05.18
         * for (EvtItemAmountVO charge : evtrsvnMstVO.getChargeList() ) {
         * totalCnt += charge.getItemCnt();
         * }
         */

        int myCnt = commonDAO.queryForInt("EvtrsvnSMainDAO.selectEvtrsvnCnt", evtrsvnMstVO);

        if (timeVO == null) {
            resultMap.put("resultCd", Config.FAIL);
            resultMap.put("resultMsg", "회차 데이타가 없습니다. 선택 회차를 다시 확인해 주세요.");
        } else if (CommonUtil.getInt(timeVO.get("totCapa")) <= CommonUtil.getInt(timeVO.get("rsvCnt")) || !"Y".equals((String) timeVO.get("rsvAbleYn"))) {
            resultMap.put("resultCd", Config.FAIL);
            resultMap.put("resultMsg", "예약이 마감되었습니다.");

        } else if (CommonUtil.getInt(timeVO.get("totCapa")) < (CommonUtil.getInt(timeVO.get("rsvCnt")) + totalCnt)) {
            resultMap.put("resultCd", Config.FAIL);
            resultMap.put("resultMsg", "잔여정원을 초과하여 신청하실 수 없습니다.(잔여정원:" + (CommonUtil.getInt(timeVO.get("totCapa")) - CommonUtil.getInt(timeVO.get("rsvCnt"))) + "명)");
        } else if (detailVO.getEvtMaxtimeCnt() > 0 && myCnt >= detailVO.getEvtMaxtimeCnt()) {
            resultMap.put("resultCd", Config.FAIL);
            resultMap.put("resultMsg", "예약 가능 횟수 가 초과되었습니다. (최대  " + detailVO.getEvtMaxtimeCnt() + "회)");
        } else {
            // master sequence
            String rsvnIdx = (String) commonDAO.queryForObject("EvtrsvnSMainDAO.selectEvtSeq", null);

            log.debug("rsvnIdx = " + rsvnIdx);
            log.debug("getEvtVeingnmpr = " + evtrsvnMstVO.getEvtVeingnmpr());

            evtrsvnMstVO.setEvtRsvnIdx(rsvnIdx);
            evtrsvnMstVO.setEvtPartcd(detailVO.getEvtPartcd());

            // 이벤트 할인 여부 조회
            param.put("COMCD", Config.COM_CD);
            param.put("YMD", evtrsvnMstVO.getEvtVeingdate());
            param.put("PART_CD", evtrsvnMstVO.getEvtPartcd());
            param.put("PGM_CD", evtrsvnMstVO.getEvtNo());
            param.put("PGM_GUBUN", "EVT");
            RsvnCommVO discInfoVO = null;
            RsvnCommVO discAnnualVO = null;

            List<RsvnCommVO> discList = (List<RsvnCommVO>) commonDAO.queryForList("RsvnCommDAO.selectEventStdmngList", param);
            RsvnCommVO discOpt = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectOptData", param);

            if (discList != null) {
                for (RsvnCommVO discVO : discList) {
                    if (discInfoVO != null) {
                        // 이미 이벤트 할당됨
                        param.put("PGM_GUBUN", "EVT"); // 의미없음
                    } else if (evtrsvnMstVO.getEvtRsvnMemno() != null && !evtrsvnMstVO.getEvtRsvnMemno().equals("") && "Y".equals(discVO.getMemberyn())) {
                        discInfoVO = discVO;
                    } else if ((evtrsvnMstVO.getEvtRsvnMemno() == null || evtrsvnMstVO.getEvtRsvnMemno().equals("")) && "Y".equals(discVO.getNonmebyn())) {
                        discInfoVO = discVO;
                    }
                }
            }
            // 유료회원 이고 개인신청이면
            if (memberVO != null && "10".equals(evtrsvnMstVO.getEvtPersnGbn())) {
                if (memberVO.getSpecialYn().equals("Y") || memberVO.getYearYn().equals("Y")) {
                    MyRsvnVO rsvnVO = new MyRsvnVO();
                    rsvnVO.setComcd(Config.COM_CD);
                    rsvnVO.setUniqId(memberVO.getMemNo());
                    rsvnVO.setRsvnIdxOne("0");
                    rsvnVO.setGubun("EVT");
                    rsvnVO.setProgramCd(Integer.toString(detailVO.getEvtNo()));
                    if ("Y".equals(memberVO.getYearYn())) {
                        rsvnVO.setMemberGbn("1"); // 연간회원
                    } else {
                        rsvnVO.setMemberGbn("2"); // 특별회원
                    }
                    RsvnCommVO annualData = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectAnnualDcData", rsvnVO);
                    if (annualData != null && annualData.getLimitCnt() == 0 && (discInfoVO == null || discInfoVO.getDcRate() < annualData.getDcRate())) {
                        // 제한 없이
                        discAnnualVO = annualData;
                        evtrsvnMstVO.setDcAnnualLimit(annualData.getLimitQty());
                    }
                }
            }

            // 가격정보 select
            List<EvtItemAmountVO> chargeList = (List<EvtItemAmountVO>) commonDAO.queryForList("EvtrsvnSMainDAO.selectChrgList", evtrsvnMstVO);
            List<EvtItemAmountVO> paramCharge = null; // evtrsvnMstVO.getChargeList(); JYS 2021.05.18
            List<EvtRsvnItemVO> chargeDataList = new ArrayList<EvtRsvnItemVO>();

            int limitQty = evtrsvnMstVO.getDcAnnualLimit();
            int remainCnt = 0;

            /*
             * if (chargeList != null && paramCharge != null) {
             * for (EvtItemAmountVO paramVO : paramCharge) {
             * for (EvtItemAmountVO chargeVO : chargeList) {
             * if (paramVO.getItemCd().equals(chargeVO.getItemCd()) && paramVO.getItemCnt() > 0) {
             * EvtRsvnItemVO itemVO = new EvtRsvnItemVO();
             * itemVO.setEvtCost(chargeVO.getItemPrice());
             * itemVO.setComcd(Config.COM_CD);
             * itemVO.setEvtRsvnIdx(rsvnIdx);
             * itemVO.setEvtRsvnItemcd(paramVO.getItemCd());
             * visitCnt += paramVO.getItemCnt();
             * long amount = chargeVO.getItemPrice() * paramVO.getItemCnt();
             * long dcAmountLong = 0;
             * int orignCnt = Integer.parseInt(paramVO.getItemCnt()+"");
             * int cnt = orignCnt;
             * if (amount >0) {
             * // 단체 할인 적용
             * if ("20".equals(evtrsvnMstVO.getEvtPersnGbn()) && detailVO.getDcReasonCd() != null &&
             * detailVO.getDcRate()>0) {
             * if ( "Y".equals(chargeVO.getGroupdcyn())) {
             * itemVO.setEvtDcType(detailVO.getDcReasonCd());
             * itemVO.setEvtDcRate(detailVO.getDcRate());
             * dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO.getItemPrice()* detailVO.getDcRate() *
             * paramVO.getItemCnt() * 0.01 , discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
             * }
             * } else {
             * //유료회원 할인
             * if (discAnnualVO != null && discAnnualVO.getEventDcRate() > 0 && limitQty == 0) {
             * limitQty = 1000;
             * }
             * int annualCnt = limitQty - remainCnt;
             * //유료회원할인을 먹인다
             * if (annualCnt >= orignCnt) {
             * annualCnt = orignCnt;
             * cnt = 0;
             * } else if (annualCnt > 0) {
             * cnt = orignCnt - annualCnt;
             * }
             * //유료회원 할인
             * if (discAnnualVO != null && discAnnualVO.getEventDcRate() > 0 && annualCnt > 0) {
             * long dcAmountLong2 = CommonUtil.DoubleToLongCalc(chargeVO.getItemPrice() * discAnnualVO.getEventDcRate()
             * * 0.01 * annualCnt, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
             * EvtRsvnItemVO chargeVO2 = new EvtRsvnItemVO();
             * chargeVO2.setEvtCost(chargeVO.getItemPrice());
             * chargeVO2.setComcd(Config.COM_CD);
             * chargeVO2.setEvtRsvnIdx(rsvnIdx);
             * chargeVO2.setEvtRsvnItemcd(paramVO.getItemCd());
             * chargeVO2.setEvtSalamt(itemVO.getEvtCost() * annualCnt);
             * chargeVO2.setEvtRsvnItemcnt(annualCnt);
             * chargeVO2.setEvtDcamt(dcAmountLong2);
             * chargeVO2.setEvtTotamt(chargeVO2.getEvtSalamt() - dcAmountLong2);
             * chargeVO2.setEvtTerminalType(evtrsvnMstVO.getEvtTrmnltype());
             * chargeVO2.setReguser(evtrsvnMstVO.getReguser());
             * chargeVO2.setEvtOnoffIntype(evtrsvnMstVO.getEvtOnoffintype());
             * chargeVO2.setEvtDcType(discAnnualVO.getEventReasoncd());
             * chargeVO2.setEvtDcRate(discAnnualVO.getEventDcRate());
             * chargeDataList.add(chargeVO2);
             * payamt += chargeVO2.getEvtTotamt();
             * saleamt += chargeVO2.getEvtSalamt();
             * }
             * //-------유료회원 할인 끝
             * if (discInfoVO != null) {
             * itemVO.setEvtDcType(discInfoVO.getEventReasoncd());
             * itemVO.setEvtDcRate(discInfoVO.getEventDcRate());
             * itemVO.setEvtEventDcid(discInfoVO.getDcid());
             * dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO.getItemPrice() * discInfoVO.getEventDcRate() * cnt *
             * 0.01 , discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
             * }
             * remainCnt += orignCnt;
             * }
             * }
             * // 이벤트 할인
             * itemVO.setEvtSalamt(itemVO.getEvtCost() * cnt);
             * itemVO.setEvtRsvnItemcnt(cnt);
             * itemVO.setEvtDcamt(dcAmountLong);
             * itemVO.setEvtTotamt(itemVO.getEvtSalamt() - dcAmountLong);
             * itemVO.setEvtTerminalType(evtrsvnMstVO.getEvtTrmnltype());
             * itemVO.setReguser(evtrsvnMstVO.getReguser());
             * itemVO.setEvtOnoffIntype(evtrsvnMstVO.getEvtOnoffintype());
             * //이용총액
             * payamt += itemVO.getEvtTotamt();
             * saleamt += itemVO.getEvtSalamt();
             * if (cnt > 0) {
             * chargeDataList.add(itemVO);
             * }
             * }
             * }
             * }
             * }
             */

            evtrsvnMstVO.setEvtRsvnPayamt(String.valueOf(payamt));
            evtrsvnMstVO.setEvtRsvnSaleamt(String.valueOf(saleamt));
            //// evtrsvnMstVO.setEvtVeingnmpr(String.valueOf(visitCnt)); JYS 2021.05.18
            evtrsvnMstVO.setEvtPartcd(detailVO.getEvtPartcd());
            evtrsvnMstVO.setCtgCd(detailVO.getComCtgcd());

            if (memberVO != null && "10".equals(evtrsvnMstVO.getEvtPersnGbn())) {

                evtrsvnMstVO.setEvtRsvnMemtype(memberVO.getMemGbn());
                evtrsvnMstVO.setEvtRsvnWebid(memberVO.getId());
                evtrsvnMstVO.setEvtRsvnCustnm(memberVO.getMemNm());
                evtrsvnMstVO.setEvtPersnGbn("1001");
                evtrsvnMstVO.setEvtRsvnMoblphon(memberVO.getHp());
                evtrsvnMstVO.setEvtEmail(memberVO.getEmail());
            }

            int noShow = 0;
            int cnt = 0;

            // 무료인 경우 노쇼 체크
            if (saleamt < 1) {
                noShow = commonDAO.queryForInt("EvtrsvnSMainDAO.selectMemNshwCnt", evtrsvnMstVO);
            }

            log.debug("noShow = " + noShow);

            if (noShow > 0) {
                resultMap.put("resultCd", Config.FAIL);
                resultMap.put("resultMsg", "noshow 회원으로 예약이 제한된 사용자 입니다.");
            } else {

                // master insert
                /*
                 * JYS 2021.05.18
                 * if (visitCnt > 0) {
                 * String payWaitTime = (String)commonDAO.queryForObject("EvtrsvnSMainDAO.selectPayWaitTime", detailVO);
                 * evtrsvnMstVO.setPayWaitTime(payWaitTime);
                 * commonDAO.getExecuteResult("EvtrsvnSMainDAO.insertEvtrsvnMst", evtrsvnMstVO);
                 * cnt++;
                 * }
                 */

                // -------------------------------------------------------------------개인 정보 암화화 시작
                String strSpowiseCmsKey = EgovProperties.getProperty("Globals.SpowiseCms.Key");

                String strEvtVisitMoblphon = String.valueOf(evtrsvnMstVO.getEvtVisitMoblphon()).replace("-", "");
                String strEncEvtVisitMoblphon = WebEncDecUtil.fn_encrypt(strEvtVisitMoblphon, strSpowiseCmsKey);

                evtrsvnMstVO.setEvtVisitMoblphon(strEncEvtVisitMoblphon);
                // -------------------------------------------------------------------개인 정보 암화화 종료

                commonDAO.getExecuteResult("EvtrsvnSMainDAO.insertEvtrsvnMst", evtrsvnMstVO);
                cnt++;

                // 회차정보 insert
                if (cnt > 0) {
                    cnt = commonDAO.getExecuteResult("EvtrsvnSMainDAO.insertEvtrsvnTime", evtrsvnMstVO);
                    if (cnt > 0) {
                        for (EvtRsvnItemVO chargeVO : chargeDataList) {
                            commonDAO.getExecuteResult("EvtrsvnSMainDAO.insertEvtrsvnItem", chargeVO);
                        }
                    }
                }

                if (cnt > 0 && payamt < 1) {
                    myRsvnVO.setComcd(evtrsvnMstVO.getComcd());
                    myRsvnVO.setLgdBUYERID(evtrsvnMstVO.getEvtRsvnWebid());
                    myRsvnVO.setPartGbn("3001"); // 강연/행사/영화
                    myRsvnVO.setTerminalType(evtrsvnMstVO.getEvtTrmnltype());
                    myRsvnVO.setLgdAMOUNT("0");

                    /*
                     * 노원결제없음 막음 JYS 2021.03.17
                     * commonDAO.getExecuteResult("ChargeDAO.insertPaymentProc", myRsvnVO);
                     * if ("OK".equals(myRsvnVO.getRetCd())) {
                     * // 정상 결제 완료
                     * resultMap.put("resultCd",Config.SUCCESS);
                     * //log.debug("===================================결제 완료=================");
                     * }
                     */

                } else if (cnt > 0 && payamt > 1) {
                    param.clear();
                    param.put("partGbn", "3001");
                    param.put("rsvnNo", evtrsvnMstVO.getEvtRsvnno());
                    param.put("paytime", evtrsvnMstVO.getPayWaitTime());
                    //// JYS 2021.05.13 commonDAO.getExecuteResult("CmmServiceDAO.insertPaywaitLog", param);
                }

                if (cnt > 0) {
                    resultMap.put("resultCd", Config.SUCCESS);
                    resultMap.put("resultMsg", rsvnIdx);
                    resultMap.put("freeYn", saleamt > 0 ? "N" : "Y");
                } else {
                    resultMap.put("resultCd", Config.FAIL);
                    resultMap.put("resultMsg", "등록 데이타가 없습니다.");
                }
            }
        }

        return resultMap;
    }

    /**
     * 강연,행사,영화 예약정보, 세부내역을 조회한다.
     * 
     * @param vo
     *            EvtrsvnMstVO
     * @return EvtrsvnMstVO
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public EvtrsvnMstVO selectEvtrsvnDetail(EvtrsvnMstVO evtrsvnMstVO) throws Exception {

        EvtrsvnMstVO result = (EvtrsvnMstVO) commonDAO.queryForObject("EvtrsvnSMainDAO.selectEvtrsvnDetail", evtrsvnMstVO);

        //// result.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));
        result.setComcd(Config.COM_CD);

        if (result != null) {
            List<EvtRsvnItemVO> chargeList = (List<EvtRsvnItemVO>) commonDAO.queryForList("EvtrsvnSMainDAO.selectEvtrsvnItem", evtrsvnMstVO);
            result.setItemList(chargeList);
        }

        return result;
    }

    /**
     * 강연/행사/영화 예약 횟수를 체크한다.
     * 
     * @param vo
     *            CamelMap
     * @return int
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public int selectEvtrsvnCnt(Map<String, Object> map) throws Exception {
        int result = commonDAO.queryForInt("EvtrsvnSMainDAO.selectEvtrsvnCnt", map);
        return result;
    }

    /**
     * 강연/행사/영화 예약 횟수를 체크한다.
     * 
     * @param vo
     *            TermsVO
     * @return Map<String,Object>
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectEvtTerms(TermsVO vo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<TermsVO> cList = (List<TermsVO>) commonDAO.queryForList("EvtrsvnSMainDAO.selectEvtTermsList", vo);
        resultMap.put("list", cList);
        return resultMap;
    }

    /**
     * 노쇼 여부를 체크한다.
     * 
     * @param vo
     *            TermsVO
     * @return Map<String,Object>
     * @exception Exception
     */
    public int selectMemNshw(Map<String, Object> map) throws Exception {
        int result = commonDAO.queryForInt("EvtrsvnSMainDAO.selectMemNshwCnt", map);
        return result;
    }

    /**
     * 잔여인원을 체크한다
     * 
     * @param vo
     *            TermsVO
     * @return Map<String,Object>
     * @exception Exception
     */
    public Map<String, Object> selectEvtRmnAmnt(EvtrsvnMstVO vo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String result = "";
        int visitCnt = vo.getEvtVeingnmpr().equals("") ? 0 : Integer.parseInt(vo.getEvtVeingnmpr());
        int rmCapa = commonDAO.queryForInt("EvtrsvnSMainDAO.selectEvtCapa", vo);

        if (visitCnt > rmCapa) {
            result = Config.FAIL;
        } else {
            result = Config.SUCCESS;
        }

        resultMap.put("result", result);
        return resultMap;
    }

    /**
     * 티켓번호
     * 
     * @param maserVO
     *            EvtrsvnMstVO
     * @return String
     * @exception Exception
     */
    public String selectTicketNumber(EvtrsvnMstVO maserVO) throws Exception {
        return (String) commonDAO.queryForObject("EvtrsvnSMainDAO.selectTicketNumber", maserVO);
    }

    /**
     * 결제마감시간
     * 
     * @param ruleVo
     *            ExbtBaseruleVO
     * @return String
     * @exception Exception
     */
    public String selectPayWaitTime(EventProgramVO ruleVo) throws Exception {
        return (String) commonDAO.queryForObject("EvtrsvnSMainDAO.selectPayWaitTime", ruleVo);
    }

    /**
     * 단체할인
     * 
     * @param ruleVo
     *            ExbtBaseruleVO
     * @return String
     * @exception Exception
     */
    public CamelMap selectEvtGrpDscnt(EvtrsvnMstVO vo) throws Exception {
        return (CamelMap) commonDAO.queryForObject("EvtrsvnSMainDAO.selectEvtGrpDscnt", vo);
    }

    /**
     * 예약정보 수정
     * 
     * @param vo
     *            EvtrsvnMstVO
     * @return String
     * @exception Exception
     */
    public String updateEvtrsvnData(EvtrsvnMstVO vo) throws Exception {
        int result = commonDAO.getExecuteResult("EvtrsvnSMainDAO.updateEvtRsvnData", vo);
        String rtnMsg = "";
        if (result > 0) {
            rtnMsg = "정보를 수정하였습니다.";
        } else {
            rtnMsg = "정보 수정에 실패하였습니다.";
        }
        return rtnMsg;
    }

    /**
     * 예약인원 수정
     * 
     * @param vo
     *            EvtrsvnMstVO
     * @return String
     * @exception Exception
     */
    public String updateEvtrsvnItem(MemberVO memberVO, EvtrsvnMstVO vo) throws Exception {
        EvtrsvnMstVO data = (EvtrsvnMstVO) commonDAO.queryForObject("EvtrsvnSMainDAO.selectEvtrsvnDetail", vo);
        if (data != null) {
            int prevMemCount = Integer.parseInt(data.getEvtVeingnmpr());
            int totalCnt = 0;

            for (EvtItemAmountVO paramVO : vo.getChargeList()) {
                totalCnt += paramVO.getItemCnt();
            }

            if (prevMemCount < totalCnt) {
                return "기존 신청자수 보다 많은 인원을 신청하실 수 없습니다.";
            } else if ((memberVO != null && memberVO.getMemNo().equals(data.getEvtRsvnMemno())) || (vo.getEvtNonmembCertno().equals(data.getEvtNonmembCertno()))) {
                if (data.getEvtPersnGbn().equals("2001") && "11".equals(data.getEvtRsvnApptype())) {
                    // 기존 쿠폰 업데이트
                    MyRsvnVO myRsvnVO = new MyRsvnVO();
                    myRsvnVO.setRsvnIdxOne(vo.getEvtRsvnIdx());
                    myRsvnVO.setHpcertno(vo.getEvtNonmembCertno());
                    myRsvnVO.setUniqId(vo.getEvtRsvnMemno());
                    myRsvnVO.setComcd(vo.getComcd());

                    // 개인 단체 구분
                    vo.setEvtPersnGbn(data.getEvtPersnGbn());

                    commonDAO.getExecuteResult("MyRsvnEvtDAO.updateEvtCouponCancel", myRsvnVO);
                    // 기존 데이타 삭제
                    commonDAO.getExecuteResult("EvtrsvnSMainDAO.deleteEvtItemAll", vo);

                    List<EvtItemAmountVO> chargeList = (List<EvtItemAmountVO>) commonDAO.queryForList("EvtrsvnSMainDAO.selectChrgList", vo);
                    List<EvtItemAmountVO> paramCharge = vo.getChargeList();

                    String rsvnIdx = vo.getEvtRsvnIdx();

                    // 이벤트 할인 여부 조회
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("COMCD", Config.COM_CD);
                    param.put("YMD", vo.getEvtTime());
                    param.put("PART_CD", data.getEvtPartcd());
                    param.put("PGM_CD", data.getEvtNo());
                    param.put("PGM_GUBUN", "EVT");

                    RsvnCommVO discAnnualVO = null;
                    RsvnCommVO discInfoVO = null;
                    List<RsvnCommVO> discList = (List<RsvnCommVO>) commonDAO.queryForList("RsvnCommDAO.selectEventStdmngList", param);
                    RsvnCommVO discOpt = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectOptData", param);

                    if (discList != null) {
                        for (RsvnCommVO discVO : discList) {
                            if (discInfoVO != null) {
                                // 이미 이벤트 할당됨
                                break;
                            } else if (memberVO != null && "Y".equals(discVO.getMemberyn())) {
                                discInfoVO = discVO;
                            } else if (memberVO == null && "Y".equals(discVO.getNonmebyn())) {
                                discInfoVO = discVO;
                            }
                        }
                    }
                    // 유료회원 이고 개인이면
                    if (memberVO != null && "1001".equals(data.getEvtPersnGbn())) {
                        if (memberVO.getSpecialYn().equals("Y") || memberVO.getYearYn().equals("Y")) {
                            MyRsvnVO rsvnVO = new MyRsvnVO();
                            rsvnVO.setComcd(Config.COM_CD);
                            rsvnVO.setUniqId(memberVO.getMemNo());
                            rsvnVO.setRsvnIdxOne("0");
                            rsvnVO.setGubun("EVT");
                            rsvnVO.setProgramCd(data.getEvtNo());
                            if ("Y".equals(memberVO.getYearYn())) {
                                rsvnVO.setMemberGbn("1"); // 연간회원
                            } else {
                                rsvnVO.setMemberGbn("2"); // 특별회원
                            }
                            RsvnCommVO annualData = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectAnnualDcData", rsvnVO);
                            if (annualData != null && annualData.getLimitCnt() == 0 && (discInfoVO == null || discInfoVO.getDcRate() < annualData.getDcRate())) {
                                // 제한 없이
                                discAnnualVO = annualData;
                                vo.setDcAnnualLimit(annualData.getLimitQty());
                            }
                        }
                    }

                    int limitQty = vo.getDcAnnualLimit();
                    int remainCnt = 0;
                    CamelMap groupdisc = selectEvtGrpDscnt(vo);

                    if (chargeList != null && paramCharge != null) {
                        for (EvtItemAmountVO paramVO : paramCharge) {
                            for (EvtItemAmountVO chargeVO : chargeList) {
                                if (paramVO.getItemCd().equals(chargeVO.getItemCd()) && paramVO.getItemCnt() > 0) {
                                    EvtRsvnItemVO itemVO = new EvtRsvnItemVO();
                                    itemVO.setComcd(Config.COM_CD);
                                    itemVO.setEvtRsvnItemcd(paramVO.getItemCd());
                                    itemVO.setEvtRsvnIdx(rsvnIdx);
                                    itemVO.setEvtCost(chargeVO.getItemPrice());

                                    long amount = chargeVO.getItemPrice() * paramVO.getItemCnt();
                                    long dcAmountLong = 0;
                                    int orignCnt = Integer.parseInt(paramVO.getItemCnt() + "");
                                    int cnt = orignCnt;

                                    if (amount > 0) {
                                        // 단체 할인 적용
                                        if (groupdisc != null && !CommonUtil.getString(groupdisc.get("dcReasonCd")).equals("")) {
                                            if ("Y".equals(chargeVO.getGroupdcyn())) {
                                                itemVO.setEvtDcType(CommonUtil.getString(groupdisc.get("dcReasonCd")));
                                                itemVO.setEvtDcRate(Long.parseLong(CommonUtil.getString(groupdisc.get("dcRate"))));
                                                itemVO.setDcName("단체할인");
                                                dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO.getItemPrice() * itemVO.getEvtDcRate() * paramVO.getItemCnt() * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
                                            }
                                        } else {
                                            int annualCnt = limitQty - remainCnt;

                                            // 유료회원할인을 먹인다
                                            if (annualCnt >= orignCnt) {
                                                annualCnt = orignCnt;
                                                cnt = 0;
                                            } else if (annualCnt > 0) {
                                                cnt = orignCnt - annualCnt;
                                            }

                                            // 유료회원 할인
                                            if (discAnnualVO != null && discAnnualVO.getDcRate() > 0 && annualCnt > 0) {
                                                long dcAmountLong2 = CommonUtil.DoubleToLongCalc(chargeVO.getItemPrice() * discAnnualVO.getDcRate() * 0.01 * annualCnt, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());

                                                EvtRsvnItemVO chargeVO2 = new EvtRsvnItemVO();
                                                chargeVO2.setEvtCost(chargeVO.getItemPrice());
                                                chargeVO2.setComcd(Config.COM_CD);
                                                chargeVO2.setEvtRsvnIdx(rsvnIdx);
                                                chargeVO2.setEvtRsvnItemcd(paramVO.getItemCd());
                                                chargeVO2.setEvtSalamt(itemVO.getEvtCost() * annualCnt);
                                                chargeVO2.setEvtRsvnItemcnt(annualCnt);
                                                chargeVO2.setEvtDcamt(dcAmountLong2);
                                                chargeVO2.setEvtTotamt(chargeVO2.getEvtSalamt() - dcAmountLong2);
                                                chargeVO2.setEvtTerminalType(vo.getEvtTrmnltype());
                                                chargeVO2.setReguser(vo.getReguser());
                                                chargeVO2.setEvtOnoffIntype(vo.getEvtOnoffintype());
                                                chargeVO2.setEvtDcType(discAnnualVO.getDcReasonCd());
                                                chargeVO2.setEvtDcRate(discAnnualVO.getDcRate());

                                                commonDAO.getExecuteResult("EvtrsvnSMainDAO.insertEvtrsvnItem", chargeVO2);

                                            }
                                            // -------유료회원 할인 끝

                                            // 이벤트 할인
                                            if (discInfoVO != null) {
                                                itemVO.setEvtDcType(discInfoVO.getDcReasonCd());
                                                itemVO.setEvtDcRate(discInfoVO.getDcRate());
                                                itemVO.setEvtEventDcid(discInfoVO.getDcid());

                                                dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO.getItemPrice() * discInfoVO.getDcRate() * cnt * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
                                            }

                                            remainCnt += orignCnt;
                                        }
                                    }
                                    if (cnt > 0) {
                                        itemVO.setEvtRsvnItemcnt(cnt);
                                        itemVO.setEvtSalamt(itemVO.getEvtCost() * cnt);
                                        itemVO.setEvtDcamt(dcAmountLong);
                                        itemVO.setEvtTotamt(itemVO.getEvtSalamt() - dcAmountLong);
                                        itemVO.setEvtTerminalType(vo.getEvtTrmnltype());
                                        itemVO.setModuser(vo.getReguser());
                                        itemVO.setEvtOnoffIntype(vo.getEvtOnoffintype());

                                        commonDAO.getExecuteResult("EvtrsvnSMainDAO.insertEvtrsvnItem", itemVO);
                                    }
                                }
                            }
                        }
                    }

                    int n = commonDAO.getExecuteResult("EvtrsvnSMainDAO.updateEvtMstAmt", myRsvnVO);

                    if (n > 0) {
                        return "OK";
                    } else {
                        return "수정실패.";
                    }
                } else {
                    return "결제 대기 상태인 경우만 수정이 가능합니다.";
                }
            } else {
                return "예약자정보와 로그인 정보가 일치하지 않습니다.";
            }
        } else {
            return "데이타가 없습니다.";
        }

    }
}
