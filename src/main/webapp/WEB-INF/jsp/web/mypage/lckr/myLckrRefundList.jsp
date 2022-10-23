<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="egovframework.com.cmm.service.EgovProperties" %>
<%@ page import="com.hisco.cmm.util.*" %>
<%@ page import ="com.hisco.user.nice.web.NamefactController"%>

<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>

<script>
//상세페이지로이동
function fnGoDetail(edcPrgmid) {
//	var _scrollTop = window.scrollY || document.documentElement.scrollTop;
//	$('#scrollVar').val(_scrollTop);

	//location.hash = $('#searchVO').serialize();
	location.href = '<c:url value="../lckr"/>' + '/' + 1;
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
						<a href="javascript:void(0)" title="이동" class="pageTit-link">내예약관리</a>
					</li>
					<li class="pageTit-item">
						<a href="javascript:void(0)" title="이동" class="pageTit-link">사물함환불현황</a>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<div class="sub">
		<%@ include file="/WEB-INF/jsp/web/mypage/myPageInclude.jsp"%>
		<%@ include file="/WEB-INF/jsp/web/mypage/myRsvnInclude.jsp"%>
		<div class="article-bottom">
			<div class="article-lsit list">
				<table class="list-table table bor-black">
					<colgroup>
						<col style="width: 5%">
						<col style="width: 20%">
						<col style="width: 10%">
						<col style="width: 10%">
						<col style="width: 10%">
						<col style="width: 10%">
						<col style="width: 10%">
						<col style="width: 10%">
						<col style="width: 15%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="number">번호</th>
							<th scope="col" class="area">센터명</th>
							<th scope="col" class="locker">사물함위치</th>
							<th scope="col" class="lockerNum">사물함번호</th>
							<th scope="col" class="rentStart">임대시작일</th>
							<th scope="col" class="rentFinish">임대종료일</th>
							<th scope="col" class="fee">임대료(원)</th>
							<th scope="col" class="data">접수일자</th>
							<th scope="col" class="state">접수상태</th>
						</tr>
					</thead>
					<tbody>
                <tr>
                  <td class="number">10</td>
                  <td class="area">
                    <a href="#" class="under">별내동자치센터</a>
                  </td>
                  <td class="locker">
                    <a href="#" class="under">사무실옆</a>
                  </td>
                  <td class="lockerNum">1065</td>
                  <td class="rentStart">2022.04.01</td>
                  <td class="rentFinish">2022.05.31</td>
                  <td class="fee">2,000원</td>
                  <td class="data">2022.07.12</td>
                  <td class="state">결제취소</td>
                </tr>
                <tr>
                  <td class="number">9</td>
                  <td class="area">
                    <a href="#" class="under">별내동자치센터</a>
                  </td>
                  <td class="locker">
                    <a href="#" class="under">사무실옆</a>
                  </td>
                  <td class="lockerNum">1065</td>
                  <td class="rentStart">2022.04.01</td>
                  <td class="rentFinish">2022.05.31</td>
                  <td class="fee">2,000원</td>
                  <td class="data">2022.07.12</td>
                  <td class="state">
                    <p class="flag flag-green">환불신청</p>
                  </td>
                </tr>
                <tr>
                  <td class="number">8</td>
                  <td class="area">
                    <a href="#" class="under">별내동자치센터</a>
                  </td>
                  <td class="locker">
                    <a href="#" class="under">사무실옆</a>
                  </td>
                  <td class="lockerNum">1065</td>
                  <td class="rentStart">2022.04.01</td>
                  <td class="rentFinish">2022.05.31</td>
                  <td class="fee">2,000원</td>
                  <td class="data">2022.07.12</td>
                  <td class="state">
                    <p class="flag flag-blue">환불완료</p>
                  </td>
                </tr>
                <tr>
                  <td class="number">7</td>
                  <td class="area">
                    <a href="#" class="under">별내동자치센터</a>
                  </td>
                  <td class="locker">
                    <a href="#" class="under">사무실옆</a>
                  </td>
                  <td class="lockerNum">1065</td>
                  <td class="rentStart">2022.04.01</td>
                  <td class="rentFinish">2022.05.31</td>
                  <td class="fee">2,000원</td>
                  <td class="data">2022.07.12</td>
                  <td class="state">
                    <p class="flag flag-green">환불신청</p>
                  </td>
                </tr>
                <tr>
                  <td class="number">6</td>
                  <td class="area">
                    <a href="#" class="under">별내동자치센터</a>
                  </td>
                  <td class="locker">
                    <a href="#" class="under">사무실옆</a>
                  </td>
                  <td class="lockerNum">1065</td>
                  <td class="rentStart">2022.04.01</td>
                  <td class="rentFinish">2022.05.31</td>
                  <td class="fee">2,000원</td>
                  <td class="data">2022.07.12</td>
                  <td class="state">결제취소</td>
                </tr>
                <tr>
                  <td class="number">5</td>
                  <td class="area">
                    <a href="#" class="under">별내동자치센터</a>
                  </td>
                  <td class="locker">
                    <a href="#" class="under">사무실옆</a>
                  </td>
                  <td class="lockerNum">1065</td>
                  <td class="rentStart">2022.04.01</td>
                  <td class="rentFinish">2022.05.31</td>
                  <td class="fee">2,000원</td>
                  <td class="data">2022.07.12</td>
                  <td class="state">
                    <p class="flag flag-blue">환불완료</p>
                  </td>
                </tr>
                <tr>
                  <td class="number">4</td>
                  <td class="area">
                    <a href="#" class="under">별내동자치센터</a>
                  </td>
                  <td class="locker">
                    <a href="#" class="under">사무실옆</a>
                  </td>
                  <td class="lockerNum">1065</td>
                  <td class="rentStart">2022.04.01</td>
                  <td class="rentFinish">2022.05.31</td>
                  <td class="fee">2,000원</td>
                  <td class="data">2022.07.12</td>
                  <td class="state">결제취소</td>
                </tr>
                <tr>
                  <td class="number">3</td>
                  <td class="area">
                    <a href="#" class="under">별내동자치센터</a>
                  </td>
                  <td class="locker">
                    <a href="#" class="under">사무실옆</a>
                  </td>
                  <td class="lockerNum">1065</td>
                  <td class="rentStart">2022.04.01</td>
                  <td class="rentFinish">2022.05.31</td>
                  <td class="fee">2,000원</td>
                  <td class="data">2022.07.12</td>
                  <td class="state">결제취소</td>
                </tr>
                <tr>
                  <td class="number">2</td>
                  <td class="area">
                    <a href="#" class="under">별내동자치센터</a>
                  </td>
                  <td class="locker">
                    <a href="#" class="under">사무실옆</a>
                  </td>
                  <td class="lockerNum">1065</td>
                  <td class="rentStart">2022.04.01</td>
                  <td class="rentFinish">2022.05.31</td>
                  <td class="fee">2,000원</td>
                  <td class="data">2022.07.12</td>
                  <td class="state">결제취소</td>
                </tr>
                <tr>
                  <td class="number">1</td>
                  <td class="area">
                    <a href="#" class="under">별내동자치센터</a>
                  </td>
                  <td class="locker">
                    <a href="#" class="under">사무실옆</a>
                  </td>
                  <td class="lockerNum">1065</td>
                  <td class="rentStart">2022.04.01</td>
                  <td class="rentFinish">2022.05.31</td>
                  <td class="fee">2,000원</td>
                  <td class="data">2022.07.12</td>
                  <td class="state">결제취소</td>
                </tr>
              </tbody>
				</table>
			</div>
		</div>

		<div class="paginate mobile">
			<a href="#" class="prev02">처음</a>
			<a href="#" class="prev">이전</a>
			<a href="#">1</a>
			<a href="#" class="on">2</a><!-- 현위치 표시 : class="on" -->
			<a href="#">3</a>
			<a href="#">4</a>
			<a href="#">5</a>
			<a href="#" class="next">다음</a>
			<a href="#" class="next02">마지막</a>
		</div>

        <div class="paginate pc">
			<a href="#" class="prev02">처음</a>
			<a href="#" class="prev">이전</a>
			<a href="#">1</a>
			<a href="#" class="on">2</a><!-- 현위치 표시 : class="on" -->
			<a href="#">3</a>
			<a href="#">4</a>
			<a href="#">5</a>
			<a href="#">6</a>
			<a href="#">7</a>
			<a href="#">8</a>
			<a href="#">9</a>
			<a href="#">10</a>
			<a href="#" class="next">다음</a>
			<a href="#" class="next02">마지막</a>
		</div>
	</div>
</main>

