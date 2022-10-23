<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : popupDetail.jsp
  * @Description : 팝업존 상세 화면
  * @Modification Information
  * @
  * @  수정일               수정자                   수정내용
  * @ -------       --------    ---------------------------
  * @ 2021.06.07    전영석                  최초 생성
  *
  *  @author 전영석
  *  @since 2021.06.07
  *  @version 1.0
  *  @see
  *
  */
%>

<head>
	<script type="text/javascript">
	/* ********************************************************
	 * 삭제처리
	 ******************************************************** */
	 function fn_delete(popupId) {
			if (confirm("삭제하시겠습니까?\n데이터를 삭제하시면 복구할 수 없습니다.")) {
				$.ajax({
				      url: './popupDelete',
				      data : {'popupId' : popupId},
				      type : "POST",
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
						         window.location.replace("./popupList<c:out value="${searchQuery}"  escapeXml="false" />");
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
	             <h1>팝업존 관리</h1>
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
							<td colspan="3" class="left"><c:out value="${result.popupTitleNm}"/></td>
						</tr>
<% /* DB 컬럼 추가 필요
						<tr>
							<th>팝업위치</th>
							<td colspan="3" class="left">
	<c:forEach items="${popupGbn}" var="gbn" varStatus="status">
			<c:if test="${gbn.cd eq  result.popupLocationcd}">
				<c:out value='${gbn.cdNm}'/>
			</c:if>
	</c:forEach>					
								(<c:out value='${result.popupLocationcd}'/>)			
							</td>
						</tr>
*/ %>						
						<tr>
							<th>링크URL</th>
							<td colspan="3" class="left"><c:out value="${result.fileUrl}"/>
								<c:choose>
									<c:when test="${ result.linkTarget eq '_blank'}">(새창)</c:when>
									<c:when test="${ result.linkTarget eq '_self'}">(본창)</c:when>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>팝업창위치</th>
							<td>가로 : <c:out value="${result.popupWlc}"/> , 세로: <c:out value="${result.popupHlc}"/></td>
							<th>팝업창사이즈</th>
							<td>Width : <c:out value="${result.popupWSize}"/> , Height: <c:out value="${result.popupHSize}"/></td>
						</tr>
						<tr>
							<th>게시기간</th>
							<td colspan="3" class="left">
								<c:choose>
									<c:when test="${empty result.ntceBgnde and empty result.ntceEndde}">상시</c:when>
									<c:otherwise><c:out value="${result.ntceBgnde}"/> ~ <c:out value="${result.ntceEndde}"/></c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>이미지</th>
							<td colspan="3" class="left">
	<c:forEach items="${fileList}" var="item" varStatus="status"><c:if test="${status.index == 0 }">
			<img src="<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName }" originName="${item.orginFileName }"/>" width="200"/></c:if>
	</c:forEach>
							</td>
						</tr>
	
	
						<tr>
							<th>등록일</th>
							<td class="left"><c:out value="${result.frstRegisterId}"/> / <fmt:formatDate value="${result.frstRegistPnttm}" pattern="yyyy-MM-dd HH:mm"/></td>
							<th>수정일</th>
							<td class="left"><c:if test="${!empty result.lastUpdusrId }"><c:out value="${result.lastUpdusrId}"/> / <fmt:formatDate value="${result.lastUpdtPnttm}" pattern="yyyy-MM-dd HH:mm"/></c:if></td>
						</tr>
	
	
	
					</tbody>
	
						</table>
	
	                </div>
	                <!-- /.card-body -->
	                <div class="card-footer">
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
			<button type="button" class="btn btn-secondary float-right mr-2" onclick="fn_delete('<c:out value="${result.popupId}"/>')">삭제</button>
		</c:when>
		<c:otherwise>
			<button type="button" class="btn btn-secondary float-right disabled mr-2">삭제</button>
		</c:otherwise>
	</c:choose>
	
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
			<a class="btn btn-info mr-2 float-right" href="./popupUpdt<c:out value="${commandMap.queryAll}"/>">수정</a>
		</c:when>
		<c:otherwise>
			<a class="btn btn-info mr-2 float-right disabled">수정</a>
		</c:otherwise>
	</c:choose>
	                  	<a class="btn btn-default " href="./popupList<c:out value="${searchQuery}"  escapeXml="false" />">목록</a>
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