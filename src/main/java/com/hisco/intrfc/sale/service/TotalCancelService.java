package com.hisco.intrfc.sale.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.intrfc.sale.vo.CancelVO;
import com.hisco.intrfc.sale.vo.CardAppHistVO;
import com.hisco.intrfc.sale.vo.EdcRsvnComptinfoVO;
import com.hisco.intrfc.sale.vo.NwPayApiType;
import com.hisco.intrfc.sale.vo.NwPayVO;
import com.hisco.intrfc.sale.vo.PayListVO;
import com.hisco.intrfc.sale.vo.PgOrdDetVO;
import com.hisco.intrfc.sale.vo.SaleDiscountVO;
import com.hisco.intrfc.sale.vo.SelngInfoVO;
import com.hisco.intrfc.sale.vo.TossResponseVO;
import com.hisco.intrfc.sms.service.BizMsgService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : TotalCancelService.java
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
@Service("totalCancelService")
public class TotalCancelService extends EgovAbstractServiceImpl {

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

    @Resource(name = "cancelService")
    private CancelService cancelService;

    @Resource(name = "tossService")
    private TossService tossService;

    @Resource(name = "nwPayService")
    private NwPayService nwPayService;

    @Resource(name = "bizMsgService")
    private BizMsgService bizMsgService;

