<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : regist.jsp
  * @Description : 등록 JSP
  * @Modification Information
  * @
  * @  수정일           수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.04.29  전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.04.29
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<script type="text/javascript" src="<c:url value="/webadm/resources/js/EgovMultiFile.js"/>"></script>

<script type="text/javascript" src="<c:url value="/webadm/validator"/>"></script>

<validator:javascript formName="articleVO" staticJavascript="false" xhtml="true" cdata="false"/>

<script>

function fn_egov_regist_article() {
	
	var form = document.articleVO;

	if (!validateArticleVO(form)) {
		return;
	} else {
		
		var captcha = $('#captcha, input[name="captcha"]');
		captcha_val = captcha.val();

		var msg = "<spring:message code="common.regist.msg" />";
		if (form.nttId.value != "" && form.nttId.value != "0") {
			msg = "수정하시겠습니까?";
		}

		if ("${articleVO.nttId}" < 1) {

			if (captcha_val == '') {
				
				alert('자동입력 방지 문자를 이미지에 보이는 문구나 스피커를 통해 들리는 숫자를 입력하세요.');
				captcha.focus();
				return;
				
			} else {
				
				$.ajax({
					url : "/intrfc/captcha/check",
					method : 'POST',
					data : {
						time : (new Date()).getTime(),
						answer : captcha_val
					},
					dataType : 'json',
					error : function() {
						alert('자동입력 방지 확인 오류가 발생하였습니다.');
					},
					success : function(data) {
						if (data.result == "OK") {
							if (confirm(msg)) {
								form.submit();
							}
						} else {
							alert("자동입력 방지 문자가 맞지 않습니다.");
						}
					}
				});
			}
			
		} else {
			
			if (confirm(msg)) {
				form.submit();
			}
			
		}

	}
}


$('input[name="solutionGbn"]').each(function() {
	
	var varValue = $(this).val();        
	var varChecked = $(this).prop('checked');
	
	console.log(varValue);
	
	if (varChecked) {
		
	}
	
});

</script>

<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit"><c:out value="${SELECTED_MENU_OBJ.menuNm}"/></h3>
		
		<div class="top_txt">
			※ 게시판 작성시 개인정보(주민등록번호, 전화번호, 주소 등)를 노출되지 않도록 유의하여 주시고, 개인정보의 노출로 인하여 제3자에 의해 피해를 입을 수 있사오니 이점 양지하여 주시기 바랍니다. 
		</div>
		<h4 class="stit">정보입력 ( <strong class="red">*</strong>필수입력)</h4>		
		
		<div class="table2 responsive-type mb50">
		
			<form:form commandName="articleVO" name="articleVO" action="./save" method="post" enctype="multipart/form-data" >
				<input type="hidden" name="searchQuery" value="${searchQuery }"/>
				<form:hidden path="nttId"/>
				<form:hidden path="ntcrId"/>
				<form:hidden path="ntcrNm"/>
				<table>
					<caption>${boardMasterVO.bbsNm} 글쓰기(카테고리, 제목, 내용입력)</caption>
					<colgroup>
						<col style="width:11.5%">
						<col style="width:38.5%">
						<col style="width:11.5%">
						<col style="width:38.5%">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" class="vtop">작성자</th>
							<td class="b"><c:out value="${userInfo.name}" /></td>
							<th scope="row" class="vtop">공개여부</th>
							<td class="b">
								<input type="radio" class="radio" name="secretAt" value="Y" onclick="fnSecretAt(1);" checked="" /><em></em>&nbsp;<label for="secretAt">비공개</label>
								&nbsp;&nbsp;&nbsp;
								<input type="radio" class="radio" name="secretAt" value="N" onclick="fnSecretAt(2);" /><em></em>&nbsp;<label for="secretAt">공개</label>							
							</td>
						</tr>
						<tr>
						
							<!-- 
							<th scope="row" class="vtop">휴대폰</th>
							<td colspan="3" class="b">
								<c:out value="${userInfo.ihidNum }" />
							</td>
							-->
							
						</tr>
						
						<!-- 
						<tr>
							<th scope="row"><label for="e_addr">카테고리</label></th>
							<td colspan="3">
								<div class="w360">
									<c:if test="${!empty ctgList }">
										<form:select path="ctgId" title="카테고리 선택" >
											<form:option value=""  >::카테고리 선택::</form:option>
												<c:forEach items="${ctgList}" var="item" varStatus="status">
													<form:option value="${item.ctgId }"><c:out value="${item.ctgNm }"/></form:option>
												</c:forEach>
										</form:select>
									</c:if>
								</div>
							</td>
						</tr>
						-->
						
						<tr>
							<th scope="row"><label for="nttSj">제목 <strong class="red"> *</strong></label></th>
							<td colspan="3">
								<form:input path="nttSj" title="제목을 입력해주세요" maxlength="70" placeholder="제목을 입력해주세요."/>
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="content">내용 <strong class="red"> *</strong></label></th>
							<td colspan="3">
								<form:textarea path="nttCn" title="내용을 입력해 주세요" style="height:260px" placeholder="내용을 입력해주세요."/>
							</td>
						</tr>
						
						<c:if test="${articleVO.nttId <1}">
							<tr>
								<th scope="row"><label for="captcha">자동입력 방지</label></th>
								<td colspan="3">
									<jsp:include page="/WEB-INF/jsp/common/captcha/captcha.jsp"/>
								</td>
							</tr>
						</c:if>
						
	                  	<c:if test="${boardMasterVO.fileAtchPosblAt == 'Y'}">
	                  
							<tr>
								<th scope="row">
									<label for="captcha">첨부파일</label>
								</th>
								<td colspan="3">
						        	 <c:import url="/web/common/upload/includeUploadRegist" >
							       		<c:param name="file_length" value="${boardMasterVO.atchPosblFileNumber}" />
							       		<c:param name="file_ext" value="${boardMasterVO.atchPosblFileExt}" />
							       		<c:param name="file_group_id" value="${articleVO.atchFileId}" />
						        	</c:import>
								</td>
							</tr>                  		
	
	                 </c:if>					
						
					</tbody>
				</table>
			</form:form>
			
		</div>
		
		<ul class="btn_area">
			<li><a href="javascript:history.back()" class="btn_ty_m_c4">취소</a></li>
			<li><a href="javascript:fn_egov_regist_article()" class="btn_ty_m_c1">등록</a></li>
		</ul>
		
		<!-- //btn_area -->
	</div>
</div>

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>