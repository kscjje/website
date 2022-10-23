<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<style>
	.sort {font-size:16px; cursor:pointer}
	.sort.active {color:red}
	#setItemTable label{margin-bottom:0.2rem}
	</style>
	<script>
		function fn_excelDown() {
			var $cform = $('#searchVO').clone(true);

			$cform.attr('id', 'excelDownloadForm');
			$cform.attr('name', 'excelDownloadForm');
			$cform.attr('method', 'get');
			$cform.attr('action', "./totalRsvnListExcel");


			$cform.find("#searchOrgNo").val($('#searchVO').find("#searchOrgNo").val());

			$('body').append($cform);
			$('#excelDownloadForm').submit();
			$('#excelDownloadForm').remove();
		}


		$(document).ready(function(){

			var pageIndex = $('#pageIndex').val();
			if(pageIndex == "" || pageIndex == "0"){
				pageIndex = "1";
			}
			fn_load_student_list(pageIndex);

			$("#modalSearchVO").find("#edcPrgmnm").keypress(function(e) {
      			if (e.keyCode == 13) {
      				fn_modal_program_find("search");
      				e.preventDefault();
      			}
      		});

			$("#periodCondition").change(function () {
				if (!$(this).val()) {
					$("#searchYear").prop("disabled", true);
					$("#searchMonth").prop("disabled", true);
				} else {
					$("#searchYear").prop("disabled", false);
					$("#searchMonth").prop("disabled", false);
				}
			});

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

			$("#searchOrgNo").change(function () {
				fn_get_rsvnset_nm_list();
			});

			<c:if test="${empty rsvnsetNmList}">
			$("#edcRsvnsetNm").prop("disabled", true);
			</c:if>
			<c:if test="${!empty rsvnsetNmList}">
			$("#edcRsvnsetNm :eq(1)").prop("selected", true);
			</c:if>

			if($("#searchOrgNo").val() != ""){
				fn_get_rsvnset_nm_list();
			}

		});

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

					if (options.length > 1) $("#edcRsvnsetNm :eq(1)").prop("selected", true);
				}
			});
      	}

		function fn_check_orgNo() {
			if (!$("#searchOrgNo").val()) {
				alert("기관을 먼저 선택하시고 검색해 주세요.");
				return;
			}
			fn_load_student_list(1);
		}

		function fn_load_student_list(page){
			$('#pageIndex').val(page);

			$.ajax({
			    url : './totalRsvnListAjax',
			    data : $('#searchVO').serialize(),
				dataType : 'html',
				success: function(data) {
			    	$("#divCard").html(data);

			    	history.pushState({pageIndex:$("#pageIndex").val()}, '회원별 접수 현황', '?' + $('#searchVO').serialize());
				}
		    });
		}

		function fn_reset(){
			$('#searchVO').find("#searchOrgNo").val(null).trigger('change');
			$('#searchVO').find("input[type=text]").val('');
			$('#searchVO').find("select[name=periodCondition]").val('');
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
				    		fn_load_student_list($('#pageIndex').val());
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
				    		fn_load_student_list($('#pageIndex').val());
				    	}
					}
			    });
			}
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
	            <h1>수강신청회원 통합검색</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div>
	</section>

<!-- Main content -->
<section class="content">

      <div class="container-fluid">
