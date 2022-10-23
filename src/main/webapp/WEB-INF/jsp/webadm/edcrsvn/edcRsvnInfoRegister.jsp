<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<style type="text/css">
		th {background-color:#f4f6f9;}
	</style>
	<script src="${requestScope.resourceContext}/js/jquery.dataTables.min.js"></script>
	<script>
		$(document).ready(function(){
			$('.datepicker').datepicker({
		        autoclose: true,
		        todayHighlight: true,
		        minDate: 0,
		        showOn:"button",
		        buttonImage:"${requestScope.resourceContext}/images/ico_select_date.png",
		        buttonImageOnly:true
		    });

			fn_change_memberGbn();
			$("#memberGbn").change(function() {
				$("#edcRsvnMemno").val("");
				$("#edcRsvnCustnm").val("");
				$("#edcRsvnMoblphon").val("");
				$("#edcRsvnBirthdate").val("");
				$("#birthSec").val("S");
				$("#edcRsvnGender").val("2");
				if ($(this).val() == "NONMEMBER") {
					$("#edcRsvnCustnm").focus();
				} else {
					$("#searchMemberBtn").trigger("click");
					setTimeout(() => $("#searchKeyword").focus(), 500);
				}
				fn_change_memberGbn();
				fn_init_dc_params();
			});

			$("#edcRsvnBirthdate").inputmask("yyyy-mm-dd");

			$("#byCash").click(function() {
				$(".paymethod").removeClass("btn-success");
				$(this).addClass("btn-success");
				$("#payMethod").val("CASH");
			});
			$("#byCard").click(function() {
				$(".paymethod").removeClass("btn-success");
				$(this).addClass("btn-success");
				$("#payMethod").val("CARD");
			});

			$("#rsvnDate").change(function () {
				fn_set_card_layer();
			});

			fn_set_card_layer();
		});

		//회원,비회원선택
		function fn_change_memberGbn() {
			var memberGbn = $("#memberGbn").val();
			if (memberGbn == "MEMBER") {
				$("input." + memberGbn).prop({"readonly":true});
				$("select." + memberGbn).prop({"disabled":true});
				$("button." + memberGbn).prop({"disabled":false});
			} else {
				$("input." + memberGbn).prop({"readonly":false});
				$("select." + memberGbn).prop({"disabled":false});
				$("button." + memberGbn).prop({"disabled":true});
			}
		}

		function fn_get_dc_list() {
			if ("Y" == "${detailVO.exclDcyn}") {
				alert("감면적용 비대상 강좌입니다.");
				return;
			}

			var memnm = $("#edcRsvnCustnm").val();
			var memhp = $("#edcRsvnMoblphon").val();
			var memgender = $("#edcRsvnGender").val();

			if (!memnm || !memhp || !memgender) {
				alert("먼저 회원선택 혹은 회원정보를 입력해 주세요.");
				return;
			}

			$("#dcMemnmTd").text(memnm);
			$("#dcMemIdTd").text($("#idTd").text());
			$("#dcMemGenderTd").text(fn_get_dender_nm(memgender));
			$("#dcMemHpTd").text(memhp);
			var birthdate = $("#edcRsvnBirthdate").val();
			var birthSec = $("#birthSec :checked").text();
			$("#dcMemBirthdateTd").text(birthdate + " (" + birthSec + ")");
			$("#dcMemJoindateTd").text($("#tmpJoindate").val());

			var param = [];
			param.push("comcd=" + $("#comcd").val());
			param.push("orgNo=" + $("#orgNo").val());
			param.push("memNo=" + $("#edcRsvnMemno").val());

			$.get({
			    url : './edcMemberDcListAjax',
			    data : param.join("&"),
				dataType : 'html',
				success: function(data) {
			    	$("#modalDcDiv").html(data);
			    	$("#applyDcBtn").trigger("click");
				}
		    });
		}

		function fn_get_dender_nm(cd) {
			if (cd == "0") return "미기재";
			else if (cd == "1") return "남성";
			else if (cd == "2") return "여성";
			else if (cd == "3") return "성별미상";
			else if (cd == "9") return "기타";
		}

		function fn_init_dc_params() {
			$("#edcReasondc").val("");
			$("#edcReasondcNm").val("");
			$("#dcRate").val(0);
			$("#edcDcamt").val(0);
			fn_refresh_pay_amt();
		}

		function fn_refresh_pay_amt() {
			var saleAmt = ${detailVO.salamt}; //프로그램금액
			var dcAmt = 0; //할인금액
			var payAmt = saleAmt; //결제금액

			var dcRate = $("#dcRate").val();
			if (!$.isNumeric(dcRate)) {
				dcRate = 0;
			}
			if (dcRate > 100) {
				dcRate = 100;
			}
			dcRate = dcRate/100;

			dcAmt = Math.round(Math.round(saleAmt * dcRate)/10)*10;
			payAmt = saleAmt - dcAmt;

			$("#edcTotamtSpan").text(comma(payAmt));

			$("#edcDcamt").val(dcAmt);
			$("#edcTotamt").val(payAmt);

			var dcDesc = comma($("#edcDcamt").val()) + " 원";
			if ($("#edcDcamt").val() > 0) {
				dcDesc = "<font color='red'>-" + dcDesc + "</font>";
				dcDesc +=  " (" + $("#edcReasondcNm").val() + " " + $("#dcRate").val() + "%)";
			}
			$("#edcDcamtDiv").html(dcDesc);

			$("#payMethod").val("");
			$(".paymethod").removeClass("btn-success");

			fn_set_card_layer();
		}

		//수강등록
		function fn_rsvn_register(rsvnStat) {
			if ($("#edcRsvnMemno").val()) {
			} else {
				if (!$("#edcRsvnCustnm").val()) {
					alert("회원이름을 입력해 주세요.");
					$("#edcRsvnCustnm").focus();
					return;
				};
				if (!$("#edcRsvnMoblphon").val()) {
					alert("휴대폰번호를 입력해 주세요.");
					$("#edcRsvnMoblphon").focus();
					return;
				};
				if (!$("#edcRsvnBirthdate").val()) {
					alert("회원이름을 입력해 주세요.");
					$("#edcRsvnBirthdate").focus();
					return;
				};
				if (!$("#edcRsvnGender").val()) {
					alert("성별을 선택해 주세요.");
					$("#edcRsvnGender").focus();
					return;
				};
			}

			if (rsvnStat == "4001") {
				if (!$("#payMethod").val()) {
					alert("결제방법을 선택해 주세요.");
					return;
				}

				if ($("#payMethod").val() == "CARD") {
					if (!$("#certNo").val()) {
						alert("승인번호를 입력해 주세요.")
						$("#cardInputBtn").trigger("click");
						$("#certNo").focus();
						return;
					}
				}
			}

			var data = {};
			var member = {};
			var itemList = [];
			var item = {};
			var payment = {};

			member.memNo = $("#edcRsvnMemno").val();
			member.memNm = $("#edcRsvnCustnm").val();
			member.memHp = $("#edcRsvnMoblphon").val();
			member.memBirthdate = $("#edcRsvnBirthdate").val();
			//member.memBirthSec = $("#birthSec").val();
			member.memGender = $("#edcRsvnGender").val();

			item.comcd = "${detailVO.comcd}";
			item.orgNo = ${detailVO.orgNo};
			item.itemCd = "${detailVO.itemCd}";
			item.costAmt = ${detailVO.costAmt};
			item.salamt = ${detailVO.salamt};
			item.monthCnt = ${detailVO.monthCnt};
			item.dcAmt = $("#edcDcamt").val();
			item.discountCd = $("#edcReasondc").val();
			item.discountRate = $("#dcRate").val();
			item.edcPrgmNo = ${detailVO.edcPrgmNo};
			item.edcRsvnsetSeq = ${detailVO.edcRsvnsetSeq};
			itemList.push(item);

			payment.onoff = "20"; //10:온라인, 20:오프라인
			payment.registGbn = "1001"; //신규
			payment.dcAmt = item.dcAmt;
			payment.terminalType = "1001"; //SM_TERMINAL_TYPE_현장PC
			payment.payAmt = item.salamt - item.dcAmt;
			payment.cashAmt = ($("#payMethod").val() == "CASH" ? payment.payAmt : 0);
			payment.cardAmt = ($("#payMethod").val() == "CARD" ? payment.payAmt : 0);
			payment.payComcd = "NOWON";
			payment.payMethod = $("#payMethod").val();
			if (payment.payMethod == "CARD") {
				payment.financeCd = $("#cardCd :selected").val();
				payment.financeNm = $("#cardCd :selected").text();
				payment.certNo = $("#certNo").val();
			} else {
				payment.financeCd = "CH";
				payment.financeNm = "현금";
			}
			payment.resultCd = "0000";
			payment.resultMsg = "결제성공";

			data.rsvnStat = rsvnStat;
			data.member = member;
			data.itemList = itemList;
			data.payment = payment;

			$.post({
			    url : './edcRsvnInfoRegister.json',
			    data : JSON.stringify(data),
			    contentType : "application/json; charset=UTF-8",
				success: function(data) {
			    	alert(data.result.msg);
			    	if (data.result.code == "SUCCESS") {
			    		$("#modal-card").find(".close").trigger("click");
			    		var summary = data.paySummary;
			    		$("#receiptNoTd").text(summary.receiptNo);
			    		$("#oidTd").text(summary.oid);
			    		$("#certNoTd").text(summary.appNo);
			    		var dd = summary.payDate;
			    		var payDate = dd.substring(0,4)+"-"+dd.substring(4,6) + "-" + dd.substring(6,8);
			    		var tt = summary.payTime;
			    		var payTime = tt.substring(0,2) + ":" + tt.substring(2,4) + ":" + tt.substring(4,6);
			    		$("#payDtimeTd").text(payDate + " " + payTime);
			    		$("#payAmtTd2").text(comma(summary.payAmt) + " 원");
			    	} else {
			    	}		
				}
		    });
		}

		function fn_set_card_layer() {
			if ($("#edcTotamt").val() < 1) {
				$(".paymethod").removeClass("btn-success");
				$(".paymethod").prop("disabled", true);
				$("#payMethod").val("CASH");
				$("#financeCd").val("");
				$("#financeNm").val("");
				$("#certNo").val("");
			} else {
				$(".paymethod").prop("disabled", false);
			}

			var dcDesc = comma($("#edcDcamt").val()) + " 원";
			if ($("#edcDcamt").val() > 0) {
				dcDesc = "<font color='red'>-" + dcDesc + "</font>";
				dcDesc +=  " (" + $("#edcReasondcNm").val() + " " + $("#dcRate").val() + "%)";
			}
			$("#saleAmtTd").text(comma("${detailVO.salamt}") + " 원");
			$("#dcAmtTd").html(dcDesc);
			$("#payAmtTd").text(comma($("#edcTotamt").val()) + " 원");
			$("#payDateTd").text($("#rsvnDate").val());
		}

		function fn_input_card_info() {
			if ($("#edcTotamt").val() < 1) {
				alert("결제할 금액이 없습니다.");
				return;
			}
			$("#cardInputBtn").trigger("click");
		}
	</script>
</head>
<body>
	<c:set var="przwinYn" value="N"/><!-- 추첨제여부 -->
	<c:if test="${detailVO.edcRsvnRectype eq '2001'}">
		<c:set var="przwinYn" value="Y"/>
	</c:if>

	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1><c:choose><c:when test="${param.source eq 'wait'}">배정대기 등록</c:when><c:otherwise>현장등록</c:otherwise></c:choose></h1>
	          </div>
	          <div class="col-sm-6" style="text-align:right">
	          		<a class="btn btn-secondary btn-sm btn-flat" href="javascript:history.back()">이전화면</a>
	          </div>
	        </div>
	      </div>
	</section>

	<!-- Main content -->
	<section class="content">

      <div class="container-fluid">

		<form:form commandName="searchVO" name="searchVO" method="get">
			<!--  edc_rsvn_info, edc_rsvn_comptinfo -->
			<form:hidden path="comcd" />
			<form:hidden path="orgNo" />
			<form:hidden path="edcPrgmNo" />
			<input type="hidden" name="edcRsvnMemno" id="edcRsvnMemno" value=""/>
			<input type="hidden" name="edcProgmCost" id="edcProgmCost" value="${detailVO.salamt}" />
			<input type="hidden" name="edcDcamt" id="edcDcamt" value="0"/>
			<input type="hidden" name="edcTotamt" id="edcTotamt" value="${detailVO.salamt}" />
			<input type="hidden" name="edcReasondc" id="edcReasondc" value=""/>
			<input type="hidden" name="edcReasondcNm" id="edcReasondcNm" value=""/>
			<input type="hidden" name="dcRate" id="dcRate" value="0"/>
			<input type="hidden" name="payMethod" id="payMethod" value=""/>

			<input type="hidden" name="tmpJoindate" id="tmpJoindate" value=""/>

			<!-- selng_info(통합매출), pay_list(결제내역) -->

		<div class="card card-primary card-outline">
       	  <div class="card-body">
	 	 	<div class="col-12">
	              <!-- /.card-header -->
                   <table class="table table-bordered text-nowrap">
	               	<colgroup>
						<col style="width: 20%;">
						<col style="width: 30%;">
						<col style="width: 20%">
						<col style="width: *">
					</colgroup>

	                <tbody>
						<tr>
							<th>신청기관명</td>
							<td>${detailVO.orgName}</td>
							<th>신청강좌명</td>
							<td>${detailVO.edcPrgmnm}</td>
						</tr>
						<tr>
							<th>모집차수</td>
							<td>${detailVO.edcRsvnsetSeq}</td>
							<th>모집차수명</td>
							<td>${detailVO.edcOdrNm}</td>
						</tr>
						<tr>
							<th>접수방식</td>
							<td>${detailVO.edcRsvnRectypeNm}</td>
							<th>접수경로</td>
							<td>${detailVO.edcRsvnAccssrdNm}</td>
						</tr>
						<tr>
							<th>접수기간</td>
							<td>${detailVO.edcRsvnPeriod}</td>
							<th>교육기간</td>
							<td>${detailVO.edcPeriod}</td>
						</tr>
						<tr>
							<th>교육요일</td>
							<td>${detailVO.edcDaygbnNm}</td>
							<th>교육시간</td>
							<td>${detailVO.edcTime}</td>
						</tr>
						<tr>
							<th>수강료</td>
							<td>
							<c:choose>
								<c:when test="${detailVO.salamt > 0}">
								<fmt:formatNumber value="${detailVO.salamt}" pattern="#,###"/> 원
								</c:when>
								<c:otherwise>무료</c:otherwise>
							</c:choose>
							</td>
							<th>비회원 접수여부</td>
							<td>비회원 접수<c:choose><c:when test="${detailVO.rsvnNonmebyn eq 'N'}">불가</c:when><c:otherwise>가능</c:otherwise></c:choose></td>
						</tr>
						<tr>
							<th>총신청인원/모집인원</td>
							<td>${detailVO.statApplyCnt}명 / ${detailVO.edcPncpa}명</td>
							<th><c:if test="${detailVO.edcRsvnRectype eq '1002' }">대기정원</c:if></td>
							<td><c:if test="${detailVO.edcRsvnRectype eq '1002' }"><fmt:formatNumber value="${detailVO.edcEndwaitCapa}" pattern="#,###"/></c:if></td>
						</tr>
						<c:if test="${detailVO.edcCapaDvdyn eq 'Y' }">
						<tr>
							<th>온라인/방문 인원/정원</td>
							<td>온라인: ${detailVO.statApplyOnCnt}명 / ${detailVO.edcOncapa}명, 방문: ${detailVO.statApplyOffCnt}명 / ${detailVO.edcOffcapa}명</td>
							<th></td>
							<td></td>
						</tr>
						</c:if>
	                </tbody>
	              </table>
	        </div>

	 	 	<div class="col-12">
	 	 		<h6>[수강신청 회원정보]</h6>
	 	 	</div>
	 	 	<div class="col-12">
	              <!-- /.card-header -->
                   <table class="table table-bordered text-nowrap">
	               	<colgroup>
						<col style="width: 15%;">
						<col style="width: 35%;">
						<col style="width: 15%">
						<col style="width: *">
					</colgroup>

	                <tbody>
						<tr>
							<th>회원명<span class="pilsu">*</span></td>
							<td>
								<select id="memberGbn" name="memberGbn" title="시간선택" class="form-control select" style="width:130px;float:left;margin-right:2px">
								 <option value="MEMBER" selected="selected">회원검색</option>
								 <c:if test="${detailVO.rsvnNonmebyn eq 'Y'}">
								 <option value="NONMEMBER">비회원등록</option>
								 </c:if>
								</select>
								<input type="text" class="form-control MEMBER NONMEMBER" id="edcRsvnCustnm" name="edcRsvnCustnm" maxlength="20" validate="required;" style="width:100px;float:left" placeholder="회원명"/>
								<button type="button" class="btn btn-secondary btn-flat MEMBER NONMEMBER" style="margin-left:10px;margin-top:3px" data-toggle="modal" data-target="#modal-member" id="searchMemberBtn">찾기</button>
								<button type="button" class="btn btn-success btn-sm btn-flat MEMBER NONMEMBER" style="margin-left:5px;margin-top:3px" data-toggle="modal" data-target="#modal-memberjoin" id="searchMemberBtn">방문회원등록</button>
							</td>
							<th>ID</td>
							<td id="idTd"></td>
						</tr>
						<tr>
							<th>휴대폰번호<span class="pilsu">*</span></td>
							<td><input type="text" class="form-control MEMBER NONMEMBER" id="edcRsvnMoblphon" name="edcRsvnMoblphon" placeHolder="휴대폰번호" maxlength="20" validate="required;" style="width:180px;float:left"/></td>
							<th>생년월일<span class="pilsu">*</span></td>
							<td>
								<input type="text" class="form-control MEMBER NONMEMBER" id="edcRsvnBirthdate" name="edcRsvnBirthdate" maxlength="20" validate="required;" style="width:120px;float:left;margin-right:2px" placeHolder="yyyy-mm-dd"/>
								<select id="birthSec" name="birthSec" title="음양선택" class="form-control select MEMBER NONMEMBER" style="width:110px;float:left;margin-right:2px">
								 <option value="S" selected="selected">양력</option>
								 <option value="M">음력</option>
								</select>
								<select id="edcRsvnGender" name="edcRsvnGender" title="성별선택" class="form-control select MEMBER NONMEMBER" style="width:110px;float:left;margin-right:2px">
								${hisco:makeOptions("SM_GENDER_GBN", "EMPTY", "2")}
								</select>
							</td>
						</tr>
	                </tbody>
	              </table>

	              <!-- /.card-header -->
                   <table class="table table-bordered text-nowrap">
	               	<colgroup>
						<col style="width: 20%;">
						<col style="width: 30%;">
						<col style="width: 20%">
						<col style="width: *">
					</colgroup>

	                <tbody>
						<tr>
							<th>수강료</td>
							<td>
							<b>
							<c:choose>
								<c:when test="${detailVO.salamt > 0}">
								<fmt:formatNumber value="${detailVO.salamt}" pattern="#,###"/> 원
								</c:when>
								<c:otherwise>무료</c:otherwise>
							</c:choose>
							</b>
							</td>
							<th>영수증번호</td>
							<td id="receiptNoTd"></td>
						</tr>
						<tr>
							<th>할인금액</td>
							<td>
								<div id="edcDcamtDiv" style="width:63px;float:left;text-align:right;padding-top:5px"><span id="edcDcamtSpan">0</span> 원</div>
								<c:if test="${detailVO.salamt > 0}">
									<c:choose>
										<c:when test="${detailVO.exclDcyn eq 'Y'}">
											<button type="button" class="btn btn-secondary btn-sm btn-flat" style="margin-left:50px;" disabled="disabled">감면적용 제외강좌</button>
										</c:when>
										<c:otherwise>
											<button type="button" class="btn btn-success btn-sm btn-flat" style="margin-left:160px;" onclick="fn_get_dc_list()">할인적용</button>
											<button type="button" class="btn btn-success btn-sm btn-flat" style="display:none" data-toggle="modal" data-target="#modal-dc" id="applyDcBtn">할인적용</button>
										</c:otherwise>
									</c:choose>
								</c:if>
							</td>
							<th>주문번호(OID)</td>
							<td id="oidTd"></td>
						</tr>
						<tr>
							<th>결제예상금액</td>
							<td>
							<b>
							<c:choose>
								<c:when test="${detailVO.salamt > 0}">
									<span id="edcTotamtSpan"><fmt:formatNumber value="${detailVO.salamt}" pattern="#,###"/></span> 원
								</c:when>
								<c:otherwise>무료</c:otherwise>
							</c:choose>
							</b>
							</td>
							<th>승인번호</td>
							<td id="certNoTd"></td>
						</tr>
						<tr>
							<th>결제일자</td>
							<td>
							<c:if test="${przwinYn eq 'N'}">
								<input id="rsvnDate" name="rsvnDate" style="width:110px;float:left" readonly="readonly" class="form-control datepicker" type="text" value="${today}" maxlength="10" autocomplete="off"/>
							</c:if>
							</td>
							<th>결제일시</td>
							<td id="payDtimeTd"></td>
						</tr>
						<tr>
							<th>결제방법</td>
							<td>
							<c:if test="${przwinYn eq 'N'}">
							<c:choose>
								<c:when test="${detailVO.salamt > 0}">
									<!-- <button type="button" class="btn btn-secondary btn-sm btn-flat paymethod" style="margin-left:10px;margin-top:3px" id="byCash">현금결제</button> -->
									<button type="button" class="btn btn-secondary btn-sm btn-flat paymethod" style="margin-left:10px;margin-top:3px" id="byCard" onclick="fn_input_card_info()">카드(전표입력)결제</button>
									<button type="button" class="btn btn-success btn-sm btn-flat" style="display:none" data-toggle="modal" data-target="#modal-card" id="cardInputBtn">할인적용</button>
								</c:when>
								<c:otherwise>
								 <b>무료</b>
								</c:otherwise>
							</c:choose>
							</c:if>
							</td>
							<th>결제금액</td>
							<td id="payAmtTd2"></td>
						</tr>
	                </tbody>
	              </table>
	        </div>
	       </div>

 	   	</div>
     	</form:form>

 	   <div class="row" style="margin-bottom:5px;">
			 <div class="col-12" style="text-align:center">
			 	<button type="button" class="btn btn-secondary btn-sm btn-flat" onclick="history.back()">이전화면</button>
			 	<c:if test="${source eq 'add'}">
			 		<c:choose>
			 			<c:when test="${detailVO.edcRsvnRectype eq '2001'}"> <!-- 2001:추첨대기 -->
			 				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" class="btn btn-info btn-sm btn-flat" onclick="fn_rsvn_register('1002')">추첨대기등록</button>
			 			</c:when>
			 			<c:otherwise>
			 				<c:if test="${detailVO.salamt < 1}">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-info btn-sm btn-flat" onclick="fn_rsvn_register('4001')">수강신청등록</button>
							</c:if>
							<c:if test="${detailVO.salamt > 0}">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-info btn-sm btn-flat" onclick="fn_rsvn_register('4001')">수강신청등록</button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-info btn-sm btn-flat" onclick="fn_rsvn_register('2001')">입금대기등록</button>
							</c:if>
			 			</c:otherwise>
			 		</c:choose>
				</c:if>
				<c:if test="${source eq 'wait' and detailVO.edcRsvnRectype eq '1002'}"> <!-- 1002:선착마감대기 -->
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" class="btn btn-info btn-sm btn-flat" onclick="fn_rsvn_register('1000')">배정대기등록</button>
				</c:if>
			</div>
       </div>


     </div>

     <%@ include file="/WEB-INF/jsp/webadm/edcrsvn/modalMemberLayer.jsp" %>
     <%@ include file="/WEB-INF/jsp/webadm/edcrsvn/modalMemberDcListLayer.jsp" %>
     <%@ include file="/WEB-INF/jsp/webadm/edcrsvn/modalCardLayer.jsp" %>
     <%@ include file="/WEB-INF/jsp/webadm/edcrsvn/modalMemberJoinLayer.jsp" %>
</section>
</body>