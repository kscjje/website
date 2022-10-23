package com.hisco.user.edcatnlc.web;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.cmm.modules.DateUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.intrfc.sale.service.NwPayService;
import com.hisco.intrfc.sale.vo.NwPayApiType;
import com.hisco.intrfc.sale.vo.NwPayVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : NwPayTestController.java
 * @Description : 노원페이 테스트
 * @author woojinp@legitsys.co.kr
 * @since 2022. 1. 7.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */

@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/edc/nwpay")
public class NwPayTestController {

    @Resource(name = "nwPayService")
    private NwPayService nwPayService;

    /**
     * nwpay 테스트 url
     */
    @RequestMapping(value = "/tapikey")
    @ResponseBody
    public ModelAndView testApikey(NwPayVO reqPayVO, HttpServletRequest request, ModelMap model,
            CommandMap commandMap) throws Exception {

        reqPayVO.setApiType(NwPayApiType.APIKEY);
        log.debug("reqPayVO = {}", reqPayVO);

        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject(nwPayService.call(reqPayVO));

        return mav;
    }

    /**
     * nwpay 테스트 url
     */
    @RequestMapping(value = "/tapikeycheck")
    @ResponseBody
    public ModelAndView testApikeycheck(NwPayVO reqPayVO, HttpServletRequest request, ModelMap model,
            CommandMap commandMap) throws Exception {

        reqPayVO.setApiType(NwPayApiType.APIKEYCHECK);
        log.debug("reqPayVO = {}", reqPayVO);

        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject(nwPayService.call(reqPayVO));

        return mav;
    }

    /**
     * nwpay 테스트 url
     */
    @RequestMapping(value = "/tbalance")
    @ResponseBody
    public ModelAndView testBalance(NwPayVO reqPayVO, HttpServletRequest request, ModelMap model,
            CommandMap commandMap) throws Exception {

        reqPayVO.setApiType(NwPayApiType.BALANCE);
        log.debug("reqPayVO = {}", reqPayVO);

        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject(nwPayService.call(reqPayVO));

        return mav;
    }

    /**
     * nwpay 테스트 url
     */
    @RequestMapping(value = "/tsendorder")
    @ResponseBody
    public ModelAndView testSendorder(NwPayVO reqPayVO, HttpServletRequest request, ModelMap model,
            CommandMap commandMap) throws Exception {

        reqPayVO.setApiType(NwPayApiType.SENDORDER);
        log.debug("reqPayVO = {}", reqPayVO);

        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject(nwPayService.call(reqPayVO));

        return mav;
    }

    /**
     * nwpay 테스트 url
     */
    @RequestMapping(value = "/twithdrawal")
    @ResponseBody
    public ModelAndView testWithdrawal(NwPayVO reqPayVO, HttpServletRequest request, ModelMap model,
            CommandMap commandMap) throws Exception {

        reqPayVO.setApiType(NwPayApiType.WITHDRAWAL);
        log.debug("reqPayVO = {}", reqPayVO);

        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject(nwPayService.call(reqPayVO));

        return mav;
    }

    // ---------------------------------------------------------------------------------------------------------

    /**
     * nwpay 연계 mock api
     */
    @PostMapping(value = "/apikey", produces = "application/json; charset=UTF-8")
    public void apikey(NwPayVO reqPayVO, HttpServletRequest request, HttpServletResponse response, ModelMap model,
            CommandMap commandMap) throws Exception {

        if ("prod".equalsIgnoreCase(CommonUtil.getProfile()))
            return;

        log.debug("reqPayVO = {}", reqPayVO);

        NwPayVO resPayVO = new NwPayVO();
        resPayVO.setApiType(NwPayApiType.APIKEY);
        resPayVO.setStatus("200");
        resPayVO.setData("success");
        resPayVO.setApikey("123456789123456");

        log.debug("resPayVO = {}", resPayVO);

        PrintWriter writer = response.getWriter();
        writer.print(JsonUtil.Object2String(resPayVO));
        writer.flush();
        writer.close();
    }

