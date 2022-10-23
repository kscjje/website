package com.hisco.cmm.web;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hisco.cmm.modules.pg.nice.NiceNamefactDto;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.TossNamefactService;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.user.nice.service.NiceNamefactService;

import lombok.extern.slf4j.Slf4j;

/**
 * 본인인증 컨트롤러 클래스
 * 
 * @author 전영석
 * @since 2021.05.06
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일      	수정자           수정내용
 *  ------------   --------    ---------------------------
 *   2021.05.06  전영석          최초 생성
 *      </pre>
 */
@Slf4j
@Controller
@CrossOrigin
@RequestMapping(value = "/web/common/prsoncerti")
public class PrsonCertiController {

    @Resource(name = "niceNamefactService")
    private NiceNamefactService niceNamefactService;

    @Resource(name = "tossNamefactService")
    private TossNamefactService tossNamefactService;

    /**
     * 본인인증
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/prsonCertiPop")
    public String selectPrsonCertiPop(HttpServletRequest request, CommandMap commandMap, ModelMap model,
            @RequestParam Map<String, Object> paramMap) throws Exception {

        tossNamefactService.setCertParameter(); // 본인인증 초기화

        model.addAttribute("commandMap", commandMap);
        model.addAttribute("payReqMap", tossNamefactService.getPayReqMap());// 본인인증 파라미터

        log.debug("model = " + model);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 본인인증
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/prsonNiceCertiPop")
    public String selectPrsonNiceCertiPop(HttpServletRequest request, CommandMap commandMap, ModelMap model,
            @RequestParam Map<String, Object> paramMap) throws Exception {

        //// niceNamefactService.setCertParameter();

        model.addAttribute("commandMap", commandMap);
        // model.addAttribute("payReqMap", niceNamefactService.getPayReqMap());

        log.debug("model = " + model);

        return HttpUtility.getViewUrl(request);
    }

    // 성공 처리 : checkplus mobile : NICE평가정보 : 휴대전화 본인인증
    @SuppressWarnings("unused")
    @RequestMapping(value = "/successPop")
    public String CHECKPLUS_MOBILE__Success(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        log.debug("call CHECKPLUS_MOBILE__Success()");
        log.debug("model = " + model);

        request.setCharacterEncoding("UTF-8");

        String sEncodeData = CHECKPLUS__requestReplace(request.getParameter("EncodeData"), "encodeData");
        String sReserved1 = CHECKPLUS__requestReplace(request.getParameter("param_r1"), "");
        String sReserved2 = CHECKPLUS__requestReplace(request.getParameter("param_r2"), "");
        String sReserved3 = CHECKPLUS__requestReplace(request.getParameter("param_r3"), "");

        NiceNamefactDto niceNamefactDto = new NiceNamefactDto();
        niceNamefactService.saveData(request, "pwchange_" + sReserved2, niceNamefactDto);

        log.debug("#### CHECKPLUS MOBILE --------------------------------------------------------- ####");
        log.debug("## sEncodeData : " + sEncodeData);
        log.debug("## sReserved1 : " + sReserved1);
        log.debug("## sReserved2 : " + sReserved2);
        log.debug("## sReserved3 : " + sReserved3);

        if (CommonUtil.isEmpty(sEncodeData)) {
            HttpUtility.sendClose(request, response, "수신 정보값이 없습니다.\n다시 본인인증을 시도해주시기 바랍니다.\n현재창은 닫힙니다.");
            return null;
        }

        // NICE로부터 부여받은 사이트 코드
        String sSiteCode = "BO655";
        // NICE로부터 부여받은 사이트 패스워드
        String sSitePassword = "oP0WUfZQDFGY";

        NiceID.Check.CPClient niceCheck = new NiceID.Check.CPClient();

        String sCipherTime = ""; // 복호화한 시간
        String sRequestNumber = ""; // 요청 번호
        String sResponseNumber = ""; // 인증 고유번호
        String sAuthType = ""; // 인증 수단
        String sName = ""; // 성명
        String sDupInfo = ""; // 중복가입 확인값 (DI_64 byte)
        String sConnInfo = ""; // 연계정보 확인값 (CI_88 byte)
        String sBirthDate = ""; // 생일
        String sGender = ""; // 성별
        String sNationalInfo = ""; // 내/외국인정보 (개발가이드 참조)
        String sMessage = "";
        String sPlainData = "";
        String sMobileNumber = ""; // 휴대전화번호

        int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

        if (iReturn == 0) {

            sPlainData = niceCheck.getPlainData();
            sCipherTime = niceCheck.getCipherDateTime();

            // 데이타를 추출합니다.
            @SuppressWarnings("rawtypes")
            java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);

            log.debug("mapresult = " + mapresult);

            sRequestNumber = (String) mapresult.get("REQ_SEQ");
            sResponseNumber = (String) mapresult.get("RES_SEQ");
            sAuthType = (String) mapresult.get("AUTH_TYPE");
            sName = (String) mapresult.get("NAME");
            sBirthDate = (String) mapresult.get("BIRTHDATE");
            sGender = (String) mapresult.get("GENDER");
            sNationalInfo = (String) mapresult.get("NATIONALINFO");
            sDupInfo = (String) mapresult.get("DI");
            sConnInfo = (String) mapresult.get("CI");

            sMobileNumber = (String) mapresult.get("MOBILE_NO");

            String strSessionSRequestNumber = (String) request.getSession().getAttribute("REQ_SEQ");

            log.debug("strSessionSRequestNumber = " + strSessionSRequestNumber);
            log.debug("sRequestNumber           = " + sRequestNumber);

            if (!sRequestNumber.equals(strSessionSRequestNumber)) {
                sMessage = "요청번호 값이 다릅니다. 올바른 경로로 접근하시기 바랍니다.";
                sResponseNumber = "";
                sAuthType = "";
            }

        } else if (iReturn == -1) {
            sMessage = "복호화 시스템 에러입니다.";
        } else if (iReturn == -4) {
            sMessage = "복호화 처리오류입니다.";
        } else if (iReturn == -5) {
            sMessage = "복호화 해쉬 오류입니다.";
        } else if (iReturn == -6) {
            sMessage = "복호화 데이터 오류입니다.";
        } else if (iReturn == -9) {
            sMessage = "입력 데이터 오류입니다.";
        } else if (iReturn == -12) {
            sMessage = "사이트 패스워드 오류입니다.";
        } else {
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
        }

        // 실패
        if (!CommonUtil.isEmpty(sMessage)) {
            HttpUtility.sendClose(request, response, sMessage + "\n다시 본인인증을 시도해주시기 바랍니다.\n현재 창은 닫힙니다.");
            return null;
        }
        // 성공
        else {
            String type = sReserved1;

            Date birthday = DateUtil.string2date(sBirthDate);

            NiceNamefactDto niceNamefactDto2 = new NiceNamefactDto();

            niceNamefactDto2.setCrc_type("3001"); // 휴대폰인증
            niceNamefactDto2.setCrc_data_ci(sConnInfo);
            niceNamefactDto2.setCrc_data_di(sDupInfo != null ? sDupInfo.trim() : "");
            niceNamefactDto2.setType(type);
            niceNamefactDto2.setName(sName);
            niceNamefactDto2.setSex("1".equals(sGender) ? "M" : "F");
            niceNamefactDto2.setAge(DateUtil.calcAge(birthday));
            niceNamefactDto2.setBirthday(birthday);
            niceNamefactDto2.setForeigner("1".equals(sNationalInfo) ? "Y" : "N");
            niceNamefactDto2.setTel(sMobileNumber);

            // 14세 미만 회원가입
            if (type.equals("child")) {
                String[] strVal = sReserved2.split("[|]");

                sName = strVal[0];
                sBirthDate = strVal[1];
                niceNamefactDto2.setName(sName);
                niceNamefactDto2.setBirthday(DateUtil.string2date(sBirthDate));
                niceNamefactDto2.setSex(strVal[2]);
            }

            // 본인인증 정보 추가하기 세션에 저장
            niceNamefactService.saveData(request, type, niceNamefactDto2);

            model.addAttribute("namefactData", niceNamefactDto2);
            model.addAttribute("namefactType", type);
            model.addAttribute("userSession", request.getSession().getAttribute(Config.USER_SESSION));
            // 종료 페이지로 이동
            return HttpUtility.getViewUrl(request);
        }
    }

    // 실패 처리 : checkplus mobile : NICE평가정보 : 휴대전화 본인인증
    @RequestMapping(value = "/failPop")
    public void CHECKPLUS_MOBILE__Fail(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpUtility.sendClose(request, response, "본인인증이 실패하였습니다.\n다시 본인인증을 시도해주시기 바랍니다.\n현재 창은 닫힙니다.");
    }

    // 토스 본인인증 리턴 페이지
    @RequestMapping(value = "/resultPop")
    public String tossAuthResult(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        /*
         ************************************************* 1.최종인증 요청 - BEGIN
         */
        String lgdRESPCODE = request.getParameter("LGD_RESPCODE");
        String lgdRESPMSG = request.getParameter("LGD_RESPMSG");

