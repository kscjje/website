<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<script type="text/javaScript" language="javascript">

	var element;

	function fn_login_action() {
		if (document.loginForm.name.value =="") {
	        alert("이름을 입력해 주세요.");
	        $("#name").focus();
	        return ;
	    } else if (document.loginForm.birthDate.value =="" || document.loginForm.birthDate.value.length != 8) {
	        alert("생년월일 8자리를 입력해 주세요.(ex.19910212 )");
	        $("#birthDate").focus();
	        return ;
	    } else if (document.loginForm.ihidNum.value =="") {
	        alert("휴대폰번호를 입력해 주세요(- 없이 숫자만)");
	        $("#ihidNum").focus();
	        return ;
	    } else if (isNaN(document.loginForm.ihidNum.value)) {
	        alert("휴대폰번호는 - 없이 숫자만 입력해 주세요");
	        $("#ihidNum").focus();
	        return ;
	    } else {

	    	$.ajax({
				url : "./nonLoginAction.json",
				data : $('#loginForm').serialize(),
				type : 'POST',
				success : function (data) {
					if (data.result.success) {
				        window.location.replace("../mypage/myRsvn/myRsvnList");
				     } else {
				    	alert(data.result.msg);
				     }
				},
				error : function (result) {
					console.log(result);
					alert("일시적인 오류가 발생하였습니다. 잠시후 다시 신청해 주세요.");
				}
			});
	    }

	}
  	$(document).ready(function() {
		$('#name').focus();

		$('#ihidNum').keydown(function() {
			if (event.keyCode==13) {
				fn_login_action();
			}
		});

  	});
</script>
</head>
<body>

			<div class="sub_visual mypage">
				<div class="inner">
					<h2>로그인</h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>

			<div id="content" class="sub_content">
				<div class="sub_top type02">
					<h3>비회원 수강신청 현황</h3>
				</div>
				<!-- 비회원 수강신청 현황 type02 클래스 추가 -->
<form name="loginForm" id="loginForm" method="post">
				<div class="pw_box type02">
					<div>
						<dl>
							<dt><span> 이름<em class="star">*</em></span></dt>
							<dd>
								<div class="inputbox">
									<label for="name" class="hidden"></label>
									<input type="text" title="" placeholder="수강신청시 입력한 이름을 입력해주세요." id="name" name="name">
								</div>
							</dd>
						</dl>
						<dl>
							<dt><span> 생년월일<em class="star">*</em></span></dt>
							<dd>
								<div class="inputbox">
									<label for="birthDate" class="hidden">생년월일 입력</label>
									<input type="tel" title="" placeholder="생년월일 8자리 (ex.19910212)" id="birthDate" name="birthDate" maxlength="8">
								</div>
							</dd>
						</dl>
						<dl>
							<dt><span> 휴대폰번호<em class="star">*</em></span></dt>
							<dd>
								<div class="inputbox">
									<label for="ihidNum" class="hidden">휴대폰번호 입력</label>
									<input type="tel" title="" placeholder="(-)없이, 숫자로 입력하세요" id="ihidNum" name="ihidNum">
								</div>
							</dd>
						</dl>
					</div>
					<a href="javascript:fn_login_action()" class="btn_s1_c1">확인</a>
				</div>
</form>
			</div>

</body>