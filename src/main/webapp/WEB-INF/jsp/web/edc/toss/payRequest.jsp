<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>통합LG유플러스 전자결서비스 결제테스트</title>
<script src="${requestScope.resourceContext}/js/jquery-1.12.4.min.js"></script>
<c:choose>
	<c:when test="${paramMap.CST_PLATFORM eq 'test'}">
<script language="javascript" src="https://pretest.uplus.co.kr:9443/xpay/js/xpay_crossplatform.js" type="text/javascript"></script>
	</c:when>
	<c:otherwise>
<script language="javascript" src="https://xpay.uplus.co.kr/xpay/js/xpay_crossplatform.js" type="text/javascript"></script>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
	var LGD_window_type = '${paramMap.CST_WINDOW_TYPE}';
	var lgdwin;
	
	/*
	* 수정불가
	*/
	function fn_launchCrossPlatform(payMethod) {
		<%-- if (LGD_window_type == "iframe") {
			// LGD_window_type=SUBMIT 인 경우 기본 창 사이즈(650, 650)를 변경하고 싶은 경우 openXpay(a, b, c, d, e, f) 의 매개변수 e:가로, f:세로 값을 정수로 전달하여 주세요
			var domain = null;
			var width = "";
			var height = "";
			lgdwin = openXpay(document.getElementById('LGD_PAYINFO'), '${CST_PLATFORM}', LGD_window_type, domain, width, height);
		} else {
			lgdwin = open_paymentwindow(document.getElementById('LGD_PAYINFO'), '${CST_PLATFORM}', LGD_window_type);
		} --%>
		
		var varUA = navigator.userAgent.toLowerCase(); //userAgent 값 얻기
		if ( varUA.indexOf("iphone") > -1||varUA.indexOf("ipad") > -1||varUA.indexOf("ipod") > -1 ) {
			$('#LGD_KVPMISPAUTOAPPYN').val("N");
			$('#LGD_MTRANSFERAUTOAPPYN').val("N");
		}

		if ($('#LGD_AMOUNT').val() == "0") {
			//0원 결제는 바로 저장으로 보냄
			//getFormObject().target = "_self";
			//getFormObject().method = "post";
			//getFormObject().action = "./myRsvnListSave";
			getFormObject().submit();
		} else {
			$('#LGD_CUSTOM_FIRSTPAY').val(payMethod);
			$('#LGD_CUSTOM_USABLEPAY').val(payMethod);

			lgdwin = openXpay(document.getElementById("LGD_PAYINFO"), '${paramMap.CST_PLATFORM}', LGD_window_type, null, "", "");
		}
	}
	
	/*
	 * 인증결과 처리
	 */
	function payment_return() {
		var fDoc;
		if (LGD_window_type == "iframe") {
			fDoc = lgdwin.contentWindow || lgdwin.contentDocument;
		} else {
			fDoc = lgdwin;
		}
		
		$("#LGD_RESPCODE").val(fDoc.document.getElementById('LGD_RESPCODE').value);				
		$("#LGD_RESPMSG").val(fDoc.document.getElementById('LGD_RESPMSG').value);
		$("#LGD_PAYKEY").val(fDoc.document.getElementById('LGD_PAYKEY').value);
		
		if ($("#LGD_RESPCODE").val() == "0000") {
			//if ($("#LGD_CUSTOM_PROCESSTYPE").val() == "TWOTR") {				
			closeIframe();
			$.post({
			    url : './payResponse.json',
			    data : $("#LGD_PAYINFO").serialize(),
			    async : false,
				success: function(data) {
					alert(data.result.msg);
					if (data.result.code == "SUCCESS") {
						//완료페이지로 이동합니다.
					}
				}
		    });
			/* } else {
				alert(document.getElementById('LGD_RESPMSG').value);
				closeIframe();
			} */
		} else {
			alert(document.getElementById('LGD_RESPMSG').value);
			closeIframe();
		}
	}
</script>
</head>
<body>
<form id="LGD_PAYINFO" name="LGD_PAYINFO" action="./payResponse" method="post">
<input type='hidden' name='edcRsvnNo' id='edcRsvnNo' value='R321121101606'>

