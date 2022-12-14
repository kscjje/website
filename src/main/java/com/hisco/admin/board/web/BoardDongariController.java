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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hisco.admin.area.service.AreaCdService;
import com.hisco.admin.board.service.BoardCtgService;
import com.hisco.admin.board.service.BoardItemService;
import com.hisco.admin.board.vo.BoardCtgVO;
import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.StringUtil;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.Board;
import egovframework.com.cop.bbs.service.BoardItemVO;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import egovframework.com.cop.bbs.service.EgovBBSMasterService;
import egovframework.com.cop.cmt.service.EgovArticleCommentService;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * ??????????????? ?????? ?????????
 *
 * @author ?????????
 * @since 2021.12.01
 * @version 1.0, 2021.12.01
 *          ------------------------------------------------------------------------
 *          ????????? ?????? ??????
 *          ------------------------------------------------------------------------
 *          ????????? 2021.12.01 ????????????
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class BoardDongariController {

    @Resource(name = "EgovArticleService")
    private EgovArticleService egovArticleService;

    @Resource(name = "EgovBBSMasterService")
    private EgovBBSMasterService egovBBSMasterService;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "boardCtgService")
    private BoardCtgService boardCtgService;

    @Resource(name = "boardItemService")
    private BoardItemService boardItemService;

    @Resource(name = "EgovArticleCommentService")
    private EgovArticleCommentService egovArticleCommentService;

    @Resource(name = "egovNttIdGnrService")
    private EgovIdGnrService nttIdgenService;

    @Resource(name = "areaCdService")
    private AreaCdService areaCdService;

    /** logService */
    @Resource(name = "logService")
    private LogService logService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    private String bbsId = "dongari";

    /**
     * XSS ?????? ??????.
     *
     * @param data
     * @return
     */
    protected String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }

        String ret = data;

        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");

        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");

        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");

        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");

        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

        return ret;
    }

    /**
     * ?????????????????? ?????? ????????? ????????????.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "????????? ??????", action = "R")
    @GetMapping({ "/dongari/{type}/list", "/dongari/{type}/list" })
    public String articleList(@PathVariable String type, @ModelAttribute("searchVO") BoardVO boardVO,
            CommandMap commandMap, ModelMap model) throws Exception {

        String bbsId = this.bbsId; // dongari

        // tpye ?????? ?????? ?????? ?????? ?????? ??????
        if (!type.equals("open") && !type.equals("approval")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        log.debug("========= call selectArticleList();");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        BoardMasterVO vo = new BoardMasterVO();
        vo.setBbsId(bbsId);
        BoardMasterVO master = egovBBSMasterService.selectBBSMasterInf(vo);

        if (!logService.checkAdminLog(commandMap, "R", master.getBbsNm() + " ??????")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
        boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        boardVO.setBbsTyCode(master.getBbsTyCode());
        boardVO.setBbsId(bbsId);

        // ?????? ?????? ??????
        // if (boardVO.getNtceStat() == null) {
        // boardVO.setNtceStat("2");
        // if (type.equals("approval")) {
        // boardVO.setNtceStat("13");
        // }
        // }

        Map<String, Object> map = egovArticleService.selectArticleNewList(boardVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));

        paginationInfo.setTotalRecordCount(totCnt);

        if (user != null) {
            model.addAttribute("sessionUniqId", user.getId());
        }

        // ???????????? ??????
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));

        model.addAttribute("bbsId", bbsId);
        model.addAttribute("type", type);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("articleVO", boardVO);
        model.addAttribute("boardMasterVO", master);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));

        log.debug("model = {}", model);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/dongari/articleList");

    }

    /**
     * ??????????????? ????????? ?????? ?????????????????? ????????????.
     *
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "??????????????? ??????", action = "C")
    @GetMapping({ "/dongari/{type}/regist", "/dongari/{type}/regist" })
    public String articleRegist(@PathVariable String type, @ModelAttribute("searchVO") BoardVO boardVO,
            CommandMap commandMap, ModelMap model) throws Exception {

        // ???????????? ?????? ??????
        if (true) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        String bbsId = this.bbsId; // dongari

        // tpye ?????? ?????? ?????? ?????? ?????? ??????
        if (!type.equals("open") && !type.equals("approval")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        log.debug("========= ======== articleRegist:" + bbsId);

        BoardMasterVO bdMstr = new BoardMasterVO();
        boardVO.setBbsId(bbsId);

        BoardMasterVO vo = new BoardMasterVO();
        vo.setBbsId(boardVO.getBbsId());
        bdMstr = egovBBSMasterService.selectBBSMasterInf(vo);

        // ???????????? ??????
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");

        model.addAttribute("bbsId", bbsId);
        model.addAttribute("type", type);
        model.addAttribute("articleVO", boardVO);
        model.addAttribute("boardMasterVO", bdMstr);

        model.addAttribute("searchQuery", commandMap.getSearchQuery("nttId")); // ????????? ???????????? ?????? , ??? ??????
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));

        BoardItemVO boardItemVO = new BoardItemVO();
        boardItemVO.setBbsId(bbsId);
        model.addAttribute("itemList", boardItemService.selectBoardItemList(boardItemVO));

        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));

        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/dongari/articleRegist");

    }

    /**
     * ?????????????????? ?????? ?????? ????????? ????????????.
     *
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "????????? ??????", action = "R")
    @GetMapping({ "/dongari/{type}/view/{nttId}", "/dongari/{type}/view/{nttId}" })
    public String articleView(@PathVariable String type, @PathVariable String nttId,
            @ModelAttribute("searchVO") BoardVO boardVO, CommandMap commandMap, ModelMap model) throws Exception {

        String bbsId = this.bbsId; // dongari

        // tpye ?????? ?????? ?????? ?????? ?????? ??????
        if (!type.equals("open") && !type.equals("approval")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        boardVO.setBbsId(bbsId);
        boardVO.setNttId(Long.parseLong(nttId));
        BoardVO vo = egovArticleService.selectArticleDetail(boardVO);

        model.addAttribute("result", vo);
        model.addAttribute("articleVO", vo);
        model.addAttribute("sessionUniqId", (user == null || user.getId() == null) ? "" : user.getId());

        BoardMasterVO master = new BoardMasterVO();

        master.setBbsId(boardVO.getBbsId());
        BoardMasterVO masterVo = egovBBSMasterService.selectBBSMasterInf(master);

        if (!logService.checkAdminLog(commandMap, "R", masterVo.getBbsNm() + " ??????")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        // ??????????????????
        BoardItemVO boardItemVO = new BoardItemVO();
        boardItemVO.setBbsId(bbsId);
        List<BoardItemVO> itemList = boardItemService.selectBoardItemList(boardItemVO);
        model.addAttribute("itemList", itemList);

        // ?????????????????? - ????????????
        if (boardVO.getNttId() > 0 && itemList.size() > 0) {
            List<BoardItemVO> itemInfoList = egovArticleService.selectArticleDetailItemList(boardVO);
            model.addAttribute("itemInfoList", itemInfoList);
        }

        model.addAttribute("bbsId", bbsId);
        model.addAttribute("type", type);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("bbsId")); // ????????? ???????????? ?????? , ??? ??????
        model.addAttribute("boardMasterVO", masterVo);
        model.addAttribute("commandMap", commandMap);

        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/dongari/articleView");
    }

    /**
     * ?????????????????? ????????????.
     *
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping({ "/dongari/{type}/save", "/dongari/{type}/save" })
    public String articleSave(@PathVariable String type, final MultipartHttpServletRequest multiRequest,
            HttpServletResponse response,
            CommandMap commandMap, @ModelAttribute("articleVO") BoardVO articleVO,
            BindingResult bindingResult, ModelMap model) throws Exception {

        String bbsId = this.bbsId; // dongari

        // tpye ?????? ?????? ?????? ?????? ?????? ??????
        if (!type.equals("open") && !type.equals("approval")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        log.debug("call insertArticle();");
        log.debug("articleVO => " + articleVO.getNttId());

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap => " + paramMap);

        log.debug("articleVO :: getNttCn : before => " + articleVO.getNttCn());

        // String strNttCn = StringUtil.URLDecode(articleVO.getNttCn(), "UTF-8");
        String strNttCn = articleVO.getNttCn();
        strNttCn = strNttCn.replace("&lt;", "<").replace("&gt;", ">");
        articleVO.setNttCn(strNttCn);

        log.debug("articleVO :: getNttCn : after => " + strNttCn);

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        articleVO.setBbsId(bbsId);

        String atchFileId = "";
        final Map<String, MultipartFile> files = multiRequest.getFileMap();

        BoardMasterVO master = new BoardMasterVO();

        master.setBbsId(articleVO.getBbsId());
        master = egovBBSMasterService.selectBBSMasterInf(master);
        model.addAttribute("boardMasterVO", master);

        log.debug("articleVO getNttId() = " + articleVO.getNttId());

        // ???????????????
        if (articleVO.getNttId() > 0) {

            // step1 DB?????? ?????? ?????????????????? uniqId ??????

            BoardVO vo = egovArticleService.selectArticleDetailOne(articleVO);

            /*
             * ?????????????????? skip
             * // @ XSS ?????? ???????????? ?????? START
             * // param1 : ???????????????ID(uniqId,esntlId)
             * log.debug("@ XSS ???????????? START ----------------------------------------------");
             * //step2 EgovXssChecker ??????????????? ????????? ????????????
             * EgovXssChecker.checkerUserXss(multiRequest, vo.getFrstRegisterId());
             * log.debug("@ XSS ???????????? END ------------------------------------------------");
             * //--------------------------------------------------------
             * // @ XSS ?????? ???????????? ?????? END
             * //--------------------------------------------------------------------------------------------
             */

            atchFileId = vo.getAtchFileId(); // ?????? ?????? ????????? ??????
        }

        if (articleVO.getNttId() > 0) {
            if (!logService.checkAdminLog(commandMap, "U", master.getBbsNm() + " ??????")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        } else {
            if (!logService.checkAdminLog(commandMap, "C", master.getBbsNm() + " ??????")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        }

        model.addAttribute("commandMap", commandMap);
        beanValidator.validate(articleVO, bindingResult);

        if (bindingResult.hasErrors()) {

            for (FieldError err : bindingResult.getFieldErrors()) {
                log.debug("Binding Error : {} [{}]", err.getField(), err.getDefaultMessage());
            }
            // ???????????? ??????
            BoardCtgVO boardCtgVO = new BoardCtgVO();
            boardCtgVO.setBbsId(bbsId);
            boardCtgVO.setUseAt("Y");
            model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));

            //// -----------------------------

            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/board/articleRegist");
        }

        articleVO.setFrstRegisterId((user == null || user.getId() == null) ? "" : user.getId());
        if (!files.isEmpty()) {
            try {
                if ("".equals(atchFileId)) {
                    List<FileVO> result = fileUtil.parseFileInf(multiRequest, "DONGARI_", 0, atchFileId, "", articleVO.getFrstRegisterId());
                    atchFileId = fileMngService.insertFileInfs(result);
                    articleVO.setAtchFileId(atchFileId);
                } else {
                    FileVO fvo = new FileVO();
                    fvo.setFileGrpinnb(atchFileId);
                    int cnt = fileMngService.getMaxFileSN(fvo);
                    List<FileVO> result = fileUtil.parseFileInf(multiRequest, "DONGARI_", cnt, atchFileId, "", articleVO.getFrstRegisterId());
                    fileMngService.updateFileInfs(result);
                }
            } catch (Exception e) {
                // status.setComplete();
                HttpUtility.sendBack(multiRequest, response, e.getMessage());

                return null;
            }
        }

        articleVO.setAtchFileId(atchFileId);

        // ???????????????
        if (articleVO.getNttId() > 0) {
            articleVO.setLastUpdusrId((user == null || user.getId() == null) ? "" : user.getId());
            articleVO.setNtcrNm(""); // dummy ?????? ?????? (????????? ?????? ?????? validator ????????? ?????? dummy??? ?????????)
            articleVO.setPassword(""); // dummy ?????? ?????? (????????? ?????? ?????? validator ????????? ?????? dummy??? ?????????)

            egovArticleService.updateArticle(articleVO, multiRequest.getParameter("fileDeleteList"));

        } else {

            long nttId = nttIdgenService.getNextIntegerId();
            articleVO.setNttId(nttId);
            egovArticleService.insertArticle(articleVO);
        }

        // 2021.11.24 ???????????? ??????
        BoardItemVO boardItemVO = new BoardItemVO();
        boardItemVO.setBbsId(articleVO.getBbsId());
        List boardItemList = boardItemService.selectBoardItemList(boardItemVO);
        if (boardItemList.size() > 0) {
            // ?????? ?????? ?????? ??? ?????? ??????
            boardItemVO.setNttId(articleVO.getNttId());
            egovArticleService.deleteBbsItemInfoByNttId(boardItemVO);

            for (int i = 0; i < boardItemList.size(); i++) {
                BoardItemVO itemVO = (BoardItemVO) boardItemList.get(i);
                String key = itemVO.getBbsItemEnid();
                String itemType = itemVO.getBbsItemType();
                String value = commandMap.getString(key);
                if ("2001".equals(itemType)) {
                    itemVO.setBbsItemLvalue(value);
                } else {
                    itemVO.setBbsItemSvalue(value);
                }
                itemVO.setNttId(articleVO.getNttId());
                egovArticleService.insertBbsItemInfo(itemVO);
            }
        }

        // status.setComplete();
        HttpUtility.sendRedirect(multiRequest, response, "?????????????????????.", Config.ADMIN_ROOT + "/dongari/" + type + "/list" + commandMap.getString("searchQuery"));

        return null;

    }

    /**
     * ??????????????? ????????? ?????? ?????????????????? ????????????.
     *
     * @param boardVO
     * @param vo
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "????????? ??????", action = "U")
    @GetMapping({ "/dongari/{type}/modify", "/dongari/{type}/modify" })
    public String articleModify(@PathVariable String type, @ModelAttribute("boardVO") BoardVO boardVO,
            CommandMap commandMap, ModelMap model)
            throws Exception {

        String bbsId = this.bbsId; // dongari

        // tpye ?????? ?????? ?????? ?????? ?????? ??????
        if (!type.equals("open") && !type.equals("approval")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        boardVO.setBbsId(bbsId);

        BoardMasterVO bmvo = new BoardMasterVO();
        bmvo.setBbsId(boardVO.getBbsId());
        bmvo = egovBBSMasterService.selectBBSMasterInf(bmvo);

        BoardVO bdvo = new BoardVO();
        bdvo = egovArticleService.selectArticleDetail(boardVO);

        // ???????????? ??????
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));

        // ??????????????????
        BoardItemVO boardItemVO = new BoardItemVO();
        boardItemVO.setBbsId(bbsId);
        List<BoardItemVO> itemList = boardItemService.selectBoardItemList(boardItemVO);
        model.addAttribute("itemList", itemList);

        // ?????????????????? - ????????????
        if (boardVO.getNttId() > 0 && itemList.size() > 0) {
            List<BoardItemVO> itemInfoList = egovArticleService.selectArticleDetailItemList(boardVO);
            model.addAttribute("itemInfoList", itemInfoList);
        }

        model.addAttribute("bbsId", bbsId);
        model.addAttribute("type", type);
        model.addAttribute("articleVO", bdvo);
        model.addAttribute("boardMasterVO", bmvo);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("nttId")); // ????????? ???????????? ?????? , ??? ??????

        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/dongari/articleRegist");

    }

    /**
     * ?????????????????? ?????? ????????? ????????????. use_at = 'N'
     *
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/dongari/{type}/delete")
    @ResponseBody
    public ModelAndView articleDelete(@PathVariable String type, HttpServletRequest request,
            @ModelAttribute("board") Board board,
            CommandMap commandMap, ModelMap model) throws Exception {

        String bbsId = this.bbsId; // dongari

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        // ?????????????????? skip--------------------------------------------------------------------------------------------
        // @ XSS ?????? ???????????? ?????? START
        // param1 : ???????????????ID(uniqId,esntlId)
        // --------------------------------------------------------
        /*
         * log.debug("@ XSS ???????????? START  ----------------------------------------------");
         * //step1 DB?????? ?????? ?????????????????? uniqId ??????
         * BoardVO vo = egovArticleService.selectArticleDetail(boardVO);
         * //step2 EgovXssChecker ??????????????? ????????? ????????????
         * // ?????????????????? ??????
         * //EgovXssChecker.checkerUserXss(request, vo.getFrstRegisterId());
         * log.debug("@ XSS ???????????? END ------------------------------------------------");
         * //--------------------------------------------------------
         * // @ XSS ?????? ???????????? ?????? END
         * //--------------------------------------------------------------------------------------------
         * BoardVO bdvo = egovArticleService.selectArticleDetail(boardVO);
         * //?????? ???????????? ?????? ?????? ??????
         * if (bdvo.getNtcrId().equals("anonymous")) {
         * model.addAttribute("result", bdvo);
         * model.addAttribute("boardMasterVO", bdMstr);
         * }
         */

        if (logService.checkAdminLog(commandMap, "D", "????????? ??????")) {
            board.setLastUpdusrId((user == null || user.getId() == null) ? "" : user.getId());
            board.setBbsId(bbsId);
            egovArticleService.deleteArticle(board);
            resultInfo = HttpUtility.getSuccessResultInfo("?????? ???????????????.");

        } else {
            resultInfo = HttpUtility.getErrorResultInfo("?????? ??????");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * ?????????????????? ?????? ????????? DB ?????? ????????????.
     *
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/dongari/{type}/deleteAll")
    @ResponseBody
    public ModelAndView articleDeleteAll(@PathVariable String type, HttpServletRequest request,
            @ModelAttribute("board") Board board,
            CommandMap commandMap, ModelMap model) throws Exception {

        String bbsId = this.bbsId; // dongari

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        if (logService.checkAdminLog(commandMap, "D", "????????? DB??????")) {
            board.setBbsId(bbsId);
            egovArticleService.deleteArticleComplete(board);
            resultInfo = HttpUtility.getSuccessResultInfo("?????? ???????????????.");

        } else {
            resultInfo = HttpUtility.getErrorResultInfo("?????? ??????");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * ??????????????? ?????? ??????
     */
    @PostMapping({ "/dongari/{type}/statUpdate", "/dongari/{type}/statUpdate" })
    @ResponseBody
    public ModelAndView statUpdate(@PathVariable String type,
            HttpServletResponse response, HttpServletRequest request,
            CommandMap commandMap, @ModelAttribute("articleVO") BoardVO articleVO,
            BindingResult bindingResult, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        // ??????
        if (!logService.checkAdminLog(commandMap, "U", "???????????? ?????? ??????")) {
            resultInfo = HttpUtility.getErrorResultInfo("????????? ????????????.");
        } else {
            String bbsId = this.bbsId; // dongari
            articleVO.setBbsId(bbsId);
            articleVO.setLastUpdusrId(user.getId());

            String[] nttIds = request.getParameterValues("nttId");
            if (nttIds.length > 0) {
                for (int i = 0; i < nttIds.length; i++) {
                    articleVO.setNttId(StringUtil.String2Long(nttIds[i], 0));
                    log.debug("Binding Error : {} [{}]", i, nttIds[i]);
                    egovArticleService.updateStatArticle(articleVO);

                }
            }
            resultInfo = HttpUtility.getSuccessResultInfo("?????? ???????????????.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }
}
