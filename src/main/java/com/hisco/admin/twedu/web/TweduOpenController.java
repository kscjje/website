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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.area.service.AreaCdService;
import com.hisco.admin.eduadm.service.EduAdmService;
import com.hisco.admin.eduadm.vo.EdcDaysVO;
import com.hisco.admin.eduadm.vo.EdcProgramVO;
import com.hisco.admin.eduadm.vo.EdcTargetAgeVO;
import com.hisco.admin.twedu.service.TweduService;
import com.hisco.admin.twedu.vo.TweduDetailVO;
import com.hisco.admin.twedu.vo.TweduPlanVO;
import com.hisco.admin.twedu.vo.TweduVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.annotation.PageActionType;
import com.hisco.cmm.modules.DateUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
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
 * @Description : ??????????????? ?????? Controller
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 3
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}/townedu/open", "#{dynamicConfig.managerRoot}/townedu/open" })
public class TweduOpenController {

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

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Value("#{dynamicConfig.adminRoot}")
    private String ADMIN_ROOT;

    @Value("${Globals.DbEncKey}")
    private String dbEncKey;

    @GetMapping("/list")
    @PageActionInfo(title = "??????????????? ???????????? ??????", action = "R")
    public String viewListOpen(@ModelAttribute("searchVO") TweduVO searchVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        int totCnt = 0;
        searchVO.setPaginationInfo(paginationInfo);

        // List<TweduVO> list = eduAdmService.selectEdcProgramList(searchVO);
        searchVO.setDbEncKey(dbEncKey);
        List<TweduVO> list = tweduService.selectList(searchVO);
        searchVO.setDbEncKey("");

        if (list != null && !list.isEmpty()) {
            totCnt = ((TweduVO) list.get(0)).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        model.addAttribute("list", list);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/twedu/tweduOpenList");
    }

    @GetMapping("/detail")
    @PageActionInfo(title = "??????????????? ???????????? ??????", action = "R")
    public String viewOpenDetail(@ModelAttribute("searchVO") TweduVO searchVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        PaginationInfo paginationInfo = commandMap.getPagingInfo();

        searchVO.setDbEncKey(dbEncKey);
        TweduDetailVO result = tweduService.select(searchVO);
        searchVO.setDbEncKey("");

        model.addAttribute("tweduVO", result);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("edcPrgmid"));

        loadCommonCodeData(model, result);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/twedu/tweduOpenDetail");
    }

