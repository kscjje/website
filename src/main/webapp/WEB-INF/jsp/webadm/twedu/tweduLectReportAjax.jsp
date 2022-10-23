<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : tweduOpenDetail.jsp
	 * @Description : 마울배움터 교육현황 보고서 JSP
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
var fn_renderReport = function(data) {
	/* 운영개요 및 헤더/푸터 */
	jQuery(".data-bind-header .data-binder").each(function(idx, elem){
		var key = elem.getAttribute("data-id");
		var val = fn_getData(data.header, key);

		elem.innerHTML = val;
	});

	/* 총 수업시간 */
	var totTime = data.header["edcRtime"]*data.header["edcClcnt"];
	jQuery("#tot_edu_time").text(totTime);

	/* 운영내용 - 출석부 */
	var trs_1 = fn_renderList(".data-bind-attendList", data, function($list, idx, rowData){
		var tr = document.createElement("tr");

		$list.find(".data-binder").each(function(idx, elem){
			var key = elem.getAttribute("data-id");
			var val = fn_getData(rowData, key);
			var suf = elem.getAttribute("data-suffix") || "";

			var td = document.createElement("td");
			td.innerHTML = val+suf;

			tr.appendChild(td);
		});

		return tr;
	}, function(fallbackMsg) {
		var tr = document.createElement("tr");
			var td = document.createElement("td");
			td.colSpan = "4";
			td.innerHTML = fallbackMsg;
		tr.appendChild(td);
		return tr;
	});

	jQuery(".data-bind-attendList tbody").empty().html(trs_1);

	/* 운영내용 - 오늘수업기록 */
	var trs_2 = fn_renderList(".data-bind-logList", data, function($list, idx, rowData){
		var tr = document.createElement("tr");
			var td1 = document.createElement("td");
				var head = document.createElement("div");
				head.className = "d-flex w-100 justify-content-between";
					var title = document.createElement("h5");
					title.className = "mb-1";
					title.innerHTML = rowData["edcTitle"];
					var dt    = document.createElement("span");
					dt.innerHTML = rowData["edcDate"];;

				head.appendChild(title);
				head.appendChild(dt);

				var hr = document.createElement("hr");
				var body = document.createElement("p");
				body.className = "mb-1";
				body.innerHTML = rowData["edcCnts"];

			td1.appendChild(head);
			td1.appendChild(hr);
			td1.appendChild(body);

			var td2 = document.createElement("td");
			td2.className = "twedu-log-preview";
				var img = document.createElement("img");
				img.src = fn_getImgPath(rowData["lectImgPath"], rowData["lectImgFilenm"], rowData["lectImgOrigin"]);
			td2.appendChild(img);

		tr.appendChild(td1);
		tr.appendChild(td2);
		return tr;
	}, function(fallbackMsg) {
		var tr = document.createElement("tr");
			var td = document.createElement("td");
			td.colSpan = "2";
			td.className = "text-center";
			td.innerHTML = fallbackMsg;
		tr.appendChild(td);
		return tr;
	});

	jQuery(".data-bind-logList tbody").empty().html(trs_2);

	/* 운영내용 - 학습일정 */
	var trs_3 = fn_renderList(".data-bind-planList", data, function($list, idx, rowData){
		var pdiv = document.createElement("div");
		pdiv.className = "list-group-item";
			var div = document.createElement("div");
				var h5 = document.createElement("h5");
				h5.className = "mb-1";
				h5.innerHTML = rowData["edcDate"];
					var small = document.createElement("small");
					small.className = "px-lg-5";
					small.innerHTML = rowData["memNm"];
				h5.appendChild(small);
			div.appendChild(h5);
			var hr = document.createElement("hr");
			var p =  document.createElement("p");
			p.className = "mb-1";
			p.innerHTML = rowData["edcTitle"];

		pdiv.appendChild(div);
		pdiv.appendChild(hr);
		pdiv.appendChild(p);

		return pdiv;
	}, function(fallbackMsg) {
		var div = document.createElement("div");
		div.className = "text-center";
		div.innerHTML = fallbackMsg;
		return div;
	});

	jQuery(".data-bind-planList").empty().html(trs_3);
};

var fn_printReport = function(){
	/* DO SOMETHING */
	window.open("./report/print");
};

jQuery(document).ready(function(){
	//jQuery("#print-report-btn").on("click", fn_printReport);
});
</script>
<style>
	#report-print-box{color:black !important}
</style>
</head>

<div class="px-lg-5 mb-3 text-right no-print">
	<button type="button" id="print-report-btn" class="btn btn-info bnt-sm" onclick="print();"><i class="fa fa-print"> 인쇄</i></button>
