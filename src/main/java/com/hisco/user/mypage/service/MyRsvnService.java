package com.hisco.user.mypage.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Constant;
import com.hisco.intrfc.charge.mapper.ChargeMapper;
import com.hisco.intrfc.sale.service.SaleChargeService;
import com.hisco.intrfc.sale.service.TossService;
import com.hisco.intrfc.sale.vo.TossResponseVO;
import com.hisco.intrfc.sale.vo.VbankPaymentInfoVO;
import com.hisco.intrfc.sms.service.BizMsgService;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcRsvnItemVO;
import com.hisco.user.evtedcrsvn.service.EvtEdcrsvnMstVO;
import com.hisco.user.evtrsvn.service.EvtRsvnItemVO;
import com.hisco.user.evtrsvn.service.EvtrsvnMstVO;
import com.hisco.user.exbtrsvn.service.ExbtChargeVO;
import com.hisco.user.exbtrsvn.service.RsvnMasterVO;
import com.hisco.user.mypage.mapper.MyRsvnEdcMapper;
import com.hisco.user.mypage.mapper.MyRsvnMapper;
import com.hisco.user.mypage.mapper.RsvnCommMapper;
import com.hisco.user.mypage.vo.MyRsvnVO;
import com.hisco.user.mypage.vo.RsvnCommVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 예약 정보 조회
 *
 * @author 진수진
 * @since 2020.09.14
 * @version 1.0, 2020.09.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.09.14 최초작성
 */
@Slf4j
@Service("myRsvnService")
public class MyRsvnService extends EgovAbstractServiceImpl {

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    @Resource(name = "myRsvnMapper")
    private MyRsvnMapper myRsvnMapper;

    @Resource(name = "rsvnCommMapper")
    private RsvnCommMapper rsvnCommMapper;

    @Resource(name = "myRsvnEdcMapper")
    private MyRsvnEdcMapper myRsvnEdcMapper;

    @Resource(name = "chargeMapper")
    private ChargeMapper chargeMapper;

    @Resource(name = "tossService")
    private TossService tossService;

    @Resource(name = "saleChargeService")
    private SaleChargeService saleChargeService;

    @Resource(name = "bizMsgService")
    private BizMsgService bizMsgService;

