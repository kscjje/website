package com.hisco.intrfc.survey.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.ScrEncDecUtil;
import com.hisco.intrfc.survey.service.SurveyService;
import com.hisco.intrfc.survey.vo.SurveyMstVO;
import com.hisco.intrfc.survey.vo.SurveyResultVO;
import com.hisco.intrfc.survey.vo.SurveyRsvnVO;
import com.hisco.intrfc.survey.vo.SurveySendVO;

import egovframework.com.cmm.service.EgovProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * 설문 컨트롤러
 * 
 * @author 전영석
 * @since 2020.09.09
 * @version 1.0, 2020.09.09
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.09.09 최초작성
 */
@Slf4j
@Controller
public class OldSurveyController {

    @Resource(name = "surveyService")
    private SurveyService surveyService;

    /**
     * 설문 정보 노출 및 실시 관련 정보를 조회한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Config.USER_ROOT + "/Oldsurvey/member/surveyExecuteList", method = { RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String selectSurveyExecuteList(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap) throws Exception {

        // log.debug("call selectSurveyExecuteList");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        List<?> lstResult = surveyService.selectSurveyExecuteList(paramMap);

        // log.debug(lstResult.toString());

        model.addAttribute("surveyExecuteList", lstResult);

        return "/web/survey/member/surveyExecuteList";
    }

    /**
     * 설문 정보 노출 및 실시 관련 정보를 조회한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/OldsurveyDetail")
    public String selectSurveyDetail(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, SurveyMstVO vo) throws Exception {

        // TODO : 임시 예약번호 삽입
        String rsvnId = commandMap.getString("qestnarRsvnNo").equals("") ? "" : commandMap.getString("qestnarRsvnNo");
        if ("".equals(rsvnId)) {
            String msg = "잘못된 접근입니다.";
            HttpUtility.sendRedirect(request, response, msg, "/web/main");
        }

        SurveyRsvnVO rsvnVO = new SurveyRsvnVO();
        rsvnVO.setComcd(Config.COM_CD);
        rsvnVO.setRsvnId(rsvnId);
        Map<String, Object> rsResult = surveyService.selectRsvnMst(rsvnVO);
        SurveySendVO svo = (SurveySendVO) rsResult.get("sendVO");

        int chk = surveyService.countSurveySendResult(svo);
        if (chk > 0) {
            String msg = "이미 설문에 참여하셨습니다.";
            HttpUtility.sendRedirect(request, response, msg, "/web/main");
        } else if (svo == null) {
            String msg = "해당 예약정보가 없습니다.";
            HttpUtility.sendRedirect(request, response, msg, "/web/main");
        }

        vo.setComcd(Config.COM_CD);
        vo.setQestnarId(svo.getQestnarId());

        Map<String, Object> lstResult = surveyService.selectSurveyDetail(vo);

        model.addAttribute("vo", lstResult.get("vo"));
        model.addAttribute("rsvnVO", rsResult.get("vo"));
        model.addAttribute("sendVO", svo);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/survey/surveyDetail");
    }

    /**
     * 설문 정보 노출 및 실시 관련 정보를 조회한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/Oldusurvey/{surveyValue}", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    public String selectSurveyForMobile(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, SurveyMstVO vo, @PathVariable(value = "surveyValue") String strSurveyValue)
            throws Exception {

        log.debug("call selectSurveyForMobile");

        String strThisSurveyValue = strSurveyValue;

        log.debug(strThisSurveyValue);

        String strSurveyKey = EgovProperties.getProperty("Globals.Survey.Key");

        log.debug("strSurveyKey = " + strSurveyKey);

        String secretKey = strSurveyKey;

        strThisSurveyValue = strThisSurveyValue.replace("$2F", "/");

        log.debug("strSurveyValue replace = " + strThisSurveyValue);

        // --------------------------------------------------------------------------------------------------------Test
        // S.
        /*
         * String strEncryptedString = ScrEncDecUtil.fn_encrypt("R120113000110", secretKey) ;
         * log.debug("strEncryptedString = " + strEncryptedString);
         * log.debug("strEncryptedString Url Encoding = " + java.net.URLEncoder.encode(strEncryptedString,
         * "UTF-8"));
         * log.debug("strEncryptedString Url Encoding Replace = " +
         * java.net.URLEncoder.encode(strEncryptedString, "UTF-8").replace("%2F", "$2F"));
         */
        // ---------------------------------------------------------------------------------------------------------Test
        // E.

        String strDecryptedString = ScrEncDecUtil.fn_decrypt(strThisSurveyValue, secretKey);

        log.debug("strSurveyValue Decrypt = " + strDecryptedString);

        String strDecValue = strDecryptedString;

        // TODO : 임시 예약번호 삽입
        // String rsvnId = commandMap.getString("qestnarRsvnNo").equals("") ? "" :
        // commandMap.getString("qestnarRsvnNo");

        String strRsvnId = strDecValue;

        if (strDecValue == null)
            strDecValue = "";

        if ("".equals(strRsvnId)) {
            String msg = "잘못된 접근입니다.";
            HttpUtility.sendRedirect(request, response, msg, "/web/main");
        }

        SurveyRsvnVO rsvnVO = new SurveyRsvnVO();
        rsvnVO.setComcd(Config.COM_CD);
        rsvnVO.setRsvnId(strRsvnId);

        Map<String, Object> rsResult = surveyService.selectRsvnMst(rsvnVO);
        SurveySendVO svo = (SurveySendVO) rsResult.get("sendVO");

        SurveyRsvnVO voDB = (SurveyRsvnVO) rsResult.get("vo");

        int chk = surveyService.countSurveySendResult(svo);
        if (chk > 0) {
            String msg = "이미 설문에 참여하셨습니다.";
            HttpUtility.sendRedirect(request, response, msg, "/web/main");
        } else if (voDB == null) {
            String msg = "해당 예약정보가 없습니다.";
            HttpUtility.sendRedirect(request, response, msg, "/web/main");
        }

        vo.setComcd(Config.COM_CD);
        vo.setQestnarId(svo.getQestnarId());

        Map<String, Object> lstResult = surveyService.selectSurveyDetail(vo);

        model.addAttribute("vo", lstResult.get("vo"));
        model.addAttribute("rsvnVO", rsResult.get("vo"));
        model.addAttribute("sendVO", svo);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/survey/surveyDetail");
    }

    /**
     * 설문 정보 노출 및 실시 관련 정보를 조회한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    // @PostMapping(value = "/surveyAction")
    @RequestMapping(value = "/Oldusurvey/surveyAction", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    public String insertSurveyDetail(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("surveyResultVO") SurveyResultVO vo) throws Exception {

        log.debug("call insertSurveyDetail");
        log.debug("vo");

        vo.setComcd(Config.COM_CD);

        Map<String, Object> rsResult = surveyService.insertSurveyResult(vo);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/survey/surveyResult");
    }

}
