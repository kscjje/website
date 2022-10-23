<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% pageContext.setAttribute("crlf", "\r\n"); %>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<!-- 
 	파일명 : payreq_mainform.jsp
 	내  용 : toss payments 화면
-->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>SPOWISE CMS 결제</title>

<script src="<%=strContext %>/js/jquery-x.x.x.min.js"></script>
<script src="<%= strContext %>/js/common_nsm_pg.js"></script>

<script type="text/javascript">

function LPad(digit, size, attatch) {
	
    var add = "";
    
    digit = digit.toString();

    if (digit.length < size) {
        var len = size - digit.length;
        for (i = 0; i < len; i++) {
            add += attatch;
        }
    }
    
    return add + digit;
}

function fn_makeOId() {
	
	var now = new Date();
	var years = now.getFullYear();
	var months = LPad(now.getMonth() + 1, 2, "0");
	var dates = LPad(now.getDate(), 2, "0");
	var hours = LPad(now.getHours(), 2, "0");
	var minutes = LPad(now.getMinutes(), 2, "0");
	var seconds = LPad(now.getSeconds(), 2, "0");
	var timeValue = years + months + dates + hours + minutes + seconds;
	
	console.log("call fn_makeOId = " + timeValue);
	
	//document.getElementById("LGD_OID").value = "test_" + timeValue;
	document.getElementById("LGD_OID").value       = timeValue;
	document.getElementById("LGD_TIMESTAMP").value = timeValue;
}

/*
* 인증요청 처리 
*/
function fn_doPay() {
	
	//1 : 관람,   2 : 강연/행사/영화,   3 : 교육프로그램,  4 : 연회원
	//EXT : 관람, EVT : 강연/행사/영화, EDC : 교육프로그램 
	
	var varChareGrp = "EXT";
	
	var varRevItem01 = "2020082700001";
	var varRevItem02 = "2020082700003";
	var varRevItem03 = "2020082700009";
	
	var varRevItemAll = varRevItem01 + "," + varRevItem02 + "," + varRevItem03; 
	
	$("#COMCD").val("SCR");
	
	fn_ajaxIfOnly("/intrfc/charge/pgOrdMstSearch", {COMCD : "SCR", RESERITEMS : varRevItemAll, CHARGE_GRP : varChareGrp},  fn_callBackStep1);
	
	// OID, TIMESTAMP 생성
	//fn_makeOId();
	
	// 결제창 호출
	//document.getElementById("LGD_PAYINFO").submit();
	
}

