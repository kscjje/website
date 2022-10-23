<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>


<script>

	function fn_member_submit() {
		
		if ($('#password').val() == "") {
			alert("비밀번호를 입력해 주세요.");
			$("#password").focus();
		} else {
			document.loginVO.submit();
		}
		
		return false;
	}
	
	setTimeout(function() {
		$("#password").focus();	
	}, 300);
	
	function fn_member_cancel() {
		if (confirm("취소하시겠습니까?")) {
			top.location.href="/web/main";
		}
	}
	
</script>

    <main class="content" id="content">


	<c:if test="${check_service eq 'myInforRegist'}">
	
      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="../../resources/images/sub/img_subVisual_05.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">개인정보 수정</h3>
        </div>
      </div>

      <div class="sub">
        <div class="pageTit">
          <div class="pageTit-inner">
            <div class="pageTit-wrap">
              <h3 class="pageTit-title">개인정보 수정</h3>
              <ul class="pageTit-list">
                <li class="pageTit-item">
                  <a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
                    <img src="../../resources/images/sub/ico_snb_home.png" alt="홈">
                  </a>
                </li>
                <li class="pageTit-item">
                  <a href="javascript:void(0)" title="이동" class="pageTit-link">로그인</a>
                </li>
                <li class="pageTit-item">
                  <a href="javascript:void(0)" title="이동" class="pageTit-link">개인정보 수정</a>
                </li>
              </ul>
            </div>
          </div>
        </div>

		<form:form commandName="loginVO" name="loginVO" id="loginVO" action="./passwdCheckSave" method="post">
		<input type="hidden" name="check_service" value="<c:out value="${check_service}"/>" />
		
        <div class="login">
          <div class="login-inner">
            <div class="login-wrap">
              <div class="login-top">
                <h3 class="login-title">비밀번호 입력</h3>
                <p class="login-txt">※ 개인정보보호를 위하여 비밀번호 확인 후 회원정보의 관리/변경이 가능합니다.<br/>※ 아래 비밀번호 입력란에 비밀번호 입력 후 "비밀번호 확인"을 클릭하세요.</p>
              </div>
              <div class="login-content">
                <div class="login-box w_box w400">
                  <!-- on 들어오면 capsWarning block -->
                  <div class="login-item on">
                    <div class="input-group">
                      <label for="password">비밀번호</label>
                      <form:password path="password" title="비밀번호" class="inp02_box w400" maxlength="20" placeHolder="현재 비밀번호를 입력해 주세요."/>
                    </div>
                    <div class="capsWarning" style="display:none;">
                      Caps Lock이 켜져 있습니다.
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="badge-btn">
          <a href="#none" class="green" onclick="fn_member_submit(); return false;">비밀번호 확인</a>
          <a href="#none" class="gray" onclick="fn_member_cancel(); return false;">취소</a>
        </div>
        
        </form:form>
      </div>



	</c:if>
	
	
	<c:if test="${check_service eq 'outMember'}">

     <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="../../resources/images/sub/img_subVisual_05.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">개인정보 수정</h3>
        </div>
      </div>

      <div class="sub">
        <div class="pageTit">
          <div class="pageTit-inner">
            <div class="pageTit-wrap">
              <h3 class="pageTit-title">회원탈퇴</h3>
              <ul class="pageTit-list">
                <li class="pageTit-item">
                  <a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
                    <img src="../../resources/images/sub/ico_snb_home.png" alt="홈">
                  </a>
                </li>
                <li class="pageTit-item">
                  <a href="javascript:void(0)" title="이동" class="pageTit-link">로그인</a>
                </li>
                <li class="pageTit-item">
                  <a href="javascript:void(0)" title="이동" class="pageTit-link">회원탈퇴</a>
                </li>
              </ul>
            </div>
          </div>
        </div>

		<form:form commandName="loginVO" name="loginVO" id="loginVO" action="./passwdCheckSave" method="post">
		<input type="hidden" name="check_service" value="<c:out value="${check_service}"/>" />
		
        <div class="login">
          <div class="login-inner">
            <div class="login-wrap">
              <div class="login-top">
                <h3 class="login-title">비밀번호 입력</h3>
                <p class="login-txt">※ 개인정보보호를 위하여 비밀번호 확인 후 회원정보의 관리/변경이 가능합니다.<br/>※ 아래 비밀번호 입력란에 비밀번호 입력 후 "비밀번호 확인"을 클릭하세요.</p>
              </div>
              <div class="login-content">
                <div class="login-box w_box w400">
                  <!-- on 들어오면 capsWarning block -->
                  <div class="login-item on">
                    <div class="input-group">
                      <label for="password">비밀번호</label>
                      <form:password path="password" title="비밀번호" class="inp02_box w400" maxlength="20" placeHolder="현재 비밀번호를 입력해 주세요."/>
                    </div>
                    <div class="capsWarning" style="display:none;">
                      Caps Lock이 켜져 있습니다.
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="badge-btn">
          <a href="#none" class="green" onclick="fn_member_submit(); return false;">비밀번호 확인</a>
          <a href="#none" class="gray" onclick="fn_member_cancel(); return false;">취소</a>
        </div>
        
        </form:form>
      </div>

	</c:if>


  </div>

  </div>
  </main>