<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
	<decorator:usePage id="thePage"/>
	<title>남양주시 평생교육포털<decorator:title/></title>
	<%@ include file="../../templates/web/base/header.jsp"%>
	<decorator:head/>
</head>

<body class="main">
	<%@ include file="../../templates/web/base/jump_menu.jsp"%>
	<div id="wrap" class="index">
		<%@ include file="../../templates/web/base/gnb.jsp"%>
		<div id="main">
			<decorator:body/>
		    <%@ include file="../../templates/web/base/footer.jsp"%>
		<div class="dim">딤</div>
		</div>
	</div>
</body>
</html>