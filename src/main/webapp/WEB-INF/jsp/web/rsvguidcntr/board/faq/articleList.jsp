<%
 /**
  * @Class Name : articleList.jsp
  * @Description : 게시판 목록 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.06    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.08.06
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/top.jsp"%>
<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit">${SELECTED_MENU_OBJ.menuNm }</h3>
<c:if test="${!empty ctgList }">
				<ul class="tab">
					<li <c:if test="${empty searchVO.searchCtg}">class="on"</c:if>><a href="./list">전체</a></li>
<c:forEach items="${ctgList}" var="item" varStatus="status">
					<li <c:if test="${searchVO.searchCtg eq item.ctgId }">class="on"</c:if>><a href="./list?searchCtg=<c:out value="${item.ctgId }"/>"><c:out value="${item.ctgNm }"/></a></li>
</c:forEach>
				</ul>
</c:if>
		<!-- tab -->
		<div class="search_wrap">
<form name="articleForm" action="./list" method="get" >
				<fieldset>
					<legend>게시판 검색</legend>
					<span class="total">총 <em><fmt:formatNumber value="${resultCnt}" /></em> 건의 게시물</span>
					<div class="search_box">
						<label for="searchCnd">검색 영역 : </label>
						<select id="searchCnd" class="ml0" name="searchCnd" title="검색 영역을 선택하세요">
							<option  value="3"  <c:if test="${searchVO.searchCnd == '3'}">selected="selected"</c:if> >제목+내용</option>
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
		<div class="quest">
	<!-- fold -->
	<ul class="list fold">
		<!-- 활성화 li class: current 추가 -->
<c:if test="${fn:length(resultList) == 0}">
			<li style="padding-top:50px;text-align:center">등록된 데이타가 없습니다.</li>
</c:if>
<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
		<li>
			<div class="fold_dep">
				<div class="fold_tit">
					<div class="hidden">질문</div><a href="#"><c:if test="${resultInfo.sjBoldAt eq 'Y' }"><b></c:if><c:out value='${resultInfo.nttSj}'/><c:if test="${resultInfo.sjBoldAt eq 'Y' }"></b></c:if></a>
				</div>
				<div class="fold_cont">
					<div class="hidden">답변</div>
					<p><c:out value="${resultInfo.nttCn}" escapeXml="false" /></p>
				</div>
			</div>
		</li>
</c:forEach>
	</ul>
	<!-- //fold -->
</div>


		<div class="paginate">
			<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
		</div>
		<!-- //paginate -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->