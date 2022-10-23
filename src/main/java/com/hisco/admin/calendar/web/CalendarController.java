package com.hisco.admin.calendar.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hisco.admin.calendar.service.CalendarManageService;
import com.hisco.admin.calendar.vo.CalendarInfoVO;
import com.hisco.admin.log.service.LogService;
import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgInfoVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * 코드 관리
 *
 * @author 진수진
 * @since 2020.07.17
 * @version 1.0, 2020.07.17
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.17 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class CalendarController {

    @Resource(name = "calendarManageService")
    private CalendarManageService calendarManageService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    /** logService */
    @Resource(name = "logService")
    private LogService logService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    /**
     * 기준 월력을 조회
     *
     * @param CalendarVO
     * @param commandMap
     * @param model
     * @return String
     * @throws Exception
     */
    @PageActionInfo(title = "기준 월력 조회", action = "R")
    @GetMapping(value = "/calendar/monthList")
    public String monthList(@ModelAttribute("searchVO") CalendarInfoVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        String today = DateUtil.printDatetime(null, "yyyyMMdd"); // 오늘
        String ym = commandMap.getString("ym");
        if (CommonUtil.isEmpty(ym)) {
            ym = DateUtil.printDatetime(null, "yyyyMM"); // 년월
        }

        // 달력 배열, 시작일, 종료일, 이전달, 다음달 정보를 일괄로 가져옴
        HashMap data = DateUtil.calendarGroup(ym, "ko");

        searchVO.setComcd(Config.COM_CD);
        searchVO.setSearchStartDts((String) data.get("startCalendarDd"));
        searchVO.setSearchEndDts((String) data.get("endCalendarDd"));
        List<?> list = calendarManageService.selectCalendarList(searchVO);

        if (list != null && list.size() > 0) {

            ArrayList cal = (ArrayList) data.get("cal");
            for (int i = 0; i < cal.size(); i++) {

                ArrayList week = (ArrayList) cal.get(i);
                for (int j = 0; j < week.size(); j++) {
                    HashMap day = (HashMap) week.get(j);

                    ArrayList schedule = new ArrayList();
                    for (int k = 0; k < list.size(); k++) {
                        CalendarInfoVO c = (CalendarInfoVO) list.get(k);
                        if (c.getCalDate().equals((String) day.get("day"))) {
                            schedule.add(c);
                        }
                    }
                    day.put("schedule", schedule);
                }
            }
        }

        model.addAttribute("today", today);
        model.addAttribute("ym", ym);
        model.addAttribute("prev", data.get("prev"));
        model.addAttribute("next", data.get("next"));
        model.addAttribute("cal", data.get("cal"));
        model.addAttribute("weekNames", data.get("weekNames"));
        // 월력휴일구분
        model.addAttribute("dateTypeList", codeService.selectCodeList("SM_CALENDAR_DATETYPE"));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 기준 월력 등록을 위한 등록페이지로 이동한다.
     *
     * @param CalendarInfoVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "기준 월력 등록/수정", action = "R")
    @GetMapping("/calendar/monthListFromAjax")
    public String monthListFromAjax(@ModelAttribute("calendarInfoVO") CalendarInfoVO calendarInfoVO,
            CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        calendarInfoVO.setComcd(Config.COM_CD);

        // 수정일경우 불러오기
        if (!commandMap.getString("MODE").equals("INSERT")) {
            CalendarInfoVO thisCalendarInfoVO = new CalendarInfoVO();
            thisCalendarInfoVO = calendarManageService.selectCalendarDetail(calendarInfoVO);
            model.addAttribute("calendarInfoVO", thisCalendarInfoVO);
            model.addAttribute("delYn", "Y");
        } else {
            model.addAttribute("calendarInfoVO", calendarInfoVO);
            model.addAttribute("delYn", "N");
        }

        // 월력일정구분
        model.addAttribute("gubunList", codeService.selectCodeList("SM_SCHEDULE_GBN"));

        // 월력휴일구분
        model.addAttribute("dateTypeList", codeService.selectCodeList("SM_CALENDAR_DATETYPE"));

        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 기준 월력 등록
     *
     * @param CalendarInfoVO
     * @param commandMap
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PostMapping("/calendar/monthListFormSave")
    @ResponseBody
    public ModelAndView monthListFormSave(@ModelAttribute("calendarInfoVO") CalendarInfoVO calendarInfoVO,
            CommandMap commandMap, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        calendarInfoVO.setReguser((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
        calendarInfoVO.setComcd(Config.COM_CD);
        calendarInfoVO.setOrgNo(0);

        if (commandMap.getString("MODE").equals("INSERT")) {
            if (logService.checkAdminLog(commandMap, "C", "기준 월력 등록")) {
                CalendarInfoVO vo = calendarManageService.selectCalendarDetail(calendarInfoVO);
                if (vo != null) {
                    // 중복 에러
                    resultInfo = HttpUtility.getErrorResultInfo("중복되는 데이터가 있습니다.");
                } else {
                    calendarManageService.insertCalendarDetail(calendarInfoVO);
                    resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");
                }
            } else {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            }

        } else {
            // 수정
            if (!logService.checkAdminLog(commandMap, "U", "기준 월력 수정")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else {
                calendarManageService.updateCalendarDetail(calendarInfoVO);
                resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
            }
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 기준 월력 삭제
     *
     * @param CalendarInfoVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/calendar/monthListFormDelete")
    @ResponseBody
    public ModelAndView monthListFormDelete(@ModelAttribute("calendarInfoVO") CalendarInfoVO calendarInfoVO,
            CommandMap commandMap, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        calendarInfoVO.setComcd(Config.COM_CD);
        calendarInfoVO.setOrgNo(0);

        // 삭제
        if (!logService.checkAdminLog(commandMap, "D", "공통상세코드 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            calendarManageService.deleteCalendarDetail(calendarInfoVO);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 월력 일괄 삭제
     *
     * @param CalendarInfoVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/calendar/monthListFormDeleteAll")
    @ResponseBody
    public ModelAndView monthListFormDeleteAll(
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        // 삭제
        if (!logService.checkAdminLog(commandMap, "D", "공통상세코드 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            calendarManageService.deleteOrgCalendarDetailAll(commandMap, request, model);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 기관별 월력 일괄 등록
     *
     * @param CalendarInfoVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/calendar/monthListGroupSave")
    @ResponseBody
    public ModelAndView monthListGroupSave(@ModelAttribute("calendarInfoVO") CalendarInfoVO calendarInfoVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        // 삭제
        if (!logService.checkAdminLog(commandMap, "C", "월력 일괄 등록")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            calendarManageService.groupComCalendarDetailAll(calendarInfoVO, commandMap, request, model);
            resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 기관별 월력을 조회
     *
     * @param CalendarVO
     * @param commandMap
     * @param model
     * @return String
     * @throws Exception
     */
    @PageActionInfo(title = "기관별 월력 조회", action = "R")
    @GetMapping(value = "/calendar/orgMonthList")
    public String orgMonthList(@ModelAttribute("searchVO") CalendarInfoVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {


        String today = DateUtil.printDatetime(null, "yyyyMMdd"); // 오늘
        String ym = commandMap.getString("ym");
        if (CommonUtil.isEmpty(ym)) {
            ym = DateUtil.printDatetime(null, "yyyyMM"); // 년월
        }

        // 달력 배열, 시작일, 종료일, 이전달, 다음달 정보를 일괄로 가져옴
        HashMap data = DateUtil.calendarGroup(ym, "ko");

        searchVO.setComcd(Config.COM_CD);
        //  orgNo가 없으면 첫번째 기관으로
        UserSession userSession = (UserSession) request.getSession().getAttribute(Config.USER_SESSION);
        System.out.println("userSession.getMyOrgList():" + userSession.getMyOrgList());

        if (userSession.getMyOrgList() != null && userSession.getMyOrgList().size() > 0 &&searchVO.getOrgNo() == 0) {

        	 System.out.println("여기는------------------------userSession.getMyOrgList():" + userSession.getMyOrgList());
            searchVO.setOrgNo( Integer.parseInt(userSession.getMyOrgList().get(0)));
            System.out.println("===========searchVO:" + searchVO.getOrgNo());
        }

        searchVO.setSearchStartDts((String) data.get("startCalendarDd"));
        searchVO.setSearchEndDts((String) data.get("endCalendarDd"));
        List<?> list = calendarManageService.selectOrgCalendarList(searchVO);

        if (list != null && list.size() > 0) {

            ArrayList cal = (ArrayList) data.get("cal");
            for (int i = 0; i < cal.size(); i++) {
                ArrayList week = (ArrayList) cal.get(i);
                for (int j = 0; j < week.size(); j++) {
                    HashMap day = (HashMap) week.get(j);

                    ArrayList schedule = new ArrayList();
                    for (int k = 0; k < list.size(); k++) {
                        CalendarInfoVO c = (CalendarInfoVO) list.get(k);
                        if (c.getCalDate().equals((String) day.get("day"))) {
                            schedule.add(c);
                        }
                    }
                    day.put("schedule", schedule);
                }
            }
        }

        model.addAttribute("today", today);
        model.addAttribute("ym", ym);
        model.addAttribute("prev", data.get("prev"));
        model.addAttribute("next", data.get("next"));
        model.addAttribute("cal", data.get("cal"));
        model.addAttribute("weekNames", data.get("weekNames"));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 기관별 월력 등록을 위한 등록페이지로 이동한다.
     *
     * @param CalendarInfoVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "기관별 월력 등록/수정", action = "R")
    @GetMapping("/calendar/orgMonthListFromAjax")
    public String orgMonthListFromAjax(@ModelAttribute("calendarInfoVO") CalendarInfoVO calendarInfoVO,
            CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        calendarInfoVO.setComcd(Config.COM_CD);

        // 수정일경우 불러오기
        if (!commandMap.getString("MODE").equals("INSERT")) {
            CalendarInfoVO thisCalendarInfoVO = new CalendarInfoVO();
            thisCalendarInfoVO = calendarManageService.selectOrgCalendarDetail(calendarInfoVO);
            model.addAttribute("calendarInfoVO", thisCalendarInfoVO);
            model.addAttribute("delYn", "Y");
        } else {
            model.addAttribute("calendarInfoVO", calendarInfoVO);
            model.addAttribute("delYn", "N");
        }

        // 내용 셀렉트
        OrgInfoVO orgInfoVO = new OrgInfoVO();
        orgInfoVO.setComcd(Config.COM_CD);
        orgInfoVO.setLevel("1");
        orgInfoVO.setOrgNo(calendarInfoVO.getOrgNo());
        orgInfoVO = orgInfoService.selectOrgInfoDetail(orgInfoVO);
        model.addAttribute("orgInfoVO", orgInfoVO);

        // 월력일정구분
        model.addAttribute("gubunList", codeService.selectCodeList("SM_SCHEDULE_GBN"));

        // 월력휴일구분
        model.addAttribute("dateTypeList", codeService.selectCodeList("SM_CALENDAR_DATETYPE"));

        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 기관별 월력 등록
     *
     * @param CalendarInfoVO
     * @param commandMap
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PostMapping("/calendar/orgMonthListFormSave")
    @ResponseBody
    public ModelAndView orgMonthListFormSave(@ModelAttribute("calendarInfoVO") CalendarInfoVO calendarInfoVO,
            CommandMap commandMap, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        calendarInfoVO.setReguser((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
        calendarInfoVO.setComcd(Config.COM_CD);

        if (commandMap.getString("MODE").equals("INSERT")) {
            if (logService.checkAdminLog(commandMap, "C", "기관별 월력 등록")) {
                CalendarInfoVO vo = calendarManageService.selectOrgCalendarDetail(calendarInfoVO);
                if (vo != null) {
                    // 중복 에러
                    resultInfo = HttpUtility.getErrorResultInfo("중복되는 데이터가 있습니다.");
                } else {
                    calendarManageService.insertOrgCalendarDetail(calendarInfoVO);
                    resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");
                }
            } else {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            }

        } else {
            // 수정
            if (!logService.checkAdminLog(commandMap, "U", "기관별 월력 수정")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else {
                calendarManageService.updateOrgCalendarDetail(calendarInfoVO);
                resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
            }
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 기관별 월력 삭제
     *
     * @param CalendarInfoVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/calendar/orgMonthListFormDelete")
    @ResponseBody
    public ModelAndView orgMonthListFormDelete(@ModelAttribute("calendarInfoVO") CalendarInfoVO calendarInfoVO,
            CommandMap commandMap, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        calendarInfoVO.setComcd(Config.COM_CD);

        // 삭제
        if (!logService.checkAdminLog(commandMap, "D", "공통상세코드 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            calendarManageService.deleteOrgCalendarDetail(calendarInfoVO);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 기관별 월력 일괄 삭제
     *
     * @param CalendarInfoVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/calendar/orgMonthListFormDeleteAll")
    @ResponseBody
    public ModelAndView orgMonthListFormDeleteAll(
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        // 삭제
        if (!logService.checkAdminLog(commandMap, "D", "공통상세코드 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            calendarManageService.deleteOrgCalendarDetailAll(commandMap, request, model);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 기관별 월력, 기준월력 불러오기 팝업
     *
     * @param CalendarInfoVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "기관별 월력 복사", action = "R")
    @GetMapping("/calendar/orgMonthListCopyAjax")
    public String orgMonthListCopyAjax(
            CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        CalendarInfoVO calendarInfoVO = new CalendarInfoVO();

        calendarInfoVO.setComcd(Config.COM_CD);
        List<?> yearList = calendarManageService.selectCalendarYearGroup(calendarInfoVO);

        String today = DateUtil.printDatetime(null, "yyyy"); // 오늘

        // 내용 셀렉트
        OrgInfoVO orgInfoVO = new OrgInfoVO();
        orgInfoVO.setComcd(Config.COM_CD);
        orgInfoVO.setLevel("1");
        orgInfoVO.setOrgNo(StringUtil.String2Int(commandMap.getString("orgNo"), 0));
        orgInfoVO = orgInfoService.selectOrgInfoDetail(orgInfoVO);
        model.addAttribute("orgInfoVO", orgInfoVO);

        model.addAttribute("yearList", yearList);
        model.addAttribute("today", today);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 기관별 월력 일괄 복사
     *
     * @param CalendarInfoVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/calendar/orgMonthListCopySave")
    @ResponseBody
    public ModelAndView orgMonthListCopySave(
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        // 삭제
        if (!logService.checkAdminLog(commandMap, "C", "기관별 월력 복사")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            calendarManageService.copyCalendarDetailAll(commandMap, request, model);
            resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 기관별 월력 일괄 등록을 위한 등록페이지로 이동한다.
     *
     * @param CalendarInfoVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "기관별 월력 일괄 등록", action = "R")
    @GetMapping("/calendar/orgMonthListGroupAjax")
    public String orgMonthListGroupAjax(@ModelAttribute("groupVO") CalendarInfoVO groupVO,
            CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        groupVO.setComcd(Config.COM_CD);
        model.addAttribute("calendarInfoVO", groupVO);

        // 내용 셀렉트
        OrgInfoVO orgInfoVO = new OrgInfoVO();
        orgInfoVO.setComcd(Config.COM_CD);
        orgInfoVO.setLevel("1");
        orgInfoVO.setOrgNo(groupVO.getOrgNo());
        orgInfoVO = orgInfoService.selectOrgInfoDetail(orgInfoVO);

        ArrayList weekNames = new ArrayList();
        for (int i = 1; i <= 7; i++) {
            weekNames.add(DateUtil.printWeek(i, "kr"));
        }
        model.addAttribute("weekNames", weekNames);

        model.addAttribute("orgInfoVO", orgInfoVO);

        // 월력일정구분
        model.addAttribute("gubunList", codeService.selectCodeList("SM_SCHEDULE_GBN"));

        // 월력휴일구분
        model.addAttribute("dateTypeList", codeService.selectCodeList("SM_CALENDAR_DATETYPE"));

        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 기관별 월력 일괄 등록
     *
     * @param CalendarInfoVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/calendar/orgMonthListGroupSave")
    @ResponseBody
    public ModelAndView orgMonthListGroupSave(@ModelAttribute("calendarInfoVO") CalendarInfoVO calendarInfoVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        // 삭제
        if (!logService.checkAdminLog(commandMap, "C", "기관별 월력 일괄 등록")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            calendarManageService.groupCalendarDetailAll(calendarInfoVO, commandMap, request, model);
            resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }
}
