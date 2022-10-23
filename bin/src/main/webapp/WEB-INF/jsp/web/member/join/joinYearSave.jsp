<%
 /**
  * @Class Name : joinYearSave.jsp
  * @Description : 연간회원  저장 페이지
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

<form:form commandName="memberYearVO" name="memberYearVO" id="memberYearVO" action="${resultUrl }" method="post">
<form:hidden path="memNo" />
<form:hidden path="itemCd" />
<textarea name="resultMsg" style="display:none">${resultMsg }</textarea>
<input type="hidden" name="resultFlag" value="${resultFlag }"/>
</form:form>
<script>
	document.memberYearVO.submit();
</script>