    /**
     * 취소
     * - 무료상품 취소
     * - 유료상품 취소 : 할인100%
     * - 유료상품 취소 : (할인xx%) + PG
     */
    public int cancel(List<EdcRsvnInfoVO> rsvnList) throws Exception {
        log.debug("[CANCEL] rsvnList => {}", JsonUtil.toPrettyJson(rsvnList));

        int totCnt = 0;
        int payAmt = 0;

        for (EdcRsvnInfoVO rsvnVO : rsvnList) {
            if (!Constant.SM_RSVN_STAT_등록완료.equals(rsvnVO.getEdcStat()))
                continue;

            if (rsvnVO.getEdcProgmCost() == 0) { // 무료상품
                totCnt += this.cancelFreeItem(rsvnVO);
                bizMsgService.sendRsvnMessage(rsvnVO.getEdcRsvnReqid());
                continue;
            }

            payAmt += rsvnVO.getEdcTotamt();

            if (rsvnVO.getEdcDcamt() + rsvnVO.getEdcTotamt() > 0) { // 유료상품
                if (StringUtils.isBlank(rsvnVO.getOid())) { // 할인율100%(oid미존재)
                    totCnt += this.cancelDc100FreeItem(rsvnVO);
                } else { // (할인xx%) + PG(oid존재)
                    totCnt += this.cancelPayItem(rsvnVO);
                }
                bizMsgService.sendRsvnMessage(rsvnVO.getEdcRsvnReqid());
            }
        }

        /* NWPAY 금액 존재시 취소 */
        for (EdcRsvnInfoVO rsvnVO : rsvnList) {
            if (!Constant.SM_RSVN_STAT_등록완료.equals(rsvnVO.getEdcStat()))
                continue;

            if (!"NWPY".equals(rsvnVO.getEdcReasondc()))
                continue;

            if (rsvnVO.getEdcDcamt() < 1)
                continue;

            if (StringUtils.isBlank(rsvnVO.getNwpayOrdid()))
                continue;

            NwPayVO reqPayVO = new NwPayVO();
            reqPayVO.setApiType(NwPayApiType.WITHDRAWAL);
            reqPayVO.setOrderID(rsvnVO.getNwpayOrdid());
            reqPayVO.setOrgNo(rsvnVO.getOrgNo());
            NwPayVO resPayVO = nwPayService.call(reqPayVO);
            if (!"200,302".contains(resPayVO.getStatus())) { // 200:성공, 302:기취소건
                log.error("NWPAY 환불 reqPayVO = {}", JsonUtil.toPrettyJson(reqPayVO));
                log.error("NWPAY 환불 resPayVO = {}", JsonUtil.toPrettyJson(resPayVO));
                throw new RuntimeException("노원PAY > " + resPayVO.getStatusDesc());
            }
        }

        /* PG결제건 존재시 취소 */
        for (EdcRsvnInfoVO rsvnVO : rsvnList) {
            /*
             * 2021.12.28 주석처리. 가상계좌도 카드,실시간과 처리 동일시
             * if (StringUtils.isBlank(rsvnVO.getAppNo())) // 승인번호가 없으면(가상계좌) skip
             * continue;
             */

            if (Constant.SM_RSVN_STAT_등록완료.equals(rsvnVO.getEdcStat()) && StringUtils.isNotBlank(rsvnVO.getTid())) {

                TossResponseVO tossResponseVO = tossService.cancel(rsvnVO.getMid(), rsvnVO.getTid(), rsvnVO.getRetDpstrNm(), rsvnVO.getRetBankCd(), rsvnVO.getRetAcountNum(), rsvnVO.getEdcRsvnMoblphon());
                log.debug("[CANCEL] tossCancelResponse => {}", JsonUtil.toPrettyJson(tossResponseVO));

                CardAppHistVO histVO = new CardAppHistVO();
                histVO.setComcd(rsvnVO.getComcd());
                histVO.setAppDate(tossResponseVO.getLGD_RESPDATE());
                histVO.setAppNo(rsvnVO.getAppNo());
                histVO.setTid(rsvnVO.getTid());
                histVO.setOid(rsvnVO.getOid());
                histVO.setAppGbn(Constant.SM_APP_GBN_카드취소);
                if (StringUtils.isNotBlank(tossResponseVO.getLGD_TIMESTAMP())) {
                    histVO.setCnlDate(tossResponseVO.getLGD_TIMESTAMP().substring(0, 8));
                    histVO.setCnlTime(tossResponseVO.getLGD_TIMESTAMP().substring(8));
                }
                histVO.setPartialCnlyn(Config.NO);
                histVO.setAppAmt(payAmt);
                histVO.setResultmsg(tossResponseVO.getLGD_RESPCODE() + "|" + tossResponseVO.getLGD_RESPMSG());

                int cardAppHistCnt = saleChargeService.newTxCancelCardAppHist(histVO);
                totCnt += cardAppHistCnt;

                log.debug(String.format("[CANCEL] %s[%s] => %s\n", "cancelCardAppHist", cardAppHistCnt, JsonUtil.toPrettyJson(histVO)));

                if (!TossService.TOSS_CANCEL_OK_LIST.contains(tossResponseVO.getLGD_RESPCODE())) { // PG결제취소실패
                    log.error("[CANCEL] 결제취소 실패 => {}", JsonUtil.toPrettyJson(tossResponseVO));
                    throw new RuntimeException(tossResponseVO.getLGD_RESPMSG());
                }

                /*
                 * if (StringUtils.isBlank(rsvnVO.getAppNo())) { // 가상계좌는 아래 로직 불필요
                 * continue;
                 * }
                 */

                PgOrdDetVO pgOrdDetVO = new PgOrdDetVO();
                pgOrdDetVO.setComcd(rsvnVO.getComcd());
                // pgOrdDetVO.setOidPcancelNo(null);
                pgOrdDetVO.setOid(rsvnVO.getOid());
                int pgOrdDetCnt = saleChargeService.cancelPgOrdDet(pgOrdDetVO);
                totCnt += pgOrdDetCnt;
                log.debug(String.format("[CANCEL] %s[%s] => %s\n", "cancelPgOrdDet", pgOrdDetCnt, JsonUtil.toPrettyJson(pgOrdDetVO)));

                break; // 1회만처리
            }
        }

        return totCnt;
    }

