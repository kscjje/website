<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="egovframework.com.cmm.service.EgovProperties" %>
<%@ page import="com.hisco.cmm.util.*" %>
<%@ page import ="com.hisco.user.nice.web.NamefactController"%>

<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>


<main class="content" id="content">

	<div class="subVisual">
		<div class="subVisual-inner">
			<figure class="subVisual-figure">
				<img src="../../resources/images/sub/img_subVisual_04.png" alt="서브 비주얼">
			</figure>
			<h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">교육신청</h3>
		</div>
	</div>
	<div class="pageTit">
		<div class="pageTit-inner">
			<div class="pageTit-wrap align-center">
				<h3 class="pageTit-title">사물함 정보</h3>
			</div>
		</div>
	</div>
	<div class="sub">
		<div class="inform">
			<div class="inform-inner">
				<div class="inform-wrap">
					<figure class="inform-figure">
						<img src="../../resources/images/sub/ico_basice_02.png" alt="아이콘">
					</figure>
					<div class="inform-body">
						<h3 class="inform-tit">
							<span>이용중인</span> 사물함<span> 정보입니다.</span>
						</h3>
					</div>
				</div>
			</div>
		</div>
		<div class="myTable">
			<div class="myTable-inner">
				<div class="myTable-wrap">
					<h3 class="myTable-title">사물함 신청정보</h3>
					<table>
						<colgroup>
							<col width="10%">
							<col width="34%">
							<col width="10%">
							<col width="40%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">이용상태</th>
								<td>임대중</td>
								<th scope="row">센터</th>
								<td>별내동주민자치센터</td>
							</tr>
							<tr>
								<th scope="row">사물함위치</th>
								<td>사무실옆</td>
								<th scope="row">사물함번호</th>
								<td>1065</td>
							</tr>
							<tr>
								<th scope="row">임대시작일</th>
								<td>2022.04.19</td>
								<th scope="row">임대종료일</th>
								<td>2022.04.19</td>
							</tr>
							<tr>
								<th scope="row">임대기간</th>
								<td>1개월</td>
								<th scope="row">임대료</th>
								<td>2,000원</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="myTable">
			<div class="myTable-inner">
				<div class="myTable-wrap">
					<h3 class="myTable-title">신청자정보</h3>
					<table>
						<colgroup>
							<col width="10%">
							<col width="34%">
							<col width="10%">
							<col width="40%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">이름</th>
								<td>김별내</td>
								<th scope="row">생년월일</th>
								<td>1980.05.18</td>
							</tr>
							<tr>
								<th scope="row">휴대폰번호</th>
								<td colspan="3">010-4739-1588</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
        <div class="myTable">
			<div class="myTable-inner">
				<div class="myTable-wrap multiple">
				<h3 class="myTable-title">결제정보</h3>
					<table class="align-center">
						<thead>
							<tr>
								<th scope="row">결제상태</th>
								<th scope="row">승인일시</th>
								<th scope="row">승인번호</th>
								<th scope="row">지불수단</th>
								<th scope="row">구분</th>
								<th scope="row">결제금액(원)</th>
								<th scope="row">영수증</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>결제상태</td>
								<td>2022.05.31<br>20:39:03</td>
								<td>00870668</td>
								<td>국민</td>
								<td>카드</td>
								<td>50,000</td>
								<td><button type="button" class="badge black">영수증출력</button></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="badge-btn">
			<a href="http://183.111.144.101/nyjcenter/html/07_sub/sub08_08_01.html#none" class="black">목록</a>
			<button type="button" class="gray" data-bs-target="#cancle-payment" data-bs-toggle="modal">결제취소</button>
		</div>
	</div>
</main>
