package com.hisco.user.nice.web;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hisco.cmm.modules.DateUtil;
import com.hisco.cmm.modules.HiscoWebTool;
import com.hisco.cmm.modules.ModuleInfoDto;
import com.hisco.cmm.modules.RequestUtil;
import com.hisco.cmm.modules.ResponseUtil;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.modules.pg.nice.NiceNamefactDto;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.user.nice.service.NiceNamefactService;

import egovframework.com.cmm.LoginVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/web/namefact")
public class NamefactController {

    private final String redirect_namefact_end = "redirect:/web/namefact/End";

    // 본인인증 최종 url 저장 세션명
    private final String end_url_session = "namefact_end_url";

    // 본인인증 최종 메시지 출력 여부 저장 세션명
    private final String message_yn_session = "namefact_message_yn";

    // 본인인증 디자인 파라미터명
    public static final String namefact_design_param = "design";

    // 본인인증 구분 파라미터명
    public static final String namefact_type_param = "type";

    // 본인인증 최종 URL 파라미터명
    public static final String namefact_end_url_param = "end";

    // 본인인증 최종 메시지 출력 여부 파라미터명
    public static final String namefact_message_param = "message";

    /**
     * 본인인증 종류 : 일반
     */
    public static final String TYPE_NORMAL = "normal";
    /**
     * 본인인증 종류 : 어린이
     */
    public static final String TYPE_CHILD = "child";
    /**
     * 본인인증 종류 : 외국인
     */
    public static final String TYPE_FOREIGNER = "foreigner";
    /**
     * 본인인증 종류 : 기타
     */
    public static final String TYPE_ETC = "etc";
    /**
     * 본인인증 종류 : API (타입 뒤에 추가 정보 삽입)
     */
    public static final String TYPE_API = "api-";

    @Resource(name = "niceNamefactService")
    private NiceNamefactService niceNamefactService;

    // 본인인증 폼 처리
    @RequestMapping(value = "/{jobGbn}")
    public String Form(@PathVariable String jobGbn, Model model, HttpServletRequest request,
            HttpServletResponse response, @RequestParam Map<String, Object> paramMap) throws Exception {

        String strCertiType = String.valueOf(paramMap.get("jobGbn"));

        String strChildName = String.valueOf(paramMap.get("childName"));
        String strChildBirthDay = String.valueOf(paramMap.get("childBirthYear")) + String.valueOf(paramMap.get("childBirthMonth")) + String.valueOf(paramMap.get("childBirthDay"));

        if ("memRegiChild".equals(strCertiType)) {

            niceNamefactService.setType(request, "child");

            request.getSession().setAttribute("childName", strChildName);
            request.getSession().setAttribute("childBirthDay", strChildBirthDay);

        } else if ("memChangePhone".equals(strCertiType)) {

            request.getSession().removeAttribute("changePhoneNum");

        }

        request.getSession().setAttribute("jobGbn", jobGbn);

        RequestUtil req = RequestUtil.getInstance(request);

        HiscoWebTool.LoadModuleInfo(request);

        model.addAttribute("namefactModuleInfo", HiscoWebTool.LoadModuleInfo("namefact"));

        log.debug("----------------------------------------------------.S");
        log.debug(HiscoWebTool.LoadModuleInfo("namefact").toString());
        log.debug("----------------------------------------------------.E");

        // 폼 디자인
        String design = req.getParam(namefact_design_param);
        if (StringUtil.IsEmpty(design))
            design = (String) request.getAttribute(namefact_design_param);

        if (design == null) {
            design = "";
        }

        log.debug("design = " + design);

        // 최종 전달 URL
        String end_url = req.getParam(namefact_end_url_param);
        if (StringUtil.IsEmpty(end_url))
            end_url = (String) request.getAttribute(namefact_end_url_param);

        log.debug("end_url = " + end_url);

        if (end_url == null) {
            end_url = "/web/namefact/End";
        }

        if (StringUtil.IsEmpty(end_url)) {
            ResponseUtil.SendMessage(request, response, "본인인증 최종 URL 정보가 없습니다.", "history.back();");
            return null;
        }

        log.debug("end_url = " + end_url);

        // 메시지 출력 여부
        String message_yn = req.getParam(namefact_message_param);
        if (StringUtil.IsEmpty(message_yn))
            message_yn = "Y";

        request.getSession().setAttribute("namefact_message_yn", message_yn);

        if (message_yn == null) {
            message_yn = "Y";
        }

        log.debug("message_yn = " + message_yn);

        // 본인인증 구분값
        String type = req.getParam(namefact_type_param);
        if (StringUtil.IsEmpty(type))
            type = (String) request.getAttribute(namefact_type_param);

        if (type == null) {
            type = "normal";
        }

        log.debug("type = " + type);

        niceNamefactService.setType(request, type);

        // 본인인증 정보
        NiceNamefactDto niceNamefactData = null;
        if (StringUtil.IsEmpty(type)) {
            niceNamefactData = niceNamefactService.getData(request);
        } else {
            niceNamefactData = niceNamefactService.getData(request, type);
        }

        //// niceNamefactData.setUserId(userId);

        if (niceNamefactData == null) {
            log.debug("---------------------------------------------------------------------------------.S");
            log.debug("niceNamefactData is null");
            log.debug("---------------------------------------------------------------------------------.E");
        } else {
            log.debug("---------------------------------------------------------------------------------.S");
            log.debug("niceNamefactData = " + niceNamefactData.toMap());
            log.debug("---------------------------------------------------------------------------------.E");
        }

        model.addAttribute("namefactData", niceNamefactData);

        request.getSession().setAttribute("namefact_end_url", end_url);

        log.debug("final model  => " + model);
        log.debug("final design => " + design);

        /* 임시 값 저장 */
        /*
         * request.getSession().setAttribute("next_url", "123");
         * request.getSession().setAttribute(namefact_end_url_param, "end");
         * request.getSession().setAttribute(namefact_type_param, "normal");
         */

        if (StringUtil.IsEmpty(design)) {
            return VIEW(request, "/form");
        } else {
            return VIEW(request, "/form_".concat(design));
        }

    }

