<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%

	//System.out.println("call returnurl.jsp");

	request.setCharacterEncoding("euc-kr");

	String LGD_RESPCODE = request.getParameter("LGD_RESPCODE");
	String LGD_RESPMSG 	= request.getParameter("LGD_RESPMSG");

	Map payReqMap = request.getParameterMap();

	//System.out.println("payReqMap = ");
	//System.out.println(payReqMap);

	//System.out.println("LGD_RESPCODE = ");
	//System.out.println(LGD_RESPCODE);

	//System.out.println("LGD_RESPMSG = ");
	//System.out.println(LGD_RESPMSG);

%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
	<script type="text/javascript">

		function setLGDResult() {

			if (opener) {

				//alert("call opener.payment_return()");
				opener.payment_return();

			} else {

				//alert("call parent.payment_return()");
				parent.payment_return();
			}

			try {

			} catch (e) {
				alert(e.message);
			}

		}


	</script>
</head>
<body onload="setLGDResult()">
<p><h1>결제 처리 중....</h1></p>
	<form method="post" name="LGD_RETURNINFO" id="LGD_RETURNINFO">

		<%
			for (Iterator i = payReqMap.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				if (payReqMap.get(key) instanceof String[]) {
					String[] valueArr = (String[])payReqMap.get(key);
					for(int k = 0; k < valueArr.length; k++)
						out.println("<input type='hidden'  name='" + key + "' id='"+key+"'value='" + valueArr[k] + "'/>");
				} else {
					String value = payReqMap.get(key) == null ? "" : (String) payReqMap.get(key);
					out.println("<input type='hidden' name='" + key + "' id='"+key+"'value='" + value + "'/>");
				}
			}
		%>

	</form>

</body>
</html>