<%
 /**
  * @Class Name : prsonCertiPop.jsp
  * @Description : 본인인증  호출
  * @Modification Information
  * @
  * @  수정일             수정자                 수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.03   전영석                최초 생성
  *
  *  @author 진수진
  *  @since 2020.08.18
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
	Map payReqMap = (Map)request.getAttribute("payReqMap");

	System.out.println("[prsonCertiPop.jsp] payReqMap = " + payReqMap);

%>

<%
	if ("test".equals(payReqMap.get("CST_PLATFORM"))) { 
%>
	<script language="javascript" src="https://pretest.uplus.co.kr:9443/xpay/js/xpay_crossplatform.js" type="text/javascript"></script>
	
<%	} else { %>
		<script src="https://xpay.uplus.co.kr/xpay/js/xpay_crossplatform.js"></script>
<%
	} 
%>

<form method="post" name ="LGD_PAYINFO" id="LGD_PAYINFO">

	<fieldset>
		<legend>휴대전화 본인인증</legend>
		
			<%
				for(Iterator i = payReqMap.keySet().iterator(); i.hasNext();) {
					Object key = i.next();
					out.println("<input type='hidden' name='" + key + "' id='"+key+"' value='" + payReqMap.get(key) + "'>" );
				}
			%>
			
	</fieldset>
	
</form>

<script type="text/javascript">

	/*
	* 수정불가.
	*/
	var LGD_window_type = 'submit';
	
	/*
	* 수정불가.
	*/
	function launchCrossPlatform() {
		lgdwin = openAuthonly( document.getElementById('LGD_PAYINFO'), document.getElementById('CST_PLATFORM').value, LGD_window_type, null );
	}
	
	/*
	* FORM 명만  수정 가능
	*/
	function getFormObject() {
		return document.getElementById("LGD_PAYINFO");
	}
	
	launchCrossPlatform();
	
</script>