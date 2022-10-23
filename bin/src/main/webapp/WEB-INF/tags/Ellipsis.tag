<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="source" rtexprvalue="true" %>
<%@ attribute name="length" rtexprvalue="true" %>
<%
out.print(com.hisco.cmm.util.StringUtil.subStrBytes(source, Integer.parseInt(length)));
%>