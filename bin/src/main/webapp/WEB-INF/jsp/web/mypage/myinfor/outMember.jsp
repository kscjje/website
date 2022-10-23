<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>

<script>

	function fn_out_member() {
		
		/* 
		if ($('#outReason').val() == "") {
			alert("탈퇴 이유를 적어 주세요");
			$('#outReason').focus();
		} else {
			document.memberVO.submit();
		}*/
		
		var id =$("input[name=id]").val();
		var password = $("input[name=password]").val();
		
		if ((id === undefined) || (id == null) || (id == '')) {
			alert("아이디를 입력해 주세요");
			return;
		}
		if ((password === undefined) || (password == null) || (password == '')) {
			alert("비밀번호를 입력해 주세요");
			return;
		}
		
		if (confirm("정말 탈퇴하시겠습니까?")) {
			document.memberVO.submit();
		} else {
			//top.location.href = "/web/main";
		}

	}

</script>

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
                <a href="javascript:void(0)" title="이동" class="pageTit-link">회원탈퇴</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="sub">
       
      <%@ include file="/WEB-INF/jsp/web/mypage/myPageInclude.jsp"%>
		<%@ include file="/WEB-INF/jsp/web/mypage/myInforInclude.jsp"%>


	<form:form commandName="memberVO" name="memberVO" id="memberVO" action="./outMemberSave" method="post">

	
        <div class="login">
          <div class="login-inner">
            <div class="login-wrap">
              <div class="login-top">
                <h3 class="login-title">회원탈퇴</h3>
                <p class="login-txt">
                  비밀번호를 입력하시고 탈퇴 버튼을 누르시면 탈퇴됩니다.
                  <br class="pc">
                  복구가 안되니 신중히 생각해주세요.
                </p>
              </div>
              <div class="login-content">
                <div class="login-box w_box w400">
                  <div class="login-item on">
                    <div class="input-group">
                      <label for="inp08_05_01">아이디</label>
                      <input type="text" name="id" id="inp08_05_01" class="inp02_box w400">
                    </div>
                  </div>
                  <p class="login-desc">※8~16자 영문 대소문자, 특수문자</p>
                </div>
                <div class="login-box w_box w400">
                  <!-- on 들어오면 capsWarning block -->
                  <div class="login-item on">
                    <div class="input-group">
                      <label for="inp08_05_02">비밀번호</label>
                      <input type="password" name="password" id="inp08_05_02" class="inp02_box w400" >
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
          <a href="/web/mypage/myinfor/myInforRegist" class="black">마이페이지로</a>
          <a href="javascript:fn_out_member();" class="gray">탈퇴</a>
        </div>
      </div>

	</form:form>


  </div>

  </div>
  </main>
