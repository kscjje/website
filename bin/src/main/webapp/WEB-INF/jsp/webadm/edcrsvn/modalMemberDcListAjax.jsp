<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	$(document).ready(function() {
		if (!$("#edcRsvnMemno").val()) {
    		$(".dcValidPeriod").prop("disabled", true);
    	}
    	$("#modalDcTbody > tr").click(function() {
  			$("#modalDcTbody tr").css( "background-color", "" );
  			$(this).css( "background-color", "#f4f4f4" );
  			$(this).find("input[type=radio]").prop("checked", true);
  		});
	});
</script>
<table class="table table-bordered table-hover text-nowrap" style="text-align:center; vertical-align:middle">
	<colgroup>
		<col style="width: 5%;">
		<col style="width: 10%;">
		<col style="width: 15%;">
		<col style="width: 15%;">
		<col style="width: 30%;">
		<col style="width: *">
	  </colgroup>
       <thead>
         <tr>
           	<th>No.</th>
           	<th>자동감면설정</th>
           	<th>감면적용</th>
           	<th>감면율</th>
           	<th>감면사유내용</th>
           	<th>자동감면설정<br/>유효기간설정</th>
         </tr>
       </thead>
       <tbody id="modalDcTbody">
     <c:if test="${fn:length(dcList) == 0}">
     </c:if>
     <c:forEach items="${dcList}" var="item" varStatus="status">
		<tr>
			<td>${status.index + 1}</td>
            <td>
            	<c:choose>
           			<c:when test="${not empty item.memNo and status.index == 0}">
            			자동감면설정
           			</c:when>
           			<c:otherwise>
           				<%-- <input type="checkbox" name="pickDc" id="pickDc" value="${item.dcReasonCd}" data-dcrate="${item.dcRate}" data-dcnm="${item.dcNm}"/> --%>
           			</c:otherwise>
           		</c:choose>
            </td>
           	<td>
           		<c:choose>
           			<c:when test="${not empty item.memNo and status.index == 0}">
            			<input type="radio" name="pickDc" id="pickDc" value="${item.dcReasonCd}" checked="checked" data-dcrate="${item.dcRate}" data-dcnm="${item.dcNm}" data-seq="${status.index}"/>
           			</c:when>
           			<c:otherwise>
           				<input type="radio" name="pickDc" id="pickDc" value="${item.dcReasonCd}" data-dcrate="${item.dcRate}" data-dcnm="${item.dcNm}" data-seq="${status.index}"/>
           			</c:otherwise>
           		</c:choose>
           	</td>
           	<td>${item.dcRate}%</td>
           	<td>${item.dcNm}</td>
           	<td>
            <%-- <c:choose>
           			<c:when test="${empty searchVO.memNo}">
           			</c:when>
           			<c:otherwise> --%>
           				<c:choose>
           					<c:when test="${item.dcconfirmYn eq 'Y'}">
           						<c:if test="${empty item.dcconfSdate}">제한없음</c:if>
           						<c:if test="${not empty item.dcconfSdate}">
           							<hisco:DateUtil datestr="${item.dcconfSdate}" format = "yyyy-MM-dd"/> ~ <hisco:DateUtil datestr="${item.dcconfEdate}" format = "yyyy-MM-dd"/>
           						</c:if>  
           					</c:when>
           					<c:when test="${item.dcconfirmYn eq 'N'}">
           						제한없음  
           					</c:when>
           					<c:otherwise>
           						<select name="dcValidPeriod${status.index}" id="dcValidPeriod${status.index}" class="form-control select dcValidPeriod">
		           					<option value="">제한없음</option>
		           					<option value="1">1개월</option>
		           					<option value="2">2개월</option>
		           					<option value="3">3개월</option>
		           					<option value="4">4개월</option>
		           					<option value="5">5개월</option>
		           					<option value="6">6개월</option>
		           					<option value="7">7개월</option>
		           					<option value="8">8개월</option>
		           					<option value="9">9개월</option>
		           					<option value="10">10개월</option>
		           					<option value="11">11개월</option>
		           					<option value="12">12개월</option>
		           				</select>
           					</c:otherwise>
           				</c:choose>           				
           			<%-- </c:otherwise>
           		</c:choose> --%>
           	</td>
		</tr>
	</c:forEach>
	</tbody>
</table>							