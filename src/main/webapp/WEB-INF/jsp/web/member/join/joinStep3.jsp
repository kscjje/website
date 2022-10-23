<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hisco.cmm.util.*" %>
<%@ page import ="com.hisco.user.nice.web.NamefactController"%>

<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>


<script>
	
	var varIframeMyUrl = ""; 
	
	//<![CDATA[
	function fn_auth_pop(type) {
		
		//type : normal / parent
	
		if (type == "child") {
			
			if ($('#name').val() == "") {
				alert("이름을 입력해 주세요.");
				$('#name').focus();
				return;
			} else if ($('#birthYear').val() == "") {
				alert("생년월일을 모두 선택해 주세요.");
				$('#birthYear').focus();
				return;
			} else if ($('#birthMonth').val() == "") {
				alert("생년월일을 모두 선택해 주세요.");
				$('#birthMonth').focus();
				return;
			} else if ($('#birthDay').val() == "") {
				alert("생년월일을 모두 선택해 주세요.");
				$('#birthDay').focus();
				return;
			}
	
			sessionStorage.setItem("childName", $("#name").val());
			sessionStorage.setItem("childBirthDay", $("#birthYear").val() + $("#birthMonth").val() + $("#birthDay").val());

		}
	
		$('#iframe_namefact').contents().find('#CHECKPLUS_MOBILE_namefact_form button').click();
		return;		
		
	}
	//]]>	
	
	
	//IPIN본인 인증
	function ipinCert() {
		//alert("ipinCert()");
		
		/* 
		parent = window
				.open(
						'/member/reqCertify.do?type=ipin',
						'popupChk',
						'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
 		 */
		$('#iframe_namefact_ipin').contents().find('#CHECKPLUS_IPIN_namefact_form button').click();
		//document.form_ipin.action = "https://cert.vno.co.kr/ipin.cb";
		//document.form_ipin.target = "popupChk";
		//document.form_ipin.submit();
 	}
	
	
	
	
	function fn_auth_complete(mid,onlykey,paytype) {
		$('#LGD_MID').val(mid);
		$('#LGD_AUTHONLYKEY').val(onlykey);
		$('#LGD_PAYTYPE').val(paytype);
		$('#memberVO').attr("action" , "./joinCertCheck");
		$('#memberVO').submit();
	}
	
	//생년월일 세팅
	function setBirthDate() {
		var dt = new Date();
	
		for (var y = dt.getFullYear()-15; y <= dt.getFullYear(); y++) {
			$("#birthYear").append(
					"<option value='"+ y +"'>" + y + " 년"
							+ "</option>");
		}
		for (var m = 1; m <= 12; m++) {
			var month = String(m).lpad(2,'0')
			$("#birthMonth").append(
					"<option value='"+ month +"'>" + month + " 월"
							+ "</option>");
		}
	
		for (var d = 1; d <= 31; d++) {
			var day = String(d).lpad(2,'0')
			$("#birthDay").append(
					"<option value='"+ day +"'>" + day + " 일"
							+ "</option>");
		}
		<c:if test="${!empty param.birthYear}">$('#birthYear').val('<c:out value="${param.birthYear}"/>');</c:if>
		<c:if test="${!empty param.birthMonth}">$('#birthMonth').val('<c:out value="${param.birthMonth}"/>');</c:if>
		<c:if test="${!empty param.birthDay}">$('#birthDay').val('<c:out value="${param.birthDay}"/>');</c:if>
	}//end of setDate
	
	$(document).ready(function() {
		
		setBirthDate();
	
		<c:choose>
			<c:when test="${!empty alertMsg and alertMsg eq 'EXISTS'}">
				if (confirm("이미 가입된 회원입니다.\n로그인 페이지로 이동하시겠습니까")) {
					window.location.replace("../login");
				}
			</c:when>
			<c:when test="${!empty alertMsg}">
				alert("<c:out value="${alertMsg}"/>");
			</c:when>
		</c:choose>
	
		$(document).attr("title", "본인인증 - 회원가입 | 남양주시청 주민자체센터 통합예약 시스템");	
		
		varIframeMyUrl = $('#iframe_namefact').attr('src');
		
	});
	
