package com.hisco.user.mypage.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hisco.admin.board.service.BoardCtgService;
import com.hisco.admin.board.vo.BoardCtgVO;
import com.hisco.admin.instrctr.service.InstrPoolService;
import com.hisco.admin.instrctr.vo.InstrPoolVO;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.RedirectUtil;
import com.hisco.cmm.vo.FileVO;
import com.hisco.extend.AbstractController;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import egovframework.com.cop.bbs.service.EgovBBSMasterService;
import egovframework.com.cop.cmt.service.CommentVO;
import egovframework.com.cop.cmt.service.EgovArticleCommentService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * ???????????? ????????????
 *
 * @author ?????????
 * @since 2022.10.14
 * @version 1.0, 2022.10.14
 *          ------------------------------------------------------------------------
 *          ????????? ?????? ??????
 *          ------------------------------------------------------------------------
 *          ????????? 2022.10.14 ????????????
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/mypage/myWriting")
public class MyWritingController extends AbstractController{
	
	
	@Resource(name = "instrPoolService")
    private InstrPoolService instrPoolService;
    
    @Resource(name = "EgovArticleService")
    private EgovArticleService egovArticleService;
    
    @Resource(name = "EgovArticleCommentService")
    private EgovArticleCommentService egovArticleCommentService;
    
    @Resource(name = "boardCtgService")
    private BoardCtgService boardCtgService;
    
    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;
    
    @Resource(name = "FileMngService")
    private FileMngService fileMngService;
    
