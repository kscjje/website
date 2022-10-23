<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>
		<div class="sub_visual mypage">
				<div class="inner">
					<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>
			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<div class="sub_top type02">
					<a href="javascript:history.back()">뒤로가기 버튼</a>
					<h3>내정보 관리</h3>
				</div>
				<!-- 22.01.07 수정  -->
				<div class="table t_table ">
					<div class="title_box">
						<p class="t_title">01. 개인 정보</p>
					</div>
					<table>
						<caption>내정보관리 표입니다. -ID , 이름, 생년월일/성별, 휴대폰번호, 사무실/집 전화, 주소, 이메일</caption>
						<colgroup>
							<col style="width:14.5%">
							<col style="width:85.5%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><span>ID</span></th>
								<td>
									<c:out value="${memberVO.id }" />
								</td>
							</tr>
							<tr>
								<th scope="row"><span>이름</span></th>
								<td>
									<c:out value="${memberVO.memNm }" />
								</td>
							</tr>
							<tr>
								<th scope="row"><span>성별</span></th>
								<td>
									<c:choose>
										<c:when test="${memberVO.gender eq '1'}">남자</c:when>
										<c:when test="${memberVO.gender eq '2'}">여자</c:when>
										<c:when test="${memberVO.gender eq '0'}">미기재</c:when>
										<c:when test="${memberVO.gender eq '3'}">성별미상</c:when>
										<c:otherwise>기타</c:otherwise>
									</c:choose>
								</td>
							</tr>


							<tr>
								<th scope="row"><span>생년월일</span></th>
								<td>
									<hisco:DateUtil datestr="${memberVO.birthDate}" format="yyyy-MM-dd"/>
								</td>
							</tr>
							<tr>
								<th scope="row"><span>휴대폰번호</span></th>

								<td><hisco:HpPrint hp="${memberVO.hp}"/></td>
							</tr>

							<tr>
								<th scope="row"><span>이메일</span></th>
								<td>
									<c:out value="${memberVO.email}"/>
								</td>
							</tr>

							<tr>
								<th scope="row">주소</th>
								<td >
									<c:out value="${memberVO.addr1}"/>
									<br/><c:out value="${memberVO.addr2}"/>
								</td>
							</tr>

						</tbody>
					</table>
				</div>
				<!--//table  -->
				<div class="btn_area">
					<a href="https://www.nowon.kr/www/mypage/BD_protectInfo.do" class="btn_s1_c1">회원정보 수정</a>
			</div>
</div>