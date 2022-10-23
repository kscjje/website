package com.hisco.user.rsvguidcntr.web;

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
import com.hisco.admin.board.vo.BoardCtgVO;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import egovframework.com.cop.bbs.service.EgovBBSMasterService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;
import nl.captcha.Captcha;

/**
 * 소통마당 게시판 컨트롤러
 * 
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.05 최초작성
 *          진수진 2020.08.06 클래스명 BoardController 으로 변경
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/rsvguidcntr")
public class BoardController {

    @Resource(name = "EgovArticleService")
    private EgovArticleService egovArticleService;

    @Resource(name = "EgovBBSMasterService")
    private EgovBBSMasterService egovBBSMasterService;

    @Resource(name = "boardCtgService")
    private BoardCtgService boardCtgService;

    //// @Resource(name = "EgovArticleCommentService")
    //// private EgovArticleCommentService egovArticleCommentService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    /**
     * 게시판 목록 조회
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/{bbsId}/list")
    public String selectBoardList(@PathVariable String bbsId, @ModelAttribute("searchVO") BoardVO boardVO,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        log.debug("call selectBoardList()");

        BoardMasterVO vo = new BoardMasterVO();

        vo.setBbsId(bbsId);
        vo.setUseAt("Y");
        BoardMasterVO master = egovBBSMasterService.selectBBSMasterInf(vo);

        if (master == null) {
            // bbsId 가 없으므로 오류 페이지로 보낸다.
            HttpUtility.sendBack(request, response, "해당 게시판이 존재하지 않습니다.");
            return null;
        }

        log.debug("----------------------------------------------------.S");
        log.debug("selectBoardList :: master = " + master);
        log.debug("master.getTmplatCours()" + master);
        log.debug(master.getTmplatCours());
        log.debug("----------------------------------------------------.E");

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
        boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        boardVO.setUseAt("Y");
        boardVO.setBbsTyCode(master.getBbsTyCode());

        Map<String, Object> map = egovArticleService.selectArticleList(boardVO);
        int totCnt = Integer.parseInt((String) map.get("resultCnt"));

        // 공지사항 추출
        List<BoardVO> noticeList = egovArticleService.selectNoticeArticleList(boardVO);

        paginationInfo.setTotalRecordCount(totCnt);

        String searchQuery = commandMap.getSearchQuery("nttId");
        if (searchQuery.length() > 0)
            searchQuery = "&" + searchQuery.substring(1);

        // 카테고리 목록
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));

        model.addAttribute("resultList", map.get("resultList"));
        model.addAttribute("resultCnt", map.get("resultCnt"));
        model.addAttribute("articleVO", boardVO);
        model.addAttribute("boardMasterVO", master);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("searchQuery", searchQuery); // 제외할 파라미터 입력 , 로 연결

        if ("mathinfor".equals(bbsId)) {
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + master.getTmplatCours() + "/mathInforList");
        } else if ("mathplay".equals(bbsId)) {
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + master.getTmplatCours() + "/mathPlayList");
        } else if ("mathfile".equals(bbsId)) {
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + master.getTmplatCours() + "/mathFileList");
        } else if ("mathquestion".equals(bbsId)) {
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + master.getTmplatCours() + "/mathQuestionList");
        } else {
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + master.getTmplatCours() + "/articleList");
        }

    }

    /**
     * 게시물에 대한 상세 정보를 조회한다.
     *
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/{bbsId}/detail")
    public String selectArticleDetail(@PathVariable String bbsId, @ModelAttribute("searchVO") BoardVO boardVO,
            HttpServletRequest request, HttpServletResponse response, CommandMap commandMap, ModelMap model)
            throws Exception {

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
            HttpUtility.sendBack(request, response, "해당글이 존재하지 않습니다.");
            return null;
        }

        if ("Y".equals(vo.getSecretAt()) && (commandMap.getUserInfo() == null || !vo.getNtcrId().equals(commandMap.getUserInfo().getId()))) {
            HttpUtility.sendBack(request, response, "비밀글로 작성자 본인만 조회할 수 있습니다.");
            return null;
        }

        model.addAttribute("result", vo);

        if (masterVo.getBbsTyCode().equals("B004")) {
            // ----------------------------
            // QnA 게시판인경우 답변 데이타
            // ----------------------------
            model.addAttribute("replyData", egovArticleService.selectArticleReplyDetail(boardVO));
        } else {
            // 이전글 / 다음글
            model.addAttribute("nextArticle", egovArticleService.selectArticleNext(boardVO));
        }

        String searchQuery = commandMap.getSearchQuery("nttId");// 제외할 파라미터 입력 , 로 연결
        if (searchQuery.length() > 0)
            searchQuery = "&" + searchQuery.substring(1);

        model.addAttribute("listParam", commandMap.getSearchQuery("nttId"));
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("boardMasterVO", masterVo);
        model.addAttribute("commandMap", commandMap);

        if ("mathinfor".equals(bbsId)) {
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + masterVo.getTmplatCours() + "/mathInforDetail");
        } else if ("mathplay".equals(bbsId)) {
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + masterVo.getTmplatCours() + "/mathPlayDetail");
        } else if ("mathfile".equals(bbsId)) {
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + masterVo.getTmplatCours() + "/mathFileDetail");
        } else if ("mathquestion".equals(bbsId)) {
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + masterVo.getTmplatCours() + "/mathQuestionDetail");
        } else {

            log.debug("====================================================================================================");
            log.debug(Config.USER_ROOT + "/rsvguidcntr/board/" + masterVo.getTmplatCours() + "/articleDetail");
            log.debug("====================================================================================================");

            // return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + masterVo.getTmplatCours() +
            // "/articleDetail");

            return HttpUtility.getViewUrl(request);

        }

    }

    /**
     * 게시물 등록을 위한 등록페이지로 이동한다.
     *
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping({ "/{bbsId}/regist", "/{bbsId}/updt" })
    public String selectArticleRegist(@PathVariable String bbsId, @ModelAttribute("articleVO") BoardVO articleVO,
            HttpServletRequest request, HttpServletResponse response, CommandMap commandMap, ModelMap model)
            throws Exception {

        if (commandMap.getUserInfo() == null) {
            HttpUtility.sendRedirect(request, response, "로그인 후 작성하실 수 있습니다.", Config.USER_ROOT + "/member/login?returnURL=" + request.getAttribute("returnURL"));
            return null;
        } else if (!commandMap.getUserInfo().isMember()) {
            HttpUtility.sendBack(request, response, "비회원은 작성 하실 수 없습니다. 회원 로그인 후 등록해 주세요.");
            return null;

        }

        LoginVO user = commandMap.getUserInfo();
        BoardMasterVO bdMstr = new BoardMasterVO();
        articleVO.setBbsId(bbsId);
        articleVO.setUseAt("Y");
        articleVO.setFrstRegisterId(user.getId());
        articleVO.setNtcrId(user.getId()); // 작성자 아이디
        articleVO.setNtcrNm(user.getName()); // 작성자 이름

        BoardVO dataVO = new BoardVO();

        // 수정인경우
        if (articleVO.getNttId() > 0) {
            // step1 DB에서 해당 게시물의 uniqId 조회
            dataVO = egovArticleService.selectArticleDetail(articleVO);

            // 작성자 아이디와 로그인 세션 아이디 비교
            if (!dataVO.getNtcrId().equals(articleVO.getNtcrId())) {
                HttpUtility.sendBack(request, response, "작성자만 수정하실 수 있습니다.");
            }

            model.addAttribute("articleVO", dataVO);
        } else {
            model.addAttribute("articleVO", articleVO);
        }

        BoardMasterVO vo = new BoardMasterVO();
        vo.setBbsId(articleVO.getBbsId());
        vo.setUseAt("Y");
        bdMstr = egovBBSMasterService.selectBBSMasterInf(vo);

        // 카테고리 목록
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");

        model.addAttribute("boardMasterVO", bdMstr);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("nttId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));
        model.addAttribute("userInfo", commandMap.getUserInfo());

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + bdMstr.getTmplatCours() + "/articleRegist");
    }

    /**
     * 게시물을 등록한다.
     *
     * @param articleVO
     * @param board
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/{bbsId}/save")
    public String insertArticle(final MultipartHttpServletRequest multiRequest, HttpServletRequest request,
            HttpServletResponse response, CommandMap commandMap, @PathVariable String bbsId,
            @ModelAttribute("articleVO") BoardVO articleVO, BindingResult bindingResult, ModelMap model)
            throws Exception {

        articleVO.setBbsId(bbsId);

        BoardMasterVO master = new BoardMasterVO();

        master.setBbsId(articleVO.getBbsId());
        master = egovBBSMasterService.selectBBSMasterInf(master);
        model.addAttribute("boardMasterVO", master);
        String atchFileId = "";

        final Map<String, MultipartFile> files = multiRequest.getFileMap();

        // 수정인경우
        if (articleVO.getNttId() > 0) {
            // step1 DB에서 해당 게시물의 uniqId 조회
            BoardVO vo = egovArticleService.selectArticleDetail(articleVO);

            // 작성자 아이디와 로그인 세션 아이디 비교
            if (!vo.getNtcrId().equals(articleVO.getNtcrId())) {
                HttpUtility.sendRedirect(request, response, "작성자만 수정하실 수 있습니다.", Config.USER_ROOT + "/rsvguidcntr/" + bbsId + "/list" + commandMap.getString("searchQuery"));
            }

            atchFileId = vo.getAtchFileId(); // 기존 파일 업로드 정보
        }

        /****************************************** 파일 업로드 처리 *****************************/
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
        /****************************************** 파일 업로드 처리 *****************************/

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

            return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + master.getTmplatCours() + "/articleRegist");
        }

        // articleVO.setFrstRegisterId((user == null || user.getId() == null) ? "" : user.getId());
        // articleVO.setAtchFileId(atchFileId);

        // 수정인경우
        if (articleVO.getNttId() > 0) {

            articleVO.setLastUpdusrId(articleVO.getNtcrId());
            egovArticleService.updateArticle(articleVO, "");

        } else {

            // 자동 방지 문자 확인
            String store_answer = CommonUtil.getString(request.getSession().getAttribute(Captcha.NAME));
            if (store_answer != null && !store_answer.equals("") && store_answer.equals(articleVO.getCaptcha())) {
                // 답변 처리
                if (articleVO.getParnts() != null && !articleVO.getParnts().equals("") && !articleVO.getParnts().equals("0")) {
                    articleVO.setReplyAt("Y");
                }

                // 익명등록 처리
                if (articleVO.getAnonymousAt() != null && articleVO.getAnonymousAt().equals("Y")) {
                    articleVO.setNtcrId("anonymous"); // 게시물 통계 집계를 위해 등록자 ID 저장
                    articleVO.setNtcrNm("익명"); // 게시물 통계 집계를 위해 등록자 Name 저장
                    articleVO.setFrstRegisterId("anonymous");
                }
                articleVO.setFrstRegisterId(articleVO.getNtcrId());
                egovArticleService.insertArticle(articleVO);

                request.getSession().removeAttribute(Captcha.NAME);
            } else {
                HttpUtility.sendBack(request, response, "자동입력 방지가 일치 하지 않습니다.");
                return null;
            }

        }

        // status.setComplete();
        HttpUtility.sendRedirect(request, response, "저장 되었습니다.", Config.USER_ROOT + "/rsvguidcntr/" + bbsId + "/list" + commandMap.getString("searchQuery"));

        return null;

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
    @PostMapping("/{bbsId}/delete")
    @ResponseBody
    public ModelAndView deleteBoardArticle(@PathVariable String bbsId, HttpServletRequest request,
            @ModelAttribute("board") BoardVO articleVO,
            CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = commandMap.getUserInfo();

        articleVO.setBbsId(bbsId);
        // step1 DB에서 해당 게시물의 uniqId 조회
        BoardVO vo = egovArticleService.selectArticleDetail(articleVO);

        // 작성자 아이디와 로그인 세션 아이디 비교
        if (user == null || !vo.getNtcrId().equals(user.getId())) {
            resultInfo = HttpUtility.getErrorResultInfo("작성자만 삭제하실 수 있습니다.");
        } else {
            vo.setLastUpdusrId((user == null || user.getId() == null) ? "" : user.getId());
            egovArticleService.deleteArticle(vo);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

}
