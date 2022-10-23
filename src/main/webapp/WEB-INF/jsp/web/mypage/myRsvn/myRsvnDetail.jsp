<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>

</head>
<body>
	<div class="sub_visual mypage">
		<div class="inner">
			<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
			<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
		</div>
	</div>
	<!--//sub_visual  -->
	<div id="content" class="sub_content">
		<div class="sub_top">
			<a href="javascript:history.back();">뒤로가기 버튼</a>
			<h3>수강신청 상세</h3>
		</div>
		<!--//top -->
		<!-- 12.14 jhj 수정 -->
		<div class="table t_flex">
			<table>
				<caption>수강신청 상세 표입니다. -예약번호</caption>
				<colgroup>
					<col width="10.7%">
					<col width="89.3%">
				</colgroup>
				<tbody>
				<tr>
					<th scope="row">예약번호</th>
					<td>${rsvnInfo.edcRsvnNo}</td>
				</tr>
			</tbody>
			</table>
		</div>
		<!--// table  -->
		<c:if test="${empty rsvnInfo.edcRsvnMemno}">
		<div class="table t_flex">
			<table>
				<caption>수강신청 상세 표입니다. - 강좌명, 강사명, 교육기간, 수업요일/시간, 장소, 신청상태, 문의전화</caption>
				<colgroup>
					<col width="10.7%">
					<col width="39.3%">
					<col width="10.7%">
					<col width="39.3%">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">이름</th>
						<td>${rsvnInfo.edcRsvnCustnm}</td>
						<th scope="row">생년월일</th>
						<td>${rsvnInfo.edcRsvnBirthdate}</td>
					</tr>
					<tr>
						<th scope="row">성별</th>
						<td>${rsvnInfo.edcRsvnGender}</td>
						<th scope="row">핸드폰번호</th>
						<td>${rsvnInfo.edcRsvnMoblphon}</td>
					</tr>
				</tbody>
			</table>
		</div>
		</c:if>
		<div class="table t_flex">
			<table>
				<caption>수강신청 상세 표입니다. - 강좌명, 강사명, 교육기간, 수업요일/시간, 장소, 신청상태, 문의전화</caption>
				<colgroup>
					<col width="10.7%">
					<col width="39.3%">
					<col width="10.7%">
					<col width="39.3%">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">강좌명</th>
						<td>${rsvnInfo.edcPrgmnm}</td>
						<th scope="row">강사명</th>
						<td>${rsvnInfo.instrctrName}</td>
					</tr>
					<tr>
						<th scope="row">교육기간</th>
						<td><hisco:DateUtil datestr="${rsvnInfo.edcReqSdate}" format="yyyy-MM-dd"/> ~ <hisco:DateUtil datestr="${rsvnInfo.edcReqEdate}" format="yyyy-MM-dd"/></td>
						<th scope="row">수업요일/시간</th>
						<td>${rsvnInfo.edcDaygbnNm} / <hisco:DateUtil datestr="${rsvnInfo.edcReqStime}" format="HH:mm"/> - <hisco:DateUtil datestr="${rsvnInfo.edcReqEtime}" format="HH:mm"/></td>
					</tr>
					<tr>
						<th scope="row">장소</th>
						<td colspan="3">TODO:어디서가져오나?</td>
					</tr>
					<tr>
						<th scope="row">신청상태</th>
						<td>${rsvnInfo.edcStatNm}</td>
						<th scope="row">문의전화</th>
						<td>${rsvnInfo.edcGuideTelno}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!--// table  -->
		<div class="table t_flex">
			<table>
				<caption>수강신청 상세 표입니다. -결제방법, 수강료 , 할인금액, 결제마감일시, 결제예상금액</caption>
				<colgroup>
					<col width="10.7%">
					<col width="39.3%">
					<col width="10.7%">
					<col width="39.3%">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">결제방식</th>
						<td>${rsvnInfo.payMethodNm}</td>
						<th scope="row">수강료</th>
						<td>
						<c:choose>
							<c:when test="${rsvnInfo.edcProgmCost > 0}">
							<fmt:formatNumber value="${rsvnInfo.edcProgmCost}"/> 원
							</c:when>
							<c:otherwise><span class="red02">무료</span></c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row">결제계좌</th>
						<td>TODO:가상계좌이용시</td>
						<th scope="row">할인금액</th>						
						<td>
						<c:choose>
							<c:when test="${not empty rsvnInfo.edcReasondc}">
								-<fmt:formatNumber value="${rsvnInfo.edcDcamt}"/> 원 (${rsvnInfo.edcReasondcNm} ${rsvnInfo.edcDcrate}%)
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row">결제마감일시</th>
						<td>
							<c:if test="${rsvnInfo.edcTotamt > 0}">
							<fmt:formatDate value="${rsvnInfo.edcPaywaitEnddatetime}" pattern = "yyyy-MM-dd HH:mm"/>까지
							</c:if>
						</td>
						<th scope="row">결제예상금액</th>
						<td><fmt:formatNumber value="${rsvnInfo.edcTotamt}"/> 원</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!--// table  -->
		<div class="table t_flex">
			<div class="title_box">
				<p class="t_title">결제완료정보</p>
			</div>
		<table>
			<caption>결제완료정보 표입니다. -영수증번호, 결제수단 , 결제일시, 승인번호, 주문번호, 결제금액, 결제취소, 취소금액, 환불계좌</caption>
			<colgroup>
				<col width="10.7%">
				<col width="39.3%">
				<col width="10.7%">
				<col width="39.3%">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">영수증번호</th>
					<td>${paySummary.receiptNo}</td>
					<th scope="row">결제수단</th>
					<td><c:if test="${not empty paySummary.payMethodNm}">${paySummary.payMethodNm}(${paySummary.financeNm})</c:if></td>
				</tr>
				<tr>
					<th scope="row">결제일시</th>
					<td><hisco:DateUtil datestr="${paySummary.payDate}${paySummary.payTime}" format="yyyy년MM월dd일 HH:mm:ss"/></td>
					<th scope="row">승인번호</th>
					<td><c:if test="${not empty paySummary.appNo}">${paySummary.appNo}</c:if></td>
				</tr>
				<tr>
					<th scope="row">주문번호(OID)</th>
					<td><c:if test="${paySummary.oid > 0}">${paySummary.oid}</c:if></td>
					<th scope="row">결제금액</th>
					<td><fmt:formatNumber value='${paySummary.payAmt}'/> 원</td>
				</tr>
				<c:if test="${not empty paySummary.cancelDtime}">
				<tr>
					<th scope="row">결제취소</th>
					<td><hisco:DateUtil datestr="${paySummary.cancelDtime}" format="yyyy년MM월dd일 HH:mm:ss"/></td>
					<th scope="row">취소금액</th>
					<td><fmt:formatNumber value='${paySummary.cancelAmt}'/> 원</td>
				</tr>
				</c:if>
				<c:if test="${not empty paySummary.refBankNm}">
				<tr>
					<th scope="row">환불계좌</th>
					<td colspan="3">${paySummary.refBankNm}/${paySummary.refAcntNo}/${paySummary.refDipositorNm}</td>
				</tr>
				</c:if>
				</tbody>
				</table>
			</div>
			<!--// table  -->
		<div class="btn_area">
			<a href="javascript:history.back()" class="btn_s1_c0">이전 목록</a>
		</div>
	</div>
</body>