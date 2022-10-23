<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import ="java.util.*, java.text.SimpleDateFormat"%>
<%@ page import ="com.knsoft.kntool.SETUP.ModuleInfoDto"%>
<%@ page import ="com.knsoft.support.*"%>

<%
// 모듈 정보
ModuleInfoDto namefactModuleInfo = (ModuleInfoDto) request.getAttribute("namefactModuleInfo");
String checkplus_ipin_response_success = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_ipin_response_success") ? (String) namefactModuleInfo.getProperties().get("checkplus_ipin_response_success") : "";
String checkplus_ipin_response_fail = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_ipin_response_fail") ? (String) namefactModuleInfo.getProperties().get("checkplus_ipin_response_fail") : "";

// 도메인 및 컨텍스트 경로
RequestUtil req = RequestUtil.getInstance(request);
String base_url = req.getLocationRoot();


// NICE로부터 부여받은 사이트 코드
String sSiteCode = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_ipin_sitecode") ? (String) namefactModuleInfo.getProperties().get("checkplus_ipin_sitecode") : "";
// NICE로부터 부여받은 사이트 패스워드
String sSitePassword = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_ipin_password") ? (String) namefactModuleInfo.getProperties().get("checkplus_ipin_password") : "";


// CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
// 성공시 이동될 URL
String sReturnUrl = base_url.concat(checkplus_ipin_response_success.startsWith("/") ? checkplus_ipin_response_success.substring(1) : checkplus_ipin_response_success);

// 실패시 이동될 URL
String sErrorUrl = base_url.concat(checkplus_ipin_response_fail.startsWith("/") ? checkplus_ipin_response_fail.substring(1) : checkplus_ipin_response_fail);




Kisinfo.Check.IPINClient pClient = new Kisinfo.Check.IPINClient();

// CP 요청번호
String sCPRequest = pClient.getRequestNO(sSiteCode);
session.setAttribute("CPREQUEST" , sCPRequest);

// Method 결과값(iRtn)에 따라 진행 여부 확인
int iRtn = pClient.fnRequest(sSiteCode, sSitePassword, sCPRequest, sReturnUrl);

// 처리 메시지
String sRtnMsg = null;
// 암호화된 데이터
String sEncData = null;


// Method 결과값(iRtn)에 따른 처리사항
if (iRtn == 0) {
	// fnRequest 함수 처리시 업체정보를 암호화한 데이터를 추출합니다.
	// 추출된 암호화된 데이타는 당사 팝업 요청시, 함께 보내주셔야 합니다.
	// 암호화 된 데이타
	sEncData = pClient.getCipherData();
	//sRtnMsg = "정상 처리되었습니다.";
} else if (iRtn == -1 || iRtn == -2) {
	sRtnMsg = "배포해 드린 서비스 모듈 중, 귀사 서버환경에 맞는 모듈을 이용해 주시기 바랍니다.<br/>귀사 서버환경에 맞는 모듈이 없다면 <strong>iRtn 값, 서버 환경정보를 정확히 확인하여 메일로 요청해 주시기 바랍니다.</strong>";
} else if (iRtn == -9) {
	sRtnMsg = "입력값 오류 : fnRequest 함수 처리시, 필요한 4개의 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.";
} else {
	sRtnMsg = "iRtn 값 확인 후, NICE평가정보 개발 담당자에게 문의해 주세요.";
}




