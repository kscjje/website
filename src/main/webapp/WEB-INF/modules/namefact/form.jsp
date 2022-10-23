<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="namefact_count" value="0" scope="request"/>
<div id="modules_namefact" class="pagemaker mode_${fn:length(fn:trim(param.mode)) <= 0 ? 'normal' : param.mode}">
	
	<input type="hidden" name="namefact_result" id="namefact_result" value="<c:out value="${namefactData != null ? 'Y' : 'N'}"/>"/>
	
	<c:if test="${param.message != 'N'}">
		<div class="indent1">
			<ul>
				<li>본인 인증 방법을 선택해 주세요.</li>
				<li>본인인증 방법 선택 후 팝업창이 나타나지 않으면 브라우저의 팝업차단을 해제해 주시기 바랍니다.</li>
			</ul>
		</div>
	</c:if>
	
	<c:choose>
		<%-- 본인인증 정보가 있을 경우 --%>
		<c:when test="${namefactData != null}">
			<h4><span>본인인증 완료</span></h4>
			<p class="indent2">
				<strong>성명</strong> : <c:out value="${namefactData.name}"/>
				<br/><strong>성별</strong> : <c:out value="${namefactData.sex == 'M' ? '남성' : '여성'}"/>
				<br/><strong>생년월일</strong> : <fmt:formatDate value="${namefactData.birthday}" pattern="yyyy년 M월 d일"/>
			</p>
			<c:choose>
				<c:when test="${param.end != null && param.end != ''}">
					<script type="text/javascript">
					//<![CDATA[
					var url = '',
						end_url = '<c:out value="${param.end}" escapeXml="false"/>';
					if (self.opener)
						url = self.opener.location.href;
					else if (self.parent)
						url = self.parent.location.href;
					else
						url = top.location.href;
					
					if (url.indexOf(end_url) < 0)
					{
						if (self.opener)
							self.opener.location.href = end_url;
						else if (self.parent)
							self.parent.location.href = end_url;
						else
							top.location.href = end_url;
					}
					//]]>
					</script>
				</c:when>
			</c:choose>
		</c:when>
		
		<%-- 본인인증 정보가 없을 경우 --%>
		<c:otherwise>
			<%-- G-PIN : 공공아이핀 --%>
			<c:if test="${namefactModuleInfo.properties.gpin_use == 'Y'}">
				<c:set var="namefact_count" value="${namefact_count + 1}" scope="request"/>
			</c:if>
			<%-- siren I-PIN : 서울신용평가정보원 : 아이핀 본인인증 --%>
			<c:if test="${namefactModuleInfo.properties.siren_ipin_use == 'Y'}">
				<c:set var="namefact_count" value="${namefact_count + 1}" scope="request"/>
			</c:if>
			<%-- siren mobile : 서울신용평가정보원 : 휴대전화 본인인증 --%>
			<c:if test="${namefactModuleInfo.properties.siren_mobile_use == 'Y'}">
				<c:set var="namefact_count" value="${namefact_count + 1}" scope="request"/>
			</c:if>
			<%-- checkplus IPIN : NICE평가정보 : IPIN 본인인증 --%>
			<c:if test="${namefactModuleInfo.properties.checkplus_ipin_use == 'Y'}">
				<c:set var="namefact_count" value="${namefact_count + 1}" scope="request"/>
			</c:if>
			<%-- checkplus mobile : NICE평가정보 : 휴대전화 본인인증 --%>
			<c:if test="${namefactModuleInfo.properties.checkplus_mobile_use == 'Y'}">
				<c:set var="namefact_count" value="${namefact_count + 1}" scope="request"/>
			</c:if>
			<%-- kmcis mobile : 한국모바일인증 : 휴대전화 본인인증 --%>
			<c:if test="${namefactModuleInfo.properties.kmcis_mobile_use == 'Y'}">
				<c:set var="namefact_count" value="${namefact_count + 1}" scope="request"/>
			</c:if>
			
			
			
			
		
			<%-- G-PIN : 공공아이핀 --%>
			<c:if test="${namefactModuleInfo.properties.gpin_use == 'Y'}">
				<jsp:include page="form__GPIN.jsp"/>
			</c:if>
			
			<%-- siren I-PIN : 서울신용평가정보원 : 아이핀 본인인증 --%>
			<c:if test="${namefactModuleInfo.properties.siren_ipin_use == 'Y'}">
				<jsp:include page="form__siren-IPIN.jsp"/>
			</c:if>
			
			<%-- siren mobile : 서울신용평가정보원 : 휴대전화 본인인증 --%>
			<c:if test="${namefactModuleInfo.properties.siren_mobile_use == 'Y'}">
				<jsp:include page="form__siren-mobile.jsp"/>
			</c:if>
			
			<%-- checkplus IPIN : NICE평가정보 : IPIN 본인인증 --%>
			<c:if test="${namefactModuleInfo.properties.checkplus_ipin_use == 'Y'}">
				<jsp:include page="form__checkplus-IPIN.jsp"/>
			</c:if>
			
			<%-- checkplus mobile : NICE평가정보 : 휴대전화 본인인증 --%>
			<c:if test="${namefactModuleInfo.properties.checkplus_mobile_use == 'Y'}">
				<jsp:include page="form__checkplus-mobile.jsp"/>
			</c:if>
			
			<%-- kmcis mobile : 한국모바일인증 : 휴대전화 본인인증 --%>
			<c:if test="${namefactModuleInfo.properties.kmcis_mobile_use == 'Y'}">
				<jsp:include page="form__kmcis-mobile.jsp"/>
			</c:if>
			
			<c:if test="${namefact_count == 0}">
				<p>
					사용 가능한 본인인증이 없습니다.
					<br/>관리자께 문의 바랍니다.
					<br/><br/>
					<a class="button action_cancel large" href="javascript:history.back()"><span>이전페이지로 돌아가기</span></a>
				</p>
			</c:if>
		</c:otherwise>
	</c:choose>
</div>
<script type="text/javascript">
//<![CDATA[
if (document.title == '') document.title = '본인인증';

if (typeof(top.crc_id) != 'undefined')
{
	var crc_id = '<%=com.hisco.cmm.modules.CryptoUtil.getMD5Hash(session.getId())%>';
	
	if (top.crc_id != crc_id)
	{
		alert('내부 처리 정보(crc_id)가 일치하지 않습니다.\n새로고침(F5)을 하셔서 다시시도해주시기 바랍니다.');
		$(document.body).empty().append('<p>내부 처리 정보(crc_id)가 일치하지 않습니다.<br/>새로고침(F5)을 하셔서 다시 시도 해주시기 바랍니다.</p>');
	}
}
//]]>
</script>
<noscript>
	자바스크립트를 활성화하셔야만 모든 기능을 사용하실 수 있습니다. 
</noscript>


