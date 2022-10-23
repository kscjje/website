<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
	<script type="text/javascript">
		function setLGDResult() {
			/* if ("${paramMap.LGD_RESPCODE}" != "0000") {
				alert("${paramMap.LGD_RESPMSG}");
			} */
			
			if (opener) {
				console.log("call opener.payment_return()");
				opener.payment_return();
			} else {
				console.log("call parent.payment_return()");
				parent.payment_return();
			}
		}
	</script>
</head>
<body onload="setLGDResult()">
<input type="hidden" name="LGD_RESPCODE" id="LGD_RESPCODE" value="${paramMap.LGD_RESPCODE}"/>
<input type="hidden" name="LGD_RESPMSG" id="LGD_RESPMSG" value="${paramMap.LGD_RESPMSG}"/>
<input type="hidden" name="LGD_PAYKEY" id="LGD_PAYKEY" value="${paramMap.LGD_PAYKEY}"/>
<input type="hidden" name="LGD_PAYTYPE" id="LGD_PAYTYPE" value="${paramMap.LGD_PAYKEY}"/>
<input type="hidden" name="LGD_BUYERIP" id="LGD_BUYERIP" value="${paramMap.LGD_PAYKEY}"/>
<input type="hidden" name="LGD_CARDNUM" id="LGD_CARDNUM" value="${paramMap.LGD_PAYKEY}"/>
<input type="hidden" name="LGD_FINANCECODE" id="LGD_FINANCECODE" value="${paramMap.LGD_PAYKEY}"/>
<input type="hidden" name="LGD_FINANCENAME" id="LGD_FINANCENAME" value="${paramMap.LGD_PAYKEY}"/>
<input type="hidden" name="LGD_AUTHTYPE" id="LGD_AUTHTYPE" value="${paramMap.LGD_PAYKEY}"/>
<input type="hidden" name="LGD_PARAMS" id="LGD_PARAMS" value="${paramMap.LGD_PARAMS}"/>
<%-- ${paramMap.LGD_RESPMSG} --%>
<%
/* {
    "http_method": "POST",
    "elapsed(ms)": 0,
    "controller.method": "TossTestController.returnUrl",
    "params": {
      "LGD_BUYERPHONE": "",
      "LGD_PAYKEY": "2021120922182536AA40A465EF49B79F598FE8FDAE7FACVGZ9F4F1",
      "LGD_BUYEREMAIL": "woojinp@naver.com",
      "LGD_RECEIVER": "",
      "LGD_DELIVERYINFO": "",
      "LGD_PAYTYPE": "SC0010",
      "LGD_BUYERIP": "221.140.142.57",
      "LGD_RECEIVERPHONE": "",
      "LGD_AFFILIATECODE": "",
      "LGD_RETURNURL": "http://localhost:8080/web/edc/toss/returnUrl",
      "LGD_RESPMSG": "인증성공",
      "KVP_CARDCODE": "",
      "LGD_CARDNUM": "5188319819772879",
      "LGD_ESCROWYN": "N",
      "LGD_FINANCECODE": "51",
      "LGD_HASHDATA": "362dd271af9d85b994bf9f12aa16f42b",
      "LGD_BUYERADDRESS": "",
      "LGD_AUTHTYPE": "XMPI",
      "LGD_FINANCENAME": "삼성",
      "LGD_DEVICE": "",
      "LGD_TIMESTAMP": "20211209221825",
      "LGD_BUYER": "홍길동",
      "LGD_PRODUCTINFO": "myLG070-인터넷전화기",
      "LGD_BUYERSSN": "",
      "LGD_PRODUCTCODE": "",
      "LGD_BUYERID": "woojinp",
      "LGD_CLOSEDATE": "",
      "LGD_OID": "test_3465",
      "LGD_RESPCODE": "0000",
      "LGD_AMOUNT": "1000",
      "LGD_CARDPREFIX": "518831",
      "LGD_MID": "tlgdacomxpay"
    },
    "request_uri": "/web/edc/toss/returnUrl"
} */
%>
</body>
</html>