<form:form commandName="searchVO" name="searchVO" method="get">
		<form:hidden path="searchOrder" />
		<form:hidden path="searchOrderDir" />
		<form:hidden path="pageIndex" />
		<form:hidden path="edcPrgmNo" />
		<input type="hidden" name="showColumnList" id="showColumnList" value="<c:out value="${showColumnList}"/>" />
		<!-- 검색box start -->
       	<div class="card card-primary card-outline">
	 	 	<div class="card-body">
	 	 	 	<div class="row">
	              	<div class="col-3">
	                   	<hisco:OrgList defaultNo="${searchVO.searchOrgNo}" id="searchOrgNo" defaultSelect="true"/>
	                </div>

					<div class="col-3">
	             		<form:select path="edcRsvnsetNm" title="모집차수"  class="form-control">
	             			<option value=''>모집차수(선택)</option>
	             			<form:options items="${rsvnsetNmList}"/>
						</form:select>
					</div>

	                <div class="col-4 input-group-append">
	                   	<div class="input-group">
							<form:input cssClass="form-control" path="edcPrgmnm" placeHolder="프로그램을 검색 선택하세요" readonly="true"/>
							<div class="input-group-append">
								<button class="btn btn-secondary" type="button" data-toggle="modal" data-target="#modal-program" onclick="fn_check_search()">
	              					<i class="fas fa-search fa-fw findprogram"></i> 찾기
	            				</button>
	            			</div>
						</div>
	                </div>
				</div>
				<div class="row" style="margin-top:10px">
	              	<div class="col-2">
	             		<form:select path="searchCondition" title="검색영역"  class="form-control">
	             				<form:option value="memNm">회원명</form:option>
	             				<form:option value="memId">ID</form:option>
	             				<form:option value="rsvnNo">예약번호</form:option>
	             				<form:option value="hp">휴대폰번호</form:option>
						</form:select>
					</div>
					<div class="col-4">
						<form:input cssClass="form-control" path="searchKeyword" placeHolder="검색어를 입력하세요"/>
					</div>
	           </div>
				<div class="row" style="margin-top:10px">
	              	<div class="col-2">
	             		<form:select path="periodCondition" title="날짜"  class="form-control">
	             			${hisco:makeOptions("SM_SRCH_PERIOD_CD", "기간조건(선택)", "${searchVO.periodCondition}")}
						</form:select>
					</div>
					<div class="col-4">
						<div class="input-group date" style="float:left;width:45%"  data-target-input="nearest" >
							<select id="searchYear" name="searchYear" title="년도" class="form-control" disabled="true">
		             			${hisco:makeYearOptions("년도", 30, "")}
							</select>
							&nbsp;
	                    </div>
						<div class="input-group date" style="float:left;width:35%"  data-target-input="nearest">
							<select id="searchMonth" name="searchMonth" title="월"  class="form-control" disabled="true">
		             			${hisco:makeMonthOptions("월", "")}
							</select>
	                    </div>
					</div>
	           </div>
	 	   	</div>
 	   	</div>
 	   	<!-- 검색box end -->


 	    <div class="row" style="margin-bottom:15px;text-align:right">
 	    	<div class="col-4"></div>
 	    	<div class="col-2">
 	    		<button type="button" class="btn btn-primary btn-block" onclick="fn_check_orgNo()">조회</button>
 	    	</div>
 	    	<div class="col-2">
 	    		<button type="button" class="btn btn-secondary btn-block" onclick="fn_reset()">초기화</button>
 	    	</div>
 	    	<div class="col-5"></div>
 	    </div>

 	    <div class="row col-12" style="margin-bottom:5px;">

			<div class="col-2">
			 	<div class="input-group input-group-sm">
			 	 <select id="recordCountPerPage" name="recordCountPerPage" title="년도" class="form-control">
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
			 <div class="col-2" style="margin-top:10px;float:left;width:45%">
			 	총  <span><fmt:formatNumber value="${totCount}"/></span> 건 조회
			 </div>

			 <div class="col-6" style="text-align:right">
				<button type="button" class="btn  btn-secondary btn-sm btn-flat"  data-toggle="modal" data-target="#modal-itemset">조회결과 항목설정</button>
				<button type="button" class="btn  btn-secondary btn-sm btn-flat" onclick="fn_excelDown()">EXCEL 내려받기</button>
				<button type="button" class="btn  btn-secondary btn-sm btn-flat" id="smsBtn">SMS발송(선택회원)</button>
				<button type="button" class="btn  btn-secondary btn-sm btn-flat" data-toggle="modal" data-target="#modal-sms" id="smsModalBtn" style="display:none"/>
			</div>
		</div>
</form:form>
 	    <div class="row">
			<div class="col-12">
				<div class="card" id="divCard">

          		 </div>
			</div>


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
									<li><label><input type="checkbox" name="selectItem" value="edcReqDate"/> 신청일시(▼)</label></li>

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

									<li><label><input type="checkbox" name="selectItem" value="edcPaywaitEnddatetime"/> 결제마감시간(▼)</label></li>
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

      <!-- 프로그램선택 -->
      <div class="modal fade" id="modal-program">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">프로그램을 선택하세요</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="programClose">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body" id="PROGRAM_TABLE">
				<div class="card card-primary card-outline">
			 	 	<div class="card-body">
 <form name="modalSearchVO" id="modalSearchVO" method="get">
			 	 	 	<div class="row">
			              	<div class="col-5">
			                   	<hisco:OrgList defaultNo="${modalSearchVO.orgNo}" id="orgNo" />
			                </div>
			                <%-- <div class="col-7">
			                   	<hisco:CateList defaultNo="${modalSearchVO.CtgCd}" id="CtgCd" />
			                </div> --%>
			            </div>
			            <div class="row" style="margin-top:10px">
							<div class="col-12">
								<input type="text" class="form-control" name="edcPrgmnm" id="edcPrgmnm" maxlength="100" placeHolder="검색할 프로그램명을 입력하세요"/>
							</div>
			            </div>
