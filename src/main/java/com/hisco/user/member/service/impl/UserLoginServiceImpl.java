package com.hisco.user.member.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.util.CommonUtil;
import com.hisco.cmm.util.Config;
import com.hisco.user.member.service.UserLoginService;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.sym.log.clg.service.LoginLog;
import egovframework.com.sym.log.clg.service.impl.LoginLogDAO;
import egovframework.com.utl.fcc.service.EgovNumberUtil;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 일반사용자 로그인 처리
 *
 * @author 전영석
 * @since 2020.08.05
 * @version 1.0, 2020.08.05
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2020.08.05 최초작성
 */
@Slf4j
@Service("userLoginService")
public class UserLoginServiceImpl extends EgovAbstractServiceImpl implements UserLoginService {

    @Resource(name = "userLoginDAO")
    private UserLoginDAO loginDAO;

    @Resource(name = "loginLogDAO")
    private LoginLogDAO loginLogDAO;

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    /**
     * 일반 로그인을 처리한다
     *
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    @Override
    public LoginVO actionLogin(LoginVO vo) throws Exception {

        // 1. 입력한 비밀번호를 암호화한다.
        String enpassword = EgovFileScrty.encryptPassword(vo.getPassword(), vo.getId());

        // log.debug("enpassword =>" + enpassword);
        // String enpassword = EgovFileScrty.getSHA512(vo.getPassword());
        vo.setPassword(enpassword);

        // 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
        LoginVO loginVO = loginDAO.actionLogin(vo);

        // 3. 결과를 리턴한다.
        if (loginVO != null && !CommonUtil.getString(loginVO.getUniqId()).equals("")) {
            return loginVO;
        } else {
            loginVO = new LoginVO();
        }

        return loginVO;
    }

    /**
     * 아이디를 찾는다.
     *
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    @Override
    public LoginVO searchId(LoginVO vo) throws Exception {

        // 1. 이름, 이메일주소가 DB와 일치하는 사용자 ID를 조회한다.
        LoginVO loginVO = loginDAO.searchId(vo);

        // 2. 결과를 리턴한다.
        if (loginVO != null && !loginVO.getId().equals("")) {
            return loginVO;
        } else {
            loginVO = new LoginVO();
        }

        return loginVO;
    }

    /**
     * 비밀번호를 찾는다.
     *
     * @param vo
     *            LoginVO
     * @return boolean
     * @exception Exception
     */
    @Override
    public boolean searchPassword(LoginVO vo) throws Exception {

        boolean result = true;

        // 1. 아이디, 이름, 이메일주소, 비밀번호 힌트, 비밀번호 정답이 DB와 일치하는 사용자 Password를 조회한다.
        LoginVO loginVO = loginDAO.searchPassword(vo);
        if (loginVO == null || loginVO.getPassword() == null || "".equals(loginVO.getPassword())) {
            return false;
        }

        // 2. 임시 비밀번호를 생성한다.(영+영+숫+영+영+숫+영+영=8자리)
        String newpassword = "";
        for (int i = 1; i <= 8; i++) {
            // 영자
            if (i % 3 != 0) {
                newpassword += EgovStringUtil.getRandomStr('a', 'z');
                // 숫자
            } else {
                newpassword += EgovNumberUtil.getRandomNum(0, 9);
            }
        }

        // 3. 임시 비밀번호를 암호화하여 DB에 저장한다.
        LoginVO pwVO = new LoginVO();
        String enpassword = EgovFileScrty.encryptPassword(newpassword, vo.getId());
        pwVO.setId(vo.getId());
        pwVO.setPassword(enpassword);
        pwVO.setUserSe(vo.getUserSe());
        loginDAO.updatePassword(pwVO);

        // 4. 임시 비밀번호를 이메일 발송한다.(메일연동솔루션 활용)
        /*
         * SndngMailVO sndngMailVO = new SndngMailVO();
         * sndngMailVO.setDsptchPerson("webmaster");
         * sndngMailVO.setRecptnPerson(vo.getEmail());
         * sndngMailVO.setSj("[MOIS] 임시 비밀번호를 발송했습니다.");
         * sndngMailVO.setEmailCn("고객님의 임시 비밀번호는 " + newpassword + " 입니다.");
         * sndngMailVO.setAtchFileId("");
         * result = sndngMailRegistService.insertSndngMail(sndngMailVO);
         */

        return result;
    }

