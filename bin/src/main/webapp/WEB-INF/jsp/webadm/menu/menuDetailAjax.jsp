<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : menuDetailAjax.jsp
  * @Description : 메뉴 등록/수정 화면 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.15    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.15
  *  @version 1.0
  *  @see
  *
  */
%>

<script>
	function fn_save() {
		var form = document.menuManageVO;

		if (!validateMenuManageVO(form)) {
			return ;
		} else {
			var msg = "등록하시겠습니까?";
			var url = "./menuSave.json";

			if (form.MODE.value == "UPDATE") {
				msg = "수정하시겠습니까?";
				url = "./menuUpdate.json";
			}
			if (confirm(msg)) {
				$.ajax({
				      url: url,
				      data : $('#menuManageVO').serialize(),
				      type : "POST",
				      dataType : "json",
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 //alert(data.result.msg);
								var  site_srl = $('#searchVO').find('[name="searchKeyword"]').val();
			        	 		load_menu(site_srl);

			        	 		$('#modal-default').find(".close").click();

					        	// window.location.reload();
					         }
				      }
				 });

			}
		}
	}
	$(document).ready(function() {


	})

</script>
<form:form commandName="menuManageVO" name="menuManageVO" action="./menuSave" method="post" >
<input type="hidden" name="MODE" value="<c:out value='${param.MODE}'/>" />
<form:hidden path="menuNo" />
<form:hidden path="siteGubun" />
<form:hidden path="upperMenuNo" />
<form:hidden path="menuOrdr" />
<form:hidden path="menuDepth" />

      <div class="row">
              <table id="modaltable" class="table table-bordered">
              <colgroup>
        				<col width="22%">
        				<col width="*">
        			</colgroup>
				<tr>
					<th><label for="menuNm">메뉴명<span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="menuNm" title="메뉴명" maxlength="70" style="width:100%" />
					               ※  [[[ → 줄바꿈 개행 문자
		   				<div><form:errors path="menuNm" cssClass="error" /></div>
					</td>
				</tr>
				<tr>
					<th><label for="menuUrl">URL</label></th>
					<td class="left">
					    <form:input path="menuUrl" title="메뉴주소" maxlength="70" style="width:100%" />
		   				<div><form:errors path="menuUrl" cssClass="error" /></div>
					</td>
				</tr>
				<!--
				<tr>
					<th><label for="menuOrdr">순서 <span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="menuOrdr" title="메뉴순서" maxlength="70" />
		   				<div><form:errors path="menuOrdr" cssClass="error" /></div>
					</td>
				</tr>
				 -->
				<tr>
					<th>
						<label for="relateImageNm">
						<c:choose>
							<c:when test="${param.siteGubun eq 'USER'}">상단 배경 class명</c:when>
							<c:otherwise>메뉴 이미지명</c:otherwise>
						</c:choose>
						</label>
					</th>
					<td class="left">
					    <form:input path="relateImageNm" title="메뉴 이미지명" maxlength="60" style="width:100%" />
		   				<div><form:errors path="relateImageNm" cssClass="error" /></div>
					</td>
				</tr>
				<tr>
					<th><label for="menuDc">URL패턴</label></th>
					<td class="left">
					    <form:textarea path="menuDc" title="URL패턴" maxlength="200" style="width:100%" />
		   				<div><form:errors path="menuDc" cssClass="error" /></div>
					</td>
				</tr>
<c:choose>
	<c:when test="${menuManageVO.siteGubun eq 'ADMIN' }">
				<tr>
					<th><label for="mainYn">SYS 노출 여부</label></th>
					<td class="left">
					    <label><form:radiobutton path="mainYn" value="Y" /> 사용함(시스템관리자만)</label>
					    &nbsp;&nbsp;&nbsp;
					    <label><form:radiobutton path="mainYn" value="N" /> 사용안함</label>
					</td>
				</tr>
	</c:when>
	<c:otherwise>
				<tr>
					<th><label for="mainYn">GNB 노출 여부</label></th>
					<td class="left">
					    <label><form:radiobutton path="mainYn" value="Y" /> 사용함</label>
					    &nbsp;&nbsp;&nbsp;
					    <label><form:radiobutton path="mainYn" value="L" /> 로그인 유저만</label>
					    &nbsp;&nbsp;&nbsp;
					    <label><form:radiobutton path="mainYn" value="N" /> 사용안함</label>
					</td>
				</tr>
	</c:otherwise>
</c:choose>

				<tr>
					<th><label for="useYn">사용여부<span class="pilsu">*</span></label></th>
					<td class="left">
					    <label><form:radiobutton path="useYn" value="Y" /> 사용함</label>
					    &nbsp;&nbsp;&nbsp;
					    <label><form:radiobutton path="useYn" value="N" /> 사용안함</label>

					</td>
				</tr>

              </table>
      </div>
 </form:form>