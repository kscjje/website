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
 * WEB ????????? ????????????
 *
 * @author ?????????
 * @since 2021.04.29
 * @version 1.0, 2021.04.29
 *          ------------------------------------------------------------------------
 *          ????????? ?????? ??????
 *          ------------------------------------------------------------------------
 *          ????????? 2021.04.29 ????????????
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
     * ????????? ?????? ??????
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
            // bbsId ??? ???????????? ?????? ???????????? ?????????.
            HttpUtility.sendBack(request, response, "?????? ???????????? ???????????? ????????????.");
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

        // ???????????? ??????
        List<BoardVO> noticeList = egovArticleService.selectNoticeArticleNewList(boardVO);

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
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("searchQuery", searchQuery); // ????????? ???????????? ?????? , ??? ??????
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
     * ???????????? ?????? ?????? ????????? ????????????.
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
            HttpUtility.sendBack(request, response, "???????????? ???????????? ????????????.");
            return null;
        }

        if ("Y".equals(vo.getSecretAt()) && (commandMap.getUserInfo() == null || !vo.getNtcrId().equals(commandMap.getUserInfo().getId()))) {
            HttpUtility.sendBack(request, response, "???????????? ????????? ????????? ????????? ??? ????????????.");
            return null;
        }

        model.addAttribute("result", vo);

        if ("qna".equals(masterVo.getBbsId())) {
        	CommentVO commentVO = new CommentVO();
            commentVO.setBbsId(bbsId);
            commentVO.setNttId(boardVO.getNttId());
            Map<String, Object> map = egovArticleCommentService.selectArticleCommentList(commentVO);
            
            // ----------------------------
            // QnA ?????????????????? ?????? ?????????
            // ----------------------------
            model.addAttribute("commentList", map.get("resultList"));
//          model.addAttribute("replyData", egovArticleService.selectArticleReplyDetail(boardVO));
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
        model.addAttribute("webDir", Config.USER_ROOT);

        // ?????????????????? - ????????????
        if (boardVO.getNttId() > 0) {
            List<BoardItemVO> itemInfoList = egovArticleService.selectArticleDetailItemList(boardVO);
            model.addAttribute("itemInfoList", itemInfoList);
        }

        // ???????????? ????????? ???????????? ??????
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
                    param.setEdcOpenyn("Y"); // ????????? ??????
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
     * ????????? ????????? ?????? ?????????????????? ????????????.
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

        // front ????????? ???????????????,??????????????? ????????? ????????? ????????? ??????
        if (!"freeboard".equals(bbsId) && !"qna".equals(bbsId)) {
            HttpUtility.sendBack(request, response, "????????? ????????????.");
            return null;
        }

        if (commandMap.getUserInfo() == null) {
        	return RedirectUtil.redirectLoginParam(model);		//????????????????????? ??????
        } else if (!commandMap.getUserInfo().isMember()) {
            HttpUtility.sendBack(request, response, "???????????? ?????? ?????? ??? ????????????. ?????? ????????? ??? ????????? ?????????.");
            return null;

        }

        LoginVO user = commandMap.getUserInfo();
        BoardMasterVO master = new BoardMasterVO();
        articleVO.setBbsId(bbsId);
        articleVO.setUseAt("Y");
        articleVO.setFrstRegisterId(user.getId());
        articleVO.setNtcrId(user.getId()); // ????????? ?????????
        articleVO.setNtcrNm(user.getName()); // ????????? ??????

        
        
        BoardVO dataVO = new BoardVO();

        // ???????????????
        if (articleVO.getNttId() > 0) {
            // step1 DB?????? ?????? ???????????? uniqId ??????
            dataVO = egovArticleService.selectArticleDetail(articleVO);

            // ????????? ???????????? ????????? ?????? ????????? ??????
            if (!dataVO.getNtcrId().equals(articleVO.getNtcrId())) {
                HttpUtility.sendBack(request, response, "???????????? ???????????? ??? ????????????.");
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

        // ???????????? ??????
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");

        model.addAttribute("boardMasterVO", master);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("nttId")); // ????????? ???????????? ?????? , ??? ??????
        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));
        model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("webDir", Config.USER_ROOT);

        String view = HttpUtility.getViewUrl(Config.USER_ROOT, "/board/" + master.getTmplatCours() + "/regist");

        log.debug("model = " + model);
        log.debug("view = " + view);

        return view;

    }

    /**
     * ???????????? ????????????.
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
            HttpUtility.sendBack(request, response, "????????? ????????????.");
        }

        if (commandMap.getUserInfo() == null) {
        	return RedirectUtil.redirectLoginParam(model);		//????????????????????? ??????
        } else if (!commandMap.getUserInfo().isMember()) {
            HttpUtility.sendBack(request, response, "???????????? ?????? ?????? ??? ????????????. ?????? ????????? ??? ????????? ?????????.");
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

        // ???????????????
        if (articleVO.getNttId() > 0) {
            // step1 DB?????? ?????? ???????????? uniqId ??????
            BoardVO vo = egovArticleService.selectArticleDetail(articleVO);

            // ????????? ???????????? ????????? ?????? ????????? ??????
            if (!vo.getNtcrId().equals(articleVO.getNtcrId())) {
                HttpUtility.sendRedirect(request, response, "???????????? ???????????? ??? ????????????.", Config.USER_ROOT + "/rsvguidcntr/" + bbsId + "/list" + commandMap.getString("searchQuery"));
            }

            atchFileId = vo.getAtchFileId(); // ?????? ?????? ????????? ??????
        }

        /****************************************** ?????? ????????? ?????? *****************************/
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

            return HttpUtility.getViewUrl(Config.USER_ROOT, "/rsvguidcntr/board/" + master.getTmplatCours() + "/articleRegist");
        }

        // articleVO.setFrstRegisterId((user == null || user.getId() == null) ? "" : user.getId());
        // articleVO.setAtchFileId(atchFileId);

        // ???????????????
        if (articleVO.getNttId() > 0) {

            articleVO.setLastUpdusrId(articleVO.getNtcrId());
            egovArticleService.updateArticle(articleVO, "");

        } else {

            // ?????? ?????? ?????? ??????
            /* 20221013 ?????????????????? ??????
             * String store_answer = CommonUtil.getString(request.getSession().getAttribute(Captcha.NAME));
            if (store_answer != null && !store_answer.equals("") && store_answer.equals(articleVO.getCaptcha())) {
                // ?????? ??????
                if (articleVO.getParnts() != null && !articleVO.getParnts().equals("") && !articleVO.getParnts().equals("0")) {
                    articleVO.setReplyAt("Y");
                }

                // ???????????? ??????
                if (articleVO.getAnonymousAt() != null && articleVO.getAnonymousAt().equals("Y")) {
                    articleVO.setNtcrId("anonymous"); // ????????? ?????? ????????? ?????? ????????? ID ??????
                    articleVO.setNtcrNm("??????"); // ????????? ?????? ????????? ?????? ????????? Name ??????
                    articleVO.setFrstRegisterId("anonymous");
                }
                articleVO.setFrstRegisterId(articleVO.getNtcrId());
                egovArticleService.insertArticle(articleVO);

                request.getSession().removeAttribute(Captcha.NAME);
            } else {
                HttpUtility.sendBack(request, response, "???????????? ????????? ?????? ?????? ????????????.");
                return null;
            }
*/
        	articleVO.setFrstRegisterId(articleVO.getNtcrId());
            egovArticleService.insertArticle(articleVO);
        }

        // status.setComplete();
        // HttpUtility.sendRedirect(request, response, "?????? ???????????????.", Config.USER_ROOT + "/rsvguidcntr/"+bbsId+"/list" +
        // commandMap.getString("searchQuery"));
        HttpUtility.sendRedirect(request, response, "?????? ???????????????.", Config.USER_ROOT + "/board/" + bbsId + "/list");

        return null;

    }

    /**
     * ???????????? ?????? ????????? ????????????. use_at = 'N'
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
            HttpUtility.sendBack(request, response, "????????? ????????????.");
        }

        if (commandMap.getUserInfo() == null) {
        	return RedirectUtil.redirectLoginParam(model);		//????????????????????? ??????
        } else if (!commandMap.getUserInfo().isMember()) {
            HttpUtility.sendBack(request, response, "???????????? ?????? ?????? ??? ????????????. ?????? ????????? ??? ????????? ?????????.");
            return null;
        }
        
        articleVO.setBbsId(bbsId);

        // step1 DB?????? ?????? ???????????? uniqId ??????
        BoardVO vo = egovArticleService.selectArticleDetail(articleVO);
        Map<String, Object> ReturnMap = new HashMap<String, Object>();
		
        // ????????? ???????????? ????????? ?????? ????????? ??????
        if (user == null || !vo.getNtcrId().equals(user.getId())) {
//          resultInfo = HttpUtility.getErrorResultInfo("???????????? ???????????? ??? ????????????.");
            ReturnMap.put("msg", "???????????? ???????????? ??? ????????????.");
        } else {
            vo.setLastUpdusrId((user == null || user.getId() == null) ? "" : user.getId());
            egovArticleService.deleteArticle(vo);
//            resultInfo = HttpUtility.getSuccessResultInfo("?????? ???????????????.");
            ReturnMap.put("result", true);
            ReturnMap.put("msg", "?????????????????????");
        }
        String jsonData = MakeJsonData(request, ReturnMap);
		System.out.println("jsonData==="+jsonData);
        return jsonData;
    }

}
