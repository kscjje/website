<%
 /**
  * @Class Name : succcessPop.jsp
  * @Description : 본인인증  성공
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.18   진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.08.18
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>

<%

	System.out.println("call [succcessPop.jsp]");

%>

<script>

	alert("본인인증이 완료되었습니다.");
	
	var varInputUserId = sessionStorage.getItem("inputVal");
	
	/* 
	opener.fn_hphone_complete('<c:out value="${namefactData.tel}" />');
	self.close();
	*/
	
</script>