    /**
     * nwpay 연계 mock api
     */
    @PostMapping(value = "/apikeycheck", produces = "application/json; charset=UTF-8")
    public void apikeycheck(NwPayVO reqPayVO, HttpServletRequest request, HttpServletResponse response, ModelMap model,
            CommandMap commandMap) throws Exception {

        if ("prod".equalsIgnoreCase(CommonUtil.getProfile()))
            return;

        log.debug("reqPayVO = {}", reqPayVO);

        NwPayVO resPayVO = new NwPayVO();
        resPayVO.setApiType(NwPayApiType.APIKEYCHECK);
        resPayVO.setStatus("200");
        resPayVO.setData("success");

        log.debug("resPayVO = {}", resPayVO);

        PrintWriter writer = response.getWriter();
        writer.print(JsonUtil.Object2String(resPayVO));
        writer.flush();
        writer.close();
    }

    /**
     * nwpay 연계 mock api
     */
    @PostMapping(value = "/balance", produces = "application/json; charset=UTF-8")
    public void balance(NwPayVO reqPayVO, HttpServletRequest request, HttpServletResponse response, ModelMap model,
            CommandMap commandMap) throws Exception {

        if ("prod".equalsIgnoreCase(CommonUtil.getProfile()))
            return;

        log.debug("reqPayVO = {}", reqPayVO);

        NwPayVO resPayVO = new NwPayVO();
        resPayVO.setApiType(NwPayApiType.BALANCE);
        resPayVO.setStatus("200");
        resPayVO.setData("success");
        resPayVO.setAmount(3560);

        log.debug("resPayVO = {}", resPayVO);

        PrintWriter writer = response.getWriter();
        writer.print(JsonUtil.Object2String(resPayVO));
        writer.flush();
        writer.close();
    }

    /**
     * nwpay 연계 mock api
     */
    @PostMapping(value = "/sendorder", produces = "application/json; charset=UTF-8")
    public void sendorder(NwPayVO reqPayVO, HttpServletRequest request, HttpServletResponse response, ModelMap model,
            CommandMap commandMap) throws Exception {

        if ("prod".equalsIgnoreCase(CommonUtil.getProfile()))
            return;

        log.debug("reqPayVO = {}", reqPayVO);

        NwPayVO resPayVO = new NwPayVO();
        resPayVO.setApiType(NwPayApiType.SENDORDER);
        resPayVO.setStatus("200");
        resPayVO.setUser(reqPayVO.getUseid());
        resPayVO.setData("success");
        resPayVO.setAmount(18000); // 남은금액
        resPayVO.setOrderID(String.format("nwoid_%s_%s", reqPayVO.getUseid(), DateUtil.getTodate("yyyyMMddHHmmss")));

        log.debug("resPayVO = {}", resPayVO);

        PrintWriter writer = response.getWriter();
        writer.print(JsonUtil.Object2String(resPayVO));
        writer.flush();
        writer.close();
    }

    /**
     * nwpay 연계 mock api
     */
    @PostMapping(value = "/withdrawal", produces = "application/json; charset=UTF-8")
    public void withdrawal(NwPayVO reqPayVO, HttpServletRequest request, HttpServletResponse response, ModelMap model,
            CommandMap commandMap) throws Exception {

        if ("prod".equalsIgnoreCase(CommonUtil.getProfile()))
            return;

        log.debug("reqPayVO = {}", reqPayVO);

        NwPayVO resPayVO = new NwPayVO();
        resPayVO.setApiType(NwPayApiType.WITHDRAWAL);
        resPayVO.setStatus("200");
        resPayVO.setData("success");

        log.debug("resPayVO = {}", resPayVO);

        PrintWriter writer = response.getWriter();
        writer.print(JsonUtil.Object2String(resPayVO));
        writer.flush();
        writer.close();
    }

}