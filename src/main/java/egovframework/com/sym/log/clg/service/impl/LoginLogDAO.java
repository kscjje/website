package egovframework.com.sym.log.clg.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.sym.log.clg.service.LoginLog;

/**
 * @Class Name : LoginLogDAO.java
 * @Description : 시스템 로그 관리를 위한 데이터 접근 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2009. 3. 11. 이삼섭 최초생성
 *               2011. 7. 01. 이기하 패키지 분리(sym.log -> sym.log.clg)
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 */
@Repository("loginLogDAO")
public class LoginLogDAO extends EgovComAbstractDAO {

    /**
     * 접속로그를 기록한다.
     *
     * @param LoginLog
     * @return
     * @throws Exception
     */
    public void logInsertLoginLog(LoginLog loginLog) throws Exception {
        insert("LoginLog.logInsertLoginLog", loginLog);
    }

    /**
     * 로그인 로그를 저장한다
     *
     * @param vo
     *            LoginVO
     * @param appGbn
     *            시스템구분
     * @param errorCode
     *            에러코드
     * @return boolean
     * @exception Exception
     */
    public void insertLoginLog(LoginVO vo, String appGbn, String errorCode, String errorMsg) throws Exception {

        String strThisErrorCode = errorCode;
        String strThisErrorMsg = errorMsg;

        // 로그인 로그 저장
        LoginLog loinLog = new LoginLog();

        if (strThisErrorCode.startsWith("G")) {
            strThisErrorMsg = "최초로그인 비번설정 필요";
            strThisErrorCode = "G";
        }

        loinLog.setLoginMthd("I");
        loinLog.setLoginId(vo.getId());
        loinLog.setLoginIp(vo.getIp());
        loinLog.setErrOccrrAt(strThisErrorCode.equals("E") ? "N" : "Y");
        loinLog.setErrorCode(strThisErrorCode.equals("E") ? "" : strThisErrorCode);
        loinLog.setErrorMsg(strThisErrorCode.equals("E") ? "" : strThisErrorMsg);
        loinLog.setConnctAppgbn(appGbn);
        loinLog.setSnsKind(vo.getSnsKind());
        loinLog.setMemNo(vo.getUniqId());

        if (vo.getId() != null && !vo.getId().equals("")) {
            insert("LoginLog.logInsertLoginLog", loinLog);
        }
    }

    /**
     * 접속로그 상세보기를 조회한다.
     *
     * @param loginLog
     * @return loginLog
     * @throws Exception
     */
    public LoginLog selectLoginLog(LoginLog loginLog) throws Exception {

        return (LoginLog) selectOne("LoginLog.selectLoginLog", loginLog);
    }

    /**
     * 접속로그를 목록을 조회한다.
     *
     * @param loginLog
     * @return
     * @throws Exception
     */
    public List<?> selectLoginLogInf(LoginLog loginLog) throws Exception {
        return list("LoginLog.selectLoginLogInf", loginLog);
    }

    /**
     * 접속로그 목록의 숫자를 조회한다.
     *
     * @param loginLog
     * @return
     * @throws Exception
     */
    public int selectLoginLogInfCnt(LoginLog loginLog) throws Exception {

        return (Integer) selectOne("LoginLog.selectLoginLogInfCnt", loginLog);
    }

}
