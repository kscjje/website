<%
 /**
  * @Class Name :  partCdList.jsp
  * @Description : 사업장 목록 JSP
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>

<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>사업장 관리</h1>
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
		              			<select name="searchCondition" title="검색"  class="form-control">
									<option value="1"  <c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if> >사업장명</option>
								</select>
						</div>
						<div class="col-4">
								<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" >
						</div>
						<div class="col-4">
							<button type="submit" class="btn btn-secondary">조회</button>
							<a href="./partCdRegi" class="btn btn-info<c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>" title="등록버튼">등록</a>
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
						<col style="width: *">
						<col style="width: 30%;">
						<col style="width: 20%;">
						<col style="width: 8%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th>번호</th>
							<th>고유번호</th>
							<th>사업장명</th>
							<th>사업장구분</th>
							<th>담당자연락처(HP)</th>
							<th>담당자전화</th>
							<th>정렬값</th>
							<th>사용여부</th>
							<th></th>
	                    </tr>
	                  </thead>
	                  <tbody>

					<c:if test="${fn:length(partCdList) == 0}">
					<tr>
						<td colspan="8">데이타가 없습니다</td>
					</tr>
					</c:if>

						<c:forEach items="${partCdList}" var="item" varStatus="status">
		                    <tr>
		                      	<td>
		                      		<c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/>
		                      	</td>
								<td>
									<a href="./partCdDetail?partCd=<c:out value="${item.partCd}"/><c:out value="${commandMap.query}"/>"><c:out value='${item.partCd}'/></a>
								</td>
								<td>
									<c:out value='${item.partNm}'/></td>
								<td>
									<c:out value='${item.partGbn}'/></td>
								</td>
								<td>
									<c:out value='${item.hpNo}'/>
								</td>
								<td>
									<c:out value='${item.tel}'/>
								</td>
								<td>
									<c:out value='${item.sortOrder}'/>
								</td>
								<td>
								  	<c:out value='${item.useYn}'/>
								</td>
								<td>
								  	<a class="btn bg-primary btn-xs<c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" href="./partCdUpdt?partCd=<c:out value="${item.partCd}"/><c:out value="${commandMap.query}"/>">수정</a>
								  	<a class="btn bg-primary btn-xs<c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" href="#" onclick="fn_deletePartCd('${item.partCd}');">삭제</a>
								</td>
		                    </tr>
						</c:forEach>

	                  </tbody>
	                </table>
              </div>

              <div class="card-footer p-0">
              	<div class="pagination_2">
					<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./partCdList?pageIndex="/>
				</div>
            </div>


          </div>
        </div>
      </div>
     </div>
</section>

<form:form commandName="templateVO" name="templateVO" action="./partCdSave" class="form-horizontal" method="post" enctype="multipart/form-data">
	<input type="hidden" name="partCd" id="partCd" />
	<input type="hidden" name="mode"   value="delete" />
</form:form>

<script>

	function fn_deletePartCd(partCd) {

		var varFrom = document.templateVO;

		if (confirm("선택하신 사업장 정보를 삭제합니다.")) {
			$("#partCd").val(partCd);
			varFrom.submit();
			return;
		}

	}

</script>