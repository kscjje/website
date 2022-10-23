package egovframework.com.sym.mnu.mpm.service;

import java.util.List;

import com.hisco.admin.menu.service.MenuSortVO;

import egovframework.com.cmm.ComDefaultVO;

/**
 * 메뉴관리에 관한 서비스 인터페이스 클래스를 정의한다.
 * 
 * @author 개발환경 개발팀 이용
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.20  이  용          최초 생성
 *   2011.07.01  서준식			자기 메뉴 정보를 상위메뉴 정보로 참조하는 메뉴정보가 있는지 조회하는
 *   							selectUpperMenuNoByPk() 메서드 추가
 *      </pre>
 */

public interface EgovMenuManageService {

    /**
     * 메뉴 상세정보를 조회
     * 
     * @param vo
     *            ComDefaultVO
     * @return MenuManageVO
     * @exception Exception
     */
    MenuManageVO selectMenuManage(ComDefaultVO vo) throws Exception;

    /**
     * 메뉴 목록을 조회
     * 
     * @param vo
     *            ComDefaultVO
     * @return List
     * @exception Exception
     */
    List<?> selectMenuManageList(ComDefaultVO vo) throws Exception;

    /**
     * 메뉴목록 총건수를 조회한다.
     * 
     * @param vo
     *            ComDefaultVO
     * @return int
     * @exception Exception
     */
    int selectMenuManageListTotCnt(ComDefaultVO vo) throws Exception;

    /**
     * 메뉴번호 존재 여부를 조회한다.
     * 
     * @param vo
     *            ComDefaultVO
     * @return int
     * @exception Exception
     */
    int selectMenuNoByPk(MenuManageVO vo) throws Exception;

    int selectUpperMenuNoByPk(MenuManageVO vo) throws Exception;

    /**
     * 메뉴 정보를 등록
     * 
     * @param vo
     *            MenuManageVO
     * @exception Exception
     */
    void insertMenuManage(MenuManageVO vo) throws Exception;

    /**
     * 메뉴 정보를 수정
     * 
     * @param vo
     *            MenuManageVO
     * @exception Exception
     */
    void updateMenuManage(MenuManageVO vo) throws Exception;

    /**
     * 메뉴 정보를 삭제
     * 
     * @param vo
     *            MenuManageVO
     * @exception Exception
     */
    void deleteMenuManage(MenuManageVO vo) throws Exception;

    /**
     * 화면에 조회된 메뉴 목록 정보를 데이터베이스에서 삭제
     * 
     * @param checkedMenuNoForDel
     *            String
     * @exception Exception
     */
    void deleteMenuManageList(String checkedMenuNoForDel) throws Exception;

    /**
     * 메뉴 순서 변경
     * 
     * @param siteGubun
     *            String
     * @param menuNos
     *            String[]
     * @exception Exception
     */
    void updateMenuManageSorting(MenuSortVO commandMap) throws Exception;

    /* 메뉴 생성 관리 */

    /**
     * 메뉴 목록을 조회
     * 
     * @return List
     * @exception Exception
     */
    List<?> selectMenuList() throws Exception;

    /* ### 메뉴관련 프로세스 ### */
    /**
     * MainMenu Head Menu 조회
     * 
     * @param vo
     *            MenuManageVO
     * @return List
     * @exception Exception
     */
    List<?> selectMainMenuHead(MenuManageVO vo) throws Exception;

    /**
     * MainMenu Head Left 조회
     * 
     * @param vo
     *            MenuManageVO
     * @return List
     * @exception Exception
     */
    List<?> selectMainMenuLeft(MenuManageVO vo) throws Exception;

    /**
     * MainMenu Head MenuURL 조회
     * 
     * @param iMenuNo
     *            int
     * @param sUniqId
     *            String
     * @return String
     * @exception Exception
     */
    String selectLastMenuURL(int iMenuNo, String sUniqId) throws Exception;

    /**
     * 로그인 사용자의 메뉴목록을 조회
     * 
     * @param vo
     *            MenuManageVO
     * @return List
     * @exception Exception
     */
    List<?> selectMenuListSite(MenuManageVO vo) throws Exception;

    /**
     * 메뉴목록 전체 조회
     * 
     * @param vo
     *            ComDefaultVO
     * @return List
     * @exception Exception
     */
    List<?> selectAllList(ComDefaultVO vo) throws Exception;

    /**
     * 메뉴 상세 정보를 조회한다.
     *
     * @param Map
     * @return void
     * @throws Exception
     */
    List<?> selectMenuByParam(java.util.Map<String, Object> paramMap) throws Exception;

    /**
     * 메뉴 상세 정보를 조회한다.
     *
     * @param Map
     * @return void
     * @throws Exception
     */
    List<?> selectSubMenuByParam(java.util.Map<String, Object> paramMap) throws Exception;

    /**
     * 메뉴 상세 정보를 조회한다.
     *
     * @param Map
     * @return void
     * @throws Exception
     */
    List<?> selectUpperMenuByParam(java.util.Map<String, Object> paramMap) throws Exception;

    /**
     * 메뉴 상세 정보를 조회한다.
     *
     * @param Map
     * @return void
     * @throws Exception
     */
    List<?> selectUpperMenuOnlyOne(java.util.Map<String, Object> paramMap) throws Exception;

    /**
     * 메뉴 상세 정보를 조회한다.
     *
     * @param Map
     * @return void
     * @throws Exception
     */
    List<?> selectUpperMenuOnlyOneN(java.util.Map<String, Object> paramMap) throws Exception;

    /**
     * 메뉴 상세정보를 조회
     * 
     * @param vo
     *            ComDefaultVO
     * @return MenuManageVO
     * @exception Exception
     */
    MenuManageVO selectMenuManageForSel(ComDefaultVO vo) throws Exception;

    /**
     * 로그인 사용자의 메뉴목록을 조회
     * 
     * @param vo
     *            MenuManageVO
     * @return List
     * @exception Exception
     */
    List<?> selectMenuListSiteMath(MenuManageVO vo) throws Exception;

}