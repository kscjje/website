package com.hisco.user.guide.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.LoginVO;

@Controller
@RequestMapping(value = Config.USER_ROOT + "/guide")
public class GuideController {

    //// @Resource(name = "mainService")
    //// private MainService mainService;

    //// @Resource(name = "EgovArticleService")
    //// private EgovArticleService egovArticleService;

    /**
     * 사이트맵
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/sitemap")
    public String selectSitemap(CommandMap commandMap, HttpSession session, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) throws Exception {

        LoginVO vo = commandMap.getUserInfo();
        UserSession uSession = (UserSession) session.getAttribute(Config.USER_SESSION);

        model.addAttribute("vo", vo);
        model.addAttribute("mList", uSession.getUserMenuList());
        return HttpUtility.getViewUrl(request);
    }

    /**
     * 개인정보 처리방침
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/pInfo")
    public String personalInfo(CommandMap commandMap, HttpSession session, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) throws Exception {

        String number = commandMap.getString("num") != null ? commandMap.getString("num") : "";
        String url = "/guide/policy/pInfo" + number;
        return HttpUtility.getViewUrl(Config.USER_ROOT, url);
    }

    /**
     * 개인정보 처리방침
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/policy")
    public String policyInfo(CommandMap commandMap, HttpSession session, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) throws Exception {

        String number = commandMap.getString("num") != null ? commandMap.getString("num") : "";
        String url = "/guide/policy/policy" + number;
        return HttpUtility.getViewUrl(Config.USER_ROOT, url);
    }

    /**
     * 저작권 공공누리
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/copyright")
    public String copyright(CommandMap commandMap, HttpSession session, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) throws Exception {

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 대국민 홍보물
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/promo")
    public String promotion(CommandMap commandMap, HttpSession session, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) throws Exception {

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 이용약관
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/useterms")
    public String useterms(CommandMap commandMap, HttpSession session, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) throws Exception {

        return HttpUtility.getViewUrl(request);
    }

}
