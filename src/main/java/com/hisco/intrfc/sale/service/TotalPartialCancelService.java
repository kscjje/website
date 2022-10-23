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
import com.hisco.intrfc.sale.vo.PayListVO;
import com.hisco.intrfc.sale.vo.PgOrdDetVO;
import com.hisco.intrfc.sale.vo.SaleDiscountVO;
import com.hisco.intrfc.sale.vo.SaleFormItemVO;
import com.hisco.intrfc.sale.vo.SaleFormPaymentVO;
import com.hisco.intrfc.sale.vo.SaleFormVO;
import com.hisco.intrfc.sale.vo.SelngInfoVO;
import com.hisco.intrfc.sale.vo.TossCancelVO;
import com.hisco.intrfc.sale.vo.TossResponseVO;
import com.hisco.intrfc.sms.service.BizMsgService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : TotalPartialCancelService.java
 * @Description : 강좌부분취소처리
 * @author woojinp@legitsys.co.kr
 * @since 2022. 1. 5.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */

@Slf4j
@Service("totalPartialCancelService")
public class TotalPartialCancelService extends EgovAbstractServiceImpl {

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

    @Resource(name = "bizMsgService")
    private BizMsgService bizMsgService;

    /**
     * 부분취소
     */
    public int cancel(List<EdcRsvnInfoVO> rsvnList, SaleFormVO cancelFormVO) throws Exception {
        List<SaleFormItemVO> cancelItemList = cancelFormVO.getItemList();
        SaleFormPaymentVO cancelPayment = cancelFormVO.getPayment();

        int totCnt = 0;
        int payAmt = 0;

        /* 강좌취소 */
        for (EdcRsvnInfoVO rsvnVO : rsvnList) {
            for (SaleFormItemVO cancelItem : cancelItemList) {
                if (rsvnVO.getEdcRsvnReqid() != cancelItem.getEdcRsvnReqid())
                    continue;

                payAmt += rsvnVO.getEdcTotamt();

                if (rsvnVO.getEdcDcamt() + rsvnVO.getEdcTotamt() > 0) { // 유료상품
                    totCnt += this.cancelPayItem(rsvnVO, cancelItem, cancelPayment);
                }

                bizMsgService.sendRsvnMessage(rsvnVO.getEdcRsvnReqid());
            }

        }

        /* PG결제건 존재시 취소 */
        int pCancelCnt = rsvnList.get(0).getPCancelCnt();
        for (EdcRsvnInfoVO rsvnVO : rsvnList) {
            for (SaleFormItemVO cancelItem : cancelItemList) {
                if (rsvnVO.getEdcRsvnReqid() != cancelItem.getEdcRsvnReqid())
                    continue;

                if (!Constant.PG_TOSS.equals(cancelItem.getCancelPayComcd()))// toss결제건이 아닌경우 아래 로직 불필요
                    continue;

                TossCancelVO tcVO = new TossCancelVO();
                tcVO.setLGD_MID(rsvnVO.getMid());
                tcVO.setLGD_TID(rsvnVO.getTid());
                tcVO.setLGD_TXNAME(Constant.PG_TOSS_TXNAME_PARTIAL_CANCEL);
                tcVO.setLGD_CANCELAMOUNT(String.valueOf(cancelItem.getCancelAmt()));
                tcVO.setLGD_PCANCELCNT(this.pcancelNo2pcancel3Str(++pCancelCnt));

                if (Constant.SITE_P_TYPE_가상계좌.equals(cancelItem.getCancelPayMethod())) {
                    tcVO.setLGD_RFBANKCODE(cancelPayment.getBankCd());
                    tcVO.setLGD_RFACCOUNTNUM(cancelPayment.getAccountNum());
                    tcVO.setLGD_RFCUSTOMERNAME(cancelPayment.getDpstrNm());
                    tcVO.setLGD_RFPHONE(rsvnVO.getEdcRsvnMoblphon());
                }

                TossResponseVO tossResponseVO = tossService.cancelPartial(tcVO);
                log.debug("[PARTIAL_CANCEL] tossCancelResponse => {}", JsonUtil.toPrettyJson(tossResponseVO));

                PgOrdDetVO pgOrdDetVO = new PgOrdDetVO();
                pgOrdDetVO.setComcd(rsvnVO.getComcd());
                pgOrdDetVO.setOidPcancelNo(tcVO.getLGD_PCANCELCNT());
                pgOrdDetVO.setOid(rsvnVO.getOid());
                int pgOrdDetCnt = saleChargeService.cancelPgOrdDet(pgOrdDetVO);
                totCnt += pgOrdDetCnt;
                log.debug(String.format("[PARTIAL_CANCEL] %s[%s] => %s\n", "cancelPgOrdDet", pgOrdDetCnt, JsonUtil.toPrettyJson(pgOrdDetVO)));

                if (Constant.SITE_P_TYPE_가상계좌.equals(cancelItem.getCancelPayMethod())) { // 가상계좌
                } else { // 카드, 실시간계좌이체
                    if (TossService.TOSS_CANCEL_OK_LIST.contains(tossResponseVO.getLGD_RESPCODE())) { // PG결제취소성공
                        CardAppHistVO histVO = new CardAppHistVO();
                        histVO.setComcd(rsvnVO.getComcd());
                        histVO.setAppDate(StringUtils.isNotBlank(tossResponseVO.getLGD_RESPDATE())
                                ? tossResponseVO.getLGD_RESPDATE() : tossResponseVO.getLGD_TIMESTAMP());
                        histVO.setAppNo(rsvnVO.getAppNo());
                        histVO.setTid(rsvnVO.getTid());
                        histVO.setOid(rsvnVO.getOid());
                        histVO.setAppGbn(Constant.SM_APP_GBN_카드취소);
                        histVO.setCnlDate(tossResponseVO.getLGD_TIMESTAMP().substring(0, 8));
                        histVO.setCnlTime(tossResponseVO.getLGD_TIMESTAMP().substring(8));
                        histVO.setPartialCnlyn(Config.YES);
                        histVO.setPcancelNo(tcVO.getLGD_PCANCELCNT());
                        histVO.setAppAmt(cancelItem.getCancelAmt());
                        histVO.setResultmsg(tossResponseVO.getLGD_RESPCODE() + "|" + tossResponseVO.getLGD_RESPMSG());

                        int cardAppHistCnt = saleChargeService.cancelCardAppHist(histVO);
                        totCnt += cardAppHistCnt;

                        log.debug(String.format("[PARTIAL_CANCEL] %s[%s] => %s\n", "cancelCardAppHist", cardAppHistCnt, JsonUtil.toPrettyJson(histVO)));
                    } else {
                        log.error("[PARTIAL_CANCEL] 결제취소 실패 => {}", JsonUtil.toPrettyJson(tossResponseVO));
                        throw new RuntimeException(tossResponseVO.getLGD_RESPMSG());
                    }
                }
            }
        }

        return totCnt;
    }

