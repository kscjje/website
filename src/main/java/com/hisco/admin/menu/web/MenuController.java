package com.hisco.admin.menu.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.log.service.LogService;
import com.hisco.admin.menu.service.MenuSortVO;
import com.hisco.admin.menu.util.SiteGubun;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sec.ram.service.AuthorRoleManage;
import egovframework.com.sec.ram.service.AuthorRoleManageVO;
import egovframework.com.sec.ram.service.EgovAuthorRoleManageService;
import egovframework.com.sym.mnu.mpm.service.EgovMenuManageService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 메뉴 관리
 *
 * @author 진수진
 * @since 2020.07.01
 * @version 1.0, 2020.07.01
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.01 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}" })
public class MenuController {

    /** EgovMenuManageService */
    @Resource(name = "meunManageService")
    private EgovMenuManageService menuManageService;

    @Resource(name = "egovAuthorRoleManageService")
    private EgovAuthorRoleManageService egovAuthorRoleManageService;

    @Resource(name = "codeService")
    private CodeService codeService;

    /** log */
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
    @PageActionInfo(title = "메뉴 목록 조회", action = "R")
    @GetMapping("/menu/menuList")
    public String selectMenuList(@ModelAttribute("searchVO") ComDefaultVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("commandMap", commandMap);

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
    @PageActionInfo(title = "메뉴 목록 조회", action = "R")
    @GetMapping("/menu/menuLoad.json")
    @ResponseBody
    public ModelAndView selectMenuLoad(@ModelAttribute("searchVO") ComDefaultVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        List<?> menuList = menuManageService.selectAllList(searchVO);

        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("menuList", menuList);
        return mav;
    }

    /**
     * 메뉴 상세 화면
     *
     * @param menuManageVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "메뉴 상세 조회", action = "R")
    @GetMapping("/menu/menuDetailAjax")
    public String selectMenuDetail(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model, java.util.Map<String, Object> paramMap) throws Exception {

        ComDefaultVO searchVO = new ComDefaultVO();

        // 1차 메뉴만 선택
        searchVO.setSearchCondition("1");
        searchVO.setSearchKeyword(menuManageVO.getSiteGubun());

        List<?> cotGrpCdList = codeService.selectCodeList("SM_MENU_KIND");
        model.addAttribute("cotMyGrpCdList", cotGrpCdList);

        List<?> listMenumanage = menuManageService.selectMenuManageList(searchVO);
        model.addAttribute("upperMenuList", listMenumanage);

        if (menuManageVO.getMenuNo() > 0) {
            // 신규등록
            searchVO.setSearchKeyword(Long.toString(menuManageVO.getMenuNo()));
            MenuManageVO menuVO = menuManageService.selectMenuManage(searchVO);
            model.addAttribute("menuManageVO", menuVO);

        } else {
            model.addAttribute("menuManageVO", menuManageVO);
        }

        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 메뉴 등록
     *
     * @param menuManageVO
     * @param commandMap
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "메뉴 입력", action = "C", ajax = true)
    @PostMapping("/menu/menuSave.json")
    @ResponseBody
    public ModelAndView insertMenu(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, CommandMap commandMap,
            HttpServletRequest request,
            BindingResult bindingResult, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        menuManageVO.setCreatPersonId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

        menuManageService.insertMenuManage(menuManageVO);
        resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");

        // 메뉴 세션 업데이트
        setMenuSessionUpdate(menuManageVO, user, request.getSession());

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 메뉴 수정
     *
     * @param menuManageVO
     * @param commandMap
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "메뉴 수정", action = "U", ajax = true)
    @PostMapping("/menu/menuUpdate.json")
    @ResponseBody
    public ModelAndView updateMenu(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, CommandMap commandMap,
            HttpServletRequest request,
            BindingResult bindingResult, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        menuManageVO.setCreatPersonId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

        menuManageService.updateMenuManage(menuManageVO);
        resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");

        // 메뉴 세션 업데이트
        setMenuSessionUpdate(menuManageVO, user, request.getSession());

        mav.addObject("result", resultInfo);
        return mav;
    }

    public void setMenuSessionUpdate(MenuManageVO menuManageVO, LoginVO user, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
        if (userSession == null) {
            userSession = new UserSession();
        }

        if (menuManageVO.getSiteGubun().equals(SiteGubun.ADMIN)) {
            // 관리자 메뉴 세션 저장
            MenuManageVO vo = new MenuManageVO();
            vo.setSiteGubun(SiteGubun.ADMIN);
            vo.setTmpId(user.getUniqId());
            vo.setGroupId(user.getGroupId());
            try {
                List<MenuManageVO> menuList = (List<MenuManageVO>) menuManageService.selectMenuListSite(vo);
                userSession.setAdminMenuList(menuList);

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            // 사용자 메뉴 세션 저장
            MenuManageVO vo = new MenuManageVO();
            vo.setSiteGubun(SiteGubun.USER);
            try {
                List<MenuManageVO> menuList = (List<MenuManageVO>) menuManageService.selectMenuListSite(vo);
                userSession.setUserMenuList(menuList);

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 메뉴 삭제
     *
     * @param menuManageVO
     * @param commandMap
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "메뉴 삭제", action = "D")
    @PostMapping("/menu/menuDelete")
    @ResponseBody
    public ModelAndView deleteMenu(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        menuManageVO.setCreatPersonId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

        if (!logService.checkAdminLog(commandMap, "D", "메뉴 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            menuManageService.deleteMenuManage(menuManageVO);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        // 메뉴 세션 업데이트
        setMenuSessionUpdate(menuManageVO, user, request.getSession());

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 메뉴와 관리롤 연결 설정 팝업
     *
     * @param menuManageVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/menu/menuAuthLinkPop")
    public String selectMenuAuthLinkList(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
            HttpServletRequest request, ModelMap model) throws Exception {
        // 권한 목록 가져오기
        AuthorRoleManageVO authorRoleManageVO = new AuthorRoleManageVO();
        authorRoleManageVO.setRoleMenuNo(menuManageVO.getMenuNo());

        model.addAttribute("authorRoleList", egovAuthorRoleManageService.selectAuthorRoleList(authorRoleManageVO));

        model.addAttribute("menuManageVO", menuManageVO);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 메뉴-권한 연결 삭제
     *
     * @param authorRoleManage
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/menu/menuAuthLinkSave")
    @ResponseBody
    public ModelAndView insertMenuAuthLink(@ModelAttribute("authorRoleManage") AuthorRoleManage authorRoleManage,
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        if (!logService.checkAdminLog(commandMap, "U", "메뉴-권한 연결 수정")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            authorRoleManage.setAuthorCodeList(request.getParameterValues("checkId"));
            egovAuthorRoleManageService.updateAuthorRoleMenu(authorRoleManage);
        }
        resultInfo = HttpUtility.getSuccessResultInfo("저장 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 메뉴 순서 변경
     *
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "메뉴 순서 변경", action = "U", ajax = true)
    @PostMapping("/menu/menuSortingSave.json")
    @ResponseBody
    public ModelAndView menuSortingSave(@ModelAttribute("menuSortVO") MenuSortVO menuSortVO, HttpServletRequest request,
            CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        log.debug("align_start:" + commandMap.getString("alignStart"));
        log.debug("align_end:" + commandMap.getString("alignEnd"));
        log.debug("align_new:" + commandMap.getString("alignNew"));

        menuManageService.updateMenuManageSorting(menuSortVO);
        resultInfo = HttpUtility.getSuccessResultInfo("저장 되었습니다.");

        MenuManageVO menuManageVO = new MenuManageVO();
        menuManageVO.setSiteGubun(menuSortVO.getSiteGubun());

        // 메뉴 세션 업데이트
        setMenuSessionUpdate(menuManageVO, user, request.getSession());

        mav.addObject("result", resultInfo);
        return mav;
    }
}
