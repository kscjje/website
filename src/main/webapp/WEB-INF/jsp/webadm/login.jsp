<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
 /**
  * @Class Name : login.jsp
  * @Description : 관리자 로그인 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.06.08    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.06.08
  *  @version 1.0
  *  @see
  *
  */

%>

<head>
<link rel="stylesheet" href="./resources/css/layout.css"'>

<script type="text/javaScript" language="javascript">
	var LOGIN_TYPE_SELECTED_CLASS  = "login_type_selected";

	$(document).ready(function() {
		fnInit();
	});

	function fn_login_action() {
		if (document.loginForm.id.value =="") {
	        alert("아이디를 입력하세요"); <%-- 아이디를 입력하세요 --%>
	    } else if (document.loginForm.password.value =="") {
	        alert("비밀번호를 입력하세요"); <%-- 비밀번호를 입력하세요 --%>
	    } else {
	        document.loginForm.submit();
	    }
	}

	function fnInit() {

		/* if (document.getElementById('loginForm').message.value != null) {
		    var message = document.getElementById('loginForm').message.value;
		} */
		$(".login_type").on("click", function(e){
			var $this = $(this);
			if(!$this.hasClass(LOGIN_TYPE_SELECTED_CLASS)) {
				$("."+LOGIN_TYPE_SELECTED_CLASS).removeClass(LOGIN_TYPE_SELECTED_CLASS);
				$this.addClass(LOGIN_TYPE_SELECTED_CLASS);

				var userType = $this.attr("user-type");
				$("#userType").val(userType);
				<%
				/*
				로그인 타입 변경시 로그인폼 값 초기화
				$("#id").val("");
				$("#password").val("");
				*/
				%>
			}
		});

		$('#id').focus();

	     if ("<c:out value='${message}'/>" != "") {
	        alert("<c:out value='${message}'/>");
	    }

	    <c:if test="${param.login_error eq '1'}">
	    alert("로그인 정보가 올바르지 않습니다.");
	    </c:if>

	    <c:if test="${param.login_error eq 'P'}">
	    alert("패스워드는 8 ~ 20 자리 입니다.");
	    </c:if>

	    <c:if test="${param.login_error eq 'D' and userType eq '1001'}">
	    	alert("외부기관 관리자 입니다. \n외부기관 로그인 페이지로 이동합니다.");
	   		 window.location.replace("..${managerRoot}/login");
	    </c:if>
	}
</script>
</head>

<body>
	<!-- javascript warning tag  -->
	<noscript class="noScriptTitle">스크립트 실행안됨</noscript>
	<!-- login_area -->
	<div class="login_area">
	    <!-- login_cont -->
	    <div class="login_cont">
	    	<h1 class="login_logo">남양주시주민자치센터 평생교육포털 통합예약서비스 관리자</h1>
	        <!-- login_cont_box -->
	        <div class="login_cont_box">
	        	<div class="login_type_box">
<c:choose>
	<c:when test="${isManager }">
	        	 	<div class="login_type manager" >외부기관 관리자 사이트</div>
	</c:when>
	<c:otherwise>
	        		<div class="login_type webadm" >관리자 사이트</div>
	</c:otherwise>
</c:choose>
	        	 </div>
				<div class="t_date">오늘일자
				<c:out value="${sysNow}"/>
				</div>
					<form name="loginForm" id="loginForm" action="./actionLogin" method="post">
						<input type="hidden" id="message" name="message" value="<c:out value='${message}'/>">
						<input type="hidden" name="userSe" value="<%=com.hisco.cmm.util.Config.COM_CD%>"/><% // 기관코드 %>
						<input type="hidden" name="userType" id="userType" value="${userType }" />	<%//구청관리자: 1001 , 외부기관관리자: 2001 %>
						<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
		                <fieldset>
		                    <ul class="login_box">
		                        <li><input type="text" name="id" id="id" class="input_ty" placeholder="아이디" title="아이디 입력" autocomplete="off"></li>
		                        <li><input type="password" name="password" id="password" maxlength="20" onkeydown="if (event.keyCode==13)fn_login_action();" class="input_ty" placeholder="비밀번호" title="비밀번호 입력"></li>

		                    	<li style="margin-top:30px">
		                    	 <button type="button" class="btn_ty_b_c1 btn-block" onclick="fn_login_action()" >로그인</button>
		                    	</li>
		                    </ul>

		               	    <div class="inq_box">
								시스템 관련 문의 : 02-2116-3983
		               	    </div>
		                </fieldset>
					</form>
	        </div>
	        <!-- //login_cont_box -->
	        <p class="copy">COPYRIGHT BY 1992 HISCO. ALL RIGHTS RESERVED.</p>
	    </div>
	    <!-- //login_cont -->
	</div>
	<!-- //login_area -->
</body>