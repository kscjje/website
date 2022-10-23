package com.hisco.intrfc.charge.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.intrfc.charge.mapper.ChargeMapper;
import com.hisco.intrfc.charge.vo.OrderIdVO;
import com.hisco.intrfc.email.mapper.EMailMapper;
import com.hisco.user.mypage.vo.MyRsvnVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;

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
@Service("chargeService")
public class ChargeService extends EgovAbstractServiceImpl {

    @Resource(name = "chargeMapper")
    private ChargeMapper chargeMapper;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "eMailMapper")
    private EMailMapper eMailMapper;

    public void insertSeokLogMyBatis(Map<String, Object> paramMap) throws Exception {
        eMailMapper.insertSeokLogMyBatis1(paramMap);
        eMailMapper.insertSeokLogMyBatis2(paramMap);
    }

    public void insertSeokLogMyBatis1(Map<String, Object> paramMap) throws Exception {
        eMailMapper.insertSeokLogMyBatis1(paramMap);
    }

    public void insertSeokLogMyBatis2(Map<String, Object> paramMap) throws Exception {
        eMailMapper.insertSeokLogMyBatis2(paramMap);
    }

    public List<?> selectSeokLogMyBatis(Map<String, Object> paramMap) throws Exception {
        return eMailMapper.selectSeokLogMyBatis(paramMap);
    }

    @SuppressWarnings("unchecked")
    public List<?> selectSeokLogProcedure(Map<String, Object> paramMap) throws Exception {

        paramMap.put("RETURN_CD", "");
        paramMap.put("RETURN_MSG", "");
        paramMap.put("RETURN_OBJ", "");
        List<EgovMap> listMap1 = (List<EgovMap>) eMailMapper.selectCallReferProc(paramMap);

        // log.debug("listMap1 = ");
        // log.debug(listMap1);

        // log.debug("paramMap = ");
        // log.debug(paramMap);

        return listMap1;
    }

    public List<?> selectPgOrdMst(Map<String, Object> paramMap) throws Exception {
        return chargeMapper.selectPgOrdMst(paramMap);
    }

    public List<?> selectPgOrdDet(Map<String, Object> paramMap) throws Exception {
        return chargeMapper.selectPgOrdDet(paramMap);
    }

    public int selectPgOrdMstRow(Map<String, Object> paramMap) throws Exception {
        return chargeMapper.selectPgOrdMstRow(paramMap);
    }

    public void insertPgOrdMst(Map<String, Object> paramMap) throws Exception {
        chargeMapper.insertPgOrdMst(paramMap);
    }

    public void insertPgOrdMstDet(Map<String, Object> paramMap) throws Exception {

        JsonArray jsonArrRESERITEMS = (JsonArray) paramMap.get("RESERITEMSArr");

        paramMap.put("OID_DET_ROWCNT", jsonArrRESERITEMS.size());

        // log.debug("call insertPgOrdMstDet");
        // log.debug(paramMap);

        chargeMapper.insertPgOrdMst(paramMap);

        String strRsvnNO = "";
        for (int i = 0; i < jsonArrRESERITEMS.size(); i++) {

            strRsvnNO = jsonArrRESERITEMS.get(i).getAsString();

            paramMap.put("OID_RSVN_NO", strRsvnNO);
            paramMap.put("OID_SEQ", (i + 1));

            chargeMapper.insertPgOrdDet(paramMap);
        }

    }

    public void updatePgOrdMst(Map<String, Object> paramMap) throws Exception {
        chargeMapper.updatePgOrdMst(paramMap);
    }

    /**
     * 예약결제대기 자동취소를 처리한다
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectChargeMainList(Map<String, Object> paramMap) throws Exception {
        return chargeMapper.selectChargeMainList(paramMap);
    }

    /**
     * PG 거래 승인내역을 저장한다
     *
     * @param Map
     * @throws Exception
     */
    public void insertCardAppHistory(Map<String, Object> paramMap) throws Exception {
        chargeMapper.insertCardAppHistory(paramMap);
    }

    /**
     * 주문번호 조회 프로시져 호출
     *
     * @param OrderIdVO
     * @throws Exception
     */
    public void selectOrderId(OrderIdVO paramMap) throws Exception {
        //// JYS 2021.05.18 chargeMapper.selectOrderIdProc( paramMap);
    }

    /**
     * 주문번호 조회 프로시져 호출
     *
     * @param OrderIdVO
     * @throws Exception
     */
    public int selectOrderIdNoPg(OrderIdVO paramMap) throws Exception {
        // chargeMapper.selectOrderIdNoPg(paramMap);
        return chargeMapper.selectNextOid();
    }

    /**
     * 주문번호 생성 프로시져 호출 로그
     *
     * @param OrderIdVO
     * @throws Exception
     */
    public void insertDbprocLog(OrderIdVO orderVO) throws Exception {
        HashMap<String, String> paramMap = new HashMap<String, String>();

        String logData = "P_COMCD=" + orderVO.getComcd() + "\n,P_USERID=" + orderVO.getUserId() + "\n,P_RSVN_NO=" + orderVO.getRsvnNo() + "\n,P_RSVN_ITEMCD=" + orderVO.getItemCd() + "\n,P_RSVN_CNT=" + orderVO.getRsvnCnt() + "\n,P_RSVN_AMT=" + orderVO.getRsvnAmt();

        paramMap.put("procName", "SP_CREATE_OID");
        paramMap.put("logData", logData);

        chargeMapper.insertDbprocLog(paramMap);
    }

    /**
     * 주문 호출 로그
     *
     * @param OrderIdVO
     * @throws Exception
     */
    public void insertDbprocLog(MyRsvnVO myRsvnVO) throws Exception {
        HashMap<String, String> paramMap = new HashMap<String, String>();

        String logData = "P_COMCD=" + myRsvnVO.getComcd() + "\n,P_USERID=" + myRsvnVO.getLgdBUYERID() + "\n,P_TERMINAL_TYPE=" + myRsvnVO.getTerminalType() + "\n,P_PART_GBN=" + myRsvnVO.getPartGbn() + "\n,ANLMEMB_SEQ=" + myRsvnVO.getMemYearSeq() + "\n,ANLMEMB_DCCD=" + myRsvnVO.getMemYearDccd() + "\n,ANLMEMB_DCRATE=" + myRsvnVO.getMemYearDcrate() + "\n,ANLMEMB_DCAMT=" + myRsvnVO.getMemYearDcamt() + "\n,ANLMEMB_SALAMT=" + myRsvnVO.getMemYearSaleamt() + "\n,P_PAY_TYPE=ALL" + "\n,P_OID=" + myRsvnVO.getLgdOID();

        paramMap.put("procName", "SP_CREATE_PAMENTINFO");
        paramMap.put("logData", logData);

        chargeMapper.insertDbprocLog(paramMap);
    }

    /**
     * 주문 취소 호출 로그
     *
     * @param OrderIdVO
     * @throws Exception
     */
    public void insertCancelDbprocLog(MyRsvnVO myRsvnVO) throws Exception {
        HashMap<String, String> paramMap = new HashMap<String, String>();

        String logData = "P_COMCD=" + myRsvnVO.getComcd() + "\n,P_USERID=" + myRsvnVO.getModuser() + "\n,P_CANCELTYPE=F" + "\n,P_RSVN_NO=" + myRsvnVO.getRsvnNo() + "\n,P_OID=" + myRsvnVO.getLgdOID() + "\n,P_PARTGBN=" + myRsvnVO.getPartGbn() + "\n,P_TERMINAL_TYPE=" + myRsvnVO.getTerminalType() + "\n,P_CANCEL_CD=" + myRsvnVO.getCancelCd() + "\n,P_APP_DATE=" + myRsvnVO.getCancelDate() + "\n,P_APP_NO=" + myRsvnVO.getCancelAppNo();

        paramMap.put("procName", "SP_PROC_RTN");
        paramMap.put("logData", logData);

        chargeMapper.insertDbprocLog(paramMap);
    }

    /**
     * 부분취소 No 업데이트
     *
     * @param myRsvnVO
     * @throws Exception
     */
    public void updatePartialNo(MyRsvnVO myRsvnVO) throws Exception {
        chargeMapper.updatePartialNo(myRsvnVO);
    }

    /**
     * 환불 규정을 가져온다
     *
     * @param Map
     * @return String
     * @throws Exception
     */
    public String selectRefundRule(CommandMap commandMap) throws Exception {
        List<CamelMap> list = chargeMapper.selectRefundRule(commandMap);
        StringBuffer bf = new StringBuffer();
        if (list != null) {
            int n = 0;
            for (CamelMap data : list) {
                n++;
                bf.append(data.get("rfndNofday") + "일 전까지 : " + data.get("rfndRate") + "% 환불");
                if (n == list.size()) {
                    String eTime = CommonUtil.getString(data.get("rfndEtime"));
                    if (eTime.length() == 4) {
                        eTime = eTime.substring(0, 2) + "시 " + eTime.substring(2, 4) + "분 ";
                    }
                    bf.append(" , " + eTime + "이후 : 취소불가");
                } else {
                    bf.append("\n");
                }
            }
        }
        return bf.toString();
    }

}
