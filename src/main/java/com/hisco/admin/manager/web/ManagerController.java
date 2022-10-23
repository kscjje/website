package com.hisco.admin.manager.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.manager.service.SysUserService;
import com.hisco.admin.manager.vo.SysUserVO;
import com.hisco.admin.menu.util.SiteGubun;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.annotation.PageActionType;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sec.ram.service.AuthorManage;
import egovframework.com.sec.ram.service.AuthorManageVO;
import egovframework.com.sec.ram.service.AuthorRoleManage;
import egovframework.com.sec.ram.service.AuthorRoleManageVO;
import egovframework.com.sec.ram.service.EgovAuthorManageService;
import egovframework.com.sec.ram.service.EgovAuthorRoleManageService;
import egovframework.com.sec.rmt.service.EgovRoleManageService;
import egovframework.com.sec.rmt.service.RoleManage;
import egovframework.com.sec.rmt.service.RoleManageVO;
import egovframework.com.sym.mnu.mcm.service.EgovMenuCreateManageService;
import egovframework.com.sym.mnu.mcm.service.MenuCreatVO;
import egovframework.com.sym.mnu.mpm.service.EgovMenuManageService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import egovframework.rte.fdl.security.intercept.EgovReloadableFilterInvocationSecurityMetadataSource;
import egovframework.rte.fdl.security.securedobject.EgovSecuredObjectService;
//// import egovframework.rte.fdl.idgnr.EgovIdGnrService;
// import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 권한 관리
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
@RequestMapping(value = "#{dynamicConfig.adminRoot}/manager")
public class ManagerController {

    @Resource(name = "egovAuthorManageService")
    private EgovAuthorManageService egovAuthorManageService;

    @Resource(name = "egovRoleManageService")
    private EgovRoleManageService egovRoleManageService;

    @Resource(name = "EgovCmmUseService")
    EgovCmmUseService egovCmmUseService;

    @Resource(name = "egovAuthorRoleManageService")
    private EgovAuthorRoleManageService egovAuthorRoleManageService;

    /** EgovMenuManageService */
    @Resource(name = "meunCreateManageService")
    private EgovMenuCreateManageService menuCreateManageService;

    /** EgovMenuManageService */
    @Resource(name = "meunManageService")
    private EgovMenuManageService menuManageService;

    @Resource(name = "sysUserService")
    private SysUserService sysUserService;

    @Value("#{dynamicConfig.adminRoot}")
    private String ADMIN_ROOT;

    @Resource(name = "databaseSecurityMetadataSource")
    EgovReloadableFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;

    @Resource(name = "securedObjectService")
    private EgovSecuredObjectService securedObjectService;

