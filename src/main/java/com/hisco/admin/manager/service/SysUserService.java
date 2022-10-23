package com.hisco.admin.manager.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hisco.admin.manager.mapper.SysUserMapper;
import com.hisco.admin.manager.vo.SysUserIpVO;
import com.hisco.admin.manager.vo.SysUserOrgVO;
import com.hisco.admin.manager.vo.SysUserVO;
import com.hisco.admin.orginfo.vo.OrgInfoVO;

import egovframework.com.sym.mnu.mcm.service.MenuCreatVO;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 관리자 계정 데이타를 조회 및 등록 구현클래스
 *
 * @author 진수진
 * @since 2020.07.14
 * @version 1.0, 2020.07.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.14 최초작성
 */
@Slf4j
@Service("sysUserService")
public class SysUserService extends EgovAbstractServiceImpl {

    @Resource(name = "sysUserMapper")
    private SysUserMapper sysUserMapper;

    /**
     * 관리자 목록을 조회한다
     *
     * @param vo
     *            SysUser
     * @return List
     * @exception Exception
     */
    public List<?> selectSysUserList(SysUserVO vo) throws Exception {
        return sysUserMapper.selectList(vo);
    }

    /**
     * 관리자 목록의 갯수를 조회한다
     *
     * @param vo
     *            SysUserVO
     * @return int
     * @exception Exception
     */
    public int selectSysUserCnt(SysUserVO vo) throws Exception {
        return (Integer) sysUserMapper.selectListCount(vo);
    }

    /**
     * 관리자상세 정보를 조회한다
     *
     * @param vo
     *            SysUserVO
     * @return SysUserVO
     * @exception Exception
     */
    public SysUserVO selectSysUserDetail(SysUserVO vo) throws Exception {

        SysUserVO data = (SysUserVO) sysUserMapper.selectMstRecord(vo);
        SysUserVO roleCd = (SysUserVO) sysUserMapper.selectRole(vo);

        List<SysUserOrgVO> partList = sysUserMapper.selectMstPartList(vo);

        if (data != null && roleCd != null) {
            data.setRoleCd(roleCd.getRoleCd());
            data.setGroupId(roleCd.getGroupId());
        }

        if (data != null && partList != null && partList.size() > 0) {
            data.setOrgNo(partList.get(0).getOrgNo());
        }

        return data;
    }

    public SysUserVO selectMstRecord(SysUserVO vo) throws Exception {
        return sysUserMapper.selectMstRecord(vo);
    }

    public List<OrgInfoVO> selectMstOrgList(SysUserVO vo) throws Exception {
        return sysUserMapper.selectMstOrgList(vo);
    }

    /**
     * 관리자 를 신규 생성한다
     *
     * @param vo
     *            SysUserVO
     * @return int
     * @exception Exception
     */
    public String insertSysUser(SysUserVO vo, SysUserIpVO[] ipList) throws Exception {
        SysUserVO data = (SysUserVO) sysUserMapper.selectMstRecord(vo);
        String result = "";

        //String password = EgovFileScrty.getSHA512(vo.getPassword());
        //vo.setPassword(password);

        if (data != null) {
            result = "ER|동일한 아이디가 존재합니다.";
        } else {
            int n = sysUserMapper.insertMst(vo);

            if (n > 0) {
                sysUserMapper.insertRole(vo);

                vo.setLognm("권한지정");
                sysUserMapper.insertRoleLog(vo);

                if (vo.getOrgNo() > 0) {
                    sysUserMapper.insertSysPart(vo);
                }

                if (vo.getOrgList() != null) {
                    for (String orgNo : vo.getOrgList()) {
                        SysUserOrgVO orgVO = new SysUserOrgVO();
                        orgVO.setOrgNo(Integer.parseInt(orgNo));
                        orgVO.setComcd(vo.getComcd());
                        orgVO.setUserId(vo.getUserId());
                        orgVO.setReguser(vo.getReguser());

                        sysUserMapper.insertOrgAccessData(orgVO);

                    }
                }

                if (ipList != null) {
                    for (SysUserIpVO ipVo : ipList) {
                        if (ipVo != null && ipVo.getIpInfo() != null && !ipVo.getIpInfo().equals(""))
                            sysUserMapper.insertSysIpUser(ipVo);
                    }
                }

                result = "OK";
            } else {
                result = "ER|데이타입력 오류";
            }
        }
        return result;
    }

    /**
     * 관리자 를 삭제한다
     *
     * @param vo
     *            SysUser
     * @return int
     * @exception Exception
     */
    public int deleteSysUser(SysUserVO vo) throws Exception {
        return 0;
    }

