package com.hisco.cmm.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.intrfc.showcounter.service.ShowCounterService;
import com.hisco.intrfc.survey.service.SurveyService;

import egovframework.com.sym.log.wlg.service.EgovWebLogService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/*
 * import egovframework.hanafi.info.sec.portal.web.cmmn.dao.CommonDao;
 * import egovframework.hanafi.info.sec.portal.web.cmmn.scheduledtasks.ScheduledTaskDAO;
 * import egovframework.hanafi.info.sec.portal.web.cmmn.scheduledtasks.TaskScheduler;
 * import egovframework.hanafi.info.sec.portal.web.cmmn.dao.ScheduleDao;
 * import egovframework.hanafi.info.sec.portal.web.cmmn.vo.AuthorVO;
 * import egovframework.rte.psl.dataaccess.util.EgovMap;
 */

/**
 * @title title
 * @packagename egovframework.hanafi.info.sec.portal.web.common.service.impl
 * @filename CommonServiceImpl.java
 * @author KDG
 * @since 2018. 10. 26.
 * @version 1.0
 * @see
 *
 *      <pre>
 * ========== Modification Information ==========
 *
 * DATE				  AUTHOR		      NOTE
 * ------------     ---------------     -----------------------------------
 * 2020. 01. 26.		  KDG			      최초 생성
 *      </pre>
 */
@Service("scheduleService")
public class ScheduleService extends EgovAbstractServiceImpl {

    @Resource(name = "EgovWebLogService")
    private EgovWebLogService egovWebLogService;

    @Resource(name = "showCounterService")
    private ShowCounterService showCounterService;

    @Resource(name = "surveyService")
    private SurveyService surveyService;

    /**
     * 웹 로그정보를 요약한다.
     * 전날의 로그를 요약하여 입력하고, 6개월전의 로그를 삭제한다.
     *
     * @param
     * @return
     * @throws Exception
     */
    public void webLogSummary() throws Exception {

        egovWebLogService.logInsertWebLogSummary();

    }

    /**
     * 계수기 정보 이동
     *
     * @param
     * @return
     * @throws Exception
     */
    public void showCounterMove(Map<String, Object> paramMap) throws Exception {

        showCounterService.showCounterMove(paramMap);

    }

    /**
     * 설문 대상 발송
     *
     * @param
     * @return
     * @throws Exception
     */
    public void sndSurveyToMem(Map<String, Object> paramMap) throws Exception {

        surveyService.sndSurveyToMem(paramMap);

    }

}
