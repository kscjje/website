<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : bannerDetail.jsp
  * @Description : 배너  상세 화면
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.30    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.30
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
	 function fn_delete(bannerId) {
			if (confirm("삭제하시겠습니까?\n데이터 삭제하시면 복구할 수 없습니다.")) {
				$.ajax({
				      url: './bannerDelete.json',
				      data : {'bannerId' : bannerId},
				      type : "POST",
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
						         window.location.replace("./orgBannerList<c:out value="${searchQuery}"  escapeXml="false" />");
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
	             <h1>기업홍보 배너존 관리</h1>
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
							<th>배너명</th>
							<td colspan="3" class="left"><c:out value="${result.bannerNm}"/></td>
						</tr>
						<tr>
							<th>배너위치</th>
							<td colspan="3" class="left">
	<c:forEach items="${bannerGbn}" var="gbn" varStatus="status">
			<c:if test="${gbn.cd eq  result.bannerGbn}">
				<c:out value='${gbn.cdNm}'/>
			</c:if>
	</c:forEach>
								(<c:out value='${result.bannerGbn}'/>)
							</td>
						</tr>
						<tr>
							<th>링크URL</th>
							<td colspan="3" class="left"><c:out value="${result.linkUrl}"/>
								<c:choose>
									<c:when test="${result.linkTarget eq '_self' }">(본창)</c:when>
									<c:when test="${result.linkTarget eq '_blank' }">(새창)</c:when>
								</c:choose>
							</td>
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
							<th>정렬순서</th>
							<td><c:out value="${result.sortOrdr}"/> </td>
							<th>사용여부</th>
							<td><c:out value="${result.reflctAt}"/></td>
						</tr>
						<tr>
							<th>이미지</th>
							<td colspan="3" class="left">
	<c:forEach items="${fileList}" var="item" varStatus="status"><c:if test="${status.index == 0 }">
			<img src="<tags:UploadFileUrl filePath="${item.filePath}" fileName="${item.fileName }" originName="${item.orginFileName }"/>" height="100"/></c:if>
	</c:forEach>
							</td>
						</tr>
						<tr>
							<th>메인노출 텍스트(선택)</th>
							<td colspan="3" class="left"><c:out value="${fn:replace(result.bannerDc , crlf , '<br/>')}" escapeXml="false" /></td>
						</tr>
						<tr>
							<th>배너클릭 연결방식</th>
							<td colspan="3" class="left">
								<c:choose>
									<c:when test="${result.linkType ne '2'}">다이렉트 URL이동</c:when>
									<c:otherwise>배너존 상세이동</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>배너존 상세텍스트</th>
							<td colspan="3" class="left">
								<c:out value="${fn:replace(result.linkContent , crlf , '<br/>')}" escapeXml="false" />
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
			<button type="button" class="btn btn-secondary float-right mr-2" onclick="fn_delete('<c:out value="${result.bannerId}"/>')">삭제</button>
		</c:when>
		<c:otherwise>
			<button type="button" class="btn btn-secondary float-right disabled mr-2">삭제</button>
		</c:otherwise>
	</c:choose>

	 <c:choose>
		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
			<a class="btn btn-info mr-2 float-right" href="./orgBannerUpdt<c:out value="${commandMap.queryAll}"/>">수정</a>
		</c:when>
		<c:otherwise>
			<button type="button" class="btn btn-info mr-2 float-right disabled">수정</button>
		</c:otherwise>
	</c:choose>
	                  	<a class="btn btn-default " href="./orgBannerList<c:out value="${searchQuery}"  escapeXml="false" />">목록</a>
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