<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<main class="content" id="content">

	<div class="subVisual">
		<div class="subVisual-inner">
			<figure class="subVisual-figure">
				<img src="/web/resources/images/sub/img_subVisual_02.png" alt="서브 비주얼">
			</figure>
			<h3 class="subVisual-title position-absolute start-50 top-50 translate-middle"><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h3>
        </div>
	</div>
	<div class="pageTit">
		<div class="pageTit-inner">
			<div class="pageTit-wrap">
				<h3 class="pageTit-title"><c:out value="${boardMasterVO.bbsNm }"/></h3>
				<ul class="pageTit-list">
					<li class="pageTit-item">
						<a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
							<img src="/web/resources/images/sub/ico_snb_home.png" alt="홈">
						</a>
              		</li>
					<li class="pageTit-item">
						<a href="javascript:void(0)" title="이동" class="pageTit-link"><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></a>
					</li>
					<li class="pageTit-item">
						<a href="javascript:void(0)" title="이동" class="pageTit-link"><c:out value="${boardMasterVO.bbsNm }"/></a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="sub">
		<form name="articleForm" action="./list" method="get" >

			<div class="searchBar">
				<div class="searchBar-inner">
					<div class="searchBar-wrap">
						<div class="searchBar-basicBox">
<c:if test="${!empty ctgList }">

								<c:set var="ctgTitle" value="기관 선택"/>

								<div class="searchBar-item w200">
									<button type="button" class="btn" id="btnCtgTitle">기관 선택</button>
									<ul class="searchBar-list">
										<input type="hidden" name="searchCtg" id="searchCtg" value="<c:out value="${searchVO.searchCtg}"/>"/>
										<li>
											<button type="button" onclick="changeCategory('')">전체</button>
										</li>
                    
	<c:forEach items="${ctgList}" var="item" varStatus="status">
										<li>
											<button type="button" onclick="changeCategory('<c:out value="${item.ctgId }"/>')"><c:out value="${item.ctgNm }"/></button>
										</li>	
				
										<c:if test="${searchVO.searchCtg eq item.ctgId }">
											<c:set var="ctgTitle" value="${item.ctgNm }"/>
										</c:if>
	</c:forEach>
									</ul>
								</div>
</c:if> 
          
							<c:set var="searchTitle" value="제목"/>
			 				<c:if test="${searchVO.searchCnd == '1'}">
								<c:set var="searchTitle" value="내용"/>
							</c:if>
			 				<c:if test="${searchVO.searchCnd == '3'}">
								<c:set var="searchTitle" value="제목 + 내용"/>
							</c:if>
							
							<div class="searchBar-item w200">
								<input type="hidden" id="searchCnd" name="searchCnd" value="<c:out value="${searchVO.searchCnd}"/>"/>
								<button type="button" class="btn"><c:out value="${searchTitle }"/></button>
								<ul class="searchBar-list">
									<li>
										<button type="button" onclick="changeSearchCnd(0)">제목</button>
									</li>
									<li>
										<button type="button" onclick="changeSearchCnd(1)">내용</button>
									</li>
									<li>
										<button type="button" onclick="changeSearchCnd(3)">제목 + 내용</button>
									</li>
								</ul>
							</div>
							<div class="searchBar-group input-group">
								<label for="inp02_03_01" class="visually-hidden"></label>
								<input type="text" name="searchWrd" class="form-control w330" title="<c:out value="${boardMasterVO.bbsNm }"/> 검색" value='<c:out value="${searchVO.searchWrd}"/>' placeholder="검색어를 입력하세요" id="searchWrd">
							</div>
							<div class="searchBar-group input-group">
								<button type="submit" class="btn searchBar-search" type="button">
									<img src="../../resources/images/layout/icon_search_w.png" alt="아이콘">검색
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>


		<div class="article-bottom">
			<div class="article-lsit list area">
				<table class="list-table area-table table bor-black">
					<colgroup>
						<col style="width: 10%">
						<col style="width: 20%">
						<col style="width: 35%">
						<col style="width: 10%">
						<col style="width: 15%">
						<col style="width: 15%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="number">번호</th>
							<th scope="col" class="area">기관명</th>
							<th scope="col" class="title">제목</th>
							<th scope="col" class="area">작성자</th>
							<th scope="col" class="date">등록일</th>
							<th scope="col" class="state">답변</th>
						</tr>
					</thead>
					<tbody>
<c:if test="${fn:length(resultList) == 0 && fn:length(noticeList) == 0}">
						<tr>
							<td colspan="6">등록된 게시글이 없습니다.</td>
						</tr>
</c:if>
<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
	<c:if test="${resultInfo.nttNo eq '1' }">
						<tr>
							<td class="number"><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
							<td class="area"><c:if test="${!empty resultInfo.ctgNm }"><c:out value="${resultInfo.ctgNm }"/> 주민자치센터</c:if></td>
							<td class="title">
								<a href="./detail?nttId=<c:out value="${resultInfo.nttId}"/>">
									<c:if test="${resultInfo.secretAt eq 'Y' }"><img src="/web/resources/images/main/ico_lock.png" alt="아이콘" style="width:20px;margin-bottom:2px;">&nbsp;</c:if>
									<c:out value='${resultInfo.nttSj}'/>
								</a>
							</td>
							<td class="area">
								<c:out value="${fn:length(resultInfo.frstRegisterNm) > 2 ? fn:substring(resultInfo.frstRegisterNm,0,fn:length(resultInfo.frstRegisterNm)-2)+='**' : fn:substring(resultInfo.frstRegisterNm,0,fn:length(resultInfo.frstRegisterNm)-1)+='*'}"/>
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

		<div class="paginate">
			<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
		</div>
		<div class="badge-btn">
			<a href="./regist" class="green">글쓰기</a>
		</div>
	</div>
</main>      

<script>
/*검색키워드 구분정보 세팅 함수*/
function changeSearchCnd(data){
	$('#searchCnd').val(data);
}
/*기관세팅 함수*/
function changeCategory(data){
	$('#searchCtg').val(data);
}
var selectCtg = '<c:out value="${ctgTitle }"/>';
if(selectCtg == ''){
	selectCtg = '기관 선택';
}
	$('#btnCtgTitle').html(selectCtg);
</script>
