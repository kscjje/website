<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>

<script>
//상세페이지로이동
function fnGoDetail(edcPrgmid) {
//	var _scrollTop = window.scrollY || document.documentElement.scrollTop;
//	$('#scrollVar').val(_scrollTop);

	//location.hash = $('#searchVO').serialize();
	location.href = '<c:url value="../mywriting"/>' + '/' + 1;
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
						<a href="javascript:void(0)" title="이동" class="pageTit-link">내글관리</a>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<div class="sub">
		<%@ include file="/WEB-INF/jsp/web/mypage/myPageInclude.jsp"%>
		<div class="article-bottom">
			<div class="article-lsit list">
				<h3 class="list-title">강사신청 현황</h3>
				<table class="list-table table bor-black">
					<colgroup>
						<col style="width: 5%">
						<col style="width: 10%">
						<col style="width: 10%">
						<col style="width: 60%">
						<col style="width: 15%">
						<col style="width: 15%">
						<col style="width: 10%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="number">번호</th>
							<th scope="col" class="member">강사명</th>
							<th scope="col" class="area">활동가능지역</th>
							<th scope="col" class="title">강의과목</th>
							<th scope="col" class="date">승인여부</th>
							<th scope="col" class="date">승인일</th>
							<th scope="col" class="date">관리</th>
						</tr>
					</thead>
					<tbody>
<c:if test="${fn:length(instrPoolList) == 0}">
						<tr>
							<td colspan="7">등록된 데이터가 없습니다.</td>
						</tr>
</c:if>					
<c:forEach items="${instrPoolList}" var="resultInfo" varStatus="status">
						<tr>
							<td class="number">${status.index+1 }</td>
							<td class="member">${resultInfo.memNm }</td>
							<td class="area">${resultInfo.targetArea }</td>
							<td class="title">
								${resultInfo.lectrField}
							</td>
							<td class="date">${resultInfo.stateNm }</td>
							<td class="date">
							<c:choose>
								<c:when test="${resultInfo.state eq '3001' }">
									<fmt:formatDate value="${resultInfo.moddate }" pattern="yyyy.MM.dd"/>
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
							</td>
							<td class="state">
							<a href="/web/mypage/teacher/modify" class="btn_s2_c0"><p class="gray">상세보기</p></a>
							</td>
						</tr>
</c:forEach>

					</tbody>
				</table>
			</div>
		</div>

		<div class="paginate pc">
			<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./myWritingList?pageIndex="/>
        </div>
        
		<div class="article-bottom">
			<div class="article-lsit list area">
				<h3 class="list-title">묻고 답하기</h3>
				<table class="list-table area-table table bor-black">
					<colgroup>
						<col style="width: 10%">
						<col style="width: 20%">
						<col style="width: 35%">
						<col style="width: 15%">
						<col style="width: 15%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="number">번호</th>
							<th scope="col" class="area">기관명</th>
							<th scope="col" class="title">제목</th>
							<th scope="col" class="date">등록일</th>
							<th scope="col" class="state">답변</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${fn:length(qnaList) == 0}">
						<tr>
							<td colspan="6">등록된 게시글이 없습니다.</td>
						</tr>
</c:if>
<c:forEach items="${qnaList}" var="resultInfo" varStatus="status">
	<c:if test="${resultInfo.nttNo eq '1' }">
						<tr>
							<td class="number"><c:out value="${paginationInfo2.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
							<td class="area"><c:if test="${!empty resultInfo.ctgNm }"><c:out value="${resultInfo.ctgNm }"/> 주민자치센터</c:if></td>
							<td class="title">
								<a href="./qna/detail?nttId=<c:out value="${resultInfo.nttId}"/>">
									<c:if test="${resultInfo.secretAt eq 'Y' }"><img src="/web/resources/images/main/ico_lock.png" alt="아이콘" style="width:20px;margin-bottom:2px;">&nbsp;</c:if>
									<c:out value='${resultInfo.nttSj}'/>
								</a>
							</td>
							<td class="date"><fmt:formatDate value="${resultInfo.frstRegisterPnttm}" pattern="yyyy-MM-dd"/></td>
							<td class="state">
								<c:out value="${!empty resultInfo.commentCo ? '<p class=\"green\">답변완료</p>' : '<p class=\"gray\">신청중</p>'}" escapeXml="false"/>
							</td>
						</tr>
	</c:if>
</c:forEach> 
					</tbody>
				</table>
			</div>
		</div>
		<div class="paginate pc">
			<tags:UserPaging pageInfo="${paginationInfo2}" pageUrl="./myWritingList?pageIndex="/>
        </div>
        
        <div class="article-bottom">
			<div class="article-lsit list area">
				<h3 class="list-title">자유게시판</h3>
				<table class="list-table area-table table bor-black">
					<colgroup>
						<col style="width: 10%">
						<col style="width: 60%">
						<col style="width: 15%">
						<!-- <col style="width: 7.5%"> -->
						<col style="width: 7.5%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="number">번호</th>
							<th scope="col" class="title">제목</th>
							<th scope="col" class="date">등록일</th>
							<!-- <th scope="col" class="file">첨부</th> -->
							<th scope="col" class="state">조회</th>
						</tr>
					</thead>
					<tbody>
<c:if test="${fn:length(freeboardList) == 0}">
						<tr>
							<td colspan="5">등록된 게시글이 없습니다.</td>
						</tr>
</c:if>
<c:forEach items="${freeboardList}" var="resultInfo" varStatus="status">
						<tr>
							<td class="number"><c:out value="${paginationInfo3.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
							<td class="title">
								<a href="./freeboard/detail?nttId=<c:out value="${resultInfo.nttId}"/>">
									<c:if test="${(!empty ctgList) && fn:length(resultInfo.ctgNm)>0}">
										<em class="st<c:out value="${resultInfo.ctgSort}"/>">[<c:out value="${resultInfo.ctgNm}"/>]</em>
									</c:if>
									<c:if test="${resultInfo.sjBoldAt eq 'Y' }"><b></c:if>
									<c:out value='${resultInfo.nttSj}'/>
									<c:if test="${resultInfo.sjBoldAt eq 'Y' }"></b></c:if>
								</a>
							</td>
							<td class="date"><fmt:formatDate value="${resultInfo.frstRegisterPnttm}" pattern="yyyy-MM-dd"/></td>
							<!-- <td class="file">
								<a href="#" download="">
									<img src="/web/resources/images/sub/ico_file.png" alt="다운로드">
								</a>
							</td> -->
							<td class="state"><c:out value="${resultInfo.inqireCo}"/></td>
						</tr>
</c:forEach>
						<!-- <tr>
							<td class="number">1</td>
							<td class="title">
								<a href="#" class="truncate-1">2022 우리쌀 활용 제과제빵(초급반) 교육생 모집</a>
							</td>
							<td class="date">2022.04.28</td>
							<td class="file">
								<a href="#" download="">
									<img src="/web/resources/images/sub/ico_file.png" alt="다운로드">
								</a>
							</td>
							<td class="state">120</td>
						</tr> -->
					</tbody>
				</table>
			</div>
		</div>
		<div class="paginate pc">
			<tags:UserPaging pageInfo="${paginationInfo3}" pageUrl="./myWritingList?pageIndex="/>
        </div>
	</div>
</main>

