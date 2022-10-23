package com.hisco.admin.board.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

import com.hisco.admin.board.service.BoardCtgService;
import com.hisco.admin.board.vo.BoardCtgVO;
import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.tpl.service.EgovTemplateManageService;
import egovframework.com.cop.tpl.service.TemplateInf;
import egovframework.com.cop.tpl.service.TemplateInfVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시판 옵션 관리 ( 템플릿 / 카테고리 etc)
 *
 * @author 진수진
 * @since 2020.07.21
 * @version 1.0, 2020.07.21
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.21 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class BoardOptionController {

    @Resource(name = "EgovTemplateManageService")
    private EgovTemplateManageService tmplatService;

    @Resource(name = "boardCtgService")
    private BoardCtgService boardCtgService;

    /*
     * @Resource(name = "propertiesService")
     * private EgovPropertyService propertyService;
     */

    @Resource(name = "EgovCmmUseService")
    private EgovCmmUseService cmmUseService;

    /** logService */
    @Resource(name = "logService")
    private LogService logService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    // Logger log = log.getLogger(this.getClass());

    /**
     * 템플릿 목록을 조회한다.
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "게시판 템플릿 목록 조회", action = "R")
    @GetMapping(value = "/board/templateList")
    public String templateList(@ModelAttribute("searchVO") TemplateInfVO tmplatInfVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        tmplatInfVO.setPaginationInfo(paginationInfo);

        Map<String, Object> map = tmplatService.selectTemplateInfs(tmplatInfVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));

        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("searchVO", tmplatInfVO);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 템플릿에 대한 상세정보를 조회한다.
     *
     * @param templateInf
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/board/templateRegistAjax")
    public String templateRegistAjax(@ModelAttribute("templateInf") TemplateInfVO templateInf, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        ComDefaultCodeVO vo = new ComDefaultCodeVO();
        vo.setCodeId("COM005");

        List<?> result = cmmUseService.selectCmmCodeDetail(vo);

        if (templateInf.getTmplatId() != null && !templateInf.getTmplatId().equals("")) {
            TemplateInfVO dataVO = tmplatService.selectTemplateInf(templateInf);
            model.addAttribute("templateInf", dataVO);
        }

        model.addAttribute("resultList", result);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 템플릿을 등록한다.
     *
     * @param templateInf
     * @param commandMap
     * @param bindingResult
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PostMapping("/board/templateSave")
    @ResponseBody
    public ModelAndView templateSave(@ModelAttribute("templateInf") TemplateInf templateInf, CommandMap commandMap,
            BindingResult bindingResult, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        templateInf.setFrstRegisterId((user == null || user.getId() == null) ? "" : user.getId());

        beanValidator.validate(templateInf, bindingResult);

        if (bindingResult.hasErrors()) {
            resultInfo = HttpUtility.getErrorResultInfo("값이 올바르지 않습니다.");

        } else if (commandMap.getString("MODE").equals("INSERT")) {
            if (!logService.checkAdminLog(commandMap, "C", "게시판 템플릿 등록")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else {
                tmplatService.insertTemplateInf(templateInf);
                resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");
            }

        } else {
            // 수정
            if (!logService.checkAdminLog(commandMap, "U", "게시판 템플릿 수정")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else {
                tmplatService.updateTemplateInf(templateInf);
                resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
            }
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 카테고리 목록을 조회한다.
     *
     * @param boardCtgVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/board/masterCtgListAjax")
    public String masterCtgListAjax(@ModelAttribute("boardCtgVO") BoardCtgVO boardCtgVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        model.addAttribute("commandMap", commandMap);
        model.addAttribute("list", boardCtgService.selectBoardCtgList(boardCtgVO));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 카테고리 대한 상세정보를 조회한다.
     *
     * @param boardCtgVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/board/masterCtgRegistAjax")
    public String masterCtgRegistAjax(@ModelAttribute("boardCtgVO") BoardCtgVO boardCtgVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        if (boardCtgVO.getCtgId() != null && !boardCtgVO.getCtgId().equals("")) {
            BoardCtgVO dataVO = boardCtgService.selectBoardCtg(boardCtgVO);
            model.addAttribute("boardCtgVO", dataVO);
        } else {
            model.addAttribute("boardCtgVO", boardCtgVO);
        }

        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 카테고리를 등록한다.
     *
     * @param boardCtgVO
     * @param commandMap
     * @param bindingResult
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PostMapping("/board/masterCtgSave")
    @ResponseBody
    public ModelAndView masterCtgSave(@ModelAttribute("boardCtgVO") BoardCtgVO boardCtgVO, CommandMap commandMap,
            BindingResult bindingResult, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        boardCtgVO.setFrstRegisterId((user == null || user.getId() == null) ? "" : user.getId());

        beanValidator.validate(boardCtgVO, bindingResult);

        if (bindingResult.hasErrors()) {
            resultInfo = HttpUtility.getErrorResultInfo("값이 올바르지 않습니다.");

        } else if (commandMap.getString("MODE").equals("INSERT")) {
            if (!logService.checkAdminLog(commandMap, "C", "게시판 카테고리 등록")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else {
                boardCtgService.insertBoardCtg(boardCtgVO);
                resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");
            }

        } else {
            // 수정
            if (!logService.checkAdminLog(commandMap, "U", "게시판 카테고리 수정")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else {
                boardCtgService.updateBoardCtg(boardCtgVO);
                resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
            }
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 카테고리를 삭제한다.
     *
     * @param boardCtgVO
     * @param commandMap
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PostMapping("/board/masterCtgDelete")
    @ResponseBody
    public ModelAndView masterCtgDelete(@ModelAttribute("boardCtgVO") BoardCtgVO boardCtgVO, CommandMap commandMap,
            ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        if (!logService.checkAdminLog(commandMap, "D", "게시판 카테고리 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            boardCtgService.deleteBoardCtg(boardCtgVO);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

}
