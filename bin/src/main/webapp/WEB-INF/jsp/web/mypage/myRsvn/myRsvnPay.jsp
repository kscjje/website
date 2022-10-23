<%
 /**
  * @Class Name : myRsvnPay.jsp
  * @Description : 예약 결제 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.10    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.09.10
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<c:choose>
	<c:when test="${myRsvnVO.CST_PLATFORM eq 'test'}"><script language="javascript" src="https://pretest.uplus.co.kr:9443/xpay/js/xpay_crossplatform.js" type="text/javascript"></script></c:when>
	<c:otherwise><script src="https://xpay.uplus.co.kr/xpay/js/xpay_crossplatform.js"></script></c:otherwise>
</c:choose>
<script type="text/javascript">
/*
* 수정불가.
*/
var LGD_window_type = '${CST_WINDOW_TYPE}';

/*
* 수정불가
*/
function fn_launchCrossPlatform(payMethod) {
	document.charset = "euc-kr";

	var varUA = navigator.userAgent.toLowerCase(); //userAgent 값 얻기
	if ( varUA.indexOf("iphone") > -1||varUA.indexOf("ipad") > -1||varUA.indexOf("ipod") > -1 ) {
		$('#LGD_KVPMISPAUTOAPPYN').val("N");
		$('#LGD_MTRANSFERAUTOAPPYN').val("N");
	}

	if ($('#LGD_AMOUNT').val() == "0") {
		//0원 결제는 바로 저장으로 보냄
		getFormObject().target = "_self";
		getFormObject().method = "post";
		getFormObject().action = "./myRsvnListSave";
		getFormObject().submit();
	} else {
		$('#LGD_CUSTOM_FIRSTPAY').val(payMethod);
		$('#LGD_CUSTOM_USABLEPAY').val(payMethod);

		lgdwin = openXpay( getFormObject(), '<c:out value="${myRsvnVO.CST_PLATFORM}"/>', LGD_window_type, null, "", "");
	}

}

/*
* FORM 명만  수정 가능
*/
function getFormObject() {
	return document.getElementById("myRsvnVO");
}

/*
 * 인증결과 처리
 */
function payment_return() {

	var fDoc;

	fDoc = lgdwin.contentWindow || lgdwin.contentDocument;

	if (fDoc.document.getElementById('LGD_RESPCODE').value == "0000") {

		document.getElementById("LGD_PAYKEY").value = fDoc.document.getElementById('LGD_PAYKEY').value;
		document.getElementById("LGD_RESPCODE").value = fDoc.document.getElementById('LGD_RESPCODE').value;
		getFormObject().target = "_self";
		getFormObject().method = "post";
		getFormObject().action = "./myRsvnListSave";
		getFormObject().submit();

	} else {

		var varReturnCd  = fDoc.document.getElementById('LGD_RESPCODE').value;
		var varReturnMsg = fDoc.document.getElementById('LGD_RESPMSG').value;


		alert(varReturnCd + "\n" + varReturnMsg);
		closeIframe();

	}
}

function fn_discount(gubun ,rsvnIdx  , itemCd , itemCnt) {
	$('body').showLoading();
	 $.ajax({
	        type : "POST",
	        url  : './myRsvnCouponAjax',
	        data : {gubun : gubun , rsvnIdxOne : rsvnIdx , itemCd:itemCd , itemCnt:itemCnt},
	        dataType: "html",
	        beforeSend : function(xhr) {
	        	xhr.setRequestHeader("${_csrf.headerName}",  "${_csrf.token}");
	        },
	        success: function(data) {
	        	$('body').hideLoading();
	        	$('#discModal .modal-txt').html(data);
	        	$('#discModal').modal('show');
	        }, error: function() {
	        	$('body').hideLoading();
	        	alert("Server Error.");
	        }
	    });
}

function fn_coupon_save() {
	$('body').showLoading();
	 $.ajax({
	        type : "POST",
	        url  : './myRsvnCouponSave',
	        data : $('#couponForm').serialize(),
	        dataType: "json",
	        beforeSend : function(xhr) {
	        	xhr.setRequestHeader("${_csrf.headerName}",  "${_csrf.token}");
	        },
	        success: function(data) {
	        	$('body').hideLoading();
	        	if (data.result.msg == "OK") {
	        		$('#discModal').modal({show: false});
		        	fn_pay_load();
	        	} else {
	        		alert(data.result.msg );
	        	}


	        }, error: function() {
	        	$('body').hideLoading();
	        	alert("Server Error.");
	        }
	    });
}
function fn_pay_load() {
	$('body').showLoading();

	 $.ajax({
	        type : "POST",
	        url  : './myRsvnPayAjax',
	        data : $('#myRsvnVO').serialize(),
	        dataType: "html",
	        beforeSend : function(xhr) {
	        	xhr.setRequestHeader("${_csrf.headerName}",  "${_csrf.token}");
	        },
	        success: function(data) {
	        	$('body').hideLoading();
	        	$('#payList').html(data);
	        }, error: function() {
	        	$('body').hideLoading();
	        	alert("Server Error.");
	        }
	    });
}
function fn_discount_cancel(gubun ,rsvnIdx , subSeq, itemCd) {
	$('body').showLoading();
	 $.ajax({
	        type : "POST",
	        url  : './myRsvnCouponCancel',
	        data : {gubun : gubun , rsvnIdxOne : rsvnIdx , subSeq: subSeq , itemCd:itemCd},
	        dataType: "json",
	        beforeSend : function(xhr) {
	        	xhr.setRequestHeader("${_csrf.headerName}",  "${_csrf.token}");
	        },
	        success: function(data) {
	        	$('body').hideLoading();
	        	fn_pay_load();

	        }, error: function() {
	        	$('body').hideLoading();
	        	alert("Server Error.");
	        }
	    });
}
function fn_group_msg() {
	alert("단체예약은  할인사유를 변경하실 수 없습니다.");
}
function fn_disc_msg() {
	alert("기본할인은  삭제하실 수 없습니다.");
}
function fn_none_msg() {
	alert("무료 상품은 할인을 선택 하실 수 없습니다.")
}
$(document).ready(function() {
	 fn_pay_load();
});

