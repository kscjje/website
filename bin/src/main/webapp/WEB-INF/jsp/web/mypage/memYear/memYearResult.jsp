<%
 /**
  * @Class Name : memYearResult.jsp
  * @Description : 연간회원 결과 페이지
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

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<div class="sub_cont" id="container">
		<div class="inner">
			<h3 class="tit mb20">유료회원 가입</h3>
			<p class="desc mb100">국립중앙과학관과 함께 미래의 꿈을 만들어 가시기 바랍니다.</p>
			<div class="join_wrap bor pt70 mb20">
				<!-- step -->
			<!-- signup -->
			<div class="signup2">
<c:choose>
	<c:when test="${param.resultFlag }">
				<h4 class="h4">유료회원 가입이 완료 되었습니다.<br> 감사합니다.</h4>
				<div class="join_end">
					<ul>
						<li>
							<dl>
								<dt>아이디</dt>
								<dd><c:out value="${memberYearVO.id}"/></dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>이름</dt>
								<dd><c:out value="${memberYearVO.memNm}"/></dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>기간</dt>
								<dd>
									<fmt:parseDate var="startYmd" value="${memberYearVO.startYmd}" pattern="yyyyMMdd"/>
									<fmt:parseDate var="endYmd" value="${memberYearVO.endYmd}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${startYmd}" pattern="yyyy.MM.dd"/> ~ <fmt:formatDate value="${endYmd}" pattern="yyyy.MM.dd"/>
								</dd>
							</dl>
						</li>
					</ul>
				</div>
				<!-- //join_end -->
				<!-- ex_txt -->
				<div class="ex_txt mb20">
					<p class="ex_tit"><i><img src="<%=strContext%>/images/sub/ico_notice03.png" alt="유의사항아이콘"></i> 안내</p>
					<p>- 유료회원 가입 내역은 ‘마이페이지’ 에서 확인하실 수 있습니다.</p>
					<p>- 유료회원 환불은 가입 후 7일 이내, 안내 센터로 문의 해 주시기 바랍니다.</p>
					<p>- 주차할인정보를 받기 위해서는 ‘마이페이지 > 내 차량정보’ 를 꼭 입력 해 주시기 바랍니다.</p>
				</div>
				<!-- //ex_txt -->
	</c:when>
	<c:otherwise>
				<h4 class="h4">유료회원 가입 결제 실패</h4>
				<div class="table2 responsive-type">
					<c:out value="${param.resultMsg }" escapeXml="false"/>
				</div>
	</c:otherwise>
</c:choose>

			</div>
			<!-- //signup -->
		</div>
		<!-- //join_wrap -->

		<ul class="btn_area long">
			<li><a href="../../main" class="btn_ty_m_c1">홈으로</a></li>
		</ul>
		<!-- //btn_area -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->