package com.hisco.user.teacher.web;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hisco.admin.board.service.BoardCtgService;
import com.hisco.admin.board.service.BoardItemService;
import com.hisco.admin.board.vo.BoardCtgVO;
import com.hisco.admin.contents.service.ContentsService;
import com.hisco.admin.instrctr.service.InstrPoolService;
import com.hisco.admin.instrctr.vo.InstrPoolVO;
import com.hisco.admin.member.service.MemberService;
import com.hisco.admin.member.vo.MemberUserVO;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.FileVO;
import com.hisco.user.member.vo.MemberVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cop.bbs.service.BoardItemVO;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import egovframework.com.cop.bbs.service.EgovBBSMasterService;
import egovframework.com.sym.mnu.mpm.service.EgovMenuManageService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/teacherpr")
public class TeacherPrController {

    @Resource(name = "instrPoolService")
    private InstrPoolService instrPoolService;

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

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "memberService")
    private MemberService memberService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    private String bbsId = "teacherpr";

    /**
     * ??????????????? ??????
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/list")
    public String list(@ModelAttribute("searchVO") BoardVO boardVO,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        commandMap.put("comcd", Config.COM_CD);
        model.addAttribute("webDir", Config.USER_ROOT);

        String bbsId = this.bbsId; // teacherpr

        log.debug("call selectBoardList()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        BoardMasterVO vo = new BoardMasterVO();

        vo.setBbsId(bbsId);
        vo.setUseAt("Y");
        BoardMasterVO master = egovBBSMasterService.selectBBSMasterInf(vo);

        if (master == null) {
            // bbsId ??? ???????????? ?????? ???????????? ?????????.
            HttpUtility.sendBack(request, response, "??????PR(?????????) ????????? ?????? ?????? ????????????.");
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
        // boardVO.setNtceStat("2"); // ????????? ?????? ?????????

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

        // ?????? pool ??????
        String poolCheck = checkPool(commandMap).equals("") ? "N" : "Y";
        model.addAttribute("poolCheck", poolCheck);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/teacherpr/list");
    }

    /**
     * ??????????????? ??????
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/write")
    public String write(
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        if (commandMap.getUserInfo() == null) {
            HttpUtility.sendRedirect(request, response, "????????? ??? ???????????? ??? ????????????.", Config.USER_ROOT + "/member/login?returnURL=" + request.getAttribute("returnURL"));
            return null;
        } else if (!commandMap.getUserInfo().isMember()) {
            HttpUtility.sendBack(request, response, "???????????? ?????? ?????? ??? ????????????. ?????? ????????? ??? ????????? ?????????.");
            return null;
        }

        LoginVO user = commandMap.getUserInfo();

        String memNo = checkPool(commandMap);
        if (memNo.equals("")) {
            HttpUtility.sendBack(request, response, "???????????? ????????? ?????? ??? ??????, ??????PR ????????? ???????????? ??? ????????????.");
            return null;
        }
        model.addAttribute("memNo", memNo);

        String bbsId = this.bbsId; // teacherpr

        commandMap.put("comcd", Config.COM_CD);

        BoardVO articleVO = new BoardVO();

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

            // ?????? ????????? ????????? ????????????. ????????????????????? ????????? ?????????.
            /*
             * BoardVO boardVO = new BoardVO();
             * boardVO.setBbsId(bbsId);
             * boardVO.setUseAt("Y");
             * boardVO.setBbsTyCode(master.getBbsTyCode());
             * boardVO.setNtcrId(user.getId());
             * int cnt = egovArticleService.selectArticleListNewCnt(boardVO);
             * if (cnt > 0) {
             * HttpUtility.sendBack(request, response, "?????? ????????? ????????? ????????????.");
             * return null;
             * }
             */
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

        log.debug("model = " + model);

        model.addAttribute("webDir", Config.USER_ROOT);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/teacherpr/write");
    }

    /**
     * ??????????????? ??????
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/modify")
    public String modify(@ModelAttribute("boardVO") BoardVO boardVO,
            HttpServletRequest request, HttpServletResponse response,
            CommandMap commandMap, ModelMap model)
            throws Exception {

        String bbsId = this.bbsId; // teacherpr

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

        if (user == null) {
            HttpUtility.sendRedirect(request, response, "????????? ??? ???????????? ??? ????????????.", Config.USER_ROOT + "/member/login?returnURL=" + request.getAttribute("returnURL"));
        }

        // ????????? ???????????? ????????? ?????? ????????? ??????
        if (!user.getId().equals(bdvo.getNtcrId())) {
            HttpUtility.sendBack(request, response, "???????????? ???????????? ??? ????????????.");
        }

        String memNo = checkPool(commandMap);
        if (memNo.equals("")) {
            HttpUtility.sendBack(request, response, "???????????? ????????? ?????? ??? ??????, ??????PR ????????? ???????????? ??? ????????????.");
            return null;
        }
        model.addAttribute("memNo", memNo);

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

        log.debug("model = " + model);

        model.addAttribute("webDir", Config.USER_ROOT);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/teacherpr/write");
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
    @PostMapping("/save")
    public String save(final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
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

        LoginVO user = commandMap.getUserInfo();
        InstrPoolVO instrPoolVO = new InstrPoolVO();
        instrPoolVO.setMemNo(user.getUniqId());
        instrPoolVO.setMemNm(user.getName());
        instrPoolVO.setBirthDate(user.getBirthDate());
        instrPoolVO.setHp(user.getHpcertno());
        instrPoolVO.setEmail(user.getEmail());
        instrPoolVO.setEduinfoOpenyn("Y");

        // ?????? pool ??????
        if (checkPool(commandMap).equals("")) {
            HttpUtility.sendBack(request, response, "???????????? ????????? ?????? ??? ??????, ??????PR ????????? ???????????? ??? ????????????.");
            return null;
        }

        String bbsId = this.bbsId; // teacherpr

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
                HttpUtility.sendRedirect(request, response, "???????????? ???????????? ??? ????????????.", Config.USER_ROOT + "/teacherpr/list" + commandMap.getString("searchQuery"));
            }

            articleVO.setNtceStat(vo.getNtceStat()); // ?????? ???????????? ??????
            atchFileId = vo.getAtchFileId(); // ?????? ?????? ????????? ??????
        } else {
            // ?????? ????????? ????????? ????????????. ????????????????????? ????????? ?????????.
            /*
             * BoardVO boardVO = new BoardVO();
             * boardVO.setBbsId(bbsId);
             * boardVO.setUseAt("Y");
             * boardVO.setBbsTyCode(master.getBbsTyCode());
             * boardVO.setNtcrId(user.getId());
             * int cnt = egovArticleService.selectArticleListNewCnt(boardVO);
             * if (cnt > 0) {
             * HttpUtility.sendBack(request, response, "?????? ????????? ????????? ????????????.");
             * return null;
             * }
             */
        }

        /****************************************** ?????? ????????? ?????? *****************************/
        if (!files.isEmpty()) {
            try {
                if ("".equals(atchFileId)) {
                    List<FileVO> result = fileUtil.parseFileInf(multiRequest, "TEACHERPR_", 0, atchFileId, "", articleVO.getFrstRegisterId());
                    atchFileId = fileMngService.insertFileInfs(result);
                    articleVO.setAtchFileId(atchFileId);
                } else {
                    FileVO fvo = new FileVO();
                    fvo.setFileGrpinnb(atchFileId);
                    int cnt = fileMngService.getMaxFileSN(fvo);
                    List<FileVO> result = fileUtil.parseFileInf(multiRequest, "TEACHERPR_", cnt, atchFileId, "", articleVO.getFrstRegisterId());
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

            return HttpUtility.getViewUrl(Config.USER_ROOT, "/teacherpr/list");
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

        HttpUtility.sendRedirect(multiRequest, response, "?????????????????????.", Config.USER_ROOT + "/teacherpr/list");

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
    @GetMapping("/detail")
    public String detail(@ModelAttribute("searchVO") BoardVO boardVO,
            HttpServletRequest request, HttpServletResponse response, CommandMap commandMap, ModelMap model)
            throws Exception {

        String bbsId = this.bbsId; // teacherpr

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
        model.addAttribute("user", commandMap.getUserInfo());

        // ??????PR???????????? ID??? ?????? ???????????? ?????? ??????.
        MemberUserVO memberUserVO = new MemberUserVO();
        memberUserVO.setId(vo.getNtcrId());
        MemberVO memberVO = memberService.selectMemberDetailById(memberUserVO);

        InstrPoolVO instrPoolVO = new InstrPoolVO();
        instrPoolVO.setMemNo(memberVO.getMemNo());
        instrPoolVO = instrPoolService.detail(instrPoolVO);
        model.addAttribute("instrPoolVO", instrPoolVO);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/teacherpr/detail");
    }

    /**
     * ?????? ??????????????? ??????
     *
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    private String checkPool(CommandMap commandMap) throws Exception {

        String chk = "";

        LoginVO user = commandMap.getUserInfo();
        if (user != null) {
            InstrPoolVO instrPoolVO = new InstrPoolVO();
            instrPoolVO.setMemNo(user.getUniqId());

            // ?????? pool ??????
            InstrPoolVO detailVO = instrPoolService.detail(instrPoolVO);
            if (detailVO != null && detailVO.getMemNo() != null && !detailVO.getMemNo().equals("")) {
                if (detailVO.getState().equals("3001")) {
                    chk = detailVO.getMemNo();
                }
            }
        }
        return chk;
    }
}
