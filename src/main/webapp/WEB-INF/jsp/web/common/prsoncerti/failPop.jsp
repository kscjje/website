<%
 /**
  * @Class Name : failPop.jsp
  * @Description : 본인인증  실패
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
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<script type="text/javascript">
// <![CDATA[
	alert("본인인증이 실패하였습니다.\n다시 본인인증을 시도해주시기 바랍니다.\n현재 창은 닫힙니다.");
	self.close();
// ]]>
</script>