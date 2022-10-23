<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>

</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>관리자 디자인 가이드</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div><!-- /.container-fluid -->
	</section>
	<section class="content">
		<div class="container-fluid">
			<div class="card card-primary card-outline">
				<div class="card-body ">
					<h6>검색 영역 버튼</h6>
				</div>
				<div class="card-body p-0">
					<div class="row">
						<div class="col-1"></div>
						<div class="col-2">
							<input type="text" class="form-control"/>
						</div>				
				 		<div class="col-2">
				 			<button type="button" class="btn btn-primary">조회</button>
				 			<button type="button" class="btn btn-info">추가</button>
						</div>
					</div>
				</div>
				<div class="card-foot">
			 		<table class="table table-bordered">
		               	<colgroup>
							<col style="width: 200px;">
							<col style="">
						</colgroup>			 			
			 			<tr>
			 				<th class="text-right">
			 					<button type="button" class="btn btn-primary">조회</button>
			 				</th>
			 				<td>
			 					&lt;button type="button" class="<span class="text-red">btn btn-primary</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
			 				</td>
			 			</tr>
			 			<tr>
			 				<th class="text-right">
			 					<button type="button" class="btn btn-info">추가</button>
			 				</th>
			 				<td>
			 					&lt;button type="button" class="<span class="text-red">btn btn-info</span>"&gt;추가&lt;/button&gt;&lt;/pre&gt;
			 				</td>
			 			</tr>
			 			<tr>
			 				<th class="text-right">
			 					<button type="button" class="btn btn-warning">순서저장</button>
			 				</th>
			 				<td>
			 					&lt;button type="button" class="<span class="text-red">btn btn-warning</span>"&gt;순서저장&lt;/button&gt;&lt;/pre&gt;
			 				</td>
			 			</tr>
			 			<tr>
			 				<th class="text-right">
			 					<button type="button" class="btn btn-secondary">초기화</button>
			 				</th>
			 				<td>
			 					&lt;button type="button" class="<span class="text-red">btn btn-secondary</span>"&gt;초기화회&lt;/button&gt;&lt;/pre&gt;
			 				</td>
			 			</tr>
					</table>
				</div>
			</div>
			 	 	 	
			<div class="card card-primary card-outline">
				<div class="card-body ">
					<h6>입력/숫정/상세</h6>
				</div>
				<div class="card-body p-0">
				 		<table class="table table-bordered">
			               	<colgroup>
								<col style="width: 200px;">
								<col style="">
							</colgroup>			 			
				 			<tr>
				 				<th class="text-right">
				 					<button type="button" class="btn btn-info">신규/추가/등록</button>
				 				</th>
				 				<td>
				 					&lt;button type="button" class="<span class="text-red">btn btn-info</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
				 				</td>
				 			</tr>
				 			<tr>
				 				<th class="text-right">
				 					<button type="button" class="btn btn-success">수정</button>
				 				</th>
				 				<td>
				 					&lt;button type="button" class="<span class="text-red">btn btn-success</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
				 				</td>
				 			</tr>
				 			<tr>
				 				<th class="text-right">
				 					<button type="button" class="btn btn-secondary">삭제/사용안함</button>
				 				</th>
				 				<td>
				 					&lt;button type="button" class="<span class="text-red">btn btn-secondary</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
				 				</td>
				 			</tr>
				 			<tr>
				 				<th class="text-right">
				 					<button type="button" class="btn btn-danger">완전삭제/데이터삭제</button>
				 				</th>
				 				<td>
				 					&lt;button type="button" class="<span class="text-red">btn btn-danger</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
				 				</td>
				 			</tr>
						</table>
						
				</div>
			</div>
			
			 	 	 	
			<div class="card card-primary card-outline">
				<div class="card-body ">
					<h6>리스트 내 버튼 (테이블 안)</h6>
				</div>
				<div class="card-body p-0">
				 		<table class="table table-bordered">
			               	<colgroup>
								<col style="width: 200px;">
								<col style="">
							</colgroup>			 			
				 			<tr>
				 				<th class="text-right">
				 					<button type="button" class="btn btn-info btn-xs btn-flat">신규/추가/등록</button>
				 				</th>
				 				<td>
				 					&lt;button type="button" class="<span class="text-red">btn btn-info btn-xs btn-flat</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
				 				</td>
				 			</tr>
				 			<tr>
				 				<th class="text-right">
				 					<button type="button" class="btn btn-success btn-xs btn-flat">수정</button>
				 				</th>
				 				<td>
				 					&lt;button type="button" class="<span class="text-red">btn btn-success btn-xs btn-flat</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
				 				</td>
				 			</tr>
				 			<tr>
				 				<th class="text-right">
				 					<button type="button" class="btn btn-secondary btn-xs btn-flat">삭제/사용안함</button>
				 				</th>
				 				<td>
				 					&lt;button type="button" class="<span class="text-red">btn btn-secondary btn-xs btn-flat</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
				 				</td>
				 			</tr>
				 			<tr>
				 				<th class="text-right">
				 					<button type="button" class="btn btn-danger btn-xs btn-flat">완전삭제/데이터삭제</button>
				 				</th>
				 				<td>
				 					&lt;button type="button" class="<span class="text-red">btn btn-danger btn-xs btn-flat</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
				 				</td>
				 			</tr>				 			
				 			<tr>
				 				<th class="text-right">
				 					<button type="button" class="btn btn-outline-secondary btn-xs btn-flat">기타</button>
				 				</th>
				 				<td>
				 					&lt;button type="button" class="<span class="text-red">btn btn-outline-secondary btn-xs btn-flat</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
				 				</td>
				 			</tr>				 			
				 			
				 			<tr>
				 				<th class="text-right">
				 					<button type="button" class="btn btn-outline-primary btn-xs btn-flat">기타</button>
				 				</th>
				 				<td>
				 					&lt;button type="button" class="<span class="text-red">btn btn-outline-primary btn-xs btn-flat</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
				 				</td>
				 			</tr>				 			
				 			<tr>
				 				<th class="text-right">
				 					<button type="button" class="btn btn-outline-info btn-xs btn-flat">기타</button>
				 				</th>
				 				<td>
				 					&lt;button type="button" class="<span class="text-red">btn btn-outline-info btn-xs btn-flat</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
				 				</td>
				 			</tr>				 			
				 			<tr>
				 				<th class="text-right">
				 					<button type="button" class="btn btn-outline-warning btn-xs btn-flat">기타</button>
				 				</th>
				 				<td>
				 					&lt;button type="button" class="<span class="text-red">btn btn-outline-warning btn-xs btn-flat</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
				 				</td>
				 			</tr>				 			
				 			<tr>
				 				<th class="text-right">
				 					<button type="button" class="btn btn-outline-danger btn-xs btn-flat">기타</button>
				 				</th>
				 				<td>
				 					&lt;button type="button" class="<span class="text-red">btn btn-outline-danger btn-xs btn-flat</span>"&gt;조회&lt;/button&gt;&lt;/pre&gt;
				 				</td>
				 			</tr>				 			
						</table>
				</div>
			</div>			
		</div>
	</section>
</body>