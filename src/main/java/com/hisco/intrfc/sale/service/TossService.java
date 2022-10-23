package com.hisco.intrfc.sale.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.ExceptionUtil;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.intrfc.sale.vo.TossCancelVO;
import com.hisco.intrfc.sale.vo.TossResponseVO;

import lgdacom.XPayClient.XPayClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("tossService")
public class TossService {

    @Value("${tosspayments.platform}")
    private String CST_PLATFORM;

    @Value("${tosspayments.conf.path}")
    private String configPath;

    /**
     * <pre>
       [신용카드]
       0000: 결제 취소 성공
       AV11: 매입전취소(승인취소) 요청시 실패, LG U+에서 자동으로 취소 처리
              자동으로 취소처리를 원치 않을 경우 LG U+에 별도로 설정 변경 요청을 해야 합니다.
       
       [계좌이체]
       0000, RF00: 환불완료 
       RF10, RF09, RF15, RF19, RF23, RF25: 환불진행중 응답건, LG U+에서 자동환불 처리
              계좌이체 환불진행중 응답건의 경우, LG U+에서 자동환불 처리합니다. 환불진행중 응답건의 경우도 반드시 환불성공 처리를 해야 합니다. (환불결과코드.xls 파일 참조)
              
       [가상계좌]
       0000: 취소완료
       RF24: 요청하신 건에 대해 입금된 내역이 없습니다.
       
       S020: 이미 취소가 되었거나, 취소할 수 없는 거래입니다.
     * </pre>
     */
    public final static List<String> TOSS_CANCEL_OK_LIST = Arrays.asList("0000", "AV11", "RF00", "RF10", "RF09", "RF15", "RF19", "RF23", "RF25", "RF24", "S020");

    /**
     * TOSS 승인 건 취소(전체취소)
     */
    public TossResponseVO cancel(String LGD_MID, String LGD_TID, String retDpstrNm, String retBankCd,
            String retAcountNum, String hp) throws Exception {

        Map<String, String> resultMap = new HashMap<String, String>();
        XPayClient xpay = new XPayClient();
        TossResponseVO tossVO = new TossResponseVO();

        try {
            xpay.Init(configPath, CST_PLATFORM);
            xpay.Init_TX(LGD_MID);
            xpay.Set("LGD_TXNAME", "Cancel");
            xpay.Set("LGD_TID", LGD_TID);

            if (StringUtils.isNotBlank(retDpstrNm) && StringUtils.isNotBlank(retBankCd) && StringUtils.isNotBlank(retAcountNum)) {
                log.debug("retDpstrNm:retBankCd = {}:{}", retDpstrNm, retBankCd);
                xpay.Set("LGD_RFBANKCODE", retBankCd);
                xpay.Set("LGD_RFACCOUNTNUM", retAcountNum);
                xpay.Set("LGD_RFCUSTOMERNAME", retDpstrNm);
                if (StringUtils.isNotBlank(hp))
                    xpay.Set("LGD_RFPHONE", hp);
            }

            /*
             * 1. 결제취소 요청 결과처리
             * 취소결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
             * [[[중요]]] 고객사에서 정상취소 처리해야할 응답코드
             * 1. 신용카드 : 0000, AV11
             * 2. 계좌이체 : 0000, RF00, RF10, RF09, RF15, RF19, RF23, RF25 (환불진행중 응답건-> 환불결과코드.xls 참고)
             * 3. 나머지 결제수단의 경우 0000(성공) 만 취소성공 처리
             */
            if (xpay.TX()) {
                // 1)결제취소결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
                resultMap.put("TX_CANCEL_MSG", "결제 취소요청이 완료되었습니다.");
                resultMap.put("TX_CANCEL_RESPONSE_CODE", xpay.m_szResCode);
                resultMap.put("TX_CANCEL_RESPONSE_MSG", xpay.m_szResMsg);
            } else {
                // 2)API 요청 실패 화면처리
                resultMap.put("TX_CANCEL_MSG", "결제 취소요청이 실패하였습니다.");
                resultMap.put("TX_CANCEL_RESPONSE_CODE", xpay.m_szResCode);
                resultMap.put("TX_CANCEL_RESPONSE_MSG", xpay.m_szResMsg);
            }

            tossVO.setLGD_RESPCODE(xpay.m_szResCode);
            tossVO.setLGD_RESPMSG(xpay.m_szResMsg);
            tossVO.setLGD_HASHDATA(xpay.Response("LGD_HASHDATA", 0));
            tossVO.setLGD_TIMESTAMP(xpay.Response("LGD_TIMESTAMP", 0));
            tossVO.setLGD_RESPDATE(xpay.Response("LGD_RESPDATE", 0));

            if (log.isInfoEnabled()) {
                for (int i = 0; i < xpay.ResponseNameCount(); i++) {
                    String key = xpay.ResponseName(i);
                    String value = "";
                    for (int j = 0; j < xpay.ResponseCount(); j++) {
                        value += xpay.Response(xpay.ResponseName(i), j);
                    }
                    resultMap.put(key, value);
                }
            }
        } catch (Exception ex) {
            log.error(ExceptionUtil.getErrorLine(ex));
            throw ex;
        } finally {
            log.info("TOSS CANCEL RESPONSE = {}", JsonUtil.toPrettyJson(resultMap));
            log.info("TOSS CANCEL RESULT = {}", JsonUtil.toPrettyJson(tossVO));
        }

        return tossVO;
    }

