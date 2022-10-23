/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.user.mypage.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.area.service.AreaCdService;
import com.hisco.admin.comctgr.service.ComCtgrService;
import com.hisco.admin.comctgr.vo.ComCtgrVO;
import com.hisco.admin.eduadm.service.EduAdmService;
import com.hisco.admin.eduadm.vo.EdcDaysVO;
import com.hisco.admin.eduadm.vo.EdcProgramVO;
import com.hisco.admin.eduadm.vo.EdcTargetAgeVO;
import com.hisco.admin.instrctr.vo.InstrctrVO;
import com.hisco.admin.twedu.service.TweduService;
import com.hisco.admin.twedu.vo.TweduAttendVO;
import com.hisco.admin.twedu.vo.TweduDetailVO;
import com.hisco.admin.twedu.vo.TweduJoinVO;
import com.hisco.admin.twedu.vo.TweduPlanVO;
import com.hisco.admin.twedu.vo.TweduReportVO;
import com.hisco.admin.twedu.vo.TweduStudentVO;
import com.hisco.admin.twedu.vo.TweduVO;
import com.hisco.cmm.modules.DateUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.HttpUtility;
import com.ibm.icu.util.Calendar;

import egovframework.com.cmm.LoginVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : MyTowneduController.java
 * @Description : 마이 페이지 - 마을배움터 컨트롤러
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 12. 14
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/mypage/myTwedu")
public class MyTowneduController {

    @Resource(name = "eduAdmService")
    private EduAdmService eduAdmService;

