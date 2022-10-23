package com.hisco.user.evtrsvn.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.cmm.object.CmnCalendar;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.FileVO;
import com.hisco.user.evtrsvn.service.CalendarVO;
import com.hisco.user.evtrsvn.service.ComCtgrVO;
import com.hisco.user.evtrsvn.service.EventProgramVO;
import com.hisco.user.evtrsvn.service.EvtStdmngVO;
import com.hisco.user.evtrsvn.service.EvtrsvnSMainService;

import egovframework.com.cmm.LoginVO;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 강연/행사/영화 관련 컨트롤러
 * 
 * @author 김희택
 * @since 2020.08.26
 * @version 1.0, 2020.08.26
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          김희택 2020.08.26 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/evtrsvn")
public class EvtrsvnSController {

    @Resource(name = "evtrsvnSMainService")
    private EvtrsvnSMainService evtrsvnService;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    /**
     * 강연/행사/영화 메인
     *
     * @param EventProgramVO
     *            vo
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/evtrsvnList")
    public String evtList(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("eventProgramVO") EventProgramVO vo) throws Exception {

        log.debug("call evtList()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        String strEvtGbn = String.valueOf(paramMap.get("evtGbn"));
        log.debug("strEvtGbn = " + strEvtGbn);

        if (("null".equals(strEvtGbn)) || (strEvtGbn == null)) {
            strEvtGbn = "ALL";
        }

        ComCtgrVO ctgVO = new ComCtgrVO();

        model.addAttribute("cList", evtrsvnService.selectEvtCategList(ctgVO));
        model.addAttribute("fCtg", vo.getComPrnctgcd());
        model.addAttribute("sCtg", vo.getComCtgcd());
        model.addAttribute("sKeyword", vo.getEvtName());
        model.addAttribute("odr", vo.getOrderField());
        model.addAttribute("evtGbn", strEvtGbn);

        log.debug("final model = " + model);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 강연/행사/영화 메인
     *
     * @param EventProgramVO
     *            vo
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/evtrsvnListAjax")
    public String evtListAjax(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("eventProgramVO") EventProgramVO vo) throws Exception {

        log.debug("call evtListAjax()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        String strEvtGbn = String.valueOf(paramMap.get("evtGbn"));
        log.debug("strEvtGbn = " + strEvtGbn);

        if (("null".equals(strEvtGbn)) || (strEvtGbn == null)) {
            strEvtGbn = "ALL";
        }

        LoginVO userInfo = commandMap.getUserInfo();
        if (userInfo != null) {
            if ("Y".equals(userInfo.getSpecialYn())) {
                vo.setMemberType("S");
            } else if ("Y".equals(userInfo.getYearYn())) {
                vo.setMemberType("U");
            }
        }

        vo.setComcd(Config.COM_CD);
        vo.setEvtGbn(strEvtGbn);

        ComCtgrVO ctgVO = new ComCtgrVO();

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        log.debug("lastRecordIndex = {}", paginationInfo.getLastRecordIndex());
        vo.setPaginationInfo(paginationInfo);

        Map<String, Object> rMap = evtrsvnService.selectEvtProgList(vo);

        String listSize = rMap.get("size").toString();
        paginationInfo.setTotalRecordCount((Integer) rMap.get("size"));
        vo.setPaginationInfo(paginationInfo);

        model.addAttribute("cList", evtrsvnService.selectEvtCategList(ctgVO));
        model.addAttribute("fCtg", vo.getComPrnctgcd());
        model.addAttribute("sCtg", vo.getComCtgcd());
        model.addAttribute("sKeyword", vo.getEvtName());
        model.addAttribute("rList", rMap.get("list"));
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("listSize", listSize);
        model.addAttribute("odr", vo.getOrderField());

        if (!StringUtils.isEmpty(vo.getComPrnctgcd())) {
            ctgVO.setComCtglvl("1");
            ctgVO.setComPrnctgcd(vo.getComPrnctgcd());
            model.addAttribute("sList", evtrsvnService.selectEvtCategList(ctgVO));
        }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 강연/행사/영화 메인
     *
     * @param partCd
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/evtrsvnDetail")
    public String evtrsvnDetail(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            @ModelAttribute("eventProgramVO") EventProgramVO vo) throws Exception {
        LoginVO userInfo = commandMap.getUserInfo();
        if (userInfo != null) {
            if ("Y".equals(userInfo.getSpecialYn())) {
                vo.setMemberType("S");
            } else if ("Y".equals(userInfo.getYearYn())) {
                vo.setMemberType("U");
            }
        }

        CmnCalendar cal = new CmnCalendar();
        vo.setComcd(Config.COM_CD);

        EventProgramVO tmpVo = evtrsvnService.selectEvtPrgDetail(vo);
        Map<String, Object> calMap = cal.getCalendar(request, tmpVo.getEvtUseSdate());

        if (tmpVo != null) {
            String imgId = EgovStringUtil.isNullToString(tmpVo.getEvtIntrimgFinnb());
            String fileId = EgovStringUtil.isNullToString(tmpVo.getEvtPlanFinnb());
            FileVO fileVO = new FileVO();
            if (!imgId.equals("")) {
                fileVO.setFileGrpinnb(imgId);
                model.addAttribute("imgList", fileMngService.selectFileInfs(fileVO));
            }
            if (!fileId.equals("")) {
                fileVO.setFileGrpinnb(fileId);
                model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
            }
        }
        model.addAttribute("calMap", calMap);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("vo", tmpVo);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 강연/행사/영화 달력
     *
     * @param partCd
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/calendarAjax")
    public String selectCalendarAj(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        CmnCalendar cal = new CmnCalendar();

        java.util.Map<String, Object> calMap = cal.getCalendar(request, "");
        model.addAttribute("calMap", calMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 강연/행사/영화 회차 목록
     *
     * @param MainSearchVO
     * @param model
     * @return Map
     * @throws Exception
     */
    @GetMapping(value = "/evtShowPrgmAjax")
    public String selectTimeAj(CommandMap commandMap, HttpServletRequest request, ModelMap model, EvtStdmngVO vo)
            throws Exception {

        log.debug("call selectTimeAj()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        LoginVO userInfo = commandMap.getUserInfo();

        EventProgramVO prgVO = new EventProgramVO();

        prgVO.setEvtNo(Integer.parseInt(vo.getEvtNo()));
        prgVO.setComcd(Config.COM_CD);
        prgVO.setEvtTime(vo.getEvtTime());

        if (userInfo != null) {
            if ("Y".equals(userInfo.getSpecialYn())) {
                prgVO.setMemberType("S");
            } else if ("Y".equals(userInfo.getYearYn())) {
                prgVO.setMemberType("U");
            }
        }

        model.addAttribute("programVO", evtrsvnService.selectEvtPrgDetail(prgVO));

        commandMap.put("comcd", Config.COM_CD);
        String dayWeek = evtrsvnService.selectEvtWeek(commandMap.getParam());
        vo.setComcd(Config.COM_CD);
        vo.setDayWeek(dayWeek);

        model.addAttribute("timeList", evtrsvnService.selectEvtTimeDetailList(vo));
        model.addAttribute("dayWeek", dayWeek);

        log.debug("final model = " + model);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 예약가능 일정 체크
     * 
     * @param commandMap
     * @return ModelAndView - 결과
     * @exception Exception
     */
    @GetMapping(value = "/evtrsvnSelectAjax")
    @ResponseBody
    public ModelAndView selectEvtSchedList(CommandMap commandMap, HttpServletRequest request, ModelMap model,
            EvtStdmngVO vo) throws Exception {

        log.debug("call selectEvtSchedList()");

        Map<String, Object> paramMap = commandMap.getParam();

        log.debug("paramMap = " + paramMap);

        ModelAndView mav = new ModelAndView("jsonView");
        commandMap.put("comcd", Config.COM_CD);

        List<CalendarVO> sList = evtrsvnService.selectEvtRsvnList(commandMap.getParam());

        log.debug("sList = " + sList);

        mav.addObject("scheList", sList);
        mav.addObject("listSize", (sList != null ? sList.size() : 0));
        return mav;
    }

    /**
     * 카테고리 리스트 (사용보류)
     *
     * @param commandMap
     * @param SurveyVO
     * @return modelandview
     * @throws Exception
     */
    @PostMapping("/join/carDupliCheckAjax")
    @ResponseBody
    public ModelAndView checkCarInfo(CommandMap commandMap, ModelMap model, ComCtgrVO vo) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("cList", evtrsvnService.selectEvtCategList(vo));
        return mav;
    }

}
