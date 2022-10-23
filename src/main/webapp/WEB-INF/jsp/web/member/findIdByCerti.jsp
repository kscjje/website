<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hisco.cmm.util.*" %>
<%@ page import ="com.hisco.user.nice.web.NamefactController"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : findId.jsp
  * @Description : 아이디 찾기 페이지
  * @Modification Information
  * @
  * @  수정일              수정자                   수정내용
  * @ -------      --------    ---------------------------
  * @ 2021.05.03    전영석                 최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.03
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<script>

	document.title = '비밀번호 찾기 < '+ document.title;
	
	//<![CDATA[
	
		function fn_member_submit() {
			
			if ($('#name').val() == "") {
				alert("이름을 입력해 주세요.");
				$('#name').focus();
			} else if ($('input[name=memGbn]:checked').val()=="email" && $('#email').val() == "") {
				alert("이메일 주소를 입력해 주세요.");
				$('#email').focus();
			} else if ($('input[name=memGbn]:checked').val()=="hphone" && $('#ihidNum').val() == "") {
				alert("휴대폰 번호를 입력해 주세요.");
				$('#ihidNum').focus();
				
			} else if (($('select[name=birthYYYY] option:selected').val()=="") || ($('select[name=birthYYYY] option:selected').val()=="연도")) {
				alert("생년월일 년도를 선택하세요");
				$('radio[name=birthYYYY]').focus();			
				
			} else if (($('select[name=birthMM] option:selected').val()=="") || ($('select[name=birthMM] option:selected').val()=="월")) {
				alert("생년월일 월을 선택하세요");
				$('radio[name=birthMM]').focus();			
				
			} else if (($('select[name=birthDD] option:selected').val()=="") || ($('select[name=birthDD] option:selected').val()=="일")) {
				alert("생년월일 일을 선택하세요");
				$('radio[name=birthDD]').focus();			
				
			} else if ($('#ihidNum').val() == "") {
				alert("휴대전화 번호를 입력하세요");
				$('#ihidNum').focus();			
				
			} else {
				//fn_ajaxSendData('./findIdResult', $('#loginVO').serialize(), callBack , "${_csrf.headerName}", "${_csrf.token}");
				$('#loginVO').submit();
			}
			
		}
	
		function callBack(data) {
	
			if (data.result.success) {
	    		$('#errPass').html(data.result.msg).removeClass("red").addClass("green");
	        } else {
	        	$('#errPass').html(data.result.msg).removeClass("green").addClass("red");
	        }
			$('#findResultDiv').show();
		}
	
		$(document).ready(function() {
			$('input[name=memGbn]').click(function() {
				if ($(this).val() == "email") {
					$('#ROW_email').show();
					$('#ROW_hphone').hide();
				} else {
					$('#ROW_email').hide();
					$('#ROW_hphone').show();
				}
			});
	
			setTimeout("$('input[name=memGbn]').eq(0).prop('checked' , true)", 100); //historyback 대비
	
			$('.phoneCheck').keyup(function() {
				$(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-") );
			});
	
		});
			
		top.crc_id = '<%=com.hisco.cmm.modules.CryptoUtil.getMD5Hash(session.getId())%>';
		
		jQuery(document).ready(function() {
			var iframe_namefact_child = $('#iframe_namefact_child'),
				iframe_namefact_parent = $('#iframe_namefact_parent');
			
		});

		function fn_auth_pop() {
			$('#iframe_namefact').contents().find('#CHECKPLUS_MOBILE_namefact_form button').click();
			return false;
		}	
		
	//]]>	
	
</script>

			<%-- 본인인증 삽입 --%>
			<c:url var="namefact_url" value="/web/namefact/findIdByCerti">
				<c:param name="<%=NamefactController.namefact_design_param%>"  value=""/>
				<c:param name="<%=NamefactController.namefact_type_param%>"    value="Normal"/>
				<c:param name="<%=NamefactController.namefact_end_url_param%>" value="/web/namefact/End"/>
				<c:param name="add_css" value="${add_css_url}"/>
				<c:param name="mode" value="button"/>
				<c:param name="inputVal" value=""/>
				<c:param name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</c:url>
			
			<iframe id="iframe_namefact" src="<c:out value='${namefact_url}'/>" title="본인인증 처리" class="auto_height" style="width: 100%; height: 1000px; border: 0 none; background: transparent; display:none;"></iframe>

			<div class="sub_cont" id="container">
				<div class="inner">
					<h3 class="tit mb70">아이디 찾기</h3>
					<ul class="tab tab5 mb35">
						<li><a href="./findId">가입정보로 찾기</a></li>
						<li class="on"><a href="./findIdByCerti">본인인증으로 찾기</a></li>
					</ul>
					<div class="mypage_wrap">
						<!-- signup -->
						<div class="signup">
							<ul>
								<li>
									<p class="img"><img src="<%=strContext%>/images/sub/img_idt.png" alt="휴대폰본인인증이미지"></p>
									<dl>
										<dt>휴대전화 본인인증 </dt>
										<dd>본인명의로 등록된 휴대폰을 통해 <br />본인확인을 받을 수 있는 서비스 입니다.</dd>
									</dl>
									<p><a href="#" onclick="fn_auth_pop(); return false;" class="btn_ty_m_c3">인증하기</a></p>
								</li>
							</ul>
						</div>
						<!-- //signup -->
					</div>
				</div>
				<!-- //inner -->
			</div>
