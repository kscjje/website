<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script src="${requestScope.resourceContext}/js/XE_validate.js"></script>
<div class="row">
	<div class="col-12">
		<div class="input-group input-group-sm" style="width: 120px; float: left; "><h6>[결제 이용정보]</h6></div>
		<div class="input-group input-group-sm" style="width: 230px; float: right;">
			<div style="vertical-align:middle; margin-right:10px; padding-top:5px"><b>환불매출일자</b></div>  
			<input type="text" id="cancelDate" name="cancelDate" readonly class="form-control datepickerfix" value="${today}"/>
		</div>
	</div>
	<div class="col-12">
		<div class="card card-primary card-outline">
			<!-- /.card-header -->
			<div class="card-body table-responsive p-0">
				<table class="table table-bordered table-hover text-nowrap" style="text-align: center; vertical-align: middle">
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
							<th>영수증번호<br />(결제일자)
							</th>
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
					<input type="hidden" id="today" name="today" value="${today}"/>
					<c:set var="payComcd" value=""/>
					<c:forEach items="${rsvnInfoList}" var="item" varStatus="status">
						<input type="hidden" id="edcRsvnReqid${status.index}" name="edcRsvnReqid${status.index}" value="${item.edcRsvnReqid}"/>
						<input type="hidden" id="edcRsvnNo${status.index}" name="edcRsvnNo${status.index}" value="${item.edcRsvnNo}"/>
						<input type="hidden" id="edcPrgmid${status.index}" name="edcPrgmid${status.index}" value="${item.edcPrgmid}"/>
						<input type="hidden" id="edcProgmCost${status.index}" name="edcProgmCost${status.index}" value="${item.edcProgmCost}"/>
						<input type="hidden" id="edcDcamt${status.index}" name="edcDcamt${status.index}" value="${item.edcDcamt}"/>
						<input type="hidden" id="edcTotamt${status.index}" name="edcTotamt${status.index}" value="${item.edcTotamt}"/>
						<input type="hidden" id="edcClcnt${status.index}" name="edcClcnt${status.index}" value="${item.edcClcnt}"/>
						<input type="hidden" id="tid${status.index}" name="tid${status.index}" value="${item.tid}"/>
						<input type="hidden" id="payMethod${status.index}" name="payMethod${status.index}" value="${item.paySummary.payMethod}" class="payMethod"/>
						<input type="hidden" id="edcReasondc${status.index}" name="edcReasondc${status.index}" value="${item.edcReasondc}"/>
						
						<input type="hidden" id="dcRate${status.index}" name="dcRate${status.index}" value="${item.paySummary.discountrate}"/>
						<%-- <input type="hidden" id="totPayAmt${status.index}" name="totPayAmt${status.index}" value="${item.paySummary.payAmt}"/> --%>
						<%-- <input type="hidden" id="totCancelAmt${status.index}" name="totCancelAmt${status.index}" value="${item.paySummary.cancelAmt}"/> --%>
						<%-- <input type="hidden" id="totCancelDcAmt${status.index}" name="totCancelDcAmt${status.index}" value="${item.paySummary.cancelDcAmt}"/> --%>
						<input type="hidden" id="cancelDcAmt${status.index}" name="cancelDcAmt${status.index}" value="${item.paySummary.cancelDcAmt}"/>
						<input type="hidden" id="remainAmt${status.index}" name="remainAmt${status.index}" value="${item.remainAmt}"/>
						
						
						<c:if test="${status.index == 0}">
						<input type="hidden" id="memNo" name="memNo" value="${item.edcRsvnMemno}"/>
						<input type="hidden" id="memHp" name="memHp" value="${item.edcRsvnMoblphon}"/>
						<input type="hidden" id="memNm" name="memNm" value="${item.edcRsvnCustnm}"/>
						<input type="hidden" id="memBirthdate" name="memBirthdate" value="${item.edcRsvnBirthdate}"/>
						<input type="hidden" id="memGender" name="memGender" value="${item.edcRsvnGender}"/>
						<input type="hidden" id="orgNo" name="orgNo" value="${item.orgNo}"/>
						<input type="hidden" id="rsvnNo" name="rsvnNo" value="${item.edcRsvnNo}"/>
						<c:set var="payComcd" value="${item.paySummary.payComcd}"/>
						<input type="hidden" id="payComcd" name="payComcd" value="${item.paySummary.payComcd}"/>
						<input type="hidden" id="payMethod" name="payMethod" value="${item.paySummary.payMethod}"/>
						<input type="hidden" id="financeCd" name="financeCd" value="${item.paySummary.financeCd}"/>
						<input type="hidden" id="financeNm" name="financeNm" value="${item.paySummary.financeNm}"/>
						
						<input type="hidden" id="dpstrNm" name="dpstrNm" value="${item.paySummary.retDpstrNm}"/>						
						<input type="hidden" id="bankCd" name="bankCd" value="${item.paySummary.retBankCd}"/>
						<input type="hidden" id="acountNum" name="acountNum" value="${item.paySummary.retAcountNum}"/>
						</c:if>
						
						<tr>
							<c:if test="${status.index == 0}">
							<td rowspan="${fn:length(rsvnInfoList) * 3}">${item.paySummary.receiptNo}<br />(<hisco:DateUtil datestr="${item.paySummary.payDate}" format="yyyy-MM-dd"/> )</td>
							</c:if>
							<td>
								<div class="input-group input-group-sm" style="width: 20px; float: left; margin-left: 15px">
									<input type="checkbox" class="form-control checkbox cancelChk" id="cancelChk${status.index}" data-idx=${status.index} checked/>
								</div>
							</td>
							<td>${item.orgNm}</td>
							<td>${item.edcPrgmnm}</td>
							<td>${item.edcDaygbnNm}</td>
							<td>${item.paySummary.payMethodNm}</td>
							<td>
								<fmt:formatNumber value='${item.edcTotamt}'/>
								<c:if test="${item.edcReasondc eq 'NWPY'}">
									<br/>
									<font color="red"><fmt:formatNumber value='${item.edcDcamt}'/>(NW)</font>
								</c:if>
							</td>
							<td>${item.edcReqSdate}</td>
							<td>${item.edcReqEdate}</td>
							<td>${item.edcClcnt}</td>
						</tr>
						<tr>
							<th colspan="3">취소내역</th>
							<th>환불기준일</th>
							<th>이용일수</th>
							<th>공제금액</th>
							<th>환불금액</th>
							<th>환불방법</th>
							<th></th>
						</tr>
						<tr>
							<td colspan="3" style="text-align:left">
				  				<c:forEach items="${item.cancelHistory}" var="citem">
				  					[<hisco:DateUtil datestr="${citem.payDate}${citem.payTime}" format="yy-MM-dd HH:mm:ss"/>] <font color="red">
				  					<c:choose>
				  						<c:when test="${citem.payMethodNm eq '현금'}">계좌이체</c:when>
				  						<c:otherwise>${citem.payMethodNm}</c:otherwise>
				  					</c:choose>
				  					&nbsp;&nbsp;&nbsp;<fmt:formatNumber value='${citem.payAmt}'/></font><br/>
				  				</c:forEach>
				  				<c:if test="${fn:length(item.cancelHistory) > 0}">
				  				잔액(취소가능금액) <fmt:formatNumber value='${item.remainAmt}'/>
				  				</c:if>
				  			</td>
							<td>
								<div class="input-group input-group-sm" style="width: 140px; float: left">
									<input type="text" id="cancelDate${status.index}" name="cancelDate${status.index}" readonly data-idx=${status.index} class="form-control datepicker cancelDate" value="${today}"/>
								</div>
							</td>
							<td>
								<div class="input-group input-group-sm" style="width: 100px; float: right">
									<input type="text" name="useDateCnt${status.index}" id="useDateCnt${status.index}" data-idx=${status.index} value="0" data-idx=${status.index} size="7px" class="form-control useDateCnt" style="text-align: right" />
								</div>
							</td>
							<td>
								<div class="input-group input-group-sm" style="width: 100px; float: right">
									<input type="text" name="deductAmt${status.index}" id="deductAmt${status.index}" data-idx=${status.index} value="0" size="7px" class="form-control deductAmt" style="text-align: right" />
								</div>
							</td>
							<td>
								<div class="input-group input-group-sm" style="width: 100px; float: right; color: red">
									<input type="text" name="cancelAmt${status.index}" id="cancelAmt${status.index}" value="0" size="7px" class="form-control cancelAmt" style="text-align: right; color: red" readonly/>
								</div>
							</td>
							<td>
								<div class="input-group input-group-sm" style="width: 100px; float: left">
									<select class="form-control cancelMethod" name="cancelMethod${status.index}" id="cancelMethod${status.index}" data-idx=${status.index}>
										<c:if test="${item.paySummary.payMethod ne 'CASH'}">
										<option value="${item.paySummary.payMethod}">결제취소</option><!-- 현금이 아닌경우, 결제취소가 기본, 계좌환불은 선택 -->
										</c:if>
										<option value="CASH">계좌환불</option><!-- 현금은 계좌환불만 -->
									</select>
								</div>
							</td>
							<td>
								<!-- <button type="button" class="btn btn-danger">결제취소</button> -->
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<form name="frmRef" id="frmRef">
<div class="row">
	<div class="col-12">
		<div class="card">
			<!-- /.card-header -->
			<div class="card-body table-responsive p-0">
				<table class="table table-bordered table-hover text-nowrap" style="text-align: center">
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
							<td style="text-align: left"><span id="totDeductAmtSpan">0</span> 원</td>
							<th rowspan="3">환불계좌</th>
							<th>은행명</th>
							<td>
								<div class="input-group input-group-sm" style="width: 150px; float: left">
									<select name="retBankCd" id="retBankCd" class="form-control select retAcnt" disabled>
										<option value="">은행선택</option>
										<option value="retBankNm">직접입력</option>
										<c:forEach items="${bankList}" var="item">
											<option value="${item.methodCd}">${item.methodNm}</option>
										</c:forEach>
									</select>
								</div>
								<div class="input-group input-group-sm" style="width: 200px; float: left; margin-left:10px">
									<input type="text" name="retBankNm" id="retBankNm" value="" class="form-control retAcnt" placeholder="환불계좌은행명을 입력하세요" maxlength="20" readonly/>
								</div>
							</td>
						</tr>
						<tr>
							<th>환불총액</th>
							<td style="text-align: left; color:red"><span id="totCancelAmtSpan">0</span> 원</td>
							<!-- <th rowspan="3">환불계좌</th> -->
							<th>예금주</th>
							<td>
								<div class="input-group input-group-sm" style="width: 300px; float: left">
									<input type="text" name="retDpstrNm" id="retDpstrNm" value="" class="form-control retAcnt" placeholder="예금주명을 입력하세요" maxlength="20"  readonly validate="required;label:;minlength:2"/>
								</div>
							</td>
						</tr>
						<tr>
							<th>환불사유</th>
							<td>
								<div class="input-group input-group-sm" style="width: 500px; float: left">
									<select class="form-control cancelReasonCd" name="cancelReasonCd" id="cancelReasonCd">
										${hisco:makeOptions("CM_RET_REASON", "EMPTY", "1001")}
									</select> &nbsp;&nbsp;&nbsp; 
									<input type="text" name="cancelReasonDesc" id="cancelReasonDesc" value="" class="form-control" placeholder="환불사유를 입력하세요" maxlength="100" readonly/>
								</div>
							</td>
							<!-- <th rowspan="3">환불계좌</th> -->
							<th>계좌번호</th>
							<td>
								<div class="input-group input-group-sm" style="width: 300px; float: left">
									<input type="text" name="retAcountNum" id="retAcountNum" value="" class="form-control retAcnt" placeholder="계좌번호를 '-'포함하여 입력하세요" maxlength="30"  readonly validate="required;label:;minlength:5"/>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</form>

