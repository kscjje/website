<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import ="java.util.*, java.text.SimpleDateFormat"%>
<%@ page import ="com.hisco.cmm.modules.ModuleInfoDto"%>
<%@ page import ="com.hisco.cmm.modules.*"%>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>

<%

	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";

	String strScheme     = request.getScheme();
	String strProtocol   = request.getProtocol();
	String strServerName = request.getServerName();
	int    intServerPort = request.getServerPort();
	String strServerUrl  = strScheme + "://" + strServerName + ":" + intServerPort;  
	String strXDomain    = request.getHeader("x-forwarded-server");
	String strXHost      = request.getHeader("x-forwarded-host");

	String strReqUri1 = String.valueOf(request.getAttribute("javax.servlet.forward.request_uri"));
	String strReqUri2 = String.valueOf(request.getAttribute("javax.servlet.include.request_uri"));

	System.out.println("----------------------------------------------Step.1.S");
	System.out.println("strScheme     = " + strScheme);
	System.out.println("strProtocol   = " + strProtocol);
	System.out.println("strServerName = " + strServerName);
	System.out.println("intServerPort = " + intServerPort);
	System.out.println("strServerUrl  = " + strServerUrl);
	System.out.println("strReqUri1  = " + strReqUri1);
	System.out.println("strReqUri2  = " + strReqUri2);
	System.out.println("strXDomain  = " + strXDomain);
	System.out.println("strXHost    = " + strXHost);
	System.out.println("----------------------------------------------Step.1.E");
%>

<script src="<%=strContext%>/js/jquery-x.x.x.min.js"></script>

<%
// 모듈 정보
ModuleInfoDto namefactModuleInfo = (ModuleInfoDto) request.getAttribute("namefactModuleInfo");
String checkplus_mobile_response_success = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_mobile_response_success") ? (String) namefactModuleInfo.getProperties().get("checkplus_mobile_response_success") : "";
String checkplus_mobile_response_fail = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_mobile_response_fail") ? (String) namefactModuleInfo.getProperties().get("checkplus_mobile_response_fail") : "";

// 도메인 및 컨텍스트 경로
RequestUtil req = RequestUtil.getInstance(request);

String strOtherLocationRoot = req.getLocationRoot();
String strOtherDomain       = req.getDomain();
String strOtherClientIp     = req.getClientIP();
String strOtherLocationRootHttp  = req.getLocationRootHttp();
String strOtherLocationRootHttps = req.getLocationRootHttps();
String strOtherUrl        = req.getUrl();
String strOtherUserAgent  = req.getUserAgent();
Boolean boolOtherIsMobile = req.isMobile();

System.out.println("----------------------------------------------Step.2.S");
System.out.println("strOtherLocationRoot = " + strOtherLocationRoot);
System.out.println("strOtherDomain       = " + strOtherDomain);
System.out.println("strOtherClientIp     = " + strOtherClientIp);
System.out.println("strOtherLocationRootHttp  = " + strOtherLocationRootHttp);
System.out.println("strOtherLocationRootHttps = " + strOtherLocationRootHttps);
System.out.println("strOtherUrl       = " + strOtherUrl);
System.out.println("strOtherUserAgent = " + strOtherUserAgent);
System.out.println("boolOtherIsMobile = " + boolOtherIsMobile);
System.out.println("----------------------------------------------Step.2.E");

//String base_url = "http://hisco.co.kr/";

String base_url = egovframework.com.cmm.service.EgovProperties.getProperty("Globals.Domain") + "/";
//String base_url = strServerUrl + "/";
System.out.println("base_url = " + base_url);

NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

// NICE로부터 부여받은 사이트 코드
String sSiteCode = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_mobile_sitecode") ? (String) namefactModuleInfo.getProperties().get("checkplus_mobile_sitecode") : "";
// NICE로부터 부여받은 사이트 패스워드
String sSitePassword = namefactModuleInfo != null && namefactModuleInfo.getProperties() != null && namefactModuleInfo.getProperties().containsKey("checkplus_mobile_password") ? (String) namefactModuleInfo.getProperties().get("checkplus_mobile_password") : "";
//System.out.println("sSiteCode : " + sSiteCode);
//System.out.println("sSitePassword : " + sSitePassword);
String sRequestNumber = "REQ0000000001";				// 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로 
														// 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