    /**
     * 관리그룹 목록
     *
     * @param authorManageVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping({ "/groupList", "/mngGroupList" })
    @PageActionInfo(title = "관리그룹 목록 조회", action = PageActionType.READ)
    public String selectGrouplist(@ModelAttribute("authorManageVO") AuthorManageVO authorManageVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        String url = HttpUtility.getViewUrl(ADMIN_ROOT, request);

        if (url.indexOf("mngGroupList") > 0) {
            authorManageVO.setSearchCondition("3");
        }
        List<?> list = egovAuthorManageService.selectAuthorList(authorManageVO);
        model.addAttribute("list", list);
        model.addAttribute("authorManageVO", authorManageVO);
        model.addAttribute("commandMap", commandMap);

        return url;
    }

    /**
     * 관리그룹 상세정보
     *
     * @param authorManageVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping({ "/groupDetailAjax", "/groupAdmDetailAjax" })
    public String selectGroupDetail(@ModelAttribute("authorManageVO") AuthorManageVO authorManageVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        if (authorManageVO.getAuthorCode() != null && !authorManageVO.getAuthorCode().equals("")) {
            // 수정
            AuthorManageVO dataVO = egovAuthorManageService.selectAuthor(authorManageVO);
            model.addAttribute("authorManageVO", dataVO);
        } else {
            model.addAttribute("authorManageVO", authorManageVO);
        }

        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(ADMIN_ROOT, request);
    }

    /**
     * 관리그룹 상세정보
     *
     * @param authorManageVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/mngGroupDetailAjax.json")
    @ResponseBody
    public ModelAndView selectGroupDetailJson(@ModelAttribute("authorManageVO") AuthorManageVO authorManageVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        // 수정
        authorManageVO.setComcd(Config.COM_CD);
        AuthorManageVO dataVO = egovAuthorManageService.selectAuthor(authorManageVO);
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", dataVO);
        return mav;
    }

    /**
     * 관리그룹 등록
     *
     * @param roleManageVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping({ "/groupSave", "/groupAdmSave", "/mngGroupSave" })
    @PageActionInfo(title = "관리그룹 등록", action = PageActionType.CREATE, ajax = true)
    @ResponseBody
    public ModelAndView insertGroupSave(@ModelAttribute("authorManageVO") AuthorManageVO authorManageVO,
            CommandMap commandMap,
            BindingResult bindingResult, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        ResultInfo resultInfo = null;
        if (authorManageVO.getAuthorCode() == null || authorManageVO.getAuthorCode().equals("")) {
            // 그룹코드 임의생성
            // authorManageVO.setParentRole("ROLE_ADMIN");
            authorManageVO.setAuthorCode(sysUserService.selectGroupNextCd());
        }

        int cnt = egovAuthorManageService.selectAuthorCodeCheck(authorManageVO);
        if (cnt > 0) {
            resultInfo = HttpUtility.getErrorResultInfo("그룹코드가 중복 되었습니다.");
        } else {
            egovAuthorManageService.insertAuthor(authorManageVO);
            databaseSecurityMetadataSource.reload();
            resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 관리그룹 수정
     *
     * @param roleManageVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping({ "/groupUpdate", "/groupAdmUpdate", "/mngGroupUpdate" })
    @PageActionInfo(title = "관리그룹 수정", action = PageActionType.UPDATE, ajax = true)
    @ResponseBody
    public ModelAndView updateGroupSave(@ModelAttribute("authorManageVO") AuthorManageVO authorManageVO,
            CommandMap commandMap,
            BindingResult bindingResult, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        ResultInfo resultInfo = null;

        // 수정
        egovAuthorManageService.updateAuthor(authorManageVO);
        resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 관리그룹 삭제
     *
     * @param authorManage
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping({ "/mngGroupDelete" })
    @PageActionInfo(title = "관리그룹 삭제", action = PageActionType.DELETE, ajax = true)
    @ResponseBody
    public ModelAndView deleteGroupSave(@ModelAttribute("authorManage") AuthorManage authorManage,
            CommandMap commandMap,
            BindingResult bindingResult, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        ResultInfo resultInfo = null;

        SysUserVO searchVO = new SysUserVO();
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setComcd(Config.COM_CD);
        searchVO.setSearchRole(authorManage.getAuthorCode());

        List<?> list = sysUserService.selectSysUserList(searchVO);

        if (list != null && list.size() > 0) {
            resultInfo = HttpUtility.getErrorResultInfo("해당 권한을 가진 관리자계정이 있어 삭제할 수 없습니다. 해당 권한의 관리자 계정을 수정하신 후 다시 시도해 주세요.");
        } else {
            // 삭제
            egovAuthorManageService.deleteAuthor(authorManage);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 관리업무 목록 불러오기
     *
     * @param roleManageVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/authList")
    @PageActionInfo(title = "관리업무 목록 조회", action = PageActionType.READ)
    public String selectAuthList(@ModelAttribute("roleManageVO") RoleManageVO roleManageVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        roleManageVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        roleManageVO.setLastIndex(paginationInfo.getLastRecordIndex());
        roleManageVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        int totCnt = egovRoleManageService.selectRoleListTotCnt(roleManageVO);
        paginationInfo.setTotalRecordCount(totCnt);

        List<?> list = egovRoleManageService.selectRoleList(roleManageVO);

        model.addAttribute("list", list);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("roleManageVO", roleManageVO);
        model.addAttribute("cmmCodeDetailList", selectCmmCodeDetailList(new ComDefaultCodeVO(), "COM029"));
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(ADMIN_ROOT, request);
    }

    /**
     * 관리업무 상세 불러오기
     *
     * @param roleManage
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/authDetailAjax")
    public String selectAuthDetail(@ModelAttribute("roleManage") RoleManage roleManage, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        if (roleManage.getRoleCode() != null && !roleManage.getRoleCode().equals("")) {
            RoleManageVO roleManageVo = new RoleManageVO();
            roleManageVo.setRoleCode(roleManage.getRoleCode());
            roleManageVo = egovRoleManageService.selectRole(roleManageVo);
            model.addAttribute("roleManage", roleManageVo);
        } else {
            model.addAttribute("roleManage", roleManage);
        }

        model.addAttribute("cmmCodeDetailList", selectCmmCodeDetailList(new ComDefaultCodeVO(), "COM029"));

        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(ADMIN_ROOT, request);
    }

    /**
     * 관리업무 등록
     *
     * @param roleManage
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/authSave.json")
    @PageActionInfo(title = "관리업무 등록", action = PageActionType.CREATE, ajax = true)
    @ResponseBody
    public ModelAndView insertAuthSave(@ModelAttribute("roleManage") RoleManage roleManage, CommandMap commandMap,
            BindingResult bindingResult, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        ResultInfo resultInfo = null;

        String roleTyp = roleManage.getRoleTyp();
        if ("method".equals(roleTyp))// KISA 보안약점 조치 (2018-10-29, 윤창원)
            roleTyp = "mtd";
        else if ("pointcut".equals(roleTyp))// KISA 보안약점 조치 (2018-10-29, 윤창원)
            roleTyp = "pct";
        else
            roleTyp = "web";

        egovRoleManageService.insertRole(roleManage);
        resultInfo = HttpUtility.getSuccessResultInfo("등록 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 관리업무 수정
     *
     * @param roleManage
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/authUpdate.json")
    @PageActionInfo(title = "관리업무 수정", action = PageActionType.UPDATE, ajax = true)
    @ResponseBody
    public ModelAndView updateAuthSave(@ModelAttribute("roleManage") RoleManage roleManage, CommandMap commandMap,
            BindingResult bindingResult, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        ResultInfo resultInfo = null;

        egovRoleManageService.updateRole(roleManage);
        resultInfo = HttpUtility.getSuccessResultInfo("수정 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 관리업무 삭제
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/authDelete")
    @PageActionInfo(title = "관리업무 삭제", action = PageActionType.DELETE, ajax = true)
    @ResponseBody
    public ModelAndView deleteAuth(HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        ResultInfo resultInfo = null;

        RoleManage roleManage = new RoleManage();
        roleManage.setRoleCodeCheck(request.getParameterValues("checkId"));

        egovRoleManageService.deleteRole(roleManage);
        resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 그룹별 권한 조회
     *
     * @param authorRoleManageVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/checkGroupAuthList")
    @PageActionInfo(title = "그룹별 권한 조회", action = PageActionType.READ)
    public String checkAuthList(@ModelAttribute("authorRoleManageVO") AuthorRoleManageVO authorRoleManageVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        model.addAttribute("authorRoleList", egovAuthorRoleManageService.selectAuthorRoleList(authorRoleManageVO));
        model.addAttribute("searchVO", authorRoleManageVO);

        // 관리권한 목록
        AuthorManageVO authorVo = new AuthorManageVO();
        // authorVo.setSearchCondition("2"); // 권한 부모 코드가 ROLE_ADMIN 인것만...
        List<?> authorList = egovAuthorManageService.selectAuthorList(authorVo);
        model.addAttribute("authorList", authorList);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(ADMIN_ROOT, request);
    }

    /**
     * 그룹별 권한 등록
     *
     * @param authorRoleManage
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/checkGroupAuthSave.json")
    @PageActionInfo(title = "그룹별 권한 등록", action = PageActionType.CREATE, ajax = true)
    @ResponseBody
    public ModelAndView insertCheckGroupAuth(@ModelAttribute("authorRoleManage") AuthorRoleManage authorRoleManage,
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");

        ResultInfo resultInfo = null;
        String[] strRoleCodes = request.getParameterValues("checkId");

        egovAuthorRoleManageService.deleteAuthorRoleByAuthorCode(authorRoleManage);// 2011.09.07

        if (strRoleCodes != null) {
            for (int i = 0; i < strRoleCodes.length; i++) {
                authorRoleManage.setRoleCode(strRoleCodes[i]);
                egovAuthorRoleManageService.insertAuthorRole(authorRoleManage);
            }
        }

        resultInfo = HttpUtility.getSuccessResultInfo("저장 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 메뉴 목록
     *
     * @param menuCreatVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/checkGroupMenuList")
    public String selectCheckMenuList(@ModelAttribute("menuCreatVO") MenuCreatVO menuCreatVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        menuCreatVO.setSiteGubun(SiteGubun.ADMIN); // 관리자 메뉴
        List<?> menuList = menuCreateManageService.selectMenuCreatList(menuCreatVO);
        model.addAttribute("menuList", menuList);
        model.addAttribute("resultVO", menuCreatVO);

        // 모든 메뉴 권한자 인지 체크
        model.addAttribute("allCnt", menuCreateManageService.selectMenuAllCnt(menuCreatVO));

        // 관리그룹 목록
        AuthorManageVO authorVo = new AuthorManageVO();
        authorVo.setSearchCondition("3"); // 권한 부모 코드가 ROLE_ADMIN 인것만...
        List<?> authorList = egovAuthorManageService.selectAuthorList(authorVo);
        model.addAttribute("authorList", authorList);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(ADMIN_ROOT, request);
    }

    /**
     * 권한별 메뉴 저장
     *
     * @param menuCreatVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/checkGroupMenuSave")
    @PageActionInfo(title = "그룹별 메뉴 등록", action = PageActionType.CREATE, ajax = true)
    @ResponseBody
    public ModelAndView insertChkGroupMenu(@ModelAttribute("menuCreatVO") MenuCreatVO menuCreatVO,
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        String authorCode = request.getParameter("groupId");
        String[] strMenuIds = request.getParameterValues("checkId");

        menuCreatVO.setCreatPersonId(user.getId());
        menuCreatVO.setAuthorCode(authorCode);

        menuCreateManageService.insertMenuCreatList(menuCreatVO, strMenuIds, request.getParameterValues("checkInsertId"), request.getParameterValues("checkUpdateId"), request.getParameterValues("checkDeleteId"));
        resultInfo = HttpUtility.getSuccessResultInfo("저장 되었습니다.");

        // 관리자 메뉴 세션 저장
        MenuManageVO vo = new MenuManageVO();
        vo.setSiteGubun(SiteGubun.ADMIN);
        vo.setTmpId(user.getUniqId());
        vo.setGroupId(user.getGroupId());

        try {
            HttpSession session = request.getSession();
            UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
            if (userSession == null) {
                userSession = new UserSession();
            }
            List<MenuManageVO> menuList = (List<MenuManageVO>) menuManageService.selectMenuListSite(vo);
            userSession.setAdminMenuList(menuList);

        } catch (ClassCastException e) {
            log.error("List<MenuManageVO> cast exception");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 권한별 모든 메뉴의 권한 저장
     *
     * @param menuCreatVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/checkGroupMenuSave2")
    @PageActionInfo(title = "모든 메뉴의 권한 등록", action = PageActionType.CREATE, ajax = true)
    @ResponseBody
    public ModelAndView insertChkGroupMenuAll(@ModelAttribute("menuCreatVO") MenuCreatVO menuCreatVO,
            HttpServletRequest request, CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        String[] strMenuIds = new String[] { "1" };

        menuCreatVO.setCreatPersonId(user.getId());
        menuCreateManageService.insertMenuCreatList(menuCreatVO, strMenuIds);
        resultInfo = HttpUtility.getSuccessResultInfo("저장 되었습니다.");

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 공통코드 호출
     *
     * @param comDefaultCodeVO
     *            ComDefaultCodeVO
     * @param codeId
     *            String
     * @return List
     * @exception Exception
     */
    public List<?> selectCmmCodeDetailList(ComDefaultCodeVO comDefaultCodeVO, String codeId) throws Exception {
        comDefaultCodeVO.setCodeId(codeId);
        return egovCmmUseService.selectCmmCodeDetail(comDefaultCodeVO);
    }
}
