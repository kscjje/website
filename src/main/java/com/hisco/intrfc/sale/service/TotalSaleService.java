package com.hisco.intrfc.sale.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.cmm.util.SessionUtil;
import com.hisco.intrfc.sale.vo.CardAppHistVO;
import com.hisco.intrfc.sale.vo.EdcRsvnComptinfoVO;
import com.hisco.intrfc.sale.vo.NwPayApiType;
import com.hisco.intrfc.sale.vo.NwPayVO;
import com.hisco.intrfc.sale.vo.PayListVO;
import com.hisco.intrfc.sale.vo.PgOrdDetVO;
import com.hisco.intrfc.sale.vo.PgOrdMstVO;
import com.hisco.intrfc.sale.vo.ReceiptInfoVO;
import com.hisco.intrfc.sale.vo.SaleDiscountVO;
import com.hisco.intrfc.sale.vo.SaleFormItemVO;
import com.hisco.intrfc.sale.vo.SaleFormMemberVO;
import com.hisco.intrfc.sale.vo.SaleFormPaymentVO;
import com.hisco.intrfc.sale.vo.SaleFormVO;
import com.hisco.intrfc.sale.vo.SelngInfoVO;
import com.hisco.intrfc.sale.vo.VbankOrderListVO;
import com.hisco.intrfc.sale.vo.VbankPaymentInfoVO;
import com.hisco.intrfc.sms.service.BizMsgService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : TotalSaleService.java
 * @Description : 강좌등록(판매)
 *              -----------등록 ----------------------------------
 *              무료강좌 수강등록
 *              유료강좌 수강등록 + 할인100%
 *              유료강좌 수강등록 + 결제금액 > 0
 *              오프라인 현금
 *              유료강좌 수강등록 + 결제금액 > 0
 *              오프라인 카드
 *              유료강좌 수강등록 + 결제금액 > 0 PG 신용카드 or 실시간계좌이체
 *              유료강좌 수강등록 + 결제금액 > 0 PG 가상계좌
 *              ---------- 취소 ----------------------------------
 * @author woojinp@legitsys.co.kr
 * @since 2021. 12. 7.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */

@Slf4j
@Service("totalSaleService")
public class TotalSaleService extends EgovAbstractServiceImpl {

    @Value("${Globals.SpowiseCms.Key}")
    private String SpowiseCmsKey;

    @Resource(name = "receiptInfoService")
    private ReceiptInfoService receiptInfoService;

    @Resource(name = "selngInfoService")
    private SelngInfoService selngInfoService;

    @Resource(name = "payListService")
    private PayListService payListService;

    @Resource(name = "saleDiscountService")
    private SaleDiscountService saleDiscountService;

    @Resource(name = "edcRsvnComptinfoService")
    private EdcRsvnComptinfoService edcRsvnComptinfoService;

    @Resource(name = "saleChargeService")
    private SaleChargeService saleChargeService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    @Resource(name = "nwPayService")
    private NwPayService nwPayService;

    @Resource(name = "bizMsgService")
    private BizMsgService bizMsgService;

