<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <main class="content" id="content">

      <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="../../resources/images/sub/img_subVisual_03.png" alt="서브 비주얼">
          </figure>
          <h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">교육신청</h3>
        </div>
      </div>

      <div class="pageTit">
        <div class="pageTit-inner">
          <div class="pageTit-wrap">
            <h3 class="pageTit-title">온라인 동영상 강좌</h3>
            <ul class="pageTit-list">
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
                  <img src="../../resources/images/sub/ico_snb_home.png" alt="홈">
                </a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">교육신청</a>
              </li>
              <li class="pageTit-item">
                <a href="javascript:void(0)" title="이동" class="pageTit-link">온라인 동영상 강좌</a>
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
        
        <div class="search-result">
          <div class="search-result-inner mt-3 mb-3">
            <div class="search-result-title">
              <img src="../../resources/images/sub/ico_search-result.png" alt="아이콘">
              <div class="search-result-txt"> 검색 결과 <span>총 ${paginationInfo.totalRecordCount}개</span>의
                <br class="mobile">결과가 조회되었습니다.
              </div>
            </div>
          </div>
        </div>
        <div class="report pr">
          <div class="report-inner">
            <div class="report-wrap">
<c:if test="${fn:length(resultList) == 0 && fn:length(noticeList) == 0}">
				<div style="width:100%;text-align:center;padding:50px;">
				등록된 온라인 동영상 강좌가 없습니다.
				</div>
</c:if>            
            
<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
              <ul class="report-list">
	<c:if test="${resultInfo.nttNo eq '1' }">
                <li class="report-item">
                  <a href="./detail?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${searchQuery}"/>" class="report-link">
                    <figure class="report-figure">
                      <img src="../../resources/images/sub/img_pr_01.png" alt="이미지">
                    </figure>
                    <div class="report-con">
                      <h3 class="report-title truncate-2"><c:out value='${resultInfo.nttSj}'/></h3>
                      <div class="report-txt">
                        <p>
                          <img src="../../resources/images/sub/ico_article_05.png" alt="등록일자아이콘">
                          	등록일 :
                          <span><fmt:formatDate value="${resultInfo.frstRegisterPnttm}" pattern="yyyy.MM.dd"/></span>
                        </p>
                        <p>
                          <img src="../../resources/images/sub/ico_article_07.png" alt="조회아이콘">
                          <span><c:out value='${resultInfo.inqireCo}'/></span>
                        </p>
                      </div>
                    </div>
                  </a>
                </li>
	</c:if>              
              </ul>

</c:forEach>            
            </div>
          </div>
        </div>


		<div class="paginate">
			<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
		</div>

      </div>
    </main>
