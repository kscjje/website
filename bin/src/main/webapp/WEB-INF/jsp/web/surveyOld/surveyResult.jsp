<%
	 /**
	  * @Class Name : surveyResult.jsp
	  * @Description : 설문조사 완료
	  * @Modification Information
	  * @
	  * @  수정일       수정자      수정내용
	  * @ -------       --------    ---------------------------
	  * @ 2020.10.13    김희택      최초 생성
	  *
	  *  @author 김희택
	  *  @since 2020.10.13
	  *  @version 1.0
	  *
	  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>
	<section class="feedback_ui">
			<div class="inner">
				<div class="top_head h_ty">
					<h1><a href="#">노원수학문화관 로고</a></h1>
					<a class="btn_close">닫기</a>
				</div>
				<!-- //top_head -->
				<div class="ment">
					<p><img src="<%=strContext%>/images/mobile/ico_check2.png" alt="체크아이콘"></p>
					<dl>
						<dt>만족도 조사에 응답해주셔서<br> 진심으로 감사합니다.</dt>
						<dd>더 나은 서비스로 보답 해 드리도록 하겠습니다.</dd>
					</dl>
				</div>
			</div>
			<!-- //inner-->
	
		<ul class="btn_area">
			<li><a href="http://www.hisco.co.kr/web/main/" class="btn_ty_m_c2">홈으로</a></li>
			<li><a href="/web/main" class="btn_ty_m_c1">통합예약 서비스 홈으로</a></li>
		</ul>
		<!-- //btn_area -->
	</section>
	<!-- //feeback_ui -->