function fn_callBackStep1(data) {
	
	console.log("call fn_callBackStep1()");
	console.log(data);
	
	var varDB_DROW = Number(data.RESULT.DB_DROW);

	console.log(varDB_DROW);
	
	if (varDB_DROW >= 1) {
		
		var varOID_STAT = data.RESULT.DB_DATA.oidStat;
		
		if (varOID_STAT == "1001") {  //거래요청

			alert("이미 거래 진행 중입니다. 개발 관련 추가적인 로직은 PL과 공유 바랍니다.");
			return;			
			
		} else if (varOID_STAT == "2001") {  //거래완료
			
			alert("이미 완료된 거래입니다.");
			return;
			
		} else if (varOID_STAT == "3001") {  //거래취소
			
			alert("거래취소 상태입니다. 개발 관련 추가적인 로직은 PL과 공유 바랍니다.");
			return;
			
		} else if (varOID_STAT == "4001") {  //거래실패
			
			alert("거래실패 상태입니다. 개발 관련 추가적인 로직은 PL과 공유 바랍니다.");
			return;
			
		}
		
		console.log(varOID_STAT);
		
	} else {
		
		fn_makeOId();
		
		var varLGD_OID       = document.getElementById("LGD_OID").value;
		var varLGD_TIMESTAMP = document.getElementById("LGD_TIMESTAMP").value;		
		var varLGD_AMOUNT    = document.getElementById("LGD_AMOUNT").value;
		var varOID_STAT      = "1001";
		
		var varPrevParamInfor = data.RESULT;
		var varNewParamInfor  = {LGD_OID : varLGD_OID, LGD_TIMESTAMP : varLGD_TIMESTAMP, LGD_AMOUNT : varLGD_AMOUNT, OID_STAT : varOID_STAT};
		
		fn_ajaxIfOnly("/intrfc/charge/pgOrdMstSave", {PPARAM_INFOR : JSON.stringify(varPrevParamInfor), NPARAM_INFOR : JSON.stringify(varNewParamInfor)},  fn_callBackStep2);
		
	}
	
	/* 
	if (varPgOrdMstRow != "1") {
		
		fn_makeOId();
		
		var varLGD_OID = document.getElementById("LGD_OID").value;
		var varLGD_TIMESTAMP = document.getElementById("LGD_TIMESTAMP").value;		
		var varLGD_AMOUNT    = document.getElementById("LGD_AMOUNT").value;
		
		fn_ajaxIfOnly("/intrfc/charge/pgOrdMstSave", {COMCD : "SCR", LGD_OID : varLGD_OID, LGD_TIMESTAMP : varLGD_TIMESTAMP, LGD_AMOUNT : varLGD_AMOUNT, OID_STAT : "1001"},  fn_callBackStep2);
		
	} else {
		
		var varOrgDataCd = data.RESULT.OrgData[0].oidStat;
		
		console.log(varOrgDataCd);
		
		if (varOrgDataCd == "1001") {
			alert("이미 결제 요청중입니다.");
			return;
		} else if (varOrgDataCd == "2001") {
			alert("거래 완료된 요청입니다.");
			return;
		} else if (varOrgDataCd == "3001") {
			alert("거래 취소된 요청입니다.");
			return;
		} else if (varOrgDataCd == "4001") {
			alert("거래 실패된 요청입니다.");
			return;										
		}
		
	}
	*/
	/* 
	var varCountPerPage = data.results.common.countPerPage;
	var varCurrentPage  = data.results.common.currentPage;
	var varErrorCode    = data.results.common.errorCode;
	var varErrorMessage = data.results.common.errorMessage;
	
	var varJusoRsltH = "<table>";
		varJusoRsltH += "	<caption>주소</caption>";
		varJusoRsltH += "<thead>";			
		varJusoRsltH += "<tr>";
		varJusoRsltH += "<th class='cat'>우편번호</th>";	
		varJusoRsltH += "<th class='title'>주소</th>";
		varJusoRsltH += "</tr>";
		varJusoRsltH += "</thead>";
		varJusoRsltH += "<tbody>";
	var varJusoRsltB = ""; 
	var varJusoRsltT = "</tbody>";
	    varJusoRsltT += "</table>";

	if (varErrorCode == 0) {
		
		var varJuso = data.results.juso;
		
		var varZipNo     = "";
		var varJibunAddr = "";
		var varRoadAddr  = "";			
		for(var i = 0; i < varJuso.length; i++) {
			
			varZipNo     = varJuso[i].zipNo;
			varJibunAddr = varJuso[i].jibunAddr;
			varRoadAddr  = varJuso[i].roadAddr;
			
			varJusoRsltB += "<tr class='n_top'>";
			varJusoRsltB += "	<td class='cat'>" + varZipNo + "</td>";
			varJusoRsltB += "	<td class='title left'><a href='#' onclick=\"fn_jusoFSelect('" + varZipNo + "', '" + varRoadAddr +  "')\">지번 ▶ " + varJibunAddr + "</br>도로명 ▶ " + varRoadAddr + "</a></td>";
			varJusoRsltB += "</tr>";
			
		}	
		
		if (varJuso.length >= 1) {
		} else {
			
			varJusoRsltB += "<tr class='n_top'>";
			varJusoRsltB += "	<td class='cat' colspan='2'>주소 정보를 찾을 수 없습니다.</td>";
			varJusoRsltB += "	</td>";
			varJusoRsltB += "</tr>";				
			
		}		
		
		$("#jusoSearchResult").empty();
		$("#jusoPageApi").empty();
		$("#jusoSearchResult").show();
		$("#jusoSearchResult").append(varJusoRsltH + varJusoRsltB + varJusoRsltT);
	
		fn_PageMake(data);
		
	}
	*/
}

