package com.hisco.admin.partcd.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hisco.admin.log.service.LogService;
import com.hisco.admin.partcd.service.PartCdService;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.TemplateVO;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.pwm.service.PopupManageVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 사업장 관리
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
@RequestMapping(value = Config.ADMIN_ROOT)
public class PartCdController {

    @Resource(name = "partCdService")
    private PartCdService partCdService;

    @Resource(name = "logService")
    private LogService logService;

    /**
     * 메뉴 목록 화면
     * 
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @GetMapping("/partcd/partCdList")
    public String selectPartCdList(@ModelAttribute("searchVO") ComDefaultVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> paramMap) throws Exception {

        paramMap.put("comcd", Config.COM_CD);

        if (!logService.checkAdminLog(commandMap, "R", "사업장 목록 조회")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paramMap.put("firstIndex", paginationInfo.getFirstRecordIndex());
        paramMap.put("lastIndex", paginationInfo.getLastRecordIndex());
        paramMap.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());

        List<?> partCdList = partCdService.selectPartCdList(paramMap);

        int intTbAllCount = 0;
        if (partCdList.size() >= 1) {
            CamelMap camelMap = (CamelMap) partCdList.get(0);
            intTbAllCount = Integer.valueOf(String.valueOf(camelMap.get("tbAllCount")));
        }

        paginationInfo.setTotalRecordCount(intTbAllCount);

        model.addAttribute("partCdList", partCdList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paramMap", paramMap);
        model.addAttribute("paginationInfo", paginationInfo);

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
    @GetMapping("/partcd/partCdRegi")
    public String selectPartCdRegi(@ModelAttribute("templateVO") TemplateVO templateVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> paramMap) throws Exception {

        paramMap.put("grpCd", "SM_PART_GBN");
        List<?> cotGrpCdList = partCdService.selectCotGrpCdList(paramMap);

        model.addAttribute("cotGrpCdList", cotGrpCdList);

        /*
         * model.addAttribute("mode" , "add");
         * model.addAttribute("popup" , popupManageVO);
         * model.addAttribute("searchQuery", commandMap.getSearchQuery("popupId"));
         * model.addAttribute("commandMap" , commandMap);
         * //팝업창시작일자(시)
         * model.addAttribute("ntceBgndeHH", getTimeHH());
         * //팝업창시작일자(분)
         * model.addAttribute("ntceBgndeMM", getTimeMM());
         * //팝업창종료일자(시)
         * model.addAttribute("ntceEnddeHH", getTimeHH());
         * //팝업창정료일자(분)
         * model.addAttribute("ntceEnddeMM", getTimeMM());
         */

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 메뉴 목록 화면
     * 
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @RequestMapping(value = "/partcd/partCdSave", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    public String savePartCd(@ModelAttribute("templateVO") ComDefaultVO searchVO, CommandMap commandMap,
            final MultipartHttpServletRequest multiRequest, HttpServletResponse response, HttpServletRequest request,
            ModelMap model, @RequestParam Map<String, Object> paramMap) throws Exception {

        paramMap.put("comcd", Config.COM_CD);

        String strPartCd = String.valueOf(paramMap.get("partCd"));
        String strMode = String.valueOf(paramMap.get("mode"));

        if (strPartCd != null && !strPartCd.equals("")) {
            if (!logService.checkAdminLog(commandMap, "U", "사업장 수정")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        } else {
            if (!logService.checkAdminLog(commandMap, "C", "사업장 등록")) {
                return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
            }
        }

        LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        if (loginVO == null) {
            HttpUtility.sendRedirect(multiRequest, response, "로그인 정보를 찾을 수 없습니다.", Config.ADMIN_ROOT + "/login" + commandMap.getString("searchQuery"));
        }

        if (loginVO != null) {
            paramMap.put("userId", loginVO.getId());
        }

        if (strMode == null) {
            strMode = "";
        }

        if ("delete".equals(strMode)) {
            partCdService.deletePartCd(paramMap);
        } else if ("insert".equals(strMode)) {
            partCdService.insertPartCd(paramMap);
        } else if ("update".equals(strMode)) {
            partCdService.updatePartCd(paramMap);
        }

        HttpUtility.sendRedirect(multiRequest, response, "정상으로 처리되었습니다.", Config.ADMIN_ROOT + "/partcd/partCdList" + commandMap.getString("searchQuery"));
        return null;

    }

    /**
     * 팝업 수정 등록화면
     *
     * @param bannerVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/partcd/partCdUpdt")
    public String partCdUpdt(@ModelAttribute("templateVO") TemplateVO templateVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> paramMap) throws Exception {

        paramMap.put("comcd", Config.COM_CD);
        paramMap.put("grpCd", "SM_PART_GBN");
        List<?> cotGrpCdList = partCdService.selectCotGrpCdList(paramMap);
        List<?> partCdList = partCdService.selectPartCdDetail(paramMap);

        model.addAttribute("cotGrpCdList", cotGrpCdList);
        model.addAttribute("partCdList", partCdList);
        model.addAttribute("mode", "update");
        model.addAttribute("searchQuery", commandMap.getSearchQuery("partCd"));
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/partcd/partCdRegi");
    }

    /**
     * 팝업 삭제 등록화면
     *
     * @param bannerVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/partcd/partCdDelt", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    public String partCdDelt(@ModelAttribute("templateVO") TemplateVO templateVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> paramMap) throws Exception {

        int intDelRow = partCdService.deletePartCd(paramMap);

        log.debug("intDelRow = {}", intDelRow);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/partcd/partCdList" + commandMap.getString("searchQuery"));

    }

    /**
     * 등록된 팝업의 상세정보를 조회한다.
     * 
     * @param bannerVO
     *            - 팝업 Vo
     * @return String - 리턴 Url
     */
    @RequestMapping(value = "/partcd/partCdDetail")
    public String selectPartCd(@ModelAttribute("templateVO") PopupManageVO popupManageVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model, @RequestParam Map<String, Object> paramMap) throws Exception {

        if (!logService.checkAdminLog(commandMap, "R", "사업장 상세 조회")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        paramMap.put("comcd", Config.COM_CD);

        List<?> partCdDetail = partCdService.selectPartCdDetail(paramMap);
        model.addAttribute("partCdDetail", partCdDetail);

        model.addAttribute("searchQuery", commandMap.getSearchQuery("partCd")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

}
