package com.hisco.user.intro.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cop.bbs.service.EgovArticleService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
// @RequestMapping(value = Config.USER_ROOT)
@RequestMapping(value = "")
public class IntroController {

    @Resource(name = "EgovArticleService")
    private EgovArticleService egovArticleService;

    /*
     * 인트로 진입 >> 도메인/Context/index.do 로 진입하면 로그가 쌓이지 않아 (user_root, admin_root 둘다 아니므로) 도메인/Context/web/intro 로 강제로 이동
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/index.do")
    public boolean main(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.debug("call main > go intro");

        HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/intro");

        return false;
    }

    /*
     * 인트로 화면
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = Config.USER_ROOT + "/intro")
    public String intro(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        log.debug("call index");

        LoginVO loginVO = commandMap.getUserInfo();

        Map<String, Object> paramMap = new HashMap<String, Object>();

        model.addAttribute("loginVO", loginVO);
        model.addAttribute("currentUrl", "main");
        model.addAttribute("comCd", Config.COM_CD);
        request.setAttribute("resourceContext", request.getContextPath() + Config.USER_ROOT + "/resources");

        log.debug("main model = " + model);

        // return HttpUtility.getViewUrl(request);

        return "/web/intro/intro";
    }
}
