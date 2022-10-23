<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="param1" rtexprvalue="true" %>
<%@ attribute name="param2" rtexprvalue="true" %>

<%
	String strEncodeVal = param2;

	String strSpowiseCmsKey = egovframework.com.cmm.service.EgovProperties.getProperty("Globals.SpowiseCms.Key");

	String strDecReValue = strEncodeVal.replace("$2F", "/");
	
	String strDecryptedString = com.hisco.cmm.util.WebEncDecUtil.fn_decrypt(strDecReValue, strSpowiseCmsKey);
	
	if (strDecryptedString == null) {
		out.println(param2);
	} else {
		
		String strEMailDivide[] = strDecryptedString.split("@");
		
		if(strEMailDivide != null) {

			if ("1".equals(param1)) {
				out.println(strEMailDivide[0]);	
			} else if ("2".equals(param1)) {
				out.println(strEMailDivide[1]);
			}			
			
		} else {
			out.println("");
		}
	
	}
%>