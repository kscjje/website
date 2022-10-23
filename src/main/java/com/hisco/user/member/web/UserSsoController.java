package com.hisco.user.member.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hisco.cmm.object.CommandMap;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.service.RestApiClient;
import com.hisco.cmm.util.Config;
import com.hisco.cmm.util.HttpUtility;
import com.hisco.cmm.util.StringUtil;
import com.hisco.cmm.util.WebEncDecUtil;
import com.hisco.user.member.service.NowonEncryptUtil;
import com.hisco.user.member.service.UserJoinService;
import com.hisco.user.member.service.UserLoginService;
import com.hisco.user.member.vo.MemberNowon;
import com.hisco.user.member.vo.MemberVO;
import com.hisco.user.mypage.service.MyInforService;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * SSO 연동 처리 컨트롤러
 *
 * @author 진수진
 * @since 2021.12.28
 * @version 1.0, 2021.12.28
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2021.12.28 최초작성
 */
@Slf4j
@Controller
@RequestMapping(value = Config.USER_ROOT + "/member/sso/")
public class UserSsoController {

    @Autowired
    RestApiClient rest;

    private String encodeKey = "ssologin";

    @Resource(name = "myInforService")
    private MyInforService myInforService;

    @Resource(name = "userJoinService")
    private UserJoinService userJoinService;

    @Resource(name = "userLoginService")
    private UserLoginService userLoginService;

