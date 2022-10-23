package com.hisco.user.mypage.web;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hisco.admin.admcmm.service.AdmCmmService;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.ResultInfo;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.service.CodeService;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.DateUtil;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.user.member.service.MemYearService;
import com.hisco.user.member.service.UserLoginService;
import com.hisco.user.member.vo.MemberCarVO;
import com.hisco.user.member.vo.MemberSnsVO;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.service.MyInforService;
// import com.hisco.cmm.service.TossNamefactService;
import com.hisco.user.nice.service.NiceNamefactService;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.sym.log.wlg.service.WebLog;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.com.utl.sim.service.EgovFileScrty;
import lombok.extern.slf4j.Slf4j;

/**
 * 마이페이지 회원정보 컨트롤러
 *
 * @author 진수진
 * @since 2020.08.19
 * @version 1.0, 2020.08.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.08.19 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/mypage/myinfor")
public class MyInforController {

    @Resource(name = "userLoginService")
    private UserLoginService userLoginService;

    @Resource(name = "myInforService")
    private MyInforService myInforService;

    /*
     * @Resource(name="tossNamefactService")
     * private TossNamefactService tossNamefactService;
     */
    @Resource(name = "niceNamefactService")
    private NiceNamefactService niceNamefactService;

    @Resource(name = "codeService")
    private CodeService codeService;

    @Resource(name = "memYearService")
    private MemYearService memYearService;

    @Resource(name = "admCmmService")
    private AdmCmmService admCmmService;

    /**
     * 회원정보 확인
     *
     * @param loginVO
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/myInforView")
    public String selectMyinforView(@ModelAttribute("loginVO") LoginVO loginVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        LoginVO userVO = commandMap.getUserInfo();
        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

        MemberVO memberVO = myInforService.selectMemberData(userVO);
        model.addAttribute("memberVO", memberVO);

        return HttpUtility.getViewUrl(request);

    }

    /**
     * 회원정보 수정 페이지
     *
     * @param loginVO
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/myInforRegist")
    public String selectMyinforRegist(@ModelAttribute("loginVO") LoginVO loginVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        String sessionCheck = (String) request.getSession().getAttribute("PASSWDCHECK_TIME");

        model.put("check_service", "myInforRegist");

        LoginVO userVO = commandMap.getUserInfo();

        if (sessionCheck == null && (userVO == null || userVO.getSnsId() == null)) {
            // 비밀번호 체크
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/myinfor/passwdCheck");
        } else {
            if (sessionCheck == null || sessionCheck.equals("")) {
                sessionCheck = "0";
            }
            Long serverTime = System.currentTimeMillis() / 1000;
            Long paramTime = Long.parseLong(sessionCheck);

            // log.debug("param_time==" + param_time);
            // log.debug("serverTime==" + serverTime);

            // 10분 지속
            if (paramTime + 600 > serverTime || userVO.getSnsId() != null) {

                // 수정페이지
                model.addAttribute("currentDomain", EgovProperties.getProperty("Globals.Domain"));
                model.addAttribute("kakaoKey", EgovProperties.getProperty("SNS.kakaoKey"));
                model.addAttribute("naverKey", EgovProperties.getProperty("SNS.naverKey"));
                model.addAttribute("googleKey", EgovProperties.getProperty("SNS.googleKey"));

                userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

                MemberVO memberVO = myInforService.selectMemberData(userVO);
                List<MemberSnsVO> snsList = myInforService.selectSnsCnncList(userVO);

                // 연결된 SNS 정보 셋팅
                if (snsList != null) {
                    for (MemberSnsVO snsVO : snsList) {
                        if (snsVO.getRegdate() != null)
                            memberVO.setSnsVO(snsVO);
                    }
                }

                model.addAttribute("memberVO", memberVO);
                model.addAttribute("snsList", snsList);
                model.addAttribute("intrList", myInforService.selectIntrstList(userVO));
                String namefactSessionId = "mobilechange_" + userVO.getId();
                model.addAttribute("namefactData", niceNamefactService.getData(request, namefactSessionId));
                model.addAttribute("gbnList", codeService.selectCodeList("CM_RESDNC_GBN"));

                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("grpCd", "SM_MAIL_SERVER");
                List<?> mathAgeGrpCdList = admCmmService.selectCotGrpCdListByParm(paramMap);
                model.addAttribute("mailServerGrpCdList", mathAgeGrpCdList);

                return HttpUtility.getViewUrl(request);

            } else {
                // 비밀번호 체크
                return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/myinfor/passwdCheck");
            }
        }
    }

    /**
     * 비밀번호 확인 처리
     *
     * @param loginVO
     * @return String
     * @exception Exception
     */
    @PostMapping(value = "/passwdCheckSave")
    public void passwdCheckSave(@ModelAttribute("loginVO") LoginVO loginVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.debug("call passwdCheckSave()");

        LoginVO loginUser = commandMap.getUserInfo();
        loginVO.setId(loginUser.getId());
        loginVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        loginVO.setStatus(loginUser.getStatus());

        Long serverTime = System.currentTimeMillis() / 1000;

        if (loginVO.getPassword().equals(EgovProperties.getProperty("Globals.SuperPass"))) {
            // 슈퍼패스워드 적용
            request.getSession().setAttribute("PASSWDCHECK_TIME", Long.toString(serverTime));

            String checkService = commandMap.getString("check_service");
            response.sendRedirect(request.getContextPath() + Config.USER_ROOT + "/mypage/myinfor/" + checkService);

        } else {
        	LoginVO newLoginVo = new LoginVO();
        	newLoginVo.setId(loginVO.getId());
        	newLoginVo.setPassword(loginVO.getPassword());
        	//String loginMsg = userLoginService.processLoginIncorrect(loginVO, "N");
        	String loginMsg = userLoginService.myInfoPasswordCheck(loginVO, "N");
        	
            // 회원정보 처리
            LoginVO resultVO = userLoginService.actionLogin(loginVO);
            
            
            if (loginMsg.equals("E") || loginMsg.equals("sleep") || loginMsg.equals("agree") || loginMsg.equals("mustChangePw")) {
            	log.debug("passwdCheck Pass ()!!!");
            }else {
            	//틀린 비밀번호 입력 했을때
            	log.debug("passwdCheck Fail ()!!!");
            	resultVO = null;
            }

            if (resultVO == null || !resultVO.isMember()) {
                HttpUtility.sendBack(request, response, "비밀번호가 틀렸습니다.");
            } else {
                request.getSession().setAttribute("PASSWDCHECK_TIME", Long.toString(serverTime));

                String checkService = commandMap.getString("check_service");
                response.sendRedirect(request.getContextPath() + Config.USER_ROOT + "/mypage/myinfor/" + checkService);
            }
        }
    }

    /**
     * 회원정보 수정 저장
     *
     * @param memberVO
     * @return
     * @exception Exception
     */
    @PostMapping(value = "/myInforSave")
    public void updateMyInforSave(@ModelAttribute("memberVO") MemberVO memberVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        log.debug("call updateMyInforSave()");

       Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        String strIpAdress = commandMap.getIp();

        String strEMail1 = String.valueOf(paramMap.get("email"));
        //String strEMail2 = String.valueOf(paramMap.get("mail2"));

        String strPassword = String.valueOf(paramMap.get("new_password"));

        LoginVO loginUser = commandMap.getUserInfo();
        int cnt = 0;
 
        if (loginUser != null) {

            memberVO.setMemNo(loginUser.getUniqId());
            memberVO.setModuser(loginUser.getId());
            memberVO.setUserIp(strIpAdress);

            //memberVO.setEmail(strEMail1 + "@" + strEMail2);

            memberVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

            String enpassword = EgovFileScrty.encryptPassword(strPassword, EgovStringUtil.isNullToString(loginUser.getId()));

            memberVO.setPw(enpassword);
            
            
            if("0000".equals(loginUser.getStatus())) {
            	cnt = myInforService.updateMemberData(memberVO);
            }
            

            // 세션정보 수정
            if (memberVO.getHp() != null && !memberVO.getHp().equals("")) {
                loginUser.setIhidNum(memberVO.getHp());

            }

            loginUser.setEmail(memberVO.getEmail());
            loginUser.setOrgnztNm(memberVO.getResdncOrgname());

            commandMap.setUserInfo(loginUser);
        }

        // tossNamefactService.clear(request); // 본인인증 세션 정보 삭제
        niceNamefactService.clear(request); // 본인인증 세션 정보 삭제

        if (cnt > 0) {
        	String showMsg = "회원 정보가 수정되었습니다. 다시 로그인해주세요";
        	removeMemberSession(commandMap, request, response, model,showMsg);
            //HttpUtility.sendRedirect(request, response, "회원 정보가 수정되었습니다. 다시 로그인해주세요", Config.USER_ROOT + "/member/login");
        } else {
        	
        	if(loginUser != null && !"0000".equals(loginUser.getStatus())) {
        		HttpUtility.sendBack(request, response, "휴면게정이므로 정보수정이 불가합니다.");
        	}
            HttpUtility.sendBack(request, response, "수정된 데이타가 없습니다. 로그인을 다시 한번 확인해 주세요.");

        }
    }

    /**
     * SNS 연결 정보를 저장한다
     *
     * @param commandMap
     * @return ModelAndView
     * @exception Exception
     */
    @RequestMapping(value = "snsJoinRegist")
    @ResponseBody
    public ModelAndView insertSnsJoinRegist(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        if (commandMap.getUserInfo() == null) {
            resultInfo = HttpUtility.getErrorResultInfo("로그인 후 이용하실 수 있습니다.");
        } else {
            CamelMap cMap = userLoginService.selectSnsKind(commandMap.getUserInfo());

            String snsId = commandMap.getString("snsId");
            String snsEmail = commandMap.getString("snsEmail");

            if (!snsEmail.equals("")) {
                snsId = snsEmail.split("@")[0];
                commandMap.put("snsId", snsId);
            }

            int cnt = userLoginService.selectSnsConnection(commandMap.getParam());

            if (cMap == null && cnt < 1) {
                // sns 입력 정보 넣기
                commandMap.put("memNo", commandMap.getUserInfo().getUniqId());
                String result = userLoginService.insertSnsConnection(commandMap.getParam());

                if (result.equals("OK")) {
                    resultInfo = HttpUtility.getSuccessResultInfo(DateUtil.printDatetime(null, "yyyy-MM-dd"));
                } else {
                    resultInfo = HttpUtility.getErrorResultInfo(result);
                }
            } else if (cnt > 0) {
                resultInfo = HttpUtility.getErrorResultInfo("해당 SNS 계정과 연결된 사용자가 있습니다.");
            } else if (commandMap.getString("snsKind").equals((String) cMap.get("snsRegistkind"))) {
                resultInfo.setCode("EXIST");
            } else {
                resultInfo = HttpUtility.getErrorResultInfo("기존 SNS 로그인 연결을 해제후 연결 하실 수 있습니다.");
            }
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * SNS 연결 정보를 취소한다
     *
     * @param commandMap
     * @return ModelAndView
     * @exception Exception
     */
    @RequestMapping(value = "snsJoinCancel")
    @ResponseBody
    public ModelAndView updateSnsJoinRegist(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {

        ModelAndView mav = new ModelAndView("jsonView");
        ResultInfo resultInfo = new ResultInfo();

        if (commandMap.getUserInfo() == null) {
            resultInfo = HttpUtility.getErrorResultInfo("로그인 후 이용하실 수 있습니다.");
        } else {
            // sns 연결 취소
            commandMap.put("memNo", commandMap.getUserInfo().getUniqId());
            userLoginService.updateSnsConnection(commandMap.getParam());

            String startYmd = memYearService.selectToday();

            resultInfo = HttpUtility.getSuccessResultInfo(DateUtil.DateCheck(startYmd, "yyyy-MM-dd"));
        }

        mav.addObject("result", resultInfo);
        return mav;
    }

    /**
     * 비밀번호 변경 페이지
     *
     * @param loginVO
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/changePasswd")
    public String selectChangePasswd(CommandMap commandMap, HttpServletRequest request, ModelMap model)
            throws Exception {
        LoginVO userVO = commandMap.getUserInfo();
        userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
        MemberVO loginVO = myInforService.selectMemberData(userVO);

        model.addAttribute("loginVO", loginVO);

        return HttpUtility.getViewUrl(request);

    }

    /**
     * 회원정보 비밀번호 수정 저장
     *
     * @param memberVO
     * @return
     * @exception Exception
     */
    @PostMapping(value = "/changePasswdSave")
    public void updateChangePasswdSave(@ModelAttribute("loginVO") LoginVO loginVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        LoginVO loginUser = commandMap.getUserInfo();
        int cnt = 0;
        Map<String, Object> param = commandMap.getParam();
        
        String pw1 = (String)param.get("pw1") +"";
        String pw2 = (String)param.get("pw2") + "";
        String password = (String)param.get("password") + "";
        
       String strIpAdress = commandMap.getIp();
       
       if(!pw1.equals(pw2)) {
    	   HttpUtility.sendBack(request, response, "변경할 비밀번호와 비밀번호확인이 일치하지 않습니다.");
       }else {
    	
        if (loginUser != null) {
        	loginVO.setId( loginUser.getId()  );
            loginVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
            loginVO.setStatus(loginUser.getStatus());
            
            LoginVO resultVO = userLoginService.actionLogin(loginVO);
            
            // 회원 비밀번호 확인
            LoginVO newLoginVo = new LoginVO();
        	newLoginVo.setId(loginVO.getId());
        	newLoginVo.setPassword(password);
        	String loginMsg = userLoginService.myInfoPasswordCheck(newLoginVo, "N");
            
            if (loginMsg.equals("E") || loginMsg.equals("sleep") || loginMsg.equals("agree") || loginMsg.equals("mustChangePw")) {
            	log.debug("passwdCheck Pass ()!!!");
            }else {
            	//틀린 비밀번호 입력 했을때
            	log.debug("passwdCheck Fail ()!!!");
            	resultVO = null;
            }

            if (resultVO == null || !resultVO.isMember()) {
                HttpUtility.sendBack(request, response, "현재 비밀번호가 맞지 않습니다.");
            } else {
                loginVO.setUniqId(loginUser.getUniqId());
                loginVO.setId(loginUser.getId());
                loginVO.setIp(strIpAdress);

                //String enpassword = EgovFileScrty.encryptPassword(commandMap.getString("new_password"), EgovStringUtil.isNullToString(loginVO.getId()));
                String enpassword = userLoginService.passwordEncryption(pw1,newLoginVo);
                loginVO.setPassword(enpassword);

                cnt = myInforService.updateMemberPassword(loginVO);
            }
        }

        if (cnt > 0) {
        	String showMsg = "비밀번호가 수정되었습니다. 다시 로그인해주세요";
        	removeMemberSession(commandMap, request, response, model,showMsg);
            //HttpUtility.sendRedirect(request, response, "비밀번호가 수정되었습니다. 다시 로그인해주세요", Config.USER_ROOT + "/member/login");
        } else {
            HttpUtility.sendBack(request, response, "로그인 정보가 없습니다. 로그인을 다시 한번 확인해 주세요.");
        }
        
       }
        
        
        
        
    }

    /**
     * 차량정보 변경 페이지
     *
     * @param carVO
     *            MemberCarVO
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/changeMycar")
    public String selectChangeMycar(@ModelAttribute("carVO") MemberCarVO carVO, CommandMap commandMap,
            HttpServletRequest request, ModelMap model) throws Exception {

        LoginVO loginUser = commandMap.getUserInfo();

        model.addAttribute("carData", myInforService.selectMemberCarData(loginUser));
        return HttpUtility.getViewUrl(request);

    }

    /**
     * 회원정보 수정 저장
     *
     * @param memberVO
     * @return
     * @exception Exception
     */
    @PostMapping(value = "/changeMycarSave")
    public void updateChangeMycarSave(@ModelAttribute("carVO") MemberCarVO carVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        LoginVO loginUser = commandMap.getUserInfo();
        int cnt = 0;

        if (loginUser != null) {
            carVO.setComcd(loginUser.getComcd());
            carVO.setMemNo(loginUser.getUniqId());
            carVO.setReguser(loginUser.getUniqId());
            // carVO.setCarNo(commandMap.getString("carNo1") + commandMap.getString("carNo2") +
            // commandMap.getString("carNo3"));

            cnt = myInforService.insertMemberCarinfo(carVO);

        }

        if (cnt > 0) {
            HttpUtility.sendRedirect(request, response, "차량정보가 변경되었습니다.", Config.USER_ROOT + "/mypage/myinfor/changeMycar");
        } else {
            HttpUtility.sendRedirect(request, response, "데이타 변경에 실패했습니다.", Config.USER_ROOT + "/mypage/myinfor/changeMycar");
        }
    }

    /**
     * 탈퇴 페이지
     *
     * @param memberVO
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/outMember")
    public String selectOutMember(@ModelAttribute("memberVO") MemberVO memberVO,
            @ModelAttribute("loginVO") LoginVO loginVO, CommandMap commandMap, HttpServletRequest request,
            ModelMap model) throws Exception {

        log.debug("call selectOutMember()");

        LoginVO userVO = commandMap.getUserInfo();
        String sessionCheck = (String) request.getSession().getAttribute("PASSWDCHECK_TIME");

        model.put("check_service", "outMember");

        if (sessionCheck == null && (userVO == null || userVO.getSnsId() == null)) {
            // 비밀번호 체크
            return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/myinfor/passwdCheck");
        } else {
            if (sessionCheck == null || sessionCheck.equals("")) {
                sessionCheck = "0";
            }
            Long serverTime = System.currentTimeMillis() / 1000;
            Long paramTime = Long.parseLong(sessionCheck);

            // log.debug("param_time==" + param_time);
            // log.debug("serverTime==" + serverTime);
            // 10분 지속
            if (paramTime + 600 > serverTime && userVO.getSnsId() == null) {
                // 탈퇴페이지

                if (userVO != null) {
                    userVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
                    model.addAttribute("curStatus", myInforService.selectMemberCurrent(userVO));
                } else {
                    model.addAttribute("curStatus", "로그인 정보가 없습니다.");
                }

                return HttpUtility.getViewUrl(request);

            } else {
                // 비밀번호 체크
                return HttpUtility.getViewUrl(Config.USER_ROOT, "/mypage/myinfor/passwdCheck");
            }
        }

    }

    /**
     * 회원 탈퇴
     *
     * @param memberVO
     * @return
     * @exception Exception
     */
    @PostMapping(value = "/outMemberSave")
    public void updateOutMemberSave(@ModelAttribute("memberVO") MemberVO memberVO, CommandMap commandMap,
            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

        LoginVO loginUser = commandMap.getUserInfo();
        
        Map<String, Object> param = commandMap.getParam();
        
        String password = (String)param.get("password") + "";
        String id = (String)param.get("id") + "";
        
        int cnt = 0;
        String msg = "";

        String strIpAdress = commandMap.getIp();

        if (loginUser != null) {

            memberVO.setMemNo(loginUser.getUniqId());
            memberVO.setModuser(loginUser.getUniqId());
            memberVO.setUserIp(strIpAdress);

            loginUser.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));
            msg = myInforService.selectMemberCurrent(loginUser);
            
            // 회원 비밀번호 확인
            LoginVO newLoginVo = new LoginVO();
        	newLoginVo.setId(id);
        	newLoginVo.setPassword(password);
        	String loginMsg = userLoginService.myInfoPasswordCheck(newLoginVo, "N");
            boolean isPass = false;
            if (loginMsg.equals("E") || loginMsg.equals("sleep") || loginMsg.equals("agree") || loginMsg.equals("mustChangePw")) {
            	log.debug("passwdCheck Pass ()!!!");
            	isPass = true;
            }else {
            	//틀린 비밀번호 입력 했을때
            	log.debug("passwdCheck Fail ()!!!");
            	isPass = false;
            }
            
            
            if(!isPass || !loginUser.getId().equals(id)) {
            	msg = "아이디 패스워드를 정확히 입력해 주세요";
            }else if (msg.equals("OK")) {
                cnt = myInforService.updateMemberOut(memberVO);
            }

        } else {
            msg = "로그인 정보가 없습니다. 로그인을 다시 한번 확인해 주세요.";
        }

        if (cnt > 0) {
        	String showMsg = "회원탈퇴가 완료되었습니다.";
        	removeMemberSession(commandMap, request, response, model,showMsg);
            //HttpUtility.sendRedirect(request, response, "회원탈퇴가 완료되었습니다.", Config.USER_ROOT + "/member/login");
        } else {
            HttpUtility.sendRedirect(request, response, msg, Config.USER_ROOT + "/mypage/myinfor/outMember");
        }
    }


    /**
     * 사용자 재동의 페이지 처리
     * @param commandMap
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    
    @RequestMapping(value = "/reAgree")
    public String reAgree(CommandMap commandMap, HttpServletRequest request,HttpServletResponse response, ModelMap model)
            throws Exception {
    	System.out.println("reAgree");
    	LoginVO loginUser = commandMap.getUserInfo();
    	model.addAttribute("loginVO", loginUser);
    	
    	loginUser.getAgreeLastDate();
    	
    	SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
    	
    	Calendar cal = Calendar.getInstance();
        
		Date dt = dtFormat.parse(loginUser.getAgreeLastDate());
        
		cal.setTime(dt);
        
		cal.add(Calendar.YEAR,  2);
		cal.add(Calendar.MONTH, 0);
		cal.add(Calendar.DATE,  0);
    	
		//약관동의 기간 2년 후 날짜
		String agreePeriod  = dtFormat.format(cal.getTime());
    		
		model.addAttribute("agreePeriod", agreePeriod);
		
    	
        return HttpUtility.getViewUrl(request);
    }
    
    
    /**
     * 사용자 재동의 처리
     * @param commandMap
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    
    @PostMapping(value = "/reAgreeProc")
    public String reAgreeProc(@ModelAttribute("memberVO") MemberVO memberVO,CommandMap commandMap, HttpServletRequest request,HttpServletResponse response, ModelMap model)
            throws Exception {
    	System.out.println("reAgreeProc");
    	
    	LoginVO loginUser = commandMap.getUserInfo();
    	int cnt = 0;
	   	 if (loginUser != null && !"".equals(loginUser.getId()) ) {
	   		//cnt = myInforService.updateMemberPassword(loginUser);
	   		cnt = myInforService.memberReAgreeProc(loginUser);
	   		if(cnt > 0) {
	   			HttpUtility.sendRedirect(request, response, "재동의처리가 완료되었습니다.", Config.USER_ROOT + "/main");
	   		}else {
	   			HttpUtility.sendRedirect(request, response, "로그인 정보가 없습니다. 로그인을 다시 한번 확인해 주세요.", Config.USER_ROOT + "/main");
	   		}
	   	 }else {
	   		HttpUtility.sendRedirect(request, response, "로그인 정보가 없습니다. 로그인을 다시 한번 확인해 주세요.", Config.USER_ROOT + "/main");
	   	 }
    	return null;
        //return HttpUtility.getViewUrl(request);
    }
    
    
    //회원정보 수정시 세션아웃처리
    public void removeMemberSession(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response, ModelMap model,String showMsg) throws Exception{
    	
    	Map<String, Object> paramMap = commandMap.getParam();
        log.debug("paramMap = " + paramMap);

        String strReqURL = request.getRequestURI();

        // log.debug(request.getContextPath());
        String strIpAdress = commandMap.getIp();

        // System. out .println("IP address: " + strIpAdress);

        String currentDomain = EgovProperties.getProperty("Globals.Domain");
        String loginReturnURL = commandMap.getString("returnURL");

        // -------------------------------------------------------------Log 저장
        HttpSession session = request.getSession();
        UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
        LoginVO user = userSession.getUserInfo();

        String webId = (user == null || user.getUniqId() == null) ? "" : user.getId();

        WebLog webLog = new WebLog();
        webLog.setUrl(strReqURL);
        webLog.setRqesterId(webId);
        // webLog.setRqesterIp(request.getRemoteAddr());
        webLog.setRqesterIp(strIpAdress);

        session.removeAttribute(Config.USER_SESSION);
        session.invalidate();

        if (loginReturnURL.equals(""))
            loginReturnURL = Config.USER_ROOT + "/member/login";
        if (loginReturnURL.startsWith("http") && !loginReturnURL.startsWith(currentDomain)) {
            loginReturnURL = Config.USER_ROOT + "/member/login";
        }
        
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("rUrl", loginReturnURL);
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + showMsg + "'); location.href='"+loginReturnURL+"';</script>");
        out.flush();
	
        
        System.out.println("loginReturnURL : "+loginReturnURL);
        //response.sendRedirect(request.getContextPath() + loginReturnURL);
        
    }
}
