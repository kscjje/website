package com.hisco.admin.log.mapper;

import java.util.List;
import java.util.Map;

import com.hisco.admin.log.vo.AdminLogVO;
import com.hisco.admin.log.vo.ErrorLogVO;
import com.hisco.cmm.object.CamelMap;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

/**
 * @Class Name : LogMapper.java
 * @Description : 로그관리(관리자)를 위한 Mapper
 * @Modification Information
 *               수정일 수정자 수정내용
 *               ------- ------- -------------------
 *               2020. 7. 16. 진수진 최초생성
 * @author 진수진
 * @since 2020. 7. 16.
 * @version
 * @see
 */
@Mapper("logMapper")
public interface LogMapper {

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
    public boolean logInsertLoginLog(AdminLogVO logVO);

    /**
     * 작업 로그 목록을 조회한다
     *
     * @param vo
     *            AdminLogVO
     * @return List
     * @exception Exception
     */
    public List<?> selectLogList(AdminLogVO vo);

    /**
     * 작업 로그 목록 갯수 조회한다
     *
     * @param vo
     *            AdminLogVO
     * @return int
     * @exception Exception
     */
    public int selectLogCount(AdminLogVO vo);

    /**
     * 웹 로그 목록을 조회한다
     *
     * @param param
     *            Map
     * @return List
     * @exception Exception
     */
    public List<CamelMap> selectWebLogList(Map<?, ?> param);

    /**
     * 날짜 목록
     *
     * @param param
     *            Map
     * @return List
     * @exception Exception
     */
    public List<CamelMap> selectDateList(Map<?, ?> param);

    /**
     * 에러 로그를 등록한다
     *
     * @param vo
     *            ErrorLogVO
     * @return int
     * @exception Exception
     */
    public int insertErrorLog(ErrorLogVO vo);

    /**
     * 에러 로그 목록을 조회한다
     *
     * @param vo
     *            ErrorLogVO
     * @return List
     * @exception Exception
     */
    public List<?> selectErrorLogList(ErrorLogVO vo);

    /**
     * 에러 로그 목록 갯수 조회한다
     *
     * @param vo
     *            ErrorLogVO
     * @return int
     * @exception Exception
     */
    public int selectErrorLogCount(ErrorLogVO vo);

    /**
     * 시스템 접속 로그 목록을 조회한다
     *
     * @param vo
     *            AdminLogVO
     * @return List
     * @exception Exception
     */
    public List<CamelMap> selectConnLogList(Map<String, Object> paramMap);

    /**
     * 개인정보 변경이력 확인
     *
     * @param vo
     *            AdminLogVO
     * @return int
     * @exception Exception
     */
    public List<EgovMap> selectMemberLogList(AdminLogVO vo);

    // 개인정보 접속이력
    public boolean logInsertPrivateLog(AdminLogVO logVO);

    // 권한 변경 로그
    public List<EgovMap> selectAuthorLogList(AdminLogVO vo);

    // 웹 서비스 페이지뷰
    public List<EgovMap> selectWebVisitLogList(AdminLogVO vo);

    // 웹 회원 로그인 접속
    public List<EgovMap> selectLoginLogList(AdminLogVO vo);

    // 결제대기시간 로그
    public List<EgovMap> selectPaywaitLogList(AdminLogVO vo);

    // 노원페이로그목록
    public List<EgovMap> selectNwpayLogList(AdminLogVO vo);

}
