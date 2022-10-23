<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name :  userTabClassAjax.jsp
  * @Description : 수강이력 JSP
  * @Modification Information
  * @
  * @  수정일           수정자              수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.11.17  진수진            최초 생성
  *
  *  @author 진수진
  *  @since 2021.11.17
  *  @version 1.0
  *  @see
  *
  */
%>
<body>
<script>
	$(document).ready(function(){

	});
</script>
	<table class="table table-sm">
        <thead>
          <tr style="text-align:center">
            <th style="width: 60px">번호</th>
            <th>신청일자</th>
            <th>예약번호</th>
            <th>기관명</th>
            <th>프로그램명</th>
            <th>수업시작일</th>
            <th>수업종료일</th>
            <th>교육비</th>
            <th>신청상태</th>
            <th>결제방법</th>
            <th>환불금액</th>
            <th>환불일자</th>
          </tr>
        </thead>
        <tbody style="text-align:center">
        	<c:if test="${empty rsvnList}">
				<tr>
					<td colspan="12">수강이력이 존재하지 않습니다.</td>
				</tr>
			</c:if>
			<c:forEach items="${rsvnList}" varStatus="status" var="item">
				<tr>
					<td>${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}</td>
					<td><hisco:DateUtil datestr="${item.edcReqDate}" format="yyyy-MM-dd"/></td>
					<td><a href="../edcrsvn/edcRsvnInfoDetail?edcRsvnNo=${item.edcRsvnNo}">${item.edcRsvnNo}</a></td>
					<td>${item.orgNm}</td>
					<td>${item.edcPrgmnm}</td>
					<td>${item.edcReqSdate}</td>
					<td>${item.edcReqEdate}</td>
					<td style="text-align:right"><fmt:formatNumber value="${item.edcProgmCost}" pattern="#,###"/></td>
					<td>${item.edcStatNm}</td>
					<td>${item.payMethodNm}</td>
					<td style="text-align:right"><c:if test="${item.cancelAmt < 0}"><fmt:formatNumber value="${item.cancelAmt}"/></c:if></td>
					<td><c:if test="${item.cancelAmt < 0}"><fmt:formatDate value="${item.cancelDate}" pattern="yyyy-MM-dd"/></c:if></td>
				</tr>
			</c:forEach>
        </tbody>
      </table>

      <div class="card-footer p-0">
           	<div class="pagination_2">
				<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="javascript:fn_load_class"/>
			</div>
     </div>

</body>