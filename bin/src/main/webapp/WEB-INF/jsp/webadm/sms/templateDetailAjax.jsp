<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	function fn_save() {
		var form = document.smsTemplateVO;

		if(form.msgName.value == ""){
			alert("메시지명을 입력하세요.");
			form.msgName.focus();
			return;
		}
		if(form.sendTelno.value == ""){
			alert("콜백 번호를 입력하세요.");
			form.sendTelno.focus();
			return;
		}
		if(form.sendmsg.value == ""){
			alert("발송내용을 입력하세요.");
			form.sendmsg.focus();
			return;
		}

		var msg = "등록하시겠습니까?";
		var url = "./templateInsert.json";

		if (form.MODE.value == "UPDATE") {
			msg = "수정하시겠습니까?";
			url = "./templateUpdate.json";
		}
		if (confirm(msg)) {
			$.ajax({
			      url: url,
			      data : $('#smsTemplateVO').serialize(),
			      type : "POST",
			      dataType : "json",
			      success: function(data) {
			    	  alert(data.result.msg);
			    	   if (data.result.code == "ERROR") {

				       } else {
					        top.location.reload();
				       }
			      }
			 });

		}
	}
	$(document).ready(function() {
		//$('#areaName').focus();
	})
</script>
<form:form commandName="smsTemplateVO" name="smsTemplateVO"  method="post" >
<input type="hidden" name="MODE" value="<c:out value='${param.MODE}'/>" />
<form:hidden path="oldMsgno" />
      <div class="row">
              <table id="modaltable" class="table table-bordered">
              	<colgroup>
        				<col width="15%">
        				<col width="35%">
        				<col width="20%">
        				<col width="30%">
        			</colgroup>
        		<tbody>
        		<tr>
					<th><label for="msgName">템플릿코드 <span class="pilsu">*</span></label></th>
					<td colspan="3" class="left">
					    <form:input path="msgno"  style="width:100%" placeHolder="없을 경우 자동 생성" maxlength="50"/>
		   				<div><form:errors path="msgno" cssClass="error" /></div>
		   				<c:if test="${smsTemplateVO.msgno < 1}"><div>※ 0 인 경우 자동 생성 됩니다.</div></c:if>
					</td>
				</tr>
				<tr>
					<th><label for="msgName">메시지명 <span class="pilsu">*</span></label></th>
					<td colspan="3" class="left">
					    <form:input path="msgName"  style="width:100%" placeHolder="EX) 예약 신청마감이 마감될 때" maxlength="50"/>
		   				<div><form:errors path="msgName" cssClass="error" /></div>
					</td>
				</tr>
				<tr>
					<th><label for="sendTelno">콜백번호 <span class="pilsu">*</span></label></th>
					<td colspan="3" class="left">
					    <form:input path="sendTelno"  style="width:100%" placeHolder="(-) 포함하여 연락처를 기입하세요 " maxlength="20"/>
		   				<div><form:errors path="sendTelno" cssClass="error" /></div>
					</td>
				</tr>
				<tr>
					<th >발송방법  <span class="pilsu">*</span></th>
					<td >
                		<label><form:radiobutton path="msgSendmethod" value="1001" /> 카카오</label>
	    				&nbsp;&nbsp;&nbsp;
	    				<label><form:radiobutton path="msgSendmethod" value="2001" /> 문자발송</label>
						<div><form:errors path="msgSendmethod" cssClass="error" /></div>
					</td>
					<th><label for="kkoMessageTemplateId">카카오 템플릿ID</label></th>
					<td >
					    <form:input path="kkoMessageTemplateId"  style="width:100%" placeHolder=" " maxlength="100"/>
		   				<div><form:errors path="kkoMessageTemplateId" cssClass="error" /></div>
					</td>
				</tr>
				<tr>
					<th><label for="sendmsg">발송내용 <span class="pilsu">*</span></label></th>
					<td colspan="2" class="left">
					    <form:textarea path="sendmsg"  style="width:100%;height:300px" maxlength="2000"/>
		   				<div><form:errors path="sendmsg" cssClass="error" /></div>
					</td>
					<td></td>
				</tr>
<c:if test="${!empty smsTemplateVO.regdate }">
				<tr>
					<th>등록일</th>
					<td class="left"><c:out value="${smsTemplateVO.reguser}"/> / <fmt:formatDate value="${smsTemplateVO.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
					<th>수정일</th>
					<td class="left"><c:out value="${smsTemplateVO.moduser}"/> / <fmt:formatDate value="${smsTemplateVO.moddate}" pattern="yyyy-MM-dd HH:mm"/></td>
				</tr>
</c:if>
				</tbody>
             </table>
      </div>
 </form:form>