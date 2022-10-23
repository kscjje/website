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

<validator:javascript formName="codeVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script>

	$(document).ready(function() {

		if (document.codeVO.MODE.value == "UPDATE") {
			$('#grpCd').prop("readonly" , true);
		}

	})
</script>
<form:form commandName="codeVO" name="codeVO" action="./parentSave" method="post" >
<input type="hidden" name="MODE" value="<c:out value='${param.MODE}'/>" />
      <div class="row">
              <table id="modaltable" class="table table-bordered">
              <colgroup>
        				<col width="35%">
        				<col width="*">
        			</colgroup>
        		<tbody>
        			<!-- 입력/선택 -->
				<c:set var="inputTxt">입력</c:set>
				<c:set var="inputSelect">선택</c:set>
				<c:set var="inputYes">예</c:set>
				<c:set var="inputNo">아니오</c:set>

				<!-- 코드ID -->
				<c:set var="title">그룹코드</c:set>
				<tr>
					<th><label for="grpCd">${title} <span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="grpCd" title="${title} ${inputTxt}"  style="width:100%"/>
		   				<div><form:errors path="grpCd" cssClass="error" /></div>
					</td>
				</tr>

				<!-- 코드ID명 -->
				<c:set var="title">그룹명 </c:set>
				<tr>
					<th><label for="grpNm">${title} <span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="grpNm" title="${title} ${inputTxt}"  style="width:100%"/>
		   				<div><form:errors path="grpNm" cssClass="error" /></div>
					</td>
				</tr>

				<!-- 기관별 코드 여부 -->
				<tr>
					<th>기관별 코드 여부<span class="pilsu">*</span></th>
					<td class="left">
						<form:select path="orgGrpcdyn" title="기관별 코드 여부" cssClass="txt">
							<form:option value="N" label=" ${inputNo}"/>
							<form:option value="Y"  label=" ${inputYes}"/>
						</form:select>
						<div><form:errors path="orgGrpcdyn" cssClass="error" /></div>
					</td>
				</tr>

				<!-- 수정 여부 -->
				<tr>
					<th>수정 가능<span class="pilsu">*</span></th>
					<td class="left">
						<form:select path="updYn" title="수정 가능여부" cssClass="txt">
							<form:option value="Y"  label=" ${inputYes}"/>
							<form:option value="N" label=" ${inputNo}"/>
						</form:select>
						<div><form:errors path="updYn" cssClass="error" /></div>
					</td>
				</tr>
				<tr>
					<th>삭제 가능<span class="pilsu">*</span></th>
					<td class="left">
						<form:select path="delYn" title="삭제 가능여부" cssClass="txt">
							<form:option value="Y"  label=" ${inputYes}"/>
							<form:option value="N" label=" ${inputNo}"/>
						</form:select>
						<div><form:errors path="delYn" cssClass="error" /></div>
					</td>
				</tr>
				</tbody>
             </table>
      </div>
 </form:form>