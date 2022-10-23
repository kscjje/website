package com.hisco.admin.area.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hisco.admin.area.service.AreaCdService;
import com.hisco.admin.area.vo.AreaCdVO;
import com.hisco.admin.log.service.LogService;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.HttpUtility;

/**
 * 지역 관리
 *
 * @author 진수진
 * @since 2021.10.21
 * @version 1.0, 2021.10.21
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.10.21 최초작성
 */
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class AreaController {

    @Autowired
    private DefaultBeanValidator beanValidator;

    @Resource(name = "areaCdService")
    private AreaCdService areaCdService;

    /** logService */
    @Resource(name = "logService")
    private LogService logService;

    @Resource(name = "codeService")
    private CodeService codeService;

    /**
     * 지역 목록을 조회한다.
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "지역 목록 조회", action = "R")
    @GetMapping(value = "/area/areaList")
    public String selectAreaList(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        model.addAttribute("resultList", areaCdService.selectAreaCdList(null));
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 지역 등록을 위한 등록페이지로 이동한다.
     *
     * @param CodeVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/area/areaDetailAjax")
    public String selectAreaDetail(@ModelAttribute("areaCdVO") AreaCdVO areaCdVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        // 수정일경우 불러오기
        if (!commandMap.getString("MODE").equals("INSERT")) {
            areaCdVO = areaCdService.selectAreaCdDetail(areaCdVO);
        }

        // 1차 지역 목록
        // model.addAttribute("parentAreaList", areaCdService.selectAreaCdSubList(0));
        model.addAttribute("areaCdVO", areaCdVO);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 지역을 등록한다.
     *
     * @param areaCdVO
     * @param commandMap
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PostMapping("/area/areaSave.json")
    @ResponseBody
    public ModelAndView insertAreaCd(@ModelAttribute("areaCdVO") AreaCdVO areaCdVO, CommandMap commandMap,
            ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        if (commandMap.getString("MODE").equals("INSERT")) {
            if (logService.checkAdminLog(commandMap, "C", "지역 등록")) {
                areaCdService.insertAreaCd(areaCdVO);
                resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");
            } else {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            }

        } else {
            // 수정
            if (!logService.checkAdminLog(commandMap, "U", "지역 수정")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else {
                areaCdService.updateAreaCd(areaCdVO);
                resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");
            }
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 지역을 삭제 한다.
     *
     * @param areaCdVO
     * @param commandMap
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PostMapping("/area/areaDelete.json")
    @ResponseBody
    public ModelAndView deleteAreaCd(@ModelAttribute("areaCdVO") AreaCdVO areaCdVO, CommandMap commandMap,
            ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        // 삭제
        if (!logService.checkAdminLog(commandMap, "D", "지역 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            areaCdService.deleteAreaCd(areaCdVO);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }
}
