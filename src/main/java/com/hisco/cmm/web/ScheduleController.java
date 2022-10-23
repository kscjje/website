package com.hisco.cmm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hisco.cmm.service.ScheduleService;
import com.hisco.intrfc.charge.service.ChargeService;
import com.hisco.intrfc.dormantacct.service.DormantAcctService;
import com.hisco.intrfc.survey.service.SurveyService;

@Controller
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    SurveyService surveyService;

    @Autowired
    ChargeService chargeService;

    @Autowired
    DormantAcctService dormantAcctService;

    /**
     * Web Log Summary
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    // @Scheduled(cron = "0 10 0 * * *")
    // @SuppressWarnings("restriction")
    // @Scheduled(fixedDelay = 3600000)
    // @Scheduled(fixedDelay = 30000)
    public void webLogSummary() throws Exception {
        scheduleService.webLogSummary();
    }

    /**
     * 계수기 데이터 이관
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    // @Scheduled(cron = "0 10 0 * * *")
    // @SuppressWarnings("restriction")
    // @Scheduled(fixedDelay = 3600000)
    // @Scheduled(fixedDelay = 30000)
    public void showCounterMove() throws Exception {

        Map<String, Object> paramMap = new HashMap();

        scheduleService.showCounterMove(paramMap);
    }

    /**
     * 설문 대상 정보 생성
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    // @Scheduled(cron = "0 1 0 * * *")
    // @SuppressWarnings("restriction")
    // @Scheduled(fixedDelay = 3600000)
    // @Scheduled(fixedDelay = 30000)
    public void makeSurveyInfo() throws Exception {

        Map<String, Object> paramMap = new HashMap();

        //// log.debug("call ScheduleController :: makeSurveyInfo()");

        // List<?> lstMainSurvey = surveyService.selectSurveyMainList(paramMap);

        // log.debug("lstMainSurvey = " + lstMainSurvey);

        // scheduleService.sndSurveyToMem(paramMap);
    }

    /**
     * 예약결제대기 자동취소를 처리한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    // @Scheduled(cron = "0 1 0 * * *")
    // @SuppressWarnings("restriction")
    // @Scheduled(fixedDelay = 3600000)
    // @Scheduled(fixedDelay = 30000)
    public void makePGCancel() throws Exception {

        Map<String, Object> paramMap = new HashMap();

        // log.debug("call ScheduleController :: makePGCancel()");

        List<?> lstMainPgCancel = chargeService.selectChargeMainList(paramMap);

        // log.debug("lstMainPgCancel = " + lstMainPgCancel);

    }

    /**
     * 휴면계정 전환 처리를 한다
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    // @Scheduled(cron = "0 1 0 * * *")
    // @SuppressWarnings("restriction")
    // @Scheduled(fixedDelay = 3600000)
    // @Scheduled(fixedDelay = 30000)
    public void makeDormantAcct() throws Exception {

        Map<String, Object> paramMap = new HashMap();

        // log.debug("call ScheduleController :: makeDormantAcct()");

        List<?> lstMainDormantAcct = dormantAcctService.selectDormantAcctMainList(paramMap);

        // log.debug("lstMainDormantAcct = " + lstMainDormantAcct);

    }

}