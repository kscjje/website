<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:choose>
	<c:when test="${!empty errorMsg }">${errorMsg }</c:when>
	<c:otherwise>
		<style type="text/css">
			pre { white-space:pre-wrap; word-break:break-all;}
		</style>
		<script src="${requestScope.resourceContext}/js/XE_validate.js"></script>
		<div class="modal-dialog max">
			<div class="modal-content">
				<div class="modal-title">
					<h4>강좌신청 동의</h4>
					<a href="javascript:pop_close();" class="btn_close"><em>팝업 닫기</em></a>
				</div>
				<div class="modal-body pop_agree">
					<%@ include file="/WEB-INF/jsp/web/edc/rsvn/rsvnTermsAgreeInclude.jsp" %>

				</div>
				<ul class="btn_area">
					<li><a href="javascript:pop_close();" class="btn_s1_c0 modal1_close" >취소</a></li>
					<c:choose>
						<c:when test="${detailVO.edcStatus eq '대기접수'}">
							<li><a href="javascript:fnApply();" class="btn_s1_c4">대기 접수</a></li>
						</c:when>
						<c:when test="${detailVO.edcStatus eq '신청'}">
							<li><a href="javascript:fnApply();" class="btn_s1_c1">확인</a></li>
						</c:when>
					</c:choose>
				</ul>
				<!-- //btn_area -->
			</div>
		</div>
	</c:otherwise>
</c:choose>