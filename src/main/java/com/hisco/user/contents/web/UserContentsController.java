package com.hisco.user.contents.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.contents.service.ContentsService;
import com.hisco.admin.contents.vo.ContentsVO;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * 컨텐츠 컨트롤러
 *
 * @author 전영석
 * @since 2021.03.18
 * @version 1.0, 2021.03.18
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.18 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/contents")
public class UserContentsController {

    @Resource(name = "contentsService")
    private ContentsService contentsService;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Value("#{dynamicConfig.managerRoot}")
    private String managerRoot;

    /**
     * 컨텐츠 view 페이지
     *
     * @param Map
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/{contentsSeq}/view")
    public String selectContentView(@PathVariable(name = "contentsSeq", required = true) String contentsSeq,
            CommandMap commandMap, HttpServletRequest request,
            HttpServletResponse response, ModelMap model)
            throws Exception {

        commandMap.put("contentsSeq", contentsSeq);
        ContentsVO contentsVO = contentsService.selectContentsDetail(commandMap.getParam());

        model.addAttribute("contentsSeq", contentsSeq);
        model.addAttribute("contentsVO", contentsVO);
        model.addAttribute("commandMap", commandMap);

        // 컨텐츠 수정 권한 체크
        model.addAttribute("editAuthYn", CommonUtil.hasRole("ROLE_SUPER")); // 슈퍼관리자 권한 체크
        model.addAttribute("webDir", Config.USER_ROOT);

        return HttpUtility.getViewUrl(Config.USER_ROOT, "/contents/contentsView");

    }

    /**
     * 현재 컨텐츠 수정 화면
     *
     * @param bannerVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/{contentsSeq}/regist")
    public String saveContentsRegi(@PathVariable(name = "contentsSeq", required = true) String contentsSeq,
            @ModelAttribute("contentsVO") ContentsVO contentsVO, CommandMap commandMap,
            HttpServletResponse response, HttpServletRequest request, ModelMap model) throws Exception {

        // 컨텐츠 수정 권한 체크
        boolean authFlag = CommonUtil.hasRole("ROLE_SUPER"); // 슈퍼관리자 권한 체크

        model.addAttribute("cotContentsList", codeService.selectCodeList("SM_CONTENTS_GBN"));
        model.addAttribute("webDir", managerRoot);

        commandMap.put("contentsSeq", contentsSeq);
        contentsVO = contentsService.selectContentsDetail(commandMap.getParam());

        model.addAttribute("contentsSeq", contentsSeq);
        model.addAttribute("contentsVO", contentsVO);
        model.addAttribute("commandMap", commandMap);

        // 파일 리스트 검색
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

        if (authFlag) {
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/contents/contentsRegist");
        } else {
            HttpUtility.sendBack(request, response, "컨텐츠 수정권한이 없습니다.");
            return null;
        }

    }

    /**
     * 컨텐츠 수정
     *
     * @param ContentsVO
     * @param request
     * @return
     * @exception Exception
     */
    @PostMapping("/contentsUpdate.json")
    @ResponseBody
    public ModelAndView contentUpdate(@ModelAttribute("contentsVO") ContentsVO contentsVO, CommandMap commandMap,
            HttpServletResponse response, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        // 컨텐츠 수정 권한 체크
        boolean authFlag = CommonUtil.hasRole("ROLE_SUPER");

        if (authFlag == false) {
            resultInfo = HttpUtility.getErrorResultInfo("수정 권한이 없습니다.");
        } else {
            contentsService.updateContents(contentsVO);

            resultInfo = HttpUtility.getSuccessResultInfo("정상으로 처리되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    @PostMapping(value = "/fileUpload.json")
    @ResponseBody
    public ModelAndView contentFileUpload(CommandMap commandMap,
            final MultipartHttpServletRequest multiRequest,
            HttpServletRequest request,
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
        String fileSn = "";

        Iterator<FileVO> iter = null;

        // 컨텐츠 수정 권한 체크
        boolean authFlag = contentsService.contentEditFlag(request);

        if (authFlag == false) {
            resultInfo = HttpUtility.getErrorResultInfo("수정 권한이 없습니다.");
        } else {
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

                        int nextFileSn = fileMngService.getMaxFileSN(fvo);
                        List<FileVO> result = fileUtil.parseFileInf(files, "CON_", nextFileSn, atchFileId, uploadFolder, user.getId());
                        iter = result.iterator();

                        fileMngService.updateFileInfs(result);
                    }

                    while (iter.hasNext()) {
                        FileVO vo = iter.next();
                        originFileName = vo.getOrginFileName();
                        uploadFolder = vo.getFilePath();
                        fileSize = vo.getFileSize();
                        fileSn = vo.getFileSn();
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
            mav.addObject("fileSn", fileSn);
            mav.addObject("fileSize", fileSize);
        }

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
    @PostMapping(value = "/fileDelete.json")
    @ResponseBody
    public ModelAndView fileDelete(CommandMap commandMap,
            HttpServletResponse response, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        // 컨텐츠 수정 권한 체크
        boolean authFlag = contentsService.contentEditFlag(request);

        if (authFlag == false) {
            resultInfo = HttpUtility.getErrorResultInfo("수정 권한이 없습니다.");
        } else {
            FileVO fvo = new FileVO();
            fvo.setFileGrpinnb(commandMap.getString("atchFileId"));
            fvo.setFileSn(commandMap.getString("fileSn"));

            fileMngService.deleteFileInf(fvo);

            resultInfo = HttpUtility.getSuccessResultInfo("삭제 처리되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 코드 목록을 json 형태로 반환
     *
     * @param codeGrpCd
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping(value = "/codelist/{codeGrpCd}/list.json")
    @ResponseBody
    public ModelAndView contentUpdate(@PathVariable String codeGrpCd, CommandMap commandMap,
            HttpServletResponse response, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("list", codeService.selectCodeList(codeGrpCd));
        return mav;

    }

}