<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : areaDetailAjax.jsp
  * @Description : 지역  상세
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.17    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.17
  *  @version 1.0
  *  @see
  *
  */
%>
<script>
	function fn_save() {
		var form = document.areaCdVO;

		var msg = "등록하시겠습니까?";
		if (form.MODE.value == "UPDATE") {
			msg = "수정하시겠습니까?";
		}
		if (confirm(msg)) {
			$.ajax({
			      url: './areaSave.json',
			      data : $('#areaCdVO').serialize(),
			      type : "POST",
			      dataType : "json",
			      success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        } else {
					         top.location.reload();
				         }
			      }
			 });

		}
	}
	$(document).ready(function() {
		$('#areaNm').focus();
	})
</script>
<form:form commandName="areaCdVO" name="areaCdVO"  method="post" >
<input type="hidden" name="MODE" value="<c:out value='${param.MODE}'/>" />
<form:hidden path="areaCd" />
<form:hidden path="parentAreaCd" value="0" />
      <div class="row">
              <table id="modaltable" class="table table-bordered">
              <colgroup>
        				<col width="25%">
        				<col width="*">
        			</colgroup>
        		<tbody>
				<c:set var="title">지역명 </c:set>
				<tr>
					<th><label for="areaNm">${title} <span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="areaNm" title="${title} 입력"  style="width:100%"/>
		   				<div><form:errors path="areaNm" cssClass="error" /></div>
					</td>
				</tr>
				<tr>
					<th><label for="sortOrder">정렬순서 <span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="sortOrder" title="정렬순서"  style="width:50%"/>
		   				<div><form:errors path="sortOrder" cssClass="error" /></div>
					</td>
				</tr>
				</tbody>
             </table>
      </div>
 </form:form>