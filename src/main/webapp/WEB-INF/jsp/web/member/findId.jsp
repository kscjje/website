<%@page import="com.hisco.user.member.web.UserLoginController"%>
<%@page import="com.hisco.user.nice.web.NamefactController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>

<script type="text/javascript">

//<![CDATA[
//모바일 본인인증
function fn_auth_pop(type) {
	
	$('#iframe_namefact').contents().find('#CHECKPLUS_MOBILE_namefact_form button').click();
	
	return;		
	
}

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
	
	/* function certCallback(){
		alert("certCallback();");
	} */
	
	var certCallback = function(){
		$('input[name=certificationYn]').val("Y");
	}
	
	function findIdByCertiResult() {
		var certificationYn = $('input[name=certificationYn]').val();
		var name = $('#nameText').val();
		var birthDate = $('#birthDateText').val();
		if (name == "") {
			alert("이름을 입력해 주세요.");
			$('#nameText').focus();
			return;
		} else if (birthDate == "") {
			alert("생년월일을 모두 선택해 주세요.");
			$('#birthDateText').focus();
			return;
		}
		
		if(certificationYn == 'Y'){
			document.memberVO.submit();
		}else{
			alert("본인인증을 해주세요");
			return;
		}
	}



</script>

<main class="content" id="content">

      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="/web/resources/images/sub/img_subVisual_05.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">아이디 찾기</h3>
        </div>
      </div>
      <div class="pageTit">
        <div class="pageTit-inner">
          <div class="pageTit-wrap">
            <h3 class="pageTit-title">아이디 찾기</h3>
            <ul class="pageTit-list">
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
                  <img src="/web/resources/images/sub/ico_snb_home.png" alt="홈">
                </a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">로그인</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">아이디 찾기</a>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="sub">

        <div class="joinMember">
          <div class="joinMember-inner">
            <div class="joinMember-wrap">
              <div class="joinMember-top">
                <h3 class="joinMember-title">아이디를 잊으셨나요?</h3>
                <p class="joinMember-txt mt-4">아래의 모든 정보를 입력해 주시면 아이디 찾기가 가능합니다.</p>
                <ul class="joinMember-list">
                
                  <li class="joinMember-item">
                  
                  <form name="memberVO" id="memberVO" method="post" action="./findIdByCertiResult">
					<input type="hidden" id="returnURL" name="returnURL" value="<c:url value="${loginReturnURL}" />" />
					<input type="hidden" name="_csrf" value="${_csrf.token}" />
					<input type="hidden" name="certificationYn" value="N" />
					
                  <div class="joinMember-box" style="padding:40px 0">
                  	<p class="login-txt">가입시 등록한 정보로 아이디를 찾을 수 있습니다.</p>
					<br/>
                  	
					<div class="login-box w_box ">
		                <div class="login-conbox w400" style="">
		                  <div class="input-group">
		                    <label for="inp09_06_01" class="visually-hidden"></label>
		                    <input type="text" id="nameText" name="name" class="inp02_box w400" placeholder="이름">
		                  </div>
		                  <div class="input-group">
		                    <label for="inp09_06_02" class="visually-hidden"></label>
		                    <input type="text" id="birthDateText" name="birthDate" class="inp02_box w400" placeholder="생년월일 예)19990101">
		                  </div>
		                </div>
		              </div>                  
 					</div>
 					</form>
 					
 					
                  <button type="button" class="btn btn-badge green w-100 mt-4"  onclick="findIdByCertiResult()">아이디 찾기</button>
                  </li>                  
                  
                  <li class="joinMember-item">
                    <a href="#" class="joinMember-link" onclick="fn_auth_pop('<c:out value="${type}"/>'); return false;" >
                      <div class="joinMember-box">
                        <figure class="joinMember-figure">
                          <img src="/web/resources/images/sub/ico_accredit_01.png" alt="아이콘">
                        </figure>
                        <h4 class="joinMember-tit">휴대폰</h4>
                        <p class="joinMember-txt">휴대폰 인증 후
                          <br>아이디 조회가 가능합니다.
                        </p>
                      </div>
                      <div class="joinMember-foot">휴대폰 인증</div>
                    </a>
                  </li>
                  
                  <li class="joinMember-item">
                    <a href="#" class="joinMember-link" onclick="ipinCert(); return false;">
                      <div class="joinMember-box">
                        <figure class="joinMember-figure">
                          <img src="/web/resources/images/sub/ico_accredit_02.png" alt="아이콘">
                        </figure>
                        <h4 class="joinMember-tit">아이핀(I-PIN)</h4>
                        <p class="joinMember-txt">아이핀(i-pin) 인증 후
                          <br>아이디 조회가 가능합니다.
                        </p>
                      </div>
                      <div class="joinMember-foot">아이핀 인증</div>
                    </a>
                  </li>
                </ul>
              </div>
            </div>
            <div class="joinMember-bot">
              <div class="joinMember-desc">
                <div class="joinMember-desc-title">
                  <img src="/web/resources/images/sub/ico_desc.png" alt="아이콘">입력정보 처리에 대한 안내
                </div>
                <ul class="joinMember-desc-list">
                  <li class="joinMember-desc-item">ㆍ입력하신 휴대폰 정보는 별도 저장되지 않으며, 신용평가기관을 통한 실명확인용으로만 이용됩니다.</li>
                  <li class="joinMember-desc-item">ㆍ이에 동의하지 않으실 경우, 아이핀 인증을 이용하여 주시기 바랍니다.</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        
        <!--휴대폰 인증-->
        <c:url var="namefact_url" value="/web/namefact/findIdByCerti">
					<c:param name="<%=NamefactController.namefact_design_param%>"  value=""/>
					<c:param name="<%=NamefactController.namefact_type_param%>"    value="Normal"/>
					<c:param name="<%=NamefactController.namefact_end_url_param%>" value="/web/namefact/memInfoEnd"/>
					<c:param name="add_css" value="${add_css_url}"/>
					<c:param name="mode" value="button"/>
					<c:param name="inputVal" value=""/>
				</c:url>
				
				<iframe id="iframe_namefact" src="<c:out value='${namefact_url}'/>" title="본인인증 처리" class="auto_height" style="width: 100%; height: 1000px; border: 0 none; background: transparent; display:none;"></iframe>			
			
			<!--  아이핀 인증 -->
			<c:url var="namefact_url_ipin" value="/web/namefact/findIdByCerti">
					<c:param name="<%=NamefactController.namefact_design_param%>"  value=""/>
					<c:param name="<%=NamefactController.namefact_type_param%>"    value="Normal"/>
					<c:param name="<%=NamefactController.namefact_end_url_param%>" value="/web/namefact/memInfoEnd"/>
					<c:param name="add_css" value="${add_css_url}"/>
					<c:param name="mode" value="button"/>
					<c:param name="inputVal" value=""/>
			</c:url>
				
			<iframe id="iframe_namefact_ipin" src="<c:out value='${namefact_url_ipin}'/>" title="본인인증 처리" class="auto_height" style="width: 100%; height: 1000px; border: 0 none; background: transparent; display:none;"></iframe>			
			

      </div>

  </main>