<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
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
$('.btn-copy').hide();
$('.btn-save-group').show();

$(document).ready(function (){

	$( ".datepicker" ).datepicker({
		"dateFormat":'yy.mm.dd',
		'monthNames':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'monthNamesShort':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'changeMonth' : true,
		'changeYear':true
	});
});
</script>

<form:form commandName="groupVO" name="groupVO" method="post" >
	<form:hidden class="form-control" path="orgNo"/>
	<div class="row">
		<table id="modaltable" class="table table-bordered">
			<colgroup>
				<col width="25%">
				<col width="*">
			</colgroup>
			<tr>
				<th>기관명</th>
				<td><c:out value="${orgInfoVO.orgNm }"/> [<c:out value="${orgInfoVO.orgNo }"/>]</td>
			</tr>
			<tr>
				<th>날짜</th>
				<td>
					<input name="sdate" id="sdate" class="form-control datepicker" style="width:45%; display:inline-block; " placeholder="시작일"/>
					~
					<input name="edate" id="edate" class="form-control datepicker" style="width:45%; display:inline-block; " placeholder="종료일"/>
				</td>
			</tr>
			<tr>
				<th>요일</th>
				<td>
					<c:set var="tdColor" value=""/>
					<c:forEach items="${weekNames}" var="weekName" varStatus="status">
						<c:choose>
							<c:when test="${status.index==0}">
								<c:set var="tdColor" value="text-red"/>
							</c:when>
							<c:when test="${status.index==6}">
								<c:set var="tdColor" value="text-blue"/>
							</c:when>
							<c:otherwise>
								<c:set var="tdColor" value=""/>
							</c:otherwise>
						</c:choose>
						<label class="<c:out value="${tdColor }"/>">
							<input type="checkbox" name="week" value="<c:out value="${status.index+1 }"/>"/> <c:out value="${weekName }"/>&nbsp;&nbsp;
						</label>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>휴일구분(*)</th>
				<td>
                   	 <form:select path="dateType" class="form-control">
  							<form:options items="${dateTypeList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
                   	 </form:select>
				</td>
			</tr>
			<tr>
				<th>비고</th>
				<td>
					<form:input class="form-control" path="calendarEtc" maxlength="20"/>
				</td>
			</tr>
		</table>
	</div>
 </form:form>

