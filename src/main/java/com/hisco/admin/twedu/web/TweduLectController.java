/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.twedu.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.area.service.AreaCdService;
import com.hisco.admin.eduadm.service.EduAdmService;
import com.hisco.admin.twedu.service.TweduService;
import com.hisco.admin.twedu.vo.TweduAttendVO;
import com.hisco.admin.twedu.vo.TweduDetailVO;
import com.hisco.admin.twedu.vo.TweduPlanVO;
import com.hisco.admin.twedu.vo.TweduStudentVO;
import com.hisco.admin.twedu.vo.TweduVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * @Class Name : TweduController.java
 * @Description : 마을배움터 관리 Controller
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 3
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}/townedu/lect", "#{dynamicConfig.managerRoot}/townedu/lect" })
public class TweduLectController {

    @Resource(name = "eduAdmService")
    private EduAdmService eduAdmService;

    @Resource(name = "tweduService")
    private TweduService tweduService;

    @Resource(name = "areaCdService")
    private AreaCdService areaCdService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Value("${Globals.DbEncKey}")
    private String dbEncKey;

    private final String TEMP_LECT_FILE_KEY = "lect_temp_file";

    @GetMapping("/list")
    @PageActionInfo(title = "마을배움터 교육현황 조회", action = "R")
    public String viewListLect(@ModelAttribute("searchVO") TweduVO searchVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        int totCnt = 0;
        searchVO.setPaginationInfo(paginationInfo);

        searchVO.setDbEncKey(dbEncKey);
        searchVO.setSearchStat("3001");
        List<TweduVO> list = tweduService.selectList(searchVO);
        searchVO.setDbEncKey("");

        if (list != null && !list.isEmpty()) {
            totCnt = ((TweduVO) list.get(0)).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        model.addAttribute("list", list);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/twedu/tweduLectList");
    }

    @GetMapping("/detail")
    @PageActionInfo(title = "마을배움터 교육현황 상세", action = "R")
    public String viewLectDetail(@ModelAttribute("searchVO") TweduVO searchVO,
            CommandMap commandMap, HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {

        model.addAttribute("commandMap", commandMap);
        model.addAttribute("complStatType", codeService.selectCodeList("SM_EDC_COMPLSTATE"));

        // 상세데이타
        TweduDetailVO tweduVO = tweduService.select(searchVO);
        // 수업기록 날짜 필요
        List<TweduPlanVO> logList = tweduService.selectLogList(searchVO);
        if (logList == null || logList.size() < 1) {
            logList = tweduVO.getEdcPlanList();
        }

        model.addAttribute("tweduVO", tweduVO);
        model.addAttribute("logList", logList);

        int passCnt = 0;

        if (logList != null) {
            for (TweduPlanVO plan : logList) {
                if (plan.getPassYn().equals("Y"))
                    passCnt++;
            }
        }

        model.addAttribute("passCnt", passCnt);

        // 수강생
        List<TweduStudentVO> studentList = tweduService.selectStudentList(searchVO);
        model.addAttribute("studentList", studentList);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/twedu/tweduLectDetail");
    }

    @GetMapping("/report/print")
    public String viewLectPrint(CommandMap commandMap, ModelMap model) throws Exception {

        model.addAttribute("commandMap", commandMap);
        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/twedu/tweduLectPrint");
    }

    @GetMapping("/{edc_prgmid}/studentAjax")
    @PageActionInfo(title = "마을배움터 학생현황 조회", action = "R")
    public String listStudents(@PathVariable int edc_prgm_no, CommandMap commandMap, ModelMap model) throws Exception {

        TweduVO vo = new TweduVO();
        vo.setEdcPrgmNo(edc_prgm_no);
        vo.setDbEncKey(dbEncKey);
        List<TweduStudentVO> list = tweduService.selectStudentList(vo);

        model.addAttribute("list", list);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/twedu/tweduLectStudentAjax");
    }
    
    @PageActionInfo(title = "마을배움터 학생현황 다운로드", action = "R")
    @GetMapping(value = {"/{edc_prgmid}/edcstudentListExcel"})
    public void edcstudentListExcel(@PathVariable int edc_prgm_no, CommandMap commandMap, HttpServletResponse response,
            HttpServletRequest request, ModelMap model) throws Exception {

    	String requestUrl =  request.getRequestURI();
    	TweduVO vo = new TweduVO();
        vo.setEdcPrgmNo(edc_prgm_no);
        vo.setDbEncKey(dbEncKey);

        String templete = "edcstudentList";
        String file_name = "마을배움터_학생목록";


        List<TweduStudentVO> list = tweduService.selectStudentList(vo);


        //엑셀 데이터 변환 시 사용 되는 data
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("list", list);


        XLSTransformer transformer = new XLSTransformer();

        InputStream is = readTemplate(templete+".xls");
        Workbook workbook = null;
        try {
            workbook = transformer.transformXLS(is, data);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            response.getWriter().println("엑셀 데이터 변환 시 에러 발생<br>"+ e.getMessage());
        }

        //엑셀 데이터 contentType 정의
        response.setContentType( "application/vnd.ms-excel" );


		if(file_name.equals("")) {
			file_name = templete;
		}

        //엑셀 파일명 설정
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName(file_name + ".xls"));

        workbook.write(response.getOutputStream());

    }

    @PostMapping("/{edc_prgmid}/student/{edc_rsvn_comptid}/status.json")
    @ResponseBody
    @PageActionInfo(title = "마을배움터 교육상태 갱신", action = "U", ajax = true)
    public ModelAndView updateEduStatus(@PathVariable("edc_prgmid") int edc_prgmid,
            @PathVariable("edc_rsvn_comptid") int edc_rsvn_comptid, @RequestBody TweduStudentVO payload,
            CommandMap commandMap) {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            payload.setModuser(user.getId());
            payload.setEdcPrgmid(edc_prgmid);

            int result = tweduService.updateStudentStatus(payload);

            if (result > 0)
                resultInfo = HttpUtility.getSuccessResultInfo("교육상태 수정에 성공하였습니다.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("교육상태 수정에 실패하였습니다.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("교육상태 수정에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @PostMapping("/{edc_prgmid}/student/status.json")
    @ResponseBody
    @PageActionInfo(title = "마을배움터 교육상태 일괄갱신", action = "U", ajax = true)
    public ModelAndView batchUpdateEduStatus(@PathVariable("edc_prgmid") int edc_prgmid,
            @RequestBody Map<String, String> payload, CommandMap commandMap) {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            String userId = user.getId();

            int result = 0;

            String[] idArr = payload.get("idArr") != null ? payload.get("idArr").split(",") : new String[] {};
            String edcComplstat = payload.get("edcComplstat");

            if (idArr.length > 0 && edcComplstat != null) {
                List<TweduStudentVO> list = new ArrayList<>();
                for (int i = 0; i < idArr.length; i++) {
                    TweduStudentVO vo = new TweduStudentVO();
                    vo.setEdcPrgmid(edc_prgmid);
                    vo.setEdcRsvnReqid(Integer.parseInt(idArr[i]));
                    vo.setEdcComplstat(edcComplstat);
                    vo.setModuser(userId);
                    list.add(vo);
                }
                result = tweduService.batchUpdateStudentStatus(list);
            }

            if (result > 0)
                resultInfo = HttpUtility.getSuccessResultInfo("교육상태 수정에 성공하였습니다.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("교육상태 수정에 실패하였습니다.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("교육상태 수정에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @GetMapping("/{edc_prgmid}/attendanceAjax")
    @PageActionInfo(title = "마을배움터 출석부 조회", action = "R")
    public String listAttendance(@PathVariable String edc_prgm_no, @ModelAttribute("searchVO") TweduVO vo,
            CommandMap commandMap, ModelMap model) throws Exception {

        vo.setEdcPrgmNo(Integer.parseInt(edc_prgm_no));
        vo.setDbEncKey(dbEncKey);

        // 수업기록 날짜 필요
        List<TweduPlanVO> logList = tweduService.selectLogList(vo);
        if (logList == null || logList.size() < 1) {
            logList = tweduService.selectPlans(vo); // 학습계획
        }

        // 수강생
        List<TweduStudentVO> studentList = tweduService.selectStudentList(vo);
        model.addAttribute("studentList", studentList);
        model.addAttribute("logList", logList);
        vo.setDbEncKey("");

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/twedu/tweduLectAttendAjax");
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

    @PostMapping("/{edc_prgmid}/attendance")
    @ResponseBody
    @PageActionInfo(title = "마을배움터 출석부 수정", action = "U", ajax = true)
    public ModelAndView updateAttendance(@PathVariable int edc_prgmid, @RequestBody List<TweduAttendVO> attendances,
            CommandMap commandMap) {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

            int result = tweduService.updateAttendanceList(attendances, user, edc_prgmid);

            if (result > 0)
                resultInfo = HttpUtility.getSuccessResultInfo("출석부 수정에 성공하였습니다.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("출석부 수정에 실패하였습니다.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("출석부 수정에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @GetMapping("/{edc_prgmid}/edulogAjax")
    @PageActionInfo(title = "마을배움터 교육기록 조회", action = "R")
    public String listEduLog(@PathVariable int edc_prgm_no, HttpSession session, CommandMap commandMap, ModelMap model)
            throws Exception {

        TweduVO vo = new TweduVO();
        vo.setEdcPrgmNo(edc_prgm_no);

        List<TweduPlanVO> logList = tweduService.selectLogList(vo);
        model.addAttribute("logList", logList);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/twedu/tweduLectEdulogAjax");

    }

    @PostMapping("/{edc_prgmid}/edulog")
    @ResponseBody
    @PageActionInfo(title = "마을배움터 수업기록 등록", action = "C", ajax = true)
    public ModelAndView createLog(@PathVariable int edc_prgm_no, @RequestBody TweduPlanVO payload,
            HttpSession session, CommandMap commandMap) {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            payload.setModuser(user.getId());
            payload.setEdcPrgmNo(edc_prgm_no);

            int result = tweduService.createLog(payload, session.getAttribute(TEMP_LECT_FILE_KEY));

            if (result > 0)
                resultInfo = HttpUtility.getSuccessResultInfo("수업기록 등록에 성공하였습니다.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("수업기록 등록에 실패하였습니다.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("수업기록 등록에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @PostMapping("/{edc_prgmid}/edulog/{lect_seq}")
    @ResponseBody
    @PageActionInfo(title = "마을배움터 수업기록 수정", action = "U", ajax = true)
    public ModelAndView updateLog(@PathVariable("edc_prgm_no") int edc_prgm_no, @PathVariable("lect_seq") int lect_seq,
            @RequestBody TweduPlanVO payload, HttpSession session, CommandMap commandMap) {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            payload.setModuser(user.getId());
            payload.setEdcPrgmNo(edc_prgm_no);

            int result = tweduService.updateLog(payload, session.getAttribute(TEMP_LECT_FILE_KEY));

            if (result > 0)
                resultInfo = HttpUtility.getSuccessResultInfo("수업기록 수정에 성공하였습니다.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("수업기록 수정에 실패하였습니다.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("수업기록 수정에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @GetMapping("/{edc_prgmid}/reportAjax")
    @PageActionInfo(title = "마을배움터 보고서 조회", action = "R")
    public String detailReport(@PathVariable int edc_prgm_no, CommandMap commandMap, ModelMap model)
            throws Exception {

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        TweduVO vo = new TweduVO();
        vo.setEdcPrgmNo(edc_prgm_no);

        // 상세데이타
        TweduDetailVO tweduVO = tweduService.select(vo);
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
        model.addAttribute("edcPrgmid", edc_prgm_no);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/twedu/tweduLectReportAjax");

    }

    @PageActionInfo(title = "마을배움터 임시파일 수정", action = "U", ajax = true)
    @PostMapping("/tempFileUpload.json")
    @ResponseBody
    public ModelAndView updateTempFileUpload(CommandMap commandMap,
            final MultipartHttpServletRequest multiRequest, HttpSession session)
            throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        String uploadFolder = "";
        String originFileName = "";
        String realFilePath = "";

        tweduService.deleteTempFile(session.getAttribute(TEMP_LECT_FILE_KEY));
        session.setAttribute(TEMP_LECT_FILE_KEY, null);

        if (!multiRequest.getMultiFileMap().isEmpty()) {
            try {
                // 기존 파일 Id
                List<FileVO> result = fileUtil.parseFileInf(multiRequest, "TWEDU_", 0, "");
                Iterator<FileVO> iter = result.iterator();

                FileVO vo = null;
                while (iter.hasNext()) {
                    vo = iter.next();
                    originFileName = vo.getOrginFileName();
                    uploadFolder = vo.getFilePath();
                    realFilePath = multiRequest.getContextPath() + Config.USER_ROOT + "/common/file/view/" + uploadFolder + vo.getFileName() + "?originName=" + java.net.URLEncoder.encode(originFileName, "UTF-8");
                }
                log.debug(vo == null ? "Y" : "N");
                resultInfo = HttpUtility.getSuccessResultInfo("파일 임시저장에 성공하었습니다.");

                mav.addObject("originFileName", originFileName);
                mav.addObject("realFilePath", realFilePath);

                session.setAttribute(TEMP_LECT_FILE_KEY, vo);
            } catch (Exception e) {
                resultInfo = HttpUtility.getErrorResultInfo(e.getMessage());
            }
        }
        mav.addObject("result", resultInfo);

        return mav;
    }
    
    /** 엑셀 템플릿을 읽는다. */
    private InputStream readTemplate(String finalTemplate) throws FileNotFoundException {

    	String templateFilePath = FileMngUtil.GetRealRootPath().concat("WEB-INF/excelTemplate/" + finalTemplate);

        return new FileInputStream(templateFilePath);
    }
    
    /** 파일이름 인코딩 */
    private String encodeFileName(String filename) {
        try {
            return URLEncoder.encode(filename, "UTF-8").replaceAll("[+]", " ");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
