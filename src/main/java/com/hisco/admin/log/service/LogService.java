package com.hisco.admin.log.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.log.mapper.LogMapper;
import com.hisco.admin.log.vo.AdminLogVO;
import com.hisco.admin.log.vo.ErrorLogVO;
import com.hisco.cmm.object.CamelMap;
import com.hisco.cmm.object.CommandMap;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class Name : LogServiceImpl.java
 * @Description : 로그관리(관리자)를 위한 인터페이스 구현 클래스
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 7. 16. 진수진 최초생성
 * @author 진수진
 * @since 2020. 7. 16.
 * @version
 * @see
 */
@Slf4j
@Service("logService")
public class LogService extends EgovAbstractServiceImpl {

    @Resource(name = "logMapper")
    private LogMapper logMapper;

    /**
     * 시스템 로그정보를 생성한다.
     *
     * @param commandMap
     * @param methodGubun
     *            String
     * @param strDesc
     *            String
     * @return 권한 존재 여부
     */
    public boolean checkAdminLog(CommandMap commandMap, String methodGubun, String strDesc) throws Exception {
        boolean flag = false;

        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

        if (isAuthenticated) {
            LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
            commandMap.setAdminUser(user);
        }

        String strIpAdress = commandMap.getIp();

        // 권한 체크
        MenuManageVO menuVO = commandMap.getSelectedMenu();
        if (menuVO != null) {
            if ("R".equals(methodGubun) || ("C".equals(methodGubun) && "Y".equals(menuVO.getInsYn())) || ("U".equals(methodGubun) && "Y".equals(menuVO.getUpdYn())) || ("D".equals(methodGubun) && "Y".equals(menuVO.getDelYn()))) {

                AdminLogVO logVO = new AdminLogVO();
                logVO.setConectId(commandMap.getAdminUser() == null ? "" : commandMap.getAdminUser().getId());
                logVO.setConectIp(strIpAdress);
                logVO.setMenuid(menuVO.getMenuNo());
                logVO.setMethodGubun(methodGubun);
                logVO.setTargetUrl(commandMap.getTrgetUrl());
                logMapper.logInsertLoginLog(logVO);

                flag = true;
            }
        } else {
            log.debug("getSelectedMenu() is null");
        }

        return flag;
    }

    /**
     * 작업 로그 목록을 조회한다
     *
     * @param vo
     *            AdminLogVO
     * @return List
     * @exception Exception
     */
    public List<?> selectAdminLogList(AdminLogVO vo) throws Exception {
        return logMapper.selectLogList(vo);
    }

    /**
     * 작업 로그 목록 갯수 조회한다
     *
     * @param vo
     *            AdminLogVO
     * @return int
     * @exception Exception
     */
    public int selectAdminLogCount(AdminLogVO vo) throws Exception {
        return logMapper.selectLogCount(vo);
    }

    /**
     * 웹 로그 목록을 조회한다
     *
     * @param param
     *            Map
     * @return List
     * @exception Exception
     */
    public List<CamelMap> selectWebLogList(Map<?, ?> param) throws Exception {
        return (List<CamelMap>) logMapper.selectWebLogList(param);
    }

    /**
     * 날짜 목록
     *
     * @param param
     *            Map
     * @return List
     * @exception Exception
     */
    public List<CamelMap> selectDateList(Map<?, ?> param) throws Exception {
        return (List<CamelMap>) logMapper.selectDateList(param);
    }

    /**
     * 에러 로그를 등록한다
     *
     * @param vo
     *            ErrorLogVO
     * @return int
     * @exception Exception
     */
    public int insertErrorLog(ErrorLogVO vo) throws Exception {
        String errorMsg = vo.getErrormsg();
        String paramVal = vo.getParamVal();

        if (errorMsg != null && errorMsg.length() > 4000) {
            vo.setErrormsg(errorMsg.substring(0, 4000));
        }

        if (paramVal != null) {
            byte[] b = paramVal.getBytes();
            byte[] copy = new byte[4000];
            if (b.length > 4000) {
                System.arraycopy(b, 0, copy, 0, 4000);

                vo.setParamVal(new String(copy));
            }

        }
        return logMapper.insertErrorLog(vo);
    }

    /**
     * 에러 로그 목록을 조회한다
     *
     * @param vo
     *            ErrorLogVO
     * @return List
     * @exception Exception
     */
    public List<?> selectErrorLogList(ErrorLogVO vo) throws Exception {
        return logMapper.selectErrorLogList(vo);
    }

    /**
     * 에러 로그 목록 갯수 조회한다
     *
     * @param vo
     *            ErrorLogVO
     * @return int
     * @exception Exception
     */
    public int selectErrorLogCount(ErrorLogVO vo) throws Exception {
        return logMapper.selectErrorLogCount(vo);
    }

    /**
     * 시스템 접속 로그 목록을 조회한다
     *
     * @param vo
     *            AdminLogVO
     * @return List
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<CamelMap> selectConnLogList(Map<String, Object> paramMap) throws Exception {
        return (List<CamelMap>) logMapper.selectConnLogList(paramMap);
    }

    /**
     * 개인정보 변경이력 확인
     *
     * @param vo
     *            AdminLogVO
     * @return int
     * @exception Exception
     */
    public List<EgovMap> selectMemberLogList(AdminLogVO vo) throws Exception {
        return logMapper.selectMemberLogList(vo);
    }

    public void logInsertPrivateLog(AdminLogVO logVO) throws Exception {
        logMapper.logInsertPrivateLog(logVO);
    }

    // 권한 변경 로그
    public List<EgovMap> selectAuthorLogList(AdminLogVO vo) throws Exception {
        return logMapper.selectAuthorLogList(vo);
    }

    // 웹 서비스 페이지뷰
    public List<EgovMap> selectWebVisitLogList(AdminLogVO vo) {
        return logMapper.selectWebVisitLogList(vo);
    }

    // 웹 회원 로그인 접속
    public List<EgovMap> selectLoginLogList(AdminLogVO vo) {
        return logMapper.selectLoginLogList(vo);
    }

    public List<EgovMap> selectPaywaitLogList(AdminLogVO vo) {
        return logMapper.selectPaywaitLogList(vo);
    }

    public List<EgovMap> selectNwpayLogList(AdminLogVO vo) {
        return logMapper.selectNwpayLogList(vo);
    }
}
