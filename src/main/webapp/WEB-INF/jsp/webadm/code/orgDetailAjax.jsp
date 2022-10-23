<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
 /**
  * @Class Name : parentDetailAjax.jsp
  * @Description : 공통코드그룹 상세(ajax)
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.10.27 이윤호              최초 생성
  *
  *  @author 이윤호
  *  @since 2021.10.27
  *  @version 1.0
  *  @see
  *
  */
%>


<script>
var updYn = "${codeVO.updYn}";
var delYn = "${codeVO.delYn}";

$('.btn-delete').hide();
$('.btn-save').hide();

if(updYn!='N'){
	$('.btn-save').show();
}
if(delYn=='Y'){
	$('.btn-delete').show();
}

$(document).ready(function() {
	if (document.codeVO.MODE.value == "UPDATE") {
		$('#cd').prop("readonly" , true);
	}
});
</script>
<form:form commandName="codeVO" name="codeVO" action="./orgSave" method="post" >
<input type="hidden" name="MODE" value="<c:out value='${param.MODE}'/>" />
<form:hidden path="grpCd" />
<form:hidden path="grpNm" />
<form:hidden path="orgNo" />
      <div class="row">
              <table id="modaltable" class="table table-bordered">
              <colgroup>
        				<col width="25%">
        				<col width="*">
        			</colgroup>
                <tr>
					<th>기관명</th>
					<td>
						<c:out value="${orgInfoVO.orgNm }"/> [<c:out value="${orgInfoVO.orgNo }"/>]
					</td>
                </tr>
                <tr>
					<th>그룹명</th>
					<td>
						<c:out value="${parentCode.grpNm }"/> [<c:out value="${parentCode.grpCd }"/>]
					</td>
                </tr>

				<!-- 상세코드 -->
				<c:set var="title">상세코드 </c:set>
				<tr>
					<th><label for="code">${title} <span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="cd" title="${title} ${inputTxt}" maxlength="70"  />
		   				<div><form:errors path="cd" cssClass="error" /></div>
					</td>
				</tr>

				<!-- 상세코드명 -->
				<c:set var="title">상세코드명 </c:set>
				<tr>
					<th><label for="codeNm">${title} <span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="cdNm" title="${title} ${inputTxt}" maxlength="70" />
		   				<div><form:errors path="cdNm" cssClass="error" /></div>
					</td>
				</tr>

				<tr style="display:none;">
					<th><label for="item1">추가 1</label></th>
					<td class="left">
					    <form:input path="item1" title="추가 1" maxlength="70" />
		   				<div><form:errors path="item1" cssClass="error" /></div>
					</td>
				</tr>
				<tr style="display:none;">
					<th><label for="item1">추가 2</label></th>
					<td class="left">
					    <form:input path="item2" title="추가 2" maxlength="70" />
		   				<div><form:errors path="item2" cssClass="error" /></div>
					</td>
				</tr>
				<tr style="display:none;">
					<th><label for="item1">추가 3</label></th>
					<td class="left">
					    <form:input path="item3" title="추가 3" maxlength="70" />
		   				<div><form:errors path="item3" cssClass="error" /></div>
					</td>
				</tr>

				<!-- 사용여부 -->
				<c:set var="title">사용여부 </c:set>
				<tr>
					<th><label for="useAt">${title } <span class="pilsu">*</span></label></th>
					<td class="left">
						<form:select path="useYn" title="${title} ${inputTxt }" cssClass="txt">
							<form:option value="Y"  label=" 사용함"/>
							<form:option value="N"  label=" 사용안함"/>
						</form:select>
						<div><form:errors path="useYn" cssClass="error" /></div>
					</td>
				</tr>

				<tr>
					<th><label for="sortOrder">순서</label> <span class="pilsu">*</span></th>
					<td class="left">
					    <form:input path="sortOrder" title="순서" maxlength="5" />
		   				<div><form:errors path="sortOrder" cssClass="error" /></div>
		   				<div>숫자만 입력해 주세요.</div>
					</td>
				</tr>

				<!-- 상세코드설명 -->
				<c:set var="title">상세코드설명 </c:set>
				<tr>
					<th><label for="codeDc">비고</label></th>
					<td class="nopd">
						<form:textarea path="remark" title="${title} ${inputTxt}" style="width:100%;height:50px"/>
						<div><form:errors path="remark" cssClass="error" /></div>
					</td>
				</tr>
				<tr style="display:none;">
					<th>수정 가능<span class="pilsu">*</span></th>
					<td class="left">
						<form:select path="updYn" title="수정 가능여부" cssClass="txt">
							<form:option value="Y"  label="가능"/>
							<form:option value="N" label="불가"/>
						</form:select>
						<div><form:errors path="updYn" cssClass="error" /></div>
					</td>
				</tr>
				<tr style="display:none;">
					<th>삭제 가능<span class="pilsu">*</span></th>
					<td class="left">
						<form:select path="delYn" title="삭제 가능여부" cssClass="txt">
							<form:option value="Y"  label="가능"/>
							<form:option value="N" label="불가"/>
						</form:select>
						<div><form:errors path="delYn" cssClass="error" /></div>
					</td>
				</tr>

              </table>
      </div>
 </form:form>