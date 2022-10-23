/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.instrctr.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.instrctr.service.InstrctrService;
import com.hisco.admin.instrctr.vo.InstrctrVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.config.DynamicConfigUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.extend.AbstractController;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.cmm.util.Utils;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : InstrctrController.java
 * @Description : 강사관리 Controller
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 5
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}/instrctr", "#{dynamicConfig.managerRoot}/instrctr" })
public class InstrctrController extends AbstractController{

    @Resource(name = "instrctrService")
    private InstrctrService instrctrService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "dynamicConfigUtil")
    private DynamicConfigUtil configUtil;

    @GetMapping("/list")
    @PageActionInfo(title = "교육프로그램 강사 조회", action = "R")
    public String list(@ModelAttribute("searchVO") InstrctrVO searchVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);

        int totCnt = 0;
        searchVO.setComcd(Config.COM_CD);

        List<InstrctrVO> list = instrctrService.list(searchVO);

        if (list != null && !list.isEmpty()) {
            totCnt = list.get(0).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        model.addAttribute("list", list);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/instrctr/instrctrList");
    }

    @GetMapping("/listAjax.json")
    @ResponseBody
    public ModelAndView listAjax(@ModelAttribute("searchVO") InstrctrVO searchVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setRecordCountPerPage(100);
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setComcd(Config.COM_CD);

        List<InstrctrVO> list = instrctrService.list(searchVO);

        ModelAndView mav = new ModelAndView("jsonView");

        log.debug("==teacherListAjax==");
        log.debug(JsonUtil.List2String(list));

        mav.addObject("result", list);
        return mav;
    }

    @GetMapping("/register")
    public String detail(@ModelAttribute("instrctrVO") InstrctrVO instrctrVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        instrctrVO.setComcd(Config.COM_CD);

        // 강사유형
        model.addAttribute("typeList", codeService.selectCodeList("CM_INSTRCTR_TYPE"));
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("mode"));
        model.addAttribute("formAction", "./register");

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/instrctr/instrctrRegi");
    }

    @GetMapping("/{instrctrNo}")
    @PageActionInfo(title = "교육프로그램 강사 조회", action = "R")
    public String detail(@PathVariable("instrctrNo") int instrctrNo,
            @ModelAttribute("instrctrVO") InstrctrVO instrctrVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        InstrctrVO data = null;

        try {
            data = instrctrService.detail(instrctrNo);
        } catch (Exception e) {
            log.debug("해당 데이터를 찾을 수 없어 목록 화면으로 돌아갑니다.");
            return "redirect:" + configUtil.getAdminDynamicPath(request, Config.ADMIN_ROOT + "/instrctr/list");
        }

        // 강사유형
        model.addAttribute("typeList", codeService.selectCodeList("CM_INSTRCTR_TYPE"));
        model.addAttribute("instrctrVO", data);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("mode"));
        model.addAttribute("formAction", "./update/" + instrctrNo);

        // 최근 교육프로그램
        model.addAttribute("edcList", instrctrService.selectEdcList(data));

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/instrctr/instrctrRegi");
    }

    @PostMapping("/register")
    @PageActionInfo(title = "교육프로그램 강사 등록", action = "C")
    public String create(@ModelAttribute InstrctrVO payload,
            CommandMap commandMap, final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
            ModelMap model) throws Exception {

        try {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

            // 파일 업로드
            final Map<String, MultipartFile> files = multiRequest.getFileMap();

            int result = instrctrService.create(payload, user, files);

            HttpUtility.sendRedirect(multiRequest, response, "저장 되었습니다.", configUtil.getAdminDynamicPath(multiRequest, Config.ADMIN_ROOT + "/instrctr/list"));
            return null;

        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("typeList", codeService.selectCodeList("CM_INSTRCTR_TYPE"));
            model.addAttribute("commandMap", commandMap);
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/instrctr/instrctrRegi");
        }

    }

    @PostMapping("/update/{instrctrNo}")
    @PageActionInfo(title = "교육프로그램 강사 수정", action = "U")
    public String update(@ModelAttribute InstrctrVO payload,
            @PathVariable("instrctrNo") int instrctrNo,
            CommandMap commandMap, final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
            ModelMap model) throws Exception {

        try {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

            // payload.setInstrctrNo(instrctrNo);
            log.debug("getNameKor=" + payload.getNameKor());

            int result = instrctrService.update(payload, user);

            HttpUtility.sendRedirect(multiRequest, response, "수정 되었습니다.", configUtil.getAdminDynamicPath(multiRequest, Config.ADMIN_ROOT + "/instrctr/list") + commandMap.getString("searchQuery"));
            return null;

        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("typeList", codeService.selectCodeList("CM_INSTRCTR_TYPE"));
            model.addAttribute("commandMap", commandMap);
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/instrctr/instrctrRegi");
        }
    }

    @PostMapping("/{instrctrNo}")
    @PageActionInfo(title = "교육프로그램 강사 삭제", action = "U", ajax = true)
    @ResponseBody
    public String delete(@PathVariable("instrctrNo") int instrctrNo,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
//        ResultInfo resultInfo = new ResultInfo();
        Map<String, Object> ReturnMap = new HashMap<String, Object>();
        
        int result = 0 ;
        try {
            result = instrctrService.delete(instrctrNo);
            if (result > 0) {
            	ReturnMap.put("result", true);
            	ReturnMap.put("comment", "강사 삭제에 성공하였습니다.");
            }
            else {
            	ReturnMap.put("result", false);
            	ReturnMap.put("comment", "강사 삭제에 실패하였습니다.");
            }
        } catch (Exception e) {
        	ReturnMap.put("result", false);
        	ReturnMap.put("comment", "강사 삭제에 실패하였습니다." + e.getMessage());
        }
        
		ReturnMap.put("data", result);
		String jsonData = MakeJsonData(request, ReturnMap);
		System.out.println("jsonData==="+jsonData);
		return jsonData;
//        mav.addObject("result", resultInfo);
//System.out.println("resultInfo: "+resultInfo);
//        return mav;
    }

    @PageActionInfo(title = "강사 사진 수정", action = "U", ajax = true)
    @PostMapping(value = "/fileUpload.json")
    @ResponseBody
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
                // 기존 파일 Id
                atchFileId = commandMap.getString("atchFileId");
                List<FileVO> result = fileUtil.parseFileInf(files, "INS_", 0, atchFileId, uploadFolder, user.getId());
                Iterator<FileVO> iter = result.iterator();

                if (!atchFileId.equals("")) {
                    // 기존 파일 삭제
                    fileMngService.deleteAndInsert(result.get(0), result);
                } else {
                    atchFileId = fileMngService.insertFileInfs(result);
                }

                instrctrService.updateFileid(commandMap.getString("comcd"), Integer.parseInt(commandMap.getString("instrctrNo")), commandMap.getString("inputid"), atchFileId, user.getId());

                while (iter.hasNext()) {
                    FileVO vo = iter.next();
                    originFileName = vo.getOrginFileName();
                    uploadFolder = vo.getFilePath();
                    realFilePath = Config.USER_ROOT + "/common/file/view/" + uploadFolder + vo.getFileName() + "?originName=" + java.net.URLEncoder.encode(originFileName, "UTF-8");
                }

                resultInfo = HttpUtility.getSuccessResultInfo("저장 되었습니다.");

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
}
