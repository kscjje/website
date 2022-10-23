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


<c:choose>
	<c:when test="${codeVO.grpCd eq 'CM_REASON_DC'}">
	<% /* 할인할증종류 일때만 사용 하는 스크립트*/ %>
		<script>
		$(document).ready(function() {
			$('#item2').on( "change", function() {
				checkItem2();
				return ;
			});
			checkItem2();
		});


		function checkItem2(){
			$('.add-tr').remove();
			if($('#item2').val()=='2002'){
				addAgeRange();
			}
			else if($('#item2').val()=='5010'){
				addChildrenRange();
			}
			else if($('#item2').val()=='1002'){
				addGradeRange();
			}
		}

		// 연령그룹 보이기
		function addAgeRange(){
			var add = '';
			add += '<tr class="add-tr">';
			add += '<th><label for="item4">연령범위 <span class="pilsu">*</span></label></th>';
			add += '<td class="left">';
			add += '<input type="number" name="item9" id="item9" title="시작범위연령" maxlength="3" value="<c:out value="${codeVO.item9}"/>" style="width:60px;"/>세 부터 ';
			add += '<input type="number" name="item10" id="item10" title="종료범위연령" maxlength="3" value="<c:out value="${codeVO.item10}"/>"  style="width:60px;"/>세 까지';
			add += '</td>';
			add += '</tr>';
			$('.add-start').after(add);
		}

		// 자녀수 보이기
		function addChildrenRange(){
			var add = '';
			add += '<tr class="add-tr">';
			add += '<th><label for="item4">자녀수 <span class="pilsu">*</span></label></th>';
			add += '<td class="left">';
			add += '최소 <input type="number" name="item9" id="item9" title="최소자녀수" maxlength="2" value="<c:out value="${codeVO.item9}"/>" style="width:60px;"/>명 부터 ';
			add += '<input type="number" name="item10" id="item10" title="최대자녀수" maxlength="2" value="<c:out value="${codeVO.item10}"/>"  style="width:60px;"/>명 까지';
			add += '</td>';
			add += '</tr>';
			$('.add-start').after(add);
		}

		// 장애등급 보이기
		function addGradeRange(){
			var add = '';
			add += '<tr class="add-tr">';
			add += '<th><label for="item4">장애등급 <span class="pilsu">*</span></label></th>';
			add += '<td class="left">';
			add += '<input type="number" name="item9" id="item9" title="최소등급" maxlength="2" value="<c:out value="${codeVO.item9}"/>" style="width:60px;"/>등급 부터 ';
			add += '<input type="number" name="item10" id="item10" title="최대등급" maxlength="2" value="<c:out value="${codeVO.item10}"/>"  style="width:60px;"/>등급 까지';
			add += '</td>';
			add += '</tr>';
			$('.add-start').after(add);
		}

		</script>
	</c:when>
	<c:otherwise>

	</c:otherwise>
</c:choose>

<form:form commandName="codeVO" name="codeVO" action="./childSave" method="post" >
<input type="hidden" name="MODE" value="<c:out value='${param.MODE}'/>" />
<form:hidden path="grpCd" />
<form:hidden path="grpNm" />
<form:hidden path="updYn" value="Y"/>
<form:hidden path="delYn" value="Y"/>

      <div class="row">
              <table id="modaltable" class="table table-bordered">
              <colgroup>
        				<col width="25%">
        				<col width="*">
        			</colgroup>
                <tr>
                  		 <th>그룹 코드</th>
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

<c:choose>
	<c:when test="${codeVO.grpCd eq 'SM_ORG_MTYPE'}">
				<tr>
					<th><label for="item1">상위유형코드 <span class="pilsu">*</span></label></th>
					<td class="left">
						 <form:input path="item1" title="상위유형코드 " maxlength="4" />
					</td>
				</tr>
	</c:when>
	<c:when test="${codeVO.grpCd eq 'CM_AGEGBN'}">
				<tr>
					<th><label for="item4">연령범위 <span class="pilsu">*</span></label></th>
					<td class="left">
					    <form:input path="item4" title="시작범위연령" maxlength="3" style="width:80px;"/>
					    ~
					    <form:input path="item5" title="시작범위연령" maxlength="3" style="width:80px;"/>
		   				<div><form:errors path="item4" cssClass="error" /></div>
		   				<div><form:errors path="item5" cssClass="error" /></div>
					</td>
				</tr>
	</c:when>
	<c:when test="${codeVO.grpCd eq 'CM_REASON_DC'}">
				<tr>
					<th><label for="item1">할인할증구분 <span class="pilsu">*</span></label></th>
					<td class="left">
						<form:select path="item1" title="할인할증구분" style="width:150px; padding:1px 2px;">
							<form:option value=""  label="==선택=="/>
							<form:options items="${dcTypeList}"  itemLabel="cdNm" itemValue="cd" />
						</form:select>
						<div><form:errors path="item1" cssClass="error" /></div>
					</td>
				</tr>
				<tr>
					<th><label for="item2">감면적용그룹 <span class="pilsu">*</span></label></th>
					<td class="left">
						<form:select path="item2" title="감면적용그룹" style="width:150px; padding:1px 2px;">
							<form:option value=""  label="==선택=="/>
							<form:options items="${dcKindList}" itemLabel="cdNm" itemValue="cd" />
						</form:select>
						<div><form:errors path="item2" cssClass="error" /></div>
					</td>
				</tr>
				<tr class="add-start">
					<th><label for="item4">할인/할증율 <span class="pilsu">*</span></label></th>
					<td class="left">
					    <input type="number" id="item4" name="item4" title="할인/할증율" maxlength="3" value="<c:out value="${codeVO.item4}"/>" style="width:80px;"/> (%)
					</td>
				</tr>
	</c:when>

	<c:otherwise>

	</c:otherwise>
</c:choose>



				<!-- 사용여부 -->
				<c:set var="title">사용여부</c:set>
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
					<th><label for="codeDc">${title } </label></th>
					<td class="nopd">
						<form:textarea path="remark" title="${title} ${inputTxt}" style="width:100%;height:50px"/>
						<div><form:errors path="remark" cssClass="error" /></div>
					</td>
				</tr>
<% /*
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
*/ %>
              </table>
      </div>
 </form:form>