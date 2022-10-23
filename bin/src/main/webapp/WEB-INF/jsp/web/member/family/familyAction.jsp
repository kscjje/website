<%
 /**
  * @Class Name : familyAction.jsp
  * @Description : 유료회원 전환  성공
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.11.03  김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.11.03
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<form:form commandName="memberVO" name="memberVO" id="memberVO" action="./familyResult" method="post">
<form:hidden path="memNo" />
<input type="hidden" value="${resultMsg}" name="resultMsg"/>
</form:form>
<script>
$('#memberVO').submit();
</script>