sRequestNumber = niceCheck.getRequestNO(sSiteCode);
session.setAttribute("REQ_SEQ" , sRequestNumber);		// 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.

String sAuthType	= "M";										// 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서
String popgubun 	= "N";										// Y : 취소버튼 있음 / N : 취소버튼 없음
String customize 	= req.isMobile() ? "Mobile" : "";			// 없으면 기본 웹페이지 / Mobile : 모바일페이지
	
System.out.println("customize = " + customize);

// CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
// 성공시 이동될 URL
String sReturnUrl = base_url.concat(checkplus_mobile_response_success.startsWith("/") ? checkplus_mobile_response_success.substring(1) : checkplus_mobile_response_success);
// 실패시 이동될 URL
String sErrorUrl = base_url.concat(checkplus_mobile_response_fail.startsWith("/") ? checkplus_mobile_response_fail.substring(1) : checkplus_mobile_response_fail);

System.out.println("--------------------------------------------------.S");
System.out.println("sReturnUrl = " + sReturnUrl);
System.out.println("sErrorUrl = " + sErrorUrl);
System.out.println("--------------------------------------------------.E");

// 입력될 plain 데이타를 만든다.
String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
					"8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
					"9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
					"7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
					"7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
					"11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
					"9:CUSTOMIZE" + customize.getBytes().length + ":" + customize;

System.out.println("sPlainData = " + sPlainData);

String sMessage = "";
String sEncData = "";

int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);

System.out.println("iReturn = " + iReturn);

if ( iReturn == 0 ) {
	sEncData = niceCheck.getCipherData();
} else if ( iReturn == -1) {
	sMessage = "암호화 시스템 에러입니다.";
} else if ( iReturn == -2) {
	sMessage = "암호화 처리오류입니다.";
} else if ( iReturn == -3) {
	sMessage = "암호화 데이터 오류입니다.";
} else if ( iReturn == -9) {
	sMessage = "입력 데이터 오류입니다.";
} else {
	sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
}

