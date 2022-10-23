package com.hisco.user.survey.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.ResponseUtil;
import com.hisco.intrfc.survey.service.SurveyService;
import com.hisco.intrfc.survey.vo.SurveyMstVO;
import com.hisco.intrfc.survey.vo.SurveyQstVO;
import com.hisco.intrfc.survey.vo.SurveyResultVO;
import com.hisco.intrfc.survey.vo.SurveyStdrmngVO;

import egovframework.com.cmm.LoginVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/survey")
public class SurveyController {

    /** logService */
    @Resource(name = "logService")
    private LogService logService;

    /** Validator */
    @Resource(name = "beanValidator")
    protected DefaultBeanValidator beanValidator;

    @Resource(name = "surveyService")
    private SurveyService surveyService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "codeService")
    private CodeService codeService;

    /**
     * 설문
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/index")
    public String index(@ModelAttribute("searchVO") SurveyMstVO searchVO, HttpServletRequest request,
            CommandMap commandMap,
            ModelMap model) throws Exception {

        model.addAttribute("webDir", Config.USER_ROOT);

        LoginVO user = commandMap.getUserInfo();
        model.addAttribute("user", user);

        // List<SurveyMstVO> surveyList = surveyService.selectThemaList(searchVO);
        List<SurveyMstVO> surveyList = surveyService.selectThemaDetailList(searchVO);

        int totCnt = 0;
        if (surveyList != null && !surveyList.isEmpty()) {
            totCnt = surveyList.get(0).getTotCnt();
        }

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("surveyList", surveyList);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/survey/index");
    }

    /**
     * 설문
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/form")
    public String form(@ModelAttribute("searchVO") SurveyMstVO searchVO,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        model.addAttribute("webDir", Config.USER_ROOT);

        SurveyMstVO surveyVO = null;
        if (searchVO.getQestnarId() > 0) {
            surveyVO = surveyService.selectThemaDetail(searchVO.getQestnarId());
        } else {
            model.addAttribute("msg", "잘못된 접근입니다.");
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/survey/empty");
        }

        if (surveyVO == null) {
            model.addAttribute("msg", "진행중인 설문이 없습니다.");
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/survey/empty");
        }

        // 운영조회
        SurveyStdrmngVO searchVO2 = new SurveyStdrmngVO();
        searchVO2.setQestnarId(surveyVO.getQestnarId());
        if (searchVO.getQestnarStdno() > 0) {
            searchVO2.setQestnarStdno(searchVO.getQestnarStdno());
        }
        SurveyStdrmngVO stdrmngVO = surveyService.selectExposeStandardDetail(searchVO2);

        if (stdrmngVO == null) {
            model.addAttribute("msg", "설문기간 정보가 없습니다.");
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/survey/empty");
        }

        // 상시운영
        if ("N".equals(stdrmngVO.getActiveYn())) {
            model.addAttribute("msg", "설문기간이 아닙니다.");
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/survey/empty");
        }

        LoginVO user = commandMap.getUserInfo();
        // 기본 체크 후 로그인 확인
        if (user == null) {
            HttpUtility.sendRedirect(request, response, "로그인 후 참여하실 수 있습니다.", Config.USER_ROOT + "/member/login?returnURL=" + request.getAttribute("returnURL"));
            return null;
        } else if (!user.isMember()) {
            HttpUtility.sendBack(request, response, "비회원은 참여 하실 수 없습니다. 회원 로그인 후 등록해 주세요.");
            return null;
        }

        SurveyResultVO checkVO = new SurveyResultVO();
        checkVO.setComcd(surveyVO.getComcd());
        checkVO.setQestnarId(surveyVO.getQestnarId());
        checkVO.setQestnarStdno(stdrmngVO.getQestnarStdno());
        checkVO.setQestnarMemno(user.getUniqId());

        int chk = surveyService.countSurveyResult(checkVO);
        if (chk > 0) {
            model.addAttribute("msg", "이미 설문에 참여하셨습니다.");
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/survey/empty");
        }

        // 질문조회
        SurveyQstVO searchQVO = new SurveyQstVO();
        searchQVO.setQestnarId(surveyVO.getQestnarId());
        List<SurveyQstVO> questionList = surveyService.selectQuestionList(searchQVO);

        // 질문항목조회설정
        if (questionList != null && !questionList.isEmpty()) {
            questionList.stream().forEach(q -> {
                q.setItemList(surveyService.selectQuestionItemList(q.getQestnarId(), q.getQestnsSeq()));
            });
        }
        model.addAttribute("survey", surveyVO);
        model.addAttribute("stdrmng", stdrmngVO);
        model.addAttribute("questionList", questionList);

        SurveyResultVO surveyResultVO = new SurveyResultVO();
        model.addAttribute("surveyResultVO", surveyResultVO);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/survey/form");
    }

    /**
     * 설문 완료
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/end")
    public String end(@ModelAttribute("searchVO") SurveyMstVO searchVO,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        model.addAttribute("webDir", Config.USER_ROOT);

        model.addAttribute("msg", "설문 참여가 완료 되었습니다.");
        return HttpUtility.getViewUrl(Config.USER_ROOT, "/survey/empty");
    }

    /**
     * 설문 등록한다.
     *
     * @param surveyResultVO
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    public String save(HttpServletResponse response, HttpServletRequest request,
            CommandMap commandMap, @ModelAttribute("surveyResultVO") SurveyResultVO surveyResultVO,
            BindingResult bindingResult, ModelMap model) throws Exception {

        LoginVO user = commandMap.getUserInfo();
        // 기본 체크 후 로그인 확인
        if (user == null) {
            HttpUtility.sendRedirect(request, response, "로그인 후 참여하실 수 있습니다.", Config.USER_ROOT + "/member/login");
            return null;
        } else if (!user.isMember()) {
            HttpUtility.sendBack(request, response, "비회원은 참여 하실 수 없습니다. 회원 로그인 후 등록해 주세요.");
            return null;
        }

        SurveyMstVO searchVO = new SurveyMstVO();
        searchVO.setQestnarId(surveyResultVO.getQestnarId());
        SurveyMstVO surveyVO = surveyService.selectThemaDetail(searchVO.getQestnarId());

        if (surveyVO == null) {
            HttpUtility.sendBack(request, response, "진행중인 설문이 없습니다.");
            return null;
        }

        // 운영조회
        SurveyStdrmngVO searchVO2 = new SurveyStdrmngVO();
        searchVO2.setQestnarId(surveyVO.getQestnarId());
        searchVO2.setQestnarStdno(surveyResultVO.getQestnarStdno());
        SurveyStdrmngVO stdrmngVO = surveyService.selectExposeStandardDetail(searchVO2);

        if (stdrmngVO == null) {
            HttpUtility.sendBack(request, response, "설문기간 정보가 없습니다.");
            return null;
        }

        SurveyResultVO checkVO = new SurveyResultVO();
        checkVO.setComcd(surveyVO.getComcd());
        checkVO.setQestnarId(surveyVO.getQestnarId());
        checkVO.setQestnarStdno(stdrmngVO.getQestnarStdno());
        checkVO.setQestnarMemno(user.getUniqId());

        int chk = surveyService.countSurveyResult(checkVO);
        if (chk > 0) {
            HttpUtility.sendBack(request, response, "이미 설문에 참여하셨습니다.");
            return null;
        }

        String resultMsg = "등록되었습니다.";
        try {
            // QESTNAR_THEMAMNG 테이블의 USE_YN 컬럼은 필터링 된다.
            // QESTNAR_STDRMNG 테이블의 USE_YN 컬럼은 상태값이 변경된다
            surveyResultVO.setComcd(surveyVO.getComcd());
            surveyService.answerSave(surveyResultVO, request, response, commandMap, model);
        } catch (Exception e) {
            resultMsg = "에러 발생. 저장되지 않았습니다.";
            e.printStackTrace();
            ResponseUtil.SendMessage(request, response, resultMsg, "history.back()");
            return null;
        }
        HttpUtility.sendRedirect(request, response, resultMsg, Config.USER_ROOT + "/survey/end");

        return null;

    }

    /**
     * 설문 등록한다.
     *
     * @param surveyResultVO
     * @return
     * @throws Exception
     */
    @PostMapping("/saveJson")
    public ModelAndView saveJson(HttpServletResponse response, HttpServletRequest request,
            CommandMap commandMap, @ModelAttribute("surveyResultVO") SurveyResultVO surveyResultVO,
            BindingResult bindingResult, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = commandMap.getUserInfo();

        ResultInfo resultInfo = null;

        // 기본 체크 후 로그인 확인
        if (user == null) {
            resultInfo = HttpUtility.getErrorResultInfo("로그인 후 참여하실 수 있습니다.");
            // HttpUtility.sendBack(request, response, "로그인 후 참여하실 수 있습니다.");
            // return null;
        } else if (!user.isMember()) {
            resultInfo = HttpUtility.getErrorResultInfo("로그인 후 참여하실 수 있습니다.");
            // HttpUtility.sendBack(request, response, "비회원은 참여 하실 수 없습니다. 회원 로그인 후 등록해 주세요.");
            // return null;
        } else {
            resultInfo = HttpUtility.getSuccessResultInfo("처리 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

}
