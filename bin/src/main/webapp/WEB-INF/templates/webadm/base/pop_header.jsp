<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String context = request.getContextPath() + com.hisco.cmm.util.Config.ADMIN_ROOT + "/resources";
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>남양주시청 주민자치센터 평생교육포털 관리자</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="shortcut icon" href="<%=context %>/images/layout/favicon.ico" type="image/x-icon">
  <link rel="apple-touch-icon" href="<%=context %>/images/layout/apple-touch-icon.png">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<%=context %>/plugins/fontawesome-free/css/all.min.css">
    <!--  datepicker-->
  <link rel="stylesheet" href="<%=context %>/plugins/bootstrap-datepicker/dist/css/bootstrap-datepicker3.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="<%=context %>/dist/css/adminlte.css">
  <link rel="stylesheet" href="<%=context %>/css/showLoading.css">
  <link rel="stylesheet" href="<%=context %>/css/common.css">

	<script src="/resources/js/jquery-x.x.x.min.js"></script>

    <!-- jQuery -->
    <!--
	<script src="<%=context %>/plugins/jquery/jquery.min.js"></script>
	-->

	<!-- Bootstrap -->
	<script src="<%=context %>/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- jQuery UI -->
	<script src="<%=context %>/plugins/jquery-ui/jquery-ui.min.js"></script>

	<!-- AdminLTE App -->
	<script src="<%=context %>/dist/js/adminlte.min.js"></script>

	<!-- AdminLTE for demo purposes -->
	<script src="<%=context %>/dist/js/demo.js"></script>

	<!--  datepicker-->
	<script src="<%=context %>/plugins/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript" src="<%=context %>/js/jquery.showLoading.min.js"></script>
	<script type="text/javascript" src="<%=context %>/js/common.js"></script>
	<script type="text/javascript" src="<%=context %>/js/EgovCmmUtil.js"></script>

</head>