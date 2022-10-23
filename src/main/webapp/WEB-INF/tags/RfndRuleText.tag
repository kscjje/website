<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="ruleObj"  type="com.hisco.cmm.object.CamelMap" %>
<%@ attribute name="gubun" rtexprvalue="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:choose>
	<c:when test="${empty ruleObj}">※ 온라인 취소가 불가능합니다.</c:when>
	<c:otherwise>
		<c:if test="${gubun eq 'EXBT'}">관람일 기준</c:if><c:if test="${gubun eq 'EVT'}">강연/행사/영화일 기준</c:if><c:if test="${gubun eq 'EDC'}">교육시작일 기준</c:if>&nbsp;
		<c:choose>
			<c:when test="${ruleObj.rfndNofday > 0 }">${ruleObj.rfndNofday }일 전 <c:if test="${!empty ruleObj.rfndEtime}">${fn:substring(ruleObj.rfndEtime,0,2)}시 ${fn:substring(ruleObj.rfndEtime,2,4)}분</c:if> 까지
			${ruleObj.rfndRate}% 환불 가능합니다.</c:when>
			<c:otherwise>당일 시작 시간 전까지 취소 가능합니다.</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>