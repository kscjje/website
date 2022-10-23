package com.hisco.user.mypage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.user.evtrsvn.service.EvtRsvnItemVO;
import com.hisco.user.exbtrsvn.service.ExbtChargeVO;
import com.hisco.user.member.vo.MemberCouponVO;
import com.hisco.user.mypage.vo.MyRsvnVO;
import com.hisco.user.mypage.vo.RsvnCommVO;

import egovframework.com.cmm.LoginVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 예약 공통 기능 서비스 구현
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
@Service("rsvnCommService")
public class RsvnCommService extends EgovAbstractServiceImpl {

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    /**
     * 할인 금액 정책
     * 
     * @param param
     *            Map
     * @return Map
     * @exception Exception
     */
    public RsvnCommVO selectOptData(Map<?, ?> param) throws Exception {
        return (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectOptData", param);
    }

    /**
     * 이벤트 할인 내역 조회
     * 
     * @param param
     *            Map
     * @return Map
     * @exception Exception
     */
    public List<RsvnCommVO> selectEventStdmngList(Map<?, ?> param) throws Exception {
        List<RsvnCommVO> list = (List<RsvnCommVO>) commonDAO.queryForList("RsvnCommDAO.selectEventStdmngList", param);
        return list;
    }

    /**
     * 감면 기준 조회
     * 
     * @param param
     *            Map
     * @return Map
     * @exception Exception
     */
    public List<RsvnCommVO> selectDcReasonList(MyRsvnVO vo) throws Exception {
        List<RsvnCommVO> list = (List<RsvnCommVO>) commonDAO.queryForList("RsvnCommDAO.selectDcReasonList", vo);
        return list;
    }

    /**
     * 연간회원 할인 정책
     * 
     * @param param
     *            Map
     * @return Map
     * @exception Exception
     */
    public RsvnCommVO selectAnnualDcData(MyRsvnVO vo) throws Exception {
        RsvnCommVO data = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectAnnualDcData", vo);
        return data;
    }

    /**
     * 사용 가능한 쿠폰 목록
     * 
     * @param param
     *            Map
     * @return Map
     * @exception Exception
     */
    public List<MemberCouponVO> selectCouponList(MyRsvnVO vo) throws Exception {
        List<MemberCouponVO> list = (List<MemberCouponVO>) commonDAO.queryForList("RsvnCommDAO.selectMyCouponList", vo);
        return list;
    }

    /**
     * 예약상태 조회
     * 
     * @param param
     *            Map
     * @return Map
     * @exception Exception
     */
    public CamelMap selectReserveData(MyRsvnVO vo) throws Exception {
        if ("EXBT".equals(vo.getGubun())) {
            CamelMap data = (CamelMap) commonDAO.queryForObject("RsvnCommDAO.selectExbtItemCharge", vo);
            return data;
        } else if ("EVT".equals(vo.getGubun())) {
            CamelMap data = (CamelMap) commonDAO.queryForObject("RsvnCommDAO.selectEvtItemCharge", vo);
            return data;
        } else if ("EDC".equals(vo.getGubun())) {
            CamelMap data = (CamelMap) commonDAO.queryForObject("RsvnCommDAO.selectEdcItemCharge", vo);
            return data;
        } else {
            return null;
        }

    }

    /**
     * 그룹 할인 정책
     * 
     * @param param
     *            Map
     * @return Map
     * @exception Exception
     */
    public RsvnCommVO selectGroupDcCheck(MyRsvnVO vo) throws Exception {
        RsvnCommVO data = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectGroupDcCheck", vo);
        return data;
    }

    /**
     * 관람 이용품목 입력
     * 
     * @param param
     *            Map
     * @return Map
     * @exception Exception
     */
    public String insertExbtRsvnItem(MyRsvnVO vo) throws Exception {
        CamelMap data = (CamelMap) commonDAO.queryForObject("RsvnCommDAO.selectExbtItemCharge", vo);

        // 할인 금액 설정
        Map<String, String> param = new HashMap<String, String>();
        param.put("COMCD", Config.COM_CD);
        RsvnCommVO discOpt = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectOptData", param);

        if (data != null) {
            vo.setPartCd(CommonUtil.getString(data.get("partcd")));
            vo.setProgramCd(CommonUtil.getString(data.get("programCd")));
            vo.setYmd(CommonUtil.getString(data.get("ymd")));
            vo.setTarget(CommonUtil.getString(data.get("target")));
            vo.setAppStatus(CommonUtil.getString(data.get("appStatus")));
        }

        if (vo.getAppStatus() == null || !vo.getAppStatus().equals("11")) {
            return "결제 대기 상태가 아닙니다.";
        }

        // 아이템 할인 / 전체 목록
        param.put("rsvnIdx", vo.getRsvnIdxOne());
        param.put("itemCd", vo.getItemCd());
        param.put("comcd", Config.COM_CD);
        List<ExbtChargeVO> chargeList = (List<ExbtChargeVO>) commonDAO.queryForList("DspyDsDAO.selectReserveItemList", param);

        RsvnCommVO annualData = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectAnnualDcData", vo);
        String annualSeq = "";
        String saleSeq = "";
        long remainCnt = 0;

        String dcType = "";
        String dcEventId = "";

        List<String> couponList = vo.getCouponIds();

        for (ExbtChargeVO chargeVO : chargeList) {
            if ("8001".equals(chargeVO.getDcKindCd())) {
                annualSeq = chargeVO.getSaleSeq(); // 연간회원 코드
                remainCnt += chargeVO.getItemSubCnt();
            } else if (!"7001".equals(chargeVO.getDcKindCd())) {
                // 쿠폰할인 제외
                saleSeq = chargeVO.getSaleSeq();
                remainCnt += chargeVO.getItemSubCnt();

                dcType = chargeVO.getDcType();
                dcEventId = chargeVO.getDcEventId();

            }
        }

        if (dcEventId == null)
            dcEventId = "";

        if (remainCnt < (couponList == null ? 0 : couponList.size())) {
            return "할인 가능 수량보다 많은 할인사유를 선택하셨습니다.(" + remainCnt + "개 할인사유 선택 가능)";
        }

        log.debug("=====================remainCnt: " + remainCnt);

        if (data != null) {
            String price = CommonUtil.getString(data.get("price"));

            if (couponList != null) {
                for (String coupon : couponList) {
                    /*
                     * INSERT INTO EXBT_RSVN_ITEM
                     * (EXBT_RSVN_IDX , EXBT_SALESEQ,
                     * EXBT_ITEMCD, EXBT_ITEMCOST, EXBT_ITEM_CNT,
                     * EXBT_AMOUNT, EXBT_DCTYPE , EXBT_EVENT_DCID , EXBT_DCRATE,
                     * EXBT_DCAMOUNT , EXBT_TOTAMOUNT, EXBT_ONOFFINTYPE, TERMINAL_TYPE, REGDATE, REGUSER
                     * )
                     * VALUES(
                     * #{rsvnIdx} , (SELECT NVL(MAX(EXBT_SALESEQ),0)+1 FROM EXBT_RSVN_ITEM WHERE
                     * EXBT_RSVN_IDX=#{rsvnIdx}),
                     * #{itemCd} , #{price} , #{itemCnt},
                     * #{amount} , #{dcType}, #{dcEventId} , NVL(#{dcRate} ,0),
                     * NVL(#{dcAmount} ,0) , #{totAmount} , '10' , #{terminalType} , SYSDATE , #{reguser}
                     * )
                     */
                    ExbtChargeVO chargeVO = new ExbtChargeVO();
                    chargeVO.setComcd(vo.getComcd());
                    chargeVO.setRsvnIdx(vo.getRsvnIdxOne());
                    chargeVO.setItemCd(vo.getItemCd());
                    chargeVO.setPrice(Long.parseLong(price)); // 단가
                    chargeVO.setDcType("6001"); // 쿠폰
                    chargeVO.setDcCpnno(coupon);

                    MemberCouponVO couponVO = (MemberCouponVO) commonDAO.queryForObject("RsvnCommDAO.selectMyCouponData", chargeVO);

                    if (couponVO != null) {
                        chargeVO.setDcRate(Long.parseLong(couponVO.getCpnDcrate())); // 할인율
                        long dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO.getPrice() * chargeVO.getDcRate() * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
                        chargeVO.setDcAmount(dcAmountLong); // 할인금액
                        chargeVO.setAmount(chargeVO.getPrice());
                        chargeVO.setTotAmount(chargeVO.getAmount() - dcAmountLong);
                        chargeVO.setTerminalType(vo.getTerminalType());
                        chargeVO.setReguser(vo.getReguser());
                        chargeVO.setItemCnt(1);
                        commonDAO.getExecuteResult("DspyDsDAO.insertReserveItem", chargeVO);
                        // 쿠폰 사용함으로 수정
                        commonDAO.getExecuteResult("RsvnCommDAO.updateMyCouponDataUse", couponVO);
                        remainCnt--;
                    }
                }
            }

            log.debug("=====================remainCnt check: " + remainCnt);
            String eventReasonCd = vo.getEventReasonCd();
            if (eventReasonCd == null)
                eventReasonCd = "";
            long dcRate = 0;

            // 유료 특별회원 할인횟수 적용
            if (annualData != null && eventReasonCd.startsWith("ANN")) {
                if (annualData.getLimitCnt() == 0 || annualData.getLimitCnt() > annualData.getApplyCnt()) {
                    int annualCnt = annualData.getLimitQty() - CommonUtil.getInt(data.get("annualCnt")); // 할인 가능 수량

                    if (annualData.getLimitQty() == 0) {
                        annualCnt = Integer.parseInt(remainCnt + "");
                    } else if (annualCnt > remainCnt) {
                        annualCnt = Integer.parseInt(remainCnt + "");
                    }

                    log.debug("=====================annualCnt: " + annualCnt);

                    if (annualCnt > 0) {
                        dcRate = annualData.getDcRate();
                        ExbtChargeVO chargeVO2 = new ExbtChargeVO();
                        chargeVO2.setComcd(vo.getComcd());
                        chargeVO2.setPrice(Long.parseLong(price));
                        chargeVO2.setItemCnt(annualCnt); // 쿠폰 사용수량을 뺌
                        chargeVO2.setSaleSeq(annualSeq);
                        chargeVO2.setDcRate(dcRate);
                        long dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO2.getPrice() * chargeVO2.getDcRate() * chargeVO2.getItemCnt() * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());

                        chargeVO2.setRsvnIdx(vo.getRsvnIdxOne());
                        chargeVO2.setItemCd(vo.getItemCd());
                        chargeVO2.setPrice(Long.parseLong(price));
                        chargeVO2.setDcType(annualData.getDcReasonCd());
                        chargeVO2.setDcAmount(dcAmountLong); // 할인금액
                        chargeVO2.setAmount((chargeVO2.getPrice() * chargeVO2.getItemCnt()));
                        chargeVO2.setTotAmount(chargeVO2.getAmount() - dcAmountLong);
                        chargeVO2.setTerminalType(vo.getTerminalType());
                        chargeVO2.setReguser(vo.getReguser());

                        if (annualSeq.equals("")) {
                            commonDAO.getExecuteResult("DspyDsDAO.insertReserveItem", chargeVO2);
                        } else {
                            commonDAO.getExecuteResult("DspyDsDAO.updateExbtItemCount", chargeVO2);
                        }

                        remainCnt = remainCnt - annualCnt;
                    } else {
                        // 유료회원 할인 삭제
                        ExbtChargeVO chargeVO3 = new ExbtChargeVO();
                        chargeVO3.setRsvnIdx(vo.getRsvnIdxOne());
                        chargeVO3.setSaleSeq(annualSeq);
                        commonDAO.getExecuteResult("DspyDsDAO.deleteExbtItem", chargeVO3);
                    }
                }
            } else if (!eventReasonCd.equals("")) {
                dcType = eventReasonCd.split("[|]")[0];
                dcEventId = eventReasonCd.split("[|]")[1];

                if (saleSeq.equals("") && !annualSeq.equals("")) {
                    saleSeq = annualSeq;
                } else if (!annualSeq.equals("")) {
                    // 유료회원 할인 삭제
                    ExbtChargeVO chargeVO3 = new ExbtChargeVO();
                    chargeVO3.setRsvnIdx(vo.getRsvnIdxOne());
                    chargeVO3.setSaleSeq(annualSeq);
                    commonDAO.getExecuteResult("DspyDsDAO.deleteExbtItem", chargeVO3);
                }
            }
            // 나머지 수량 조절
            ExbtChargeVO chargeVO2 = new ExbtChargeVO();
            chargeVO2.setPrice(Long.parseLong(price));
            chargeVO2.setItemCnt(remainCnt);
            chargeVO2.setSaleSeq(saleSeq);
            chargeVO2.setDcEventId(dcEventId);
            chargeVO2.setDcType(dcType);
            chargeVO2.setComcd(vo.getComcd());
            chargeVO2.setRsvnIdx(vo.getRsvnIdxOne());

            if (chargeVO2.getItemCnt() < 1) {
                commonDAO.getExecuteResult("DspyDsDAO.deleteExbtItem", chargeVO2);
            } else {
                // 이벤트 할인율
                if (!dcEventId.equals("") && !dcEventId.equals("0")) {
                    dcRate = (int) commonDAO.queryForObject("RsvnCommDAO.selectEventDcRate", chargeVO2);
                } else {
                    dcRate = 0;
                }

                chargeVO2.setDcRate(dcRate);
                long dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO2.getPrice() * chargeVO2.getDcRate() * chargeVO2.getItemCnt() * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());

                chargeVO2.setItemCd(vo.getItemCd());
                chargeVO2.setPrice(Long.parseLong(price));
                chargeVO2.setDcAmount(dcAmountLong); // 할인금액
                chargeVO2.setAmount((chargeVO2.getPrice() * chargeVO2.getItemCnt()));
                chargeVO2.setTotAmount(chargeVO2.getAmount() - dcAmountLong);
                chargeVO2.setTerminalType(vo.getTerminalType());
                chargeVO2.setReguser(vo.getReguser());

                if (saleSeq.equals("")) {
                    commonDAO.getExecuteResult("DspyDsDAO.insertReserveItem", chargeVO2);
                } else {
                    commonDAO.getExecuteResult("DspyDsDAO.updateExbtItemCount", chargeVO2);
                }
            }

            // 최종 업데이트
            commonDAO.getExecuteResult("DspyDsDAO.updateExbtMstData", vo);

        }

