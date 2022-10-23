package com.hisco.admin.manager.web;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.hisco.admin.log.service.LogService;
import com.hisco.admin.manager.service.SysUserService;
import com.hisco.admin.manager.vo.SysUserIpVO;
import com.hisco.admin.manager.vo.SysUserVO;
import com.hisco.admin.orginfo.service.OrgInfoService;
import com.hisco.admin.orginfo.vo.OrgInfoVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.annotation.PageActionType;
import com.hisco.cmm.modules.StringUtil;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sec.ram.service.AuthorManageVO;
import egovframework.com.sec.ram.service.EgovAuthorManageService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 관리자 계정 관리
 *
 * @author 진수진
 * @since 2020.07.14
 * @version 1.0, 2020.07.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.14 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = "#{dynamicConfig.adminRoot}")
public class SysUserController {

    /** EgovPropertyService */
    /*
     * @Resource(name = "propertiesService")
     * protected EgovPropertyService propertiesService;
     */

    @Resource(name = "sysUserService")
    private SysUserService sysUserService;

    @Resource(name = "egovAuthorManageService")
    private EgovAuthorManageService egovAuthorManageService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    /** log */
    @Resource(name = "logService")
    private LogService logService;

    @Resource(name = "orgInfoService")
    private OrgInfoService orgInfoService;

