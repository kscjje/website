package com.hisco.user.board.web;

import java.util.HashMap;
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
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hisco.admin.board.service.BoardCtgService;
import com.hisco.admin.board.vo.BoardCtgVO;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.RedirectUtil;
import com.hisco.cmm.vo.FileVO;
import com.hisco.extend.AbstractController;
import com.hisco.user.edcatnlc.service.EdcProgramService;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cop.bbs.service.BoardItemVO;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import egovframework.com.cop.bbs.service.EgovBBSMasterService;
import egovframework.com.cop.cmt.service.CommentVO;
import egovframework.com.cop.cmt.service.EgovArticleCommentService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * WEB 게시판 컨트롤러
 *
 * @author 전영석
 * @since 2021.04.29
 * @version 1.0, 2021.04.29
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.04.29 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/board")
public class WebBoardController extends AbstractController{

    @Resource(name = "EgovArticleService")
    private EgovArticleService egovArticleService;

    @Resource(name = "EgovBBSMasterService")
    private EgovBBSMasterService egovBBSMasterService;

    @Resource(name = "EgovArticleCommentService")
    private EgovArticleCommentService egovArticleCommentService;
    
    @Resource(name = "boardCtgService")
    private BoardCtgService boardCtgService;

    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

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
    public String list(@PathVariable String bbsId, @ModelAttribute("searchVO") BoardVO boardVO,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        log.debug("call selectBoardList()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

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

        Map<String, Object> map = egovArticleService.selectArticleNewList(boardVO);
        int intTotCnt = Integer.parseInt((String) map.get("resultCnt"));

        log.debug("intTotCnt = " + intTotCnt);

        // 공지사항 추출
        List<BoardVO> noticeList = egovArticleService.selectNoticeArticleNewList(boardVO);

        paginationInfo.setTotalRecordCount(intTotCnt);

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
        model.addAttribute("webDir", Config.USER_ROOT);

        /*
         * if ("mathinfor".equals(bbsId)) {
         * return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + master.getTmplatCours() +
         * "/mathInforList");
         * } else if ("mathplay".equals(bbsId)) {
         * return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + master.getTmplatCours() +
         * "/mathPlayList");
         * } else if ("mathfile".equals(bbsId)) {
         * return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + master.getTmplatCours() +
         * "/mathFileList");
         * } else if ("mathquestion".equals(bbsId)) {
         * return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + master.getTmplatCours() +
         * "/mathQuestionList");
         * } else {
         * return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + master.getTmplatCours() +
         * "/articleList");
         * }
         */

        String view = HttpUtility.getViewUrl(Config.USER_ROOT, "/board/" + master.getTmplatCours() + "/list");

        log.debug("model = " + model);
        log.debug("view = " + view);

        return view;

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
    public String detail(@PathVariable String bbsId, @ModelAttribute("searchVO") BoardVO boardVO,
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

        if ("qna".equals(masterVo.getBbsId())) {
        	CommentVO commentVO = new CommentVO();
            commentVO.setBbsId(bbsId);
            commentVO.setNttId(boardVO.getNttId());
            Map<String, Object> map = egovArticleCommentService.selectArticleCommentList(commentVO);
            
            // ----------------------------
            // QnA 게시판인경우 답변 데이타
            // ----------------------------
            model.addAttribute("commentList", map.get("resultList"));
//          model.addAttribute("replyData", egovArticleService.selectArticleReplyDetail(boardVO));
        } else {
            // 이전글 / 다음글
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

        String searchQuery = commandMap.getSearchQuery("nttId");// 제외할 파라미터 입력 , 로 연결
        if (searchQuery.length() > 0)
            searchQuery = "&" + searchQuery.substring(1);

        // 카테고리 목록
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");

        model.addAttribute("listParam", commandMap.getSearchQuery("nttId"));
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("boardMasterVO", masterVo);
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("webDir", Config.USER_ROOT);

        // 추가입력항목 - 입력된값
        if (boardVO.getNttId() > 0) {
            List<BoardItemVO> itemInfoList = egovArticleService.selectArticleDetailItemList(boardVO);
            model.addAttribute("itemInfoList", itemInfoList);
        }

        // 교육강좌 설정이 있을경우 처리
        EdcProgramVO programVO = null;
        if (vo.getEdcProgram() != null && !"".equals(vo.getEdcProgram())) {
            LoginVO loginVO = commandMap.getUserInfo();
            int edcPrgmid = StringUtil.String2Int(vo.getEdcProgram(), 0);
            if (edcPrgmid > 0) {
                EdcProgramVO param = new EdcProgramVO();
                param.setEdcPrgmNo(edcPrgmid);

                String edcRsvnsetSeq = edcProgramService.selectProgramRsvnSetMax(param);
                if(edcRsvnsetSeq != null && !edcRsvnsetSeq.equals("")){
                	param.setEdcRsvnsetSeq(Integer.parseInt(edcRsvnsetSeq));
                    param.setProgramNo(Config.EDC_PROGRAM_NO_PREFIX);
                    param.setEdcOpenyn("Y"); // 공개된 것만
                    if (loginVO != null && loginVO.isMember()){
                    	param.setMemNo(loginVO.getUniqId());
                    }

                    programVO = edcProgramService.selectProgramDetail(param);
                }

            }
        }
        model.addAttribute("programVO", programVO);

        String view = HttpUtility.getViewUrl(Config.USER_ROOT, "/board/" + masterVo.getTmplatCours() + "/detail");

        log.debug("model = " + model);
        log.debug("view = " + view);

        return view;
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
    public String regist(@PathVariable String bbsId, @ModelAttribute("articleVO") BoardVO articleVO,
            HttpServletRequest request, HttpServletResponse response, CommandMap commandMap, ModelMap model)
            throws Exception {

        // front 에서는 자유게시판,묻고답하기 이외에 글쓰기 기능이 없음
        if (!"freeboard".equals(bbsId) && !"qna".equals(bbsId)) {
            HttpUtility.sendBack(request, response, "권한이 없습니다.");
            return null;
        }

        if (commandMap.getUserInfo() == null) {
        	return RedirectUtil.redirectLoginParam(model);		//로그인화면으로 이동
        } else if (!commandMap.getUserInfo().isMember()) {
            HttpUtility.sendBack(request, response, "비회원은 작성 하실 수 없습니다. 회원 로그인 후 등록해 주세요.");
            return null;

        }

        LoginVO user = commandMap.getUserInfo();
        BoardMasterVO master = new BoardMasterVO();
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
            dataVO.setNttSj(dataVO.getNttSj().replaceAll("&#60;","<").replaceAll("&#62;",">"));
            dataVO.setNttCn(dataVO.getNttCn().replaceAll("&#60;","<").replaceAll("&#62;",">"));
            model.addAttribute("articleVO", dataVO);
        } else {
            model.addAttribute("articleVO", articleVO);
        }

        BoardMasterVO vo = new BoardMasterVO();
        vo.setBbsId(articleVO.getBbsId());
        vo.setUseAt("Y");
        master = egovBBSMasterService.selectBBSMasterInf(vo);

        // 카테고리 목록
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");

        model.addAttribute("boardMasterVO", master);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("nttId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));
        model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("webDir", Config.USER_ROOT);

        String view = HttpUtility.getViewUrl(Config.USER_ROOT, "/board/" + master.getTmplatCours() + "/regist");

        log.debug("model = " + model);
        log.debug("view = " + view);

        return view;

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
    public String save(final MultipartHttpServletRequest multiRequest, HttpServletRequest request,
            HttpServletResponse response, CommandMap commandMap, @PathVariable String bbsId,
            @ModelAttribute("articleVO") BoardVO articleVO, BindingResult bindingResult, ModelMap model)
            throws Exception {
    	if (!"freeboard".equals(bbsId) && !"qna".equals(bbsId)) {
            HttpUtility.sendBack(request, response, "권한이 없습니다.");
        }

        if (commandMap.getUserInfo() == null) {
        	return RedirectUtil.redirectLoginParam(model);		//로그인화면으로 이동
        } else if (!commandMap.getUserInfo().isMember()) {
            HttpUtility.sendBack(request, response, "비회원은 작성 하실 수 없습니다. 회원 로그인 후 등록해 주세요.");
            return null;

        }
        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);
        log.debug("nttId = {}", articleVO.getNttId());
        
        articleVO.setNttSj(articleVO.getNttSj().replaceAll("<", "&#60;").replaceAll(">", "&#62;"));
        articleVO.setNttCn(articleVO.getNttCn().replaceAll("<", "&#60;").replaceAll(">", "&#62;"));
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
            /* 20221013 자동입력방지 보류
             * String store_answer = CommonUtil.getString(request.getSession().getAttribute(Captcha.NAME));
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
*/
        	articleVO.setFrstRegisterId(articleVO.getNtcrId());
            egovArticleService.insertArticle(articleVO);
        }

        // status.setComplete();
        // HttpUtility.sendRedirect(request, response, "저장 되었습니다.", Config.USER_ROOT + "/rsvguidcntr/"+bbsId+"/list" +
        // commandMap.getString("searchQuery"));
        HttpUtility.sendRedirect(request, response, "저장 되었습니다.", Config.USER_ROOT + "/board/" + bbsId + "/list");

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
    public String delete(@PathVariable String bbsId, HttpServletRequest request,
            @ModelAttribute("board") BoardVO articleVO, CommandMap commandMap, ModelMap model,HttpServletResponse response) throws Exception {

        LoginVO user = commandMap.getUserInfo();

        if (!"freeboard".equals(bbsId) && !"qna".equals(bbsId)) {
            HttpUtility.sendBack(request, response, "권한이 없습니다.");
        }

        if (commandMap.getUserInfo() == null) {
        	return RedirectUtil.redirectLoginParam(model);		//로그인화면으로 이동
        } else if (!commandMap.getUserInfo().isMember()) {
            HttpUtility.sendBack(request, response, "비회원은 작성 하실 수 없습니다. 회원 로그인 후 등록해 주세요.");
            return null;
        }
        
        articleVO.setBbsId(bbsId);

        // step1 DB에서 해당 게시물의 uniqId 조회
        BoardVO vo = egovArticleService.selectArticleDetail(articleVO);
        Map<String, Object> ReturnMap = new HashMap<String, Object>();
		
        // 작성자 아이디와 로그인 세션 아이디 비교
        if (user == null || !vo.getNtcrId().equals(user.getId())) {
//          resultInfo = HttpUtility.getErrorResultInfo("작성자만 삭제하실 수 있습니다.");
            ReturnMap.put("msg", "작성자만 삭제하실 수 있습니다.");
        } else {
            vo.setLastUpdusrId((user == null || user.getId() == null) ? "" : user.getId());
            egovArticleService.deleteArticle(vo);
//            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
            ReturnMap.put("result", true);
            ReturnMap.put("msg", "삭제되었습니다");
        }
        String jsonData = MakeJsonData(request, ReturnMap);
		System.out.println("jsonData==="+jsonData);
        return jsonData;
    }

}
