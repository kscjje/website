<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
<script>
	function fn_reserve_cancel(edcRsvnReqid, edcRsvnNo) {
		if (confirm("해당강좌의 신청을 취소하시겠습니까?")) {
			 $.post({
		        url  : './rsvnCancel.json',
		        data : {'edcRsvnReqid':edcRsvnReqid, 'edcRsvnNo':edcRsvnNo},
		        success: function(data) {
		        	alert(data.result.msg);
		        	if (data.result.success) {
		        		window.location.reload();
		        	}
		        }, error: function() {
		        	alert("일시적인 문제가 발생하였습니다. 잠시 후 다시 시도해 주세요.");
		        }
		    });
		}
	}

	function printCert(idx){
		$('#edcRsvnReqid').val(idx);
		$('#edcRsvnMstVO').attr("action" , "../myRsvn/edcCompPopup");
		window.open('','rsvnPop','width=818, height=900, resizable=yes, scrollbars=yes, status=no, titlebar=0, toolbar=0, left=300, top=100');

		$('#edcRsvnMstVO').submit();

	}

	$(document).ready(function(){
		$('#appStatus').change(function(){
			$('#listForm').submit();
		});
	})
</script>
</head>
<body>
			<div class="sub_visual mypage">
				<div class="inner">
					<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>
			<div id="content" class="sub_content">
				<!-- tab_link -->
<c:choose>
	<c:when test="${userInfo.isMember}">
					<%@ include file="/WEB-INF/templates/web/base/tab_menu.jsp"%>
				<!--// tab_link -->
				<div class="sub_top type01">
					<h3>수강신청 현황</h3>
				</div>
	</c:when>
	<c:otherwise>
				<div class="sub_top type01" style="margin-top:0px">
					<h3>비회원 수강신청 현황</h3>
				</div>
	</c:otherwise>
</c:choose>

				<!--//sub_top -->
				<div class="table t_block">
					<!-- 컨텐츠 오른쪽 정렬일때 f_right 클래스 추가 -->
<form id="listForm" method="get">
					<div class="title_box">
						<c:choose>
							<c:when test="${!userInfo.isMember}">
								<p class="t_title"><c:out value="${userInfo.name }"/>님의 수강신청 현황입니다.</p>
							</c:when>
							<c:otherwise>
								<p class="t_title mobile03">* 수료증은 PC에서 확인가능</p>
							</c:otherwise>
						</c:choose>

						<div class="right_box">
							<select id="appStatus" name="appStatus" title="신청상태">
						 	 	${hisco:makeOptions("SM_RSVN_STAT", "신청상태 (전체)", param.appStatus)}
						 	</select>
						</div>
					 </div>
</form>
					<table>
						<caption>수강신청 현황 표입니다. 대학명, 주소, 연락처, 수강신청 바로가기</caption>
						<colgroup>
							<col width="4%">
							<col width="10%">
							<col width="*">
							<col width="10%">
							<col width="12%">
							<col width="5%">
							<col width="8%">
							<col width="8%">
							<col width="8%">
							<col width="12%">
							<col width="8%">
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>신청일시</th>
								<th>강좌명</th>
								<th>기관명</th>
								<th>교육기간</th>
								<th>수강료</th>
								<th>신청상태</th>
								<th>추첨상태</th>
								<th>바로진행</th>
								<th>비고</th>
								<th>수료상태</th>
							</tr>
						</thead>
						<tbody>
<c:if test="${fn:length(list) == 0}">
						<tr>
							<td colspan="11" class="text-center">등록된 데이타가 없습니다.</td>
						</tr>
</c:if>

