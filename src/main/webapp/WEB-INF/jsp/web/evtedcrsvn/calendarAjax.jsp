<%
 /**
  * @Class Name : calendarAjax.jsp
  * @Description : 달력 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.07    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.08.07
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<div class="head">
	<a href="javascript:moveCalendar('2' , '<c:out value="${calMap.currMonth}"/>','<c:out value="${calMap.currYear}"/>')" class="prev">이전</a>
	<strong><c:out value="${calMap.calTitle}"/></strong>
	<a href="javascript:moveCalendar('1' , '<c:out value="${calMap.currMonth}"/>','<c:out value="${calMap.currYear}"/>')" class="next">다음</a>
</div>

<!-- //head-->
<div class="divi">
	<ul>
		<li><i class="pos"></i>예약 가능</li>
		<li><i class="impos"></i>예약 불가능</li>
		<li><i class="hol"></i>휴관</li>
	</ul>
</div>
<!-- //divi -->
<table>
	<caption>월,화,수,목,금,토,일 순으로 나열된 달력 표입니다.</caption>
	<colgroup>
		<col style="width:14.28571428571429%">
		<col style="width:14.28571428571429%">
		<col style="width:14.28571428571429%">
		<col style="width:14.28571428571429%">
		<col style="width:14.28571428571429%">
		<col style="width:14.28571428571429%">
		<col style="width:14.28571428571429%">
	</colgroup>
	<thead>
		<tr>
			<th scope="col">일</th>
			<th scope="col">월</th>
			<th scope="col">화</th>
			<th scope="col">수</th>
			<th scope="col">목</th>
			<th scope="col">금</th>
			<th scope="col">토</th>
		</tr>
	</thead>
	<tbody id="calendarDiv">
		<c:out value="${calMap.calStr}" escapeXml="false" />
	</tbody>
</table>
<!-- //table-->
	<script>
		calDate = $(".head").find("strong").html();
		calYear = calDate.split(".")[0];
		calMon = calDate.split(".")[1];

		//프로그램 일정 로드
		fn_loadSchedules("${calMap.calTitle}".replace("." , ""));
	</script>