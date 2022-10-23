package com.hisco.admin.terms.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hisco.admin.log.service.LogService;
import com.hisco.admin.terms.service.TermsService;
import com.hisco.admin.terms.vo.TermsVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 약관관리
 * 
 * @author 진수진
 * @since 2020.07.29
 * @version 1.0, 2020.07.29
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.29 최초작성
 */
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}" })
public class TermsController {
    /** logService */
    @Resource(name = "logService")
    private LogService logService;

    @Resource(name = "termsService")
    private TermsService termsService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    /**
     * 약관 목록을 조회한다.
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "약관 목록 조회", action = "R")
    @GetMapping(value = "/terms/termsList")
    public String termsList(@ModelAttribute("searchVO") TermsVO termsVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        termsVO.setPaginationInfo(paginationInfo);

        List<?> resultList = termsService.selectTermsList(termsVO);
        int totCnt = termsService.selectTermsListCnt(termsVO);

        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("resultList", resultList);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("searchVO", termsVO);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 약관 상세보기
     *
     * @param termsVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "약관 상세 조회", action = "R")
    @GetMapping("/terms/termsDetail")
    public String termsDetail(@ModelAttribute("termsVO") TermsVO termsVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        TermsVO result = termsService.selectTermsDetail(termsVO);
        model.addAttribute("result", result);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("stplatId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 약관 등록화면
     *
     * @param termsVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "약관 등록", action = "C")
    @GetMapping("/terms/termsRegist")
    public String termsRegist(@ModelAttribute("termsVO") TermsVO termsVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        // 신규등록
        model.addAttribute("mode", "add");
        model.addAttribute("termsVO", termsVO);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("stplatId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 약관 수정화면
     *
     * @param termsVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "약관 수정", action = "U")
    @GetMapping("/terms/termsUpdt")
    public String termsUpdt(@ModelAttribute("termsVO") TermsVO termsVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        // 수정
        TermsVO data = termsService.selectTermsDetail(termsVO);

        model.addAttribute("mode", "edit");
        model.addAttribute("termsVO", data);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("stplatId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/terms/termsRegist");
    }

    /**
     * 약관을 등록한다.
     *
     * @param termsVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/terms/termsSave")
    public String termsSave(@ModelAttribute("termsVO") TermsVO termsVO, HttpServletRequest request,
            HttpServletResponse response, CommandMap commandMap,
            BindingResult bindingResult,
            ModelMap model) throws Exception {
        if (commandMap.getString("mode").equals("edit")) {
            if (!logService.checkAdminLog(commandMap, "U", "약관 수정")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        } else {
            if (!logService.checkAdminLog(commandMap, "C", "약관 등록")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        }

        String result = "";
        beanValidator.validate(termsVO, bindingResult);

        if (bindingResult.hasErrors()) {

            model.addAttribute("mode", commandMap.getString("mode"));
            model.addAttribute("termsVO", termsVO);
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/terms/termsRegist");
        }

        termsVO.setReguser(commandMap.getAdminUser() != null ? commandMap.getAdminUser().getId() : "");

        // 수정인경우
        if (commandMap.getString("mode").equals("edit")) {
            int n = termsService.updateTerms(termsVO);
            if (n > 0)
                result = "OK";
        } else {
            result = termsService.insertTerms(termsVO);
        }

        if (result.equals("OK")) {
            String searchQuery = commandMap.getString("searchQuery");
            HttpUtility.sendRedirect(request, response, "처리되었습니다.", HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/terms/termsList" + searchQuery));
        } else {
            HttpUtility.sendBack(request, response, result);
        }

        return null;

    }

    /**
     * 약관 삭제
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/terms/termsDelete")
    @ResponseBody
    public ModelAndView termsDelete(@ModelAttribute("termsVO") TermsVO termsVO, HttpServletRequest request,
            CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        if (!logService.checkAdminLog(commandMap, "D", "약관 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            termsService.deleteTerms(termsVO);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }
}