    @Resource(name = "areaCdService")
    private AreaCdService areaCdService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "tweduService")
    private TweduService tweduService;

    @Value("${Globals.DbEncKey}")
    private String dbEncKey;

    @Resource(name = "comCtgrService")
    private ComCtgrService comCtgrService;

    /**
     * 마을배움터 개설현황
     *
     * @param
     * @return
     * @exception Exception
     */
    @GetMapping("/openList")
    public String viewOpenList(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        LoginVO user = commandMap.getUserInfo();

        model.addAttribute("userNm", user.getName());
        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/twedu/myTweduOpenList");
    }

    /**
     * 카테고리 목록
     *
     * @param
     * @return
     * @exception Exception
     */
    @GetMapping("/cateList.json")
    public String cateList(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        ComCtgrVO ctgParam = new ComCtgrVO();
        ctgParam.setComcd(Config.COM_CD);
        ctgParam.setUseYn("Y");
        // log.error("comCtgrService = {}", comCtgrService);
        List<ComCtgrVO> cateList = (List<ComCtgrVO>) comCtgrService.selectComctgrListForTree(ctgParam);

        model.addAttribute("cateList", cateList);
        return HttpUtility.getViewUrl(request);
    }

    // 분야 1depth 인 것만 선택

    /**
     * 마을배움터 학습현황
     *
     * @param
     * @return
     * @exception Exception
     */
    @GetMapping("/lectList")
    public String viewLectList(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        LoginVO user = commandMap.getUserInfo();

        model.addAttribute("userNm", user.getName());
        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/twedu/myTweduLectList");
    }

    /**
     * 마을배움터 출석부
     *
     * @param
     * @return
     * @exception Exception
     */
    @GetMapping("/lectAttend")
    public String viewLectAttend(@RequestParam("edc_prgm_id") int edc_prgm_id,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        TweduVO vo = new TweduVO();
        vo.setEdcPrgmNo(edc_prgm_id);
        vo.setDbEncKey(dbEncKey);
        TweduVO result = tweduService.selectEduTerm(vo);

        LoginVO userVO = commandMap.getUserInfo();
        if (result != null && !result.getMemNo().equals(userVO.getUniqId())) {
            HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/mypage/myTwedu/openList");
            return null;
        }

        model.addAttribute("edcPrgmid", edc_prgm_id);
        model.addAttribute("edcSdate", result.getEdcSdate());
        model.addAttribute("edcEdate", result.getEdcEdate());
        model.addAttribute("edcDays", result.getEdcDays());

        // 수강생
        List<TweduStudentVO> studentList = tweduService.selectStudentList(vo);
        model.addAttribute("studentList", studentList);

        // 수업기록 날짜 필요
        List<TweduPlanVO> logList = tweduService.selectLogList(vo);
        if (logList == null || logList.size() < 1) {
            logList = tweduService.selectPlans(vo); // 학습계획

            // 수업이 시작되었으면 학습계획데이타를 수업기록 데이타로 옮긴다
            if (result.getCurEdcClcnt() > 0) {
                tweduService.insertReportLogPlan(vo);
            }
        }

        model.addAttribute("logList", logList);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/twedu/myTweduAttend");
    }

    /**
     * 마을배움터 교육기록
     *
     * @param
     * @return
     * @exception Exception
     */
    @GetMapping("/lectArchive")
    public String viewLectArchive(@RequestParam("edc_prgm_id") int edc_prgm_id, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        TweduVO vo = new TweduVO();
        vo.setEdcPrgmNo(edc_prgm_id);
        vo.setDbEncKey(dbEncKey);
        TweduVO result = tweduService.selectEduTerm(vo);

        LoginVO userVO = commandMap.getUserInfo();
        if (result != null && !result.getMemNo().equals(userVO.getUniqId())) {
            HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/mypage/myTwedu/openList");
            return null;
        }

        // 수업기록
        List<TweduPlanVO> logList = tweduService.selectLogList(vo);

        // 수업기록 데이타가 없으면 학습계획을 디폴트로
        if (logList == null || logList.size() < 1) {
            logList = tweduService.selectPlans(vo); // 학습계획
        }

        model.addAttribute("edcPrgmid", edc_prgm_id);
        model.addAttribute("logList", logList);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/twedu/myTweduArchive");
    }

    /**
     * 마을배움터 보고서
     *
     * @param
     * @return
     * @exception Exception
     */
    @GetMapping("/lectReport")
    public String viewLectReport(@RequestParam("edc_prgm_id") int edc_prgm_id, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        LoginVO user = commandMap.getUserInfo();

        TweduVO vo = new TweduVO();
        vo.setEdcPrgmNo(edc_prgm_id);

        // 상세데이타
        TweduDetailVO tweduVO = tweduService.select(vo);

        LoginVO userVO = commandMap.getUserInfo();
        if (tweduVO != null && !tweduVO.getMemNo().equals(userVO.getUniqId())) {
            HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/mypage/myTwedu/openList");
            return null;
        }

        // 수강생
        List<TweduStudentVO> studentList = tweduService.selectStudentList(vo);
        model.addAttribute("studentList", studentList);

        // 수업기록
        List<TweduPlanVO> logList = tweduService.selectLogList(vo);
        model.addAttribute("logList", logList);

        int passCnt = 0;

        if (logList != null) {
            for (TweduPlanVO logVO : logList) {
                if ("Y".equals(logVO.getPassYn())) {
                    passCnt++;
                }
            }
        }

        SimpleDateFormat f = new SimpleDateFormat("HHmm", java.util.Locale.KOREA);
        java.util.Date d1 = f.parse(tweduVO.getEdcStime());
        java.util.Date d2 = f.parse(tweduVO.getEdcEtime());
        long diff = d2.getTime() - d1.getTime();
        long min = diff / 60000;

        model.addAttribute("eduMin", min);
        model.addAttribute("passCnt", passCnt);
        model.addAttribute("data", tweduVO);
        model.addAttribute("reportUserNm", user.getName());
        model.addAttribute("edcPrgmid", edc_prgm_id);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/twedu/myTweduReport");
    }

    /**
     * 마을배움터 참여현황
     *
     * @param
     * @return
     * @exception Exception
     */
    @GetMapping("/joinList")
    public String viewJoinList(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        LoginVO user = commandMap.getUserInfo();

        model.addAttribute("userNm", user.getName());
        // 교육상태
        model.addAttribute("complType", codeService.selectCodeList("SM_EDC_COMPLSTATE"));
        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/twedu/myTweduJoinList");
    }

    /**
     * 마을배움터 개설신청
     *
     * @param
     * @return
     * @exception Exception
     */
    @RequestMapping("/openRegi")
    public String viewOpenRegi(@ModelAttribute("tweduVO") TweduDetailVO tweduVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        LoginVO user = commandMap.getUserInfo();

        tweduVO.setEdcDaysList(eduAdmService.selectProgramDays(tweduVO));
        tweduVO.setOrgNo(tweduService.selectOrgNo());
        tweduVO.setEdcTargetSage("0");
        tweduVO.setEdcTargetEage("99");

        // 데이타 복사
        if (!commandMap.getString("copyEdcId").equals("")) {
            TweduVO searchVO = new TweduVO();
            searchVO.setEdcPrgmNo(Integer.parseInt(commandMap.getString("copyEdcId")));
            searchVO.setDbEncKey(dbEncKey);
            tweduVO = tweduService.select(searchVO);
        }

        loadCommonCodeData(model, tweduVO);

        if (!commandMap.getString("copyEdcId").equals("")) {
            String copyEdcWeek = commandMap.getString("copyEdcWeek");

            for (EdcDaysVO day : tweduVO.getEdcDaysList()) {
                if (copyEdcWeek.indexOf(day.getEdcDaygbn()) >= 0) {
                    day.setDayChk(day.getEdcDaygbn());
                } else {
                    day.setDayChk("");
                }
            }
            java.util.Date sdate = DateUtil.string2date(commandMap.getString("edcSdate"), "yyyy.MM.dd");
            java.util.Date edate = DateUtil.string2date(commandMap.getString("edcEdate"), "yyyy.MM.dd");

            tweduVO.setEdcSdate(DateUtil.printDatetime(sdate, "yyyy-MM-dd"));
            tweduVO.setEdcEdate(DateUtil.printDatetime(edate, "yyyy-MM-dd"));

            // 수업계획의 날짜를 셋팅한다
            List<TweduPlanVO> planList = tweduVO.getEdcPlanList();
            if (planList != null) {
                String startYMD = tweduVO.getEdcSdate();

                for (TweduPlanVO planVO : planList) {
                    boolean flag = true;

                    while (flag) {
                        String week = DateUtil.DateCheck(startYMD, "E"); // 요일을 구한다
                        if (week.equals("월")) {
                            week = "1";
                        } else if (week.equals("화")) {
                            week = "2";
                        } else if (week.equals("수")) {
                            week = "3";
                        } else if (week.equals("목")) {
                            week = "4";
                        } else if (week.equals("금")) {
                            week = "5";
                        } else if (week.equals("토")) {
                            week = "6";
                        } else if (week.equals("일")) {
                            week = "7";
                        }

                        if (copyEdcWeek.equals("") || copyEdcWeek.indexOf(week) >= 0) {
                            flag = false;
                            planVO.setEdcDate(startYMD);
                        }
                        // 다음날로 체크
                        startYMD = DateUtil.DateAddStr(startYMD, 1, "yyyy-MM-dd");
                    }

                }
            }

            tweduVO.setEdcPrgmNo(0);
            tweduVO.setEdcImgFileid("");
            tweduVO.setEdcImgOrigin("");
            tweduVO.setEdcPlanOrigin("");
            tweduVO.setEdcPlanPath("");
            tweduVO.setItemCd(0);

        }

        model.addAttribute("userNm", user.getName());
        model.addAttribute("tweduVO", tweduVO);
        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/twedu/myTweduOpenRegi");
    }

    /**
     * 마을배움터 개설현황 상세
     *
     * @param
     * @return
     * @exception Exception
     */
    @GetMapping("/openDetail")
    public String viewOpenDetail(@RequestParam("edc_prgm_id") int edc_prgm_id,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
        LoginVO user = commandMap.getUserInfo();

        TweduVO searchVO = new TweduVO();
        searchVO.setEdcPrgmNo(edc_prgm_id);

        searchVO.setDbEncKey(dbEncKey);
        TweduDetailVO result = tweduService.select(searchVO);
        searchVO.setDbEncKey("");

        LoginVO userVO = commandMap.getUserInfo();
        if (result != null && !result.getMemNo().equals(userVO.getUniqId())) {
            HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/mypage/myTwedu/openList");
            return null;
        }

        loadCommonCodeData(model, result);

        model.addAttribute("tweduVO", result);
        model.addAttribute("userNm", user.getName());
        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/twedu/myTweduOpenRegi");
    }

    /**
     * 마을배움터 신규 개설
     *
     * @param
     * @return
     * @exception Exception
     */
    @PostMapping("/openRegister.json")
    public String registerProgram(@ModelAttribute("tweduVO") TweduDetailVO tweduVO,
            CommandMap commandMap, MultipartHttpServletRequest multiRequest, ModelMap model,
            HttpServletResponse response)
            throws Exception {

        try {
            LoginVO user = commandMap.getUserInfo();

            String userIp = commandMap.getIp();
            String userId = user.getId();
            String memNo = user.getUniqId();

            // 파일 업로드
            final Map<String, MultipartFile> files = multiRequest.getFileMap();

            /* 기본 정보 세팅 */
            tweduVO.setEdcPrgmNo(codeService.selectNextseq("EDC_PROGRAM")); // 시퀀스 구하기
            tweduVO.setItemCd(codeService.selectNextseq("PROGRAM_ITEM"));
            tweduVO.setEdcProgmType("4001"); // 프로그램 유형 4001(마을배움터)로 고정
            tweduVO.setEdcRsvnRectype(Constant.SM_LEREC_TYPE_선착접수); // 마을배움터는 오직 선착접수
            tweduVO.setEdcRsvnAccssrd("1001"); // 마을배움터는 온라인 접수만 가능
            tweduVO.setEdcFeeType("2001"); // 마을배움터는 기본적으로 무료
            tweduVO.setEdcRsvnsetNm(tweduVO.getEdcSdate().substring(0,4)+"년 01차 모집"); //모집차수명은 0000년 01차 모집
            tweduVO.setUseYn("Y");
            tweduVO.setEdcOpenyn("Y");
            tweduVO.setReguser(user.getId());

            /* 모집시작일시는 프로그램 등록일시로 설정한다 - 마을배움터 */
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String today = sdf.format(Calendar.getInstance().getTime());

            tweduVO.setEdcRsvnSdate(today);
            tweduVO.setEdcRsvnStimeHour("09");
            tweduVO.setEdcRsvnStimeMin("00");

            // 접수마감일은 교육시작일 하루전 오후 6시 까지
            tweduVO.setEdcRsvnEdate(DateUtil.DateAddStr(tweduVO.getEdcSdate(), -1, "yyyy-MM-dd"));
            tweduVO.setEdcRsvnEtimeHour("18");
            tweduVO.setEdcRsvnEtimeMin("00");

            InstrctrVO instctrVO = prepareData(tweduVO, memNo, userIp, userId, multiRequest);

            int result = tweduService.insert(tweduVO, instctrVO, files, user);

            model.addAttribute("result", HttpUtility.getSuccessResultInfo("저장되었습니다."));

        } catch (Exception e) {
            e.printStackTrace();

            model.addAttribute("result", HttpUtility.getErrorResultInfo(e.getMessage()));
        }

        return HttpUtility.getViewUrl(multiRequest);

    }

    /**
     * 마을배움터 개설 수정
     *
     * @param
     * @return
     * @exception Exception
     */
    @PostMapping("/openModify.json")
    @ResponseBody
    public ModelAndView saveProgram(@ModelAttribute("tweduVO") TweduDetailVO tweduVO,
            CommandMap commandMap, MultipartHttpServletRequest multiRequest) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = commandMap.getUserInfo();

            String userIp = commandMap.getIp();
            String userId = user.getId();
            String memNo = user.getUniqId();

            // 접수마감일은 교육시작일 하루전 오후 6시 까지
            tweduVO.setEdcRsvnEdate(DateUtil.DateAddStr(tweduVO.getEdcSdate(), -1, "yyyy-MM-dd"));
            tweduVO.setEdcRsvnEtimeHour("18");
            tweduVO.setEdcRsvnEtimeMin("00");
            tweduVO.setEdcRsvnRectype(Constant.SM_LEREC_TYPE_선착접수); // 마을배움터는 오직 선착접수
            tweduVO.setEdcRsvnAccssrd("1001"); // 마을배움터는 온라인 접수만 가능
            tweduVO.setEdcFeeType("2001"); // 마을배움터는 기본적으로 무료
            tweduVO.setEdcOpenyn(null);
            tweduVO.setModuser(user.getId());

            EdcProgramVO edcProgramVO = new EdcProgramVO();
            edcProgramVO.setEdcPrgmNo(tweduVO.getEdcPrgmNo());

            String maxSeq = eduAdmService.selectProgramRsvnSetMax(edcProgramVO);

            tweduVO.setEdcRsvnsetSeq(maxSeq);

            // 파일 업로드
            final Map<String, MultipartFile> files = multiRequest.getFileMap();

            InstrctrVO instctrVO = prepareData(tweduVO, memNo, userIp, userId, multiRequest);

            int result = tweduService.update(tweduVO, instctrVO, files, user);

            String msg = "신청서가 수정되었습니다.";
            if (tweduVO.getEdcPrg().equals("3001")) {
                msg = "신청서제출이 완료되었습니다.";
            }

            if (result > 0)
                resultInfo = HttpUtility.getSuccessResultInfo(msg);
            else
                resultInfo = HttpUtility.getErrorResultInfo("데이타 수정에 실패하였습니다.");

        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = HttpUtility.getErrorResultInfo("데이타 수정에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    /**
     * 마을배움터 개설현황 리스트
     *
     * @param
     * @return
     * @exception Exception
     */
    @GetMapping({ "/open", "/lect" })
    @ResponseBody
    public ModelAndView getOpenList(@ModelAttribute("searchVO") TweduVO searchVO, CommandMap commandMap,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = commandMap.getUserInfo();
            String memNo = user.getUniqId();

            searchVO.setMemNo(memNo);
            searchVO.setPaginationInfo(commandMap.getPagingInfo());
            searchVO.setDbEncKey(dbEncKey);

            if (request.getRequestURI().indexOf("lect") > 0) {
                searchVO.setSearchStat("3001"); // 승인완료건
            }

            List<TweduVO> list = tweduService.selectList(searchVO);
            searchVO.setDbEncKey("");

            if (list != null) {
                resultInfo = HttpUtility.getSuccessResultInfo("마을배움터 개설현황 조회에 성공하였습니다.");

                mav.addObject("data", list);
            } else {
                resultInfo = HttpUtility.getErrorResultInfo("마을배움터 개설현황 조회에 실패하였습니다.");
            }

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("마을배움터 개설현황 조회에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    /**
     * 마을배움터 개설현황 상세
     *
     * @param
     * @return
     * @exception Exception
     */
    @GetMapping("/open/{edc_prgm_id}")
    @ResponseBody
    public ModelAndView getOpenDetail(@PathVariable("edc_prgm_id") int edc_prgm_id,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            TweduVO searchVO = new TweduVO();
            searchVO.setEdcPrgmNo(edc_prgm_id);

            searchVO.setDbEncKey(dbEncKey);
            TweduDetailVO result = tweduService.select(searchVO);
            searchVO.setDbEncKey("");

            if (result != null) {
                resultInfo = HttpUtility.getSuccessResultInfo("이전 개설신청 상세 조회에 성공하였습니다.");

                mav.addObject("data", result);
            } else {
                resultInfo = HttpUtility.getErrorResultInfo("이전 개설신청 상세 조회에 실패하였습니다.");
            }

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("이전 개설신청 상세 조회에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    /**
     * 마을배움터 신규/저장 전 데이터 준비
     *
     * @param
     * @return
     * @exception Exception
     */
    private InstrctrVO prepareData(TweduDetailVO tweduVO, String memNo, String userIp, String userId,
            MultipartHttpServletRequest multiRequest) throws Exception {
        /* 기본 정보 세팅 */
        tweduVO.setUserIp(userIp);
        tweduVO.setMemNo(memNo);
        tweduVO.setEdcOncapa(tweduVO.getEdcPncpa());

        /* 연령제한 정보 세팅 */

        if ("Y".equals(tweduVO.getEdcLimitAgeyn())) {
            List<EdcTargetAgeVO> ageList = new ArrayList<EdcTargetAgeVO>();

            EdcTargetAgeVO ageTarget = new EdcTargetAgeVO();
            ageTarget.setEdcTargetAgeNm("나이제한1");
            ageTarget.setEdcTargetSage(Integer.parseInt(tweduVO.getEdcTargetSage()));
            ageTarget.setEdcTargetEage(Integer.parseInt(tweduVO.getEdcTargetEage()));

            ageList.add(ageTarget);

            tweduVO.setEdcAgeList(ageList);
        }

        /* 교육요일 정보 세팅 */
        String[] edcDays = multiRequest.getParameterValues("edcDays");
        if (edcDays != null && edcDays.length > 0) {
            List<EdcDaysVO> days = new ArrayList<>();
            for (int i = 0; i < edcDays.length; i++) {
                EdcDaysVO day = new EdcDaysVO();
                day.setComcd(Config.COM_CD);
                day.setDayChk(edcDays[i]);
                day.setReguser(userId);
                days.add(day);
            }
            tweduVO.setEdcDaysList(days);
        }

        /* 강사 정보 세팅 */
        InstrctrVO instctrVO = new InstrctrVO();
        instctrVO.setComcd(Config.COM_CD);
        instctrVO.setOrgNo(tweduVO.getOrgNo());
        instctrVO.setNameKor(tweduVO.getInstrctrName());
        instctrVO.setHpNo(multiRequest.getParameter("instrctrHp"));
        instctrVO.setBankNm(tweduVO.getEdcFeeBnkNm());
        instctrVO.setBankAccNo(tweduVO.getEdcFeeAccNo());
        instctrVO.setBankAccNm(tweduVO.getEdcFeeBnkNm());

        /* 수업계획서 정보 세팅 */
        String[] lectDates = multiRequest.getParameterValues("lectDate");
        String[] lectTitles = multiRequest.getParameterValues("lectTitle");
        String[] lectContentss = multiRequest.getParameterValues("lectContents");
        String[] lectEtc = multiRequest.getParameterValues("lectEtc");

        if (lectDates != null && lectDates.length > 0) {
            List<TweduPlanVO> plans = new ArrayList<>();
            for (int i = 0; i < lectDates.length; i++) {
                TweduPlanVO p = new TweduPlanVO();
                p.setComcd(Config.COM_CD);
                p.setEdcPrgmNo(tweduVO.getEdcPrgmNo());
                p.setEdcClassNo(i + 1);
                p.setEdcDate(lectDates[i]);
                p.setEdcTitle(lectTitles[i]);
                p.setEdcCnts(lectContentss[i]);
                p.setEdcEtccnts(lectEtc[i]);
                p.setReguser(userId);
                plans.add(p);
            }
            tweduVO.setEdcPlanList(plans);
        }

        return instctrVO;
    }

    @GetMapping("/lectAttendList.json")
    @ResponseBody
    public ModelAndView listAttendance(@ModelAttribute("searchVO") TweduVO vo,
            CommandMap commandMap) {
        /* edc_atend_mng */
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            List<TweduAttendVO> result = tweduService.selectAttendanceList(vo);

            mav.addObject("data", result);
            resultInfo = HttpUtility.getSuccessResultInfo("OK");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("출석부 조회에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    @PostMapping("/lect/{edcPrgmid}/attendance.json")
    @ResponseBody
    public ModelAndView updateAttendance(@PathVariable int edcPrgmid, @RequestBody List<TweduAttendVO> attendances,
            CommandMap commandMap) {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = commandMap.getUserInfo();

            int result = tweduService.updateAttendanceList(attendances, user, edcPrgmid);

            if (result > 0)
                resultInfo = HttpUtility.getSuccessResultInfo("출석부 수정에 성공하였습니다.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("출석부 수정에 실패하였습니다.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("출석부 수정에 실패하였습니다:" + e.getMessage());
            e.printStackTrace();
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @PostMapping("/lect/{edc_prgm_no}/edulog.json")
    @ResponseBody
    public ModelAndView updateLog(@PathVariable("edc_prgm_no") int edc_prgm_no,
            MultipartHttpServletRequest multiRequest, HttpSession session, CommandMap commandMap) {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = commandMap.getUserInfo();

            Map<String, MultipartFile> files = multiRequest.getFileMap();
            Set<String> keys = files.keySet();
            log.debug("size of keys=" + keys.size());
            for (String key : keys) {
                log.debug(key + "==>" + files.get(key).getOriginalFilename());
            }

            String[] lectSeqArr = multiRequest.getParameterValues("seq");

            int result = 0;
            if (lectSeqArr != null) {
                List<TweduPlanVO> list = new ArrayList<>();
                for (int i = 0; i < lectSeqArr.length; i++) {

                    int seq = Integer.parseInt(lectSeqArr[i]);

                    TweduPlanVO vo = new TweduPlanVO();
                    vo.setComcd(Config.COM_CD);
                    vo.setEdcPrgmNo(edc_prgm_no);
                    vo.setLectSeq(seq + 1);
                    vo.setEdcDate(commandMap.getString("lectDate_" + seq));
                    vo.setEdcTitle(commandMap.getString("lectTitle_" + seq));
                    vo.setEdcCnts(commandMap.getString("lectContent_" + seq));
                    vo.setLectFileid(commandMap.getString("lectFileid_" + seq));
                    vo.setReguser(user.getId());

                    list.add(i, vo);
                }

                result = tweduService.batchUpdateLog(list, files, user);
            }

            if (result > 0)
                resultInfo = HttpUtility.getSuccessResultInfo("저장되었습니다.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("교육기록 저장에 실패하였습니다.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("수업기록 수정에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @GetMapping("/lect/{edc_prgm_no}/report")
    @ResponseBody
    public ModelAndView detailReport(@PathVariable int edc_prgm_no, CommandMap commandMap) {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = commandMap.getUserInfo();

            TweduVO vo = new TweduVO();
            vo.setEdcPrgmNo(edc_prgm_no);

            TweduReportVO data = tweduService.selectReport(vo);

            mav.addObject("data", data);
            mav.addObject("reportUserNm", user.getName());
            resultInfo = HttpUtility.getSuccessResultInfo("");
        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("마을배움터 보고서 조회에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 마을배움터 참여현황 리스트
     *
     * @param
     * @return
     * @exception Exception
     */
    @GetMapping("/join")
    @ResponseBody
    public ModelAndView getJoinList(@RequestParam("edcComplstat") String edcComplstat, CommandMap commandMap) {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = commandMap.getUserInfo();
            String memNo = user.getUniqId();

            TweduJoinVO searchVO = new TweduJoinVO();
            searchVO.setEdcMemNo(memNo);
            searchVO.setEdcComplstat(edcComplstat);
            searchVO.setPaginationInfo(commandMap.getPagingInfo());
            List<TweduJoinVO> list = tweduService.selectJoinList(searchVO);

            if (list != null) {
                resultInfo = HttpUtility.getSuccessResultInfo("마을배움터 참여현황 조회에 성공하였습니다.");

                mav.addObject("data", list);
            } else {
                resultInfo = HttpUtility.getErrorResultInfo("마을배움터 참여현황 조회에 실패하였습니다.");
            }

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("마을배움터 참여현황 조회에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    /**
     * 마을배움터 공통코드 로드
     *
     * @param
     * @return
     * @exception Exception
     */
    private void loadCommonCodeData(ModelMap model, EdcProgramVO tweduVO) throws Exception {
        // 지역
        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));

        // 프로그램유형 - SM_EDCPROGM_TYPE - 4001
        // 요일
        // model.addAttribute("daysList", eduAdmService.selectProgramDays(tweduVO));

        // 개설상태(승인상태)
        model.addAttribute("apprvType", codeService.selectCodeList("SM_EDCPG_ESTBL_STAT"));
        // 교육대상
        model.addAttribute("targetType", codeService.selectCodeList("CM_AGEGBN"));
    }
}
