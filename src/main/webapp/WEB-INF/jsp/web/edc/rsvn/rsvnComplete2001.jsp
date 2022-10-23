<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
	
</head>
<body>
			<div class="sub_visual">
				<div class="inner">
					<h2>강좌 신청</h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>				
			</div>
			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<div class="sub_top">
					<h3>강좌신청 접수</h3>
				</div>
				<!--//top -->
				<div class="ex_txt">
					<div class="e_box">
						<div class="img_con"></div>
						<p>강좌 <em>신청접수</em></p>
						<span>구민정보화교육에 강좌 신청접수 되었습니다.</span>
					</div>
				</div>
				<!--//ex_txt  -->

				<div class="table t_flex">
					<table>
						<caption>강좌신청 접수 표입니다. -강좌명, 실주소, 주관기관, 접수마감, 추첨일, 발표일, 발표확인, 교육기간 , 요일시간, 정원수, 교육장소, 강좌분야, 교육대상, 강좌회차, 수강료, 신청일시</caption>
						<colgroup>
							<col style="width:10.7%">
							<col style="width:39.3%">
							<col style="width:10.7%">
							<col style="width:39.3%">
						</colgroup>
						<tbody>
						<c:choose>
							<c:when test="${payMethod eq 'VBANK'}">
								<tr>
									<th scope="row">예약번호</th>
									<td colspan="3">${rsvnInfo.edcRsvnNo}</td>
								</tr>
								<tr>
									<th scope="row">강좌명</th>
									<td colspan="3">${rsvnInfo.edcPrgmnm}</td>
								</tr>
								<!-- <tr>
									<th scope="row">실주소</th>
									<td colspan="3">서울특별시 노원구 상계6,7동 735-1번지  |  노원평생교육원 4층 대회의실</td>
								</tr> -->
								<tr>
									<th scope="row">주관기관</th>
									<td>${rsvnInfo.orgNm} &gt; ${rsvnInfo.edcRelDeptname}</td>
									<th scope="row">신청기간</th>
									<td><hisco:DateUtil datestr="${rsvnInfo.edcRsvnSdate}" format="yyyy.MM.dd"/> ~ <hisco:DateUtil datestr="${rsvnInfo.edcRsvnEdate}" format="yyyy.MM.dd"/></td>
								</tr>
								<tr>
									<th scope="row">교육기간</th>
									<td><hisco:DateUtil datestr="${rsvnInfo.edcReqSdate}" format="yyyy.MM.dd"/> ~ <hisco:DateUtil datestr="${rsvnInfo.edcReqEdate}" format="yyyy.MM.dd"/></td>
									<th scope="row">요일시간</th>
									<td>${rsvnInfo.edcDaygbnNm} / <hisco:DateUtil datestr="${rsvnInfo.edcReqStime}" format="HH:mm"/> ~ <hisco:DateUtil datestr="${rsvnInfo.edcReqEtime}" format="HH:mm"/></td>
								</tr>
								<tr>
									<th scope="row">정원수</th>
									<td>${rsvnInfo.edcPncpa}명 </td>
									<th scope="row">교육장소</th>
									<td>${rsvnInfo.edcPlacenm}</td>
								</tr>
								<tr>
									<th scope="row">강좌분야</th>
									<td>${rsvnInfo.comCtgnm}</td>
									<th scope="row">교육대상</th>
									<td>${rsvnInfo.edcTargetAgegbnNm}</td>
								</tr>
								<tr>
									<th scope="row">강좌 회차</th>
									<td>${rsvnInfo.edcOdr}회 </td>
									<th scope="row">수강료</th>
									<td>
									<c:choose>
										<c:when test="${rsvnInfo.edcProgmCost > 0}">
										<fmt:formatNumber value="${rsvnInfo.edcProgmCost}" pattern="#,###"/>원
										</c:when>
										<c:otherwise><span class="red02">무료</span></c:otherwise>
									</c:choose>
									</td>
								</tr>
								<tr>
									<th scope="row">할인정보</th>
									<td>
									<c:choose>
										<c:when test="${rsvnInfo.edcDcamt > 0 and empty rsvnInfo.edcReasondcNm}">
											노원페이 (최대20%)
										</c:when>
										<c:otherwise>
											${rsvnInfo.edcReasondcNm} (${rsvnInfo.edcDcrate}%)
										</c:otherwise>
									</c:choose>
									</td>
									<th scope="row">결제금액</th>
									<td><fmt:formatNumber value="${rsvnInfo.edcTotamt}" pattern="#,###"/>원</td>
								</tr>
								<tr>
									<th scope="row" colspan="3">입금계좌</th>
									<td>TODO:입금계좌정보입력:[ㅇㅇ은행] 12120111111-14-111 / 예금주 : 한아랑</td>
								</tr>								
								<tr>
									<th scope="row" colspan="3">환불 받을 내계좌</th>
									<td>TODO:환불계좌정보입력[ㅇㅇ은행] 12120111111-14-111 / 예금주 : 한아랑</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<th scope="row">강좌명</th>
									<td colspan="3">${rsvnInfo.edcPrgmnm}</td>
								</tr>
								<tr>
									<th scope="row">장소</th>
									<td colspan="3">${rsvnInfo.edcPlacenm}</td>
								</tr>
								<tr>
									<th scope="row">주관기관</th>
									<td>${rsvnInfo.orgName} &gt; ${rsvnInfo.edcRelDeptname}</td>
									<th scope="row">접수마감</th>
									<td><hisco:DateUtil datestr="${rsvnInfo.edcRsvnEdate}" format="yyyy.MM.dd"/> (${rsvnInfo.edcRsvnEdday}일 남음)</td>
								</tr>
								<tr>
									<th scope="row">교육대상</th>
									<%-- <td>${rsvnInfo.edcTargetinfo}</td> --%>
									<td>${rsvnInfo.edcTargetAgegbnNm}</td>
									<th scope="row">교육기간</th>
									<td><hisco:DateUtil datestr="${rsvnInfo.edcReqSdate}" format="yyyy.MM.dd"/> ~ <hisco:DateUtil datestr="${rsvnInfo.edcReqEdate}" format="yyyy.MM.dd"/></td>
								</tr>
								<tr>
									<th scope="row">정원수</th>
									<td>${rsvnInfo.edcPncpa}명 </td>
									<th scope="row">요일시간</th>
									<td>${rsvnInfo.edcDaygbnNm} / <hisco:DateUtil datestr="${rsvnInfo.edcReqStime}" format="HH:mm"/> ~ <hisco:DateUtil datestr="${rsvnInfo.edcReqEtime}" format="HH:mm"/></td>
								</tr>
								<tr>
									<th scope="row">수강료</th>
									<td>
									<c:choose>
										<c:when test="${rsvnInfo.edcProgmCost > 0}">
										<fmt:formatNumber value="${rsvnInfo.edcProgmCost}" pattern="#,###"/>원
										</c:when>
										<c:otherwise>무료</c:otherwise>
									</c:choose>
									</td>
									<th scope="row">강좌 회차</th>
									<td>${rsvnInfo.edcOdr}회</td>
								</tr>
							</c:otherwise>
						</tbody>
					</table>
				</div>
				<!--// table  -->
				<div class="btn_area">
					<a href="<c:url value='/web/mypage/myRsvn/myRsvnList'/>" class="btn_s2_c0">목록 이동</a>
				</div>
			</div>
			<!--// sub_content  -->
</body>