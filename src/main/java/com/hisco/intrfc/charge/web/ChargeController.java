package com.hisco.intrfc.charge.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.cmm.util.Config;

import egovframework.com.cmm.service.EgovProperties;

/*
 * 결제 처리 컨트롤러
 * @author 전영석
 * @since 2020.08.25
 * @version 1.0, 2020.08.25
 * ------------------------------------------------------------------------
 * 작성자 일자 내용
 * ------------------------------------------------------------------------
 * 전영석 2020.08.25 최초작성
 */

@Controller
public class ChargeController {

    /**
     * Open 결제 요청 화면
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/charge/openNsmCharge", method = { RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String openNsmCharge(HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> paramMap)
            throws Exception {

        // log.debug("call openNsmCharge");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        return "intrfc/charge/payreq_mainform";
    }

    /**
     * 결제 요청 단계 1
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/charge/reqNsmCharge", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    public String reqNsmCharge(HttpServletRequest request, @RequestParam Map<String, Object> paramMap, ModelMap model)
            throws Exception {

        // log.debug("call reqNsmCharge");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        model.addAttribute("_csrf", String.valueOf(paramMap.get("_csrf")));

        return "intrfc/charge/payreq_crossplatform";
    }

    /**
     * 결제 요청 단계
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/charge/return/returnCallNsmUrl", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String returnCallNsmUrl(HttpServletRequest request, @RequestParam Map<String, Object> paramMap,
            ModelMap model) throws Exception {

        // log.debug("call returnCallNsmUrl");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        // model.addAttribute(paramMap);
        model.addAttribute("_csrf", String.valueOf(paramMap.get("_csrf")));

        return "intrfc/charge/returnurl";
    }

    /**
     * 결제 요청 단계
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/charge/return/resNsmPay", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String resNsmPay(HttpServletRequest request, @RequestParam Map<String, Object> paramMap, ModelMap model)
            throws Exception {

        // log.debug("call resNsmPay");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        // log.debug(csrf.getHeaderName());
        // log.debug(csrf.getParameterName());

        model.addAttribute("_csrf", String.valueOf(paramMap.get("_csrf")));

        return "intrfc/charge/payres";
    }

    /**
     * 결제 요청 단계
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/charge/cancelNsmPay", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String cancelNsmPay(HttpServletRequest request, @RequestParam Map<String, Object> paramMap, ModelMap model)
            throws Exception {

        // log.debug("call cancelNsmPay");
        // log.debug(paramMap);

        return "intrfc/charge/Cancel";
    }

    /**
     * 결제 요청 단계
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/charge/casNoteUrlNsmPay", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String casNoteUrlNsmPay(HttpServletRequest request, @RequestParam Map<String, Object> paramMap,
            ModelMap model) throws Exception {

        // log.debug("call casNoteUrlNsmPay");
        // log.debug(paramMap);

        return "intrfc/charge/cas_noteurl";
    }

    /**
     * 결제 요청 단계
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/charge/casReceiptNsmPay", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String casReceiptNsmPay(HttpServletRequest request, @RequestParam Map<String, Object> paramMap,
            ModelMap model) throws Exception {

        // log.debug("call casReceiptNsmPay");
        // log.debug(paramMap);

        return "intrfc/charge/CashReceipt";
    }

    /*
     * @SuppressWarnings("unchecked")
     * @RequestMapping(value = Config.INTRFC_ROOT + "/charge/reqNsmCharge", method={RequestMethod.POST,
     * RequestMethod.GET}, produces = "application/json; charset=UTF-8")
     * @ResponseBody
     * public ModelAndView reqNsmCharge(HttpServletRequest request, @RequestParam Map<String, Object> paramMap) throws
     * Exception {
     * ModelAndView mav = new ModelAndView("jsonView");
     * log.debug("call reqNsmCharge");
     * log.debug(paramMap);
     * return mav;
     * }
     */

