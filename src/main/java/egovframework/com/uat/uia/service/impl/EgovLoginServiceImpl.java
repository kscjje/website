package egovframework.com.uat.uia.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.log.mapper.LogMapper;
import com.hisco.admin.log.vo.AdminLogVO;
import com.hisco.admin.manager.mapper.SysUserMapper;
import com.hisco.cmm.mapper.CommonDAO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.util.Config;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.config.EgovLoginConfig;
import egovframework.com.uat.uia.service.EgovLoginService;
import egovframework.com.uat.uia.service.LockStatus;
import egovframework.com.utl.fcc.service.EgovNumberUtil;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 비즈니스 구현 클래스
 *
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.06
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.06  박지욱          최초 생성
 *  2011.08.26  서준식          EsntlId를 이용한 로그인 추가
 *  2014.12.08	이기하			암호화방식 변경(EgovFileScrty.encryptPassword)
 *  2017.07.21  장동한 			로그인인증제한 작업
 *      </pre>
 */
@Slf4j
@Service("loginService")
public class EgovLoginServiceImpl extends EgovAbstractServiceImpl implements EgovLoginService {

    @Resource(name = "loginDAO")
    private LoginDAO loginDAO;

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    @Resource(name = "sysUserMapper")
    private SysUserMapper sysUserMapper;

    @Resource(name = "logMapper")
    private LogMapper logMapper;

    @Resource(name = "egovLoginConfig")
    private EgovLoginConfig egovLoginConfig;