if (!StringUtil.IsEmpty(sMessage)) {
%>
<div id="namefact_type namefact_type__checkplus-mobile">
	<h4><span>NICE평가정보 휴대전화 본인인증</span></h4>
	<p class="indent2">
		<%=sMessage%>
	</p>
</div>
<%
}
else
{
%>
<c:choose>
	<c:when test="${param.mode == 'ipin'}"></c:when>
	<c:when test="${param.mode == 'mobile'}">
		<form id="CHECKPLUS_MOBILE_namefact_form" method="post" action="<c:out value="${namefactModuleInfo.properties.checkplus_mobile_request}"/>">
			<fieldset>
				<legend>휴대전화 본인인증</legend>
				
				<input type="hidden"   name="EncodeData" value="<%=sEncData%>"/>
				<input type="hidden"   name="m" value="checkplusSerivce"/>
				<input type="hidden"   name="param_r1" value=""/>
				<input type="hidden"   name="param_r2" value=""/>
				<input type="hidden"   name="param_r3" value=""/>
				<!-- <input type ="hidden"  name="recvMethodType"  value="get"> --> 
			</fieldset>
		</form>
		<script type="text/javascript">
		// <![CDATA[
		jQuery(document).ready(function() {
			$('#CHECKPLUS_MOBILE_namefact_form').submit();
		});
		// ]]>
		</script>		
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${param.mode == 'button'}">
				<form id="CHECKPLUS_MOBILE_namefact_form" class="${namefact_count == 1 ? 'single' : ''}" target="CHECKPLUS_MOBILE_namefact_window" method="post" action="<c:out value="${namefactModuleInfo.properties.checkplus_mobile_request}"/>">
					<fieldset>
						<legend>휴대전화 본인인증</legend>
						
						<input type="hidden" name="EncodeData" value="<%=sEncData%>"/>
						<input type="hidden" name="m" value="checkplusSerivce"/>
						<input type="hidden" name="param_r1" value=""/>
						<input type="hidden" name="param_r2" value=""/>
						<input type="hidden" name="param_r3" value=""/>
						
						<p class="txtcenter">
							<button type="submit" class="button action_check mobile" title="팝업창 열림"><span><strong>휴대전화</strong> 본인인증</span></button>
						</p>
					</fieldset>
				</form>
			</c:when>
			<c:when test="${param.mode == 'simple'}">
				<form id="CHECKPLUS_MOBILE_namefact_form" class="${namefact_count == 1 ? 'single' : ''}" target="CHECKPLUS_MOBILE_namefact_window" method="post" action="<c:out value="${namefactModuleInfo.properties.checkplus_mobile_request}"/>">
					<fieldset>
						<legend>휴대전화 본인인증</legend>
						
						<input type="hidden" name="EncodeData" value="<%=sEncData%>"/>
						<input type="hidden" name="m" value="checkplusSerivce"/>
						<input type="hidden" name="param_r1" value=""/>
						<input type="hidden" name="param_r2" value=""/>
						<input type="hidden" name="param_r3" value=""/>
						
						<p class="order_box">
							<button type="submit" class="button action_check mobile" title="팝업창 열림"><span>휴대전화 본인인증</span></button>
						</p>
						<div class="order_description">
							<ul>
								<li>본인명의의 메시지 수신 가능한 휴대폰으로 인증번호를 받으실 수 있습니다.</li>
							</ul> 
						</div>
					</fieldset>
				</form>
			</c:when>
			<c:otherwise>
				<div id="namefact_type namefact_type__checkplus-mobile">
					<h4><span>NICE평가정보 휴대전화 본인인증</span></h4>
					<div class="proc_form">
						<h5><span>휴대전화 본인인증이란?</span></h5>
						<p class="indent3">
							모바일 본인인증서비스는 생년월일 기반의 본인인증서비스로 개인정보 입력 없이도 실명확인, 본인확인이 가능한 서비스입니다.
						</p>
						<ul class="indent3 list">
							<li>
								진행방법<br/>
								<span class="proc_txt start">&quot;본인인증&quot; 버튼클릭</span><span class="proc_arrow"> → </span><span class="proc_txt line1">새(팝업)창이동</span><span class="proc_arrow"> → </span><span class="proc_txt">이동통신사 선택 및 정보입력</span><span class="proc_arrow"> → </span><span class="proc_txt line1">본인인증확인</span><span class="proc_arrow"> → </span><span class="proc_txt line1">새(팝업)창 닫힘</span><span class="proc_arrow"> → </span><span class="proc_txt end">현재창 다음페이지로 이동</span>
							</li>
							<li>문의 : <c:out value="${namefactModuleInfo.properties.checkplus_mobile_inquiry}"/></li>
						</ul>
						
						<form id="CHECKPLUS_MOBILE_namefact_form" class="${namefact_count == 1 ? 'single' : ''}" target="CHECKPLUS_MOBILE_namefact_window" method="post" action="<c:out value="${namefactModuleInfo.properties.checkplus_mobile_request}"/>">
							<fieldset>
								<legend>휴대전화 본인인증</legend>
								
								<input type="hidden" name="EncodeData" value="<%=sEncData%>"/>
								<input type="hidden" name="m" value="checkplusSerivce"/>
								<input type="hidden" name="param_r1" value=""/>
								<input type="hidden" name="param_r2" value=""/>
								<input type="hidden" name="param_r3" value=""/>
								
								<p class="txtcenter">
									<button type="submit" class="button action_check mobile large" title="팝업창 열림"><span>휴대전화 본인인증</span></button>
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
			
			$('#CHECKPLUS_MOBILE_namefact_form').submit(function(e) {
				
				var form   = $(this);
				var	target = form.attr('target');
				var win = window.open('', target, 'width=500, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=100, top=50');
				
				if (win == null) {
					alert('** 휴대전화 본인인증 **\n팝업 차단을 해제해주시기 바랍니다.\n\n ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ MSN,야후,구글 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.');
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