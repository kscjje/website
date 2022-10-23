package com.hisco.intrfc.dashboard.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.cmm.object.CommandMap;
import com.hisco.intrfc.dashboard.service.DashboardService;

/**
 * Dashboard 컨트롤러
 *
 * @author 전영석
 * @since 2020.10.18
 * @version 1.0, 2020.10.18
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.10.18 최초작성
 */
@Controller
public class DashboardController {

    @Resource(name = "dashboardService")
    private DashboardService dashboardService;

    /**
     * Dashboard 주화면을 Open 조회한다.
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dashboard/main", method = { RequestMethod.GET }, produces = "application/json; charset=UTF-8")
    public String openDashboard(HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> paramMap)
            throws Exception {

        // log.debug("call openDashboard");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        List<?> lstResultToday = dashboardService.selectToday(paramMap);

        // log.debug(lstResultToday);

        model.addAttribute("resultToday", lstResultToday);

        return "/intrfc/dashboard/dashboardMain";
    }

    /**
     * 어제 일자를 조회한다.
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dashboard/selectYesterday", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectYesterday(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap) throws Exception {

        // log.debug("call selectYesterday");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();

        List<?> lstResultYesterday = dashboardService.selectYesterday(paramMap);

        finalResult.put("SUCCESS_YN", "Y");
        finalResult.put("resultYesterday", lstResultYesterday);

        mav.addObject("RESULT", finalResult);

        // log.debug(mav);

        return mav;
    }

    /**
     * 어제 일자를 조회한다.
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dashboard/selectToday", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectToday(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap) throws Exception {

        // log.debug("call selectToday");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();

        List<?> lstResultYesterday = dashboardService.selectToday(paramMap);

        finalResult.put("SUCCESS_YN", "Y");
        finalResult.put("resultToday", lstResultYesterday);

        mav.addObject("RESULT", finalResult);

        // log.debug(mav);

        return mav;
    }

    /**
     * 선택 일자를 조회한다.
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dashboard/selectDate", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectDate(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap) throws Exception {

        // log.debug("call selectDate");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();

        List<?> lstResultDate = dashboardService.selectDate(paramMap);

        finalResult.put("SUCCESS_YN", "Y");
        finalResult.put("resultDate", lstResultDate);

        mav.addObject("RESULT", finalResult);

        // log.debug(mav);

        return mav;
    }

    /**
     * Dashboard 내용을 조회한다.
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dashboard/selectStt1", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectStt1(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        // log.debug("call selectStt1");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();

        List<?> lstResultMember = dashboardService.selectStt1(paramMap);

        finalResult.put("SUCCESS_YN", "Y");
        finalResult.put("resultMember", lstResultMember);

        mav.addObject("RESULT", finalResult);

        // log.debug(mav);

        return mav;

    }

    /**
     * Dashboard 기준 총 입장객 내용을 조회한다.
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dashboard/selectStt22", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectStt22(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        // log.debug("call selectStt22");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();

        List<?> lstResultMember = dashboardService.selectStt22(paramMap);

        finalResult.put("SUCCESS_YN", "Y");
        finalResult.put("resultStt22", lstResultMember);

        mav.addObject("RESULT", finalResult);

        // log.debug(mav);

        return mav;

    }

    /**
     * Dashboard 기준 총 관람객 내용을 조회한다.
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dashboard/selectStt2", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectStt2(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        // log.debug("call selectStt2");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();

        List<?> lstResultMember = dashboardService.selectStt2(paramMap);

        finalResult.put("SUCCESS_YN", "Y");
        finalResult.put("resultStt2", lstResultMember);

        mav.addObject("RESULT", finalResult);

        // log.debug(mav);

        return mav;

    }

    /**
     * Dashboard 기준 총 매출 내용을 조회한다.
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dashboard/selectStt3", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectStt3(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        // log.debug("call selectStt3");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();

        List<?> lstResultMember = dashboardService.selectStt3(paramMap);

        finalResult.put("SUCCESS_YN", "Y");
        finalResult.put("resultStt3", lstResultMember);

        mav.addObject("RESULT", finalResult);

        // log.debug(mav);

        return mav;

    }

    /**
     * Dashboard 내용을 조회한다.
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dashboard/selectStt4", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectStt4(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        // log.debug("call selectStt4");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();

        List<?> lstResultMember = dashboardService.selectStt4(paramMap);

        finalResult.put("SUCCESS_YN", "Y");
        finalResult.put("resultStt4", lstResultMember);

        mav.addObject("RESULT", finalResult);

        // log.debug(mav);

        return mav;

    }

    /**
     * Dashboard 내용을 조회한다.
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dashboard/selectStt5", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectStt5(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        // log.debug("call selectStt5");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();

        List<?> lstResultMember = dashboardService.selectStt5(paramMap);

        finalResult.put("SUCCESS_YN", "Y");
        finalResult.put("resultStt5", lstResultMember);

        mav.addObject("RESULT", finalResult);

        // log.debug(mav);

        return mav;

    }

    /**
     * Dashboard 내용을 조회한다.
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dashboard/selectStt52", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectStt52(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        // log.debug("call selectStt52");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();

        List<?> lstResultMember = dashboardService.selectStt52(paramMap);

        finalResult.put("SUCCESS_YN", "Y");
        finalResult.put("resultStt52", lstResultMember);

        mav.addObject("RESULT", finalResult);

        // log.debug(mav);

        return mav;

    }

    /**
     * Dashboard 내용을 조회한다.
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dashboard/selectStt6", method = { RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ModelAndView selectStt6(HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        // log.debug("call selectStt6");
        // log.debug(request.getCharacterEncoding());
        // log.debug(paramMap);

        ModelAndView mav = new ModelAndView("jsonView");

        Map<String, Object> finalResult = new HashMap<String, Object>();

        List<?> lstResultMember = dashboardService.selectStt6(paramMap);

        finalResult.put("SUCCESS_YN", "Y");
        finalResult.put("resultStt6", lstResultMember);

        mav.addObject("RESULT", finalResult);

        // log.debug(mav);

        return mav;

    }

}