    private void SaveEndUrl(HttpServletRequest request, String url) {
        request.getSession().setAttribute(end_url_session, url);
    }

    private String LoadEndUrl(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(end_url_session);
    }

    private void SaveMessageYN(HttpServletRequest request, String yn) {
        request.getSession().setAttribute(message_yn_session, yn);
    }

    private String LoadMessageYN(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(message_yn_session);
    }

    // 뷰 처리
    private String VIEW(HttpServletRequest request, String page) {
		RequestUtil requestData = RequestUtil.getInstance(request);

        StringBuffer sbView = new StringBuffer();
		sbView.append(requestData.getViewType());
        sbView.append("/web/namefact/");
        sbView.append(page);

        log.debug("sbView.toString() = " + sbView.toString());

        String strGotoView = sbView.toString();

        if ("popup:/web/namefact//form".equals(strGotoView)) {
            strGotoView = "/modules/namefact/form";
        }

        return strGotoView;

    }

    // 본인인증 폼 처리
    @RequestMapping(value = "/End")
    public String End(Model model, HttpServletRequest request, HttpServletResponse response,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        log.debug("call End()");
        log.debug("paramMap = " + paramMap);

        String strMyCCid = "";

        String strJobgbn = String.valueOf(request.getSession().getAttribute("jobGbn"));

        if ("memChangePhone".equals(strJobgbn)) {
            LoginVO userVO = commandMap.getUserInfo();
            if (userVO == null) {
                HttpUtility.sendRedirect(request, response, "로그인 후 다시 시도하십시오", Config.USER_ROOT + "/member/login?returnURL=" + URLEncoder.encode("/web/mypage/myinfor/myInforRegist", "UTF-8"));
                return null;
            } else {
                strMyCCid = userVO.getPiAuthkey();
            }
        }

        // 14세 미만 어린이 확인
        String strType = niceNamefactService.getType(request);
        log.debug("strType = " + strType);

        String strChildName = "";
        String strChildBirthDay = "";
        if ("child".equals(strType)) {

            strChildName = String.valueOf(request.getSession().getAttribute("childName"));
            strChildBirthDay = String.valueOf(request.getSession().getAttribute("childBirthDay"));

            log.debug("strChildName     = " + strChildName);
            log.debug("strChildBirthDay = " + strChildBirthDay);

        }

        //// NiceNamefactDto data = niceNamefactService.getData(request, strType);

        String strBirthDate = String.valueOf(request.getSession().getAttribute("birstDate"));

        NiceNamefactDto namefactDto = niceNamefactService.getData(request, "myConfirmCerti_" + strBirthDate);

        log.debug("namefactDto = " + namefactDto);

        if (StringUtil.EqualsNotCase("child", strType)) {

            if (namefactDto != null && namefactDto.getAge() > 0 && namefactDto.getAge() >= 14) {

                niceNamefactService.clear(request, strType);

                StringBuffer url = new StringBuffer();

                url.append("if (self.opener && self.opener.parent) { self.opener.parent.location.reload(); self.close(); }");
                url.append("else if (self.opener) { self.opener.location.reload(); self.close(); }");
                url.append("else if (self.parent) { self.parent.location.reload(); }");
                url.append("else { self.location.reload(); }");

                ResponseUtil.SendMessage(request, response, "어린이는 14세 미만입니다.", url.toString());
                return null;
            }

        } else {

            if (namefactDto != null && namefactDto.getAge() > 0 && namefactDto.getAge() < 14) {

                niceNamefactService.clear(request, strType);

                StringBuffer url = new StringBuffer();

                url.append("if (self.opener && self.opener.parent) { self.opener.parent.location.reload(); self.close(); }");
                url.append("else if (self.opener) { self.opener.location.reload(); self.close(); }");
                url.append("else if (self.parent) { self.parent.location.reload(); }");
                url.append("else { self.location.reload(); }");

                ResponseUtil.SendMessage(request, response, "14세 미만은 어린이입니다.", url.toString());
                return null;
            }
        }

        String strReturnUrl = "";

        if ("memRegiNormal".equals(strJobgbn)) {
            strReturnUrl = "/web/member/join/joinStep4";
        } else if ("memRegiChild".equals(strJobgbn)) {
        	strReturnUrl = "/web/member/join/joinStep4";
        } else if ("findPasswd".equals(strJobgbn)) {
            strReturnUrl = "/web/member/findPasswdSet";
        } else if ("findIdByCerti".equals(strJobgbn)) {
            strReturnUrl = "/web/member/findIdByCertiResult";
        } else if ("memChangePhone".equals(strJobgbn)) {
            strReturnUrl = "/web/mypage/myinfor/myInforRegist";

            // log.debug("changePhoneNum = " + namefactDto.getTel());
            // model.addAttribute("changePhoneNum", namefactDto.getTel());

            String strCrCDi = namefactDto.getCrc_data_di();

            if (strCrCDi == null) {
                strCrCDi = "";
            }

            log.debug("strCrCDi = " + strCrCDi);
            log.debug("strMyCCid = " + strMyCCid);

            if (!(strCrCDi.equals(strMyCCid))) {
                HttpUtility.sendClose(request, response, "로그인 사용자와 본인인증 사용자가 일치하지 않습니다.");
                return null;
            }

            request.getSession().setAttribute("changePhoneNum", namefactDto.getTel());

        }

        log.debug("strReturnUrl 2 = " + strReturnUrl);

        if (StringUtil.IsEmpty(strReturnUrl)) {

            HttpUtility.sendClose(request, response, "본인인증을 완료하였으나, 돌아갈 주소 정보가 없습니다.");
            return null;

        } else {

            String strMssageYn = LoadMessageYN(request);
            
            if ("findIdByCerti".equals(strJobgbn)) {
            	model.addAttribute("errMsg", "");
            	return "/web/member/resCertify";
            }else {

            // 이동 주소 처리
            // String endUrl = LoadEndUrl(request);
            StringBuffer sbUrl = new StringBuffer();

            sbUrl.append("if (self.opener && self.opener.parent) { self.opener.parent.location.href = '").append(strReturnUrl).append("'; self.close(); }");
            sbUrl.append("else if (self.opener) { self.opener.location.href = '").append(strReturnUrl).append("'; self.close(); }");
            sbUrl.append("else if (self.parent) { self.parent.location.href = '").append(strReturnUrl).append("'; }");
            sbUrl.append("else { self.location.href = '").append(strReturnUrl).append("'; }");

            log.debug("sbUrl = " + sbUrl);
            
            ResponseUtil.SendMessage(request, response, !StringUtil.EqualsNotCase("N", strMssageYn)
                    ? "본인인증이 완료되었습니다." : null, sbUrl.toString(), "600", null);
            }
        }

        return null;
    }