    /**
     * 2011.08.26
     * EsntlId를 이용한 로그인을 처리한다
     *
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    @Override
    public LoginVO actionLoginByEsntlId(LoginVO vo) throws Exception {

        LoginVO loginVO = loginDAO.actionLoginByEsntlId(vo);

        // 3. 결과를 리턴한다.
        if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
            return loginVO;
        } else {
            loginVO = new LoginVO();
        }

        return loginVO;
    }

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
        // String enpassword = EgovFileScrty.encryptPassword(vo.getPassword(), vo.getId());
        //String enpassword = EgovFileScrty.getSHA512(vo.getPassword());
        //vo.setPassword(enpassword);

        // 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
        LoginVO loginVO = loginDAO.actionLogin(vo);

        // 3. 결과를 리턴한다.
        if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
            return loginVO;
        } else {
            loginVO = new LoginVO();
        }

        return loginVO;
    }

    /**
     * 인증서 로그인을 처리한다
     *
     * @param vo
     *            LoginVO
     * @return LoginVO
     * @exception Exception
     */
    @Override
    public LoginVO actionCrtfctLogin(LoginVO vo) throws Exception {

        // 1. DN값으로 ID, PW를 조회한다.
        LoginVO loginVO = loginDAO.actionCrtfctLogin(vo);

        // 3. 결과를 리턴한다.
        if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
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
     * 로그인인증제한을 처리한다.
     *
     * @param vo
     *            LoginVO
     * @return boolean
     * @exception Exception
     */
    public String processLoginIncorrect(LoginVO vo) throws Exception {

        log.debug("call processLoginIncorrect()");

        String sRtnCode = "C";

        // String enpassword = EgovFileScrty.encryptPassword(vo.getPassword(),
        // EgovStringUtil.isNullToString(vo.getId()));
        String enpassword = EgovFileScrty.getSHA512(vo.getPassword());

        log.debug("vo.getPassword() = " + vo.getPassword());
        log.debug("enpassword = " + enpassword);

        Map<String, String> mapParam = new HashMap<String, String>();
        mapParam.put("USER_SE", vo.getUserSe());
        mapParam.put("id", EgovStringUtil.isNullToString(vo.getId())); // KISA 보안약점 조치 (2018-10-29, 윤창원)
        mapParam.put("comcd", Config.COM_CD);

        int lockCountConfig = loginDAO.selectLockCntConfig(mapParam);
        if (lockCountConfig == 0) {
            lockCountConfig = egovLoginConfig.getLockCount();
        }

        Map<?, ?> mapLockUserInfo = loginDAO.selectLoginIncorrect(vo);

        /*사용자 암호화 부분 수정*/
		String raw 			= vo.getPassword();
		String salt 		= (String) mapLockUserInfo.get("userSalt");
		String rawAndSalt 	= raw+salt;
		String hex 			= "";

		System.out.println("raw : "+raw);
		System.out.println("salt : "+salt);
		
		MessageDigest md 	= MessageDigest.getInstance("SHA-512");
		// 평문 암호화
		md.update(raw.getBytes());
		hex = String.format("%064x", new BigInteger(1, md.digest()));
		System.out.println("raw의 해시값 : "+hex);
		
		// 평문+salt 암호화
		md.update(rawAndSalt.getBytes());
		hex = String.format("%064x", new BigInteger(1, md.digest()));
		System.out.println("raw+salt의 해시값 : "+hex);
		
		if(hex.contentEquals((String) mapLockUserInfo.get("userPw"))) {
			System.out.println("로그인성공");
		}
		else {
			System.out.println("로그인실패");
		}
        if (mapLockUserInfo == null) {
            sRtnCode = LockStatus.NOT_FOUND;
        } 
        else 
        {
            // 잠김시
            int lockCnt = Integer.parseInt(String.valueOf(mapLockUserInfo.get("lockCnt")));
            String lockAt = (String) mapLockUserInfo.get("lockAt");
            String userPw = (String) mapLockUserInfo.get("userPw");

            if( vo.getUserType().equals("1001") && "2001".equals(mapLockUserInfo.get("userType")+"")){
            	sRtnCode = LockStatus.NOT_ALLOWED ;  //내부 외부 다름
            }else if ("Y".equals(lockAt)) {
                // Dead Code
                sRtnCode = LockStatus.LOCKED + lockCnt;
                // 패드워드 인증시
            //} else if ((userPw).equals(enpassword)) {
            }else if(hex.contentEquals((String) mapLockUserInfo.get("userPw"))) {
                // LOCK 해제
                mapParam.put("updateAt", LockStatus.UNLOCKED);
                loginDAO.updateLoginIncorrect(mapParam);
                sRtnCode = LockStatus.UNLOCKED;
                vo.setPassword(hex);
                // 패드워드 비인증시
            } else if (!"Y".equals(lockAt)) {
                // LOCK 설정
                if (lockCountConfig > 0 && lockCnt + 1 >= lockCountConfig) {
                    mapParam.put("updateAt", LockStatus.LOCKED);
                    loginDAO.updateLoginIncorrect(mapParam);
                    sRtnCode = LockStatus.LOCKED + (lockCnt + 1);
                    // LOCK 증가
                } else {
                    mapParam.put("updateAt", LockStatus.LOCKING);
                    loginDAO.updateLoginIncorrect(mapParam);
                    sRtnCode = LockStatus.LOCKING + (lockCnt + 1);
                }
            }
        }
        log.debug("sRtnCode = " + sRtnCode);
        AdminLogVO logVO = new AdminLogVO();
        logVO.setConectId(vo.getId());
        logVO.setConectIp(vo.getIp());
        logVO.setMenuid(0);
        logVO.setMethodGubun("L");
        logVO.setErroryn(sRtnCode.equals(LockStatus.UNLOCKED) ? "N" : "Y");
        logVO.setErrorcode(sRtnCode.equals(LockStatus.UNLOCKED) ? "" : sRtnCode);

        logMapper.logInsertLoginLog(logVO);

        return sRtnCode;
    }

    /**
     * IP 제한 내역을 체크하다.
     *
     * @param vo
     *            LoginVO
     * @return Map
     * @exception Exception
     */
    public CamelMap selectAcntIppolicyCheck(LoginVO vo) throws Exception {
        return sysUserMapper.selectAcntIppolicyCheck(vo);
    }
}
