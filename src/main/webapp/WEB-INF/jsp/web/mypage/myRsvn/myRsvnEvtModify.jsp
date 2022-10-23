<%
 /**
  * @Class Name : myRsvnEvtModify.jsp
  * @Description : 강연/행사/영화 예약 관련 정보 수정
  * @Modification Information
  * @
  * @  수정일         수정자      수정내용
  * @ ------------   --------    ---------------------------
  * @ 2020.11.04      김희택      최초 생성
  *
  *  @author 김희택
  *  @since 2020.11.04
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit">예약 정보 수정</h3>
		<div class="mypage_wrap bor">
			<!-- signup2 -->
			<div class="signup2">

<form:form commandName="mstVO" name="mstVO" id="mstVO" method="post">
<input type="hidden" name="evtRsvnIdx" id="evtRsvnIdx" value="${mstVO.evtRsvnIdx }" />
<input type="hidden" name="evtNo" id="evtno" value="${mstVO.evtNo }" />
<form:hidden path="dcAnnualLimit" />
				<p class="stit">예약 정보</p>
				<div class="table2 responsive-type ">
					<table>
						<caption>예약자 정보를 입력하는 표입니다.</caption>
						<colgroup>
							<col style="width:30%">
							<col style="width:70%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop">관람 제목</th>
								<td class="b">
									<c:out value="${mstVO.evtPartnm }"/>
									<c:out value="${mstVO.evtName }"/>
									<%-- <c:choose>
										<c:when test="${mstVO.evtR eq '1001' }">강연/행사/영화</c:when>
										<c:otherwise><c:out value="${mstVO.evtName }"/></c:otherwise>
									</c:choose> --%>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">일시</th>
								<td class="b"><fmt:parseDate var="evtYmd" value="${mstVO.evtVeingdate}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${evtYmd}" pattern="yyyy.MM.dd"/>
									&nbsp;&nbsp;${fn:substring(mstVO.evtStime,0,2) }:${fn:substring(mstVO.evtStime,2,4) } ~ ${fn:substring(mstVO.evtEtime,0,2) }:${fn:substring(mstVO.evtEtime,2,4) }
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">인원수</th>
								<td class="b">
<c:forEach items="${mstVO.itemList}" var="item" varStatus="s">
	<c:if test="${item.evtRsvnItemcd ne prevItemCd }">
		<c:set var="prevItemCd" value="${item.evtRsvnItemcd}" />
	 	<c:out value="${item.evtItemNm }"/> : <c:out value="${item.evtRsvnItemcnt }"/>명&nbsp;&nbsp;&nbsp;
	</c:if>
</c:forEach>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop"><label for="custNm">예약자명</label></th>
								<td><c:out value="${mstVO.evtRsvnCustnm }"/></td>
							</tr>
							<tr>
								<th scope="row" class="vtop"><label for="exbtHp">예약자  연락처</label></th>
								<td><tags:HpTelShow hpval="${mstVO.evtRsvnMoblphon}"/></td>
							</tr>
<c:choose>
	<c:when test="${ mstVO.evtPersnGbn eq '2001' }">
							<tr>
								<th scope="row" ><label for="evtRsvnGrpnm">단체,학교, 기관명</label></th>
								<td><form:input path="evtRsvnGrpnm"  placeholder="단체명을 입력해주세요." title="단체명을 입력해주세요" maxlength="50" value="${mstVO.evtRsvnGrpnm }"/>
									<p id="err_evtRsvnGrpnm" class="red" style="display:none">단체명을 입력해주세요.</p>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="evtVisitCustnm">방문 인솔자명</label></th>
								<td><form:input path="evtVisitCustnm"  placeholder="이름을 입력해주세요." title="방문 인솔자명을 입력해주세요" value="${mstVO.evtVisitCustnm }" maxlength="50"/>
									<p id="err_evtVisitCustnm" class="red" style="display:none">방문 인솔자명을 입력해주세요.</p>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="evtVisitMoblphon">방문 인솔자 연락처</label></th>
								<td><input type="text" name="evtVisitMoblphon" id="evtVisitMoblphon" placeholder="‘-’ 을 빼고 입력 해 주십시오." title="방문 인솔자 연락처를 입력해 주세요." class="phoneCheck"  value="<tags:HpTelShow hpval="${mstVO.evtVisitMoblphon}"/>" maxlength="13"/>
									<p id="err_evtVisitMoblphon" class="red" style="display:none">방문 인솔자 연락처를 입력해주세요.</p>
								</td>
							</tr>
	</c:when>
</c:choose>

							<tr>
								<th scope="row">이메일</th>
								<td>
									<form:input path="evtEmail"  placeholder="이메일주소를 입력해 주세요." maxlength="50" title="이메일주소를 입력해 주세요." value="${mstVO.evtEmail }"/>
									<p id="err_evtEmail" class="red" style="display:none">이메일주소를 입력해주세요.</p>
								</td>
							</tr>
						</tbody>
					</table>
					<!-- //table -->
				</div>

				<!-- //join_wrap -->
				<ul class="btn_area mb50">
					<li><a href="javascript:fn_next()" class="btn_ty_m_c1">예약정보 저장</a></li>
				</ul>
				<!-- //btn_area -->
<c:if test="${ mstVO.evtRsvnApptype eq '11'}">
				<p class="stit">예약 인원 <span class="tb_txt">※ 예약하신 인원(${mstVO.evtVeingnmpr}명) 이하로 수정하실 수 있습니다.</span></p>
					<!-- board_ty -->
				<div class="board_ty">
					<ul>
<c:forEach items="${mstVO.chargeList}" var="item" varStatus="s">
<form:hidden path="chargeList[${s.index}].itemCd" value="${item.itemCd }"/>
<form:hidden path="chargeList[${s.index}].itemPrice" value="${item.itemPrice }"/>
<form:hidden path="chargeList[${s.index}].itemNm" value="${item.itemNm }"/>
<form:hidden path="chargeList[${s.index}].dcRate" value="${item.dcRate}"/>
<form:hidden path="chargeList[${s.index}].dcAnnualRate" value="${item.dcAnnualRate}"/>
						<li>
							<div class="info">
								<!-- info_tit -->
								<div class="info_tit">
									<span><c:out value="${item.itemNm }" /><em> <fmt:formatNumber value="${item.itemPrice}"/>원</em></span>
								</div>
								<!-- //info_tit -->
								<!-- numcontrol -->
								<div class="numcontrol num01">
									<input type="button" class="btn_minus" onclick="fn_cnt_change('<c:out value="${item.itemCd }"/>' , ${s.index} ,  -1 , this)"/>
									<form:input path="chargeList[${s.index}].itemCnt" class="count" value="${item.itemCnt }" data-idx="${s.index}" data-item="${item.itemCd}" autocomplete="false" title="예약인원수 입력"/>
									<input type="button" class="btn_plus" onclick="fn_cnt_change('<c:out value="${item.itemCd }"/>' , ${s.index} ,  1 , this)"/>
								</div>
								<!-- //numcontrol -->
							</div>
							<!-- //info -->
						<c:choose>
						<c:when test="${item.itemPrice < 1}">
								<!-- info_price -->
							<ul class="info_price" id="DIV_PRICE_<c:out value="${item.itemCd }" />">
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
							<ul class="info_price" id="DIV_PRICE_<c:out value="${item.itemCd }" />">
								<li class="ex_totalp">
									<dl >
										<dt>총 금액</dt>
										<dd>0 원</dd>
									</dl>
								</li>
<c:if test="${item.dcAnnualRate > 0 and item.itemPrice>0}">
								<li class="ex_disc_2">
									<dl>
										<dt>유료회원 (<c:out value="${item.dcAnnualRate}"/>% 할인)<em id="DIV_AQTY_${item.itemCd }">0명 적용</em></dt>
										<dd>0 원</dd>
									</dl>
								</li>
</c:if>
<c:if test="${item.dcRate > 0 and item.itemPrice>0}">
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
				<!-- //join_wrap -->
			<ul class="btn_area mb10">
				<li><a href="javascript:fn_person_save()" class="btn_ty_m_c1">예약인원 저장</a></li>
			</ul>
			<!-- //btn_area -->
</c:if>
</form:form>


						</div>
					</div>
					<!-- //inner -->
<ul id="cancel_area" class="btn_area long">
<c:choose>
	<c:when test="${mstVO.evtRsvnApptype eq '11'}">
			<li><a href="./myRsvnList?gubun=EVT" class="btn_ty_m_c3">나의 예약 목록</a></li>
	</c:when>
	<c:otherwise>
			<li><a href="./myRsvnCompList?gubun=EVT&pageIndex=${param.pageIndex}" class="btn_ty_m3_c3">나의 예약 목록</a></li>
	</c:otherwise>
</c:choose>
</ul>
				</div>
				<!-- //sub_cont -->
</div>

<script>

$(document).ready(function() {
	$('input[type=text]').keyup(function() {
		$('#err_'+$(this).attr("id")).hide();
	});

	$('.count').on('change', function() {
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

	$('.phoneCheck').keyup(function() {
		$(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-") );
	});

	setTimeout("fn_total_price_set()", 100); //historyback 시 금액 계산

});
function fn_cnt_change(itemCd , index, cnt , obj, type) {
	var grpMinCnt = ${mstVO.grpMinCnt};
	var grpMaxCnt = ${mstVO.grpMaxCnt};
	var numFlag = true;
	var numObj =  $(obj).parent().find(".count");
	var txt = numObj.val();
	var newTxt = parseInt(txt) + cnt;
	var prevCnt = document.getElementById("chargeList"+index+".itemCnt").value;
	var price = document.getElementById("chargeList"+index+".itemPrice").value;

	var maxCnt = ${mstVO.evtVeingnmpr};

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
	} else if (totalCnt > maxCnt) {
		alert("신청하신 인원 " + maxCnt + "명 이하로 수정하실 수 있습니다.");
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
		var price = document.getElementById("chargeList"+idx+".itemPrice").value;
		var orignCnt = document.getElementById("chargeList"+idx+".itemCnt").value;
		var discRate = document.getElementById("chargeList"+idx+".dcRate").value;
		var discAnnual = document.getElementById("chargeList"+idx+".dcAnnualRate").value;

		var discPrice = 0;
		var discPrice2 = 0;
		var itemCd = $(this).data("item");
		var cnt = orignCnt;

		var newPrice = orignCnt * parseInt(price);
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


		if (discRate != "" && parseInt(discRate) > 0) {
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

function fn_person_save() {
	var grpMinCnt = ${mstVO.grpMinCnt};
	var grpMaxCnt = ${mstVO.grpMaxCnt};
	var maxCnt = ${mstVO.evtVeingnmpr};

	//총 인원 체크
	var totalCnt  = 0;
	$.each($('.count') , function(idx , item) {
		var myCnt = document.getElementById("chargeList"+idx+".itemCnt").value;
		if (myCnt == "") myCnt = 0;
		totalCnt += parseInt(myCnt);
	});

	if (totalCnt < 1) {
		alert("인원을 최소 1명 이상 등록해 주세요.");
	} else if (totalCnt > maxCnt) {
		alert("신청하신 인원 " + maxCnt + "명 이하로 수정하실 수 있습니다.");
		numFlag = false;
	} else if (grpMinCnt > 0 && totalCnt < grpMinCnt) {
		alert("단체 신청은 최소  "+grpMinCnt+"명 이상 신청하실 수 있습니다.");
		numFlag = false;
	} else {
		$('body').showLoading();

		 $.ajax({
		        type : "POST",
		        url  : './myRsvnEvtItemSave',
		        data : $('#mstVO').serialize(),
		        dataType: "json",
		        beforeSend : function(xhr) {
		        	xhr.setRequestHeader("${_csrf.headerName}",  "${_csrf.token}");
		        },
		        success: function(data) {
		        	$('body').hideLoading();
		        	if (data.RESULT == "OK") {
		        		alert("수정되었습니다.");
			        	window.location.reload();
		        	} else {
		        		alert(data.RESULT);
		        	}
		        }, error: function() {
		        	$('body').hideLoading();
		        	alert("Server Error.");
		        }
		    });
	}
}

function fn_next() {
	var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;//이메일 정규식
	var hpRule = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;

	var nullObj = "";


	<c:if test="${mstVO.evtPersnGbn eq '2001'}">
		if ($('#evtRsvnGrpnm').val() == "") {
			if (nullObj == "") nullObj = "evtRsvnGrpnm";
			$('#err_evtRsvnGrpnm').text("단체명을 입력해 주세요.").show();
		}
		if ($('#evtVisitCustnm').val() == "") {
			if (nullObj == "") nullObj = "evtVisitCustnm";
			$('#err_evtVisitCustnm').text("방문 인솔자명을 입력해 주세요.").show();
		}
		if ($('#evtVisitMoblphon').val() == "") {
			if (nullObj == "") nullObj = "evtVisitMoblphon";
			$('#err_evtVisitMoblphon').text("방문 인솔자 연락처를 입력해 주세요.").show();
		}
		if (!hpRule.test($('#evtVisitMoblphon').val())) {
			if (nullObj == "") nullObj = "evtVisitMoblphon";
			$('#err_evtVisitMoblphon').text("방문 인솔자 연락처가 형식에 맞지 않습니다.").show();
		}
	</c:if>
		if ($('#evtEmail').val() == "") {
			if (nullObj == "") nullObj = "exbtEmail";
			$('#err_evtEmail').text("이메일 주소를 입력해 주세요.").show();
		} else if (!emailRule.test($('#evtEmail').val())) {
			if (nullObj == "") nullObj = "exbtEmail";
			$('#err_evtEmail').text("이메일 주소가 형식에 맞지 않습니다.").show();
		}

		if (nullObj != "") {
			$('#' + nullObj).focus();
		} else {
			$('body').showLoading();
			 $.ajax({
			        type : "POST",
			        url  : './myRsvnEvtSave',
			        data : {
			        	evtRsvnIdx : $('#evtRsvnIdx').val(),
			        	evtRsvnGrpnm : $('#evtRsvnGrpnm').val(),
			        	evtVisitCustnm : $('#evtVisitCustnm').val(),
			        	evtVisitMoblphon : $('#evtVisitMoblphon').val(),
			        	evtEmail : $('#evtEmail').val()
			        },
			        dataType: "json",
			        beforeSend : function(xhr) {
			        	xhr.setRequestHeader("${_csrf.headerName}",  "${_csrf.token}");
			        },
			        success: function(data) {
			        	$('body').hideLoading();
			        	if (data.RESULT == "OK") {
			        		alert("수정되었습니다.");
				        	window.location.reload();
			        	} else {
			        		alert(data.RESULT);
			        	}
			        }, error: function() {
			        	$('body').hideLoading();
			        	alert("Server Error.");
			        }
			    });

		}
}

function fn_reserve_success(data) {

	 if (data.result.resultCd == "SUCCESS") {
		 $('#evtRsvnIdx').val( data.result.resultMsg );
    	 $('#mstVO').attr("action" , "./reserveResult");
    	 $('#mstVO').submit();
    } else {
		alert(data.result.resultMsg);
    }
}
</script>