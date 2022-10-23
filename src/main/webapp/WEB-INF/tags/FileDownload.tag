<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="param" rtexprvalue="true" %>
<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.ADMIN_ROOT;
	String strDownloadEncodeUrl = com.hisco.cmm.modules.StringUtil.URLEncode(param);
%>
<%= strContext %>/common/file/download?atchFileNm=<%=strDownloadEncodeUrl%>