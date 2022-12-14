package com.hisco.admin.board.web;

import java.util.List;
import java.util.Map;

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

import com.hisco.admin.board.service.BoardCtgService;
import com.hisco.admin.board.service.BoardItemService;
import com.hisco.admin.board.service.BoardMasterService;
import com.hisco.admin.board.vo.BoardCtgVO;
import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovComponentChecker;
// import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.BoardItemVO;
import egovframework.com.cop.bbs.service.BoardMaster;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.EgovBBSMasterService;
import egovframework.com.cop.tpl.service.EgovTemplateManageService;
import egovframework.com.utl.fcc.service.EgovStringUtil;
//// import egovframework.rte.fdl.idgnr.EgovIdGnrService;
//// import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * ????????? ????????? ????????? ?????? ????????????
 *
 * @author ?????????
 * @since 2020.07.17
 * @version 1.0, 2020.07.17
 *          ------------------------------------------------------------------------
 *          ????????? ?????? ??????
 *          ------------------------------------------------------------------------
 *          ????????? 2020.07.17 ????????????
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class BoardMasterController {

    @Resource(name = "BoardMasterService")
    private BoardMasterService boardMasterService;

    @Resource(name = "EgovBBSMasterService")
    private EgovBBSMasterService egovBBSMasterService;

    @Resource(name = "EgovCmmUseService")
    private EgovCmmUseService cmmUseService;

    @Resource(name = "EgovTemplateManageService")
    private EgovTemplateManageService tmplatService;

    @Resource(name = "boardCtgService")
    private BoardCtgService boardCtgService;

    @Resource(name = "boardItemService")
    private BoardItemService boardItemService;

    /** logService */
    @Resource(name = "logService")
    private LogService logService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    /**
     * ????????? ????????? ????????? ????????????.
     *
     * @param boardMasterVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "?????????????????? ?????? ??????", action = "R")
    @GetMapping("/board/masterList")
    public String masterList(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        boardMasterVO.setPaginationInfo(paginationInfo);

        Map<String, Object> map = egovBBSMasterService.selectBBSMasterInfs(boardMasterVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));

        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ????????? ????????? ????????? ?????? ?????????????????? ????????????.
     *
     * @param boardMasterVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "?????????????????? ??????", action = "C")
    @GetMapping("/board/masterRegist")
    public String masterRegist(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        BoardMasterVO boardMaster = new BoardMasterVO();
        // ????????????(???????????????)
        ComDefaultCodeVO vo = new ComDefaultCodeVO();
        vo.setCodeId("COM101");
        List<?> codeResult = cmmUseService.selectCmmCodeDetail(vo);
        model.addAttribute("bbsTyCode", codeResult);

        // ????????? ??????
        model.addAttribute("templateList", tmplatService.selectTemplateWhiteList());

        // ????????????
        if (boardMasterVO.getBbsId() != null && !boardMasterVO.getBbsId().equals("")) {
            boardMaster = egovBBSMasterService.selectBBSMasterInf(boardMasterVO);

            BoardCtgVO boardCtgVO = new BoardCtgVO();
            boardCtgVO.setBbsId(boardMasterVO.getBbsId());
            model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));

            BoardItemVO boardItemVO = new BoardItemVO();
            boardItemVO.setBbsId(boardMasterVO.getBbsId());
            model.addAttribute("itemList", boardItemService.selectBoardItemList(boardItemVO));

        }

        model.addAttribute("boardMasterVO", boardMaster);

        // ---------------------------------
        // 2011.09.15 : 2?????? ?????? ?????? ?????? ?????? ??????
        // ---------------------------------

        if (EgovComponentChecker.hasComponent("EgovArticleCommentService")) {
            model.addAttribute("useComment", "true");
        }

        if (EgovComponentChecker.hasComponent("EgovBBSSatisfactionService")) {
            model.addAttribute("useSatisfaction", "true");
        }

        model.addAttribute("searchQuery", commandMap.getSearchQuery("bbsId")); // ????????? ???????????? ?????? , ??? ??????
        model.addAttribute("mode", "add");

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ????????? ????????? ????????? ????????????.
     *
     * @param boardMasterVO
     * @param boardMaster
     * @param status
     * @return
     * @throws Exception
     */
    @PostMapping("/board/masterSave")
    public String masterSave(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("searchVO") BoardMasterVO boardMasterVO,
            @ModelAttribute("boardMaster") BoardMaster boardMaster, CommandMap commandMap, BindingResult bindingResult,
            ModelMap model) throws Exception {

        // ????????????
        if (commandMap.getString("mode").equals("edit")) {
            if (!logService.checkAdminLog(commandMap, "U", "?????????????????? ??????")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        } else {
            if (!logService.checkAdminLog(commandMap, "C", "?????????????????? ??????")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        }

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        beanValidator.validate(boardMaster, bindingResult);
        if (bindingResult.hasErrors()) {
            ComDefaultCodeVO vo = new ComDefaultCodeVO();
            // ?????????????????????
            vo.setCodeId("COM101");
            List<?> codeResult = cmmUseService.selectCmmCodeDetail(vo);
            model.addAttribute("bbsTyCode", codeResult);

            // ????????? ??????
            model.addAttribute("templateList", tmplatService.selectTemplateWhiteList());

            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/board/masterRegist");
        }

        boardMaster.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getId()));
        if ((boardMasterVO == null ? "" : EgovStringUtil.isNullToString(boardMasterVO.getBlogAt())).equals("Y")) {
            boardMaster.setBlogAt("Y");
        } else {
            boardMaster.setBlogAt("N");
        }

        String resultMsg = "?????????????????????.";
        String resultUrl = Config.ADMIN_ROOT + "/board/masterList";
        // ????????????
        if (commandMap.getString("mode").equals("edit")) {
            resultMsg = "?????? ???????????????.";
            resultUrl = Config.ADMIN_ROOT + "/board/masterList" + commandMap.getString("searchQuery");
        }

        try {
            boardMasterService.masterSave(boardMaster, request, response, commandMap, model);
        } catch (Exception e) {
            resultMsg = "?????? ??????. ???????????? ???????????????.";
            e.printStackTrace();
            log.debug("================= Exception" + e.getMessage());
        }

        HttpUtility.sendRedirect(request, response, resultMsg, resultUrl);
        return null;

    }

    /**
     * ?????? ????????? ????????? ????????? ?????? ?????????????????? ????????????.
     *
     * @param boardMasterVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "?????????????????? ??????", action = "U")
    @GetMapping("/board/masterUpdt")
    public String masterUpdt(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        BoardMasterVO boardMaster = new BoardMasterVO();
        // ????????????(???????????????)
        ComDefaultCodeVO vo = new ComDefaultCodeVO();
        vo.setCodeId("COM101");
        List<?> codeResult = cmmUseService.selectCmmCodeDetail(vo);
        model.addAttribute("bbsTyCode", codeResult);

        // ????????? ??????
        model.addAttribute("templateList", tmplatService.selectTemplateWhiteList());

        // ????????????
        boardMaster = egovBBSMasterService.selectBBSMasterInf(boardMasterVO);
        model.addAttribute("boardMasterVO", boardMaster);

        // ---------------------------------
        // 2011.09.15 : 2?????? ?????? ?????? ?????? ?????? ??????
        // ---------------------------------

        if (EgovComponentChecker.hasComponent("EgovArticleCommentService")) {
            model.addAttribute("useComment", "true");
        }

        if (EgovComponentChecker.hasComponent("EgovBBSSatisfactionService")) {
            model.addAttribute("useSatisfaction", "true");
        }

        model.addAttribute("searchQuery", commandMap.getSearchQuery("bbsId")); // ????????? ???????????? ?????? , ??? ??????
        model.addAttribute("mode", "edit");

        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(boardMasterVO.getBbsId());
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));

        BoardItemVO boardItemVO = new BoardItemVO();
        boardItemVO.setBbsId(boardMasterVO.getBbsId());
        model.addAttribute("itemList", boardItemService.selectBoardItemList(boardItemVO));

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/board/masterRegist");
    }

    /**
     * ????????? ????????? ??????????????? ????????????.
     *
     * @param boardMasterVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "?????????????????? ?????? ??????", action = "R")
    @GetMapping("/board/masterDetail")
    public String masterDetail(@ModelAttribute("searchVO") BoardMasterVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        if (!logService.checkAdminLog(commandMap, "R", "?????????????????? ?????? ??????")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        BoardMasterVO vo = egovBBSMasterService.selectBBSMasterInf(searchVO);
        model.addAttribute("result", vo);

        // ---------------------------------
        // 2011.09.15 : 2?????? ?????? ?????? ?????? ?????? ??????
        // ---------------------------------

        if (EgovComponentChecker.hasComponent("EgovArticleCommentService")) {
            model.addAttribute("useComment", "true");
        }

        if (EgovComponentChecker.hasComponent("EgovBBSSatisfactionService")) {
            model.addAttribute("useSatisfaction", "true");
        }

        model.addAttribute("commandMap", commandMap);
        model.addAttribute("listQuery", commandMap.getSearchQuery("bbsId")); // bbsId ??? ????????? ????????????

        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(vo.getBbsId());
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));

        BoardItemVO boardItemVO = new BoardItemVO();
        boardItemVO.setBbsId(vo.getBbsId());
        model.addAttribute("itemList", boardItemService.selectBoardItemList(boardItemVO));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ????????? ????????? ????????? ????????????.
     *
     * @param boardMasterVO
     * @param boardMaster
     * @param status
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "?????????????????? ??????", action = "D")
    @GetMapping("/board/masterDelete")
    public String masterDelete(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap,
            @ModelAttribute("searchVO") BoardMasterVO boardMasterVO,
            @ModelAttribute("boardMaster") BoardMaster boardMaster) throws Exception {

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        if (!logService.checkAdminLog(commandMap, "D", "?????????????????? ??????")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        } else {
            boardMaster.setLastUpdusrId(user == null ? "" : EgovStringUtil.isNullToString(user.getId()));
            egovBBSMasterService.deleteBBSMasterInf(boardMaster);
            HttpUtility.sendRedirect(request, response, "?????????????????????.", Config.ADMIN_ROOT + "/board/masterList");
        }
        // status.setComplete();
        return null;

    }

    /**
     * ????????? ?????? ??????
     *
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/board/masterIdCheckAjax")
    @ResponseBody
    public ModelAndView masterIdCheckAjax(CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        BoardMasterVO searchVO = new BoardMasterVO();
        searchVO.setBbsId(commandMap.getString("bbsId"));

        if (commandMap.getString("bbsId").equals("")) {
            resultInfo = HttpUtility.getErrorResultInfo("?????? ???????????? ????????????.");
        } else {
            BoardMasterVO vo = egovBBSMasterService.selectBBSMasterInf(searchVO);
            if (vo != null) {
                resultInfo = HttpUtility.getErrorResultInfo("?????? ???????????? ????????? ?????????.");
            } else {
                resultInfo = HttpUtility.getSuccessResultInfo(commandMap.getString("bbsId"));
            }
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

}