    /**
     * 무표상품 취소
     */
    private int cancelFreeItem(EdcRsvnInfoVO rsvnVO) throws Exception {
        int rsvnCnt = 0;
        int selngCnt = 0;
        int payListCnt = 0;
        int comptinfoCnt = 0;
        int cancelCnt = 0;

        // 1.영수증(RECEPIT_INFO)테이블 입력: 불필요
        /*
         * ReceiptInfoVO receiptInfoVO = makeReceiptInfoVO(itemList.get(0), payment);
         * receiptCnt = receiptInfoService.updateReceiptInfo(receiptInfoVO);
         */

        // payment.setRsvnNo(edcRsvnInfoService.selectNextRsvnNumber());

        // 2.통합판매(SELNG_INFO)테이블입력
        SelngInfoVO selngInfoVO = new SelngInfoVO();
        selngInfoVO.setComcd(rsvnVO.getComcd());
        // selngInfoVO.setSelngId(selngInfoService.selectNextSelngId()); //쿼리내조회
        selngInfoVO.setOrgSelngId(rsvnVO.getSelngId());
        selngInfoVO.setSlipType(Constant.SM_SLIP_TYPE_취소전표);
        selngInfoVO.setSlipState(Constant.SM_SLIP_STATE_취소);
        selngCnt += selngInfoService.cancelSelngInfo(selngInfoVO);

        // rsvnVO.selngNo => 매출원장의 고유번호
        // selngInfoVO.selngNo => 취소장의 고유번호 (mapper호출시 쿼리내 생성)

        // 3.결제내역(PAY_LIST)테이블입력
        PayListVO payListVO = new PayListVO();
        payListVO.setComcd(rsvnVO.getComcd());
        payListVO.setSelngId(selngInfoVO.getSelngId());
        payListVO.setOrgSelngId(rsvnVO.getSelngId());
        payListVO.setOrgPaySeq(rsvnVO.getPaySeq());
        payListVO.setSlipType(Constant.SM_SLIP_TYPE_취소전표);
        payListVO.setSlipState(Constant.SM_SLIP_STATE_취소);
        payListCnt += payListService.cancelPayList(payListVO);

        // 4.할인정보(SALEDISCOUNTS)테이블입력
        /*
         * if (payment.getDcAmt() > 0) {
         * SaleDiscountVO saleDiscountVO = makeSaleDiscountVO(item, payment, selngInfoVO);
         * saleDiscountVO.setOrgSaleSeq(item.getSelngId());
         * saleDiscountVO.setOrgSeq(item.getPaySeq());
         * saleDiscountCnt += saleDiscountService.cancelSaleDiscount(saleDiscountVO);
         * }
         */

        // 5.CANCEL 테이블 입력
        CancelVO cancelVO = new CancelVO();
        cancelVO.setComcd(rsvnVO.getComcd());
        cancelVO.setSaleSeq(selngInfoVO.getSelngId());
        // cancelVO.setRetRegdate(retRegdate);
        cancelVO.setMemNo(rsvnVO.getEdcMemNo());
        // cancelVO.setCancelCd(null);
        // cancelVO.setCancelDate(cancelDate);
        cancelVO.setReturnAmt(rsvnVO.getEdcTotamt()); // 환불금액. 여기서는0. BO에서는 관리자 입력금액
        cancelVO.setVatAmt(0);
        cancelVO.setBreakAmt(0); // 위약금
        cancelVO.setUseAmt(0); // 이용금액. BO에서는 결제금액 - 환불금액
        // cancelVO.setRateAmt(rateAmt); //미정
        // cancelVO.setRemark(remark);
        cancelVO.setReturnSelngid(rsvnVO.getSelngId());
        cancelVO.setOldItemEdate(rsvnVO.getEdcReqEdate());
        cancelVO.setTotalBasedDaycnt(rsvnVO.getEdcClcnt()); // 총수업횟수
        cancelVO.setUsedDaysCount(0); // 수업참여일수. BO에서 환불시 쿼리를 통해 계산
        cancelCnt += cancelService.insertCancel(cancelVO);

        // 6.EDC_RSVN_INFO 테이블 입력
        EdcRsvnInfoVO tobeRsvnInfoVO = new EdcRsvnInfoVO();
        tobeRsvnInfoVO.setComcd(rsvnVO.getComcd());
        tobeRsvnInfoVO.setEdcRsvnReqid(rsvnVO.getEdcRsvnReqid());
        tobeRsvnInfoVO.setEdcRsvnNo(rsvnVO.getEdcRsvnNo());
        tobeRsvnInfoVO.setEdcRetyn(Config.YES);
        tobeRsvnInfoVO.setRetSelngId(selngInfoVO.getSelngId());
        tobeRsvnInfoVO.setEdcStat(Constant.SM_RSVN_STAT_신청취소);
        rsvnCnt += edcRsvnInfoService.cancelRsvnInfo(tobeRsvnInfoVO);

        // 7.EDC_RSVN_COMPTINFO 테이블 입력
        EdcRsvnComptinfoVO rsvnComptInfoVO = new EdcRsvnComptinfoVO();
        rsvnComptInfoVO.setComcd(rsvnVO.getComcd());
        rsvnComptInfoVO.setEdcRsvnReqid(rsvnVO.getEdcRsvnReqid());
        rsvnComptInfoVO.setEdcRetyn(Config.YES);
        rsvnComptInfoVO.setRetSelngId(selngInfoVO.getSelngId());
        comptinfoCnt = edcRsvnComptinfoService.cancelEdcRsvnComptinfo(rsvnComptInfoVO);

        log.debug(String.format("[CANCEL] 입력결과  selngCnt:%s, payListCnt:%s, cancelCnt:%s, rsvnCnt:%s, comptinfoCnt:%s", selngCnt, payListCnt, cancelCnt, rsvnCnt, comptinfoCnt));

        return selngCnt + payListCnt + cancelCnt + rsvnCnt + comptinfoCnt;
    }