function fn_callBackStep2(data) {

	console.log("call fn_callBackStep2()");
	console.log(data);
	
	if (data.RESULT.FINAL_ACTION != "SUCCESS") {
	} else {
		document.getElementById("LGD_PAYINFO").submit();				
	}
	
	/* 
	var varPgOrdMstRow = data.RESULT.pgOrdMstRow; 
	
	if (varPgOrdMstRow == "1") {
		document.getElementById("LGD_PAYINFO").submit();
	}
	*/
	
}

</script>
</head>
<body>

<form method="post" id="LGD_PAYINFO" action="/intrfc/charge/reqNsmCharge">

	<input type="text" id="COMCD" name="COMCD" />

    <div>
        <table>
            <tr>
                <td>상점아이디(t를 제외한 아이디) </td>
                <td>
                	<input type="text" name="CST_MID" id="CST_MID" value="lgdacomxpay"/> 
                	<!-- <input type="text" name="CST_MID" id="CST_MID" value="nsm482_2"/> -->
                </td>
            </tr>
            <tr>
                <td>서비스,테스트 </td>
                <td>
                	<select name="CST_PLATFORM" id="CST_PLATFORM">
						<option value="test">test</option>
						<option value="service">service</option>
					</select>
                </td>
            </tr>
            <tr>
                <td>구매자 이름 </td>
                <td><input type="text" name="LGD_BUYER" id="LGD_BUYER" value="홍길동"/></td>
            </tr>
            <tr>
                <td>상품정보 </td>
                <td><input type="text" name="LGD_PRODUCTINFO" id="LGD_PRODUCTINFO" value="myLG070-인터넷전화기"/></td>
            </tr>
            <tr>
                <td>결제금액 </td>
                <td><input type="text" name="LGD_AMOUNT" id="LGD_AMOUNT" value="10"/></td>
            </tr>
            <tr>
                <td>구매자 이메일 </td>
                <td><input type="text" name="LGD_BUYEREMAIL" id="LGD_BUYEREMAIL" value=""/></td>
            </tr>
            <tr>
                <td>주문번호 </td>
                <td><input type="text" name="LGD_OID" id="LGD_OID" value="test_1234567890020"/></td>
            </tr>
              <tr>
                <td>타임스탬프 </td>
                <td><input type="text" name="LGD_TIMESTAMP" id="LGD_TIMESTAMP" value="1234567890"/></td>
            </tr>
            
            <tr>
                <td>초기결제수단 </td>
                <td>
                	<select name="LGD_CUSTOM_USABLEPAY" id="LGD_CUSTOM_USABLEPAY">
						<option value="SC0010">신용카드</option>				
						<option value="SC0030">계좌이체</option>
										
						<!-- <option value="SC0040">무통장입금</option>				
						<option value="SC0060">휴대폰</option>				
						<option value="SC0070">유선전화결제</option>				
						<option value="SC0090">OK캐쉬백</option>				
						<option value="SC0111">문화상품권</option>				
						<option value="SC0112">게임문화상품권</option>	
						-->
									
					</select>
				</td>
			</tr>
            <tr>
                <td>결제창 호출 방식 </td>
                <td>
                	<select name="LGD_WINDOW_TYPE" id="LGD_WINDOW_TYPE"> 
                	    <option value="iframe" selected>iframe</option>
						<!-- <option value="SUBMIT">SUBMIT</option>  -->
					</select>
				</td>
            </tr>
            <tr>
                <td>신용카드 카드사 인증 페이지 연동 방식 </td>
                <td>
                	<select name="LGD_CUSTOM_SWITCHINGTYPE" id="LGD_CUSTOM_SWITCHINGTYPE">
					<!-- <option value="SUBMIT">SUBMIT</option> -->
						<option value="IFRAME" selected>IFRAME</option>
					</select>
				</td>
            </tr>
            <tr>
                <td colspan="2">
                <input type="button" value="결제하기" onclick="fn_doPay();" /><br/>
                </td>
            </tr>
        </table>
    </div>
    </form>
</body>
</html>