        return "OK";
    }

    /**
     * 관람 이용품목 취소
     * 
     * @param param
     *            Map
     * @return Map
     * @exception Exception
     */
    public String deleteExbtRsvnItem(MyRsvnVO vo, RsvnCommVO eventDcInfo, String annualLimitYn) throws Exception {
        CamelMap data = (CamelMap) commonDAO.queryForObject("RsvnCommDAO.selectExbtItemCharge", vo);

        if (data != null) {
            vo.setPartCd(CommonUtil.getString(data.get("partcd")));
            vo.setProgramCd(CommonUtil.getString(data.get("programCd")));
            vo.setYmd(CommonUtil.getString(data.get("ymd")));
            vo.setTarget(CommonUtil.getString(data.get("target")));
            vo.setAppStatus(CommonUtil.getString(data.get("appStatus")));
        }

        if (vo.getAppStatus() == null || !vo.getAppStatus().equals("11")) {
            return "결제 대기 상태가 아닙니다.";
        }

        // 할인 금액 설정
        Map<String, String> param = new HashMap<String, String>();
        param.put("COMCD", Config.COM_CD);
        RsvnCommVO discOpt = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectOptData", param);

        // 아이템 할인 / 전체 목록
        param.put("rsvnIdx", vo.getRsvnIdxOne());
        param.put("itemCd", vo.getItemCd());
        param.put("comcd", Config.COM_CD);
        List<ExbtChargeVO> chargeList = (List<ExbtChargeVO>) commonDAO.queryForList("DspyDsDAO.selectReserveItemList", param);

        String saleSeq = "";
        String dcType = "";
        String dcEventId = "";
        long remainCnt = 0;
        long cancelCnt = 0;
        long dcRate = 0;

        if (data != null) {
            String price = CommonUtil.getString(data.get("price"));

            for (ExbtChargeVO chargeVO : chargeList) {
                if (chargeVO.getSaleSeq().equals(vo.getSubSeq())) {
                    cancelCnt = chargeVO.getItemSubCnt(); // 취소할 수량
                } else if ("7001".equals(chargeVO.getDcKindCd())) {
                    // 쿠폰할인 제외
                    dcRate = 0;
                } else if ("8001".equals(chargeVO.getDcKindCd()) && !annualLimitYn.equals("N")) {
                    // 유료회원할인 제외
                    dcRate = 0;
                } else {
                    saleSeq = chargeVO.getSaleSeq();
                    remainCnt = chargeVO.getItemSubCnt();
                    dcRate = chargeVO.getDcRate(); // 할인율
                    dcType = chargeVO.getDcType();
                    dcEventId = chargeVO.getDcEventId();
                }
            }

            // 기본 할인 적용
            if (saleSeq.equals("") && eventDcInfo != null) {
                dcRate = eventDcInfo.getDcRate(); // 할인율
                dcType = eventDcInfo.getDcReasonCd();
                dcEventId = eventDcInfo.getDcid();
            }

            ExbtChargeVO chargeVO2 = new ExbtChargeVO();
            chargeVO2.setComcd(vo.getComcd());
            chargeVO2.setPrice(Long.parseLong(price));
            chargeVO2.setItemCnt(remainCnt + cancelCnt);
            chargeVO2.setSaleSeq(saleSeq);
            chargeVO2.setDcRate(dcRate);
            long dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO2.getPrice() * chargeVO2.getDcRate() * chargeVO2.getItemCnt() * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());

            chargeVO2.setRsvnIdx(vo.getRsvnIdxOne());
            chargeVO2.setItemCd(vo.getItemCd());
            chargeVO2.setPrice(Long.parseLong(price));
            chargeVO2.setDcType(dcType);
            chargeVO2.setDcEventId(dcEventId);
            chargeVO2.setDcAmount(dcAmountLong); // 할인금액
            chargeVO2.setAmount((chargeVO2.getPrice() * chargeVO2.getItemCnt()));
            chargeVO2.setTotAmount(chargeVO2.getAmount() - dcAmountLong);
            chargeVO2.setTerminalType(vo.getTerminalType());
            chargeVO2.setReguser(vo.getReguser());

            // 쿠폰 재사용
            commonDAO.getExecuteResult("RsvnCommDAO.updateMyCouponDataCancel", vo);

            if (saleSeq.equals("")) {
                commonDAO.getExecuteResult("DspyDsDAO.insertReserveItem", chargeVO2);
            } else {
                commonDAO.getExecuteResult("DspyDsDAO.updateExbtItemCount", chargeVO2);
            }

            // 삭제
            chargeVO2.setSaleSeq(vo.getSubSeq());
            commonDAO.getExecuteResult("DspyDsDAO.deleteExbtItem", chargeVO2);
            // 최종 업데이트
            commonDAO.getExecuteResult("DspyDsDAO.updateExbtMstData", vo);

        }

        return "OK";
    }

    /**
     * 강연/행사/영화 이용품목 입력
     * 
     * @param param
     *            Map
     * @return Map
     * @exception Exception
     */
    public String insertEvtRsvnItem(MyRsvnVO vo) throws Exception {
        CamelMap data = (CamelMap) commonDAO.queryForObject("RsvnCommDAO.selectEvtItemCharge", vo);

        if (data != null) {
            vo.setPartCd(CommonUtil.getString(data.get("partcd")));
            vo.setProgramCd(CommonUtil.getString(data.get("programCd")));
            vo.setYmd(CommonUtil.getString(data.get("ymd")));
            vo.setTarget(CommonUtil.getString(data.get("target")));
            vo.setAppStatus(CommonUtil.getString(data.get("appStatus")));
        }

        if (vo.getAppStatus() == null || !vo.getAppStatus().equals("11")) {
            return "결제 대기 상태가 아닙니다.";
        }

        // 할인 금액 설정
        Map<String, String> param = new HashMap<String, String>();
        param.put("COMCD", Config.COM_CD);
        RsvnCommVO discOpt = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectOptData", param);

        // 아이템 할인 / 전체 목록
        param.put("evtRsvnIdx", vo.getRsvnIdxOne());
        param.put("itemCd", vo.getItemCd());
        param.put("comcd", Config.COM_CD);
        List<EvtRsvnItemVO> chargeList = (List<EvtRsvnItemVO>) commonDAO.queryForList("EvtrsvnSMainDAO.selectEvtrsvnItem", param);

        RsvnCommVO annualData = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectAnnualDcData", vo);
        String annualSeq = "";
        String saleSeq = "";
        long remainCnt = 0;
        String dcType = "";
        String dcEventId = "";

        List<String> couponList = vo.getCouponIds();

        for (EvtRsvnItemVO chargeVO : chargeList) {
            if ("8001".equals(chargeVO.getDcKindCd())) {
                annualSeq = chargeVO.getEvtSaleSeq(); // 연간회원 코드
                remainCnt += chargeVO.getItemSubCnt();
            } else if (!"7001".equals(chargeVO.getDcKindCd())) {
                // 쿠폰할인 제외
                saleSeq = chargeVO.getEvtSaleSeq();
                remainCnt += chargeVO.getItemSubCnt();

                dcType = chargeVO.getEvtDcType();
                dcEventId = chargeVO.getEvtEventDcid();
            }
        }

        if (dcEventId == null)
            dcEventId = "";

        if (remainCnt < (couponList == null ? 0 : couponList.size())) {
            return "할인 가능 수량보다 많은 할인사유를 선택하셨습니다.(" + remainCnt + "개 할인사유 선택 가능)";
        }

        if (data != null) {
            String price = CommonUtil.getString(data.get("itemPrice"));

            if (couponList != null) {
                for (String coupon : couponList) {
                    EvtRsvnItemVO chargeVO = new EvtRsvnItemVO();
                    chargeVO.setComcd(vo.getComcd());
                    chargeVO.setEvtRsvnIdx(vo.getRsvnIdxOne());
                    chargeVO.setEvtRsvnItemcd(vo.getItemCd());
                    chargeVO.setEvtCost(Long.parseLong(price)); // 단가
                    chargeVO.setEvtDcType("6001"); // 쿠폰
                    chargeVO.setEvtDcCpnno(coupon);
                    chargeVO.setDcCpnno(coupon);

                    MemberCouponVO couponVO = (MemberCouponVO) commonDAO.queryForObject("RsvnCommDAO.selectMyCouponData", chargeVO);

                    if (couponVO != null) {
                        chargeVO.setEvtDcRate(Long.parseLong(couponVO.getCpnDcrate())); // 할인율
                        long dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO.getEvtCost() * chargeVO.getEvtDcRate() * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());
                        chargeVO.setEvtDcamt(dcAmountLong); // 할인금액
                        chargeVO.setEvtSalamt(chargeVO.getEvtCost());
                        chargeVO.setEvtTotamt(chargeVO.getEvtSalamt() - dcAmountLong);
                        chargeVO.setEvtTerminalType(vo.getTerminalType());
                        chargeVO.setReguser(vo.getReguser());
                        chargeVO.setEvtRsvnItemcnt(1);
                        commonDAO.getExecuteResult("EvtrsvnSMainDAO.insertEvtrsvnItem", chargeVO);
                        // 쿠폰 사용함으로 수정
                        commonDAO.getExecuteResult("RsvnCommDAO.updateMyCouponDataUse", couponVO);
                        remainCnt--;
                    }
                }
            }
            String eventReasonCd = vo.getEventReasonCd();
            if (eventReasonCd == null)
                eventReasonCd = "";
            long dcRate = 0;

            // 유료 특별회원 할인횟수 적용
            if (annualData != null && eventReasonCd.startsWith("ANN")) {
                if (annualData.getLimitCnt() == 0 || annualData.getLimitCnt() > annualData.getApplyCnt()) {
                    int annualCnt = annualData.getLimitQty() - CommonUtil.getInt(data.get("annualCnt")); // 할인 가능 수량

                    if (annualData.getLimitQty() == 0) {
                        annualCnt = Integer.parseInt(remainCnt + "");
                    } else if (annualCnt > remainCnt) {
                        annualCnt = Integer.parseInt(remainCnt + "");
                    }

                    if (annualCnt > 0) {
                        dcRate = annualData.getDcRate();
                        EvtRsvnItemVO chargeVO2 = new EvtRsvnItemVO();
                        chargeVO2.setComcd(vo.getComcd());
                        chargeVO2.setEvtCost(Long.parseLong(price)); // 단가
                        chargeVO2.setEvtRsvnItemcnt(annualCnt);
                        chargeVO2.setEvtSaleSeq(annualSeq);
                        chargeVO2.setEvtDcRate(dcRate);
                        long dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO2.getEvtCost() * chargeVO2.getEvtDcRate() * chargeVO2.getEvtRsvnItemcnt() * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());

                        chargeVO2.setEvtRsvnIdx(vo.getRsvnIdxOne());
                        chargeVO2.setEvtRsvnItemcd(vo.getItemCd());
                        chargeVO2.setEvtDcType(annualData.getDcReasonCd());
                        chargeVO2.setEvtDcamt(dcAmountLong); // 할인금액
                        chargeVO2.setEvtSalamt((chargeVO2.getEvtCost() * chargeVO2.getEvtRsvnItemcnt()));
                        chargeVO2.setEvtTotamt(chargeVO2.getEvtSalamt() - dcAmountLong);
                        chargeVO2.setEvtTerminalType(vo.getTerminalType());
                        chargeVO2.setReguser(vo.getReguser());

                        if (annualSeq.equals("")) {
                            commonDAO.getExecuteResult("EvtrsvnSMainDAO.insertEvtrsvnItem", chargeVO2);
                        } else {
                            commonDAO.getExecuteResult("EvtrsvnSMainDAO.updateEvtItemCount", chargeVO2);
                        }

                        remainCnt = remainCnt - annualCnt;
                    } else {
                        // 유료회원 할인 삭제
                        EvtRsvnItemVO chargeVO3 = new EvtRsvnItemVO();
                        chargeVO3.setEvtRsvnIdx(vo.getRsvnIdxOne());
                        chargeVO3.setEvtSaleSeq(annualSeq);
                        commonDAO.getExecuteResult("EvtrsvnSMainDAO.deleteEvtItem", chargeVO3);
                    }
                }
            } else if (!eventReasonCd.equals("")) {
                dcType = eventReasonCd.split("[|]")[0];
                dcEventId = eventReasonCd.split("[|]")[1];

                if (saleSeq.equals("") && !annualSeq.equals("")) {
                    saleSeq = annualSeq;
                } else if (!annualSeq.equals("")) {
                    // 유료회원 할인 삭제
                    EvtRsvnItemVO chargeVO3 = new EvtRsvnItemVO();
                    chargeVO3.setEvtRsvnIdx(vo.getRsvnIdxOne());
                    chargeVO3.setEvtSaleSeq(annualSeq);
                    commonDAO.getExecuteResult("EvtrsvnSMainDAO.deleteEvtItem", chargeVO3);
                }
            }

            // 나머지 수량 조절
            EvtRsvnItemVO chargeVO2 = new EvtRsvnItemVO();
            chargeVO2.setEvtCost(Long.parseLong(price));
            chargeVO2.setEvtRsvnItemcnt(remainCnt);
            chargeVO2.setEvtSaleSeq(saleSeq);
            chargeVO2.setEvtEventDcid(dcEventId);
            chargeVO2.setEvtDcType(dcType);
            chargeVO2.setComcd(vo.getComcd());
            chargeVO2.setEvtRsvnIdx(vo.getRsvnIdxOne());
            chargeVO2.setDcEventId(dcEventId);

            if (chargeVO2.getEvtRsvnItemcnt() < 1) {
                commonDAO.getExecuteResult("EvtrsvnSMainDAO.deleteEvtItem", chargeVO2);
            } else {
                // 이벤트 할인율
                if (!dcEventId.equals("") && !dcEventId.equals("0")) {
                    dcRate = (int) commonDAO.queryForObject("RsvnCommDAO.selectEventDcRate", chargeVO2);
                } else {
                    dcRate = 0;
                }

                chargeVO2.setEvtDcRate(dcRate);
                long dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO2.getEvtCost() * chargeVO2.getEvtDcRate() * chargeVO2.getEvtRsvnItemcnt() * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());

                chargeVO2.setEvtRsvnItemcd(vo.getItemCd());
                chargeVO2.setEvtCost(Long.parseLong(price));
                chargeVO2.setEvtDcamt(dcAmountLong); // 할인금액
                chargeVO2.setEvtSalamt((chargeVO2.getEvtCost() * chargeVO2.getEvtRsvnItemcnt()));
                chargeVO2.setEvtTotamt(chargeVO2.getEvtSalamt() - dcAmountLong);
                chargeVO2.setEvtTerminalType(vo.getTerminalType());
                chargeVO2.setReguser(vo.getReguser());

                if (saleSeq.equals("")) {
                    commonDAO.getExecuteResult("EvtrsvnSMainDAO.insertEvtrsvnItem", chargeVO2);
                } else {
                    commonDAO.getExecuteResult("EvtrsvnSMainDAO.updateEvtItemCount", chargeVO2);
                }
            }

            // 최종 업데이트
            commonDAO.getExecuteResult("EvtrsvnSMainDAO.updateEvtMstData", vo);
        }

        return "OK";
    }

    /**
     * 강연/행사/영화 이용품목 취소
     * 
     * @param param
     *            Map
     * @return Map
     * @exception Exception
     */
    public String deleteEvtRsvnItem(MyRsvnVO vo, RsvnCommVO eventDcInfo, String annualLimitYn) throws Exception {
        CamelMap data = (CamelMap) commonDAO.queryForObject("RsvnCommDAO.selectEvtItemCharge", vo);

        if (data != null) {
            vo.setPartCd(CommonUtil.getString(data.get("partcd")));
            vo.setProgramCd(CommonUtil.getString(data.get("programCd")));
            vo.setYmd(CommonUtil.getString(data.get("ymd")));
            vo.setTarget(CommonUtil.getString(data.get("target")));
            vo.setAppStatus(CommonUtil.getString(data.get("appStatus")));
        }

        if (vo.getAppStatus() == null || !vo.getAppStatus().equals("11")) {
            return "결제 대기 상태가 아닙니다.";
        }

        // 할인 금액 설정
        Map<String, String> param = new HashMap<String, String>();
        param.put("COMCD", Config.COM_CD);
        RsvnCommVO discOpt = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectOptData", param);

        // 아이템 할인 / 전체 목록
        param.put("evtRsvnIdx", vo.getRsvnIdxOne());
        param.put("itemCd", vo.getItemCd());
        param.put("comcd", Config.COM_CD);
        List<EvtRsvnItemVO> chargeList = (List<EvtRsvnItemVO>) commonDAO.queryForList("EvtrsvnSMainDAO.selectEvtrsvnItem", param);

        String saleSeq = "";
        String dcType = "";
        String dcEventId = "";
        long remainCnt = 0;
        long cancelCnt = 0;
        long dcRate = 0;

        if (data != null) {
            String price = CommonUtil.getString(data.get("itemPrice"));

            for (EvtRsvnItemVO chargeVO : chargeList) {
                if (chargeVO.getEvtSaleSeq().equals(vo.getSubSeq())) {
                    cancelCnt = chargeVO.getItemSubCnt(); // 취소할 수량
                } else if ("7001".equals(chargeVO.getDcKindCd())) {
                    // 쿠폰할인 제외
                    dcRate = 0;
                } else if ("8001".equals(chargeVO.getDcKindCd()) && !annualLimitYn.equals("N")) {
                    // 유료회원할인 제외
                    dcRate = 0;
                } else {
                    saleSeq = chargeVO.getEvtSaleSeq();
                    remainCnt = chargeVO.getItemSubCnt();
                    dcRate = chargeVO.getEvtDcRate(); // 할인율
                    dcType = chargeVO.getEvtDcType();
                    dcEventId = chargeVO.getEvtEventDcid();
                }
            }

            // 기본 할인 적용
            if (saleSeq.equals("") && eventDcInfo != null) {
                dcRate = eventDcInfo.getDcRate(); // 할인율
                dcType = eventDcInfo.getDcReasonCd();
                dcEventId = eventDcInfo.getDcid();
            }

            EvtRsvnItemVO chargeVO2 = new EvtRsvnItemVO();
            chargeVO2.setComcd(vo.getComcd());
            chargeVO2.setEvtCost(Long.parseLong(price));
            chargeVO2.setEvtRsvnItemcnt(remainCnt + cancelCnt);
            chargeVO2.setEvtSaleSeq(saleSeq);
            chargeVO2.setEvtEventDcid(dcEventId);
            chargeVO2.setEvtDcType(dcType);
            chargeVO2.setComcd(vo.getComcd());
            chargeVO2.setEvtRsvnIdx(vo.getRsvnIdxOne());
            chargeVO2.setDcEventId(dcEventId);
            chargeVO2.setEvtDcRate(dcRate);

            long dcAmountLong = CommonUtil.DoubleToLongCalc(chargeVO2.getEvtCost() * chargeVO2.getEvtDcRate() * chargeVO2.getEvtRsvnItemcnt() * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());

            chargeVO2.setEvtRsvnIdx(vo.getRsvnIdxOne());
            chargeVO2.setEvtRsvnItemcd(vo.getItemCd());
            chargeVO2.setEvtDcamt(dcAmountLong); // 할인금액
            chargeVO2.setEvtSalamt((chargeVO2.getEvtCost() * chargeVO2.getEvtRsvnItemcnt()));
            chargeVO2.setEvtTotamt(chargeVO2.getEvtSalamt() - dcAmountLong);
            chargeVO2.setEvtTerminalType(vo.getTerminalType());
            chargeVO2.setReguser(vo.getReguser());

            // 쿠폰 재사용
            commonDAO.getExecuteResult("RsvnCommDAO.updateMyCouponDataCancel", vo);

            if (saleSeq.equals("")) {
                commonDAO.getExecuteResult("EvtrsvnSMainDAO.insertEvtrsvnItem", chargeVO2);
            } else {
                commonDAO.getExecuteResult("EvtrsvnSMainDAO.updateEvtItemCount", chargeVO2);
            }

            // 삭제
            chargeVO2.setEvtSaleSeq(vo.getSubSeq());
            commonDAO.getExecuteResult("EvtrsvnSMainDAO.deleteEvtItem", chargeVO2);
            // 최종 업데이트
            commonDAO.getExecuteResult("EvtrsvnSMainDAO.updateEvtMstData", vo);

        }

        return "OK";
    }

    /**
     * 교육 할인 삭제
     * 
     * @param param
     *            Map
     * @return Map
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public String deleteEdcRsvnItem(MyRsvnVO vo, RsvnCommVO eventDcInfo) throws Exception {

        CamelMap data = (CamelMap) commonDAO.queryForObject("RsvnCommDAO.selectEdcItemCharge", vo);
        String programCost = CommonUtil.getString(data.get("itemPrice"));
        if (data != null) {
            vo.setAppStatus(CommonUtil.getString(data.get("appStatus")));
        }
        if (vo.getAppStatus() == null || !vo.getAppStatus().equals("11")) {
            return "결제 대기 상태가 아닙니다.";
        }

        long longPrice = Long.parseLong(programCost); // 원가격
        long dcAmountLong = 0;
        // 쿠폰 재사용
        commonDAO.getExecuteResult("RsvnCommDAO.updateMyCouponDataCancel", vo);
        // 가격 재설정
        if (eventDcInfo != null) {
            // 할인 금액 설정
            Map<String, String> param = new HashMap<String, String>();
            param.put("COMCD", Config.COM_CD);
            RsvnCommVO discOpt = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectOptData", param);
            long dblCpnRate = eventDcInfo.getDcRate();// 할인율

            dcAmountLong = CommonUtil.DoubleToLongCalc(longPrice * dblCpnRate * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());

            vo.setDiscReasonDc(eventDcInfo.getDcReasonCd());
            vo.setEventReasonCd(eventDcInfo.getDcid());
        }

        vo.setPayAmt(longPrice - dcAmountLong);
        vo.setDcAmt(dcAmountLong);

        commonDAO.getExecuteResult("RsvnCommDAO.updateEucRsvnInfoCoupon", vo);

        return "OK";
    }

    /**
     * 교육 이용품목 입력
     * 
     * @param param
     *            Map
     * @return Map
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public String insertEdcRsvnItem(MyRsvnVO vo, LoginVO userVO) throws Exception {
        String strEdcRsvnReqid = vo.getRsvnIdxOne();
        String discountSelectVal = vo.getEventReasonCd();
        String programCost = "";

        vo.setUniqId(strEdcRsvnReqid);

        CamelMap data = (CamelMap) commonDAO.queryForObject("RsvnCommDAO.selectEdcItemCharge", vo);

        if (data != null) {
            vo.setPartCd(CommonUtil.getString(data.get("partcd")));
            vo.setAppStatus(CommonUtil.getString(data.get("appStatus")));

            programCost = CommonUtil.getString(data.get("itemPrice"));

        }

        if (vo.getAppStatus() == null || !vo.getAppStatus().equals("11")) {
            return "결제 대기 상태가 아닙니다.";
        }

        // 할인 금액 설정
        Map<String, String> param = new HashMap<String, String>();
        param.put("COMCD", Config.COM_CD);
        RsvnCommVO discOpt = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectOptData", param);

        boolean dcflag = false;

        // 쿠폰 취소
        commonDAO.getExecuteResult("RsvnCommDAO.updateMyCouponDataCancel", vo);

        if (discountSelectVal != null) {
            if (discountSelectVal.startsWith("CPN")) {
                vo.setCouponIdOne(discountSelectVal.substring(4)); // 쿠폰

                param.put("dcCpnno", vo.getCouponIdOne());
                param.put("comcd", vo.getComcd());

                MemberCouponVO couponVO = (MemberCouponVO) commonDAO.queryForObject("RsvnCommDAO.selectMyCouponData", param);

                if (couponVO != null) {
                    long dblCpnRate = Long.parseLong(couponVO.getCpnDcrate()); // 할인율
                    long longPrice = Long.parseLong(programCost); // 원가격
                    long dcAmountLong = CommonUtil.DoubleToLongCalc(longPrice * dblCpnRate * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());

                    vo.setPayAmt(longPrice - dcAmountLong);
                    vo.setDcAmt(dcAmountLong);
                    vo.setDiscReasonDc("6001"); // 쿠폰할인
                    vo.setEventReasonCd("");

                    commonDAO.getExecuteResult("RsvnCommDAO.updateEucRsvnInfoCoupon", vo);
                    // 쿠폰 사용함으로 수정
                    commonDAO.getExecuteResult("RsvnCommDAO.updateMyCouponDataUse", couponVO);

                    dcflag = true;
                }
            } else if (discountSelectVal.startsWith("ANN")) {
                // 연간회원
                RsvnCommVO annualData = (RsvnCommVO) commonDAO.queryForObject("RsvnCommDAO.selectAnnualDcData", vo);
                if (annualData.getLimitCnt() > annualData.getApplyCnt()) {
                    long dblCpnRate = annualData.getDcRate();// 할인율
                    long longPrice = Long.parseLong(programCost); // 원가격
                    long dcAmountLong = CommonUtil.DoubleToLongCalc(longPrice * dblCpnRate * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());

                    vo.setPayAmt(longPrice - dcAmountLong);
                    vo.setDcAmt(dcAmountLong);
                    vo.setDiscReasonDc(annualData.getDcReasonCd());
                    vo.setEventReasonCd("");
                    commonDAO.getExecuteResult("RsvnCommDAO.updateEucRsvnInfoCoupon", vo);

                    dcflag = true;
                }
            }
        }

        if (!dcflag) {
            // 할인 사유가 없다면 기본 이벤트 할인을 적용

            // 이벤트 할인 여부 조회
            param.put("COMCD", Config.COM_CD);
            param.put("YMD", (String) data.get("ymd"));
            param.put("PART_CD", (String) data.get("partcd"));
            param.put("PGM_CD", CommonUtil.getString(data.get("programCd")));
            param.put("PGM_GUBUN", "EDC");

            List<RsvnCommVO> discList = selectEventStdmngList(param);

            // 할인 조회
            RsvnCommVO eventDcInfo = null;

            if (discList != null) {
                for (RsvnCommVO discVO : discList) {
                    if (userVO.isMember() && "Y".equals(discVO.getMemberyn())) {
                        if (eventDcInfo == null || eventDcInfo.getDcRate() > discVO.getDcRate()) {
                            eventDcInfo = discVO;
                        }
                    } else if (!userVO.isMember() && "Y".equals(discVO.getNonmebyn())) {
                        if (eventDcInfo == null || eventDcInfo.getDcRate() > discVO.getDcRate()) {
                            eventDcInfo = discVO;
                        }
                    }
                }

                if (eventDcInfo != null) {
                    long dblCpnRate = eventDcInfo.getDcRate();// 할인율
                    long longPrice = Long.parseLong(programCost); // 원가격
                    long dcAmountLong = CommonUtil.DoubleToLongCalc(longPrice * dblCpnRate * 0.01, discOpt.getPayUpdownUnit(), discOpt.getPayUpdown());

                    vo.setPayAmt(longPrice - dcAmountLong);
                    vo.setDcAmt(dcAmountLong);
                    vo.setDiscReasonDc(eventDcInfo.getDcReasonCd());
                    vo.setEventReasonCd(eventDcInfo.getDcid());
                    commonDAO.getExecuteResult("RsvnCommDAO.updateEucRsvnInfoCoupon", vo);

                    dcflag = true;
                }
            }
        }
        return "OK";
    }

}
