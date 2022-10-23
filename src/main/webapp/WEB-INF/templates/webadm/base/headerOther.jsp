<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="com.hisco.cmm.config.DynamicConfigUtil" %>
<%
	WebApplicationContext wapContext = org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	DynamicConfigUtil configUtil = (DynamicConfigUtil) wapContext.getBean("dynamicConfigUtil");
	String adminRoot = configUtil.getAdminPath(request);
	String strContext = request.getContextPath() + adminRoot + "/resources";
%>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="shortcut icon" href="<%=strContext %>/images/layout/favicon.ico" type="image/x-icon">
  <link rel="apple-touch-icon" href="<%=strContext %>/images/layout/apple-touch-icon.png">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<%=strContext %>/plugins/fontawesome-free/css/all.min.css">
  <!--  datepicker-->
  <link rel="stylesheet" href="<%=strContext %>/plugins/bootstrap-datepicker/dist/css/bootstrap-datepicker3.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="<%=strContext %>/dist/css/adminlte.css">
  <link rel="stylesheet" href="<%=strContext %>/css/showLoading.css">
  <link rel="stylesheet" href="<%=strContext %>/css/common.css">
  <!-- jQuery -->
  
	<script src="<%=strContext %>/plugins/jquery/jquery-x.x.x.min.js"></script>
	<!-- Bootstrap -->
	<script src="<%=strContext %>/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- jQuery UI -->
	<script src="<%=strContext %>/plugins/jquery-ui/jquery-ui.min.js"></script>
	<!-- AdminLTE App -->
	<script src="<%=strContext %>/dist/js/adminlte.min.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="<%=strContext %>/dist/js/demo.js"></script>
	 <!--  datepicker-->
	<script src="<%=strContext %>/plugins/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript" src="<%=strContext %>/js/jquery.showLoading.min.js"></script>
	<script type="text/javascript" src="<%=strContext %>/js/common.js"></script>
	<script type="text/javascript" src="<%=strContext %>/js/EgovCmmUtil.js"></script>
	<script>
		var ADMIN_ROOT = "${ADMIN_ROOT}";
			
		$.ajaxSetup({
			dataType : "json"
			, headers : {
				"${_csrf.headerName}" : "${_csrf.token}"
			}
		});
	</script>