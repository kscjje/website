package egovframework.com.sym.log.clg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.sym.log.clg.service.EgovLoginLogService;
import egovframework.com.sym.log.clg.service.LoginLog;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * @Class Name : EgovLoginLogServiceImpl.java
 * @Description : 접속로그 관리를 위한 서비스 구현 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2009. 3. 11. 이삼섭 최초생성
 *               2011. 7. 01. 이기하 패키지 분리(stm.log -> sym.log.clg)
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 */
@Service("EgovLoginLogService")
public class EgovLoginLogServiceImpl extends EgovAbstractServiceImpl implements
        EgovLoginLogService {

    @Resource(name = "loginLogDAO")
    private LoginLogDAO loginLogDAO;

    /** ID Generation */
    //// @Resource(name="egovLoginLogIdGnrService")
    //// private EgovIdGnrService egovLoginLogIdGnrService;

    /**
     * 접속로그를 기록한다.
     *
     * @param LoginLog
     */
    @Override
    public void logInsertLoginLog(LoginLog loinLog) throws Exception {
        loginLogDAO.logInsertLoginLog(loinLog);
    }

    /**
     * 접속로그를 조회한다.
     *
     * @param loginLog
     * @return loginLog
     * @throws Exception
     */
    @Override
    public LoginLog selectLoginLog(LoginLog loginLog) throws Exception {

        return loginLogDAO.selectLoginLog(loginLog);
    }

    /**
     * 접속로그 목록을 조회한다.
     *
     * @param LoginLog
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Map<?, ?> selectLoginLogInf(LoginLog loinLog) throws Exception {
        List<?> result = loginLogDAO.selectLoginLogInf(loinLog);
        int intCnt = loginLogDAO.selectLoginLogInfCnt(loinLog);

        Map<String, Object> rsltmap = new HashMap();
        rsltmap.put("resultList", result);
        rsltmap.put("resultCnt", Integer.toString(intCnt));

        return rsltmap;
    }

}
