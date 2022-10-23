<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<script type="text/javascript">
var fn_renderOpenHistory = function(data) {
	var formatDate = function(y_m_d) { return y_m_d.replace(/\d{2}(\d{2})-(\d{2})-(\d{2})/,"$1.$2.$3"); };
	
	var trs = fn_renderList("", data, function($list, idx, rowData){
		return fn_dom({ tagName: "tr", childs: [
			{ tagName: "td", attrs:{ innerHTML: rowData["rnum"] } },
			{ tagName: "td", childs: [{ tagName: "a", attrs: { href:"./openDetail?edc_prgm_id="+rowData["edcPrgmid"], target:"_blank", className:"a_link", innerHTML: rowData["edcPrgmnm"] } }] },
			{ tagName: "td", attrs:{ innerHTML: formatDate(rowData["edcSdate"]) +"~" + formatDate(rowData["edcEdate"]) } },
			{ tagName: "td", childs: [{ tagName: "a", attrs: { href:"#", className:"a_link load-detail-item", innerHTML: "불러오기", "data-id": rowData["edcPrgmid"] } }] },
		]}); 
	}, function(fallbackMsg){
		return fn_dom({ tagName: "tr", childs: [
			{ tagName: "td", attrs: { colSpan:"4", innerHTML: fallbackMsg } }	
		]});
	});
	
	jQuery("#twedu-open-hist").empty().append(trs);
};

var fn_renderOpenHistData = function(data) {
	var first_rnum = 1;
	var tot_cnt = 0;
	
	if(data.length) {
		first_rnum = data[0].rnum;
		tot_cnt = data[0].totCnt;		
	}
	
	fn_renderOpenHistory(data);
	fn_renderPaging("#twedu-hist-paging", first_rnum, tot_cnt, 10);
};

var fn_loadOpenHistory = function(pageIndex){
	var queryString = "";
	if(pageIndex) {
		queryString = "?pageIndex="+pageIndex;
	}
	
	jQuery.ajax({
		url: "./open.json"+queryString,
		type: "GET",
		success: function(json){
			console.log(json);
			if(json.result.code == "SUCCESS") {
				fn_renderOpenHistData(json.data);
			}	
		},
		error: function(json) {
			alert("서버에러.");
		}
	});
};	

var fn_loadOpenDetail = function(edc_prgm_id) {
	jQuery.ajax({
		url: "./open/"+edc_prgm_id+".json",
		type: "GET",
		success: function(json){
			if(json.result.code == "SUCCESS") {
				fn_setOpenDtail && fn_setOpenDtail(json.data);
				pop_close();
			}	
		},
		error: function(json) {
			alert("서버에러.");
		}
	});	
};

var pop_close = function() {
	jQuery('.modal').popup('hide');
	jQuery('body').removeClass('on').removeClass('hid');
};

jQuery.fn.popOpen = function() {
	jQuery(this).click(function(e) {
		var s = jQuery(this).attr("href");
		jQuery(s).popup('show');
		jQuery('body').addClass('on');
		jQuery(this).addClass('chk1');
	});
	return this;
};

jQuery(document).ready(function(){
	jQuery("#twedu-hist-paging").on("click", "a", function(){
		fn_loadOpenHistory(this["page-index"]);
	});
	
	jQuery("#twedu-open-hist").on("click", ".load-detail-item", function(){
		var edc_prgm_id = this["data-id"];
		fn_loadOpenDetail(edc_prgm_id);
	});
});
</script>
</head>
<!-- 팝업 -->
<div class="modal" id="modal3">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-title">
				<h4>이전 개설신청 불러오기</h4>
				<a href="javascript:pop_close();" class="btn_close"><em>팝업 닫기</em></a>
			</div>
			<div class="modal-body">
				<div class="table scroll500">
					<div class="title_box">
					 <p class="t_title"><span id="pop_user_nm">${userNm}</span>님! 마을배움터 학습관리 현황입니다.</p>
					</div>
					<table>
						<caption>학습관리 표입니다. -번호, 강좌명, 진행상태/회차, 기간, 출석부, 교육기록, 교육기록</caption>
						<colgroup>
							<col width="10%">
							<col width="25%">
							<col width="40%">
							<col width="25%">
					
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>강좌명</th>
								<th>기간</th>
								<th>불러오기</th>
							</tr>
						</thead>
						<tbody id="twedu-open-hist"></tbody>
					</table>
					<div class="paginate" id="twedu-hist-paging"></div>
				</div>
				<!-- //btn_area -->
			</div>
		</div>
	</div>
</div>