<%
 /**
  * @Class Name : mainLayout.jsp
  * @Description : 메인 화면 JSP
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
<%@ page import="com.hisco.cmm.util.Config" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<%
	com.hisco.cmm.object.UserSession userVO = (com.hisco.cmm.object.UserSession)session.getAttribute(Config.USER_SESSION);
	egovframework.com.cmm.LoginVO user = userVO.getUserInfo();
%>

<tiles:insertAttribute name="header" />

<body>

<tiles:insertAttribute name="top" />
<tiles:insertAttribute name="body" />
<tiles:insertAttribute name="footer" />

</body>
</html>