package com.hisco.user.banner.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.service.FileMngService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.vo.FileVO;

import egovframework.com.uss.ion.bnr.service.BannerVO;
import egovframework.com.uss.ion.bnr.service.EgovBannerService;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
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
@RequestMapping(value = Config.USER_ROOT + "/banner")
public class WebBannerController {

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

    @Autowired
    private DefaultBeanValidator beanValidator;

    /**
     * 등록된 배너의 상세정보를 조회한다.
     * 
     * @param bannerVO
     *            - 배너 Vo
     * @return String - 리턴 Url
     */
    @GetMapping(value = "/view")
    public String view(@ModelAttribute("bannerVO") BannerVO bannerVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {

        BannerVO data = egovBannerService.selectBanner(bannerVO);

        if (data == null) {
            HttpUtility.sendBack(request, response, "잘못된 접근입니다.");
            return null;
        }

        if (!"Y".equals(data.getIsActive())) {
            HttpUtility.sendBack(request, response, "잘못된 접근입니다.");
            return null;
        }

        if (data.getBannerImageFile() != null) {
            // 파일 리스트 검색
            if (!"".equals(data.getBannerImageFile())) {
                FileVO fileVO = new FileVO();
                fileVO.setFileGrpinnb(data.getBannerImageFile());
                model.addAttribute("fileList", fileMngService.selectFileInfs(fileVO));
            }
        }
        model.addAttribute("result", data);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

}