    // G-PIN 처리 : 수신페이지 : 암호화 안된 정보
    @RequestMapping(value = "/GPIN/Response/NotEnc")
    public String GPIN_Response_NOTENC(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        request.setCharacterEncoding("UTF-8");

        String dupInfo = request.getParameter("dupInfo");
        String virtualNo = request.getParameter("virtualNo");
        String realName = request.getParameter("realName");
        String sex = request.getParameter("sex");
        String age = request.getParameter("age");
        String birthDate = request.getParameter("birthDate");
        String nationalInfo = request.getParameter("nationalInfo");
        String authInfo = request.getParameter("authInfo");

        if (StringUtil.IsEmpty(dupInfo))
            dupInfo = (String) request.getSession().getAttribute("dupInfo");
        if (StringUtil.IsEmpty(virtualNo))
            virtualNo = (String) request.getSession().getAttribute("virtualNo");
        if (StringUtil.IsEmpty(realName))
            realName = (String) request.getSession().getAttribute("realName");
        if (StringUtil.IsEmpty(sex))
            sex = (String) request.getSession().getAttribute("sex");
        if (StringUtil.IsEmpty(age))
            age = (String) request.getSession().getAttribute("age");
        if (StringUtil.IsEmpty(birthDate))
            birthDate = (String) request.getSession().getAttribute("birthDate");
        if (StringUtil.IsEmpty(nationalInfo))
            nationalInfo = (String) request.getSession().getAttribute("nationalInfo");
        if (StringUtil.IsEmpty(authInfo))
            authInfo = (String) request.getSession().getAttribute("authInfo");

        String type = niceNamefactService.getType(request);
        Date birthday = DateUtil.string2date(birthDate);
        NiceNamefactDto namefactData = new NiceNamefactDto();

        namefactData.setCrc_type("gpin");
        namefactData.setCrc_data_ci(virtualNo);
        namefactData.setCrc_data_di(dupInfo);
        namefactData.setType(type);
        namefactData.setName(realName);
        namefactData.setSex("1".equals(sex) ? "M" : "F");
        namefactData.setAge(DateUtil.calcAge(birthday));
        namefactData.setBirthday(birthday);
        namefactData.setTel(null);
        namefactData.setForeigner("1".equals(nationalInfo) ? "Y" : "N");

        // 본인인증 정보 추가하기
        niceNamefactService.saveData(request, type, namefactData);

        // 종료 페이지로 이동
        return redirect_namefact_end;
    }