    /**
     * 로그인인증제한을 조회한다.
     *
     * @param vo
     *            LoginVO
     * @return boolean
     * @exception Exception
     */
    public Map<?, ?> selectLoginIncorrect(LoginVO vo) throws Exception {

        Map<?, ?> returnVO = loginDAO.selectLoginIncorrect(vo);

        return returnVO;
    }

    /**
     * 로그인인증제한을 조회한다.
     *
     * @param vo
     *            LoginVO
     * @return boolean
     * @exception Exception
     */
    public Map<?, ?> selectSnsLoginIncorrect(LoginVO vo) throws Exception {

        Map<?, ?> returnVO = loginDAO.selectSnsLoginIncorrect(vo);

        return returnVO;
    }

    /**
     * 로그인 로그를 저장한다
     *
     * @param vo
     *            LoginVO
     * @return boolean
     * @exception Exception
     */
    public void insertLoginLog(LoginVO vo, String errorCode , String errorMsg) throws Exception {
        // 로그인 로그 저장
        LoginLog loinLog = new LoginLog();
        loinLog.setLoginMthd("I");
        loinLog.setLoginId((vo.getSnsId() != null && !vo.getSnsId().equals("")) ? vo.getSnsId() : vo.getId());
        loinLog.setLoginIp(vo.getIp());
        loinLog.setErrOccrrAt(errorCode.equals("OK") ? "N" : "Y");
        loinLog.setErrorCode(errorCode.equals("OK") ? "" : errorCode);
        loinLog.setConnctAppgbn("2001"); // 사용자 구분코드
        loinLog.setErrorMsg(errorMsg);
        loinLog.setMemNo(vo.getUniqId());
        loinLog.setSnsKind(vo.getSnsKind());

        loginLogDAO.logInsertLoginLog(loinLog);
    }

    /**
     * 로그인 로그를 저장한다
     *
     * @param vo
     *            LoginVO
     * @return boolean
     * @exception Exception
     */
    public void insertLoginLog(LoginVO vo, String errorCode) throws Exception {
        // 로그인 로그 저장
        LoginLog loinLog = new LoginLog();
        loinLog.setLoginMthd("I");
        loinLog.setLoginId((vo.getSnsId() != null && !vo.getSnsId().equals("")) ? vo.getSnsId() : vo.getId());
        loinLog.setLoginIp(vo.getIp());
        loinLog.setErrOccrrAt(errorCode.equals("E") ? "N" : "Y");
        loinLog.setErrorCode(errorCode.equals("E") ? "" : "");
        loinLog.setConnctAppgbn("2001"); // 사용자 구분코드
        loinLog.setMemNo(vo.getUniqId());

        loginLogDAO.logInsertLoginLog(loinLog);
    }

