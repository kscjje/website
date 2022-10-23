<%
 /**
  * @Class Name : mobileChangePop.jsp
  * @Description : 휴대전화 번호 변경시 본인인증  성공
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.28   진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.08.28
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
		<c:choose>
			<c:when test="${empty userInfo}">
				<script>
				alert("로그인 정보가 없습니다. 로그인을 다시 확인해 주세요.");
				self.close();
				</script>
			</c:when>
			<c:when test="${!empty memberVO}">
				<script>
				alert("본인인증이 완료되었습니다.");
				opener.fn_hphone_complete('<c:out value="${namefactData.tel}" />');
				self.close();
				</script>
			</c:when>
			<c:otherwise>
				<script>
				alert("로그인한 정보와 본인인증 정보가 일치하지 않습니다.");
				self.close();
				</script>
			</c:otherwise>
		</c:choose>
