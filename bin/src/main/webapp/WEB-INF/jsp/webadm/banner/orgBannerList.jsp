<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : bannerList.jsp
  * @Description : 배너 목록 JSP
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
	<script>
		$(document).ready(function () {
			$( ".s-isActive" ).on( "change", function() {
				$('form[name=searchForm]').submit();
			});
		});

		function fn_gubun_change(gubun) {
			$('#searchGubun').val(gubun);
			document.searchForm.submit();
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
	<form name="searchForm"  method="get" >
	<input type="hidden" name="searchGubun" id="searchGubun" value="<c:out value="${searchVO.searchGubun }"/>" />
	      <div class="container-fluid">
	       	<div class="card card-primary card-outline card-outline-tabs">
<c:if test="${bannerGbn!=null and fn:length(bannerGbn)>1}">

				<div class="card-header p-0 border-bottom-0">
	                <ul class="nav nav-tabs" id="custom-tabs-four-tab" role="tablist">

	                  <li class="nav-item">
	                    <a class="nav-link <c:if test="${empty searchVO.searchGubun}">active</c:if>"  href="javascript:fn_gubun_change('${gbn}')" role="tab" aria-controls="custom-tabs-four-home" aria-selected="true">전체</a>
	                  </li>
	<c:forEach items="${bannerGbn}" var="gbn" varStatus="status">
	                  <li class="nav-item">
	                    <a class="nav-link <c:if test="${searchVO.searchGubun eq gbn.cd}">active</c:if>" href="javascript:fn_gubun_change('${gbn.cd}')" role="tab">${gbn.cdNm }</a>
	                  </li>
	 </c:forEach>
	                </ul>
	              </div>
</c:if>
			 	 	 <div class="card-body">
			 	 	 	<div class="row">
			              	<div class="col-2">
		              			<select name="searchCondition" title="검색영역"  class="form-control">
									<option value="1"  <c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if> >배너명</option>
								</select>
							</div>
							<div class="col-2">
									<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어입력" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" >
							</div>
							<div class="col-2">
								<select name="isActive" title="활성화"  class="form-control s-isActive">
									<option value="" <c:if test="${searchVO.isActive eq '' }">selected</c:if> >구분(전체)</option>
									<option value="Y" <c:if test="${searchVO.isActive eq 'Y' }">selected</c:if> >활성상태만보기</option>
								</select>
							</div>
							<div class="col-4">
								<button type="submit" class="btn btn-secondary">조회</button>
								<a href="./orgBannerRegist" class="btn btn-info<c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>" title="등록버튼">등록</a><!-- 등록 -->
			              	</div>

	 					</div>
	 				</div>

	 			</div>
	 </form>
	       <div class="row">
	          <div class="col-12">
	            <div class="card">
	              <!-- /.card-header -->

	              <div class="card-body table-responsive p-0">
		               <table class="table table-hover text-nowrap">
		               	<colgroup>
							<col style="width: 80px;">
							<col style="width: 120px;">
							<col style="width: *">
							<col style="width: 30%; white-space: nowrap; display:block;">
							<col style="width: 20%;">
							<col style="width: 80px;">
							<col style="width: 100px;">
							<col style="width: 80px;">
						</colgroup>
		                  <thead>
		                    <tr>
		                      	<th>번호</th><!-- 번호 -->
								<th>위치</th>
								<th>배너명</th>
								<th>링크URL</th>
								<th>게시기간</th>
								<th>순서</th>
								<th>사용여부</th>
								<th>관리</th>
		                    </tr>
		                  </thead>
		                  <tbody>
	<c:if test="${fn:length(resultList) == 0}">
						<tr>
							<td colspan="8">등록된 데이타가 없습니다</td>
						</tr>
	</c:if>
	<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
		                    <tr <c:if test="${resultInfo.isActive eq 'Y'}"> class="text-green"</c:if> >
		                      	<td><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
								<td>
	<c:forEach items="${bannerGbn}" var="gbn" varStatus="status">
			<c:if test="${gbn.cd eq  resultInfo.bannerGbn}">
				<c:out value='${gbn.cdNm}'/>
			</c:if>
	</c:forEach>
									(<c:out value='${resultInfo.bannerGbn}'/>)
								</td>
								<td><a href="./orgBannerDetail?bannerId=<c:out value="${resultInfo.bannerId}"/><c:out value="${commandMap.query}"/>"><c:out value='${resultInfo.bannerNm}'/></a></td>
								<td><c:out value='${resultInfo.linkUrl}'/></td>
								<td>
									<c:choose>
										<c:when test="${empty resultInfo.ntceBgnde and empty resultInfo.ntceEndde}">상시</c:when>
										<c:otherwise><c:out value="${resultInfo.ntceBgnde}"/> ~ <c:out value="${resultInfo.ntceEndde}"/></c:otherwise>
									</c:choose>
								</td>
								<td><c:out value='${resultInfo.sortOrdr}'/></td>
								<td>
									<c:choose>
										<c:when test="${resultInfo.reflctAt eq 'Y'}">사용</c:when>
										<c:otherwise>사용안함</c:otherwise>
									</c:choose>
								</td>
								<td>
								  <a class="btn bg-primary btn-xs<c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" href="./orgBannerUpdt?bannerId=<c:out value="${resultInfo.bannerId}"/><c:out value="${commandMap.query}"/>">수정</a>
								</td>
		                    </tr>
	</c:forEach>

		                  </tbody>
		                </table>
	              </div>
	              <!-- /.card-body -->
	              <div class="card-footer p-0">
	              	<div class="pagination_2">
						<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./orgBannerList?pageIndex="/>
					</div>
	            </div>

	            <!-- /.card -->
	          </div>
	        </div>
	      </div>
	     </div>
	</section>
</body>