    /**
     * 로그인인증제한을 처리한다.
     *
     * @param vo
     *            LoginVO
     * @return boolean
     * @exception Exception
     */
    public String processLoginIncorrect(LoginVO vo, String snsYn) throws Exception {

        String sRtnCode = "C";

        Map<String, String> mapParam = new HashMap<String, String>();
        mapParam.put("comcd", Config.COM_CD);

        int intLockCountConfig = loginDAO.selectLockCntConfig(mapParam);
        int intPasswdChangeConfig = loginDAO.selectPasswdChangeConfig(mapParam);

        log.debug("intLockCountConfig = " + intLockCountConfig);
        log.debug("intPasswdChangeConfig = " + intPasswdChangeConfig);

        Map<?, ?> mapLockUserInfo = null;
        if (snsYn.equals("Y")) { // 소셜계정 로그인 인 경우
            String snsEmail = vo.getSnsEmail();
            if (snsEmail != null && !snsEmail.equals("")) {
                vo.setSnsId(snsEmail.split("@")[0]);
            }
            mapLockUserInfo = loginDAO.selectSnsLoginIncorrect(vo);
            if (mapLockUserInfo != null) {
                vo.setId(CommonUtil.getString(mapLockUserInfo.get("userId")));
            }
        } else {
            mapLockUserInfo = loginDAO.selectLoginIncorrect(vo);
            if (mapLockUserInfo == null) {
                // 휴면계정 조회
                mapLockUserInfo = (Map<?, ?>) commonDAO.queryForObject("UserLoginDAO.selectLoginIncorrect2", vo);
            }
        }

        
        
        
        
        
        
        
        
        
        //String enpassword = EgovFileScrty.encryptPassword(vo.getPassword(), EgovStringUtil.isNullToString(vo.getId()));

        // log.debug("===============enpassword=>" + enpassword);
        // String enpassword = EgovFileScrty.getSHA512(vo.getPassword());

        /*
         * log.debug("-----------------------------------------------------------------");
         * enpassword = String.valueOf(mapLockUserInfo.get("userPw"));
         * log.debug("Member All Pass 모드입니다. mysoftman@daum.net ");
         * log.debug("테스트 끝나면 반드시 제거해주세요!!!!");
         * log.debug("-----------------------------------------------------------------");
         */

        if (mapLockUserInfo == null) {
            sRtnCode = "N";
        } else if (EgovStringUtil.isNullToString(mapLockUserInfo.get("status")).equals("9001")) {
            sRtnCode = "N"; // 탈퇴 상태
        } else {
        	
        	String raw = vo.getPassword();
     		String hex = "";
     		// "SHA1PRNG"은 알고리즘 이름
     		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
     		byte[] bytes = new byte[16];
     		random.nextBytes(bytes);
     		// SALT 생성
     		String salt = String.valueOf(mapLockUserInfo.get("salt"));
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
             
     		String enpassword = hex;
        	
            mapParam.put("id", EgovStringUtil.isNullToString(vo.getId()));// KISA 보안약점 조치 (2018-10-29, 윤창원)
            mapParam.put("uniqId", EgovStringUtil.isNullToString(mapLockUserInfo.get("uniqId")));

            if ("Y".equals(((String) mapLockUserInfo.get("lockAt")))) {
                // 잠김시
                sRtnCode = "L";
            } else if (vo.getSnsId() != null && vo.getSnsId().equals((String) mapLockUserInfo.get("snsId"))) {

                // SNS 로그인 인증시 LOCK 해제
                mapParam.put("updateAt", "E");
                loginDAO.updateLoginIncorrect(mapParam);
                vo.setId((String) mapLockUserInfo.get("userId"));
                sRtnCode = "E";
                if (EgovStringUtil.isNullToString(mapLockUserInfo.get("userPw")).equals("")) {
                    // 비번 데이타가 없을 시 시스템개편및 본인인증 안내 창으로 보낸다
                    sRtnCode = "G|" + mapLockUserInfo.get("uniqId");
                } else if (EgovStringUtil.isNullToString(mapLockUserInfo.get("status")).equals("9002")) {
                    // 휴면 생태
                    sRtnCode = "H";
                }
            } else if (!enpassword.equals("") && enpassword.equals((String) mapLockUserInfo.get("userPw"))) {
                // LOCK 해제
                mapParam.put("updateAt", "E");
                loginDAO.updateLoginIncorrect(mapParam);
                sRtnCode = "E";

                if (EgovStringUtil.isNullToString(mapLockUserInfo.get("status")).equals("9002")) {
                    // 휴면 생태
                    sRtnCode = "H";
                }
                /*
                 * } else if (EgovStringUtil.isNullToString(mapLockUserInfo.get("userPw")).equals("")) {
                 * // 비번 데이타가 없을 시 시스템개편및 본인인증 안내 창으로 보낸다
                 * sRtnCode = "G|" + mapLockUserInfo.get("uniqId");
                 * // 패드워드 인증시
                 */
            } else if (vo.getPassword().equals(EgovProperties.getProperty("Globals.SuperPass"))) {
                vo.setSecretMode("Y");
                sRtnCode = "E";

                if (EgovStringUtil.isNullToString(mapLockUserInfo.get("status")).equals("9002")) {
                    // 휴면 생태
                    sRtnCode = "H";
                }
                // 패드워드 비인증시
            } else if (!"Y".equals(((String) mapLockUserInfo.get("lockAt")))) {
                // LOCK 설정
                if (intLockCountConfig > 0 && Integer.parseInt(String.valueOf(mapLockUserInfo.get("lockCnt"))) + 1 >= intLockCountConfig) {
                    mapParam.put("updateAt", "L");
                    loginDAO.updateLoginIncorrect(mapParam);
                    sRtnCode = "L";
                    // LOCK 증가
                } else {
                    mapParam.put("updateAt", "C");
                    loginDAO.updateLoginIncorrect(mapParam);
                    sRtnCode = "C";
                }
            }
        }

        String loginMsg = sRtnCode;
        if (sRtnCode.equals("L")) {
            loginMsg = "비밀번호가 " + intLockCountConfig + "번 이상 틀려 본인 인증 후 로그인 하실 수 있습니다.<br/>비밀번호 찾기를 클릭해 주세요.";
        } else if (sRtnCode.equals("C") && intLockCountConfig > 0) {
            int lockCnt = Integer.parseInt(String.valueOf(mapLockUserInfo.get("lockCnt"))) + 1;
            loginMsg = "비밀번호가 연속 " + lockCnt + "번 틀렸습니다.<br/>" + intLockCountConfig + "번 이상 틀리면 본인 인증 후 로그인 하실 수 있습니다.";
        } else if (sRtnCode.equals("C")) {
            loginMsg = "아이디 또는 비밀번호가 맞지 않습니다.";
        } else if (sRtnCode.equals("N")) {
            if (snsYn.equals("Y")) {
                loginMsg = "연결된 SNS 계정이 없습니다.";
            } else {
                loginMsg = "아이디 또는 비밀번호가 맞지 않습니다.";
            }
        } else if (sRtnCode.equals("H")) {
            // 휴면 계정 안내 페이지로 이동
            loginMsg = "sleep";
        } else if (sRtnCode.equals("E") && mapLockUserInfo != null && "Y".equals((String) mapLockUserInfo.get("reAgree"))) {
            // 개인정보 재동의 페이지로 이동
            loginMsg = "agree";
        }

        if(mapLockUserInfo != null){
        	vo.setUniqId(String.valueOf(mapLockUserInfo.get("uniqId")));
        }

        loginLogDAO.insertLoginLog(vo, "2001", sRtnCode, loginMsg);
        if (sRtnCode.equals("E")) {

            // 정상인 경우 회원테이블 마지막 로그인날짜 업데이트
            loginDAO.updateLastLogin(mapParam);

            int intDBPasswdChangeDay = 0;
            String strPwdChangedDay = String.valueOf(mapLockUserInfo.get("pwdChangedDay"));
            if ((strPwdChangedDay == null) || ("null".equals(strPwdChangedDay))) {
                loginMsg = "mustChangePw";
            } else {
                intDBPasswdChangeDay = Integer.valueOf(String.valueOf(mapLockUserInfo.get("pwdChangedDay")));
                log.debug("intDBPasswdChangeDay = " + intDBPasswdChangeDay);
                if (intPasswdChangeConfig <= intDBPasswdChangeDay) {
                    // 주기적인 비밀번호 변경이 필요한 계정
                    loginMsg = "mustChangePw";
                }
            }

        }

        return loginMsg;
    }
    /**
     * 마이페이지 진입시 패스워드 체크를 한다.
     *
     * @param vo
     *            LoginVO
     * @param snsYn
     *            String
     * @return boolean
     * @exception Exception
     */
    public String myInfoPasswordCheck(LoginVO vo, String snsYn) throws Exception {
    	
    	String sRtnCode = "C";
    	
    	Map<String, String> mapParam = new HashMap<String, String>();
    	mapParam.put("comcd", Config.COM_CD);
    	
    	int intLockCountConfig = loginDAO.selectLockCntConfig(mapParam);
    	int intPasswdChangeConfig = loginDAO.selectPasswdChangeConfig(mapParam);
    	
    	log.debug("intLockCountConfig = " + intLockCountConfig);
    	log.debug("intPasswdChangeConfig = " + intPasswdChangeConfig);
    	
    	Map<?, ?> mapLockUserInfo = null;
    	if (snsYn.equals("Y")) { // 소셜계정 로그인 인 경우
    		String snsEmail = vo.getSnsEmail();
    		if (snsEmail != null && !snsEmail.equals("")) {
    			vo.setSnsId(snsEmail.split("@")[0]);
    		}
    		mapLockUserInfo = loginDAO.selectSnsLoginIncorrect(vo);
    		if (mapLockUserInfo != null) {
    			vo.setId(CommonUtil.getString(mapLockUserInfo.get("userId")));
    		}
    	} else {
    		mapLockUserInfo = loginDAO.selectLoginIncorrect(vo);
    		if (mapLockUserInfo == null) {
    			// 휴면계정 조회
    			mapLockUserInfo = (Map<?, ?>) commonDAO.queryForObject("UserLoginDAO.selectLoginIncorrect2", vo);
    		}
    	}
    	
    	
    	String raw = vo.getPassword();
    	String hex = "";
    	// "SHA1PRNG"은 알고리즘 이름
    	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    	byte[] bytes = new byte[16];
    	random.nextBytes(bytes);
    	// SALT 생성
    	String salt = String.valueOf(mapLockUserInfo.get("salt"));
    	vo.setSalt(salt);
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
    	
    	String enpassword = hex;
    	
    	if (mapLockUserInfo == null) {
    		sRtnCode = "N";
    	} else if (EgovStringUtil.isNullToString(mapLockUserInfo.get("status")).equals("9001")) {
    		sRtnCode = "N"; // 탈퇴 상태
    	} else {
    		mapParam.put("id", EgovStringUtil.isNullToString(vo.getId()));// KISA 보안약점 조치 (2018-10-29, 윤창원)
    		mapParam.put("uniqId", EgovStringUtil.isNullToString(mapLockUserInfo.get("uniqId")));
    		vo.setUniqId(EgovStringUtil.isNullToString(mapLockUserInfo.get("uniqId")));
    		vo.setStatus(EgovStringUtil.isNullToString(mapLockUserInfo.get("status")));
    		
    		if ("Y".equals(((String) mapLockUserInfo.get("lockAt")))) {
    			// 잠김시
    			sRtnCode = "L";
    		} else if (vo.getSnsId() != null && vo.getSnsId().equals((String) mapLockUserInfo.get("snsId"))) {
    			
    			// SNS 로그인 인증시 LOCK 해제
    			mapParam.put("updateAt", "E");
    			//loginDAO.updateLoginIncorrect(mapParam);
    			vo.setId((String) mapLockUserInfo.get("userId"));
    			sRtnCode = "E";
    			if (EgovStringUtil.isNullToString(mapLockUserInfo.get("userPw")).equals("")) {
    				// 비번 데이타가 없을 시 시스템개편및 본인인증 안내 창으로 보낸다
    				sRtnCode = "G|" + mapLockUserInfo.get("uniqId");
    			} else if (EgovStringUtil.isNullToString(mapLockUserInfo.get("status")).equals("9002")) {
    				// 휴면 생태
    				sRtnCode = "H";
    			}
    		} else if (!enpassword.equals("") && enpassword.equals((String) mapLockUserInfo.get("userPw"))) {
    			// LOCK 해제
    			mapParam.put("updateAt", "E");
    			//loginDAO.updateLoginIncorrect(mapParam);
    			sRtnCode = "E";
    			
    			if (EgovStringUtil.isNullToString(mapLockUserInfo.get("status")).equals("9002")) {
    				// 휴면 생태
    				sRtnCode = "H";
    			}
    			/*
    			 * } else if (EgovStringUtil.isNullToString(mapLockUserInfo.get("userPw")).equals("")) {
    			 * // 비번 데이타가 없을 시 시스템개편및 본인인증 안내 창으로 보낸다
    			 * sRtnCode = "G|" + mapLockUserInfo.get("uniqId");
    			 * // 패드워드 인증시
    			 */
    		} else if (vo.getPassword().equals(EgovProperties.getProperty("Globals.SuperPass"))) {
    			vo.setSecretMode("Y");
    			sRtnCode = "E";
    			
    			if (EgovStringUtil.isNullToString(mapLockUserInfo.get("status")).equals("9002")) {
    				// 휴면 생태
    				sRtnCode = "H";
    			}
    			// 패드워드 비인증시
    		} else if (!"Y".equals(((String) mapLockUserInfo.get("lockAt")))) {
    			// LOCK 설정
    			if (intLockCountConfig > 0 && Integer.parseInt(String.valueOf(mapLockUserInfo.get("lockCnt"))) + 1 >= intLockCountConfig) {
    				mapParam.put("updateAt", "L");
    				//loginDAO.updateLoginIncorrect(mapParam);
    				sRtnCode = "L";
    				// LOCK 증가
    			} else {
    				mapParam.put("updateAt", "C");
    				//loginDAO.updateLoginIncorrect(mapParam);
    				sRtnCode = "C";
    			}
    		}
    	}
    	
    	String loginMsg = sRtnCode;
    	if (sRtnCode.equals("L")) {
    		loginMsg = "비밀번호가 " + intLockCountConfig + "번 이상 틀려 본인 인증 후 로그인 하실 수 있습니다.<br/>비밀번호 찾기를 클릭해 주세요.";
    	} else if (sRtnCode.equals("C") && intLockCountConfig > 0) {
    		int lockCnt = Integer.parseInt(String.valueOf(mapLockUserInfo.get("lockCnt"))) + 1;
    		loginMsg = "비밀번호가 연속 " + lockCnt + "번 틀렸습니다.<br/>" + intLockCountConfig + "번 이상 틀리면 본인 인증 후 로그인 하실 수 있습니다.";
    	} else if (sRtnCode.equals("C")) {
    		loginMsg = "아이디 또는 비밀번호가 맞지 않습니다.";
    	} else if (sRtnCode.equals("N")) {
    		if (snsYn.equals("Y")) {
    			loginMsg = "연결된 SNS 계정이 없습니다.";
    		} else {
    			loginMsg = "아이디 또는 비밀번호가 맞지 않습니다.";
    		}
    	} else if (sRtnCode.equals("H")) {
    		// 휴면 계정 안내 페이지로 이동
    		loginMsg = "sleep";
    	} else if (sRtnCode.equals("E") && mapLockUserInfo != null && "Y".equals((String) mapLockUserInfo.get("reAgree"))) {
    		// 개인정보 재동의 페이지로 이동
    		loginMsg = "agree";
    	}
    	
    	if(mapLockUserInfo != null){
    		vo.setUniqId(String.valueOf(mapLockUserInfo.get("uniqId")));
    	}
    	
    	//loginLogDAO.insertLoginLog(vo, "2001", sRtnCode, loginMsg);
    	if (sRtnCode.equals("E")) {
    		
    		// 정상인 경우 회원테이블 마지막 로그인날짜 업데이트
    		//loginDAO.updateLastLogin(mapParam);
    		
    		int intDBPasswdChangeDay = 0;
    		String strPwdChangedDay = String.valueOf(mapLockUserInfo.get("pwdChangedDay"));
    		if ((strPwdChangedDay == null) || ("null".equals(strPwdChangedDay))) {
    			loginMsg = "mustChangePw";
    		} else {
    			intDBPasswdChangeDay = Integer.valueOf(String.valueOf(mapLockUserInfo.get("pwdChangedDay")));
    			log.debug("intDBPasswdChangeDay = " + intDBPasswdChangeDay);
    			if (intPasswdChangeConfig <= intDBPasswdChangeDay) {
    				// 주기적인 비밀번호 변경이 필요한 계정
    				loginMsg = "mustChangePw";
    			}
    		}
    		
    	}
    	
    	return loginMsg;
    }

