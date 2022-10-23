<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : mathQuestionList.jsp
  * @Description : 이달의 문제 목록 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.04.14    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.04.14
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/top.jsp"%>
<%@ include file="/WEB-INF/templates/web/base/subMenu.jsp"%>

<div class="sub_cont" id="container">
	<div class="inner">
	
		<h3 class="tit">
			<c:out value="${fn:replace(SELECTED_MENU_OBJ.menuNm, '[[[', '')}" />
		</h3>
		
		<%-- <c:if test="${!empty ctgList }">
			<ul class="tab">
				<li <c:if test="${empty searchVO.searchCtg}">class="on"</c:if>><a href="./list">전체</a></li>
				<c:forEach items="${ctgList}" var="item" varStatus="status">
					<li <c:if test="${searchVO.searchCtg eq item.ctgId }">class="on"</c:if>><a href="./list?searchCtg=<c:out value="${item.ctgId }"/>"><c:out value="${item.ctgNm }"/></a></li>
				</c:forEach>
			</ul>
		</c:if> --%>
			
		<div class="search_wrap">
			<form name="articleForm" action="./list" method="get" >
				<fieldset>
					<legend>게시판 검색</legend>
					<span class="total">총 <em><fmt:formatNumber value="${resultCnt+fn:length(noticeList)}" /></em> 건의 게시물</span>

					<div class="search_box">
						<label for="searchCnd">검색 영역 : </label>
						<select id="searchCnd" name="searchCnd" class="ml0" title="검색 영역을 선택하세요">
							<option  value="0"  <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> >제목</option>
							<option  value="1"  <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if> >내용</option>
						</select>
						<label for="searchWrd">검색어 : </label>
						<input id="searchWrd" name="searchWrd" title="검색어를 입력하세요" type="text" value="<c:out value="${searchVO.searchWrd}"/>" class="input_text" placeholder="검색어를 입력해주세요.">
						<button type="submit" class="button"><span>검색</span></button>
					</div>
				</fieldset>
			</form>
		</div>
		
		<!-- //search_wrap -->
		<div class="m_table">
			<table>
				<caption>${boardMasterVO.bbsNm} 게시판 목록</caption>
				<thead>
					<tr>
						<th class="number">번호</th>
						<th class="title">제목</th>
						<th class="writer">작성자</th>
						<th class="date">등록일</th>
						<th class="hit">조회</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${paginationInfo.currentPageNo < 2 }">
						<!-- 공지사항 본문 -->
						<c:forEach items="${noticeList}" var="resultInfo" varStatus="status">
							<tr class="n_top">
								<td class="number"><img src="<%=strContext%>/images/sub/ico_notice.png" alt="중요공지아이콘"></td>
								<td class="title left"><a href="./detail?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${searchQuery}"/>"><c:out value='${resultInfo.nttSj}'/></a>
									<c:if test="${resultInfo.fileCnt > 0 }"><i><img src="<%=strContext%>/images/sub/ico_file.png" alt="첨부파일아이콘"></i></c:if>
								</td>
								<td class="writer"><c:out value='${resultInfo.frstRegisterNm}'/></td>
								<td class="date"><fmt:formatDate value="${resultInfo.frstRegisterPnttm}" pattern="yyyy.MM.dd"/></td>
								<td class="hit"><c:out value='${resultInfo.inqireCo}'/></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(resultList) == 0}">
							<tr>
								<td colspan="5" class="text-center">등록된 데이타가 없습니다.</td>
							</tr>
					</c:if>
					<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
							<tr>
								<td class="number"><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
								<td class="title left">
									<c:choose>
										<c:when test="${resultInfo.secretAt eq 'Y'}">
												<i class="lock">
													<img src="<%=strContext%>/images/sub/ico_lock.png" alt="잠금아이콘">
												</i>
											<a href="./detail?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${searchQuery}"/>"><c:if test="${resultInfo.sjBoldAt eq 'Y' }"><b></c:if><c:out value='${resultInfo.nttSj}'/><c:if test="${resultInfo.sjBoldAt eq 'Y' }"></b></c:if></a>
										</c:when>
										<c:otherwise>
											<a href="./detail?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${searchQuery}"/>"><c:if test="${resultInfo.sjBoldAt eq 'Y' }"><b></c:if><c:out value='${resultInfo.nttSj}'/><c:if test="${resultInfo.sjBoldAt eq 'Y' }"></b></c:if></a>
										</c:otherwise>
									</c:choose>
									<c:if test="${resultInfo.fileCnt > 0 }"><i><img src="<%=strContext%>/images/sub/ico_file.png" alt="첨부파일아이콘"></i></c:if>
								</td>
								<td class="writer"><c:out value='${resultInfo.frstRegisterNm}'/></td>
								<td class="date"><fmt:formatDate value="${resultInfo.frstRegisterPnttm}" pattern="yyyy.MM.dd"/></td>
								<td class="hit"><fmt:formatNumber value="${resultInfo.inqireCo}" /></td>
							</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- //m_table -->
		<div class="paginate">
			<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
		</div>
		<!-- //paginate -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>