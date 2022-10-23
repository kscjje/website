<%
 /**
  * @Class Name : resultAjax.jsp
  * @Description : 본인인증  리턴
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.27   진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.08.27
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.hisco.cmm.util.Config" %>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%

String LGD_RESPCODE = (String)request.getAttribute("LGD_RESPCODE");
String LGD_RESPMSG 	= (String)request.getAttribute("LGD_RESPMSG");

%>

<p><h1>RETURN_URL (인증결과)</h1></p>
<div>
<p>LGD_RESPCODE (결과코드) : <%= LGD_RESPCODE %></p>
<p>LGD_RESPMSG (결과메시지): <%= LGD_RESPMSG %></p>

<c:choose>

	<c:when test="${LGD_RESPCODE eq '0000'}">
	
		<form name="resultForm" id="resultForm" method="POST">
			<input type="hidden" name="LGD_MID" id="LGD_MID" value="<c:out value="${LGD_MID}"/>" />
			<input type="hidden" name="LGD_AUTHONLYKEY" id="LGD_AUTHONLYKEY" value="<c:out value="${LGD_AUTHONLYKEY}"/>" />
			<input type="hidden" name="LGD_PAYTYPE" id="LGD_PAYTYPE" value="<c:out value="${LGD_PAYTYPE}"/>" />
		</form>
		
		<script>
			
			
			/* 
			if (opener.$('#cert_type').val() == "hpchange") {
				$('#resultForm').attr("action" , "<%=Config.USER_ROOT%>/member/join/mobileChangePop");
				$('#resultForm').submit();
			} else {
				opener.fn_auth_complete( $('#LGD_MID').val() , $('#LGD_AUTHONLYKEY').val() , $('#LGD_PAYTYPE').val());
			}
			*/
	
			self.close();
			
		</script>
		
	</c:when>
	<c:otherwise>
		<script>
			alert("본인인증이 실패했습니다.\n오류코드 : <c:out value="${LGD_RESPCODE}"/>");
			self.close();
		</script>
	</c:otherwise>

</c:choose>