    /**
     * 관리자 를 수정한다
     *
     * @param vo
     *            SysUser
     * @return int
     * @exception Exception
     */
    public int updateSysUser(SysUserVO vo, String oldRoleCd, SysUserIpVO[] ipList) throws Exception {

        int cnt = (int) sysUserMapper.selectRoleCount(vo);
        int n = sysUserMapper.updateMst(vo);

        if (n > 0) {
            if (cnt < 1) {
                sysUserMapper.insertRole(vo);
                vo.setLognm("권한지정");
            } else {
                sysUserMapper.updateRole(vo);
                vo.setLognm("권한수정");
            }

            if (!vo.getRoleCd().equals(oldRoleCd)) {
                sysUserMapper.insertRoleLog(vo);
            }

            int chk = sysUserMapper.selectPartCount(vo);

            if (vo.getOrgNo() > 0) {
                if (chk > 0) {
                    sysUserMapper.updateSysPart(vo);
                } else {
                    sysUserMapper.insertSysPart(vo);
                }
            } else if (chk > 0) {
                sysUserMapper.deleteSysPart(vo);
            }

            if (vo.getOrgList() != null && vo.getOrgList().size() > 0) {
                // 삭제
                sysUserMapper.deleteOrgAccessData(vo.getComcd(), vo.getUserId(), vo.getOrgList());

                for (String orgNo : vo.getOrgList()) {
                    SysUserOrgVO orgVO = new SysUserOrgVO();
                    orgVO.setOrgNo(Integer.parseInt(orgNo));
                    orgVO.setComcd(vo.getComcd());
                    orgVO.setUserId(vo.getUserId());
                    orgVO.setReguser(vo.getReguser());

                    int orgCnt = sysUserMapper.selectOrgAccessCount(orgVO);

                    if (orgCnt < 1) {
                        sysUserMapper.insertOrgAccessData(orgVO);
                    }
                }

            } else {
                sysUserMapper.deleteOrgAccessData(vo.getComcd(), vo.getUserId(), null);
            }

            // 기존 IP 삭제
            sysUserMapper.deleteSysIpUserAll(vo);

            if (ipList != null) {
                for (SysUserIpVO ipVo : ipList) {
                    if (ipVo != null && ipVo.getIpInfo() != null && !ipVo.getIpInfo().equals(""))
                        sysUserMapper.insertSysIpUser(ipVo);
                }
            }

        }

        return n;
    }


    /**
     * 관리자 를 수정한다
     *
     * @param vo
     *            SysUser
     * @return int
     * @exception Exception
     */
    public int updateMyInfo(SysUserVO vo) throws Exception {
        int n = sysUserMapper.updateMyInfo(vo);

        return n;
    }

    /**
     * 관리자 비밀번호를 수정한다
     *
     * @param vo
     *            SysUser
     * @return int
     * @exception Exception
     */
    public int updateSysUserPassword(SysUserVO vo) throws Exception {
        //String password = EgovFileScrty.getSHA512(vo.getPassword());
        //vo.setPassword(password);

        int n = sysUserMapper.updatePassword(vo);

        return n;
    }

    /**
     * 관리자 권한을 수정한다
     *
     * @param vo
     *            SysUser
     * @return int
     * @exception Exception
     */
    public int updateSysUserRole(SysUserVO vo) throws Exception {
        int cnt = (int) sysUserMapper.selectRoleCount(vo);

        if (cnt < 1) {
            sysUserMapper.insertRole(vo);
            vo.setLognm("권한지정");
        } else {
            sysUserMapper.updateRole(vo);
            vo.setLognm("권한변경");
        }

        sysUserMapper.insertRoleLog(vo);

        return cnt;
    }

    /**
     * 관리자 권한을 삭제한다
     *
     * @param vo
     *            SysUser
     * @return int
     * @exception Exception
     */
    public int deleteSysUserRole(SysUserVO vo) throws Exception {
        int cnt = sysUserMapper.deleteRole(vo);
        vo.setLognm("권한해제");
        if (cnt > 0) {
            sysUserMapper.insertRoleLog(vo);
        }
        return cnt;
    }

    /**
     * 관리자 그룹 목록을 조회한다
     *
     * @param vo
     *            SysUser
     * @return List
     * @exception Exception
     */
    public List<?> selectSysGroupList(SysUserVO vo) throws Exception {
        return sysUserMapper.selectGroupList(vo);
    }

    /**
     * 관리자 그룹 상세를 조회한다
     *
     * @param vo
     *            SysUser
     * @return List
     * @exception Exception
     */
    public SysUserVO selectSysGroupDetail(SysUserVO vo) throws Exception {
        return (SysUserVO) sysUserMapper.selectGroupDetail(vo);
    }

    /**
     * 관리자 그룹을 신규 생성한다
     *
     * @param vo
     *            SysUser
     * @return String
     * @exception Exception
     */
    public int insertSysGroup(SysUserVO vo) throws Exception {
        return sysUserMapper.insertGroup(vo);
    }

    /**
     * 관리자 그룹을 수정한다
     *
     * @param vo
     *            SysUser
     * @return String
     * @exception Exception
     */
    public int updateSysGroup(SysUserVO vo) throws Exception {
        return sysUserMapper.updateGroup(vo);
    }

    /**
     * 관리자 그룹을 삭제한다
     *
     * @param vo
     *            SysUser
     * @return int
     * @exception Exception
     */
    public int deleteSysGroup(SysUserVO vo) throws Exception {
        return sysUserMapper.deleteGroup(vo);
    }

