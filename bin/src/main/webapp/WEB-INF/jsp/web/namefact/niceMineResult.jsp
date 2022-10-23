<%
 /**
  * @Class Name : niceMineResult.jsp
  * @Description : 본인인증  리턴
  * @Modification Information
  * @
  * @  수정일            수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.04  전영석                  최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.04
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.hisco.cmm.util.Config" %>
<%@ include file="../../include/top.jsp"%>

		<form:form commandName="memberVO" id="memberVO" name="memberVO" action="./findPasswdSave" method="post">	
		
			<input type="hidden" name="inputVal" id="inputVal" />
			
			<div class="sub_cont" id="container">
				<div class="inner">
					<h3 class="tit mb70">비밀번호 재설정</h3>
					<div class="mypage_wrap bor">
						<!-- signup -->
						<div class="signup2">
							<p class="stit">비밀번호 재설정 </p>
							<div class="table2 responsive-type mb50">
								<table>
									<caption>비밀번호 재설정을 위한 입력폼입니다.</caption>
									<colgroup>
										<col style="width:20%">
										<col style="width:80%">
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="vtop"><label for="new_password">새 비밀번호 <strong class="red">*</strong></label></th>
											<td>
												<input type="password" id="new_password" name="new_password" placeholder="비밀번호를 입력해주세요." />
												<p class="green">보안수준 : 보통</p>
											</td>
										</tr>
										<tr>
											<th scope="row" class="vtop"><label for="new_password2">새 비밀번호 확인 <strong class="red">*</strong></label></th>
											<td>
												<input type="password" id="new_password2" name="new_password2" placeholder="비밀번호를 입력해주세요." />
												<p class="red">비밀번호가 일치하지 않습니다.</p>
											</td>
										</tr>
									</tbody>
								</table>
								<!-- //table -->
							</div>
							<p class="desc red">※ 영문, 숫자, 특수문자 조합하여 9자부터 16까지 가능합니다.</p>
						</div>
						<!-- //signup -->
					</div>
					<ul class="btn_area">
						<li><a href="#" onclick="fn_member_submit(); return false;" class="btn_ty_m_c1">비밀번호 변경</a></li>
					</ul>
				</div>
				<!-- //inner -->
			</div>
			<!-- //sub_cont -->
			
		</form:form>	
			
<script>

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
	
</script>