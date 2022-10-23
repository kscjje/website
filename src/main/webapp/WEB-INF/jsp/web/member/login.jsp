<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javaScript" language="javascript">

	var element;

	function fn_login_action() {

		if (document.loginForm.id.value =="") {
	        alert("아이디를 입력하세요"); <%-- 아이디를 입력하세요 --%>
	        $("#id").focus();
	        return false;
	    } else if (document.loginForm.password.value =="") {
	        alert("비밀번호를 입력하세요"); <%-- 비밀번호를 입력하세요 --%>
	        $("#password").focus();
	        return false;
	    } else {
	    	if ($('#saveCheck').is('checked')) {
	    		fn_webid_save(true)
	    	}
	    	fn_ajaxSendData('./actionLogin.json',  $('#loginForm').serialize(), fn_login_success , "${_csrf.headerName}", "${_csrf.token}") ;
	    }

	}

	function fn_login_success(data) {

		 if (data.result.success) {
	     	//alert("로그인 성공");
	     	if (data.result.msg == "agree") {
				window.location.replace("./loginReagree?returnURL=" + $('#returnURL').val());
	     	//} else if (data.result.msg == "mustChangePw") {
	     	//	window.location.replace("./passwdChange");
	     	} else {
	     		var returnURL = $('#returnURL').val();
	         	window.location.replace(returnURL);
	     	}
	     } else {

	    	$('#password').val("");
	     	if (data.result.msg == "N") {
	     		$('#loginResultMsg').html("아이디 또는 비밀번호가 틀렸습니다.");
	     	} else if (data.result.msg.indexOf("G") == 0) {

	     		var msg = data.result.msg;
	     		var memno = data.result.msg.substring(2, data.result.msg.length);
	     		document.updateForm.updateId.value =memno;
	     		document.updateForm.submit();

	     	} else if (data.result.msg == "sleep") {
	    		$('#loginForm').attr("action" , "./loginSleep");
	    		$('#loginForm').submit();
	     	} else {
	 			$('#loginResultMsg').html(data.result.msg);

				if (data.result.msg.indexOf("비밀번호 찾기")>0) {
					if (confirm("비밀번호를 5회 이상 틀려 잠금상태 입니다.\n비밀번호 재설정 페이지로 이동하시겠습니까?")) {
						window.location.href = "./findPasswd";
					}
				}
	     	}
	     }
	}

	function fn_auth_pop() {
		var prsonCertiWin = window.open('/web/common/prsoncerti/prsonCertiPop', 'prsonCertiWin', 'width=650, height=650, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=100, top=50');

		if (!prsonCertiWin)
		{
			alert('※ 휴대전화 본인인증 \n팝업 차단을 해제해주시기 바랍니다.\n\n ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.');
			return false;
		}

	}

	function fn_auth_complete(mid,onlykey,paytype) {
		$('#LGD_MID').val(mid);
		$('#LGD_AUTHONLYKEY').val(onlykey);
		$('#LGD_PAYTYPE').val(paytype);
		$('#memberVO').attr("action" , "./loginCertCheck");
		$('#memberVO').submit();
	}

	function fn_webid_save(obj) {
		//쿠키저장
		var chked = obj;
		var id = $('#id').val();
		if (chked) {
			setCookie('webidSv', id , '365');
		} else {
			setCookie('webidSv', '' , '365');
		}

	}

  	$(document).ready(function() {

		$('#id').focus();

		$('#password').keydown(function() {
			$('#loginResultMsg').html("");

			if (event.keyCode==13) {
				fn_login_action();
			}
		});

  	});

  	$(document).ready(function() {

		var thisTimer = setInterval(function() {

			clearInterval(thisTimer);
			$("#ssIFrame_google").attr('title', '구글 로그인 페이지');

		}, 500);

  });

</script>

<form name="loginForm" id="loginForm" method="post">
<input type="hidden" id="returnURL" name="returnURL" value="<c:url value="${loginReturnURL}" />" />
<input type="hidden" name="_csrf" value="${_csrf.token}" />
<main class="content" id="content">

      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="/web/resources/images/sub/img_subVisual_05.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">로그인</h3>
        </div>
      </div>
      <div class="pageTit">
        <div class="pageTit-inner">
          <div class="pageTit-wrap">
            <h3 class="pageTit-title">로그인</h3>
            <ul class="pageTit-list">
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
                  <img src="/web/resources/images/sub/ico_snb_home.png" alt="홈">
                </a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">로그인</a>
              </li>
            </ul>
          </div>
        </div>
      </div>


      <div class="sub">

        <div class="login">
          <div class="login-inner">
            <div class="login-wrap">
              <h3 class="login-title">로그인하며 다양한 강좌를 즐기세요</h3>
              <p class="login-txt">남양주시 주민자치센터 통합관리시스템의 강좌는 로그인 후 강좌를 보거나 수강신청을 하실 수 있습니다.</p>
              <div class="login-box w_box ">
                <div class="login-conbox w400">
                  <div class="input-group">
                    <label for="id" class="visually-hidden">아이디</label>
                    <input name="id" id="id" title="아이디를 입력하세요" type="text" class="inp02_box w400" placeholder="아이디" value="<c:out value="${cookie.webidSv.value}"/>">
                  </div>
                  <div class="input-group">
                    <label for="password" class="visually-hidden">비밀번호</label>
                    <input name="password" id="password" title="비밀번호을 입력하세요" type="password" class="inp02_box w400" placeholder="비밀번호">
                  </div>
                  <p class="login-warning" style="padding-top:14px;padding-bottom:14px;" id="loginResultMsg"></p>
                  <p class="login-warning" style="color:blue">동일 아이디로 중복 로그인할 경우 자동으로 로그아웃되니 주의하시기 바랍니다.</p>
                  <button type="button" onclick="fn_login_action()" class="btn btn-badge green w-100 mt-4">로그인</button>
                </div>
              </div>
              <div class="login-find">
                <p class="or">또는</p>
                <hr>
                <ul class="login-find-list">
                <li class="login-find-item">
                    <a href="/web/member/findIdOrPasswd" class="login-find-link">
                     	 아이디/비밀번호 찾기
                    </a>
                </li>
                  <li class="login-find-item">
                    <a href="/web/member/join/joinStep1" class="login-find-link">
                      회원가입 하기
                    </a>
                  </li>
                  </ul>
              </div>



            </div>
          </div>
        </div>
      </div>
    </main>
</form>    