package com.hisco.user.orgintro.web;

import java.util.ArrayList;
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
import com.hisco.admin.area.vo.AreaCdVO;
import com.hisco.admin.board.service.BoardCtgService;
import com.hisco.admin.board.service.BoardItemService;
import com.hisco.admin.board.vo.BoardCtgVO;
import com.hisco.admin.contents.service.ContentsService;
import com.hisco.admin.contents.vo.ContentsVO;
import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgContentsVO;
import com.hisco.admin.orginfo.vo.OrgInfoVO;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.CodeVO;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cop.bbs.service.BoardItemVO;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import egovframework.com.cop.bbs.service.EgovBBSMasterService;
import egovframework.com.sym.mnu.mpm.service.EgovMenuManageService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/orgintro")
public class OrgintroController {

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "contentsService")
    private ContentsService contentsService;

    /** EgovMenuManageService */
    @Resource(name = "meunManageService")
    private EgovMenuManageService menuManageService;

    @Resource(name = "EgovArticleService")
    private EgovArticleService egovArticleService;

    @Resource(name = "EgovBBSMasterService")
    private EgovBBSMasterService egovBBSMasterService;

    @Resource(name = "boardCtgService")
    private BoardCtgService boardCtgService;

    @Resource(name = "boardItemService")
    private BoardItemService boardItemService;

    @Resource(name = "areaCdService")
    private AreaCdService areaCdService;

    //// @Resource(name = "EgovArticleCommentService")
    //// private EgovArticleCommentService egovArticleCommentService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    private String bbsId = "dongari";

    /**
     * ?????? ??????
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/{orgType}")
    public String list(@PathVariable String orgType,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        commandMap.put("comcd", Config.COM_CD);
        commandMap.put("orgLtype", orgType);

        if ("9001".equals(orgType)) {
            // ?????? ????????? ?????? ???????????? ????????????
            List<CodeVO> codeList = codeService.selectCodeList("SM_ORG_MTYPE");
            List<CodeVO> newCodeList = new ArrayList<CodeVO>();

            String selectedCoe = commandMap.getString("orgMtype");
            if (codeList != null) {
                for (CodeVO codeVO : codeList) {
                    if (orgType.equals(codeVO.getItem1())) {
                        newCodeList.add(codeVO);

                        // ????????? ????????? ??????
                        if (selectedCoe.equals("")) {
                            selectedCoe = codeVO.getCd();
                        }
                        if (selectedCoe.equals(codeVO.getCd())) {
                            model.addAttribute("codeDetail", codeVO);
                            commandMap.put("orgMtype", selectedCoe); // ???????????? ??????????????? ?????????
                        }
                    }
                }
            }

            model.addAttribute("orgTypeList", newCodeList);
        }

        commandMap.put("orgCntsactiveyn", "Y"); //???????????? Y ?????????
        List<OrgInfoVO> orgList = orgInfoService.selectOrgInfoUserList(commandMap.getParam());
        String orgNo = commandMap.getString("orgNo");

        if ("9001".equals(orgType)) {
            // ?????? ????????? ?????? ?????? ????????? ??????

        } else {
            ContentsVO contentsVO = null; // ????????? ????????? ????????? ?????????

            // ????????? ????????? ????????? ????????????
            if (orgNo.equals("") || orgNo.equals("all")) {
                MenuManageVO menuVO = commandMap.getSelectedMenu();
                commandMap.put("menuNo", menuVO.getMenuNo());

                contentsVO = contentsService.selectContentsDetail(commandMap.getParam());
            }

            if (contentsVO != null && contentsVO.getContentsSeq() > 0) {
                // ????????? ????????? ????????? ?????? ?????? ?????? ????????? ????????? ????????????
                model.addAttribute("contentsVO", contentsVO);
                // ????????? ?????? ?????? ??????
                model.addAttribute("editAuthYn", contentsService.contentEditFlag(request));

                // ???????????? ?????? ????????? ??????
                if (!"".equals(contentsVO.getFileId2())) {
                    FileVO fileVO = new FileVO();
                    fileVO.setFileGrpinnb(contentsVO.getFileId2());
                    model.addAttribute("fileList2", fileMngService.selectFileInfs(fileVO));
                }

            }

            // ????????????????????? ?????? ?????? ???????????? ???????????? ?????? ????????? ????????? ?????? ?????? ?????????
            if (orgList != null && orgList.size() > 0 && orgNo.equals("") && !orgType.equals("3001")) {
                orgNo = String.valueOf(orgList.get(0).getOrgNo());
            }
            if (orgNo != null && !orgNo.equals("")) {
                // ?????? ????????? ????????? ?????? ?????? ?????? ????????????
                OrgContentsVO orgContentsVO = new OrgContentsVO();
                orgContentsVO.setComcd(Config.COM_CD);
                orgContentsVO.setOrgNo(Integer.parseInt(orgNo));
                orgContentsVO = orgInfoService.selectOrgContents(orgContentsVO);

                model.addAttribute("orgDetail", orgInfoService.selectOrgInfoDetail(Config.COM_CD, Integer.parseInt(orgNo)));
                model.addAttribute("orgContentsVO", orgContentsVO);
            }

        }

        model.addAttribute("orgList", orgList);
        model.addAttribute("orgNo", orgNo);
        model.addAttribute("orgType", orgType);
        model.addAttribute("webDir", Config.USER_ROOT);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/orgintro/orgMain");
    }

    /**
     * ?????? ????????? json ????????? ??????
     *
     * @param orgType
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping(value = "/orgList/{orgType}.json")
    @ResponseBody
    public ModelAndView contentUpdate(@PathVariable String orgType, CommandMap commandMap,
            HttpServletResponse response, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        commandMap.put("orgLtype", orgType);
        commandMap.put("comcd", Config.COM_CD);
        List<OrgInfoVO> orgList = orgInfoService.selectOrgInfoUserList(commandMap.getParam());
        mav.addObject("list", orgList);
        return mav;

    }

    /**
     * ?????? ??????
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/location")
    public String location(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model)
            throws Exception {

        // ??????
        List<AreaCdVO> areaList = (List<AreaCdVO>) areaCdService.selectAreaCdList(new AreaCdVO());
        model.addAttribute("areaList", areaList);

        OrgInfoVO searchVO = new OrgInfoVO();
        searchVO.setSearchCondition("location");
        List<OrgInfoVO> list = (List<OrgInfoVO>) orgInfoService.selectOrgInfoList(searchVO);
        model.addAttribute("orgList", list);

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping("/locationAjax")
    public String listAjax(@ModelAttribute("searchVO") OrgInfoVO searchVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);

        int totCnt = 0;
        searchVO.setComcd(Config.COM_CD);
        List<OrgInfoVO> list = (List<OrgInfoVO>) orgInfoService.selectOrgInfoListPaging(searchVO);

        if (list != null && list.size() > 0) {
            totCnt = list.get(0).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        model.addAttribute("list", list);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ??????????????? ??????
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/dongari/list")
    public String dongariList(@ModelAttribute("searchVO") BoardVO boardVO,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        String orgType = "dongari";
        commandMap.put("comcd", Config.COM_CD);
        commandMap.put("orgLtype", orgType);

        // ????????? ????????? ????????? ????????????
        MenuManageVO menuVO = commandMap.getSelectedMenu();
        commandMap.put("menuNo", menuVO.getMenuNo());

        ContentsVO contentsVO = contentsService.selectContentsDetail(commandMap.getParam());
        if (contentsVO != null && contentsVO.getContentsSeq() > 0) {
            // ????????? ????????? ????????? ?????? ?????? ?????? ????????? ????????? ????????????
            model.addAttribute("contentsVO", contentsVO);
        }

        model.addAttribute("orgType", orgType);
        model.addAttribute("webDir", Config.USER_ROOT);

        /***************** ?????? ????????? ????????? ?????? ?????? *******************/

        String bbsId = this.bbsId; // dongari

        log.debug("call selectBoardList()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        BoardMasterVO vo = new BoardMasterVO();

        vo.setBbsId(bbsId);
        vo.setUseAt("Y");
        BoardMasterVO master = egovBBSMasterService.selectBBSMasterInf(vo);

        if (master == null) {
            // bbsId ??? ???????????? ?????? ???????????? ?????????.
            HttpUtility.sendBack(request, response, "???????????????(?????????) ????????? ?????? ?????? ????????????.");
            return null;
        }

        log.debug("----------------------------------------------------.S");
        log.debug("selectBoardList :: master = " + master);
        log.debug("master.getTmplatCours()" + master);
        log.debug(master.getTmplatCours());
        log.debug("----------------------------------------------------.E");

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setRecordCountPerPage(6);
        boardVO.setBbsId(bbsId);
        boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
        boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        boardVO.setUseAt("Y");
        boardVO.setBbsTyCode(master.getBbsTyCode());
        boardVO.setNtceStat("2"); // ????????? ?????? ?????????

        Map<String, Object> map = egovArticleService.selectArticleNewList(boardVO);
        int intTotCnt = Integer.parseInt((String) map.get("resultCnt"));

        log.debug("intTotCnt = " + intTotCnt);

        // ???????????? ??????
        // List<BoardVO> noticeList = egovArticleService.selectNoticeArticleNewList(boardVO);

        paginationInfo.setTotalRecordCount(intTotCnt);

        String searchQuery = commandMap.getSearchQuery("nttId");
        if (searchQuery.length() > 0)
            searchQuery = "&" + searchQuery.substring(1);

        // ???????????? ??????
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));

        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("articleVO", boardVO);
        model.addAttribute("boardMasterVO", master);
        model.addAttribute("paginationInfo", paginationInfo);
        // model.addAttribute("noticeList", noticeList);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("searchQuery", searchQuery); // ????????? ???????????? ?????? , ??? ??????

        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/orgintro/dongariList");
    }

    /**
     * ??????????????? ??????
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/dongari/write")
    public String dongariWrite(
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        if (commandMap.getUserInfo() == null) {
            HttpUtility.sendRedirect(request, response, "????????? ??? ???????????? ??? ????????????.", Config.USER_ROOT + "/member/login?returnURL=" + request.getAttribute("returnURL"));
            return null;
        } else if (!commandMap.getUserInfo().isMember()) {
            HttpUtility.sendBack(request, response, "???????????? ?????? ?????? ??? ????????????. ?????? ????????? ??? ????????? ?????????.");
            return null;
        }

        String orgType = "dongari";
        commandMap.put("comcd", Config.COM_CD);
        commandMap.put("orgLtype", orgType);

        String bbsId = this.bbsId; // dongari
        BoardVO articleVO = new BoardVO();

        LoginVO user = commandMap.getUserInfo();

        BoardMasterVO master = new BoardMasterVO();
        articleVO.setBbsId(bbsId);
        articleVO.setUseAt("Y");
        articleVO.setFrstRegisterId(user.getId());
        articleVO.setNtcrId(user.getId()); // ????????? ?????????
        articleVO.setNtcrNm(user.getName()); // ????????? ??????

        // ??????????????????
        BoardItemVO boardItemVO = new BoardItemVO();
        boardItemVO.setBbsId(bbsId);
        List<BoardItemVO> itemList = boardItemService.selectBoardItemList(boardItemVO);
        model.addAttribute("itemList", itemList);

        BoardVO dataVO = new BoardVO();

        // ???????????????
        if (articleVO.getNttId() > 0) {
            // step1 DB?????? ?????? ???????????? uniqId ??????
            dataVO = egovArticleService.selectArticleDetail(articleVO);

            // ????????? ???????????? ????????? ?????? ????????? ??????
            if (!dataVO.getNtcrId().equals(articleVO.getNtcrId())) {
                HttpUtility.sendBack(request, response, "???????????? ???????????? ??? ????????????.");
            }

            model.addAttribute("articleVO", dataVO);

            // ?????????????????? - ????????????
            if (dataVO.getNttId() > 0 && itemList.size() > 0) {
                List<BoardItemVO> itemInfoList = egovArticleService.selectArticleDetailItemList(dataVO);
                model.addAttribute("itemInfoList", itemInfoList);
            }
        } else {
            model.addAttribute("articleVO", articleVO);
        }

        BoardMasterVO vo = new BoardMasterVO();
        vo.setBbsId(articleVO.getBbsId());
        vo.setUseAt("Y");
        master = egovBBSMasterService.selectBBSMasterInf(vo);

        // ???????????? ??????
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");

        model.addAttribute("boardMasterVO", master);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("nttId")); // ????????? ???????????? ?????? , ??? ??????
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));
        model.addAttribute("userInfo", commandMap.getUserInfo());

        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));

        log.debug("model = " + model);

        model.addAttribute("webDir", Config.USER_ROOT);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/orgintro/dongariWrite");
    }

    /**
     * ??????????????? ??????
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/dongari/modify")
    public String dongariModify(@ModelAttribute("boardVO") BoardVO boardVO,
            HttpServletRequest request, HttpServletResponse response,
            CommandMap commandMap, ModelMap model)
            throws Exception {

        String bbsId = this.bbsId; // dongari

        LoginVO user = commandMap.getUserInfo();
        boardVO.setBbsId(bbsId);

        BoardMasterVO bmvo = new BoardMasterVO();
        bmvo.setBbsId(boardVO.getBbsId());
        bmvo = egovBBSMasterService.selectBBSMasterInf(bmvo);

        BoardVO bdvo = new BoardVO();
        bdvo = egovArticleService.selectArticleDetail(boardVO);

        // ???????????????
        if (bdvo.getNttId() < 0) {
            HttpUtility.sendBack(request, response, "????????? ???????????????.");
        }

        // ????????? ???????????? ????????? ?????? ????????? ??????
        if (!user.getId().equals(bdvo.getNtcrId())) {
            HttpUtility.sendBack(request, response, "???????????? ???????????? ??? ????????????.");
        }

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
        model.addAttribute("articleVO", bdvo);
        model.addAttribute("boardMasterVO", bmvo);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("nttId")); // ????????? ???????????? ?????? , ??? ??????

        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));

        log.debug("model = " + model);

        model.addAttribute("webDir", Config.USER_ROOT);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/orgintro/dongariWrite");
    }

    /**
     * ???????????? ????????????.
     *
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/dongari/save")
    public String dongariSave(final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
            HttpServletRequest request,
            CommandMap commandMap, @ModelAttribute("articleVO") BoardVO articleVO,
            BindingResult bindingResult, ModelMap model) throws Exception {

        if (commandMap.getUserInfo() == null) {
            HttpUtility.sendRedirect(request, response, "????????? ??? ???????????? ??? ????????????.", Config.USER_ROOT + "/member/login?returnURL=" + request.getAttribute("returnURL"));
            return null;
        } else if (!commandMap.getUserInfo().isMember()) {
            HttpUtility.sendBack(request, response, "???????????? ?????? ?????? ??? ????????????. ?????? ????????? ??? ????????? ?????????.");
            return null;
        }

        String bbsId = this.bbsId; // dongari

        log.debug("call insertArticle();");
        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap => " + paramMap);
        log.debug("nttId = {}", articleVO.getNttId());

        articleVO.setBbsId(bbsId);
        articleVO.setNtceStat("1"); // ???????????? ?????? ??????

        BoardMasterVO master = new BoardMasterVO();

        master.setBbsId(articleVO.getBbsId());
        master = egovBBSMasterService.selectBBSMasterInf(master);
        model.addAttribute("boardMasterVO", master);
        String atchFileId = "";

        final Map<String, MultipartFile> files = multiRequest.getFileMap();

        // ???????????????
        if (articleVO.getNttId() > 0) {
            // step1 DB?????? ?????? ???????????? uniqId ??????
            BoardVO vo = egovArticleService.selectArticleDetail(articleVO);

            // ????????? ???????????? ????????? ?????? ????????? ??????
            if (!vo.getNtcrId().equals(articleVO.getNtcrId())) {
                HttpUtility.sendRedirect(request, response, "???????????? ???????????? ??? ????????????.", Config.USER_ROOT + "/orgintro/dongari/list" + commandMap.getString("searchQuery"));
            }

            articleVO.setNtceStat(vo.getNtceStat()); // ?????? ???????????? ??????
            atchFileId = vo.getAtchFileId(); // ?????? ?????? ????????? ??????
        }

        /****************************************** ?????? ????????? ?????? *****************************/
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
        /****************************************** ?????? ????????? ?????? *****************************/

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

            return HttpUtility.getViewUrl(Config.USER_ROOT, "/orgintro/dongari/list");
        }

        // articleVO.setFrstRegisterId((user == null || user.getId() == null) ? "" : user.getId());
        // articleVO.setAtchFileId(atchFileId);

        // ???????????????
        if (articleVO.getNttId() > 0) {

            articleVO.setLastUpdusrId(articleVO.getNtcrId());
            egovArticleService.updateArticle(articleVO, multiRequest.getParameter("fileDeleteList"));

        } else {

            articleVO.setFrstRegisterId(articleVO.getNtcrId());
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
                String type = itemVO.getBbsItemType();
                String value = commandMap.getString(key);
                if ("2001".equals(type)) {
                    itemVO.setBbsItemLvalue(value);
                } else {
                    itemVO.setBbsItemSvalue(value);
                }
                itemVO.setNttId(articleVO.getNttId());
                egovArticleService.insertBbsItemInfo(itemVO);
            }
        }

        HttpUtility.sendRedirect(multiRequest, response, "?????????????????????.", Config.USER_ROOT + "/orgintro/dongari/list");

        return null;

    }

    /**
     * ???????????? ?????? ?????? ????????? ????????????.
     *
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/dongari/detail")
    public String dongariDetail(@ModelAttribute("searchVO") BoardVO boardVO,
            HttpServletRequest request, HttpServletResponse response, CommandMap commandMap, ModelMap model)
            throws Exception {

        String bbsId = this.bbsId; // dongari

        log.debug("call selectArticleDetail() :: " + bbsId + ", NttId = " + boardVO.getNttId());

        BoardMasterVO master = new BoardMasterVO();

        master.setUseAt("Y");
        master.setBbsId(bbsId);
        BoardMasterVO masterVo = egovBBSMasterService.selectBBSMasterInf(master);

        boardVO.setBbsId(bbsId);
        boardVO.setUseAt("Y");
        //// boardVO.setNttId(boardVO.getNttId());

        BoardVO vo = egovArticleService.selectArticleDetailIncrease(boardVO);

        if (vo == null) {
            HttpUtility.sendBack(request, response, "???????????? ???????????? ????????????.");
            return null;
        }

        if ("Y".equals(vo.getSecretAt()) && (commandMap.getUserInfo() == null || !vo.getNtcrId().equals(commandMap.getUserInfo().getId()))) {
            HttpUtility.sendBack(request, response, "???????????? ????????? ????????? ????????? ??? ????????????.");
            return null;
        }

        model.addAttribute("result", vo);

        if (masterVo.getBbsTyCode().equals("B004")) {
            // ----------------------------
            // QnA ?????????????????? ?????? ?????????
            // ----------------------------
            model.addAttribute("replyData", egovArticleService.selectArticleReplyDetail(boardVO));
        } else {
            // ????????? / ?????????
            boardVO.setSearchCnd("-1");
            Map nextArticle = egovArticleService.selectArticleNext(boardVO);
            model.addAttribute("nextArticle", nextArticle);
            if (nextArticle != null && nextArticle.get("nextNttId") != null) {
                String nextNttId = nextArticle.get("nextNttId").toString();
                BoardVO nextBoardVo = new BoardVO();
                nextBoardVo.setBbsId(bbsId);
                nextBoardVo.setUseAt("Y");
                nextBoardVo.setNttId(StringUtil.String2Long(nextNttId, 0));
                model.addAttribute("nextBoardVo", egovArticleService.selectArticleDetailIncrease(nextBoardVo));
            }
            if (nextArticle != null && nextArticle.get("prevNttId") != null) {
                String prevNttId = nextArticle.get("prevNttId").toString();
                BoardVO prevBoardVo = new BoardVO();
                prevBoardVo.setBbsId(bbsId);
                prevBoardVo.setUseAt("Y");
                prevBoardVo.setNttId(StringUtil.String2Long(prevNttId, 0));
                model.addAttribute("prevBoardVo", egovArticleService.selectArticleDetailIncrease(prevBoardVo));
            }
        }

        String searchQuery = commandMap.getSearchQuery("nttId");// ????????? ???????????? ?????? , ??? ??????
        if (searchQuery.length() > 0)
            searchQuery = "&" + searchQuery.substring(1);

        // ???????????? ??????
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");

        model.addAttribute("listParam", commandMap.getSearchQuery("nttId"));
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("boardMasterVO", masterVo);
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));
        model.addAttribute("commandMap", commandMap);

        // ?????????????????? - ????????????
        if (boardVO.getNttId() > 0) {
            List<BoardItemVO> itemInfoList = egovArticleService.selectArticleDetailItemList(boardVO);
            model.addAttribute("itemInfoList", itemInfoList);
        }

        model.addAttribute("webDir", Config.USER_ROOT);
        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));
        model.addAttribute("user", commandMap.getUserInfo());

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/orgintro/dongariDetail");
    }
}
