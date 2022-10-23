<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%

	String strScheme     = request.getScheme();
	String strProtocol   = request.getProtocol();
	String strServerName = request.getServerName();
	int    intServerPort = request.getServerPort();
	String strServerUrl  = strScheme + "://" + strServerName + ":" + intServerPort + request.getContextPath() ;
	String strServerWWWUrl = "http://www.hisco.co.kr/web/main";

	System.out.println("----------------------------------------------Step.1.S");
	System.out.println("strScheme     = " + strScheme);
	System.out.println("strProtocol   = " + strProtocol);
	System.out.println("strServerName = " + strServerName);
	System.out.println("intServerPort = " + intServerPort);
	System.out.println("strServerUrl  = " + strServerUrl);
	System.out.println("strServerWWWUrl  = " + strServerWWWUrl);
	System.out.println("----------------------------------------------Step.1.E");

	//out.println("<script>top.location.href='" + strServerUrl + "/web/intro';</script>");
	out.println("<script>top.location.href='" + strServerUrl + "/web/main';</script>");

%>