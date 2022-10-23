<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import ="com.knsoft.kntool.MODULES.namefact.NamefactController"%>
<script>
document.title = '본인인증 < '+ document.title;
</script>

<script type="text/javascript">
//<![CDATA[
top.crc_id = '<%=com.knsoft.support.CryptoUtil.getMD5Hash(session.getId())%>';

jQuery(document).ready(function()
{
	var iframe_namefact_child = $('#iframe_namefact_child'),
		iframe_namefact_parent = $('#iframe_namefact_parent');
	
	console.log(iframe_namefact_child);
	console.log(iframe_namefact_parent);
	
});

//]]>
</script>

${memberForm.type}


<c:url var="add_css_url" value="/module/${ModuleConfigData.module_key}/${ModuleConfigData.extra_datas.skin == '' ? 'default' : ModuleConfigData.extra_datas.skin}/css/default.css"/>

<c:choose>

		<%-- 14세미만 보호자만 본인인증 --%>
		<c:when test="${memberForm.type == 'child' || fn:indexOf(memberForm.type, 'child') > -1}">
				<%-- 본인인증 삽입 --%>
				<c:url var="namefact_url" value="/Namefact">
					<c:param name="<%=NamefactController.namefact_design_param%>" value=""/>
					<c:param name="<%=NamefactController.namefact_type_param%>" value="parent"/>
					<c:param name="<%=NamefactController.namefact_end_url_param%>" value="${next_url}"/>
					<c:param name="add_css" value="${add_css_url}"/>
				</c:url>
				<iframe id="iframe_namefact" class="iframe_namefact auto_height" src="<c:out value="${namefact_url}"/>" title="보호자(법정대리인) 본인인증 처리" style="width: 100%; height: 1000px; border: 0 none; background: transparent;  display:none;"></iframe>
		</c:when>
		<c:otherwise>
				<%-- 본인인증 삽입 --%>
				<c:url var="namefact_url" value="/Namefact">
					<c:param name="<%=NamefactController.namefact_design_param%>" value=""/>
					<c:param name="<%=NamefactController.namefact_type_param%>" value="${memberForm.type}"/>
					<c:param name="<%=NamefactController.namefact_end_url_param%>" value="${next_url}"/>
					<c:param name="add_css" value="${add_css_url}"/>
					<c:param name="mode" value="button"/>
				</c:url>
				
				${namefact_url}
				
				<iframe id="iframe_namefact" src="<c:out value="${namefact_url}"/>" title="본인인증 처리" class="auto_height" style="width: 100%; height: 1000px; border: 0 none; background: transparent; display:none;"></iframe>
		</c:otherwise>				
</c:choose>				



				<ul class="joinStep">
					<li><div><div><span>회원종류 <em>선택</em></span></div></div></li>
					<li><div><div><span>이용약관 <em>동의</em></span></div></div></li>
					<c:if test="${ModuleConfigData.extra_datas.namefact_used eq 'Y'}">
					<li class="on"><div><div><span>본인 <em>인증</em></span></div></div></li>
					</c:if>
					<li><div><div><span>회원정보<em>입력</em></span></div></div></li>
					<li><div><div><span>회원가입 <em>완료</em></span></div></div></li>
				</ul>
				
<c:choose>

		<%-- 14세미만 보호자만 본인인증 --%>
		<c:when test="${memberForm.type == 'child' || fn:indexOf(memberForm.type, 'child') > -1}">
				<ul class="subDsc lgnDsc">
					<li>가입을 위한 본인인증 방법을 선택해주세요.</li>
					<li>14세 미만 회원이 회원가입을 하시려면 반드시 <span class="sky">"부모님(법적보호자)의 동의"</span>를 받아야 합니다.</li>
					<li><span class="sky">"부모님(법적보호자)의 동의"</span>는 부모님(법적보호자)의 <span class="red">본인인증으로 동의 한 것으로 간주</span>됩니다.</li>
				</ul>
		</c:when>
		<c:otherwise>
				<ul class="subDsc lgnDsc">
					<li>가입을 위한 본인인증 방법을 선택해주세요.</li>
					<li>아이디/비밀번호 분실 등 본인 여부 확인이 필요할 경우를 위해 반드시 신분증에 기재된 이름, 생년월일을 입력해주세요.88888</li>
				</ul>
		</c:otherwise>				
</c:choose>			

				<div class="joinCer ">
					<div>
						<div class="joinCerti">
							<img src="${design_root}/img/sub/join_certifi01.png" alt="휴대폰 본인인증 이미지">
							<h5>휴대폰 본인인증56563</h5>
							<p>본인명의로 등록된 휴대폰을 통해<br/> 본인 확인을 받을 수 있는 서비스입니다.</p>
							<a href="#" title="휴대폰 본인인증 새창열림" class="" title="휴대폰 본인인증 하기 버튼" onclick="$('#iframe_namefact').contents().find('#CHECKPLUS_MOBILE_namefact_form button').click(); return false;">인증하기</a>
						</div>
					</div>
					<div>
						<div class="joinCerti">
							<img src="${design_root}/img/sub/join_certifi02.png" alt="아이핀인증 이미지">
							<h5>아이핀(i-PIN) 본인인증</h5>
							<p>개인식별 번호를 발급받아 인터넷상에서<br/> 본인확인을 받을 수 있는 서비스입니다.</p>
							<a href="#" title="아이핀인증 새창열림" class="" title="아이핀인증 하기 버튼" onclick="$('#iframe_namefact').contents().find('#CHECKPLUS_IPIN_namefact_form button').click(); return false;">인증하기</a>
						</div>
					</div>
				</div>
