<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="hpval" rtexprvalue="true" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%if(hpval.length() == 11){ %>
<%=hpval.substring(0,3) %>-<%=hpval.substring(3,7) %>-<%=hpval.substring(7,11) %>
<%}else if(hpval.startsWith("02") && hpval.length()  > 8){ %>
<%=hpval.substring(0,2) %>-<%=hpval.substring(2,hpval.length()-4) %>-<%=hpval.substring(hpval.length()-4) %>
<%}else if(hpval.length() == 10){ %>
<%=hpval.substring(0,3) %>-<%=hpval.substring(3,6) %>-<%=hpval.substring(6) %>
<%}else{ %>
<c:out value="<%=hpval %>"/>
<%} %>