    /**
     * 수강신청등록
     */
    public int register(SaleFormVO saleFormVO) throws Exception {

        log.debug("saleFormVO => {}", JsonUtil.toPrettyJson(saleFormVO));

        SaleFormMemberVO member = saleFormVO.getMember();
        List<SaleFormItemVO> itemList = saleFormVO.getItemList();
        SaleFormPaymentVO payment = saleFormVO.getPayment();

        int rsvnCnt = 0;
        int receiptCnt = 0;
        int selngCnt = 0;
        int payListCnt = 0;
        int saleDiscountCnt = 0;
        int pgOrdMstCnt = 0;
        int pgOrdDetCnt = 0;
        int cardAppHistCnt = 0;
        int comptinfoCnt = 0;
        int vBankCnt = 0;

        if (payment.getPayAmt() > 0) { // 최종결제금액 유(PG결제포함)
            if (Constant.PG_TOSS.equals(payment.getPayComcd())) {// 온라인 PG를 통한결제:카드,실시간계좌이체
                // 1.영수증(RECEPIT_INFO)테이블 입력
                ReceiptInfoVO receiptInfoVO = makeReceiptInfoVO(itemList.get(0), payment);
                receiptCnt = receiptInfoService.insertReceiptInfo(receiptInfoVO);
                log.debug(String.format("%s[%s] => %s\n", "insertReceiptInfo", receiptCnt, JsonUtil.toPrettyJson(receiptInfoVO)));

                // 예약건이 존재하면 현재 예약상태 조회
                String rsvnNo = payment.getRsvnNo();
                if (StringUtils.isBlank(rsvnNo)) {
                    payment.setRsvnNo(edcRsvnInfoService.selectNextRsvnNumber());
                }

                for (SaleFormItemVO item : itemList) {
                    // 2.EDC_RSVN_INFO 테이블 입력
                    EdcRsvnInfoVO rsvnInfoVO = makeEdcRsvnInfoVO(member, item, payment);
                    if (StringUtils.isNotBlank(rsvnNo)) { // 예약건존재
                        // 2.EDC_RSVN_INFO 테이블 갱신
                        rsvnCnt += edcRsvnInfoService.completeRsvnInfo(rsvnInfoVO);
                        log.debug(String.format("%s[%s] => %s\n", "completeRsvnInfo", rsvnCnt, JsonUtil.toPrettyJson(rsvnInfoVO)));
                    } else { // 예약건 미존재
                        // 2.EDC_RSVN_INFO 테이블 입력
                        rsvnCnt += edcRsvnInfoService.saveRsvnInfo(rsvnInfoVO);
                        log.debug(String.format("%s[%s] => %s\n", "saveRsvnInfo", rsvnCnt, JsonUtil.toPrettyJson(rsvnInfoVO)));
                    }

                    // 3.통합판매(SELNG_INFO)테이블입력
                    SelngInfoVO selngInfoVO = makeSelngInfoVO(member, item, payment, receiptInfoVO);
                    selngCnt += selngInfoService.insertSelngInfo(selngInfoVO);
                    log.debug(String.format("%s[%s] => %s\n", "insertSelngInfo", selngCnt, JsonUtil.toPrettyJson(selngInfoVO)));

                    // 4.결제내역(PAY_LIST)테이블입력(PG를 이용할 경우)
                    PayListVO payListVO = makePayListVO(itemList.get(0), payment, receiptInfoVO, selngInfoVO);
                    payListCnt += payListService.insertPayList(payListVO);
                    log.debug(String.format("%s[%s] => %s\n", "insertPayList", payListCnt, JsonUtil.toPrettyJson(payListVO)));

                    // 5.할인정보(SALESDISCOUNTS)테이블입력
                    if (item.getDcAmt() > 0) {
                        if (item.getNwpayAmt() > 0) {
                            NwPayVO reqPayVO = new NwPayVO();
                            reqPayVO.setApiType(NwPayApiType.SENDORDER);
                            reqPayVO.setUseid(item.getNwpayId());
                            reqPayVO.setUsenw(item.getNwpayAmt());
                            reqPayVO.setOrgNo(item.getOrgNo());
                            NwPayVO resPayVO = nwPayService.call(reqPayVO);
                            if (resPayVO.success()) {
                                item.setNwpayOrdid(resPayVO.getOrderID());
                                SessionUtil.setAttribute(Constant.NWPAY_ORDERID_SESSION_KEY, resPayVO.getOrderID()); // rollback대비용
                            } else {
                                log.error("NWPAY 사용 reqPayVO = {}", JsonUtil.toPrettyJson(reqPayVO));
                                log.error("NWPAY 사용 resPayVO = {}", JsonUtil.toPrettyJson(resPayVO));
                                throw new RuntimeException("노원PAY > " + resPayVO.getStatusDesc());
                            }
                        }

                        SaleDiscountVO saleDiscountVO = makeSaleDiscountVO(item, payment, selngInfoVO);
                        saleDiscountCnt += saleDiscountService.insertSaleDiscount(saleDiscountVO);
                        log.debug(String.format("%s[%s] => %s\n", "insertSaleDiscount", saleDiscountCnt, JsonUtil.toPrettyJson(saleDiscountVO)));
                    }

                    // 6.EDC_RSVN_COMPTINFO 테이블 입력
                    EdcRsvnComptinfoVO rsvnComptInfoVO = makeEdcRsvnComptinfoVO(item, rsvnInfoVO, selngInfoVO);
                    comptinfoCnt += edcRsvnComptinfoService.insertEdcRsvnComptinfo(rsvnComptInfoVO);
                    log.debug(String.format("%s[%s] => %s\n", "insertEdcRsvnComptinfo", comptinfoCnt, JsonUtil.toPrettyJson(rsvnComptInfoVO)));

                    bizMsgService.sendRsvnMessage(rsvnInfoVO.getEdcRsvnReqid());
                }

                // 7~9 pg(toss) 건래 관련 테이블 입력.
                if (Constant.SITE_P_TYPE_가상계좌.equals(payment.getPayMethod())) {
                    VbankPaymentInfoVO vPaymentVO = makeVbankPaymentInfoVO(member, itemList.get(0), payment);
                    vPaymentVO.setVbankSeq(payment.getVbankSeq());
                    vPaymentVO.setVbankPayamt(payment.getPayAmt()); // 입금금액
                    // vPaymentVO.setVbankOveramt(payment.); // 과오금액. 쿼리내 계산
                    vPaymentVO.setVbankPaymentDate(payment.getSaleDate()); // 입금완료일(pg사로부터 받은 전문의 입금일자)
                    vPaymentVO.setVbankPaymentTime(payment.getSaleTime()); // 입금완료시(pg사로부터 받은 전문의 입금시간)
                    vPaymentVO.setOid(payment.getOid());
                    vBankCnt = saleChargeService.completeVbankPaymentInfo(vPaymentVO);
                    log.debug(String.format("%s[%s] => %s\n", "completeVbankPaymentInfo", vBankCnt, JsonUtil.toPrettyJson(vPaymentVO)));
                } else { // 카드, 실시간
                    CardAppHistVO cardAppHistVO = makeCardAppHistVO(member, itemList.get(0), payment);
                    cardAppHistCnt = saleChargeService.insertCardAppHist(cardAppHistVO);
                    log.debug(String.format("%s[%s] => %s\n", "insertCardAppHist", cardAppHistCnt, JsonUtil.toPrettyJson(cardAppHistVO)));
                }

            } else { // 오프라인.현금,신용카드
                // 1.영수증(RECEPIT_INFO)테이블 입력
                ReceiptInfoVO receiptInfoVO = makeReceiptInfoVO(itemList.get(0), payment);
                receiptCnt = receiptInfoService.insertReceiptInfo(receiptInfoVO);
                log.debug(String.format("%s[%s] => %s\n", "insertReceiptInfo", receiptCnt, JsonUtil.toPrettyJson(receiptInfoVO)));

                /*
                 * if (StringUtils.isBlank(payment.getRsvnNo())) { // 없으면 관리자예약&완료처리. cf:고객예약 후 관리자사이트 입금확인시 rsvnNo존재
                 * payment.setRsvnNo(edcRsvnInfoService.selectNextRsvnNumber());
                 * }
                 */

                for (SaleFormItemVO item : itemList) {
                    // 2.EDC_RSVN_INFO 테이블 입력
                    EdcRsvnInfoVO rsvnInfoVO = makeEdcRsvnInfoVO(member, item, payment);
                    if (StringUtils.isBlank(payment.getRsvnNo())) { // 입금완료 insert
                        rsvnCnt += edcRsvnInfoService.saveRsvnInfo(rsvnInfoVO);
                        log.debug(String.format("%s[%s] => %s\n", "saveRsvnInfo", rsvnCnt, JsonUtil.toPrettyJson(rsvnInfoVO)));
                    } else { // 입금대기상태 -> 입금완료 update
                        rsvnCnt += edcRsvnInfoService.completeRsvnInfo(rsvnInfoVO);
                        log.debug(String.format("%s[%s] => %s\n", "completeRsvnInfo", rsvnCnt, JsonUtil.toPrettyJson(rsvnInfoVO)));
                    }
                    saleFormVO.setEdcRsvnReqid(rsvnInfoVO.getEdcRsvnReqid()); // 화면에 결제정보 노출을 위한 설정

                    // 3.통합판매(SELNG_INFO)테이블입력
                    SelngInfoVO selngInfoVO = makeSelngInfoVO(member, item, payment, receiptInfoVO);
                    selngCnt += selngInfoService.insertSelngInfo(selngInfoVO);
                    log.debug(String.format("%s[%s] => %s\n", "insertSelngInfo", selngCnt, JsonUtil.toPrettyJson(selngInfoVO)));

                    // 4.할인정보(SALEDISCOUNTS)테이블입력
                    if (item.getDcAmt() > 0) {
                        SaleDiscountVO saleDiscountVO = makeSaleDiscountVO(item, payment, selngInfoVO);
                        saleDiscountCnt += saleDiscountService.insertSaleDiscount(saleDiscountVO);
                        log.debug(String.format("%s[%s] => %s\n", "insertSaleDiscount", saleDiscountCnt, JsonUtil.toPrettyJson(saleDiscountVO)));
                    }

                    // 5.지불정보(PAY_LIST) 테이블입력
                    PayListVO payListVO = makePayListVO(itemList.get(0), payment, receiptInfoVO, selngInfoVO);
                    payListCnt += payListService.insertPayList(payListVO);
                    log.debug(String.format("%s[%s] => %s\n", "insertPayList", payListCnt, JsonUtil.toPrettyJson(payListVO)));

                    // 6.EDC_RSVN_COMPTINFO 테이블 입력
                    EdcRsvnComptinfoVO rsvnComptInfoVO = makeEdcRsvnComptinfoVO(item, rsvnInfoVO, selngInfoVO);
                    comptinfoCnt += edcRsvnComptinfoService.insertEdcRsvnComptinfo(rsvnComptInfoVO);
                    log.debug(String.format("%s[%s] => %s\n", "insertEdcRsvnComptinfo", comptinfoCnt, JsonUtil.toPrettyJson(rsvnComptInfoVO)));

                    bizMsgService.sendRsvnMessage(rsvnInfoVO.getEdcRsvnReqid());
                }
            }
        } else { // 최종결제금액 무:
            // 온라인일경우는 edc_rsvn_info 테이블에 2001(입금대기)건이 존재하고 4001(등록완료)로 전환 update
            // 오프라인일경우는 edc_rsvn_info 테이블에 바로 4001로 insert

            // 1.영수증(RECEPIT_INFO)테이블 입력
            ReceiptInfoVO receiptInfoVO = makeReceiptInfoVO(itemList.get(0), payment);
            receiptCnt = receiptInfoService.insertReceiptInfo(receiptInfoVO);
            log.debug(String.format("%s[%s] => %s\n", "insertReceiptInfo", receiptCnt, JsonUtil.toPrettyJson(receiptInfoVO)));

            String rsvnNo = payment.getRsvnNo();
            if (StringUtils.isBlank(rsvnNo)) {
                payment.setRsvnNo(edcRsvnInfoService.selectNextRsvnNumber());
            }

            for (SaleFormItemVO item : itemList) {
                EdcRsvnInfoVO rsvnInfoVO = makeEdcRsvnInfoVO(member, item, payment);
                if (!Config.YES.equals(item.getSkipRsvnInfoTableYn())) { // 관리자 > 추첨 + 무료인경우:Y
                    if (StringUtils.isNotBlank(rsvnNo)) {
                        // 2.EDC_RSVN_INFO 테이블 갱신
                        rsvnCnt += edcRsvnInfoService.completeRsvnInfo(rsvnInfoVO);
                        log.debug(String.format("%s[%s] => %s\n", "completeRsvnInfo", rsvnCnt, JsonUtil.toPrettyJson(rsvnInfoVO)));
                    } else {
                        // 2.EDC_RSVN_INFO 테이블 입력
                        rsvnCnt += edcRsvnInfoService.saveRsvnInfo(rsvnInfoVO);
                        log.debug(String.format("%s[%s] => %s\n", "saveRsvnInfo", rsvnCnt, JsonUtil.toPrettyJson(rsvnInfoVO)));
                    }
                }

                // 3.통합판매(SELNG_INFO)테이블입력
                SelngInfoVO selngInfoVO = makeSelngInfoVO(member, item, payment, receiptInfoVO);
                selngCnt += selngInfoService.insertSelngInfo(selngInfoVO);
                log.debug(String.format("%s[%s] => %s\n", "insertSelngInfo", selngCnt, JsonUtil.toPrettyJson(selngInfoVO)));

                // 4.결제내역(PAY_LIST)테이블입력
                PayListVO payListVO = makePayListVO(itemList.get(0), payment, receiptInfoVO, selngInfoVO);
                payListCnt += payListService.insertPayList(payListVO);
                log.debug(String.format("%s[%s] => %s\n", "insertPayList", payListCnt, JsonUtil.toPrettyJson(payListVO)));

                // 5.할인정보(SALEDISCOUNTS)테이블입력
                if (item.getDcAmt() > 0) {
                    SaleDiscountVO saleDiscountVO = makeSaleDiscountVO(item, payment, selngInfoVO);
                    saleDiscountCnt += saleDiscountService.insertSaleDiscount(saleDiscountVO);
                    log.debug(String.format("%s[%s] => %s\n", "insertSaleDiscount", saleDiscountCnt, JsonUtil.toPrettyJson(saleDiscountVO)));
                }

                // 6.EDC_RSVN_COMPTINFO 테이블 입력
                EdcRsvnComptinfoVO rsvnComptInfoVO = makeEdcRsvnComptinfoVO(item, rsvnInfoVO, selngInfoVO);
                comptinfoCnt += edcRsvnComptinfoService.insertEdcRsvnComptinfo(rsvnComptInfoVO);
                log.debug(String.format("%s[%s] => %s\n", "insertEdcRsvnComptinfo", comptinfoCnt, JsonUtil.toPrettyJson(rsvnComptInfoVO)));

            	// 관리자 > 추첨 + 무료인경우:Y 메세지 발송 안되도록 설정
                if (!Config.YES.equals(item.getSkipRsvnInfoTableYn())) { 
                	bizMsgService.sendRsvnMessage(rsvnInfoVO.getEdcRsvnReqid());
                }
            }

        }

        // log.debug(String.format("입력결과 rsvnCnt:%s, selngCnt:%s, payListCnt:%s, saleDiscountCnt:%s, comptinfoCnt:%s",
        // rsvnCnt, selngCnt, payListCnt, saleDiscountCnt, comptinfoCnt));

        int totalCnt = rsvnCnt + receiptCnt + selngCnt + payListCnt + saleDiscountCnt + pgOrdMstCnt + pgOrdDetCnt + cardAppHistCnt + comptinfoCnt;
        log.debug("totalCnt = {}", totalCnt);

        return totalCnt;
    }

