package com.hisco.admin.popup.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.pwm.service.EgovPopupManageService;
import egovframework.com.uss.ion.pwm.service.PopupManageVO;
//// import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 팝업 관리를 위한 콘트롤러
 * 
 * @author 진수진
 * @since 2020.08.04
 * @version 1.0, 2020.08.04
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.08.04 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class PopupController {

    /** logService */
    @Resource(name = "logService")
    private LogService logService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "codeService")
    private CodeService codeService;

    /** EgovPopupManageService */
    @Resource(name = "egovPopupManageService")
    private EgovPopupManageService egovPopupManageService;

    /** Message ID Generation */
    //// @Resource(name="egovPopupManageIdGnrService")
    //// private EgovIdGnrService egovPopupManageIdGnrService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    /**
     * 팝업 목록을 조회한다.
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "팝업 목록 조회", action = "R")
    @GetMapping(value = "/popup/popupList")
    public String popupList(@ModelAttribute("popupManageVO") PopupManageVO popupManageVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {
        /*
         * if (!logService.checkAdminLog(commandMap, "R", "팝업 목록 조회")) {
         * return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
         * }
         */
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        popupManageVO.setPaginationInfo(paginationInfo);

        int totCnt = egovPopupManageService.selectPopupListCount(popupManageVO);
        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("resultList", egovPopupManageService.selectPopupList(popupManageVO));
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("searchVO", popupManageVO);

        // 팝업 종류 ( WEB_POPUP_GBN 코드그룹 새로 추가함 )
        // model.addAttribute("popupGbn", codeService.selectCodeList("WEB_POPUP_GBN"));

        log.debug("model = " + model);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 등록된 팝업의 상세정보를 조회한다.
     * 
     * @param bannerVO
     *            - 팝업 Vo
     * @return String - 리턴 Url
     */
    @PageActionInfo(title = "팝업 상세 조회", action = "R")
    @RequestMapping(value = "/popup/popupDetail")
    public String popupDetail(@ModelAttribute("popupManageVO") PopupManageVO popupManageVO, CommandMap commandMap,
            HttpServletRequest request,
            ModelMap model) throws Exception {

        PopupManageVO data = egovPopupManageService.selectPopup(popupManageVO);
        model.addAttribute("result", data);

        if (data.getImageFileId() != null) {
            // 파일 리스트 검색
            if (!"".equals(data.getImageFileId())) {
                FileVO fileVO = new FileVO();
                fileVO.setFileGrpinnb(data.getImageFileId());
                model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
            }
        }

        model.addAttribute("searchQuery", commandMap.getSearchQuery("popupId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("commandMap", commandMap);

        // 팝업 종류 ( WEB_POPUP_GBN 코드그룹 새로 추가함 )
        // model.addAttribute("popupGbn", codeService.selectCodeList("WEB_POPUP_GBN"));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 팝업 등록화면
     *
     * @param bannerVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "팝업 등록화면", action = "C")
    @GetMapping("/popup/popupRegist")
    public String popupRegist(@ModelAttribute("popupManageVO") PopupManageVO popupManageVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        // 신규등록
        model.addAttribute("mode", "add");
        model.addAttribute("popup", popupManageVO);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("popupId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("commandMap", commandMap);

        // 팝업창시작일자(시)
        model.addAttribute("ntceBgndeHH", DateUtil.getTimeHH());
        // 팝업창시작일자(분)
        model.addAttribute("ntceBgndeMM", DateUtil.getTimeMM());
        // 팝업창종료일자(시)
        model.addAttribute("ntceEnddeHH", DateUtil.getTimeHH());
        // 팝업창정료일자(분)
        model.addAttribute("ntceEnddeMM", DateUtil.getTimeMM());

        // 팝업 종류 ( WEB_POPUP_GBN 코드그룹 새로 추가함 )
        // model.addAttribute("popupGbn", codeService.selectCodeList("WEB_POPUP_GBN"));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 팝업 수정화면
     *
     * @param bannerVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "팝업 수정화면", action = "U")
    @GetMapping("/popup/popupUpdt")
    public String popupUpdt(@ModelAttribute("popupManageVO") PopupManageVO popupManageVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        // 수정

        PopupManageVO data = egovPopupManageService.selectPopup(popupManageVO);
        model.addAttribute("popupManageVO", data);

        if (data.getImageFileId() != null) {
            // 파일 리스트 검색
            if (!"".equals(data.getImageFileId())) {
                FileVO fileVO = new FileVO();
                fileVO.setFileGrpinnb(data.getImageFileId());
                model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
            }
        }

        model.addAttribute("mode", "edit");
        model.addAttribute("searchQuery", commandMap.getSearchQuery("popupId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("commandMap", commandMap);

        // 팝업창시작일자(시)
        model.addAttribute("ntceBgndeHH", DateUtil.getTimeHH());
        // 팝업창시작일자(분)
        model.addAttribute("ntceBgndeMM", DateUtil.getTimeMM());
        // 팝업창종료일자(시)
        model.addAttribute("ntceEnddeHH", DateUtil.getTimeHH());
        // 팝업창정료일자(분)
        model.addAttribute("ntceEnddeMM", DateUtil.getTimeMM());

        // 팝업 종류 ( WEB_POPUP_GBN 코드그룹 새로 추가함 )
        // model.addAttribute("popupGbn", codeService.selectCodeList("WEB_POPUP_GBN"));

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/popup/popupRegist");
    }

    /**
     * 이미지 슬라이드 정보를 신규로 등록한다.
     * 
     * @param popupManageVO
     * @return url
     */
    @PostMapping(value = "/popup/popupSave")
    public String popupSave(final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
            @ModelAttribute("popupManageVO") PopupManageVO popupManageVO,
            CommandMap commandMap,
            BindingResult bindingResult,
            ModelMap model) throws Exception {

        if (popupManageVO.getPopupId() != null && !popupManageVO.getPopupId().equals("")) {
            if (!logService.checkAdminLog(commandMap, "U", "이미지 슬라이드 수정")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        } else {
            if (!logService.checkAdminLog(commandMap, "C", "이미지 슬라이드 등록")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        }

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        popupManageVO.setFrstRegisterId(user.getId());
        popupManageVO.setLastUpdusrId(user.getId());

        beanValidator.validate(popupManageVO, bindingResult); // validation 수행

        if (bindingResult.hasErrors()) {
            model.addAttribute("popupManageVO", popupManageVO);
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/popup/popupRegist");
        } else {
            List<FileVO> result = null;
            String uploadFolder = "";
            String imageFileId = "";
            String imageFileNm = "";

            final Map<String, MultipartFile> files = multiRequest.getFileMap();

            if (!files.isEmpty()) {
                // List<FileVO> parseFileInf(Map<String, MultipartFile> files, String KeyStr, int fileKeyParam, String
                // atchFileId, String storePath)
                try {
                    result = fileUtil.parseFileInf(files, "POP_", 0, "", uploadFolder, user.getId());
                } catch (Exception e) {
                    HttpUtility.sendBack(multiRequest, response, e.getMessage());
                    return null;
                }

                imageFileId = fileMngService.insertFileInfs(result);

                FileVO vo = null;
                Iterator<FileVO> iter = result.iterator();

                while (iter.hasNext()) {
                    vo = iter.next();
                    imageFileNm = vo.getOrginFileName();
                }
                if (vo == null) {
                    popupManageVO.setAtchFile(false);
                } else {
                    popupManageVO.setImageFileNm(imageFileNm);
                    popupManageVO.setImageFileId(imageFileId);
                    popupManageVO.setAtchFile(true);
                }

            } else {
                popupManageVO.setAtchFile(false);
            }
            if (popupManageVO.getPopupId() != null && !popupManageVO.getPopupId().equals("")) {
                egovPopupManageService.updatePopup(popupManageVO);
            } else {
                egovPopupManageService.insertPopup(popupManageVO);
            }

            HttpUtility.sendRedirect(multiRequest, response, "처리되었습니다.", Config.ADMIN_ROOT + "/popup/popupList" + commandMap.getString("searchQuery"));
            return null;

        }
    }

    /**
     * 팝업 삭제
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/popup/popupDelete")
    @ResponseBody
    public ModelAndView popupDelete(@ModelAttribute("popupManageVO") PopupManageVO popupManageVO,
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        if (!logService.checkAdminLog(commandMap, "D", "팝업 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            egovPopupManageService.deletePopup(popupManageVO);
            resultInfo = HttpUtility.getSuccessResultInfo("선택한 팝업이 삭제되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 순서 상태 일괄 변경
     */
    @PostMapping("/popup/statUpdate")
    @ResponseBody
    public ModelAndView statUpdate(
            HttpServletRequest request, CommandMap commandMap, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        // 수정
        if (!logService.checkAdminLog(commandMap, "U", "팝업 정렬순 적용")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            PopupManageVO popupManageVO = new PopupManageVO();
            String[] popupIdArr = request.getParameterValues("popupId");
            String[] ntceAtArr = request.getParameterValues("ntceAt");
            String[] sortOrderArr = request.getParameterValues("sortOrder");
            if (popupIdArr.length > 0) {
                for (int i = 0; i < popupIdArr.length; i++) {
                    popupManageVO.setPopupId(popupIdArr[i]);
                    popupManageVO.setNtceAt(ntceAtArr[i]);
                    popupManageVO.setSortOrder(sortOrderArr[i]);
                    popupManageVO.setLastUpdusrId(user.getId());
                    egovPopupManageService.updateStatPopup(popupManageVO);

                }
            }
            resultInfo = HttpUtility.getSuccessResultInfo("처리 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }
}
