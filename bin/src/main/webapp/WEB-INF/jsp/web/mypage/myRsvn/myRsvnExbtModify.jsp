<%
 /**
  * @Class Name : myRsvnExbtModify.jsp
  * @Description : 관람 예약 관련 정보 수정
  * @Modification Information
  * @
  * @  수정일         수정자      수정내용
  * @ ------------   --------    ---------------------------
  * @ 2020.09.16      전영석      최초 생성
  *
  *  @author 전영석
  *  @since 2020.09.16
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

<form:form commandName="rsvnMasterVO" name="rsvnMasterVO" id="rsvnMasterVO" action="./reserveAction" method="post">
<form:hidden path="rsvnIdx" />
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
									<c:out value="${rsvnMasterVO.partNm }"/>
									<c:choose>
										<c:when test="${rsvnMasterVO.exbtType eq '1001' }">관람</c:when>
										<c:otherwise><c:out value="${rsvnMasterVO.exbtName }"/></c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">일시</th>
								<td class="b"><fmt:parseDate var="extYmd" value="${rsvnMasterVO.ymd}" pattern="yyyyMMdd"/>
									<fmt:formatDate value="${extYmd}" pattern="yyyy.MM.dd"/>
									&nbsp;&nbsp;${fn:substring(rsvnMasterVO.exbtStime,0,2) }:${fn:substring(rsvnMasterVO.exbtStime,2,4) } ~ ${fn:substring(rsvnMasterVO.exbtEtime,0,2) }:${fn:substring(rsvnMasterVO.exbtEtime,2,4) }
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">인원수</th>
								<td class="b">
<c:forEach items="${rsvnMasterVO.chargeList}" var="item" varStatus="s">
	<c:if test="${item.itemCnt > 0 }"> <c:out value="${item.itemNm }"/> : <c:out value="${item.itemCnt }"/>명&nbsp;&nbsp;&nbsp;</c:if>
</c:forEach>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop"><label for="custNm">예약자명</label></th>
								<td><c:out value="${rsvnMasterVO.custNm }"/></td>
							</tr>
							<tr>
								<th scope="row" class="vtop"><label for="exbtHp">예약자  연락처</label></th>
								<td><tags:HpTelShow hpval="${rsvnMasterVO.exbtHp}"/></td>
							</tr>
<c:choose>
	<c:when test="${rsvnMasterVO.target eq '2001'}">

							<tr>
								<th scope="row" ><label for="name">단체,학교, 기관명</label></th>
								<td><form:input path="grpnm"  placeholder="단체명을 입력해주세요." maxlength="50"/>
									<p id="err_grpnm" class="red" style="display:none">단체명을 입력해주세요.</p>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="visitcustNm">방문 인솔자명</label></th>
								<td><form:input path="visitcustNm"  placeholder="이름을 입력해주세요." value="${rsvnMasterVO.visitcustNm}" maxlength="50"/>
									<p id="err_visitcustNm" class="red" style="display:none">방문 인솔자명을 입력해주세요.</p>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="visitcustHpno">방문 인솔자 연락처</label></th>
								<td><input type="text" name="visitcustHpno" id="visitcustHpno" placeholder="‘-’ 을 빼고 입력 해 주십시오."  value="<tags:HpTelShow hpval="${rsvnMasterVO.visitcustHpno}"/>" maxlength="13" class="phoneCheck"/>
									<p id="err_visitcustHpno" class="red" style="display:none">방문 인솔자 연락처를 입력해주세요.</p>
								</td>
							</tr>
	</c:when>
</c:choose>

							<tr>
								<th scope="row">이메일</th>
								<td>
									<form:input path="exbtEmail"  placeholder="이메일주소를 입력 해 주십시오." maxlength="50"/>
									<p id="err_exbtEmail" class="red" style="display:none">이메일주소를 입력해주세요.</p>
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
<c:if test="${ rsvnMasterVO.apptype eq '11'}">
				<p class="stit">예약 인원 <span class="tb_txt">※ 예약하신 인원(${rsvnMasterVO.visitnum}명) 이하로 수정하실 수 있습니다.</span></p>
				<!-- board_ty -->
				<div class="board_ty">
					<ul>
<c:forEach items="${rsvnMasterVO.chargeList}" var="item" varStatus="s">
<form:hidden path="chargeList[${s.index}].itemCd" value="${item.itemCd }"/>
<form:hidden path="chargeList[${s.index}].price" value="${item.price }"/>
<form:hidden path="chargeList[${s.index}].itemNm" value="${item.itemNm }"/>
<form:hidden path="chargeList[${s.index}].dcKindCd" value="${item.dcKindCd }"/>
<form:hidden path="chargeList[${s.index}].dcType" value="${item.dcType}"/>
<form:hidden path="chargeList[${s.index}].dcRate" value="${item.dcRate}"/>
<form:hidden path="chargeList[${s.index}].dcAnnualRate" value="${item.dcAnnualRate}"/>
<form:hidden path="chargeList[${s.index}].adultYn" value="${item.adultYn}"/>
						<li>
							<div class="info">
								<!-- info_tit -->
								<div class="info_tit">
									<span><c:out value="${item.itemNm }" /><em> <fmt:formatNumber value="${item.price}"/>원</em></span>
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
						<c:when test="${item.price < 1}">
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
	<c:when test="${rsvnMasterVO.apptype eq '11'}">
			<li><a href="./myRsvnList?gubun=EXBT" class="btn_ty_m_c3">나의 예약 목록</a></li>
	</c:when>
	<c:otherwise>
			<li><a href="./myRsvnCompList?gubun=EXBT&pageIndex=${param.pageIndex}" class="btn_ty_m3_c3">나의 예약 목록</a></li>
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

	$('.phoneCheck').keyup(function() {
		$(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-") );
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

	setTimeout("fn_total_price_set()", 100); //historyback 시 금액 계산

});
function fn_cnt_change(itemCd , index, cnt , obj, type) {
	var grpMinCnt = ${rsvnMasterVO.grpMinCnt};
	var grpMaxCnt = ${rsvnMasterVO.grpMaxCnt};
	var numFlag = true;
	var numObj =  $(obj).parent().find(".count");
	var txt = numObj.val();
	var newTxt = parseInt(txt) + cnt;
	var prevCnt = document.getElementById("chargeList"+index+".itemCnt").value;
	var price = document.getElementById("chargeList"+index+".price").value;

	var maxCnt = ${rsvnMasterVO.visitnum};

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
		var price = document.getElementById("chargeList"+idx+".price").value;
		var orignCnt = document.getElementById("chargeList"+idx+".itemCnt").value;
		var discRate = document.getElementById("chargeList"+idx+".dcRate").value;
		var discAnnual = document.getElementById("chargeList"+idx+".dcAnnualRate").value;
		var discPrice = 0;
		var discPrice2 = 0;
		var itemCd = $(this).data("item");

		var newPrice = orignCnt * parseInt(price);
		var cnt = orignCnt;

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

function fn_person_save() {
	var grpMinCnt = ${rsvnMasterVO.grpMinCnt};
	var grpMaxCnt = ${rsvnMasterVO.grpMaxCnt};
	var maxCnt = ${rsvnMasterVO.visitnum};

	var adultCnt = 0; //성인인원수
	var childCnt = 0; //유아인원수
	var totalCnt  = 0;//총 인원 체크

	$.each($('.count') , function(idx , item) {
		var adultYn = document.getElementById("chargeList"+idx+".adultYn").value;
		var myCnt = document.getElementById("chargeList"+idx+".itemCnt").value;
		if (myCnt == "") myCnt = 0;
		totalCnt += parseInt(myCnt);

		if (adultYn == "Y") {
			adultCnt +=  parseInt(myCnt);
		} else if (adultYn == "N") {
			childCnt +=  parseInt(myCnt);
		}
	});

	if (totalCnt < 1) {
		alert("인원을 최소 1명 이상 등록해 주세요.");
	} else if (totalCnt > maxCnt) {
		alert("예약하신 인원 " + maxCnt + "명 이하로 수정하실 수 있습니다.");
		numFlag = false;
	} else if (grpMinCnt > 0 && totalCnt < grpMinCnt) {
		alert("단체 신청은 최소  "+grpMinCnt+"명 이상 신청하실 수 있습니다.");
		numFlag = false;
<c:if test="${rsvnMasterVO.partNm eq '꿈아띠체험관'}">
	} else if (adultCnt < Math.ceil(childCnt/6)) {
		alert("유아 예약 인원 6명당 성인 1명을 필수로 예약하셔야 합니다.\n( 현재 유아인원이 "+childCnt+"명 이므로 최소 성인 "+Math.ceil(childCnt/6)+"명이 필요합니다.)");
</c:if>
	} else {
		$('body').showLoading();

		 $.ajax({
		        type : "POST",
		        url  : './myRsvnExbtItemSave',
		        data : $('#rsvnMasterVO').serialize(),
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


<c:if test="${rsvnMasterVO.target eq '2001'}">
	 	if ($('#grpnm').val() == "") {
			if (nullObj == "") nullObj = "grpnm";
			$('#err_grpnm').text("단체명을 입력해 주세요.").show();
	 	}
		if ($('#visitcustNm').val() == "") {
			if (nullObj == "") nullObj = "visitcustNm";
			$('#err_visitcustNm').text("방문 인솔자명을 입력해 주세요.").show();
		}
		if ($('#visitcustHpno').val() == "") {
			if (nullObj == "") nullObj = "visitcustHpno";
			$('#err_visitcustHpno').text("방문 인솔자 연락처를 입력해 주세요.").show();
		} else if (!hpRule.test($('#visitcustHpno').val())) {
			if (nullObj == "") nullObj = "visitcustHpno";
			$('#err_visitcustHpno').text("방문 인솔자 연락처가 형식에 맞지 않습니다.").show();
		}
</c:if>
		if ($('#exbtEmail').val() == "") {
			if (nullObj == "") nullObj = "exbtEmail";
			$('#err_exbtEmail').text("이메일 주소를 입력해 주세요.").show();
		} else if (!emailRule.test($('#exbtEmail').val())) {
			if (nullObj == "") nullObj = "exbtEmail";
			$('#err_exbtEmail').text("이메일 주소가 형식에 맞지 않습니다.").show();
		}

		if (nullObj != "") {
			$('#' + nullObj).focus();
		} else {
			$('body').showLoading();

			 $.ajax({
			        type : "POST",
			        url  : './myRsvnExbtSave',
			        data : $('#rsvnMasterVO').serialize(),
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
		 $('#rsvnIdx').val( data.result.resultMsg );
    	 $('#rsvnMasterVO').attr("action" , "./reserveResult");
    	 $('#rsvnMasterVO').submit();
    } else {
		alert(data.result.resultMsg);
    }
}
</script>