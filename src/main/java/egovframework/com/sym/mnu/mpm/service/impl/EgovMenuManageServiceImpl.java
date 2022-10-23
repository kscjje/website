package egovframework.com.sym.mnu.mpm.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hisco.admin.menu.service.MenuSortVO;
import com.hisco.cmm.mapper.CommonDAO;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.sym.mnu.mpm.service.EgovMenuManageService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 메뉴목록관리, 생성, 사이트맵을 처리하는 비즈니스 구현 클래스를 정의한다.
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
 *   										selectUpperMenuNoByPk() 메서드 추가
 *   2017-02-13  이정은          시큐어코딩(ES) - 시큐어코딩 부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
 *   2019-12-06  신용호          KISA 보안약점 조치 (부적절한 예외처리)
 *   2020-07-17  진수진          메뉴 정렬순서 변경
 *      </pre>
 */

@Service("meunManageService")
public class EgovMenuManageServiceImpl extends EgovAbstractServiceImpl implements EgovMenuManageService {

    @Resource(name = "menuManageDAO")
    private MenuManageDAO menuManageDAO;

    @Resource(name = "commonDAO")
    private CommonDAO commonDAO;

    //// @Resource(name = "excelZipService")
    //// private EgovExcelService excelZipService;

    @Resource(name = "multipartResolver")
    CommonsMultipartResolver mailmultipartResolver;

    /**
     * 메뉴 상세정보를 조회
     *
     * @param vo
     *            ComDefaultVO
     * @return MenuManageVO
     * @exception Exception
     */
    public MenuManageVO selectMenuManage(ComDefaultVO vo) throws Exception {
        return menuManageDAO.selectMenuManage(vo);
    }

    /**
     * 메뉴 목록을 조회
     *
     * @param vo
     *            ComDefaultVO
     * @return List
     * @exception Exception
     */
    public List<?> selectMenuManageList(ComDefaultVO vo) throws Exception {
        return menuManageDAO.selectMenuManageList(vo);
    }

    /**
     * 메뉴목록 총건수를 조회한다.
     *
     * @param vo
     *            ComDefaultVO
     * @return int
     * @exception Exception
     */
    public int selectMenuManageListTotCnt(ComDefaultVO vo) throws Exception {
        return menuManageDAO.selectMenuManageListTotCnt(vo);
    }

    /**
     * 메뉴번호를 상위메뉴로 참조하고 있는 메뉴 존재여부를 조회
     *
     * @param vo
     *            ComDefaultVO
     * @return int
     * @exception Exception
     */
    public int selectUpperMenuNoByPk(MenuManageVO vo) throws Exception {
        return menuManageDAO.selectUpperMenuNoByPk(vo);
    }

    /**
     * 메뉴번호 존재 여부를 조회한다.
     *
     * @param vo
     *            ComDefaultVO
     * @return int
     * @exception Exception
     */
    public int selectMenuNoByPk(MenuManageVO vo) throws Exception {
        return menuManageDAO.selectMenuNoByPk(vo);
    }

    /**
     * 메뉴 정보를 등록
     *
     * @param vo
     *            MenuManageVO
     * @exception Exception
     */
    public void insertMenuManage(MenuManageVO vo) throws Exception {
        // 순서 체크
        int menuOrdr = 0;
        menuOrdr = menuManageDAO.selectNextMenuOrdr(vo);

        if (vo.getMenuOrdr() > menuOrdr || vo.getMenuOrdr() <= 0) {
            vo.setMenuOrdr(menuOrdr);
        }

        if (menuOrdr > 0) {
            menuManageDAO.updateMenuOrdr(vo); // 순서변경
        }

        menuManageDAO.insertMenuManage(vo); // 입력

    }

    /**
     * 메뉴 정보를 수정
     *
     * @param vo
     *            MenuManageVO
     * @exception Exception
     */
    public void updateMenuManage(MenuManageVO vo) throws Exception {
        menuManageDAO.updateMenuManage(vo);
    }

    /**
     * 메뉴 정보를 삭제
     *
     * @param vo
     *            MenuManageVO
     * @exception Exception
     */
    public void deleteMenuManage(MenuManageVO vo) throws Exception {
        // menuManageDAO.deleteMenuManage(vo);
        // 데이타 삭제 대신 del_yn='Y' 로 업데이트
        menuManageDAO.updateMenuManageDelete(vo);
    }

