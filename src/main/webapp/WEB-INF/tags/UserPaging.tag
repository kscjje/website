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
		<c:choose><c:when test="${pageInfo.currentPageNo > 1 }"><a href="${pageUrl}(1)" class="prev02" title="처음으로이동">처음</a></c:when><c:otherwise><a class="prev02" title="처음으로이동">처음</a></c:otherwise></c:choose>
		<c:if test="${pageInfo.firstPageNoOnPageList <= pageGroupSize}"><a class="prev" title="이전으로이동">이전</a></c:if>
		<c:if test="${pageInfo.firstPageNoOnPageList >  pageGroupSize}"><a class="prev" href="${pageUrl}(${pageInfo.firstPageNoOnPageList - pageGroupSize})" title="이전으로이동">이전</a></c:if>
		<c:forEach var="pageIndex" begin="${pageInfo.firstPageNoOnPageList}" end="${pageInfo.lastPageNoOnPageList}"><a href="${pageUrl}(${pageIndex})" <c:if test="${pageIndex == pageInfo.currentPageNo}">class="on"</c:if> title="${pageIndex}페이지로이동">${pageIndex}</a></c:forEach>
		<c:if test="${pageInfo.lastPageNoOnPageList >= pageInfo.totalPageCount}"><a class="next">다음</a></c:if>
		<c:if test="${pageInfo.lastPageNoOnPageList <  pageInfo.totalPageCount}"><a href="${pageUrl}(${pageInfo.firstPageNoOnPageList + pageGroupSize})" class="next" title="다음으로이동">다음</a></c:if>
		<c:choose><c:when test="${pageInfo.currentPageNo < pageInfo.totalPageCount }"><a href="${pageUrl}(${pageInfo.totalPageCount })" class="next02" title="마지막으로이동">마지막</a></c:when><c:otherwise><a class="next02" title="마지막으로이동">마지막</a></c:otherwise></c:choose>
	</c:when>
	<c:otherwise>
	    <c:choose><c:when test="${pageInfo.currentPageNo > 1 }"><a href="${pageUrl}1<%=param%>" class="prev02" title="처음으로이동">처음</a></c:when><c:otherwise><a class="prev02" title="처음으로이동">처음</a></c:otherwise></c:choose>
	    <c:if test="${pageInfo.firstPageNoOnPageList <= pageGroupSize}"><a class="prev" title="이전으로이동">이전</a></c:if>
	    <c:if test="${pageInfo.firstPageNoOnPageList >  pageGroupSize}"><a href="${pageUrl}${pageInfo.firstPageNoOnPageList - pageGroupSize}<%=param%>" class="prev" title="이전으로이동">이전</a></c:if>
		<c:forEach var="pageIndex" begin="${pageInfo.firstPageNoOnPageList}" end="${pageInfo.lastPageNoOnPageList}"><a href="${pageUrl}${pageIndex}<%=param%>" <c:if test="${pageIndex == pageInfo.currentPageNo}">class="on"</c:if> title="${pageIndex}페이지로이동">${pageIndex}</a></c:forEach>
		<c:if test="${pageInfo.lastPageNoOnPageList >= pageInfo.totalPageCount}"><a class="next" title="다음으로이동">다음</a></c:if>
		<c:if test="${pageInfo.lastPageNoOnPageList < pageInfo.totalPageCount}"><a href="${pageUrlName}${pageInfo.lastPageNoOnPageList + 1}<%=param%>" class="next" title="다음으로이동">다음</a></c:if>
		<c:choose><c:when test="${pageInfo.currentPageNo < pageInfo.totalPageCount }"><a href="${pageUrl}${pageInfo.totalPageCount }<%=param%>" class="next02" title="마지막으로이동">마지막</a></c:when><c:otherwise><a class="next02" title="마지막으로이동">마지막</a></c:otherwise></c:choose>
	</c:otherwise>
</c:choose>

</c:if>