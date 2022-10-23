<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<script>
		$(document).ready(function(){
			$("#totCount").text(<fmt:formatNumber value="${totCount}"/>);
			$("#chkAll").click(function() {
				$(":checkbox").prop("checked", $(this).prop("checked") ? true : false);
			});
			$("tbody").find(":checkbox").click(function() {
				$("#chkAll").prop("checked", false);
			});

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
				$('#pageIndex').val(1);
				fn_get_rsvn_list();

			});

			if(sort_column == "") sort_column = "edcRsvnNo";
			if(sort_order == "") sort_order = "desc";
			$.each($('.sort') , function(){
				if($(this).attr("id") == sort_column){
					$(this).addClass("active");
					if(sort_order == "asc"){
						$(this).text("▲");
					}
				}
			});

			$("a.tip").hover(
				function() {
					tempTitle = '"환불취소 상세내역"을 여기 눌러서 확인해요~!';

					$("body").append("<div id='tip'></div>");
					$("#tip").text(tempTitle);

					//도움말영역이 나타날 위치 구하기
					var pagex = $(this).offset().left - 20;
					var pagey = $(this).offset().top - $("#tip").innerHeight();

					//$("#tip").show();
					$("#tip").css({left:pagex+"px", top:pagey+"px"}).fadeIn(500);
				},
				function() {
					$(this).attr("title",tempTitle);
					$("#tip").remove(); //마우스커서가 떠나면 툴팁영역 제거
				}
			);
		});

	</script>

	<div class="card-body table-responsive p-0">
		<table class="table table-bordered table-hover text-nowrap sms-target-table" style="text-align:center" id="dataTable${exceldown}">
	        <colgroup>
				<col style="width: 8%;">
				<col style="width: 15%;">
				<col style="width: 9%">
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
			</colgroup>
            <thead>
                <tr>
                   	<th><input type="checkbox" name="all" id="chkAll"/></th>
                   	<th>예약번호 <span id="edcRsvnNo" class="sort">▼</span></th>
                   	<th>접수경로</th>
                   	<th>회원번호</th>
                   	<th>회원명 <span id="edcRsvnCustnm" class="sort">▼</span></th>
                   	<th>ID <span id="id" class="sort">▼</span></th>
					<th>휴대폰번호  <span id="tel" class="sort">▼</span></th>
					<th>성별</th>
					<th>생년월일</th>
					<th>신청일시 <span id="edcReqDate" class="sort">▼</span></th>
					<th>결제마감시간 <span id="waitEnddate" class="sort">▼</span></th>
					<th>신청상태 <span id="edcStat" class="sort">▼</span></th>
					<th>예상금액</th>
					<th>결제금액</th>
					<th>비고</th>
               </tr>
            </thead>
            <tbody>
               <c:if test="${fn:length(rsvnInfoList) == 0}">
                 <tr>
                   	<td colspan="14" align="center">데이타가 없습니다</td>
                 </tr>
               </c:if>
               <c:forEach items="${rsvnInfoList}" var="item" varStatus="status">
               	 <c:set var="cancelBtnType" value="btn-danger"/>
               	 <c:if test="${item.edcOnoffintype eq '오프라인'}"><c:set var="cancelBtnType" value="btn-success"/></c:if>
				 <tr <c:if test="${fn:contains(item.edcStatNm, '취소')}">style="color:red"</c:if>>
                   	<td><input type="checkbox" data-memno="${item.edcMemNo}" data-memnm="${item.edcRsvnCustnm}" data-memhp="<hisco:HpPrint hp="${item.edcRsvnMoblphon}"/>" data-memsmsyn="${item.smsYn}" value=""/></td>
                   	<td>
                   		<c:if test="${empty exceldown}"><a href="./edcRsvnInfoDetail?edcRsvnNo=${item.edcRsvnNo}" class="tip"></c:if>
                   		${item.edcRsvnNo}
                   		<c:if test="${empty exceldown}"></a></c:if>
                   	</td>
                   	<td>${item.edcOnoffintype}</td>
                   	<td><c:if test="${not empty exceldown and item.edcMemNo ne '비회원'}">'</c:if>${item.edcMemNo}</td>
                   	<td>${item.edcRsvnCustnm}</td>
					<td>${item.id}</td>
                   	<td><hisco:HpPrint hp="${item.edcRsvnMoblphon}"/></td>
					<td>${item.genderNm}</td>
					<td><hisco:DateUtil datestr="${item.edcRsvnBirthdate}" format="yyyy-MM-dd"/></td>
					<td><hisco:DateUtil datestr="${item.edcReqDate}${item.edcReqTime}" format="yyyy.MM.dd HH:mm"/></td>
					<td><fmt:formatDate value="${item.edcPaywaitEnddatetime}" pattern="yyyy-MM-dd HH:mm"  /></td>
					<td><c:if test="${item.edcStatNm ne '탈락'}">${item.edcStatNm}</c:if></td>
					<td>
						<fmt:formatNumber value='${item.edcTotamt}'/>
					</td>
					<td>
						<c:choose>
							<c:when test="${item.edcStat eq '3004'}"><fmt:formatNumber value='${item.cancelAmt}'/></c:when>
							<c:when test="${item.payAmt > 0}"><fmt:formatNumber value='${item.payAmt}'/></c:when>
							<c:otherwise>
								<c:if test="${item.edcStat eq '2001'}">
									<button type="button" class="btn btn-success btn-xs" style="margin-top:-18px;margin-bottom:-15px;" onclick="fn_pay_in_cash(${item.edcRsvnReqid}, '${item.edcRsvnNo}')">입금확인</button>
								</c:if>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:if test="${item.edcStatNm eq '탈락'}">${item.edcStatNm}</c:if>
						<c:if test="${fn:substring(item.edcStat, 0, 1) ne '3'}">
							<c:choose>
								<c:when test="${item.edcStat eq '4001'}">
									<c:if test="${item.payAmt > 0}">
									<button type="button" class="btn ${cancelBtnType} btn-xs"  style="margin-left:5px;margin-top:-18px;margin-bottom:-15px;" onclick="fn_cancel(${item.edcRsvnReqid}, '${item.edcRsvnNo}', '${item.edcStat}', '${item.payCancelYn}', '${item.tid}', '${item.editYn}')">
										환불취소
									</button>
									</c:if>
									<c:if test="${item.payAmt == 0}">
									<button type="button" class="btn ${cancelBtnType} btn-xs"  style="margin-left:5px;margin-top:-18px;margin-bottom:-15px;" onclick="fn_all_cancel(${item.edcRsvnReqid}, '${item.edcRsvnNo}')">
										신청취소
									</button>
									</c:if>
								</c:when>
								<c:otherwise><button type="button" class="btn ${cancelBtnType} btn-xs"  style="margin-top:-18px;margin-bottom:-15px;" onclick="fn_all_cancel(${item.edcRsvnReqid}, '${item.edcRsvnNo}')">신청취소</button></c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${fn:substring(item.edcStat, 0, 1) eq '3' and item.remainAmt > 0}"><!-- 부분환불건은 계속 취소가능 -->
							<button type="button" class="btn ${cancelBtnType} btn-xs"  style="margin-left:5px;margin-top:-18px;margin-bottom:-15px;" onclick="fn_cancel(${item.edcRsvnReqid}, '${item.edcRsvnNo}', '${item.edcStat}', '${item.payCancelYn}', '${item.tid}', '${item.editYn}')">
								환불취소
							</button>
						</c:if>
					</td>
                 </tr>
               </c:forEach>
            </tbody>
        </table>
	</div>
	<div class="card-footer p-0">
		<div class="pagination_2">
			<tags:AdminPaging pageInfo="${commandMap.pagingInfo}" pageUrl="javascript:fn_set_page"/>
		</div>
	</div>