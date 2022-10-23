/*
 * This software is the confidential and proprietary information of
 * Shinsegae Internatinal Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with HISCO.
 */
package com.hisco.admin.instrctr.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.instrctr.service.InstrPoolService;
import com.hisco.admin.instrctr.vo.InstrPoolVO;
import com.hisco.admin.member.service.MemberService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.config.DynamicConfigUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.FileVO;
import com.hisco.intrfc.sms.service.BizMsgService;
import com.hisco.intrfc.sms.vo.BizMsgVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : InstrPoolController.java
 * @Description : 강사Pool관리 Controller
 * @author vivasoul@legitsys.co.kr
 * @since 2021. 11. 9
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}/instrpool", "#{dynamicConfig.managerRoot}/instrpool" })
public class InstrPoolController {
    @Resource(name = "instrPoolService")
    private InstrPoolService instrPoolService;

    @Resource(name = "memberService")
    private MemberService memberService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "dynamicConfigUtil")
    private DynamicConfigUtil configUtil;


    @Resource(name = "bizMsgService")
    private BizMsgService bizMsgService;

    @GetMapping("/list")
    @PageActionInfo(title = "강사은행 조회", action = "R")
    public String list(@ModelAttribute("searchVO") InstrPoolVO searchVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);

        int totCnt = 0;
        List<InstrPoolVO> list = instrPoolService.list(searchVO);

        if (list != null && !list.isEmpty()) {
            totCnt = list.get(0).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        model.addAttribute("list", list);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);

        // 상태
        model.addAttribute("stateList", codeService.selectCodeList("SM_INSTRCTR_STATE"));

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/instrctr/instrPoolList");
    }

    @GetMapping("/regist")
    public String detail(@ModelAttribute("instrPoolVO") InstrPoolVO instrPoolVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/instrctr/instrPoolRegist");
    }

    @GetMapping("/detail")
    @PageActionInfo(title = "강사은행  상세조회", action = "R")
    public String detail(CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        InstrPoolVO instrPoolVO = new InstrPoolVO();
        instrPoolVO.setMemNo(commandMap.getString("detailNo"));

        try {
            instrPoolVO = instrPoolService.detail(instrPoolVO);
        } catch (Exception e) {
            log.debug("해당 데이터를 찾을 수 없어 목록 화면으로 돌아갑니다.");
            return "redirect:" + configUtil.getAdminDynamicPath(request, Config.ADMIN_ROOT + "/instrpool/list");
        }

        // 파일 리스트 검색
        if (instrPoolVO.getProflImageid() != null && !"".equals(instrPoolVO.getProflImageid())) {
            FileVO fileVO = new FileVO();
            fileVO.setFileGrpinnb(instrPoolVO.getProflImageid());

            List<FileVO> fileList = fileMngService.selectFileInfs(fileVO);

            for (FileVO file : fileList) {
                if (file.getFileSn().equals("1")) {
                    model.addAttribute("planFile", file); // 강의계획서
                } else {
                    model.addAttribute("imgFile", file); // 대표 이미지
                }
            }
        }

        model.addAttribute("instrPoolVO", instrPoolVO);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("searchString", commandMap.getSearchQuery("detailNo"));
        // 상태
        model.addAttribute("stateList", codeService.selectCodeList("SM_INSTRCTR_STATE"));

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/instrctr/instrPoolDetail");
    }

    @PageActionInfo(title = "강사은행  등록", action = "C", ajax = true)
    @PostMapping("/regist/{memNo}.json")
    @ResponseBody
    public ModelAndView create(@PathVariable("memNo") String memNo, @ModelAttribute InstrPoolVO instrPoolVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;
        instrPoolVO.setMemNo(memNo);

        try {
            int result = instrPoolService.create(instrPoolVO);
            if (result > 0)
                resultInfo = HttpUtility.getSuccessResultInfo("등록되었습니다.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("강사Pool 등록에 실패하였습니다.");
        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("강사Pool 등록에 실패하였습니다:" + e.getMessage());
        }
        mav.addObject("result", resultInfo);
        return mav;

    }

    @PageActionInfo(title = "강사은행  수정", action = "U")
    @PostMapping("/modifySave")
    public String poolModifySave(@ModelAttribute("instrPoolVO") InstrPoolVO instrPoolVO,
            CommandMap commandMap, final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
            HttpServletRequest request,
            ModelMap model) throws Exception {

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        // 파일 업로드
        final Map<String, MultipartFile> files = multiRequest.getFileMap();
        String atachFileId = instrPoolVO.getProflImageid();
        if (atachFileId == null)
            atachFileId = "";

        if (files != null) {
            List<FileVO> result = fileUtil.parseFileInf(files, "TCH_", 1, atachFileId, "", user.getId(), "file_1");

            if (result != null && result.size() > 0) {
                atachFileId = result.get(0).getFileGrpinnb();
            } else if (result == null) {
                result = new ArrayList<FileVO>();
            }

            List<FileVO> result2 = fileUtil.parseFileInf(files, "TCH_", 2, atachFileId, "", user.getId(), "file_2");

            if (result2 != null && result2.size() > 0) {
                result.add(result2.get(0));

            }

            if (result != null && result.size() > 0) {
                if (!instrPoolVO.getProflImageid().equals("")) {
                    // 기존 파일그룹 코드가 있으면
                    fileMngService.updateFileInfs(atachFileId, "", result);
                } else {
                    fileMngService.insertFileInfs(result);
                    atachFileId = result.get(0).getFileGrpinnb();
                }
            }
        }

        instrPoolVO.setProflImageid(atachFileId);
        int result = instrPoolService.update(instrPoolVO);

        HttpUtility.sendRedirect(multiRequest, response, "수정 되었습니다.", configUtil.getAdminDynamicPath(request, Config.ADMIN_ROOT + "/instrpool/list") + commandMap.getString("searchQuery"));
        return null;

    }

    @PageActionInfo(title = "강사은행  삭제", action = "D", ajax = true)
    @PostMapping("/delete/{memNo}.json")
    @ResponseBody
    public ModelAndView delete(@PathVariable("memNo") String memNo,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        try {
            InstrPoolVO instrPoolVO = new InstrPoolVO();
            instrPoolVO.setMemNo(memNo);
            int result = instrPoolService.delete(instrPoolVO);

            if (result > -1)
                resultInfo = HttpUtility.getSuccessResultInfo("삭제되었습니다.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("강사Pool 삭제에 실패하였습니다.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("강사Pool 삭제에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @PageActionInfo(title = "강사은행  상태 변경", action = "U", ajax = true)
    @PostMapping("/updateState/{memNo}.json")
    @ResponseBody
    public ModelAndView updateState(@PathVariable("memNo") String memNo,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        try {
            InstrPoolVO instrPoolVO = new InstrPoolVO();
            instrPoolVO.setMemNo(memNo);
            instrPoolVO.setState(commandMap.getString("state"));
            instrPoolVO.setModuser(user.getId());
            int result = instrPoolService.updateState(instrPoolVO);

            //승인완료이면 메시지 발송
            if(instrPoolVO.getState().equals("3001") && result > 0){
            	try{
	            	instrPoolVO = instrPoolService.detail(instrPoolVO);

	            	BizMsgVO msgVO = new BizMsgVO();
	            	msgVO.setMsgNo(10);
	            	msgVO.setDestName(instrPoolVO.getMemNm());
	            	msgVO.setDestPhone(instrPoolVO.getHp());
	            	bizMsgService.sendKakaoMessage(msgVO);
            	}catch(Exception e){
            		//문자 에러는 체크하지 않음
            	}
            }

            if (result > -1)
                resultInfo = HttpUtility.getSuccessResultInfo("상태가 변경 되었습니다.");
            else
                resultInfo = HttpUtility.getErrorResultInfo("상태변경에 실패하였습니다.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("상태변경에 실패하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @PageActionInfo(title = "강사은행  일괄승인", action = "U", ajax = true)
    @PostMapping("/updateStateChecked.json")
    @ResponseBody
    public ModelAndView updateStateChecked(@ModelAttribute("instrPoolVO") InstrPoolVO instrPoolVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        List<String> memList = null;

        try {
            instrPoolVO.setState("3001");
            int result = 0;
            if (request.getParameterValues("checkedMem") != null) {
                instrPoolVO.setCheckedMem(request.getParameterValues("checkedMem"));
                memList = instrPoolService.updateStateCheckAll(instrPoolVO);
            }

            resultInfo = HttpUtility.getSuccessResultInfo("상태가 변경 되었습니다.");

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("일괄승인에 실패하였습니다:" + e.getMessage());

            e.printStackTrace();
        }

        //승인한 사람들 메시지 보내기
        if(memList != null && memList.size() > 0){
        	for(String memNo : memList){
        		try{
        			instrPoolVO.setMemNo(memNo);
	            	instrPoolVO = instrPoolService.detail(instrPoolVO);

	            	BizMsgVO msgVO = new BizMsgVO();
	            	msgVO.setMsgNo(10);
	            	msgVO.setDestName(instrPoolVO.getMemNm());
	            	msgVO.setDestPhone(instrPoolVO.getHp());
	            	bizMsgService.sendKakaoMessage(msgVO);

            	}catch(Exception e){
            		//문자 에러는 체크하지 않음
            	}
        	}
        }

        mav.addObject("result", resultInfo);

        return mav;
    }

    @GetMapping("/members")
    public ModelAndView listMembers(@RequestParam Map<String, Object> paramMap, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        paramMap.put("comcd", Config.COM_CD);

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paramMap.put("firstIndex", paginationInfo.getFirstRecordIndex());
        paramMap.put("lastIndex", paginationInfo.getLastRecordIndex());
        paramMap.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());

        try {
            List<?> memberList = memberService.selectMemberList(paramMap);

            resultInfo = HttpUtility.getSuccessResultInfo("");
            mav.addObject("data", memberList);

        } catch (Exception e) {
            resultInfo = HttpUtility.getErrorResultInfo("회원 조회에 실패 하였습니다:" + e.getMessage());
        }

        mav.addObject("result", resultInfo);
        return mav;
    }
}
