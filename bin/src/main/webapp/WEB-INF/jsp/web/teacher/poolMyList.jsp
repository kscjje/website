<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body>

			<div class="sub_visual <c:out value="${SELECTED_MENU_OBJ.relateImageNm}" />">
				<div class="inner">
					<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>
			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<!-- tab_link -->
				<%@ include file="/WEB-INF/templates/web/base/tab_menu.jsp"%>
				<!--// tab_link -->
				<div class="sub_top type01">
					<h3>강사등록 현황</h3>
				</div>
				<!-- // sub_top -->
				<div class="table t_block">
					<table>
						<caption>강사등록 현황 표입니다. -번호, 강좌명, 기간, 수강신청 승인상태, 상태바로가기</caption>
						<colgroup>
							<col style="width:6%">
							<col style="width:20%">
							<col style="width:40%">
							<col style="width:14%">
							<col style="width:20%">
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>강사명</th>
								<th>과목</th>
								<th>승인 여부</th>
								<th>승인날짜</th>
							</tr>
						</thead>
						<tbody>
<c:if test="${empty list }">
							<tr>
								<td colspan="5" style="text-align:center">등록된 데이타가 없습니다.</td>
							</tr>
</c:if>
<c:forEach items="${list}" var="resultInfo" varStatus="status">
							<tr>
								<td class="b_number">${status.index+1 }</td>
								<td><a href="./modify" class="a_link">${resultInfo.memNm }</a></td>
								<td>${resultInfo.lectrField }</td>
								<td class="b_status">${resultInfo.stateNm }</td>
								<td>
									<c:choose>
										<c:when test="${resultInfo.state eq '3001' }">
											<fmt:formatDate value="${resultInfo.moddate }" pattern="yyyy.MM.dd"/>
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</td>
								<td class="m_td"><a href="./modify" class="btn_s2_c0">상세보기</a></td>
							</tr>
</c:forEach>
						</tbody>
					</table>
				</div>
				<!-- // table -->
			</div>
</body>