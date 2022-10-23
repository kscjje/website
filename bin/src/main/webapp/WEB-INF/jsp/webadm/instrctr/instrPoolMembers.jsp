<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
var fn_getYMD = function(timeInMs) {
	var date = new Date(timeInMs);

	var yyyy = date.getFullYear();
	var mm = date.getMonth()+1;
	mm = mm < 10 ? mm +"0" : mm + "";
	var dd = date.getDate();
	dd = dd < 10 ? dd +"0" : dd + "";

	return yyyy+mm+dd;
}

var fn_renderMembers = function(data) {
	var $table = jQuery("#member-table tbody");

	$table.empty();

	var trs = [];
	for(var i=0; i<data.length; i++) {
		var member = data[i];

		var tr = document.createElement("tr");
		tr["raw-data"] = member;
			var td1 = document.createElement("td");
			td1.innerText = member.rnum;
			var td2 = document.createElement("td");
			td2.innerText = member.memNo;
			var td3 = document.createElement("td");
			td3.innerText = member.memNm;
			var td4 = document.createElement("td");
			td4.innerText = member.gender;
			var td5 = document.createElement("td");
			td5.innerText = member.birthDate;
			var td6 = document.createElement("td");
			td6.innerText = fn_getYMD(member.regdate);

		tr.appendChild(td1);
		tr.appendChild(td2);
		tr.appendChild(td3);
		tr.appendChild(td4);
		tr.appendChild(td5);
		tr.appendChild(td6);

		trs.push(tr);
	}
	$table.append(trs);
};

var fn_renderPaging = function(first_rnum, tot_cnt) {
	var $paging = jQuery("#member-paging");

	var MAX_ROW_PER_PAGE = 10;

	var tot_page = Math.ceil(tot_cnt/MAX_ROW_PER_PAGE);
	var cur_page =  Math.ceil(first_rnum/MAX_ROW_PER_PAGE);

	$paging.empty();
	var lis = [];
	for(var i=1; i<tot_page+1; i++) {
		var li = document.createElement("li");
		if( i == cur_page) li.className = "paginate_button current";
		else			   li.className = "paginate_button";
		li["page-index"] = i;
			var a = document.createElement("a");
			a.innerText = i;
			a.href = "javascript:void(0)";
		li.appendChild(a);
		lis.push(li);
	}
	$paging.append(lis);
}

var fn_renderData = function(data) {
	if(!data.length) return;

	var first_rnum = data[0].rnum;
	var tot_cnt = data[0].tbAllCount;

	fn_renderMembers(data);
	fn_renderPaging(first_rnum, tot_cnt);
}

var fn_searchMember = function(pageIndex) {
	var queryString = "";
	if(pageIndex) {
		queryString = "?pageIndex="+pageIndex;
	}

	jQuery.ajax({
		url : "./members"+JSON_REQ_SUFFIX+queryString,
		type: "GET",
		contentType:"application/json",
		success: function(json) {
			if (json.result.code == "ERROR") {
				alert(json.result.msg);
			} else {
				fn_renderData(json.data);
			}
		}
	});
};
</script>
<!-- 모달 박스 -->
<div class="modal fade" id="modal-default" >
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">회원 검색</h4>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
			</div>
			<div class="modal-body">
				<table class="table table-hover text-nowrap" id="member-table">
               	<colgroup>
					<col style="width: 9%;">
					<col style="width: 10;">
					<col style="width: *">
					<col style="width: 9%;">
					<col style="width: 15%;">
					<col style="width: 10%;">
				</colgroup>
                  <thead>
                    <tr>
                      	<th>번호</th><!-- 번호 -->
                      	<th>회원번호</th>
                      	<th>이름</th>
						<th>성별</th>
						<th>생년월일</th>
						<th>등록일자</th>
                    </tr>
                  </thead>
                  <tbody>
	                   <tr>
	                   		<td>1</td>
	                      	<td>51232</td>
							<td>아무개</td>
							<td>남</td>
							<td>2021.11.12</td>
							<td>2001.01.01</td>
	                    </tr>
                  </tbody>
                </table>
                <div class="pagination_2">
	                <ul class="pagination pagination-sm no-margin pull-right" id="member-paging">

	                </ul>
                </div>
			</div>
			<div class="modal-footer justify-content-between">
				<button type="button" class="btn btn-default " data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary " >Search</button>
			</div>
		</div>
	</div>
</div>