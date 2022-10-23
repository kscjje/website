package com.hisco.admin.orginfo.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.admin.orginfo.service.ComInfoService;
import com.hisco.admin.orginfo.vo.ComInfoVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : ComInfoController.java
 * @Description : 운영사업자 관리
 * @author user
 * @since 2021. 10. 26.
 * @version 1.0
 * @see
 *      Copyright(c) 2021 HISCO. All rights reserved
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}", "#{dynamicConfig.managerRoot}" })
public class ComInfoController {

    @Resource(name = "comInfoService")
    private ComInfoService comInfoService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @PageActionInfo(title = "운영사업자 조회", action = "R")
    @GetMapping(value = "/orginfo/comInfo")
    public String selectComInfo(@ModelAttribute("comInfoVO") ComInfoVO comInfoVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model)
            throws Exception {

        comInfoVO.setComcd(Config.COM_CD); // 디폴트 설정된 comcd 에 해당되는 정보를 select
        ComInfoVO defaultComInfo = comInfoService.selectComInfoDetail(comInfoVO);

        if (defaultComInfo != null) {
            comInfoVO = defaultComInfo;
        }
        // SMS 연동업체
        model.addAttribute("smsProviderList", codeService.selectCodeList("SM_SMS_PROVIDER"));

     // payment 연동업체
        model.addAttribute("paymentList", codeService.selectCodeList("SM_PAYMENT_CORP"));


        // 온라인회원패스워드변경주기
        model.addAttribute("mbPwdCycleList", codeService.selectCodeList("SM_ONLINE_MBPWD_CYCLE"));

        model.addAttribute("comInfoVO", comInfoVO);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    @PageActionInfo(title = "운영사업자 수정", action = "U")
    @PostMapping(value = "/orginfo/comInfoSave")
    public void insertComInfo(@ModelAttribute("comInfoVO") ComInfoVO comInfoVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws Exception {
System.out.println("1312312313123132----------");
        ComInfoVO defaultComInfo = comInfoService.selectComInfoDetail(comInfoVO);
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        comInfoVO.setReguser(user.getId());
        comInfoVO.setModuser(user.getId());

        // 등록된 정보가 있으면 update
        if (defaultComInfo != null) {
            comInfoService.updateComInfo(comInfoVO);
        } else {
            comInfoService.insertComInfo(comInfoVO);
        }

        HttpUtility.sendRedirect(request, response, "저장 되었습니다.", Config.ADMIN_ROOT + "/orginfo/comInfo");
    }

}
