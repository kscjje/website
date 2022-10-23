<%
 /**
  * @Class Name : changePasswd.jsp
  * @Description : 비밀번호 변경 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.19    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.08.19
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<script>
	function fn_passwd_check() {
		var newPassword = $('#new_password').val();
		var msg = passwdValidation(newPassword);
		if (msg == '사용가능한 비밀번호 입니다.') {
			$('#errPass').removeClass('red').addClass('green').html('사용가능한 비밀번호 입니다.');
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
		if ($('#password').val() == "") {
			$('#errPass3').html("현재 비밀번호를 입력해 주세요.");
			$('#password').focus();
			flag= false;
		}

		if (flag) {
			 document.loginVO.submit();
		}
	}
</script>
<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit">회원정보 수정</h3>
		<ul class="tab tab3">
			<li ><a href="./myInforRegist">회원정보 수정</a></li><!-- 현재위치 class: on 추가 -->
			<li class="on"><a >비밀번호 변경</a></li>
			<li><a href="./changeMycar">내 차량정보</a></li>
			<li><a href="./outMember">회원탈퇴</a></li>
		</ul>
<form:form commandName="loginVO" name="loginVO" id="loginVO" action="./changePasswdSave" method="post">
<form:hidden path="birthDate"/>
<form:hidden path="id"/>
<form:hidden path="hp"/>
		<!-- mypage_wrap -->
		<div class="mypage_wrap bor">
			<!-- signup -->
			<div class="signup2">
				<p class="stit">비밀번호 변경</p>
				<div class="table2 responsive-type mb50">
					<table>
						<caption>비밀번호 변경을 위한 입력폼입니다.</caption>
						<colgroup>
							<col style="width:25%">
							<col style="width:75%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop"><label for="password">현재 비밀번호</label></th>
								<td>
									<input type="password" id="password" name="password" placeholder="현재 비밀번호를 입력해주세요." onkeyup="fn_reset_msg()">
									<p id="errPass3" class="errMsg red"></p>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop"><label for="new_password">새로운 비밀번호</label></th>
								<td>
									<input type="password" id="new_password" name="new_password"  onkeyup="fn_passwd_check()" placeholder="새로운 비밀번호를 입력해주세요." maxlength="20">
									<p>※ 영문, 숫자, 특수문자 중 2종류 이상 8 ~ 20자 이내로 입력해 주세요.</p>
									<p>※ 사용하실 수 없는 비밀번호
									<p>- 아이디와 3자리 이상이 포함된 경우</p>
									<p>- 핸드폰의  마지막 4자리 또는 8자리가 포함된 경우</p>
									<p>- 생일의 년도 또는 생일의 월일 4자리가 포함된 경우</p>
									<p>- 3자 이상의 연속 또는 반복 문자 및 숫자가 포함된 경우</p>
									<p class="green" id="errPass"></p>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop"><label for="new_password2">비밀번호 확인</label></th>
								<td>
									<input type="password" id="new_password2" name="new_password2" onkeyup="fn_passwd_confirm()"  placeholder="비밀번호를 재입력해주세요." maxlength="20">
									<p id="errPass2" class="errMsg red"></p>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- //table -->
				</div>
			</div>
			<!-- //signup -->
		</div>
		<!-- //mypage_wrap -->
		<ul class="btn_area">
			<li><a href="javascript:fn_member_submit()" class="btn_ty_m_c1">변경 완료</a></li>
		</ul>
		<!-- //btn_area -->
</form:form>
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->