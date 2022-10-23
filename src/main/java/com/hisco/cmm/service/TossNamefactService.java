package com.hisco.cmm.service;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.vo.NamefactVO;

import egovframework.com.cmm.service.EgovProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("tossNamefactService")
public class TossNamefactService {

    private static final String SESSION_NAME = "SESSION-NAMEFACT-RESULT";
    private static final String SESSION_TYPE_NAME = "SESSION-NAMEFACT-TYPE";
    private static final String DEFAULT_KEY_NAME = "normal";

    private Map<String, String> payReqMap;

    public Map<String, String> getPayReqMap() {
        return payReqMap;
    }

    public void setNiceCertParameter() {

        payReqMap = new HashMap<String, String>();

        StringBuffer sb = new StringBuffer();
        String hashData = "";

        try {

            byte[] bNoti = sb.toString().getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bNoti);

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                int c = digest[i] & 0xff;
                if (c <= 15) {
                    strBuf.append("0");
                }
                strBuf.append(Integer.toHexString(c));
            }
            hashData = strBuf.toString();

        } catch (Exception e) {
            log.error("오류");
        }

        payReqMap.put("LGD_HASHDATA", hashData);

        log.debug("payReqMap = " + payReqMap);

    }

    public void setCertParameter() {

        payReqMap = new HashMap<String, String>();

        String cstPLATFORM = EgovProperties.getProperty("tosspayments.mobilecheckPlatform");

        String cid = EgovProperties.getProperty("tosspayments.mobilecheckId");
        String mid = ("test".equals(cstPLATFORM.trim()) ? "t" : "") + cid;
        String mertKey = EgovProperties.getProperty("tosspayments.mobilecheckMertkey");
        String timeStamp = DateUtil.printDatetime(null, "yyyyMMddHHmmss");
        String returnURL = EgovProperties.getProperty("Globals.Domain") + "/web/common/prsoncerti/resultPop";
        String hashData = "";

        StringBuffer sb = new StringBuffer();
        sb.append(mid);
        sb.append("000000"); // 휴대폰 대체 인증시 000000 으로 넘길것
        sb.append(timeStamp); // 타임스탬프(YYYYMMDDhhmmss)
        sb.append(mertKey);

        try {
            byte[] bNoti = sb.toString().getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bNoti);

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                int c = digest[i] & 0xff;
                if (c <= 15) {
                    strBuf.append("0");
                }
                strBuf.append(Integer.toHexString(c));
            }
            hashData = strBuf.toString();

        } catch (Exception e) {
            log.error("오류");
        }

        payReqMap.put("CST_PLATFORM", cstPLATFORM); // 테스트, 서비스 구분
        payReqMap.put("CST_MID", cid); // 상점아이디
        payReqMap.put("LGD_MID", mid); // 상점아이디
        payReqMap.put("LGD_HASHDATA", hashData); // MD5 해쉬암호값
        payReqMap.put("LGD_BUYER", "구매자"); // 구매자
        payReqMap.put("LGD_BUYERSSN", "000000"); // 생년월일 6자리 (YYMMDD) 또는 사업자번호 10자리, 휴대폰 본인인증을 사용할 경우 생년월일은 '0' 6자리를
                                                 // 넘기세요. 예)000000
        payReqMap.put("LGD_NAMECHECKYN", "N"); // 계좌실명확인여부
        payReqMap.put("LGD_HOLDCHECKYN", "Y"); // 휴대폰본인확인 SMS발송 여부
        payReqMap.put("LGD_CUSTOM_SKIN", "red"); // 본인확인창 SKIN
        payReqMap.put("LGD_TIMESTAMP", timeStamp); // 타임스탬프
        payReqMap.put("LGD_CUSTOM_USABLEPAY", "ASC007"); // 디폴트 결제수단 (해당 필드를 보내지 않으면 결제수단 선택 UI 가 보이게 됩니다.)
        payReqMap.put("LGD_WINDOW_TYPE", "popup"); // 본인확인창 호출 방식 (수정불가)
        payReqMap.put("LGD_RETURNURL", returnURL); // 응답수신페이지
        payReqMap.put("LGD_VERSION", "JSP_NON-ActiveX_AuthOnly"); // 사용타입 정보(수정 및 삭제 금지): 이 정보를 근거로 어떤 서비스를 사용하는지 판단할 수
                                                                  // 있습니다.
        payReqMap.put("LGD_OSTYPE_CHECK", "P");
        payReqMap.put("LGD_DOMAIN_URL", "xpay");
        payReqMap.put("LGD_MOBILE_AUTH_FIRST", "0");
        payReqMap.put("LGD_MOBILE_SUBAUTH_SITECD", "123456789abc");

        /* Return URL에서 인증 결과 수신 시 셋팅될 파라미터 입니다. */
        payReqMap.put("LGD_RESPCODE", "");
        payReqMap.put("LGD_RESPMSG", "");
        payReqMap.put("LGD_AUTHONLYKEY", "");
        payReqMap.put("LGD_PAYTYPE", "");

    }

    public NamefactVO getCertResult(String lgdMID, String lgdAUTHONLYKEY, String lgdPAYTYPE) {

        String cSTPLATFORM = EgovProperties.getProperty("tosspayments.mobilecheckPlatform");
        String configPath = FileMngUtil.GetRealRootPath().concat("WEB-INF/lgdacom"); // 토스페이먼츠에서 제공한
                                                                                     // 환경파일("/conf/lgdacom.conf,/conf/mall.conf")
                                                                                     // 위치 지정.

        // 해당 API를 사용하기 위해 WEB-INF/lib/XPayClient.jar 를 Classpath 로 등록하셔야 합니다.
        // (1) XpayClient의 사용을 위한 xpay 객체 생성

        /*
         * XPayClient xpay = new XPayClient();
         * // (2) Init: XPayClient 초기화(환경설정 파일 로드)
         * // configPath: 설정파일
         * // CST_PLATFORM: - test, service 값에 따라 lgdacom.conf의 test_url(test) 또는 url(srvice) 사용
         * // - test, service 값에 따라 테스트용 또는 서비스용 아이디 생성
         * boolean isInitOK = xpay.Init(configPath, cSTPLATFORM);
         * String lgdRESPCODE = "0000";
         * String lgdRESPMSG = "";
         * NamefactVO namefactData = new NamefactVO();
         * if ( !isInitOK ) {
         * //API 초기화 실패 화면처리
         * lgdRESPCODE = "ERROR";
         * lgdRESPMSG = "결제요청을 초기화 하는데 실패하였습니다.";
         * }
         * if (lgdRESPCODE.equals("0000")) {
         * try {
         * // (3) Init_TX: 메모리에 mall.conf, lgdacom.conf 할당 및 트랜잭션의 고유한 키 TXID 생성
         * xpay.Init_TX(lgdMID);
         * xpay.Set("LGD_TXNAME", "AuthOnlyByKey");
         * xpay.Set("LGD_AUTHONLYKEY", lgdAUTHONLYKEY);
         * xpay.Set("LGD_PAYTYPE", lgdPAYTYPE);
         * } catch (Exception e) {
         * // LG U+ API 사용 불가, 설정파일 확인 등 필요(예외처리)
         * lgdRESPCODE = "ERROR";
         * lgdRESPMSG = "토스페이먼츠 제공 API를 사용할 수 없습니다. 환경파일 설정을 확인해 주시기 바랍니다.";
         * }
         * }
         * // (4) TX: lgdacom.conf에 설정된 URL로 소켓 통신하여 최종 인증요청, 결과값으로 true, false 리턴
         * if (lgdMID == null || lgdMID.equals("")) {
         * lgdRESPCODE = "ERROR";
         * lgdRESPMSG = "본인인증 정보가 없습니다. 다시 본인인증을 해주세요. ";
         * } else if (lgdRESPCODE.equals("0000")) {
         * if ( xpay.TX() ) {
         * //1)인증결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
         * String birthDt = xpay.Response("LGD_MOBILE_SUBAUTH_BIRTH",0);
         * if (birthDt.startsWith("0") || birthDt.startsWith("1")||birthDt.startsWith("2")) {
         * birthDt = "20" + birthDt;
         * } else {
         * birthDt = "19" + birthDt;
         * }
         * Date birthday = DateUtil.string2date(birthDt);
         * for (int i = 0; i < xpay.ResponseNameCount(); i++)
         * {
         * log.info(xpay.ResponseName(i) + " = ");
         * for (int j = 0; j < xpay.ResponseCount(); j++)
         * {
         * log.info("\t" + xpay.Response(xpay.ResponseName(i), j) + "<br>");
         * if (xpay.ResponseName(i).equals("LGD_RESPCODE")) {
         * lgdRESPCODE = xpay.Response(xpay.ResponseName(i), j);
         * } else if (xpay.ResponseName(i).equals("LGD_RESPMSG")) {
         * lgdRESPMSG = xpay.Response(xpay.ResponseName(i), j);
         * }
         * }
         * }
         * if (lgdRESPCODE.equals("0000")) {
         * namefactData.setCrc_type("3001"); // 휴대폰인증
         * if (cSTPLATFORM.equals("test")) { // 테스트 버전일경우 핸드폰 번호로 본인인증 키로 함
         * namefactData.setCrc_data_ci(xpay.Response("LGD_MOBILENUM",0));
         * namefactData.setCrc_data_di(xpay.Response("LGD_MOBILENUM",0));
         * } else {
         * namefactData.setCrc_data_ci(xpay.Response("LGD_AUTHSUB_CI",0).trim());
         * namefactData.setCrc_data_di(xpay.Response("LGD_AUTHSUB_DI",0).trim());
         * }
         * namefactData.setName(xpay.Response("LGD_MOBILE_SUBAUTH_NAME",0));
         * namefactData.setSex("1".equals(xpay.Response("LGD_MOBILE_SUBAUTH_SEX",0)) ? "M" : "F");
         * namefactData.setAge(DateUtil.calcAge(birthday));
         * namefactData.setBirthday(birthday);
         * namefactData.setForeigner("N");
         * namefactData.setBirthYmd(birthDt);
         * namefactData.setTel(xpay.Response("LGD_MOBILENUM",0));
         * }
         * } else {
         * //2)API 요청실패 화면처리
         * lgdRESPCODE = xpay.m_szResCode;
         * lgdRESPMSG = xpay.m_szResMsg;
         * }
         * }
         * namefactData.setRespCode(lgdRESPCODE);
         * namefactData.setRespMsg(lgdRESPMSG);
         * return namefactData;
         */

        return null; // 2021.06.06 JYS
    }

    public void setType(HttpServletRequest request, String type) {
        if (CommonUtil.isEmpty(type))
            request.getSession().setAttribute(SESSION_TYPE_NAME, DEFAULT_KEY_NAME);
        else
            request.getSession().setAttribute(SESSION_TYPE_NAME, type);
    }

    public String getType(HttpServletRequest request) {
        String mode = (String) request.getSession().getAttribute(SESSION_TYPE_NAME);
        if (CommonUtil.isEmpty(mode))
            return DEFAULT_KEY_NAME;
        else
            return mode;
    }

    /**
     * 본인인증 정보
     * 
     * @param request
     *            리퀘스트
     * @param type
     *            구분명
     * @return
     */
    @SuppressWarnings("unchecked")
    public NamefactVO getData(HttpServletRequest request, String type) {
        Map<String, NamefactVO> datas = (Map<String, NamefactVO>) request.getSession().getAttribute(SESSION_NAME);

        //// log.info(datas.toString());

        if (type == null || type.equals("")) {
            return null;
        } else if (datas != null && datas.size() > 0 && datas.containsKey(type)) {
            NamefactVO data = datas.get(type);
            // if (data != null && !CommonUtil.isEmpty(data.getType()) && (!CommonUtil.isEmpty(data.getCrc_data_ci()) ||
            // !CommonUtil.isEmpty(data.getCrc_data_di())) && !CommonUtil.isEmpty(data.getName()))
            // {
            return data;
            // }
        }

        return null;
    }

    /**
     * 본인인증 정보(기본)
     * 
     * @param request
     *            리퀘스트
     * @return
     */
    public NamefactVO getData(HttpServletRequest request) {
        return getData(request, DEFAULT_KEY_NAME);
    }

    /**
     * 본인인증 정보 확인
     * 
     * @param request
     *            리퀘스트
     * @param type
     *            구분명
     * @return
     */
    public boolean existsData(HttpServletRequest request, String type) {
        return getData(request, type) != null;
    }

    /**
     * 본인인증 정보 확인(기본)
     * 
     * @param request
     *            리퀘스트
     * @return
     */
    public boolean existsData(HttpServletRequest request) {
        return existsData(request, DEFAULT_KEY_NAME);
    }

    /**
     * 본인인증 정보 저장
     * 
     * @param request
     *            리퀘스트
     * @param type
     *            구분명
     * @param data
     *            본인인증 정보
     */
    @SuppressWarnings("unchecked")
    public void saveData(HttpServletRequest request, String type, NamefactVO data) {
        Map<String, NamefactVO> datas = (Map<String, NamefactVO>) request.getSession().getAttribute(SESSION_NAME);
        if (datas == null)
            datas = new HashMap<String, NamefactVO>();

        datas.put(type, data);

        request.getSession().setAttribute(SESSION_NAME, datas);
    }

    /**
     * 본인인증 정보 저장(기본)
     * 
     * @param request
     *            리퀘스트
     * @param data
     *            본인인증 정보
     */
    public void saveData(HttpServletRequest request, NamefactVO data) {
        saveData(request, DEFAULT_KEY_NAME, data);
    }

    /**
     * 본인인증 정보 전체 제거
     * 
     * @param request
     */
    public void clear(HttpServletRequest request) {
        request.getSession().removeAttribute(SESSION_NAME);
    }

    /**
     * 본인인증 정보 제거
     * 
     * @param request
     *            리퀘스트
     * @param type
     *            구분명
     */
    @SuppressWarnings("unchecked")
    public void clear(HttpServletRequest request, String type) {
        Map<String, NamefactVO> datas = (Map<String, NamefactVO>) request.getSession().getAttribute(SESSION_NAME);
        if (datas == null)
            datas = new HashMap<String, NamefactVO>();

        datas.remove(type);

        request.getSession().setAttribute(SESSION_NAME, datas);
    }
}
