package com.hisco.admin.sms.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.sms.service.SmsTemplateService;
import com.hisco.admin.sms.vo.SmsTemplateVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

/**
 * SMS 자동 발송 템플릿 관리
 *
 * @author 진수진
 * @since 2021.12.01
 * @version 1.0, 2021.12.01
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.12.01 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}" })
public class SmsTemplateController {

    @Resource(name = "smsTemplateService")
    private SmsTemplateService smsTemplateService;

    @PageActionInfo(title = "자동 메시지 템플릿 목록 조회", action = "R")
    @GetMapping(value = "/sms/templateList")
    public String templateList(@ModelAttribute("smsTemplateVO") SmsTemplateVO smsTemplateVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        model.addAttribute("resultList", smsTemplateService.selectTemplateList(smsTemplateVO));
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 템플릿 등록을 위한 등록페이지로 이동한다.
     *
     * @param SmsTemplateVO
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/sms/templateDetailAjax")
    public String selecTemplateDetail(@ModelAttribute("smsTemplateVO") SmsTemplateVO smsTemplateVO,
            CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        smsTemplateVO.setMsgSendmethod("1001"); // 카카오 발송을 디폴트로 셋팅

        // 수정일경우 불러오기
        if (!commandMap.getString("MODE").equals("INSERT")) {
            smsTemplateVO = smsTemplateService.selectTemplateDetail(smsTemplateVO);
            smsTemplateVO.setOldMsgno(smsTemplateVO.getMsgno());
        }

        model.addAttribute("smsTemplateVO", smsTemplateVO);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 템플릿을 등록한다.
     *
     * @param SmsTemplateVO
     * @param commandMap
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PageActionInfo(title = "자동 메시지 템플릿 등록", action = "C", ajax = true)
    @PostMapping("/sms/templateInsert.json")
    @ResponseBody
    public ModelAndView insertTemplate(@ModelAttribute("smsTemplateVO") SmsTemplateVO smsTemplateVO,
            CommandMap commandMap,
            ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;
log.debug("잘되라~~~~~~~~~1");
        smsTemplateService.insertTemplate(smsTemplateVO);
        resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 템플릿을 수정한다.
     *
     * @param SmsTemplateVO
     * @param commandMap
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PageActionInfo(title = "자동 메시지 템플릿 수정", action = "U", ajax = true)
    @PostMapping("/sms/templateUpdate.json")
    @ResponseBody
    public ModelAndView updateTemplate(@ModelAttribute("smsTemplateVO") SmsTemplateVO smsTemplateVO,
            CommandMap commandMap,
            ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        smsTemplateService.updateTemplate(smsTemplateVO);
        resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 템플릿을 삭제한다.
     *
     * @param SmsTemplateVO
     * @param commandMap
     * @param model
     * @return ModelAndView
     * @throws Exception
     */
    @PageActionInfo(title = "자동 메시지 템플릿 삭제", action = "D", ajax = true)
    @PostMapping("/sms/templateDelete.json")
    @ResponseBody
    public ModelAndView deleteTemplate(@ModelAttribute("smsTemplateVO") SmsTemplateVO smsTemplateVO,
            CommandMap commandMap,
            ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        smsTemplateService.deleteTemplate(smsTemplateVO);
        resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }
}
