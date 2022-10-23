<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : myTweduJoinList.jsp
  * @Description :마을배움터 참여현황
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
var fn_renderJoinList = function(data) {
	var trs = fn_renderList("", data, function($list, idx, rowData){
		var childs = null;
		if(rowData["edcComplstat"] ===  "2001" &&  rowData["edcCtfhvyn"] ===  "Y") {
			childs = [{ tagName: "a", attrs: { href:"javascript:printCert("+rowData["edcRsvnReqid"]+");", className: "btn_s2_c1 btn_pop certf-preview-btn", innerHTML: "수료증 보기", _certf_data_: rowData } }];
		}

		var edcPrgmid = rowData["edcPrgmid"];

		return fn_dom({ tagName: "tr", childs: [
			{ tagName: "td", attrs:{ className : "b_number" ,innerHTML: rowData["rnum"] } },
			{ tagName: "td", attrs:{ className : "b_date02" , innerHTML: rowData["edcReqDate"] } },
			{ tagName: "td", attrs:{ className : "b_name02"} , childs: [{ tagName: "a", attrs: { href:"../../edc/program/"+edcPrgmid, className: "a_link", innerHTML: rowData["edcPrgmnm"]} }] },
			{ tagName: "td", attrs:{ className : "b_username" , innerHTML: "<em class=\"b_txt\">강사 : </em>" + rowData["instrctrName"] } },
			{ tagName: "td", attrs:{ className : "b_period02" ,innerHTML: rowData["edcSdate"] +"~" + rowData["edcEdate"] } },
			{ tagName: "td", attrs:{ className : "b_status"} ,childs: [{ tagName: "a", attrs: { href:"../myRsvn/myRsvnDetail?edcRsvnReqid="+ rowData["edcRsvnReqid"] , className: "a_link", innerHTML: rowData["edcStat"]} }] },
			{ tagName: "td", attrs:{ className : "b_certificate"} , childs: childs },
			{ tagName: "td", attrs:{ className : "m_td mt85"} ,childs: [{ tagName: "a", attrs: { href:"../myRsvn/myRsvnDetail?edcRsvnReqid="+ rowData["edcRsvnReqid"] , className: "btn_s2_c1", innerHTML: '상세보기'} }] }
		]});
	}, function(fallbackMsg){
		return fn_dom({ tagName: "tr", childs: [
			{ tagName: "td", attrs: { colSpan:"7", innerHTML: fallbackMsg } }
		]});
	});

	jQuery("#twedu-join-list").empty().append(trs);
};

var fn_renderJoinListData = function(data) {
	var first_rnum = 1;
	var tot_cnt = 0;

	if(data.length) {
		first_rnum = data[0].rnum;
		tot_cnt = data[0].totCnt;
	}

	fn_renderJoinList(data);
	fn_renderPaging("#twedu-join-paging", first_rnum, tot_cnt, 10);
};

var fn_loadJoinList = function(edcComplstat, pageIndex){
	var queryString = "?edcComplstat="+edcComplstat;
	if(pageIndex) {
		queryString = "&pageIndex="+pageIndex;
	}

	jQuery.ajax({
		url: "./join.json"+queryString,
		type: "GET",
		success: function(json){
			if(json.result.code == "SUCCESS") {
				fn_renderJoinListData(json.data);
				console.log(json.data);
			}
		},
		error: function(json) {
			alert("서버에러.");
		}
	});
};
jQuery(document).ready(function(e){
	jQuery("#twedu-join-paging").on("click", "a", function(){
		fn_loadJoinList(jQuery("#edcComplstat").val(), this["page-index"]);
	});

	jQuery("#edcComplstat").on("change", function(){
		fn_loadJoinList(jQuery("#edcComplstat").val());
	}).trigger("change");


});

function printCert(idx){
	$('#edcRsvnReqid').val(idx);
	$('#edcRsvnMstVO').attr("action" , "../myRsvn/edcCompPopup");
	window.open('','rsvnPop','width=818, height=900, resizable=yes, scrollbars=yes, status=no, titlebar=0, toolbar=0, left=300, top=100');

	$('#edcRsvnMstVO').submit();

}
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
			<h3>마을배움터 참여현황</h3>
			<p class="sb_txt">(마을배움터 수강생용)</p>
		</div>
		<!--//top -->
		<div class="table t_block">
			<div class="title_box">
				<p class="t_title mobile03">* PC웹에서 수료증 확인</p>
				<div class="right_box">
					<select id="edcComplstat" name="edcComplstat">
						<option value="">교육상태 (전체)</option>
						<option value="0000">교육대기</option>
					<c:forEach items="${complType}" var="item" varStatus="status">
						<option value="${item.cd }">${item.cdNm }</option>
					</c:forEach>
					</select>
				</div>
			</div>


		<!--// title_box  -->
			<table>
				<caption>참여현황 표입니다.- 번호, 신청일시, 강좌명, 강사, 교육기간, 교육상태, 수료증</caption>
				<colgroup>
					<col width="6%">
					<col width="15%">
					<col width="*">
					<col width="13%">
					<col width="18%">
					<col width="13%">
					<col width="10%">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>신청일시</th>
						<th>강좌명</th>
						<th>강사</th>
						<th>교육기간</th>
						<th>교육상태</th>
						<th>수료증</th>
					</tr>
				</thead>
				<tbody id="twedu-join-list"></tbody>
			</table>
		</div>
		<!--// table  -->
		<div class="paginate" id="twedu-join-paging"></div>
		<!-- pagenate -->
	</div>
	<!-- 팝업 -->
	<form id="edcRsvnMstVO" name="edcRsvnMstVO"  target="rsvnPop" method="post">
		<input type="hidden" name="edcRsvnReqid" id="edcRsvnReqid"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
</body>
