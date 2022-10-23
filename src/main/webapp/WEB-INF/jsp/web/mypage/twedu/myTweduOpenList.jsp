<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : myTweduOpenList.jsp
  * @Description :마을배움터 개설현황
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.12.14   이기태          최초 생성
  *
  *  @author 이기태
  *  @since 2021.12.14
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
<script type="text/javascript" src="<c:url value='/web/resources/js/twedu.js'/>"></script>
<script type="text/javascript">
var fn_renderOpenList = function(data) {
	var formatDate = function(y_m_d) { return y_m_d.replace(/\d{2}(\d{2})-(\d{2})-(\d{2})/,"$1.$2.$3"); };

	var trs = fn_renderList("", data, function($list, idx, rowData){
		var isTemp = rowData["edcPrg"] === "0000";

		return fn_dom({ tagName: "tr", childs: [
			{ tagName: "td" , attrs:{className : "b_number",  innerHTML: rowData["rnum"] } },
			{ tagName: "td" , attrs:{className : "b_name"} , childs: [{ tagName: "a", attrs: { href: (rowData["edcPrg"]=='3001'?"../../edc/program/"+rowData["edcPrgmid"]  : "javascript:;") ,
				className: (rowData["edcPrg"]=='3001'?"a_link" : "")
				, style: (rowData["edcPrg"]=='3001'?"" : "cursor:default")
				, innerHTML:rowData["edcPrgmnm"]} }]},
			{ tagName: "td" , attrs:{className : "b_date" ,  innerHTML: formatDate(rowData["edcSdate"]) +"~" + formatDate(rowData["edcEdate"]) } },
			{ tagName: "td" , attrs:{className : "b_status" ,  innerHTML: rowData["edcPrgcdnm"] } },
			{ tagName: "td" , attrs:{className : "b_progress mt54"} , childs: [{ tagName: "a", attrs: { href:"./openDetail?edc_prgm_id="+rowData["edcPrgmid"], target:"_self",
										className: "btn_s2_c2", innerHTML:"개설신청 상세/수정" } }] },
			{ tagName: "td" , attrs:{className : "b_progress bg_w mg0"} , childs: [{ tagName: "a", attrs: { href:"javascript:fn_copySet("+rowData["edcPrgmid"]+" , '"+rowData['edcDaygbnNm']+"')",
										className: "btn_s2_c1", innerHTML:"복사 후 불러오기" } }] },

		]});
	}, function(fallbackMsg){
		return fn_dom({ tagName: "tr", childs: [
			{ tagName: "td", attrs: { colSpan:"6", innerHTML: fallbackMsg } }
		]});
	});

	jQuery("#twedu-open-list").empty().append(trs);
};

var fn_copySet = function(prgId , week) {
	document.copyForm.copyEdcId.value = prgId;

	$.each($('.date_line').find("a") , function(index,item){
		if(week.indexOf($(this).text()) >= 0){
			$(this).addClass("on");
		}else{
			$(this).removeClass("on");
		}
	});

	$('#modal03').popup('show');
}

var fn_copySetOk = function(){
	var f = document.copyForm;
	var selectedWeek = "";
	$.each($('.date_line').find("a.on") , function(index,item){
		selectedWeek += $(this).data("weekno");
	});

	f.copyEdcWeek.value = selectedWeek;

	if(f.edcSdate.value == ""){
		alert("교육 시작일을 입력해 주세요.");
		f.edcSdate.focus();
	}else if(f.edcEdate.value == ""){
		alert("교육 종료일을 입력해 주세요.");
		f.edcEdate.focus();
	}else if(f.copyEdcWeek.value == ""){
		alert("교육 요일을 선택해 주세요.");
	}else{
		f.submit();
	}
}

var fn_renderOpenListData = function(data) {
	var first_rnum = 1;
	var tot_cnt = 0;

	if(data.length) {
		first_rnum = data[0].rnum;
		tot_cnt = data[0].totCnt;
	}

	fn_renderOpenList(data);
	fn_renderPaging("#twedu-list-paging", first_rnum, tot_cnt, 10);
};

