<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<%
		String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
	%>
	
	<link rel="apple-touch-icon" href="<%=strContext%>/images/layout/apple-touch-icon.png">
	<link rel="stylesheet" href="<%=strContext%>/css/style.css">
	<link href="<%=strContext%>/site/css/style.css" rel="stylesheet" type="text/css" />

	<script src="<%=strContext%>/js/jquery-x.x.x.min.js"></script>
	<script src="<%=strContext%>/js/jquery.easing.1.3.js"></script>
	<script src="<%=strContext%>/js/front_ui.js"></script>
	<script src="<%=strContext%>/js/common_web.js"></script>
	<script src="<%=strContext%>/js/swiper.min.js"></script>
	<script src="<%=strContext%>/site/js/slick.js"></script>
	<script src="<%=strContext%>/site/js/ui.js"></script>
	<script>		
		$.ajaxSetup({
			dataType : "json"
			, headers : {
				"${_csrf.headerName}" : "${_csrf.token}"
			}
		});
	</script>
