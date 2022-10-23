package com.hisco.admin.orginfo.web;

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

import com.hisco.admin.area.service.AreaCdService;
import com.hisco.admin.orginfo.service.ComInfoService;
import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgContentsVO;
import com.hisco.admin.orginfo.vo.OrgInfoVO;
import com.hisco.admin.orginfo.vo.OrgOptinfoVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.config.DynamicConfigUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : OrgInfoController.java
 * @Description : 이용기관 정보 관리
 * @author user
 * @since 2021. 10. 26.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class OrgInfoController {

    @Resource(name = "comInfoService")
    private ComInfoService comInfoService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    @Resource(name = "areaCdService")
    private AreaCdService areaCdService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "dynamicConfigUtil")
    private DynamicConfigUtil configUtil;

    @Autowired
    private DefaultBeanValidator beanValidator;

    @PageActionInfo(title = "이용기관 목록 조회", action = "R")
    @GetMapping(value = "/orginfo/orgInfoList")
    public String selectOrgInfoList(@ModelAttribute("searchVO") OrgInfoVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);

        int totCnt = 0;
        searchVO.setComcd(Config.COM_CD);
        List<OrgInfoVO> list = (List<OrgInfoVO>) orgInfoService.selectOrgInfoListPaging(searchVO);

        if (list != null && list.size() > 0) {
            totCnt = list.get(0).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        // 기관유형
        model.addAttribute("typeList", codeService.selectCodeList("SM_ORG_LTYPE"));
        model.addAttribute("list", list);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "이용기관 상세 조회", action = "R")
    @GetMapping(value = "/orginfo/orgInfoDetail")
    public String selectOrgInfoDetail(@ModelAttribute("searchVO") OrgInfoVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        searchVO.setComcd(Config.COM_CD);

        // 내용 셀렉트
        OrgContentsVO orgContentsVO = new OrgContentsVO();
        orgContentsVO.setComcd(searchVO.getComcd());
        orgContentsVO.setOrgNo(searchVO.getOrgNo());

        orgContentsVO = orgInfoService.selectOrgContents(orgContentsVO);
        model.addAttribute("orgContentsVO", orgContentsVO);

        model.addAttribute("commandMap", commandMap);
        model.addAttribute("queryString", commandMap.getSearchQuery("orgNo"));
        model.addAttribute("adminRoot", configUtil.getAdminPath(request));

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping(value = "/orginfo/orgInfoDetailAjax")
    public String selectOrgInfoDetailAjax(@ModelAttribute("orgInfoVO") OrgInfoVO orgInfoVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        // 지역
        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));
        // 상위 기관 선택
        orgInfoVO.setLevel("1");
        model.addAttribute("upOrgList", orgInfoService.selectOrgInfoList(orgInfoVO));

        // 기관유형
        model.addAttribute("typeList", codeService.selectCodeList("SM_ORG_LTYPE"));

        // 내용 셀렉트
        orgInfoVO = orgInfoService.selectOrgInfoDetail(orgInfoVO);

        model.addAttribute("orgInfoVO", orgInfoVO);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("adminRoot", configUtil.getAdminPath(request));

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping(value = "/orginfo/orgInfoContentsAjax")
    public String selectOrgInfoContentsAjax(@ModelAttribute("orgContentsVO") OrgContentsVO orgContentsVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        // 내용 셀렉트
        orgContentsVO = orgInfoService.selectOrgContents(orgContentsVO);
        model.addAttribute("orgContentsVO", orgContentsVO);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("adminRoot", configUtil.getAdminPath(request));

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping(value = "/orginfo/orgInfoOptinfoAjax")
    public String selectOrgInfoOptinfoAjax(@ModelAttribute("orgOptinfoVO") OrgOptinfoVO orgOptinfoVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        // 영수증출력용지구분설정
        model.addAttribute("receiptList", codeService.selectCodeList("SM_RECEIPT_TYPE"));
        // 금액 단위
        model.addAttribute("updownUnitList", codeService.selectCodeList("SM_AMOUNT_UNIT"));
        // 절상 절사 단위
        model.addAttribute("updownList", codeService.selectCodeList("SM_RNDUPDN_GBN"));
        // 연령계산
        model.addAttribute("ageList", codeService.selectCodeList("SM_AGE_APPGN"));

        // 내용 셀렉트
        orgOptinfoVO = orgInfoService.selectOrgOptinfo(orgOptinfoVO);

        model.addAttribute("orgOptinfoVO", orgOptinfoVO);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping(value = "/orginfo/orgInfoDcAjax")
    public String selectOrgInfoDcAjax(@ModelAttribute("orgDcVO") OrgInfoVO orgDcVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        // 감면정보
        orgDcVO.setOrgDcList(orgInfoService.selectDcList(orgDcVO.getComcd(), orgDcVO.getOrgNo()));

        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping(value = "/orginfo/orgInfoRegist")
    public String selectOrgInfoRegist(@ModelAttribute("orgInfoVO") OrgInfoVO orgInfoVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        orgInfoVO.setComcd(Config.COM_CD);
        orgInfoVO.setOrgDcList(orgInfoService.selectDcList(orgInfoVO.getComcd(), orgInfoVO.getOrgNo())); // 감면 기준 목록
        orgInfoVO.setOrgCntsactiveyn("Y");

        // 지역
        model.addAttribute("areaList", areaCdService.selectAreaCdList(null));
        // 영수증출력용지구분설정
        model.addAttribute("receiptList", codeService.selectCodeList("SM_RECEIPT_TYPE"));
        // 금액 단위
        model.addAttribute("updownUnitList", codeService.selectCodeList("SM_AMOUNT_UNIT"));
        // 절상 절사 단위
        model.addAttribute("updownList", codeService.selectCodeList("SM_RNDUPDN_GBN"));
        // 연령계산
        model.addAttribute("ageList", codeService.selectCodeList("SM_AGE_APPGN"));
        // 상위 기관 선택
        orgInfoVO.setLevel("1");
        model.addAttribute("upOrgList", orgInfoService.selectOrgInfoList(orgInfoVO));

        // 기관유형
        model.addAttribute("typeList", codeService.selectCodeList("SM_ORG_LTYPE"));

        model.addAttribute("commandMap", commandMap);
        model.addAttribute("adminRoot", configUtil.getAdminPath(request));

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "기관 정보 등록", action = "C")
    @PostMapping(value = "/orginfo/orgInfoRegistSave")
    public String insertOrgInfoRegist(@ModelAttribute("orgInfoVO") OrgInfoVO orgInfoVO,
            CommandMap commandMap,
            final MultipartHttpServletRequest multiRequest,
            HttpServletResponse response,
            BindingResult bindingResult, ModelMap model)
            throws Exception {

        beanValidator.validate(orgInfoVO, bindingResult); // validation 수행

        if (bindingResult.hasErrors()) {
            orgInfoVO.setOrgDcList(orgInfoService.selectDcList(orgInfoVO.getComcd(), orgInfoVO.getOrgNo())); // 감면 기준 목록
            // 지역
            model.addAttribute("areaList", areaCdService.selectAreaCdList(null));
            // 영수증출력용지구분설정
            model.addAttribute("receiptList", codeService.selectCodeList("SM_RECEIPT_TYPE"));
            // 금액 단위
            model.addAttribute("updownUnitList", codeService.selectCodeList("SM_AMOUNT_UNIT"));
            // 절상 절사 단위
            model.addAttribute("updownList", codeService.selectCodeList("SM_RNDUPDN_GBN"));
            // 연령계산
            model.addAttribute("ageList", codeService.selectCodeList("SM_AGE_APPGN"));
            // 상위 기관 선택
            orgInfoVO.setLevel("1");
            model.addAttribute("upOrgList", orgInfoService.selectOrgInfoList(orgInfoVO));
            model.addAttribute("commandMap", commandMap);
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/orginfo/orgInfoRegist");
        } else {
            try {
                LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
                String orgFileid = "";
                String orgStampimgFfinnb = "";

                // 파일 업로드
                final Map<String, MultipartFile> files = multiRequest.getFileMap();

                if (files != null) {
                    List<FileVO> result = fileUtil.parseFileInf(files, "ORG_", 0, "", "", user.getId(), "file_1");
                    if (result != null && result.size() > 0) {
                        fileMngService.insertFileInfs(result);
                        orgFileid = result.get(0).getFileGrpinnb();
                    }

                    List<FileVO> result4 = fileUtil.parseFileInf(files, "ORG_", 0, "", "", user.getId(), "file_4");
                    if (result4 != null && result4.size() > 0) {
                        fileMngService.insertFileInfs(result4);
                        orgStampimgFfinnb = result4.get(0).getFileGrpinnb();
                    }

                }

                orgInfoVO.getOrgContents().setOrgFileid(orgFileid);
                orgInfoVO.getOrgOptinfo().setOrgStampimgFfinnb(orgStampimgFfinnb);

                orgInfoVO.setOrgNo(codeService.selectNextseq("ORG_INFO")); // 시퀀스 구하기
                orgInfoVO.setReguser(user.getId());

                orgInfoService.insertOrgInfo(orgInfoVO);

                HttpUtility.sendRedirect(multiRequest, response, "처리되었습니다.", Config.ADMIN_ROOT + "/orginfo/orgInfoList" + commandMap.getString("searchQuery"));
                return null;
            } catch (Exception e) {
                orgInfoVO.setOrgDcList(orgInfoService.selectDcList(orgInfoVO.getComcd(), orgInfoVO.getOrgNo())); // 감면
                // 지역
                model.addAttribute("areaList", areaCdService.selectAreaCdList(null));
                // 영수증출력용지구분설정
                model.addAttribute("receiptList", codeService.selectCodeList("SM_RECEIPT_TYPE"));
                // 금액 단위
                model.addAttribute("updownUnitList", codeService.selectCodeList("SM_AMOUNT_UNIT"));
                // 절상 절사 단위
                model.addAttribute("updownList", codeService.selectCodeList("SM_RNDUPDN_GBN"));
                // 연령계산
                model.addAttribute("ageList", codeService.selectCodeList("SM_AGE_APPGN"));
                // 상위 기관 선택
                orgInfoVO.setLevel("1");
                model.addAttribute("upOrgList", orgInfoService.selectOrgInfoList(orgInfoVO));

                model.addAttribute("errorMsg", e.getMessage());
                model.addAttribute("commandMap", commandMap);

                e.printStackTrace();
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/orginfo/orgInfoRegist");
            }

        }
    }

    @PageActionInfo(title = "기관 소개 이미지 수정", action = "U", ajax = true)
    @PostMapping(value = "/orginfo/orgInfoFileUpload.json")
    @ResponseBody
    public ModelAndView insertOrgInfoFileUpload(CommandMap commandMap,
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

        if (!files.isEmpty()) {
            try {
                // 기존 파일 Id
                atchFileId = commandMap.getString("atchFileId");
                List<FileVO> result = fileUtil.parseFileInf(files, "ORG_", 0, atchFileId, uploadFolder, user.getId());
                Iterator<FileVO> iter = result.iterator();

                if (!atchFileId.equals("")) {
                    // 기존 파일 삭제
                    fileMngService.deleteAndInsert(result.get(0), result);
                } else {
                    atchFileId = fileMngService.insertFileInfs(result);
                }

                // DB 업데이트
                if (commandMap.getString("inputid").equals("orgStampimgFfinnb")) {
                    // 직인 이미지
                    orgInfoService.updateOrgOptinfoFileid(commandMap.getString("comcd"), Integer.parseInt(commandMap.getString("orgNo")), commandMap.getString("inputid"), atchFileId, user.getId());
                } else {
                    orgInfoService.updateOrgContentsFileid(commandMap.getString("comcd"), Integer.parseInt(commandMap.getString("orgNo")), commandMap.getString("inputid"), atchFileId, user.getId());
                }

                while (iter.hasNext()) {
                    FileVO vo = iter.next();
                    originFileName = vo.getOrginFileName();
                    uploadFolder = vo.getFilePath();
                    realFilePath = multiRequest.getContextPath() + Config.USER_ROOT + "/common/file/view/" + uploadFolder + vo.getFileName() + "?originName=" + java.net.URLEncoder.encode(originFileName, "UTF-8");
                }

                resultInfo = HttpUtility.getSuccessResultInfo("저장 되었습니다.");

            } catch (Exception e) {
                resultInfo = HttpUtility.getErrorResultInfo(e.getMessage());
            }
        }

        mav.addObject("result", resultInfo);
        mav.addObject("originFileName", originFileName);
        mav.addObject("realFilePath", realFilePath);
        mav.addObject("atchFileId", atchFileId);
        return mav;

    }

    /**
     * 기관정보 수정
     *
     * @param orgInfoVO
     * @param model
     * @return
     * @throws Exception
     */ 
    @PostMapping("/orginfo/orgInfoDetailSave.json")
    @PageActionInfo(title = "기관 정보 수정", action = "U", ajax = true)
    @ResponseBody
    public ModelAndView updateOrgInfoDetail(@ModelAttribute("orgInfoVO") OrgInfoVO orgInfoVO,
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        orgInfoVO.setModuser(user.getId());

        orgInfoService.updateOrgInfo(orgInfoVO);
        resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 기관소개 수정
     *
     * @param orgContentsVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/orginfo/orgInfoContentsSave.json")
    @PageActionInfo(title = "기관 소개 수정", action = "U", ajax = true)
    @ResponseBody
    public ModelAndView orgInfoContentsSave(@ModelAttribute("orgContentsVO") OrgContentsVO orgContentsVO,
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        orgContentsVO.setModuser(user.getId());

        orgInfoService.updateOrgContents(orgContentsVO);
        resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 강좌소개 수정
     *
     * @param orgContentsVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/orginfo/orgInfoGuideSave.json")
    @PageActionInfo(title = "기관 강좌안내 수정", action = "U", ajax = true)
    @ResponseBody
    public ModelAndView orgInfoGuideSave(@ModelAttribute("orgContentsVO") OrgContentsVO orgContentsVO,
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        orgContentsVO.setModuser(user.getId());

        orgInfoService.updateOrgContentsGude(orgContentsVO);
        resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 기관소개 수정
     *
     * @param orgContentsVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/orginfo/orgInfoOptinfoSave.json")
    @PageActionInfo(title = "기관 운영환경 수정", action = "U", ajax = true)
    @ResponseBody
    public ModelAndView orgInfoOptinfoSave(@ModelAttribute("orgOptinfoVO") OrgOptinfoVO orgOptinfoVO,
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        orgOptinfoVO.setModuser(user.getId());

        orgInfoService.updateOrgOptinfo(orgOptinfoVO);
        resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 기관소개 수정
     *
     * @param orgContentsVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/orginfo/orgInfoDcSave.json")
    @PageActionInfo(title = "기관 감면정보 수정", action = "U", ajax = true)
    @ResponseBody
    public ModelAndView orgInfoDcSave(@ModelAttribute("orgInfoVO") OrgInfoVO orgInfoVO,
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        orgInfoVO.setReguser(user.getId());

        orgInfoService.updateOrgDc(orgInfoVO);
        resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 기관정보 삭제
     *
     * @param orgInfoVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/orginfo/orgInfoDelete.json")
    @PageActionInfo(title = "기관 정보 삭제", action = "D", ajax = true)
    @ResponseBody
    public ModelAndView deleteOrgInfoDetail(@ModelAttribute("orgInfoVO") OrgInfoVO orgInfoVO,
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        orgInfoVO.setModuser(user.getId());

        orgInfoService.deleteOrgInfo(orgInfoVO);
        resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 기관 목록을 json 형태로 반환
     *
     * @param orgType
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping(value = "/orginfo/orgList.json")
    @ResponseBody
    public ModelAndView contentUpdate(@ModelAttribute("orgInfoVO") OrgInfoVO orgInfoVO, CommandMap commandMap,
            HttpServletResponse response, HttpServletRequest request) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        orgInfoVO.setSearchStat(orgInfoVO.getOrgLtype());
        orgInfoVO.setSearchUseYn(orgInfoVO.getOrgMtype());

        List<?> orgList = orgInfoService.selectOrgInfoList(orgInfoVO);

        mav.addObject("list", orgList);
        return mav;

    }

}
