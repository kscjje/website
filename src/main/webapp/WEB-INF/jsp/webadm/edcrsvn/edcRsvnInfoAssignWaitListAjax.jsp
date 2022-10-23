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
			if(sort_order == "") sort_order = "asc";
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
			</colgroup>
            <thead>
                <tr>
                   	<c:if test="${empty exceldown}"><th><input type="checkbox" name="all" id="chkAll"/></th></c:if>
                   	<th>예약번호 <span id="edcRsvnNo" class="sort">▼</span></th>
                   	<th>접수경로</th>
                   	<th>대기순위</th>
                   	<th>회원번호</th>
                   	<th>회원명 <span id="edcRsvnCustnm" class="sort">▼</span></th>
                   	<th>ID <span id="id" class="sort">▼</span></th>
					<th>휴대폰번호 <span id="tel" class="sort">▼</span></th>
					<th>성별</th>
					<th>생년월일</th>
					<th>신청일시 <span id="edcReqDate" class="sort">▼</span></th>
					<c:if test="${empty exceldown}">
					<th>대기자배정</th>
					<th>취소</th>
					</c:if>
               </tr>
            </thead>
            <tbody>
               <c:if test="${fn:length(rsvnInfoList) == 0}">
                 <tr>
                   	<td colspan="13" align="center">데이타가 없습니다</td>
                 </tr>
               </c:if>
               <c:forEach items="${rsvnInfoList}" var="item" varStatus="status">
				 <tr >
                   	<c:if test="${empty exceldown}">
                   	<td><input type="checkbox" data-memno="${item.edcMemNo}" data-memnm="${item.edcRsvnCustnm}" data-memhp="<hisco:HpPrint hp="${item.edcRsvnMoblphon}"/>" data-memsmsyn="${item.smsYn}" value=""/></td>
                   	</c:if>
                   	<td>${item.edcRsvnNo}</td>
                   	<td>${item.edcOnoffintype}</td>
                   	<td>${item.waitNo}</td>
                   	<td><c:if test="${not empty exceldown}">'</c:if>${item.edcRsvnMemno}</td>
                   	<td>${item.edcRsvnCustnm}</td>
					<td>${item.id}</td>
                   	<td><hisco:HpPrint hp="${item.edcRsvnMoblphon}"/></td>
					<td>${item.genderNm}</td>
					<td><hisco:DateUtil datestr="${item.edcRsvnBirthdate}" format="yyyy-MM-dd"/></td>
					<td><hisco:DateUtil datestr="${item.edcReqDate}${item.edcReqTime}" format="yyyy.MM.dd HH:mm"/></td>
					<c:if test="${empty exceldown}">
					<%-- <td class="inbtn"><button type="button" class="btn btn-info btn-xs" onclick='fn_program_register("${item.comcd}", ${item.edcRsvnReqid}, "${item.edcRsvnNo}", "${item.edcRsvnCustnm}")'>수강등록</button></td> --%>
					<td class="inbtn"><button type="button" class="btn btn-info btn-xs" onclick='fn_set_1000_2001("${item.edcRsvnNo}", "${item.edcRsvnCustnm}")' data-toggle="modal" data-target="#modal-1000-2001">배정등록</button></td>
					<td class="inbtn"><button type="button" class="btn btn-secondary btn-xs" onclick='fn_cancel_waiting("${item.comcd}", ${item.edcRsvnReqid}, "${item.edcRsvnNo}", "${item.edcRsvnCustnm}")'>대기취소</button></td>
					</c:if>
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