    /**
     * 화면에 조회된 메뉴정보로 메뉴생성내역 데이터베이스에서 입력
     *
     * @param menuCreatVO
     *            MenuCreatVO
     * @param insertMenuNo
     *            String[]
     * @exception Exception
     */
    public void insertMenuCreatList(MenuCreatVO menuCreatVO, String[] insertMenuNo) throws Exception {
        int authorCnt = (Integer) sysUserMapper.selectCountGroupMenu(menuCreatVO);

        log.debug("authorCnt = {}", authorCnt);

        // 이전에 존재하는 권한코드에 대한 메뉴설정내역 삭제
        if (authorCnt > 0) {
            sysUserMapper.deleteGroupMenu(menuCreatVO);
        }

        if (insertMenuNo != null) {
            for (int i = 0; i < insertMenuNo.length; i++) {
                menuCreatVO.setMenuNo(Integer.parseInt(insertMenuNo[i]));
                sysUserMapper.insertGroupMenu(menuCreatVO);
            }
        }
    }

    /**
     * 화면에 조회된 메뉴정보로 메뉴생성내역 데이터베이스에서 입력
     *
     * @param menuCreatVO
     *            MenuCreatVO
     * @param insertMenuNo
     *            String[]
     * @param insMenuNo
     *            String[]
     * @param updMenuNo
     *            String[]
     * @param delMenuNo
     *            String[]
     * @exception Exception
     */
    public void insertMenuCreatList(MenuCreatVO menuCreatVO, String[] insertMenuNo, String[] insMenuNo,
            String[] updMenuNo, String[] delMenuNo) throws Exception {
        int authorCnt = (Integer) sysUserMapper.selectCountGroupMenu(menuCreatVO);

        // 이전에 존재하는 권한코드에 대한 메뉴설정내역 삭제
        if (authorCnt > 0) {
            sysUserMapper.deleteGroupMenu(menuCreatVO);
        }

        if (insertMenuNo != null) {
            for (int i = 0; i < insertMenuNo.length; i++) {
                menuCreatVO.setMenuNo(Integer.parseInt(insertMenuNo[i]));
                sysUserMapper.insertGroupMenu(menuCreatVO);
            }
        }

        // 입력권한 Y 로 수정
        if (insMenuNo != null) {
            for (int i = 0; i < insMenuNo.length; i++) {
                menuCreatVO.setMenuNo(Integer.parseInt(insMenuNo[i]));
                menuCreatVO.setInsYn("Y");
                sysUserMapper.updateGroupMenuIns(menuCreatVO);
            }
        }

        // 수정권한 Y 로 수정
        if (updMenuNo != null) {
            for (int i = 0; i < updMenuNo.length; i++) {
                menuCreatVO.setMenuNo(Integer.parseInt(updMenuNo[i]));
                menuCreatVO.setUpdYn("Y");
                sysUserMapper.updateGroupMenuUpd(menuCreatVO);
            }
        }

        // 삭제권한 Y 로 수정
        if (delMenuNo != null) {
            for (int i = 0; i < delMenuNo.length; i++) {
                menuCreatVO.setMenuNo(Integer.parseInt(delMenuNo[i]));
                menuCreatVO.setDelYn("Y");
                sysUserMapper.updateGroupMenuDel(menuCreatVO);
            }
        }

    }

    public int selectMenuGroup_All(MenuCreatVO menuCreatVO) throws Exception {
        return (Integer) sysUserMapper.selectMenuGroup_All(menuCreatVO);
    }

    /**
     * 사용자에 등록된 IP 리스트를 조회한다.
     *
     * @param vo
     *            SysUserVO
     * @return SysUserVO
     * @exception Exception
     */
    public List<?> selectSysUserIpList(SysUserVO vo) throws Exception {
        return sysUserMapper.selectSysUserIpList(vo);
    }

    /**
     * IP 접속 차단 설정 정보를 신규 저장한다
     *
     * @param vo
     *            SysUserVO
     * @return int
     * @exception Exception
     */
    public String insertSysIpUser(SysUserIpVO vo) throws Exception {

        String strResult = "";

        int intResult = sysUserMapper.insertSysIpUser(vo);
        if (intResult > 0) {
            strResult = "OK";
        } else {
            strResult = "ER|데이타입력 오류";
        }

        return strResult;
    }

    /**
     * IP 접속 차단 설정 정보를 삭제한다
     *
     * @param vo
     *            SysUser
     * @return int
     * @exception Exception
     */
    public String deleteSysIpUser(SysUserIpVO vo) throws Exception {

        String strResult = "";

        int intResult = sysUserMapper.deleteSysIpUser(vo);
        if (intResult > 0) {
            strResult = "OK";
        } else {
            strResult = "ER|데이타입력 오류";
        }

        return strResult;
    }

    public String selectGroupNextCd() {
        return sysUserMapper.selectGroupNextCd();
    }

    public List<String> selectMyOrgList(String comcd, String userId) {
        return sysUserMapper.selectMyOrgList(comcd, userId);
    }
}
