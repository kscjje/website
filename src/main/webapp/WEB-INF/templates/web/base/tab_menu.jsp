<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
com.hisco.cmm.object.UserSession userVO = (com.hisco.cmm.object.UserSession)session.getAttribute(com.hisco.cmm.util.Config.USER_SESSION);
pageContext.setAttribute("menuList", userVO.getUserMenuList());
%>
				<ul class="no-print tab_link link_go<c:if test="${SELECTED_MENU_OBJ.tabMenuCnt >= 8 }"> flex_wrap</c:if>">
<c:forEach items="${menuList}" var="item" varStatus="status">

	<c:if test="${item.upperMenuId == SELECTED_MENU_OBJ.upperMenuNo }">
					<li><a href="<c:url value="${item.menuUrl }"/>" <c:if test="${item.menuNo eq  SELECTED_MENU_OBJ.menuNo}">class="on"</c:if>>${item.menuNm }</a></li>
	</c:if>
</c:forEach>
				</ul>
				<!--// tab_link -->