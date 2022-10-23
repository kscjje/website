<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
	 /**
	  * @Class Name : autoContentsUpd.jsp
	  * @Description : 교육 예약 개인 목록 리스트
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
		<h3 class="tit">컨텐츠 수정</h3>
		<div class="table2 responsive-type mb50">
		
			<form:form commandName="templateVO" name="templateVO" action="./contentsSave" method="post" enctype="multipart/form-data">
			
					<input name="menuNo"    type="hidden"  value="<c:out value='${contentsDetail[0].menuNo}' />" />
					<input name="mode"      type="hidden"  value="update" />
			
					<table>
						<caption>컨텐츠 수정</caption>
						<colgroup>
							<col style="width:11.5%">
							<col style="width:38.5%">
							<col style="width:11.5%">
							<col style="width:38.5%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop">메뉴번호</th>
								<td class="b">
									<label class="col-sm-2 col-form-label">${contentsDetail[0].menuNo}</label>
								</td>
								<th scope="row" class="vtop">컨텐츠 속성</th>
								<td class="b">
			                    	<select name="cntsType" id="cntsType" class="form-control " style="width:100px;float:left">
			                    		<option value="1001" <c:if test="${contentsDetail[0].cntsType eq '1001'}"> selected </c:if> > HTML</option>
			                    		<option value="2001" <c:if test="${contentsDetail[0].cntsType eq '2001'}"> selected </c:if> > TEXT</option>
			                    	</select>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">컨텐츠 내용</th>
								<td class="b" colspan="3">
									<textarea name="contentsMain" title="컨텐츠 내용" class="form-control " rows="15" cols="100" style="width:100%;float:left" type="text"><c:out value='${contentsDetail[0].contentsMain}'/></textarea>
								</td>
							</tr>							
						
						</tbody>
					</table>
						
			</form:form>
			
		</div>
		
		<ul class="btn_area">
			<li><a href="javascript:history.back()" class="btn_ty_m_c4">취소</a></li>
			<li><a href="#" onclick="fn_save(); return false;" class="btn_ty_m_c1">수정</a></li>
		</ul>
		<!-- //btn_area -->
	</div>
	
</div>

<script>

	function fn_save() {
		
		console.log("call fn_save();");
		
		var varFrom = document.templateVO;
		
		var varContentsMain = $($("textarea[name='contentsMain']")).val();
		if ((varContentsMain === undefined) || (varContentsMain == null) || (varContentsMain == '')) {
			alert("컨텐츠 내용은 비울 수 없습니다.");
			$("textarea[name='contentsMain']").focus();
			return;
		}			
		
		var msg = "<spring:message code='common.regist.msg' />";
		if (varFrom.mode.value == "update") {
			msg = "수정하시겠습니까?";
		}
		
		if (confirm(msg)) {
			varFrom.submit();
		}		
		
	}
	
</script>

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>