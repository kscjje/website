<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : passwdChange.jsp
  * @Description : 회원서비스 비밀번호 변경 안내 페이지
  * @Modification Information
  * @
  * @  수정일             수정자               수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.14   전영석               최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.14
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<script>
	
	function fn_passwd_check() {
		
		$('#confirmPass').html("");
		
		var varConfirm_password = $("#confirm_password").val();
		if ((varConfirm_password == null) || (varConfirm_password == '') || (varConfirm_password === undefined)) {
			$('#confirmPass').html("기존 비밀번호를 입력하세요");
			$("#confirm_password").focus();
			return false;
		} 
		
		console.log(varConfirm_password);
		
		var newPassword = $('#new_password').val();
		var msg = passwdValidationNew(newPassword);
		if (msg == '사용가능한 비밀번호 입니다.') {
			$('#errPass').removeClass('red').addClass('green').html('사용 가능한 비밀번호 입니다.');
			return true;
		} else {
			$('#errPass').removeClass('green').addClass('red').html(msg);
	        $('#new_password').focus();
	        return false;
		}
		
	}
	
	function fn_passwd_confirm() {
		
		if ($('#new_password').val()  != $('#new_password2').val() ) {
			$('#errPass2').html("비밀번호와 비밀번호 확인값이 일치하지 않습니다.");
			$('#new_password2').focus();
			return false;
		} else {
			$('#errPass2').html("");
			return true;
		}
		
	}
	
	function fn_reset_msg() {
		$('#errPass3').html("");
	}
	
	function fn_member_submit() {
		
		var flag = true;
	
		if (!fn_passwd_confirm()) {
			flag = false;
		}
	
		if (!fn_passwd_check()) {
			flag = false;
		}
	
		if (flag) {
			document.memberVO.submit();
		}
	}

</script>

			<div class="sub_cont" id="container">
				<div class="inner">
					<h3 class="tit mb35">비밀번호 변경안내</h3>
					<p class="desc mb70">회원님께서는 3개월 동안 비밀번호를 변경하지 않고 동일 비밀번호를 사용 중이십니다. <br />회원님의 소중한 개인정보를 안전하게 보호하기 위해서 비밀번호 변경을 진행하여 주시기 바랍니다.<br />비밀번호 변경을 진행하지 않을 시 홈페이지 이용이 불가해집니다.</p>
					<div class="mypage_wrap bor">
						<!-- signup -->
						<div class="signup2">
						
							<form:form commandName="memberVO" id="memberVO" name="memberVO" action="./changePasswdSave" method="post">
						
								<input type="hidden" id="birthDate" name="birthDate" value="" />
								<input type="hidden" id="id"        name="id"        value="" />
						
								<p class="stit">비밀번호 재설정</p>
								<div class="table2 responsive-type mb50">
									<table>
										<caption>비밀번호 재설정을 위한 입력폼입니다.</caption>
										<colgroup>
											<col style="width:20%">
											<col style="width:80%">
										</colgroup>
										<tbody>
											<tr>
												<th scope="row" class="vtop"><label for="confirm_password">기존 비밀번호</label></th>
												<td>
													<input type="password" id="confirm_password" name="confirm_password" placeholder="비밀번호를 입력해주세요." />
													<!-- <p class="red">비밀번호가 일치하지 않습니다.</p> -->
													<p id="confirmPass" class="errMsg red"></p>
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop"><label for="new_password">새 비밀번호</label></th>
												<td>
													<input type="password" id="new_password" name="new_password" placeholder="비밀번호를 입력해주세요." />
													<!-- <p class="green">보안수준 : 보통</p> -->
													<p id="errPass" class="errMsg red"></p>
												</td>
											</tr>
											<tr>
												<th scope="row" class="vtop"><label for="pass2">새 비밀번호 확인</label></th>
												<td>
													<input type="password" id="new_password2" name="new_password2" placeholder="비밀번호를 입력해주세요." />
													<!-- <p class="red">비밀번호가 일치하지 않습니다.</p> -->
													<p id="errPass2" class="errMsg red"></p>
												</td>
											</tr>
										</tbody>
									</table>
									<!-- //table -->
								</div>
								<p class="desc red">※ 영문, 숫자, 특수문자 조합하여 9자부터 16까지 가능합니다.</p>
							
							</form:form>
							
						</div>
						<!-- //signup -->
					</div>
					<ul class="btn_area">
						<li><a href="javascript:fn_member_submit()" class="btn_ty_m_c1">비밀번호 변경</a></li>
						<li><a href="/web/main" class="btn_ty_m_c4">다음에 변경하기</a></li>
					</ul>
				</div>
				<!-- //inner -->
			</div>
			<!-- //sub_cont -->
			
	<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>			
			
<script>

var varUserId = "${loginVO.id}";

if (varUserId == null) {
	alert("세션이 만료되었습니다.");
	top.location.href="/web/main";
} else {
	$("#id").val("${loginVO.id}");
	$("#birthDate").val("${loginVO.birthDate}");	
}

</script>			
			
			