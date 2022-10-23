package com.hisco.admin.contents.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.hisco.admin.contents.mapper.ContentsMapper;
import com.hisco.admin.contents.vo.ContentsVO;
import com.hisco.cmm.object.UserSession;
import com.hisco.cmm.util.Config;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.mnu.mpm.service.EgovMenuManageService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 카테고리 관리 Service 구현 클래스
 *
 * @author 전영석
 * @since 2021.03.19
 * @version 1.0, 2021.03.19
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          전영석 2021.03.19 최초작성
 */
@Service("contentsService")
public class ContentsService extends EgovAbstractServiceImpl {

    @Resource(name = "contentsMapper")
    private ContentsMapper contentsMapper;

    /** EgovMenuManageService */
    @Resource(name = "meunManageService")
    private EgovMenuManageService menuManageService;

    /**
     * 컨텐츠 목록을 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public List<?> selectContentList(ContentsVO paramMap) throws Exception {
        return contentsMapper.selectContentList(paramMap);
    }

    /**
     * 컨텐츠 상세 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public ContentsVO selectContentsDetail(Map<String, Object> paramMap) throws Exception {
        return contentsMapper.selectContentsDetail(paramMap);
    }

    /**
     * 연결된 메뉴 정보를 조회한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public ContentsVO selectMenuTitle(Map<String, Object> paramMap) throws Exception {
        return contentsMapper.selectMenuTitle(paramMap);
    }

    /**
     * 개인 교육 프로그램을 신규 등록한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int insertContents(ContentsVO paramMap) throws Exception {
        int cnt = contentsMapper.insertContents(paramMap);

        // 연결된 메뉴의 URL 도 함께 수정한다
        if (cnt > 0 && paramMap.getMenuNo() > 0) {
            String menuUrl = Config.USER_ROOT + "/contents/" + paramMap.getContentsSeq() + "/view";
            String menuDc = "\\A" + menuUrl.substring(0, menuUrl.length() - 5) + ".*\\Z";

            paramMap.setMenuUrl(menuUrl);
            paramMap.setMenuDc(menuDc);
            contentsMapper.updateContentsUrl(paramMap);
        }

        return cnt;
    }

    /**
     * 컨텐츠를 수정한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int updateContents(ContentsVO paramMap) throws Exception {
        return contentsMapper.updateContents(paramMap);
    }

    public int updateContentsFileId2(ContentsVO paramMap) {
        return contentsMapper.updateContentsFileId2(paramMap);
    }

    /**
     * 컨텐츠를 수정한다.
     *
     * @param Map
     * @return List
     * @throws Exception
     */
    public int deleteContents(ContentsVO paramMap) throws Exception {
        return contentsMapper.deleteContents(paramMap);
    }

    public boolean contentEditFlag(HttpServletRequest request) {
        // 컨텐츠 수정 권한이 있는지 체크
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        boolean editAuthYn = false;
        if (user != null && !"".equals(user.getId())) {
            if (user.getGroupId() < 1) {
                editAuthYn = true;
            } else {
                // 메뉴 권한 가져오기
                UserSession userSession = (UserSession) request.getSession().getAttribute(Config.USER_SESSION);
                List<MenuManageVO> menuList = userSession.getAdminMenuList();

                if ((menuList == null || menuList.size() < 1)) {

                    MenuManageVO vo = new MenuManageVO();
                    vo.setSiteGubun("ADMIN");
                    vo.setTmpId(user.getId());
                    vo.setGroupId(user.getGroupId());

                    try {
                        menuList = (List<MenuManageVO>) menuManageService.selectMenuListSite(vo);
                        String strReqURL = Config.ADMIN_ROOT + "/contents/contentsList"; // 관리자 컨텐츠 관리 메뉴

                        if (menuList != null) {
                            for (MenuManageVO menuVO : menuList) {
                                String strRolePttrn = (menuVO.getRolePttrn() == null
                                        ? "" : menuVO.getRolePttrn()) + (menuVO.getMenuDc() != null
                                                ? ((menuVO.getRolePttrn() != null && menuVO.getRolePttrn().length() > 0
                                                        ? "||" : "") + menuVO.getMenuDc())
                                                : "");

                                List<String> patternList = Arrays.asList(strRolePttrn.split("[||]"));

                                for (String strRegExp : patternList) {
                                    if (strReqURL.matches(strRegExp)) {
                                        if ("Y".equals(menuVO.getUpdYn())) {
                                            editAuthYn = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        // log.error("List<MenuManageVO> cast exception");
                    }
                }

            }
        }

        return editAuthYn;
    }

}