    /**
     * 로그인 정보로 회원의 결제대기 수량 조회
     *
     * @param vo
     *            LoginVO
     * @return Map
     * @exception Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map<?, ?> selectReserveCountData(LoginVO vo) throws Exception {

        Map<String, String> exbtData = (Map<String, String>) commonDAO.queryForObject("MyRsvnExbtDAO.selectReserveCountData", vo);
        Map<String, String> edcData = (Map<String, String>) commonDAO.queryForObject("MyRsvnEdcDAO.selectReserveCountData", vo);
        Map<String, String> edcGrpData = (Map<String, String>) commonDAO.queryForObject("MyRsvnEdcDAO.selectReserveCountGrpData", vo);

        Map<String, String> evtedcData = (Map<String, String>) commonDAO.queryForObject("MyRsvnEvtEdcDAO.selectReserveCountData", vo);

        Map<String, String> evtData = (Map<String, String>) commonDAO.queryForObject("MyRsvnEvtDAO.selectReserveCountData", vo);

        HashMap<String, Map> map = new HashMap<String, Map>();

        map.put("exbtData", exbtData);
        map.put("edcData", edcData);
        map.put("edcGrpData", edcGrpData);
        map.put("evtedcData", evtedcData);
        map.put("evtData", evtData);

        return map;
    }

    /**
     * 로그인 정보로 회원의 관람 예약 목록
     *
     * @param vo
     *            LoginVO
     * @return List<RsvnMasterVO>
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<RsvnMasterVO> selectExbtMasterList(LoginVO vo, String mode) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("comcd", vo.getComcd());
        map.put("uniqId", vo.getUniqId());
        map.put("hpcertno", vo.getHpcertno());
        map.put("mode", mode);

        return (List<RsvnMasterVO>) commonDAO.queryForList("MyRsvnExbtDAO.selectExbtMasterList", map);
    }

    /**
     * 관람 예약 결제 선택 목록
     *
     * @param vo
     *            LoginVO
     * @return Map
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<RsvnMasterVO> selectExbtPayList(MyRsvnVO myRsvnVO, String appGbn) throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("COMCD", myRsvnVO.getComcd());

        List<RsvnMasterVO> list = (List<RsvnMasterVO>) commonDAO.queryForList("MyRsvnExbtDAO.selectExbtMasterList", myRsvnVO);
        RsvnCommVO discOpt = (RsvnCommVO) rsvnCommMapper.selectOrgOptInfo(myRsvnVO);

        myRsvnVO.setUpdownAmtUnit(discOpt.getRefundUpdownUnit());
        myRsvnVO.setUpdownAmtGbn(discOpt.getRefundUpdown());

        for (RsvnMasterVO vo : list) {
            List<ExbtChargeVO> chargeList = (List<ExbtChargeVO>) commonDAO.queryForList("DspyDsDAO.selectReserveItemList", vo);
            vo.setChargeList(chargeList);

            long cancelLong = 0;
            long breakLong = 0;
            String prevItemCd = "";
            String itemInfo = "";
            for (ExbtChargeVO itemVO : chargeList) {
                if (!prevItemCd.equals(itemVO.getItemCd())) {
                    if (!itemInfo.equals(""))
                        itemInfo += "\n  ";
                    itemInfo += itemVO.getItemNm() + " : " + itemVO.getItemCnt() + "명";
                    prevItemCd = itemVO.getItemCd();
                }
            }

            vo.setItemInfo(itemInfo);

            // 환불 기준 조회
            if ("complete".equals(myRsvnVO.getMode())) {
                myRsvnVO.setRsvnIdxOne(vo.getRsvnIdx());
                myRsvnVO.setComcd(vo.getComcd());
                myRsvnVO.setPartCd(vo.getExbtPartcd());
                String rfndRate = "100";

                // 결제 취소 실패 상태인경우
                if (appGbn.equals("2") && vo.getApptype().equals("20")) {
                    vo.setRfndRule(selectDefaultRefundRule());
                } else {
                    vo.setRfndRule(myRsvnMapper.selectRefundRuleData(myRsvnVO));
                    rfndRate = CommonUtil.getString(vo.getRfndRule().get("rfndRate"));
                }

                if (rfndRate.equals(""))
                    rfndRate = "100";

                for (ExbtChargeVO itemVO : chargeList) {
                    if (vo.getApptype().startsWith("3")) {
                        cancelLong += itemVO.getCancelAmt();
                        breakLong += itemVO.getBreakAmt();

                    } else {
                        long cancelAmt = CommonUtil.DoubleToLongCalc((itemVO.getItemAmount() * 0.01) * Double.parseDouble(rfndRate), discOpt.getRefundUpdownUnit(), discOpt.getRefundUpdown());
                        itemVO.setCancelAmt(cancelAmt);

                        cancelLong += cancelAmt;
                    }
                }

                // log.debug("===============================");
                // log.debug(CommonUtil.RuleText(vo.getRfndRule()));
                // log.debug("===============================");

                vo.setCancelAmt(cancelLong);
                vo.setBreakAmt(breakLong);
                vo.setRfndRate(rfndRate);
            }
        }

        return list;
    }

    /**
     * 예약 취소 (결제없음)
     *
     * @param vo
     *            RsvnMasterVO
     * @return
     * @exception Exception
     */
    public String updateReserveCancel(MyRsvnVO myRsvnVO) throws Exception {
        String returnMsg = "";
        int cnt = 0;
        boolean callProce = false;

        myRsvnVO.setRsvnIdx(Arrays.asList(myRsvnVO.getRsvnIdxOne()));
        EdcRsvnInfoVO data = myRsvnEdcMapper.selectEdcDetailData(myRsvnVO);
        // 교육취소
        myRsvnVO.setLgdOID(data.getOid());
        myRsvnVO.setOrderId(data.getOid());
        myRsvnVO.setRsvnNo(data.getEdcRsvnNo());
        // myRsvnVO.setAppDate(CommonUtil.getDateString2(data.getRegdate()));

        // 결제 대기상태 이거나 무료 이고 취소 가능한 상태이면
        if (Constant.SM_RSVN_STAT_입금대기.equals(data.getEdcStat())) {
            // 예약 취소
            cnt = myRsvnEdcMapper.updateEdcMasterCancel(myRsvnVO);
        } else if (Constant.SM_RSVN_STAT_등록완료.equals(data.getEdcStat()) && (data.getOid() == null || "".equals(data.getOid()))) {
            // 예약취소
            cnt = myRsvnEdcMapper.updateEdcMasterCancel(myRsvnVO);
            // 매출 취소 시킨다
        }

        if (cnt > 0) {
            returnMsg = "OK";
        } else {

        }

        return returnMsg;

    }

