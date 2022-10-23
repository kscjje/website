<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:formatDate var="now_yyyyMMdd" value="${now}" pattern="yyyyMMdd"/>

<meta charset="utf-8" />
<meta name="description" content="<c:out value="${SiteData.extra_datas.site_explain}" default="${SiteData.title}" />">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

<meta name="format-detection" content="telephone=no" />

	<meta property="og:type" content="website">
	<meta property="og:title" content="<kn:DocumentTitle reverse="true"/>">
	<meta property="og:description" content="<c:out value="${SiteData.extra_datas.site_explain}" default="${SiteData.title}" />">
	
	<script>
		//<![CDATA[
			var ROOT = '<c:url value="/web/main"/>';
			var SITE_NAME = '<c:out value="${SiteData.name}"/>';
			var SITE_PATH = ROOT + '<c:out value="${SiteData.path}"/>';
			var ISLOGIN = '<c:out value="${IsLogin}"/>' == 'true';
			<c:if test="${IsAdmin || IsAdminGroup || IsSuperAdmin}">var ADMIN_ROOT = '<c:url value="${ADMIN_ROOT}/"/>';</c:if>
			<c:if test="${IsLogin}">  var MEM_NO = '<c:url value="${MemberSessionData.mem_no}"/>';</c:if>
			<c:if test="${!IsLogin}"> var LOGIN_URL = '<c:out value="${login_url}" escapeXml="false"/>';</c:if>			

			var IS_PC = '<c:out value="${IsPc}"/>' == 'true';
			var IS_MOBILE = '<c:out value="${IsMobile}"/>' == 'true';
			
			var PARAM_MODULE_ACTION = '<%=com.hisco.cmm.modules.RequestUtil.PARAM_MODULE_ACTION%>';
			var PARAM_MODULE_VALUE = '<%=com.hisco.cmm.modules.RequestUtil.PARAM_MODULE_VALUE%>';
			var PARAM_SUB_MODULE_ACTION = '<%=com.hisco.cmm.modules.RequestUtil.PARAM_SUB_MODULE_ACTION%>';
			var PARAM_SUB_MODULE_VALUE = '<%=com.hisco.cmm.modules.RequestUtil.PARAM_SUB_MODULE_VALUE%>';
			
			var DESIGN_ROOT = '<c:url value="/design/homepage/${SiteData.extra_datas.design_path}"/>';
		//]]>
	</script>	
	
	<c:if test="${(IsLogin && !(IsAdmin || IsAdminGroup || IsSuperAdmin))
		|| (IsLogin && (SiteData.path == 'mngcms' || SiteData.path == 'intranet'))}">
		<c:choose>
			<c:when test="${SiteData.path == 'mngcms' || SiteData.path == 'intranet'}">
				<kn:MenuLink var="logout_url" menu="로그아웃" moduleAction="logout_force" param="'login_check' : 'skip', 'referer' : ''" htmlEncode="false"/>
			</c:when>
			<c:otherwise>
				<kn:MenuLink var="logout_url" menu="로그아웃" param="'login_check' : 'skip', 'referer' : ''" htmlEncode="false"/>
			</c:otherwise>
		</c:choose>

		<script>
		
			//<![CDATA[
			var ss_interval = parseInt('<%=(session.getMaxInactiveInterval() - (5 * 60)) * 1000%>', 10),
				ss_interval_m = (ss_interval / 1000) / 60,
				ss_time_space_m = 5,
				timer_id1 = null,
				timer_id2 = null,
				prev_focus = null;
			
			
			console.log("ss_interval : "  + ss_interval);
			console.log("ss_interval_m : "  + ss_interval_m);
			
			function SessionExtend() {
				
				//var form = $('form[method="post"]');
				var form = $('form');
				if (form.length > 0)
				{
					var url = ROOT + 'session/nocut_page',
						iframe = $('<iframe id="session_nocut" title="page for processing session maintenance" src="" style="position: absolute; width: 0; height: 0; border: 0 none; overflow: hidden;"></iframe>');
					
					iframe.load(function(e)
					{
						window.clearInterval(timer_id1);
						window.clearInterval(timer_id2);
						
						timer_id1 = window.setInterval(TimeoutMessage, ss_interval);
						
						$(this).remove();
						
						$('#session_timeout_box').remove();
						$('#session_timeout_cover').remove();
						
						window.setTimeout(TimeoutMessage, ss_interval);
						
						alert('로그인 연장이 완료되었습니다.');
						$(prev_focus).focus();
					});
					
					iframe.attr('src', url);
					$(document.body).append(iframe);
				}
				else
				{
					location.reload();
					$(prev_focus).focus();
				}
			}
			
			function TimeoutMessage()
			{
				prev_focus = $('*:focus');
				var html = $('<div id="session_timeout_box" style="position: fixed; left: 0; top: 20px; z-index: 999999; width: 100%;">\n\t<div style="width: 500px; max-width: 95%; padding: 1px; margin: 0 auto; background-color: #fff; box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.7);">\n\t\t<h1 style="line-height: 40px; margin: 0; background-color: #1969c5; text-align: center; font-size: 16px; font-weight: 600; color: #fff;">자동 로그아웃 알림</h1>\n\t\t<p style="line-height: 30px; margin: 20px; font-size: 14px;">\n\t\t\t<strong style="color: #104790">'+ ss_interval_m +'분</strong> 동안 홈페이지를 사용하지 않아 <strong style="color: #104790">'+ ss_time_space_m +'분후</strong>에 <em style="color: #f85800; font-style: normal; text-decoration: underline;">자동로그아웃</em>됩니다.\n\t\t</p>\n\t\t<p style="line-height: 30px; margin: 20px; font-size: 20px; text-align: center;">\n\t\t\t남은시간 : <strong class="timeout_second">'+ ss_time_space_m +'분</strong>\n\t\t</p>\n\t\t<p style="padding: 20px 0 0 0; margin: 20px; border-top: 1px solid #e0e0e0; text-align: center;">\n\t\t\t<a class="button" href="javascript:SessionExtend()"><span>로그인 연장하기</span></a>\n\t\t</p>\n\t</div>\n</div>\n<div id="session_timeout_cover" style="position: fixed; left: 0; top: 0; z-index: 999998; width: 100%; height: 100%; background-color: #333; background-color: rgba(0, 0, 0, 0.7);"></div>');
				html.insertBefore($('body > *').eq(0));
				html.find('a').focus();
				
				var time_limit = ss_time_space_m * 60;
				function timeout() {
					var minute = parseInt(time_limit / 60),
						second = time_limit % 60,
						time_string = '';
					
					if (minute > 0) time_string += minute +'분';
					if (second > 0) time_string += (time_string != '' ? ' ' : '') + second +'초';
					
					html.find('.timeout_second').text(time_string);
					
					if (minute <= 0 && second <= 0) {
						location.href = '${fn:replace(logout_url, '\'', '\\\'')}&referer='+ encodeURIComponent(ROOT + '${SiteData.path}');
					} else {
						time_limit--;
					}
				}
				timer_id2 = window.setInterval(timeout, 1000);
			}
			
			timer_id1 = window.setInterval(TimeoutMessage, ss_interval);
		//]]>
		</script>
			
	</c:if>