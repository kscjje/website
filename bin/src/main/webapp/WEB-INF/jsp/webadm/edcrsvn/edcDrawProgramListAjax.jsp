<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<style type="text/css">
		.table thead th {background-color:#f4f6f9;vertical-align:middle}
	</style>
	<script>
		$(document).ready(function(){
			var totCount = ${totCount};
			var height = totCount >= 7 ? 570 : (170 + totCount*50);
			$("#divDrawCard").parent().css("height", height + "px");

			$("#chkAll").click(function() {
				$("#tbodyDrawProgramList :checkbox").prop("checked", $(this).prop("checked") ? true : false);
				fn_change_pickbtn_status();
				fn_change_dodrawbtn_status();
			});

			$("tbody").find(":checkbox").click(function() {
				$("#chkAll").prop("checked", false);
				fn_change_pickbtn_status();
				fn_change_dodrawbtn_status();
			});

			//tr클릭시 체크박스 on/off
			$("#tbodyDrawProgramList > tr > td").click(function() {
				if ($(this).hasClass("chk")) return;
				var checked = $(this).parent().find(":checkbox").prop("checked");
				$(this).parent().find(":checkbox").prop("checked", !checked);
				$("#chkAll").prop("checked", false);
				fn_change_pickbtn_status();
				fn_change_dodrawbtn_status();
			});

			$('.colorChange').click(function(){
				$('#programTable').find("tbody tr").css("background-color" , "#fff").css("color" , "#666");
	    		$(this).css("background-color" , "#adb5bd").css("color" , "white");
			})
		});
	</script>
	<table class="table table-bordered text-nowrap table-hover" style="text-align:center;" id="programTable">
		<colgroup>
			<col style="width: 5%;">
			<col style="width: 20%;">
			<col style="width: 15%">
			<col style="width: 45%">
			<col style="width: 20%">
			<col style="width: 10%">
			<col style="width: *">
		</colgroup>
		<thead>
			<tr>
				<th style="padding-left:15px"><input type="checkbox" name="chkAll" id="chkAll" value=""/></th>
				<th>기관명</th>
				<th>프로그램<br/>고유번호</th>
				<th>프로그램명</th>
				<th>모집차수명</th>
				<th>정원</th>
				<th>접수<br/>인원</th>
			</tr>
		</thead>
		<tbody id="tbodyDrawProgramList">
			<c:if test="${fn:length(programList) == 0}">
				<tr>
					<td colspan="7" align="center">
					<c:choose>
						<c:when test="${searchVO.searchOrgNo eq '-1'}">
							기관을  선택해 주세요
						</c:when>
						<c:otherwise>
							데이타가 없습니다
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</c:if>
			<c:forEach items="${programList}" var="item" varStatus="status">
				<c:if test="${status.index == 0}">
				<input type="hidden" id="modalEdcPrgmnm" value="${item.edcPrgmnm}"/>
				<input type="hidden" id="modalEdcRsvnPeriod" value="${item.edcRsvnPeriod}"/>
				</c:if>
				<c:choose>
					<c:when test="${not empty item.przwinStat}">
						<tr class="colorChange" onclick="fn_get_draw_done_rsvn_list(${item.orgNo}, ${item.edcPrgmNo}, ${item.edcRsvnsetSeq}, '${searchVO.periodCondition}', '${searchVO.searchYear}', '${searchVO.searchMonth}', '${searchVO.channel}')">
					</c:when>
					<c:otherwise>
						<tr class="colorChange" onclick="fn_get_draw_done_rsvn_list()">
					</c:otherwise>
				</c:choose>
					<c:choose>
						<c:when test="${not empty item.przwinStat}">
							<td>추첨<br/>완료</td>
						</c:when>
						<c:otherwise>
							<td style="padding-left:15px" class="chk"><input type="checkbox" name="edcPrgmNo" id="edcPrgmNo" value="${item.edcPrgmNo}"/></td>
						</c:otherwise>
					</c:choose>
					<td>${item.orgName}</td>
					<td>${item.edcPrgmNo}</td>
					<td>${item.edcPrgmnm}</td>
					<td>${item.orgName}</td>
					<td>${item.edcPncpa}</td>
					<td>${item.statApplyCnt}</td>
					<input type="hidden" id="edcRsvnsetSeq" value="${item.edcRsvnsetSeq}"/>
					<input type="hidden" name="orgNo" id="orgNo" value="${item.orgNo}"/>
					<input type="hidden" name="edcPrgmnm" id="edcPrgmnm" value="${item.edcPrgmnm}"/>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="card-footer p-0">
		<div class="pagination_2" style="padding-top:10px">
			<tags:AdminPaging pageInfo="${commandMap.pagingInfo}" pageUrl="javascript:fn_set_page"/>
		</div>
	</div>