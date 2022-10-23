<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%

	//System.out.println("call returnurl_mobile.jsp");

	request.setCharacterEncoding("euc-kr");

	String LGD_RESPCODE = request.getParameter("LGD_RESPCODE");
	String LGD_RESPMSG 	= request.getParameter("LGD_RESPMSG");
	String LGD_PRODUCTCODE 	= request.getParameter("LGD_PRODUCTCODE");

	Map payReqMap = request.getParameterMap();
	String gubun = (String)session.getAttribute("LGD_PRODUCTCODE");

	if (gubun == null) gubun ="";

	String returnURL = "/member/join/joinYearSave";

	if (LGD_PRODUCTCODE == null) {
		 //취소 되었을 경우 파라미터가 없다
		 if (gubun.equals("")) {
			 returnURL = "/web/main" ;
		 } else {
			 returnURL = "/web/mypage/myRsvn/myRsvnList?gubun=" + gubun;
		 }

	} else if (LGD_PRODUCTCODE.equals("EXBT") ||LGD_PRODUCTCODE.equals("EDC")||LGD_PRODUCTCODE.equals("EVT") ) {
		 returnURL = "/mypage/myRsvn/myRsvnListSave";
	}

%>

<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
	<script type="text/javascript">

		function setLGDResult() {
			<%if (LGD_PRODUCTCODE == null) {%>
			alert("결제가 취소되었습니다.");
			window.location.replace("<%=returnURL%>");
			<%} else {%>
				document.LGD_RETURNINFO.submit();
			<%}%>
		}
	</script>
</head>
<body onload="setLGDResult()">

<p><h1>결제 처리 중...</h1></p>
	<form method="post" name="LGD_RETURNINFO" id="LGD_RETURNINFO" action="${domain }<%=returnURL%>">

		<%
		if (payReqMap != null) {
			for (Iterator i = payReqMap.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				if (payReqMap.get(key) instanceof String[]) {
					String[] valueArr = (String[])payReqMap.get(key);
					for(int k = 0; k < valueArr.length; k++)
						out.println("<input type='hidden'  name='" + key + "' id='"+key+"' value='" + valueArr[k] + "'/>");
				} else {
					String value = payReqMap.get(key) == null ? "" : (String) payReqMap.get(key);
					out.println("<input type='hidden'  name='" + key + "' id='"+key+"' value='" + value + "'/>");
				}
			}
		}
		%>

	</form>

</body>
</html>