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
  * @ 2020.09.21    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.09.21
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
					<caption>예약 정보 확인 표입니다.</caption>
					<colgroup>
						<col style="width:20%">
						<col style="width:80%">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" class="vtop">관람 제목</th>
							<td class="b">
								[<c:out value="${resultVO.partNm }"/>]
								<c:choose>
									<c:when test="${resultVO.exbtType eq '1001' }">관람</c:when>
									<c:otherwise><c:out value="${resultVO.exbtName }"/></c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th scope="row" class="vtop">관람 일시</th>
							<td class="b"><fmt:parseDate var="extYmd" value="${resultVO.ymd}" pattern="yyyyMMdd"/>
								<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
								&nbsp;&nbsp;${fn:substring(resultVO.exbtStime,0,2) }:${fn:substring(resultVO.exbtStime,2,4) } ~ ${fn:substring(resultVO.exbtEtime,0,2) }:${fn:substring(resultVO.exbtEtime,2,4) }
							</td>
						</tr>
						<tr>
							<th scope="row" class="vtop">인원수</th>
							<td class="b">
								<c:out value="${resultVO.visitnum}"/> 명
							</td>
						</tr>
						<tr>
							<th scope="row" class="vtop">예약 번호</th>
							<td class="b"><c:out value="${resultVO.rsvnNo}"/></td>
						</tr>
						<tr>
							<th scope="row" class="vtop">예약일</th>
							<td><fmt:formatDate value="${resultVO.regdate}" pattern="yyyy.MM.dd HH:mm"/></td>
						</tr>
						<tr>
							<th scope="row" class="vtop">예약자 이름</th>
							<td><c:out value="${resultVO.custNm}"/></td>
						</tr>
						<tr>
							<th scope="row" class="vtop">예약자 연락처</th>
							<td>
								<tags:HpDecrypt param="${resultVO.exbtHp}"/>
							</td>
						</tr>
						<tr>
							<th scope="row" class="vtop">이메일</th>
							<td>
								<tags:EmailDecrypt param="${resultVO.exbtEmail}"/>
							</td>
						</tr>
			<c:choose>
			<c:when test="${resultVO.target eq '2001' }">
						<tr>
							<th scope="row" class="vtop">단체명</th>
							<td ><c:out value="${resultVO.grpnm }"/></td>
						</tr>
						<tr>
							<th scope="row" class="vtop">방문 인솔자명</th>
							<td ><c:out value="${resultVO.visitcustNm }"/></td>
						</tr>
						<tr>
							<th scope="row" class="vtop">방문 인솔자 연락처</th>
							<td>
								<tags:HpDecrypt param="${resultVO.visitcustHpno}"/>
							</td>
						</tr>
			</c:when>
			</c:choose>
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
							<th scope="row" class="vtop">예약 상태</th>
							<td class="b">
							<c:out value="${resultVO.exbtApptypeNm}"/>
								<%-- <c:choose>
									<c:when test="${resultVO.apptype eq '11' }">결제 대기중</c:when>
									<c:when test="${resultVO.apptype eq '12' }">취소완료</c:when>
									<c:when test="${resultVO.apptype eq '20' and resultVO.saleamt > 0}">결제완료</c:when>
									<c:when test="${resultVO.apptype eq '20' }">예약완료</c:when>
									<c:when test="${resultVO.apptype eq '31' }">환불완료</c:when>
									<c:when test="${resultVO.apptype eq '32' }">결제기한 만료 자동취소</c:when>
								</c:choose> --%>
							</td>
						</tr>
<c:if test="${resultVO.saleamt>0 }">
						<tr>
							<th scope="row" class="vtop">결제 금액</th>
							<td class="b">
								<fmt:formatNumber value="${resultVO.payamt}"/>원
							</td>
						</tr>
</c:if>
<c:if test="${resultVO.apptype eq '11' }">
						<tr>
							<th scope="row" class="vtop">결제 마감시간</th>
							<td class="b red">
								<c:choose>
									<c:when test="${empty resultVO.paywaitEnddt }">관람 시작 전까지 결제 가능</c:when>
									<c:otherwise><fmt:formatDate value="${resultVO.paywaitEnddt}" pattern="yyyy.MM.dd HH:mm"/></c:otherwise>
								</c:choose>
							</td>
						</tr>
</c:if>
					</tbody>
				</table>
			</div>
			<!-- //table -->
		</div>
		<!-- modal-txt -->

		<ul class="btn_area">
			<c:choose>
				<c:when test="${resultVO.apptype eq '20' or (resultVO.target eq '2001' and resultVO.apptype eq '11')}">
					<li><a href="javascript:;" onclick="fn_pop_rsvnCerti('${resultVO.rsvnIdx}')" class="btn_ty_m3_c1">예약확인증 출력하기</a></li>
				</c:when>
			</c:choose>
			<c:if test="${resultVO.target eq '2001' and (resultVO.apptype eq '11' or resultVO.apptype eq '20')}">
				<c:choose>
					<c:when test="${resultVO.editYn eq 'Y'}">
						<li><a href="./myRsvnExbtModify?rsvnIdx=${resultVO.rsvnIdx}" class="btn_ty_m3_c3">예약정보 수정</a></li>
					</c:when>
					<c:otherwise>
						<!-- <li><a href="javascript:;" onclick="alert('관람일 이후에는 수정이 불가능합니다.')" class="btn_ty_m3_c3">예약정보 수정</a></li> -->
					</c:otherwise>
				</c:choose>

			</c:if>

		</ul>
	</div>
