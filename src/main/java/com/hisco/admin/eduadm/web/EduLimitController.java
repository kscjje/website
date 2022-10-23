package com.hisco.admin.eduadm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.eduadm.service.EduAdmService;
import com.hisco.admin.eduadm.service.EduLimitService;
import com.hisco.admin.eduadm.vo.EdcLimitVO;
import com.hisco.admin.eduadm.vo.EdcProgramVO;
import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgInfoVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 교육 프로그램 신청횟수 제한 관리
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 *          진수진 2021.10.21 노원평생학습관 수정
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class EduLimitController {

    @Resource(name = "eduAdmService")
    private EduAdmService eduAdmService;

    @Resource(name = "eduLimitService")
    private EduLimitService eduLimitService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    /**
     * 교육 프로그램 관리 목록
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "신청횟수 제한설정 목록", action = "R")
    @GetMapping("/eduadm/edcLimitList")
    public String selectEdcLimitList(@ModelAttribute("searchVO") OrgInfoVO orgInfoVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        List<OrgInfoVO> orgList = (List<OrgInfoVO>) orgInfoService.selectOrgInfoList(orgInfoVO);
        model.addAttribute("orgList", orgList);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 교육 프로그램 관리 등록화면
     *
     * @param edcLimitVO
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping("/eduadm/edcLimitRegistAjax")
    public String selectEdcLimitRegist(@ModelAttribute("edcLimitVO") EdcLimitVO edcLimitVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        // 제한 범위
        model.addAttribute("limitGbn", codeService.selectCodeList("SM_EDCRSVN_LIMITGBN"));
        // 제한 기간
        model.addAttribute("pdGbn", codeService.selectCodeList("SM_EDC_LIMIT_PDGBN"));

        if (edcLimitVO.getOrgNo() > 0) {
            EdcLimitVO edcLimitData = eduLimitService.selectEdcLimitRecord(edcLimitVO);
            if (edcLimitData != null) {
                edcLimitVO = edcLimitData;
            }
        }

        if (edcLimitVO.getEdcRsvnlimitYn() == null || edcLimitVO.getEdcRsvnlimitYn().equals("")) {
            edcLimitVO.setEdcRsvnlimitYn("N");
        }

        model.addAttribute("ctgList", eduLimitService.selectEdcLimitComctgList(edcLimitVO));
        model.addAttribute("prgmList", eduLimitService.selectEdcLimitProgramList(edcLimitVO));

        model.addAttribute("edcLimitVO", edcLimitVO);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 프로그램 제한설정 저장
     *
     * @param edcProgramVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/eduadm/edcLimitRegistSave.json")
    @PageActionInfo(title = "프로그램 제한설정 저장", action = "C", ajax = true)
    @ResponseBody
    public ModelAndView edcLimitRegistSave(@ModelAttribute("edcLimitVO") EdcLimitVO edcLimitVO,
            HttpServletRequest request, CommandMap commandMap, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        edcLimitVO.setReguser(user.getId());
        edcLimitVO.setModuser(user.getId());

        if ("2001".equals(edcLimitVO.getEdcRsvnlmitGbn())) {
            eduLimitService.insertEdcLimit(edcLimitVO, request.getParameterValues("selectedCtgcd"));
        } else {
            eduLimitService.insertEdcLimit(edcLimitVO, request.getParameterValues("selectedPrgmid"));
        }

        resultInfo = HttpUtility.getSuccessResultInfo("설정 저장 완료");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 프로그램명 검색
     *
     * @param edcProgramVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/eduadm/edcLimitProgram.json")
    @ResponseBody
    public ModelAndView edcLimitProgram(@ModelAttribute("edcProgramVO") EdcProgramVO edcProgramVO,
            HttpServletRequest request, CommandMap commandMap, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        PaginationInfo paginationInfo = commandMap.getPagingInfo();

        edcProgramVO.setEdcProgmType("1001"); // 일반교육
        edcProgramVO.setSearchOrder("prgNm");
        edcProgramVO.setSearchOrderDir("asc");
        edcProgramVO.setSearchUse("Y");
        paginationInfo.setRecordCountPerPage(1000);
/*
        List<EdcProgramVO> programList = (List<EdcProgramVO>) eduAdmService.selectEdcProgramList(edcProgramVO);
        List<HashMap> resultList = new ArrayList<HashMap>();

        if (programList != null) {
            for (EdcProgramVO data : programList) {
                HashMap map = new HashMap();
                map.put("edcPrgmNo", data.getEdcPrgmNo());
                map.put("edcPrgmnm", data.getEdcPrgmnm());
                map.put("comCtgNm", data.getComCtgNm());
                map.put("edcOpenyn", data.getEdcOpenyn());

                resultList.add(map);
            }
        }
*/
        //mav.addObject("result", resultList);
        mav.addObject("result", null);
        return mav;
    }

}
