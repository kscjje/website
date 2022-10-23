<%@ tag pageEncoding="utf-8" %>
<%@ attribute name="pageInfo" rtexprvalue="true" type="egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo" %>
<%@ attribute name="pageUrl" rtexprvalue="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String param = "";
	java.util.Enumeration<?> enumeration = request.getParameterNames();
	while(enumeration.hasMoreElements()){
		String key = (String) enumeration.nextElement();

		if(!key.equals("pageIndex")){
			param += "&"+key +"=" + java.net.URLEncoder.encode(request.getParameter(key),"UTF-8");
		}
	}

	int pageGroupSize = 10;
%>
<c:set var="pageUrlName"><c:out value="${pageUrl}" default="fnPageList"/></c:set>
<c:set var="pageGroupSize" value="<%=pageGroupSize %>" />
<c:if test="${pageInfo.totalRecordCount > 0 }">
<c:choose>
	<c:when test="${fn:indexOf(pageUrl,'javascript')==0 }">
			<ul class="pagination pagination-sm no-margin pull-right">
	    		<li class="paginate_button prev" id="dataTables-example_previous">
					<c:if test="${pageInfo.firstPageNoOnPageList <= pageGroupSize}"><a >Previous</a></c:if>
					<c:if test="${pageInfo.firstPageNoOnPageList >  pageGroupSize}"><a href="javascript:goPage(${pageInfo.firstPageNoOnPageList - pageGroupSize})">Previous</a></c:if>
				</li>
				<c:forEach var="pageIndex" begin="${pageInfo.firstPageNoOnPageList}" end="${pageInfo.lastPageNoOnPageList}"><li class="paginate_button<c:if test="${pageIndex == pageInfo.currentPageNo}"> current</c:if>"><a href="javascript:goPage(${pageIndex})">${pageIndex}</a></c:forEach>
				<li class="paginate_button next" id="dataTables-example_next">
				<c:if test="${pageInfo.lastPageNoOnPageList >= pageInfo.totalPageCount}"><a >Next</a></c:if><c:if test="${pageInfo.lastPageNoOnPageList <  pageInfo.totalPageCount}"><a href="javascript:goPage(${pageInfo.firstPageNoOnPageList + pageGroupSize})">Next</a></c:if>
				</li>
			</ul>
	</c:when>
	<c:otherwise>
			<ul class="pagination pagination-sm no-margin pull-right">
	    		<li class="paginate_button prev" id="dataTables-example_previous">
				<c:if test="${pageInfo.firstPageNoOnPageList >  pageGroupSize}"><a href="${pageUrl}${pageInfo.firstPageNoOnPageList - pageGroupSize}<%=param%>">Previous</a></c:if>
				</li>
				<c:forEach var="pageIndex" begin="${pageInfo.firstPageNoOnPageList}" end="${pageInfo.lastPageNoOnPageList}"><li class="paginate_button<c:if test="${pageIndex == pageInfo.currentPageNo}"> current</c:if>"><a href="javascript:goPage(${pageIndex})">${pageIndex}</a></c:forEach>
				<li class="paginate_button next" id="dataTables-example_next">
				<c:if test="${pageInfo.lastPageNoOnPageList < pageInfo.totalPageCount}"><a href="${pageUrlName}${pageInfo.lastPageNoOnPageList + 1}<%=param%>">Next</a></c:if>
				</li>
			</ul>
	</c:otherwise>
</c:choose>

</c:if>

<script>
	function goPage(val){
		var form = document.searchVO;
		form.pageIndex.value = val;
		form.submit();
	}
</script>