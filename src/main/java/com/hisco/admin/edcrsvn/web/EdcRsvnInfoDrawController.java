package com.hisco.admin.edcrsvn.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.admin.edcrsvn.service.EdcRsvnInfoDrawService;
import com.hisco.admin.edcrsvn.vo.EdcRsvnInfoDrawVO;
import com.hisco.admin.eduadm.service.EduAdmService;
import com.hisco.admin.log.service.LogService;
import com.hisco.admin.manager.service.SysUserService;
import com.hisco.admin.manager.vo.SysUserVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.annotation.PageActionType;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.SessionUtil;
import com.hisco.user.edcatnlc.service.EdcProgramService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;
import com.hisco.user.edcatnlc.vo.EdcRsvnInfoVO;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * ??????????????????
 *
 * @author
 * @since
 * @version
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}/edcrsvn", "#{dynamicConfig.managerRoot}/edcrsvn" })
public class EdcRsvnInfoDrawController {

    private String adminRoot = Config.ADMIN_ROOT;

    @Resource(name = "logService")
    private LogService logService;

    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "eduAdmService")
    private EduAdmService eduAdmService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    @Resource(name = "edcRsvnInfoDrawService")
    private EdcRsvnInfoDrawService edcRsvnInfoDrawService;

    @Resource(name = "sysUserService")
    private SysUserService sysUserService;

    private final static String DO_DRAW_RESULT = "doDrawResult"; // ?????? ?????? ?????? ??????????????? ???????????? key???

    @PageActionInfo(title = "????????????>????????????", action = PageActionType.READ)
    @GetMapping("/edcDrawProgramList")
    public String edcRsvnInfoDraw(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        // ???????????? ??????????????? ?????????, ???????????? ?????? ??????????????? ???????????? ??????
        if (StringUtils.isBlank(searchVO.getSearchOrgNo())) {
            LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            SysUserVO sysUserVO = new SysUserVO();
            sysUserVO.setUserId(loginVO.getId());
            sysUserVO = sysUserService.selectMstRecord(sysUserVO);
            searchVO.setSearchOrgNo(String.valueOf(sysUserVO.getOrgNo()));
        }

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("programList", Collections.EMPTY_LIST);
        model.addAttribute("commandMap", commandMap);

        SessionUtil.removeAttribute("doDrawResultVO");

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping("/edcDrawProgramListAjax")
    public String edcDrawProgramListAjax(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (StringUtils.isBlank(searchVO.getSearchOrgNo())) {
            searchVO.setSearchOrgNo("-1");
        }

		PaginationInfo paginationInfo = commandMap.getPagingInfo();
		paginationInfo.setRecordCountPerPage(searchVO.getRecordCountPerPage());
		searchVO.setPaginationInfo(paginationInfo);

        searchVO.setEdcRsvnRectype(Constant.SM_LEREC_TYPE_???????????????);
        List<EdcProgramVO> programList = edcProgramService.selectProgramList(searchVO);

        int totCount = 0;
        if (programList != null && programList.size() >= 1) {
            totCount = ((EdcProgramVO) programList.get(0)).getTotCount();
        }

        paginationInfo.setTotalRecordCount(totCount);

        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("programList", programList);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("totCount", totCount);

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "????????????????????????", action = PageActionType.READ, ajax = true)
    @PostMapping("/edcRsvnInfoList.json")
    public String edcRsvnInfoListJson(@RequestBody EdcRsvnInfoVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        searchVO.setChannel(Constant.CM_CHANNEL_BO);
        searchVO.setEdcRsvnRectype(Constant.SM_LEREC_TYPE_???????????????);
        // searchVO.setEdcStatList(Arrays.asList(Constant.SM_RSVN_STAT_????????????, Constant.SM_RSVN_STAT_????????????,
        // Constant.SM_RSVN_STAT_????????????));
        searchVO.setEdcStat(Constant.SM_RSVN_STAT_????????????);
        searchVO.setUsePagingYn(Config.NO);

        model.addAttribute("rsvnInfoList", edcRsvnInfoService.selectRsvnList(searchVO));

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "????????????>????????????", action = PageActionType.UPDATE, ajax = true)
    @PostMapping("/edcRsvnInfoDoDraw.json")
    public String edcRsvnInfoDoDraw(@RequestBody EdcRsvnInfoDrawVO paramVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        ResultInfo resultInfo = new ResultInfo(Config.SUCCESS, "??????????????? ?????????????????????.");

        try {
            edcRsvnInfoDrawService.doDraw(paramVO);
        } catch (Exception ex) {
            resultInfo = new ResultInfo(Config.FAIL, ex.getMessage());
        }

        // ?????? ?????? ??????????????? ?????? ????????????
        SessionUtil.setAttribute(DO_DRAW_RESULT, paramVO);

        model.clear();
        model.addAttribute("resultVO", paramVO);
        model.addAttribute("result", resultInfo);

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "????????????>??????????????????", action = PageActionType.UPDATE, ajax = true)
    @PostMapping("/edcRsvnInfoUndoDraw.json")
    public String edcRsvnInfoUndoDraw(CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        // ???????????? ?????? ????????? ????????? ???????????? ????????????
        EdcRsvnInfoDrawVO drawVO = (EdcRsvnInfoDrawVO) SessionUtil.getAttribute(DO_DRAW_RESULT);

        ResultInfo resultInfo = new ResultInfo(Config.SUCCESS, "??????????????? ?????????????????????.");

        try {
            edcRsvnInfoDrawService.undoDraw(drawVO);
        } catch (Exception ex) {
            resultInfo = new ResultInfo(Config.FAIL, ex.getMessage());
        }

        model.clear();
        model.addAttribute("result", resultInfo);

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "????????????>??????", action = PageActionType.UPDATE, ajax = true)
    @PostMapping("/edcRsvnInfoConfirmDraw.json")
    public String edcRsvnInfoConfirmDraw(CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        // ???????????? ?????? ????????? ????????? ???????????? ????????????
        EdcRsvnInfoDrawVO drawVO = (EdcRsvnInfoDrawVO) SessionUtil.getAttribute(DO_DRAW_RESULT);

        ResultInfo resultInfo = new ResultInfo(Config.SUCCESS, "??????????????? ?????????????????????.");

        try {
            edcRsvnInfoDrawService.confirmDraw(drawVO);
            SessionUtil.removeAttribute(DO_DRAW_RESULT);
        } catch (Exception ex) {
            resultInfo = new ResultInfo(Config.FAIL, ex.getMessage());
        }

        model.clear();
        model.addAttribute("result", resultInfo);

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "????????????>????????????", action = PageActionType.READ, ajax = true, inqry = true)
    @GetMapping("/edcDrawDoneRsvnInfoList.json")
    public String edcDrawDoneRsvnInfoList(@ModelAttribute EdcRsvnInfoVO searchVO,
            CommandMap commandMap, ModelMap model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        searchVO.setEdcRsvnRectype(Constant.SM_LEREC_TYPE_???????????????);
        searchVO.setPrzwinStat(Constant.SM_PRZWIN_STAT_????????????);
        searchVO.setUsePagingYn(Config.NO);
        searchVO.setEdcStat("");

        model.addAttribute("rsvnInfoList", edcRsvnInfoService.selectRsvnList(searchVO));

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "????????????>???????????? ??????????????????", action = PageActionType.READ)
    @GetMapping(value = "/edcDrawDoneRsvnInfoListExcel")
    public void edcDrawDoneRsvnInfoListExcel(EdcRsvnInfoVO searchVO, CommandMap commandMap,
            HttpServletResponse response,
            HttpServletRequest request, ModelMap model) throws Exception {

        String templete = "edcDrawDoneList";
        String file_name = "????????????";

        searchVO.setEdcRsvnRectype(Constant.SM_LEREC_TYPE_???????????????);
        searchVO.setPrzwinStat(Constant.SM_PRZWIN_STAT_????????????);
        searchVO.setUsePagingYn(Config.NO);
        searchVO.setEdcStat("");
        searchVO.setExcelyn("Y");

        List<EdcRsvnInfoVO> resultList = edcRsvnInfoService.selectRsvnList(searchVO);

        if (resultList == null || resultList.isEmpty())
            return;

        for (EdcRsvnInfoVO vo : resultList) {
            vo.setEdcRsvnMoblphon(StringUtils.isBlank(vo.getEdcRsvnMoblphon())
                    ? "" : vo.getEdcRsvnMoblphon().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3"));

            vo.setEdcRsvnBirthdate(StringUtils.isBlank(vo.getEdcRsvnBirthdate())
                    ? "" : vo.getEdcRsvnBirthdate().replaceAll("(\\d{4})(\\d{2})(\\d{2})", "$1-$2-$3"));
        }

        // ?????? ????????? ?????? ??? ?????? ?????? data
        Map data = new HashMap();
        data.put("programNm", resultList.get(0).getEdcPrgmnm());
        data.put("list", resultList);

        XLSTransformer transformer = new XLSTransformer();

        InputStream is = readTemplate(templete + ".xls");
        Workbook workbook = null;
        try {
            workbook = transformer.transformXLS(is, data);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            response.getWriter().println("?????? ????????? ?????? ??? ?????? ??????<br>" + e.getMessage());
        }

        // ?????? ????????? contentType ??????
        response.setContentType("application/vnd.ms-excel");

        // ?????? ????????? ??????
        response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName(file_name + ".xls"));
        workbook.write(response.getOutputStream());
    }

    /** ?????? ???????????? ?????????. */
    private InputStream readTemplate(String finalTemplate) throws FileNotFoundException {

        String templateFilePath = FileMngUtil.GetRealRootPath().concat("WEB-INF/excelTemplate/" + finalTemplate);

        return new FileInputStream(templateFilePath);
    }

    /** ???????????? ????????? */
    private String encodeFileName(String filename) {
        try {
            return URLEncoder.encode(filename, "UTF-8").replaceAll("[+]", " ");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
