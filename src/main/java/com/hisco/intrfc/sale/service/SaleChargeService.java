package com.hisco.intrfc.sale.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.intrfc.sale.mapper.SaleChargeMapper;
import com.hisco.intrfc.sale.vo.CardAppHistVO;
import com.hisco.intrfc.sale.vo.PaySummaryVO;
import com.hisco.intrfc.sale.vo.PgOrdDetVO;
import com.hisco.intrfc.sale.vo.PgOrdMstVO;
import com.hisco.intrfc.sale.vo.VbankOrderListVO;
import com.hisco.intrfc.sale.vo.VbankPaymentInfoVO;

/**
 * @Class Name : SaleChargeService.java
 * @Description :
 * @author woojinp@legitsys.co.kr
 * @since 2021. 12. 7.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */

@Service("saleChargeService")
public class SaleChargeService {

    @Resource(name = "saleChargeMapper")
    private SaleChargeMapper saleChargeMapper;

    public int savePgInfo() {
        return 1;
    }

    public int insertPgOrdMst(PgOrdMstVO vo) throws Exception {
        return saleChargeMapper.insertPgOrdMst(vo);
    }

    public int insertPgOrdDet(PgOrdDetVO vo) throws Exception {
        return saleChargeMapper.insertPgOrdDet(vo);
    }

    public int cancelPgOrdDet(PgOrdDetVO vo) throws Exception {
        return saleChargeMapper.cancelPgOrdDet(vo);
    }

    public int updatePgOrdMst(PgOrdMstVO vo) throws Exception {
        return saleChargeMapper.updatePgOrdMst(vo);
    }

    /**
     * PG 거래 승인내역을 저장한다
     */
    public int insertCardAppHist(CardAppHistVO vo) throws Exception {
        return saleChargeMapper.insertCardAppHist(vo);
    }

    /**
     * PG 거래 취소내역을 저장한다
     */
    public int cancelCardAppHist(CardAppHistVO vo) throws Exception {
        return saleChargeMapper.cancelCardAppHist(vo);
    }

    public int newTxCancelCardAppHist(CardAppHistVO vo) throws Exception {
        return saleChargeMapper.cancelCardAppHist(vo);
    }

    /**
     * 기승인내역건수조회
     */
    public int selectCardAppHistCnt(CardAppHistVO vo) throws Exception {
        return saleChargeMapper.selectCardAppHistCnt(vo);
    }

    /**
     * updateCardAppCancel
     */
    public int updateCardAppHistForCancel(CardAppHistVO vo) throws Exception {
        return saleChargeMapper.updateCardAppHistForCancel(vo);
    }

    /**
     * 주문번호 조회 프로시져 호출
     *
     * @param OrderIdVO
     * @throws Exception
     */

    /**
     * 주문번호 조회
     */
    public int selectOrderIdNoPg() throws Exception {
        return saleChargeMapper.selectOrderIdNoPg();
    }

    public String selectNextOid() throws Exception {
        return saleChargeMapper.selectNextOid();

    }

    /**
     * 부분취소 No 업데이트
     *
     * @param myRsvnVO
     * @throws Exception
     */
    public int updatePartialNo(PgOrdDetVO vo) throws Exception {
        return saleChargeMapper.updatePartialNo(vo);
    }

    /**
     * 환불 규정을 가져온다
     *
     * @param Map
     * @return String
     * @throws Exception
     */
    public List<CamelMap> selectRefundRule(CommandMap commandMap) throws Exception {
        return saleChargeMapper.selectRefundRule(commandMap);
    }

    /**
     * 카드 사별 수수료 구한다
     */
    public CamelMap selectPaymethodRate(Map<String, Object> paramMap) throws Exception {
        return saleChargeMapper.selectPaymethodRate(paramMap);
    }

    public int insertVbankPaymentInfo(VbankPaymentInfoVO vo) throws Exception {
        return saleChargeMapper.insertVbankPaymentInfo(vo);
    }

    public int completeVbankPaymentInfo(VbankPaymentInfoVO vo) throws Exception {
        return saleChargeMapper.completeVbankPaymentInfo(vo);
    }

    public int insertVbankOrderList(VbankOrderListVO vo) throws Exception {
        return saleChargeMapper.insertVbankOrderList(vo);
    }

    public int deleteVbankInfo(VbankOrderListVO vo) throws Exception {
        return saleChargeMapper.deleteVbankInfo(vo);
    }

    public int updateVbankPaymentInfo(VbankPaymentInfoVO vo) throws Exception {
        return saleChargeMapper.updateVbankPaymentInfo(vo);
    }

    public PaySummaryVO selectPaySummary(PaySummaryVO vo) throws Exception {
        return saleChargeMapper.selectPaySummary(vo);
    }

    public List<PaySummaryVO> selectCancelHistory(PaySummaryVO vo) throws Exception {
        return saleChargeMapper.selectCancelHistory(vo);
    }
}
