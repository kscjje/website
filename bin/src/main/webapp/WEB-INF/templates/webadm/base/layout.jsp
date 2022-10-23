<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<tiles:insertAttribute name="header" />

<body class="hold-transition sidebar-mini text-sm">

    <div class="wrapper">
		<tiles:insertAttribute name="left" />
	<div class="content-wrapper">
		<tiles:insertAttribute name="body" />
	</div>
		<tiles:insertAttribute name="footer" />
  	<div class="control-sidebar-bg"></div>
  	
</div>

</body>

</html>