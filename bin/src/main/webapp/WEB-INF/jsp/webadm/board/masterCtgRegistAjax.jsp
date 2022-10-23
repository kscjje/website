<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
 /**
  * @Class Name : masterCtgRegistAjax.jsp
  * @Description : 카테고리 등록 화면
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.21    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.21
  *  @version 1.0
  *  @see
  *
  */
%>

<script>
	function fn_save() {
		var form = document.boardCtgVO;

		if (!validateBoardCtgVO(form)) {
			return ;
		} else {
			var msg = "등록하시겠습니까?";
			if (form.MODE.value == "UPDATE") {
				msg = "수정하시겠습니까?";
			}
			if (confirm(msg)) {
				$.ajax({
				      url: './masterCtgSave.json',
				      data : $('#boardCtgVO').serialize(),
				      type : "POST",
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
					        	 fn_ctg_load();
					         }
				      }
				 });
			}
		}
	}
</script>
<form:form commandName="boardCtgVO" name="boardCtgVO" method="post" >
<input type="hidden" name="MODE" value="<c:out value='${param.MODE}'/>" />
 <form:hidden path="ctgId" />
 <form:hidden path="bbsId" />
      <div class="row">
              <table id="modaltable" class="table table-bordered">
              <colgroup>
        				<col width="25%">
        				<col width="*">
        			</colgroup>
        		<tbody>
				<tr>
					<th><label for="ctgNm"><span class="pilsu">*</span> 카테고리이름</label></th>
					<td class="left">
					    <form:input path="ctgNm" title="카테고리 이름" maxlength="50" style="width:100%"/>
		   				<div><form:errors path="ctgNm" cssClass="error" /></div>
					</td>
				</tr>

				<tr>
					<th><label for="ctgSort">정렬 순서 </label></th>
					<td class="left">
					    <form:input path="ctgSort" title="${title }"  maxlength="10" style="width:100%"/>
		   				<div><form:errors path="ctgSort" cssClass="error" /></div>
					</td>
				</tr>

				<!-- 사용여부 -->
				<c:set var="title">사용여부</c:set>
				<tr>
					<th><span class="pilsu">*</span> ${title } </th>
					<td class="left">
						<label><form:radiobutton path="useAt" value="Y" /> 사용함</label>
					    &nbsp;&nbsp;&nbsp;
					    <label><form:radiobutton path="useAt" value="N" /> 사용안함</label>
						<div><form:errors path="useAt" cssClass="error text-red" /></div>
					</td>
				</tr>
				</tbody>
             </table>
      </div>
 </form:form>