</script>

			<c:if test="${type eq 'normal' }">
			
				<c:url var="namefact_url" value="/web/namefact/memRegiNormal">
					<c:param name="<%=NamefactController.namefact_design_param%>"  value=""/>
					<c:param name="<%=NamefactController.namefact_type_param%>"    value="Normal"/>
					<c:param name="<%=NamefactController.namefact_end_url_param%>" value="/web/namefact/End"/>
					<c:param name="add_css" value="${add_css_url}"/>
					<c:param name="mode" value="button"/>
					<c:param name="inputVal" value=""/>
				</c:url>
				
				<iframe id="iframe_namefact" src="<c:out value='${namefact_url}'/>" title="본인인증 처리" class="auto_height" style="width: 100%; height: 1000px; border: 0 none; background: transparent; display:none;"></iframe>			
			
			</c:if>
			
			<c:if test="${type eq 'child' }">
			
				<%-- 본인인증 삽입 --%>
				<c:url var="namefact_url" value="/web/namefact/memRegiChild">
					<c:param name="<%=NamefactController.namefact_design_param%>"  value=""/>
					<c:param name="<%=NamefactController.namefact_type_param%>"    value="Normal"/>
					<c:param name="<%=NamefactController.namefact_end_url_param%>" value="/web/namefact/End"/>
					<c:param name="add_css" value="${add_css_url}"/>
					<c:param name="mode" value="button"/>
					<c:param name="inputVal" value=""/>
				</c:url>
				
				<iframe id="iframe_namefact" src="<c:out value='${namefact_url}'/>" title="본인인증 처리" class="auto_height" style="width: 100%; height: 1000px; border: 0 none; background: transparent; display:none;"></iframe>			
			
			</c:if>
			
			<c:if test="${type eq 'normal' }">
			<%-- ipin 본인인증 삽입 --%>
				<c:url var="namefact_url_ipin" value="/web/namefact/memRegiNormal">
					<c:param name="<%=NamefactController.namefact_design_param%>"  value=""/>
					<c:param name="<%=NamefactController.namefact_type_param%>"    value="Normal"/>
					<c:param name="<%=NamefactController.namefact_end_url_param%>" value="/web/namefact/End"/>
					<c:param name="add_css" value="${add_css_url}"/>
					<c:param name="mode" value="button"/>
					<c:param name="inputVal" value=""/>
				</c:url>
				
				<iframe id="iframe_namefact_ipin" src="<c:out value='${namefact_url_ipin}'/>" title="본인인증 처리" class="auto_height" style="width: 100%; height: 1000px; border: 0 none; background: transparent; display:none;"></iframe>			
			
			</c:if>
			
			<c:if test="${type eq 'child' }">
			<%-- ipin 본인인증 삽입 --%>
				<c:url var="namefact_url_ipin" value="/web/namefact/memRegiChild">
					<c:param name="<%=NamefactController.namefact_design_param%>"  value=""/>
					<c:param name="<%=NamefactController.namefact_type_param%>"    value="Normal"/>
					<c:param name="<%=NamefactController.namefact_end_url_param%>" value="/web/namefact/End"/>
					<c:param name="add_css" value="${add_css_url}"/>
					<c:param name="mode" value="button"/>
					<c:param name="inputVal" value=""/>
				</c:url>
				
				<iframe id="iframe_namefact_ipin" src="<c:out value='${namefact_url_ipin}'/>" title="본인인증 처리" class="auto_height" style="width: 100%; height: 1000px; border: 0 none; background: transparent; display:none;"></iframe>			
			
			</c:if>


