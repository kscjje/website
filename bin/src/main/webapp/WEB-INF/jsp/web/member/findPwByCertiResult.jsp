<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : findPwByCertiResult.jsp
  * @Description : 본인 인증 비밀번호 변경 결과
  * @Modification Information
  * @
  * @  수정일              수정자           수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.05.12    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.05.12
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

		<div class="sub_cont" id="container">
			<div class="inner">
				<h3 class="tit mb20">비밀번호 변경 완료</h3>
				<!-- join_end -->
					<div class="join_end">
						<div class="invit3">
							로그인하시면 홈페이지 이용이 가능합니다.
							<div><a href="/web/member/login" class="btn_ty_m3_c1">로그인 하기</a></div>
						</div>
					</div>
					<!-- //join_end -->
			</div>
			<!-- //inner -->
		</div>

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>