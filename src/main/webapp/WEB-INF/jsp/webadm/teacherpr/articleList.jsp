<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
 /**
  * @Class Name : articleList.jsp
  * @Description : 게시판 게시물  목록 화면
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
<head>
	<script>
		function fn_article_delete(nttId) {
			if (confirm("사용안함 처리 하시겠습니까?\n처리 시 데이타는 남아 있고 사용자 페이지에서는 노출되지 않습니다.")) {
				$.ajax({
				      url: './delete.json',
				      data : {'nttId' : nttId},
				      type : "POST",
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
						         top.location.reload();
					         }
				      }
				 });

			}

		}
	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1><small>강사관리 > </small> 강사PR
	            </h1>
	          </div>
	          <div class="col-sm-6">

	          </div>
	        </div>
	      </div><!-- /.container-fluid -->
	</section>

	<!-- Main content -->
	<section class="content">
	      <div class="container-fluid">
	       	<div class="card  card-primary card-outline">
	<form name="articleForm" action="./list" method="get" >
			 	 	 <div class="card-body">
			 	 	 	<div class="row">

	<c:if test="${!empty ctgList }">
							<div class="col-2">
									<select name="searchCtg" title="카테고리 선택"  class="form-control">
										<option value=""  >::카테고리::</option>
	<c:forEach items="${ctgList}" var="item" varStatus="status">
										<option value="<c:out value="${item.ctgId }"/>" <c:if test="${searchVO.searchCtg eq item.ctgId }">selected</c:if> ><c:out value="${item.ctgNm }"/></option>
	</c:forEach>
									</select>

							</div>
	</c:if>
			              	<div class="col-3">
		              			<select name="searchCnd" title="검색영억"  class="form-control">
									<option value="0"  <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> >강사PR 제목</option>
									<option value="1"  <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if> >강좌소개</option>
									<option value="2"  <c:if test="${searchVO.searchCnd == '2'}">selected="selected"</c:if> >작성자</option>
								</select>
							</div>
							<div class="col-3">
									<input class="form-control" name="searchWrd" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchWrd}"/>'  maxlength="155" >
							</div>
							<div class="col-2">
								<button type="submit" class="btn btn-secondary">조회</button>
			              	</div>
	<c:if test="${empty ctgList }">
							<div class="col-2"></div>
	</c:if>
	 					</div>
	 				</div>
	 </form>
	 			</div>

	       <div class="row">
	          <div class="col-12">
	            <div class="card card-primary ">
	              <!-- /.card-header -->

	              <div class="card-body table-responsive p-0">
		               <table class="table table-hover text-nowrap">
		               	<colgroup>
							<col style="width: 5%;">
							<col style="width: 7%;">
							<col style="width: *">
							<col style="width: 10%;">
							<col style="width: 10%;">
							<col style="width: 8%;">
						</colgroup>
		                  <thead>
		                    <tr>
		                      	<th>번호</th><!-- 번호 -->
		                      	<th>강사명</th>
								<th class="board_th_link">강사PR 제목</th>
								<th>강좌주제</th>
								<th>핵심키워드</th>
								<th>관리</th>
		                    </tr>
		                  </thead>
		                  <tbody>
	<c:if test="${fn:length(resultList) == 0}">
						<tr>
							<td colspan="10" class="text-center">데이타가 없습니다</td>
						</tr>
	</c:if>
<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
								<td>
				<c:choose>
					<c:when test="${resultInfo.useAt eq 'Y' }">
									<c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/>
					</c:when>
					<c:otherwise>
									<span class="text-red"><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></span>
					</c:otherwise>
				</c:choose>
								</td>
								<td><c:out value='${resultInfo.frstRegisterNm}'/></td>
								<td>
				<c:choose>
					<c:when test="${resultInfo.useAt eq 'Y' }">
						<a href="./view/<c:out value="${resultInfo.nttId}"/><c:out value="${commandMap.queryAll}"/>"><c:out value='${fn:substring(resultInfo.nttSj, 0, 40)}'/></a>
					</c:when>
					<c:otherwise>
						<a href="./view/<c:out value="${resultInfo.nttId}"/><c:out value="${commandMap.queryAll}"/>" class="text-red"><strike><i><c:out value='${fn:substring(resultInfo.nttSj, 0, 40)}'/></i></strike></a>
					</c:otherwise>
				</c:choose>
								</td>
								<td><c:out value='${fn:substring(resultInfo.item1, 0, 20)}'/></td>
								<td>
									<c:if test="${fn:length(resultInfo.item2)>0 }">#<c:out value='${resultInfo.item2}'/></c:if>
									<c:if test="${fn:length(resultInfo.item3)>0 }">#<c:out value='${resultInfo.item3}'/></c:if>
									<c:if test="${fn:length(resultInfo.item4)>0 }">#<c:out value='${resultInfo.item4}'/></c:if>
									<c:if test="${fn:length(resultInfo.item5)>0 }">#<c:out value='${resultInfo.item5}'/></c:if>
								</td>
								<td>
									<c:if test="${resultInfo.useAt eq 'Y' }">
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
			<a class="btn bg-primary btn-xs" href="./modify?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${commandMap.query}"/>">수정</a>
		</c:when>
		<c:otherwise>
			<a class="btn bg-primary btn-xs disabled">수정</a>
		</c:otherwise>
	</c:choose>
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
			<button type="button" onclick="fn_article_delete(<c:out value="${resultInfo.nttId}"/>)" class="btn btn-secondary btn-xs" >사용안함</button>
		</c:when>
		<c:otherwise>
		<% /*
			<button type="button" class="btn btn-secondary btn-xs disabled" >삭제</button>
			*/ %>
		</c:otherwise>
	</c:choose>
									</c:if>
								</td>
		                    </tr>
</c:forEach>

		                  </tbody>
		                </table>
	              </div>
	              <!-- /.card-body -->
	              <div class="card-footer p-0">
	              	<div class="pagination_2">
						<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
					</div>
	            </div>

	            <!-- /.card -->
	          </div>
	        </div>
	      </div>
	     </div>
	</section>
</body>
