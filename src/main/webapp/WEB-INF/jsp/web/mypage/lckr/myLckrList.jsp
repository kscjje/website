<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
						<a href="javascript:void(0)" title="이동" class="pageTit-link">사물함이력현황</a>
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
						<col style="width: 25%">
						<col style="width: 15%">
						<col style="width: 20%">
						<col style="width: 10%">
						<col style="width: 10%">
						<col style="width: 15%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="number">번호</th>
							<th scope="col" class="title">강좌명</th>
							<th scope="col" class="area">센터명</th>
							<th scope="col" class="data">교육기간</th>
							<th scope="col" class="fee">수강료</th>
							<th scope="col" class="reception">접수일자</th>
							<th scope="col" class="state">접수상태</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="number">10</td>
							<td class="title">
								<a href="javascript:fnGoDetail()" class="under">캘리그라피</a>
							</td>
							<td class="area">
								<a href="javascript:fnGoDetail()" class="under">별내동자치센터</a>
							</td>
							<td class="data">2022.04.01~2022.06.30<br> 화 / 18:30 ~ 20:20</td>
							<td class="fee">2,000원</td>
							<td class="reception">2022-08-02</td>
							<td class="state">
								<p class="flag flag-green">결제대기</p>
							</td>
						</tr>
                <tr>
                  <td class="number">9</td>
                  <td class="title">
                    <a href="javascript:fnGoDetail()" class="under">사주명리학</a>
                  </td>
                  <td class="area">
                    <a href="javascript:fnGoDetail()" class="under">별내동자치센터</a>
                  </td>
                  <td class="data">2022.04.01~2022.06.30
                    <br> 화 / 18:30 ~ 20:20
                  </td>
                  <td class="fee">2,000원</td>
                  <td class="reception">2022-08-02</td>
                  <td class="state">
                    <a href="#" class="red" data-bs-target="#cancle-payment" data-bs-toggle="modal">결제취소</a>
                  </td>
                </tr>
                <tr>
                  <td class="number">8</td>
                  <td class="title">
                    <a href="javascript:fnGoDetail()" class="under">한글서에전문가과정-한경희</a>
                  </td>
                  <td class="area">
                    <a href="javascript:fnGoDetail()" class="under">별내동자치센터</a>
                  </td>
                  <td class="data">2022.04.01~2022.06.30
                    <br> 화 / 18:30 ~ 20:20
                  </td>
                  <td class="fee">2,000원</td>
                  <td class="reception">2022-08-02</td>
                  <td class="state">
                    <p class="flag flag-red">결제시간경과취소</p>
                  </td>
                </tr>
                <tr>
                  <td class="number">7</td>
                  <td class="title">
                    <a href="javascript:fnGoDetail()" class="under">주역&amp;보학</a>
                  </td>
                  <td class="area">
                    <a href="javascript:fnGoDetail()" class="under">별내동자치센터</a>
                  </td>
                  <td class="data">2022.04.01~2022.06.30
                    <br> 화 / 18:30 ~ 20:20
                  </td>
                  <td class="fee">2,000원</td>
                  <td class="reception">2022-08-02</td>
                  <td class="state">
                    <p class="flag flag-green">선착대기</p>
                  </td>
                </tr>
                <tr>
                  <td class="number">6</td>
                  <td class="title">
                    <a href="javascript:fnGoDetail()" class="under">문인화</a>
                  </td>
                  <td class="area">
                    <a href="javascript:fnGoDetail()" class="under">별내동자치센터</a>
                  </td>
                  <td class="data">2022.04.01~2022.06.30
                    <br> 화 / 18:30 ~ 20:20
                  </td>
                  <td class="fee">2,000원</td>
                  <td class="reception">2022-08-02</td>
                  <td class="state">
                    <a href="#" class="red" data-bs-target="#apply-classes" data-bs-toggle="modal">결제취소</a>
                  </td>
                </tr>
                <tr>
                  <td class="number">5</td>
                  <td class="title">
                    <a href="javascript:fnGoDetail()" class="under">한글한문서예</a>
                  </td>
                  <td class="area">
                    <a href="javascript:fnGoDetail()" class="under">별내동자치센터</a>
                  </td>
                  <td class="data">2022.04.01~2022.06.30
                    <br> 화 / 18:30 ~ 20:20
                  </td>
                  <td class="fee">2,000원</td>
                  <td class="reception">2022-08-02</td>
                  <td class="state">
                    <button class="green">재등록</button>
                  </td>
                </tr>
                <tr>
                  <td class="number">4</td>
                  <td class="title">
                    <a href="javascript:fnGoDetail()" class="under">펜글씨</a>
                  </td>
                  <td class="area">
                    <a href="javascript:fnGoDetail()" class="under">별내동자치센터</a>
                  </td>
                  <td class="data">2022.04.01~2022.06.30
                    <br> 화 / 18:30 ~ 20:20
                  </td>
                  <td class="fee">2,000원</td>
                  <td class="reception">2022-08-02</td>
                  <td class="state">
                    <a href="#" class="blue">환불신청</a>
                  </td>
                </tr>
                <tr>
                  <td class="number">3</td>
                  <td class="title">
                    <a href="javascript:fnGoDetail()" class="under">캘리그라피</a>
                  </td>
                  <td class="area">
                    <a href="javascript:fnGoDetail()" class="under">별내동자치센터</a>
                  </td>
                  <td class="data">2022.04.01~2022.06.30
                    <br> 화 / 18:30 ~ 20:20
                  </td>
                  <td class="fee">2,000원</td>
                  <td class="reception">2022-08-02</td>
                  <td class="state">환불완료</td>
                </tr>
                <tr>
                  <td class="number">2</td>
                  <td class="title">
                    <a href="javascript:fnGoDetail()" class="under">통기타</a>
                  </td>
                  <td class="area">
                    <a href="javascript:fnGoDetail()" class="under">별내동자치센터</a>
                  </td>
                  <td class="data">2022.04.01~2022.06.30
                    <br> 화 / 18:30 ~ 20:20
                  </td>
                  <td class="fee">2,000원</td>
                  <td class="reception">2022-08-02</td>
                  <td class="state">신청취소</td>
                </tr>
                <tr>
                  <td class="number">1</td>
                  <td class="title">
                    <a href="javascript:fnGoDetail()" class="under">한글서예전문가과정-한경희</a>
                  </td>
                  <td class="area">
                    <a href="javascript:fnGoDetail()" class="under">별내동자치센터</a>
                  </td>
                  <td class="data">2022.04.01~2022.06.30
                    <br> 화 / 18:30 ~ 20:20
                  </td>
                  <td class="fee">2,000원</td>
                  <td class="reception">2022-08-02</td>
                  <td class="state">
                    <a href="#" class="red" data-bs-target="#apply-class" data-bs-toggle="modal">신청취소</a>
                  </td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div class="paginate pc">
 				<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./myRsvnList?pageIndex="/>
        </div>
	</div>
</main>

