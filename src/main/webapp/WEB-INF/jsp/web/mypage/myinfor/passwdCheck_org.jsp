<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : passwdCheck.jsp
  * @Description : 비밀번호 확인 페이지
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

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<script>

	function fn_member_submit() {
		
		if ($('#password').val() == "") {
			alert("비밀번호를 입력해 주세요.");
			$("#password").focus();
		} else {
			document.loginVO.submit();
		}
		
		return false;
	}
	
	setTimeout(function() {
		$("#password").focus();	
	}, 300);
	
	function fn_member_cancel() {
		if (confirm("취소하시겠습니까?")) {
			top.location.href="/web/main";
		}
	}
	
</script>

		<c:if test="${check_service eq 'myInforRegist'}">

			<div class="sub_cont" id="container">
				<div class="inner">
				
					<h3 class="tit mb20">개인정보 수정</h3>
				
					<form:form commandName="loginVO" name="loginVO" id="loginVO" action="./passwdCheckSave" method="post">
						
						<input type="hidden" name="check_service" value="<c:out value="${check_service}"/>" />
						
						<!-- mypage_wrap -->
						<div class="mypage_wrap">
							<!-- signup -->
							<div class="signup">
								<ul>
									<li class="wid100">
										<p class="img"><img src="<%=strContext%>/images/sub/img_password.png" alt="비밀번호확인이미지" /></p>
										<dl>
											<dt>비밀번호 입력</dt>
											<dd>※ 개인정보보호를 위하여 비밀번호 확인 후 회원정보의 관리/변경이 가능합니다.</dd>
											<dd>※ 아래 비밀번호 입력란에 비밀번호 입력 후 "비밀번호 확인"을 클릭하세요.</dd>
										</dl>
										<p>
											<label for="password">비밀번호</label>
											<form:password path="password" title="비밀번호" maxlength="20" placeHolder="현재 비밀번호를 입력해 주세요."/>
										</p>
									</li>
								</ul>
							</div>
							<!-- //signup -->
						</div>
						<!-- //mypage_wrap -->						
						
					</form:form>
					

					<ul class="btn_area">
						<li><a href="#" onclick="fn_member_submit(); return false;" class="btn_ty_m_c1">비밀번호 확인</a></li>
						<li><a href="#" onclick="fn_member_cancel(); return false;" class="btn_ty_m_c4">취소</a></li>
					</ul>
					<!-- //btn_area -->
				</div>
				<!-- //inner -->
			</div>
			<!-- //sub_cont -->
		</c:if>
		
		<c:if test="${check_service eq 'outMember'}">
		
			<div class="sub_cont" id="container">
				<div class="inner">
					<h3 class="tit mb20">회원탈퇴</h3>
					
					<form:form commandName="loginVO" name="loginVO" id="loginVO" action="./passwdCheckSave" method="post">
					
						<input type="hidden" name="check_service" value="<c:out value="${check_service}"/>" />
					
						<!-- mypage_wrap -->
						<div class="mypage_wrap">
							<!-- signup -->
							<div class="signup">
								<ul>
									<li class="wid100">
										<p class="img"><img src="<%=strContext%>/images/sub/img_password.png" alt="비밀번호확인이미지" /></p>
										<dl>
											<dt>비밀번호 입력</dt>
											<dd>※ 회원 탈퇴 시 노원수학문화관 홈페이지 내 예약/신청 이용이 불가합니다. <!-- <br />또한 탈퇴된 사용자의 ID는 재사용할 수 없습니다. --></dd>
											<dd>※ 아래 비밀번호 입력란에 비밀번호 입력 후 "비밀번호 확인"을 클릭하세요. </dd>
										</dl>
										<p>
											<label for="password">비밀번호</label>
											<form:password path="password" title="비밀번호" maxlength="20" placeHolder="현재 비밀번호를 입력해 주세요."/>
										</p>
									</li>
								</ul>
							</div>
							<!-- //signup -->
						</div>
						
					</form:form>
						
					<!-- //mypage_wrap -->
					<ul class="btn_area">
						<li><a href="#" onclick="fn_member_submit(); return false;" class="btn_ty_m_c1">비밀번호 확인</a></li>
						<li><a href="#" onclick="fn_member_cancel(); return false;" class="btn_ty_m_c4">취소</a></li>
					</ul>
					<!-- //btn_area -->
				</div>
				<!-- //inner -->
			</div>
			<!-- //sub_cont -->		
		
		</c:if>			

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>