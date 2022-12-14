package com.hisco.user.mypage.web;

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

import com.hisco.admin.area.service.AreaCdService;
import com.hisco.admin.board.service.BoardCtgService;
import com.hisco.admin.board.service.BoardItemService;
import com.hisco.admin.board.vo.BoardCtgVO;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cop.bbs.service.BoardItemVO;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import egovframework.com.cop.bbs.service.EgovBBSMasterService;
import egovframework.com.sym.mnu.mpm.service.EgovMenuManageService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 마이페이지 학습동아리
 * 
 * @author 이윤호
 * @since 2021.12.06
 * @version 1.0, 2021.12.06
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          이윤호 2021.12.06 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/mypage/dongari")
public class MyDongariController {

    @Resource(name = "codeService")
    private CodeService codeService;

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
     * 학습동아리 목록
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/list")
    public String dongariList(@ModelAttribute("searchVO") BoardVO boardVO,
            CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {

        String orgType = "dongari";
        commandMap.put("comcd", Config.COM_CD);
        model.addAttribute("webDir", Config.USER_ROOT);

        LoginVO user = commandMap.getUserInfo();

        /***************** 여기 부터는 게시판 관련 소스 *******************/

        String bbsId = this.bbsId; // dongari

        log.debug("call selectBoardList()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        BoardMasterVO vo = new BoardMasterVO();

        vo.setBbsId(bbsId);
        vo.setUseAt("Y");
        BoardMasterVO master = egovBBSMasterService.selectBBSMasterInf(vo);

        if (master == null) {
            // bbsId 가 없으므로 오류 페이지로 보낸다.
            HttpUtility.sendBack(request, response, "학습동아리(게시판) 설정이 되어 있지 않습니다.");
            return null;
        }

        log.debug("----------------------------------------------------.S");
        log.debug("selectBoardList :: master = " + master);
        log.debug("master.getTmplatCours()" + master);
        log.debug(master.getTmplatCours());
        log.debug("----------------------------------------------------.E");

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setRecordCountPerPage(10);
        boardVO.setBbsId(bbsId);
        boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
        boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        boardVO.setUseAt("Y");
        boardVO.setBbsTyCode(master.getBbsTyCode());
        // boardVO.setNtceStat("2"); // 승인된 것만 필터링
        boardVO.setNtcrId(user.getId()); // 내가쓴 게시물만 보기

        Map<String, Object> map = egovArticleService.selectArticleNewList(boardVO);
        int intTotCnt = Integer.parseInt((String) map.get("resultCnt"));

        log.debug("intTotCnt = " + intTotCnt);

        // 공지사항 추출
        // List<BoardVO> noticeList = egovArticleService.selectNoticeArticleNewList(boardVO);

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
        // model.addAttribute("noticeList", noticeList);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("userInfo", commandMap.getUserInfo());
        model.addAttribute("searchQuery", searchQuery); // 제외할 파라미터 입력 , 로 연결

        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/dongari/dongariList");
    }

    /**
     * 학습동아리 수정
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/modify")
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

        // 수정인경우
        if (bdvo.getNttId() < 0) {
            HttpUtility.sendBack(request, response, "잘못된 접근입니다.");
        }

        // 작성자 아이디와 로그인 세션 아이디 비교
        if (!user.getId().equals(bdvo.getNtcrId())) {
            HttpUtility.sendBack(request, response, "작성자만 수정하실 수 있습니다.");
        }

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

        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));

        log.debug("model = " + model);

        model.addAttribute("webDir", Config.USER_ROOT);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/dongari/dongariWrite");
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
    @PostMapping("/save")
    public String dongariSave(final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
            HttpServletRequest request,
            CommandMap commandMap, @ModelAttribute("articleVO") BoardVO articleVO,
            BindingResult bindingResult, ModelMap model) throws Exception {

        if (commandMap.getUserInfo() == null) {
            HttpUtility.sendRedirect(request, response, "로그인 후 작성하실 수 있습니다.", Config.USER_ROOT + "/member/login?returnURL=" + request.getAttribute("returnURL"));
            return null;
        } else if (!commandMap.getUserInfo().isMember()) {
            HttpUtility.sendBack(request, response, "비회원은 작성 하실 수 없습니다. 회원 로그인 후 등록해 주세요.");
            return null;
        }

        String bbsId = this.bbsId; // dongari

        log.debug("call insertArticle();");
        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap => " + paramMap);
        log.debug("nttId = {}", articleVO.getNttId());

        articleVO.setBbsId(bbsId);
        articleVO.setNtceStat("1"); // 승인요청 강제 설정

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

            articleVO.setNtceStat(vo.getNtceStat()); // 기존 승인상태 상속
            atchFileId = vo.getAtchFileId(); // 기존 파일 업로드 정보
        }

        /****************************************** 파일 업로드 처리 *****************************/
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

            return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/dongari/list");
        }

        // articleVO.setFrstRegisterId((user == null || user.getId() == null) ? "" : user.getId());
        // articleVO.setAtchFileId(atchFileId);

        // 수정인경우
        if (articleVO.getNttId() > 0) {

            articleVO.setLastUpdusrId(articleVO.getNtcrId());
            egovArticleService.updateArticle(articleVO, multiRequest.getParameter("fileDeleteList"));

        } else {

            articleVO.setFrstRegisterId(articleVO.getNtcrId());
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

        HttpUtility.sendRedirect(multiRequest, response, "등록되었습니다.", Config.USER_ROOT + "/mypage/dongari/list");

        return null;

    }

    /**
     * 게시물에 대한 상세 정보를 조회한다.
     *
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/detail")
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

        // 추가입력항목 - 입력된값
        if (boardVO.getNttId() > 0) {
            List<BoardItemVO> itemInfoList = egovArticleService.selectArticleDetailItemList(boardVO);
            model.addAttribute("itemInfoList", itemInfoList);
        }

        model.addAttribute("webDir", Config.USER_ROOT);
        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));
        model.addAttribute("user", commandMap.getUserInfo());

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/dongari/dongariDetail");
    }
}
