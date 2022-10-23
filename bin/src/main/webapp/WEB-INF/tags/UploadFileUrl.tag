<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="filePath" rtexprvalue="true" %>
<%@ attribute name="fileName" rtexprvalue="true" %>
<%@ attribute name="originName" rtexprvalue="true" %>
<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT;
%>
<%= strContext %>/common/file/view/<%=filePath %><%=fileName%>?originName=<%=java.net.URLEncoder.encode(originName,"UTF-8")%>