    /**
     * 화면에 조회된 메뉴 목록 정보를 데이터베이스에서 삭제
     *
     * @param checkedMenuNoForDel
     *            String
     * @exception Exception
     */
    public void deleteMenuManageList(String checkedMenuNoForDel) throws Exception {
        MenuManageVO vo = null;

        String[] delMenuNo = checkedMenuNoForDel.split(",");

        if (delMenuNo != null && (delMenuNo.length > 0)) {
            for (int i = 0; i < delMenuNo.length; i++) {
                vo = new MenuManageVO();
                vo.setMenuNo(Integer.parseInt(delMenuNo[i]));
                menuManageDAO.deleteMenuManage(vo);
            }
        }
    }

    /**
     * 메뉴 순서 변경
     *
     * @param siteGubun
     *            String
     * @param menuNos
     *            String[]
     * @exception Exception
     */
    public void updateMenuManageSorting(MenuSortVO menuSortVO) throws Exception {

        menuManageDAO.updateMenuOrdr2(menuSortVO);

    }

    /* 메뉴 생성 관리 */

    /**
     * 메뉴 목록을 조회
     *
     * @return List
     * @exception Exception
     */
    public List<?> selectMenuList() throws Exception {
        return menuManageDAO.selectMenuList();
    }

    /* ### 메뉴관련 프로세스 ### */
    /**
     * MainMenu Head Menu 조회
     *
     * @param vo
     *            MenuManageVO
     * @return List
     * @exception Exception
     */
    public List<?> selectMainMenuHead(MenuManageVO vo) throws Exception {
        return menuManageDAO.selectMainMenuHead(vo);
    }

    /**
     * MainMenu Head Left 조회
     *
     * @param vo
     *            MenuManageVO
     * @return List
     * @exception Exception
     */
    public List<?> selectMainMenuLeft(MenuManageVO vo) throws Exception {
        return menuManageDAO.selectMainMenuLeft(vo);
    }

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
    public String selectLastMenuURL(int iMenuNo, String sUniqId) throws Exception {
        MenuManageVO vo = new MenuManageVO();
        vo.setMenuNo(selectLastMenuNo(iMenuNo, sUniqId));
        return menuManageDAO.selectLastMenuURL(vo);
    }

    /**
     * MainMenu Head Menu MenuNo 조회
     *
     * @param iMenuNo
     *            int
     * @param sUniqId
     *            String
     * @return String
     * @exception Exception
     */
    private int selectLastMenuNo(int iMenuNo, String sUniqId) throws Exception {
        int chkMenuNo = iMenuNo;
        int cntMenuNo = 0;
        for (; chkMenuNo > -1;) {
            chkMenuNo = selectLastMenuNoChk(chkMenuNo, sUniqId);
            if (chkMenuNo > 0) {
                cntMenuNo = chkMenuNo;
            }
        }
        return cntMenuNo;
    }

    /**
     * MainMenu Head Menu Last MenuNo 조회
     *
     * @param iMenuNo
     *            int
     * @param sUniqId
     *            String
     * @return String
     * @exception Exception
     */
    private int selectLastMenuNoChk(int iMenuNo, String sUniqId) throws Exception {
        MenuManageVO vo = new MenuManageVO();
        vo.setMenuNo(iMenuNo);
        vo.setTempValue(sUniqId);
        int chkMenuNo = 0;
        int cntMenuNo = 0;
        cntMenuNo = menuManageDAO.selectLastMenuNoCnt(vo);
        if (cntMenuNo > 0) {
            chkMenuNo = menuManageDAO.selectLastMenuNo(vo);
        } else {
            chkMenuNo = -1;
        }
        return chkMenuNo;
    }

    /**
     * 로그인 사용자의 메뉴목록을 조회
     *
     * @param vo
     *            MenuManageVO
     * @return List
     * @exception Exception
     */
    public List<?> selectMenuListSite(MenuManageVO vo) throws Exception {

        List<MenuManageVO> menuList = (List<MenuManageVO>) menuManageDAO.selectMenuListSite(vo);
        return menuList;
    }

    /**
     * 메뉴목록 전체 조회
     *
     * @param vo
     *            ComDefaultVO
     * @return List
     * @exception Exception
     */
    public List<?> selectAllList(ComDefaultVO vo) throws Exception {
        return menuManageDAO.selectAllList(vo);
    }

    /**
     * 메뉴정보 전체데이타 초기화
     *
     * @return boolean
     * @exception Exception
     */
    /*
     * private boolean deleteAllMenuList() throws Exception {
     * return menuManageDAO.deleteAllMenuList();
     * }
     */

    /**
     * 메뉴정보를 일괄 등록
     *
     * @param vo
     *            MenuManageVO
     * @return boolean
     * @exception Exception
     */
    /*
     * private boolean insertMenuManageBind(MenuManageVO vo) throws Exception {
     * // 순서 체크
     * int menuOrdr = menuManageDAO.selectNextMenuOrdr(vo);
     * vo.setMenuOrdr(menuOrdr);
     * menuManageDAO.updateMenuOrdr(vo); // 순서변경
     * menuManageDAO.insertMenuManage(vo); // 입력
     * return true;
     * }
     */

