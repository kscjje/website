<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : templateRegistAjax.jsp
  * @Description : 템플릿 등록 화면
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
	$(document).ready(function() {

	});
</script>

<form:form commandName="templateInf" name="templateInf" method="post" >
<input type="hidden" name="MODE" value="<c:out value='${param.MODE}'/>" />
 <form:hidden path="tmplatId" />
      <div class="row">
              <table id="modaltable" class="table table-bordered">
              <colgroup>
        				<col width="25%">
        				<col width="*">
        			</colgroup>
        		<tbody>
				<tr>
					<c:set var="title">템플릿명</c:set>
					<th><label for="tmplatNm"><span class="pilsu">*</span> ${title } </label></th>
					<td class="left">
					    <form:input path="tmplatNm" title="${title }" maxlength="70" style="width:100%"/>
		   				<div><form:errors path="tmplatNm" cssClass="error" /></div>
					</td>
				</tr>
                <tr>
                	<c:set var="title">템플릿 타입</c:set>
               		 <th><span class="pilsu">*</span>  ${title } </th>
                      <td>
                      	<form:select path="tmplatSeCode" title="${title }" >
    					<form:option value="" label="::선택하세요::"/>
							<form:options items="${resultList}"  itemValue="code" itemLabel="codeNm"/>
						</form:select>
                      </td>
                </tr>

				<tr>
					<c:set var="title">템플릿 경로</c:set>
					<th><label for="tmplatCours"><span class="pilsu">*</span>  ${title } </label></th>
					<td class="left">
					    <form:input path="tmplatCours" title="${title }"  maxlength="70" style="width:100%"/>
		   				<div><form:errors path="tmplatCours" cssClass="error" /></div>
		   				<div>/WEB-INF/jsp/web/rsvguidcntr/board/ <br>하위 폴더명 입력.<br>
		   					해당 폴더의 jsp 파일을 호출합니다.
		   				</div>
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