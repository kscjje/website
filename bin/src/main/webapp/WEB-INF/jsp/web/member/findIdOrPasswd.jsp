<%@page import="com.hisco.user.member.web.UserLoginController"%>
<%@page import="com.hisco.user.nice.web.NamefactController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>


<main class="content" id="content">

      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="/web/resources/images/sub/img_subVisual_05.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">아이디찾기</h3>
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

        <div class="joinMember">
          <div class="joinMember-inner">
            <div class="joinMember-wrap">
              <div class="joinMember-top">
                <h3 class="joinMember-title">아이디 또는 비밀번호를 잊으셨나요?</h3>
                <p class="joinMember-txt mt-4">아래의 모든 정보를 입력해 주시면 아이디 찾기/비밀번호 찾기가 가능합니다.</p>
                <ul class="joinMember-list">
                  <li class="joinMember-item">
                    <a href="/web/member/findId" class="joinMember-link">
                      <div class="joinMember-box">
                        <figure class="joinMember-figure">
                          <img src="/web/resources/images/sub/ico_find_01.png" alt="아이콘">
                        </figure>
                        <h4 class="joinMember-tit">아이디찾기</h4>
                        <p class="joinMember-txt">아이핀(i-pin) 인증 또는 휴대폰 인증 후
                          <br>아이디조회가 가능합니다.
                        </p>
                      </div>
                      <div class="joinMember-foot">아이디 찾기</div>
                    </a>
                  </li>
                  <li class="joinMember-item">
                    <a href="/web/member/findPasswd" class="joinMember-link">
                      <div class="joinMember-box">
                        <figure class="joinMember-figure">
                          <img src="/web/resources/images/sub/ico_find_02.png" alt="아이콘">
                        </figure>
                        <h4 class="joinMember-tit">비밀번호 찾기</h4>
                        <p class="joinMember-txt">아이핀(i-pin) 인증 또는 휴대폰 인증 후
                          <br>비밀번호변경이 가능합니다.
                        </p>
                      </div>
                      <div class="joinMember-foot">비밀번호 찾기</div>
                    </a>
                  </li>
                </ul>
              </div>
            </div>
            <div class="joinMember-bot">
              <div class="joinMember-desc">
                <div class="joinMember-desc-title">
                  <img src="/web/resources/images/sub/ico_desc.png" alt="아이콘">입력정보 처리에 대한 안내
                </div>
                <ul class="joinMember-desc-list">
                  <li class="joinMember-desc-item">ㆍ입력하신 휴대폰 정보는 별도 저장되지 않으며, 신용평가기관을 통한 실명확인용으로만 이용됩니다.</li>
                  <li class="joinMember-desc-item">ㆍ이에 동의하지 않으실 경우, 아이핀 인증을 이용하여 주시기 바랍니다.</li>
                </ul>
              </div>
            </div>
          </div>
        </div>



      </div>

  </main>