    /**
     * 입금대기(2001), 배정대기(1000), 추첨대기(1002) => 3001(취소)
     */
    public int updateReserveCancel(List<EdcRsvnInfoVO> rsvnList) throws Exception {
        int cnt = 0;
        MyRsvnVO myRsvnVO = new MyRsvnVO();

        for (EdcRsvnInfoVO vo : rsvnList) {
            // 결제 대기상태 이거나 무료 이고 취소 가능한 상태이면
            if (Constant.ING_RSVN_STAT_LIST.contains(vo.getEdcStat())) {// 예약 취소
                myRsvnVO.setComcd(vo.getComcd());
                myRsvnVO.setRsvnIdxOne(String.valueOf(vo.getEdcRsvnReqid()));
                cnt += myRsvnEdcMapper.updateEdcMasterCancel(myRsvnVO); // 기존 edc_stat -> 3001로 변경

                bizMsgService.sendRsvnMessage(vo.getEdcRsvnReqid());
            }
        }

        EdcRsvnInfoVO rsvnVO = rsvnList.get(0);

        // 발급한 가상계좌가 있으면 취소
        if (StringUtils.isNotBlank(rsvnVO.getMid()) && StringUtils.isNotBlank(rsvnVO.getTid())) {
            TossResponseVO tossCancelVO = tossService.cancel(rsvnVO.getMid(), rsvnVO.getTid(), rsvnVO.getRetDpstrNm(), rsvnVO.getRetBankCd(), rsvnVO.getRetAcountNum(), rsvnVO.getEdcRsvnMoblphon());

            if (!TossService.TOSS_CANCEL_OK_LIST.contains(tossCancelVO.getLGD_RESPCODE())) { // PG결제취소성공
                throw new RuntimeException(tossCancelVO.getLGD_RESPMSG());
            } else {
                VbankPaymentInfoVO vo = new VbankPaymentInfoVO();
                vo.setComcd(rsvnVO.getComcd());
                vo.setVbankSeq(rsvnVO.getVbankSeq());
                vo.setVbankStatus(Constant.SM_VBANK_PAYMENT_STATUS_입금신청취소);
                vo.setVbankEtc(tossCancelVO.getLGD_RESPCODE() + "|" + tossCancelVO.getLGD_RESPMSG());
                saleChargeService.completeVbankPaymentInfo(vo);
            }
        }

        return cnt;
    }

    /**
     * 결제 취소
     *
     * @param param
     *            Map
     * @return int
     * @exception Exception
     */
    public int updateCardAppCancel(MyRsvnVO myRsvnVO) throws Exception {
        // card history update
        int n = 0;
        if (myRsvnVO.getPartialYn() != null && myRsvnVO.getPartialYn().equals("Y")) {
            if (myRsvnVO.getOldPcancelNo() == null || "".equals(myRsvnVO.getOldPcancelNo())) {
                n = chargeMapper.insertCardAppHistoryForCancel(myRsvnVO);
            } else {
                n = 1;
            }
        } else {
            // 취소 상태로 업데이트
            chargeMapper.updateCardAppHistoryForCancel(myRsvnVO);
            n = 1;
        }
        return n;
    }

    /**
     * 결제 취소
     *
     * @param param
     *            Map
     * @return int
     * @exception Exception
     */
    public int updatePayCancel(MyRsvnVO myRsvnVO) throws Exception {

        int n = 0;
        // 결제 취소 프로시져 호출

        chargeMapper.insertReturnProc(myRsvnVO);

        if ("OK".equals(myRsvnVO.getRetCd())) {
            n = 1;
        } else {
            n = 0;
        }

        return n;
    }

    /**
     * 강연/행사/영화 로그인 정보로 예약정보를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return MemberVO
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<EvtrsvnMstVO> selectMyEvtList(LoginVO vo, String mode) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("comcd", vo.getComcd());
        map.put("uniqId", vo.getUniqId());
        map.put("hpcertno", vo.getHpcertno());
        map.put("mode", mode);

        return (List<EvtrsvnMstVO>) commonDAO.queryForList("MyRsvnEvtDAO.selectMyEvtRsvnList", map);

    }

    /**
     * 단체교육 로그인 정보로 예약정보를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return MemberVO
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<EvtEdcrsvnMstVO> selectMyEvtEdcList(LoginVO vo, String mode) throws Exception {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("comcd", vo.getComcd());
        map.put("uniqId", vo.getUniqId());
        map.put("hpcertno", vo.getHpcertno());
        map.put("mode", mode);

        return (List<EvtEdcrsvnMstVO>) commonDAO.queryForList("MyRsvnEvtEdcDAO.selectMyEvtEdcRsvnList", map);

    }

    /**
     * 강연/행사/영화 결제 세부 목록을 조회한다
     *
     * @param myRsvnVO
     *            MyRsvnVO
     * @return List<EvtrsvnMstVO>
     * @exception Exception
     */