    /**
     * 부분취소
     */
    public TossResponseVO cancelPartial(TossCancelVO tcVO) throws Exception {

        Map<String, String> resultMap = new HashMap<String, String>();
        XPayClient xpay = new XPayClient();
        TossResponseVO tossVO = new TossResponseVO();

        try {

            log.debug("TossCancelVO = {}", JsonUtil.toPrettyJson(tcVO));

            if (!partCancelable(tcVO)) {
                throw new RuntimeException("부분취소를 할 수 없습니다.");
            }

            xpay.Init(configPath, CST_PLATFORM); // CST_PLATFORM: test, service값에 따라 lgdacom.conf의 test_url 혹은 url 사용
            xpay.Init_TX(tcVO.getLGD_MID());
            xpay.Set("LGD_TXNAME", tcVO.getLGD_TXNAME());
            xpay.Set("LGD_TID", tcVO.getLGD_TID());

            xpay.Set("LGD_CANCELAMOUNT", tcVO.getLGD_CANCELAMOUNT());
            xpay.Set("LGD_PCANCELCNT", tcVO.getLGD_PCANCELCNT());

            xpay.Set("LGD_REMAINAMOUNT", StringUtils.defaultString(tcVO.getLGD_REMAINAMOUNT())); // 신용카드만

            // xpay.Set("LGD_CANCELTAXFREEAMOUNT", LGD_CANCELTAXFREEAMOUNT);
            xpay.Set("LGD_RFBANKCODE", StringUtils.defaultString(tcVO.getLGD_RFBANKCODE())); // 가상계좌만
            xpay.Set("LGD_RFACCOUNTNUM", StringUtils.defaultString(tcVO.getLGD_RFACCOUNTNUM())); // 가상계좌만
            xpay.Set("LGD_RFCUSTOMERNAME", StringUtils.defaultString(tcVO.getLGD_RFCUSTOMERNAME())); // 가상계좌만
            xpay.Set("LGD_RFPHONE", StringUtils.defaultString(tcVO.getLGD_RFPHONE())); // 가상계좌만

            /*
             * 1. 결제취소 요청 결과처리
             * 취소결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
             * [[[중요]]] 고객사에서 정상취소 처리해야할 응답코드
             * 1. 신용카드 : 0000, AV11
             * 2. 계좌이체 : 0000, RF00, RF10, RF09, RF15, RF19, RF23, RF25 (환불진행중 응답건-> 환불결과코드.xls 참고)
             * 3. 나머지 결제수단의 경우 0000(성공) 만 취소성공 처리
             */
            if (xpay.TX()) {
                // 1)결제취소결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
                resultMap.put("TX_CANCEL_MSG", (Constant.PG_TOSS_TXNAME_PARTIAL_CANCEL.equals(tcVO.getLGD_TXNAME())
                        ? "부분" : "") + "결제 취소요청 성공");
            } else {
                // 2)API 요청 실패 화면처리
                resultMap.put("TX_CANCEL_MSG", (Constant.PG_TOSS_TXNAME_PARTIAL_CANCEL.equals(tcVO.getLGD_TXNAME())
                        ? "부분" : "") + "결제 취소요청 실패");
            }

            tossVO.setLGD_RESPCODE(xpay.m_szResCode);
            tossVO.setLGD_RESPMSG(xpay.m_szResMsg);
            tossVO.setLGD_HASHDATA(xpay.Response("LGD_HASHDATA", 0));
            tossVO.setLGD_TIMESTAMP(xpay.Response("LGD_TIMESTAMP", 0));
            tossVO.setLGD_RESPDATE(xpay.Response("LGD_RESPDATE", 0));

            for (int i = 0; i < xpay.ResponseNameCount(); i++) {
                String key = xpay.ResponseName(i);
                String value = "";
                for (int j = 0; j < xpay.ResponseCount(); j++) {
                    value += xpay.Response(xpay.ResponseName(i), j);
                }
                resultMap.put(key, value);
            }
        } catch (Exception ex) {
            log.error(ExceptionUtil.getErrorLine(ex));
            throw ex;
        } finally {
            log.info("TOSS CANCEL RESPONSE = {}", JsonUtil.toPrettyJson(resultMap));
        }

        return tossVO;
    }

