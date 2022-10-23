<%
 /**
  * @Class Name : myRsvnResult.jsp
  * @Description : 결제 결과 페이지
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
			<h3 class="tit">나의 예약내역</h3>
			<div class="join_wrap bor">
			<!-- step -->
			<ul class="step step2">
				<li>
					<dl>
						<dt>1</dt>
						<dd>결제정보</dd>
					</dl>
				</li>
				<li class="on"><!-- 현재 on -->
					<dl>
						<dt>2</dt>
						<dd>결제완료</dd>
					</dl>
				</li>
			</ul>
			<!-- //step -->
			<!-- signup2 -->
			<div class="signup2">
<c:choose>
	<c:when test="${param.resultFlag }">
				<h4 class="h4">총 <em class="pink">${pgData.detCnt }</em>건의 결제가 완료되었습니다.</h4>
				<!-- table -->
				<p class="stit">결제정보</p>
				<div class="table2 responsive-type">
					<table>
						<caption>나의예약내역 결제정보 확인표입니다.</caption>
						<colgroup>
							<col style="width:20%">
							<col style="width:80%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop">결제 방법</th>
								<td class="b"><c:choose><c:when test="${pgData.pType eq 'CARD'}">신용카드</c:when><c:when test="${pgData.pType eq 'BANK'}">계좌이체</c:when></c:choose> (${pgData.methodNm })</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">결제 금액</th>
								<td class="b"><fmt:formatNumber value="${pgData.appAmt }"/>원</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- //table -->
				<ul class="btn_area long">
					<li><a href="./myRsvnDetail?orderId=${myRsvnVO.orderId }&gubun=${myRsvnVO.LGD_PRODUCTCODE}" class="btn_ty_m3_c3">상세내역 확인하기</a></li>
				</ul>
	</c:when>
	<c:otherwise>
				<h4 class="h4">결제 실패</h4>
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
			<li><a href="//www.hisco.co.kr/web/main" class="btn_ty_m3_c1">노원수학문화관 홈으로</a></li>
			<li><a href="../../main" class="btn_ty_m3_c5">통합예약 사이트 홈으로</a></li>
		</ul>
		<!-- //btn_area -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->