</form>
			 	   	</div>
		            <div class="row" style="margin-bottom:15px;text-align:right">
			 	    	<div class="col-4"></div>
			 	    	<div class="col-2">
			 	    		<button type="button" class="btn btn-primary btn-block" onclick="fn_modal_program_find('search')">조회</button>
			 	    	</div>
			 	    	<div class="col-2">
			 	    		<button type="button" class="btn btn-secondary btn-block" onclick="fn_modal_reset()">초기화</button>
			 	    	</div>
			 	    	<div class="col-5"></div>
			 	     </div>
		 	   	</div>

		 	   	<div class="row">
		          <div class="col-12">
		            <div class="card">
		              <!-- /.card-header -->
		              <div class="card-body table-responsive p-0" style="overflow:scroll; height:400px;">
			               <table class="table table-bordered table-hover text-nowrap">
			               	 <colgroup>
								<col style="width: 10%;">
								<col style="width: 15%;">
								<col style="width: *">
								<col style="width: 9%;">
							  </colgroup>
			                  <thead>
			                    <tr>
			                      	<th align='center'>분야</th>
			                      	<th align='center'>프로그램고유번호</th>
			                      	<th align='center'><a href="javascript:fn_modal_program_find('prgmnm');" id="togglenm">프로그램명▲</a></th>
			                      	<th align='center'>공개여부</th>
			                    </tr>
			                  </thead>
			                  <tbody id="modalProgramTbody">
			                  	<tr>
									<td colspan="4" align="center">기관선택은 필수 입니다.</td>
								</tr>
								<!-- <tr>
									<td>기타</td>
									<td>1000001</td>
									<td>프로그램명1</td>
									<td>공개여부</td>
								</tr>
								<tr>
									<td>기타</td>
									<td>1000002</td>
									<td>프로그램명2</td>
									<td>공개여부</td>
								</tr> -->
			                  </tbody>
			                </table>
		              </div>
		          </div>
		        </div>
		      </div>

            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" id="onclick-btn" class="btn btn-primary" onclick="fn_pick_program()">선택프로그램 적용</button>
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

		function fn_check_search(){
			if($("#modalSearchVO").find("#orgNo").val() != $('#searchOrgNo').val()){
				$("#modalSearchVO").find("#orgNo").val($('#searchOrgNo').val()).trigger('change');

				fn_modal_program_find();
			}
		}


      	function fn_modal_program_find(src) {
			var data = {};
			data.searchOrderBy = "BY_PROGRAM_NM_ASC";


			if (src == "prgmnm") {
				if ($("#togglenm").text().indexOf("▼") > 0) data.searchOrderBy = "BY_PROGRAM_NM_ASC";
				else data.searchOrderBy = "BY_PROGRAM_NM_DESC";
			}

			var orgNo = $("#modalSearchVO").find("#orgNo").val();
			var CtgCd = $("#modalSearchVO").find("#CtgCd").val();
			var edcPrgmnm = $("#modalSearchVO").find("#edcPrgmnm").val();

			if (orgNo) data.searchOrgNo = orgNo;
			if (CtgCd) data.searchCtgcd = CtgCd;
			if (edcPrgmnm){
				data.searchKeyword = edcPrgmnm;
				data.searchCondition = "1";
			}

			if (!orgNo) {
				alert("기관선택은 필수 입니다.");
				return;
			}

			var htmlArray = [];
			var html = "<tr><td align='center'>#CATE#</td><td align='center'>#PRGMID#</td><td align='left'>#PRGMNM#</td><td align='center'>#OPENYN#</td></tr>";
			$.ajax({
				url: './edcProgramList.json',
				data: data,
				success: function(data) {
			    	$.each(data.programList, function(idx, item) {
			    		var temp = html.replace("#CATE#", (item.comCtgNm==undefined?'-':item.comCtgNm));
			    		temp = temp.replace("#PRGMID#", item.edcPrgmNo);
			    		temp = temp.replace("#PRGMNM#", item.edcPrgmnm);
			    		temp = temp.replace("#OPENYN#", item.edcOpenyn);
			    		htmlArray[idx] = temp;
			    	});
			    	$("#modalProgramTbody").html(htmlArray.join(""));

			    	if(htmlArray.length < 1){
			    		$("#modalProgramTbody").html('<tr><td colspan="4" align="center">검색된 데이타가 없습니다.</td></tr>');
			    	}

			    	fn_set_tr_click();
			    	fn_modal_programnm_toggle();
				}
			});
		}

      	function fn_modal_programnm_toggle() {
      		var linkText = $("#togglenm").text();
      		if (linkText.indexOf("▼") > 0) {
      			$("#togglenm").text("프로그램명▲");
      		} else {
      			$("#togglenm").text("프로그램명▼");
      		}
      	}

		function fn_modal_reset() {
			$("#modalSearchVO").find("#orgNo option:eq(0)").attr("selected", "");
			$("#modalSearchVO").find("#select2-orgNo-container").attr("title", "기관선택").text("기관선택");
			$("#modalSearchVO").find("#CtgCd option:eq(0)").attr("selected", "");
			$("#modalSearchVO").find("#select2-CtgCd-container").attr("title", "분야선택").text("분야 선택");
			$("#modalSearchVO")[0].reset();
			$("#modalProgramTbody").html('<tr><td colspan="4" align="center">조건 선택 후 조회하세요</td></tr>');
		}

      	var pickPrgmNo = "";
      	var pickPrgmnm = "";
      	/*프로그램클릭*/

      	function fn_set_tr_click() {
      		$("#modalProgramTbody > tr").click(function() {
      			$("#modalProgramTbody tr").css( "background-color", "" ).css("color" , "#666");
      			pickPrgmNo = $(this).find("td:eq(1)").text();
      			pickPrgmnm = $(this).find("td:eq(2)").text();
      			$(this).css("background-color" , "#adb5bd").css("color" , "white");
      		});
      	}



      	/*프로그램선택*/
      	function fn_pick_program() {
      		//검색조건 일치 시키기
      		$('#searchOrgNo').val($("#modalSearchVO").find("#orgNo").val()).trigger('change');
      		$("#searchVO").find('#CtgCd').val($("#modalSearchVO").find("#CtgCd").val()).trigger('change');
      		$("#searchVO").find('#comCtgnm').val($("#modalSearchVO").find("#CtgCd option:checked").text());

      		$("#edcPrgmNo").val(pickPrgmNo);
      		$("#edcPrgmnm").val(pickPrgmnm);
      		$("#programClose").trigger("click");
      		fn_get_rsvnset_info_list();
      	}

      	/*모집차수목록조회*/
      	function fn_get_rsvnset_info_list() {
      		var options = [];
      		options.push("<option value='0'>모집차수(선택)</option>")
      		$.get({
				url: './edcRsvnsetInfoList.json',
				data: {edcPrgmNo: pickPrgmNo},
				success: function(data) {
					$.each(data.rsvnsetInfoList, function(idx, item) {
						var title = item.edcRsvnsetNm;
						if(title == 'null' || title == null) title = item.edcRsvnsetSeq;
						options.push("<option value='" + item.edcRsvnsetSeq + "'>" + title + "</option>")
			    	});

					$("#edcRsvnsetSeq").children("option").remove();
					$("#edcRsvnsetSeq").append(options.join(""));
					$("#edcRsvnsetSeq").prop("disabled", options.length > 1 ? false : true);
				}
			});
      	}
      </script>

    <!-- sms 발송 modal layer -->
	<%@ include file="/WEB-INF/jsp/webadm/edcrsvn/modalSmsLayer.jsp" %>
	<%@ include file="/WEB-INF/jsp/webadm/edcrsvn/modalPartialCancelLayer.jsp" %>
	<div id="divHidden" style="display:none"></div>
	<button type="button" class="btn  btn-secondary btn-sm btn-flat" data-toggle="modal" data-target="#modal-partial-cancel" id="partialCancelModalBtn" style="display:none"/>
</section>
</body>