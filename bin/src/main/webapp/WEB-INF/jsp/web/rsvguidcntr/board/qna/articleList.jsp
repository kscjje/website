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

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/top.jsp"%>
<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit"><c:out value="${SELECTED_MENU_OBJ.menuNm}"/></h3>

		<div class="top_txt">
			<c:out value="${fn:replace(boardMasterVO.bbsIntrcn , crlf , '<br/>')}" escapeXml="false" />
		</div>

		<!-- 임시 개발용 버튼 (나중에 지울 예정입니다.) -->
		<!--
		<button type="button" id="searchJusoPop"><span>주소 검색</span></button>&nbsp;|&nbsp;
		<button type="button" id="certiChargePop"><span>결제 처리</span></button>&nbsp;|&nbsp;
		<button type="button" id="sendMailPop"><span>메일 전송</span></button>&nbsp;|&nbsp;
		<button type="button" id="sendSmsPop"><span>문자 메시지 전송</span></button>&nbsp;|&nbsp;
 -->
		<!--
			<a onclick="window.open(this.href,'popup','width=700,height=700,scrollbars=no'); return false;" href="https://pgweb.tosspayments.com:18443/pg/wmp/Home2009/demo/xpaydemo/payreq_crossplatform.jsp" class="buttontype07"><span>실행하기</span></a>
		-->

		<div class="search_wrap<c:if test="${!empty ctgList }"> w3</c:if>">
			<form name="articleForm" action="./list" method="get" >
				<fieldset>
					<legend>게시판 검색</legend>
					
					<!-- 
					<span class="total">총 <em><fmt:formatNumber value="${resultCnt+fn:length(noticeList)}" /></em> 건의 게시물</span>
					-->

					<div class="search_box">
					
						<label for="searchCnd">검색 영역 : </label>
						<select id="searchCnd" name="searchCnd" <c:if test="${empty ctgList }">class="ml0"</c:if> title="검색 영역을 선택하세요">
							<option value="9"  <c:if test="${searchVO.searchCnd == '9'}">selected="selected"</c:if> >제목+내용</option>
							<option value="0"  <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> >제목</option>
							<option value="1"  <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if> >내용</option>
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
				<!--               <colgroup>
                                        <col style="width:11%">
                                        <col style="">
                                        <col style="width:11%">
                                        <col style="width:11%">
                                        <col style="width:10%">
                                    </colgroup>-->
				<thead>
					<tr>
						<th class="number">No</th>
						<!-- <th class="cat">카테고리</th> -->
						<th class="title">제목</th>
						<th class="writer">작성자</th>
						<th class="date">등록일</th>
						<th class="date">조회</th>
						<th class="state">답변상태</th>
					</tr>
				</thead>
				<tbody>
				
				<c:if test="${paginationInfo.currentPageNo < 2 }">
					<!-- 공지사항 본문 -->
					<c:forEach items="${noticeList}" var="resultInfo" varStatus="status">
						<tr class="n_top">
							<td class="number"><img src="<%=strContext%>/images/sub/ico_notice.png" alt="중요공지아이콘"></td>
							<td class="cat"><c:out value="${noticeInfo.ctgNm}"/></td>
							<td class="state"></td>
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
								<c:if test="${resultInfo.fileCnt > 0 }"><i><img src="/resources/images/sub/ico_file.png" alt="첨부파일아이콘"></i></c:if>
							</td>
							<td class="writer"><c:out value='${resultInfo.frstRegisterNm}'/></td>
							<td class="date"><fmt:formatDate value="${resultInfo.frstRegisterPnttm}" pattern="yyyy.MM.dd"/></td>
						</tr>
					</c:forEach>
				</c:if>
			
				<c:if test="${fn:length(resultList) == 0}">
					<tr>
						<td colspan="6" class="text-center">등록된 데이타가 없습니다.</td>
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
						<td class="date"><c:out value='${resultInfo.inqireCo}'/></td>
						<td class="state"><c:choose><c:when test="${resultInfo.answerCnt >0 }"><span class="st s2">답변완료</span></c:when><c:otherwise><span class="st s1">&nbsp;&nbsp;&nbsp;접  수&nbsp;&nbsp;&nbsp;</span></c:otherwise></c:choose></td>						
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- //m_table -->
		<div class="paginate">
			<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
		</div>
		<ul class="btn_area">
			<li class="list_btn"><a href="./regist" class="btn_ty_m_c1">글쓰기</a></li>
		</ul>
		<!-- //btn_area -->
		<!-- //paginate -->
	</div>
	<!-- //inner -->
</div>
<!-- //sub_cont -->