if (!StringUtil.IsEmpty(sRtnMsg)) {
%>
<div id="namefact_type namefact_type__checkplus-mobile">
	<h4><span>NICE평가정보 아이핀(i-PIN) 본인인증</span></h4>
	<p class="indent2">
		<%=sRtnMsg%>
	</p>
</div>
<%
}
else
{
%>
<c:set var="iRtn" value="<%=iRtn%>"/>
<c:set var="sRtnMsg" value="<%=sRtnMsg%>"/>
<c:set var="sEncData" value="<%=sEncData%>"/>


<c:choose>
	<c:when test="${param.mode == 'mobile'}"></c:when>
	<c:when test="${param.mode == 'ipin'}">
		<form id="CHECKPLUS_IPIN_namefact_form" method="post" action="<c:out value="${namefactModuleInfo.properties.checkplus_ipin_request}"/>">
			<fieldset>
				<legend>아이핀(i-PIN) 본인인증</legend>
				
				<input type="hidden" name="enc_data" value="<%=sEncData%>"/>
				<input type="hidden" name="m" value="pubmain"/>
				<input type="hidden" name="param_r1" value=""/>
				<input type="hidden" name="param_r2" value=""/>
				<input type="hidden" name="param_r3" value=""/>
			</fieldset>
		</form>
		<script type="text/javascript">
		// <![CDATA[
		jQuery(document).ready(function()
		{
			$('#CHECKPLUS_IPIN_namefact_form').submit();
		});
		// ]]>
		</script>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${param.mode == 'button'}">
				<form id="CHECKPLUS_IPIN_namefact_form" class="${namefact_count == 1 ? 'single' : ''}" target="CHECKPLUS_IPIN_namefact_form" method="post" action="<c:out value="${namefactModuleInfo.properties.checkplus_ipin_request}"/>">
					<fieldset>
						<legend>아이핀(i-PIN) 본인인증</legend>
						
						<input type="hidden" name="enc_data" value="<%=sEncData%>"/>
						<input type="hidden" name="m" value="pubmain"/>
						<input type="hidden" name="param_r1" value=""/>
						<input type="hidden" name="param_r2" value=""/>
						<input type="hidden" name="param_r3" value=""/>
						
						<p class="txtcenter">
							<button type="submit" class="button action_check ipin" title="팝업창 열림"><span><strong>아이핀(i-PIN)</strong> 본인인증</span></button>
						</p>
					</fieldset>
				</form>
			</c:when>
			<c:when test="${param.mode == 'simple'}">
				<form id="CHECKPLUS_IPIN_namefact_form" class="${namefact_count == 1 ? 'single' : ''}" target="CHECKPLUS_IPIN_namefact_form" method="post" action="<c:out value="${namefactModuleInfo.properties.checkplus_ipin_request}"/>">
					<fieldset>
						<legend>아이핀(i-PIN) 본인인증</legend>
						
						<input type="hidden" name="enc_data" value="<%=sEncData%>"/>
						<input type="hidden" name="m" value="pubmain"/>
						<input type="hidden" name="param_r1" value=""/>
						<input type="hidden" name="param_r2" value=""/>
						<input type="hidden" name="param_r3" value=""/>
						
						<p class="order_box">
							<button type="submit" class="button action_check ipin" title="팝업창 열림"><span>나이스 I-PIN 인증</span></button>
							<a class="button action_link" target="_blank" title="새창열림" href="http://www.niceipin.co.kr/"><span>아이핀 신규가입</span></a>
						</p>
						<div class="order_description">
							<ul>
								<li>아이핀 인증시에는 인증센터를 통하여 미리 회원가입이 되있어야 합니다.</li>
							</ul> 
						</div>
					</fieldset>
				</form>
			</c:when>
			<c:otherwise>
				<div id="namefact_type namefact_type__checkplus-mobile">
					<h4><span>NICE평가정보 아이핀(i-PIN) 본인인증</span></h4>
					<div class="proc_form">
						<h5><span>아이핀(i-PIN) 본인인증이란?</span></h5>
						<p class="indent3">
							&quot;인터넷 개인 식별 번호(Internet Personal Identification Number)&quot;의 영문 머리글자를 따 만든 용어입니다.
						</p>
						<ul class="indent3 list">
							<li>
								진행방법<br/>
								<span class="proc_txt start">&quot;본인인증&quot; 버튼클릭</span><span class="proc_arrow"> → </span><span class="proc_txt line1">새(팝업)창이동</span><span class="proc_arrow"> → </span><span class="proc_txt line1">정보입력</span><span class="proc_arrow"> → </span><span class="proc_txt line1">본인인증확인</span><span class="proc_arrow"> → </span><span class="proc_txt line1">새(팝업)창 닫힘</span><span class="proc_arrow"> → </span><span class="proc_txt end">현재창 다음페이지로 이동</span>
							</li>
							<li>문의 : <c:out value="${namefactModuleInfo.properties.checkplus_ipin_inquiry}"/></li>
						</ul>
						
						<form id="CHECKPLUS_IPIN_namefact_form" class="${namefact_count == 1 ? 'single' : ''}" target="CHECKPLUS_IPIN_namefact_form" method="post" action="<c:out value="${namefactModuleInfo.properties.checkplus_ipin_request}"/>">
							<fieldset>
								<legend>아이핀(i-PIN) 본인인증</legend>
								
								<input type="hidden" name="enc_data" value="<%=sEncData%>"/>
								<input type="hidden" name="m" value="pubmain"/>
								<input type="hidden" name="param_r1" value=""/>
								<input type="hidden" name="param_r2" value=""/>
								<input type="hidden" name="param_r3" value=""/>
								
								<p class="txtcenter">
									<button type="submit" class="button action_check large ipin" title="팝업창 열림"><span>아이핀(i-PIN) 본인인증</span></button>
								</p>
							</fieldset>
						</form>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		
		<script type="text/javascript">
		// <![CDATA[
		jQuery(document).ready(function() {
			$('#CHECKPLUS_IPIN_namefact_form').submit(function(e) {
				var form = $(this),
					target = form.attr('target'),
					win = window.open('', target, 'width=450, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
				
				if (win == null) {
					alert('** 아이핀(i-PIN) 본인인증 **\n팝업 차단을 해제해주시기 바랍니다.\n\n ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ MSN,야후,구글 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.');
					return false;
				} else {
					//form.submit();
					win.focus();
				}
			});
		});
		// ]]>
		</script>
	</c:otherwise>
</c:choose>
<%
}
%>