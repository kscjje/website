<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<style type="text/css">
		th {background-color:#f4f6f9;}
		.sort {font-size:16px; cursor:pointer}
		.sort.active {color:red}

		#tip {
			display: none;
			position: absolute;
			padding: 5px;
			width: 300px;
			background: #05184d;
			color: #fff;
			border-radius: 5px;
			-webkit-border-radius: 5px;
			-moz-border-radius: 5px;
		}
	</style>
	<script src="${requestScope.resourceContext}/js/jquery.dataTables.min.js"></script>
	<script>
		$(document).ready(function(){
			$.each(["#recordCountPerPage", "#edcStat"], function(idx, element) {
				$(element).change(function () {
					$('#pageIndex').val(1);
					fn_get_rsvn_list();
				});
			});

			fn_get_rsvn_list(true);

			//sms 발송 제어 start
			$("#smsBtn").click(function() {
				if ($(".sms-target-table :checkbox").filter(":checked").length < 1) {
					alert("발송대상 회원을 선택해주세요.");
					return;
				}
				fn_set_sms_list();
				if (!$.trim($("#senderTelno").val())) {
					$("#senderTel").val($("#orgTel").val());
				}
				$("#smsModalBtn").trigger("click");
			});
			//sms 발송 제어 end
		});

		function fn_excelDown() {
			$.get({
			    url : './edcRsvnInfoListAjax?exceldown=Y',
			    data : $('#searchVO').serialize(),
				dataType : 'html',
				success: function(data) {
			    	$("#divHidden").html(data);
			    	tableExportExcel("dataTableY", "강좌접수인원현황");
				}
		    });
		}

		function fn_get_rsvn_list(historyFlag) {
			$.ajax({
			    url : './edcRsvnInfoListAjax',
			    data : $('#searchVO').serialize(),
				dataType : 'html',
				success: function(data) {
			    	$("#divCard").html(data);
			    	if(historyFlag){
			    		history.pushState({pageIndex:$("#pageIndex").val()}, '예약목록조회', '?' + $('#searchVO').serialize());
			    	}

				}
		    });
		}

		function fn_set_page(page) {
			var page = page > 0 ? page : 1;
			$('#pageIndex').val(page);
			fn_get_rsvn_list();
		}

		//입금확인(현금입금처리)
		function fn_pay_in_cash(edcRsvnReqid, edcRsvnNo) {
			if (confirm("[<hisco:DateUtil datestr='' format='yyyy-MM-dd'/>] 일자로 입금확인 처리하시겠습니까?")) {
				$.post({
				    url : './edcRsvnPay.json',
				    data : {edcRsvnReqid:edcRsvnReqid, edcRsvnNo:edcRsvnNo},
					success: function(data) {
				    	alert(data.result.msg);
				    	if (data.result.code == "SUCCESS") {
				    		location.reload();
				    	}
					}
			    });
			}
		}

		//유료 신청취소/환불취소
		function fn_cancel(edcRsvnReqid, edcRsvnNo, edcStat, payCancelYn, tid, allCancelYn) {
			var cookieCancelYn = getCookie('allCancelYn');
			if (cookieCancelYn) {
				allCancelYn = cookieCancelYn;
			}

			console.log("edcRsvnReqid = " + edcRsvnReqid);
			console.log("edcRsvnNo = " + edcRsvnNo);
			console.log("edcStat = " + edcStat);
			console.log("payCancelYn = " + payCancelYn);
			console.log("tid = " + tid);
			console.log("allCancelYn = " + allCancelYn);

			if (allCancelYn == "Y") { //유료이고 수업시작 전이면 전액환불 취소처리
				fn_all_cancel(edcRsvnReqid, edcRsvnNo);
			} else {
				fn_partial_cancel_ui(edcRsvnReqid, edcRsvnNo);
			}
		}

		//신청취소, 전체환불처리
		function fn_all_cancel(edcRsvnReqid, edcRsvnNo) {
			if (confirm("취소 처리하시겠습니까?")) {
				$.post({
				    url : './edcRsvnCancel.json',
				    data : {edcRsvnReqid:edcRsvnReqid, edcRsvnNo:edcRsvnNo},
					success: function(data) {
				    	alert(data.result.msg);
				    	if (data.result.code == "SUCCESS") {
				    		location.reload();
				    	}
					}
			    });
			}
		}

		//부분환불처리화면구성
		function fn_partial_cancel_ui(edcRsvnReqid, edcRsvnNo) {
			$.get({
			    url : './edcRsvnPartialCancelAjax',
			    dataType : 'html',
			    data : {edcRsvnNo:edcRsvnNo},
				success: function(data) {
			    	$("#partialCancelDiv").html(data);
			    	$("#partialCancelModalBtn").trigger("click");
				}
		    });
		}

		//부분환불처리
		function fn_partial_cancel(edcRsvnReqid, edcRsvnNo) {
			if (confirm("취소 처리하시겠습니까?")) {
				return;
				$.post({
				    url : './edcRsvnPartialCancel.json',
				    data : {edcRsvnReqid:edcRsvnReqid, edcRsvnNo:edcRsvnNo},
					success: function(data) {
				    	alert(data.result.msg);
				    	if (data.result.code == "SUCCESS") {
				    		location.reload();
				    	}
					}
			    });
			}
		}

		// 테스트용
		function getCookie(name) {
			var x, y;
			var val = document.cookie.split(';');

			for (var i = 0; i < val.length; i++) {
				x = val[i].substr(0, val[i].indexOf('='));
				y = val[i].substr(val[i].indexOf('=') + 1);
				x = x.replace(/^\s+|\s+$/g, ''); // 앞과 뒤의 공백 제거하기
				if (x == name) {
					return unescape(y); // unescape로 디코딩 후 값 리턴
				}
			}
		}

		function setCookie(name, value, days) {
			  var exdate = new Date();
			  exdate.setDate(exdate.getDate() + days);
			  // 설정 일수만큼 현재시간에 만료값으로 지정

			  var cookie_value = value + ((days == null) ? '' : '; path=/; expires=' + exdate.toUTCString());
			  document.cookie = name + '=' + cookie_value;
		}
	</script>