<input type='hidden' name='CST_MID' id='CST_MID' value='${paramMap.CST_MID}'>
<input type='hidden' name='LGD_MID' id='LGD_MID' value='${paramMap.LGD_MID}'>
<input type='hidden' name='LGD_OID' id='LGD_OID' value='${paramMap.LGD_OID}'>

<input type="hidden" name="CST_PLATFORM" id="CST_PLATFORM" value="${paramMap.CST_PLATFORM}"/>
<input type="hidden" name="LGD_RETURNURL" id="LGD_RETURNURL" value="${paramMap.LGD_RETURNURL}"/>
<input type="hidden" name="LGD_CASNOTEURL" id="LGD_CASNOTEURL" value="${paramMap.LGD_CASNOTEURL}"/>

<input type="hidden" name="LGD_ENCODING" id="LGD_ENCODING" value="UTF-8"/>
<input type="hidden" name="LGD_ENCODING_RETURNURL" id="LGD_ENCODING_RETURNURL" value="UTF-8"/>
<input type="hidden" name="LGD_ENCODING_NOTEURL" id="LGD_ENCODING_NOTEURL" value="UTF-8"/>

<input type="hidden" name="LGD_CUSTOM_PROCESSTYPE" id="LGD_CUSTOM_PROCESSTYPE" value="TWOTR"/>
<input type="hidden" name="LGD_CUSTOM_SKIN" id="LGD_CUSTOM_SKIN" value="red"/>
<input type="hidden" name="CST_WINDOW_TYPE" id="CST_WINDOW_TYPE" value="${paramMap.CST_WINDOW_TYPE}"/>
<input type="hidden" name="LGD_VERSION" id="LGD_VERSION" value="JSP_Non-ActiveX_SmartXPay"/>
<input type="hidden" name="LGD_CUSTOM_SWITCHINGTYPE" id="LGD_CUSTOM_SWITCHINGTYPE" value="${paramMap.LGD_CUSTOM_SWITCHINGTYPE}"/>

<input type="hidden" name="LGD_MPILOTTEAPPCARDWAPURL" id="LGD_MPILOTTEAPPCARDWAPURL" value=""/><!-- iOS 연동시 필수 -->
<input type="hidden" name="LGD_KVPMISPWAPURL" id="LGD_KVPMISPWAPURL" value=""/><!-- ISP 카드결제 연동을 위한 파라미터(필수) -->
<input type="hidden" name="LGD_KVPMISPCANCELURL" id="LGD_KVPMISPCANCELURL" value=""/><!-- ISP 카드결제 연동을 위한 파라미터(필수) -->
<input type="hidden" name="LGD_KVPMISPAUTOAPPYN" id="LGD_KVPMISPAUTOAPPYN" value="A"/><!-- iOS: N -->
<input type="hidden" name="LGD_MTRANSFERWAPURL" id="LGD_MTRANSFERWAPURL" value=""/><!-- 계좌이체 연동을 위한 파라미터(필수) -->
<input type="hidden" name="LGD_MTRANSFERCANCELURL" id="LGD_MTRANSFERCANCELURL" value=""/><!-- 계좌이체 연동을 위한 파라미터(필수) -->
<input type="hidden" name="LGD_MTRANSFERAUTOAPPYN" id="LGD_MTRANSFERAUTOAPPYN" value="A"/><!-- android여부(A:android, N:ios) -->
<input type="hidden" name="LGD_ESCROW_USEYN" id="LGD_ESCROW_USEYN" value="N"/>

