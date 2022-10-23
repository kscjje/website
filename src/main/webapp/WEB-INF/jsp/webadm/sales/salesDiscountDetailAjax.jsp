<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : salesDiscountList.jsp
	 * @Description : 할인사유별 수입급집계현황 상세 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.01.20     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2022.01.20
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<c:choose>
	<c:when test="${empty list}">
	<tr><td colSpan="17">데이터가 없습니다.</td></tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${list}" var="item" varStatus="status">
		<tr>
			<td>${item.rnum}</td>
			<td>${item.payDate}</td>
			<td>${item.orgNm}</td>
			<td><a href="./receiptDetail?receiptNo=${item.receiptNo}">${item.receiptNo}</a></td>
	<c:choose>
		<c:when test="${item.memNo eq '비회원'}">
			<td>${item.memNo}</td>
			<td>${item.memNm}</td>
		</c:when>
		<c:otherwise>
			<td><a href="../member/userUpdt?memNo=${item.memNo}">${item.memNo}</a></td>
			<td><a href="../member/userUpdt?memNo=${item.memNo}">${item.memNm}</a></td>
		</c:otherwise>
	</c:choose>
			<td>${item.edcPrgmnm}</td>
			<td>${item.itemNm}</td>
			<td style="text-align:right">${item.salnum}</td>
			<td style="text-align:right"><fmt:formatNumber value="${item.costAmt}" type="number" groupingUsed="true"/></td>
			<td style="text-align:right"><fmt:formatNumber value="${item.dcAmt}" type="number" groupingUsed="true"/></td>
			<td>${item.dcTypeNm}</td>
			<td>${item.payMethodNm}</td>
			<td>${item.payLocNm}</td>
			<td style="text-align:right"><fmt:formatNumber value="${item.payAmt}" type="number" groupingUsed="true"/></td>
			<td>${item.cashier}</td>
			<td>${item.oid}</td>
		</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>