    /**
     * 메뉴 상세 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectMenuByParam(Map<String, Object> paramMap) throws Exception {
        return commonDAO.queryForList("menuManageDAO.selectMenuByParam", paramMap);
    }

    /**
     * 메뉴 상세 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectSubMenuByParam(Map<String, Object> paramMap) throws Exception {
        return commonDAO.queryForList("menuManageDAO.selectSubMenuByParam", paramMap);
    }

    /**
     * 메뉴 상세 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectUpperMenuByParam(Map<String, Object> paramMap) throws Exception {
        return commonDAO.queryForList("menuManageDAO.selectUpperMenuByParam", paramMap);
    }

    /**
     * 메뉴 상세 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectUpperMenuOnlyOne(Map<String, Object> paramMap) throws Exception {
        return commonDAO.queryForList("menuManageDAO.selectUpperMenuOnlyOne", paramMap);
    }

    /**
     * 메뉴 상세 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectUpperMenuOnlyOneN(Map<String, Object> paramMap) throws Exception {
        return commonDAO.queryForList("menuManageDAO.selectUpperMenuOnlyOneN", paramMap);
    }

    /**
     * 메뉴 상세정보를 조회
     *
     * @param vo
     *            ComDefaultVO
     * @return MenuManageVO
     * @exception Exception
     */
    public MenuManageVO selectMenuManageForSel(ComDefaultVO vo) throws Exception {
        return menuManageDAO.selectMenuManageForSel(vo);
    }

    /**
     * 로그인 사용자의 메뉴목록을 조회
     *
     * @param vo
     *            MenuManageVO
     * @return List
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
    public List<?> selectMenuListSiteMath(MenuManageVO vo) throws Exception {

        List<MenuManageVO> menuList = (List<MenuManageVO>) menuManageDAO.selectMenuListSite(vo);

        /*
         * List<MenuManageVO> newMenuList = new ArrayList<MenuManageVO>();
         * int index = 0;
         * for (MenuManageVO item : menuList) {
         * //관람 사업장 목록을 가져온다
         * newMenuList.add(index++, item);
         * if (item.getUpperMenuNo() == 0 && item.getMenuDc()!=null && item.getMenuDc().indexOf("/exbtsvn/dspy/") > 0) {
         * Map<String,String> param = new HashMap<String,String>();
         * param.put("comcd", Config.COM_CD);
         * List<CamelMap> partList = (List<CamelMap>)commonDAO.queryForList("DspyDsDAO.selectPartMenuList", param);
         * if (partList != null) {
         * for (CamelMap subItem : partList) {
         * MenuManageVO newMenuVO = new MenuManageVO();
         * newMenuVO.setMenuDepth(2);
         * newMenuVO.setUpperMainYn(item.getUpperMainYn());
         * newMenuVO.setUpperMenuNm(item.getMenuNm());
         * newMenuVO.setUpperMenuNo(Integer.parseInt(item.getMenuNo()+""));
         * if (CommonUtil.getInt(subItem.get("exbtSeq")) > 0) {
         * ////newMenuVO.setMenuNo(Integer.parseInt("1" + subItem.get("exbtSeq")));
         * ////String url = Config.USER_ROOT + "/exbtrsvn/dspy/detail?uniqueId="+subItem.get("exbtSeq");
         * ////newMenuVO.setMenuUrl(url);
         * ////newMenuVO.setMenuDc("\\A"+url + ".*\\Z" );
         * } else {
         * newMenuVO.setMenuNo(Integer.parseInt("1" + subItem.get("partCd")));
         * newMenuVO.setMenuUrl(Config.USER_ROOT + "/exbtrsvn/dspy/" + subItem.get("partCd"));
         * newMenuVO.setMenuDc("\\A"+Config.USER_ROOT + "/exbtrsvn/dspy/" + subItem.get("partCd") + ".*\\Z" );
         * //추가
         * newMenuVO.setMenuOrdr(item.getMenuOrdr());
         * newMenuVO.setRelateImageNm(item.getRelateImageNm());
         * newMenuVO.setMainYn("Y");
         * newMenuVO.setFrontGnbmenuyn("Y");
         * newMenuVO.setUseYn("Y");
         * newMenuVO.setMenuNm((String)subItem.get("partNm"));
         * newMenuList.add(index++, newMenuVO);
         * }
         * }
         * }
         * }
         * }
         * return newMenuList;
         */

        return menuList;

    }

}