<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
	<decorator:usePage id="thePage"/>
	<title>남양주시 주민자치센터 평생교육포털 관리자<decorator:title/></title>	
	<%@ include file="../../templates/webadm/base/header.jsp"%>
	<decorator:head/>
</head>
	
<body class="hold-transition sidebar-mini text-sm">
    <div class="wrapper">
		<%@ include file="../../templates/webadm/base/left.jsp"%>
		<div class="content-wrapper">
			<decorator:body/> <%//<section..> ~ </section> %>
		</div>
		<%@ include file="../../templates/webadm/base/footer.jsp"%>
		<div class="control-sidebar-bg"></div>
	</div>
</body>
</html>