<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name :  contentsList.jsp
  * @Description : 컨텐츠 관리 목록 JSP
  * @Modification Information
  * @
  * @  수정일           수정자              수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.03.19  전영석            최초 생성
  *
  *  @author 전영석
  *  @since 2021.03.19
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
	<script>
		function fn_deleteContents(contentsSeq) {

			if (confirm("선택하신 컨텐츠 정보를 삭제합니다.")) {
				$.ajax({
				      url: './contentsDelete.json',
				      type : "POST",
				      data : {'contentsSeq' : contentsSeq},
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
						        window.location.reload();
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
	            <h1>컨텐츠 관리</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div>
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
										<option value="1"  <c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if> >컨텐츠명</option>
									</select>
							</div>
							<div class="col-4">
									<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" >
							</div>
							<div class="col-4">
								<button type="submit" class="btn btn-secondary">조회</button>
								<a href="./contentsRegist" class="btn btn-info<c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>" title="등록버튼">등록</a>
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
							<col style="width: 8%;">
							<col style="width: 8%;">
							<col style="width: 15%">
							<col style="width: 9%;">
							<col style="width: *">
							<col style="width: 9%;">
							<col style="width: 15%;">
							<col style="width: 10%;">
						</colgroup>
		                  <thead>
		                    <tr>
		                      	<th>번호</th>
		                      	<th>고유번호</th>
		                      	<th>연결 메뉴</th>
		                      	<th>컨텐츠 속성</th>
		                      	<th>컨텐츠 제목</th>
								<th>사용여부</th>
								<th>최종수정일</th>
								<th>관리</th>
		                    </tr>
		                  </thead>
		                  <tbody>

						<c:if test="${fn:length(contentsList) == 0}">
							<tr>
								<td colspan="8">데이타가 없습니다</td>
							</tr>
						</c:if>

<c:forEach items="${contentsList}" var="item" varStatus="status">
			                    <tr>
			                      	<td><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
									<td><c:out value='${item.contentsSeq}'/></td>
									<td>${item.menuNm } <c:if test="${item.menuNo > 0 }">(${item.menuNo })</c:if></td>
									<td>
										<c:if test="${item.cntsType eq '1001'}">
											HTML
										</c:if>
										<c:if test="${item.cntsType eq '2001'}">
											TEXT
										</c:if>
									</td>
									<td>
										<c:choose>
											<c:when test="${empty item.menuUrl }"><c:out value='${item.title}'/></c:when>
											<c:otherwise><a href="<c:url value="${item.menuUrl}"/>"><c:out value='${item.title}'/></a></c:otherwise>
										</c:choose>

									</td>
									<td>
									  	<c:out value='${item.useYn}'/>
									</td>
									<td>
									  	<fmt:formatDate value='${item.moddate}' pattern="yyyy.MM.dd HH:mm"/>
									</td>
									<td>
									  	<a class="btn bg-primary btn-xs<c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" href="./contentsUpdt?contentsSeq=${item.contentsSeq}">수정</a>
									  	<a class="btn bg-secondary btn-xs<c:if test="${commandMap.selectedMenu.delYn ne 'Y' }"> disabled</c:if>" href="javascript:fn_deleteContents('${item.contentsSeq}');">삭제</a>
									</td>
			                    </tr>
</c:forEach>
		                  </tbody>
		                </table>
	              </div>

	              <div class="card-footer p-0">
	              	<div class="pagination_2">
						<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./contentsList?pageIndex="/>
					</div>
	            </div>


	          </div>
	        </div>
	      </div>
	     </div>
	</section>
</body>