    /**
     * 예약번호로 예약건이 있는지 조회하여, 현재 예약상태를 리턴한다.
     */
    @Deprecated
    private String getCurrentRsvnStat(SaleFormPaymentVO payment) throws Exception {
        if (StringUtils.isBlank(payment.getRsvnNo()))
            return "";

        EdcRsvnInfoVO rsvnInfoParam = new EdcRsvnInfoVO();
        rsvnInfoParam.setEdcRsvnNo(payment.getRsvnNo());
        List<EdcRsvnInfoVO> rsvnInfoList = edcRsvnInfoService.selectRsvnListForPay(rsvnInfoParam);

        if (rsvnInfoList == null || rsvnInfoList.isEmpty())
            return "";

        return rsvnInfoList.get(0).getEdcStat();
    }

    public int saveTossTransactionInfo(SaleFormVO saleFormVO) throws Exception {
        SaleFormMemberVO member = saleFormVO.getMember();
        List<SaleFormItemVO> itemList = saleFormVO.getItemList();
        SaleFormPaymentVO payment = saleFormVO.getPayment();

        // 7.CARD_APP_HIST
        CardAppHistVO cardAppHistVO = makeCardAppHistVO(member, itemList.get(0), payment);
        int cardAppHistCnt = saleChargeService.insertCardAppHist(cardAppHistVO);

        /*
         * 7~9 일괄 저장했으나, 저장시점을 달리하고 필요시 update 하는 것으로 요청을 받아 8~9 주석처리
         * int pgOrdMstCnt = 0;
         * int pgOrdDetCnt = 0;
         * if (itemList != null && !itemList.isEmpty()) {
         * // 8.PG_ORD_MST
         * PgOrdMstVO ordMstVO = makePgOrdMstVO(itemList.get(0), payment);
         * pgOrdMstCnt = saleChargeService.insertPgOrdMst(ordMstVO);
         * // 9.PG_ORD_DET
         * for (SaleFormItemVO item : itemList) {
         * PgOrdDetVO ordDetVO = makePgOrdDet(item, payment);
         * pgOrdDetCnt += saleChargeService.insertPgOrdDet(ordDetVO);
         * }
         * }
         */

        return cardAppHistCnt;
    }

