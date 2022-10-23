package com.hisco.admin.edcrsvn.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.eduadm.service.EduAdmService;
import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.annotation.PageActionType;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.user.edcatnlc.service.EdcProgramService;
import com.hisco.user.edcatnlc.service.EdcRsvnInfoService;
import com.hisco.user.edcatnlc.vo.EdcProgramVO;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 수강접수현황
 *
 * @author
 * @since
 * @version
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}/edcrsvn", "#{dynamicConfig.managerRoot}/edcrsvn" })
public class EdcProgramReceptionController {

    private String adminRoot = Config.ADMIN_ROOT;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "logService")
    private LogService logService;

    @Resource(name = "edcProgramService")
    private EdcProgramService edcProgramService;

    @Resource(name = "eduAdmService")
    private EduAdmService eduAdmService;

    @Resource(name = "edcRsvnInfoService")
    private EdcRsvnInfoService edcRsvnInfoService;

    /**
     * 교육 프로그램 관리 목록
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "수강접수현황", action = PageActionType.READ)
    @GetMapping("/edcProgramReceptionList")
    public String edcProgramReceptionList(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        searchVO.setRecordCountPerPage(10);

        // 접수방식
        model.addAttribute("receptType", codeService.selectCodeList("SM_LERECPTYPE"));
        model.addAttribute("programList", Collections.EMPTY_LIST);
        model.addAttribute("commandMap", commandMap);

        if (StringUtils.isNotBlank(searchVO.getSearchOrgNo())) {
            model.addAttribute("rsvnsetNmList", edcProgramService.selectRsvnSetNmList(Integer.parseInt(searchVO.getSearchOrgNo())));
        }

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping("/edcProgramList.json")
    public String selectEdcaReceptionList(@ModelAttribute("searchVO") com.hisco.admin.eduadm.vo.EdcProgramVO searchVO,
            HttpServletRequest request, ModelMap model, CommandMap commandMap) throws Exception {

        searchVO.setRecordCountPerPage(1000);
//        List<?> programList = eduAdmService.selectEdcProgramList(searchVO);
//
//        model.addAttribute("programList", programList);

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping("/edcRsvnsetNmList.json")
    public String selectEdcRsvnsetNmList(HttpServletRequest request, ModelMap model, CommandMap commandMap)
            throws Exception {

        String orgNo = (String) commandMap.get("orgNo");

        if (StringUtils.isNotBlank(orgNo)) {
            model.addAttribute("rsvnsetNmList", edcProgramService.selectRsvnSetNmList(Integer.parseInt(orgNo)));
        }

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "수강신청현황목록", action = PageActionType.READ, ajax = true)
    @RequestMapping(value = "/edcProgramReceptionListAjax")
    public String edcProgramReceptionListAjax(@ModelAttribute("searchVO") EdcProgramVO searchVO,
            HttpServletRequest request, ModelMap model,
            @RequestParam Map<String, Object> paramMap, CommandMap commandMap) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        paginationInfo.setRecordCountPerPage(searchVO.getRecordCountPerPage());
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setChannel(Constant.CM_CHANNEL_BO);
        searchVO.setSearchTab("CATE");

        if ("programNm".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_PROGRAM_NM_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_PROGRAM_NM_ASC");
            }
        } else if ("apply".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_APPLY_CLOSE_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_APPLY_CLOSE");
            }
        } else if ("rsvnType".equals(searchVO.getSearchOrder())) {
            if ("desc".equals(searchVO.getSearchOrderDir())) {
                searchVO.setSearchOrderBy("BY_RSVN_TYPE_DESC");
            } else {
                searchVO.setSearchOrderBy("BY_RSVN_TYPE_ASC");
            }
        }

        if (Config.YES.equalsIgnoreCase((String) paramMap.get("exceldown"))) {
            searchVO.setUsePagingYn(Config.NO);
        }

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
        model.addAttribute("exceldown", (String) paramMap.get("exceldown"));

        return HttpUtility.getViewUrl(request);
    }

    @GetMapping("/edcRsvnsetInfoList.json")
    public String edcRsvnsetInfoList(HttpServletRequest request, CommandMap commandMap, ModelMap model)
            throws Exception {

        com.hisco.admin.eduadm.vo.EdcProgramVO edcProgramVO = new com.hisco.admin.eduadm.vo.EdcProgramVO();
        edcProgramVO.setEdcPrgmNo(Integer.parseInt(request.getParameter("edcPrgmNo")));

        model.clear();
        model.addAttribute("rsvnsetInfoList", eduAdmService.selectProgramRsvnSetList(edcProgramVO));

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "프로그램 강제종료처리", action = PageActionType.UPDATE, ajax = true)
    @PostMapping("/edcProgramForceClose.json")
    @ResponseBody
    public ModelAndView updateEdcProgramForceClose(
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        com.hisco.admin.eduadm.vo.EdcProgramVO edcProgramVO = new com.hisco.admin.eduadm.vo.EdcProgramVO();
        edcProgramVO.setEdcPrgmNo(Integer.parseInt(commandMap.getString("edcPrgmNo")));
        edcProgramVO.setEdcRsvnsetSeq(commandMap.getString("edcRsvnsetSeq"));
        edcProgramVO.setRsvnForceCloseyn(Config.YES);
        edcProgramVO.setUserIp(commandMap.getIp());

        ResultInfo resultInfo = HttpUtility.getErrorResultInfo("강제마감 처리에 실패하였습니다.");

        if (eduAdmService.updateEdcProgramForceClose(edcProgramVO) == 1)
            resultInfo = HttpUtility.getSuccessResultInfo("성공적으로 강제마감이 완료되었습니다.");

        mav.addObject("result", resultInfo);

        return mav;
    }
}
