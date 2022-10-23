<%
 /**
  * @Class Name : memYearMain.jsp
  * @Description : 연간회원 안내 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.10    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.09.10
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<div class="sub_cont" id="container">
		<div class="inner">
<c:choose>
	<c:when test="${memberYearVO.validYn eq 'Y' }">
			<h3 class="tit mb20">유료회원 갱신</h3>
			<p class="desc mb100">노원수학문화관과 함께 미래의 꿈을 만들어 가시기 바랍니다.</p>
	</c:when>
	<c:otherwise>
			<h3 class="tit mb20">유료회원 가입</h3>
			<p class="desc mb100">미래의 과학으로 가는 지름길 노원수학문화관의 유료회원으로 초대 합니다.</p>
	</c:otherwise>
</c:choose>
			<div class="join_wrap bor pt70 mb20">
				<!-- step -->
			<!-- signup -->
			<div class="signup2">
<c:choose>
	<c:when test="${userVO.specialYn eq 'Y' }">
				<!-- join_end -->
				<div class="join_end">
					<ul>
						<li>
							<dl>
								<dt>아이디</dt>
								<dd><c:out value="${userVO.id}"/></dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>이름</dt>
								<dd><c:out value="${userVO.name}"/></dd>
							</dl>
						</li>
					</ul>
					<div class="invit2">
						<a >
							<dl>
								<dt>현재 특별회원입니다.</dt>
								<dd>특별회원은  유료회원을 가입 하실 수 없습니다. </dd>
							</dl>
						</a>

					</div>
				</div>
				<!-- //join_end -->
	</c:when>
	<c:when test="${empty memberYearVO.startYmd }">
				<!-- join_end -->
				<div class="join_end">
					<ul>
						<li>
							<dl>
								<dt>아이디</dt>
								<dd><c:out value="${userVO.id}"/></dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>이름</dt>
								<dd><c:out value="${userVO.name}"/></dd>
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

	</c:when>
	<c:otherwise>
				<h4 class="h4">유료회원 가입 정보 확인</h4>
				<div class="table2 responsive-type mb20">
					<table>
						<caption>유료회원 등록을 위한 정보 확인표입니다.</caption>
						<colgroup>
							<col style="width:20%">
							<col style="width:80%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop">아이디</th>
								<td class="b"><c:out value="${userVO.id}"/></td>
							</tr>
							<tr>
								<th scope="row" class="vtop">가입일</th>
								<td class="b">
									<fmt:parseDate var="startYmd" value="${memberYearVO.startYmd}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${startYmd}" pattern="yyyy년 MM월 dd일"/>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">만료일</th>
								<td class="b">
									<fmt:parseDate var="endYmd" value="${memberYearVO.endYmd}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${endYmd}" pattern="yyyy년 MM월 dd일"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="ex_txt">
					<p>
						<c:choose>
							<c:when test="${memberYearVO.validYn eq 'Y' }">만료일 까지 앞으로 ${memberYearVO.remainDate}일 남았습니다. </c:when>
							<c:otherwise>유료회원 기간이 만료되었습니다.</c:otherwise>
						</c:choose>
					 </p>
					 <c:if test="${memberYearVO.validYn eq 'Y'}">
					 	<fmt:parseDate var="applyDate" value="${memberYearVO.applyDate}" pattern="yyyyMMdd"/>
					 	<p>유료회원 갱신은 <fmt:formatDate value="${applyDate}" pattern="yyyy년 MM월 dd일"/> 부터 가능합니다.(만료일 30일전) </p>
					 </c:if>
					<p> 만료일이 지난 이후 유료회원 갱신은 되지 않습니다</p>
				</div>
	</c:otherwise>
</c:choose>
			</div>
			<!-- //signup -->

			<ul class="btn_area long">
			<li>
<c:choose>
	<c:when test="${userVO.specialYn eq 'Y' }"><!-- 가입불가 --></c:when>
	<c:when test="${empty memberYearVO.startYmd }">
				<a href="./memYearRegist" class="btn_ty_m3_c1">유료회원 가입 하기</a>
	</c:when>
	<c:when test="${memberYearVO.remainDate <= 30 }">
				<a href="./memYearRegist" class="btn_ty_m3_c1">유료회원 갱신 하기</a>
	</c:when>
</c:choose>
				</li>
		</ul>
		<!-- //btn_area -->

		</div>
		<!-- //join_wrap -->

		<div class="mypage_wrap bor">
			<!-- 할인 정책 -->
			<%@ include file="../../member/join/incMemyearBenefit.jsp"%>
			<!-- // 할인 정책 -->
		</div>
		<!-- //join_wrap -->


	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->