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
var delYn = "${delYn}";
$('.btn-delete').hide();
if(delYn=='Y'){
	$('.btn-delete').show();
}
$('.btn-save').show();
</script>

<form:form commandName="calendarInfoVO" name="calendarInfoVO" method="post" >
	<input type="hidden" name="MODE" value="<c:out value='${param.MODE}'/>" />
	<input type="hidden" name="dateTypeOri" value="<c:out value='${calendarInfoVO.dateType}'/>" />
	<div class="row">
		<table id="modaltable" class="table table-bordered">
			<colgroup>
				<col width="25%">
				<col width="*">
			</colgroup>
			<tr>
				<td>날짜</td>
				<td>
					<form:hidden class="form-control" path="calDate"/>
					<input type="text" class="form-control" value="<c:out value="${fn:substring(calendarInfoVO.calDate,0,4) }"/>.<c:out value="${fn:substring(calendarInfoVO.calDate,4,6) }"/>.<c:out value="${fn:substring(calendarInfoVO.calDate,6,8) }"/>" readonly>	
				</td>
			</tr>
			<tr>
				<td>휴일구분(*)</td>
				<td>
                   	 <form:select path="dateType" class="form-control">
  							<form:options items="${dateTypeList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
                   	 </form:select>				
				</td>
			</tr>
			<tr>
				<td>비고</td>
				<td>
					<form:input class="form-control" path="calendarEtc" maxlength="20"/>
				</td>
			</tr>
		</table>
	</div>
 </form:form>
 
