<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : termsList.jsp
  * @Description : 약관 목록 JSP
  * @Modification Information
  * @
  * @  수정일              수정자            수정내용
  * @ -------       ------    ---------------------------
  * @ 2021.06.08    전영석           최초 생성
  *
  *  @author 전영석
  *  @since 2021.06.08
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
	            <h1>약관 관리</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div><!-- /.container-fluid -->
	</section>

	<!-- Main content -->
	<section class="content">
	      <div class="container-fluid">
	       	<div class="card card-primary card-outline">
				<form name="searchForm"  method="get" >
			 	 	<div class="card-body">
			 	 	 	<div class="row">
			              	<div class="col-4">
		              			<select name="searchCondition" title="검색영역"  class="form-control">
									<option value="0"  <c:if test="${searchVO.searchCondition == '0'}">selected="selected"</c:if> >제목</option>
								</select>
							</div>
							<div class="col-4">
									<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" >
							</div>
							<div class="col-4">
								<button type="submit" class="btn btn-secondary">조회</button>
								<a href="./termsRegist" class="btn btn-info<c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>" title="등록버튼">등록</a><!-- 등록 -->
			              	</div>

	 					</div>
	 				</div>
	 			</form>
	 			</div>

	       <div class="row">
	          <div class="col-12">
	            <div class="card">
	              <!-- /.card-header -->

	              <div class="card-body table-responsive p-0">
		               <table class="table table-hover text-nowrap">
		               	<colgroup>
							<col style="width: 9%;">
							<col style="width: 10%;">
							<col style="width: *">
							<col style="width: 10%;">
							<col style="width: 12%;">
							<col style="width: 12%;">
							<col style="width: 10%;">
						</colgroup>
		                  <thead>
		                    <tr>
		                      	<th>번호</th><!-- 번호 -->
								<th>약관코드</th>
								<th>제목</th>
								<th>사용여부</th>
								<th>등록일</th>
								<th>수정일</th>
								<th>관리</th>
		                    </tr>
		                  </thead>
		                  <tbody>
							<c:if test="${fn:length(resultList) == 0}">
								<tr>
									<td colspan="7">데이타가 없습니다</td>
								</tr>
							</c:if>
							<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
			                    <tr>
			                      	<td><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
									<td><c:out value='${resultInfo.stplatId}'/></td>
									<td><a href="./termsDetail?stplatId=<c:out value="${resultInfo.stplatId}"/><c:out value="${commandMap.query}"/>"><c:out value='${resultInfo.stplatName}'/></a></td>
									<td><c:out value='${resultInfo.useYn}'/></td>
									<td><fmt:formatDate value="${resultInfo.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
									<td><fmt:formatDate value="${resultInfo.moddate}" pattern="yyyy-MM-dd HH:mm"/></td>
									<td>
									  <a class="btn bg-primary btn-xs<c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" href="./termsUpdt?stplatId=<c:out value="${resultInfo.stplatId}"/><c:out value="${commandMap.query}"/>">수정</a>
									</td>
			                    </tr>
							</c:forEach>

		                  </tbody>
		                </table>
	              </div>
	              <!-- /.card-body -->
	              <div class="card-footer p-0">
	              	<div class="pagination_2">
						<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./termsList?pageIndex="/>
					</div>
	            </div>

	            <!-- /.card -->
	          </div>
	        </div>
	      </div>
	     </div>
	</section>
</body>