    /**
     * 1st Step 관련 Tables
     * EDC_RSVN_INFO(update)
     * PG_ORD_MST, PG_ORD_DET INSERT(insert)
     * VBANK_PAYMENT_INFO, VBANK_ORDER_LIST(insert)
     */
    public int save1StepTablesForPG(SaleFormVO saleFormVO) throws Exception {

        log.debug("saleFormVO => {}", JsonUtil.toPrettyJson(saleFormVO));

        List<SaleFormItemVO> itemList = saleFormVO.getItemList();
        SaleFormPaymentVO payment = saleFormVO.getPayment();
        SaleFormMemberVO member = saleFormVO.getMember();

        int pgOrdMstCnt = 0;
        int pgOrdDetCnt = 0;
        int rsvnCnt = 0;
        int vBankPaymentCnt = 0;
        int vBankOrderListCnt = 0;

        if (itemList == null || itemList.isEmpty())
            return 0;

        // 1.PG_ORD_MST insert
        PgOrdMstVO ordMstVO = makePgOrdMstVO(itemList, payment);
        pgOrdMstCnt = saleChargeService.insertPgOrdMst(ordMstVO);
        log.debug(String.format("%s[%s] => %s\n", "insertPgOrdMst", pgOrdMstCnt, JsonUtil.toPrettyJson(ordMstVO)));

        int i = 1;
        for (SaleFormItemVO item : itemList) {
            // 2.PG_ORD_DET insert
            PgOrdDetVO ordDetVO = makePgOrdDet(item, payment);
            ordDetVO.setOidSeq(i++);
            pgOrdDetCnt += saleChargeService.insertPgOrdDet(ordDetVO);
            log.debug(String.format("%s[%s] => %s\n", "insertPgOrdDet", pgOrdDetCnt, JsonUtil.toPrettyJson(ordDetVO)));

            // 3.EDC_RSVN_INFO update
            EdcRsvnInfoVO rsvnInfoVO = makeEdcRsvnInfoVO(member, item, payment);
            rsvnCnt += edcRsvnInfoService.updateRsvnInfo(rsvnInfoVO);
            log.debug(String.format("%s[%s] => %s\n", "updateRsvnInfo", rsvnCnt, JsonUtil.toPrettyJson(rsvnInfoVO)));
        }

        if (Constant.TOSS_P_TYPE_가상계좌.equals(payment.getPayMethod())) {
            // 0. 기존 임시데이터 삭제(VBANK_PAYMENT_INFO, VBANK_ORDER_LIST)
            for (SaleFormItemVO item : itemList) {
                VbankOrderListVO vo = new VbankOrderListVO();
                vo.setComcd(item.getComcd());
                vo.setTrNo(item.getEdcRsvnReqid());
                int cnt = saleChargeService.deleteVbankInfo(vo);
                log.debug(String.format("%s[%s] => %s\n", "deleteVbankInfo", cnt, JsonUtil.toPrettyJson(vo)));
                break;
            }

            // 1.VBANK_PAYMENT_INFO 입력
            VbankPaymentInfoVO vPaymentVO = makeVbankPaymentInfoVO(member, itemList.get(0), payment);
            vPaymentVO.setVbankPayamt(0); // 입금금액
            vPaymentVO.setVbankOveramt(0); // 과오금액
            vPaymentVO.setVbankPaymentDate(null); // 입금완료일(pg사로부터 받은 전문의 입금일자)
            vPaymentVO.setVbankPaymentTime(null); // 입금완료시(pg사로부터 받은 전문의 입금시간)

            vBankPaymentCnt = saleChargeService.insertVbankPaymentInfo(vPaymentVO);
            log.debug(String.format("%s[%s] => %s\n", "insertVbankPaymentInfo", vBankPaymentCnt, JsonUtil.toPrettyJson(vPaymentVO)));

            for (SaleFormItemVO item : itemList) {
                // 2.VBANK_ORDER_LIST 입력
                VbankOrderListVO vOrderListVO = makeVbankOrderListVO(item, payment, vPaymentVO);
                vBankOrderListCnt += saleChargeService.insertVbankOrderList(vOrderListVO);
                log.debug(String.format("%s[%s] => %s\n", "insertVbankOrderList", vBankOrderListCnt, JsonUtil.toPrettyJson(vOrderListVO)));
            }

            // saveVBankCnt += saveVBankInfo(saleFormVO);
        }

        return pgOrdMstCnt + pgOrdDetCnt + rsvnCnt + vBankPaymentCnt + vBankOrderListCnt;
    }

