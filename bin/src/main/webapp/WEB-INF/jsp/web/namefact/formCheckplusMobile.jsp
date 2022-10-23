<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import ="java.util.*, java.text.SimpleDateFormat"%>

		<form id="CHECKPLUS_MOBILE_namefact_form" method="post" action="https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb?m=auth_mobile_main">
			<fieldset>
				<legend>휴대전화 본인인증</legend>
				
				<input type="hidden" name="EncodeData" value=""/>
				<input type="hidden" name="m" value="checkplusSerivce"/>
				<input type="hidden" name="param_r1" value=""/>
				<input type="hidden" name="param_r2" value=""/>
				<input type="hidden" name="param_r3" value=""/>
			</fieldset>
		</form>
		<script type="text/javascript">
		// <![CDATA[
		jQuery(document).ready(function()
		{
			$('#CHECKPLUS_MOBILE_namefact_form').submit();
		});
		// ]]>
		</script>	
		
		
