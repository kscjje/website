<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
	 /**
	  * @Class Name : autoContentsDisp.jsp
	  * @Description : 메뉴 자동 표출
	  * @Modification Information
	  * @
	  * @  수정일       수정자      수정내용
	  * @ -------       --------    ---------------------------
	  * @ 2020.09.08    전영석      최초 생성
	  *
	  *  @author 전영석
	  *  @since 2020.09.08
	  *  @version 1.0
	  *
	  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<% pageContext.setAttribute("newLineChar", "\n"); %>

				<div class="sub_cont" id="container">
				
					<div class="inner">
						<h3 class="tit">강좌 약관 동의</h3>
						<div class="top_txt">
							
							<c:if test="${contentsList[0].cntsType eq '1001'}">  <!--  HTML -->
								<c:out value="${contentsList[0].contentsMain}" escapeXml="false"/>	
							</c:if>
							<c:if test="${contentsList[0].cntsType eq '2001'}">  <!--  TEXT -->
							
								<c:out value="${fn:replace(contentsList[0].contentsMain, newLineChar, '<br/>')}" escapeXml="false" />
								
							</c:if>

						</div>

						<div class="signup2 w100">

							<ul class="agree_choice">
								<li class="w100">
									<input type="checkbox" id="total_chk">
									<label for="total_chk">약관을 확인했습니다.</label>
								</li>
							</ul>
					
						</div>
					
					</div>
					
					<!-- //inner -->
									
					<ul class="btn_area">
						<li class="list_btn"><a href="#" onclick="fnGoBack(); return false;" class="btn_ty_m_c4">이전</a></li>
						<li class="list_btn"><a href="#" onclick="fnGoNext(); return false;" class="btn_ty_m_c1">다음</a></li>
					</ul>						
					
					<c:if test="${superAdminYn eq 'Y'}">
						<ul class="btn_area">
							<li class="list_btn"><a href="../../autoContentsUpd?menuNo=<c:out value="${contentsList[0].menuNo}"/>" class="btn_ty_m_c1">컨텐츠 수정</a></li>
						</ul>
					</c:if>					
					
				</div>
				<!-- //sub_cont -->

				<form:form commandName="templateVO" name="templateVO" action="../../../mathhubook/lecturerRegi" class="form-horizontal" method="post">
		
				</form:form>

<script type="text/javascript">
	
	$(document).ready(function() {
		//fn_set_params();
	});

	function fnGoBack() {
		history.go(-1);
	}
	
	function fnGoNext() {
		
		var varChecked = $("#total_chk").is(':checked');
		
		if (!varChecked) {
			alert('약관에 동의하여야 합니다.');
			return;
		}

		var varFrom = document.templateVO;
		
		varFrom.submit();
		
	}	
	
</script>

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>