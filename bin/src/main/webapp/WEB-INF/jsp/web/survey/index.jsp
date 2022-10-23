<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : index.jsp
  * @Description : 설문 JSP
  * @Modification Information
  * @
  * @  수정일           수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2022.01.06  이윤호                 최초 생성
  *
  *  @author 이윤호
  *  @since 2022.01.06
  *  @version 1.0
  *  @see
  *
  */
%>
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
					<h3>제목 </h3>
				</div>
			<form:form commandName="searchVO" name="searchVO" method="get">
				<div class="search_sel s_column">
					<div class="select s1">
              			<select title="검색"  class="form-control">
							<option value="0" selected="selected" >제목</option><!-- 글 제목  -->
						</select>
					</div>
					<div class="inputbox">
						<label for="searchKeyword" class="hidden"></label>
						<input name="searchKeyword" id="searchKeyword" type="text"  size="35" title="검색어" value='${searchVO.searchKeyword}'  maxlength="155" >
					</div>
					<button class="btn_s3_c1" type="submit">검색</button>
				</div>
			</form:form>

				<!--// search_sel  -->
				<div class="m_table">
					<table>
						<caption> 표. - 번호,등록일, 설문명, 등록기관명, 설문종료일, 참여자수 , 설문참여 </caption>
						<thead>
							<tr>
								<th scope="col" class="number">번호</th>
								<th scope="col" class="">등록일</th>
								<th scope="col" class="title">설문명</th>
								<th scope="col" class="title">등록기관명</th>
								<th scope="col" class="date">설문종료일</th>
								<th scope="col" class="hit">참여자수</th>
								<th scope="col" class="file">설문참여</th>
							</tr>
						</thead>
						<tbody>

<c:if test="${fn:length(surveyList) == 0}">
							<tr>
								<td colspan="7" class="text-center">등록된 데이타가 없습니다.</td>
							</tr>
</c:if>
<c:forEach items="${surveyList}" var="item" varStatus="status">
							<tr>
								<td class="text-center">${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}</td>
								<td class="text-center"><c:out value="${fn:substring(item.regdate,0,10)}"/></td>
								<td class="text-center"><c:out value="${item.qestnarName}"/></td>
								<td class="text-center"><c:out value="${item.orgNm}"/></td>
					<c:choose>
					 	<c:when test="${item.qestnarOpertype eq '1001'}">
					 			<td class="text-center">상시운영</td>
					 	</c:when>
					 	<c:otherwise>
					 			<td class="text-center">
							<c:if test="${item.qestnarOperedate!=null && fn:length(item.qestnarOperedate)==8}">
									${fn:substring(item.qestnarOperedate,0,4)}-${fn:substring(item.qestnarOperedate,4,6)}-${fn:substring(item.qestnarOperedate,6,8)}
							</c:if>					 			
					 			</td>
					 	</c:otherwise>
					 </c:choose>								
								<td class="text-center"><c:out value="${item.submitCnt}"/></td>
								<td class="text-center">
						
							<c:if test="${item.activeYn eq 'Y'}">
								<a href="./form?qestnarId=${item.qestnarId }&qestnarStdno=${item.qestnarStdno}" class="btn_s2_c1">참여</a>
							</c:if>
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
			<!--// sub_content  -->
</body>