    /**
     * 유료강좌 수강등록 + 결제금액 > 0 PG 가상계좌
     * 예약 > 결제시 가상계좌선택 > PG사로부터 받은 계좌정보 저장
     * 회원이 가상계좌로 입금 완료하면 pg사는 casnoteurl을 통해 입금정보를 통보하고 입금정보와,, 여기서저장한 정보를 토대로 최종 수강등록을 완료해야 한다.
     * 준비단계, 최초1회입력
     */
    @Deprecated
    public int saveVBankInfo(SaleFormVO sale) throws Exception {

        SaleFormMemberVO member = sale.getMember();
        List<SaleFormItemVO> itemList = sale.getItemList();
        // SaleFormProgramVO program = sale.getProgram();
        SaleFormPaymentVO payment = sale.getPayment();

        if (member == null || itemList == null || payment == null) {
            throw new RuntimeException("필요항목이 부족합니다.");
        }

        if (StringUtils.isBlank(payment.getOid())) {
            throw new RuntimeException("주문번호(oid)는 필수값입니다.");
        }

        // 0. 기존 임시데이터 삭제(VBANK_PAYMENT_INFO, VBANK_ORDER_LIST)
        for (SaleFormItemVO item : itemList) {
            VbankOrderListVO vo = new VbankOrderListVO();
            vo.setComcd(item.getComcd());
            vo.setTrNo(item.getEdcRsvnReqid());
            saleChargeService.deleteVbankInfo(vo);
            break;
        }

        // 1.VBANK_PAYMENT_INFO 입력
        VbankPaymentInfoVO vPaymentVO = makeVbankPaymentInfoVO(member, itemList.get(0), payment);
        vPaymentVO.setVbankPayamt(0); // 입금금액
        vPaymentVO.setVbankOveramt(0); // 과오금액
        vPaymentVO.setVbankPaymentDate(null); // 입금완료일(pg사로부터 받은 전문의 입금일자)
        vPaymentVO.setVbankPaymentTime(null); // 입금완료시(pg사로부터 받은 전문의 입금시간)

        int vBankPaymentCnt = saleChargeService.insertVbankPaymentInfo(vPaymentVO);

        int vBankOrderListCnt = 0;
        int rsvnInfoCnt = 0;

        for (SaleFormItemVO item : itemList) {
            // 2.VBANK_ORDER_LIST 입력
            VbankOrderListVO vOrderListVO = makeVbankOrderListVO(item, payment, vPaymentVO);
            vBankOrderListCnt += saleChargeService.insertVbankOrderList(vOrderListVO);
        }

        return vBankOrderListCnt + vBankPaymentCnt + rsvnInfoCnt;
    }

    /**
     * PG>가상계좌> 입급계좌가 발급된 후 update
     */
    public int updateVBankInfo(SaleFormVO saleFormVO) throws Exception {
        log.debug("saleFormVO => {}", JsonUtil.toPrettyJson(saleFormVO));

        SaleFormMemberVO member = saleFormVO.getMember();
        List<SaleFormItemVO> itemList = saleFormVO.getItemList();
        SaleFormPaymentVO payment = saleFormVO.getPayment();

        // 1.VBANK_PAYMENT_INFO 갱신
        VbankPaymentInfoVO vPaymentVO = makeVbankPaymentInfoVO(member, itemList.get(0), payment);
        int vBankPaymentCnt = saleChargeService.updateVbankPaymentInfo(vPaymentVO);
        log.debug(String.format("%s[%s] => %s\n", "updateVbankPaymentInfo", vBankPaymentCnt, JsonUtil.toPrettyJson(vPaymentVO)));

        return vBankPaymentCnt;
    }

    /**
     * 유료: PG > 가상계좌이체. 입금
     */
    public int updateRegisterVBank(SaleFormVO sale) throws Exception {
        // 1. VBANK_PAYMENT_INFO update (상태 및 입금일시 변경)

        // 2. VBANK_ORDER_LIST update (상태 및 입금일시변경)

        // 3. 나머지 테이블들 insert

        return 1;
    }

    private EdcRsvnComptinfoVO makeEdcRsvnComptinfoVO(SaleFormItemVO item, EdcRsvnInfoVO rsvnInfoVO,
            SelngInfoVO selngInfoVO) {
        EdcRsvnComptinfoVO rsvnComptInfoVO = new EdcRsvnComptinfoVO();
        rsvnComptInfoVO.setComcd(item.getComcd());
        rsvnComptInfoVO.setEdcRsvnReqid(rsvnInfoVO.getEdcRsvnReqid());
        // rsvnComptInfoVO.setEdcRsvnComptid(edcRsvnComptid);//쿼리내 조회 입력(max+1)
        rsvnComptInfoVO.setEdcRsvnMemtype(rsvnInfoVO.getEdcRsvnMemtype());
        rsvnComptInfoVO.setEdcMemNo(rsvnInfoVO.getEdcMemNo());
        rsvnComptInfoVO.setEdcReqMemNo(rsvnInfoVO.getEdcRsvnMemno());
        rsvnComptInfoVO.setEdcPrgmNo(rsvnInfoVO.getEdcPrgmNo());
        rsvnComptInfoVO.setEdcRsvnCustnm(rsvnInfoVO.getEdcRsvnCustnm());
        rsvnComptInfoVO.setEdcRsvnBirthdate(rsvnInfoVO.getEdcRsvnBirthdate());
        rsvnComptInfoVO.setEdcRsvnGender(rsvnInfoVO.getEdcRsvnGender());
        rsvnComptInfoVO.setEdcRsvnMoblphon(rsvnInfoVO.getEdcRsvnMoblphon());
        // rsvnComptInfoVO.setEdcNonmembCertno(edcNonmembCertno);
        rsvnComptInfoVO.setSelngId(selngInfoVO.getSelngId());
        rsvnComptInfoVO.setEdcSdate(rsvnInfoVO.getEdcReqSdate());
        rsvnComptInfoVO.setEdcEdate(rsvnInfoVO.getEdcReqEdate());
        rsvnComptInfoVO.setEdcStime(rsvnInfoVO.getEdcReqStime());
        rsvnComptInfoVO.setEdcEtime(rsvnInfoVO.getEdcReqEtime());
        // rsvnComptInfoVO.setEdcComplstat(edcComplstat); // 교육수료상태
        // rsvnComptInfoVO.setEdcComplyn(edcComplyn); //교육수료여부
        // rsvnComptInfoVO.setEdcConfirmDate(edcConfirmDate); //교육수료확정일자
        // rsvnComptInfoVO.setEdcComplUserid(edcComplUserid); //교육수료처리자
        // rsvnComptInfoVO.setEdcRetyn(edcRetyn); //환불여부
        // rsvnComptInfoVO.setRetSelngId(retSelngId); //환불selngid
        return rsvnComptInfoVO;
    }

