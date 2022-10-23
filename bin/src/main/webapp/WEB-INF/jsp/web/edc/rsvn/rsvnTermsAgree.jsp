<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
	<style type="text/css">
	pre { white-space:pre-wrap; word-break:break-all;}
	</style>
	<script src="${requestScope.resourceContext}/js/XE_validate.js"></script>
</head>
<body>
			<div class="sub_visual">
				<div class="inner">
					<h2>강좌 신청</h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>

			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<div class="sub_top">
					<a href="javascript:history.go(-1);">뒤로가기 버튼</a>
					<h3>강좌 신청자 정보입력</h3>
				</div>
				<!--//top -->
				<%@ include file="/WEB-INF/jsp/web/edc/rsvn/rsvnTermsAgreeInclude.jsp" %>


				<div class="btn_area">
					<a href="javascript:history.go(-1);">취소</a>
					<c:choose>
						<c:when test="${detailVO.edcStatus eq '대기접수'}">
							<a href="javascript:fnApply();" class="btn_s1_c4">대기 접수</a>
						</c:when>
						<c:when test="${detailVO.edcStatus eq '신청'}">
							<a href="javascript:fnApply();" class="btn_blue">확인</a>
						</c:when>
					</c:choose>

				</div>
			</div>
			<!--// sub_content  -->
			
			<div class="modal" id="discountModal">
			<!-- 감면혜택 모달 팝업 -->
			</div>
</body>