<c:forEach items="${list}" var="resultVO" varStatus="status">
							<tr>
								<td class="b_number"><c:out value="${paginationInfo.totalRecordCount - (paginationInfo.currentPageNo - 1) * paginationInfo.recordCountPerPage - status.index}"/></td>
								<td class="b_date">
									<fmt:parseDate var="edcReqDate" value="${resultVO.edcReqDate}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${edcReqDate}" pattern="yy-MM-dd"/>
									<fmt:parseDate var="edcReqTime" value="${resultVO.edcReqTime}" pattern="HHmmss"/>
									<fmt:formatDate value="${edcReqTime}" pattern=" HH:mm"/>
								</td>
								<td class="b_name"><a class="a_link" href="<c:url value='/web/edc/program/${resultVO.edcPrgmid}'/>"><c:out value="${resultVO.edcPrgmnm}"/></a></td>
								<td class="b_or"><em class="b_txt">기관</em><c:out value="${resultVO.orgNm}"/></td>
								<td class="b_period"><em class="b_txt">기간</em> <hisco:DateUtil datestr="${resultVO.edcReqSdate}" format="yy.MM.dd"/>~<hisco:DateUtil datestr="${resultVO.edcReqEdate}" format="yy.MM.dd"/></td>
								<td class="b_pay"><em class="b_txt">가격</em>
									<c:choose>
										<c:when test="${resultVO.edcProgmCost < 1}">무료</c:when>
										<c:otherwise>
											<fmt:formatNumber value="${resultVO.edcTotamt}" pattern="#,###"/>원
										</c:otherwise>
									</c:choose>
								</td>
								<td class="b_status"><a class="a_link" href="<c:url value='./myRsvnDetail?edcRsvnReqid=${resultVO.edcRsvnReqid}'/><c:out value="${commandMap.query}"/>" style="word-break: break-all;">${resultVO.edcStatNm }</a></td>
								<td class="b_status02">
									<c:choose>
										<c:when test="${resultVO.edcRsvnRectype eq '2001' and resultVO.rsvnDrwtConftn eq 'Y'}">
											${resultVO.przwinGbnNm}
										</c:when>
									</c:choose>
								</td>
								<td class="b_progress">
									<div class="flexbox flex_direction_center">
										<a href="<c:url value='./myRsvnDetail?edcRsvnReqid=${resultVO.edcRsvnReqid}'/><c:out value="${commandMap.query}"/>" class="btn_s2_c1 bg_w">상세보기</a>
										<c:if test="${resultVO.edcStat eq '2001' and !empty resultVO.webPaymentMethod and resultVO.webPaymentMethod ne '0' and resultVO.vbankSeq < 1}">
											<a href="<c:url value='../../edc/rsvn/pay'/>/${resultVO.edcRsvnNo}" class="btn_s2_c1">결제하기</a>
										</c:if>
										<c:if test="${(resultVO.edcStat eq '4001' and resultVO.editYn eq 'Y') or fn:indexOf(resultVO.edcStat,'10')==0 or resultVO.edcStat eq '2001'}">
											<a href="javascript:fn_reserve_cancel(${resultVO.edcRsvnReqid}, '${resultVO.edcRsvnNo}')" class="btn_s2_c0">${(!empty resultVO.oid and resultVO.edcTotamt >0)?'결제취소':'신청취소'}</a>
										</c:if>
									</div>
								</td>
								<td class="b_etc">
									<c:choose>
										<c:when test="${resultVO.edcStat eq '3001' or resultVO.edcStat eq '3003' }">
											[신청취소 일시]
											<br class="pc"><fmt:formatDate value="${resultVO.moddate}" pattern = "yyyy-MM-dd HH:mm"  />
										</c:when>
										<c:when test="${resultVO.edcStat eq '3004' }">
											[환불처리 일시]
											<br class="pc"><fmt:formatDate value="${resultVO.cancelDate}" pattern = "yyyy-MM-dd HH:mm"  />
										</c:when>
										<c:when test="${resultVO.edcStat eq '2001' }">
											[결제마감 일시]
											<br  class="pc"><fmt:formatDate value="${resultVO.edcPaywaitEnddatetime}" pattern = "yyyy-MM-dd HH:mm"  />
										</c:when>
										<c:when test="${resultVO.edcStat eq '4001' }">
											<c:if test="${resultVO.edcProgmCost > 0}">
												${resultVO.payMethodNm }
												<c:if test="${!empty resultVO.financeNm }">
													<br class="pc"/>(${resultVO.financeNm})
												</c:if>
											</c:if>
										</c:when>
										<c:when test="${resultVO.edcRsvnRectype eq '2001'}">
											[추첨발표일]
											<fmt:parseDate var="drwtDate" value="${resultVO.drwtNtcedate}" pattern="yyyyMMdd"/>
											<br class="pc"/><fmt:formatDate value="${drwtDate}" pattern="yyyy-MM-dd"/>
										</c:when>
										<c:when test="${resultVO.waitNo > 0}">
											${resultVO.waitNo} 순위 대기
										</c:when>
									</c:choose>

								</td>
								<td class="b_certificate">
									<c:if test="${resultVO.edcComplstat eq '2001'}">
										<a href="javascript:printCert(${resultVO.edcRsvnReqid})" class="btn_s2_c1">수료증</a>
									</c:if>
								</td>
							</tr>
</c:forEach>

						</tbody>
					</table>
				</div>
				<!--// table  -->
				<div class="paginate">
					<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./myRsvnList?pageIndex="/>
				</div>
				<!-- pagenate -->
			</div>

	<!-- 팝업 -->
	<form id="edcRsvnMstVO" name="edcRsvnMstVO"  target="rsvnPop" method="post">
		<input type="hidden" name="edcRsvnReqid" id="edcRsvnReqid"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
</body>