        if (lgdRESPCODE == null) {
            lgdRESPCODE = "ERROR";
            lgdRESPMSG = "null 오류";
        }

        String lgdPLATFORM = request.getParameter("CST_PLATFORM");
        if (lgdPLATFORM == null)
            lgdPLATFORM = "service";

        String lgdMID = request.getParameter("LGD_MID");
        String lgdAUTHONLYKEY = request.getParameter("LGD_AUTHONLYKEY"); // 토스페이먼츠으로부터 부여받은 인증키
        String lgdPAYTYPE = request.getParameter("LGD_PAYTYPE"); // 인증요청타입 (신용카드:ASC001, 휴대폰 대체인증:ASC007, 계좌:ASC004)

        model.addAttribute("LGD_RESPCODE", lgdRESPCODE);
        model.addAttribute("LGD_RESPMSG", lgdRESPMSG);
        model.addAttribute("LGD_MID", lgdMID);
        model.addAttribute("LGD_AUTHONLYKEY", lgdAUTHONLYKEY);
        model.addAttribute("LGD_PAYTYPE", lgdPAYTYPE);

        return HttpUtility.getViewUrl(request);
    }

    /*
     * @RequestMapping(value = "/resultAjax")
     * public String tossAuthResult(
     * Model model
     * , HttpServletRequest request
     * , HttpServletResponse response
     * ) throws Exception
     * {
     * //request.setCharacterEncoding("UTF-8");
     * String configPath = FileMngUtil.GetRealRootPath().concat("WEB-INF/lgdacom"); //토스페이먼츠에서 제공한
     * 환경파일("/conf/lgdacom.conf,/conf/mall.conf") 위치 지정.
     * String LGD_RESPCODE = request.getParameter("LGD_RESPCODE");
     * String LGD_RESPMSG = request.getParameter("LGD_RESPMSG");
     * namefactService.saveData(request, "test" , new NamefactVO());
     * if (LGD_RESPCODE == null) {
     * LGD_RESPCODE = "ERROR";
     * LGD_RESPMSG = "null 오류";
     * }
     * String CST_PLATFORM = request.getParameter("CST_PLATFORM");
     * if (CST_PLATFORM == null) CST_PLATFORM = "service";
     * String CST_MID = request.getParameter("CST_MID");
     * String LGD_MID = ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID;
     * String LGD_AUTHONLYKEY = request.getParameter("LGD_AUTHONLYKEY"); //토스페이먼츠으로부터 부여받은 인증키
     * String LGD_PAYTYPE = request.getParameter("LGD_PAYTYPE"); //인증요청타입 (신용카드:ASC001, 휴대폰 대체인증:ASC007, 계좌:ASC004)
     * //해당 API를 사용하기 위해 WEB-INF/lib/XPayClient.jar 를 Classpath 로 등록하셔야 합니다.
     * // (1) XpayClient의 사용을 위한 xpay 객체 생성
     * XPayClient xpay = new XPayClient();
     * // (2) Init: XPayClient 초기화(환경설정 파일 로드)
     * // configPath: 설정파일
     * // CST_PLATFORM: - test, service 값에 따라 lgdacom.conf의 test_url(test) 또는 url(srvice) 사용
     * // - test, service 값에 따라 테스트용 또는 서비스용 아이디 생성
     * boolean isInitOK = xpay.Init(configPath, CST_PLATFORM);
     * if ( !isInitOK ) {
     * //API 초기화 실패 화면처리
     * LGD_RESPCODE = "ERROR";
     * LGD_RESPMSG = "결제요청을 초기화 하는데 실패하였습니다.";
     * }
     * if (LGD_RESPCODE.equals("0000")) {
     * try {
     * // (3) Init_TX: 메모리에 mall.conf, lgdacom.conf 할당 및 트랜잭션의 고유한 키 TXID 생성
     * xpay.Init_TX(LGD_MID);
     * xpay.Set("LGD_TXNAME", "AuthOnlyByKey");
     * xpay.Set("LGD_AUTHONLYKEY", LGD_AUTHONLYKEY);
     * xpay.Set("LGD_PAYTYPE", LGD_PAYTYPE);
     * } catch (Exception e) {
     * // LG U+ API 사용 불가, 설정파일 확인 등 필요(예외처리)
     * LGD_RESPCODE = "ERROR";
     * LGD_RESPMSG = "토스페이먼츠 제공 API를 사용할 수 없습니다. 환경파일 설정을 확인해 주시기 바랍니다.";
     * }
     * }
     * // (4) TX: lgdacom.conf에 설정된 URL로 소켓 통신하여 최종 인증요청, 결과값으로 true, false 리턴
     * if (LGD_RESPCODE.equals("0000")) {
     * if ( xpay.TX() ) {
     * //1)인증결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
     * NamefactVO namefactData = new NamefactVO();
     * Date birthday = DateUtil.string2date(xpay.Response("LGD_MOBILE_SUBAUTH_BIRTH",0));
     * namefactData.setCrc_type("3001"); // 휴대폰인증
     * namefactData.setCrc_data_ci(xpay.Response("LGD_AUTHSUB_CI",0));
     * namefactData.setCrc_data_di(xpay.Response("LGD_AUTHSUB_DI",0));
     * namefactData.setName(xpay.Response("LGD_MOBILE_SUBAUTH_NAME",0));
     * namefactData.setSex("1".equals(xpay.Response("LGD_MOBILE_SUBAUTH_SEX",0)) ? "M" : "F");
     * namefactData.setAge(DateUtil.calcAge(birthday));
     * namefactData.setBirthday(birthday);
     * namefactData.setForeigner("N");
     * namefactData.setTel(xpay.Response("LGD_MOBILENUM",0));
     * // 본인인증 정보 추가하기 세션에 저장
     * namefactService.saveData(request, LGD_TIMESTAMP , namefactData);
     * } else {
     * //2)API 요청실패 화면처리
     * LGD_RESPCODE = xpay.m_szResCode;
     * LGD_RESPMSG = xpay.m_szResMsg;
     * }
     * }
     * model.addAttribute("LGD_RESPCODE", LGD_RESPCODE);
     * model.addAttribute("LGD_RESPMSG", LGD_RESPMSG);
     * return HttpUtility.getViewUrl(request);
     * }
     */
    // checkplus 파라미터 치환 함수
    private String CHECKPLUS__requestReplace(String originParam, String gubun) {
        String result = "";

        String paramValue = originParam;

        if (paramValue != null) {
            paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

            paramValue = paramValue.replaceAll("\\*", "");
            paramValue = paramValue.replaceAll("\\?", "");
            paramValue = paramValue.replaceAll("\\[", "");
            paramValue = paramValue.replaceAll("\\{", "");
            paramValue = paramValue.replaceAll("\\(", "");
            paramValue = paramValue.replaceAll("\\)", "");
            paramValue = paramValue.replaceAll("\\^", "");
            paramValue = paramValue.replaceAll("\\$", "");
            paramValue = paramValue.replaceAll("'", "");
            paramValue = paramValue.replaceAll("@", "");
            paramValue = paramValue.replaceAll("%", "");
            paramValue = paramValue.replaceAll(";", "");
            paramValue = paramValue.replaceAll(":", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll("#", "");
            paramValue = paramValue.replaceAll("--", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll(",", "");

            if (gubun != "encodeData") {
                paramValue = paramValue.replaceAll("\\+", "");
                paramValue = paramValue.replaceAll("/", "");
                paramValue = paramValue.replaceAll("=", "");
            }

            result = paramValue;
        }
        return result;
    }
}
