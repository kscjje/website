<%
 /**
  * @Class Name : joinUpdateEnd.jsp
  * @Description : 가입완료 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.20    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.08.20
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<script>
	function fn_yearmem_join() {
		$('#memberForm').submit();
	}
</script>
<div class="sub_cont" id="container">
		<div class="inner">
			<h3 class="tit mb20">노원수학문화관 통합예약<br/>누리집 최초 본인인증</h3>
			<p class="desc mb100">노원수학문화관과 함께 미래의 꿈을 만들어 가시기 바랍니다.</p>
			<div class="join_wrap bor mb40">
				<!-- step -->
				<ul class="step">
						<li ><!-- 현재 on -->
							<dl>
								<dt>1</dt>
								<dd>최초 본인인증</dd>
							</dl>
						</li>
						<li >
							<dl>
								<dt>2</dt>
								<dd>정보입력</dd>
							</dl>
						</li>
						<li class="on">
							<dl>
								<dt>3</dt>
								<dd>수정완료</dd>
							</dl>
						</li>
					</ul>
			<!-- //step -->
			<!-- signup -->
			<div class="signup2">
				<h4 class="h4">회원 정보 수정이 완료되었습니다.</h4>
				<!-- join_end -->
				<p class="desc mb40">변경하신 비밀번호로 로그인 후 예약 서비스를 이용하실 수 있습니다.</p>
				<!-- //join_end -->
			</div>
			<!-- //signup -->
		</div>
		<!-- //join_wrap -->

		<!-- //join_wrap -->
		<ul class="btn_area long">
			<li><a href="/web/member/login" class="btn_ty_m3_c1">로그인</a></li>
		</ul>
		<!-- //btn_area -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->