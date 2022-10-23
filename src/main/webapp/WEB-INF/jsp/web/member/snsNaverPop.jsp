<%
 /**
  * @Class Name : snsNaverAjax.jsp
  * @Description : 네이버 연동 로그인 콜백 url
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.13   진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.08.13
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>
<script src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.2.js" charset="utf-8"></script>
<script>
	var naver_id_login = new naver_id_login("<c:out value="${naverKey}" />", "<c:out value="${currentDomain}" />/web/member/snsNaverPop");
	naver_id_login.get_naver_userprofile("naverSignInCallback()");

	function naverSignInCallback() {
		var email = naver_id_login.getProfileData('email');
		var id = naver_id_login.getProfileData('id');

		window.opener.fn_sns_join(id ,email ,'1001');
		window.close()

	}

</script>