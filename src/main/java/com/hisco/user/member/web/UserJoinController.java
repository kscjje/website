package com.hisco.user.member.web;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hisco.admin.admcmm.service.AdmCmmService;
import com.hisco.admin.terms.vo.TermsVO;
import com.hisco.cmm.modules.RequestUtil;
import com.hisco.cmm.modules.pg.nice.NiceNamefactDto;
import com.hisco.cmm.modules.site.thissite.module_config.ModuleConfigVo;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.EMailUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.intrfc.sms.service.SmsService;
import com.hisco.user.evtrsvn.service.EvtrsvnMstVO;
import com.hisco.user.member.service.UserJoinService;
import com.hisco.user.member.service.UserLoginService;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.service.MyInforService;
import com.hisco.user.nice.service.NiceNamefactService;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.uss.ion.bnr.service.BannerVO;
import lombok.extern.slf4j.Slf4j;

/**
 * ??????????????? ???????????? ?????? ????????????
 *
 * @author ?????????
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          ????????? ?????? ??????
 *          ------------------------------------------------------------------------
 *          ????????? 2020.08.12 ????????????
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/member")
public class UserJoinController {

    @Resource(name = "niceNamefactService")
    private NiceNamefactService niceNamefactService;

    /*
     * @Resource(name="tossNamefactService")
     * private TossNamefactService tossNamefactService;
     */

    @Resource(name = "userLoginService")
    private UserLoginService userLoginService;

    @Resource(name = "myInforService")
    private MyInforService myInforService;

    @Resource(name = "userJoinService")
    private UserJoinService userJoinService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "smsService")
    private SmsService smsService;

    @Resource(name = "admCmmService")
    private AdmCmmService admCmmService;

    @Autowired
    private DefaultBeanValidator beanValidator;

    /**
     * ???????????? ?????? ??????.
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/join/joinStep1")
    public String joinFirst(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {
        if (commandMap.getUserInfo() != null) {
            HttpUtility.sendBack(request, response, "????????? ??????????????? ??????????????? ?????? ??? ????????????.");
            return null;
        }

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ?????? ??????.
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @GetMapping(value = "/join/joinStep2")
    public String joinSecond(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, TermsVO termsVO) throws Exception {
        if (commandMap.getUserInfo() != null) {
            HttpUtility.sendBack(request, response, "????????? ??????????????? ??????????????? ?????? ??? ????????????.");
            return null;
        }

        List<TermsVO> result = (List<TermsVO>) userJoinService.selectTermsList(termsVO);
        model.addAttribute("result", result);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ?????? ?????? ??????.
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/join/joinStep3")
    public String joinThird(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @ModelAttribute("memberVO") MemberVO memberVO) throws Exception {

        log.info("-------------------------call UserJoinController :: joinThird :: ------------ ");

        if (commandMap.getUserInfo() != null) {
            HttpUtility.sendRedirect(request, response, "????????? ??????????????? ??????????????? ?????? ??? ????????????.", Config.USER_ROOT + "/main");
            return null;
        }

        if (!commandMap.getString("type").equals("normal") && !commandMap.getString("type").equals("child")) {
            HttpUtility.sendRedirect(request, response, "?????? ?????? ?????? ?????? ????????????.", Config.USER_ROOT + "/member/join/joinStep1");
            return null;
        }

        // ------------------------------------------???????????? Start.------------>
        RequestUtil requestData = RequestUtil.getInstance(request);
        ModuleConfigVo moduleConfigData = requestData.HiscoModuleConfigInfo();

        if (moduleConfigData == null) {
            log.info("moduleConfigData is null");
        } else {
            log.info("moduleConfigData = " + moduleConfigData.toMap());
        }
        // ------------------------------------------???????????? End.------------>

        model.addAttribute("type", commandMap.getString("type"));

        log.debug("final model = " + model);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ??? ?????? ?????? ????????????
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/join/joinCertCheck")
    public String joinCertChk(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @ModelAttribute("memberVO") MemberVO memberVO) throws Exception {

        log.debug("call joinCertCheck()");

        Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        if (commandMap.getUserInfo() != null) {
            HttpUtility.sendRedirect(request, response, "????????? ??????????????? ??????????????? ?????? ??? ????????????.", Config.USER_ROOT + "/main");
            return null;
        }
        if (!commandMap.getString("type").equals("normal") && !commandMap.getString("type").equals("child")) {
            HttpUtility.sendRedirect(request, response, "?????? ?????? ?????? ?????? ????????????.", Config.USER_ROOT + "/member/join/joinStep1");
            return null;
        }

        String type = commandMap.getString("type");
        model.addAttribute("type", commandMap.getString("type"));

        /*
         * JYS 2021.05.11 NamefactVO namefactData = tossNamefactService.getCertResult(commandMap.getString("LGD_MID"),
         * commandMap.getString("LGD_AUTHONLYKEY"), commandMap.getString("LGD_PAYTYPE"));
         * if (namefactData == null ) {
         * model.addAttribute("alertMsg", "???????????? ????????? ????????????." );
         * return HttpUtility.getViewUrl(Config.USER_ROOT, "/member/join/joinStep3");
         * } else if (!"0000".equals(namefactData.getRespCode())) {
         * model.addAttribute("alertMsg", namefactData.getRespMsg());
         * return HttpUtility.getViewUrl(Config.USER_ROOT, "/member/join/joinStep3");
         * } else {
         * //????????? ?????? ????????? ??????
         * if (type.equals("child")) {
         * namefactData.setName(commandMap.getString("name"));
         * namefactData.setSex(commandMap.getString("gender"));
         * String birthday = commandMap.getString("birthYear")+commandMap.getString("birthMonth") +
         * commandMap.getString("birthDay");
         * namefactData.setBirthday(DateUtil.string2date(birthday));
         * }
         * //???????????? ?????? ?????? ??????
         * MemberVO memberCheck =myInforService.selectMemberSearchByName(namefactData);
         * if (memberCheck != null) {
         * model.addAttribute("alertMsg", "EXISTS");
         * return HttpUtility.getViewUrl(Config.USER_ROOT, "/member/join/joinStep3");
         * } else {
         * //???????????? ????????? ??????
         * tossNamefactService.saveData(request, type, namefactData);
         * HttpUtility.sendRedirect(request, response, "" , Config.USER_ROOT + "/member/join/joinStep4?type="+type);
         * return null;
         * }
         * }
         */

        return null;

    }

    /**
     * ???????????? ?????? ??????.
     *
     * @param searchVO
     * @param CalendarVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/join/joinStep4")
    public String joinForth(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @ModelAttribute("memberVO") MemberVO memberVO) throws Exception {

        log.debug("call joinForth()");

        Map<String, Object> paramPMap = commandMap.getParam();
        log.debug("paramPMap = " + paramPMap);

        String strType = commandMap.getString("type");

        log.debug("strType = " + strType);

        String strSessionType = String.valueOf(request.getSession().getAttribute("jobGbn"));

        log.debug("strSessionType = " + strSessionType);

        if ((strType == null) || ("".equals(strType))) {
            if ("memRegiChild".equals(strSessionType)) {
                strType = "child";
            } else {
                strType = "normal";
            }
        }

        // NamefactVO namefactData = tossNamefactService.getData(request, type);

        String strBirthDate = String.valueOf(request.getSession().getAttribute("birstDate"));
        log.debug("strBirthDate = " + strBirthDate);

        NiceNamefactDto niceNamefactDto = niceNamefactService.getData(request, "myConfirmCerti_" + strBirthDate);

        if (niceNamefactDto == null) {
            log.debug("niceNamefactDto is null");
        } else {
            log.debug("niceNamefactDto = " + niceNamefactDto.toMap());
        }

        if (niceNamefactDto == null) {

            HttpUtility.sendRedirect(request, response, "???????????? ????????? ????????????.", Config.USER_ROOT + "/member/join/joinStep1");
            return null;

        } else {

            String strCrc_data_di = niceNamefactDto.getCrc_data_di();

            Map<String, Object> paramMyMap = new HashMap<String, Object>();
            paramMyMap.put("crc_data_di", strCrc_data_di);
            List<?> memberCertiList = userJoinService.selectMemberByCertiId(paramMyMap);

            if (memberCertiList.size() >= 1) {

                log.debug("memberCertiList is not null");

                if ("child".equals(strType)) {
                } else {
                    HttpUtility.sendRedirect(request, response, "?????? ????????? ?????? ???????????????.", Config.USER_ROOT + "/member/login");
                    return null;
                }

            } else {
                log.debug("memberCertiList is null");
            }

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("grpCd", "SM_MAIL_SERVER");
            List<?> mathAgeGrpCdList = admCmmService.selectCotGrpCdListByParm(paramMap);
            model.addAttribute("mailServerGrpCdList", mathAgeGrpCdList);

            // ???????????? ?????? ??????
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

            memberVO.setPiAuthkey(niceNamefactDto.getCrc_data_di());
            memberVO.setPiAuthtype( "checkplus_ipin".equals(niceNamefactDto.getCrc_type())? "4001":"3001"); // ????????? ?????? : 3001 , ????????? ?????? : 4001
            memberVO.setHp(niceNamefactDto.getTel());
            memberVO.setGender(niceNamefactDto.getSex());
            memberVO.setMemNm(niceNamefactDto.getName());
            memberVO.setBirthDate(dateFormat.format(niceNamefactDto.getBirthday()));
            
            //14??? ?????? ???????????? 14????????? ?????? ?????????
      	  if("child".equals(strType)&&   "checkplus_ipin".equals(niceNamefactDto.getCrc_type())   ) {
      		  if( niceNamefactDto.getAge() >= 14) {
      			HttpUtility.sendRedirect(request, response, "??? 14??? ?????? ??????????????? ????????? ????????????.", Config.USER_ROOT + "/member/join/joinStep1");
      		  }  
      	  }

            if (strType.equals("child")) {
                // ?????? ?????? ??????
                memberVO.setPiPAuthkey(niceNamefactDto.getCrc_data_di());
                memberVO.setPiPAuthtype( "checkplus_ipin".equals(niceNamefactDto.getCrc_type())? "4001":"3001"); // ????????? ?????? : 3001 , ????????? ?????? : 4001
            }

        }

        /*
         * JYS 2021.05.11 ??????
         * if (namefactData == null ) {
         * HttpUtility.sendRedirect(request, response, "???????????? ????????? ????????????." , Config.USER_ROOT + "/member/join/joinStep1");
         * return null;
         * } else {
         * //???????????? ?????? ??????
         * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd" , Locale.KOREA);
         * memberVO.setPiAuthkey(namefactData.getCrc_data_di());
         * memberVO.setPiAuthtype("3001"); // ????????? ??????
         * memberVO.setHp(namefactData.getTel());
         * memberVO.setGender(namefactData.getSex());
         * memberVO.setMemNm(namefactData.getName());
         * memberVO.setBirthDate( dateFormat.format(namefactData.getBirthday()));
         * if (type.equals("child")) {
         * //?????? ?????? ??????
         * memberVO.setPiPAuthkey(namefactData.getCrc_data_di());
         * memberVO.setPiPAuthtype("3001"); // ????????? ??????
         * }
         * }
         * model.addAttribute("type",type);
         * model.addAttribute("intrList", myInforService.selectIntrstList( new LoginVO() ));
         * model.addAttribute("snsList", myInforService.selectSnsCnncList( new LoginVO() ));
         * model.addAttribute("gbnList", codeService.selectCodeList("CM_RESDNC_GBN"));
         * model.addAttribute("currentDomain", EgovProperties.getProperty("Globals.Domain"));
         * model.addAttribute("kakaoKey", EgovProperties.getProperty("SNS.kakaoKey"));
         * model.addAttribute("naverKey", EgovProperties.getProperty("SNS.naverKey"));
         * model.addAttribute("googleKey", EgovProperties.getProperty("SNS.googleKey"));
         */

        model.addAttribute("type", strType);
        
        if("child".equals(strType)&&   "checkplus_mobile".equals(niceNamefactDto.getCrc_type())   ) {
        	//return HttpUtility.getViewUrl(request);
        	//System.out.println("/web/member/join/ChildJoinStep4 --> " + HttpUtility.getViewUrl(request) );
        	return "/web/member/join/ChildJoinStep4"; // 14??? ?????? ????????? ????????? ????????? ???????????? ???
        }else {
        	return HttpUtility.getViewUrl(request);
        }
    }

    /**
     * ????????? / ????????? ?????? ??????
     *
     * @param commandMap
     * @param CalendarVO
     * @return
     * @throws Exception
     */
    @PostMapping("/join/memDupliCheckAjax")
    @ResponseBody
    public ModelAndView checkMemberDuplicate(CommandMap commandMap, ModelMap model, MemberVO memberVO)
            throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = null;
        // set enckey
        memberVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        String type = commandMap.getString("type");

        if (StringUtils.isEmpty(memberVO.getId()) && StringUtils.isEmpty(memberVO.getEmail())) {
            resultInfo = HttpUtility.getErrorResultInfo("?????? ???????????? ????????????.");
        } else {
            if (type.equals("id")) {
                MemberVO vo = userJoinService.selectMemberDetail(memberVO);
                if (vo != null) {
                    resultInfo = HttpUtility.getErrorResultInfo("?????? ???????????? ????????? ?????????.");
                } else {
                    resultInfo = HttpUtility.getSuccessResultInfo("?????? ?????? ??? ?????? ????????? ?????????.");
                }
            } else if (type.equals("email")) {
                int check = userJoinService.selectMemberEmailCheck(memberVO);
                if (check < 1) {
                    resultInfo = HttpUtility.getSuccessResultInfo("?????? ?????? ??? ?????? ????????? ?????????.");
                } else {
                    resultInfo = HttpUtility.getErrorResultInfo("?????? ???????????? ????????? ?????????.");
                }
            }
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * ???????????? ??????????????????
     *
     * @param searchVO
     * @param CalendarVO
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/join/joinActionTest")
    public String insertMembertest(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("memberVO") MemberVO memberVO, BindingResult bindingResult, ModelMap model)
            throws Exception {
        memberVO.setJoinDate(DateUtil.printDatetime(null, "yyyy-MM-dd"));
        // ????????????
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("MAIL_ID", "sujinbest@gmail.com");
            paramMap.put("MAIL_TITLE", "????????????????????? ??????????????? ?????????????????????.");
            paramMap.put("TEMPLATE_ID", "joinGeneral");

            paramMap.put("ID", "quency");
            paramMap.put("NAME", "?????????");
            paramMap.put("REGDATE", memberVO.getJoinDate());

            EMailUtil webEMailUtil = new EMailUtil();
            webEMailUtil.sendToEmail(paramMap, null);

        } catch (Exception e) {
            // ???????????? ?????? ??????
            e.printStackTrace();

            response.getWriter().print(e.getMessage());
        }

        return null;

    }

    /**
     * ??????????????? ????????????
     *
     * @param searchVO
     * @param CalendarVO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/join/joinAction")
    public String insertMember(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("memberVO") MemberVO memberVO, BindingResult bindingResult, ModelMap model)
            throws Exception {

        log.debug("call insertMember()");

        Map<String, Object> myParamMap = commandMap.getParam();
        log.debug("myParamMap = " + myParamMap);

        String type = commandMap.getString("type");
        memberVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        Map<String, Object> paramMapN = commandMap.getParam();
        log.debug("paramMapN = " + paramMapN);

        String strIpAdress = commandMap.getIp();

        memberVO.setUserIp(strIpAdress);
        memberVO.setComcd(Config.COM_CD);
        memberVO.setReguser(memberVO.getId());

        String strBirthDate = String.valueOf(request.getSession().getAttribute("birstDate"));
        log.debug("strBirthDate = " + strBirthDate);

        NiceNamefactDto niceNamefactDto = niceNamefactService.getData(request, "myConfirmCerti_" + strBirthDate);

        if (niceNamefactDto == null) {
            log.debug("niceNamefactDto is null");
        } else {
            log.debug("niceNamefactDto = " + niceNamefactDto.toMap());
        }

        // insert
        /// NamefactVO namefactData = tossNamefactService.getData(request, type);

        if (niceNamefactDto == null) {
            HttpUtility.sendRedirect(request, response, "???????????? ????????? ????????????.", Config.USER_ROOT + "/member/join/joinStep1");
        } else {

            String snsId = memberVO.getSnsVO().getSnsid();

            if (!snsId.equals("") && snsId.indexOf("@") > 0) {
                snsId = snsId.split("@")[0];
                memberVO.getSnsVO().setSnsid(snsId);
            }

            /*?????? ???????????? ????????? ??????*/
         // ???????????? ??????
    		String raw = memberVO.getPw();
    		String hex = "";
    		
    		// "SHA1PRNG"??? ???????????? ??????
    		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    		byte[] bytes = new byte[16];
    		random.nextBytes(bytes);
    		// SALT ??????
    		String salt = new String(Base64.getEncoder().encode(bytes));
    		String rawAndSalt = raw+salt;
    		
    		System.out.println("raw : "+raw);
    		System.out.println("salt : "+salt);
    		
    		MessageDigest md = MessageDigest.getInstance("SHA-512");
    		// ?????? ?????????
    		md.update(raw.getBytes());
    		hex = String.format("%064x", new BigInteger(1, md.digest()));
    		System.out.println("raw??? ????????? : "+hex);
    		
    		// ??????+salt ?????????
    		md.update(rawAndSalt.getBytes());
    		hex = String.format("%064x", new BigInteger(1, md.digest()));
    		System.out.println("raw+salt??? ????????? : "+hex);
    		
    		memberVO.setSalt(salt);
    		memberVO.setPw(hex);            
            
            
            
            
            Map<String, Object> result = userJoinService.insertMemberInfo(memberVO);

            if (Config.SUCCESS.equals((String) result.get("result"))) {

                niceNamefactService.clear(request); // ???????????? ???????????? ??????
                memberVO.setJoinDate(DateUtil.printDatetime(null, "yyyy-MM-dd"));

                // ????????????
                try {
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("MAIL_ID", memberVO.getEmail());
                    paramMap.put("MAIL_TITLE", "???????????? ?????????????????? ????????????????????? ??????????????? ?????????????????????.");
                    paramMap.put("TEMPLATE_ID", "joinGeneral");

                    paramMap.put("ID", memberVO.getId());
                    paramMap.put("NAME", memberVO.getMemNm());
                    paramMap.put("REGDATE", memberVO.getJoinDate());

                    //// JYS 2021.06.16 EMailUtil webEMailUtil = new EMailUtil();
                    //// JYS 2021.06.16 webEMailUtil.sendToEmail(paramMap, null);

                } catch (Exception e) {
                    // ???????????? ?????? ??????
                    log.error("???????????? ???????????? ??????:" + e.getMessage());
                }

                // SMS ??????
                try {
                    Map<String, String> smsParam = new HashMap<String, String>();
                    smsParam.put("hp", memberVO.getHp());
                    smsParam.put("msgno", "1");
                    smsParam.put("msgcd", "1");
                    smsParam.put("?????????", memberVO.getMemNm());

                    LoginVO userVO = new LoginVO();
                    userVO.setId(memberVO.getMemNo());
                    userVO.setName(memberVO.getMemNm());

                    //// JYS 2021.06.16 smsService.sendMessage(smsParam , userVO);
                } catch (Exception e) {
                    // sms?????? ?????? ??????
                    log.error("???????????? ???????????? ??????:" + e.getMessage());
                }

                model.addAttribute("memberVO", memberVO);
                return HttpUtility.getViewUrl(request);
            } else {
                HttpUtility.sendBack(request, response, (String) result.get("resultMsg"));
            }
        }

        return HttpUtility.getViewUrl(request);

        /*
         * JYS 2021.05.11 ??????
         * //insert
         * NamefactVO namefactData = tossNamefactService.getData(request, type);
         * if (namefactData == null ) {
         * HttpUtility.sendRedirect(request, response, "???????????? ????????? ????????????." , Config.USER_ROOT + "/member/join/joinStep1");
         * } else {
         * String snsId = memberVO.getSnsVO().getSnsid();
         * if (!snsId.equals("") && snsId.indexOf("@") > 0) {
         * snsId = snsId.split("@")[0];
         * memberVO.getSnsVO().setSnsid(snsId);
         * }
         * Map<String, Object> result = userJoinService.insertMemberInfo(memberVO);
         * if (Config.SUCCESS.equals((String)result.get("result"))) {
         * tossNamefactService.clear(request); // ???????????? ???????????? ??????
         * memberVO.setJoinDate(DateUtil.printDatetime(null ,"yyyy-MM-dd"));
         * //????????????
         * try {
         * Map<String, Object> paramMap = new HashMap<String,Object>();
         * paramMap.put("MAIL_ID", memberVO.getEmail());
         * paramMap.put("MAIL_TITLE", "????????????????????? ??????????????? ?????????????????????.");
         * paramMap.put("TEMPLATE_ID", "joinGeneral");
         * paramMap.put("ID", memberVO.getId());
         * paramMap.put("NAME", memberVO.getMemNm());
         * paramMap.put("REGDATE", memberVO.getJoinDate());
         * EMailUtil webEMailUtil = new EMailUtil();
         * webEMailUtil.sendToEmail(paramMap, null);
         * } catch (Exception e) {
         * //???????????? ?????? ??????
         * log.error("???????????? ???????????? ??????:" + e.getMessage());
         * }
         * //SMS ??????
         * try {
         * Map<String, String> smsParam = new HashMap<String, String>();
         * smsParam.put("hp", memberVO.getHp());
         * smsParam.put("msgno", "1");
         * smsParam.put("msgcd", "1");
         * smsParam.put("?????????",memberVO.getMemNm());
         * LoginVO userVO = new LoginVO();
         * userVO.setId(memberVO.getMemNo());
         * userVO.setName(memberVO.getMemNm());
         * smsService.sendMessage(smsParam , userVO);
         * } catch (Exception e) {
         * //sms?????? ?????? ??????
         * log.error("???????????? ???????????? ??????:" + e.getMessage());
         * }
         * model.addAttribute("memberVO", memberVO);
         * return HttpUtility.getViewUrl(request);
         * } else {
         * HttpUtility.sendBack(request, response, (String)result.get("resultMsg"));
         * }
         * }
         */
    }

    /**
     * Join Result Page
     *
     * @param
     * @return callback page
     * @exception Exception
     */
    @RequestMapping("/join/joinResult")
    public String memberJoinResult(HttpServletRequest request, ModelMap model, BannerVO vo,
            @ModelAttribute("memberVO") MemberVO memberVO) throws Exception {
        vo.setBannerGbn("2001"); // ???????????? ??????
        model.addAttribute("bList", userJoinService.selectBannerList(vo));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ?????????????????? ???????????? ??? ?????? ?????? ????????????
     *
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/join/mobileChangePop")
    public String selectMobileChangeSearch(CommandMap commandMap, HttpServletRequest request,
            HttpServletResponse response, ModelMap model) throws Exception {

        /*
         * JYS 2021.05.11
         * NamefactVO namefactData = tossNamefactService.getCertResult(commandMap.getString("LGD_MID"),
         * commandMap.getString("LGD_AUTHONLYKEY"), commandMap.getString("LGD_PAYTYPE"));
         * //?????????????????? ????????? ???????????? ??????
         * UserSession user = (UserSession)request.getSession().getAttribute(Config.USER_SESSION);
         * if (namefactData == null ) {
         * HttpUtility.sendBack(request, response, "???????????? ????????? ????????????.");
         * } else if (!"0000".equals(namefactData.getRespCode())) {
         * HttpUtility.sendBack(request, response, namefactData.getRespMsg());
         * } else if (user == null || user.getUserInfo()== null || !user.getUserInfo().isMember()) {
         * HttpUtility.sendBack(request, response, "????????? ????????? ????????????. ???????????? ????????? ?????????.");
         * } else {
         * String userId = user!=null?user.getUserInfo().getId():"";
         * namefactData.setId(userId);
         * MemberVO memberVO = myInforService.selectMemberSearchById(namefactData);
         * model.addAttribute("memberVO", memberVO);
         * model.addAttribute("userInfo", user!=null?user.getUserInfo():null);
         * model.addAttribute("namefactData", namefactData);
         * if (!userId.equals("") && memberVO != null && memberVO.getId().equals(userId)) {
         * tossNamefactService.saveData(request, "mobilechange_" + userId, namefactData); // ?????????????????? ????????? ??????
         * }
         * }
         */

        return HttpUtility.getViewUrl(request);
    }

    /**
     * SNS ???????????? ??????
     *
     * @param
     * @return result - ??????
     * @exception Exception
     */
    @PostMapping(value = "/join/findSnsCheck")
    @ResponseBody
    public ModelAndView findSnsCheck(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        String snsId = commandMap.getString("snsId");
        String snsEmail = commandMap.getString("snsEmail");

        if (!snsEmail.equals("")) {
            snsId = snsEmail.split("@")[0];
            commandMap.put("snsId", snsId);
        }

        int cnt = userLoginService.selectSnsConnection(commandMap.getParam());
        if (cnt < 1) {
            resultInfo.setSuccess(true);
        } else {
            resultInfo.setSuccess(false);
        }
        mav.addObject("result", resultInfo);

        return mav;
    }

    /**
     * ?????? ?????? ?????? ?????????
     *
     * @param
     * @return callback page
     * @exception Exception
     */
    @PostMapping(value = "/join/joinUpdate")
    public String memberJoinUpdate(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @ModelAttribute("memberVO") MemberVO memberVO) throws Exception {
        LoginVO userVO = new LoginVO();
        // ??????????????? ??????????????? ?????????
        userVO.setUniqId(commandMap.getString("updateId"));
        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        MemberVO dataVO = myInforService.selectMemberData(userVO);

        if (dataVO == null) {
            HttpUtility.sendRedirect(request, response, "?????? ????????? ????????????.????????? ??? ?????? ????????? ?????????.", Config.USER_ROOT + "/member/login");
            return null;
        }

        model.addAttribute("memberVO", dataVO);
        model.addAttribute("snsId", commandMap.getString("snsId"));

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ???????????? ??? ?????? ?????? ????????????
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/join/joinUpdateCertCheck")
    public String joinUpdateCertCheck(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @ModelAttribute("memberVO") MemberVO memberVO) throws Exception {

        if (commandMap.getUserInfo() != null) {
            HttpUtility.sendRedirect(request, response, "????????? ??????????????? ??????????????? ?????? ??? ????????????.", Config.USER_ROOT + "/main");
            return null;
        }

        String type = commandMap.getString("type");
        model.addAttribute("type", commandMap.getString("type"));

        /*
         * JYS 2021.05.11
         * NamefactVO namefactData = tossNamefactService.getCertResult(commandMap.getString("LGD_MID"),
         * commandMap.getString("LGD_AUTHONLYKEY"), commandMap.getString("LGD_PAYTYPE"));
         * if (namefactData == null ) {
         * HttpUtility.sendBack(request, response, "???????????? ????????? ????????????.");
         * return null;
         * } else if (!"0000".equals(namefactData.getRespCode())) {
         * HttpUtility.sendBack(request, response, namefactData.getRespMsg());
         * return null;
         * } else {
         * //????????? ?????? ????????? ??????
         * if (type.equals("child")) {
         * namefactData.setName(commandMap.getString("name"));
         * namefactData.setBirthYmd(commandMap.getString("birthYmd"));
         * namefactData.setSex(commandMap.getString("gender"));
         * Date birthday = DateUtil.string2date(commandMap.getString("birthYmd"));
         * namefactData.setBirthday(birthday);
         * }
         * namefactData.setMemNo(memberVO.getMemNo());
         * namefactData.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
         * namefactData.setSnsId(commandMap.getString("snsId"));
         * //???????????? ?????? ?????? ??????
         * MemberVO memberCheck =myInforService.selectOldMemberSearch(namefactData);
         * if (memberCheck != null) {
         * //???????????? ????????? ??????
         * tossNamefactService.saveData(request, type, namefactData);
         * HttpUtility.sendRedirect(request, response, "" , Config.USER_ROOT +
         * "/member/join/joinUpdateinput?type="+type);
         * return null;
         * } else {
         * HttpUtility.sendBack(request, response, "???????????? ????????? ?????? ???????????? ???????????? ????????????.");
         * return null;
         * }
         * }
         */

        return null;

    }

    /**
     * ?????? ?????? ????????? ?????? ?????????
     *
     * @param searchVO
     * @param CalendarVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/join/joinUpdateinput")
    public String joinReinput(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @ModelAttribute("memberVO") MemberVO memberVO) throws Exception {

        String type = commandMap.getString("type");

        /*
         * JYS 2021.05.11
         * NamefactVO namefactData = tossNamefactService.getData(request, type);
         * LoginVO userVO = new LoginVO();
         * MemberVO data = new MemberVO();
         * if (namefactData == null ) {
         * HttpUtility.sendRedirect(request, response, "???????????? ????????? ????????????." , Config.USER_ROOT + "/member/login");
         * return null;
         * } else {
         * //???????????? ?????? ??????
         * userVO.setUniqId(namefactData.getMemNo());
         * userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
         * data = myInforService.selectMemberData( userVO );
         * data.setPiAuthkey(namefactData.getCrc_data_di());
         * data.setPiAuthtype("3001"); // ????????? ??????
         * data.setHp(namefactData.getTel());
         * data.setGender(namefactData.getSex());
         * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd" , Locale.KOREA);
         * data.setBirthDate( dateFormat.format(namefactData.getBirthday()));
         * data.setSnsRegistyn((namefactData.getSnsId()!=null&& !namefactData.getSnsId().equals(""))?"Y":"N" );
         * if (type.equals("child")) {
         * //?????? ?????? ??????
         * data.setPiPAuthkey(namefactData.getCrc_data_di());
         * data.setPiPAuthtype("3001"); // ????????? ??????
         * }
         * }
         * model.addAttribute("memberVO",data);
         * model.addAttribute("type",type);
         * model.addAttribute("intrList", myInforService.selectIntrstList( userVO ));
         * model.addAttribute("gbnList", codeService.selectCodeList("CM_RESDNC_GBN"));
         */

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ??????????????? ????????????
     *
     * @param searchVO
     * @param CalendarVO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/join/joinUpdateSave")
    public String joinUpdateSave(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("memberVO") MemberVO memberVO, BindingResult bindingResult, ModelMap model)
            throws Exception {

        String strIpAdress = commandMap.getIp();

        String type = commandMap.getString("type");
        memberVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        memberVO.setUserIp(strIpAdress);
        memberVO.setComcd(Config.COM_CD);

        beanValidator.validate(memberVO, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("type", commandMap.getString("type"));
            model.addAttribute("intrList", myInforService.selectIntrstList(new LoginVO()));
            model.addAttribute("gbnList", codeService.selectCodeList("CM_RESDNC_GBN"));

            return HttpUtility.getViewUrl(Config.USER_ROOT, "/member/join/joinUpdateinput");
        } else {

            /*
             * JYS 2021.05.11
             * //insert
             * NamefactVO namefactData = tossNamefactService.getData(request, type);
             * if (namefactData == null ) {
             * HttpUtility.sendRedirect(request, response, "???????????? ????????? ????????????." , Config.USER_ROOT + "/member/login");
             * } else {
             * String enpassword = EgovFileScrty.encryptPassword(memberVO.getPw(), memberVO.getId());
             * memberVO.setPw(enpassword);
             * MemberVO vo = userJoinService.selectMemberDetail(memberVO);
             * if (vo == null || vo.getMemNo()==null || vo.getMemNo().equals(memberVO.getMemNo())) {
             * int cnt = myInforService.updateMemberDataOld(memberVO);
             * if (cnt > 0) {
             * tossNamefactService.clear(request); // ???????????? ???????????? ??????
             * HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/member/join/joinUpdateEnd");
             * } else {
             * HttpUtility.sendBack(request, response, "????????? ???????????? ????????????. ???????????? ?????? ?????? ????????? ?????????.");
             * }
             * } else {
             * HttpUtility.sendBack(request, response, "?????? ???????????? ????????? ?????????. ???????????? ????????? ?????????.");
             * }
             * }
             */

            return null;
        }

    }

    /**
     * ?????? ?????? ????????? ?????? ????????? ???
     *
     * @param searchVO
     * @param CalendarVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/join/joinUpdateEnd")
    public String joinUpdateEnd(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model, @ModelAttribute("memberVO") MemberVO memberVO) throws Exception {

        return HttpUtility.getViewUrl(request);
    }

    /**
     * ????????????
     *
     * @param commandMap
     * @return String - jsp ?????????
     * @exception Exception
     */
    @RequestMapping(value = "jusoPop", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json; charset=UTF-8")
    public String jusoPop(@ModelAttribute("evtrsvnMstVO") EvtrsvnMstVO evtrsvnMstVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        log.debug("call jusoPop()");

        commandMap.put("comcd", Config.COM_CD);
        evtrsvnMstVO.setDbEnckey(EgovProperties.getProperty("Globals.DbEncKey"));
        // ?????? ??????

        //// model.addAttribute("resultVO", evtrsvnService.selectEvtrsvnDetail(evtrsvnMstVO));
        return HttpUtility.getViewUrl(request);
    }

}