</script>
<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit">나의 예약내역</h3>
		<div class="join_wrap bor">
			<!-- step -->
			<ul class="step step2">
				<li class="on">
					<dl><!-- 현재 on -->
						<dt>1</dt>
						<dd>결제정보</dd>
					</dl>
				</li>
				<li>
					<dl>
						<dt>2</dt>
						<dd>결제완료</dd>
					</dl>
				</li>
			</ul>
			<!-- //step -->
			<!-- signup2 -->

<form:form commandName="myRsvnVO" name="myRsvnVO" id="myRsvnVO" action="./myRsvnPay" method="post" accept-charset="EUC-KR">
<form:hidden path="gubun" />
<form:hidden path="CST_PLATFORM" />
<form:hidden path="LGD_RETURNURL" />
<form:hidden path="LGD_CASNOTEURL" /><!-- 무통장 -->
<input type="hidden" name="LGD_CUSTOM_PROCESSTYPE" id="LGD_CUSTOM_PROCESSTYPE" value="TWOTR"/>
<input type="hidden" name="LGD_CUSTOM_SKIN" id="LGD_CUSTOM_SKIN" value="red"/>
<input type="hidden" name="CST_WINDOW_TYPE" id="CST_WINDOW_TYPE" value="${CST_WINDOW_TYPE }"/>
<input type="hidden" name="LGD_VERSION" id="LGD_VERSION" value="JSP_Non-ActiveX_SmartXPay"/>
<input type="hidden" name="LGD_CUSTOM_SWITCHINGTYPE" id="LGD_CUSTOM_SWITCHINGTYPE" value="${LGD_CUSTOM_SWITCHINGTYPE }"/>
<input type="hidden" name="LGD_MPILOTTEAPPCARDWAPURL" id="LGD_MPILOTTEAPPCARDWAPURL" value=""/>
<input type="hidden" name="LGD_KVPMISPWAPURL" id="LGD_KVPMISPWAPURL" value=""/>
<input type="hidden" name="LGD_KVPMISPCANCELURL" id="LGD_KVPMISPCANCELURL" value=""/>
<input type="hidden" name="LGD_KVPMISPAUTOAPPYN" id="LGD_KVPMISPAUTOAPPYN" value="A"/><!-- iOS: N -->
<input type="hidden" name="LGD_MTRANSFERWAPURL" id="LGD_MTRANSFERWAPURL" value=""/>
<input type="hidden" name="LGD_MTRANSFERCANCELURL" id="LGD_MTRANSFERCANCELURL" value=""/>
<input type="hidden" name="LGD_MTRANSFERAUTOAPPYN" id="LGD_MTRANSFERAUTOAPPYN" value="A"/>
<input type="hidden" name="LGD_RESPCODE" id="LGD_RESPCODE" value=""/>
<input type="hidden" name="LGD_PAYKEY" id="LGD_PAYKEY" value=""/>
<input type="hidden" name="LGD_RESPMSG" id="LGD_RESPMSG" value=""/>
<input type="hidden" name="LGD_CUSTOM_FIRSTPAY" id="LGD_CUSTOM_FIRSTPAY" value="SC0010"/>
<input type="hidden" name="LGD_CUSTOM_USABLEPAY" id="LGD_CUSTOM_USABLEPAY" value="SC0010"/>
<c:forEach items="${myRsvnVO.rsvnIdx}" var="val" varStatus="status">
<input type="hidden" name="rsvnIdx" value="${val }"/>
</c:forEach>
		<div class="signup2" id="payList">


		</div>
</form:form>
	</div>
	<ul class="btn_area long">
		<li><a href="./myRsvnList?gubun=${myRsvnVO.gubun }" class="btn_ty_m_c4">목록</a></li>
		<li><a href="javascript:fn_launchCrossPlatform('SC0010')" class="btn_ty_m3_c1" title="토스 전자결제 레이어열림">신용카드 결제하기</a></li>
		<li><a href="javascript:fn_launchCrossPlatform('SC0030')" class="btn_ty_m3_c3" title="토스 전자결제 레이어열림">계좌이체 결제하기</a></li>
	</ul>
	<!-- //inner -->
</div>
<!-- //sub_cont -->

<div class="modal" id="discModal">
	<div class="modal-dialog" style="max-width:820px;">
		<div class="modal-content">
			<div class="modal-title">
				<h4>할인사유 선택</h4>
				<a href="#" class="pop_close" data-dismiss="modal">닫기</a>
			</div>
			<div class="modal-body">
				<p></p>
				<div class="modal-txt">

					<!-- //table -->
					<p class="ex mt10">※ 할인사유는 중복되지 않습니다.</p>
				</div>
			</div>
			<ul class="btn_area">
				<li><a href="javascript:fn_coupon_save()" class="btn_ty_m_c1">적용하기</a></li>
			</ul>
			<!-- //btn_area -->
		</div>
	</div>
</div>
<!-- //팝업 -->