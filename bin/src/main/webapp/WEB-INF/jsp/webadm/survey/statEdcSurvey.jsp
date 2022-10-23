<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>

</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>설문조사(강의평가형) 통계현황</h1>
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
				<div class="card-body">
                      <table class="table table-hover text-nowrap">
		               	<colgroup>
							<col style="width: 20%">
							<col style="width: 30%">
							<col style="width: 20%">
							<col style="width: 30%">
						</colgroup>
		                  <tbody>
		                    <tr>
		                      	<td bgcolor="#CCCCCC">강좌제목명</td>
								<td colspan="3">매주 수,월계동 배드민턴 모임으로 단합합시다.</td>
		                    </tr>
							<tr>
		                      	<td bgcolor="#CCCCCC">진행상태</td>
								<td>교육중</td>
								<td bgcolor="#CCCCCC">정원수</td>
								<td>총 18명 (온라인 12명 / 현장 6명)</td>
		                    </tr>
							<tr>
		                      	<td bgcolor="#CCCCCC">진행/총 회차</td>
								<td>7회 진행(총 18회차 중)</td>
								<td bgcolor="#CCCCCC">교육인원(총원)수</td>
								<td>총 16명(도중 3명 충원됨)</td>
		                    </tr>
							<tr>
		                      	<td bgcolor="#CCCCCC">평가 참여자(률)</td>
								<td>9명(50%)</td>
								<td bgcolor="#CCCCCC">총 평가점수</td>
								<td>75점</td>
		                    </tr>
		                  </tbody>
		              </table>
 				</div>
	 	   </div>

	       <div class="row">
	          <div class="col-12">
	            <div class="card">
	              <!-- /.card-header -->

	              <div class="card-body table-responsive p-0">
		               <table class="table table-hover text-nowrap">
		               	<colgroup>
							<col style="width: 8%">
							<col style="width: 30%">
							<col style="width: 30%">
							<col style="width: 30%">
							<col style="width: *">
						</colgroup>
		                  <thead>
		                    <tr>
		                      	<th>번호</th><!-- 번호 -->
		                      	<th>작성일</th>
								<th>작성자</th>
								<th>총평점수</th>
								<th>상세내용</th>
		                    </tr>
		                  </thead>
		                  <tbody>
					<c:if test="${fn:length(surveyList) == 0}">
						<tr>
							<td colspan="5" align="center">데이타가 없습니다</td>
						</tr>
					</c:if>
						<c:forEach items="${surveyList}" var="item" varStatus="status">
		                    <tr>
		                      	<td>${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}</td>
								<td>2021-09-13</td>
								<td>김아랑</td>
								<td class="left">★★★★</td>
								<td>
							  		<a class="btn btn-info btn-xs" href="./editQuestion/${item.qestnarId}">[보기]</a>
								</td>
		                    </tr>
						</c:forEach>

		                  </tbody>
		                </table>
	              </div>
	              <!-- /.card-body -->
	              <div class="card-footer p-0">
	              	<div class="pagination_2">
						<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./themaList?pageIndex="/>
					</div>
	            </div>

	            <!-- /.card -->
	          </div>
	        </div>
	      </div>
	     </div>
	</section>
</body>