    private int cancelDc100FreeItem(EdcRsvnInfoVO rsvnVO) throws Exception {
        int rsvnCnt = 0;
        int selngCnt = 0;
        int payListCnt = 0;
        int comptinfoCnt = 0;
        int cancelCnt = 0;
        int saleDiscountCnt = 0;

        // 1.영수증(RECEPIT_INFO)테이블 입력: 불필요
        /*
         * ReceiptInfoVO receiptInfoVO = makeReceiptInfoVO(itemList.get(0), payment);
         * receiptCnt = receiptInfoService.updateReceiptInfo(receiptInfoVO);
         */

        // payment.setRsvnNo(edcRsvnInfoService.selectNextRsvnNumber());

        // 2.통합판매(SELNG_INFO)테이블입력
        SelngInfoVO selngInfoVO = new SelngInfoVO();
        selngInfoVO.setComcd(rsvnVO.getComcd());
        // selngInfoVO.setSelngId(selngInfoService.selectNextSelngId()); //쿼리내조회
        selngInfoVO.setOrgSelngId(rsvnVO.getSelngId());
        selngInfoVO.setSlipType(Constant.SM_SLIP_TYPE_취소전표);
        selngInfoVO.setSlipState(Constant.SM_SLIP_STATE_취소);
        selngCnt += selngInfoService.cancelSelngInfo(selngInfoVO);

        // rsvnVO.selngNo => 매출원장의 고유번호
        // selngInfoVO.selngNo => 취소장의 고유번호 (mapper호출시 쿼리내 생성)

        // 3.결제내역(PAY_LIST)테이블입력
        PayListVO payListVO = new PayListVO();
        payListVO.setComcd(rsvnVO.getComcd());
        payListVO.setSelngId(selngInfoVO.getSelngId());
        payListVO.setOrgSelngId(rsvnVO.getSelngId());
        payListVO.setOrgPaySeq(rsvnVO.getPaySeq());
        payListVO.setSlipType(Constant.SM_SLIP_TYPE_취소전표);
        payListVO.setSlipState(Constant.SM_SLIP_STATE_취소);
        payListCnt += payListService.cancelPayList(payListVO);

        // 4.할인정보(SALEDISCOUNTS)테이블입력
        if (rsvnVO.getEdcDcamt() > 0) {
            SaleDiscountVO saleDiscountVO = new SaleDiscountVO();
            saleDiscountVO.setComcd(rsvnVO.getComcd());
            saleDiscountVO.setSaleSeq(selngInfoVO.getSelngId());
            saleDiscountVO.setOrgSaleSeq(rsvnVO.getSelngId());
            saleDiscountVO.setOrgSeq(1);
            saleDiscountVO.setDiscountamount(rsvnVO.getEdcDcamt());
            // saleDiscountVO.setDiscountdate(discountdate);
            // saleDiscountVO.setNwpayOrdid(nwpayOrdid);
            // saleDiscountVO.setEtc(etc);
            saleDiscountCnt += saleDiscountService.cancelSaleDiscount(saleDiscountVO);
        }

        // 5.CANCEL 테이블 입력
        CancelVO cancelVO = new CancelVO();
        cancelVO.setComcd(rsvnVO.getComcd());
        cancelVO.setSaleSeq(selngInfoVO.getSelngId());
        // cancelVO.setRetRegdate(retRegdate);
        cancelVO.setMemNo(rsvnVO.getEdcMemNo());
        // cancelVO.setCancelCd(null);
        // cancelVO.setCancelDate(cancelDate);
        cancelVO.setReturnAmt(rsvnVO.getEdcTotamt()); // 환불금액. 여기서는0. BO에서는 관리자 입력금액
        cancelVO.setVatAmt(0);
        cancelVO.setBreakAmt(0); // 위약금
        cancelVO.setUseAmt(0); // 이용금액. BO에서는 결제금액 - 환불금액
        // cancelVO.setRateAmt(rateAmt); //미정
        // cancelVO.setRemark(remark);
        cancelVO.setReturnSelngid(rsvnVO.getSelngId());
        cancelVO.setOldItemEdate(rsvnVO.getEdcReqEdate());
        cancelVO.setTotalBasedDaycnt(rsvnVO.getEdcClcnt()); // 총수업횟수
        cancelVO.setUsedDaysCount(0); // 수업참여일수. BO에서 환불시 쿼리를 통해 계산
        cancelCnt += cancelService.insertCancel(cancelVO);

        // 6.EDC_RSVN_INFO 테이블 입력
        EdcRsvnInfoVO tobeRsvnInfoVO = new EdcRsvnInfoVO();
        tobeRsvnInfoVO.setComcd(rsvnVO.getComcd());
        tobeRsvnInfoVO.setEdcRsvnReqid(rsvnVO.getEdcRsvnReqid());
        tobeRsvnInfoVO.setEdcRsvnNo(rsvnVO.getEdcRsvnNo());
        tobeRsvnInfoVO.setEdcRetyn(Config.YES);
        tobeRsvnInfoVO.setRetSelngId(selngInfoVO.getSelngId());
        tobeRsvnInfoVO.setEdcStat(Constant.SM_RSVN_STAT_신청취소);
        rsvnCnt += edcRsvnInfoService.cancelRsvnInfo(tobeRsvnInfoVO);

        // 7.EDC_RSVN_COMPTINFO 테이블 입력
        EdcRsvnComptinfoVO rsvnComptInfoVO = new EdcRsvnComptinfoVO();
        rsvnComptInfoVO.setComcd(rsvnVO.getComcd());
        rsvnComptInfoVO.setEdcRsvnReqid(rsvnVO.getEdcRsvnReqid());
        rsvnComptInfoVO.setEdcRetyn(Config.YES);
        rsvnComptInfoVO.setRetSelngId(selngInfoVO.getSelngId());
        comptinfoCnt = edcRsvnComptinfoService.cancelEdcRsvnComptinfo(rsvnComptInfoVO);

        log.debug(String.format("[CANCEL] 입력결과  selngCnt:%s, payListCnt:%s, saleDiscountCnt:%s, cancelCnt:%s, rsvnCnt:%s, comptinfoCnt:%s", selngCnt, payListCnt, saleDiscountCnt, cancelCnt, rsvnCnt, comptinfoCnt));

        return selngCnt + payListCnt + saleDiscountCnt + cancelCnt + rsvnCnt + comptinfoCnt;
    }

