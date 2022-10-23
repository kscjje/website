<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<head>

</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>설문조사 기준관리</h1>
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
				<form:form commandName="searchVO" name="searchVO" method="get">
					 <div class="card-body">
			 	 	 	<div class="row">
			              	<div class="col-4">
			              		<select name="searchCondition" class="form-control" title="검색영역">
			              			${hisco:makeOptions("SM_QESTNAR_SRCH_COND_CD", "EMPTY", searchVO.searchCondition)}
			              		</select>
							</div>
							<div class="col-4">
								<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어" value='${searchVO.searchKeyword}'  maxlength="155" >
							</div>
							<div class="col-4">
								<button type="submit" class="btn btn-secondary">조회</button>
								<a href="./addThema" class="btn btn-info" title="등록">등록</a><!-- 등록 -->
			              	</div>
	 					</div>
	 				</div>
	 			</form:form>
	 	   </div>
	       <div class="row">
	          <div class="col-12">
	            <div class="card">
	              <!-- /.card-header -->

	              <div class="card-body table-responsive p-0">
		               <table class="table table-hover text-nowrap">
		               	<colgroup>
							<col style="width: 8%;">
							<col style="width: 13%;">
							<col style="width: 13%;">
							<col style="width: *">
							<col style="width: 10%;">
							<col style="width: 13%;">
							<col style="width: 10%;">
							<col style="width: 13%;">
						</colgroup>
		                  <thead>
		                    <tr>
		                      	<th>번호</th><!-- 번호 -->
		                      	<th>등록일</th>
								<th>구분</th>
								<th>기관유형(대)</th>
								<th>설문 주제</th>
								<th>참여수</th>
								<th>강좌대상/강좌명</th>
								<th>현황</th>
		                    </tr>
		                  </thead>
		                  <tbody>
					<c:if test="${fn:length(expStdList) == 0}">
						<tr>
							<td colspan="8" align="center">데이타가 없습니다</td>
						</tr>
					</c:if>
						<c:forEach items="${expStdList}" var="item" varStatus="status">
		                    <tr>
		                      	<td>${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}</td>
								<td>${item.useYn}</td>
								<td>${item.useYn}</td>
								<td class="left"><a href="./editExpStandard?qestnarId=${item.useYn}${commandMap.query}" >${item.useYn}</a></td>
								<td>${item.useYn}</td>
								<td>${item.useYn}</td>
								<td>
							  		<a class="btn btn-info btn-xs" href="./surveyQuestionList?qestnarId=${item.useYn}&${commandMap.query}">[설문]</a>
							  		<a class="btn btn-info btn-xs" href="./surveyQuestionList?qestnarId=${item.useYn}&${commandMap.query}">[통계]</a>
								</td>
								<td>${item.useYn}</td>
		                    </tr>
						</c:forEach>

		                  </tbody>
		                </table>
	              </div>
	              <!-- /.card-body -->
	              <div class="card-footer p-0">
	              	<div class="pagination_2">
						<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./surveyThemaList?pageIndex="/>
					</div>
	            </div>

	            <!-- /.card -->
	          </div>
	        </div>
	      </div>
	   </div>
	</section>
</body>