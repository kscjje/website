<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
     <div class="modal fade" id="modal-partial-cancel">
        <div class="modal-dialog modal-xxl">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">환불취소</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            
            <div class="modal-body" id="partialCancelDiv">
				
			   <%-- <div class="row">
		          <div class="col-12">
			 	 	<h6>[결제 이용정보]</h6>
			 	  </div>
		          <div class="col-12">
		            <div class="card card-primary card-outline">
		              <!-- /.card-header -->
		              <div class="card-body table-responsive p-0">
			               <table class="table table-bordered table-hover text-nowrap" style="text-align:center;vertical-align:middle">
			               	 <colgroup>
								<col style="width: 10%">
								<col style="width: 2%">
								<col style="width: 10%">
								<col style="width: 25%">
								<col style="width: 10%">
								<col style="width: 8%">
								<col style="width: 8%">
								<col style="width: 8%">
								<col style="width: 8%">
								<col style="width: *">
							  </colgroup>
							  <thead>
							  	<tr>
							  		<th>영수증번호<br/>(결제일자)</th>
							  		<th>환불대상</th>
							  		<th>기관명</th>
							  		<th>이용강좌명</th>
							  		<th>수업요일</th>
							  		<th>결제방법</th>
							  		<th>결제금액</th>
							  		<th>수업시작일</th>
							  		<th>수업종료일</th>
							  		<th>수업일수</th>
							  	</tr>
							  </thead>
			                  <tbody>
			                  	<tr>
							  		<td rowspan="3">21224555444<br/>(21-12-01)</td>
							  		<td>
							  			<div class="input-group input-group-sm" style="width:20px;float:left;margin-left:15px">
							  				<input type="checkbox" class="form-control checkbox"/>
							  			</div>
							  		</td>
							  		<td>공릉평생교육원</td>
							  		<td>바리스타자격반</td>
							  		<td>월,수,금</td>
							  		<td>신한카드</td>
							  		<td>
							  			35,000<br/>
							  			(<font color="red">기최소금액: -5,000</font>)
							  		</td>
							  		<td>2021-11-05</td>
							  		<td>2021-11-30</td>
							  		<td>11</td>
							  	</tr>
							  	<tr>
							  		<!-- <th>영수증번호</th> -->
							  		<td colspan="3" rowspan="2"></td>
							  		<!-- <th>기관명</th>
							  		<th>이용강좌명</th> -->
							  		<th>환불기준일</th>
							  		<th>이용일수</th>
							  		<th>공제금액</th>
							  		<th>환불금액</th>
							  		<th>환불방법</th>
							  		<th></th>
							  	</tr>
							  	<tr>
							  		<!-- <th>영수증번호</th> -->
							  		<!-- <th>환불대상</th>
							  		<th>기관명</th>
							  		<th>이용강좌명</th> -->
							  		<td>
							  			<div class="input-group input-group-sm" style="width:140px;float:left">
							  				<input id="cancelDate" name="cancelDate" readonly="readonly" class="form-control pcdatepicker" type="text" value="${today}" maxlength="10" autocomplete="off"/>
							  			</div>
							  		</td>
							  		<td>
							  			<div class="input-group input-group-sm" style="width:100px;float:right">
							  				<input type="text" value="3" size="7px" class="form-control" style="text-align:right"/>
							  			</div>
							  		</td>
							  		<td>
							  			<div class="input-group input-group-sm" style="width:100px;float:right">
							  				<input type="text" value="5,000" size="7px" class="form-control" style="text-align:right"/>
							  			</div>
							  		</td>
							  		<td>
							  			<div class="input-group input-group-sm" style="width:100px;float:right">
							  				<input type="text" value="-30000" size="7px" class="form-control" style="text-align:right"/>
							  			</div>
							  		</td>
							  		<td>
							  			<div class="input-group input-group-sm" style="width:100px;float:left">
							  				<select class="form-control"><option>결제취소</option></select>
							  			</div>
							  		</td>
							  		<td>
							  			<button type="button" class="btn btn-danger">결제취소</button>
							  		</td>
							  	</tr>
			                  </tbody>
			                </table>
		              </div>
		             </div>
		          </div>
		        </div>
		        
		        
		        <div class="row">
		          <div class="col-12">
		            <div class="card" id="modalMemberCardDiv">
		              <!-- /.card-header -->
		              <div class="card-body table-responsive p-0">
			               <table class="table table-bordered table-hover text-nowrap" style="text-align:center" id="dataTable">
			               	 <colgroup>
								<col style="width: 10%">
								<col style="width: 40%">
								<col style="width: 10%">
								<col style="width: 10%">
								<col style="width: *">
							  </colgroup>
			                  <tbody>
			                  	<tr>
			                      	<th>공제총금액</th>
			                      	<td style="text-align:left">5000원</td>
			                      	<th rowspan="3">환불계좌</th>
			                      	<th>은행명</th>
			                      	<td>
			                      		<div class="input-group input-group-sm" style="width:300px;float:left">
			                      			<input type="text" value="" class="form-control" placeholder="환불계좌은행명을 입력하세요" size="20px"/>
			                      		</div>
			                      	</td>
								</tr>
								<tr>
			                      	<th>환불총액</th>
			                      	<td style="text-align:left">-30,000원</td>
			                      	<!-- <th rowspan="3">환불계좌</th> -->
			                      	<th>예금주</th>
			                      	<td>
			                      		<div class="input-group input-group-sm" style="width:300px;float:left">
			                      			<input type="text" value="" class="form-control" placeholder="예금주명을 입력하세요" size="20px"/>
			                      		</div>
			                      	</td>
								</tr>
								<tr>
			                      	<th>환불사유</th>
			                      	<td>
			                      		<div class="input-group input-group-sm" style="width:500px;float:left">
			                      			<select class="form-control">
			                      				<option>직접입력</option>
			                      			</select>
			                      			&nbsp;&nbsp;&nbsp;
			                      			<input type="text" value="" class="form-control" placeholder="환불사유를 입력하세요" maxlength="100"/>
			                      		</div>
			                      	</td>
			                      	<!-- <th rowspan="3">환불계좌</th> -->
			                      	<th>계좌번호</th>
			                      	<td>
			                      		<div class="input-group input-group-sm" style="width:300px;float:left">
			                      			<input type="text" value="" class="form-control" placeholder="계좌번호를 '-'포함하여 입력하세요" size="20px"/>
			                      		</div>
			                      	</td>	
								</tr>
			                  </tbody>
			                </table>
		              </div>
		             </div>
		          </div>
		        </div> --%>
		        
      
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" id="onclick-btn" class="btn btn-danger btn-primary" onclick="fn_refund_cancel()">환불등록</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
            
	<script>
	function fn_refund_cancel() {
		var tid = $("#tid0").val();
		var payMethod = $("#payMethod0").val();
		console.log("tid = " + tid);
		console.log("payMethod = " + payMethod);
		
		if ($(".cancelChk:checked").length == 0) {
			alert("환불등록하고자 하는 강좌를 선택해 주세요.");
			return;
		}
		
		var cashRefund = false; //결제취소		
		$(".cancelChk:checked").each(function() {
			var idx = $(this).data('idx');
			tid = $("#tid" + idx).val();
			if ($("#cancelMethod" + idx).val() == "CASH") {
				cashRefund = true; //계좌환불
			}
		});
		
		if ($("#cancelReasonCd").val() == "3001") { //기타사유 직접입력
			if (!$.trim($("#cancelReasonDesc").val())) {
				alert("환불사유를 입력해 주세요.");
				$("#cancelReasonDesc").focus();
				return;
			}
		}

		if (payMethod == "VBANK") { //가상계좌 결제취소
			$("#retBankNm").val("");
			if (!$("#retBankCd").val() && $("#retBankCd").val() == "retBankNm") {
				alert("환불계좌은행을 선택해 주세요.");
				return;
			}
		}
		
		if (payMethod == "VBANK" || cashRefund) { //가상계좌 혹은 관리자가 직접 계좌환불 실시
			if (!$("#retBankCd").val()) {
				alert("환불계좌은행을 선택해 주세요.");
				return;
			} else if ($("#retBankCd").val() == "retBankNm") { //계좌환불
				if (!$.trim($("#retBankNm").val())) {
					alert("환불계좌은행명을 입력하세요.");
					$("#retBankNm").focus();
					return;
				}
			}
			
			if (!frmRef.validateForm())
				return;
		}
		
		var data = {};
		var member = {};
		var itemList = [];
		var item = {};
		var payment = {};
		
		member.memNo = $("#memNo").val();			
		member.memNm = $("#memNm").val();
		member.memHp = $("#memHp").val();
		member.memBirthdate = $("#memBirthdate").val();
		member.memGender = $("#memGender").val();
		
		var cancelPayAmt = 0;
		var remainAmt = 0;
		$(".cancelChk:checked").each(function() {
			var idx = $(this).data('idx');
			item.cancelDcAmt = parseInt($("#cancelDcAmt" + idx).val())*-1; //+금액으로 전달
			item.cancelAmt = parseInt($("#cancelAmt" + idx).val().replaceAll(",", ""))*-1; //+금액으로전달
			item.useDateCnt = $("#useDateCnt" + idx).val();
			item.remainDateCnt = parseInt($("#edcClcnt" + idx).val()) - parseInt(item.useDateCnt);
			item.remainAmt = parseInt($("#remainAmt" + idx).val());
			
			cancelPayAmt += parseInt(item.cancelAmt);
			remainAmt += parseInt(item.remainAmt);
			
			item.edcRsvnReqid = $("#edcRsvnReqid" + idx).val();
			item.costAmt = $("#edcProgmCost" + idx).val();
			item.orgNo = $("#orgNo").val();
			
			if ($("#cancelMethod" + idx).val() == "CASH") {
				item.cancelPayComcd = "NOWON";
				item.cancelPayMethod = "CASH";
				item.cancelFinanceCd = "CH";
				item.cancelFinanceNm = "현금";
			} else {
				item.cancelPayComcd = $("#payComcd").val();
				item.cancelPayMethod = $("#payMethod").val();
				item.cancelFinanceCd = $("#financeCd").val();
				item.cancelFinanceNm = $("#financeNm").val();
			}
			
			item.cancelDate = $("#cancelDate").val().replaceAll("-", "");
			itemList.push(item);
		});
		
		if (remainAmt < cancelPayAmt) {
			alert("환불할 금액(" + comma(cancelPayAmt) + ")이 잔액(" + comma(remainAmt) + ")보다 큽니다.");
			return;
		}
		
		payment.rsvnNo = $("#rsvnNo").val();
		payment.onoff = "20"; //10:온라인, 20:오프라인
		payment.registGbn = "1001"; //신규
		payment.cancelPayAmt = cancelPayAmt;
		payment.terminalType = "1001"; //SM_TERMINAL_TYPE_현장PC
		
		payment.cancelReasonCd = $("#cancelReasonCd").val();
		payment.cancelReasonDesc = $.trim($("#cancelReasonDesc").val());
		
		payment.dpstrNm = $("#retDpstrNm").val();
		payment.bankCd = $("#retBankCd").val();
		payment.bankNm = $("#retBankNm").val();
		payment.accountNum = $("#retAcountNum").val();
		
		if (payment.bankCd == "retBankNm") {
			payment.bankCd = "";
		} else {
			payment.bankNm = $("#retBankCd option:checked").text();
		}
		
		if (payment.bankNm == "은행선택") {
			payment.bankNm = "";
		}
		
		data.rsvnStat = "3004"; //환불취소
		data.member = member;
		data.itemList = itemList;
		data.payment = payment;
		
		if (confirm("환불등록하시겠습니까?")) {
			$.post({
			    url : './edcRsvnPartialCancel.json',
			    data : JSON.stringify(data),
			    contentType : "application/json; charset=UTF-8",
				success: function(data) {
			    	alert(data.result.msg);
			    	if (data.result.code == "SUCCESS") {
			    		//fn_partial_cancel_ui($("#edcRsvnReqid0").val(), $("#edcRsvnNo0").val()); //현재 layer 갱신
			    		location.reload();
			    	} else {
			    	}
				}
		    });
		}
	}	
	</script>