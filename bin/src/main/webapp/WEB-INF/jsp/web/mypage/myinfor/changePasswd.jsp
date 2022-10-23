<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


    <main class="content" id="content">

      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="../../resources/images/sub/img_subVisual_04.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">마이페이지</h3>
        </div>
      </div>
      <div class="pageTit">
        <div class="pageTit-inner">
          <div class="pageTit-wrap">
            <h3 class="pageTit-title">마이페이지</h3>
            <ul class="pageTit-list">
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
                  <img src="../../resources/images/sub/ico_snb_home.png" alt="홈">
                </a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">마이페이지</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">내정보관리</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">비밀번호 변경</a>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="sub">
        
        
      <%@ include file="/WEB-INF/jsp/web/mypage/myPageInclude.jsp"%>
		<%@ include file="/WEB-INF/jsp/web/mypage/myInforInclude.jsp"%>
        
<form name="memberVO" id="memberVO" method="post" action="./changePasswdSave">
<input type="hidden" id="returnURL" name="returnURL" value="<c:url value="${loginReturnURL}" />" />
<input type="hidden" name="_csrf" value="${_csrf.token}" />
<input type="hidden" name="id" id="id" value="${loginVO.id}" />
<input type="hidden" name="hp" id="hp" value="${loginVO.hp}" />
        <div class="login">
          <div class="login-inner">
            <div class="login-wrap">
              <div class="login-top">
                <h3 class="login-title">비밀번호 변경</h3>
                <p class="login-txt">
                  개인정보 보호를 위해 비밀번호는 <strong>주기적으로 변경</strong>하시는 것을 추천드립니다.
                  <br class="pc">
                  비밀번호가 노출되지 않도록 주의해 주시길 바랍니다.
                </p>
              </div>
              <div class="login-content">
                <div class="login-box w_box w400">
                  <!-- on 들어오면 capsWarning block -->
                  <div class="login-item on">
                    <div class="input-group">
                      <label for="inp08_02_01">현재 비밀번호</label>
                      <input type="password" name="password" id="inp08_02_01" class="inp02_box w400" onkeyup="checkCapsLock(event);">
                    </div>
                    <div class="capsWarning" style="display: none;">
                      Caps Lock이 켜져 있습니다.
                    </div>
                  </div>

                </div>
                <div class="login-box w_box w400">
                  <!-- on 들어오면 capsWarning block -->
                  <div class="login-item on">
                    <div class="input-group">
                      <label for="inp08_02_02">비밀번호 변경</label>
                      <input type="password" name="pw1" id="inp08_02_02" class="inp02_box w400" onkeyup="checkCapsLock(event); passCheck('pw1'); return false;">
                    </div>
                  </div>
                  <p class="login-desc">※8~16자 영문 대소문자, 특수문자</p>
                </div>
                <div class="login-box w_box w400">
                  <!-- on 들어오면 capsWarning block -->
                  <div class="login-item on">
                    <div class="input-group">
                      <label for="inp08_02_03">비밀번호 확인</label>
                      <input type="password" name="pw2"  id="inp08_02_03" class="inp02_box w400" onkeyup="checkCapsLock(event); passCheck('pw2'); return false;">
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
 </form>       
        
        
        
        <div class="badge-btn">
          <!--<a href="#none" class="black" data-bs-target="#password-change" data-bs-toggle="modal">마이페이지로</a>-->
          <a href="#none" class="green" data-bs-target="#password-change" data-bs-toggle="modal">수정</a>
        </div>
      </div>

  </div>

  </div>
  </main>
  
<script type="text/javascript">

function checkCapsLock(event)  {
	  if (event.getModifierState("CapsLock")) {
	    $(".capsWarning").show();
	  }else {
	    $(".capsWarning").hide();
	  }
	}
	
	
function changePassword() {
	//alert("changePassword()");
	
	var password = $('input[name=password]').val();
	var pw1 =$('input[name=pw1]').val();
	var pw2 =$('input[name=pw2]').val();
	
	var newPassCheck = false;
	newPassCheck = passCheck("pw1");
	newPassCheck = passCheck("pw2");
	
	if(!newPassCheck){
		alert("비밀번호 조건에 맞게 비밀번호를 작성해주세요.");
		$("#cancelChangePassword").click();
		return;
	}
	
	
	if( pw1 != pw2){
		alert("변경할 비밀번호가 일치하지 않습니다.");
		$("#cancelChangePassword").click();
		return;
	}else{
		$('#memberVO').submit();
	}
	
}

function passCheck(name) {
	
	var newPassword = $("input[name="+name+"]").val()
	var msg = passwdValidationNew(newPassword);
	if (msg == '사용가능한 비밀번호 입니다.') {
		//$('#errPass').removeClass('text-red').addClass('text-green').html('사용가능한 비밀번호 입니다.');
		return true;
	} else {
		//$('#errPass').removeClass('text-green').addClass('text-red').html(msg);
		$("input[name="+name+"]").focus();
        return false;
	}
}
</script>

