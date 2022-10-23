<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : qna/list.jsp
  * @Description : 목록 JSP
  * @Modification Information
  * @
  * @  수정일               수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.04.29    전영석                 최초 생성
  *
  *  @author 전영석
  *  @since 2021.04.29
  *  @version 1.0
  *  @see
  *
  */
%>
<body>

			<div class="sub_visual">
				<div class="inner">
					<h2>알림마당</h2>
					<div class="btn_util">
						<button class="share">
							<span class="hidden">공유하기</span>
						</button>
						<button class="print">
							<span class="hidden">인쇄</span></button>
					</div>
				</div>
			</div>
			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<% /* 나중에 DB메뉴에서 불러 오는 값으로 변경 예정 */ %>
				<ul class="tab_link">
					<li><a href="<%=com.hisco.cmm.util.Config.USER_ROOT%>/board/notice/list" class="on">공지사항</a></li>
					<li><a href="<%=com.hisco.cmm.util.Config.USER_ROOT%>/board/notice/list">기관소식</a></li>
					<li><a href="<%=com.hisco.cmm.util.Config.USER_ROOT%>/board/notice/list">언론보도</a></li>
					<li><a href="<%=com.hisco.cmm.util.Config.USER_ROOT%>/board/notice/list">대학교 평생교육</a></li>
					<li><a href="<%=com.hisco.cmm.util.Config.USER_ROOT%>/board/notice/list">온라인강좌 안내</a></li>
					<li><a href="<%=com.hisco.cmm.util.Config.USER_ROOT%>/board/notice/list">자료실</a></li>
				</ul>
				<!--// tab_link -->
				<div class="sub_top type01">
					<h3><c:out value="${boardMasterVO.bbsNm }"/></h3>
				</div>
			<form name="articleForm" action="./list#subTitle" method="get" >
				<div class="search_sel s_column">
	<c:if test="${!empty ctgList }">
					<div class="select s1">
						<select name="searchCtg" title="카테고리 선택"  class="form-control">
							<option value=""  >카테고리</option>
	<c:forEach items="${ctgList}" var="item" varStatus="status">
							<option value="<c:out value="${item.ctgId }"/>" <c:if test="${searchVO.searchCtg eq item.ctgId }">selected</c:if> ><c:out value="${item.ctgNm }"/></option>
	</c:forEach>
						</select>
					</div>
	</c:if>

					<div class="select s1">
              			<select name="searchCnd" title="검색영역"  class="form-control">
							<option value="0"  <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> >제목</option><!-- 글 제목  -->
							<option value="1"  <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if> >내용</option><!-- 글 내용 -->
							<option value="2"  <c:if test="${searchVO.searchCnd == '2'}">selected="selected"</c:if> >등록자</option><!-- 작성자 -->
						</select>
					</div>
					<div class="inputbox">
						<label for="searchWrd" class="hidden"></label>
						<input type="text" name="searchWrd" title="<c:out value="${boardMasterVO.bbsNm }"/> 검색" value='<c:out value="${searchVO.searchWrd}"/>' placeholder="" id="searchWrd">
					</div>
					<button class="btn_s3_c2" type="submit">검색</button>
					<a class="btn_s3_c3" href="./list#subTitle">초기화</a>
				</div>
			</form>
				<!--// search_sel  -->
				<div class="m_table">
					<table>
						<caption><c:out value="${boardMasterVO.bbsNm }"/> 표. - 번호,제목,작성자,작성일,조회,파일</caption>
						<thead>
							<tr>
								<th scope="col" class="number">번호</th>
								<th scope="col" class="title">제목</th>
								<th scope="col" class="writer">작성자</th>
								<th scope="col" class="date">작성일</th>
								<th scope="col" class="hit">조회</th>
								<th scope="col" class="file">답변상태</th>
							</tr>
						</thead>
						<tbody>

					<c:if test="${paginationInfo.currentPageNo < 2 }">

						<c:forEach items="${noticeList}" var="resultInfo" varStatus="status">
							<tr class="n_top">
								<td class="number"><a class="btn_s3_c1" href="javascript:">공지</a></td>
								<td class="title left">
									<a href="./detail?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${searchQuery}"/>">
									<c:if test="${resultInfo.secretAt eq 'Y'}">
											<i class="lock"><img src="<%=com.hisco.cmm.util.Config.USER_ROOT%>/resources/images/sub/ico_lock.png" alt="잠금아이콘"></i>
									</c:if>
									<c:if test="${(!empty ctgList) && fn:length(resultInfo.ctgNm)>0}">
										<em class="st1">[<c:out value="${resultInfo.ctgNm}"/>]</em>
									</c:if>
									<c:if test="${resultInfo.sjBoldAt eq 'Y' }"><b></c:if>
									<c:out value='${resultInfo.nttSj}'/>
									<c:if test="${resultInfo.sjBoldAt eq 'Y' }"></b></c:if>
									</a>
									<c:if test="${resultInfo.fileCnt > 0 }">
										<a href="javascript:" class="btn_file"><i><img src="<%=com.hisco.cmm.util.Config.USER_ROOT%>/resources/images/common/ico_file.png" alt="첨부파일아이콘"></i></a>
									</c:if>
									<c:if test="${resultInfo.newAt eq 'Y' }">
									<i class="n_mark"><img src="<%=com.hisco.cmm.util.Config.USER_ROOT%>/resources/images/common/ico_new.png" alt="새 게시글 아이콘"></i>
									</c:if>
								</td>
								<td class="writer"><c:out value='${resultInfo.frstRegisterNm}'/></td>
								<td class="date"><fmt:formatDate value="${resultInfo.frstRegisterPnttm}" pattern="yyyy-MM-dd"/></td>
								<td class="hit"><c:out value="${resultInfo.inqireCo}"/></td>
								<td class="state"></td>
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
									<a href="./detail?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${searchQuery}"/>">
									<c:if test="${resultInfo.secretAt eq 'Y'}">
											<i class="lock"><img src="<%=com.hisco.cmm.util.Config.USER_ROOT%>/resources/images/sub/ico_lock.png" alt="잠금아이콘"></i>
									</c:if>
									<c:if test="${(!empty ctgList) && fn:length(resultInfo.ctgNm)>0}">
										<em class="st1">[<c:out value="${resultInfo.ctgNm}"/>]</em>
									</c:if>
									<c:if test="${resultInfo.sjBoldAt eq 'Y' }"><b></c:if>
									<c:out value='${resultInfo.nttSj}'/>
									<c:if test="${resultInfo.sjBoldAt eq 'Y' }"></b></c:if>
									</a>
									<c:if test="${resultInfo.fileCnt > 0 }">
										<a href="javascript:" class="btn_file"><i><img src="<%=com.hisco.cmm.util.Config.USER_ROOT%>/resources/images/common/ico_file.png" alt="첨부파일아이콘"></i></a>
									</c:if>
									<c:if test="${resultInfo.newAt eq 'Y' }">
									<i class="n_mark"><img src="<%=com.hisco.cmm.util.Config.USER_ROOT%>/resources/images/common/ico_new.png" alt="새 게시글 아이콘"></i>
									</c:if>
								</td>
								<td class="writer"><c:out value="${resultInfo.frstRegisterNm}"/></td>
								<td class="date"><c:out value="${resultInfo.frstRegisterPnttm}"/></td>
								<td class="hit"><c:out value="${resultInfo.inqireCo}"/></td>
								<td class="state">
									<c:choose>
										<c:when test="${resultInfo.answerCnt >0 }"><span class="st s2">답변완료</span></c:when>
										<c:otherwise><span class="st s1">&nbsp;&nbsp;&nbsp;접  수&nbsp;&nbsp;&nbsp;</span></c:otherwise></c:choose>
								</td>
							</tr>
				</c:forEach>
						</tbody>
					</table>
				</div>
					<div class="paginate">
						<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
					</div>
			</div>
</body>
