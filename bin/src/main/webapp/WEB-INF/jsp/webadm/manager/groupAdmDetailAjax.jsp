<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : groupAdmDetailAjax.jsp
  * @Description : 권한 그룹 등록/수정 화면 JSP
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
	function fn_group_save() {
		var form = document.authorManageVO;

		if (!validateAuthorManage(form)) {
			return ;
		} else {
			var msg = "등록하시겠습니까?";
			var mehtod = "POST";
			var url = "./groupAdmSave.json";
			if (form.MODE.value == "UPDATE") {
				msg = "수정하시겠습니까?";
				url = "./groupAdmUpdate.json";
			}
			if (confirm(msg)) {
				$.ajax({
				      url: url,
				      data : $('#authorManageVO').serialize(),
				      type : mehtod,
				      dataType : "json",
				      headers: {
				    	  "${_csrf.headerName}": "${_csrf.token}"
				      },
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
						         window.location.reload();
					         }
				      }
				 });

			}
		}
	}
	$(document).ready(function() {


	})
</script>
<form:form commandName="authorManageVO" name="authorManageVO" action="./groupSave" method="post" >
<input type="hidden" name="MODE" value="<c:out value='${param.MODE}'/>" />
<form:hidden path="parentRole"/>
      <div class="row">
              <table id="modaltable" class="table table-bordered">
              <colgroup>
        				<col width="20%">
        				<col width="*">
        			</colgroup>
				<tr>
					<th><label for="authorCode">그룹코드<span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="authorCode" title="그룹코드" maxlength="70" style="width:100%" />
		   				<div><form:errors path="authorCode" cssClass="error" /></div>
		   				<div>"ROLE_" 로 시작하는 영문 으로 입력해 주세요.</div>
					</td>
				</tr>
				<tr>
					<th><label for="menuUrl">그룹명 <span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="authorNm" title="그룹명" maxlength="70" style="width:100%" />
		   				<div><form:errors path="authorNm" cssClass="error" /></div>
					</td>
				</tr>
				<tr>
					<th><label for="menuDc">설명</label></th>
					<td class="left">
					    <form:input path="authorDc" title="설명" maxlength="70" style="width:100%" />
		   				<div><form:errors path="authorDc" cssClass="error" /></div>
					</td>
				</tr>

              </table>
      </div>
 </form:form>