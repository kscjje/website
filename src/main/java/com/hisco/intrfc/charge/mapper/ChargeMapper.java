package com.hisco.intrfc.charge.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.intrfc.charge.vo.OrderIdVO;
import com.hisco.user.mypage.vo.MyRsvnVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * Sms 송신 처리
 * 
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.20
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.20 최초작성
 */
@Mapper("chargeMapper")
public interface ChargeMapper {

    public List<?> selectPgOrdMst(Map<String, Object> paramMap);

    public List<?> selectPgOrdDet(Map<String, Object> paramMap);

    public int selectPgOrdMstRow(Map<String, Object> paramMap);

    public void insertPgOrdMst(Map<String, Object> paramMap);

    public void insertPgOrdDet(Map<String, Object> paramMap);

    public void updatePgOrdMst(Map<String, Object> paramMap);

    /**
     * 예약결제대기 자동취소를 처리한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectChargeMainList(Map<String, Object> paramMap);

    /**
     * PG 거래 승인내역을 저장한다
     *
     * @param Map
     * @throws Exception
     */
    public int insertCardAppHistory(Map<String, Object> paramMap);

    /**
     * 기승인내역건수조회
     */
    public int selectCardAppHistoryCnt(Map<String, Object> paramMap);

    /**
     * PG 거래 취소내역을 저장한다.updateCardAppPartial
     *
     * @param Map
     * @throws Exception
     */
    public int insertCardAppHistoryForCancel(MyRsvnVO vo);

    /**
     * updateCardAppCancel
     */
    public int updateCardAppHistoryForCancel(MyRsvnVO vo);

    /**
     * 주문번호 조회 프로시져 호출
     *
     * @param OrderIdVO
     * @throws Exception
     */
    // public void selectOrderId(OrderIdVO paramMap);

    /**
     * 주문번호 조회 프로시져 호출
     *
     * @param OrderIdVO
     * @throws Exception
     */
    public void selectOrderIdNoPg(OrderIdVO paramMap);

    /**
     * 주문번호 생성 프로시져 호출 로그
     *
     * @param OrderIdVO
     * @throws Exception
     */
    public void insertDbprocLog(OrderIdVO orderVO);

    public void insertDbprocLog(HashMap<String, String> paramMap);

    public int selectNextOid();

    /**
     * 주문 호출 로그
     *
     * @param OrderIdVO
     * @throws Exception
     */
    public void insertDbprocLog(MyRsvnVO myRsvnVO);

    /**
     * 부분취소 No 업데이트
     *
     * @param myRsvnVO
     * @throws Exception
     */
    public void updatePartialNo(MyRsvnVO myRsvnVO);

    /**
     * 환불 규정을 가져온다
     *
     * @param Map
     * @return String
     * @throws Exception
     */
    public List<CamelMap> selectRefundRule(CommandMap commandMap);

    // TODO:2021.10.31 취소프로시져의 정체는?
    public int insertReturnProc(MyRsvnVO myRsvnVO);

    /**
     * 카드 사별 수수료 구한다
     */
    public CamelMap selectPaymethodRate(Map<String, Object> paramMap);
}
