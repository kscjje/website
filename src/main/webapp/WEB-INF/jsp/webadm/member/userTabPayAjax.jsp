<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name :  userTabPayAjax.jsp
  * @Description : 결제이력 JSP
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
<head>
	<style type="text/css">
		th {vertical-align:middle}
	</style>
	<script>
		$(document).ready(function(){
	
		});
	</script>
</head>
<body>
	<table class="table table-sm">
        <thead>
          <tr style="text-align:center">
            <th style="width: 60px">번호</th>
            <th>결제일자</th>
            <th>거래시간</th>
            <th>영수증번호</th>
            <th>결제금액</th>
            <th>결제방법</th>
            <th>거래구분</th>
            <th>승인번호</th>
            <th>거래주문번호</th>
            <th>환불<br/>계좌번호</th>
            <th>환불<br/>계좌명</th>
            <th>환불계좌<br/>은행명</th>
          </tr>
        </thead>
        <tbody style="text-align:center">
        	<c:if test="${empty pgList}">
				<tr>
					<td colspan="12">신용결제이력이 존재하지 않습니다.</td>
				</tr>
			</c:if>
			<c:forEach items="${pgList}" varStatus="status" var="item">
				<tr>
					<td>${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}</td>
					<td><hisco:DateUtil datestr="${item.payDate}" format="yyyy-MM-dd"/></td>
					<td><hisco:DateUtil datestr="${item.payDate}${item.payTime}" format="HH:mm:ss"/></td>
					<td>${item.receiptNo}</td>
					<td style="text-align:right"><fmt:formatNumber value="${item.payAmt}" pattern="#,###"/></td>
					<td>${item.payMethodNm}(${item.financeNm})</td>
					<td><c:choose><c:when test="${item.payAmt > 0}">결제</c:when><c:otherwise>취소</c:otherwise></c:choose></td>
					<td>${item.appNo}</td>
					<td>${item.oid}</td>
					<td>${item.retAcountNum}</td>
					<td>${item.retDpstrNm}</td>
					<td>${item.retBankNm}</td>
				</tr>
			</c:forEach>
        </tbody>
      </table>

      <div class="card-footer p-0">
           	<div class="pagination_2">
				<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="javascript:fn_load_pay"/>
			</div>
     </div>

</body>