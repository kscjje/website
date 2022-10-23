<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<main class="content" id="content">
	  <div class="subVisual">
        <div class="subVisual-inner">
          <figure class="subVisual-figure">
            <img src="../../resources/images/sub/img_subVisual_01.png" alt="서브 비주얼">
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
		                  <img src="../../resources/images/sub/ico_snb_home.png" alt="홈">
		                </a>
		              </li>
		              <!-- 
		              <li class="pageTit-item">
		                <a href="javascript:void(0)" title="주민자치센터" class="pageTit-link">주민자치센터</a>
		              </li>
		               -->
		              <li class="pageTit-item">
		                <a href="javascript:void(0)" title="<c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" />" class="pageTit-link"><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></a>
		              </li>
		              <li class="pageTit-item">
		                <a href="javascript:void(0)" title="<c:out value="${boardMasterVO.bbsNm }"/>" class="pageTit-link"><c:out value="${boardMasterVO.bbsNm }"/></a>
		              </li>
		            </ul>
		          </div>
		        </div>
		      </div>      
      
      <div class="sub">
        <div class="report pr">
          <div class="report-inner">
              
              
<c:if test="${fn:length(resultList) == 0}">
		<div class="report-wrap" style="text-align:center;">
			<span>등록된 정보가 없습니다.</span>
		</div>
</c:if>              
              
<c:if test="${fn:length(resultList) > 0}">
            <div class="report-wrap">

              <ul class="report-list">
		<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
                <li class="report-item">
                  <a href="./detail?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${searchQuery}"/>"  class="report-link" title="<c:out value='${resultInfo.nttSj}'/>">
                    <figure class="report-figure">
                      <img src="<c:url value="${webDir}"/>/common/file/view?atchFileId=<c:out value="${resultInfo.atchFileId}"/>&fileSn=<c:out value="${resultInfo.atchImg}"/>" alt="<c:out value='${resultInfo.nttSj}'/>"/>
                    <!-- 
                      <img src="<c:url value="${webDir}"/>/common/file/view?atchFileId=<c:out value="${resultInfo.atchFileId}"/>&fileSn=<c:out value="${resultInfo.atchImg}"/>" alt="<c:out value='${resultInfo.nttSj}'/>"  onError="this.src='<c:url value="${webDir}"/>/resources/images/data/no_img.jpg'">
 					-->                      
                    </figure>
                    <div class="report-con">
                      <h3 class="report-title truncate-2"><c:out value='${resultInfo.nttSj}'/></h3>
                      <div class="report-txt">
                        <p>
                          <img src="../../resources/images/sub/ico_article_05.png" alt="일정아이콘">
                          	일정 : <span><fmt:formatDate value="${resultInfo.frstRegisterPnttm}" pattern="yyyy.MM.dd"/></span>
                        </p>
                        <p>
                          <img src="../../resources/images/sub/ico_article_07.png" alt="조회수아이콘">
                          <span><c:out value="${resultInfo.inqireCo}"/></span>
                        </p>
                      </div>
                    </div>
                  </a>
                </li>
			</c:forEach>
              </ul>
            </div>
</c:if>

          </div>
        </div>


        <div class="paginate pc">
			<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
        </div>
      </div>
</main>      