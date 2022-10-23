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
                <a href="javascript:void(0)" title="이동" class="pageTit-link">주민자치센터</a>
              </li>
               -->
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
        <div class="detail">
          <div class="detail-inner">
            <div class="detail-wrap">
              <div class="detail-head">
                <h4 class="detail-title">
				<!-- 
						<c:if test="${result.noticeAt eq 'Y' }"><a class="btn_s2_c1" href="javascript:">공지</a></c:if>
						<c:if test="${(!empty ctgList) && fn:length(result.ctgNm)>0}">
							<em class="st1">[<c:out value="${result.ctgNm}"/>]</em>
						</c:if>
 				-->						
						<c:out value="${result.nttSj}"/>
						    				
				</h4>
                <div class="detail-desc">
                  <div class="desc-left">
                    <p>작성자 : </p>
                    <span><c:out value="${result.frstRegisterNm}"/></span>
                  </div>
                  <div class="desc-right">
                    <span>
                      <img src="../../resources/images/sub/ico_article_05.png" alt="아이콘">2021-09-03
                    </span>
                    <span>
                      <img src="../../resources/images/sub/ico_article_07.png" alt="아이콘">85
                    </span>
                  </div>
                </div>
                <div class="detail-file">
                  <span class="file-tit">첨부파일</span>
                  <ul>
					<% /* 첨부 파일 */ %> <% /* show_image : 본문에 첨부파일 이미지 보여주기 여부 */ %>
					<c:if test="${not empty result.atchFileId}">
						<c:import url="${webDir}/common/upload/includeUploadDetail" >
				       		<c:param name="file_group_id" value="${result.atchFileId}" />
				       		<c:param name="show_type" value="user" />
				       		<c:param name="show_image" value="Y" />
			        	</c:import>
					</c:if>

                  </ul>
                </div>
              </div>
              <div class="detail-con">
					<c:out value="${fn:replace(result.nttCn , crlf , '<br/>')}" escapeXml="false" />
              </div>
            </div>
          </div>
        </div>
        

<c:if test="${nextBoardVo!=null ||  prevBoardVo!=null}">
        <div class="pageMove">
	<c:if test="${prevBoardVo!=null}">

  		  <a href="./detail?nttId=<c:out value="${prevBoardVo.nttId}"/><c:out value="${searchQuery}"/>" class="pageMove-link prev">
            <div>
              <span>이전글</span>
              <p class="truncate-1">
 <!--               
					<c:if test="${(!empty ctgList) && fn:length(prevBoardVo.ctgNm)>0}">
							<em class="st1">[<c:out value="${prevBoardVo.ctgNm}"/>]</em>
					</c:if>
 -->					
					<c:out value="${prevBoardVo.nttSj}"/>              
              </p>
            </div>
            <span class="pageMove-data"><fmt:formatDate value="${prevBoardVo.frstRegisterPnttm}" pattern="yyyy.MM.dd"/></span>
          </a>	
	</c:if>
	<c:if test="${nextBoardVo!=null}">
	
          <a href="./detail?nttId=<c:out value="${nextBoardVo.nttId}"/><c:out value="${searchQuery}"/>" class="pageMove-link next">
            <div>
              <span>다음글</span>
              <p class="truncate-1">
<!-- 
							<c:if test="${(!empty ctgList) && fn:length(nextBoardVo.ctgNm)>0}">
								<em class="st1">[<c:out value="${nextBoardVo.ctgNm}"/>]</em>
							</c:if>
 -->							
							<c:out value="${nextBoardVo.nttSj}"/>

              </p>
            </div>
            <span class="pageMove-data"><fmt:formatDate value="${nextBoardVo.frstRegisterPnttm}" pattern="yyyy.MM.dd"/></span>
          </a>	
	
	</c:if>
				</div>
</c:if>

        <div class="badge-btn">
        	<a href="./list?<c:out value="${searchQuery}"/>" class="black">목록</a>
        </div>
        <!--// sub_content  -->
      </div>
  </main>