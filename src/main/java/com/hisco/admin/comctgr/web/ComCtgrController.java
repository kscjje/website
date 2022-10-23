package com.hisco.admin.comctgr.web;

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

import com.hisco.admin.comctgr.service.ComCtgrService;
import com.hisco.admin.comctgr.vo.ComCtgrVO;
import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.JsonUtil;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * 카테고리 관리
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class ComCtgrController {

    @Resource(name = "comCtgrService")
    private ComCtgrService comCtgrService;

    @Resource(name = "logService")
    private LogService logService;

    /**
     * 프로그램 분류 조회 화면
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "프로그램 분류 조회", action = "R")
    @GetMapping("/comctgr/comCtgrList")
    public String selectComctgrList(@ModelAttribute("searchVO") ComDefaultVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        searchVO.setComcd(Config.COM_CD);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 프로그램 분류 데이타 Ajax 리턴
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping("/comctgr/comCtgrListAjax.json")
    @ResponseBody
    public ModelAndView selectComctgrListAjax(@ModelAttribute("searchVO") ComCtgrVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        searchVO.setComcd(Config.COM_CD);
        List<?> comctgrListTree = comCtgrService.selectComctgrListForTree(searchVO);
        ModelAndView mav = new ModelAndView("jsonView");

        log.debug("==selectComctgrListAjax==");
        log.debug(JsonUtil.List2String(comctgrListTree));

        mav.addObject("result", comctgrListTree);
        return mav;
    }

    /**
     * 프로그램 분류 데이타 Ajax 리턴
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping("/comctgr/comCtgrDetailAjax.json")
    @ResponseBody
    public ModelAndView selectComctgrDetailAjax(@ModelAttribute("searchVO") ComCtgrVO comCtgrVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        ComCtgrVO detailVO = comCtgrService.selectComctgrDetail(comCtgrVO);
        ModelAndView mav = new ModelAndView("jsonView");

        mav.addObject("result", detailVO);
        return mav;
    }

    /**
     * 프로그램 분류를 등록한다.
     *
     * @param comCtgrVO
     * @param commandMap
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PostMapping("/comctgr/comCtgrRegist.json")
    @ResponseBody
    public ModelAndView insertComCtgr(@ModelAttribute("comCtgrVO") ComCtgrVO comCtgrVO, CommandMap commandMap,
            ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;
  
        // 선택 데이타 정보 가져오기
        ComCtgrVO defaultVO = comCtgrService.selectComctgrDetail(comCtgrVO);

        if(defaultVO.getCtgLvl() >= 2) {
        	resultInfo = HttpUtility.getErrorResultInfo("분류는 최대 depth 2까지만 등록할 수 있습니다.");
        	mav.addObject("result", resultInfo);
            return mav;
        }
      
        if (commandMap.getString("level").equals("0")) {
            // 최상위
            comCtgrVO.setCtgLvl(0);
            comCtgrVO.setParentCtgCd("");
            comCtgrVO.setTopCtgCd("");
        } else {
            // 하위 레벨
            comCtgrVO.setCtgLvl(defaultVO.getCtgLvl() + 1);
            comCtgrVO.setParentCtgCd(defaultVO.getCtgCd());
            if (defaultVO.getCtgLvl() == 0) {
                comCtgrVO.setTopCtgCd(defaultVO.getCtgCd());
            } else {
                comCtgrVO.setTopCtgCd(defaultVO.getTopCtgCd());
            }
        }
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        comCtgrVO.setReguser(user.getId());

        if (logService.checkAdminLog(commandMap, "C", "프로그램 분류 등록")) {
            comCtgrService.insertComCtgr(comCtgrVO);
            resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");
        } else {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 프로그램 분류를 수정한다.
     *
     * @param comCtgrVO
     * @param commandMap
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PostMapping("/comctgr/comCtgrModify.json")
    @ResponseBody
    public ModelAndView updateComCtgr(@ModelAttribute("comCtgrVO") ComCtgrVO comCtgrVO, CommandMap commandMap,
            ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        comCtgrVO.setModuser(user.getId());

        if (!logService.checkAdminLog(commandMap, "U", "프로그램 분류 수정")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            comCtgrService.updateComCtgr(comCtgrVO);
            resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 프로그램 분류를 수정한다.
     *
     * @param comCtgrVO
     * @param commandMap
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PostMapping("/comctgr/comCtgrSortSave.json")
    @ResponseBody
    public ModelAndView updateComCtgrSort(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        if (!logService.checkAdminLog(commandMap, "U", "프로그램 분류 정렬 수정")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            comCtgrService.updateComCtgrSort(commandMap.getString("comcd"), request.getParameterValues("listCtgCd"), user.getId());
            resultInfo = HttpUtility.getSuccessResultInfo("순서변경이 저장 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 프로그램 분류를 삭제한다.
     *
     * @param comCtgrVO
     * @param commandMap
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PostMapping("/comctgr/comCtgrDelete.json")
    @ResponseBody
    public ModelAndView deleteComCtgr(@ModelAttribute("comCtgrVO") ComCtgrVO comCtgrVO, CommandMap commandMap,
            ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        if (!logService.checkAdminLog(commandMap, "D", "프로그램 분류 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            comCtgrService.deleteComctgr(comCtgrVO);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 프로그램 분류 조회 화면
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "프로그램 분류 조회", action = "R")
    @GetMapping("/comctgr/comCtgrPopup")
    public String selectComctgrListPopup(@ModelAttribute("searchVO") ComDefaultVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        searchVO.setComcd(Config.COM_CD);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

}
