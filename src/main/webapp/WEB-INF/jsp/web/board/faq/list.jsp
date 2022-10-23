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
		<div class="bySector">
			<ul class="bySector-list nav nav-pills" role="tablist">
				<li class="bySector-item nav-item" role="presentation" style="width: 25%;" onclick="javscript:goPageLink('')">
					<button <c:out value='${searchVO.searchCtg eq "" ? "class=\'nav-link active\' aria-selected=\'true\'" : "class=\'nav-link\' aria-selected=\'false\'"}' escapeXml="false"/> data-bs-toggle="pill" data-bs-target="#pills-home" type="button" role="tab" aria-controls="pills-home">전체</button>
				</li>
				<li class="bySector-item nav-item" role="presentation" style="width: 25%;" onclick="javscript:goPageLink('BBSCTG_0000000000050')">
					<button <c:out value='${searchVO.searchCtg eq "BBSCTG_0000000000050" ? "class=\'nav-link active\' aria-selected=\'true\'" : "class=\'nav-link\' aria-selected=\'false\'"}' escapeXml="false"/> data-bs-toggle="pill" data-bs-target="#pills-profile" type="button" role="tab" aria-controls="pills-profile">회원가입 및 탈퇴</button>
				</li>
				<li class="bySector-item nav-item" role="presentation" style="width: 25%;" onclick="javscript:goPageLink('BBSCTG_0000000000051')">
					<button <c:out value='${searchVO.searchCtg eq "BBSCTG_0000000000051" ? "class=\'nav-link active\' aria-selected=\'true\'" : "class=\'nav-link\' aria-selected=\'false\'"}' escapeXml="false"/> data-bs-toggle="pill" data-bs-target="#pills-contact" type="button" role="tab" aria-controls="pills-contact">수강신청</button>
				</li>
				<li class="bySector-item nav-item" role="presentation" style="width: 25%;" onclick="javscript:goPageLink('BBSCTG_0000000000052')">
					<button <c:out value='${searchVO.searchCtg eq "BBSCTG_0000000000052" ? "class=\'nav-link active\' aria-selected=\'true\'" : "class=\'nav-link\' aria-selected=\'false\'"}' escapeXml="false"/> data-bs-toggle="pill" data-bs-target="#pills-contact" type="button" role="tab" aria-controls="pills-contact">취소 및 환불</button>
				</li>
			</ul>
		</div>
		<form name="articleForm" action="./list" method="get" >
			<div class="searchBar">
				<div class="searchBar-inner">
					<div class="searchBar-wrap mt-5 mb-5">
						<div class="searchBar-basicBox">
							<c:set var="searchTitle" value="질문"/>
			 				<c:if test="${searchVO.searchCnd == '1'}">
								<c:set var="searchTitle" value="답변"/>
							</c:if>
			 				<c:if test="${searchVO.searchCnd == '3'}">
								<c:set var="searchTitle" value="질문 + 답변"/>
							</c:if>
							
							<div class="searchBar-item w200">
								<input type="hidden" id="searchCnd" name="searchCnd" value="<c:out value="${searchVO.searchCnd}"/>"/>
								<input type="hidden" id="searchCtg" name="searchCtg" value="<c:out value="${searchVO.searchCtg}"/>"/>
								<button type="button" class="btn"><c:out value="${searchTitle }"/></button>
								<ul class="searchBar-list">
									<li>
										<button type="button" onclick="changeSearchCnd(0)">질문</button>
									</li>
									<li>
										<button type="button" onclick="changeSearchCnd(1)">답변</button>
									</li>
									<li>
										<button type="button" onclick="changeSearchCnd(3)">질문 + 답변</button>
									</li>
								</ul>
							</div>
							<div class="searchBar-group input-group">
								<label for="inp02_03_01" class="visually-hidden"></label>
								<input type="text" name="searchWrd" class="form-control w330" title="<c:out value="${boardMasterVO.bbsNm }"/> 검색" value='<c:out value="${searchVO.searchWrd}"/>' placeholder="검색어를 입력하세요" id="searchWrd" autocomplete="off">
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
		<div class="faq">
			<div class="faq-inner">
				<div class="faq-wrap">
					<ul class="faq-list">
<c:if test="${fn:length(resultList) == 0}">
						<li class="faq-item">
							<button type="button" class="faq-btn btn" style="display:block;">
								<p>
									등록된 데이터가 없습니다.
								</p>
							</button>
						</li>
</c:if>
<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
						<li class="faq-item">
							<button type="button" class="faq-btn btn">
								<i class="q-icon"></i>
								<p>
									<strong><c:out value="${resultInfo.ctgNm }"/></strong> <c:out value="${resultInfo.nttSj}"/>
								</p>
							</button>
							<div class="faq-ans">
								<i class="a-icon"></i>
								<p>
									<c:out value="${resultInfo.nttCn }" escapeXml="false"/>
								</p>
							</div>
						</li>
</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		<div class="paginate">
			<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
		</div>
	</div>
</main>
<script>
/*검색키워드 구분정보 세팅 함수*/
function changeSearchCnd(data){
	$('#searchCnd').val(data);
}
function goPageLink(ctg){
	var form = document.articleForm;
	form.searchWrd.value = "";
	form.searchCnd.value = "";
	form.searchCtg.value=ctg;
	form.submit();
}
</script>
