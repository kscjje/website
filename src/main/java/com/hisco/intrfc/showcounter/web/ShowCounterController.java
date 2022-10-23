package com.hisco.intrfc.showcounter.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.cmm.util.Config;

/*
 * 계수기 송신 처리 컨트롤러
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.20
 * ------------------------------------------------------------------------
 * 작성자 일자 내용
 * ------------------------------------------------------------------------
 * 전영석 2020.08.20 최초작성
 */

@Controller
public class ShowCounterController {

    //// @Resource(name = "showCounterService")
    //// private ShowCounterService showCounterService;

    //// @Resource(name = "FileMngUtil")
    //// private FileMngUtil fileUtil;

    //// @Resource(name = "FileMngService")
    //// private FileMngService fileMngService;

    /**
     * 계수기 정보 이관 처리
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    /*
     * @GetMapping(value = "/email/sendEmail")
     * public String sendEmail(HttpServletRequest request, ModelMap model) throws Exception {
     * return "";
     * }
     */

    /**
     * 계수기 정보 이관
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = Config.INTRFC_ROOT + "/showcounter/procShowCounter", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView procShowCounter(HttpServletRequest request, @RequestParam Map<String, Object> paramMap)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        // log.debug("call procShowCounter");
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

}
