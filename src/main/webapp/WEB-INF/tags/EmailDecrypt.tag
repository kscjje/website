<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="param" rtexprvalue="true" %>

<%
	//String strEncodeVal = com.hisco.cmm.modules.StringUtil.URLEncode(param);
	String strEncodeVal = param;

	String strSpowiseCmsKey = egovframework.com.cmm.service.EgovProperties.getProperty("Globals.SpowiseCms.Key");

	String strDecReValue = strEncodeVal.replace("$2F", "/");
	
	String strDecryptedString = com.hisco.cmm.util.WebEncDecUtil.fn_decrypt(strDecReValue, strSpowiseCmsKey);
	
	if(strDecryptedString == null){
		out.println(param);
	} else {
		out.println(strDecryptedString);
	}
	
%>