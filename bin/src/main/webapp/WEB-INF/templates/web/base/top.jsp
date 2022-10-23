<%
 /**
  * @Class Name : top.jsp
  * @Description : top 화면 JSP
  * @Modification Information
  * @
  * @  수정일              수정자              수정내용
  * @ -------      --------    ---------------------------
  * @ 2021.04.19    전영석             최초 생성
  *
  *  @author 전영석
  *  @since 2021.04.19
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="egovframework.com.sym.mnu.mpm.service.MenuManageVO,com.hisco.cmm.util.Config" %>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>

<%

	com.hisco.cmm.object.UserSession userVO = (com.hisco.cmm.object.UserSession)session.getAttribute(Config.USER_SESSION);

	egovframework.com.cmm.LoginVO loginVO   = null;
	
	String strIsLogin = "N";
	String strLoginNm = "";
	String strLoginId = "";
	
	if (userVO == null) {
		System.out.println("userVO is null");
	} else {
		loginVO = userVO.getUserInfo();
		if (loginVO == null) {
			System.out.println("loginVO is null");
			strLoginId = "";
		} else {
			strIsLogin = "Y";
			strLoginNm = loginVO.getName();
			strLoginId = loginVO.getId();
		}
	}

	pageContext.setAttribute("isLogin", strIsLogin);
	pageContext.setAttribute("loginUserNm", strLoginNm);	
	pageContext.setAttribute("loginUserId", strLoginId);
	pageContext.setAttribute("menuList", userVO.getUserMenuList());
	pageContext.setAttribute("menuListDtl", userVO.getUserMenuList());
	
	System.out.println(userVO.getUserMenuList());
	
	String reqURL = request.getRequestURI();

%>

	