    /**
     * SSO 로그인
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/loginAction")
    public String loginAction(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {

        NowonEncryptUtil aes256De = new NowonEncryptUtil();

        String returnURL = commandMap.getString("returnURL");

        String loginResultCode = commandMap.getString("loginResultCode");
        String userId = aes256De.decrypt(commandMap.getString("userId"));
        String userName = aes256De.decrypt(commandMap.getString("userName"));

        String email = aes256De.decrypt(commandMap.getString("email"));
        String telNo = aes256De.decrypt(commandMap.getString("telNo"));
        String mobileNo = aes256De.decrypt(commandMap.getString("mobileNo"));
        String zip = aes256De.decrypt(commandMap.getString("zip"));
        String address = aes256De.decrypt(commandMap.getString("address"));
        String detailAddress = aes256De.decrypt(commandMap.getString("detailAddress"));
        String birthDay = aes256De.decrypt(commandMap.getString("birthDay"));
        String sexCode = aes256De.decrypt(commandMap.getString("sexCode"));
        String dupInfo = aes256De.decrypt(commandMap.getString("dupInfo"));
        String loginType = aes256De.decrypt(commandMap.getString("loginType"));
        String status = aes256De.decrypt(commandMap.getString("status"));

        if(StringUtil.IsEmpty(dupInfo)){
        	dupInfo = aes256De.decrypt(commandMap.getString("duplinfo"));
        }

        // 로그인 성공
        MemberVO param = new MemberVO();
        param.setId(userId);
        param.setMemNm(userName);
        param.setGender("1001".equals(sexCode)
                ? "M" : (("1002".equals(sexCode) ? "F" : "0")));

        param.setAddr1(address);
        param.setAddr2(detailAddress);
        param.setPostNum(zip);

        param.setEmail(email);
        param.setUserIp(commandMap.getIp());

        param.setHp(mobileNo.replaceAll("-", ""));
        param.setBirthDate(birthDay.replaceAll("-", ""));
        param.setPiAuthkey(dupInfo);
        param.setPiAuthtype(loginType.equals("mobile") ? "3001" : "2001");



        LoginVO vo = new LoginVO();
        String inserErrorMsg = "";

        vo.setId(userId);
        vo.setIp(commandMap.getIp());
        vo.setSnsKind("SSO");


        if (loginResultCode.equals("OK") && status.equals("01")) {
            String dupcheckKey = param.getMemNm() + param.getBirthDate() + param.getHp();

            if (param.getBirthDate().length() > 4)
                param.setBirthMmdd(param.getBirthDate().substring(4));

            String ssn = param.getBirthDate().substring(2) + (param.getGender().equals("M") ? "1" : "2");
            if (param.getBirthDate().startsWith("2")) {
                ssn = param.getBirthDate().substring(2) + (param.getGender().equals("F") ? "3" : "4");
            }
            param.setSsn(ssn);
            param.setDupcheckKey(dupcheckKey);
            param.setReguser("SSO");
            param.setModuser("SSO");

            // 아이디 체크
            MemberVO memberVO = userJoinService.selectMemberDetail(param);

            // 아이디가 존재하지 않으면
            if (memberVO == null || StringUtil.IsEmpty(memberVO.getMemNo())) {
                // 이름/핸드폰/생년월일 체크
                MemberVO memberVO2 = userJoinService.selectMemberDetailOff(param);

                if (memberVO2 != null && !StringUtil.IsEmpty(memberVO2.getMemNo()) && StringUtil.IsEmpty(memberVO2.getId())) {
                    // 오프라인 가입자가 있으면 아이디 업데이트
                    String gender = param.getGender();
                    if (gender.equals("M")) {
                        param.setGender("1");
                    } else if (gender.equals("F")) {
                        param.setGender("2");
                    }
                    param.setMemNo(memberVO2.getMemNo());
                    myInforService.updateMemberData(param);
                } else {
                    // 신규 가입 시킨다
                    Map<String, Object> resultMap = userJoinService.insertMemberInfo(param);
                    if (!String.valueOf(resultMap.get("result")).equals(Config.SUCCESS)) {
                        log.error("회원가입 실패");
                        log.error("USERID : " + param.getId());
                        log.error("ERRORMSG : " + resultMap.get("resultMsg"));

                        inserErrorMsg = String.valueOf(resultMap.get("resultMsg"));
                    }
                }
            } else {
                // 이미 등록된 회원정보가 있음
            	/*
                String gender = param.getGender();
                if (gender.equals("M")) {
                    param.setGender("1");
                } else if (gender.equals("F")) {
                    param.setGender("2");
                }
                param.setMemNo(memberVO.getMemNo());
                myInforService.updateMemberData(param);
                */
            }

            // 아이디 다시 체크
            LoginVO loginVO = new LoginVO();
            loginVO.setId(param.getId());
            loginVO.setSecretMode("Y"); // 패스워드 체크 안함
            loginVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

            LoginVO resultVO = userLoginService.actionLogin(loginVO);

            String msg = "로그인 실패";
            if (!StringUtil.IsEmpty(inserErrorMsg)) {
                msg += "(" + inserErrorMsg + ")";
            }

            if (resultVO == null || StringUtil.IsEmpty(resultVO.getUniqId())) {
            	// 에러로그 저장
            	userLoginService.insertLoginLog(vo, "ERR" , msg);

                HttpUtility.sendRedirect(request, response, msg, Config.USER_ROOT + "/main");
            } else {
                // 로그인 세션 생성
                HttpSession session = request.getSession();
                UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
                userSession.setUserInfo(resultVO);
                session.setAttribute(Config.USER_SESSION, userSession);

                // 정상  로그 저장
                vo.setUniqId(resultVO.getUniqId());
                userLoginService.insertLoginLog(vo, "OK" , "");

                if(returnURL.equals("") || !returnURL.startsWith(Config.USER_ROOT + "/") ){
               	 HttpUtility.sendRedirect(request, response, "", Config.USER_ROOT + "/main");
               }else{
               	 HttpUtility.sendRedirect(request, response, "", returnURL);
               }

            }

        } else if (loginResultCode.equals("OK") && status.equals("02")) {
        	// 탈퇴 로그 저장
        	userLoginService.insertLoginLog(vo,  loginResultCode , "탈퇴아이디");
        	// 탈퇴
            // 아이디 체크
            MemberVO memberVO = userJoinService.selectMemberDetail(param);
            memberVO.setModuser("SSO");
            memberVO.setUserIp(commandMap.getIp());

            if (memberVO != null && !StringUtil.IsEmpty(memberVO.getMemNo())) {
                int cnt = myInforService.updateMemberOut(memberVO);

                if (cnt > 0) {
                    HttpSession session = request.getSession();
                    session.removeAttribute(Config.USER_SESSION);
                    session.invalidate();
                }
            }

            if(returnURL.equals("") || !returnURL.startsWith(Config.USER_ROOT + "/") ){
            	 HttpUtility.sendRedirect(request, response, "로그인 실패(탈퇴아이디)", Config.USER_ROOT + "/main");
            }else{
            	 HttpUtility.sendRedirect(request, response, "로그인 실패(탈퇴아이디)", returnURL);
            }



        } else {
        	userLoginService.insertLoginLog(vo, loginResultCode , "SSO 로그인 실패");

            HttpUtility.sendRedirect(request, response, "로그인에 실패하였습니다.", Config.USER_ROOT + "/main");
        }

        return null;

    }

    /**
     * SSO 로그인
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login")
    public String ssoLogin(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {

        JSONObject resultAPIJson = new JSONObject();
        MemberNowon memberNown = new MemberNowon();
        MemberNowon memberNownResult = new MemberNowon();

        // String encStrVal = WebEncDecUtil.fn_encrypt("quency", encodeKey);
        // System.out.println("===================encStrVal:" + URLEncoder.encode(encStrVal, "UTF-8"));

        memberNown.setUserId(WebEncDecUtil.fn_decrypt(commandMap.getString("encodeUserid"), encodeKey));
        memberNown.setUserPassword("tnwls3327!");

        resultAPIJson = rest.callRestAPI("https://www.nowon.kr/external/sso/lll_sso_login.do", memberNown);

        memberNownResult = (MemberNowon) JSONObject.toBean(resultAPIJson, MemberNowon.class);

        if ("ok".equals(memberNownResult.getLoginResultCode())) {
            // 로그인 성공
            MemberVO param = new MemberVO();
            param.setId(memberNownResult.getUserId());
            param.setMemNm(memberNownResult.getUserName());
            param.setGender("1001".equals(memberNownResult.getSexCode())
                    ? "M" : (("1002".equals(memberNownResult.getSexCode()) ? "F" : "0")));

            param.setAddr1(memberNownResult.getAddress());
            param.setAddr2(memberNownResult.getDetailAddress());
            param.setPostNum(memberNownResult.getZip());

            param.setEmail(memberNownResult.getEmail());
            param.setUserIp(commandMap.getIp());

            param.setHp(memberNownResult.getMobileNo() == null
                    ? "" : memberNownResult.getMobileNo().replaceAll("-", ""));
            param.setBirthDate(memberNownResult.getBirthDay() == null
                    ? "" : memberNownResult.getBirthDay().replaceAll("-", ""));
            String dupcheckKey = param.getMemNm() + param.getBirthDate() + param.getHp();

            // 본인인증 키
            // memberVO.getPiAuthkey()

            param.setPw(EgovProperties.getProperty("Globals.SuperPass"));

            if (param.getBirthDate().length() > 4)
                param.setBirthMmdd(param.getBirthDate().substring(4));

            String ssn = param.getBirthDate().substring(2) + (param.getGender().equals("M") ? "1" : "2");
            if (param.getBirthDate().startsWith("2")) {
                ssn = param.getBirthDate().substring(2) + (param.getGender().equals("F") ? "3" : "4");
            }
            param.setSsn(ssn);
            param.setDupcheckKey(dupcheckKey);
            param.setReguser("SSO");
            param.setModuser("SSO");

            // 아이디 체크
            MemberVO memberVO = userJoinService.selectMemberDetail(param);

            // 아이디가 존재하지 않으면
            if (memberVO == null || StringUtil.IsEmpty(memberVO.getMemNo())) {
                // 이름/핸드폰/생년월일 체크
                MemberVO memberVO2 = userJoinService.selectMemberDetailOff(param);

                if (memberVO2 != null && !StringUtil.IsEmpty(memberVO2.getMemNo()) && StringUtil.IsEmpty(memberVO2.getId())) {
                    // 오프라인 가입자가 있으면 아이디 업데이트
                    String gender = param.getGender();
                    if (gender.equals("M")) {
                        param.setGender("1");
                    } else if (gender.equals("F")) {
                        param.setGender("2");
                    }
                    param.setMemNo(memberVO2.getMemNo());
                    myInforService.updateMemberData(param);
                } else {
                    // 신규 가입 시킨다
                    Map<String, Object> resultMap = userJoinService.insertMemberInfo(param);
                    if (!String.valueOf(resultMap.get("result")).equals(Config.SUCCESS)) {
                        log.error("회원가입 실패");
                        log.error("USERID : " + param.getId());
                        log.error("ERRORMSG : " + resultMap.get("resultMsg"));
                    }
                }
            } else {
                // 이미 등록된 회원정보가 있음
                String gender = param.getGender();
                if (gender.equals("M")) {
                    param.setGender("1");
                } else if (gender.equals("F")) {
                    param.setGender("2");
                }
                param.setMemNo(memberVO.getMemNo());
                myInforService.updateMemberData(param);
            }

            // 아이디 다시 체크
            LoginVO loginVO = new LoginVO();
            loginVO.setId(param.getId());
            loginVO.setSecretMode("Y"); // 패스워드 체크 안함
            loginVO.setDbEncKey(EgovProperties.getProperty("Globals.DbEncKey"));

            LoginVO resultVO = userLoginService.actionLogin(loginVO);

            if (resultVO == null || StringUtil.IsEmpty(resultVO.getUniqId())) {
                HttpUtility.sendRedirect(request, response, "로그인에 실패하였습니다.", request.getContextPath() + Config.USER_ROOT + "/main");
            } else {
                // 로그인 세션 생성
                HttpSession session = request.getSession();
                UserSession userSession = (UserSession) session.getAttribute(Config.USER_SESSION);
                userSession.setUserInfo(resultVO);
                session.setAttribute(Config.USER_SESSION, userSession);

                HttpUtility.sendRedirect(request, response, "로그인 되었습니다.", request.getContextPath() + Config.USER_ROOT + "/main");

            }

        } else {
            HttpUtility.sendRedirect(request, response, "로그인에 실패하였습니다.", request.getContextPath() + Config.USER_ROOT + "/main");
        }

        model.addAttribute("memberNown", memberNownResult);

        return HttpUtility.getViewUrl(request);
    }

    /**
     * SSO 회원정보 업데이트
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update.json")
    public String ssoUpdate(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {

    	NowonEncryptUtil aes256De = new NowonEncryptUtil();

        //String userId = aes256De.decrypt(commandMap.getString("userId"));
        String userName = aes256De.decrypt(commandMap.getString("userName"));
        String email = aes256De.decrypt(commandMap.getString("email"));
        String telNo = aes256De.decrypt(commandMap.getString("telNo"));
        String mobileNo = aes256De.decrypt(commandMap.getString("mobileNo"));
        String zip = aes256De.decrypt(commandMap.getString("zip"));
        String address = aes256De.decrypt(commandMap.getString("address"));
        String detailAddress = aes256De.decrypt(commandMap.getString("detailAddress"));
        String birthDay = aes256De.decrypt(commandMap.getString("birthDay"));
        String sexCode = aes256De.decrypt(commandMap.getString("sexCode"));
        String dupInfo = aes256De.decrypt(commandMap.getString("dupInfo"));
        String status = aes256De.decrypt(commandMap.getString("status"));
        String user_gubun = aes256De.decrypt(commandMap.getString("user_gubun"));

        if(StringUtil.IsEmpty(dupInfo)){
        	dupInfo = aes256De.decrypt(commandMap.getString("duplinfo"));
        }


        if(dupInfo.equals("")){
        	model.addAttribute("result", "IDEMPTY");

        }else if(user_gubun.equals("add") || status.equals("02")){
        	// 신규 또는 탈퇴 는 처리안함
        	model.addAttribute("result", "INVALID");
        }else{

            // 로그인 성공
            MemberVO param = new MemberVO();
            //param.setId(userId);
            param.setMemNm(userName);
            param.setEmail(email);

           // param.setGender("1001".equals(sexCode)? "M" : (("1002".equals(sexCode) ? "F" : "0")));

            param.setAddr1(address);
            param.setAddr2(detailAddress);
            param.setPostNum(zip);
            param.setHp(mobileNo.replaceAll("-", ""));
            //param.setBirthDate(birthDay.replaceAll("-", ""));
            param.setPiAuthkey(dupInfo);
            //param.setPiAuthtype(loginType.equals("mobile") ? "3001" : "2001");



            //본인인증 키로 회원정보 체크
            MemberVO memberVO = userJoinService.selectMemberByAuthkey(param);

            if (memberVO != null && !StringUtil.IsEmpty(memberVO.getMemNo())) {
                param.setMemNo(memberVO.getMemNo());
                param.setModuser("SSO");
                param.setReguser("SSO");
                param.setUserIp(commandMap.getIp());

                myInforService.updateMemberData(param);

                model.addAttribute("result", "OK");
            } else {
                model.addAttribute("result", "NOTEXIST");
            }
        }


        return HttpUtility.getViewUrl(request);
    }

    /**
     * SSO 회원 탈퇴
     *
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/outMember.json")
    public String ssoOutmember(CommandMap commandMap, HttpServletRequest request, HttpServletResponse response,
            ModelMap model) throws Exception {

    	NowonEncryptUtil aes256De = new NowonEncryptUtil();

    	String dupInfo = aes256De.decrypt(commandMap.getString("dupInfo"));
        String userId = aes256De.decrypt(commandMap.getString("userId"));
        String status = aes256De.decrypt(commandMap.getString("status"));
        String user_gubun = aes256De.decrypt(commandMap.getString("user_gubun"));

        if(StringUtil.IsEmpty(dupInfo)){
        	dupInfo = aes256De.decrypt(commandMap.getString("duplinfo"));
        }

        LoginVO vo = new LoginVO();
        vo.setId(userId);
        vo.setIp(commandMap.getIp());
        vo.setSnsKind("SSO");

        // 로그인 성공
        MemberVO param = new MemberVO();
        param.setId(userId);
        param.setPiAuthkey(dupInfo);

        if(dupInfo.equals("")){
        	model.addAttribute("result", "IDEMPTY");
        }else if(status.equals("02") && user_gubun.equals("modify")){
	        // 탈퇴 로그 저장
	    	//userLoginService.insertLoginLog(vo,  "OUT" , "탈퇴처리");

	    	//본인인증 키로 회원정보 체크
            MemberVO memberVO = userJoinService.selectMemberByAuthkey(param);
	        memberVO.setModuser("SSO");
	        memberVO.setUserIp(commandMap.getIp());

	        if (memberVO != null && !StringUtil.IsEmpty(memberVO.getMemNo())) {
	            int cnt = myInforService.updateMemberOut(memberVO);

	            model.addAttribute("result", "OK");

	        } else {
	               model.addAttribute("result", "NOTEXIST");
	        }
        }else{
        	model.addAttribute("result", "INVALID");
        }

        return HttpUtility.getViewUrl(request);
    }

}
