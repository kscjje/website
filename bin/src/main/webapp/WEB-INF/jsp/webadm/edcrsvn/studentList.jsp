<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<style>
	.sort {font-size:16px; cursor:pointer}
	.sort.active {color:red}
	#divCard{max-height:380px;overflow:auto}
	#setItemTable label{margin-bottom:0.2rem}
	</style>
	<script>
		var SELECT_PRGMID = "";
		var SELECT_PRGSET = "";
		var SELECT_PAGENO = 1;

		function fn_excelDown() {
			var $cform = $('#searchVO').clone(true);

			$cform.attr('id', 'excelDownloadForm');
			$cform.attr('name', 'excelDownloadForm');
			$cform.attr('method', 'get');
			$cform.attr('action', "./studentListExcel");

			$('body').append($cform);
			$('#excelDownloadForm').submit();
			$('#excelDownloadForm').remove();
		}

		function fn_book_excel(){
			$('#studentBookForm').submit();
		}

		function fn_prgmvo_submit() {
			if (!$("#searchOrgNo").val()) {
				alert("기관을 먼저 선택하시고 검색해 주세요.");
				return;
			}
			document.prgmVO.submit();
		}

		function fn_get_program_list() {
			$("#divSubCard").html("");

			$.ajax({
			    url : './studentPrgListAjax',
			    data : $('#prgmVO').serialize(),
				dataType : 'html',
				success: function(data) {
			    	$("#divCard").html(data);

			    	$('#dataTable').find("tbody tr").click(function(){
			    		$('#dataTable').find("tbody tr").css("background-color" , "#fff").css("color" , "#666");
			    		$(this).css("background-color" , "#adb5bd").css("color" , "white");

			    		SELECT_PRGMID = $(this).attr("data-prgmId");
			    		SELECT_PRGSET = $(this).attr("data-prgmSet");
			    		fn_load_student_list(1);

			    	});

			    	var edcPrgmNo = $('#searchVO').find("#edcPrgmNo").val();
					var edcRsvnsetSeq = $('#searchVO').find("#edcRsvnsetSeq").val();

					if(edcPrgmNo != undefined) SELECT_PRGMID = edcPrgmNo;
					if(edcRsvnsetSeq != undefined) SELECT_PRGSET = edcRsvnsetSeq;

			    	$.each($('#dataTable').find("tbody tr") , function(index,item){
						if($(this).attr("data-prgmId") == SELECT_PRGMID &&  $(this).attr("data-prgmSet") == edcRsvnsetSeq){
							$('#dataTable').find("tbody tr").css("background-color" , "#fff").css("color" , "#666");
				    		$(this).css("background-color" , "#adb5bd").css("color" , "white");
						}
			    	});


			    	if($("#dataTable tr:eq(1)").attr("data-prgmId")){
			    		$("#dataTable tr:eq(1)").click();
			    	}
				}
		    });
		}

		$(document).ready(function(){
			$("#searchOrgNo").change(function () {
				fn_get_rsvnset_nm_list();
			});

			<c:if test="${empty rsvnsetNmList}">
			$("#edcRsvnsetNm").prop("disabled", true);
			</c:if>

			fn_get_program_list();
			/*
			if($("#searchOrgNo").val() != ""){
				fn_get_rsvnset_nm_list();
			}
			*/
		});

		function fn_load_student_list(page){
			if (!SELECT_PRGMID) return;

			var edcStat = $('#searchVO').find("#edcStat").val();
			var searchOrder = $('#searchVO').find("#searchOrder").val();
			var searchOrderDir = $('#searchVO').find("#searchOrderDir").val();
			var recordCountPerPage = $('#searchVO').find("#recordCountPerPage").val();
			var showColumnList = $('#searchVO').find("#showColumnList").val();
			var excludeCancelListYn = $("#excludeCancelListYn:checked").val();

			if(edcStat == undefined) edcStat = "";
			if(searchOrder == undefined) searchOrder = "";
			if(searchOrderDir == undefined) searchOrderDir = "";
			if(recordCountPerPage == undefined) recordCountPerPage = "10";
			if(showColumnList == undefined) showColumnList = "";

			SELECT_PAGENO = page;

			$.ajax({
			    url : './studentMemListAjax',
			    data : {edcPrgmNo : SELECT_PRGMID
			    		, edcRsvnsetSeq : SELECT_PRGSET
			    		, pageIndex : page
			    		, edcStat : edcStat
			    		, searchOrder:searchOrder
			    		, searchOrderDir:searchOrderDir
			    		, recordCountPerPage:recordCountPerPage
			    		, showColumnList:showColumnList
			    		, excludeCancelListYn : excludeCancelListYn
			    		},
				dataType : 'html',
				success: function(data) {
			    	$("#divSubCard").html(data);
				}
		    });

		}

		function fn_find_category(){
			$.ajax({
			    url: '../comctgr/comCtgrListAjax.json',
			    success: function(data) {
			    	var LIST = data.result;
			        $('#CTG_TABLE').find("tbody").html("");

			        if(LIST){
			        	$('#CTG_TABLE').find("tbody").html(categoryTreeHtml(LIST));
			        }
				}
		    });
		}

		function fn_reset(){
			$('#prgmVO').find("#searchOrgNo").val(null).trigger('change');
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
				    		fn_load_student_list(SELECT_PAGENO);
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
				    		fn_load_student_list(SELECT_PAGENO);
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

		/*모집차수목록조회*/
      	function fn_get_rsvnset_nm_list() {
      		var options = [];
      		options.push('<option value="">모집차수(선택)</option>');
      		$.get({
				url: './edcRsvnsetNmList.json',
				data: {orgNo: $("#searchOrgNo").val()},
				success: function(data) {
					$.each(data.rsvnsetNmList, function(idx, rsvnsetNm) {
						options.push('<option value="' + rsvnsetNm + '">' + rsvnsetNm + '</option>');
			    	});

					$("#edcRsvnsetNm").children("option").remove();
					$("#edcRsvnsetNm").append(options.join(""));
					$("#edcRsvnsetNm").prop("disabled", options.length > 1 ? false : true);

					$("#edcRsvnsetNm :eq(1)").prop("selected", true);

				}
			});
      	}
	</script>
</head>
<body>

	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>수강등록 회원현황</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div>
	</section>

<!-- Main content -->
<section class="content">

      <div class="container-fluid">
<form:form commandName="prgmVO" name="prgmVO" method="get">
		<form:hidden path="edcPrgmNo" />
		<form:hidden path="searchOrder" />
		<form:hidden path="searchOrderDir" />
		<!-- 검색box start -->
       	<div class="card card-primary card-outline">
	 	 	<div class="card-body">
	 	 	 	<div class="row">
	              	<div class="col-3">
	                   	<hisco:OrgList defaultNo="${prgmVO.searchOrgNo}" id="searchOrgNo" defaultSelect="true"/>
	                </div>

	                <div class="col-2">
	             		<form:select path="edcRsvnsetNm" title="모집차수"  class="form-control" >
	             			<option value=''>모집차수(선택)</option>
	             			<form:options items="${rsvnsetNmList}" />
						</form:select>
					</div>

	              	<div class="col-2">
	             		<form:select path="periodCondition" title="날짜"  class="form-control">
	             			${hisco:makeOptions("SM_SRCH_PERIOD_CD", "기간조건(선택)", prgmVO.periodCondition)}
						</form:select>
					</div>
					<div class="col-4">
						<div class="input-group date" style="float:left;width:45%"  data-target-input="nearest" >
							<select id="searchYear" name="searchYear" title="년도" class="form-control">
		             			${hisco:makeYearOptions("년도", 30, prgmVO.searchYear)}
							</select>
							&nbsp;
	                    </div>
						<div class="input-group date" style="float:left;width:35%"  data-target-input="nearest">
							<select id="searchMonth" name="searchMonth" title="월"  class="form-control">
		             			${hisco:makeMonthOptions("월", prgmVO.searchMonth)}
							</select>
	                    </div>
					</div>
	           </div>
	 	   	</div>
 	   	</div>
 	   	<!-- 검색box end -->
		</form:form>

 	    <div class="row" style="margin-bottom:15px;text-align:right">
 	    	<div class="col-4"></div>
 	    	<div class="col-2">
 	    		<button type="button" class="btn btn-primary btn-block" onclick="fn_prgmvo_submit()">조회</button>
 	    	</div>
 	    	<div class="col-2">
 	    		<button type="button" class="btn btn-secondary btn-block" onclick="fn_reset()">초기화</button>
 	    	</div>
 	    	<div class="col-5"></div>
 	    </div>

 	    <div class="row">

          <div class="col-12">
            <div class="card" id="divCard">


           </div>
        </div>
      </div>





       <div id="divSubCard">

      </div>
     </div>

     <!-- 프로그램 분야 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">프로그램 분야를 선택하세요</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
              <table class="table table-sm border-0" id="CTG_TABLE">
                  <tbody>

                  </tbody>
                </table>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" class="btn btn-default" data-dismiss="modal">선택</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>


       <!--출석부  박스 -->
     <div class="modal fade" id="modal-book" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">출석부 내려받기 (엑셀형식)</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
<form name="studentBookForm" id="studentBookForm" action="./studentBookExcel" method="get">
<input type="hidden" name="edcPrgmNo">
<input type="hidden" name="edcRsvnsetSeq">
              <table class="table table-sm table-bordered">
                  <colgroup>
        				<col width="35%">
        				<col width="*">
        			</colgroup>
                  <tbody>
						<tr>
							<th><label for="grpCd">휴대폰 번호표기</label></th>
							<td class="left">
							    <label><input type="radio" name="showHpYn" value="Y" checked/> 노출</label>
							    <label><input type="radio" name="showHpYn" value="N" /> 숨김</label>
							</td>
						</tr>
						<tr>
							<th><label for="grpCd">나이 표기</label></th>
							<td class="left">
							    <label><input type="radio" name="showAgeYn" value="Y" checked/> 노출</label>
							    <label><input type="radio" name="showAgeYn" value="N" /> 숨김</label>
							</td>
						</tr>
						<tr>
							<th><label for="grpCd">성별 표기</label></th>
							<td class="left">
							    <label><input type="radio" name="showGenderYn" value="Y" checked/> 노출</label>
							    <label><input type="radio" name="showGenderYn" value="N" /> 숨김</label>
							</td>
						</tr>
						<tr>
							<th><label for="grpCd">중도환불자 포함여부</label></th>
							<td class="left">
							    <label><input type="radio" name="showRefundYn" value="Y" checked/> 포함</label>
							    <label><input type="radio" name="showRefundYn" value="N" /> 제외</label>
							</td>
						</tr>
						<tr>
							<th><label for="grpCd">출석년월</label></th>
							<td class="left">
							    <select id="showYear" name="showYear" title="년도">
			             			${hisco:makeYearOptions("년도", 10, "")}
								</select>
								년도 &nbsp;
								<select id="showMonth" name="showMonth" title="월">
			             			${hisco:makeMonthOptions("월", "")}
								</select>
								월
							</td>
						</tr>
                  </tbody>
                </table>
</form>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" class="btn btn-default" onclick="fn_book_excel()">내려받기</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>

      <!-- 조회 항목 설정-->
     <div class="modal fade" id="modal-itemset" >
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">조회결과 항목을 설정하세요</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
              <table class="table table-bordered table-sm " id="setItemTable">
                  <thead>
                  	<tr>
                  		<th>조회결과 미적용 항목</th>
                  		<th></th>
                  		<th>적용항목(Dragn&Drop 으로 순서 변경 가능)</th>
                  	</tr>
                  </thead>
                  <tbody>
					<tr>
						<td>
							<div>
								<ul style="list-style:none">
									<li><label><input type="checkbox" name="selectItem" value="edcOnoffintype"/> 접수경로(▼)</label></li>
									<li><label><input type="checkbox" name="selectItem" value="edcReqDate"/> 등록일자(▼)</label></li>

									<li><label><input type="checkbox" name="selectItem" value="edcPrgmnm"/> 프로그램명</label></li>
									<li><label><input type="checkbox" name="selectItem" value="edcRsvnsetNm"/> 모집차수</label></li>
									<li><label><input type="checkbox" name="selectItem" value="edcDaygbnNm"/> 수업요일</label></li>
									<li><label><input type="checkbox" name="selectItem" value="edcSdate"/> 수업시작일</label></li>
									<li><label><input type="checkbox" name="selectItem" value="edcEdate"/> 수업종료일</label></li>

									<li><label><input type="checkbox" name="selectItem" value="edcMemNo"/> 회원번호(▼)</label></li>
									<li><label><input type="checkbox" name="selectItem" value="edcRsvnCustnm"/> 회원명(▼)</label></li>
									<li><label><input type="checkbox" name="selectItem" value="id"/> ID(▼)</label></li>
									<li><label><input type="checkbox" name="selectItem" value="edcRsvnMoblphon"/> 휴대폰번호(▼)</label></li>
									<li><label><input type="checkbox" name="selectItem" value="genderNm"> 성별</label></li>
									<li><label><input type="checkbox" name="selectItem" value="edcRsvnBirthdate"> 생년월일</label></li>
									<li><label><input type="checkbox" name="selectItem" value="edcHomeAddr"/> 주소</label></li>
									<li><label><input type="checkbox" name="selectItem" value="edcEmail"/> 이메일</label></li>

									<li><label><input type="checkbox" name="selectItem" value="edcStatNm"/> 등록상태(▼)</label></li>
									<li><label><input type="checkbox" name="selectItem" value="payMethodNm"/> 결제수단(▼)</label></li>
									<li><label><input type="checkbox" name="selectItem" value="edcProgmCost"/> 수강료</label></li>
									<li><label><input type="checkbox" name="selectItem" value="payAmt"/> 결제금액</label></li>
									<li><label><input type="checkbox" name="selectItem" value="edcDcamt"/> 할인금액</label></li>
									<li><label><input type="checkbox" name="selectItem" value="edcReasondc"/> 할인종류</label></li>

									<li><label><input type="checkbox" name="selectItem" value="payDate"/> 결제일시</label></li>
									<li><label><input type="checkbox" name="selectItem" value="oid"/> 주문번호</label></li>
									<li><label><input type="checkbox" name="selectItem" value="receiptNo"/> 영수증번호</label></li>

									<li><label><input type="checkbox" name="selectItem" value="cancelDtime"/> 취소일시(▼)</label></li>
									<li><label><input type="checkbox" name="selectItem" value="cancelAmt"/> 환불금액</label></li>
									<li><label><input type="checkbox" name="selectItem" value="retAcountNum"/> 환불계좌번호</label></li>
									<li><label><input type="checkbox" name="selectItem" value="retDpstrNm"/> 환불계좌명</label></li>
									<li><label><input type="checkbox" name="selectItem" value="retBankNm"/> 환불계좌은행명</label></li>

									<li><label><input type="checkbox" name="selectItem" value="edcComplstat"/> 수료여부</label></li>
								</ul>
							</div>
						</td>
						<td style="vertical-align:middle; text-align:center">
							<div>
								<button type="button" onclick="removeSetItem()" class="btn  btn-secondary btn-xs btn-flat">◀ 적용제외</button>
							</div>

							<div style="margin-top:20px">
								<button type="button" onclick="addSelectItem()" class="btn  btn-secondary btn-xs btn-flat">적용하기 ▶</button>
							</div>
						</td>
						<td>
							<div>
								<ul style="list-style:none" id="showItem">

								</ul>
							</div>
						</td>
					</tr>
                  </tbody>
                </table>
                <div style="float:right;color:red">* 위의 적용항목은 마우스로 드레그로 순서변경 가능합니다.</div>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" class="btn btn-default" onclick="fn_saveSelctItem()">적용하기</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>

      <script>
      	function removeSetItem(){
      		var cnt = $('input[name=setItem]:checked');
      		if(cnt < 1){
      			alert("적용항목에서 제외할 항목을 선택해 주세요.");
      		}else{
      			$.each($('input[name=setItem]:checked') , function(index,item){
      				var val = $(this).val();
      				$('input[name=selectItem][value='+val+']').eq(0).parent().show();

      				$(this).parent().parent().remove();
      			});
      		}
      	}
      	function addSelectItem(){
      		var cnt = $('input[name=selectItem]:checked');
      		if(cnt < 1){
      			alert("적용항목에 추가할 항목을 선택해 주세요.");
      		}else{
      			$.each($('input[name=selectItem]:checked') , function(index,item){
      				var val = $(this).val();

      				var obj = $('input[name=selectItem][value='+val+']').eq(0);

      				if($('input[name=setItem][value='+val+']').length < 1){
      					var html = "<li>";
        				html += "<label><input type=\"checkbox\" name=\"setItem\" value=\""+ obj.val() +"\"/> "+ obj.parent().text()+"</label>";
        				html += "</li>";
        				$('#showItem').append(html);

        				obj.parent().hide();
      				}


      			});
      		}
      	}
      	function fn_saveSelctItem(){
      		var finalSelectItem = "";

      		$.each($('input[name=setItem]') , function(index,item){
  				var val = $(this).val();
  				if(finalSelectItem != ""){
  					finalSelectItem += "|";
  				}
  				finalSelectItem  += val;
  			});

      		$('#showColumnList').val(finalSelectItem);
      		fn_load_student_list(1);

      		$('#modal-itemset').modal("hide");

      	}
      </script>

    <!-- sms 발송 modal layer -->
	<%@ include file="/WEB-INF/jsp/webadm/edcrsvn/modalSmsLayer.jsp" %>
	<%@ include file="/WEB-INF/jsp/webadm/edcrsvn/modalPartialCancelLayer.jsp" %>
	<div id="divHidden" style="display:none"></div>
	<button type="button" class="btn  btn-secondary btn-sm btn-flat" data-toggle="modal" data-target="#modal-partial-cancel" id="partialCancelModalBtn" style="display:none"/>
</section>
</body>