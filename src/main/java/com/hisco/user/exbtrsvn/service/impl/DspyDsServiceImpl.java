package com.hisco.user.exbtrsvn.service.impl;

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
import com.hisco.intrfc.charge.vo.OrderIdVO;
import com.hisco.user.exbtrsvn.service.CalendarVO;
import com.hisco.user.exbtrsvn.service.DspyDsService;
import com.hisco.user.exbtrsvn.service.ExbtBaseruleVO;
import com.hisco.user.exbtrsvn.service.ExbtChargeVO;
import com.hisco.user.exbtrsvn.service.ExbtTimeVO;
import com.hisco.user.exbtrsvn.service.RsvnMasterVO;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.vo.MyRsvnVO;
import com.hisco.user.mypage.vo.RsvnCommVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 관람 정보 조회 구현 클래스
 * 
 * @author 진수진
 * @since 2020.08.12
 * @version 1.0, 2020.08.12
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.08.12 최초작성
 */
@Service("dspyDsService")
public class DspyDsServiceImpl extends EgovAbstractServiceImpl implements DspyDsService {

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    /**
     * 관람 기본 데이타를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    public CamelMap selectselectPartCd(Map<?, ?> vo) throws Exception {
        return (CamelMap) commonDAO.queryForObject("DspyDsDAO.selectPartCd", vo);
    }

    /**
     * 관람 파일 아이디를 수정한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    public void updatePartCdFile(Map<?, ?> vo) throws Exception {
        int cnt = (Integer) commonDAO.queryForObject("DspyDsDAO.selectPartCdPref", vo);

        if (cnt < 1) {
            commonDAO.getExecuteResult("DspyDsDAO.insertPartCdFile", vo);
        } else {
            commonDAO.getExecuteResult("DspyDsDAO.updatePartCdFile", vo);
        }

    }

    /**
     * 기준설정 데이타를 조회한다
     * 
     * @param vo
     *            CamelMap
     * @return CamelMap
     * @exception Exception
     */
    public CamelMap selectBaseRule(Map<?, ?> vo) throws Exception {
        return (CamelMap) commonDAO.queryForObject("DspyDsDAO.selectBaseRule", vo);
    }

    public void updateBaseRule(Map<?, ?> vo) throws Exception {
        commonDAO.getExecuteResult("DspyDsDAO.updateBaseRule", vo);
    }

    /**
     * 일정 데이타를 조회한다
     * 
     * @param vo
     *            Map
     * @return CalendarVO
     * @exception Exception
     */
    public List<CalendarVO> selectScheduleList(Map<?, ?> vo) throws Exception {
        return (List<CalendarVO>) commonDAO.queryForList("DspyDsDAO.selectScheduleList", vo);
    }

    /**
     * 관람 기준 설정 목록을 조회한다
     * 
     * @param vo
     *            Map
     * @return ExbtBaseruleVO
     * @exception Exception
     */
    public List<ExbtBaseruleVO> selectExbtBaseList(Map<?, ?> vo) throws Exception {
        return (List<ExbtBaseruleVO>) commonDAO.queryForList("DspyDsDAO.selectExbtBaseList", vo);
    }

    /**
     * 관람 기준 설정 상세를 조회한다
     * 
     * @param vo
     *            Map
     * @return ExbtBaseruleVO
     * @exception Exception
     */
    public ExbtBaseruleVO selectExbtBaseDetail(Map<?, ?> vo) throws Exception {
        return (ExbtBaseruleVO) commonDAO.queryForObject("DspyDsDAO.selectExbtBaseList", vo);
    }

    /**
     * 관람 회차 목록을 조회한다
     * 
     * @param vo
     *            Map
     * @return ExbtTimeVO
     * @exception Exception
     */
    public List<ExbtTimeVO> selectExbtTimeList(Map<String, Object> vo) throws Exception {
        return (List<ExbtTimeVO>) commonDAO.queryForList("DspyDsDAO.selectExbtTimeList", vo);
    }

    /**
     * 관람 회차 상세정보를 조회한다
     * 
     * @param vo
     *            Map
     * @return ExbtTimeVO
     * @exception Exception
     */
    public ExbtTimeVO selectExbtTimeData(Map<String, Object> vo) throws Exception {
        return (ExbtTimeVO) commonDAO.queryForObject("DspyDsDAO.selectExbtTimeList", vo);
    }