    private String pcancelNo2pcancel3Str(int cnt) {
        if (cnt < 10) {
            return "00" + cnt;
        }
        return "0" + cnt;
    }

    private int cancelPayItem(EdcRsvnInfoVO rsvnVO, SaleFormItemVO cancelItem, SaleFormPaymentVO cancelPayment)
            throws Exception {
        log.debug("[PARTIAL_CANCEL] cancelPayItem cancelItem => {}", JsonUtil.toPrettyJson(cancelItem));
        log.debug("[PARTIAL_CANCEL] cancelPayItem cancelPayment => {}", JsonUtil.toPrettyJson(cancelPayment));

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
        selngInfoVO.setSlipState(Constant.SM_SLIP_STATE_정상);
        selngInfoVO.setDcAmt(cancelItem.getCancelDcAmt()); // 환불DC금액
        selngInfoVO.setSalamt(cancelItem.getCancelAmt()); // 환불금액
        selngInfoVO.setSelngDate(cancelItem.getCancelDate());
        selngInfoVO.setTerminalType(cancelPayment.getTerminalType());
        if (Constant.SITE_P_TYPE_현금.equals(cancelItem.getCancelPayMethod()) || Constant.SITE_P_TYPE_가상계좌.equals(cancelItem.getCancelPayMethod())) {
            selngInfoVO.setDpstrNm(cancelPayment.getDpstrNm());
            selngInfoVO.setBankCd(cancelPayment.getBankCd());
            selngInfoVO.setBankNm(cancelPayment.getBankNm());
            selngInfoVO.setAcountNum(cancelPayment.getAccountNum());
        }
        selngCnt += selngInfoService.cancelSelngInfo(selngInfoVO);
        log.debug(String.format("[PARTIAL_CANCEL] %s[%s] => %s\n", "insertReceiptInfo", selngCnt, JsonUtil.toPrettyJson(selngInfoVO)));

        // rsvnVO.selngNo => 매출원장의 고유번호
        // selngInfoVO.selngNo => 취소장의 고유번호 (mapper호출시 쿼리내 생성)

        // 3.결제내역(PAY_LIST)테이블입력
        PayListVO payListVO = new PayListVO();
        payListVO.setComcd(rsvnVO.getComcd());
        payListVO.setSelngId(selngInfoVO.getSelngId());
        payListVO.setOrgSelngId(rsvnVO.getSelngId());
        payListVO.setOrgPaySeq(rsvnVO.getPaySeq());
        payListVO.setSlipType(Constant.SM_SLIP_TYPE_환불전표);
        payListVO.setSlipState(Constant.SM_SLIP_STATE_정상);
        payListVO.setPayAmt(cancelItem.getCancelAmt());
        payListVO.setTerminalType(cancelPayment.getTerminalType());
        payListVO.setPayDate(cancelItem.getCancelDate());
        payListVO.setPComcd(cancelItem.getCancelPayComcd());
        payListVO.setPType(cancelItem.getCancelPayMethod());
        payListVO.setMethodCd(cancelItem.getCancelFinanceCd());
        payListCnt += payListService.cancelPayList(payListVO);
        log.debug(String.format("[PARTIAL_CANCEL] %s[%s] => %s\n", "cancelPayList", payListCnt, JsonUtil.toPrettyJson(payListVO)));

        // 4.할인정보(SALEDISCOUNTS)테이블입력
        if (rsvnVO.getEdcDcamt() > 0) {
            SaleDiscountVO saleDiscountVO = new SaleDiscountVO();
            saleDiscountVO.setComcd(rsvnVO.getComcd());
            saleDiscountVO.setSaleSeq(selngInfoVO.getSelngId());
            saleDiscountVO.setOrgSaleSeq(rsvnVO.getSelngId());
            saleDiscountVO.setOrgSeq(1);
            saleDiscountVO.setDiscountamount(cancelItem.getCancelDcAmt());
            saleDiscountVO.setDiscountdate(cancelItem.getCancelDate());

            // saleDiscountVO.setDiscountdate(discountdate);
            // saleDiscountVO.setNwpayOrdid(nwpayOrdid);
            // saleDiscountVO.setEtc(etc);
            saleDiscountCnt += saleDiscountService.cancelSaleDiscount(saleDiscountVO);
            log.debug(String.format("[PARTIAL_CANCEL] %s[%s] => %s\n", "cancelSaleDiscount", saleDiscountCnt, JsonUtil.toPrettyJson(saleDiscountVO)));
        }

        // 5.CANCEL 테이블 입력
        CancelVO cancelVO = new CancelVO();
        cancelVO.setComcd(rsvnVO.getComcd());
        cancelVO.setSaleSeq(selngInfoVO.getSelngId());
        // cancelVO.setRetRegdate(retRegdate);
        cancelVO.setMemNo(rsvnVO.getEdcMemNo());
        cancelVO.setCancelCd(cancelPayment.getCancelReasonCd());
        cancelVO.setRemark(cancelPayment.getCancelReasonDesc());
        cancelVO.setCancelDate(cancelItem.getCancelDate());
        cancelVO.setReturnAmt(cancelItem.getCancelAmt()); // 환불금액. cancel, return 동일 의미로 간주
        cancelVO.setVatAmt(0);
        cancelVO.setBreakAmt(0); // 위약금
        cancelVO.setUseAmt(rsvnVO.getRemainAmt() - cancelItem.getCancelAmt()); // 이용금액. BO에서는 결제금액 - 환불금액

        // cancelVO.setRateAmt(rateAmt); //미정
        // cancelVO.setRemark(remark);
        cancelVO.setReturnSelngid(selngInfoVO.getSelngId());
        cancelVO.setOldItemEdate(rsvnVO.getEdcReqEdate());
        cancelVO.setTotalBasedDaycnt(rsvnVO.getEdcClcnt()); // 총수업횟수
        cancelVO.setUsedDaysCount(cancelItem.getUseDateCnt()); // 수업참여일수. BO에서 환불시 쿼리를 통해 계산
        cancelCnt += cancelService.insertCancel(cancelVO);
        log.debug(String.format("[PARTIAL_CANCEL] %s[%s] => %s\n", "insertCancel", cancelCnt, JsonUtil.toPrettyJson(cancelVO)));

        // 6.EDC_RSVN_INFO 테이블 입력
        EdcRsvnInfoVO tobeRsvnInfoVO = new EdcRsvnInfoVO();
        tobeRsvnInfoVO.setComcd(rsvnVO.getComcd());
        tobeRsvnInfoVO.setEdcRsvnReqid(rsvnVO.getEdcRsvnReqid());
        tobeRsvnInfoVO.setEdcRsvnNo(rsvnVO.getEdcRsvnNo());
        tobeRsvnInfoVO.setEdcRetyn(Config.YES);
        tobeRsvnInfoVO.setRetSelngId(selngInfoVO.getSelngId());
        tobeRsvnInfoVO.setEdcStat(Constant.SM_RSVN_STAT_환불취소);
        rsvnCnt += edcRsvnInfoService.cancelRsvnInfo(tobeRsvnInfoVO);
        log.debug(String.format("[PARTIAL_CANCEL] %s[%s] => %s\n", "cancelRsvnInfo", rsvnCnt, JsonUtil.toPrettyJson(tobeRsvnInfoVO)));

        // 7.EDC_RSVN_COMPTINFO 테이블 입력
        EdcRsvnComptinfoVO rsvnComptInfoVO = new EdcRsvnComptinfoVO();
        rsvnComptInfoVO.setComcd(rsvnVO.getComcd());
        rsvnComptInfoVO.setEdcRsvnReqid(rsvnVO.getEdcRsvnReqid());
        rsvnComptInfoVO.setEdcRetyn(Config.YES);
        rsvnComptInfoVO.setRetSelngId(selngInfoVO.getSelngId());
        comptinfoCnt += edcRsvnComptinfoService.cancelEdcRsvnComptinfo(rsvnComptInfoVO);
        log.debug(String.format("[PARTIAL_CANCEL] %s[%s] => %s\n", "cancelEdcRsvnComptinfo", comptinfoCnt, JsonUtil.toPrettyJson(rsvnComptInfoVO)));

        log.debug(String.format("[PARTIAL_CANCEL] 입력결과  selngCnt:%s, payListCnt:%s, saleDiscountCnt:%s, cancelCnt:%s, rsvnCnt:%s, comptinfoCnt:%s", selngCnt, payListCnt, saleDiscountCnt, cancelCnt, rsvnCnt, comptinfoCnt));

        return selngCnt + payListCnt + saleDiscountCnt + cancelCnt + rsvnCnt + comptinfoCnt;
    }

}
