<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="param" rtexprvalue="true" %>
<%
	String strEncodeUrl = com.hisco.cmm.modules.StringUtil.URLEncode(param);
%>
/webadm/wasresource/resourceList?dir=<%=strEncodeUrl%>