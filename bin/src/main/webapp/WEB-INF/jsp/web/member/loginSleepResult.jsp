<%
 /**
  * @Class Name : loginSleepResult.jsp
  * @Description : 휴면계정 해제 결과페이지
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.09.22    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.09.22
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>
<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit mb20">휴면계정 안내</h3>
		<p class="desc mb70">회원님 반갑습니다! 국립중앙과학관입니다.</p>
		<!-- mypage_wrap -->
		<div class="mypage_wrap">
			<!-- signup -->
			<div class="signup3">
				<ul>
					<li>
						<p class="img"><img src="<%=strContext%>/images/sub/img_sleep_off.png" alt="휴면계정해지후이미지"></p>
						<dl>
							<dt>"<c:out value="${resultVO.id }"/>" 계정이 휴면상태에서 정상적으로 해제 되었습니다.</dt>
							<dd>회원님 정보의 안전한 보관을 위하여 비밀번호 변경을 부탁드리며<br class="pc"> 변경된 회원정보가 있다면 최신 정보로 업데이트 해 주시기 바랍니다.</dd>
						</dl>
					</li>
				</ul>
			</div>
			<!-- //signup -->
		</div>
		<!-- //mypage_wrap -->
		<ul class="btn_area long">
			<li><a href="/web/mypage/myinfor/changePasswd" class="btn_ty_m3_c5">비밀번호 변경</a></li>
			<li><a href="/web/mypage/myinfor/myInforRegist" class="btn_ty_m3_c3">회원정보 변경</a></li>
			<li><a href="/web/mypage/myinfor/changeMycar" class="btn_ty_m3_c1">내 차량정보 변경</a></li>
		</ul>
		<!-- //btn_area -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->