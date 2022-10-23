<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<style type="text/css">
		.table th {background-color:#f4f6f9;vertical-align:middle;}
		.table td.inbtn {padding:0.4rem;vertical-align:middle}
		.sort {font-size:16px; cursor:pointer}
		.sort.active {color:red}
	</style>
	<script src="${requestScope.resourceContext}/js/jquery.dataTables.min.js"></script>
	<script>
		$(document).ready(function(){
			$.each(["#recordCountPerPage"], function(idx, element) {
				$(element).change(function () {
					$('#pageIndex').val(1);
					fn_get_rsvn_list();
				});
			});

			fn_get_rsvn_list();

			//sms 발송 제어 start
			$("#smsBtn").click(function() {
				if ($(".sms-target-table :checkbox").filter(":checked").length < 1) {
					alert("발송대상 회원을 선택해주세요.");
					return;
				}
				fn_set_sms_list();
				$("#smsModalBtn").trigger("click");
			});
			//sms 발송 제어 end
		});

		function fn_excelDown() {
			$.get({
			    url : './edcRsvnInfoAssignWaitListAjax?exceldown=Y',
			    data : $('#searchVO').serialize(),
				dataType : 'html',
				success: function(data) {
			    	$("#divHidden").html(data);
			    	tableExportExcel("dataTableY", "강좌접수배정대기현황");
				}
		    });
		}

		function fn_get_rsvn_list() {
			$.ajax({
			    url : './edcRsvnInfoAssignWaitListAjax',
			    data : $('#searchVO').serialize(),
				dataType : 'html',
				success: function(data) {
			    	$("#divCard").html(data);
				}
		    });
		}

		function fn_set_page(page) {
			var page = page > 0 ? page : 1;
			$('#pageIndex').val(page);
			fn_get_rsvn_list();
		}

		//수강등록(추후사용예정)
		function fn_program_register(comcd, edcRsvnReqid, edcRsvnNo, edcRsvnCustnm) {
			$("#edcRsvnNo", $("#registForm")).val(edcRsvnNo);
			$("#registForm").submit();
		}
		
		var tmpRsvnNo;
		function fn_set_1000_2001(rsvnNo, custNm) {
			tmpRsvnNo = rsvnNo;
			$("#spanCustNm").text(custNm);
		}
		
		//배정등록
		function fn_update_1000_2001() {
			var data = {};
			data.edcRsvnNo = tmpRsvnNo;

			$.post({
			    url : './edcRsvnInfo1000To2001.json',
			    data : JSON.stringify(data),
			    contentType : "application/json; charset=UTF-8",
				success: function(data) {
					alert(data.result.msg);
					if (data.result.code == "SUCCESS") {
						history.back(-1);
					}
				}
		    });
		}

		//대기취소
		function fn_cancel_waiting(comcd, edcRsvnReqid, edcRsvnNo, name) {

			if (confirm(name + " 회원님의 대기신청내역을 취소하시겠습니까?")) {
				var data = {};
				data.comcd = comcd;
				data.edcRsvnReqid = edcRsvnReqid;
				data.edcRsvnNo = edcRsvnNo;

				$.post({
				    url : './edcRsvnInfoAssignWaitingCancel.json',
				    data : JSON.stringify(data),
				    contentType : "application/json; charset=UTF-8",
					success: function(data) {
						alert(data.result.msg);
						if (data.result.code == "SUCCESS") {
							fn_get_rsvn_list();
						}
					}
			    });
			}
		}
	</script>
</head>
<body>
	<form name="registForm" id="registForm" action="./edcRsvnInfoWaiterRegister" method="get">
		<input type="hidden" name="edcRsvnNo" id="edcRsvnNo" value=""/>
		<input type="hidden" name="source" id="source" value="waitRegi"/>
	</form>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>강좌접수 배정대기 현황</h1>
	          </div>
	          <div class="col-sm-6" style="text-align:right">
	          		<c:set var="backURL" value="${referer }"/>
	          		<c:if test="${empty referer}"><c:set var="backURL" value="javascript:history.go(-1);"/></c:if>
					<a class="btn btn-secondary btn-sm btn-flat" href="${backURL}">이전화면</a>
	          </div>
	        </div>
	      </div>
	</section>

<!-- Main content -->
<section class="content">

      <div class="container-fluid">

		<div class="card card-primary card-outline">
       	  <div class="card-body">
	 	 	<div class="col-12 p-0">
				<%@ include file="/WEB-INF/jsp/webadm/edcrsvn/edcProgramDetailAjax.jsp" %>
	        </div>
	       </div>
 	   	</div>


	<form:form commandName="searchVO" name="searchVO" method="get">
		<form:hidden path="edcPrgmid" />
		<form:hidden path="pageIndex" />
		<form:hidden path="edcRsvnsetSeq" />
		<form:hidden path="searchOrder" />
		<form:hidden path="searchOrderDir" />

 	   <div class="row" style="margin-bottom:5px;">
			 <div class="col-2">
			 	<div class="input-group input-group-sm">
			 	 <select id="recordCountPerPage" name="recordCountPerPage" title="보기갯수" class="form-control">
			 	 	${hisco:makeOptions("CM_COUNT_PER_PAGE", "보기갯수(선택)", searchVO.recordCountPerPage)}
			 	 </select>
			 	 </div>
			 </div>
		 	 <div class="col-2" style="margin-top:10px;float:right;width:45%">
			 	총  <span id="totCount">0</span> 건 조회
			 </div>
			 <div class="col-8" style="margin-top:5px; text-align:right">
				<button type="button" class="btn  btn-secondary btn-sm btn-flat" onclick="fn_excelDown()">EXCEL내려받기</button>
				<button type="button" class="btn  btn-secondary btn-sm btn-flat" id="smsBtn">SMS발송(선택회원)</button>
				<button type="button" class="btn  btn-secondary btn-sm btn-flat" data-toggle="modal" data-target="#modal-sms" id="smsModalBtn" style="display:none"/>
			</div>
       </div>
     </form:form>

       <div class="row">

          <div class="col-12">
            <div class="card" id="divCard">
              <!-- /.card-header -->

              <div class="card-body table-responsive p-0">
	               <table class="table table-bordered table-hover text-nowrap" style="text-align:center">
	               	<colgroup>
						<col style="width: 8%;">
						<col style="width: 15%;">
						<col style="width: 9%">
						<col style="width: *">
						<col style="width: 9%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th><input type="checkbox" name="all" id="chkAll"/></th>
		                   	<th>예약번호 ▼</th>
		                   	<th>접수경로</th>
		                   	<th>대기순위</th>
		                   	<th>회원번호</th>
		                   	<th>회원명 ▼</th>
		                   	<th>ID ▼</th>
							<th>휴대폰번호 ▼</th>
							<th>성별</th>
							<th>생년월일</th>
							<th>신청일시 ▼</th>
							<th>대기자배정</th>
							<th>취소</th>
	                    </tr>
	                  </thead>
	                  <tbody>
						<tr>
							<td colspan="13" align="center">데이타가 없습니다</td>
						</tr>
	                  </tbody>
	                </table>
              </div>
          </div>
        </div>
      </div>
     </div>

     <%@ include file="/WEB-INF/jsp/webadm/edcrsvn/modalSmsLayer.jsp" %>

     <div id="divHidden" style="display:none"></div>
     
     <!-- 배정등록처리 -->
      <div class="modal fade" id="modal-1000-2001" >
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">대기 배정 등록</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 modal-title">
              <h4 class="modal-title">
              	<span id="spanCustNm">홍길동</span> 회원님의 대기신청 강좌인 <br/>
              	'<span id="spanPrgmnm">${detailVO.edcPrgmnm}</span>' 를 배정등록 하시겠습니까?
              </h4>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" class="btn btn-info" data-dismiss="modal" onclick="fn_update_1000_2001()">배정등록</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
</section>
</body>