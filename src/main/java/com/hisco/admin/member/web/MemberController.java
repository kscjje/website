package com.hisco.admin.member.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hisco.admin.eduadm.vo.EdcProgramVO;
import com.hisco.admin.log.service.LogService;
import com.hisco.admin.log.vo.AdminLogVO;
import com.hisco.admin.member.service.MemberService;
import com.hisco.admin.member.vo.MemberUserVO;
import com.hisco.cmm.annotation.PageActionInfo;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.Constant;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.FileMngUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.JsonUtil;
import com.hisco.user.member.service.NowonEncryptUtil;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.service.MyInforService;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * ???????????? ??????
 *
 * @author ?????????
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          ????????? ?????? ??????
 *          ------------------------------------------------------------------------
 *          ????????? 2021.03.19 ????????????
 *          ????????? 2021.10.18 ???????????? ?????? WebMemberController -> MemberController
 */
@Slf4j
@Controller
@RequestMapping(value = { "#{dynamicConfig.adminRoot}" })
public class MemberController {
    @Resource(name = "memberService")
    private MemberService memberService;

    @Resource(name = "logService")
    private LogService logService;

    @Resource(name = "myInforService")
    private MyInforService myInforService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    /**
     * ?????? ?????? ??????
     *
     * @param searchVO
     * @param request
     * @return
     * @exception Exception
     */
    @PageActionInfo(title = "?????? ?????? ??????", action = "R" , inqry=true)
    @GetMapping("/member/userList")
    public String selectWebMemberList(@ModelAttribute("searchVO") MemberUserVO searchVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        PaginationInfo paginationInfo = commandMap.getPagingInfo();
        searchVO.setPaginationInfo(paginationInfo);

        List<?> indiRptReqList = memberService.selectMemberList(searchVO);

        int intTbAllCount = 0;
        if (indiRptReqList.size() >= 1) {
            CamelMap camelMap = (CamelMap) indiRptReqList.get(0);
            intTbAllCount = Integer.valueOf(String.valueOf(camelMap.get("tbAllCount")));
        }

        paginationInfo.setTotalRecordCount(intTbAllCount);

        model.addAttribute("WebMemberList", indiRptReqList);

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("paginationInfo", paginationInfo);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ?????? ????????????
     *
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "??????  ?????? ??????", action = "R")
    @GetMapping({ "/member/userDetail", "/member/userUpdt" })
    public String selectWebMemberDetail(@ModelAttribute("memberUserVO") MemberUserVO memberUserVO,
            CommandMap commandMap, HttpServletRequest request, ModelMap model) throws Exception {

        MemberVO memberVO = memberService.selectMemberDetail(memberUserVO);

        model.addAttribute("memberUserVO", memberVO);
        model.addAttribute("commandMap", commandMap);
        model.addAttribute("searchQuery", commandMap.getSearchQuery("memNo"));

      //???????????? ???????????? ?????? ??????
         if(memberVO != null){
        	 AdminLogVO logVO = new AdminLogVO();
             logVO.setConectId(commandMap.getAdminUser() == null ? "" : commandMap.getAdminUser().getId());
             logVO.setConectIp(commandMap.getIp());
             logVO.setMenuid(commandMap.getSelectedMenu().getMenuNo());
             logVO.setMethodGubun("R");
             logVO.setTargetUrl(commandMap.getTrgetUrl());
             logVO.setInqryMemberinfo("??????:" + memberVO.getMemNm() + " , ???????????? : "+ memberVO.getMemNo());
             logService.logInsertPrivateLog(logVO);
         }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????? ?????? ?????? ??????
     *
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/member/userRegist")
    public String memberRegist(CommandMap commandMap, @ModelAttribute("memberUserVO") MemberVO memberUserVO,
            HttpServletRequest request, ModelMap model) throws Exception {
        memberUserVO.setComcd(Config.COM_CD);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ????????? ????????? ????????????.
     *
     * @param memberUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "??????  ?????? ??????", action = "C", ajax = true)
    @PostMapping("/member/userSave.json")
    @ResponseBody
    public ModelAndView insertSysUser(@ModelAttribute("memberUserVO") MemberUserVO memberUserVO,
            HttpServletRequest request,
            HttpServletResponse response, CommandMap commandMap,
            BindingResult bindingResult,
            ModelMap model) throws Exception {

        beanValidator.validate(memberUserVO, bindingResult);
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        if (bindingResult.hasErrors()) {
            log.error(JsonUtil.List2String(bindingResult.getAllErrors()));

            resultInfo = HttpUtility.getErrorResultInfo("???????????? ??????");
        } else {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

            memberUserVO.setReguser(user.getId());
            memberUserVO.setUserIp(commandMap.getIp());
            Map<String, Object> result = memberService.insertMemberInfo(memberUserVO);

            if (Config.SUCCESS.equals((String) result.get("result"))) {
                resultInfo = HttpUtility.getSuccessResultInfo("?????? ???????????????");

            } else {
                resultInfo = HttpUtility.getErrorResultInfo((String) result.get("resultMsg"));
            }

        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * ????????? ????????? ????????????.
     *
     * @param memberUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "??????  ???????????? ?????????", action = "U")
    @PostMapping("/member/userPasswdSet.json")
    @ResponseBody
    public ModelAndView userPasswdSet(@ModelAttribute("memberUserVO") MemberUserVO memberUserVO,
            HttpServletRequest request,
            HttpServletResponse response, CommandMap commandMap,
            ModelMap model) throws Exception {

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        // ?????? ????????? ??????
        MemberUserVO memberVO = memberService.selectMemberDetail(memberUserVO);

        // ????????? 4????????? ???????????? ??????
        String newPasswd = memberVO.getHp();
        if (newPasswd.length() >= 4)
            newPasswd = newPasswd.substring(newPasswd.length() - 4);

        String enpassword = EgovFileScrty.encryptPassword(newPasswd, EgovStringUtil.isNullToString(memberVO.getId()));
        LoginVO loginVO = new LoginVO();
        loginVO.setPassword(enpassword);
        loginVO.setId(user.getId()); // moduser ???
        loginVO.setStatus(memberVO.getStatus());
        loginVO.setIp(commandMap.getIp());
        loginVO.setUniqId(memberVO.getMemNo());

        int cnt = myInforService.updateMemberPassword(loginVO);

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        if (cnt > 0) {
            resultInfo.setSuccess(true);
        } else {
            resultInfo.setSuccess(false);
        }

        mav.addObject("result", resultInfo);
        return mav;

    }

    /**
     * ????????? ????????? ????????????.
     *
     * @param memberUserVO
     * @param model
     * @return
     * @throws Exception
     */
    @PageActionInfo(title = "??????  ?????? ??????", action = "U", ajax = true)
    @PostMapping("/member/userUpdtSave.json")
    @ResponseBody
    public ModelAndView userUpdtSave(@ModelAttribute("memberUserVO") MemberUserVO memberUserVO,
            HttpServletRequest request,
            HttpServletResponse response, CommandMap commandMap,
            BindingResult bindingResult,
            ModelMap model) throws Exception {

        beanValidator.validate(memberUserVO, bindingResult);
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        if (bindingResult.hasErrors()) {
            log.error(JsonUtil.List2String(bindingResult.getAllErrors()));

            resultInfo = HttpUtility.getErrorResultInfo("???????????? ??????");
        } else {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            memberUserVO.setModuser(user.getId());
            memberUserVO.setUserIp(commandMap.getIp());



            if(memberUserVO.getSmsYn() == null){
            	memberUserVO.setSmsYn("N");
            }
            if(memberUserVO.getEmailYn() == null){
            	memberUserVO.setEmailYn("N");
            }
            int cnt = myInforService.updateMemberData(memberUserVO);

            if (cnt > 0) {
                resultInfo = HttpUtility.getSuccessResultInfo("?????? ?????? ???????????????");
            } else {
                resultInfo = HttpUtility.getErrorResultInfo("?????? ????????? ??????");
            }
        }

        mav.addObject("result", resultInfo);
        return mav;

    }


    @PageActionInfo(title = "?????? ?????? ????????????", action = "R"  , inqry=true)
    @GetMapping(value = {"/member/userListExcel"})
    public void edcProgramListExcel(@ModelAttribute("searchVO") MemberUserVO searchVO, CommandMap commandMap, HttpServletResponse response,
            HttpServletRequest request, ModelMap model) throws Exception {

        searchVO.setExcelyn("Y");

        String templete = "memberList";
        String file_name = "??????_??????";

    	List<?> indiRptReqList = memberService.selectMemberList(searchVO);

        //?????? ????????? ?????? ??? ?????? ?????? data
        Map data = new HashMap();
        data.put("list", indiRptReqList);

        XLSTransformer transformer = new XLSTransformer();

        InputStream is = readTemplate(templete+".xls");
        Workbook workbook = null;
        try {
            workbook = transformer.transformXLS(is, data);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            response.getWriter().println("?????? ????????? ?????? ??? ?????? ??????<br>"+ e.getMessage());
        }

        //?????? ????????? contentType ??????
        response.setContentType( "application/vnd.ms-excel" );


		if(file_name.equals("")) {
			file_name = templete;
		}

        //?????? ????????? ??????
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
