<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>주소 검색 결과</title>
</head>

<script language="javascript">

	function init() {
		
		var varRoadFullAddr  = "${roadFullAddr}";
		var varRoadAddrPart1 = "${roadAddrPart1}";
		var varRoadAddrPart2 = "${roadAddrPart2}";
		var varEngAddr    = "${engAddr}";
		var varJibunAddr  = "${jibunAddr}";
		var varZipNo      = "${zipNo}";
		var varAddrDetail = "${addrDetail}";
		var varAdmCd   = "${admCd}";
		var varRnMgtSn = "${rnMgtSn}";
		var varBdMgtSn = "${bdMgtSn}";
		var varDetBdNmList = "${detBdNmList}";
		var varBdNm   = "${bdNm}";
		var varBdKdcd = "${bdKdcd}";
		var varSiNm   = "${siNm}";
		var varSggNm  = "${sggNm}";
		var varEmdNm  = "${emdNm}";
		var varLiNm   = "${liNm}";
		var varRn     = "${rn}";
		var varUdrtYn   = "${udrtYn}";
		var varBuldMnnm = "${buldMnnm}";
		var varBuldSlno = "${buldSlno}";
		var varMtYn     = "${mtYn}";
		var varLnbrMnnm = "${lnbrMnnm}";
		var varLnbrSlno = "${lnbrSlno}";
		var varEmdNo    = "${emdNo}";
		
		document.write("01. roadFullAddr = " + varRoadFullAddr);
		document.write("<br/>");
		document.write("02. roadAddrPart1 = " + varRoadAddrPart1);
		document.write("<br/>");
		document.write("03. roadAddrPart2 = " + varRoadAddrPart2);
		document.write("<br/>");
		document.write("04. engAddr = " + varEngAddr);
		document.write("<br/>");
		document.write("05. jibunAddr = " + varJibunAddr);
		document.write("<br/>");
		document.write("06. zipNo = " + varZipNo);
		document.write("<br/>");
		document.write("07. addrDetail = " + varAddrDetail);
		document.write("<br/>");
		document.write("08. admCd = " + varAdmCd);
		document.write("<br/>");
		document.write("09. rnMgtSn = " + varRnMgtSn);
		document.write("<br/>");
		document.write("10. bdMgtSn = " + varBdMgtSn);
		document.write("<br/>");
		document.write("11. detBdNmList = " + varDetBdNmList);
		document.write("<br/>");
		document.write("12. bdNm = " + varBdNm);
		document.write("<br/>");
		document.write("13. bdKdcd = " + varBdKdcd);
		document.write("<br/>");
		document.write("14. siNm = " + varSiNm);
		document.write("<br/>");
		document.write("15. sggNm = " + varSggNm);
		document.write("<br/>");
		document.write("16. emdNm = " + varEmdNm);
		document.write("<br/>");
		document.write("17. liNm = " + varLiNm);
		document.write("<br/>");
		document.write("18. rn = " + varRn);
		document.write("<br/>");
		document.write("19. udrtYn = " + varUdrtYn);
		document.write("<br/>");
		document.write("20. buldMnnm = " + varBuldMnnm);
		document.write("<br/>");
		document.write("21. buldSlno = " + varBuldSlno);
		document.write("<br/>");
		document.write("22. mtYn = " + varMtYn);
		document.write("<br/>");
		document.write("23. lnbrMnnm = " + varLnbrMnnm);
		document.write("<br/>");
		document.write("24. lnbrSlno = " + varLnbrSlno);
		document.write("<br/>");
		document.write("25. emdNo = " + varEmdNo);		
		
	}
	
</script>

<body onload="init();">
	
</body>
</html>
