<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : reserveStep1.jsp
  * @Description : 안내 및 주문 JSP
  * @Modification Information
  * @
  * @  수정일              수정자            수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.19    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.19
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<script>

$(document).ready(function() {
	
	$('#total_chk').click(function() {
		var chkBox = $(this);
		if (chkBox.is(':checked')) {
			$(".agree_list input[type='checkbox']").prop("checked", true);
		} else {
			$(".agree_list input[type='checkbox']").prop("checked", false);
		}
	}); 
	
	$('.agree_list.ty input[type="checkbox"] + label').click(function() {
		$(this).toggleClass('on');
	}); 

	$('.agree_list.ty li i').click(function() {
		$(this).toggleClass('on');
		$(this).parent().siblings('.agree_cont').stop().slideToggle();
	});

	$('.count').keyup(function() {
		var idx = $(this).data("idx");
		var itemCd = $(this).data("item");

		if ($(this).val() == "") {
			$(this).val("0");
		}

		if ( isNaN($(this).val())) {
			alert("숫자만 입력해주세요.");
			$(this).val("0");
		}

		fn_cnt_change(itemCd,  idx , 0, this, 'cus');
	});


	////setTimeout("fn_total_price_set()", 100); //historyback 시 금액 계산
	setTimeout("fn_login_check()", 100); //로그인여부 체크
	
	$(document).attr("title", "안내 및 주문 - 관람예약 | 노원수학문화관");

});

