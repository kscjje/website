<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LG유플러스 전자결제 샘플 페이지 (XPay)</title>
</head>
<body>
	<form:form commandName="LGD_PAYINFO" name="LGD_PAYINFO" action="payRequest" method="post">
    <div>
        <table>
            <tr>
                <td>상점아이디(t를 제외한 아이디) </td>
                <td><input type="text" name="CST_MID" value="lgdacomxpay"/></td>
            </tr>
            <tr>
                <td>서비스,테스트 </td>
                <td><input type="text" name="CST_PLATFORM" value="test"/></td>
            </tr>
            <tr>
                <td>구매자 이름 </td>
                <td><input type="text" name="LGD_BUYER" value="홍길동"/></td>
            </tr>
            <tr>
                <td>구매자 ID </td>
                <td><input type="text" name="LGD_BUYERID" value="woojinp"/></td>
            </tr>
            <tr>
                <td>상품정보 </td>
                <td><input type="text" name="LGD_PRODUCTINFO" value="myLG070-인터넷전화기"/></td>
            </tr>
            <tr>
                <td>결제금액 </td>
                <td><input type="text" name="LGD_AMOUNT" value="1000"/></td>
            </tr>
            <tr>
                <td>구매자 이메일 </td>
                <td><input type="text" name="LGD_BUYEREMAIL" value="woojinp@naver.com"/></td>
            </tr>
            <tr>
                <td>주문번호 </td>
                <td><input type="text" name="LGD_OID" value="test_${LGD_OID}"/></td>
            </tr>
            <tr>
                <td>타임스탬프 </td>
                <td><input type="text" name="LGD_TIMESTAMP" value="${LGD_TIMESTAMP}"/></td>
            </tr>
            <tr>
                <td>초기결제수단 </td>
                <td><select name="LGD_CUSTOM_FIRSTPAY">
							<option value="SC0010">신용카드</option>				
							<option value="SC0030">계좌이체</option>				
							<option value="SC0040">무통장입금</option>
				</select></td>
            <tr>
                <td>
                <input type="submit" value="결제하기" /><br/>
                </td>
            </tr>
        </table>
    </div>
    </form:form>
</body>
</html>