    // 부분취소 가능여부 확인
    private boolean partCancelable(TossCancelVO tcVO) throws Exception {

        Map<String, String> resultMap = new HashMap<String, String>();
        XPayClient xpay = new XPayClient();
        String LGD_RESPCODE = null;

        try {
            xpay.Init(configPath, CST_PLATFORM); // CST_PLATFORM: test, service값에 따라 lgdacom.conf의 test_url 혹은 url 사용
            xpay.Init_TX(tcVO.getLGD_MID());

            xpay.Set("LGD_TXNAME", Constant.PG_TOSS_TXNAME_GET_PART_CANCELABLE);
            xpay.Set("LGD_TID", tcVO.getLGD_TID());

            /**
             * 부분취소 가능여부 결과처리
             */
            if (xpay.TX()) {
                // 1)결제취소결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
                resultMap.put("(custom)TX_PARTCANCELABLE_MSG", "부분취소 가능여부 조회요청 성공");
            } else {
                // 2)API 요청 실패 화면처리
                resultMap.put("(custom)TX_PARTCANCELABLE_MSG", "부분취소 가능여부 조회요청 실패");
            }

            LGD_RESPCODE = xpay.m_szResCode;

            for (int i = 0; i < xpay.ResponseNameCount(); i++) {
                String key = xpay.ResponseName(i);
                String value = "";
                for (int j = 0; j < xpay.ResponseCount(); j++) {
                    value += xpay.Response(xpay.ResponseName(i), j);
                }
                resultMap.put(key, value);
            }
        } catch (Exception ex) {
            log.error(ExceptionUtil.getErrorLine(ex));
            throw ex;
        } finally {
            log.info("TOSS PARTCANCELABLE RESPONSE = {}", JsonUtil.toPrettyJson(resultMap));
            // log.info("TOSS PARTCANCELABLE RESULT = {}", JsonUtil.toPrettyJson(tossVO));
        }

        if ("0000,S020".contains(LGD_RESPCODE)) { // S020:이미 취소되었거나, 취소할 수 없는 거래
            return true;
        }

        return false;
    }
}
