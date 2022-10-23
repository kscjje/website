<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : myTweduLectList.jsp
  * @Description :마을배움터 학습현황
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
		var edcPrgmid = rowData["edcPrgmid"];
		var edcClcnt = rowData["edcClcnt"];
		var curEdcClcnt = rowData["curEdcClcnt"];
		var edcClState = "";

	    var curStuCnt = rowData["curStuCnt"] + "/" + rowData["edcPncpa"];

		if(curEdcClcnt === 0) {
			edcClState = "교육대기";
		} else if(curEdcClcnt < edcClcnt) {
			edcClState = "교육중("+curEdcClcnt+"/"+edcClcnt+")";
		} else {
			edcClState = "교육완료";
		}

		return fn_dom({ tagName: "tr", childs: [
			{ tagName: "td", attrs:{className : "b_number" , innerHTML: rowData["rnum"] } },
			{ tagName: "td", attrs:{className : "b_name"} , childs: [{ tagName: "a", attrs: { href:"../../edc/program/"+edcPrgmid, className: "a_link", innerHTML: rowData["edcPrgmnm"]} }] },
			{ tagName: "td", attrs:{className : "b_status" , innerHTML: edcClState } },
			{ tagName: "td", attrs:{className : "b_date" , innerHTML: formatDate(rowData["edcSdate"]) +"~" + formatDate(rowData["edcEdate"]) } },
			{ tagName: "td", attrs:{className : "b_count" , innerHTML: "<em class=\"b_txt\">수강생/정원 : </em>&nbsp;" + curStuCnt } },
			{ tagName: "td", attrs:{className : "b_progress mt85"} , childs: [{ tagName: "a", attrs: { href:"./lectAttend?edc_prgm_id="+edcPrgmid, className: "btn_s2_c1", innerHTML: "출석부" } }] },
			{ tagName: "td", attrs:{className : "b_progress mg0"} , childs: [{ tagName: "a", attrs: { href:"./lectArchive?edc_prgm_id="+edcPrgmid, className: "btn_s2_c2", innerHTML: "교육기록" } }] },
			{ tagName: "td", attrs:{className : "b_progress mg0"} , childs: [{ tagName: "a", attrs: { href:"./lectReport?edc_prgm_id="+edcPrgmid, className: "btn_s2_c0", innerHTML: "보고서" } }] }

		]});
	}, function(fallbackMsg){
		return fn_dom({ tagName: "tr", childs: [
			{ tagName: "td", attrs: { colSpan:"7", innerHTML: fallbackMsg } }
		]});
	});

	jQuery("#twedu-open-list").empty().append(trs);
};

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
		url: "./lect.json"+queryString,
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
			<h3>마을배움터 학습관리</h3>
			<p class="sb_txt">(강좌개설 강사전용)</p>
		</div>
		<!--//top -->
		<div class="table t_block">
			<div class="title_box">
				<p class="t_title mobile03"></p>
				<div class="right_box">
				<a href="../../orgintro/4001" class="btn_s2_c1_p30">마을배움터란?</a>
				</div>
			 </div>
			<table>
				<caption>학습관리 표입니다. -번호, 강좌명, 진행상태/회차, 기간, 출석부, 교육기록, 보고서</caption>
				<colgroup>
					<col width="5.5%">
					<col width="*">
					<col width="15%">
					<col width="16%">
					<col width="13%">
					<col width="13%">
					<col width="13%">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>강좌명</th>
						<th>진행상태(회차)</th>
						<th>교육기간</th>
						<th>수강생/정원</th>
						<th>출석부</th>
						<th>교육기록</th>
						<th>보고서</th>
					</tr>
				</thead>
				<tbody id="twedu-open-list"></tbody>
			</table>
		</div>
		<!--// table  -->

		<div class="paginate" id="twedu-list-paging"></div>
		<!-- pagenate -->
	</div>
</body>