var fn_loadOpenList = function(pageIndex){
	var queryString = "";
	if(pageIndex) {
		queryString = "?pageIndex="+pageIndex;
	}

	jQuery.ajax({
		url: "./open.json"+queryString,
		type: "GET",
		success: function(json){
			if(json.result.code == "SUCCESS") {
				fn_renderOpenListData(json.data);
			}
		},
		error: function(json) {
			alert("서버에러.");
		}
	});
};
jQuery(document).ready(function(){
	fn_loadOpenList();

	jQuery("#twedu-list-paging").on("click", "a", function(){
		fn_loadOpenList(this["page-index"]);
	});

	$(".datepicker").datepicker({
		showOn: "button",
		dateFormat: 'yy.mm.dd',
		prevText: '이전 달',
		nextText: '다음 달',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
	});
});
</script>
</head>
<body>
	<div class="sub_visual mypage">
		<div class="inner">
			<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
			<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
		</div>
	</div>
	<div id="content" class="sub_content">
		<!-- tab_link -->
		<%@ include file="/WEB-INF/templates/web/base/tab_menu.jsp"%>
		<!--// tab_link -->
		<div class="sub_top type01 flex_column">
			<h3>마을배움터 개설현황</h3>
			<p class="sb_txt">(강좌개설 강사전용)</p>
		</div>
		<!-- // sub_top -->
		<div class="table t_block">
			<div class="title_box">
				<p class="t_title"></p>
				<div class="right_box">
				<a href="../../orgintro/4001" class="btn_s2_c1_p30">마을배움터란?</a>
				</div>
			 </div>
			<table>
				<caption>연락처 표입니다. 대학명, 주소, 연락처, 수강신청 바로가기</caption>
				<colgroup>
					<!-- 21.12.31 수정 -->
					<col style="width:6%">
					<col style="width:16.5%">
					<col style="width:16.5%">
					<col style="width:15%">
					<col style="width:23%">
					<col style="width:23%">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>강좌명</th>
						<th>교육 기간</th>
						<th>승인 상태</th>
						<th>상태 바로가기</th>
						<th>복사 후 개설신청</th>
					</tr>
				</thead>
				<tbody id="twedu-open-list"></tbody>
			</table>
		</div>
		<!-- // table -->
		<div class="paginate" id="twedu-list-paging"></div>
		<!-- pagenate -->
	</div>

	<div class="modal" id="modal03">
		<div class="modal-dialog" style="max-width:800px">
			<div class="modal-content">
				<div class="modal-title">
					<h4>개설신청 기간/요일 작성</h4>
					<a href="javascript:pop_close();" class="btn_close"><em>팝업 닫기</em></a>
				</div>
				<div class="modal-body">
						<p class="t_title">개설할 기간/요일 기입하면, 학습계획 날짜가 자동계산됩니다. </p>
<form id="copyForm" name="copyForm" method="post" action="./openRegi">
<input type="hidden" name="copyEdcId" />
<input type="hidden" name="copyEdcWeek" />
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<div >
						<ul class="date_table">
							<li>
								<p>개설할 기간</p>
								<div>
									<div class="w_box date">
										<div class="inputbox date">
											<label for="edcSdate" class="hidden"></label>
											<input type="text" title="교육기간 입력" id="edcSdate" name="edcSdate" maxlength="10" readonly class="datepicker">
										</div>
										<span>~</span>
										<div class="inputbox date">
											<label for="edcEdate" class="hidden"></label>
											<input type="text" title="교육기간 입력" id="edcEdate" name="edcEdate" maxlength="10" readonly class="datepicker">
										</div>
								</div>
								</div>
							</li>
							<li>
								<p>개설할 요일</p>
								<div>
									<div class="date_line"><a href="javascript:;" data-weekno="1" >월</a><a href="javascript:;" data-weekno="2">화</a><a href="javascript:;" data-weekno="3">수</a><a href="javascript:;" data-weekno="4">목</a><a href="javascript:;" data-weekno="5">금</a><a href="javascript:;" data-weekno="6">토</a><a href="javascript:;" data-weekno="7">일</a></div>
								</div>
							</li>
						</ul>
				</div>
</form>
					<ul class="btn_area">
						<li><a href="javascript:pop_close()" class="btn_s1_c0 modal1_close">취소</a></li>
						<li><a href="javascript:fn_copySetOk()" class="btn_s1_c1 modal1_close">개설적용</a></li>
					</ul>
					<!-- //btn_area -->
				</div>

			</div>
		</div>
	</div>
	<!-- //팝업 -->
</body>
