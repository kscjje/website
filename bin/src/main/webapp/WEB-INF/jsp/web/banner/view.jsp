<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
	<style>
		.btn_ty_s_c2 {display:none !important;}
	</style>
</head>

<body>
			<div class="sub_visual">
				<div class="inner">
					<h2>알림마당</h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>
			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<div class="sub_top type02">
					<a href="javascript:history.back()">뒤로가기 버튼</a>
					<h3><c:out value="${result.bannerNm }"/> </h3>
				</div>
				<div class="board-view">
					<div class="head">
						<c:out value="${fn:replace(result.bannerDc , crlf , '<br/>')}" escapeXml="false" />
					</div>
					<div class="body">
						<c:out value="${fn:replace(result.linkContent , crlf , '<br/>')}" escapeXml="false" />
					</div>
				</div>
				
			
			
				
				<div class="btn_area">
							<a href="javascript:history.back();" class="btn_s1_c0">뒤로가기</a>
				<c:if test="${result.linkUrl!=null && fn:length(result.linkUrl)>0}">
					<c:choose>
						<c:when test="${fn:startsWith(result.linkUrl,'http')}">
			                <a href="<c:out value='${result.linkUrl}'/>" class="btn_s1_c1" target="<c:out value='${result.linkTarget}'/>">
						</c:when>
						<c:otherwise>
			                <a href="<c:url value='${result.linkUrl}'/>" class="btn_s1_c1" target="<c:out value='${result.linkTarget}'/>">
						</c:otherwise>
					</c:choose>
							바로가기
							</a>
				</c:if>					
				</div>
			</div>
			<!--// sub_content  -->

</body>