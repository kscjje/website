<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : notice/list.jsp
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



123123123
<body>
			<div class="sub_visual">
				<div class="inner">
					<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>
			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<%@ include file="/WEB-INF/templates/web/base/tab_menu.jsp"%>
				<!--// tab_link -->
				<div class="sub_top type01"  id="subTitle">
					<h3><c:out value="${boardMasterVO.bbsNm }"/></h3>
				</div>
			<form name="articleForm" action="./list" method="get" >
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
					<button class="btn_s3_c1" type="submit">검색</button>

					<% /* 2021.12.24 기획자 박예현 : 초기화 버튼 삭제요청
					<a class="btn_s3_c3" href="./list">초기화</a>
					*/ %>
				</div>
			</form>


<% /* 공지사항 본문
<c:choose>
	<c:when test="${fn:length(noticeList) > 0}">
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
								<th scope="col" class="file">파일</th>
							</tr>
						</thead>
						<tbody>

					<c:forEach items="${noticeList}" var="noticeInfo" varStatus="status">
							<tr>
								<td class="number"><a class="btn_s2_c1" href="javascript:">공지</a></td>
								<td class="title left"
									<a href="./detail?nttId=<c:out value="${noticeInfo.nttId}"/><c:out value="${searchQuery}"/>">
									<c:if test="${(!empty ctgList) && fn:length(noticeInfo.ctgNm)>0}">
										<em class="st<c:out value="${noticeInfo.ctgSort}"/>">[<c:out value="${noticeInfo.ctgNm}"/>]</em>
									</c:if>
									<c:if test="${noticeInfo.sjBoldAt eq 'Y' }"><b></c:if>
									<c:out value='${noticeInfo.nttSj}'/>
									<c:if test="${noticeInfo.sjBoldAt eq 'Y' }"></b></c:if>
									</a>
									<c:if test="${noticeInfo.newAt eq 'Y' }">
									<i class="n_mark"><img src="<c:url value="${webDir}"/>/resources/images/common/ico_new.png" alt="새 게시글 아이콘"></i>
									</c:if>
								</td>

								<td class="writer"><c:out value="${noticeInfo.frstRegisterNm}"/></td>
								<td class="date"><c:out value="${noticeInfo.frstRegisterPnttm}"/></td>
								<td class="hit"><c:out value="${noticeInfo.inqireCo}"/></td>
								<td class="file">
								<c:if test="${resultInfo.fileCnt > 0 }">
									<a href="javascript:" class="btn_file"><i><img src="<c:url value="${webDir}"/>/resources/images/common/ico_file.png" alt="첨부파일아이콘"></i></a>
								</c:if>
								</td>
							</tr>
					</c:forEach>
					<c:if test="${fn:length(resultList) == 0}">
							<tr>
								<td colspan="6" class="text-center">등록된 데이타가 없습니다.</td>
							</tr>
					</c:if>
						</tbody>
					</table>
				</div>
	</c:when>
	<c:otherwise>
		<c:if test="${fn:length(resultList) == 0}">
				<!--// search_sel  -->
				<div class="m_table">
					<table>
						<caption><c:out value="${boardMasterVO.bbsNm }"/> 표. - 등록된 데이타가 없음 안내</caption>

						<tbody>
							<tr>
								<td class="text-center">등록된 데이타가 없습니다.</td>
							</tr>
						</tbody>
					</table>
				</div>
		</c:if>
	</c:otherwise>
</c:choose>
*/ %>

	<c:if test="${fn:length(resultList) == 0}">
			<!--// search_sel  -->
			<div class="m_table">
				<table>
					<caption><c:out value="${boardMasterVO.bbsNm }"/> 표. - 등록된 데이타가 없음 안내</caption>

					<tbody>
						<tr>
							<td class="text-center">등록된 데이타가 없습니다.</td>
						</tr>
					</tbody>
				</table>
			</div>
	</c:if>

<c:if test="${fn:length(resultList) > 0}">
				<div class="report_list">
					<ul>
		<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
						<li>
						 	<a href="./detail?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${searchQuery}"/>">
								<div class="img_box">
									<img src="<c:url value="${webDir}"/>/common/file/view?atchFileId=<c:out value="${resultInfo.atchFileId}"/>&fileSn=<c:out value="${resultInfo.atchImg}"/>" alt="첨부파일 이미지"  onError="this.src='<c:url value="${webDir}"/>/resources/images/data/no_img.jpg'">
								</div>
								<div class="txt_area">
									<c:if test="${resultInfo.ctgSort >0 and fn:length(resultInfo.ctgNm)>0}">
										<div class="name">
											<p style="font-weight:normal">[<c:out value="${resultInfo.ctgNm}"/>]</p>
										</div>
									</c:if>
									<p><c:out value='${resultInfo.nttSj}'/></p>
									<span class="date"><fmt:formatDate value="${resultInfo.frstRegisterPnttm}" pattern="yyyy-MM-dd"/></span>
								</div>
							</a>
						</li>
		</c:forEach>
					</ul>
				</div>
				<!--// web_box  -->
</c:if>

					<div class="paginate">
						<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
					</div>
			</div>
			<!--// sub_content  -->
</body>