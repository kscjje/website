package com.hisco.user.evtedcrsvn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.hisco.admin.terms.vo.TermsVO;
import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.WebEncDecUtil;
import com.hisco.user.evtedcrsvn.service.EventProgramVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcItemAmountVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcRsvnItemVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcStdmngVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcrsvnMstVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcrsvnService;
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
@Service("evtEdcrsvnService")
public class EvtEdcrsvnServiceImpl extends EgovAbstractServiceImpl implements EvtEdcrsvnService {

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
        return (CamelMap) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectProgramData", vo);
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
        commonDAO.getExecuteResult("EvtEdcrsvnSMainDAO.updateProgramData", vo);
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
    public List<EvtEdcItemAmountVO> selectEvtEdcChargeList(EvtEdcStdmngVO vo) throws Exception {
        List<EvtEdcItemAmountVO> cList = (List<EvtEdcItemAmountVO>) commonDAO.queryForList("EvtEdcrsvnSMainDAO.selectChrgList", vo);
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
        return (String) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectRsvnNumber", null);
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
    public Map<String, String> insertEvtEdcrsvnInfo(HttpServletRequest request, Map<String, Object> paramMap,
            MemberVO memberVO, EvtEdcrsvnMstVO evtedcrsvnMstVO, EventProgramVO detailVO, MyRsvnVO myRsvnVO)
            throws Exception {

        // Declare
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<String, String> param = new HashMap<String, String>();

        int visitCnt = 0;
        long saleamt = 0;
        long payamt = 0;

        // set parameter
        evtedcrsvnMstVO.setEvtTime(evtedcrsvnMstVO.getEvtVeingdate());
        String dayWeek = (String) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectEvtEdcTimeDay", evtedcrsvnMstVO);
        evtedcrsvnMstVO.setDayWeek(dayWeek);
        CamelMap timeVO = (CamelMap) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectEvtEdcTimeList", evtedcrsvnMstVO);

        long totalCnt = 0;

        /*
         * JYS 2021.05.18
         * for (EvtEdcItemAmountVO charge : evtrsvnMstVO.getChargeList() ) {
         * totalCnt += charge.getItemCnt();
         * }
         */

        int myCnt = commonDAO.queryForInt("EvtEdcrsvnSMainDAO.selectEvtEdcrsvnCnt", evtedcrsvnMstVO);

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
            String rsvnIdx = (String) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectEvtEdcSeq", null);

            log.debug("rsvnIdx = " + rsvnIdx);
            log.debug("getEvtEdcVeingnmpr = " + evtedcrsvnMstVO.getEvtVeingnmpr());

            evtedcrsvnMstVO.setEvtRsvnIdx(rsvnIdx);
            evtedcrsvnMstVO.setEvtPartcd(detailVO.getEvtPartCd());

            // 이벤트 할인 여부 조회
            param.put("COMCD", Config.COM_CD);
            param.put("YMD", evtedcrsvnMstVO.getEvtVeingdate());
            param.put("PART_CD", evtedcrsvnMstVO.getEvtPartcd());
            param.put("PGM_CD", evtedcrsvnMstVO.getEvtNo());
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
                    } else if (evtedcrsvnMstVO.getEvtRsvnMemno() != null && !evtedcrsvnMstVO.getEvtRsvnMemno().equals("") && "Y".equals(discVO.getMemberyn())) {
                        discInfoVO = discVO;
                    } else if ((evtedcrsvnMstVO.getEvtRsvnMemno() == null || evtedcrsvnMstVO.getEvtRsvnMemno().equals("")) && "Y".equals(discVO.getNonmebyn())) {
                        discInfoVO = discVO;
                    }
                }
            }
            // 유료회원 이고 개인신청이면
            if (memberVO != null && "10".equals(evtedcrsvnMstVO.getEvtPersnGbn())) {
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
                        evtedcrsvnMstVO.setDcAnnualLimit(annualData.getLimitQty());
                    }
                }
            }

            // 가격정보 select
            List<EvtEdcItemAmountVO> chargeList = (List<EvtEdcItemAmountVO>) commonDAO.queryForList("EvtEdcrsvnSMainDAO.selectChrgList", evtedcrsvnMstVO);
            List<EvtEdcItemAmountVO> paramCharge = null; // evtrsvnMstVO.getChargeList(); JYS 2021.05.18
            List<EvtEdcRsvnItemVO> chargeDataList = new ArrayList<EvtEdcRsvnItemVO>();

            int limitQty = evtedcrsvnMstVO.getDcAnnualLimit();
            int remainCnt = 0;

            evtedcrsvnMstVO.setEvtRsvnPayamt(String.valueOf(payamt));
            evtedcrsvnMstVO.setEvtRsvnSaleamt(String.valueOf(saleamt));
            //// evtrsvnMstVO.setEvtEdcVeingnmpr(String.valueOf(visitCnt)); JYS 2021.05.18
            evtedcrsvnMstVO.setEvtPartcd(detailVO.getEvtPartCd());
            evtedcrsvnMstVO.setCtgCd(detailVO.getCtgCd());

            /*
             * OLD 2021.07.15
             * if (memberVO != null && "10".equals(evtedcrsvnMstVO.getEvtPersnGbn())) {
             * evtedcrsvnMstVO.setEvtRsvnMemtype(memberVO.getMemGbn());
             * evtedcrsvnMstVO.setEvtRsvnWebid(memberVO.getId());
             * evtedcrsvnMstVO.setEvtRsvnCustnm(memberVO.getMemNm());
             * evtedcrsvnMstVO.setEvtPersnGbn("1001");
             * evtedcrsvnMstVO.setEvtRsvnMoblphon(memberVO.getHp());
             * evtedcrsvnMstVO.setEvtEmail(memberVO.getEmail());
             * }
             */

            evtedcrsvnMstVO.setEvtRsvnMemtype(memberVO.getMemGbn());
            evtedcrsvnMstVO.setEvtRsvnWebid(memberVO.getId());
            evtedcrsvnMstVO.setEvtRsvnCustnm(memberVO.getMemNm());
            evtedcrsvnMstVO.setEvtRsvnMoblphon(memberVO.getHp());
            evtedcrsvnMstVO.setEvtEmail(memberVO.getEmail());

            int noShow = 0;
            int cnt = 0;

            // 무료인 경우 노쇼 체크
            if (saleamt < 1) {
                noShow = commonDAO.queryForInt("EvtEdcrsvnSMainDAO.selectMemNshwCnt", evtedcrsvnMstVO);
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
                 * String payWaitTime = (String)commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectPayWaitTime",
                 * detailVO);
                 * evtrsvnMstVO.setPayWaitTime(payWaitTime);
                 * commonDAO.getExecuteResult("EvtEdcrsvnSMainDAO.insertEvtEdcrsvnMst", evtrsvnMstVO);
                 * cnt++;
                 * }
                 */

                // -------------------------------------------------------------------개인 정보 암화화 시작
                String strSpowiseCmsKey = EgovProperties.getProperty("Globals.SpowiseCms.Key");

                String strEvtEdcVisitMoblphon = String.valueOf(evtedcrsvnMstVO.getEvtVisitMoblphon()).replace("-", "");
                String strEncEvtEdcVisitMoblphon = WebEncDecUtil.fn_encrypt(strEvtEdcVisitMoblphon, strSpowiseCmsKey);

                evtedcrsvnMstVO.setEvtVisitMoblphon(strEncEvtEdcVisitMoblphon);
                // -------------------------------------------------------------------개인 정보 암화화 종료

                if ("10".equals(evtedcrsvnMstVO.getEvtPersnGbn())) {

                    paramMap.put("comcd", Config.COM_CD);

                    String[] strEvtRsvnfmNameArr = request.getParameterValues("evtRsvnfmName");
                    String[] strEvtRsvnfmTelnoArr = request.getParameterValues("evtRsvnfmTelno");

                    paramMap.put("evtRsvnIdx", evtedcrsvnMstVO.getEvtRsvnIdx());

                    for (int i = 0; i < strEvtRsvnfmNameArr.length; i++) {

                        String strEvtRsvnfmName = strEvtRsvnfmNameArr[i];
                        String strEvtRsvnfmTelno = strEvtRsvnfmTelnoArr[i];

                        paramMap.put("evtRsvnFmseq", (i + 1));
                        paramMap.put("evtRsvnfmName", strEvtRsvnfmName);

                        // ---------------------------------------------------------암호화.S
                        strEvtRsvnfmTelno = strEvtRsvnfmTelno.replace("-", "");
                        String strEvtRsvnfmTelnoEnc = WebEncDecUtil.fn_encrypt(strEvtRsvnfmTelno, strSpowiseCmsKey);

                        paramMap.put("evtRsvnfmTelno", strEvtRsvnfmTelnoEnc);
                        // ---------------------------------------------------------암호화.E

                        commonDAO.getExecuteResult("EvtEdcrsvnSMainDAO.insertGrpRsvnFamlyinfo", paramMap);

                    }

                }

                commonDAO.getExecuteResult("EvtEdcrsvnSMainDAO.insertEvtEdcrsvnMst", evtedcrsvnMstVO);
                cnt++;

                // 회차정보 insert
                if (cnt > 0) {
                    cnt = commonDAO.getExecuteResult("EvtEdcrsvnSMainDAO.insertEvtEdcrsvnTime", evtedcrsvnMstVO);
                    if (cnt > 0) {
                        for (EvtEdcRsvnItemVO chargeVO : chargeDataList) {
                            commonDAO.getExecuteResult("EvtEdcrsvnSMainDAO.insertEvtEdcrsvnItem", chargeVO);
                        }
                    }
                }

                if (cnt > 0 && payamt < 1) {
                    myRsvnVO.setComcd(evtedcrsvnMstVO.getComcd());
                    myRsvnVO.setLgdBUYERID(evtedcrsvnMstVO.getEvtRsvnWebid());
                    myRsvnVO.setPartGbn("2001");
                    myRsvnVO.setTerminalType(evtedcrsvnMstVO.getEvtTrmnltype());
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
                    param.put("partGbn", "2001");
                    param.put("rsvnNo", evtedcrsvnMstVO.getEvtRsvnno());
                    param.put("paytime", evtedcrsvnMstVO.getPayWaitTime());
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
     *            EvtEdcrsvnMstVO
     * @return EvtEdcrsvnMstVO
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public EvtEdcrsvnMstVO selectEvtEdcrsvnDetail(EvtEdcrsvnMstVO evtrsvnMstVO) throws Exception {

        EvtEdcrsvnMstVO result = (EvtEdcrsvnMstVO) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectEvtEdcrsvnDetail", evtrsvnMstVO);

        //// result.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));
        result.setComcd(Config.COM_CD);

        if (result != null) {
            List<EvtEdcRsvnItemVO> chargeList = (List<EvtEdcRsvnItemVO>) commonDAO.queryForList("EvtEdcrsvnSMainDAO.selectEvtEdcrsvnItem", evtrsvnMstVO);
            result.setItemList(chargeList);
        }

        return result;
    }

    /**
     * 강연,행사,영화 예약정보, 세부내역을 조회한다.
     * 
     * @param vo
     *            EvtEdcrsvnMstVO
     * @return EvtEdcrsvnMstVO
     * @exception Exception
     */
    public List<?> selectGrpRsvnFamlyinfo(EvtEdcrsvnMstVO evtrsvnMstVO) throws Exception {
        return commonDAO.queryForList("EvtEdcrsvnSMainDAO.selectGrpRsvnFamlyinfo", evtrsvnMstVO);
    }

    /**
     * 강연/행사/영화 예약 횟수를 체크한다.
     * 
     * @param vo
     *            CamelMap
     * @return int
     * @exception Exception
     */
    public int selectEvtEdcrsvnCnt(Map<String, Object> map) throws Exception {
        int result = commonDAO.queryForInt("EvtEdcrsvnSMainDAO.selectEvtEdcrsvnCnt", map);
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
    public Map<String, Object> selectEvtEdcTerms(TermsVO vo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<TermsVO> cList = (List<TermsVO>) commonDAO.queryForList("EvtEdcrsvnSMainDAO.selectEvtEdcTermsList", vo);
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
        int result = commonDAO.queryForInt("EvtEdcrsvnSMainDAO.selectMemNshwCnt", map);
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
    public Map<String, Object> selectEvtEdcRmnAmnt(EvtEdcrsvnMstVO vo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String result = "";
        int visitCnt = vo.getEvtVeingnmpr().equals("") ? 0 : Integer.parseInt(vo.getEvtVeingnmpr());
        int rmCapa = commonDAO.queryForInt("EvtEdcrsvnSMainDAO.selectEvtEdcCapa", vo);

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
     *            EvtEdcrsvnMstVO
     * @return String
     * @exception Exception
     */
    public String selectTicketNumber(EvtEdcrsvnMstVO maserVO) throws Exception {
        return (String) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectTicketNumber", maserVO);
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
        return (String) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectPayWaitTime", ruleVo);
    }

    /**
     * 단체할인
     * 
     * @param ruleVo
     *            ExbtBaseruleVO
     * @return String
     * @exception Exception
     */
    public CamelMap selectEvtEdcGrpDscnt(EvtEdcrsvnMstVO vo) throws Exception {
        return (CamelMap) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectEvtEdcGrpDscnt", vo);
    }

    /**
     * 예약정보 수정
     * 
     * @param vo
     *            EvtEdcrsvnMstVO
     * @return String
     * @exception Exception
     */
    public String updateEvtEdcrsvnData(EvtEdcrsvnMstVO vo) throws Exception {
        int result = commonDAO.getExecuteResult("EvtEdcrsvnSMainDAO.updateEvtEdcRsvnData", vo);
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
     *            EvtEdcrsvnMstVO
     * @return String
     * @exception Exception
     */
    public String updateEvtEdcrsvnItem(MemberVO memberVO, EvtEdcrsvnMstVO vo) throws Exception {
        EvtEdcrsvnMstVO data = (EvtEdcrsvnMstVO) commonDAO.queryForObject("EvtEdcrsvnSMainDAO.selectEvtEdcrsvnDetail", vo);
        if (data != null) {
            int prevMemCount = Integer.parseInt(data.getEvtVeingnmpr());
            int totalCnt = 0;

            for (EvtEdcItemAmountVO paramVO : vo.getChargeList()) {
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

                    commonDAO.getExecuteResult("MyRsvnEvtEdcDAO.updateEvtEdcCouponCancel", myRsvnVO);
                    // 기존 데이타 삭제
                    commonDAO.getExecuteResult("EvtEdcrsvnSMainDAO.deleteEvtEdcItemAll", vo);

                    List<EvtEdcItemAmountVO> chargeList = (List<EvtEdcItemAmountVO>) commonDAO.queryForList("EvtEdcrsvnSMainDAO.selectChrgList", vo);
                    List<EvtEdcItemAmountVO> paramCharge = vo.getChargeList();

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
                    CamelMap groupdisc = selectEvtEdcGrpDscnt(vo);

                    if (chargeList != null && paramCharge != null) {
                        for (EvtEdcItemAmountVO paramVO : paramCharge) {
                            for (EvtEdcItemAmountVO chargeVO : chargeList) {
                                if (paramVO.getItemCd().equals(chargeVO.getItemCd()) && paramVO.getItemCnt() > 0) {
                                    EvtEdcRsvnItemVO itemVO = new EvtEdcRsvnItemVO();
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

                                                EvtEdcRsvnItemVO chargeVO2 = new EvtEdcRsvnItemVO();
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

                                                commonDAO.getExecuteResult("EvtEdcrsvnSMainDAO.insertEvtEdcrsvnItem", chargeVO2);

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

                                        commonDAO.getExecuteResult("EvtEdcrsvnSMainDAO.insertEvtEdcrsvnItem", itemVO);
                                    }
                                }
                            }
                        }
                    }

                    int n = commonDAO.getExecuteResult("EvtEdcrsvnSMainDAO.updateEvtEdcMstAmt", myRsvnVO);

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
