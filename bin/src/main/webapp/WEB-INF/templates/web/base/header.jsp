<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	com.hisco.cmm.object.UserSession userVO = (com.hisco.cmm.object.UserSession)session.getAttribute(com.hisco.cmm.util.Config.USER_SESSION);
	egovframework.com.cmm.LoginVO loginVO   = null;

	String strIsLogin = "N";
	String strLoginNm = "";
	String strLoginId = "";

	if (userVO != null) {
		loginVO = userVO.getUserInfo();
		if (loginVO == null || !loginVO.isMember()) {
			strLoginId = "";
		} else {
			strIsLogin = "Y";
			strLoginNm = loginVO.getName();
			strLoginId = loginVO.getId();
		}
	}

	pageContext.setAttribute("isLogin", strIsLogin);
	pageContext.setAttribute("loginUserNm", strLoginNm);
	pageContext.setAttribute("loginUserId", strLoginId);
	pageContext.setAttribute("menuList", (userVO == null)? null:userVO.getUserMenuList());
	pageContext.setAttribute("domain" ,  egovframework.com.cmm.service.EgovProperties.getProperty("Globals.Domain"));
	//pageContext.setAttribute("loginVO" ,  loginVO);


	String reqURL = request.getRequestURI();

	// 통합검색 검색어
	String _qt_ = request.getParameter("_qt_");
	pageContext.setAttribute("_qt_", _qt_);
%>

	<meta charset="utf-8">
	<title>남양주시청 주민자치센터 평생교육포털</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport"
		content="user-scalable=yes, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
	<meta name="format-detection" content="telephone=no" />
  	<meta property="og:title" content="남양주시청 주민자치센터 평생교육포털">
  	<meta property="og:url" content="http://www.hisco.co.kr/web/main/">
  	<meta property="og:image" content="http://www.hisco.co.kr/web/resources/images/layout/open_logo.png">
 	<meta property="og:description" content="남양주시청 주민자치센터 평생교육포털 ">
	<link rel="shortcut icon" href="${requestScope.resourceContext}/favicon.ico" type="image/x-icon">
	
	
	  <link rel="shortcut icon" href="${requestScope.resourceContext}/images/favicon.ico" type="image/x-icon">
	  <!-- style -->
	  <link rel="stylesheet" href="${requestScope.resourceContext}/css/jquery-ui.min.css">
	  <link rel="stylesheet" href="${requestScope.resourceContext}/css/bootstrap.css">
	  <link rel="stylesheet" href="${requestScope.resourceContext}/css/bootstrap-datepicker.css">
	
	  <link rel="stylesheet" href="${requestScope.resourceContext}/css/swiper.min.css">
	  <link rel="stylesheet" href="${requestScope.resourceContext}/css/default.css">
	  <link rel="stylesheet" href="${requestScope.resourceContext}/css/template.css">
	  <link rel="stylesheet" href="${requestScope.resourceContext}/css/common.css">
	  <link rel="stylesheet" href="${requestScope.resourceContext}/css/layout.css">
	  <link rel="stylesheet" href="${requestScope.resourceContext}/css/main.css">
	  <link rel="stylesheet" href="${requestScope.resourceContext}/css/sub.css">
	  <!-- //style -->
	  <!-- script -->
	  <script src="${requestScope.resourceContext}/js/jquery-3.6.0.min.js"></script>
	  <script src="${requestScope.resourceContext}/js/jquery-ui.min.js"></script>
	  <script src="${requestScope.resourceContext}/js/jquery.rwdImageMaps.min.js"></script>
	  <script src="${requestScope.resourceContext}/js/bootstrap.min.js"></script>
	  <script src="${requestScope.resourceContext}/js/bootstrap-datepicker.js"></script>
	  <script src="${requestScope.resourceContext}/js/bootstrap-datepicker.kr.min.js"></script>
	  <script src="${requestScope.resourceContext}/js/swiper-bundle.min.js"></script>
	  <script src="${requestScope.resourceContext}/js/layout.js"></script>
	  <script src="${requestScope.resourceContext}/js/main.js"></script>
	  <script src="${requestScope.resourceContext}/js/sub.js"></script>
	
	
	<script src="${requestScope.resourceContext}/js/common_web.js"></script>
	
	
	
	
	<!-- custom js 추가 -->
	<% /* loading.gif 이미지도 context path 설정에 따라 변경되도록 수정 */ %>
	<script>
	var ajaxLoadingImg = "${requestScope.resourceContext}/images/common/loading.gif";
	</script>
	<link rel="stylesheet" href="${requestScope.resourceContext}/css/showLoading.css">
	<script type="text/javascript" src="${requestScope.resourceContext}/js/jquery.showLoading.min.js"></script>

	<sec:csrfMetaTags />

	<script>
		$.ajaxSetup({
			dataType : "json"
			, type : "GET"
			, cache : false
			, headers : {
				"${_csrf.headerName}" : "${_csrf.token}"
			}
			, beforeSend : function() {
				//$('body').showLoading();
			}
			, complete : function() {
				//$('body').hideLoading();
			}
			, error: function(res) {
				if(res.status == 403) {
					alert("권한이 없습니다. 로그인 후 이용해주세요.");
			<%-- location.href = "/nwll/web/member/login" --%>
				} else {
					alert("서버 에러");	
				}
			}
		});

		/*스크롤 탑 버튼*/
		function mediaq() {
			var windowWidth = $( window ).width();
		    if(windowWidth < 990) {
					if ($(this).scrollTop() > 200) {
		        $('.top_btn').fadeIn();
		    } else {
		        $('.top_btn').fadeOut();
		    }
		    } else {
					$('.top_btn').fadeOut();
		    }
		}

		$('.top_btn a').click(function (e) {
			e.preventDefault();
		   $('html,body').animate({ scrollTop: 0 }, 300);
		});

		function setResponsive() {
		    mediaq();
		}

		$(window).on('load', function () {
		    setResponsive();
		});

		$(window).on('resize', function () {
		    setResponsive();
		});

		/* 통합검색 추가 - 이윤호 */
	  	$(document).ready(function() {
	  		// 검색창에서 엔터
			$('.gnb-search').keydown(function() {
				if (event.keyCode==13) {
					var searchVal = (jQuery(this).val() || "").trim();
					if(searchVal){
						gnbSearch(searchVal);
					}
				}
			});

			// 검색버튼 클릭
			$( ".gnb-search-btn" ).on( "click", function() {
				var searchVal = (jQuery( this ).parent().find('.gnb-search').val() || "").trim();
				if(searchVal){
					gnbSearch(searchVal);
				}
			});
		});

		/* 통합검색 */
		function gnbSearch(txt) {
			var timestamp = new Date().getTime();
			var url = "<c:url value="/web/edc/program/list"/>#searchTab=EDCANM&searchKeyword="+txt;
			var $form = $('<form/>').attr('action',url).attr('method','get');
			$('<input type="hidden" name="_t">').val(timestamp).appendTo($form);
			$form.appendTo('body').submit();
		}
		/* 로그인 */
		function doLogin(){
			window.location.href='/web/member/login';
		}
		
		function doLogout(){
			window.location.href = '/web/member/logout';
		}

	</script>
	<style>
	@media print
	{
	    .no-print, .no-print *
	    {
	        display: none !important; height:0px !important; padding-top:0px !important;margin-top:0px !important
	    }
		#main{padding-top:0px !important}
		.sub_visual {display: none !important;}
	}
	</style>
	
	
	