    /**
     * 선택날짜 요일을 조회한다
     * 
     * @param vo
     *            Map
     * @return String
     * @exception Exception
     */
    public String selectExbtWeek(Map<?, ?> vo) throws Exception {
        String dayWeek = (String) commonDAO.queryForObject("DspyDsDAO.selectExbtTimeDay", vo);
        return dayWeek;
    }

    /**
     * 관람 요금을 조회한다
     * 
     * @param vo
     *            Map
     * @return List<ExbtChargeVO>
     * @exception Exception
     */
    public List<ExbtChargeVO> selectExbtChargeList(Map<?, ?> vo) throws Exception {
        return (List<ExbtChargeVO>) commonDAO.queryForList("DspyDsDAO.selectExbtChargeList", vo);
    }

    /**
     * 예약 번호 가져오기
     * 
     * @param
     * @return String
     * @exception Exception
     */
    public String selectRsvnNumber() throws Exception {
        return (String) commonDAO.queryForObject("DspyDsDAO.selectRsvnNumber", null);
    }

    /**
     * 예약 정보를 저장한다
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return Map<String,String>
     * @exception Exception
     */
    public Map<String, String> insertRegistMst(MemberVO memberVO, RsvnMasterVO maserVO, ExbtBaseruleVO baseDataVO,
            MyRsvnVO myRsvnVO) throws Exception {

        Map<String, String> param = new HashMap<String, String>();
        Map<String, String> returnMap = new HashMap<String, String>();
        // 회차 정보
        param.put("comcd", maserVO.getComcd());
        param.put("ymd", maserVO.getYmd());
        param.put("dayWeek", (String) commonDAO.queryForObject("DspyDsDAO.selectExbtTimeDay", param)); // 요일체크
        param.put("exbtSeq", maserVO.getExbtSeq());
        param.put("timeseq", maserVO.getTimeseq());
        ExbtTimeVO timeVO = (ExbtTimeVO) commonDAO.queryForObject("DspyDsDAO.selectExbtTimeList", param);

        maserVO.setLimitMethod(baseDataVO.getRsvnLimitMethod());
        int myCnt = (int) commonDAO.queryForObject("DspyDsDAO.selectReserveLimitCount", maserVO);

        long totalCnt = 0;

        /*
         * for (ExbtChargeVO charge : maserVO.getChargeList() ) {
         * totalCnt += charge.getItemCnt();
         * }
         */

        if (timeVO == null) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "회차 데이타가 없습니다. 선택 회차를 다시 확인해 주세요.");
        } else if (timeVO.getTotCapa() <= timeVO.getReserveCnt() || !"Y".equals(timeVO.getReserveAbleYn())) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "예약이 마감되었습니다.");

        } else if (timeVO.getTotCapa() < (timeVO.getReserveCnt() + totalCnt)) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "잔여정원을 초과하여 신청하실 수 없습니다.(잔여정원:" + (timeVO.getTotCapa() - timeVO.getReserveCnt()) + "명)");
        } else if (baseDataVO.getRsvnLimitMethod().equals("20") && baseDataVO.getRsvnLimitCnt() > 0 && myCnt >= baseDataVO.getRsvnLimitCnt()) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "예약 가능 횟수 가 초과되었습니다. (하루 최대 " + baseDataVO.getRsvnLimitCnt() + "회)");
        } else if (baseDataVO.getRsvnLimitMethod().equals("21") && baseDataVO.getRsvnLimitCnt() > 0 && myCnt >= baseDataVO.getRsvnLimitCnt()) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "예약 가능 횟수 가 초과되었습니다. (한달 최대 " + baseDataVO.getRsvnLimitCnt() + "회)");
        } else if (baseDataVO.getRsvnLimitMethod().equals("22") && baseDataVO.getRsvnLimitCnt() > 0 && myCnt >= baseDataVO.getRsvnLimitCnt()) {
            returnMap.put("resultCd", Config.FAIL);
            returnMap.put("resultMsg", "예약 가능 횟수 가 초과되었습니다. (1년 최대 " + baseDataVO.getRsvnLimitCnt() + "회)");
        } else {

            String rsvnIdx = (String) commonDAO.queryForObject("DspyDsDAO.selectReserveKey", null);
            maserVO.setRsvnIdx(rsvnIdx);

            int vistCnt = Integer.parseInt(maserVO.getExbtVistnmpr());
            long saleAmount = 0;
            long payAmount = 0;

            String itemInfo = "";

            // 이벤트 할인 여부 조회
            param.put("COMCD", Config.COM_CD);
            param.put("YMD", maserVO.getYmd());
            param.put("PART_CD", baseDataVO.getExbtPartcd());
            param.put("PGM_CD", baseDataVO.getExbtSeq());
            param.put("PGM_GUBUN", "EXBT");

            RsvnCommVO discAnnualVO = null;
            RsvnCommVO discInfoVO = null;
            List<RsvnCommVO> discList = (List<RsvnCommVO>) commonDAO.queryForList("RsvnCommDAO.selectEventStdmngList", param);
            RsvnCommVO discOpt = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectOptData", param);

            if (discList != null) {
                for (RsvnCommVO discVO : discList) {
                    if (discInfoVO != null) {
                        // 이미 이벤트 할당됨
                        break;
                    } else if (maserVO.getMemNo() != null && !maserVO.getMemNo().equals("") && "Y".equals(discVO.getMemberyn())) {
                        discInfoVO = discVO;
                    } else if ((maserVO.getMemNo() == null || maserVO.getMemNo().equals("")) && "Y".equals(discVO.getNonmebyn())) {
                        discInfoVO = discVO;
                    }
                }
            }

            // 유료회원
            if (memberVO != null && "10".equals(maserVO.getTarget())) {
                if (memberVO.getSpecialYn().equals("Y") || memberVO.getYearYn().equals("Y")) {
                    MyRsvnVO rsvnVO = new MyRsvnVO();
                    rsvnVO.setComcd(Config.COM_CD);
                    rsvnVO.setUniqId(memberVO.getMemNo());
                    rsvnVO.setRsvnIdxOne("0");
                    rsvnVO.setGubun("EXBT");
                    rsvnVO.setProgramCd(baseDataVO.getExbtSeq());
                    if ("Y".equals(memberVO.getYearYn())) {
                        rsvnVO.setMemberGbn("1"); // 연간회원
                    } else {
                        rsvnVO.setMemberGbn("2"); // 특별회원
                    }
                    RsvnCommVO annualData = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectAnnualDcData", rsvnVO);
                    if (annualData != null && annualData.getLimitCnt() == 0 && (discInfoVO == null || discInfoVO.getDcRate() < annualData.getDcRate())) {
                        // 제한 없이
                        discAnnualVO = annualData;
                        maserVO.setDcAnnualLimit(annualData.getLimitQty());
                    }
                }
            }

            //// List<ExbtChargeVO> chargeList =
            //// (List<ExbtChargeVO>)commonDAO.queryForList("DspyDsDAO.selectExbtChargeList", maserVO); JYS 2021.05.19
            List<ExbtChargeVO> chargeList = null; // JYS 2021.05.19
            List<ExbtChargeVO> paramCharge = maserVO.getChargeList();

            List<ExbtChargeVO> chargeDataList = new ArrayList<ExbtChargeVO>();

            int limitQty = maserVO.getDcAnnualLimit();
            int remainCnt = 0;

            /*
             * if (chargeList != null && paramCharge != null) {
             * for (ExbtChargeVO paramVO : paramCharge) {
             * for (ExbtChargeVO chargeVO : chargeList) {
             * if (paramVO.getItemCd().equals(chargeVO.getItemCd()) && paramVO.getItemCnt() > 0) {
             * vistCnt += paramVO.getItemCnt();
             * long amount = chargeVO.getPrice() * paramVO.getItemCnt();
             * long dcAmountLong = 0;
             * int orignCnt = Integer.parseInt(paramVO.getItemCnt()+"");
             * int cnt = orignCnt;
             * chargeVO.setRsvnIdx(rsvnIdx);
             * chargeVO.setTerminalType(maserVO.getTerminalType());
             * chargeVO.setReguser(maserVO.getReguser());
             * if (!itemInfo.equals(""))itemInfo += "\n  ";
             * itemInfo += chargeVO.getItemNm() +" : " + paramVO.getItemCnt()+"명";
             * if (amount > 0) {
             * // 단체 할인 적용
             * if ("20".equals(maserVO.getTarget()) && baseDataVO.getDcReasonCd() != null && baseDataVO.getDcRate()>0) {
             * if ( "Y".equals(chargeVO.getGroupdcYn())) {
             * chargeVO.setDcKindCd("6001");
             * chargeVO.setDcType(baseDataVO.getDcReasonCd());
             * chargeVO.setDcRate(baseDataVO.getDcRate());
             * chargeVO.setDcName("단체할인");
             * dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO.getPrice() * baseDataVO.getDcRate() * 0.01 *
             * paramVO.getItemCnt() , discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
             * }
             * } else {
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
             * long dcAmountLong2 = CommonUtil.DoubleToLongCalc(chargeVO.getPrice() * discAnnualVO.getEventDcRate() *
             * 0.01 * annualCnt, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
             * ExbtChargeVO chargeVO2 = new ExbtChargeVO();
             * chargeVO2.setRsvnIdx(rsvnIdx);
             * chargeVO2.setItemCd(chargeVO.getItemCd());
             * chargeVO2.setPrice(chargeVO.getPrice());
             * chargeVO2.setAmount( chargeVO.getPrice() * annualCnt);
             * chargeVO2.setTerminalType(maserVO.getTerminalType());
             * chargeVO2.setReguser(maserVO.getReguser());
             * chargeVO2.setDcType(discAnnualVO.getEventReasoncd());
             * chargeVO2.setDcRate(discAnnualVO.getEventDcRate());
             * chargeVO2.setItemCnt(annualCnt);
             * chargeVO2.setDcAmount(dcAmountLong2);
             * chargeVO2.setTotAmount(chargeVO2.getAmount() - dcAmountLong2 );
             * chargeDataList.add(chargeVO2);
             * payAmount += chargeVO2.getTotAmount();
             * saleAmount += chargeVO2.getAmount();
             * }
             * //-------유료회원 할인 끝
             * if (discInfoVO != null) { // 이벤트 할인
             * //chargeVO.setDcKindCd(dcTypeCd);
             * chargeVO.setDcType(discInfoVO.getEventReasoncd());
             * chargeVO.setDcRate(discInfoVO.getEventDcRate());
             * chargeVO.setDcEventId(discInfoVO.getDcid());
             * dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO.getPrice() * discInfoVO.getEventDcRate() * 0.01 * cnt
             * , discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
             * }
             * remainCnt += orignCnt;
             * }
             * }
             * chargeVO.setItemCnt(cnt);
             * chargeVO.setAmount(chargeVO.getPrice() * cnt);
             * chargeVO.setDcAmount(dcAmountLong);
             * chargeVO.setTotAmount(chargeVO.getAmount() - dcAmountLong );
             * chargeDataList.add(chargeVO);
             * payAmount += chargeVO.getTotAmount();
             * saleAmount += chargeVO.getAmount();
             * }
             * }
             * }
             * }
             */

            maserVO.setVisitnum(vistCnt); // 총 방문객수
            maserVO.setSaleamt(saleAmount); // 원금액
            maserVO.setPayamt(payAmount); // 최종결제금액
            int cnt = 0;

            /*
             * 2021.05.19
             * if (vistCnt > 0) {
             * String payWaitTime = (String)commonDAO.queryForObject("DspyDsDAO.selectPayWaitTime", baseDataVO);
             * maserVO.setPayWaitTime(payWaitTime);
             * cnt = commonDAO.getExecuteResult("DspyDsDAO.insertReserveMaster", maserVO);
             * }
             */

            // -------------------------------------------------------------------개인 정보 암화화 시작

            String strSpowiseCmsKey = EgovProperties.getProperty("Globals.SpowiseCms.Key");

            String strVisitcustHpno = String.valueOf(maserVO.getVisitcustHpno());

            String strEncVisitcustHpno = WebEncDecUtil.fn_encrypt(strVisitcustHpno, strSpowiseCmsKey);

            maserVO.setVisitcustHpno(strEncVisitcustHpno);
            // -------------------------------------------------------------------개인 정보 암화화 종료

            cnt = commonDAO.getExecuteResult("DspyDsDAO.insertReserveMaster", maserVO);

            if (cnt > 0) {
                cnt = commonDAO.getExecuteResult("DspyDsDAO.insertReserveTime", maserVO);
            }

            if (cnt > 0) {
                for (ExbtChargeVO chargeVO : chargeDataList) {
                    if (chargeVO.getItemCnt() > 0) {
                        commonDAO.getExecuteResult("DspyDsDAO.insertReserveItem", chargeVO);
                    }
                }
            }
            // 무료 관람 인경우 결제 프로시져 호출
            if (cnt > 0 & payAmount < 1) {
                myRsvnVO.setComcd(maserVO.getComcd());
                myRsvnVO.setLgdBUYERID(maserVO.getWebId());
                myRsvnVO.setPartGbn("1001"); // 관람
                myRsvnVO.setTerminalType(maserVO.getTerminalType());
                myRsvnVO.setLgdAMOUNT("0");

                /*
                 * 노원 결제 없음 막음 JYS 2021.03.17
                 * commonDAO.getExecuteResult("ChargeDAO.insertPaymentProc", myRsvnVO);
                 * if ("OK".equals(myRsvnVO.getRetCd())) {
                 * // 정상 결제 완료
                 * returnMap.put("resultCd",Config.SUCCESS);
                 * }
                 */

            } else if (cnt > 0 && payAmount > 1) {
                param.clear();
                param.put("partGbn", "1001");
                param.put("rsvnNo", maserVO.getRsvnNo());
                param.put("paytime", maserVO.getPayWaitTime());
                //// JYS 2021.05.13 commonDAO.getExecuteResult("CmmServiceDAO.insertPaywaitLog", param);
            }

            if (cnt > 0) {
                returnMap.put("resultCd", Config.SUCCESS);
                returnMap.put("resultMsg", rsvnIdx);
                returnMap.put("freeYn", payAmount > 0 ? "N" : "Y");
                returnMap.put("itemInfo", itemInfo);
            } else {
                returnMap.put("resultCd", Config.FAIL);
                returnMap.put("resultMsg", "등록 데이타가 없습니다.");
            }
        }

        return returnMap;
    }

    /**
     * 예약 정보를 조회한다
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return RsvnMasterVO
     * @exception Exception
     */
    public RsvnMasterVO selectRegistMst(RsvnMasterVO maserVO) throws Exception {
        return (RsvnMasterVO) commonDAO.queryForObject("DspyDsDAO.selectReserveMaster", maserVO);
    }

    /**
     * 예약 품목을 조회한다
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return List<ExbtChargeVO>
     * @exception Exception
     */
    public List<ExbtChargeVO> selectRegistItemList(RsvnMasterVO maserVO) throws Exception {
        return (List<ExbtChargeVO>) commonDAO.queryForList("DspyDsDAO.selectReserveItemList", maserVO);
    }

    /**
     * 휴관일 여부를 체크한다
     * 
     * @param vo
     *            Map
     * @return int
     * @exception Exception
     */
    public int selectHolidayCheck(Map<?, ?> vo) throws Exception {
        return (int) commonDAO.queryForObject("DspyDsDAO.selectHolidayCheck", vo);
    }

    /**
     * 이용횟수 제한 기준에 따른 이용횟수 조회
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return int
     * @exception Exception
     */
    public int selectReserveLimitCount(RsvnMasterVO maserVO) throws Exception {
        return (int) commonDAO.queryForObject("DspyDsDAO.selectReserveLimitCount", maserVO);
    }

    /**
     * 약관을 조회한다
     * 
     * @return List<TermsVO>
     * @exception Exception
     */
    public List<TermsVO> selectTermsList() throws Exception {
        return (List<TermsVO>) commonDAO.queryForList("DspyDsDAO.selectTermsList", null);
    }

    /**
     * 티켓번호
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return String
     * @exception Exception
     */
    public String selectTicketNumber(RsvnMasterVO maserVO) throws Exception {
        return (String) commonDAO.queryForObject("DspyDsDAO.selectTicketNumber", maserVO);
    }

    /**
     * 결제마감시간
     * 
     * @param ruleVo
     *            ExbtBaseruleVO
     * @return String
     * @exception Exception
     */
    public String selectPayWaitTime(ExbtBaseruleVO ruleVo) throws Exception {
        return (String) commonDAO.queryForObject("DspyDsDAO.selectPayWaitTime", ruleVo);
    }

    /**
     * 예약정보 수정
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return int
     * @exception Exception
     */
    public int updateReserveMaster(RsvnMasterVO maserVO) throws Exception {
        return commonDAO.getExecuteResult("DspyDsDAO.updateReserveMaster", maserVO);
    }

    /**
     * 그룹할인 여부 조회
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return String
     * @exception Exception
     */
    public CamelMap selectGroupDiscount(RsvnMasterVO maserVO) throws Exception {
        return (CamelMap) commonDAO.queryForObject("DspyDsDAO.selectGroupDiscount", maserVO);
    }

    /**
     * 예약정보 인원 수정
     * 
     * @param maserVO
     *            RsvnMasterVO
     * @return String
     * @exception Exception
     */
    public String updateReserveItem(MemberVO memberVO, RsvnMasterVO maserVO) throws Exception {
        RsvnMasterVO data = (RsvnMasterVO) commonDAO.queryForObject("DspyDsDAO.selectReserveMaster", maserVO);

        if (data != null) {
            int prevMemCount = data.getVisitnum();
            int totalCnt = 0;

            for (ExbtChargeVO paramVO : maserVO.getChargeList()) {
                totalCnt += paramVO.getItemCnt();
            }

            if (prevMemCount < totalCnt) {
                return "기존 신청자수 보다 많은 인원을 신청하실 수 없습니다.";
            } else if ((memberVO != null && memberVO.getMemNo().equals(data.getMemNo())) || (maserVO.getHpcertno().equals(data.getHpcertno()))) {
                if (data.getTarget().equals("2001") && data.getApptype().equals("11")) {
                    // 기존 쿠폰 업데이트
                    MyRsvnVO myRsvnVO = new MyRsvnVO();
                    myRsvnVO.setRsvnIdxOne(maserVO.getRsvnIdx());
                    myRsvnVO.setHpcertno(maserVO.getHpcertno());
                    myRsvnVO.setUniqId(maserVO.getMemNo());
                    myRsvnVO.setComcd(maserVO.getComcd());

                    commonDAO.getExecuteResult("MyRsvnExbtDAO.updateExbtCouponCancel", myRsvnVO);
                    commonDAO.getExecuteResult("DspyDsDAO.deleteExbtItemAll", maserVO);

                    // 기존 데이타 삭제
                    commonDAO.getExecuteResult("DspyDsDAO.deleteExbtItemAll", maserVO);

                    List<ExbtChargeVO> chargeList = (List<ExbtChargeVO>) commonDAO.queryForList("DspyDsDAO.selectExbtChargeList", data);
                    List<ExbtChargeVO> paramCharge = maserVO.getChargeList();

                    String rsvnIdx = maserVO.getRsvnIdx();

                    // 이벤트 할인 여부 조회
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("COMCD", Config.COM_CD);
                    param.put("YMD", data.getYmd());
                    param.put("PART_CD", data.getExbtPartcd());
                    param.put("PGM_CD", data.getExbtSeq());
                    param.put("PGM_GUBUN", "EXBT");

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

                    // 유료회원
                    /*
                     * if (memberVO != null) {
                     * if (memberVO.getSpecialYn().equals("Y") || memberVO.getYearYn().equals("Y")) {
                     * MyRsvnVO rsvnVO = new MyRsvnVO();
                     * rsvnVO.setComcd(Config.COM_CD);
                     * rsvnVO.setUniqId(memberVO.getMemNo());
                     * rsvnVO.setRsvnIdxOne("0");
                     * rsvnVO.setGubun("EXBT");
                     * rsvnVO.setProgramCd(data.getExbtSeq());
                     * if ("Y".equals(memberVO.getYearYn())) {
                     * rsvnVO.setMemberGbn("1"); // 연간회원
                     * } else {
                     * rsvnVO.setMemberGbn("2"); // 특별회원
                     * }
                     * RsvnCommVO annualData = (RsvnCommVO)commonDAO.queryForObject("RsvnCommDAO.selectAnnualDcData",
                     * rsvnVO);
                     * if (annualData != null && annualData.getLimitCnt() == 0 && (discInfoVO == null ||
                     * discInfoVO.getEventDcRate() < annualData.getEventDcRate())) {
                     * //제한 없이
                     * discAnnualVO = annualData;
                     * maserVO.setDcAnnualLimit(annualData.getLimitQty());
                     * }
                     * }
                     * }
                     */

                    int limitQty = maserVO.getDcAnnualLimit();
                    int remainCnt = 0;
                    CamelMap groupdisc = (CamelMap) commonDAO.queryForObject("DspyDsDAO.selectGroupDiscount", data);

                    if (chargeList != null && paramCharge != null) {
                        for (ExbtChargeVO paramVO : paramCharge) {
                            for (ExbtChargeVO chargeVO : chargeList) {
                                if (paramVO.getItemCd().equals(chargeVO.getItemCd()) && paramVO.getItemCnt() > 0) {

                                    long amount = chargeVO.getPrice() * paramVO.getItemCnt();
                                    long dcAmountLong = 0;
                                    int orignCnt = Integer.parseInt(paramVO.getItemCnt() + "");
                                    int cnt = orignCnt;

                                    chargeVO.setRsvnIdx(rsvnIdx);
                                    chargeVO.setAmount(amount);
                                    chargeVO.setTerminalType(maserVO.getTerminalType());
                                    chargeVO.setReguser(maserVO.getReguser());

                                    if (amount > 0) {
                                        // 단체 할인 적용
                                        if (groupdisc != null && !CommonUtil.getString(groupdisc.get("dcReasonCd")).equals("") && "Y".equals(chargeVO.getGroupdcYn())) {
                                            chargeVO.setDcKindCd("6001");
                                            chargeVO.setDcType(CommonUtil.getString(groupdisc.get("dcReasonCd")));
                                            chargeVO.setDcRate(Long.parseLong(CommonUtil.getString(groupdisc.get("dcRate"))));
                                            chargeVO.setDcName("단체할인");
                                            chargeVO.setItemCnt(paramVO.getItemCnt());

                                            dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO.getPrice() * Long.parseLong(CommonUtil.getString(groupdisc.get("dcRate"))) * paramVO.getItemCnt() * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
                                        } else {
                                            int annualCnt = limitQty - remainCnt;

                                            // 유료회원할인을 먹인다
                                            if (annualCnt >= orignCnt) {
                                                annualCnt = orignCnt;
                                                cnt = 0;
                                            } else {
                                                cnt = orignCnt - annualCnt;
                                            }

                                            // 유료회원 할인
                                            if (discAnnualVO != null && discAnnualVO.getDcRate() > 0 && annualCnt > 0) {
                                                long dcAmountLong2 = CommonUtil.DoubleToLongCalc(chargeVO.getPrice() * discAnnualVO.getDcRate() * 0.01 * annualCnt, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());

                                                ExbtChargeVO chargeVO2 = new ExbtChargeVO();
                                                chargeVO2.setRsvnIdx(rsvnIdx);
                                                chargeVO2.setItemCd(chargeVO.getItemCd());
                                                chargeVO2.setPrice(chargeVO.getPrice());
                                                chargeVO2.setAmount(chargeVO.getPrice() * annualCnt);
                                                chargeVO2.setTerminalType(maserVO.getTerminalType());
                                                chargeVO2.setReguser(maserVO.getReguser());
                                                chargeVO2.setDcType(discAnnualVO.getDcReasonCd());
                                                chargeVO2.setDcRate(discAnnualVO.getDcRate());
                                                chargeVO2.setItemCnt(annualCnt);
                                                chargeVO2.setDcAmount(dcAmountLong2);
                                                chargeVO2.setTotAmount(chargeVO2.getAmount() - dcAmountLong2);
                                                commonDAO.getExecuteResult("DspyDsDAO.insertReserveItem", chargeVO2);

                                            }
                                            // -------유료회원 할인 끝
                                            chargeVO.setAmount(chargeVO.getPrice() * cnt);
                                            chargeVO.setItemCnt(cnt);

                                            if (discInfoVO != null) { // 이벤트 할인
                                                chargeVO.setDcType(discInfoVO.getDcReasonCd());
                                                chargeVO.setDcRate(discInfoVO.getDcRate());
                                                chargeVO.setDcEventId(discInfoVO.getDcid());
                                                dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO.getPrice() * discInfoVO.getDcRate() * 0.01 * cnt, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
                                            }

                                            remainCnt += orignCnt;
                                        }
                                    }
                                    chargeVO.setItemCnt(cnt);
                                    chargeVO.setDcAmount(dcAmountLong);
                                    chargeVO.setTotAmount(amount - dcAmountLong);

                                    if (chargeVO.getItemCnt() > 0) {
                                        commonDAO.getExecuteResult("DspyDsDAO.insertReserveItem", chargeVO);
                                    }
                                }
                            }
                        }
                    }

                    int n = commonDAO.getExecuteResult("DspyDsDAO.updateExbtMstData", myRsvnVO);

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

    /**
     * 관람 상품 예약가능 시작일
     * 
     * @param param
     * @return String
     * @exception Exception
     */
    public String selectStartYmdByBase(Map<?, ?> param) throws Exception {
        return (String) commonDAO.queryForObject("DspyDsDAO.selectStartYmdByBase", param);
    }
}