    /**
     * 관리자 목록
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "관리자 목록", action = PageActionType.READ)
    @GetMapping("/manager/sysUserList")
    public String selectSysUserlist(@ModelAttribute("searchVO") SysUserVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        // 로그인한 관리자의 세션에 저장된 기관 목록만 셀렉트 한다
        UserSession userSession = (UserSession) request.getSession().getAttribute(Config.USER_SESSION);
        // 기관유형
        model.addAttribute("typeList", codeService.selectCodeList("SM_ORG_LTYPE"));

        // 기관 목록
        OrgInfoVO orgInfoVO = new OrgInfoVO();
        orgInfoVO.setComcd(Config.COM_CD);
        orgInfoVO.setMyOrgList(userSession.getMyOrgList());
        orgInfoVO.setSearchStat(searchVO.getOrgLtype());
        orgInfoVO.setSearchUseYn(searchVO.getOrgMtype());
        List<OrgInfoVO> orgList = (List<OrgInfoVO>) orgInfoService.selectOrgInfoList(orgInfoVO);

        model.addAttribute("orgList", orgList);

        AuthorManageVO authorVo = new AuthorManageVO();
        authorVo.setSearchCondition("3"); // 권한 부모 코드가 ROLE_ADMIN 인것만...
        List<?> authorList = egovAuthorManageService.selectAuthorList(authorVo);
        model.addAttribute("authorList", authorList);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 관리자 목록
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/manager/sysUserListAjax")
    public String selectSysUserlistAjax(@ModelAttribute("searchVO") SysUserVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        // 로그인한 관리자의 세션에 저장된 기관 목록만 셀렉트 한다
        UserSession userSession = (UserSession) request.getSession().getAttribute(Config.USER_SESSION);

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setComcd(Config.COM_CD);
        
        LoginVO adminInfo = userSession.getAdminInfo();
        if(adminInfo.getAuthorCode().contentEquals("ROLE_SUPER")) {
        	searchVO.setMyOrgList(null);
        }
        else {
        	searchVO.setMyOrgList(userSession.getMyOrgList());
        }

        List<?> list = sysUserService.selectSysUserList(searchVO);

        if (list != null && list.size() > 0) {
            int totCnt = ((SysUserVO) list.get(0)).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        model.addAttribute("list", list);
        model.addAttribute("sysUser", searchVO);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 관리자 등록화면
     *
     * @param sysUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/manager/sysUserRegistAjax")
    public String selectUserRegist(@ModelAttribute("sysUserVO") SysUserVO sysUserVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        // 관리권한 목록
        AuthorManageVO authorVo = new AuthorManageVO();
        authorVo.setSearchCondition("3"); // 권한 부모 코드가 ROLE_ADMIN 인것만...
        List<?> authorList = egovAuthorManageService.selectAuthorList(authorVo);

        sysUserVO.setUseYn("Y");
        sysUserVO.setAcntStats("1000");
        sysUserVO.setStats("Y");

        model.addAttribute("authorList", authorList);

        if (!StringUtil.IsEmpty(sysUserVO.getUserId())) {
            // 수정 화면
            SysUserVO userVO = sysUserService.selectSysUserDetail(sysUserVO);
            if (userVO.getUseYn() == null) {
                userVO.setUseYn("Y");
            }
            if (userVO.getAcntStats() == null) {
                userVO.setAcntStats("1000");
            }

            if (userVO.getUseYn().equals("N")) {
                userVO.setAcntStats("N");
            }

            sysUserVO = userVO;

            // 관리 기관 목록
            model.addAttribute("orgList", sysUserService.selectMstOrgList(userVO));

            // 접속 제한 IP 목록
            model.addAttribute("ipList", sysUserService.selectSysUserIpList(userVO));

            model.addAttribute("mode", "edit");
        } else {
            model.addAttribute("mode", "add");
        }

        model.addAttribute("sysUserVO", sysUserVO);
        model.addAttribute("commandMap", commandMap);

        // 관리자유형
        model.addAttribute("userType", codeService.selectCodeList("SM_ADMINUSER_TYPE"));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 관리자 수정화면
     *
     * @param sysUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/manager/sysUserUpdt")
    public String selectUserUpdt(@ModelAttribute("sysUserVO") SysUserVO sysUserVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        // 관리권한 목록
        AuthorManageVO authorVo = new AuthorManageVO();
        authorVo.setSearchCondition("3"); // 권한 부모 코드가 ROLE_ADMIN 인것만...
        List<?> authorList = egovAuthorManageService.selectAuthorList(authorVo);

        SysUserVO userVO = sysUserService.selectSysUserDetail(sysUserVO);
        if (sysUserVO.getUseYn() == null) {
            sysUserVO.setUseYn("Y");
        }
        if (sysUserVO.getAcntStats() == null) {
            sysUserVO.setAcntStats("1000");
        }

        model.addAttribute("mode", "edit");
        model.addAttribute("authorList", authorList);
        model.addAttribute("sysUserVO", userVO);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("userId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("commandMap", commandMap);

        // 관리자유형
        model.addAttribute("userType", codeService.selectCodeList("SM_ADMINUSER_TYPE"));

        // 관리 기관 목록
        model.addAttribute("orgList", sysUserService.selectMstOrgList(sysUserVO));

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/manager/sysUserRegist");
    }

    /**
     * 관리자 계정을 등록한다.
     *
     * @param sysUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/manager/sysUserInsert.json")
    @PageActionInfo(title = "관리자 계정 등록", action = "C", ajax = true)
    @ResponseBody
    public ModelAndView insertSysUser(@ModelAttribute("sysUserVO") SysUserVO sysUserVO, HttpServletRequest request,
            CommandMap commandMap,
            ModelMap model) throws Exception {

        String strIpAdress = commandMap.getIp();

        sysUserVO.setComcd(Config.COM_CD);
        sysUserVO.setReguser(commandMap.getAdminUser() != null ? commandMap.getAdminUser().getId() : "blank");
        
        
        //sysUserVO.setPassword(commandMap.getString("new_passwd"));
		// 비밀번호 평문
		String raw = commandMap.getString("new_passwd");
		String hex = "";
		
		// "SHA1PRNG"은 알고리즘 이름
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);
		// SALT 생성
		String salt = new String(Base64.getEncoder().encode(bytes));
		String rawAndSalt = raw+salt;
		
		System.out.println("raw : "+raw);
		System.out.println("salt : "+salt);
		
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		// 평문 암호화
		md.update(raw.getBytes());
		hex = String.format("%064x", new BigInteger(1, md.digest()));
		System.out.println("raw의 해시값 : "+hex);
		
		// 평문+salt 암호화
		md.update(rawAndSalt.getBytes());
		hex = String.format("%064x", new BigInteger(1, md.digest()));
		System.out.println("raw+salt의 해시값 : "+hex);
		
		//String password = EgovFileScrty.getSHA512(vo.getPassword());
		//vo.setPassword(password);
		sysUserVO.setUserSalt(salt);
		sysUserVO.setPassword(hex);
        
        
        
        sysUserVO.setIp(strIpAdress);

        sysUserVO.setUseYn("Y");
        sysUserVO.setAcntStats("1000"); // 활성
        sysUserVO.setStats("Y");

        if (request.getParameterValues("orgListNo") != null) {
            sysUserVO.setOrgList(Arrays.asList(request.getParameterValues("orgListNo")));
        }

        SysUserIpVO[] ipList = null;

        String[] ipinfoList = request.getParameterValues("ipInfoList");
        if (ipinfoList != null) {
            ipList = new SysUserIpVO[ipinfoList.length];
            for (int i = 0; i < ipinfoList.length; i++) {
                SysUserIpVO vo = new SysUserIpVO();
                vo.setComcd(Config.COM_CD);
                vo.setUserId(sysUserVO.getUserId());
                vo.setIpInfo(ipinfoList[i]);
                vo.setReguser(sysUserVO.getReguser());

                ipList[i] = vo;
            }
        }

        String result = sysUserService.insertSysUser(sysUserVO, ipList);

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        if (result.equals("OK")) {
            resultInfo = HttpUtility.getSuccessResultInfo(commandMap.getString("userId"));
        } else {
            resultInfo = HttpUtility.getErrorResultInfo(result);
        }

        mav.clear();
        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 관리자 계정을 수정한다.
     *
     * @param sysUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/manager/sysUserUpdate.json")
    @PageActionInfo(title = "관리자 계정 수정", action = "U", ajax = true)
    @ResponseBody
    public ModelAndView updateSysUser(@ModelAttribute("sysUserVO") SysUserVO sysUserVO, HttpServletRequest request,
            CommandMap commandMap,
            ModelMap model) throws Exception {

        String strIpAdress = commandMap.getIp();

        sysUserVO.setComcd(Config.COM_CD);
        sysUserVO.setIp(strIpAdress);

        if (sysUserVO.getUseYn() == null) {
            sysUserVO.setUseYn("Y");
        }
        if (sysUserVO.getAcntStats() == null) {
            sysUserVO.setAcntStats("1000");
        } else if (sysUserVO.getAcntStats().equals("N")) {
            sysUserVO.setAcntStats(null);
            sysUserVO.setUseYn("N");

        }

        if (request.getParameterValues("orgListNo") != null) {
            sysUserVO.setOrgList(Arrays.asList(request.getParameterValues("orgListNo")));
        }

        SysUserIpVO[] ipList = null;
        String[] ipinfoList = request.getParameterValues("ipInfoList");
        if (ipinfoList != null) {
            ipList = new SysUserIpVO[ipinfoList.length];
            for (int i = 0; i < ipinfoList.length; i++) {
                SysUserIpVO vo = new SysUserIpVO();
                vo.setComcd(Config.COM_CD);
                vo.setUserId(sysUserVO.getUserId());
                vo.setIpInfo(ipinfoList[i]);
                vo.setReguser(sysUserVO.getReguser());

                ipList[i] = vo;
            }
        }

        int n = sysUserService.updateSysUser(sysUserVO, commandMap.getString("oldRoleCd"), ipList);

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        if (n > 0) {
            resultInfo = HttpUtility.getSuccessResultInfo(commandMap.getString("userId"));
        } else {
            resultInfo = HttpUtility.getErrorResultInfo("수정에 실패하였습니다");
        }

        mav.clear();
        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * 아이디 중복 체크
     *
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/manager/sysUserDupliCheckAjax.json")
    @ResponseBody
    public ModelAndView checkSysUserDuplicate(CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;

        SysUserVO sysUserVO = new SysUserVO();
        sysUserVO.setUserId(commandMap.getString("userId"));
        sysUserVO.setComcd(Config.COM_CD);

        if (commandMap.getString("userId").equals("")) {
            resultInfo = HttpUtility.getErrorResultInfo("값이 충분하지 않습니다.");
        } else {
            SysUserVO vo = sysUserService.selectSysUserDetail(sysUserVO);
            if (vo != null) {
                resultInfo = HttpUtility.getErrorResultInfo("이미 사용중인 아이디 입니다.");
            } else {
                resultInfo = HttpUtility.getSuccessResultInfo(commandMap.getString("userId"));
            }
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 비밀번호 변경
     *
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/manager/sysUserPasswdUpdt.json")
    @PageActionInfo(title = "관리자 비밀번호 변경", action = "U", ajax = true)
    @ResponseBody
    public ModelAndView updateSysUserPassword(@ModelAttribute("sysUserVO") SysUserVO sysUserVO, CommandMap commandMap,
            ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;
        sysUserVO.setComcd(Config.COM_CD);
        sysUserVO.setReguser(user != null ? user.getId() : "blank");
        
        //sysUserVO.setPassword(commandMap.getString("new_passwd"));
        String raw = commandMap.getString("new_passwd");
		String hex = "";
		
		// "SHA1PRNG"은 알고리즘 이름
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);
		// SALT 생성
		String salt = new String(Base64.getEncoder().encode(bytes));
		String rawAndSalt = raw+salt;
		
		System.out.println("raw : "+raw);
		System.out.println("salt : "+salt);
		
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		// 평문 암호화
		md.update(raw.getBytes());
		hex = String.format("%064x", new BigInteger(1, md.digest()));
		System.out.println("raw의 해시값 : "+hex);
		
		// 평문+salt 암호화
		md.update(rawAndSalt.getBytes());
		hex = String.format("%064x", new BigInteger(1, md.digest()));
		System.out.println("raw+salt의 해시값 : "+hex);
		
		//String password = EgovFileScrty.getSHA512(vo.getPassword());
		//vo.setPassword(password);
		sysUserVO.setUserSalt(salt);
		sysUserVO.setPassword(hex);

        if (commandMap.getString("new_passwd").equals("") || commandMap.getString("new_passwd2").equals("") || commandMap.getString("userId").equals("")) {
            resultInfo = HttpUtility.getErrorResultInfo("값이 충분하지 않습니다.");
        } else {
            // 수정
            sysUserService.updateSysUserPassword(sysUserVO);
            resultInfo = HttpUtility.getSuccessResultInfo("비밀번호가 변경 되었습니다.");
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 관리자 목록
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/manager/checkUserAuthList")
    public String selectSysUserList(@ModelAttribute("searchVO") SysUserVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        if (!logService.checkAdminLog(commandMap, "R", "관리자 목록 조회")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setComcd(Config.COM_CD);

        // int totCnt = sysUserService.selectSysUserCnt(searchVO);
        // paginationInfo.setTotalRecordCount(totCnt);
        // model.addAttribute("paginationInfo", paginationInfo);

        // 관리권한 목록
        AuthorManageVO authorVo = new AuthorManageVO();
        authorVo.setSearchCondition("3"); // 권한 부모 코드가 ROLE_ADMIN 인것만...
        List<?> authorList = egovAuthorManageService.selectAuthorList(authorVo);
        model.addAttribute("authorList", authorList);

        List<?> list = sysUserService.selectSysUserList(searchVO);
        model.addAttribute("list", list);
        model.addAttribute("sysUser", searchVO);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 권한별 관리롤 저장
     *
     * @param authorRoleManage
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/manager/checkUserAuthSave")
    @ResponseBody
    public ModelAndView insertCheckUserAuth(@ModelAttribute("sysUserVO") SysUserVO vo, HttpServletRequest request,
            CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        String strIpAdress = commandMap.getIp();

        if (!logService.checkAdminLog(commandMap, "U", "관리자 권한 수정")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            vo.setIp(strIpAdress);
            vo.setReguser(user.getId());
            sysUserService.updateSysUserRole(vo);
            resultInfo = HttpUtility.getSuccessResultInfo("변경 되었습니다.");
        }
        mav.addObject("sysUserVO", null);
        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 권한별 관리롤 삭제
     *
     * @param authorRoleManage
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/manager/checkUserAuthDelete")
    @ResponseBody
    public ModelAndView deleteCheckUserAuth(@ModelAttribute("sysUserVO") SysUserVO vo, HttpServletRequest request,
            CommandMap commandMap, ModelMap model) throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        String strIpAdress = commandMap.getIp();

        if (!logService.checkAdminLog(commandMap, "D", "관리자 권한 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            vo.setIp(strIpAdress);
            vo.setReguser(user.getId());
            sysUserService.deleteSysUserRole(vo);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }
        mav.addObject("sysUserVO", null);
        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 관리자 그룹 목록
     *
     * @param authorManageVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/manager/sysGroupList")
    public String selectGroupAdmlist(@ModelAttribute("sysUserVO") SysUserVO vo, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        if (!logService.checkAdminLog(commandMap, "R", "권한그룹 관리 조회")) {
            return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/accessDenied");
        }

        List<?> list = sysUserService.selectSysGroupList(vo);
        model.addAttribute("list", list);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 관리그룹 상세정보
     *
     * @param authorManageVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping({ "/manager/sysGroupDetailAjax" })
    public String selectGroupDetail(@ModelAttribute("sysUserVO") SysUserVO vo, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        if (vo.getGroupId() > 0) {
            // 수정
            SysUserVO dataVO = sysUserService.selectSysGroupDetail(vo);
            model.addAttribute("sysUserVO", dataVO);
        } else {
            model.addAttribute("sysUserVO", vo);
        }

        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * 관리 그룹 등록
     *
     * @param SysUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/manager/sysGroupInsert")
    @ResponseBody
    public ModelAndView insertGroup(@ModelAttribute("sysUserVO") SysUserVO vo, HttpServletRequest request,
            CommandMap commandMap, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        if (!logService.checkAdminLog(commandMap, "C", "관리 그룹 등록")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            vo.setReguser(user.getId());
            sysUserService.insertSysGroup(vo);
            resultInfo = HttpUtility.getSuccessResultInfo("저장 되었습니다.");
        }

        mav.addObject("sysUserVO", null);
        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 관리 그룹 수정
     *
     * @param SysUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/manager/sysGroupUpdate")
    @ResponseBody
    public ModelAndView updateGroup(@ModelAttribute("sysUserVO") SysUserVO vo, HttpServletRequest request,
            CommandMap commandMap, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        if (!logService.checkAdminLog(commandMap, "U", "관리 그룹 수정")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            vo.setReguser(user.getId());
            sysUserService.updateSysGroup(vo);
            resultInfo = HttpUtility.getSuccessResultInfo("변경 되었습니다.");
        }
        mav.addObject("sysUserVO", null);
        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 관리 그룹 삭제
     *
     * @param SysUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/manager/sysGroupDelete")
    @ResponseBody
    public ModelAndView deleteGroup(@ModelAttribute("sysUserVO") SysUserVO vo, HttpServletRequest request,
            CommandMap commandMap, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        if (!logService.checkAdminLog(commandMap, "D", "관리 그룹 삭제")) {
            resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
        } else {
            vo.setReguser(user.getId());
            sysUserService.deleteSysGroup(vo);
            resultInfo = HttpUtility.getSuccessResultInfo("삭제 되었습니다.");
        }
        mav.addObject("sysUserVO", null);
        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 관리자 목록
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "IP 접속제한 목록 조회", action = PageActionType.READ)
    @GetMapping("/manager/sysIpsetList")
    public String selectSysUserIplist(@ModelAttribute("searchVO") SysUserVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);
        searchVO.setComcd(Config.COM_CD);

        List<?> list = sysUserService.selectSysUserList(searchVO);

        if (list != null && list.size() > 0) {
            int totCnt = ((SysUserVO) list.get(0)).getTotCnt();
            paginationInfo.setTotalRecordCount(totCnt);
        }

        model.addAttribute("mode", "ipset");
        model.addAttribute("list", list);
        model.addAttribute("paginationInfo", paginationInfo);
        model.addAttribute("commandMap", commandMap);

        return HttpUtility.getViewUrl(Config.ADMIN_ROOT, "/manager/sysUserList");
    }

    /**
     * 관리자 IP 수정 화면
     *
     * @param sysUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/manager/sysUserIpUpdt")
    public String selectUserIpUpdt(@ModelAttribute("sysUserVO") SysUserVO sysUserVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {
        SysUserVO vo = new SysUserVO();

        // List<?> authorList = sysUserService.selectSysGroupList(vo);

        sysUserVO.setComcd(Config.COM_CD);

        model.addAttribute("mode", "edit");
        // model.addAttribute("authorList", authorList);
        model.addAttribute("sysUserVO", sysUserService.selectSysUserDetail(sysUserVO));
        model.addAttribute("sysUserIpList", sysUserService.selectSysUserIpList(sysUserVO));
        model.addAttribute("searchQuery", commandMap.getSearchQuery("userId")); // 제외할 파라미터 입력 , 로 연결
        model.addAttribute("commandMap", commandMap);

        log.debug("model = " + model);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * IP 접속 차단 설정 정보를 저장한다.
     *
     * @param sysUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/manager/sysIpsetSave.json")
    @ResponseBody
    public ModelAndView saveSysIpUser(@ModelAttribute("sysUserVO") SysUserIpVO sysUserVO, HttpServletRequest request,
            HttpServletResponse response, CommandMap commandMap, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        sysUserVO.setComcd(Config.COM_CD);
        sysUserVO.setReguser(user.getId());

        if (commandMap.getString("mode").equals("edit")) {
            if (!logService.checkAdminLog(commandMap, "U", "IP 접속 차단 설정 수정/삭제")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");

            }
            // 수정 기능 안만듬.
        } else {
            if (!logService.checkAdminLog(commandMap, "C", "IP 접속 차단 설정 등록")) {
                resultInfo = HttpUtility.getErrorResultInfo("권한이 없습니다.");
            } else {
                sysUserService.insertSysIpUser(sysUserVO);
                resultInfo = HttpUtility.getSuccessResultInfo("등록되었습니다");
            }
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * IP 접속 차단 설정 정보를 삭제
     *
     * @param sysUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "IP 접속 차단 설정 정보 삭제", action = "D", ajax = true)
    @PostMapping("/manager/sysIpsetDelete.json")
    @ResponseBody
    public ModelAndView deleteSysIpUser(@ModelAttribute("sysUserVO") SysUserIpVO sysUserVO, HttpServletRequest request,
            HttpServletResponse response, CommandMap commandMap, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        ResultInfo resultInfo = null;

        sysUserVO.setComcd(Config.COM_CD);
        sysUserVO.setReguser(user.getId());
        sysUserService.deleteSysIpUser(sysUserVO);
        resultInfo = HttpUtility.getSuccessResultInfo("삭제되었습니다");

        mav.addObject("result", resultInfo);
        return mav;

    }

}
