package com.hisco.admin.contents.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.contents.service.ContentsService;
import com.hisco.admin.contents.vo.ContentsVO;
import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 카테고리 관리
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class ContentsController {

    @Resource(name = "contentsService")
    private ContentsService contentsService;

    @Resource(name = "logService")
    private LogService logService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "codeService")
    private CodeService codeService;

    private int maxFileSizeMB = 10;

    private static final String UPLOAD_ALLOW_EXT = EgovProperties.getProperty("Globals.fileUpload.Extensions");

    /**
     * 개인 교육 프로그램 관리
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "컨텐츠 관리 조회", action = "R")
    @GetMapping("/contents/contentsList")
    public String contentsList(@ModelAttribute("searchVO") ContentsVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> paramMap) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);
System.out.println("searchVO.getSearchKeyword():"+ searchVO.getSearchKeyword());
        List<?> list = contentsService.selectContentList(searchVO);

        if (list.size() >= 1) {
            ContentsVO data = (ContentsVO) list.get(0);
            paginationInfo.setTotalRecordCount(data.getTotCnt());
        }

        model.addAttribute("contentsList", list);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 컨텐츠 등록 / 수정 화면
     *
     * @param contentsVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping({ "/contents/contentsRegist", "/contents/contentsUpdt" })
    public String contentsRegi(@ModelAttribute("contentsVO") ContentsVO contentsVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        // 컨텐츠 타입
        model.addAttribute("cotContentsList", codeService.selectCodeList("SM_CONTENTS_GBN"));
        model.addAttribute("mode", "insert");
        contentsVO.setUseYn("Y");

        if (!commandMap.getString("menuNo").equals("") || !commandMap.getString("contentsSeq").equals("")) {
            ContentsVO data = contentsService.selectContentsDetail(commandMap.getParam());
            if (data != null && data.getContentsSeq() > 0) {
                contentsVO = data;
                model.addAttribute("mode", "update");
                model.addAttribute("contentsVO", contentsVO);
            } else if (!commandMap.getString("menuNo").equals("")) {
                ContentsVO data2 = contentsService.selectMenuTitle(commandMap.getParam());
                contentsVO.setMenuNm(data2.getMenuNm());
            }
        }

        // 수정인 경우 파일 리스트 검색
        if (!"".equals(contentsVO.getFileId())) {
            FileVO fileVO = new FileVO();
            fileVO.setFileGrpinnb(contentsVO.getFileId());
            model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
        }

        // 수정인 경우 파일 리스트 검색
        if (!"".equals(contentsVO.getFileId2())) {
            FileVO fileVO = new FileVO();
            fileVO.setFileGrpinnb(contentsVO.getFileId2());
            model.addAttribute("fileList2", fileMngService.selectFileInfs(fileVO));
        }

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/contents/contentsRegist");
    }

    /**
     * 컨텐츠 관리 등록
     *
     * @param ContentsVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "컨텐츠 등록", action = "C", ajax = true)
    @PostMapping("/contents/contentsInsert.json")
    @ResponseBody
    public ModelAndView contentsSave(@ModelAttribute("contentsVO") ContentsVO contentsVO, CommandMap commandMap,
            HttpServletResponse response, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        contentsService.insertContents(contentsVO);

        resultInfo = HttpUtility.getSuccessResultInfo("정상으로 처리되었습니다.");
        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 컨텐츠 수정
     *
     * @param ContentsVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "컨텐츠 등록", action = "U", ajax = true)
    @PostMapping("/contents/contentsUpdate.json")
    @ResponseBody
    public ModelAndView contentUpdate(@ModelAttribute("contentsVO") ContentsVO contentsVO, CommandMap commandMap,
            HttpServletResponse response, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        int intPKey = contentsService.updateContents(contentsVO);

        resultInfo = HttpUtility.getSuccessResultInfo("정상으로 처리되었습니다.");
        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 컨텐츠 삭제
     *
     * @param ContentsVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "컨텐츠 삭제", action = "D", ajax = true)
    @PostMapping("/contents/contentsDelete.json")
    @ResponseBody
    public ModelAndView contentDelete(@ModelAttribute("contentsVO") ContentsVO contentsVO, CommandMap commandMap,
            HttpServletResponse response, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        contentsService.deleteContents(contentsVO);

        resultInfo = HttpUtility.getSuccessResultInfo("정상으로 처리되었습니다.");
        mav.addObject("result", resultInfo);
        return mav;

    }

    @PostMapping(value = "/contents/fileUpload.json")
    @ResponseBody
    public ModelAndView contentFileUpload(CommandMap commandMap,
            final MultipartHttpServletRequest multiRequest,
            HttpServletResponse response,
            ModelMap model)
            throws Exception {

        final Map<String, MultipartFile> files = multiRequest.getFileMap();
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        String uploadFolder = "";
        String originFileName = "";
        String realFilePath = "";
        String atchFileId = "";
        String fileSize = "";

        Iterator<FileVO> iter = null;

        if (!files.isEmpty()) {
            try {
                // 기존 파일 Id
                atchFileId = commandMap.getString("atchFileId");

                if ("".equals(atchFileId)) {
                    List<FileVO> result = fileUtil.parseFileInf(files, "CON_", 0, atchFileId, uploadFolder, user.getId());
                    iter = result.iterator();

                    atchFileId = fileMngService.insertFileInfs(result);
                } else {
                    FileVO fvo = new FileVO();
                    fvo.setFileGrpinnb(atchFileId);

                    int fileSn = fileMngService.getMaxFileSN(fvo);
                    List<FileVO> result = fileUtil.parseFileInf(files, "CON_", fileSn, atchFileId, uploadFolder, user.getId());
                    iter = result.iterator();

                    fileMngService.updateFileInfs(result);
                }

                while (iter.hasNext()) {
                    FileVO vo = iter.next();
                    originFileName = vo.getOrginFileName();
                    uploadFolder = vo.getFilePath();
                    fileSize = vo.getFileSize();
                    realFilePath = multiRequest.getContextPath() + Config.USER_ROOT + "/common/file/view/" + uploadFolder + vo.getFileName() + "?originName=" + java.net.URLEncoder.encode(originFileName, "UTF-8");
                }

                resultInfo = HttpUtility.getSuccessResultInfo("업로드 되었습니다.");

            } catch (Exception e) {
                resultInfo = HttpUtility.getErrorResultInfo(e.getMessage());
            }
        }

        mav.addObject("originFileName", originFileName);
        mav.addObject("realFilePath", realFilePath);
        mav.addObject("atchFileId", atchFileId);
        mav.addObject("fileSize", fileSize);

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 파일 삭제
     *
     * @param ContentsVO
     * @param request
     * @return
     * @exception Exception
     */
    @PostMapping(value = "/contents/fileDelete.json")
    @ResponseBody
    public ModelAndView fileDelete(CommandMap commandMap,
            HttpServletResponse response, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        FileVO fvo = new FileVO();
        fvo.setFileGrpinnb(commandMap.getString("atchFileId"));
        fvo.setFileSn(commandMap.getString("fileSn"));

        fileMngService.deleteFileInf(fvo);

        resultInfo = HttpUtility.getSuccessResultInfo("삭제 처리되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 업로드 등록 페이지
     *
     * @param commandMap
     * @param response
     * @throws Exception
     */
    @GetMapping("/contents/uploadRegistPopup")
    public String selectFileRegist(HttpServletRequest request,
            CommandMap commandMap,
            ModelMap model) throws Exception {

        commandMap.put("comcd", Config.COM_CD);
        String fileId = commandMap.getString("atachFileId");

        // 수정인 경우 파일 리스트 검색
        if (!"".equals(fileId)) {
            FileVO fileVO = new FileVO();
            fileVO.setFileGrpinnb(fileId);
            model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
        }

        if (!commandMap.getString("param_uploadSize").equals("")) {
            maxFileSizeMB = Integer.parseInt(commandMap.getString("param_uploadSize"));
        }

        model.addAttribute("maxFileSizeMB", maxFileSizeMB);
        model.addAttribute("uploadAllowExt", UPLOAD_ALLOW_EXT);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("fileId", fileId);

        return HttpUtility.getViewUrl(request);
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
    @PostMapping("/contents/uploadRegisSave")
    public String insertArticle(final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
            CommandMap commandMap,
            ModelMap model) throws Exception {

        commandMap.put("comcd", Config.COM_CD);
        String fileDeleteList = commandMap.getString("fileDeleteList");
        String fileId = commandMap.getString("fileGrpId");
        String contentsSeq = commandMap.getString("contentsSeq");

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        final Map<String, MultipartFile> files = multiRequest.getFileMap();

        try {

            // 파일 다중 업로드
            if ("".equals(fileId)) {
                List<FileVO> result = fileUtil.parseFileInf(multiRequest, "CON_", 0, fileId, "", user.getId());
                fileId = fileMngService.insertFileInfs(result);

            } else {
                FileVO fvo = new FileVO();
                fvo.setFileGrpinnb(fileId);
                int cnt = fileMngService.getMaxFileSN(fvo);
                List<FileVO> result = fileUtil.parseFileInf(multiRequest, "CON_", cnt, fileId, "", user.getId());
                fileMngService.updateFileInfs(fileId, fileDeleteList, result);
            }

            if (!contentsSeq.equals("")) {
                ContentsVO contentsVO = new ContentsVO();
                contentsVO.setContentsSeq(Integer.parseInt(contentsSeq));
                contentsVO.setFileId2(fileId);
                contentsService.updateContentsFileId2(contentsVO);
            }

        } catch (Exception e) {
            HttpUtility.sendBack(multiRequest, response, e.getMessage());
            return null;
        }

        commandMap.put("fileId", fileId); // 파일 아이디

        // status.setComplete();
        HttpUtility.sendRedirect(multiRequest, response, "처리되었습니다.", Config.ADMIN_ROOT + "/contents/uploadRegistPopup?atachFileId=" + fileId);

        return null;

    }
}