</div>
<div id="report-print-box">
<div class="">
	<h3 class="text-center mb-3" >[<span class="data-binder" data-id="edcPrgmnm"><c:out value="${data.edcPrgmnm}"/></span>]</h3>
	<div class="mb-4">
		<table class="table table-bordered mb-3 text-center">
			<colgroup>
				<col style="width:10%"/><col style="width:20%"/>
				<col style="width:14%"/><col style="width:20%"/>
				<col style="width:14%"/><col style="width:*"/>
			</colgroup>
			<tr>
				<th class="colored-head">보고일</th>
				<td style="text-align:left">
					<c:set var="now" value="<%=new java.util.Date()%>" />
					<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />
				</td>
				<th class="colored-head">보고시점 실행 회차</th>
				<td style="text-align:left">${passCnt}회차</td>
				<th class="colored-head">보고시점 실행시간</th>
				<td style="text-align:left">
					(<span class="data-binder" data-id="edcRtime">${eduMin}</span>분x<span class="data-binder" data-id="edcClcnt">${passCnt}</span>회차) = <span id="tot_edu_time"><fmt:formatNumber value="${passCnt*eduMin }"/></span>분
				</td>
			</tr>
		</table>
	</div>

	<h6 class="mb-1">1. 운영개요</h6>
	<div class="mb-4">
		<table class="table table-bordered mb-3 text-center">
			<colgroup>
				<col style="width:10%"/><col style="width:20%"/>
				<col style="width:14%"/><col style="width:20%"/>
				<col style="width:14%"/><col style="width:*"/>
			</colgroup>
			<tr>
				<th class="colored-head">선생님</th>
				<td style="text-align:left"><span class="data-binder" data-id="instrctrName">${data.instrctrName}</span></td>
				<th class="colored-head">시작일</th>
				<td style="text-align:left"><span class="data-binder" data-id="edcSdate">${data.edcSdate}</span></td>
				<th class="colored-head">종료일</th>
				<td style="text-align:left"><span class="data-binder" data-id="edcEdate">${data.edcEdate}</span></td>
			</tr>
			<tr>
				<th class="colored-head">교육 회차</th>
				<td style="text-align:left"><span class="data-binder" data-id="edcClcnt">${data.edcClcnt}</span>회</td>
				<th class="colored-head">교육참여자</th>
				<td style="text-align:left"><span class="data-binder" data-id="stdCnt">${fn:length(studentList) }</span>명</td>
				<th class="colored-head">교육대상</th>
				<td style="text-align:left"><span class="data-binder" data-id="edcTargetAge">${data.targetName}</span> </td>
			</tr>
		</table>
	</div>
</div>
<h6 class="mb-1">2. 운영내용 : 출석부</h6>
<div class="mb-1">
	<table class="table table-bordered mb-4 text-center data-bind-attendList" data-list-id="attendList">
		<thead class="thead-light">
			<tr>
				<th class="data-binder" data-id="memNm">No.</th>
				<th class="data-binder" data-id="memNm">성명</th>
				<th class="data-binder" data-id="passCnt">출석일</th>
				<th class="data-binder" data-id="nopassCnt">결석일</th>
				<th class="data-binder" data-id="passRate">출석률</th>
				<th class="data-binder" data-id="passRate">교육상태</th>
			</tr>
		</thead>
		<tbody>

<c:forEach items="${studentList}" var="item" varStatus="status">
							<tr>
								<td>${status.index+1 }</td>
								<td>${item.memNm }</td>
								<td>${item.attendCnt }</td>
								<td>${passCnt-item.attendCnt }</td>
								<td><fmt:formatNumber type="percent" value="${item.attendCnt/passCnt}"  pattern="0%"/></td>
								<td>${item.edcStat }</td>
							</tr>
</c:forEach>

		</tbody>
	</table>
	<h6 class="mb-1">3. 운영내용 : 수업기록</h6>
	<table class="table table-bordered mb-4 data-bind-logList" data-list-id="logList">
			<colgroup>
						<col width="6%">
						<col width="11.5%">
						<col width="9%">
						<col width="13%">
						<col width="*">
						<col width="14%">
					</colgroup>
					<thead>
						<tr>
							<th class="colored-head">회차</th>
							<th class="colored-head">날짜</th>
							<th class="colored-head">출석/정원</th>
							<th class="colored-head">주제</th>
							<th class="colored-head">학습내용 일정/기록</th>
							<th class="colored-head">사진보기</th>
						</tr>
					</thead>
					<tbody class="twedu-log-list">

<c:forEach items="${logList}" var="item" varStatus="status">
					<tr>
						<td style="text-align:center">${status.index+1 }</td>
						<td style="text-align:center">
							${item.edcDate}
						</td>
						<td style="text-align:center">${item.passCnt}/${item.totalMemCnt}</td>
						<td style="text-align:left">
							<c:out value="${item.edcTitle }"/>
						</td>
						<td  style="text-align:left">
							${fn:replace(item.edcCnts,crlf , '<br/>')}
						</td>
						<td style="text-align:center">
							<div class="file_list">
								<span class="file-preview-link" data-imgpath="<tags:UploadFileUrl filePath="${item.lectImgPath}" fileName="${item.lectImgFilenm }" originName="${item.lectImgOrigin }"/>">
									<c:if test="${!empty item.lectImgPath}">
										<img style="max-width: 100%;" src="<tags:UploadFileUrl filePath="${item.lectImgPath}" fileName="${item.lectImgFilenm }" originName="${item.lectImgOrigin }"/>" />
										<em style="border-bottom:0px">${item.lectImgOrigin}</em>
									</c:if>
								</span>
							</div>
						</td>
					</tr>
</c:forEach>

		</tbody>
	</table>

</div>
<hr class="mb-5"/>
<h5 class="text-center mb-5">위와같이 [마을배움터] 운영결과를 보고합니다.</h5>
<h6 class="text-right px-lg-5 data-bind-header">보고일: <span class="data-binder" data-id="reportDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd" /></span></h6>
<h6 class="text-right px-lg-5">보고자: <span class="reportUserNm">${data.memNm}</span></h6>
</div>