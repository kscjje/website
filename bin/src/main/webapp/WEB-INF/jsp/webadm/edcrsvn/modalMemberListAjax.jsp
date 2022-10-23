<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<script>
		$(document).ready(function(){
			$("#modalMemberTbody > tr").click(function() {
      			$("#modalMemberTbody tr").css( "background-color", "" );
      			$(this).css( "background-color", "#f4f4f4" );
      			$(this).find("input[type=radio]").prop("checked", true);
      		});
			<c:if test="${fn:length(memberList) > 0}">
			$('#dataTable').DataTable({
      			columnDefs: [
      				{orderable: false, targets: [0,1,2,3,5,6]}
      			],
      			//"order": [[ 1, "asc" ]], //회원번호
      			"paging": false,
      			"searching": false,
      			"info": false,
      			"destroy": true
      	    });
			</c:if>
		});
	</script>

	<div class="card-body table-responsive p-0">
		<table class="table table-bordered table-hover text-nowrap sms-target-table" style="text-align:center" id="dataTable">
	        <colgroup>
				<col style="width: 5%;">
				<col style="width: 15%;">
				<col style="width: 30%;">
				<col style="width: 15%;">
				<col style="width: 20%;">
				<col style="width: *">
			</colgroup>
            <thead>
              <tr>
               	<th></th>
               	<th>회원번호</th>
               	<th>회원명</th>
               	<th>생년월일</th>
               	<th style="cursor:pointer" id="thSort">휴대폰번호 ▼</th>
               	<th>ID</th>
               	<th>성별</th>
              </tr>
            </thead>
            <tbody id="modalMemberTbody">
               <c:if test="${fn:length(memberList) == 0}">
                 <tr>
                   	<td colspan="7" align="center">데이타가 없습니다</td>
                 </tr>
               </c:if>
				<c:forEach items="${memberList}" var="item" varStatus="status">
				 <tr >
                   	<td><input type="radio" name="pick" value="${item.memNo}" data-gender="${item.genderCd}" data-birthsec="${item.birthSec}"
                   				data-id="${item.id}" data-joindate="<fmt:formatDate value='${item.regdate}' pattern='yyyy-MM-dd'/>"/></td>
                   	<td>${item.memNo}</td>
                   	<td>${item.memNm}</td>
					<td><hisco:DateUtil datestr="${item.birthDate}" format="yyyy-MM-dd"/></td>
                   	<td><hisco:HpPrint hp="${item.hp}"/></td>
                   	<td>${item.id}</td>
					<td>
						<c:choose>
							<c:when test="${item.gender eq '0'}">미기재</c:when>
							<c:when test="${item.gender eq '1'}">남성</c:when>
							<c:when test="${item.gender eq '2'}">여성</c:when>
							<c:when test="${item.gender eq '3'}">성별미상</c:when>
							<c:when test="${item.gender eq '9'}">기타</c:when>
							<c:otherwise>${item.gender }</c:otherwise>
						</c:choose>
					</td>
                 </tr>
               </c:forEach>
            </tbody>
        </table>
        <c:if test="${fn:length(memberList) > 0}">
		<div class="pagination_2">
			<tags:AdminPaging pageInfo="${commandMap.pagingInfo}" pageUrl="javascript:fn_set_page"/>
		</div>
		</c:if>
	</div>