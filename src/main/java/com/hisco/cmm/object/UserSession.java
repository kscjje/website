package com.hisco.cmm.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;

/**
 * 세션 정보를 담는 객체
 *
 * @author 진수진
 * @since 2020.07.01
 * @version 1.0, 2020.07.01
 *          ------------------------------------------------------------------------
 *          작성자 일자 내용
 *          ------------------------------------------------------------------------
 *          진수진 2020.07.01 최초작성
 */
public class UserSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 로그인한 회원정보
     */
    private LoginVO userInfo;

    /**
     * 관리자 로그인정보 
     * 
     */
    private LoginVO adminInfo;
    
    /**
     * 관리자 메뉴 정보
     */
    private List<MenuManageVO> adminMenuList;

    /**
     * 사용자 메뉴 정보
     */
    private List<MenuManageVO> userMenuList;

    /**
     * 추가 정보 설정
     */
    private Map addInfo = new HashMap();

    /**
     * 관리 기업 목록
     */
    private List<String> myOrgList;

    public UserSession() {
    }

    /**
     * 사용자 로그인 여부 반환 ( 비회원 로그인일 수 있음)
     *
     * @param
     * @return boolean
     */
    public boolean isLogin() {
        return (userInfo != null);
    }

    /**
     * 사용자 회원 여부 반환
     *
     * @param
     * @return boolean
     */
    public boolean isMember() {
        if (userInfo != null && userInfo.isMember()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 관리자 로그인 여부 반환
     *
     * @param
     * @return boolean
     */
    public boolean isAdminLogin() {
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        return isAuthenticated;
    }

    /**
     * 추가정보 반환
     *
     * @param
     * @return Map
     */
    public Map getAddInfo() {
        return addInfo;
    }

    /**
     * 추가정보 설정
     *
     * @param Map
     *            addInfo
     * @return
     */
    public void setAddInfo(Map addInfo) {
        this.addInfo = addInfo;
    }

    /**
     * 회원의 유일한 고유값을 반환합니다.
     *
     * @return 회원아이디_기업키
     */
    public String getUniqueId() {
        String result = "";
        return result;
    }

    /**
     * 사용자 객체 반환
     *
     * @return LoginVO
     */
    public LoginVO getUserInfo() {
        return userInfo;
    }

    /**
     * 사용자 객체 설정
     *
     * @param userInfo
     * @return
     */
    public void setUserInfo(LoginVO userInfo) {
        this.userInfo = userInfo;
    }

    
    
    /**
     * 관리자 로그인 정보객체 반환
     * 
     */
    public LoginVO getAdminInfo() {
		return adminInfo;
	}
    /**
     * 관리자 로그인 정보 설정 객체
     * 
     */
	public void setAdminInfo(LoginVO adminInfo) {
		this.adminInfo = adminInfo;
	}

	/**
     * 관리자 메뉴 목록 반환
     *
     * @param
     * @return List<MenuManageVO>
     */
    public List<MenuManageVO> getAdminMenuList() {
        return adminMenuList;
    }

    /**
     * 관리자 메뉴 목록 설정
     *
     * @param List<MenuManageVO>
     * @return
     */
    public void setAdminMenuList(List<MenuManageVO> tmpMenuList) {
        if (tmpMenuList != null) {
            this.adminMenuList = new ArrayList<MenuManageVO>();
            int i = 0;
            for (MenuManageVO vo : tmpMenuList) {
                this.adminMenuList.add(i++, vo);
            }
        } else {
            this.adminMenuList = null;
        }

    }

    /**
     * 사용자 메뉴 목록 반환
     *
     * @param
     * @return List<MenuManageVO>
     */
    public List<MenuManageVO> getUserMenuList() {
        return userMenuList;
    }

    /**
     * 사용자 메뉴 목록 설정
     *
     * @param List<MenuManageVO>
     * @return
     */
    public void setUserMenuList(List<MenuManageVO> tmpMenuList) {
        if (tmpMenuList != null) {
            this.userMenuList = new ArrayList<MenuManageVO>();
            int i = 0;
            for (MenuManageVO vo : tmpMenuList) {
                this.userMenuList.add(i++, vo);
            }
        } else {
            this.userMenuList = null;
        }
    }

    public List<String> getMyOrgList() {
        return myOrgList;
    }

    public void setMyOrgList(List<String> myOrgList) {
        this.myOrgList = myOrgList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
