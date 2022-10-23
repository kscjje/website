<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%
	WebApplicationContext wapContext = org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	Object dynamicConfig = wapContext.getBean("dynamicConfig");
	String adminRoot = dynamicConfig != null ? ((com.hisco.cmm.config.DynamicConfig)dynamicConfig).getAdminRoot() : com.hisco.cmm.util.Config.ADMIN_ROOT;

	String strScheme     = request.getScheme();
	String strProtocol   = request.getProtocol();
	String strServerName = request.getServerName();
	int    intServerPort = request.getServerPort();
	String strServerUrl  = strScheme + "://" + strServerName + ":" + intServerPort;
	String strServerWWWUrl = "http://www.hisco.co.kr"+adminRoot+"/login";

	System.out.println("----------------------------------------------Step.1.S");
	System.out.println("strScheme     = " + strScheme);
	System.out.println("strProtocol   = " + strProtocol);
	System.out.println("strServerName = " + strServerName);
	System.out.println("intServerPort = " + intServerPort);
	System.out.println("strServerUrl  = " + strServerUrl);
	System.out.println("strServerWWWUrl  = " + strServerWWWUrl);
	System.out.println("----------------------------------------------Step.1.E");
	
	if (("localhost".equals(strServerName)) || ("127.0.0.1".equals(strServerName)) || ("211.240.5.167".equals(strServerName))) {
		out.println("<script>top.location.href='" + strServerUrl + adminRoot +"/login';</script>");
	} else if ("192.128.16.56".equals(strServerName)) {
		out.println("<script>top.location.href='" + strServerWWWUrl + "';</script>");
	}
	
%>