</head>
<body>

	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>강좌접수 인원현황</h1>
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
		<form:hidden path="edcPrgmNo" />
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
		 	 <div class="col-2">
		 	 	<div class="input-group input-group-sm">
				  	<select id="edcStat" name="edcStat" title="신청상태" class="form-control">
				 	 	${hisco:makeOptions("SM_RSVN_STAT", "신청상태(선택)", searchVO.edcStat)}
				 	 </select>
				 </div>
		 	 </div>
			 <div class="col-2" style="margin-top:10px;float:right;width:45%">
			 	총  <span id="totCount">0</span> 건 조회
			 </div>
			 <div class="col-6" style="margin-top:5px; text-align:right">
				<button type="button" class="btn  btn-secondary btn-sm btn-flat" onclick="fn_excelDown()">EXCEL내려받기</button>
				<button type="button" class="btn  btn-secondary btn-sm btn-flat" id="smsBtn">SMS발송(선택회원)</button>
				<button type="button" class="btn  btn-secondary btn-sm btn-flat" data-toggle="modal" data-target="#modal-sms" id="smsModalBtn" style="display:none"/>
				<button type="button" class="btn  btn-secondary btn-sm btn-flat" data-toggle="modal" data-target="#modal-partial-cancel" id="partialCancelModalBtn" style="display:none"/>
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
	                      	<th>예약번호</th>
	                      	<th>접수경로</th>
	                      	<th>회원번호</th>
	                      	<th>회원명</th>
	                      	<th>ID</th>
							<th>휴대폰번호</th>
							<th>성별</th>
							<th>생년월일</th>
							<th>신청일시</th>
							<th>결제마감시간</th>
							<th>신청상태</th>
							<th>결제금액</th>
							<th>비고</th>
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

    <!-- sms 발송 modal layer -->
	<%@ include file="/WEB-INF/jsp/webadm/edcrsvn/modalSmsLayer.jsp" %>
	<%@ include file="/WEB-INF/jsp/webadm/edcrsvn/modalPartialCancelLayer.jsp" %>
	<div id="divHidden" style="display:none"></div>
</section>
</body>