    private int cancelPayItem(EdcRsvnInfoVO rsvnVO) throws Exception {
        log.debug("[CANCEL] cancelPayItem rsvnVO => {}", JsonUtil.toPrettyJson(rsvnVO));

        int rsvnCnt = 0;
        int selngCnt = 0;
        int payListCnt = 0;
        int comptinfoCnt = 0;
        int cancelCnt = 0;
        int saleDiscountCnt = 0;

        // 1.영수증(RECEPIT_INFO)테이블 입력: 불필요
        /*
         * ReceiptInfoVO receiptInfoVO = makeReceiptInfoVO(itemList.get(0), payment);
         * receiptCnt = receiptInfoService.updateReceiptInfo(receiptInfoVO);
         */

        // payment.setRsvnNo(edcRsvnInfoService.selectNextRsvnNumber());

        // 2.통합판매(SELNG_INFO)테이블입력
        SelngInfoVO selngInfoVO = new SelngInfoVO();
        selngInfoVO.setComcd(rsvnVO.getComcd());
        // selngInfoVO.setSelngId(selngInfoService.selectNextSelngId()); //쿼리내조회
        selngInfoVO.setOrgSelngId(rsvnVO.getSelngId());
        selngInfoVO.setSlipType(Constant.SM_SLIP_TYPE_환불전표);
        selngInfoVO.setSlipState(Constant.SM_SLIP_STATE_환불);
        selngInfoVO.setDcAmt(rsvnVO.getCancelDcAmt()); // 환불DC금액
        selngInfoVO.setSalamt(rsvnVO.getCancelAmt()); // 환불금액
        if (selngInfoVO.getSalamt() == 0) {
            selngInfoVO.setDcAmt(rsvnVO.getEdcDcamt()); // 환불DC금액
            selngInfoVO.setSalamt(rsvnVO.getEdcTotamt()); // 환불금액
        }
        selngCnt += selngInfoService.cancelSelngInfo(selngInfoVO);
        log.debug(String.format("[CANCEL] %s[%s] => %s\n", "insertReceiptInfo", selngCnt, JsonUtil.toPrettyJson(selngInfoVO)));

        // rsvnVO.selngNo => 매출원장의 고유번호
        // selngInfoVO.selngNo => 취소장의 고유번호 (mapper호출시 쿼리내 생성)

        // 3.결제내역(PAY_LIST)테이블입력
        PayListVO payListVO = new PayListVO();
        payListVO.setComcd(rsvnVO.getComcd());
        payListVO.setSelngId(selngInfoVO.getSelngId());
        payListVO.setOrgSelngId(rsvnVO.getSelngId());
        payListVO.setOrgPaySeq(rsvnVO.getPaySeq());
        payListVO.setSlipType(Constant.SM_SLIP_TYPE_환불전표);
        payListVO.setSlipState(Constant.SM_SLIP_STATE_환불);
        payListVO.setPayAmt(rsvnVO.getCancelAmt());
        if (payListVO.getPayAmt() == 0) {
            payListVO.setPayAmt(rsvnVO.getEdcTotamt());
        }
        payListCnt += payListService.cancelPayList(payListVO);
        log.debug(String.format("[CANCEL] %s[%s] => %s\n", "cancelPayList", payListCnt, JsonUtil.toPrettyJson(payListVO)));

        // 4.할인정보(SALEDISCOUNTS)테이블입력
        if (rsvnVO.getEdcDcamt() > 0) {
            SaleDiscountVO saleDiscountVO = new SaleDiscountVO();
            saleDiscountVO.setComcd(rsvnVO.getComcd());
            saleDiscountVO.setSaleSeq(selngInfoVO.getSelngId());
            saleDiscountVO.setOrgSaleSeq(rsvnVO.getSelngId());
            saleDiscountVO.setOrgSeq(1);
            saleDiscountVO.setDiscountamount(rsvnVO.getCancelDcAmt());
            if (saleDiscountVO.getDiscountamount() == 0) {
                saleDiscountVO.setDiscountamount(rsvnVO.getEdcDcamt());
            }
            // saleDiscountVO.setDiscountdate(discountdate);
            // saleDiscountVO.setNwpayOrdid(nwpayOrdid);
            // saleDiscountVO.setEtc(etc);
            saleDiscountCnt += saleDiscountService.cancelSaleDiscount(saleDiscountVO);
            log.debug(String.format("[CANCEL] %s[%s] => %s\n", "cancelSaleDiscount", saleDiscountCnt, JsonUtil.toPrettyJson(saleDiscountVO)));
        }

        // 5.CANCEL 테이블 입력
        CancelVO cancelVO = new CancelVO();
        cancelVO.setComcd(rsvnVO.getComcd());
        cancelVO.setSaleSeq(selngInfoVO.getSelngId());
        // cancelVO.setRetRegdate(retRegdate);
        cancelVO.setMemNo(rsvnVO.getEdcMemNo());
        // cancelVO.setCancelCd(null);
        // cancelVO.setCancelDate(cancelDate);
        cancelVO.setReturnAmt(rsvnVO.getCancelAmt()); // 환불금액. cancel, return 동일 의미로 간주
        cancelVO.setVatAmt(0);
        cancelVO.setBreakAmt(0); // 위약금
        cancelVO.setUseAmt(rsvnVO.getEdcProgmCost() - rsvnVO.getCancelAmt()); // 이용금액. BO에서는 결제금액 - 환불금액
        if (cancelVO.getReturnAmt() == 0) {
            cancelVO.setReturnAmt(rsvnVO.getEdcTotamt()); // 환불금액. cancel, return 동일 의미로 간주
            cancelVO.setBreakAmt(0); // 위약금
            cancelVO.setUseAmt(0); // 이용금액. BO에서는 결제금액 - 환불금액
        }
        // cancelVO.setRateAmt(rateAmt); //미정
        // cancelVO.setRemark(remark);
        cancelVO.setReturnSelngid(rsvnVO.getSelngId());
        cancelVO.setOldItemEdate(rsvnVO.getEdcReqEdate());
        cancelVO.setTotalBasedDaycnt(rsvnVO.getEdcClcnt()); // 총수업횟수
        cancelVO.setUsedDaysCount(0); // 수업참여일수. BO에서 환불시 쿼리를 통해 계산
        cancelCnt += cancelService.insertCancel(cancelVO);
        log.debug(String.format("[CANCEL] %s[%s] => %s\n", "insertCancel", cancelCnt, JsonUtil.toPrettyJson(cancelVO)));

        // 6.EDC_RSVN_INFO 테이블 입력
        EdcRsvnInfoVO tobeRsvnInfoVO = new EdcRsvnInfoVO();
        tobeRsvnInfoVO.setComcd(rsvnVO.getComcd());
        tobeRsvnInfoVO.setEdcRsvnReqid(rsvnVO.getEdcRsvnReqid());
        tobeRsvnInfoVO.setEdcRsvnNo(rsvnVO.getEdcRsvnNo());
        tobeRsvnInfoVO.setEdcRetyn(Config.YES);
        tobeRsvnInfoVO.setRetSelngId(selngInfoVO.getSelngId());
        tobeRsvnInfoVO.setEdcStat(Constant.SM_RSVN_STAT_환불취소);
        rsvnCnt += edcRsvnInfoService.cancelRsvnInfo(tobeRsvnInfoVO);
        log.debug(String.format("[CANCEL] %s[%s] => %s\n", "cancelRsvnInfo", rsvnCnt, JsonUtil.toPrettyJson(tobeRsvnInfoVO)));

        // 7.EDC_RSVN_COMPTINFO 테이블 입력
        EdcRsvnComptinfoVO rsvnComptInfoVO = new EdcRsvnComptinfoVO();
        rsvnComptInfoVO.setComcd(rsvnVO.getComcd());
        rsvnComptInfoVO.setEdcRsvnReqid(rsvnVO.getEdcRsvnReqid());
        rsvnComptInfoVO.setEdcRetyn(Config.YES);
        rsvnComptInfoVO.setRetSelngId(selngInfoVO.getSelngId());
        comptinfoCnt += edcRsvnComptinfoService.cancelEdcRsvnComptinfo(rsvnComptInfoVO);
        log.debug(String.format("[CANCEL] %s[%s] => %s\n", "cancelEdcRsvnComptinfo", comptinfoCnt, JsonUtil.toPrettyJson(rsvnComptInfoVO)));

        log.debug(String.format("[CANCEL] 입력결과  selngCnt:%s, payListCnt:%s, saleDiscountCnt:%s, cancelCnt:%s, rsvnCnt:%s, comptinfoCnt:%s", selngCnt, payListCnt, saleDiscountCnt, cancelCnt, rsvnCnt, comptinfoCnt));

        return selngCnt + payListCnt + saleDiscountCnt + cancelCnt + rsvnCnt + comptinfoCnt;
    }

}
