<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : authDetailAjax.jsp
  * @Description : 권한정보 등록/수정  화면  JSP
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
		var form = document.roleManage;

		if (!validateRoleManage(form)) {
			return ;
		} else {
			var msg = "등록하시겠습니까?";
			var url = './authSave.json';

			if (form.MODE.value == "UPDATE") {
				msg = "수정하시겠습니까?";
				url = './authUpdate.json';
			}
			if (confirm(msg)) {
				$.ajax({
				      url:  url,
				      data : $('#roleManage').serialize(),
				      type : "POST",
				      dataType : "json",
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
<form:form commandName="roleManage" name="roleManage" action="./authSave" method="post" >
<input type="hidden" name="MODE" value="<c:out value='${param.MODE}'/>" />
<form:hidden path="roleCode" />
       <div class="row">
              <table id="modaltable" class="table table-bordered">
              <colgroup>
        				<col width="20%">
        				<col width="*">
        			</colgroup>
                <tr>
                  		 <th>업무명 <span class="pilsu">*</span></th>
                         <td>
                         	<form:input path="roleNm" title="업무명" maxlength="50" style="width:100%"/>
                         </td>
                </tr>

				<tr>
					<th><label for="rolePtn">패턴 <span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="rolePtn" title="그룹코드" maxlength="200" style="width:100%" />
		   				<div><form:errors path="rolePtn" cssClass="error" /></div>
					</td>
				</tr>
				<tr>
					<th><label for="roleDc">설명 <span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="roleDc" title="그룹명" maxlength="1000" style="width:100%" />
		   				<div><form:errors path="roleDc" cssClass="error" /></div>
					</td>
				</tr>
				<tr>
					<th><label for="roleTyp">타입 <span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:select path="roleTyp">
							<form:option value="url">URL</form:option>
							<form:option value="pointcut">POINTCUT</form:option>
							<form:option value="method">method</form:option>
						</form:select>
		   				<div><form:errors path="roleTyp" cssClass="error" /></div>
					</td>
				</tr>
				<tr>
					<th><label for="roleSort">순서 <span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="roleSort" title="순서" maxlength="10" style="width:100%" />
		   				<div><form:errors path="roleSort" cssClass="error" /></div>
					</td>
				</tr>

              </table>
      </div>
 </form:form>