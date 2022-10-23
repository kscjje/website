<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" 		uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page import="egovframework.com.sym.mnu.mpm.service.MenuManageVO" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%pageContext.setAttribute("crlf", "\r\n"); %>
<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<meta name="HandheldFriendly" content="true">
	<meta name="format-detection" content="telephone=no">
	<meta name="theme-color" content="#013668">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>SPOWISE CMS 설문</title>
	<link rel="stylesheet" href="/resources/css/srvStyle.css">
	<script type="text/javascript" src="/resources/js/jquery-x.x.x.min.js"></script>
	<script src="<%=strContext%>/js/common_nsm.js"></script>
</head>