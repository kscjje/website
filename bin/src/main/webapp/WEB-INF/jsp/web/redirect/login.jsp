<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<c:if test="${not empty PARAM_MESSAGE}">
	<c:set var="PARAM_MESSAGE">${fn:replace(PARAM_MESSAGE, '"', '\\"')}</c:set>
	<script type="text/javascript">
		alert("<c:out value='${PARAM_MESSAGE}'/>");
	</script>
</c:if>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="redirectForm" id="redirectForm" method="get" action="<c:out value='${LOGIN_URL}'/>" target="_top">
	<%--<sec:csrfInput />--%>
	<input type="hidden" name="returnURL" value="<c:out value='${RETURN_URL}'/>">
</form>
<script type="text/javascript">
	var frm = document.redirectForm;
	frm.submit();
</script>
</body>
</html>