function fn_cnt_change(itemCd, index, cnt, obj) {
	
	var target        = "<c:out value='${param.target}'/>";
	var varMaxCnt     = Number("${baseDataVO.grpMaxCnt}");
	var varAllRscvRow = Number("0");
	var varPernCnt    = $("#personCnt").val();
	
	var grpMinCnt = Number("${baseDataVO.grpMinCnt}");
	var grpMaxCnt = Number("${baseDataVO.grpMaxCnt}");		

	console.log(target);
	console.log(varMaxCnt);
	console.log(varAllRscvRow);
	console.log(varPernCnt);
	
	if (itemCd == 'plus') {
		varPernCnt ++;

		if ((varMaxCnt - varAllRscvRow) < varPernCnt) {
			alert("현재 잔여정원은 " + (varMaxCnt - varAllRscvRow) + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
			return false;
		}

		$("#personCnt").val(varPernCnt);

	} else if (itemCd == 'minus') {

		varPernCnt --;

		if (varPernCnt <= 0) {
			alert("더는 줄일 수 없습니다");
			return false;
		}

		$("#personCnt").val(varPernCnt);

	} else if ((varMaxCnt - varAllRscvRow) < varPernCnt) {
		alert("현재 잔여정원은 " + (varMaxCnt - varAllRscvRow) + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
		//fn_total_price_set();
		return false;
	}

	//fn_total_price_set();
	return false;	
	
	/* 
	var target     = "<c:out value='${param.target}'/>";
	var grpMinCnt  = Number("${baseDataVO.grpMinCnt}");
	var grpMaxCnt  = Number("${baseDataVO.grpMaxCnt}");	
	var varMaxCnt  = grpMaxCnt;
	var varAllRscvRow = Number("0");
	var numFlag    = true;
	var numObj     =  $(obj).parent().find(".count");
	var txt        = numObj.val();
	var newTxt     = parseInt(txt) + cnt;
	var varPernCnt = $("#personCnt").val();
	
	////var prevCnt   = document.getElementById("chargeList"+index+".itemCnt").value;
	////var price     = document.getElementById("chargeList"+index+".price").value;

	var maxCnt = ${timeVO.totCapa-timeVO.reserveCnt};
	
	console.log("maxCnt = " + maxCnt);
	
	 
	if (type == "cus") {
		numObj = $(obj);
	}	
	
	
	var totalCnt  = Number($("#personCnt").val());
	
	console.log(totalCnt);
	console.log("newTxt = " + newTxt);
	
	if (newTxt <= 0) {
		alert("더 줄일 수 없습니다.");
		return false;
	}
	
	if (newTxt < 0) {
		
		//0보다 작을순 없음
		<c:if test="${timeVO.groupyn eq 'Y'}">
			} else if (target == "10" && totalCnt >= grpMinCnt && grpMinCnt > 0 && cnt >=0) {
				if (confirm("${baseDataVO.grpMinCnt} 명 이상 신청하실 경우 단체로 예약하셔야 합니다.\n단체 신청페이지로 이동하시겠습니까?")) {
					window.location.replace("./reserveStep1?target=20&exbtSeq=<c:out value="${param.exbtSeq}"/>&stdseq=<c:out value="${param.stdseq}"/>&timeseq=<c:out value="${param.timeseq}"/>&ymd=<c:out value="${param.ymd}"/>");
				}
			numFlag = false;
		</c:if>
	
	} else if (totalCnt > maxCnt) {
		alert("현재 잔여정원은 " + maxCnt + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
		numFlag = false;
	} else if (grpMaxCnt >0 && totalCnt > grpMaxCnt) {
		alert("예약가능 최대인원은  " + grpMaxCnt + "명입니다.\n"+grpMaxCnt+"명 이하로 인원을 선택해 주세요.");
		numFlag = false;
	} else {
		numObj.val(newTxt);
	}	

	if (numFlag) {

		if (itemCd == 'plus') {
			varPernCnt ++;

			if ((varMaxCnt - varAllRscvRow) < varPernCnt) {
				alert("현재 잔여정원은 " + (varMaxCnt - varAllRscvRow) + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
				return false;
			}

			$("#personCnt").val(varPernCnt);

		} else if (itemCd == 'minus') {

			varPernCnt --;

			if (varPernCnt <= 0) {
				alert("더는 줄일 수 없습니다");
				return false;
			}

			$("#personCnt").val(varPernCnt);

		} else if ((varMaxCnt - varAllRscvRow) < varPernCnt) {
			alert("현재 잔여정원은 " + (varMaxCnt - varAllRscvRow) + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
			//fn_total_price_set();
			return false;
		}		
		
	}
	*/
	
	/* 
	if (newTxt < 0) {
		
		//0보다 작을순 없음
		<c:if test="${timeVO.groupyn eq 'Y'}">
			} else if (target == "10" && totalCnt >= grpMinCnt && grpMinCnt > 0 && cnt >=0) {
				if (confirm("${baseDataVO.grpMinCnt} 명 이상 신청하실 경우 단체로 예약하셔야 합니다.\n단체 신청페이지로 이동하시겠습니까?")) {
					window.location.replace("./reserveStep1?target=20&exbtSeq=<c:out value="${param.exbtSeq}"/>&stdseq=<c:out value="${param.stdseq}"/>&timeseq=<c:out value="${param.timeseq}"/>&ymd=<c:out value="${param.ymd}"/>");
				}
			numFlag = false;
		</c:if>
	
	} else if (totalCnt > maxCnt) {
		alert("현재 잔여정원은 " + maxCnt + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
		numFlag = false;
	} else if (grpMaxCnt >0 && totalCnt > grpMaxCnt) {
		alert("예약가능 최대인원은  " + grpMaxCnt + "명입니다.\n"+grpMaxCnt+"명 이하로 인원을 선택해 주세요.");
		numFlag = false;
	} else {
		numObj.val(newTxt);
	}
	*/
	
	//총금액 셋팅
	/* 2021.05.19
	if (!numFlag ) {
		numObj.val(prevCnt);
	}
	*/
	
	////JYS 2021.05.19 fn_total_price_set();	
	
	/* 
	var maxCnt = ${timeVO.totCapa-timeVO.reserveCnt};

	if (type == "cus") {
		numObj = $(obj);
	}	
	
	
	var varMaxCnt     = Number("${evtData.evtOrgMaxcnt}");
	var varAllRscvRow = Number("0");
	var varPernCnt    = $("#personCnt").val();
	
	var grpMinCnt = Number("${evtData.evtOrgMincnt}");
	var grpMaxCnt = Number("${evtData.evtOrgMaxcnt}");		

	console.log(target);
	console.log(varMaxCnt);
	console.log(varAllRscvRow);
	console.log(varPernCnt);
	
	if (itemCd == 'plus') {
		varPernCnt ++;

		if ((varMaxCnt - varAllRscvRow) < varPernCnt) {
			alert("현재 잔여정원은 " + (varMaxCnt - varAllRscvRow) + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
			return false;
		}

		$("#personCnt").val(varPernCnt);

	} else if (itemCd == 'minus') {

		varPernCnt --;

		if (varPernCnt <= 0) {
			alert("더는 줄일 수 없습니다");
			return false;
		}

		$("#personCnt").val(varPernCnt);

	} else if ((varMaxCnt - varAllRscvRow) < varPernCnt) {
		alert("현재 잔여정원은 " + (varMaxCnt - varAllRscvRow) + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
		//fn_total_price_set();
		return false;
	}
	
	//fn_total_price_set();
	return false;
	*/

	return false;
}

/* 
function fn_cnt_change(itemCd , index, cnt , obj, type) {
	var target = "<c:out value="${param.target}"/>";
	var grpMinCnt = ${baseDataVO.grpMinCnt};
	var grpMaxCnt = ${baseDataVO.grpMaxCnt};
	var numFlag = true;
	var numObj =  $(obj).parent().find(".count");
	var txt = numObj.val();
	var newTxt = parseInt(txt) + cnt;
	var prevCnt = document.getElementById("chargeList"+index+".itemCnt").value;
	var price = document.getElementById("chargeList"+index+".price").value;


	var maxCnt = ${timeVO.totCapa-timeVO.reserveCnt};

	if (type == "cus") {
		numObj = $(obj);
	}

	//총 인원 체크
	var totalCnt  = 0;
	$.each($('.count') , function(idx , item) {
		var myCnt = document.getElementById("chargeList"+idx+".itemCnt").value;
		if (myCnt == "") myCnt = 0;
		totalCnt += parseInt(myCnt);
	});
	//alert(totalCnt);
	totalCnt = totalCnt+cnt;

	if (newTxt < 0) {
		//0보다 작을순 없음
<c:if test="${timeVO.groupyn eq 'Y'}">
	} else if (target == "10" && totalCnt >= grpMinCnt && grpMinCnt > 0 && cnt >=0) {
		if (confirm("${baseDataVO.grpMinCnt} 명 이상 신청하실 경우 단체로 예약하셔야 합니다.\n단체 신청페이지로 이동하시겠습니까?")) {
			window.location.replace("./reserveStep1?target=20&exbtSeq=<c:out value="${param.exbtSeq}"/>&stdseq=<c:out value="${param.stdseq}"/>&timeseq=<c:out value="${param.timeseq}"/>&ymd=<c:out value="${param.ymd}"/>");
		}
		numFlag = false;
</c:if>
	} else if (totalCnt > maxCnt) {
		alert("현재 잔여정원은 " + maxCnt + "명입니다.\n잔여정원 이하로 인원을 선택해 주세요.");
		numFlag = false;
	} else if (grpMaxCnt >0 && totalCnt > grpMaxCnt) {
		alert("예약가능 최대인원은  " + grpMaxCnt + "명입니다.\n"+grpMaxCnt+"명 이하로 인원을 선택해 주세요.");
		numFlag = false;
	} else {
		numObj.val(newTxt);
	}

	//총금액 셋팅
	if (!numFlag ) {
		numObj.val(prevCnt);
	}
	fn_total_price_set();
}
*/


function fn_total_price_set() {
	
	var totalPrice = 0;
	var totalDisc = 0;

	var updownUnit = "${optData.payUpdownUnit}";
	var updownType =  "${optData.payUpdown}";

	var remainCnt = 0;
	var limitQty = 0;
	if ($('#dcAnnualLimit').val() != "") {
		limitQty = parseInt($('#dcAnnualLimit').val()) ;
	}

	$.each($('.count') , function(idx , item) {
		
		/* 
		var price = document.getElementById("chargeList"+idx+".price").value;
		var orignCnt = document.getElementById("chargeList"+idx+".itemCnt").value;
		var discRate = document.getElementById("chargeList"+idx+".dcRate").value;
		var discAnnual = document.getElementById("chargeList"+idx+".dcAnnualRate").value;
		*/
		
		var price    = 0;
		var orignCnt = 0;
		var discRate = 0;
		var discAnnual = 0;		
		
		var discPrice = 0;
		var discPrice2 = 0;
		var itemCd = $(this).data("item");

		var newPrice = orignCnt * parseInt(price);
		var cnt = orignCnt;

		if ( discAnnual != "" && parseInt(discAnnual ) > 0 && limitQty == 0) {
			limitQty = 1000;
		}

		var annualCnt = limitQty - remainCnt;
		if (annualCnt > 0 && discAnnual != "" && parseInt(discAnnual ) > 0) {
			//유료회원할인을 먹인다
			if (annualCnt >= orignCnt) {
				annualCnt = orignCnt;
				cnt = 0;
			} else {
				cnt = orignCnt - annualCnt;
			}

			discPrice2 = fn_pay_updown(updownUnit,updownType,discAnnual , price,annualCnt);
			$('#DIV_PRICE_'+itemCd).find('.ex_disc_2 > dl > dd').text(numberWithCommas(discPrice2) + " 원");
			$('#DIV_AQTY_'+itemCd).text(annualCnt + "명 적용");

			totalDisc += discPrice2;
		} else if (discAnnual != "" && parseInt(discAnnual ) > 0) {
			$('#DIV_PRICE_'+itemCd).find('.ex_disc_2 > dl > dd').text("0 원");
			$('#DIV_AQTY_'+itemCd).text("0명 적용");
		}


		if ( discRate != "" && parseInt(discRate ) > 0) {
			discPrice = fn_pay_updown(updownUnit,updownType,discRate , price,cnt);
			$('#DIV_PRICE_'+itemCd).find('.ex_disc > dl > dd').text(numberWithCommas(discPrice) + " 원");
			$('#DIV_DQTY_'+itemCd).text(cnt + "명 적용");
			totalDisc += discPrice;
		}
		totalPrice += newPrice;
		remainCnt  += parseInt(orignCnt);
		$('#DIV_PRICE_'+itemCd).find('.ex_total > dl > dd').text(numberWithCommas(newPrice-discPrice-discPrice2) + " 원");
		$('#DIV_PRICE_'+itemCd).find('.ex_totalp > dl > dd').text(numberWithCommas(newPrice) + " 원");

	});

	$('#priceSum').text(numberWithCommas(totalPrice) + " 원");
	$('#priceTotal').text(numberWithCommas(totalPrice-totalDisc) + " 원");
	$('#priceDiscount').text(numberWithCommas(totalDisc) + " 원");


}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function fn_next() {
	
	var notCheckCnt = 0;
	$.each($('.agree_list.ty input[type="checkbox"]') , function(idx , item) {
		if (!$(item).prop("checked")) {
			notCheckCnt++;
		}
	});

	if (notCheckCnt > 0) {
		alert("약관을 확인하시고 모두 동의해주세요.");
		$('#total_chk').focus();
		return false;
	}
	
	$("#exbtVistnmpr").val($("#personCnt").val());
	$("#visitnum").val($("#personCnt").val());	
	
	var totalCnt    = Number($("#personCnt").val());
	var notCheckCnt = 0;
	var grpMinCnt = Number("${baseDataVO.grpMinCnt}");
	var grpMaxCnt = Number("${baseDataVO.grpMaxCnt}");

	var adultCnt = 0; //성인인원수
	var childCnt = 0; //유아인원수

	/* 2021.05.19 JYS  
	$.each($('.count') , function(idx , item) {
		var adultYn = document.getElementById("chargeList"+idx+".adultYn").value;
		var cnt = parseInt(document.getElementById("chargeList"+idx+".itemCnt").value,10);

		totalCnt += cnt;

		if (adultYn == "Y") {
			adultCnt += cnt;
		} else if (adultYn == "N") {
			childCnt += cnt;
		}
	});
	*/

	/* JYS 2021.05.19 	
	$.each($('.agree_list.ty input[type="checkbox"]') , function(idx , item) {
		if (!$(item).prop("checked")) {
			notCheckCnt++;
		}
	});
	*/
	
	var target = "<c:out value='${param.target}'/>";

	if (notCheckCnt > 0) {
		alert("예약유의사항 , 환불유의사항 , 개인정보 이용에 모두 동의후 예약하실 수 있습니다.");
		$('#total_chk').focus();
	} else if (totalCnt < 1) {
		alert("예약 인원을 선택해 주세요.");
	} else if (target == "20" && grpMinCnt > 0 && totalCnt < grpMinCnt) {
		alert("단체 신청 최소 인원수 [" + grpMinCnt + "]명 미만으로 예약하실 수 없습니다.");
	} else if (target == "20" && grpMaxCnt > 0 && totalCnt > grpMaxCnt) {
		alert("단체 신청 최대  인원수를 초과하여 예약하실 수 없습니다.");
	<c:if test="${timeVO.groupyn eq 'Y'}">
		} else if (target == "10" && grpMinCnt >0 && totalCnt >= grpMinCnt) {
			if (confirm("${baseDataVO.grpMinCnt} 명 이상 신청하실 경우 단체로 예약하셔야 합니다.\n단체 신청페이지로 이동하시겠습니까?")) {
				window.location.replace("./reserveStep1?target=20&exbtSeq=<c:out value="${param.exbtSeq}"/>&stdseq=<c:out value="${param.stdseq}"/>&timeseq=<c:out value="${param.timeseq}"/>&ymd=<c:out value="${param.ymd}"/>");
			}
	</c:if>
	<c:if test="${baseDataVO.exbtPartNm eq '꿈아띠체험관'}">
		} else if (adultCnt < Math.ceil(childCnt/6)) {
			alert("유아 예약 인원 6명당 성인 1명을 필수로 예약하셔야 합니다.\n( 현재 유아인원이 "+childCnt+"명 이므로 최소 성인 "+Math.ceil(childCnt/6)+"명이 필요합니다.)");
	</c:if>
	} else {
		document.rsvnMasterVO.submit();
	}
}

function fnAgreeOnClick(parmThis) {
	$(parmThis).parent().siblings('.agree_cont').stop().slideToggle();		
	return false;	
}

function fnAgreeOnKeyup(parmThis) {
	$(parmThis).toggleClass('on');
	$(parmThis).parent().parent().siblings('.agree_cont').stop().slideToggle();
	return false;	
}


function fn_rsvn_send() {

	var notCheckCnt = 0;
	$.each($('.agree_list.ty input[type="checkbox"]') , function(idx , item) {
		if (!$(item).prop("checked")) {
			notCheckCnt++;
		}
	});

	if (notCheckCnt > 0) {
		alert("약관을 확인하시고 모두 동의해주세요.");
		$('#total_chk').focus();
		return false;
	}
	
	$("#exbtVistnmpr").val($("#personCnt").val());
	$("#visitnum").val($("#personCnt").val());
	
	if (confirm("관람 정보를 예약합니다.")) {
		
		$.ajax({
			 
			type : "POST",
	        url  : './reserveAction',
	        data : $('#rsvnMasterVO').serialize(),
	        dataType: "json",
	        beforeSend : function(xhr) {
	        	xhr.setRequestHeader("${_csrf.headerName}",  "${_csrf.token}");
	        },
	        success: function(data) {
	        	
	        	////$('body').hideLoading();
	        	
	        	if (data != null) {
	        		if ((data.ERROR_CODE === undefined) || (data.ERROR_CODE == null) || (data.ERROR_CODE == '')) {
	        		} else {
	        			if (data.ERROR_CODE == -1) {
	            			alert(data.ERROR_MSG);
	            			return;
	        			} else if (data.ERROR_CODE == -3) {
	        				alert(data.ERROR_MSG);
							top.location.href = "/web/main";
	        				return;
	        			}
	        		}
	        	}
	        	fn_reserve_success(data);

	        }, error: function(jqXHR, exception) {
	        	$('body').hideLoading();
	        	var varStatus = jqXHR.status;

	        	if (varStatus == "403") {
	        		alert("접근 권한이 없습니다. 로그인을 다시 확인해 주세요.");
	        		top.location.replace("/web/main");
	        	} else {
	        		alert("시스템 오류 입니다 .");
	        	}
	        }
		});			
		
	}
	
}

	function fn_reserve_success(data) {
	
		if (data.result.resultCd == "SUCCESS") {
			
			$('#rsvnIdx').val( data.result.resultMsg );
	   	 	$('#rsvnMasterVO').attr("action" , "./reserveIndiResult");
	   	 	$('#rsvnMasterVO').submit();
	   	 
	   	} else {
			alert(data.result.resultMsg);
	   	}
		
	}

</script>

<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit"><c:out value="${SELECTED_MENU_OBJ.menuNm}"/></h3>
		<div class="join_wrap bor">
			<!-- step -->
			
			
			<c:if test="${param.target eq '10'}">
				<ul class="step step3">
					<li>
						<dl>
							<dt>1</dt>
							<dd>일자 선택</dd>
						</dl>
					</li>
					<li class="on">
						<!-- 현재 on -->
						<dl>
							<dt>2</dt>
							<dd>안내사항 동의 및 여부</dd>
						</dl>
					</li>
					<li>
						<dl>
							<dt>3</dt>
							<dd>예약 완료 안내</dd>
						</dl>
					</li>
				</ul>
			</c:if>			
			
			<c:if test="${param.target eq '20'}">
			
				<ul class="step step3">
					<li>
						<dl>
							<dt>1</dt>
							<dd>일자 선택</dd>
						</dl>
					</li>
					<li class="on">
						<!-- 현재 on -->
						<dl>
							<dt>2</dt>
							<dd>인원입력 및 동의</dd>
						</dl>
					</li>
					<li>
						<dl>
							<dt>3</dt>
							<dd>예약자 정보 입력</dd>
						</dl>
					</li>
					<li>
						<dl>
							<dt>4</dt>
							<dd>예약 완료 안내</dd>
						</dl>
					</li>
				</ul>				
			
			</c:if>
			
			<!-- 
			<ul class="step step4">
				<li>
					<dl>
						<dt>1</dt>
						<dd>회차 선택</dd>
					</dl>
				</li>
				<li class="on">
					<dl>
						<dt>2</dt>
						<dd>안내 및 주문</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>3</dt>
						<dd>정보 입력</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>4</dt>
						<dd><c:choose><c:when test="${baseDataVO.feeType eq '1001' }">결제 대기</c:when><c:otherwise>예약 완료</c:otherwise></c:choose></dd>
					</dl>
				</li>
			</ul>
			-->
			
			<!-- //step -->
			<!-- signup2 -->
			<div class="signup2">
			
				<p class="stit">선택된 관람 일정을 확인해 주십시오.</p>
				<div class="table2 responsive-type mb50">
				
					<table>
						<caption>관람 일정정보안내표입니다.</caption>
						<colgroup>
							<col style="width:20%">
							<col style="width:30%">
							<col style="width:20%">
							<col style="width:30%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop">프로그램명</th>
								<td class="b" colspan="3">
									<c:out value="${baseDataVO.exbtName}"/>
									<!-- 노원수학문화관 관람 예약 -->
								</td>
							</tr>
							<tr>
								<!-- <th scope="row" class="vtop">
									장소
								</th>
								<td class="b">
									노원수학문화관
								</td> -->
								<th scope="row" class="vtop">일자</th>
								<td class="b" colspan="3">
									<fmt:parseDate var="extYmd" value="${param.ymd}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
									<!-- 2021.08.17 -->
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">회차</th>
								<td class="b">
									<c:out value="${timeVO.timeName }"/>
									(${fn:substring(timeVO.sTime,0,2)}:${fn:substring(timeVO.sTime,2,4)} ~ ${fn:substring(timeVO.eTime,0,2)}:${fn:substring(timeVO.eTime,2,4)})									
									<!-- 2회차 (11:00 ~ 12:00) -->
								</td>
								<th scope="row" class="vtop">관람유형</th>
								<td class="b">
									<c:out value="${baseDataVO.exbtTypeNm}"/>
									<!-- 일반관람 -->
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">비용</th>
								<td class="b" colspan="3">무료</td>
							</tr>
						</tbody>
					</table>				
				
				
					<table style="display:none;">
						<caption>예약 정보 확인표입니다.</caption>
						<colgroup>
							<col style="width:20%">
							<col style="width:80%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop">관람 제목</th>
								<td class="b">
									<c:choose>
										<c:when test="${baseDataVO.exbtType eq '1001'}"><c:out value="${baseDataVO.exbtPartNm}"/> 관람</c:when>
										<c:otherwise><c:out value="${baseDataVO.exbtName}"/></c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">일시</th>
								<td class="b"><fmt:parseDate var="extYmd" value="${param.ymd}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
									&nbsp;&nbsp;${fn:substring(timeVO.sTime,0,2) }:${fn:substring(timeVO.sTime,2,4) } ~ ${fn:substring(timeVO.eTime,0,2) }:${fn:substring(timeVO.eTime,2,4) }
									<!-- (<c:out value="${timeVO.timeName }"/>) -->
								</td>
							</tr>
	
							<tr>
								<th scope="row" class="vtop">잔여정원</th>
								<td><fmt:formatNumber value="${timeVO.totCapa-timeVO.reserveCnt}"/>명</td>
							</tr>
							<c:if test="${param.target  eq '20' }">
								<tr>
									<th scope="row" class="vtop">단체 기준</th>
									<td> 최소 <fmt:formatNumber value="${baseDataVO.grpMinCnt}"/>명 ~ 최대  <fmt:formatNumber value="${baseDataVO.grpMaxCnt}"/>명</td>
								</tr>
							</c:if>
						</tbody>
					</table>
					<!-- //table -->
						
					<div class="board_ty">
						<ul>
							<li>
								<div class="info">
									<!-- info_tit -->
									<div class="info_tit">
										<span>참가인원(명) <em>관람에 참가할 인원을 입력해주세요</em></span>
									</div>
									<!-- //info_tit -->
									<!-- numcontrol -->
									<div class="numcontrol">
									
										<!-- 
										<input value="-" id="m" type="button" class="btn_minus" />
										<input type="text" class="count" value="1"  title="인원" />
										<input value="+" id="p" type="button" class="btn_plus" />
										-->
										
										<input type="button" class="btn_minus" onclick="fn_cnt_change('minus', 0,  -1, this)">
										<input id="personCnt" name="personCnt" class="count" value="1" type="text" autocomplete="false" title="참여 인원수 입력">
										<input type="button" class="btn_plus" onclick="fn_cnt_change('plus', 0,  1, this)">										
										
									</div>
									<!-- //numcontrol -->
								</div>
								<!-- //info -->
							</li>
						</ul>
					</div>
					<!-- //board_ty -->
					<!-- calc -->
					<div class="calc mb70">
						<div class="cal2">
							<dl>
								<dt>결제 금액</dt>
								<dd class="pink">무료</dd>
							</dl>
						</div>
					</div>			
				
					<p class="stit">관람 신청 시 유의사항에 동의해 주시기 바랍니다.</p>
					<!-- agree_list -->
					<ul class="agree_list ty mb70">
						<c:forEach items="${termsList}" var="item" varStatus="stat">
							<c:choose>
								<c:when test="${baseDataVO.feeType ne '1001' && item.stplatId eq '2002'}"></c:when>
								<c:otherwise>
									<li>
										<div class="agree_tit">
											<input type="checkbox" id="c${stat.index+1 }"/>
				                        	<label for="c${stat.index+1}" class="on"><c:out value="${item.stplatName}"/></label>
				                        	
											<!-- <i class="on">내용보기</i> -->
											<a href="#" onclick="fnAgreeOnClick(this); return false;" tabindex="-1"><i class="on" onkeyup="fnAgreeOnKeyup(this); return false;" tabindex="0">내용보기</i></a>
											
										</div>
										<div class="agree_cont">
											<c:out value="${fn:replace(item.stplatCn, crlf , '<br>')}" escapeXml="false" />
										</div>
									</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
					<!-- //agree_list -->
									
					<!-- 
					<ul class="agree_list ty">
						<li>
							<input type="checkbox" id="c1">
							<label for="c1">(필수) 예약유의사항</label>
							<div class="agree_cont">
								<p>* 주말 및 공휴일 혼잡 안내</p>
								<p class="sub_p">- 주말 및 공휴일 12시 이후부터는 입장 대기시간이 1시간 이상 소요 될 수 있으니 쾌적한 관람을 원하시는 분들께서는 평일에 방문해 주시길 바랍니다.</p>
							</div>
						</li>
					</ul>
					-->					
					
				</div>				
			
			
			
				<!-- 
				<ul class="agree_choice ty">
					<li>
						<input type="checkbox" id="total_chk">
						<label for="total_chk">
						<c:choose>
								<c:when test="${baseDataVO.feeType eq '1001' }"> 예약 유의사항, 환불 유의사항, 개인정보 이용동의에 모두 동의합니다.</c:when>
								<c:otherwise> 예약 유의사항, 개인정보 이용동의에 모두 동의합니다.</c:otherwise>
							</c:choose></label>
					</li>
				</ul>
				-->


				<!-- 
				<p class="stit mb5">예약 인원 선택 <span class="tb_txt">※ 쿠폰 또는 기타 할인은 결제 화면에서 변경하실 수 있습니다.</span></p>
				-->
				
				<c:if test="${rsvnMasterVO.dcAnnualLimit > 0}">
					<p class="desc mb5" style="text-align:left">유료회원 할인은 <span class="pink">최대 ${rsvnMasterVO.dcAnnualLimit }명</span> 까지 가능합니다.</p>
				</c:if>
				
				<!-- board_ty -->
				<form:form commandName="rsvnMasterVO" name="rsvnMasterVO" id="rsvnMasterVO" action="./reserveStep2" method="post">
				
					<form:hidden path="target" />
					<form:hidden path="exbtSeq" />
					<form:hidden path="timeseq" />
					<form:hidden path="stdseq" />
					<form:hidden path="ymd" />
					<form:hidden path="dcAnnualLimit" />
					<form:hidden path="exbtVistnmpr" />
					
					<form:hidden path="memType" />
					<form:hidden path="hpcertno" />
					<form:hidden path="rsvnIdx" />
					<form:hidden path="memNo" />
					<form:hidden path="webId" />
					<form:hidden path="custNm" />
					<form:hidden path="exbtHp" />
					<form:hidden path="exbtEmail" />
					<form:hidden path="visitnum" />
					
								<div class="board_ty">
									<ul>
					<c:forEach items="${rsvnMasterVO.chargeList}" var="item" varStatus="s">
						<form:hidden path="chargeList[${s.index}].itemCd" value="${item.itemCd}"/>
						<form:hidden path="chargeList[${s.index}].price" value="${item.price}"/>
						<form:hidden path="chargeList[${s.index}].itemNm" value="${item.itemNm}"/>
						<form:hidden path="chargeList[${s.index}].dcKindCd" value="${item.dcKindCd}"/>
						<form:hidden path="chargeList[${s.index}].dcType" value="${item.dcType}"/>
						<form:hidden path="chargeList[${s.index}].dcRate" value="${item.dcRate}"/>
						<form:hidden path="chargeList[${s.index}].dcAnnualRate" value="${item.dcAnnualRate}"/>
						<form:hidden path="chargeList[${s.index}].adultYn" value="${item.adultYn}"/>
							<li>
								<div class="info">
									<!-- info_tit -->
									<div class="info_tit">
										<span><c:out value="${item.itemNm}" /><em> <fmt:formatNumber value="${item.price}"/>원</em></span>
									</div>
									<!-- //info_tit -->
									<!-- numcontrol -->
									<div class="numcontrol num01">

										<!-- 
										<input type="button" title="${item.itemNm} 예약인원수제거" class="btn_minus" onclick="fn_cnt_change('<c:out value="${item.itemCd}"/>' , ${s.index} ,  -1 , this)"/>
										<form:input path="chargeList[${s.index}].itemCnt" class="count" value="0" data-idx="${s.index}" data-item="${item.itemCd}" autocomplete="false" title="${item.itemNm} 예약인원수 입력"/>
										<input type="button" title="${item.itemNm} 예약인원수추가" class="btn_plus" onclick="fn_cnt_change('<c:out value="${item.itemCd}"/>' , ${s.index} ,  1 , this)"/>
										-->
										
									</div>
									<!-- //numcontrol -->
								</div>
								<!-- //info -->
							<c:choose>
							<c:when test="${baseDataVO.feeType eq '2001'}">
									<!-- info_price -->
								<ul class="info_price" id="DIV_PRICE_${item.itemCd }">
									<li class="ex_totalp">
										<dl >
											<dt>총 금액</dt>
											<dd>0 원</dd>
										</dl>
									</li>
								</ul>
								<!-- //info_price -->
								</c:when>
								<c:otherwise>
								<!-- info_price -->
								<ul class="info_price" id="DIV_PRICE_${item.itemCd }">
									<li class="ex_totalp">
										<dl >
											<dt>총 금액</dt>
											<dd>0 원</dd>
										</dl>
									</li>
									<c:if test="${item.dcAnnualRate > 0 and item.price>0}">
										<li class="ex_disc_2">
											<dl>
												<dt>유료회원 (<c:out value="${item.dcAnnualRate}"/>% 할인)<em id="DIV_AQTY_${item.itemCd }">0명 적용</em></dt>
												<dd>0 원</dd>
											</dl>
										</li>
									</c:if>
									<c:if test="${item.dcRate > 0 and item.price>0}">
										<li class="ex_disc">
											<dl>
												<dt><c:out value="${item.dcName}"/> (<c:out value="${item.dcRate}"/>% 할인)<em id="DIV_DQTY_${item.itemCd }">0명 적용</em></dt>
												<dd>0 원</dd>
											</dl>
										</li>
									</c:if>
									<li class="ex_total">
										<dl>
											<dt>결제 예상 금액</dt>
											<dd>0 원</dd>
										</dl>
									</li>
								</ul>
								<!-- //info_price -->
								</c:otherwise>
							</c:choose>
							</li>
						</c:forEach>

						</ul>
					</div>
				</form:form>
				
				<!-- //board_ty -->
				<!-- calc -->
				<c:if test="${baseDataVO.feeType eq '1001' }">
				<div class="calc">
					<div class="cal1">
						<dl>
							<dt>총 금액</dt>
							<dd id="priceSum">0 원</dd>
						</dl>
						<span><img src="<%=strContext%>/images/sub/ico_minus.png" alt="빼기아이콘"></span>
						<dl>
							<dt>할인 예상 금액</dt>
							<dd id="priceDiscount">0 원</dd>
						</dl>
					</div>
					<div class="cal2">
						<span><img src="<%=strContext%>/images/sub/ico_total.png" alt="전체금액아이콘"></span>
						<dl>
							<dt>결제 예정 금액</dt>
							<dd class="pink" id="priceTotal">무료</dd>
						</dl>
					</div>
				</div>

				</c:if>
				<!-- //calc -->
			</div>
			<!-- //signup2 -->
		</div>
		<!-- //join_wrap -->
		<ul class="btn_area">
		
			<c:if test="${param.target eq '10'}">
		
				<li><a href="javascript:history.back()" class="btn_ty_m_c4">이전페이지 이동</a></li>
				<li><a href="javascript:fn_rsvn_send()" class="btn_ty_m_c1">예약하기</a></li>
				
			</c:if>
			
			<c:if test="${param.target eq '20'}">
		
				<li><a href="javascript:history.back()" class="btn_ty_m_c4">이전페이지 이동</a></li>
				<li><a href="javascript:fn_next()" class="btn_ty_m_c1">다음단계</a></li>
				
			</c:if>			
			
		</ul>
		<!-- //btn_area -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>