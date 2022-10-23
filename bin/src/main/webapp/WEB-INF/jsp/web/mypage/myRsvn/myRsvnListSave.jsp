<%
 /**
  * @Class Name : myRsvnYearSave.jsp
  * @Description : 결제  저장 페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.08    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.09.08
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>

<form:form commandName="myRsvnVO" name="myRsvnVO" id="myRsvnVO" action="${resultUrl }" method="post">
<form:hidden path="LGD_OID" />
<form:hidden path="LGD_PRODUCTCODE" />
<textarea name="resultMsg" style="display:none">${resultMsg }</textarea>
<input type="hidden" name="resultFlag" value="${resultFlag }"/>
</form:form>
<script>
	document.myRsvnVO.submit();
</script>