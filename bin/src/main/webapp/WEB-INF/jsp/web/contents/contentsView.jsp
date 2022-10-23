<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>

	<div class="sub_visual <c:out value="${SELECTED_MENU_OBJ.relateImageNm}"/>">
		<div class="inner">
			<!-- <h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2> -->
			<!--%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%//-->
		</div>
	</div>

			<!--//sub_visual  -->
	<div id="content" class="sub_content">
		<!-- %@ include file="/WEB-INF/templates/web/base/tab_menu.jsp"%> -->
		<c:set var="resourceUrl" value="${requestScope.resourceContext}/" />
		<c:set var="cnts" value="${fn:replace(contentsVO.cnts , '../../resources/' , resourceUrl)}" />
		<c:out value="${cnts }" escapeXml="false"/>


		<c:if test="${editAuthYn eq true }">
		
	        <div class="badge-btn" style="padding-bottom:40px;">
	          <a href="<c:url value="${webDir}/contents/${contentsVO.contentsSeq}/regist"/>" class="green">컨텐츠 수정(관리자전용)</a>
	        </div>
		</c:if>

	</div>
</body>