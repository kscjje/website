<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%
	WebApplicationContext wapContext = org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	Object dynamicConfig = wapContext.getBean("dynamicConfig");
	String adminRoot = dynamicConfig != null ? ((com.hisco.cmm.config.DynamicConfig)dynamicConfig).getAdminRoot() : com.hisco.cmm.util.Config.ADMIN_ROOT;
%>
<c:set var="ADMIN_ROOT" value='<%=request.getContextPath() + adminRoot + "/"%>'/>
<c:set var="CONTEXT_PATH" value='<%=request.getContextPath() %>'/>
<%
 /**
  * @Class Name : masterList.jsp
  * @Description : 게시판 마스터 목록 화면
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.21    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.21
  *  @version 1.0
  *  @see
  *
  */
%>


<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>게시판 관리</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div><!-- /.container-fluid -->
	</section>

	<!-- Main content -->
	<section class="content">
	      <div class="container-fluid">
	       	<div class="card">
				<form name="BBSMasterForm" action="./masterList" method="get" >
			 	 	 <div class="card-body">
			 	 	 	<div class="row">
			              	<div class="col-4">
			              			<select name="searchCnd" title="검색영역"  class="form-control">
										<option value="0"  <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> >게시판명</option><!-- 게시판명 -->
										<option value="1"  <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if> >게시판 소개</option><!-- 게시판 소개내용 -->
									</select>
							</div>
							<div class="col-4">
									<input class="form-control" name="searchWrd" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchWrd}"/>'  maxlength="155" >
							</div>
							<div class="col-4">
								<button type="submit" class="btn btn-secondary">조회</button>
								<c:choose>
									<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
										<a href="./masterRegist" class="btn btn-info" title="등록버튼">등록</a><!-- 등록 -->
									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn-info disabled">등록</button>
									</c:otherwise>
								</c:choose>

			              	</div>

	 					</div>
	 				</div>
	 			</form>
	 	   </div>
	       <div class="row">
	          <div class="col-12">
	            <div class="card card-primary card-outline">
	              <!-- /.card-header -->

	              <div class="card-body table-responsive p-0">
		               <table class="table table-hover text-nowrap">
		               	<colgroup>
							<col style="width: 8%;">
							<col style="width: 13%;">
							<col style="width: *">
							<col style="width: 10%;">
							<col style="width: 13%;">
							<col style="width: 10%;">
							<col style="width: 10%;">
							<col style="width: 13%;">
							<col style="width: 10%;">
						</colgroup>
		                  <thead>
		                    <tr>
		                      	<th>번호</th><!-- 번호 -->
		                      	<th>게시판 ID</th>
								<th>게시판명</th><!-- 게시판명 -->
								<th>작성자명</th><!-- 작성자명 -->
								<th>작성일시</th><!-- 작성시각 -->
								<th>사용여부</th><!-- 사용여부 -->
								<th>게시글 수</th>
								<th>마지막 등록일</th>
								<th>관리</th>
		                    </tr>
		                  </thead>
		                  <tbody>
					<c:if test="${fn:length(resultList) == 0}">
						<tr>
							<td colspan="9">데이타가 없습니다</td>
						</tr>
					</c:if>
						<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
		                    <tr>
		                      	<td><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
								<td><c:out value='${resultInfo.bbsId}'/></td>
								<td class="left"><a href="./masterDetail?bbsId=<c:out value="${resultInfo.bbsId}"/><c:out value="${commandMap.query}"/>" ><c:out value='${fn:substring(resultInfo.bbsNm, 0, 40)}'/></a></td>
								<td><c:out value='${resultInfo.frstRegisterId}'/></td>
								<td><fmt:formatDate value="${resultInfo.frstRegisterPnttm}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td><c:out value='${resultInfo.useAt}'/></td>
								<td><c:out value='${resultInfo.articleCnt}'/></td>
								<td><fmt:formatDate value="${resultInfo.articleDate}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td>
						<c:choose>
							<c:when test="${resultInfo.bbsId eq 'teacherpr' }">
								<a class="btn bg-info btn-xs" href="<c:out value="${ADMIN_ROOT }"/>teacherpr/list">강사PR 관리</a>
								<a class="btn bg-success btn-xs" href="<c:out value="${CONTEXT_PATH }"/><%=com.hisco.cmm.util.Config.USER_ROOT%>/teacher/pr/list" target="_blank">FRONT</a>
							</c:when>
							<c:when test="${resultInfo.bbsId eq 'dongari' }">
								<a class="btn bg-info btn-xs" href="<c:out value="${ADMIN_ROOT }"/>dongari/open/list">동아리 관리</a>
								<a class="btn bg-success btn-xs" href="<c:out value="${CONTEXT_PATH }"/><%=com.hisco.cmm.util.Config.USER_ROOT%>/orgintro/dongari/list" target="_blank">FRONT</a>
							</c:when>
							<c:otherwise>
								<a class="btn bg-primary btn-xs" href="./article/<c:out value="${resultInfo.bbsId}"/>/list">게시글 관리</a>
								<a class="btn bg-success btn-xs" href="<c:out value="${CONTEXT_PATH }"/><%=com.hisco.cmm.util.Config.USER_ROOT%>/board/<c:out value="${resultInfo.bbsId}"/>/list" target="_blank">FRONT</a>
							</c:otherwise>
						</c:choose>

				 				<c:choose>
									<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
										<a class="btn btn-info btn-xs" href="./masterUpdt?bbsId=<c:out value="${resultInfo.bbsId}"/><c:out value="${commandMap.query}"/>">수정</a>
									</c:when>
									<c:otherwise>
										<a class="btn btn-info btn-xs disabled">수정</a>
									</c:otherwise>
								</c:choose>
								</td>
		                    </tr>
						</c:forEach>

		                  </tbody>
		                </table>
	              </div>
	              <!-- /.card-body -->
	              <div class="card-footer p-0">
	              	<div class="pagination_2">
						<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./masterList?pageIndex="/>
					</div>
	            </div>

	            <!-- /.card -->
	          </div>
	        </div>
	      </div>
	     </div>
	</section>
</body>