    // checkplus 파라미터 치환 함수
    private String CHECKPLUS__requestReplace(String paramValue, String gubun) {

        String strThisParamValue = paramValue;

        String result = "";

        if (strThisParamValue != null) {

            strThisParamValue = strThisParamValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

            strThisParamValue = strThisParamValue.replaceAll("\\*", "");
            strThisParamValue = strThisParamValue.replaceAll("\\?", "");
            strThisParamValue = strThisParamValue.replaceAll("\\[", "");
            strThisParamValue = strThisParamValue.replaceAll("\\{", "");
            strThisParamValue = strThisParamValue.replaceAll("\\(", "");
            strThisParamValue = strThisParamValue.replaceAll("\\)", "");
            strThisParamValue = strThisParamValue.replaceAll("\\^", "");
            strThisParamValue = strThisParamValue.replaceAll("\\$", "");
            strThisParamValue = strThisParamValue.replaceAll("'", "");
            strThisParamValue = strThisParamValue.replaceAll("@", "");
            strThisParamValue = strThisParamValue.replaceAll("%", "");
            strThisParamValue = strThisParamValue.replaceAll(";", "");
            strThisParamValue = strThisParamValue.replaceAll(":", "");
            strThisParamValue = strThisParamValue.replaceAll("-", "");
            strThisParamValue = strThisParamValue.replaceAll("#", "");
            strThisParamValue = strThisParamValue.replaceAll("--", "");
            strThisParamValue = strThisParamValue.replaceAll("-", "");
            strThisParamValue = strThisParamValue.replaceAll(",", "");

            if (gubun != "encodeData") {
                strThisParamValue = strThisParamValue.replaceAll("\\+", "");
                strThisParamValue = strThisParamValue.replaceAll("/", "");
                strThisParamValue = strThisParamValue.replaceAll("=", "");
            }

            result = strThisParamValue;

        }

        return result;
    }