    @PostMapping("/{edc_prgm_no}.json")
    @ResponseBody
    @PageActionInfo(title = "??????????????? ???????????? ??????", action = "U", ajax = true)
    public ModelAndView update(
            @ModelAttribute TweduDetailVO tweduVO,
            @PathVariable String edc_prgm_no,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        String userId = user.getId();

        try {

            // ?????????????????? ??????????????? ????????? ?????? 6??? ??????
            tweduVO.setEdcPrgmNo(Integer.parseInt(edc_prgm_no));
            tweduVO.setEdcRsvnEdate(DateUtil.DateAddStr(tweduVO.getEdcSdate(), -1, "yyyy-MM-dd"));
            tweduVO.setEdcRsvnEtimeHour("18");
            tweduVO.setEdcRsvnEtimeMin("00");
            tweduVO.setEdcRsvnRectype(Constant.SM_LEREC_TYPE_????????????); // ?????????????????? ?????? ????????????
            tweduVO.setEdcRsvnAccssrd("1001"); // ?????????????????? ????????? ????????? ??????
            tweduVO.setEdcFeeType("2001"); // ?????????????????? ??????????????? ??????

            if ("Y".equals(tweduVO.getEdcLimitAgeyn())) {
                List<EdcTargetAgeVO> ageList = new ArrayList<EdcTargetAgeVO>();

                EdcTargetAgeVO ageTarget = new EdcTargetAgeVO();
                ageTarget.setEdcTargetAgeNm("????????????1");
                ageTarget.setEdcTargetSage(Integer.parseInt(tweduVO.getEdcTargetSage()));
                ageTarget.setEdcTargetEage(Integer.parseInt(tweduVO.getEdcTargetEage()));

                ageList.add(ageTarget);

                tweduVO.setEdcAgeList(ageList);
            }

            /* ???????????? ?????? ?????? */
            String[] edcDays = request.getParameterValues("edcDays");
            if (edcDays != null && edcDays.length > 0) {
                List<EdcDaysVO> days = new ArrayList<>();
                for (int i = 0; i < edcDays.length; i++) {
                    EdcDaysVO day = new EdcDaysVO();
                    day.setComcd(Config.COM_CD);
                    day.setEdcPrgmNo(tweduVO.getEdcPrgmNo());
                    day.setDayChk(edcDays[i]);
                    day.setReguser(userId);
                    days.add(day);
                }
                tweduVO.setEdcDaysList(days);
            }

            /* ??????????????? ?????? ?????? */
            String[] lectDates = request.getParameterValues("lectDate");
            String[] lectTitles = request.getParameterValues("lectTitle");
            String[] lectContentss = request.getParameterValues("lectContents");
            String[] lectEtc = request.getParameterValues("lectEtc");

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

            int result = tweduService.update(tweduVO, user);

            if (result > 0)
                resultInfo = HttpUtility.getSuccessResultInfo("?????? ???????????????.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("????????? ?????????????????????.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo(e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;

    }

    @PostMapping("/{edc_prgm_no}/status")
    @ResponseBody
    @PageActionInfo(title = "??????????????? ???????????? ??????", action = "U", ajax = true)
    public ModelAndView updateStatus(@PathVariable int edc_prgm_no, @RequestBody TweduVO payload,
            CommandMap commandMap) {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            payload.setEdcPrgmNo(edc_prgm_no);
            int result = tweduService.updateStatus(payload, user);

            if (result > 0)
                resultInfo = HttpUtility.getSuccessResultInfo("???????????? ????????? ?????????????????????.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("???????????? ????????? ?????????????????????.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("???????????? ????????? ?????????????????????:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @PostMapping("/status")
    @ResponseBody
    @PageActionInfo(title = "??????????????? ?????? ????????????", action = "U", ajax = true)
    public ModelAndView batchUpdateStatus(CommandMap commandMap, @RequestBody Map<String, String> payload) {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            int result = 0;

            String[] idArr = payload.get("idArr") != null ? payload.get("idArr").split(",") : new String[] {};
            String edcPrg = payload.get("edcPrg");

            if (idArr.length > 0 && edcPrg != null) {
                List<TweduVO> list = new ArrayList<>();
                for (int i = 0; i < idArr.length; i++) {
                    TweduVO vo = new TweduVO();
                    vo.setEdcPrgmNo(Integer.parseInt(idArr[i]));
                    vo.setEdcPrg(edcPrg);
                    list.add(vo);
                }
                result = tweduService.batchUpdateStatus(list, user);
            }

            if (result > 0)
                resultInfo = HttpUtility.getSuccessResultInfo("???????????? ????????? ?????????????????????.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("???????????? ????????? ?????????????????????.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("???????????? ????????? ?????????????????????:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @PostMapping("/rsvn")
    @ResponseBody
    @PageActionInfo(title = "??????????????? ?????? ??????????????????", action = "U", ajax = true)
    public ModelAndView batchUpdateRsvn(CommandMap commandMap, @RequestBody Map<String, String> payload) {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            int result = 0;

            String[] idArr = payload.get("idArr") != null ? payload.get("idArr").split(",") : new String[] {};
            String ymd = payload.get("ymd");
            String hms = payload.get("hms");

            if (idArr.length > 0 && ymd != null && hms != null) {
                List<TweduDetailVO> list = new ArrayList<>();
                for (int i = 0; i < idArr.length; i++) {
                    TweduDetailVO vo = new TweduDetailVO();
                    vo.setEdcPrgmNo(Integer.parseInt(idArr[i]));
                    vo.setEdcRsvnSdate(ymd);
                    vo.setEdcRsvnStime(hms);
                    list.add(vo);
                }
                result = tweduService.batchUpdateRsvn(list, user);
            }

            if (result > 0)
                resultInfo = HttpUtility.getSuccessResultInfo("?????????????????? ????????? ?????????????????????.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("?????????????????? ????????? ?????????????????????.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("?????????????????? ????????? ?????????????????????:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @PostMapping("/edcFileUpload.json")
    @ResponseBody
    @PageActionInfo(title = "??????????????? ?????? ??????", action = "U", ajax = true)
    public ModelAndView updateFileUpload(CommandMap commandMap,
            final MultipartHttpServletRequest multiRequest,
            HttpServletResponse response,
            ModelMap model)
            throws Exception {

        final Map<String, MultipartFile> files = multiRequest.getFileMap();
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        String uploadFolder = "";
        String originFileName = "";
        String realFilePath = "";
        String atchFileId = "";

        if (!files.isEmpty()) {
            try {
                // ?????? ?????? Id
                atchFileId = commandMap.getString("atchFileId");
                List<FileVO> result = fileUtil.parseFileInf(files, "EDC_", 0, atchFileId, uploadFolder, user.getId());
                Iterator<FileVO> iter = result.iterator();

                if (!atchFileId.equals("")) {
                    // ?????? ?????? ??????
                    fileMngService.deleteAndInsert(result.get(0), result);
                } else {
                    atchFileId = fileMngService.insertFileInfs(result);
                }

                eduAdmService.updateEdcProgramFileid(commandMap.getString("comcd"), Integer.parseInt(commandMap.getString("edcPrgmid")), commandMap.getString("inputid"), atchFileId, user.getId());

                while (iter.hasNext()) {
                    FileVO vo = iter.next();
                    originFileName = vo.getOrginFileName();
                    uploadFolder = vo.getFilePath();
                    realFilePath = multiRequest.getContextPath() + Config.USER_ROOT + "/common/file/view/" + uploadFolder + vo.getFileName() + "?originName=" + java.net.URLEncoder.encode(originFileName, "UTF-8");
                }

                resultInfo = HttpUtility.getSuccessResultInfo("?????? ???????????????.");

            } catch (Exception e) {
                resultInfo = HttpUtility.getErrorResultInfo(e.getMessage());
            }
        }

        mav.addObject("result", resultInfo);
        mav.addObject("originFileName", originFileName);
        mav.addObject("realFilePath", realFilePath);
        mav.addObject("atchFileId", atchFileId);
        return mav;

    }
    
    @PageActionInfo(title = "??????????????? ???????????? ????????????", action = PageActionType.READ)
    @GetMapping(value = "/edcOpenListExcel")
    public void edcOpenListExcel(@ModelAttribute("searchVO") TweduVO searchVO, HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap, HttpServletResponse response) throws Exception {
    	 
		String templete = "edcOpenList";
		String file_name = "???????????????_????????????";

    	PaginationInfo paginationInfo = commandMap.getPagingInfo();
    	searchVO.setRecordCountPerPage(Integer.MAX_VALUE);
        paginationInfo.setRecordCountPerPage(searchVO.getRecordCountPerPage());
        searchVO.setPaginationInfo(paginationInfo);

        searchVO.setDbEncKey(dbEncKey);
        
        List<TweduVO> programList = tweduService.selectList(searchVO);
        
        //?????? ????????? ?????? ??? ?????? ?????? data
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("list", programList);
        
        XLSTransformer transformer = new XLSTransformer();

        InputStream is = readTemplate(templete+".xls");
        Workbook workbook = null;
        try {
            workbook = transformer.transformXLS(is, data);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            response.getWriter().println("?????? ????????? ?????? ??? ?????? ??????<br>"+ e.getMessage());
        }

        //?????? ????????? contentType ??????
        response.setContentType( "application/vnd.ms-excel" );


		if(file_name.equals("")) {
			file_name = templete;
		}

        //?????? ????????? ??????
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName(file_name + ".xls"));

        workbook.write(response.getOutputStream());
    }

    /** ?????? ???????????? ?????????. */
    private InputStream readTemplate(String finalTemplate) throws FileNotFoundException {

    	String templateFilePath = FileMngUtil.GetRealRootPath().concat("WEB-INF/excelTemplate/" + finalTemplate);

        return new FileInputStream(templateFilePath);
    }
    
    /** ???????????? ????????? */
    private String encodeFileName(String filename) {
        try {
            return URLEncoder.encode(filename, "UTF-8").replaceAll("[+]", " ");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    private void loadCommonCodeData(ModelMap model, EdcProgramVO tweduVO) throws Exception {
        // ??????
        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));

        // ?????????????????? - SM_EDCPROGM_TYPE - 4001
        // ??????
        // model.addAttribute("daysList", eduAdmService.selectProgramDays(tweduVO));

        // ????????????(????????????)
        model.addAttribute("apprvType", codeService.selectCodeList("SM_EDCPG_ESTBL_STAT"));
        // ????????????
        model.addAttribute("targetType", codeService.selectCodeList("CM_AGEGBN"));
    }
}
