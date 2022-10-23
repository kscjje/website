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

<% pageContext.setAttribute("newLineChar", "\n"); %>


				<div class="sub_cont" id="container">
					<div class="inner">
						<!--  <h3 class="tit">교육예약</h3> -->
						<div class="cate_wrap">
							
							<c:if test="${contentsList[0].cntsType eq '1001'}">  <!--  HTML -->
								<c:out value="${contentsList[0].contentsMain}" escapeXml="false"/>	
							</c:if>
							<c:if test="${contentsList[0].cntsType eq '2001'}">  <!--  TEXT -->
							
								<c:out value="${fn:replace(contentsList[0].contentsMain, newLineChar, '<br/>')}" escapeXml="false" />
								
							</c:if>

						</div>
					</div>
					<!-- //inner -->
					
					<c:if test="${not empty nextMenuNo}">
						<ul class="btn_area">
							<li class="list_btn"><a href="./autoContentsUpd?menuNo=<c:out value="${contentsList[0].menuNo}"/>" class="btn_ty_m_c1">${nextMenuNo}</a></li>
						</ul>						
					</c:if>
					
					<c:if test="${superAdminYn eq 'Y'}">
						<ul class="btn_area">
							<li class="list_btn"><a href="./autoContentsUpd?menuNo=<c:out value="${contentsList[0].menuNo}"/>" class="btn_ty_m_c1">컨텐츠 수정</a></li>
						</ul>
					</c:if>					
					
				</div>
				<!-- //sub_cont -->


<script type="text/javascript">
	
	$(document).ready(function() {
		//fn_set_params();
	});

</script>