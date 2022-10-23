package com.hisco.admin.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hisco.admin.manager.vo.SysUserIpVO;
import com.hisco.admin.manager.vo.SysUserOrgVO;
import com.hisco.admin.manager.vo.SysUserVO;
import com.hisco.admin.orginfo.vo.OrgInfoVO;
import com.hisco.cmm.object.CamelMap;

import egovframework.com.cmm.LoginVO;
import egovframework.com.sym.mnu.mcm.service.MenuCreatVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 관리자 계정 데이타를 조회 및 등록 Mapper
 *
 * @author 진수진
 * @since 2020.07.14
 * @version 1.0, 2020.07.14
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.14 최초작성
 */
@Mapper("sysUserMapper")
public interface SysUserMapper {

    /**
     * 관리자 목록을 조회한다
     *
     * @param vo
     *            SysUser
     * @return List
     * @exception Exception
     */
    public List<?> selectList(SysUserVO vo);

    /**
     * 관리자 목록의 갯수를 조회한다
     *
     * @param vo
     *            SysUserVO
     * @return int
     * @exception Exception
     */
    public int selectListCount(SysUserVO vo);

    /**
     * 관리자상세 정보를 조회한다
     *
     * @param vo
     *            SysUserVO
     * @return SysUserVO
     * @exception Exception
     */
    public SysUserVO selectMstRecord(SysUserVO vo);

    public List<SysUserOrgVO> selectMstPartList(SysUserVO vo);

    public List<OrgInfoVO> selectMstOrgList(SysUserVO vo);

    public List<String> selectMyOrgList(@Param("comcd") String comcd, @Param("userId") String userId);

    public SysUserVO selectRole(SysUserVO vo);

    /**
     * 관리자 를 신규 생성한다
     *
     * @param vo
     *            SysUserVO
     * @return int
     * @exception Exception
     */
    public int insertMst(SysUserVO vo);

    public int insertRole(SysUserVO vo);

    public int insertRoleLog(SysUserVO vo);

    public int updateMst(SysUserVO vo);

    public int updateMyInfo(SysUserVO vo);

    /**
     * 관리자 를 수정한다
     *
     * @param vo
     *            SysUser
     * @return int
     * @exception Exception
     */
    public int selectRoleCount(SysUserVO vo);

    public int updateRole(SysUserVO vo);

    /**
     * 관리자 비밀번호를 수정한다
     *
     * @param vo
     *            SysUser
     * @return int
     * @exception Exception
     */
    public int updatePassword(SysUserVO vo);

    /**
     * 관리자 권한을 삭제한다
     *
     * @param vo
     *            SysUser
     * @return int
     * @exception Exception
     */
    public int deleteRole(SysUserVO vo);

    /**
     * 관리자 그룹 목록을 조회한다
     *
     * @param vo
     *            SysUser
     * @return List
     * @exception Exception
     */
    public List<?> selectGroupList(SysUserVO vo);

    /**
     * 관리자 그룹 상세를 조회한다
     *
     * @param vo
     *            SysUser
     * @return List
     * @exception Exception
     */
    public SysUserVO selectGroupDetail(SysUserVO vo);

    /**
     * 관리자 그룹을 신규 생성한다
     *
     * @param vo
     *            SysUser
     * @return String
     * @exception Exception
     */
    public int insertGroup(SysUserVO vo);

    /**
     * 관리자 그룹을 수정한다
     *
     * @param vo
     *            SysUser
     * @return String
     * @exception Exception
     */
    public int updateGroup(SysUserVO vo);

    /**
     * 관리자 그룹을 삭제한다
     *
     * @param vo
     *            SysUser
     * @return int
     * @exception Exception
     */
    public int deleteGroup(SysUserVO vo);

    /**
     * 화면에 조회된 메뉴정보로 메뉴생성내역 데이터베이스에서 입력
     *
     * @param menuCreatVO
     *            MenuCreatVO
     * @param insertMenuNo
     *            String[]
     * @exception Exception
     */
    public int selectCountGroupMenu(MenuCreatVO menuCreatVO);

    public int deleteGroupMenu(MenuCreatVO menuCreatVO);

    public int insertGroupMenu(MenuCreatVO menuCreatVO);

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
    public void updateGroupMenuIns(MenuCreatVO menuCreatVO);

    public void updateGroupMenuUpd(MenuCreatVO menuCreatVO);

    public void updateGroupMenuDel(MenuCreatVO menuCreatVO);

    public int selectMenuGroup_All(MenuCreatVO menuCreatVO);

    /**
     * 사용자에 등록된 IP 리스트를 조회한다.
     *
     * @param vo
     *            SysUserVO
     * @return SysUserVO
     * @exception Exception
     */
    public List<?> selectSysUserIpList(SysUserVO vo);

    /**
     * IP 접속 차단 설정 정보를 신규 저장한다
     *
     * @param vo
     *            SysUserIpVO
     * @return int
     * @exception Exception
     */
    public int insertSysIpUser(SysUserIpVO vo);

    /**
     * IP 접속 차단 설정 정보를 삭제한다
     *
     * @param vo
     *            SysUserIpVO
     * @return int
     * @exception Exception
     */
    public int deleteSysIpUser(SysUserIpVO vo);

    public int deleteSysIpUserAll(SysUserVO vo);

    public CamelMap selectAcntIppolicyCheck(LoginVO vo);

    public int selectPartCount(SysUserVO vo);

    public int insertSysPart(SysUserVO vo);

    public int updateSysPart(SysUserVO vo);

    public int deleteSysPart(SysUserVO vo);

    public int selectOrgAccessCount(SysUserOrgVO vo);

    public int insertOrgAccessData(SysUserOrgVO vo);

    // 관리 기관 삭제
    public void deleteOrgAccessData(@Param("comcd") String comcd, @Param("userId") String userId,
            @Param("orgList") List<String> orgList);

    public String selectGroupNextCd();
}
