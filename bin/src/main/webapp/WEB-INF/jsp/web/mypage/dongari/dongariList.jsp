<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<script>
		$(document).ready(function () {
			$(".search-order").on( "change", function() {
				$('#articleForm').find('input[name=sortOrdr]').val($(this).val());
				$('#articleForm').submit();
			});
		});

		function fn_select_ctg(ctgId){
			$('#articleForm').find('input[name=searchCtg]').val(ctgId);
			$('#articleForm').submit();
		}
	</script>
</head>
<body>
	<div class="sub_visual">
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
			<h3>
			<c:choose>
				<c:when test="${fn:length(SELECTED_MENU_OBJ.menuNm)>0}">
					<c:out value="${SELECTED_MENU_OBJ.menuNm}" />
				</c:when>
				<c:otherwise>
					마이페이지 > 학습동아리 개설현황
				</c:otherwise>
			</c:choose>
			</h3>
		</div>

		<form name="articleForm" id="articleForm" action="./list" method="get" >
			<input type="hidden" name="searchCtg" value="<c:out value="${searchVO.searchCtg }"/>"/>
			<input type="hidden" name="sortOrdr" value="<c:out value="${searchVO.sortOrdr }"/>"/>
		</form>


		<!-- // sub_top -->
		<div class="table t_block">
			<table>
				<caption>학습동아리 개설현황 표입니다. -번호, 강좌명, 기간, 수강신청 승인상태, 상태바로가기</caption>
				<colgroup>
					<col style="width:5.5%">
					<col style="width:21.5%">
					<col style="width:21.5%">
					<col style="width:17%">
					<col style="width:34.5%">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>강좌명</th>
						<th>지역/시간</th>
						<th>승인 상태</th>
						<th>상태 바로가기</th>
					</tr>
				</thead>
				<tbody>
<c:if test="${fn:length(resultList) < 1}">
					<tr>
						<td colspan="5" class="text-center">학습동아리 신청 내역이 없습니다.</td>
					</tr>
</c:if>
<c:if test="${fn:length(resultList) > 0}">
	<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
					<tr>
						<td class="b_number"><c:out value="${status.index + 1}"/></td>
						<td class="b_name"><a href="../../orgintro/dongari/detail?nttId=<c:out value="${resultInfo.nttId}"/>" class="a_link"><c:out value='${resultInfo.nttSj}'/></a></td>
						<td class="b_date">
							<c:if test="${!empty areaList}">
								<c:forEach items="${areaList}" var="item" varStatus="status">
									<c:if test="${item.areaCd eq resultInfo.item6}">
										<c:out value="${item.areaName }"/>
									</c:if>
								</c:forEach>
							</c:if>
							/
							<c:out value='${resultInfo.item8}'/>
						</td>
						<td class="b_status">
			<c:choose>
				<c:when test="${resultInfo.ntceStat eq '1'}">
							승인대기
				</c:when>
				<c:when test="${resultInfo.ntceStat eq '2'}">
							승인
				</c:when>
				<c:when test="${resultInfo.ntceStat eq '3'}">
							반려
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
						</td>
						<td class="b_progress mt54">
			<c:choose>
				<c:when test="${resultInfo.ntceStat eq '1' or resultInfo.ntceStat eq '3'}">
							<a class="btn_s2_c2" href="./modify?nttId=<c:out value="${resultInfo.nttId}"/>" >개설신청 내역/수정</a>
				</c:when>
				<c:when test="${resultInfo.ntceStat eq '2'}">
							<a class="btn_s2_c1" href="<c:url value="/web"/>/orgintro/dongari/detail?nttId=<c:out value="${resultInfo.nttId}"/>" >개설신청 내역</a>
				</c:when>
			</c:choose>
						</td>
					</tr>
	</c:forEach>
</c:if>
				</tbody>
			</table>
		</div>
		<!-- // table -->

		<div class="paginate">
			<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
		</div>
	</div>
</body>