<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
	<script>
		$(document).ready(function () {
			$('.btn-zoom-in').click(function(){
				nowZoom = nowZoom + 10;
				if(nowZoom >= 200) nowZoom = 200;   // 화면크기 최대 확대율 200%
				zooms();
			});
			$('.btn-zoom-out').click(function(){
				nowZoom = nowZoom - 10;
				if(nowZoom <= 70) nowZoom = 70;   // 화면크기 최대 축소율 70%
				zooms();
			});
		});
		var nowZoom = 100;
		function zooms() {
			document.body.style.zoom = nowZoom + "%";
			$('.btn-zoom-default').text(nowZoom + "%");
			if(nowZoom == 70) {
				alert("더 이상 축소할 수 없습니다.");   // 화면 축소율이 70% 이하일 경우 경고창
			}
			if(nowZoom == 200) {
				alert("더 이상 확대할 수 없습니다.");   // 화면 확대율이 200% 이상일 경우 경고창
			}
		}

		function doMsg(){
			alert("노원플랫폼은 사이트 준비중입니다.");
		}
	</script>
	<style>
		.top-bar .main-site li:nth-child(2) {background-color: #fff; color: #f3673d;}
	</style>
</head>

<body>
	<div id="jump_menu">
		<h1>건너뛰기 메뉴</h1>
		<ul>
			<li><a href="#gnb" id="top_menu">주메뉴 바로가기</a></li>
			<li><a href="#content">본문 바로가기</a></li>
		</ul>
	</div>
	<div id="wrap" class="index">
			<div id="content">
				<div class="intro">
					<div class="intro_visual">
						<div class="top_title">
						<h1 id="logo">
							<a href="<c:url value='/web/main'/>">국제화 교육특구 노원 노원평생교육포털</a>
						</h1>
						<p>노원평생교육포털 홈페이지 방문을 환영합니다.  <br class="mobile">	원하는 서비스를 선택해 주십시오.</p>
					</div>
						<div class="intro_box">
							<div class="intro_item">
								<div class="img_con portal">평생교육포털 이미지</div>
								<div class="txt_con">
									<p>평생교육포털</p>
									<a href="<c:url value='/web/main'/>" class="btn_s1_c1">메인이동</a>
								</div>
							</div>
							<div class="intro_item apply">
								<div class="img_con">강좌신청 이미지</div>
								<div class="txt_con">
									<p>강좌신청</p>
									<a href="<c:url value='/web/edc/program/list'/>" class="btn_s1_c2">바로가기</a>
								</div>
							</div>
						</div>
					</div>
					<!--// intro_visual  -->
					<div class="intro_link">
						<h2>교육지원</h2>
						<ul>
							<li>
								<a href="https://www.nowonedu.kr" target="_blank" title="새창열기">노원교육플랫폼</a>
							</li>
							<li>
								<a href="https://www.nowon.kr/math/main" target="_blank" title="새창열기">노원수학문화관</a>
							</li>
							<li>
								<a href="https://nowoncosmos.or.kr/" target="_blank" title="새창열기">노원천문우주과학관</a>
							</li>
							<li>
								<a href="https://science.seoul.go.kr/main" target="_blank" title="새창열기">서울시립과학관</a>
							</li>
							<li>
								<a href="https://www.nowonenglish.co.kr/" target="_blank" title="새창열기">노원 원어민영어 화상학습</a>
							</li>
							<li>
								<a href="https://nowon.kr/nwll/web/board/onlineclass/list?searchCnd=0&searchWrd=%EC%82%AC%EC%9D%B4%EB%B2%84%EA%B5%90%EC%8B%A4" target="_blank" title="새창열기">노원 어린이 사이버교실</a>
							</li>
							<li>
								<a href="https://www.nowon.kr/nwll/web/board/onlineclass/detail?nttId=219" target="_blank" title="새창열기">중 · 고등 사이버스쿨</a>
							</li>
						</ul>
					</div>
				</div>
				<!--// intro  -->
			</div>
		<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>
		<div class="dim">딤</div>
		<% /* 노원구 영문 사이트로 링크 이동
		<script>
			//kor eng 버튼
			$('.language-select a').click(function(){
					$('.language-select a').removeClass('on');
					$(this).addClass('on');
			})
		</script>
			*/ %>
	</div>
	<!-- // wrap -->


</body>