    private CardAppHistVO makeCardAppHistVO(SaleFormMemberVO member, SaleFormItemVO item, SaleFormPaymentVO payment) {
        CardAppHistVO cardAppHistVO = new CardAppHistVO();
        cardAppHistVO.setComcd(item.getComcd());
        cardAppHistVO.setOrgNo(item.getOrgNo());
        cardAppHistVO.setAppDate(payment.getCertDtime());
        cardAppHistVO.setAppNo(payment.getCertNo());
        cardAppHistVO.setMemNo(member.getMemNo());
        cardAppHistVO.setAppGbn(payment.getAppGbn());
        // cardAppHistVO.setCnlDate(cnlDate);
        cardAppHistVO.setPartialCnlyn(Config.NO);
        cardAppHistVO.setCardNo1(payment.getCardNo1());
        cardAppHistVO.setCardNo2(payment.getCardNo2());
        cardAppHistVO.setCardNo3(payment.getCardNo3());
        // cardAppHistVO.setCardNo4("****");
        cardAppHistVO.setPComcd(payment.getPayComcd());
        cardAppHistVO.setPType(payment.getPayMethod());
        cardAppHistVO.setCardSec(payment.getFinanceCd()); // pg사에서 받는값으로 은행코드,카드사코드
        // cardAppHistVO.setBankCd(payment.getPayCd()); // 12월10일 미입력으로
        // cardAppHistVO.setCardCd(payment.getPayCd()); // 12월10일 미입력으로
        cardAppHistVO.setHalbuCnt(payment.getHalbuCnt());
        cardAppHistVO.setAppAmt(payment.getPayAmt());
        // cardAppHistVO.setCashUserInfo(cashUserInfo); //TODO: 현금영수증발행관련 추가
        // cardAppHistVO.setCashAppGbn(cashAppGbn); //TODO: 현금영수증발행관련 추가
        // cardAppHistVO.setCashAppCnldate(cashAppCnldate); //TODO: 현금영수증취소관련 추가
        // cardAppHistVO.setCardSaleNo(cardSaleNo); //올렛결제서비스일경우. 12월10일 미입력으로
        cardAppHistVO.setStoreNo(payment.getStoreNo());
        cardAppHistVO.setPayListYn(payment.getPayListYn());
        cardAppHistVO.setForceYn(Config.NO);
        cardAppHistVO.setPgVan("PG");
        // cardAppHistVO.setKeyinType(keyinType);
        // cardAppHistVO.setInDate(inDate);
        cardAppHistVO.setCheckGbn(payment.getCheckCardYn());
        // cardAppHistVO.setCardandbankRateAmt(cardandbankRateAmt);
        cardAppHistVO.setCardandbankRate(payment.getFeeRate());
        // cardAppHistVO.setRemark(remark);
        cardAppHistVO.setMid(payment.getMid());
        cardAppHistVO.setOid(payment.getOid());
        cardAppHistVO.setTid(payment.getTid());
        // cardAppHistVO.setOrderTerminalid(orderTerminalid);
        // cardAppHistVO.setPcancelNo(pcancelNo);
        cardAppHistVO.setUip(payment.getIp());
        cardAppHistVO.setTerminalType(payment.getTerminalType());
        cardAppHistVO.setResultmsg(String.format("%s|%s", payment.getResultCd(), payment.getResultMsg()));

        return cardAppHistVO;
    }

    private PgOrdDetVO makePgOrdDet(SaleFormItemVO item, SaleFormPaymentVO payment) {
        PgOrdDetVO ordDetVO = new PgOrdDetVO();
        ordDetVO.setComcd(item.getComcd());
        ordDetVO.setOid(payment.getOid());
        // ordDetVO.setOidSeq(oidSeq);//쿼리내 조회 입력
        ordDetVO.setOidRsvnNo(payment.getRsvnNo());
        ordDetVO.setOidItemcd(item.getItemCd());
        // ordDetVO.setOidPcancelNo(oidPcancelNo);
        return ordDetVO;
    }

    private PgOrdMstVO makePgOrdMstVO(List<SaleFormItemVO> item, SaleFormPaymentVO payment) {
        PgOrdMstVO ordMstVO = new PgOrdMstVO();
        ordMstVO.setComcd(item.get(0).getComcd());
        ordMstVO.setOid(payment.getOid());
        ordMstVO.setOidAmt(payment.getPayAmt());
        ordMstVO.setOidStat(payment.getOidStat());
        ordMstVO.setOidDetRowcnt(item.size());
        if (StringUtils.isNotBlank(payment.getResultCd())) {
            ordMstVO.setRequestResult(String.format("%s|%s", payment.getResultCd(), payment.getResultMsg()));
        }
        // ordMstVO.setOidCnlDate(oidCnlDate);
        // ordMstVO.setOidCnlTime(oidCnlTime);
        // ordMstVO.setOidCnlResult(oidCnlResult);
        if (StringUtils.isNotBlank(payment.getCertNo())) {
            ordMstVO.setOidAppNo(payment.getCertNo());
        }
        return ordMstVO;
    }

    private SaleDiscountVO makeSaleDiscountVO(SaleFormItemVO item, SaleFormPaymentVO payment, SelngInfoVO selngInfoVO) {
        SaleDiscountVO saleDiscountVO = new SaleDiscountVO();
        saleDiscountVO.setComcd(item.getComcd());
        saleDiscountVO.setSaleSeq(selngInfoVO.getSelngId());
        // saleDiscountVO.setSeq(seq);
        saleDiscountVO.setDiscountdate(payment.getSaleDate());
        saleDiscountVO.setDiscountcd(item.getDiscountCd());
        saleDiscountVO.setBeforeamount(item.getSalamt());
        saleDiscountVO.setDiscountrate(item.getDiscountRate());
        saleDiscountVO.setDiscountamount(item.getDcAmt());
        saleDiscountVO.setNwpayOrdid(item.getNwpayOrdid());
        return saleDiscountVO;
    }

