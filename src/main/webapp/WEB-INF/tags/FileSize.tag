<%@ tag pageEncoding="utf-8" %>
<%@ attribute name="fileSize" rtexprvalue="true" %>
<%
	long fileSizeByte = Long.parseLong(fileSize);
	java.text.DecimalFormat oneDecimal = new java.text.DecimalFormat("0.0");
	long absNumber = java.lang.Math.abs(fileSizeByte);
	double result = fileSizeByte;
	String suffix = "";
	if (absNumber < 1024) {
	  oneDecimal = new java.text.DecimalFormat("0");
	  suffix = "Byte";
	} else if (absNumber < 1024 * 1024) {
	  result = fileSizeByte / 1024.0;
	  suffix = "KB";
	} else if (absNumber < 1024 * 1024 * 1024) {
	  result = fileSizeByte / (1024.0 * 1024);
	  suffix = "MB";
	} else {
	  result = fileSizeByte / (1024.0 * 1024 * 1024);
	  suffix = "GB";
	}
%><%=oneDecimal.format(result) + suffix%>