    /**
     * 결제 처리
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = Config.INTRFC_ROOT + "/charge/sndCharge", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView sndCharge(HttpServletRequest request, @RequestParam Map<String, Object> paramMap)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        // log.debug("call sndCharge");
        // log.debug(paramMap);

        /*
         * String strGlobalCd = "1";
         * String strGlobalMsg = "Success";
         * JSONObject rsltCdJsonObj = new JSONObject();
         * JSONObject dataJsonObj = new JSONObject();
         * try {
         * paramMap.put("LOG_MSG", "전 로그 메시지 0");
         * eMailService.insertSeokLogMyBatis(paramMap);
         * //paramMap.put("LOG_MSG", "전 로그 메시지 1");
         * //eMailService.insertSeokLogMyBatis1(paramMap);
         * //paramMap.put("LOG_MSG", "전 로그 메시지 2");
         * //eMailService.insertSeokLogMyBatis2(paramMap);
         * List<?> seokLogMyBatisResult = eMailService.selectSeokLogMyBatis(paramMap);
         * List<?> seokLogProcedureResult = eMailService.selectSeokLogProcedure(paramMap);
         * log.debug("--------------------------------------------------MyBatis.1");
         * log.debug("MyBatisSelectResult   = " + seokLogMyBatisResult);
         * log.debug("--------------------------------------------------MyBatis.2");
         * log.debug("ProcedureSelectResult = " + seokLogProcedureResult);
         * log.debug("--------------------------------------------------MyBatis.3");
         * dataJsonObj.put("MYBATIS_RESULT", seokLogMyBatisResult);
         * dataJsonObj.put("PROCEDURE_RESULT", seokLogProcedureResult);
         * } catch (Exception e) {
         * strGlobalCd = "-1";
         * strGlobalMsg = e.getMessage();
         * throw new Exception(e.getMessage());
         * }
         * rsltCdJsonObj.put("CODE", strGlobalCd);
         * rsltCdJsonObj.put("MSG", strGlobalMsg);
         * rsltCdJsonObj.put("DATA", dataJsonObj);
         * log.debug("-----------------------S");
         * log.debug("return => " + rsltCdJsonObj);
         * log.debug("-----------------------E");
         * mav.addObject("RESULT", rsltCdJsonObj);
         */

        return mav;
    }

    /**
     * 결제 요청 단계
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/charge/payresNsmPay", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    public String payresNsmPay(HttpServletRequest request, @RequestParam Map<String, Object> paramMap, ModelMap model)
            throws Exception {

        // log.debug("call payresNsmPay");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        model.addAttribute("_csrf", String.valueOf(paramMap.get("_csrf")));

        return "intrfc/charge/payres";
    }

    /**
     * 결제 요청 단계
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/charge/remoteCallNsmUrl", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String remoteCallNsmUrl(HttpServletRequest request, @RequestParam Map<String, Object> paramMap,
            ModelMap model) throws Exception {

        // log.debug("call remoteCallNsmUrl");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        // model.addAttribute(paramMap);

        model.addAttribute("_csrf", String.valueOf(paramMap.get("_csrf")));

        return "intrfc/charge/returnurl";
    }

    /**
     * 결제 요청 단계
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/charge/return/reCallNsmUrl", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String reCallNsmUrl(HttpServletRequest request, @RequestParam Map<String, Object> paramMap, ModelMap model)
            throws Exception {

        // log.debug("call reCallNsmUrl");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        // model.addAttribute(paramMap);

        model.addAttribute("_csrf", String.valueOf(paramMap.get("_csrf")));

        return "intrfc/charge/returnurl";
    }

    /**
     * 결제 요청 단계 (모바일)
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.INTRFC_ROOT + "/charge/return/reCallNsmMbUrl", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String reCallNsmUrlMobile(HttpServletRequest request, @RequestParam Map<String, Object> paramMap,
            ModelMap model) throws Exception {

        // log.debug("call reCallNsmUrlMobile");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        // model.addAttribute(paramMap);

        model.addAttribute("_csrf", String.valueOf(paramMap.get("_csrf")));
        model.addAttribute("domain", EgovProperties.getProperty("Globals.Domain") + Config.USER_ROOT);

        return "intrfc/charge/returnurl_mobile";
    }
}