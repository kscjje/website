<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : tweduOpenStudent.jsp
	 * @Description : 마울배움터 교육현황 학생현황 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.11.05     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2021.12.06
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<head>
<script type="text/javascript">
function fn_excelDown() {
	var form = document.createElement("form");

	form.setAttribute('id', 'excelDownloadForm');
	form.setAttribute('name', 'excelDownloadForm');
	form.setAttribute('method', 'get');
	form.setAttribute('action', "./"+jQuery("#edcPrgmid").val()+"/edcstudentListExcel");

	$('body').append(form);
	$('#excelDownloadForm').submit();
	$('#excelDownloadForm').remove();
}

var fn_updateStudStatus = function(edc_prgmid, edcRsvnReqid, complStat) {
	jQuery.ajax({
		url: "./"+edc_prgmid+"/student/"+edcRsvnReqid+"/status.json",
		type: "POST",
		contentType:"application/json",
		data: JSON.stringify({
			edcRsvnReqid: edcRsvnReqid,
			edcComplstat: complStat
		}),
		success: function(json) {
			alert(json.result.msg);
		}
	});
};

var fn_batchUpdateStudStatus = function(edc_prgmid, edcRsvnComptidArr, complStat) {
	jQuery.ajax({
		url: "./"+edc_prgmid+"/student/status.json",
		type: "POST",
		contentType:"application/json",
		data: JSON.stringify({
			idArr: edcRsvnComptidArr.join(","),
			edcComplstat: complStat
		}),
		success: function(json) {
			alert(json.result.msg);
			fn_loadStudents(edc_prgmid);
		}
	});
};

var fn_getChecked = function() {
	var checkArr = [];
	jQuery(".std-check:checked").each(function(idx, elem){
		checkArr.push(elem.value);
	});

	return checkArr;
};

jQuery(document).ready(function(){

	jQuery("#student-list").on("change", ".edc-compl-stat", function(){
		var edc_prgmid = jQuery("#edcPrgmid").val();

		var val = $(this).find("option:checked").text();

		if(confirm("수업상태를 [" +val+ "] 로 변경하시겠습니까?")){

			var $this = jQuery(this);
			var edcRsvnComptid = $this.parent().parent().find(".std-check").attr("value");
			var edcComplstat  = $this.val();

			fn_updateStudStatus(edc_prgmid, edcRsvnComptid, edcComplstat);
		}


	});

	jQuery("#std-check-all").on("click", function(){
		jQuery(".std-check").prop("checked", this.checked);
	});

	jQuery(".stud-batch-btn").on("click", function(){
		var chkArr = fn_getChecked();
		if(chkArr.length) {

			var val = $(this).text();

			if(confirm("수업상태를 [" +val+ "] 로 변경하시겠습니까?")){
				var edc_prgmid = jQuery("#edcPrgmid").val();
				var edcComplstat  = jQuery(this).attr("stat-data");

				fn_batchUpdateStudStatus(edc_prgmid, chkArr, edcComplstat);
			}
		} else {
			alert("선택된 학생이 없습니다.");
		}
	});


	$('#statusChangeBtn').find(".btn-primary").text($('#edcStatus').val() );
	$('select[name=newStatus]').find("option[value='']").text($('#edcStatus').val() );


	if($('#edcStatus').val() == "교육대기"){
		$('#statusChangeBtn').hide();
	}

});
</script>
</head>
<div class="justify-content-start" id="statusChangeBtn">
	<table class="table table-bordered">
		<tr>
			<td class="text-center" style="width:120px;vertical-align:middle;">선택일괄변경</td>
			<td style="border-right: 0px;">
				<button type="button" class="btn btn-primary stud-batch-btn" stat-data="">수강중</button>
				<button type="button" class="btn btn-danger stud-batch-btn" stat-data="1001">중퇴</button>
				<button type="button" class="btn btn-success stud-batch-btn" stat-data="2001">수료</button>
				
			</td>
		</tr>
	</table>
</div>
<div class="">
	<table class="table" style="margin: 0px;">
		<tr>
			<td class="text-right" style="border-left: 0px; border-top: 0px;">
				<button type="button" onclick="fn_excelDown()" class="btn  btn-secondary btn-sm btn-flat">엑셀다운</button>
			</td>
		</tr>
	</table>
</div>
<table class="table table-bordered table-hover text-center">
	<colgroup>
		<col style="width:5%"/>
		<col style="width:15%"/>
		<col style="width:15%"/>
		<col style="width:13%"/>
		<col style="width:15%"/>
		<col style="width:7%"/>
		<col style="width:15%"/>
		<col style="width:10%"/>
	</colgroup>
	<thead class="thead-light">
		<tr>
			<th><input type="checkbox" id="std-check-all" value="Y"/></th>
			<th>신청일</th>
			<th>ID</th>
			<th>학생명</th>
			<th>생년월일</th>
			<th>성별</th>
			<th>휴대폰번호</th>
			<th>수업상태</th>
		</tr>
	</thead>
	<tbody id="student-list">
<c:forEach items="${list}" var="item" varStatus="status">
			<tr >
				<td><input type="checkbox" class="std-check" value="${item.edcRsvnReqid }"/></td>
				<td><fmt:formatDate value="${item.regdate }" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>${item.memId }</td>
				<td>${item.memNm }</td>
				<td>${item.birthDate }</td>
				<td>${item.gender }</td>
				<td>${item.memHp }</td>
				<td>
					<select name="newStatus" class="edc-compl-stat">
						<option value="">교육대기</option>
						<option value="1001" <c:if test="${item.edcComplstat eq '1001'}">selected</c:if>>중퇴</option>
						<option value="2001" <c:if test="${item.edcComplstat eq '2001'}">selected</c:if>>수료</option>
					</select>
				</td>
			</tr>
</c:forEach>

	</tbody>
</table>
<div class="float-left px-lg-5">
	학생수: <span id="tot-stud-cnt">${fn:length(list) }</span>명
</div>

<div style="display:none;" id="edcComplstat-holder">
<select class="edc-compl-stat" name="edcComplstat" class="form-control">
	<option value="">교육대기</option>
<c:forEach items="${complStatType}" var="item" varStatus="status">
	<option value="${item.cd}" >${item.cdNm}</option>
</c:forEach>
</select>
</div>