    private PayListVO makePayListVO(SaleFormItemVO item, SaleFormPaymentVO payment, ReceiptInfoVO receiptInfoVO,
            SelngInfoVO selngInfoVO) {
        PayListVO payListVO = new PayListVO();
        payListVO.setComcd(item.getComcd());
        payListVO.setSelngId(selngInfoVO.getSelngId());
        // payListVO.setPaySeq(paySeq); //max+1
        payListVO.setSlipType(Constant.SM_SLIP_TYPE_정상전표);
        payListVO.setReceiptNo(receiptInfoVO.getReceiptNo());
        payListVO.setPayDate(payment.getSaleDate());
        payListVO.setPayTime(payment.getSaleTime());
        payListVO.setPayAmt(item.getSalamt() - item.getDcAmt());
        payListVO.setAppDate(StringUtils.isBlank(payment.getCertDtime()) ? null : payment.getCertDtime());
        payListVO.setAppNo(payment.getCertNo());
        // payListVO.setCashier(cashier); //수납자
        // payListVO.setInDate(inDate); //입금예정일자
        if (StringUtils.isNotBlank(payment.getPayComcd())) {
            payListVO.setPComcd(payment.getPayComcd()); // 온라인입금일경우만 설정
            payListVO.setPType(payment.getPayMethod()); // CASH, CARD, BANK, VBANK
            payListVO.setMethodCd(payment.getFinanceCd()); // 카드사,은행사코드
            payListVO.setMethodNm(payment.getFinanceNm()); // 카드사,은행명
            payListVO.setFinanceBrandCd(payment.getFinanceBrandCd()); // 카드사일경우 브랜드코드=> 100(visa), 200(master),,
        } /*
           * else {
           * payListVO.setPComcd("XX");
           * }
           */
        payListVO.setCheckGbn(payment.getCheckCardYn());
        payListVO.setOid(payment.getOid());
        payListVO.setCancelYn(Config.NO);
        payListVO.setTerminalType(payment.getTerminalType());
        // payListVO.setRemark(payment.getPayListRemark());
        payListVO.setSlipState(Constant.SM_SLIP_STATE_정상);
        // payListVO.setOrgPayDate(""); //환불취소원거래일자
        // payListVO.setParentcomcd(parentcomcd); //환불취소시
        // payListVO.setParentselngid(parentselngid); //환불취소시
        // payListVO.setParentpayseq(parentpayseq); //환불취소시
        return payListVO;
    }

    private SelngInfoVO makeSelngInfoVO(SaleFormMemberVO member, SaleFormItemVO item,
            SaleFormPaymentVO payment, ReceiptInfoVO receiptInfoVO) {
        SelngInfoVO selngInfoVO = new SelngInfoVO();
        selngInfoVO.setComcd(item.getComcd());
        // selngInfoVO.setSelngId(selngId);
        selngInfoVO.setMemNo(member.getMemNo());
        selngInfoVO.setRegistGbn(Constant.SM_REGIST_GBN_신규등록);
        // selngInfoVO.setRegistPartcd(item.getPartCd()); //미입력으로결곤
        selngInfoVO.setOrgNo(item.getOrgNo());
        // selngInfoVO.setSelngPartCd(item.getPartCd()); //미입력으로결론
        // selngInfoVO.setItemCtgd(program.getCtgCd()); //미입력으로결론
        selngInfoVO.setItemCd(item.getItemCd());
        selngInfoVO.setSelngDate(payment.getSaleDate());
        selngInfoVO.setSelngTime(payment.getSaleTime());
        selngInfoVO.setSlipType(Constant.SM_SLIP_TYPE_정상전표);
        selngInfoVO.setUseMonthcnt(item.getMonthCnt());
        selngInfoVO.setUseItemSdate(item.getEdcSdate());
        selngInfoVO.setUseItemEdate(item.getEdcEdate());
        selngInfoVO.setCostAmt(item.getCostAmt()); // 원가
        selngInfoVO.setUnitAmt(item.getSalamt()); // 단가
        selngInfoVO.setSalnum(1); // 갯수
        selngInfoVO.setDcAmt(item.getDcAmt()); // 할인
        selngInfoVO.setSalamt(item.getSalamt() - item.getDcAmt()); // unitamt-dcamt
        // selngInfoVO.setSalamtUpdnUnit(Constant.SM_SALE_UPDN_UNIT_원); // 미입력으로결론
        // selngInfoVO.setSalamtUpdnGbn(Constant.SM_SALE_UPDN_GBN_절상); // 미입력으로결론
        selngInfoVO.setVatYn(item.getVatYn());
        // selngInfoVO.setVatAmt(0);//쿼리내에서 입력
        selngInfoVO.setSetleMemno(member.getMemNo());
        selngInfoVO.setMidReturnyn(Config.NO);
        // selngInfoVO.setChangeYn(Config.NO); // 미입력으로결론
        selngInfoVO.setReturnYn(Config.NO);
        // selngInfoVO.setOrgSelngDate(orgSelngDate);
        // selngInfoVO.setDelYn(Config.NO); // 미입력으로결론
        // selngInfoVO.setRecessYn(Config.NO); // 미입력으로결론
        selngInfoVO.setRemark(payment.getSelngInfoRemark());
        selngInfoVO.setTerminalType(payment.getTerminalType());
        selngInfoVO.setOid(payment.getOid());
        selngInfoVO.setReceiptNo(receiptInfoVO.getReceiptNo());
        selngInfoVO.setSlipState(Constant.SM_SLIP_STATE_정상);
        // selngInfoVO.setParentcomcd(parentcomcd); //취소일경우 넣어야
        // selngInfoVO.setParentselngid(parentselngid); //취소일경우 넣어야
        selngInfoVO.setDpstrNm(payment.getDpstrNm());
        selngInfoVO.setBankCd(payment.getBankCd());
        selngInfoVO.setBankNm(payment.getBankNm());
        selngInfoVO.setAcountNum(payment.getAccountNum());
        return selngInfoVO;
    }

    private ReceiptInfoVO makeReceiptInfoVO(SaleFormItemVO item, SaleFormPaymentVO payment) {
        ReceiptInfoVO receiptInfoVO = new ReceiptInfoVO();
        receiptInfoVO.setComcd(item.getComcd());
        receiptInfoVO.setOrgNo(item.getOrgNo());
        receiptInfoVO.setRptDate(payment.getSaleDate());
        receiptInfoVO.setRptTime(payment.getSaleTime());
        receiptInfoVO.setPayAmt(payment.getPayAmt());
        receiptInfoVO.setCashAmt(payment.getCashAmt());
        receiptInfoVO.setCardAmt(payment.getCardAmt());
        return receiptInfoVO;
    }