    @Autowired
    private DefaultBeanValidator beanValidator;
    /**
     * ??????????????? - ????????????
     * @param commandMap
     * @param request
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
    
    @RequestMapping("/myWritingList")
    public String selectWritingList(CommandMap commandMap,
            HttpServletRequest request, ModelMap model,HttpServletResponse response) throws Exception {

    	log.debug("call selectWritingList");
    	
    	LoginVO loginVO = commandMap.getUserInfo();
    	PaginationInfo paginationInfo = commandMap.getPagingInfo();
    	
    	if (loginVO == null) {
    		return RedirectUtil.redirectLoginParam(model);		//????????????????????? ??????
        }
    	int totCnt = 0;
    	
    	//PR?????? ????????????
    	InstrPoolVO searchVO = new InstrPoolVO();
        searchVO.setMemNo(commandMap.getUserInfo().getUniqId());
        List<InstrPoolVO> instrPoolList = instrPoolService.list(searchVO);
        paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("instrPoolList", instrPoolList);
        model.addAttribute("paginationInfo", paginationInfo);
        
        
    	//??????????????? ??????
        PaginationInfo paginationInfo2 = commandMap.getPagingInfo();
        BoardVO boardVO = new BoardVO();
        boardVO.setFirstIndex(paginationInfo2.getFirstRecordIndex());
        boardVO.setLastIndex(paginationInfo2.getLastRecordIndex());
        boardVO.setRecordCountPerPage(paginationInfo2.getRecordCountPerPage());
        boardVO.setUseAt("Y");
        boardVO.setBbsId("qna");
        boardVO.setNtcrId(commandMap.getUserInfo().getId());
        
        Map<String, Object> qna = egovArticleService.selectArticleNewList(boardVO);
    	
    	paginationInfo2.setTotalRecordCount(Integer.parseInt((String) qna.get("resultCnt")));
    	model.addAttribute("qnaList", qna.get("resultList"));
        model.addAttribute("paginationInfo2", paginationInfo2);
        
        //??????????????? ??????
        PaginationInfo paginationInfo3 = commandMap.getPagingInfo();
        BoardVO boardVO2 = new BoardVO();
        boardVO2.setFirstIndex(paginationInfo2.getFirstRecordIndex());
        boardVO2.setLastIndex(paginationInfo2.getLastRecordIndex());
        boardVO2.setRecordCountPerPage(paginationInfo2.getRecordCountPerPage());
        boardVO2.setUseAt("Y");
        boardVO2.setBbsId("freeboard");
        boardVO2.setNtcrId(commandMap.getUserInfo().getId());
        
        Map<String, Object> freeborad = egovArticleService.selectArticleNewList(boardVO2);
    	
    	paginationInfo2.setTotalRecordCount(Integer.parseInt((String) freeborad.get("resultCnt")));
    	model.addAttribute("freeboardList", freeborad.get("resultList"));
        model.addAttribute("paginationInfo3", paginationInfo3);

    	model.addAttribute("userInfo", commandMap.getUserInfo());
    	model.addAttribute("commandMap", commandMap);
    	return HttpUtility.getViewUrl(request);
    }
    
    @RequestMapping("/{bbsId}/detail")
    public String selectWritingDetail(@PathVariable String bbsId,CommandMap commandMap,
            HttpServletRequest request, ModelMap model,HttpServletResponse response,@RequestParam String nttId) throws Exception {

    	log.debug("call selectWritingDetail");
    	
    	LoginVO loginVO = commandMap.getUserInfo();
    	
    	if (loginVO == null) {
    		return RedirectUtil.redirectLoginParam(model);		//????????????????????? ??????
        }
    	BoardVO boardVO = new BoardVO();
        boardVO.setBbsId(bbsId);
        boardVO.setUseAt("Y");
        boardVO.setNttId(StringUtil.String2Long(nttId, 0));

        BoardVO vo = egovArticleService.selectArticleBBSDetail(boardVO);

        if (vo == null) {
            HttpUtility.sendBack(request, response, "???????????? ???????????? ????????????.");
            return null;
        }

        model.addAttribute("result", vo);

        if ("qna".equals(bbsId)) {
        	CommentVO commentVO = new CommentVO();
            commentVO.setBbsId(bbsId);
            commentVO.setNttId(boardVO.getNttId());
            Map<String, Object> map = egovArticleCommentService.selectArticleCommentList(commentVO);
            
            // QnA ????????? ?????? ?????????
            model.addAttribute("commentList", map.get("resultList"));
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
                model.addAttribute("nextBoardVo", egovArticleService.selectArticleBBSDetail(nextBoardVo));
            }
            if (nextArticle != null && nextArticle.get("prevNttId") != null) {
                String prevNttId = nextArticle.get("prevNttId").toString();
                BoardVO prevBoardVo = new BoardVO();
                prevBoardVo.setBbsId(bbsId);
                prevBoardVo.setUseAt("Y");
                prevBoardVo.setNttId(StringUtil.String2Long(prevNttId, 0));
                model.addAttribute("prevBoardVo", egovArticleService.selectArticleBBSDetail(prevBoardVo));
            }
        }

        // ???????????? ??????
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");

        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));
        model.addAttribute("commandMap", commandMap);
        String view = HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/myWriting/detail");
        
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
    @GetMapping({"/{bbsId}/updt" })
    public String regist(@PathVariable String bbsId, @ModelAttribute("articleVO") BoardVO articleVO,
            HttpServletRequest request, HttpServletResponse response, CommandMap commandMap, ModelMap model)
            throws Exception {

        // front ????????? ???????????????,??????????????? ????????? ????????? ????????? ??????
        if (!"freeboard".equals(bbsId) && !"qna".equals(bbsId)) {
            HttpUtility.sendBack(request, response, "????????? ????????????.");
        }

        if (commandMap.getUserInfo() == null) {
        	return RedirectUtil.redirectLoginParam(model);		//????????????????????? ??????
        }

        LoginVO user = commandMap.getUserInfo();
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
        }

        BoardMasterVO vo = new BoardMasterVO();
        vo.setBbsId(articleVO.getBbsId());
        vo.setUseAt("Y");

        // ???????????? ??????
        BoardCtgVO boardCtgVO = new BoardCtgVO();
        boardCtgVO.setBbsId(bbsId);
        boardCtgVO.setUseAt("Y");

        model.addAttribute("ctgList", boardCtgService.selectBoardCtgList(boardCtgVO));
        model.addAttribute("userInfo", commandMap.getUserInfo());

        String view = HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/myWriting/updt");

        log.debug("model = " + model);
        log.debug("view = " + view);

        return view;

    }
    
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

        String atchFileId = "";

        final Map<String, MultipartFile> files = multiRequest.getFileMap();

        // ???????????????
        if (articleVO.getNttId() > 0) {
            // step1 DB?????? ?????? ???????????? uniqId ??????
            BoardVO vo = egovArticleService.selectArticleDetail(articleVO);

            // ????????? ???????????? ????????? ?????? ????????? ??????
            if (!vo.getNtcrId().equals(articleVO.getNtcrId())) {
                HttpUtility.sendRedirect(request, response, "???????????? ???????????? ??? ????????????.", Config.USER_ROOT + "/mypage/myWriting/myWritingList");
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

            return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/web/myWriting/updt");
        }

        // articleVO.setFrstRegisterId((user == null || user.getId() == null) ? "" : user.getId());
        // articleVO.setAtchFileId(atchFileId);

        // ???????????????
        if (articleVO.getNttId() > 0) {

            articleVO.setLastUpdusrId(articleVO.getNtcrId());
            egovArticleService.updateArticle(articleVO, "");

        } else {

        	articleVO.setFrstRegisterId(articleVO.getNtcrId());
            egovArticleService.insertArticle(articleVO);
        }

        HttpUtility.sendRedirect(request, response, "?????? ???????????????.", Config.USER_ROOT + "/mypage/myWriting/myWritingList");

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