<main class="content" id="content">

      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="../../resources/images/sub/img_subVisual_05.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">회원가입</h3>
        </div>
      </div>
      <div class="pageTit">
        <div class="pageTit-inner">
          <div class="pageTit-wrap">
            <h3 class="pageTit-title">회원가입</h3>
            <ul class="pageTit-list">
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
                  <img src="../../resources/images/sub/ico_snb_home.png" alt="홈">
                </a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">회원</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">회원가입</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">회원유형</a>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="sub">
        <div class="accredit pc">
          <div class="accredit-inner">
            <div class="accredit-wrap">
              <ul class="accredit-list">
                <li class="accredit-item">
                  <p>01</p>
           회원선택
                </li>
                <li class="accredit-item">
                  <p>02</p>
                  약관동의
                </li>
                <li class="accredit-item on">
                  <p>03</p>
                  본인인증
                </li>

                <li class="accredit-item">
                  <p>04</p>
                  회원정보입력
                </li>
                <li class="accredit-item">
                  <p>05</p>
                  회원가입 완료
                </li>
              </ul>
            </div>
          </div>
        </div>

        <div class="joinMember">
          <div class="joinMember-inner">
            <div class="joinMember-wrap">
              <div class="joinMember-top">
                <h3 class="joinMember-title">인증방법 선택</h3>
                <ul class="joinMember-list">
                  
                  
                  <!-- 14세 이상 회원가입 -->
                  <c:if test="${type eq 'normal' }">
                 
	                  <li class="joinMember-item">
	                    <a href="#none"  onclick="fn_auth_pop('<c:out value="${type}"/>'); return false;" class="joinMember-link" title="새창열림">
	                      <div class="joinMember-box">
	                        <figure class="joinMember-figure">
	                          <img src="../../resources/images/sub/ico_accredit_01.png" alt="아이콘">
	                        </figure>
	                        <h4 class="joinMember-tit">휴대폰</h4>
	                        <p class="joinMember-txt">본인 명의의 휴대폰으로 인증하기</p>
	                      </div>
	                      <div class="joinMember-foot">휴대폰 인증하기</div>
	                    </a>
	                  </li>
                  
                  </c:if>
                  
                  <c:if test="${type eq 'child' }">
                  
                  
	                  <li class="joinMember-item">
	                    <a href="#" class="joinMember-link" onclick="fn_auth_pop('<c:out value="${type}"/>'); return false;">
	                      <div class="joinMember-box">
	                        <figure class="joinMember-figure">
	                          <img src="../../resources/images/sub/ico_accredit_01.png" alt="아이콘">
	                        </figure>
	                        <h4 class="joinMember-tit">휴대폰</h4>
	                        <p class="joinMember-txt">본인 명의의 휴대폰으로 인증하기</p>
	                      </div>
	                      <div class="joinMember-foot">휴대폰 인증하기</div>
	                    </a>
	                  </li>
                                    
                  </c:if>
                  
                  <li class="joinMember-item">
                    <a href="#" class="joinMember-link" onclick="ipinCert(); return false;">
                      <div class="joinMember-box">
                        <figure class="joinMember-figure">
                          <img src="../../resources/images/sub/ico_accredit_02.png" alt="아이콘">
                        </figure>
                        <h4 class="joinMember-tit">아이핀(I-PIN)</h4>
                        <p class="joinMember-txt">본인 명의의 아이핀(I-PIN)으로 인증하기</p>
                      </div>
                      <div class="joinMember-foot">아이핀 인증하기</div>
                    </a>
                  </li>
                </ul>
              </div>
            </div>
            <div class="joinMember-bot">
              <div class="joinMember-desc">
                <div class="joinMember-desc-title">
                  <img src="../../resources/images/sub/ico_desc.png" alt="아이콘">입력정보 처리에 대한 안내
                </div>
                <ul class="joinMember-desc-list">
                  <li class="joinMember-desc-item">ㆍ입력하신 휴대폰 정보는 별도 저장되지 않으며, 신용평가기관을 통한 실명확인용으로만 이용됩니다.</li>
                  <li class="joinMember-desc-item">ㆍ이에 동의하지 않으실 경우, 아이핀 인증을 이용하여 주시기 바랍니다.</li>
                </ul>
              </div>
            </div>
          </div>
        </div>



      </div>

  </div>
  </main>