<script>
	var rsvnList = ${rsvnInfoListJson};
	
	$(document).ready(function(){
		$('.datepickerfix').datepicker({
	        autoclose: true,
	        todayHighlight: true,
	        <c:if test="${payComcd eq 'TOSS'}">
	        minDate: new Date('${today}'), //PG는 당일로 한정해야
	        maxDate: new Date('${today}'), //PG는 당일로 한정해야
	        </c:if>
	        showOn:"button",
	        buttonImage:"${requestScope.resourceContext}/images/ico_select_date.png",
	        buttonImageOnly:true
	    });
		$('.datepicker').datepicker({
	        autoclose: true,
	        todayHighlight: true,
	        showOn:"button",
	        buttonImage:"${requestScope.resourceContext}/images/ico_select_date.png",
	        buttonImageOnly:true
	    });
		
		$("#retDpstrNm").val($("#dpstrNm").val());
		$("#retBankCd").val($("#bankCd").val());
		$("#retBankNm").val($("#bankNm").val());
		$("#retAcountNum").val($("#acountNum").val());
		
		$("#modal-partial-cancel tr").mouseover(function(e) {
			$(this).css({"background-color":"#ffffff"});
		});
		
		$(".cancelChk").change(function() {
			fn_refresh_tot_area();
		});
		
		$(".cancelDate").change(function() {
			fn_recalculate($(this).data("idx"));
		});
		
		$("#cancelDate").change(function() {
			var pgvanCancel = false;
			$(".cancelChk:checked").each(function() {
				var idx = $(this).data('idx');
				if ($("#cancelMethod" + idx).val() == "pgvanCancel") {
					pgvanCancel = true;
				}
			});
			if (pgvanCancel) {
				alert("환불방법이 결제취소일 경우 환불매출일자를 변경할 수 없습니다.");
				$("#cancelDate").val($("#today").val());
			}
		});
		
		$(".useDateCnt").keyup(function() {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
			if ($.trim($(this).val()) == "") {
				$(this).val(0);
			}
			
			if ($(this).val() < 0) {
				$(this).val(0);
			}
			
			var idx = $(this).data("idx");			
			if (parseInt($(this).val()) > parseInt($("#edcClcnt" + idx).val())) {
				$(this).val($("#edcClcnt" + idx).val());
			}
			
			fn_recalculate($(this).data("idx"));
		});
		
		$(".deductAmt").keyup(function(e) {
			$(this).val($(this).val().replace(/[^0-9]/g,""));
			if ($.trim($(this).val()) == "") {
				$(this).val(0);
			}
			
			var idx = $(this).data("idx");
			var edcTotamt = parseInt($("#edcTotamt" + idx).val());
			
			if ($(this).val() > edcTotamt) {
				$(this).val(edcTotamt);
			}
			
			var deductAmt = parseInt($(this).val().replaceAll(",", ""));
			var cancelAmt = (edcTotamt - deductAmt) * -1;
			$("#deductAmt" + idx).val(comma(deductAmt));
			$("#cancelAmt" + idx).val(comma(cancelAmt));
			fn_refresh_tot_area();
		});
		
		$(".cancelReasonCd").change(function() {
			if ($(this).val() == "3001") {
				$("#cancelReasonDesc").prop("readonly", false).focus();
			} else {
				$("#cancelReasonDesc").prop("readonly", true);
				$("#cancelReasonDesc").val("");
			}
		});

		var existVBank = false;
		$(".payMethod").each(function() {
			if ($(this).val() == "VBANK") {
				existVBank = true;
			}
		});
		
		if (existVBank) {
			$(".retAcnt").prop("readonly", false);
			$(".retAcnt").prop("disabled", false);
		} else {
			$(".cancelMethod").change(function() {
				$(".cancelMethod").val($(this).val());
				
				if ($(this).val() == "CASH") { //계좌환불
					$(".retAcnt").prop("readonly", false);
					$(".retAcnt").prop("disabled", false);
					$("#retBankCd").focus();
				} else { //결제취소
					$(".retAcnt").prop("readonly", true);
					$(".retAcnt").prop("disabled", true);
					
					if ($("#cancelDate").val() != $("#today").val()) {
						alert("환불매출일을 오늘로 변경하였습니다.");
						$("#cancelDate").val($("#today").val());
					}
				}
			});
		}
		
		//환불계좌변경시 입력란 제어
		$("#retBankCd").change(function() {
			if ($(this).val() == "retBankNm") { //직접입력
				$("#retBankNm").prop("readonly", false);
				$("#retBankNm").prop("disabled", false);
				$("#retBankNm").focus();
			} else { //결제취소
				$("#retBankNm").prop("readonly", true);
				$("#retBankNm").prop("disabled", true);
			}
		});
		
		//계좌환불건이면 환불계좌정보 활성화
		$(".cancelMethod option:selected").each(function() {
			if ($(this).val() == "CASH") { //계좌환불
				$(".retAcnt").prop("readonly", false);
				$(".retAcnt").prop("disabled", false);
				$("#retBankCd").focus();
			}
		});
		
		$("#retAcountNum").bind("keyup blue", function(e) {
			$(this).val($(this).val().replace(/[^0-9-]/g, ''));
		});
		
		fn_recalculate_all();
		
		var nwpayYn = "N";
		var nwpayAmt = 0;
		$.each(rsvnList, function(idx, item) {
			if (item.edcReasondc == "NWPY") {
				nwpayYn = "Y";
			}
			nwpayAmt += parseInt(item.edcDcamt);			
		});
		
		if (nwpayYn == "Y") {
			alert("※ 노원PAY " + comma(nwpayAmt) + "(NW)이 사용되었습니다.\n노원PAY는 부분환불되지 않습니다.환불시 유의해 주세요.");
		}
		
		window.setupValidateForm(frmRef);
	});
	
	function fn_recalculate_all() {
		rsvnList.forEach(function(rsvn, idx) {
			fn_recalculate(idx);
		});
	}
	
	function fn_recalculate(idx) {
		var edcRsvnReqid = $("#edcRsvnReqid" + idx).val();
		
		var edcProgmCost = parseInt($("#edcProgmCost" + idx).val()); //최초할인금액
		var edcDcamt = parseInt($("#edcDcamt" + idx).val()); //최초할인금액		
		var edcTotamt = parseInt($("#edcTotamt" + idx).val()); //최초결제금액
		var dcRate = parseInt($("#dcRate" + idx).val()); //최초결제금액의할인율
		
		//var totPayAmt = parseInt($("#totPayAmt" + idx).val()); //(누척)현재까지 총결제금액(최초결제금액-취소금액)
		//var totCancelAmt = parseInt($("#totCancelAmt" + idx).val()); //(누적)현재까지 취소금액
		//var totCancelDcAmt = parseInt($("#totCancelDcAmt" + idx).val()); //(누적)현재까지 취소DC금액
		
		var cancelDate = $("#cancelDate" + idx).val();
		console.log("edcRsvnReqid:edcTotamt = " + edcRsvnReqid + ":" + edcTotamt);
		console.log("cancelDate = " + cancelDate);
		
		var totLectDateCnt = parseInt($("#edcClcnt" + idx).val()); //총강좌횟수
		var useDateCnt = parseInt($("#useDateCnt" + idx).val()); //강좌이용횟수
		var deductDateCnt = 0; //공제할강좌횟수
		
		rsvnList.forEach(function(rsvn, idx1) {
			if (rsvn.edcRsvnReqid == edcRsvnReqid) {
				totLectDateCnt = rsvn.lectDateList.length;
				rsvn.lectDateList.forEach(function(lectDate, idx2) {
					if (lectDate < cancelDate) {
						deductDateCnt++;
					}
				});
			}
		});
		
		if (useDateCnt < deductDateCnt) {
			useDateCnt = deductDateCnt;
		}
		$("#useDateCnt" + idx).val(useDateCnt);
		console.log("totLectDateCnt:useDateCnt(duductDateCnt) = " + totLectDateCnt + ":" + useDateCnt);

		//--------------- 1.강좌이용일수/수업일수 비율로 계산 --------------------------------
		var deductRate = (useDateCnt / totLectDateCnt) * 100; //공제율
		var orginalDeductAmt = Math.round((edcProgmCost * (deductRate / 100)) / 10) * 10; //원판매가의 공제금액
		var deductDcAmt = Math.round((orginalDeductAmt * (dcRate / 100)) / 10) * 10; //공제DC금액
		var deductAmt = orginalDeductAmt - deductDcAmt; //공제금액(고객에게 환불해줄 금액)
		var cancelAmt = (edcTotamt - deductAmt) * -1; //취소금액 = 결제금액 - 공제금액
		var cancelDcAmt = (edcDcamt - deductDcAmt) * -1; //최소할인금액 = 결제할인금액 - 공제할인금액
		console.log("(before - cancel) deductRate = " + deductRate); 
		console.log("(before - cancel) deductDcAmt:deductAmt = " + deductDcAmt + ":" + deductAmt);
		console.log("(before - cancel) cancelDcAmt:cancelAmt = " + cancelDcAmt + ":" + cancelAmt);
		
		//--------------- 2.기존 취소금액이 있으면 차감 ------------------------------------
		//deductDcAmt = deductDcAmt - totCancelDcAmt; 
		//deductAmt = deductAmt - totCancelAmt;		
		//console.log("(after - cancel) deductDcAmt:deductAmt = " + deductDcAmt + ":" + deductAmt);
		
		$("#deductDcAmt" + idx).val(deductDcAmt);
		$("#deductAmt" + idx).val(comma(deductAmt));
		$("#cancelAmt" + idx).val(comma(cancelAmt));
		$("#cancelDcAmt" + idx).val(cancelDcAmt);
		
		fn_refresh_tot_area();
	}
	
	function fn_refresh_tot_area() {
		var totDeductAmt = 0;
		var totCancelAmt = 0;
		
		$(".cancelChk:checked").each(function() {
			var idx = $(this).data("idx");
			totDeductAmt += parseInt($("#deductAmt" + idx).val().replaceAll(",", "")); 
			totCancelAmt += parseInt($("#cancelAmt" + idx).val().replaceAll(",", ""));
		});
		
		$("#totDeductAmtSpan").text(comma(totDeductAmt));
		$("#totCancelAmtSpan").text(comma(totCancelAmt));
	}
</script>