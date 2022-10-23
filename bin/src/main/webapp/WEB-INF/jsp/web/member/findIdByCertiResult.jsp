<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>


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
      
      <c:choose>
	      <c:when test="${fn:length(resultList) > 0}">
		      <!-- 아이디가 있을때 -->
		        <div class="joinMember">
		          <div class="joinMember-inner">
		            <div class="joinMember-wrap">
		              <h3 class="joinMember-title">아이디찾기</h3>
		              <div class="joinMember-bot">
		                <div class="joinMember-desc">
		                  <figure class="joinMember-figure">
		                    <img src="/web/resources/images/sub/ico_find_01.png" alt="이미지">
		                  </figure>
		                  <strong class="mt-3">회원님의 사용중인 아이디는  
		                  	
		                  	<c:forEach var="item" items="${resultList}" begin="0" end="10" step="1" varStatus="status"> 
     							<br> ${item.id }
 							</c:forEach>
		                  	입니다.</strong>
		                  <p class="mt-2">가입당시에 입력한 내용을 토대로 찾은 결과 입니다.</p>
		                </div>
		              </div>
		            </div>
		
		
		
		          </div>
		        </div>
      
	      </c:when>
	      
	      <c:otherwise>
	      	
			<!-- 아이디가 없을때 -->
			        <div class="joinMember">
			          <div class="joinMember-inner">
			            <div class="joinMember-wrap">
			              <h3 class="joinMember-title">아이디찾기</h3>
			              <div class="joinMember-bot">
			                <div class="joinMember-desc">
			                  <figure class="joinMember-figure">
			                    <img src="/web/resources/images/sub/ico_find_03.png" alt="이미지">
			                  </figure>
			                  <strong class="mt-3">해당 본인인증으로 가입된 아이디가 존재하지 않습니다.</strong>
			                  <p class="mt-2">가입당시에 입력한 내용을 토대로 찾은 결과 입니다.</p>
			                </div>
			              </div>
			            </div>
			
			
			
			          </div>
			        </div>
        
	      </c:otherwise>
      </c:choose>
        
      


        <div class="badge-btn">
          <a href="/web/member/join/joinStep1" class="black">회원가입</a>
          <a href="/web/member/login" class="green">로그인</a>
          <a href="/web/member/findId" class="orange">다시찾기</a>
        </div>



      </div>

  </main>