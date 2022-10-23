<%
 /**
  * @Class Name : joinResult.jsp
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
<%@ include file="../../../include/top.jsp"%>
<script>
	function fn_yearmem_join() {
		$('#memberForm').submit();
	}
	function fn_family_join() {
		$('#memberForm').attr("action" , "../family/checkFamily");
		$('#memberForm').submit();
	}
	
	$(document).ready(function() {
		$(document).attr("title", "가입완료 - 회원가입 | 노원수학문화관");	
	});	
	
</script>
<div class="sub_cont" id="container">
		<div class="inner">
			<h3 class="tit mb20">가입완료</h3>
			<p class="desc mb100">노원수학문화관과 함께 미래의 꿈을 만들어 가시기 바랍니다.</p>
			<div class="join_wrap bor mb40">
				<!-- step -->
			<ul class="step">
				<li>
					<dl>
						<dt>1</dt>
						<dd>유형선택</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>2</dt>
						<dd>약관동의</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>3</dt>
						<dd>본인인증</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>4</dt>
						<dd>정보입력</dd>
					</dl>
				</li>
				<li class="on">
					<!-- 현재 on -->
					<dl>
						<dt>5</dt>
						<dd>가입완료</dd>
					</dl>
				</li>
			</ul>
			<!-- //step -->
			<!-- signup -->
			<div class="signup2">
				<h4 class="h4">회원가입이 완료되었습니다.</h4>
				<!-- join_end -->
				<div class="join_end">
					<ul>
						<li>
							<dl>
								<dt>아이디</dt>
								<dd><c:out value="${param.id }"/></dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>이름</dt>
								<dd><c:out value="${param.memNm }"/></dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>가입 일자</dt>
								<dd><c:out value="${param.joinDate }"/></dd>
							</dl>
						</li>
					</ul>
					<div class="invit2">
						<a >
							<dl>
								<dt>높은 퀄리티의 노원수학문화관<br> 유료회원으로 초대합니다.</dt>
								<dd>언제나 다양한 체험, 과학문화의 산실,<br> 조금 더 특별하게 노원수학문화관을 만나보세요. </dd>
							</dl>
						</a>
					</div>
				</div>
				<!-- //join_end -->
			</div>
			<!-- //signup -->
		</div>
		<!-- //join_wrap -->

		<div class="mypage_wrap bor">
			<!-- 할인 정책 -->
			<%@ include file="incMemyearBenefit.jsp"%>
			<!-- // 할인 정책 -->
		</div>
		<!-- //join_wrap -->
<form:form commandName="memberVO" name="memberVO" id="memberForm" action="./joinYearRegist" method="post">
<form:hidden path="memNo"/>
</form:form>
		<ul class="btn_area long">
			<li><a href="/web/member/login" class="btn_ty_m_c4">로그인</a></li>
			<li><a href="javascript:fn_yearmem_join()" class="btn_ty_m3_c1">유료회원 가입 하기</a></li>
			<li><a href="javascript:fn_family_join()" class="btn_ty_m3_c5">기존가족회원 개인유료가입</a></li>
		</ul>
		<!-- //btn_area -->

	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->