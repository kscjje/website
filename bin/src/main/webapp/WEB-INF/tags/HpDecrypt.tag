<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="param" rtexprvalue="true" %>

<%
	//String strEncodeVal = com.hisco.cmm.modules.StringUtil.URLEncode(param);
	String strEncodeVal = param;

	String strSpowiseCmsKey = egovframework.com.cmm.service.EgovProperties.getProperty("Globals.SpowiseCms.Key");

	String strDecReValue = strEncodeVal.replace("$2F", "/");
	
	String strDecryptedString = com.hisco.cmm.util.WebEncDecUtil.fn_decrypt(strDecReValue, strSpowiseCmsKey);
	
	if(strDecryptedString != null) { 

		if (strDecryptedString.length() == 11) {
			
			strDecryptedString = strDecryptedString.substring(0,3) + "-" + strDecryptedString.substring(3,7) + "-" + strDecryptedString.substring(7,11);
			
		} else if(strDecryptedString.startsWith("02") && strDecryptedString.length() > 8) {
			
			strDecryptedString = strDecryptedString.substring(0,2) + "-" + strDecryptedString.substring(2,strDecryptedString.length()-4) + "-" + strDecryptedString.substring(strDecryptedString.length()-4);
			
		} else if(strDecryptedString.length() == 10) {
			
			strDecryptedString = strDecryptedString.substring(0,3) + "-" + strDecryptedString.substring(3,6) + "-" + strDecryptedString.substring(6);
		}
	
		out.println(strDecryptedString);		
	} else {
		out.println(param);
	}

%>