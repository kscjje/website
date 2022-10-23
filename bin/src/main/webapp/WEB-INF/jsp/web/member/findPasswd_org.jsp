<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="com.hisco.cmm.util.*" %>
<%@ page import ="com.hisco.user.nice.web.NamefactController"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : findPasswd.jsp
  * @Description : 비밀번호 찾기 페이지
  * @Modification Information
  * @
  * @  수정일               수정자          수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.06    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.06
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<script type="text/javascript">

	//<![CDATA[
	
		document.title = '비밀번호 찾기 < '+ document.title;
		
		top.crc_id = '<%=com.hisco.cmm.modules.CryptoUtil.getMD5Hash(session.getId())%>';
		
		jQuery(document).ready(function() {
			var iframe_namefact_child = $('#iframe_namefact_child'),
				iframe_namefact_parent = $('#iframe_namefact_parent');
			
		});

		function fn_auth_pop() {
			
			var varUserId = $("#userId").val();  
			
			if ((varUserId == null) || (varUserId == '') || (varUserId === undefined)) {
				alert("회원 아이디를 입력해주세요");
				$("#userId").focus();
				return false;
			}
			
			sessionStorage.setItem("inputVal", varUserId);
			
			$('#iframe_namefact').contents().find('#CHECKPLUS_MOBILE_namefact_form').find("#inputVal").val(varUserId);
			$('#iframe_namefact').contents().find('#CHECKPLUS_MOBILE_namefact_form button').click();
			return false;
			
		}	
		
	//]]>
	
</script>

			<%-- 본인인증 삽입 --%>
			<c:url var="namefact_url" value="/web/namefact/findPasswd">
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
					<h3 class="tit mb20">비밀번호 찾기</h3>
					<p class="desc mb70">가입시 등록된 회원 아이디를 입력하시고, 본인인증 하시면 비밀번호를 재설정을 하실 수 있습니다.</p>
					<div class="mypage_wrap">
						<div class="signup2">
							<div class="table2 responsive-type mb55">
								<table>
									<caption>비밀번호 찾기를 위한 입력폼입니다.</caption>
									<colgroup>
										<col style="width:20%">
										<col style="width:80%">
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="vtop"><label for="l2">회원 아이디</label></th>
											<td><input id="userId" name="userId" title="회원가입 시 등록한 아이디를 입력하세요" type="text" value="" class="input_log" placeholder="회원가입 시 등록한 아이디를 입력 해 주십시오" /></td>
										</tr>
									</tbody>
								</table>
								<!-- //table -->
							</div>
						</div>
						<!-- signup -->
						<div class="signup">
							<ul>
								<li>
									<p class="img"><img src="<%=strContext%>/images/sub/img_idt.png" alt="휴대폰본인인증이미지"></p>
									<dl>
										<dt>휴대전화 본인인증 </dt>
										<dd>본인명의로 등록된 휴대폰을 통해 <br />본인확인을 받을 수 있는 서비스 입니다.</dd>
									</dl>
									<p>
										<a href="#" onclick="fn_auth_pop(); return false;" class="btn_ty_m_c3">인증하기</a> 
									</p>
								</li>
							</ul>
						</div>
						<!-- //signup -->
					</div>
				</div>
				<!-- //inner -->
			</div>		
			
<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>
			