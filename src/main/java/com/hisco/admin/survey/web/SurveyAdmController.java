package com.hisco.admin.survey.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.annotation.PageActionType;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.ResponseUtil;
import com.hisco.cmm.util.SessionUtil;
import com.hisco.cmm.vo.CodeVO;
import com.hisco.cmm.vo.FileVO;
import com.hisco.intrfc.survey.service.SurveyService;
import com.hisco.intrfc.survey.vo.SurveyMstVO;
import com.hisco.intrfc.survey.vo.SurveyQstVO;
import com.hisco.intrfc.survey.vo.SurveyResultVO;
import com.hisco.intrfc.survey.vo.SurveyStdrmngVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
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
@RequestMapping(value = { "#{dynamicConfig.adminRoot}/survey", "#{dynamicConfig.managerRoot}/survey" })
public class SurveyAdmController {

    /** logService */
    @Resource(name = "logService")
    private LogService logService;

    /** Validator */
    @Resource(name = "beanValidator")
    protected DefaultBeanValidator beanValidator;

    @Resource(name = "surveyService")
    private SurveyService surveyService;

    @Value("#{dynamicConfig.adminRoot}")
    private String ADMIN_ROOT;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "codeService")
    private CodeService codeService;

    /**
     * 설문주제목록
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "설문주제 목록 조회", action = PageActionType.READ)
    @GetMapping(value = "/themaList")
    public String themaList(@ModelAttribute("searchVO") SurveyMstVO searchVO, HttpServletRequest request,
            CommandMap commandMap,
            ModelMap model) throws Exception {

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

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 설문주제추가/수정
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "설문주제 등록", action = "C")
    @GetMapping(value = { "/themaAdd", "/themaEdit" })
    public String themaForm(@ModelAttribute("themaVO") SurveyMstVO themaVO,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        // 테마조회
        if (themaVO.getQestnarId() > 0) { // editThema
            themaVO = surveyService.selectThemaDetail(themaVO.getQestnarId());
            if (themaVO == null) {
                ResponseUtil.SendMessage(request, response, "잘못된 접근 입니다.", "history.back()");
                return null;
            }
        }

        // 수정 , 저장 가능 여부
        String possible = "Y";

        List<SurveyQstVO> questionList = null;
        if (themaVO.getQestnarId() > 0) { // editThema
            // 질문조회
            SurveyQstVO searchVO = new SurveyQstVO();
            searchVO.setQestnarId(themaVO.getQestnarId());
            questionList = surveyService.selectQuestionList(searchVO);

            // 질문항목조회설정
            if (questionList != null && !questionList.isEmpty()) {
                questionList.stream().forEach(q -> {
                    q.setItemList(surveyService.selectQuestionItemList(q.getQestnarId(), q.getQestnsSeq()));
                });
            }
            model.addAttribute("questionList", questionList);

            // 운영조회
            SurveyStdrmngVO searchVO2 = new SurveyStdrmngVO();
            searchVO2.setQestnarId(themaVO.getQestnarId());
            SurveyStdrmngVO stdrmngVO = surveyService.selectExposeStandardDetail(searchVO2);
            if (stdrmngVO == null) {
                stdrmngVO = new SurveyStdrmngVO();
            }
            themaVO.setStdrmng(stdrmngVO);

            // 참여자 있는지 확인
            SurveyResultVO checkVO = new SurveyResultVO();
            checkVO.setComcd(stdrmngVO.getComcd());
            checkVO.setQestnarId(stdrmngVO.getQestnarId());
            checkVO.setQestnarStdno(stdrmngVO.getQestnarStdno());
            int chk = surveyService.countSurveyResult(checkVO);
            if (chk > 0) {
                possible = "N";
            }
        }
        themaVO.setQuestionList(questionList);
        model.addAttribute("themaVO", themaVO);
        Gson gson = new GsonBuilder().create();
        model.addAttribute("themaVOJson", gson.toJson(themaVO));

        // 질문유형 공통코드 조회
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        List<CodeVO> questionTypeList = codeService.selectCodeList(Constant.SM_QESTNAR_TYPE);
        model.addAttribute("questionTypeList", questionTypeList);
        model.addAttribute("questionTypeListJson", gson.toJson(questionTypeList));

        model.addAttribute("commandMap", commandMap);
        model.addAttribute("possible", possible);

        // (시)
        model.addAttribute("qestnarOpenTimeHH", DateUtil.getTimeHH());
        // (분)
        model.addAttribute("qestnarOpenTimeMM", DateUtil.getTimeMM());

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/survey/themaForm");
    }

    @PostMapping(value = { "/themaSave" })
    public String themaSave(final MultipartHttpServletRequest multiRequest,
            @ModelAttribute("surveyMstVO") SurveyMstVO surveyMstVO,
            BindingResult bindingResult,
            CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        // 수정모드
        if (surveyMstVO.getQestnarId() > 0) {
            if (!logService.checkAdminLog(commandMap, "U", "설문주제 수정")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }

            SurveyStdrmngVO searchVO2 = new SurveyStdrmngVO();
            searchVO2.setQestnarId(surveyMstVO.getQestnarId());
            SurveyStdrmngVO stdrmngVO = surveyService.selectExposeStandardDetail(searchVO2);

            SurveyResultVO checkVO = new SurveyResultVO();
            checkVO.setComcd(stdrmngVO.getComcd());
            checkVO.setQestnarId(stdrmngVO.getQestnarId());
            checkVO.setQestnarStdno(stdrmngVO.getQestnarStdno());
            int chk = surveyService.countSurveyResult(checkVO);
            if (chk > 0) {
                ResponseUtil.SendMessage(request, response, "설문 참여자있어 수정이 불가능 합니다.", "history.back()");
                return null;
            }
        } else {
            if (!logService.checkAdminLog(commandMap, "C", "설문주제 등록")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        }

        List<FileVO> fileList = null;
        String uploadFolder = "";
        final Map<String, MultipartFile> files = multiRequest.getFileMap();
        if (files != null && !files.isEmpty()) {
            try {

                fileList = fileUtil.parseFileInf(files, "SURVEY_", 0, "", uploadFolder, SessionUtil.getLoginId(), "file_1");
                if (fileList != null && !fileList.isEmpty()) {
                    if (StringUtils.isNotBlank(surveyMstVO.getQestnarImgfilnb())) {
                        // 기존 파일 삭제
                        fileMngService.deleteFileInfs(fileList);
                    }
                    String qestnarImgfilnb = fileMngService.insertFileInfs(fileList);
                    surveyMstVO.setQestnarImgfilnb(qestnarImgfilnb);
                }
            } catch (Exception e) {
                log.error(e.toString());
            }
        }

        String resultMsg = "저장되었습니다.";
        try {
            // QESTNAR_THEMAMNG 테이블의 USE_YN 컬럼은 필터링 된다.
            // QESTNAR_STDRMNG 테이블의 USE_YN 컬럼은 상태값이 변경된다
            surveyService.masterSave(surveyMstVO, request, response, commandMap, model);
        } catch (Exception e) {
            resultMsg = "에러 발생. 저장되지 않았습니다.";
            e.printStackTrace();
            ResponseUtil.SendMessage(request, response, resultMsg, "history.back()");
            return null;
        }

        HttpUtility.sendRedirect(multiRequest, response, resultMsg, Config.ADMIN_ROOT + "/survey/themaList" + commandMap.getString("searchQuery"));
        return null;
    }

    /**
     * 공개여부, 삭제 수정
     */
    @PostMapping(value = { "/themaStdrmngFieldUpdate" })
    public ModelAndView themaStdrmngFieldUpdate(
            @ModelAttribute("surveyMstVO") SurveyMstVO surveyMstVO,
            BindingResult bindingResult,
            CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        // 수정
        if (!logService.checkAdminLog(commandMap, "U", "상태변경")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            surveyService.stdrmngFieldUpdate(surveyMstVO);
            resultInfo = HttpUtility.getSuccessResultInfo("처리 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 설문 결과
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "설문 결과", action = "R")
    @GetMapping(value = "/result")
    public String result(@ModelAttribute("themaVO") SurveyMstVO themaVO,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        // 테마조회
        if (themaVO.getQestnarId() > 0) { // editThema
            themaVO = surveyService.selectThemaDetail(themaVO.getQestnarId());
        }
        if (themaVO == null) {
            ResponseUtil.SendMessage(request, response, "잘못된 접근 입니다.", "history.back()");
            return null;
        }

        // 수정 , 저장 가능 여부
        String possible = "Y";

        List<SurveyQstVO> questionList = null;
        if (themaVO.getQestnarId() > 0) { // editThema

            // 운영조회
            SurveyStdrmngVO searchVO2 = new SurveyStdrmngVO();
            searchVO2.setQestnarId(themaVO.getQestnarId());
            SurveyStdrmngVO stdrmngVO = surveyService.selectExposeStandardDetail(searchVO2);
            if (stdrmngVO == null) {
                stdrmngVO = new SurveyStdrmngVO();
            }
            themaVO.setStdrmng(stdrmngVO);

            // 참여자 있는지 확인
            SurveyResultVO checkVO = new SurveyResultVO();
            checkVO.setComcd(stdrmngVO.getComcd());
            checkVO.setQestnarId(stdrmngVO.getQestnarId());
            checkVO.setQestnarStdno(stdrmngVO.getQestnarStdno());
            int total = surveyService.countSurveyResult(checkVO);

            // 참여자 조회
            List<SurveyResultVO> resultList = surveyService.selectSurveyResultList(checkVO);
            if (resultList != null && !resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    SurveyResultVO r = resultList.get(i);
                    r.setDtlList(surveyService.selectSurveyResultDetailList(r));
                }
            }
            model.addAttribute("resultList", resultList);

            // 질문조회
            SurveyQstVO searchVO = new SurveyQstVO();
            searchVO.setQestnarId(themaVO.getQestnarId());
            questionList = surveyService.selectQuestionList(searchVO);

            // 질문항목조회설정
            if (questionList != null && !questionList.isEmpty()) {
                for (int i = 0; i < questionList.size(); i++) {
                    SurveyQstVO q = questionList.get(i);
                    q.setItemList(surveyService.selectQuestionItemList(q.getQestnarId(), q.getQestnsSeq(), stdrmngVO.getQestnarStdno(), total));
                }
            }
            model.addAttribute("questionList", questionList);

        }
        themaVO.setQuestionList(questionList);
        model.addAttribute("themaVO", themaVO);

        // 질문유형 공통코드 조회
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        List<CodeVO> questionTypeList = codeService.selectCodeList(Constant.SM_QESTNAR_TYPE);
        model.addAttribute("questionTypeList", questionTypeList);
        model.addAttribute("questionTypeListJson", gson.toJson(questionTypeList));

        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/survey/result");
    }

    /**
     * 설문지 > 설문 및 설문항목 관리
     */
    @GetMapping(value = "/editQuestion/{qestnarId}")
    public String editQuestion(@PathVariable("qestnarId") int qestnarId, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        model.addAttribute("commandMap", commandMap);
        model.addAttribute("qestnarId", qestnarId);

        // 테마조회
        SurveyMstVO themaVO = surveyService.selectThemaDetail(qestnarId);
        if (themaVO == null) {
            HttpUtility.sendRedirect(request, response, "설문주제가 존재하지 않습니다.", Config.ADMIN_ROOT + "/survey/themaList");
            return null;
        }
        model.addAttribute("themaVO", themaVO);

        // 질문조회
        SurveyQstVO searchVO = new SurveyQstVO();
        searchVO.setQestnarId(qestnarId);
        List<SurveyQstVO> questionList = surveyService.selectQuestionList(searchVO);

        // 질문항목조회설정
        if (questionList != null && !questionList.isEmpty()) {
            questionList.stream().forEach(q -> {
                q.setItemList(surveyService.selectQuestionItemList(q.getQestnarId(), q.getQestnsSeq()));
            });
            model.addAttribute("questionVO", questionList.get(0));
        }

        // 질문유형 공통코드 조회
        Gson gson = new GsonBuilder().create();
        model.addAttribute("questionList", gson.toJson(questionList));

        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        model.addAttribute("questionTypeList", gson.toJson(codeService.selectCodeList(Constant.SM_QESTNAR_TYPE)));

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/survey/editQuestion");
    }

    @PostMapping(value = "/saveQuestion.json")
    public String saveQuestion(HttpServletRequest request, ModelMap model, @RequestBody List<SurveyQstVO> questionList)
            throws Exception {

        questionList.stream().forEach(qst -> {
            log.debug("{}", qst);
            if (qst.getItemList() != null) {
                qst.getItemList().stream().forEach(item -> {
                    log.debug("{}", item);
                });
            }
        });

        model.addAttribute("result", Config.FAIL);
        if (questionList != null && !questionList.isEmpty()) {
            surveyService.saveQuestion(questionList);
            model.addAttribute("result", Config.SUCCESS);
        }

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/survey/editQuestion");
    }

    @PageActionInfo(title = "설문노출기준 목록 조회", action = PageActionType.READ)
    @GetMapping(value = "/expStandardList")
    public String expStandardList(@ModelAttribute("searchVO") SurveyStdrmngVO searchVO,
            HttpServletRequest request,
            CommandMap commandMap,
            ModelMap model) throws Exception {

        if (Constant.SM_QESTNAR_SRCH_COND_CD_주제명.equals(searchVO.getSearchCondition())) {
            if (StringUtils.isNotBlank(searchVO.getSearchKeyword())) {
                // searchVO.setQestnarName(searchVO.getSearchKeyword());
            }
        }
        List<SurveyStdrmngVO> expStdList = surveyService.selectExposeStandardList(searchVO);

        int totCnt = 0;
        if (expStdList != null && !expStdList.isEmpty()) {
            totCnt = expStdList.get(0).getTotCnt();
        }

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("expStdList", expStdList);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 설문노출기준 추가/수정
     */
    // @PageActionInfo(title = "설문주제 등록", action = "C")
    @GetMapping(value = { "/addExpStandard", "/editExpStandard" })
    public String addExpStandard(@ModelAttribute("surveyMstVO") SurveyMstVO surveyMstVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        if (surveyMstVO.getQestnarId() > 0) { // editThema
            surveyMstVO = surveyService.selectThemaDetail(surveyMstVO.getQestnarId());
        }

        model.addAttribute("commandMap", commandMap);
        model.addAttribute("surveyMstVO", surveyMstVO);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/survey/addThema");
    }

    /**
     * 설문노출기준 저장
     */
    @PostMapping(value = { "/saveExpStanard" })
    public String saveExpStanard(final MultipartHttpServletRequest multiRequest,
            @ModelAttribute("surveyMstVO") SurveyMstVO surveyMstVO,
            BindingResult bindingResult,
            CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        // 서버측 검증
        beanValidator.validate(surveyMstVO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("surveyMstVO", surveyMstVO);
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/survey/addThema");
        }

        List<FileVO> fileList = null;
        String uploadFolder = "";

        final Map<String, MultipartFile> files = multiRequest.getFileMap();
        if (files != null && !files.isEmpty()) {
            try {
                fileList = fileUtil.parseFileInf(files, "SURVEY_", 0, "", uploadFolder, SessionUtil.getLoginId(), "file_1");
                if (fileList != null && !fileList.isEmpty()) {
                    if (StringUtils.isNotBlank(surveyMstVO.getQestnarImgfilnb())) {
                        // 기존 파일 삭제
                        fileMngService.deleteAndInsert(fileList.get(0), fileList);
                    } else {
                        String qestnarImgfilnb = fileMngService.insertFileInfs(fileList);
                        surveyMstVO.setQestnarImgfilnb(qestnarImgfilnb);
                    }
                }
            } catch (Exception e) {
                log.error(e.toString());
            }
        }

        if (surveyMstVO.getQestnarId() < 1) {
            surveyService.insertThema(surveyMstVO);
        } else {
            surveyService.updateThema(surveyMstVO);
        }

        HttpUtility.sendRedirect(multiRequest, response, "처리되었습니다.", Config.ADMIN_ROOT + "/survey/themaList" + commandMap.getString("searchQuery"));
        return null;
    }

    @PageActionInfo(title = "설문조사(강의평가형)통계현황", action = PageActionType.READ)
    @GetMapping(value = "/statEdcSurvey")
    public String statEdcSurvey(@ModelAttribute("searchVO") SurveyMstVO searchVO, HttpServletRequest request,
            CommandMap commandMap,
            ModelMap model) throws Exception {

        if (Constant.SM_QESTNAR_SRCH_COND_CD_주제명.equals(searchVO.getSearchCondition())) {
            if (StringUtils.isNotBlank(searchVO.getSearchKeyword())) {
                searchVO.setQestnarName(searchVO.getSearchKeyword());
            }
        }
        List<SurveyMstVO> surveyList = surveyService.selectThemaList(searchVO);

        int totCnt = 0;
        if (surveyList != null && !surveyList.isEmpty()) {
            totCnt = surveyList.get(0).getTotCnt();
        }

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("surveyList", surveyList);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /////////////////////////////// 백업 ////////////////////////////////////////

    /**
     * 설문주제목록
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "설문주제 목록 조회", action = PageActionType.READ)
    @GetMapping(value = "/themaListBack")
    public String themaListBack(@ModelAttribute("searchVO") SurveyMstVO searchVO, HttpServletRequest request,
            CommandMap commandMap,
            ModelMap model) throws Exception {

        if (Constant.SM_QESTNAR_SRCH_COND_CD_주제명.equals(searchVO.getSearchCondition())) {
            if (StringUtils.isNotBlank(searchVO.getSearchKeyword())) {
                searchVO.setQestnarName(searchVO.getSearchKeyword());
            }
        }
        List<SurveyMstVO> surveyList = surveyService.selectThemaList(searchVO);

        int totCnt = 0;
        if (surveyList != null && !surveyList.isEmpty()) {
            totCnt = surveyList.get(0).getTotCnt();
        }

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("surveyList", surveyList);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 설문주제추가
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    // @PageActionInfo(title = "설문주제 등록", action = "C")
    @GetMapping(value = { "/addThema", "/editThema" })
    public String addThema(@ModelAttribute("surveyMstVO") SurveyMstVO surveyMstVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        if (surveyMstVO.getQestnarId() > 0) { // editThema
            surveyMstVO = surveyService.selectThemaDetail(surveyMstVO.getQestnarId());
        }

        model.addAttribute("commandMap", commandMap);
        model.addAttribute("surveyMstVO", surveyMstVO);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/survey/addThema");
    }

    @PostMapping(value = { "/saveThemaOld" })
    public String saveThemaOld(final MultipartHttpServletRequest multiRequest,
            @ModelAttribute("surveyMstVO") SurveyMstVO surveyMstVO,
            BindingResult bindingResult,
            CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        // 서버측 검증
        beanValidator.validate(surveyMstVO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("surveyMstVO", surveyMstVO);
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/survey/addThema");
        }

        List<FileVO> fileList = null;
        String uploadFolder = "";

        final Map<String, MultipartFile> files = multiRequest.getFileMap();
        if (files != null && !files.isEmpty()) {
            try {
                fileList = fileUtil.parseFileInf(files, "SURVEY_", 0, "", uploadFolder, SessionUtil.getLoginId(), "file_1");
                if (fileList != null && !fileList.isEmpty()) {
                    if (StringUtils.isNotBlank(surveyMstVO.getQestnarImgfilnb())) {
                        // 기존 파일 삭제
                        fileMngService.deleteAndInsert(fileList.get(0), fileList);
                    } else {
                        String qestnarImgfilnb = fileMngService.insertFileInfs(fileList);
                        surveyMstVO.setQestnarImgfilnb(qestnarImgfilnb);
                    }
                }
            } catch (Exception e) {
                log.error(e.toString());
            }
        }

        if (surveyMstVO.getQestnarId() < 1) {
            surveyService.insertThema(surveyMstVO);
        } else {
            surveyService.updateThema(surveyMstVO);
        }

        HttpUtility.sendRedirect(multiRequest, response, "처리되었습니다.", Config.ADMIN_ROOT + "/survey/themaList" + commandMap.getString("searchQuery"));
        return null;
    }
}
