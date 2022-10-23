<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
	<script type="text/javascript">
	/* ********************************************************
	 * 삭제처리
	 ******************************************************** */
	 function fn_delete(stplatId) {

			if (confirm("삭제하시겠습니까?\n데이터 삭제하시면 복구할 수 없습니다.")) {
				$.ajax({
				      url: './termsDelete.json',
				      data : {'stplatId' : stplatId},
				      type : "POST",
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
						         window.location.replace("./termsList<c:out value="${searchQuery}"  escapeXml="false" />");
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
	
	       <div class="row">
	          <div class="col-12">
	            <div class="card card-primary card-outline">
	              <!-- /.card-header -->
	              <div class="card-body  table-responsive p-0">
	                	<table class="table table-bordered text-nowrap">
		               	<colgroup>
							<col style="width:10%;">
							<col style="width:40%;">
							<col style="width:10%;">
							<col style="width:40%;">
						</colgroup>
	
						<tbody>
						<!-- 글 제목 -->
						<tr>
							<th>제목</th>
							<td colspan="3" class="left"><c:out value="${result.stplatName}"/></td>
						</tr>
						<tr>
							<th>코드</th>
							<td><c:out value="${result.stplatId}"/> </td>
							<th>사용여부</th>
							<td><c:out value="${result.useYn}"/></td>
						</tr>
						<tr>
							<th>등록</th>
							<td class="left"><c:out value="${result.reguser}"/> / <fmt:formatDate value="${result.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
							<th>수정</th>
							<td class="left"><c:if test="${!empty result.moduser }"><c:out value="${result.moduser}"/> / <fmt:formatDate value="${result.moddate}" pattern="yyyy-MM-dd HH:mm"/></c:if></td>
						</tr>
	
						<!-- 글 내용 -->
						<tr>
							<th class="vtop">내용</th>
							<td colspan="5" class="cnt">
								<c:out value="${fn:replace(result.stplatCn, crlf , '<br>')}" escapeXml="false" />
							</td>
						</tr>
	
	
					</tbody>
	
						</table>
	
	                </div>
	                <!-- /.card-body -->
	                <div class="card-footer">
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
			<button type="button" class="btn btn-secondary float-right mr-2" onclick="fn_delete('<c:out value="${result.stplatId}"/>')">삭제</button>
		</c:when>
		<c:otherwise>
			<button type="button" class="btn btn-secondary float-right disabled mr-2">삭제</button>
		</c:otherwise>
	</c:choose>
	
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
			<a class="btn btn-info mr-2 float-right" href="./termsUpdt<c:out value="${commandMap.queryAll}"/>">수정</a>
		</c:when>
		<c:otherwise>
			<a class="btn btn-info mr-2 float-right disabled">수정</a>
		</c:otherwise>
	</c:choose>
	                  	<a class="btn btn-default " href="./termsList<c:out value="${searchQuery}"  escapeXml="false" />">목록</a>
	                </div>
	                <!-- /.card-footer -->
	
	              </div>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
	          </div>
	        </div>
	</section>
</body>
