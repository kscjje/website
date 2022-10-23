<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : findIdResult.jsp
  * @Description : 아이디 찾기 페이지
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
			
				<c:choose>
				
					<c:when test="${result.success eq 'true' }">						
				
						<div class="inner">
							<h3 class="tit mb20">아이디 찾기</h3>
							<div class="mypage_wrap mb35">
								<h4 class="h4">아이디 : <strong class="blu"><c:out value="${result.msg}"/></strong></h4>
							</div>
							<div class="mypage_wrap">
								<h4 class="h4 mb20">아이디 찾기 안내 </h4>
								<p class="desc mb35">비밀번호를 찾으시려면 비밀번호 찾기를 클릭하세요.</p>
								<ul class="btn_area">
									<li><a href="/web/member/login" class="btn_ty_m_c4">로그인하기</a></li>
									<li><a href="/web/member/findPasswd" class="btn_ty_m_c1">비밀번호 찾기</a></li>
								</ul>
							</div>
						</div>				
				
					</c:when>				
				
					<c:when test="${result.success eq 'false' }">				

						<div class="inner">
							<h3 class="tit mb20">아이디 찾기</h3>
							<div class="mypage_wrap">
								<h4 class="h4">등록된 아이디가 없습니다.</h4>
							</div>
							<ul class="btn_area">
								<li><a href="/web/member/join/joinStep1" class="btn_ty_m_c1">회원가입 하기</a></li>
							</ul>
						</div>
						<!-- //inner -->
						
					</c:when>
				</c:choose>	
								
			</div>
			<!-- //sub_cont -->

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>
