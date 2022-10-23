<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<c:set var="html_mode_edit" value="true"/>
	<%@ include file="inc.header_resource.jsp" %>
	
	<c:if test="${fn:length(fn:trim(document_title)) <= 0 && MenuData != null}">
		<c:set var="document_title" value="${MenuData.name}"/>
	</c:if>
	
	<title><c:out value="${document_title}" default="제목 없음"/></title>
	
	<!-- 
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/web/common/editor/pagemaker/css/pagemaker_view.css"/>"/>
	-->
	
</head>
<body class="single">

	<div id="contents" class="contents">
		<tiles:insertAttribute name="body" ignore="true" />
	</div>
	
</body>
</html>