<!-- REQUEST -->
<input type="hidden" name="LGD_BUYER" id="LGD_BUYER" value="${paramMap.LGD_BUYER}"/>
<input type="hidden" name="LGD_BUYERID" id="LGD_BUYERID" value="${paramMap.LGD_BUYERID}"/>
<input type="hidden" name="LGD_BUYEREMAIL" id="LGD_BUYEREMAIL" value="${paramMap.LGD_BUYEREMAIL}"/>
<input type="hidden" name="LGD_PRODUCTINFO" id="LGD_PRODUCTINFO" value="${paramMap.LGD_PRODUCTINFO}"/>
<input type="hidden" name="LGD_AMOUNT" id="LGD_AMOUNT" value="${paramMap.LGD_AMOUNT}"/>
<input type="hidden" name="LGD_TIMESTAMP" id="LGD_TIMESTAMP" value="${paramMap.LGD_TIMESTAMP}"/>
<input type="hidden" name="LGD_HASHDATA" id="LGD_HASHDATA" value="${paramMap.LGD_HASHDATA}"/>
<!-- 무통장입금 입금기한:yyyyMMddHHmmss -->
<input type="hidden" name="LGD_CLOSEDATE" id="LGD_CLOSEDATE" value="${paramMap.LGD_CLOSEDATE}"/>

<!-- 결제결과(RETURN URL을 통해 전달되는 값) -->
<input type="hidden" name="LGD_RESPCODE" id="LGD_RESPCODE" value=""/>
<input type="hidden" name="LGD_PAYKEY" id="LGD_PAYKEY" value=""/>
<input type="hidden" name="LGD_RESPMSG" id="LGD_RESPMSG" value=""/>
<!-- 아래 키값들은 returnUrl에서 session에 설정하고 payResponse에서 session에서 추출하여 사용 -->
<!-- <input type="hidden" name="LGD_PAYTYPE" id="LGD_PAYTYPE" value=""/>
<input type="hidden" name="LGD_BUYERIP" id="LGD_BUYERIP" value=""/>
<input type="hidden" name="LGD_CARDNUM" id="LGD_CARDNUM" value=""/>
<input type="hidden" name="LGD_FINANCECODE" id="LGD_FINANCECODE" value=""/>
<input type="hidden" name="LGD_FINANCENAME" id="LGD_FINANCENAME" value=""/>
<input type="hidden" name="LGD_AUTHTYPE" id="LGD_AUTHTYPE" value=""/> -->

<input type="hidden" name="LGD_CUSTOM_FIRSTPAY" id="LGD_CUSTOM_FIRSTPAY" value="SC0010"/>
<input type="hidden" name="LGD_CUSTOM_USABLEPAY" id="LGD_CUSTOM_USABLEPAY" value="SC0010"/>
<input type='hidden' name='LGD_WINDOW_TYPE' id='LGD_WINDOW_TYPE' value='iframe'>
<input type='hidden' name='LGD_WINDOW_VER' id='LGD_WINDOW_VER' value='2.5'>

<input type="hidden" name="dpstrNm" id="LGD_CLOSEDATE" value="${paramMap.LGD_CLOSEDATE}"/>
<input type="hidden" name="bankCd" id="LGD_CLOSEDATE" value="${paramMap.LGD_CLOSEDATE}"/>
<input type="hidden" name="bankNm" id="LGD_CLOSEDATE" value="${paramMap.LGD_CLOSEDATE}"/>
<input type="hidden" name="accountNum" id="LGD_CLOSEDATE" value="${paramMap.LGD_CLOSEDATE}"/>

<table>
    <tr>
        <td>구매자 이름 </td>
        <td>${paramMap.LGD_BUYER}</td>
    </tr>
    <tr>
        <td>상품정보 </td>
        <td>${paramMap.LGD_PRODUCTINFO}</td>
    </tr>
    <tr>
        <td>결제금액 </td>
        <td>${paramMap.LGD_AMOUNT}</td>
    </tr>
    <tr>
        <td>구매자 이메일 </td>
        <td>${paramMap.LGD_BUYEREMAIL}</td>
    </tr>
    <tr>
        <td>주문번호 </td>
        <td>${paramMap.LGD_OID}</td>
    </tr>
    <tr>
        <td colspan="2">* 추가 상세 결제요청 파라미터는 메뉴얼을 참조하시기 바랍니다.</td>
    </tr>
    <tr>
        <td colspan="2"></td>
    </tr>
    <tr>
        <td colspan="2">
		<input type="button" value="인증요청" onclick="fn_launchCrossPlatform('SC0010')"/>
        </td>
    </tr>
</table>
</form>
</body>

</html>