<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${empty exceldown}">
	<script>
		$(document).ready(function(){
			$("#totCount").text(<fmt:formatNumber value="${totCount}"/>);
			var sort_column = $('#searchOrder').val();
			var sort_order = $('#searchOrderDir').val();

			$('.sort').click(function(){
				var id = $(this).attr("id");
				if(id == sort_column){
					if(sort_order == "" || sort_order == "desc"){
						sort_order = "asc";
					}else{
						sort_order = "desc";
					}
				}else{
					sort_order = "asc";
				}
				$('#searchOrder').val(id);
				$('#searchOrderDir').val(sort_order);
				fn_set_page(1);

			});

			//if(sort_column == "") sort_column = "createdate";
			//if(sort_order == "") sort_order = "desc";
			$.each($('.sort') , function(){
				if($(this).attr("id") == sort_column){
					$(this).addClass("active");
					if(sort_order == "asc"){
						$(this).text("▲");
					}
				}
			});
		});
	</script>
</c:if>
<div class="card-body table-responsive p-0">
	<table class="table table-bordered table-hover text-nowrap" style="text-align:center" id="dataTable${exceldown}">
		<colgroup>
			<c:if test="${empty exceldown}"><col style="width: 5%;"></c:if>
			<col style="width: 7%;">
			<col style="width: 15%;">
			<col style="width: *">
			<col style="width: 9%;">
			<col style="width: 9%;">
			<col style="width: 9%;">
			<col style="width: 9%;">
			<col style="width: 9%;">
			<col style="width: 9%;">
			<col style="width: 9%;">
			<col style="width: 9%;">
			<col style="width: 9%;">
			<col style="width: 9%;">
			<col style="width: 9%;">
			<col style="width: 9%;">
			<col style="width: 9%;">
			<col style="width: 9%;">
		</colgroup>
		<thead>
			<tr>
				<c:if test="${empty exceldown}"><th></th></c:if>
				<th>번호</th>
				<th>기관명</th>
				<th>프로그램명 <span id="programNm" class="sort">▼</span></th>
				<th>모집차수</th>
				<th>접수기간</th>
				<th>접수상태 <span id="apply" class="sort">▼</span></th>
				<th>접수마감</th>
				<th>수업시작일</th>
				<th>접수방식 <span id="rsvnType" class="sort">▼</span></th>
				<th>추첨</th>
				<th>정원</th>
				<th>신청인원</th>
				<th>결제완료</th>
				<th>입금대기</th>
				<th>취소</th>
				<th>잔여정원</th>
				<th>배정대기</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${fn:length(programList) == 0}">
			<tr>
				<td colspan="18" align="center">데이타가 없습니다</td>
			</tr>
			</c:if>
			<c:forEach items="${programList}" var="item" varStatus="status">
			<tr>
				<c:if test="${empty exceldown}">
					<td><input type="checkbox" value="" data-prgmid="${item.edcPrgmNo}" data-setseq="${item.edcRsvnsetSeq}" data-orgno="${item.orgNo}" <%-- <c:if test="${item.edcStatus ne '신청'}">disabled</c:if> --%>/></td>
				</c:if>
				<td>
					<c:choose>
						<c:when test="${empty exceldown}">
							${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}
						</c:when>
						<c:otherwise>
							${paginationInfo.totalRecordCount - status.index}
						</c:otherwise>
					</c:choose>
				</td>
				<td>${item.orgName}</td>
				<td>
					<c:if test="${empty exceldown}"><a href="javascript:;" onclick="fnGoDetail('./edcRsvnInfoList' , '${item.edcPrgmNo}' , '${item.edcRsvnsetSeq}')"></c:if>
					${item.edcPrgmnm}
					<c:if test="${empty exceldown}"></a></c:if>
				</td>
				<td>${item.edcRsvnsetNm} (${item.edcRsvnsetSeq})</td>
				<td><hisco:DateUtil datestr="${item.edcRsvnSdate}" format="yyyy-MM-dd"/> ~ <hisco:DateUtil datestr="${item.edcRsvnEdate}" format="yyyy-MM-dd"/></td>
				<td>
					<c:choose>
						<c:when test="${item.edcStatus eq '신청'}">접수중</c:when>
						<c:otherwise>${item.edcStatus}</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:if test="${empty exceldown and item.edcStatus eq '신청'}">
						<button type="button" class="btn btn-danger btn-xs"  style="margin-top:-18px;margin-bottom:-15px;" onclick='fn_modal_force_close("${item.comcd}", "${item.edcPrgmNo}","${item.edcRsvnsetSeq}", "${item.edcPrgmnm}")' data-toggle="modal" data-target="#modal-force-close">강제마감</button>
					</c:if>
				</td>
				<td><hisco:DateUtil datestr="${item.edcSdate}" format="yyyy-MM-dd"/></td>
				<td>${item.rsvnTypeNm}</td>
				<td>
					<c:if test="${item.edcRsvnRectype eq '2001'}"> <!-- 추첨 -->
						<c:choose>
							<c:when test="${item.przwinStat eq '1101'}">
								당첨확정
							</c:when>
							<c:otherwise>
								<c:if test="${empty exceldown}">
								<button type="button" class="btn btn-success btn-xs" style="margin-top:-18px;margin-bottom:-15px;" onclick="fn_draw_edc_rsvn_info(${item.orgNo}, ${item.edcPrgmNo})">추첨</button>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:if>
				</td>
				<td>${item.edcPncpa}</td>
				<c:if test="${not empty exceldown}">
					<td><fmt:formatNumber value="${item.statApplyCnt}"/></td>
				</c:if>
				<c:if test="${empty exceldown}">
					<td>
						<a href="javascript:;" onclick="fnGoDetail('./edcRsvnInfoList' , '${item.edcPrgmNo}' , '${item.edcRsvnsetSeq}')"><fmt:formatNumber value="${item.statApplyCnt}"/></a>
					</td>
				</c:if>
				<c:choose>
					<c:when test="${item.edcRsvnRectype eq '2001'}"> <!-- 추첨 -->
						<c:choose>
							<c:when test="${item.przwinStat eq '1101'}">
								<td><fmt:formatNumber value="${item.statPaydoneCnt}"/></td>
								<td><fmt:formatNumber value="${item.statPaywaitCnt}"/></td>
								<td><fmt:formatNumber value="${item.statCancelCnt}"/></td>
								<td><fmt:formatNumber value="${item.statRemainCnt}"/></td>
								<td></td>
							</c:when>
							<c:otherwise>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<td><fmt:formatNumber value="${item.statPaydoneCnt}"/></td>
						<td><fmt:formatNumber value="${item.statPaywaitCnt}"/></td>
						<td><fmt:formatNumber value="${item.statCancelCnt}"/></td>
						<td><fmt:formatNumber value="${item.statRemainCnt}"/></td>
						<c:choose>
							<c:when test="${item.edcRsvnRectype eq '1002'}"><!-- 선착대기 -->
								<c:if test="${not empty exceldown}">
									<td><fmt:formatNumber value="${item.statAssignWaitCnt}"/></td>
								</c:if>
								<c:if test="${empty exceldown}">
								<td>
									<a href="javascript:;" onclick="fnGoDetail('./edcRsvnInfoAssignWaitList' , '${item.edcPrgmNo}' , '${item.edcRsvnsetSeq}')"><fmt:formatNumber value="${item.statAssignWaitCnt}"/></a>
								</td>
								</c:if>
							</c:when>
							<c:otherwise>
								<td></td>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</tr>
			</c:forEach>
		</tbody>
	</table>
 </div>
 <c:if test="${empty exceldown}">
 <div class="card-footer p-0">
	<div class="pagination_2">
		<tags:AdminPaging pageInfo="${commandMap.pagingInfo}" pageUrl="javascript:fn_set_page"/>
	</div>
</div>
</c:if>