<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : errorPage.jsp
  * @Description : System Exception
  * @Modification Information
  * @
  * @  수정일         수정자      수정내용
  * @ ------------   --------    ---------------------------
  * @ 2020.10.08      전영석      최초 생성
  *
  *  @author 전영석
  *  @since 2020.10.08
  *  @version 1.0
  *  @see
  *
  */
%>
<%pageContext.setAttribute("crlf", "\r\n"); %>
<%
	String title = "남양주시 주민자치 포털";
	if (request.getAttribute("SELECTED_MENU_OBJ") != null) {
		egovframework.com.sym.mnu.mpm.service.MenuManageVO menuVO = (egovframework.com.sym.mnu.mpm.service.MenuManageVO)request.getAttribute("SELECTED_MENU_OBJ");
		if (menuVO != null) {
			title = menuVO.getMenuNm() + " | " + title;
		}
	}

	String realContext = request.getContextPath();
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
	String strCurrentUrl = String.valueOf(request.getAttribute("currentUrl"));
%>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<title><%=title%></title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport"
		content="user-scalable=yes, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
	<meta name="format-detection" content="telephone=no" />

	<link rel="stylesheet" type="text/css" href="<%=strContext%>/css/style.css">
	<script src="<%=strContext%>/js/jquery-1.12.4.min.js"></script>
	<script src="<%=strContext%>/js/jquery.popupoverlay.js"></script>
	<script src="<%=strContext%>/js/front_ui.js"></script>
	<script src="<%=strContext%>/js/main.js"></script>
	<script src="<%=strContext%>/js/slick.min.js"></script>
	<script src="<%=strContext%>/js/swiper.min.js"></script>
	<script src="<%=strContext%>/js/ui.js"></script>

	<script>

		if (location.href.indexOf("/webadm/") > -1) {
			document.title = "SPOWISE CMS 관리자 페이지";
		}

		function fn_goNextUrl() {
			if (location.href.indexOf("/web/") > -1) {
				location.href = "<%=realContext%>/web/main";
			} else if (location.href.indexOf("/webadm/") > -1) {
				document.title = "SPOWISE CMS 관리자 페이지";
				location.href = "<%=realContext%>/webadm/login";
			}
		}
	</script>
	<style>
		#wrap{background-color:#fff}
		
		
		/*error*/
		.error {display: flex;justify-content: center;align-items: center;flex-direction: column;height:100vh;background:url(/web/resources/images/common/bg_full.jpg) center no-repeat;background-size:contain;text-align: center;}
		.error .title {font-size:39px;color:#222;line-height: 1;margin-bottom:15px;font-weight: 700;}
		.error p {font-size: 21px;color:#666;}
		.error a {text-align: center;margin-top:118px;}
				
		[class^="btn_"][class*="_c1"] {
		    background: #004e9e;
		    border: 1px solid #004e9e;
		    color: #fff;
		}				
		[class^="btn_"][class*="_s3"] {
		    min-width: 100px;
		    height: 55px;
		    padding: 0 20px;
		    line-height: 55px;
		    border-radius: 10px;
		    font-size: 18px;
		}
		[class^="btn_"] {
		    display: inline-block;
		}		
		a, a:hover, a:link, a:visited {
		    text-decoration: none;
		    color: inherit;
		}		
	</style>
</head>
<body>
	<div id="wrap" class="index">


		<div id="main" class="pt0">
			<div class="error" >
				<p class="title">
			<c:choose>
				<c:when test="${empty status}">
					예상치 못한 오류가 발생하였습니다.
				</c:when>
				<c:when test="${status eq '400' }">
					잘못된 요청입니다.
				</c:when>
				<c:when test="${status eq '403' }">
					접근 기한이 만료되었습니다. 처음부터 다시 시도해 주세요.
				</c:when>
				<c:when test="${status eq '404' }">
					페이지를 찾을 수 없습니다.
				</c:when>
				<c:when test="${status eq '500' }">
					서버 내부 에러입니다.
				</c:when>
				<c:when test="${status eq '503' }">
					서비스가 준비 중입니다.
				</c:when>
				<c:otherwise>
					예상치 못한 오류가 발생하였습니다.
				</c:otherwise>
			</c:choose>
				</p>
				<p>예약사이트 홈으로 이동 또는 잠시 후 다시 접속 해주시기 바랍니다.</p>
				<a  href="#" onclick="fn_goNextUrl(); return false;" class="btn_s3_c1 ">홈으로</a>
			</div>


		</div>
		<!-- //main -->
		<div class="dim">딤</div>

	</div>

<script>
	/*스크롤 탑 버튼*/
function mediaq(){
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


</script>
</body>
</html>