<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<c:choose>
	<c:when test="${param.mode == 'button'}">
		<form id="GPIN_namefact_form" class="${namefact_count == 1 ? 'single' : ''}" target="GPIN_namefact_window" method="post" action="<c:out value="${namefactModuleInfo.properties.gpin_request}"/>">
			<fieldset>
				<legend>G-PIN(공공I-PIN) 본인인증</legend>
				
				<p class="txtcenter">
					<button type="submit" class="button action_check gpin" title="팝업창 열림"><span><strong>G-PIN(공공I-PIN)</strong> 본인인증</span></button>
				</p>
			</fieldset>
		</form>
	</c:when>
	<c:otherwise>
		<div id="namefact_type namefact_type__GPIN">
			<h4><span>G-PIN(공공I-PIN)</span></h4>
			<div class="proc_form">
				<h5><span>G-PIN(공공I-PIN)이란?</span></h5>
				<p class="indent3">
					G-PIN은 Government Personal Identification Number의 약자로, 인터넷상 개인 식별번호를 의미합니다.
					<br/>G-PIN은 대면확인이 어려운 인터넷에서 주민등록번호를 사용하지 않고도 본인임을 확인할 수 있는 수단입니다.
				</p>
				<ul class="indent3 list">
					<li>G-PIN을 이용하시려면 공공I-PIN센터(<a href="http://g-pin.go.kr" target="_blank" title="새창열림">http://g-pin.go.kr</a>)에 회원가입 후 이용하시면 됩니다.</li>
					<li>
						진행방법<br/>
						<span class="proc_txt start">&quot;공공I-PIN인증&quot; 버튼클릭</span><span class="proc_arrow"> → </span><span class="proc_txt line1">새(팝업)창이동</span><span class="proc_arrow"> → </span><span class="proc_txt">아이디, 비밀번호 입력</span><span class="proc_arrow"> → </span><span class="proc_txt line1">로그인 확인</span><span class="proc_arrow"> → </span><span class="proc_txt line1">새(팝업)창 닫힘</span><span class="proc_arrow"> → </span><span class="proc_txt end">현재창 다음페이지로 이동</span>
					</li>
					<li>문의 : <c:out value="${namefactModuleInfo.properties.gpin_inquiry}"/></li>
				</ul>
				
				<form id="GPIN_namefact_form" class="${namefact_count == 1 ? 'single' : ''}" target="GPIN_namefact_window" method="post" action="<c:out value="${namefactModuleInfo.properties.gpin_request}"/>">
					<fieldset>
						<legend>G-PIN(공공I-PIN) 본인인증</legend>
						
						<p class="txtcenter">
							<button type="submit" class="button action_check gpin large" title="팝업창 열림"><span>G-PIN(공공I-PIN) 본인인증</span></button>
						</p>
					</fieldset>
				</form>
			</div>
		</div>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
//<![CDATA[
jQuery(document).ready(function()
{
	$('#GPIN_namefact_form').submit(function(e)
	{
		var form = $(this),
			target = form.attr('target'),
			win = window.open('', target, 'width=360, height=120, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=100, top=50');
		
		if (win == null)
		{
			alert('** G-PIN 본인인증 **\n팝업 차단을 해제해주시기 바랍니다.\n\n ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ MSN,야후,구글 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.');
			return false;
		}
		else
		{
			form.submit();
			win.focus();
		}
	});
});
//]]>
</script>