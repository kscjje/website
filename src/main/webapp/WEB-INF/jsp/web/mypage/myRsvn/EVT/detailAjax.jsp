<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : detailAjax.jsp
  * @Description : 예약목록 상세모달
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.15    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.09.15
  *  @version 1.0
  *  @see
  *
  */
%>

<div class="modal-body scroll2" tabindex="0">
<div class="modal-txt">
			<p class="stit">예약 정보 확인</p>
				<!-- table -->
				<div class="table2 responsive-type mb50">
					<table>
					<caption>출력용 예약증</caption>
						<caption>예약프로그램 정보 표입니다.</caption>
						<colgroup>
							<col style="width:20%">
							<col style="width:80%">
						</colgroup>
						<tbody>
						<tr>
							<th scope="row" class="vtop">제목</th>
							<td class="b"><c:out value="${resultVO.evtName}"/></td>
						</tr>
						<tr>
							<th scope="row" class="vtop">예약번호</th>
							<td class="b"><c:out value="${resultVO.evtRsvnno}"/></td>
						</tr>
						<tr>
							<th scope="row" class="vtop">강연/행사/영화 일시</th>
							<td class="b">
								<fmt:parseDate var="dateStr1" value="${resultVO.evtVeingdate}" pattern="yyyyMMdd" />
								<fmt:formatDate value="${dateStr1}" pattern="yyyy.MM.dd" />
								 &nbsp;&nbsp;<c:out value="${fn:substring(resultVO.evtStime,0,2)} : ${fn:substring(resultVO.evtStime,2,4)}" /> ~ <c:out value="${fn:substring(resultVO.evtEtime,0,2)} : ${fn:substring(resultVO.evtEtime,2,4)}" />
							</td>
						</tr>
						<tr>
							<th scope="row" class="vtop">인원수</th>
							<td class="b">
							
								<c:out value="${resultVO.evtVeingnmpr}"/> 명
												
<%-- <c:forEach items="${resultVO.itemList}" var="item" varStatus="s">
	<c:if test="${item.evtRsvnItemcd ne prevItemCd }">
		<c:set var="prevItemCd" value="${item.evtRsvnItemcd}" />
	 	<c:out value="${item.evtItemNm }"/> : <c:out value="${item.evtRsvnItemcnt}"/>명&nbsp;&nbsp;&nbsp;
	</c:if>
</c:forEach> --%>
							</td>
						</tr>
						<tr>
							<th scope="row" class="vtop">예약일</th>
							<td class="b"><fmt:formatDate value="${resultVO.regdate}" pattern="yyyy.MM.dd HH:mm"/></td>
						</tr>
						<tr>
							<th scope="row" class="vtop">예약자 이름</th>
							<td ><c:out value="${resultVO.evtRsvnCustnm }"/></td>
						</tr>

						<tr>
							<th scope="row" class="vtop">예약자 연락처</th>
							<td>
								<tags:HpDecrypt param="${resultVO.evtRsvnMoblphon}"/>
							</td>
						</tr>
						<c:if test="${ resultVO.evtPersnGbn eq '2001'}">
							<tr>
								<th scope="row" class="vtop">단체명</th>
								<td ><c:out value="${resultVO.evtRsvnGrpnm}"/></td>
							</tr>
							<tr>
								<th scope="row" class="vtop">인솔자 명</th>
								<td ><c:out value="${resultVO.evtVisitCustnm}"/></td>
							</tr>
							<tr>
								<th scope="row" class="vtop">인솔자 연락처</th>
								<td>
									<tags:HpDecrypt param="${resultVO.evtVisitMoblphon}"/>
								</td>
							</tr>
						</c:if>
						</tbody>
					</table>
				</div>

				<!-- //table -->
				<!-- //예약자 정보 -->
				<p class="stit">결제 정보</p>
				<!-- table -->
				<div class="table2 responsive-type mb50">
					<table>
						<caption>결제 정보 표입니다.</caption>
						<colgroup>
							<col style="width:20%">
							<col style="width:80%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop">결제 상태</th>
								<td class="b">
								<c:out value="${resultVO.evtApptypeNm}"/>
									<%-- <c:choose>
										<c:when test="${resultVO.evtRsvnApptype eq '11' }">결제 대기중</c:when>
										<c:when test="${resultVO.evtRsvnApptype eq '12' }">취소완료</c:when>
										<c:when test="${resultVO.evtRsvnApptype eq '20' and resultVO.evtRsvnSaleamt > 0}">결제완료</c:when>
										<c:when test="${resultVO.evtRsvnApptype eq '20' }">예약완료</c:when>
										<c:when test="${resultVO.evtRsvnApptype eq '31' }">환불완료</c:when>
										<c:when test="${resultVO.evtRsvnApptype eq '32' }">결제기한 만료 자동취소</c:when>
									</c:choose> --%>
								</td>
							</tr>
								<tr>
									<th scope="row" class="vtop">결제 금액</th>
									<td class="b">
										<%-- <fmt:formatNumber value="${resultVO.evtRsvnPayamt}"/>원 --%>
										무료
									</td>
								</tr>

<c:if test="${resultVO.evtRsvnApptype eq '11' }">
							<tr>
								<th scope="row" class="vtop">결제마감시간</th>
								<td class="b red">
									<c:choose>
										<c:when test="${empty resultVO.evtPaywaitEnddatetime }">강연/행사/영화 시작 전까지 결제 가능</c:when>
										<c:otherwise><fmt:formatDate value="${resultVO.evtPaywaitEnddatetime}" pattern="yyyy.MM.dd HH:mm"/></c:otherwise>
									</c:choose>
								</td>
							</tr>
</c:if>
						</tbody>
					</table>
				</div>
				<!-- //table -->
				<!-- //결제 정보 -->
		</div>
		<!-- modal-txt -->
		<ul class="btn_area">
			<c:choose>
				<c:when test="${resultVO.evtRsvnApptype eq '20' or (resultVO.evtPersnGbn eq '2001' and resultVO.evtRsvnApptype eq '11')}">
					<li><a href="#" onclick="fn_pop_rsvnCerti('${resultVO.evtRsvnIdx}')" class="btn_ty_m3_c1">예약확인증 출력하기</a></li>
				</c:when>
			</c:choose>
			<c:if test="${resultVO.evtPersnGbn eq '2001' and (resultVO.evtRsvnApptype eq '11' or resultVO.evtRsvnApptype eq '20')}">
				<c:choose>
					<c:when test="${resultVO.editYn eq 'Y'}">
						<li></li>
					</c:when>
					<c:otherwise>
						<!-- <li><a href="javascript:;" onclick="alert('관람일 이후에는 수정이 불가능합니다.')" class="btn_ty_m3_c3">예약정보 수정</a></li> -->
					</c:otherwise>
				</c:choose>
			</c:if>

		</ul>

</div>
<!-- modal-body -->