    private EdcRsvnInfoVO makeEdcRsvnInfoVO(SaleFormMemberVO member, SaleFormItemVO item,
            SaleFormPaymentVO payment) throws Exception {
        EdcRsvnInfoVO rsvnInfoVO = new EdcRsvnInfoVO();
        rsvnInfoVO.setComcd(item.getComcd());
        if (item.getEdcRsvnReqid() > 0)
            rsvnInfoVO.setEdcRsvnReqid(item.getEdcRsvnReqid()); // 그렇지 않을 경우 쿼리내에서 nextval
        rsvnInfoVO.setEdcRsvnNo(payment.getRsvnNo());
        // rsvnInfoVO.setEdcReqDate(edcReqDate); //sysdate
        // rsvnInfoVO.setEdcReqTime(edcReqTime); //sysdate
        rsvnInfoVO.setEdcMemNo(member.getMemNo());
        rsvnInfoVO.setEdcRsvnMemno(member.getMemNo());
        rsvnInfoVO.setEdcRsvnMemtype(StringUtils.isBlank(member.getMemNo())
                ? Constant.EDC_RSVN_MEMTYPE_비회원 : Constant.EDC_RSVN_MEMTYPE_회원);
        // rsvnInfoVO.setEdcRsvnOrgname(edcRsvnOrgname);
        // rsvnInfoVO.setEdcRsvnScnm(edcRsvnScnm); //학교이름

        if (StringUtils.isBlank(member.getMemNo())) {
            rsvnInfoVO.setEdcRsvnCustnm(member.getMemNm());
            // rsvnInfoVO.setEdcRsvnTel(edcRsvnTel);
            rsvnInfoVO.setEdcRsvnMoblphon(member.getMemHp().replaceAll("-", ""));
            // rsvnInfoVO.setEdcEmail(edcEmail);
            // rsvnInfoVO.setEdcReqCustnm(member.getMemNm());
            rsvnInfoVO.setEdcRsvnBirthdate(member.getMemBirthdate().replaceAll("-", ""));
            rsvnInfoVO.setEdcRsvnGender(member.getMemGender());
            // rsvnInfoVO.setEdcReqMoblphon(member.getMemHp());
        }

        // rsvnInfoVO.setEdcNonmembCertno(edcNonmembCertno);
        rsvnInfoVO.setEdcVistnmpr(1);
        rsvnInfoVO.setEdcRegistgbn(Constant.SM_REGIST_GBN_신규등록);
        rsvnInfoVO.setEdcPrgmNo(item.getEdcPrgmNo());
        rsvnInfoVO.setEdcReqItemCd(item.getItemCd());
        rsvnInfoVO.setEdcRsvnsetSeq(item.getEdcRsvnsetSeq());
        // rsvnInfoVO.setEdcReqSdate(edcReqSdate); //쿼리내수행
        // rsvnInfoVO.setEdcReqEdate(edcReqEdate); //쿼리내수행
        // rsvnInfoVO.setEdcReqStime(edcReqStime); //쿼리내수행
        // rsvnInfoVO.setEdcReqEtime(edcReqEtime); //쿼리내수행
        rsvnInfoVO.setEdcMonthcnt(item.getMonthCnt());
        rsvnInfoVO.setEdcProgmCost(item.getSalamt());
        rsvnInfoVO.setEdcOnoffintype(payment.getOnoff());
        rsvnInfoVO.setEdcOnoffpyntype(payment.getOnoff());
        rsvnInfoVO.setEdcReasondc(item.getDiscountCd());
        rsvnInfoVO.setEdcDcamt(item.getDcAmt());
        rsvnInfoVO.setEdcTotamt(item.getSalamt() - item.getDcAmt());
        rsvnInfoVO.setEdcTrmnlType(payment.getTerminalType());
        // rsvnInfoVO.setEdcPaywaitEnddatetime(edcPaywaitEnddatetime); //edcRsvnInfoService.saveRsvnInfo에서 처리
        // rsvnInfoVO.setEdcConfirmDate(edcConfirmDate);
        // rsvnInfoVO.setEdcRetyn(edcRetyn);
        // rsvnInfoVO.setRetSelngId(retSelngId);
        rsvnInfoVO.setNwpayUserid(item.getNwpayId());
        rsvnInfoVO.setEdcStat(Constant.SM_RSVN_STAT_등록완료);
        return rsvnInfoVO;
    }

    private VbankOrderListVO makeVbankOrderListVO(SaleFormItemVO item, SaleFormPaymentVO payment,
            VbankPaymentInfoVO vPaymentVO) {
        VbankOrderListVO vOrderListVO = new VbankOrderListVO();
        vOrderListVO.setComcd(item.getComcd());
        vOrderListVO.setVbankSeq(vPaymentVO.getVbankSeq());
        // vOrderListVO.setVbankOrdseq(vbankOrdseq); //쿠러내 조회 입력(MAX+1)
        vOrderListVO.setVbankReqtype(Constant.SM_VBANK_REQTYPE_교육프로그램및사물함신청결제);
        vOrderListVO.setTrNo(item.getEdcRsvnReqid());
        vOrderListVO.setCostAmt(item.getCostAmt());
        vOrderListVO.setDcAmt(item.getDcAmt());
        vOrderListVO.setTotalAmt(item.getCostAmt() - item.getDcAmt());
        return vOrderListVO;
    }

    private VbankPaymentInfoVO makeVbankPaymentInfoVO(SaleFormMemberVO member, SaleFormItemVO item,
            SaleFormPaymentVO payment) {
        VbankPaymentInfoVO vPaymentVO = new VbankPaymentInfoVO();
        vPaymentVO.setComcd(item.getComcd());
        // vPaymentVO.setVbankSeq(vbankSeq); //쿼리내 조회 입력(BEFORE)
        vPaymentVO.setVbankMemNo(member.getMemNo());
        vPaymentVO.setVbankReqtype(Constant.SM_VBANK_REQTYPE_교육프로그램및사물함신청결제);

        vPaymentVO.setVbankAccountNo(payment.getVbankAccountNo());
        vPaymentVO.setVbankReqDate(payment.getSaleDate());
        vPaymentVO.setVbankReqTime(payment.getSaleTime());
        vPaymentVO.setVbankName(payment.getVbankName());

        vPaymentVO.setVbankPname(payment.getVbankPname());
        vPaymentVO.setVbankPtel(payment.getVbankPtel());
        vPaymentVO.setVbankPemail(payment.getVbankPemail());
        vPaymentVO.setVbankPayWaitdate(payment.getVbankPayWaitdate());
        vPaymentVO.setVbankPayWaittime(payment.getVbankPayWaittime());

        vPaymentVO.setVbankStatus(payment.getVbankStatus()); // 호출부에서 설정해야
        vPaymentVO.setVbankBankcd(payment.getVbankBankcd());
        vPaymentVO.setVbankAmount(payment.getPayAmt()); // 예정금액

        vPaymentVO.setVbankMid(payment.getMid());
        vPaymentVO.setVbankTid(payment.getTid());
        vPaymentVO.setVbankOid(payment.getOid());

        vPaymentVO.setRetDpstrNm(payment.getDpstrNm());
        vPaymentVO.setRetBankNm(payment.getBankNm());
        vPaymentVO.setRetBankCd(payment.getBankCd());
        vPaymentVO.setRetAcountNum(payment.getAccountNum());

        if (StringUtils.isNotBlank(payment.getResultCd()))
            vPaymentVO.setVbankEtc(payment.getResultCd() + ":" + payment.getResultMsg());

        return vPaymentVO;
    }

    public int wait(SaleFormVO saleFormVO) throws Exception {
        return 1;
    }

}
