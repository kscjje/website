<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : tweduOpenDetail.jsp
	 * @Description : 마울배움터 교육현황 출석부 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.11.05     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2021.11.23
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<head>
<script type="text/javascript">
var JSON_REQ_SUFFIX = ".json";
var NAME_OF_WEEK = ["일","월","화","수","목","금","토"];

var fn_loadAttendances = function(edc_prgmid){
	var queryStr = "?edcPrgmid="+edc_prgmid;

	jQuery.ajax({
		url : "./lectAttendList"+JSON_REQ_SUFFIX+queryStr,
		type: "GET",
		cache : false,
		success: function(json) {
			if (json.result.code == "ERROR") {
				alert(json.result.msg);
			} else {
				for(var i=0;i<json.data.length;i++){
					var attData = json.data[i];

					if(attData.edcAtendGbn == "1001"){
						$('#DTCHK_'+attData.edcRsvnReqid+'_' + attData.edcAtendDate).addClass("not-attended").text("결석");

					}else if(attData.edcAtendGbn == "2001"){
						$('#DTCHK_'+attData.edcRsvnReqid+'_' + attData.edcAtendDate).addClass("attended").text("출석");
					}else{

					}
				}
			}

			$.each($('.attendCnt') , function(index,item){
				var tr = $(this).parent().parent().parent();
				var totalCnt = tr.find("td.attended").length;

				$(this).text(totalCnt);
			});


		}
	});
};



jQuery(document).ready(function(){

	fn_loadAttendances(jQuery("#edcPrgmid").val());

	$.each($('.totalCnt') , function(index,item){
		var tr = $(this).parent().parent().parent();
		var totalCnt = tr.find("td.attend-chagned").length;

		$(this).text(totalCnt);
	});

	var width = parseInt($('#pills-tab').css("width").replace("px", ""), 10) - 300;
	$('#scrollDiv').css("width", width + "px");
	$(".nameRow").height("67.5px");

	//var height = $('.memRow').eq(0).outerHeight();

	//$('.divColumn').outerHeight(height);



});
</script>
<style type="text/css">
#attend-list  { border:1px solid #dee2e6; }
#attend-list th { background-color:#e9ecef; }
.attend-block-head, .attend-block { width:30px; }
.attend-block.not-attended { background-color:#ffddae; }
.attend-block.attended {background-color:#aeeeff; }
.attend-block.attend-chagned { border:1px solid black; }
</style>
</head>
<div class="mb-4">
	<table class="text-center"  id="attend-list" style="border-bottom:0px">
		<colgroup>
			<col style="width:50px;min-width:50px;"/>
			<col style="width:100px;min-width:100px;"/>
			<col style="width:150px;min-width:150px;"/>
		</colgroup>
		<tbody>
			<tr class="info nameRow" style="height:67.5px !important">
				<th style="height:67.5px !important;border-right:1px solid #dee2e6">번호</th>
				<th style="height:67.5px !important;border-right:1px solid #dee2e6">학생명</th>
				<th style="height:67.5px !important;border-right:1px solid #dee2e6">상태/출석</th>

				<td 	<c:if test="${fn:length(studentList)>0}">rowspan="${fn:length(studentList)+2}"</c:if> style="margin: 0px; padding: 0px !important;vertical-align:top">
					<div class="table-responsive p-0" id="scrollDiv" style="overflow:auto" >
						<table width="${fn:length(weekList)*150 }" class="table table-hover table-bordered" style="margin-bottom: 0px">
							<thead>
								<tr class="info weekRow">
<c:forEach items="${logList}" var="item" varStatus="status">
									<th data-passyn="${item.passYn }" style="width:65px;min-width:65px;height:67px !important">${fn:substring(item.edcDate,5,10)} <br>(${item.weekName })</th>
</c:forEach>
								</tr>
							</thead>
							<tbody>
<c:forEach items="${studentList}" var="item" varStatus="status">
<c:set var="edcEdate" value="${item.edcEdate }"/>
								<tr class="divColumn">
	<c:forEach items="${logList}" var="logItem" varStatus="status2">
		<c:set var="edcToday" value="${fn:replace(logItem.edcDate,'-','')}"/>
		<c:set var="attendClass" value=""/>
		<c:choose>
			<c:when test="${logItem.passYn eq 'Y' and edcEdate >= edcToday}">
				<c:set var="attendClass" value="attend-chagned"/>
			</c:when>
		</c:choose>
									<td style="height:63px !important;width:65px;min-width:65px" id="DTCHK_${item.edcRsvnReqid}_${edcToday}" class="attend-block ${attendClass }" data-reqid="${item.edcRsvnReqid }" data-ymd="${edcToday }">

									</td>
	</c:forEach>
								</tr>
</c:forEach>

							</tbody>
						</table>

					</div>
				</td>
			</tr>
<c:forEach items="${studentList}" var="item" varStatus="status">
	<c:set var="edcEdate" value="${item.edcEdate }"/>
			<tr class="memRow" style="border-bottom:1px solid #dee2e6">
				<td>${status.index+1 }</td>
				<td style="height:63px !important;border-right:1px solid #dee2e6;border-left:1px solid #dee2e6">${item.memNm }<br/>${item.memHp }</td>
				<td>${item.edcStat}<div><c:if test="${item.edcStat ne '교육전'}">(<span class="attendCnt">-</span>/<span class="totalCnt">-</span>)</c:if></div></td>
			</tr>
</c:forEach>
			<tr >
				<td colspan="3"></td>
			</tr>
		</tbody>
	</table>
</div>