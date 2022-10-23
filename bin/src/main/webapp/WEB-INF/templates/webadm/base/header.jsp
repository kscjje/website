<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="com.hisco.cmm.config.DynamicConfigUtil" %>
<%
	WebApplicationContext wapContext = org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	DynamicConfigUtil configUtil = (DynamicConfigUtil) wapContext.getBean("dynamicConfigUtil");
	String adminRoot = configUtil.getAdminPath(request);
%>
<c:set var="ADMIN_ROOT" value='<%=request.getContextPath() + adminRoot + "/"%>'/>
<c:set var="strContext" value='<%=request.getContextPath() + adminRoot + "/resources"%>'/>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">

  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="shortcut icon" href="${strContext}/images/layout/favicon.ico" type="image/x-icon">
  <link rel="apple-touch-icon" href="${strContext}/images/layout/apple-touch-icon.png">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="${strContext}/plugins/fontawesome-free/css/all.min.css">
  <link rel="stylesheet" href="${strContext}/plugins/jquery-ui/jquery-ui.min.css">
   <!-- iCheck for checkboxes and radio inputs -->
  <link rel="stylesheet" href="${strContext}/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
  <!-- Select2 -->
  <link rel="stylesheet" href="${strContext}/plugins/select2/css/select2.min.css">
  <link rel="stylesheet" href="${strContext}/plugins/select2/css/select2-bootstrap4.min.css">

  <!-- Theme style -->
  <link rel="stylesheet" href="${strContext}/dist/css/adminlte.css">
  <link rel="stylesheet" href="${strContext}/css/showLoading.css">
  <link rel="stylesheet" href="${strContext}/css/common.css">

  <style>
	.nav-flat.nav-sidebar > .nav-item .nav-treeview, .nav-flat.nav-sidebar > .nav-item > .nav-treeview {
	   background: rgba(255, 255, 255, 0);
	}
	@media print
	{
	    .no-print, .no-print *
	    {
	        display: none !important; height:0px !important; padding-top:0px !important;margin-top:0px !important
	    }
	}
  </style>

  	<!-- jQuery -->
	<script src="${strContext}/plugins/jquery/jquery-x.x.x.min.js"></script>

	<!-- Bootstrap -->
	<script src="${strContext}/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- jQuery UI -->
	<script src="${strContext}/plugins/jquery-ui/jquery-ui.min.js"></script>

	<!-- AdminLTE App -->
	<script src="${strContext}/dist/js/adminlte.min.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="${strContext}/dist/js/demo.js"></script>
	<script type="text/javascript" src="${strContext}/js/jquery.form.js"></script>
    <script type='text/javascript' src="${strContext}/js/jquery.inputmask.bundle.js"></script>
	<script type='text/javascript' src="${strContext}/plugins/select2/js/select2.full.min.js"></script>
	<script type="text/javascript" src="${strContext}/js/jquery.number.js"></script>
	<script type="text/javascript" src="${strContext}/js/jquery.showLoading.min.js"></script>
	<script type="text/javascript" src="${strContext}/js/common.js"></script>
	<script type="text/javascript" src="${strContext}/js/EgovCmmUtil.js"></script>
	
	<link rel="stylesheet" href="${strContext}/tui/css/tui-grid.css" />
	<link rel="stylesheet" href="${strContext}/tui/css/tui-pagination.css" />
	<link rel="stylesheet" href="${strContext}/tui/css/tui-date-picker.css" />

	<script src="${strContext}/tui/js/tui-pagination.js"></script>
	<script src="${strContext}/tui/js/tui-date-picker.js"></script>
	<script src="${strContext}/tui/js/tui-time-picker.js"></script>
	<script src="${strContext}/tui/js/tui-grid.js"></script>
	
	<script>
		var ADMIN_ROOT = "${ADMIN_ROOT}";

		var __goToLoginPage__ = function(){
			location.href = "${ADMIN_ROOT}login";
		};

		var __ajaxErrorCallback__ = function(response, fallbackMsg) {
			if(response.status == 403) {
				alert("접근 기한이 만료되었거나 권한이 없습니다.\n로그인 페이지로 이동합니다.");
				__goToLoginPage__();
			} else {
				if(fallbackMsg instanceof Function) {
					fallbackMsg(response);
				} else {
					alert(fallbackMsg);
				}

			}
		};

		var __ajaxHtmlCallback__ = function(){
			alert("로그아웃 상태 입니다.\n로그인 페이지로 이동합니다.");
			__goToLoginPage__();
		}

		var __dateDiffCheck__ = function(date1 , date2){
			if(date1 != "" && date2 != ""){
				var dateD1 = new Date(date1);
				var dateD2 = new Date(date2);

				if(dateD1 <= dateD2){
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
		}

		$.ajaxSetup({
			type : "GET"
			, dataType : "json"
			, cache : false
			, headers : {
				"${_csrf.headerName}" : "${_csrf.token}"
			}
			, error: function(res) {
				//debugger;
				__ajaxErrorCallback__(res, "에러 발생. 관리자에게 문의 주세요.");
			}
		});


		jQuery.fn.serializeJSON = function() {
			var $this = jQuery(this);
			var queryString = $this.serialize();

			var data = {}

			var kvarr = queryString.split("&");
			var kvarr_len = kvarr.length;

			if(kvarr && kvarr_len) {
				for(var i=0; i<kvarr_len; i++) {
					var kv = kvarr[i].split("=");
					if(kv.length == 2) {
						var k = kv[0];
						var v = decodeURIComponent(kv[1] || "");
						data[k] = v;
					}
				}
			}

			return data;
		};


		// content-header 부분 타이틀 수정 : menu타이틀 + jsp 소스 타이틀
		jQuery(document).ready(function(){
			var menuTitle = "${SELECTED_MENU_OBJ.getMenuNm() }";
			var jspTitle = $('.content-header > .container-fluid > .row > div:first-child > h1').text();

			var title = ""
			title += menuTitle;
			if($.trim(menuTitle).replace(/ /gi, "") != $.trim(jspTitle).replace(/ /gi, "")){
				title += "&nbsp;<span style='font-size:18px; color:#777;'> &gt; " + jspTitle + "</span>";
			}
			$('.content-header > .container-fluid > .row > div:first-child > h1').html(title)
			$('.content-header').css("visibility","visible");


			//날짜 버튼
			$('.fa-calendar').parent().parent().click(function(){
				var obj = $(this).parent().find(".datepicker");
				if(obj.prop("disabled") == false){
					obj.focus();
				}

			});
		});

	</script>
	<style>
		/* title 부분에 메뉴명을 추가 후 보이도록 설정*/
		.content-header {visibility :hidden;}
	</style>