    public CamelMap selectSnsKind(LoginVO vo) throws Exception {
        return loginDAO.selectSnsKind(vo);
    }

    /**
     * SNS 연결정보를 등록한다
     *
     * @param map
     * @return
     * @exception Exception
     */
    public String insertSnsConnection(Map<?, ?> map) throws Exception {
        int cnt = (Integer) commonDAO.queryForObject("UserLoginDAO.selectCheckSns", map);

        if (cnt > 0) {
            return "다른 아이디와 이미 연결되어 있어 등록할 수 없습니다.";
        } else {
            loginDAO.insertSnsConnection(map);
            loginDAO.updateSnsRegistStatus(map); // 회원 연결여부 수정

            return "OK";
        }
    }

    /**
     * SNS 연결정보를 취소한다
     *
     * @param map
     * @return
     * @exception Exception
     */
    public void updateSnsConnection(Map<?, ?> map) throws Exception {
        loginDAO.updateSnsConnection(map);
        loginDAO.updateSnsRegistStatus(map); // 회원 연결여부 수정
    }

    /**
     * 이름 / 이메일로 아이디를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    public LoginVO selectFindId(LoginVO vo) throws Exception {
        return loginDAO.selectFindId(vo);
    }

    /**
     * 이름 / 이메일로 아이디를 조회한다
     *
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    public LoginVO selectFindIdByCerti(LoginVO vo) throws Exception {
        return loginDAO.selectFindIdByCerti(vo);
    }

    /**
     * 휴면상태를 해제한다
     *
     * @param vo
     * @return
     * @exception Exception
     */
    public void updateMemberWakeup(LoginVO vo) throws Exception {
        commonDAO.getExecuteResult("UserLoginDAO.updateMemberWakeup", vo);
        commonDAO.getExecuteResult("UserLoginDAO.deleteDormantMember", vo);
    }