    public CamelMap selectDefaultRefundRule() {
        CamelMap map = new CamelMap();

        map.put("rfndNofday", 0);
        map.put("rfndRate", 100);
        map.put("validYn", "Y");
        return map;
    }

    @SuppressWarnings("unchecked")
    public List<EvtrsvnMstVO> selectEvtPaylist(MyRsvnVO myRsvnVO, String appGbn) throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("COMCD", myRsvnVO.getComcd());

        RsvnCommVO discOpt = (RsvnCommVO) rsvnCommMapper.selectOrgOptInfo(myRsvnVO);

        myRsvnVO.setUpdownAmtUnit(discOpt.getRefundUpdownUnit());
        myRsvnVO.setUpdownAmtGbn(discOpt.getRefundUpdown());

        List<EvtrsvnMstVO> list = (List<EvtrsvnMstVO>) commonDAO.queryForList("MyRsvnEvtDAO.selectMyEvtRsvnList", myRsvnVO);

        for (EvtrsvnMstVO vo : list) {
            List<EvtRsvnItemVO> itemList = (List<EvtRsvnItemVO>) commonDAO.queryForList("EvtrsvnSMainDAO.selectEvtrsvnItem", vo);
            vo.setItemList(itemList);

            long cancelLong = 0; // 환불 가능 금액
            long breakLong = 0; // 공제금액

            String prevItemCd = "";
            String itemInfo = "";
            for (EvtRsvnItemVO itemVO : itemList) {
                if (!prevItemCd.equals(itemVO.getEvtRsvnItemcd())) {
                    if (!itemInfo.equals(""))
                        itemInfo += "\n  ";
                    itemInfo += itemVO.getEvtItemNm() + " : " + itemVO.getEvtRsvnItemcnt() + "명";
                    prevItemCd = itemVO.getEvtRsvnItemcd();
                }
            }

            vo.setItemInfo(itemInfo);

            // 환불 기준 조회
            if ("complete".equals(myRsvnVO.getMode())) {
                myRsvnVO.setRsvnIdxOne(vo.getEvtRsvnIdx());
                myRsvnVO.setComcd(vo.getComcd());
                myRsvnVO.setPartCd(vo.getEvtPartcd());

                String rfndRate = "100";
                // 결제 상태인경우
                if (appGbn.equals("2") && vo.getEvtRsvnApptype().equals("20")) {
                    vo.setRfndRule(selectDefaultRefundRule());
                } else {
                    vo.setRfndRule((CamelMap) myRsvnMapper.selectRefundRuleData(myRsvnVO));
                    rfndRate = CommonUtil.getString(vo.getRfndRule().get("rfndRate"));
                }

                if (rfndRate.equals(""))
                    rfndRate = "100";

                for (EvtRsvnItemVO itemVO : itemList) {
                    if (vo.getEvtRsvnApptype().startsWith("3")) {
                        cancelLong += itemVO.getCancelAmt();
                        breakLong += itemVO.getBreakAmt();
                    } else {
                        long cancelAmt = CommonUtil.DoubleToLongCalc((itemVO.getItemAmount() * 0.01) * Double.parseDouble(rfndRate), discOpt.getRefundUpdownUnit(), discOpt.getRefundUpdown());
                        itemVO.setCancelAmt(cancelAmt);

                        cancelLong += cancelAmt;
                    }
                }

                vo.setCancelAmt(cancelLong);
                vo.setBreakAmt(breakLong);
                vo.setRfndRate(rfndRate);
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<EvtEdcrsvnMstVO> selectEvtEdcPaylist(MyRsvnVO myRsvnVO, String appGbn) throws Exception {

        Map<String, String> param = new HashMap<String, String>();
        param.put("COMCD", myRsvnVO.getComcd());

        RsvnCommVO discOpt = (RsvnCommVO) rsvnCommMapper.selectOrgOptInfo(myRsvnVO);

        myRsvnVO.setUpdownAmtUnit(discOpt.getRefundUpdownUnit());
        myRsvnVO.setUpdownAmtGbn(discOpt.getRefundUpdown());

        List<EvtEdcrsvnMstVO> list = (List<EvtEdcrsvnMstVO>) commonDAO.queryForList("MyRsvnEvtEdcDAO.selectMyEvtEdcRsvnList", myRsvnVO);

        for (EvtEdcrsvnMstVO vo : list) {
            List<EvtEdcRsvnItemVO> itemList = (List<EvtEdcRsvnItemVO>) commonDAO.queryForList("EvtEdcrsvnSMainDAO.selectEvtEdcrsvnItem", vo);
            vo.setItemList(itemList);

            long cancelLong = 0; // 환불 가능 금액
            long breakLong = 0; // 공제금액

            String prevItemCd = "";
            String itemInfo = "";
            for (EvtEdcRsvnItemVO itemVO : itemList) {
                if (!prevItemCd.equals(itemVO.getEvtRsvnItemcd())) {
                    if (!itemInfo.equals(""))
                        itemInfo += "\n  ";
                    itemInfo += itemVO.getEvtItemNm() + " : " + itemVO.getEvtRsvnItemcnt() + "명";
                    prevItemCd = itemVO.getEvtRsvnItemcd();
                }
            }

            vo.setItemInfo(itemInfo);

            // 환불 기준 조회
            if ("complete".equals(myRsvnVO.getMode())) {
                myRsvnVO.setRsvnIdxOne(vo.getEvtRsvnIdx());
                myRsvnVO.setComcd(vo.getComcd());
                myRsvnVO.setPartCd(vo.getEvtPartcd());

                String rfndRate = "100";
                // 결제 상태인경우
                if (appGbn.equals("2") && vo.getEvtRsvnApptype().equals("20")) {
                    vo.setRfndRule(selectDefaultRefundRule());
                } else {
                    vo.setRfndRule((CamelMap) myRsvnMapper.selectRefundRuleData(myRsvnVO));
                    rfndRate = CommonUtil.getString(vo.getRfndRule().get("rfndRate"));
                }

                if (rfndRate.equals(""))
                    rfndRate = "100";

                for (EvtEdcRsvnItemVO itemVO : itemList) {
                    if (vo.getEvtRsvnApptype().startsWith("3")) {
                        cancelLong += itemVO.getCancelAmt();
                        breakLong += itemVO.getBreakAmt();
                    } else {
                        long cancelAmt = CommonUtil.DoubleToLongCalc((itemVO.getItemAmount() * 0.01) * Double.parseDouble(rfndRate), discOpt.getRefundUpdownUnit(), discOpt.getRefundUpdown());
                        itemVO.setCancelAmt(cancelAmt);

                        cancelLong += cancelAmt;
                    }
                }

                vo.setCancelAmt(cancelLong);
                vo.setBreakAmt(breakLong);
                vo.setRfndRate(rfndRate);
            }
        }
        return list;
    }

    /**
     * 강연/행사/영화 결제 완료 목록 페이징
     *
     * @param myRsvnVO
     *            MyRsvnVO
     * @return List<EvtrsvnMstVO>
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<EvtrsvnMstVO> selectMyEvtPagingList(MyRsvnVO myRsvnVO) throws Exception {
        return (List<EvtrsvnMstVO>) commonDAO.queryForList("MyRsvnEvtDAO.selectMyEvtRsvnPagingList", myRsvnVO);
    }

    /**
     * 상세정보를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return MemberVO
     * @exception Exception
     */
    public Map<String, Object> selectMyEvtrsvnDtl(Map<String, Object> map) throws Exception {
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        EvtrsvnMstVO result = (EvtrsvnMstVO) commonDAO.queryForObject("MyRsvnEvtDAO.selectMyEvtRsvn", map);
        rtnMap.put("dtl", result);
        return rtnMap;
    }

    /**
     * 세부내역을 조회한다
     *
     * @param vo
     *            LoginVO
     * @return MemberVO
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectMyEvtrsvnItem(Map<String, Object> map) throws Exception {
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        List<EvtRsvnItemVO> result = (List<EvtRsvnItemVO>) commonDAO.queryForList("MyRsvnEvtDAO.selectEvtrsvnItem", map);
        rtnMap.put("list", result);
        return rtnMap;
    }

    /**
     * 사업장 환경 정보
     *
     * @param myRsvnVO
     *            MyRsvnVO
     * @return Map
     * @exception Exception
     */
    public MyRsvnVO selectPartSystem(MyRsvnVO myRsvnVO) throws Exception {
        return (MyRsvnVO) myRsvnMapper.selectPartSystem(myRsvnVO);
    }

    /**
     * 결제 후 저장한다
     *
     * @param Map
     * @throws Exception
     */
    public boolean insertPaymentInfo(Map<String, Object> paramMap, MyRsvnVO myRsvnVO) throws Exception {

        // 카드 사별 수수료 구한다
        CamelMap map = (CamelMap) chargeMapper.selectPaymethodRate(paramMap);

        if (map != null && map.get("cardRate") != null) {
            double rate = Double.parseDouble(String.valueOf(map.get("cardRate")));
            double rateAmt = Math.round(Double.parseDouble((String) paramMap.get("LGD_AMOUNT")) * rate) / 100.0;
            paramMap.put("CARDANDBANK_RATE_AMT", Double.toString(rateAmt)); // 수수료금액
            paramMap.put("CARDANDBANK_RATE", Double.toString(rate)); // 수수료율
        } else {
            paramMap.put("CARDANDBANK_RATE_AMT", "0"); // 수수료금액
            paramMap.put("CARDANDBANK_RATE", "0"); // 수수료율
        }

        // PG 거래 승인내역을 저장한다
        paramMap.put("comcd", myRsvnVO.getComcd());
        int n = 0;
        int chk = 0;
        if ("0".equals((String) paramMap.get("LGD_AMOUNT"))) {
            n = 1; // 0원 결제 일경우
        } else {

            // 중복 체크
            chk = (int) chargeMapper.selectCardAppHistoryCnt(paramMap);
            if (chk == 0) {
                n = chargeMapper.insertCardAppHistory(paramMap);
            } else {
                n = 1;
            }
        }

        // log.debug((String)paramMap.get("LGD_AMOUNT"));
        // log.debug("n = " + n);

        if (n > 0 && chk == 0) {
            // 결제 완료 프로시져 호출
            String partGbn = "9001";
            String productCode = EgovStringUtil.isNullToString(paramMap.get("LGD_PRODUCTCODE"));
            if (productCode.equals("EXBT")) {
                partGbn = "1001";
            } else if (productCode.equals("EDC")) {
                partGbn = "2001";
            } else if (productCode.equals("EVT")) {
                partGbn = "3001";
            }

            myRsvnVO.setPartGbn(partGbn);
            myRsvnVO.setTerminalType((String) paramMap.get("TERMINAL_TYPE"));

            /*
             * 노원결제없음 막음 JYS 2021.03.17
             * chargeMapper.insertPaymentProc", myRsvnVO);
             * if ("OK".equals(myRsvnVO.getRetCd())) {
             * n = 1;
             * } else {
             * n = 0;
             * }
             */

            n = 1; /// 노원결제없음 JYS 2021.03.17

        }

        return n > 0;
    }

    /**
     * PG 거래승인내역 조회
     *
     * @param myRsvnVO
     *            MyRsvnVO
     * @return Map
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectCardAppHistData(MyRsvnVO myRsvnVO) throws Exception {
        return (Map<String, Object>) myRsvnMapper.selectCardAppHistData(myRsvnVO);
    }

    /**
     * 관람 취소 선택 금액 조회
     *
     * @param myRsvnVO
     *            MyRsvnVO
     * @return Map
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectExbtCancelInfo(MyRsvnVO myRsvnVO, String appGbn) throws Exception {
        myRsvnVO.setMode("complete");
        long cancelLong = 0;
        int validCnt = 0;

        Map<String, Object> cancelInfo = (Map<String, Object>) commonDAO.queryForObject("MyRsvnExbtDAO.selectExbtCancelInfo", myRsvnVO);

        List<RsvnMasterVO> payList = selectExbtPayList(myRsvnVO, appGbn);
        for (RsvnMasterVO vo : payList) {
            if (vo.getApptype().equals("20") && vo.getTicketChkCnt() < 1) {
                validCnt++;
            }

            for (ExbtChargeVO item : vo.getChargeList()) {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("comcd", myRsvnVO.getComcd());
                paramMap.put("saleSeq", item.getSelngId());
                // 취소 temp 있을 경우 삭제
                myRsvnMapper.deleteCancelTemp(paramMap);

                paramMap.put("memNo", myRsvnVO.getUniqId());
                paramMap.put("cancelCd", myRsvnVO.getCancelCd());
                paramMap.put("returnAmt", item.getCancelAmt());
                if ("Y".equals(item.getVatYn())) {
                    long vatAmt = CommonUtil.DoubleToLongCalc((item.getCancelAmt() * 1.0) / 11.0, myRsvnVO.getUpdownAmtUnit(), myRsvnVO.getUpdownAmtGbn());
                    paramMap.put("vatAmt", vatAmt);
                } else {
                    paramMap.put("vatAmt", 0);
                }

                if (cancelInfo != null && cancelInfo.get("cardRate") != null) {
                    double rate = Double.parseDouble(String.valueOf(cancelInfo.get("cardRate")));
                    double rateAmt = Math.round((item.getCancelAmt() * 1.0) * rate) / 100.0;
                    paramMap.put("rateAmt", Double.toString(rateAmt)); // 수수료금액
                }

                paramMap.put("breakAmt", item.getItemAmount() - item.getCancelAmt());
                paramMap.put("useAmt", "0");
                paramMap.put("updownAmtUnit", myRsvnVO.getUpdownAmtUnit());
                paramMap.put("updownAmtGbn", myRsvnVO.getUpdownAmtGbn());
                paramMap.put("reguser", myRsvnVO.getModuser());
                // 취소 데이타 입력
                myRsvnMapper.insertCancelTemp(paramMap);
            }

            cancelLong += vo.getCancelAmt();

        }

        cancelInfo.put("cancelAmt", cancelLong);
        cancelInfo.put("validCnt", validCnt);

        return cancelInfo;
    }

    /**
     * 강연/행사/영화 취소 선택 금액 조회
     *
     * @param myRsvnVO
     *            MyRsvnVO
     * @return Map
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectEvtCancelInfo(MyRsvnVO myRsvnVO, String appGbn) throws Exception {
        myRsvnVO.setMode("complete");
        long cancelLong = 0;
        int validCnt = 0;
        Map<String, Object> cancelInfo = (Map<String, Object>) commonDAO.queryForObject("MyRsvnEvtDAO.selectEvtCancelInfo", myRsvnVO);

        List<EvtrsvnMstVO> payList = selectEvtPaylist(myRsvnVO, appGbn);

        for (EvtrsvnMstVO vo : payList) {
            if (vo.getEvtRsvnApptype().equals("20") && vo.getTicketChkCnt() < 1) {
                validCnt++;
            }
            for (EvtRsvnItemVO item : vo.getItemList()) {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("comcd", myRsvnVO.getComcd());
                paramMap.put("saleSeq", item.getSelngId());
                // 취소 temp 있을 경우 삭제
                myRsvnMapper.deleteCancelTemp(paramMap);

                paramMap.put("memNo", myRsvnVO.getUniqId());
                paramMap.put("cancelCd", myRsvnVO.getCancelCd());
                paramMap.put("returnAmt", item.getCancelAmt());
                if ("Y".equals(item.getVatYn())) {
                    long vatAmt = CommonUtil.DoubleToLongCalc((item.getCancelAmt() * 1.0) / 11.0, "", ""); // 부가세 계산
                    paramMap.put("vatAmt", vatAmt);
                } else {
                    paramMap.put("vatAmt", 0);
                }

                if (cancelInfo != null && cancelInfo.get("cardRate") != null) {
                    double rate = Double.parseDouble(String.valueOf(cancelInfo.get("cardRate")));
                    double rateAmt = Math.round((item.getCancelAmt() * 1.0) * rate) / 100.0;
                    paramMap.put("rateAmt", Double.toString(rateAmt)); // 수수료금액
                }

                paramMap.put("breakAmt", item.getItemAmount() - item.getCancelAmt());
                paramMap.put("useAmt", "0");
                paramMap.put("updownAmtUnit", myRsvnVO.getUpdownAmtUnit());
                paramMap.put("updownAmtGbn", myRsvnVO.getUpdownAmtGbn());
                paramMap.put("reguser", myRsvnVO.getModuser());
                // 취소 데이타 입력
                myRsvnMapper.insertCancelTemp(paramMap);
            }

            cancelLong += vo.getCancelAmt();

        }

        cancelInfo.put("cancelAmt", cancelLong);
        cancelInfo.put("validCnt", validCnt);

        return cancelInfo;
    }

    /**
     * 교육 로그인 정보로 예약정보를 조회한다(개인)
     *
     * @param vo
     *            LoginVO
     * @return MemberVO
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<EdcRsvnInfoVO> selectMyEdcList(Map<String, Object> map) throws Exception {

        List<EdcRsvnInfoVO> list = (List<EdcRsvnInfoVO>) myRsvnEdcMapper.selectMyEdcRsvnList(map);
        if (list != null) {
            for (EdcRsvnInfoVO item : list) {
                // item.setEduPeriod(CommonUtil.EduPeriodDate(item, "N"));
            }
        }

        return list;

    }

    /**
     * 교육 결제 세부 목록을 조회한다
     *
     * @param myRsvnVO
     *            MyRsvnVO
     * @return List<EvtrsvnMstVO>
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<EdcRsvnInfoVO> selectEdcPaylist(MyRsvnVO myRsvnVO, String appGbn) throws Exception {
        /*
         * Map<String, String> param = new HashMap<String, String>();
         * param.put("COMCD", myRsvnVO.getComcd());
         * RsvnCommVO discOpt = (RsvnCommVO) rsvnCommMapper.selectOrgOptInfo(myRsvnVO);
         * myRsvnVO.setUpdownAmtUnit(discOpt.getRefundUpdownUnit());
         * myRsvnVO.setUpdownAmtGbn(discOpt.getRefundUpdown());
         */
        List<EdcRsvnInfoVO> list = (List<EdcRsvnInfoVO>) myRsvnEdcMapper.selectMyEdcRsvnList(myRsvnVO);

        /*
         * for (EdcRsvnMstVO vo : list) {
         * vo.setEduPeriod(CommonUtil.EduPeriodDate(vo));
         * // 환불 기준 조회
         * if ("complete".equals(myRsvnVO.getMode())) {
         * myRsvnVO.setRsvnIdxOne(vo.getEdcRsvnReqid());
         * myRsvnVO.setComcd(vo.getComcd());
         * myRsvnVO.setPartCd(vo.getEdcPartcd());
         * String rfndRate = "100";
         * // 결제 취소 실패 상태인경우
         * if (appGbn.equals("2") && vo.getEdcStat().equals("20")) {
         * vo.setRfndRule(selectDefaultRefundRule());
         * } else {
         * vo.setRfndRule((CamelMap) myRsvnMapper.selectRefundRuleData(myRsvnVO));
         * rfndRate = CommonUtil.getString(vo.getRfndRule().get("rfndRate"));
         * }
         * if (rfndRate.equals(""))
         * rfndRate = "100";
         * if (!vo.getEdcStat().startsWith("3")) {
         * long cancelAmt = CommonUtil.DoubleToLongCalc((vo.getEdcTotamt() * 0.01) * Double.parseDouble(rfndRate),
         * discOpt.getRefundUpdownUnit(), discOpt.getRefundUpdown());
         * vo.setCancelAmt(cancelAmt);
         * }
         * vo.setRfndRate(rfndRate);
         * }
         * }
         */

        return list;
    }

    /**
     * 교육 상세 정보를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return MemberVO
     * @exception Exception
     */
    public EdcRsvnInfoVO selectMyEdcRsvnDtl(MyRsvnVO myRsvnVO) throws Exception {
        return myRsvnEdcMapper.selectMyEdcRsvnDtl(myRsvnVO);
    }

    /**
     * 교육 결제 완료 목록 페이징
     *
     * @param myRsvnVO
     *            MyRsvnVO
     * @return List<EvtrsvnMstVO>
     * @exception Exception
     */
    public List<EdcRsvnInfoVO> selectMyEdcPagingList(MyRsvnVO myRsvnVO) throws Exception {
        return myRsvnEdcMapper.selectMyEdcPagingList(myRsvnVO);
    }

    public List<EdcRsvnInfoVO> selectMyPgHistory(MyRsvnVO myRsvnVO) throws Exception {
        return myRsvnEdcMapper.selectMyPgHistory(myRsvnVO);
    }

    /**
     * 관람 예약 상세 조회
     *
     * @param myRsvnVO
     *            MyRsvnVO
     * @return RsvnMasterVO
     * @exception Exception
     */
    public RsvnMasterVO selectExbtDetailData(MyRsvnVO myRsvnVO) throws Exception {
        RsvnMasterVO data = (RsvnMasterVO) commonDAO.queryForObject("MyRsvnExbtDAO.selectExbtDetailData", myRsvnVO);
        if (data != null) {
            data.setRfndRule((CamelMap) myRsvnMapper.selectRefundRuleData(myRsvnVO));
        }
        return data;
    }

    /**
     * 강연/행사/영화 예약 상세 조회
     *
     * @param myRsvnVO
     *            MyRsvnVO
     * @return EvtrsvnMstVO
     * @exception Exception
     */
    public EvtrsvnMstVO selectEvtDetailData(MyRsvnVO myRsvnVO) throws Exception {
        EvtrsvnMstVO data = (EvtrsvnMstVO) commonDAO.queryForObject("MyRsvnEvtDAO.selectEvtDetailData", myRsvnVO);
        if (data != null) {
            data.setRfndRule((CamelMap) myRsvnMapper.selectRefundRuleData(myRsvnVO));
        }
        return data;
    }

    /**
     * 교육 상세 정보를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return MemberVO
     * @exception Exception
     */
    public CamelMap selectMyMathRsvnDtl(Map<String, Object> map) throws Exception {
        return (CamelMap) commonDAO.queryForObject("MyRsvnMathDAO.selectMyMathRsvnDtl", map);
    }

}
