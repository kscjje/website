package com.hisco.admin.banner.web;

import java.util.ArrayList;
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
import com.hisco.cmm.config.DynamicConfigUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.CodeVO;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.bnr.service.Banner;
import egovframework.com.uss.ion.bnr.service.BannerVO;
import egovframework.com.uss.ion.bnr.service.EgovBannerService;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 배너 관리를 위한 콘트롤러
 *
 * @author 진수진
 * @since 2020.07.30
 * @version 1.0, 2020.07.30
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.30 최초작성
 */

@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class BannerController {

    /** logService */
    @Resource(name = "logService")
    private LogService logService;

    @Resource(name = "FileMngUtil")
    private FileMngUtil fileUtil;

    @Resource(name = "FileMngService")
    private FileMngService fileMngService;

    @Resource(name = "egovBannerService")
    private EgovBannerService egovBannerService;

    /** Message ID Generation */
    @Resource(name = "egovBannerIdGnrService")
    private EgovIdGnrService egovBannerIdGnrService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "dynamicConfigUtil")
    private DynamicConfigUtil configUtil;

    @Autowired
    private DefaultBeanValidator beanValidator;

    /**
     * 일반배너, 기관홍보 배너 구분 (url에 org 있으면 기관)
     */
    private String isOrg(HttpServletRequest request) {

        String yn = "N";
        if (request.getServletPath().indexOf("org") > -1) {
            yn = "Y";
        }
        return yn;
    }

    /**
     * 일반배너, 기관홍보 배너 구분 (url에 org 있으면 기관)
     */
    private ArrayList<CodeVO> getBannerGbn(String isOrg) throws Exception {

        ArrayList<CodeVO> bannerGbn = new ArrayList<CodeVO>();
        List<CodeVO> temp = codeService.selectCodeList("WEB_BANNER_GBN");
        if (temp.size() > 0) {
            for (int i = 0; i < temp.size(); i++) {
                CodeVO c = temp.get(i);
                if (isOrg.equals("N")) {
                    if ("1001".equals(c.getCd())) {
                        bannerGbn.add(c);
                    }
                } else {
                    if (!"1001".equals(c.getCd())) {
                        bannerGbn.add(c);
                    }
                }
            }
        }

        return bannerGbn;
    }

    /**
     * 배너 목록을 조회한다.
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "배너 목록 조회", action = "R")
    @GetMapping(value = { "/banner/bannerList", "/banner/orgBannerList" })
    public String bannerList(@ModelAttribute("bannerVO") BannerVO bannerVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        // 배너 종류 ( WEB_BANNER_GBN 코드그룹 새로 추가함 )
        String isOrg = isOrg(request);
        bannerVO.setIsOrg(isOrg);
        bannerVO.setComcd(Config.COM_CD);

        List<CodeVO> bannerGbn = getBannerGbn(isOrg);
        model.addAttribute("bannerGbn", bannerGbn);

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        bannerVO.setPaginationInfo(paginationInfo);

        int totCnt = egovBannerService.selectBannerListTotCnt(bannerVO);
        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("resultList", egovBannerService.selectBannerList(bannerVO));
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("searchVO", bannerVO);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 등록된 배너의 상세정보를 조회한다.
     *
     * @param bannerVO
     *            - 배너 Vo
     * @return String - 리턴 Url
     */
    @PageActionInfo(title = "배너 상세정보 조회", action = "R")
    @GetMapping(value = { "/banner/bannerDetail", "/banner/orgBannerDetail" })
    public String bannerDetail(@ModelAttribute("bannerVO") BannerVO bannerVO, CommandMap commandMap,
            HttpServletRequest request,
            ModelMap model) throws Exception {

        BannerVO data = egovBannerService.selectBanner(bannerVO);
        if (data.getBannerImageFile() != null) {
            // 파일 리스트 검색
            if (!"".equals(data.getBannerImageFile())) {
                FileVO fileVO = new FileVO();
                fileVO.setFileGrpinnb(data.getBannerImageFile());
                model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
            }
        }
        model.addAttribute("result", data);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("bannerId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("commandMap", commandMap);
        // 배너 종류
        model.addAttribute("bannerGbn", codeService.selectCodeList("WEB_BANNER_GBN"));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 배너 등록화면
     *
     * @param bannerVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping({ "/banner/bannerRegist", "/banner/orgBannerRegist" })
    public String bannerRegist(@ModelAttribute("bannerVO") BannerVO bannerVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        // 신규등록
        model.addAttribute("mode", "add");
        model.addAttribute("banner", bannerVO);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("bannerId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("commandMap", commandMap);

        // 개시 시작일자(시)
        model.addAttribute("ntceBgndeHH", DateUtil.getTimeHH());
        // 개시 시작일자(분)
        model.addAttribute("ntceBgndeMM", DateUtil.getTimeMM());
        // 개시 종료일자(시)
        model.addAttribute("ntceEnddeHH", DateUtil.getTimeHH());
        // 개시 정료일자(분)
        model.addAttribute("ntceEnddeMM", DateUtil.getTimeMM());

        // 배너 종류 ( WEB_BANNER_GBN 코드그룹 새로 추가함 )
        String isOrg = isOrg(request);
        List<CodeVO> bannerGbn = getBannerGbn(isOrg);
        model.addAttribute("bannerGbn", bannerGbn);

        boolean hasRole = CommonUtil.hasRole("ROLE_SUPER");// 슈퍼관리자 권한 체크
        model.addAttribute("hasRole", hasRole);

        if (!hasRole) {
            // 슈퍼관리자가 아니면 사용안함으로 등록한다
            bannerVO.setReflctAt("N");
            bannerVO.setSortOrdr("99");
        }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 배너 등록화면
     *
     * @param bannerVO
     * @param model
     * @return
     * @throws Exception
     */

    @GetMapping({ "/banner/bannerUpdt", "/banner/orgBannerUpdt" })
    public String bannerUpdt(@ModelAttribute("bannerVO") BannerVO bannerVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        // 배너 종류 ( WEB_BANNER_GBN 코드그룹 새로 추가함 )
        String isOrg = isOrg(request);
        bannerVO.setIsOrg(isOrg);

        List<CodeVO> bannerGbn = getBannerGbn(isOrg);
        model.addAttribute("bannerGbn", bannerGbn);

        // 수정
        model.addAttribute("banner", egovBannerService.selectBanner(bannerVO));

        model.addAttribute("mode", "edit");
        model.addAttribute("searchQuery", commandMap.getSearchQuery("bannerId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("commandMap", commandMap);

        // 개시 시작일자(시)
        model.addAttribute("ntceBgndeHH", DateUtil.getTimeHH());
        // 개시 시작일자(분)
        model.addAttribute("ntceBgndeMM", DateUtil.getTimeMM());
        // 개시 종료일자(시)
        model.addAttribute("ntceEnddeHH", DateUtil.getTimeHH());
        // 개시 정료일자(분)
        model.addAttribute("ntceEnddeMM", DateUtil.getTimeMM());

        model.addAttribute("isManager", configUtil.isAdminEX(request));
        model.addAttribute("hasRole", CommonUtil.hasRole("ROLE_SUPER")); // 슈퍼관리자 권한 체크

        String view = "/banner/bannerRegist";
        if ("Y".equals(isOrg)) {
            view = "/banner/orgBannerRegist";
        }
        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, view);
    }

    /**
     * 배너정보를 신규로 등록한다.
     *
     * @param banner
     *            - 배너 model
     * @return url
     */
    @SuppressWarnings("unused")
    @PostMapping(value = "/banner/bannerSave")
    public String bannerSave(final MultipartHttpServletRequest multiRequest, HttpServletResponse response,
            @ModelAttribute("banner") Banner banner,
            CommandMap commandMap,
            BindingResult bindingResult,
            ModelMap model) throws Exception {

        if (banner.getBannerId() != null && !banner.getBannerId().equals("")) {
            if (!logService.checkAdminLog(commandMap, "U", "배너 수정")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        } else {
            if (!logService.checkAdminLog(commandMap, "C", "배너 등록")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        }

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        banner.setFrstRegisterId(user.getId());

        beanValidator.validate(banner, bindingResult); // validation 수행

        if (bindingResult.hasErrors()) {
            model.addAttribute("banner", banner);
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/banner/bannerRegist");
        } else {
            List<FileVO> result = null;

            String uploadFolder = "";
            String bannerImage = "";
            String bannerImageFile = "";
            String atchFileId = "";

            final Map<String, MultipartFile> files = multiRequest.getFileMap();

            if (!files.isEmpty()) {
                // List<FileVO> parseFileInf(Map<String, MultipartFile> files, String KeyStr, int fileKeyParam, String
                // atchFileId, String storePath)

                try {
                    result = fileUtil.parseFileInf(files, "BNR_", 0, "", uploadFolder, user.getId());
                } catch (Exception e) {
                    HttpUtility.sendBack(multiRequest, response, e.getMessage());
                    return null;
                }

                atchFileId = fileMngService.insertFileInfs(result);

                FileVO vo = null;
                Iterator<FileVO> iter = result.iterator();

                while (iter.hasNext()) {
                    vo = iter.next();
                    bannerImage = vo.getOrginFileName();
                    bannerImageFile = vo.getFileName();
                }
                if (vo == null) {
                    banner.setAtchFile(false);
                } else {
                    banner.setBannerImage(bannerImage);
                    banner.setBannerImageFile(atchFileId);
                    banner.setAtchFile(true);
                }

            } else {
                banner.setAtchFile(false);
            }
            if (banner.getBannerId() != null && !banner.getBannerId().equals("")) {
                egovBannerService.updateBanner(banner);
            } else {
                banner.setBannerId(egovBannerIdGnrService.getNextStringId());
                egovBannerService.insertBanner(banner);
            }

            String url = Config.ADMIN_ROOT + "/banner/bannerList";
            String isOrg = commandMap.getString("isOrg");
            if (isOrg != null && "Y".equals(isOrg)) {
                url = Config.ADMIN_ROOT + "/banner/orgBannerList";
            }
            HttpUtility.sendRedirect(multiRequest, response, "처리되었습니다.", url + commandMap.getString("searchQuery"));
            return null;

        }
    }

    /**
     * 배너 삭제
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/banner/bannerDelete")
    @ResponseBody
    public ModelAndView bannerDelete(@ModelAttribute("bannerVO") Banner banner, HttpServletRequest request,
            CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        if (!logService.checkAdminLog(commandMap, "D", "배너 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            egovBannerService.deleteBanner(banner);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

}
