package com.hisco.intrfc.sale.mapper;

import java.util.List;
import java.util.Map;

import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.intrfc.sale.vo.CardAppHistVO;
import com.hisco.intrfc.sale.vo.PaySummaryVO;
import com.hisco.intrfc.sale.vo.PgOrdDetVO;
import com.hisco.intrfc.sale.vo.PgOrdMstVO;
import com.hisco.intrfc.sale.vo.VbankOrderListVO;
import com.hisco.intrfc.sale.vo.VbankPaymentInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : SaleChargeMapper.java
 * @Description : 자세한 클래스 설명
 * @author woojinp@legitsys.co.kr
 * @since 2021. 12. 7.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Mapper("saleChargeMapper")
public interface SaleChargeMapper {

    public int insertPgOrdMst(PgOrdMstVO vo);

    public int insertPgOrdDet(PgOrdDetVO vo);

    public int cancelPgOrdDet(PgOrdDetVO vo);

    public int updatePgOrdMst(PgOrdMstVO vo);

    /**
     * PG 거래 승인내역을 저장한다
     *
     * @param Map
     * @throws Exception
     */
    public int insertCardAppHist(CardAppHistVO vo);

    public int cancelCardAppHist(CardAppHistVO vo);

    /**
     * 기승인내역건수조회
     */
    public int selectCardAppHistCnt(CardAppHistVO vo);

    /**
     * updateCardAppCancel
     */
    public int updateCardAppHistForCancel(CardAppHistVO vo);

    /**
     * 주문번호 조회 프로시져 호출
     *
     * @param OrderIdVO
     * @throws Exception
     */

    /**
     * 주문번호 조회
     */
    public int selectOrderIdNoPg();

    public String selectNextOid();

    /**
     * 부분취소 No 업데이트
     *
     * @param myRsvnVO
     * @throws Exception
     */
    public int updatePartialNo(PgOrdDetVO vo);

    /**
     * 환불 규정을 가져온다
     *
     * @param Map
     * @return String
     * @throws Exception
     */
    public List<CamelMap> selectRefundRule(CommandMap commandMap);

    /**
     * 카드 사별 수수료 구한다
     */
    public CamelMap selectPaymethodRate(Map<String, Object> paramMap);

    public int insertVbankPaymentInfo(VbankPaymentInfoVO vo);

    public int completeVbankPaymentInfo(VbankPaymentInfoVO vo);

    public int insertVbankOrderList(VbankOrderListVO vo);

    public int deleteVbankInfo(VbankOrderListVO vo);

    public int updateVbankPaymentInfo(VbankPaymentInfoVO vo);

    public PaySummaryVO selectPaySummary(PaySummaryVO vo);

    public List<PaySummaryVO> selectCancelHistory(PaySummaryVO vo);
}
