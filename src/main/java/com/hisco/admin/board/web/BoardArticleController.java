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
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.Board;
import egovframework.com.cop.bbs.service.BoardItemVO;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import egovframework.com.cop.bbs.service.EgovBBSMasterService;
//// import egovframework.com.cop.bbs.service.EgovBBSSatisfactionService;
import egovframework.com.cop.cmt.service.Comment;
import egovframework.com.cop.cmt.service.CommentVO;
import egovframework.com.cop.cmt.service.EgovArticleCommentService;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시판 게시물 관리를 위한 콘트롤러
 *
 * @author 진수진
 * @since 2020.07.22
 * @version 1.0, 2020.07.22
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.22 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class BoardArticleController {

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

    /** logService */
    @Resource(name = "logService")
    private LogService logService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    /**
     * XSS 방지 처리.
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
     * 게시글관리 메인
     *
     * @param boardMasterVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "게시글관리 메인", action = "R")
    @GetMapping("/board/articleMain")
    public String articleMain(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, CommandMap commandMap,
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
     * 게시물에 대한 목록을 조회한다.
     *
     * @param boardVO
     * @param sessionVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "게시글 목록", action = "R")
    @GetMapping("/board/article/{bbsId}/list")
    public String articleList(@PathVariable String bbsId, @ModelAttribute("searchVO") BoardVO boardVO,
            CommandMap commandMap, ModelMap model) throws Exception {

        log.debug("========= call selectArticleList();");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        BoardMasterVO vo = new BoardMasterVO();
        vo.setBbsId(bbsId);
        BoardMasterVO master = egovBBSMasterService.selectBBSMasterInf(vo);

        if (!logService.checkAdminLog(commandMap, "R", master.getBbsNm() + " 조회")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
        boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        boardVO.setBbsTyCode(master.getBbsTyCode()); // qna 게시판 (B004) 는 질문글만 select 한다

        Map<String, Object> map = egovArticleService.selectArticleList(boardVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));

        // 공지사항 추출
        List<BoardVO> noticeList = egovArticleService.selectNoticeArticleList(boardVO);

        paginationInfo.setTotalRecordCount(totCnt);

        if (user != null) {
            model.addAttribute("sessionUniqId", user.getId());
        }

        // 카테고리 목록
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));

        model.addAttribute("bbsId", bbsId);
        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("articleVO", boardVO);
        model.addAttribute("boardMasterVO", master);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("commandMap", commandMap);

        log.debug("model = {}", model);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/board/articleList");

    }

    /**
     * 게시물 등록을 위한 등록페이지로 이동한다.
     *
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "게시글 등록", action = "C")
    @GetMapping("/board/article/{bbsId}/regist")
    public String articleRegist(@PathVariable String bbsId, @ModelAttribute("searchVO") BoardVO boardVO,
            CommandMap commandMap, ModelMap model) throws Exception {

        log.debug("========= ======== articleRegist:" + bbsId);

        BoardMasterVO bdMstr = new BoardMasterVO();
        boardVO.setBbsId(bbsId);

        BoardMasterVO vo = new BoardMasterVO();
        vo.setBbsId(boardVO.getBbsId());
        bdMstr = egovBBSMasterService.selectBBSMasterInf(vo);

        // 카테고리 목록
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");

        model.addAttribute("bbsId", bbsId);
        model.addAttribute("articleVO", boardVO);
        model.addAttribute("boardMasterVO", bdMstr);

        model.addAttribute("searchQuery", commandMap.getSearchQuery("nttId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));

        BoardItemVO boardItemVO = new BoardItemVO();
        boardItemVO.setBbsId(bbsId);
        model.addAttribute("itemList", boardItemService.selectBoardItemList(boardItemVO));

        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/board/articleRegist");

    }

    /**
     * 게시물에 대한 상세 정보를 조회한다.
     *
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "게시글 보기", action = "R")
    @GetMapping("/board/article/{bbsId}/view/{nttId}")
    public String articleView(@PathVariable String bbsId, @PathVariable String nttId,
            @ModelAttribute("searchVO") BoardVO boardVO, CommandMap commandMap, ModelMap model) throws Exception {
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

        if (!logService.checkAdminLog(commandMap, "R", masterVo.getBbsNm() + " 조회")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        // ----------------------------
        // QnA 게시판인경우 답변 데이타
        // ----------------------------
        model.addAttribute("replyData", egovArticleService.selectArticleReplyDetail(boardVO));

        // 추가입력항목
        BoardItemVO boardItemVO = new BoardItemVO();
        boardItemVO.setBbsId(bbsId);
        List<BoardItemVO> itemList = boardItemService.selectBoardItemList(boardItemVO);
        model.addAttribute("itemList", itemList);

        // 추가입력항목 - 입력된값
        if (boardVO.getNttId() > 0 && itemList.size() > 0) {
            List<BoardItemVO> itemInfoList = egovArticleService.selectArticleDetailItemList(boardVO);
            model.addAttribute("itemInfoList", itemInfoList);
        }

        // ----------------------------
        // 댓글 처리
        // ----------------------------
        CommentVO commentVO = new CommentVO();
        commentVO.setBbsId(bbsId);
        commentVO.setNttId(Long.parseLong(nttId));
        Map<String, Object> map = egovArticleCommentService.selectArticleCommentList(commentVO);
        // int totCnt = Integer.parseInt((String)map.get("resultCnt"));

        model.addAttribute("bbsId", bbsId);
        model.addAttribute("commentList", map.get("resultList"));
        model.addAttribute("searchQuery", commandMap.getSearchQuery("bbsId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("boardMasterVO", masterVo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/board/articleView");
    }

    /**
     * 게시물에 대한 댓글 목록
     *
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "게시글 코멘트 목록", action = "R")
    @GetMapping("/board/article/{bbsId}/commentListAjax")
    public String articleCommentList(@PathVariable String bbsId, @ModelAttribute("searchVO") BoardVO boardVO,
            CommandMap commandMap, ModelMap model) throws Exception {
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        if (logService.checkAdminLog(commandMap, "R", "댓글 조회 [" + boardVO.getNttId() + "]")) {
            // ----------------------------
            // 댓글 처리
            // ----------------------------
            CommentVO commentVO = new CommentVO();
            commentVO.setBbsId(bbsId);
            commentVO.setNttId(boardVO.getNttId());
            Map<String, Object> map = egovArticleCommentService.selectArticleCommentList(commentVO);
            // int totCnt = Integer.parseInt((String)map.get("resultCnt"));
            
            model.addAttribute("bbsId", bbsId);
            model.addAttribute("commentList", map.get("resultList"));
            model.addAttribute("commandMap", commandMap);
            model.addAttribute("boardVO", boardVO);

        }

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/board/articleCommentList");
    }

    /**
     * 게시물을 등록한다.
     *
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/board/article/{bbsId}/save")
    public String articleSave(final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
            CommandMap commandMap, @PathVariable String bbsId, @ModelAttribute("articleVO") BoardVO articleVO,
            BindingResult bindingResult, ModelMap model) throws Exception {

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

        // 수정인경우
        if (articleVO.getNttId() > 0) {

            // step1 DB에서 해당 게시물의 uniqId 조회

            BoardVO vo = egovArticleService.selectArticleDetailOne(articleVO);

            /*
             * 관리자에서는 skip
             * // @ XSS 대응 권한체크 체크 START
             * // param1 : 사용자고유ID(uniqId,esntlId)
             * log.debug("@ XSS 권한체크 START ----------------------------------------------");
             * //step2 EgovXssChecker 공통모듈을 이용한 권한체크
             * EgovXssChecker.checkerUserXss(multiRequest, vo.getFrstRegisterId());
             * log.debug("@ XSS 권한체크 END ------------------------------------------------");
             * //--------------------------------------------------------
             * // @ XSS 대응 권한체크 체크 END
             * //--------------------------------------------------------------------------------------------
             */

            atchFileId = vo.getAtchFileId(); // 기존 파일 업로드 정보
        }

        if (articleVO.getNttId() > 0) {
            if (!logService.checkAdminLog(commandMap, "U", master.getBbsNm() + " 수정")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        } else {
            if (!logService.checkAdminLog(commandMap, "C", master.getBbsNm() + " 등록")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        }

        model.addAttribute("commandMap", commandMap);
        beanValidator.validate(articleVO, bindingResult);

        if (bindingResult.hasErrors()) {

            for (FieldError err : bindingResult.getFieldErrors()) {
                log.debug("Binding Error : {} [{}]", err.getField(), err.getDefaultMessage());
            }
            // 카테고리 목록
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
                    List<FileVO> result = fileUtil.parseFileInf(multiRequest, "BBS_", 0, atchFileId, "", articleVO.getFrstRegisterId());
                    atchFileId = fileMngService.insertFileInfs(result);
                    articleVO.setAtchFileId(atchFileId);
                } else {
                    FileVO fvo = new FileVO();
                    fvo.setFileGrpinnb(atchFileId);
                    int cnt = fileMngService.getMaxFileSN(fvo);
                    List<FileVO> result = fileUtil.parseFileInf(multiRequest, "BBS_", cnt, atchFileId, "", articleVO.getFrstRegisterId());
                    fileMngService.updateFileInfs(result);
                }
            } catch (Exception e) {
                // status.setComplete();
                HttpUtility.sendBack(multiRequest, response, e.getMessage());

                return null;
            }
        }

        articleVO.setAtchFileId(atchFileId);

        // 수정인경우
        if (articleVO.getNttId() > 0) {
            articleVO.setLastUpdusrId((user == null || user.getId() == null) ? "" : user.getId());
            articleVO.setNtcrNm(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
            articleVO.setPassword(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

            egovArticleService.updateArticle(articleVO, multiRequest.getParameter("fileDeleteList"));

        } else {

            long nttId = nttIdgenService.getNextIntegerId();
            articleVO.setNttId(nttId);

            // 답변 처리
            if (articleVO.getParnts() != null && !articleVO.getParnts().equals("") && !articleVO.getParnts().equals("0")) {
                articleVO.setReplyAt("Y");
            }

            // 익명등록 처리
            if (articleVO.getAnonymousAt() != null && articleVO.getAnonymousAt().equals("Y")) {
                articleVO.setNtcrId("anonymous"); // 게시물 통계 집계를 위해 등록자 ID 저장
                articleVO.setNtcrNm("익명"); // 게시물 통계 집계를 위해 등록자 Name 저장
                articleVO.setFrstRegisterId("anonymous");

            } else {
                articleVO.setNtcrId((user == null || user.getId() == null) ? "" : user.getId()); // 게시물 통계 집계를 위해 등록자 ID
                                                                                                 // 저장
                articleVO.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName()); // 게시물 통계 집계를 위해
                                                                                                     // 등록자 Name 저장

            }
            egovArticleService.insertArticle(articleVO);
        }

        // 2021.11.24 추가항목 처리
        BoardItemVO boardItemVO = new BoardItemVO();
        boardItemVO.setBbsId(articleVO.getBbsId());
        List boardItemList = boardItemService.selectBoardItemList(boardItemVO);
        if (boardItemList.size() > 0) {
            // 기존 정보 삭제 후 일괄 입력
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

        // status.setComplete();
        HttpUtility.sendRedirect(multiRequest, response, "처리되었습니다.", Config.ADMIN_ROOT + "/board/article/" + bbsId + "/list" + commandMap.getString("searchQuery"));

        return null;

    }

    /**
     * 게시물에 대한 답변 등록을 위한 등록페이지로 이동한다.
     *
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "게시글 답변 등록", action = "C")
    @GetMapping("/board/article/{bbsId}/replayRegist")
    public String articleReplayRegist(@ModelAttribute("searchVO") BoardVO boardVO, @PathVariable String bbsId,
            CommandMap commandMap, ModelMap model) throws Exception {
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        boardVO.setBbsId(bbsId);

        BoardMasterVO master = new BoardMasterVO();
        BoardVO articleVO = new BoardVO();
        master.setBbsId(boardVO.getBbsId());

        master = egovBBSMasterService.selectBBSMasterInf(master);
        articleVO = egovArticleService.selectArticleDetail(boardVO);

        if (articleVO != null) {
            articleVO.setAtchFileId("");
            articleVO.setNttSj("[답변]" + articleVO.getNttSj());
            String contents = articleVO.getNttCn();

            contents = "\r\n\r\n--------------------원본 게시글--------------------\r\n" + contents;

            articleVO.setNttCn(contents);

        }

        model.addAttribute("bbsId", bbsId);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("nttId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("boardMasterVO", master);
        model.addAttribute("result", boardVO);
        model.addAttribute("articleVO", articleVO);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/board/articleReplyRegist");
    }

    /**
     * 게시물 수정을 위한 수정페이지로 이동한다.
     *
     * @param boardVO
     * @param vo
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "게시글 수정", action = "U")
    @GetMapping("/board/article/{bbsId}/modify")
    public String articleModify(@PathVariable String bbsId, @ModelAttribute("boardVO") BoardVO boardVO,
            CommandMap commandMap, ModelMap model)
            throws Exception {

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        boardVO.setBbsId(bbsId);

        BoardMasterVO bmvo = new BoardMasterVO();
        bmvo.setBbsId(boardVO.getBbsId());
        bmvo = egovBBSMasterService.selectBBSMasterInf(bmvo);

        BoardVO bdvo = new BoardVO();
        bdvo = egovArticleService.selectArticleDetail(boardVO);

        // 카테고리 목록
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));

        // 추가입력항목
        BoardItemVO boardItemVO = new BoardItemVO();
        boardItemVO.setBbsId(bbsId);
        List<BoardItemVO> itemList = boardItemService.selectBoardItemList(boardItemVO);
        model.addAttribute("itemList", itemList);

        // 추가입력항목 - 입력된값
        if (boardVO.getNttId() > 0 && itemList.size() > 0) {
            List<BoardItemVO> itemInfoList = egovArticleService.selectArticleDetailItemList(boardVO);
            model.addAttribute("itemInfoList", itemInfoList);
        }

        model.addAttribute("bbsId", bbsId);
        model.addAttribute("articleVO", bdvo);
        model.addAttribute("boardMasterVO", bmvo);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("nttId")); // 제외할 파라미터 입력 , 로 연결

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/board/articleRegist");

    }

    /**
     * 게시물에 대한 내용을 삭제한다. use_at = 'N'
     *
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/board/article/{bbsId}/delete")
    @ResponseBody
    public ModelAndView articleDelete(@PathVariable String bbsId, HttpServletRequest request,
            @ModelAttribute("board") Board board,
            CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        // 관리자에서는 skip--------------------------------------------------------------------------------------------
        // @ XSS 대응 권한체크 체크 START
        // param1 : 사용자고유ID(uniqId,esntlId)
        // --------------------------------------------------------
        /*
         * log.debug("@ XSS 권한체크 START  ----------------------------------------------");
         * //step1 DB에서 해당 게시물의 uniqId 조회
         * BoardVO vo = egovArticleService.selectArticleDetail(boardVO);
         * //step2 EgovXssChecker 공통모듈을 이용한 권한체크
         * // 관리자에서는 안함
         * //EgovXssChecker.checkerUserXss(request, vo.getFrstRegisterId());
         * log.debug("@ XSS 권한체크 END ------------------------------------------------");
         * //--------------------------------------------------------
         * // @ XSS 대응 권한체크 체크 END
         * //--------------------------------------------------------------------------------------------
         * BoardVO bdvo = egovArticleService.selectArticleDetail(boardVO);
         * //익명 등록글인 경우 수정 불가
         * if (bdvo.getNtcrId().equals("anonymous")) {
         * model.addAttribute("result", bdvo);
         * model.addAttribute("boardMasterVO", bdMstr);
         * }
         */

        if (logService.checkAdminLog(commandMap, "D", "게시글 삭제")) {
            board.setLastUpdusrId((user == null || user.getId() == null) ? "" : user.getId());

            egovArticleService.deleteArticle(board);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");

        } else {
            resultInfo = HttpUtility.getErrorResultInfo("권한 없음");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 게시물에 대한 내용을 DB 에서 삭제한다.
     *
     * @param boardVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/board/article/{bbsId}/deleteAll")
    @ResponseBody
    public ModelAndView articleDeleteAll(@PathVariable String bbsId, HttpServletRequest request,
            @ModelAttribute("board") Board board,
            CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        if (logService.checkAdminLog(commandMap, "D", "게시글 DB삭제")) {
            egovArticleService.deleteArticleComplete(board);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");

        } else {
            resultInfo = HttpUtility.getErrorResultInfo("권한 없음");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 댓글을 등록한다.
     *
     * @param comment
     * @param comment
     * @param bindingResult
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/board/article/{bbsId}/commentSave")
    @ResponseBody
    public ModelAndView articleCommentSave(@PathVariable String bbsId, HttpServletRequest request,
            @ModelAttribute("comment") Comment comment, CommandMap commandMap,
            BindingResult bindingResult, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        comment.setBbsId(bbsId);
        beanValidator.validate(comment, bindingResult);
        if (bindingResult.hasErrors()) {
            resultInfo = HttpUtility.getErrorResultInfo("댓글내용은 필수 입력값입니다.");
        }

        if (logService.checkAdminLog(commandMap, "C", "코멘트 작성 [게시글ID:" + comment.getNttId() + "]")) {
            comment.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getId()));
            comment.setWrterId(user == null ? "" : EgovStringUtil.isNullToString(user.getId()));
            comment.setWrterNm(user == null ? "" : EgovStringUtil.isNullToString(user.getName()));

            egovArticleCommentService.insertArticleComment(comment);
            resultInfo = HttpUtility.getSuccessResultInfo("저장 되었습니다.");
        } else {
            resultInfo = HttpUtility.getErrorResultInfo("권한 없음");
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 댓글을 삭제한다.
     *
     * @param commentVO
     * @param comment
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/board/article/{bbsId}/commentDelete")
    @ResponseBody
    public ModelAndView articleCommentDelete(@PathVariable String bbsId, HttpServletRequest request,
            @ModelAttribute("searchVO") CommentVO commentVO,
            CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        if (logService.checkAdminLog(commandMap, "D", "코멘트 삭제 [게시글ID:" + commentVO.getNttId() + "]")) {
            egovArticleCommentService.deleteArticleComment(commentVO);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        } else {
            resultInfo = HttpUtility.getErrorResultInfo("권한 없음");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

}