    // 성공 처리 : checkplus mobile : NICE평가정보 : 휴대전화 본인인증
    @SuppressWarnings("unused")
    @RequestMapping(value = "/CHECKPLUS_MOBILE/Success")
    public String CHECKPLUS_MOBILE__Success(Model model, HttpServletRequest request, HttpServletResponse response,
            @RequestParam Map<String, Object> paramMap) throws Exception {

        request.setCharacterEncoding("UTF-8");

        HiscoWebTool.LoadModuleInfo(request);

        ModuleInfoDto namefactModuleInfo = HiscoWebTool.LoadModuleInfo("namefact");

        // 사용 유무 확인
        String use = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_mobile_use")
                ? (String) namefactModuleInfo.getProperties().get("checkplus_mobile_use") : null;
        String strUse = "Y";
        if (!StringUtil.EqualsNotCase("Y", strUse)) {
            HttpUtility.sendClose(request, response, "NICE평가정보 : 휴대전화 본인인증을 사용할 수 없습니다.\\n관리자께 문의해주시기 바랍니다.\\n현재창은 닫힙니다.");
            return null;
        }

        String sEncodeData = CHECKPLUS__requestReplace(request.getParameter("EncodeData"), "encodeData");
        String sReserved1 = CHECKPLUS__requestReplace(request.getParameter("param_r1"), "");
        String sReserved2 = CHECKPLUS__requestReplace(request.getParameter("param_r2"), "");
        String sReserved3 = CHECKPLUS__requestReplace(request.getParameter("param_r3"), "");
        String sReserved4 = CHECKPLUS__requestReplace(request.getParameter("param_r4"), "");

        NiceNamefactDto namefactDto = new NiceNamefactDto();

        log.debug("sEncodeData = " + sEncodeData);
        log.debug("sReserved1 = " + sReserved1);
        log.debug("sReserved2 = " + sReserved2);
        log.debug("sReserved3 = " + sReserved3);
        log.debug("sReserved4 = " + sReserved4);

        if (StringUtil.IsEmpty(sEncodeData)) {
            HttpUtility.sendClose(request, response, "수신 정보값이 없습니다.\\n다시 본인인증을 시도해주시기 바랍니다.\\n현재창은 닫힙니다.");
            return null;
        }

        // NICE로부터 부여받은 사이트 코드
        String sSiteCode = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_mobile_sitecode")
                ? (String) namefactModuleInfo.getProperties().get("checkplus_mobile_sitecode") : null;
        // String sSiteCode = "BO655";

        // NICE로부터 부여받은 사이트 패스워드
        String sSitePassword = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_mobile_password")
                ? (String) namefactModuleInfo.getProperties().get("checkplus_mobile_password") : null;
        //// String sSitePassword = "oP0WUfZQDFGY";

        if (StringUtil.IsEmpty(sSiteCode) || StringUtil.IsEmpty(sSitePassword)) {
            HttpUtility.sendClose(request, response, "사이트 아이디 등 설정 정보값이 없습니다.\\n관리자께 문의해주시기 바랍니다.\\n현재창은 닫힙니다.");
            return null;
        }

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
        String ssMobileCompany = ""; // 통신사

        int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

        log.debug("iReturn = " + iReturn);

        if (iReturn == 0) {

            sPlainData = niceCheck.getPlainData();
            sCipherTime = niceCheck.getCipherDateTime();

            log.debug("sPlainData  = " + sPlainData);
            log.debug("sCipherTime = " + sCipherTime);

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
            ssMobileCompany = (String) mapresult.get("MOBILE_CO");

            String session_sRequestNumber = (String) request.getSession().getAttribute("REQ_SEQ");

            log.debug("session_sRequestNumber = " + session_sRequestNumber);

            log.debug("sMobileNumber = " + sMobileNumber);

            /*if (!sRequestNumber.equals(session_sRequestNumber)) {
                sMessage = "요청번호 값이 다릅니다. 올바른 경로로 접근하시기 바랍니다.";
                sResponseNumber = "";
                sAuthType = "";
            }*/

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

        request.getSession().setAttribute("birstDate", sBirthDate);

        // 실패
        if (!StringUtil.IsEmpty(sMessage)) {
            HttpUtility.sendClose(request, response, sMessage.concat("\n다시 본인인증을 시도해주시기 바랍니다.\n현재 창은 닫힙니다."));
        } else {

            String type = niceNamefactService.getType(request);
            Date birthday = DateUtil.string2date(sBirthDate);

            namefactDto.setCrc_type("checkplus_mobile");
            namefactDto.setCrc_data_ci(sConnInfo);
            namefactDto.setCrc_data_di(sDupInfo);
            namefactDto.setType(type);
            namefactDto.setName(sName);
            namefactDto.setSex("1".equals(sGender) ? "M" : "F");
            namefactDto.setAge(DateUtil.calcAge(birthday));
            namefactDto.setBirthday(birthday);
            namefactDto.setTel(null);
            namefactDto.setForeigner("1".equals(sNationalInfo) ? "Y" : "N");
            namefactDto.setTel(sMobileNumber);

            // 본인인증 정보 추가하기
            // niceNamefactService.saveData(request, type, namefactDto);

            niceNamefactService.saveData(request, "myConfirmCerti_" + sBirthDate, namefactDto);

            // 종료 페이지로 이동
            return redirect_namefact_end;
        }

        return null;
    }

    // 실패 처리 : checkplus mobile : NICE평가정보 : 휴대전화 본인인증
    @RequestMapping(value = "/CHECKPLUS_MOBILE/Fail")
    public String CHECKPLUS_MOBILE__Fail(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ResponseUtil.SendMessage(request, response, "본인인증이 실패하였습니다.\n다시 본인인증을 시도해주시기 바랍니다.\n현재 창은 닫힙니다.", "self.close();");
        return null;
    }

    // 성공 처리 : checkplus i-PIN : NICE평가정보 : 아이핀 본인인증
    @SuppressWarnings("unused")
    @RequestMapping(value = "/CHECKPLUS_IPIN/Success")
    public String CHECKPLUS_IPIN__Success(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        request.setCharacterEncoding("UTF-8");

        HiscoWebTool.LoadModuleInfo(request);

        ModuleInfoDto namefactModuleInfo = HiscoWebTool.LoadModuleInfo("namefact");

        // 사용 유무 확인
        String use = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_ipin_use")
                ? (String) namefactModuleInfo.getProperties().get("checkplus_ipin_use") : null;
        if (!StringUtil.EqualsNotCase("Y", use)) {
            ResponseUtil.SendMessage(request, response, "NICE평가정보 : 아이핀 본인인증을 사용할 수 없습니다.\n관리자께 문의해주시기 바랍니다.\n현재창은 닫힙니다.", "self.close();");
            return null;
        }

        String sEncodeData = CHECKPLUS__requestReplace(request.getParameter("EncodeData") != null
                ? request.getParameter("EncodeData") : request.getParameter("enc_data"), "encodeData");
        String sReserved1 = CHECKPLUS__requestReplace(request.getParameter("param_r1"), "");
        String sReserved2 = CHECKPLUS__requestReplace(request.getParameter("param_r2"), "");
        String sReserved3 = CHECKPLUS__requestReplace(request.getParameter("param_r3"), "");
        String sReserved4 = CHECKPLUS__requestReplace(request.getParameter("param_r4"), "");

        if (StringUtil.IsEmpty(sEncodeData)) {
            ResponseUtil.SendMessage(request, response, "수신 정보값이 없습니다.\n다시 본인인증을 시도해주시기 바랍니다.\n현재창은 닫힙니다.", "self.close();");
            return null;
        }

        log.debug("sReserved4 = " + sReserved4);

        // NICE로부터 부여받은 사이트 코드
        String sSiteCode = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_ipin_sitecode")
                ? (String) namefactModuleInfo.getProperties().get("checkplus_ipin_sitecode") : null;

        // NICE로부터 부여받은 사이트 패스워드
        String sSitePassword = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_ipin_password")
                ? (String) namefactModuleInfo.getProperties().get("checkplus_ipin_password") : null;

        if (StringUtil.IsEmpty(sSiteCode) || StringUtil.IsEmpty(sSitePassword)) {
            ResponseUtil.SendMessage(request, response, "사이트 아이디 등 설정 정보값이 없습니다.\n관리자께 문의해주시기 바랍니다.\n현재창은 닫힙니다.", "self.close();");
            return null;
        }

        // CP 요청번호 : ipin_main.jsp 에서 세션 처리한 데이타
        String sCPRequest = (String) request.getSession().getAttribute("CPREQUEST");

        Kisinfo.Check.IPINClient pClient = new Kisinfo.Check.IPINClient();

        int iRtn = pClient.fnResponse(sSiteCode, sSitePassword, sEncodeData);

        String sRtnMsg = null; // 처리결과 메세지
        String sVNumber = null; // 가상주민번호 (13자리이며, 숫자 또는 문자 포함)
        String sName = null; // 이름
        String sDupInfo = null; // 중복가입 확인값 (DI - 64 byte 고유값)
        String sAgeCode = null; // 연령대 코드 (개발 가이드 참조)
        String sGenderCode = null; // 성별 코드 (개발 가이드 참조)
        String sBirthDate = null; // 생년월일 (YYYYMMDD)
        String sNationalInfo = null; // 내/외국인 정보 (개발 가이드 참조)
        String sCPRequestNum = null; // CP 요청번호

        // Method 결과값에 따른 처리사항
        if (iRtn == 1) {
            sRtnMsg = "";
            sVNumber = pClient.getVNumber();
            sName = pClient.getName();
            sDupInfo = pClient.getDupInfo();
            sAgeCode = pClient.getAgeCode();
            sGenderCode = pClient.getGenderCode();
            sBirthDate = pClient.getBirthDate();
            sNationalInfo = pClient.getNationalInfo();
            sCPRequestNum = pClient.getCPRequestNO();
        } else if (iRtn == -1 || iRtn == -4) {
            sRtnMsg = "iRtn 값, 서버 환경정보를 정확히 확인하여 주시기 바랍니다.";
        } else if (iRtn == -6) {
            sRtnMsg = "당사는 한글 charset 정보를 euc-kr 로 처리하고 있으니, euc-kr 에 대해서 허용해 주시기 바랍니다.\n한글 charset 정보가 명확하다면 iRtn 값, 서버 환경정보를 정확히 확인하여 메일로 요청해 주시기 바랍니다.</B>";
        } else if (iRtn == -9) {
            sRtnMsg = "입력값 오류 : fnResponse 함수 처리시, 필요한 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.";
        } else if (iRtn == -12) {
            sRtnMsg = "CP 비밀번호 불일치 : IPIN 서비스 사이트 패스워드를 확인해 주시기 바랍니다.";
        } else if (iRtn == -13) {
            // sRtnMsg = "CP 요청번호 불일치 : 세션에 넣은 sCPRequest 데이타를 확인해 주시기 바랍니다.";
            sRtnMsg = "요청번호 값이 다릅니다. 올바른 경로로 접근하시기 바랍니다.";
        } else {
            sRtnMsg = "iRtn값(" + iRtn + ") 확인 후, NICE평가정보 전산 담당자에게 문의해 주세요.";
        }
        
        request.getSession().setAttribute("birstDate", sBirthDate);

        // 실패
        if (!StringUtil.IsEmpty(sRtnMsg)) {
            ResponseUtil.SendMessage(request, response, sRtnMsg.concat("\n다시 본인인증을 시도해주시기 바랍니다.\n현재 창은 닫힙니다."), "self.close();");
        } else {
            // 성공
            String type = niceNamefactService.getType(request);
            Date birthday = DateUtil.string2date(sBirthDate);
            NiceNamefactDto namefactDto = new NiceNamefactDto();

            namefactDto.setCrc_type("checkplus_ipin");
            namefactDto.setCrc_data_ci(sVNumber); 
            namefactDto.setCrc_data_di(sDupInfo); 
            namefactDto.setType(type);
            namefactDto.setName(sName);
            namefactDto.setSex("1".equals(sGenderCode) ? "M" : "F");
            namefactDto.setAge(DateUtil.calcAge(birthday));
            namefactDto.setBirthday(birthday);
            namefactDto.setTel(null);
            namefactDto.setForeigner("1".equals(sNationalInfo) ? "Y" : "N");

            // 본인인증 정보 추가하기
            //niceNamefactService.saveData(request, type, namefactData);
            niceNamefactService.saveData(request, "myConfirmCerti_" + sBirthDate, namefactDto);
            
            // 종료 페이지로 이동
            return redirect_namefact_end;
        }

        return null;
    }

    // 실패 처리 : checkplus i-PIN : NICE평가정보 : 아이핀 본인인증
    @RequestMapping(value = "/CHECKPLUS_IPIN/Fail")
    public String CHECKPLUS_IPIN__Fail(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ResponseUtil.SendMessage(request, response, "본인인증이 실패하였습니다.\n다시 본인인증을 시도해주시기 바랍니다.\n현재 창은 닫힙니다.", "self.close();");
        return null;
    }

    @RequestMapping(value = "/niceMineResult")
    public String niceAuthResult(Model model, HttpServletRequest request, HttpServletResponse response,
            @RequestParam Map<String, Object> paramMap) throws Exception {

        String strBirthDate = String.valueOf(request.getSession().getAttribute("birstDate"));

        NiceNamefactDto namefactDto = niceNamefactService.getData(request, "myConfirmCerti_" + strBirthDate);

        log.debug("namefactDto = " + namefactDto);

        /*
         ************************************************* 1.최종인증 요청 - BEGIN
         */
        /*
         * String lgdRESPCODE = request.getParameter("LGD_RESPCODE");
         * String lgdRESPMSG = request.getParameter("LGD_RESPMSG");
         * if (lgdRESPCODE == null) {
         * lgdRESPCODE = "ERROR";
         * lgdRESPMSG = "null 오류";
         * }
         * String lgdPLATFORM = request.getParameter("CST_PLATFORM");
         * if (lgdPLATFORM == null) lgdPLATFORM = "service";
         * String lgdMID = request.getParameter("LGD_MID");
         * String lgdAUTHONLYKEY = request.getParameter("LGD_AUTHONLYKEY"); //토스페이먼츠으로부터 부여받은 인증키
         * String lgdPAYTYPE = request.getParameter("LGD_PAYTYPE"); //인증요청타입 (신용카드:ASC001, 휴대폰 대체인증:ASC007, 계좌:ASC004)
         * model.addAttribute("LGD_RESPCODE", lgdRESPCODE);
         * model.addAttribute("LGD_RESPMSG", lgdRESPMSG);
         * model.addAttribute("LGD_MID", lgdMID);
         * model.addAttribute("LGD_AUTHONLYKEY", lgdAUTHONLYKEY);
         * model.addAttribute("LGD_PAYTYPE", lgdPAYTYPE);
         */

        return HttpUtility.getViewUrl(request);
    }

}