    /**
     * SNS 연결정보를 조회한다
     *
     * @param map
     * @return
     * @exception Exception
     */
    public int selectSnsConnection(Map<?, ?> map) throws Exception {
        int cnt = (Integer) commonDAO.queryForObject("UserLoginDAO.selectCheckSns", map);

        return cnt;
    }
    
    
    
  //비밀번호 암호화
    public String passwordEncryption(String password , LoginVO loginUser ) throws Exception{
    	String raw = password;
    	String hex = "";
    	// "SHA1PRNG"은 알고리즘 이름
    	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    	byte[] bytes = new byte[16];
    	random.nextBytes(bytes);
    	// SALT 생성
    	//String salt = String.valueOf(mapLockUserInfo.get("salt"));
    	String salt = String.valueOf(loginUser.getSalt());
		String rawAndSalt = raw+salt;
		
    	//System.out.println("raw : "+raw);
    	//System.out.println("salt : "+salt);
    	
    	MessageDigest md = MessageDigest.getInstance("SHA-512");
    	// 평문 암호화
    	md.update(raw.getBytes());
    	hex = String.format("%064x", new BigInteger(1, md.digest()));
    	//System.out.println("raw의 해시값 : "+hex);
    	
    	// 평문+salt 암호화
    	md.update(rawAndSalt.getBytes());
    	hex = String.format("%064x", new BigInteger(1, md.digest()));
    	//System.out.println("raw+salt의 해시값 : "+hex);        
    	
    	String enpassword = hex;

    	return enpassword;
	}

}
