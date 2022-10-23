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
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>SPOWISE CMS</title>
  <!-- Tell the browser to be responsive to screen width -->

  <sec:csrfMetaTags />

  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="shortcut icon" href="<%=strContext%>/images/layout/favicon.ico" type="image/x-icon">
  <link rel="apple-touch-icon" href="<%=strContext%>/images/layout/apple-touch-icon.png">
  <link rel="stylesheet" href="<%=strContext%>/css/style.css">
  <link rel="stylesheet" href="<%=strContext%>/css/showLoading.css">
    <!-- jQuery -->

<!-- login api -->
<script src="<%=strContext%>/js/jquery-x.x.x.min.js"></script>
<script src="<%=strContext%>/js/jquery.easing.1.3.js"></script>
<script src="<%=strContext%>/js/common_nsm.js"></script>
<script src="<%=strContext%>/js/swiper.min.js"></script>
<script src="<%=strContext%>/js/jquery-ui.min.js"></script>
<script src="<%=strContext%>/js/jquery.showLoading.min.js"></script>

</head>