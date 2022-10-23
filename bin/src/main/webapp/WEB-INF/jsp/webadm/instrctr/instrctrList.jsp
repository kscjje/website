<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : instrctrList.jsp
	 * @Description : 강사관리 리스트 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.11.05     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2021.11.05
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<script>
$(document).ready(function(){
	$( ".datepicker" ).datepicker({
		"dateFormat":'yy-mm-dd',
		'monthNames':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'monthNamesShort':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'changeMonth' : true,
		'changeYear':true
	});
});
</script>
<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>교육 강사 현황</h1>
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
		              	<div class="col-3">
 							<hisco:OrgList defaultNo="${param.searchOrgNo}" id="searchOrgNo" />
 		              	</div>

 		              	<div class="col-1" style="text-align:right">등록일</div>
 		              	<div class="col-4">
								<div class="input-group date" style="float:left;width:45%"  data-target-input="nearest" >
			                        <form:input cssClass="form-control datepicker" path="searchStartDts" autocomplete="off"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>

								<div style="width:10%;float:left;text-align:center">~</div>
								<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest">
			                        <form:input cssClass="form-control datepicker" path="searchEndDts" autocomplete="off"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>
						</div>

 					</div>
 					<div class="row pt-2">
 						<div class="col-3">
	              			<select name="searchCondition" title="검색"  class="form-control">
 								<option value="name"  <c:if test="${searchVO.searchCondition eq 'name'}">selected="selected"</c:if> >강사명</option>
							</select>
 						</div>
						<div class="col-3">
 								<input class="form-control" name="searchKeyword" type="text" value="<c:out value="${searchVO.searchKeyword}"/>" size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" >
 						</div>
 						<div class="col-3">
 							<button type="submit" class="btn btn-secondary">조회</button>
							<a href="./register" class="btn btn-info<c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>" title="등록">등록</a><!-- 등록 -->
 		              	</div>

 					</div>
 				</div>
</form:form>
 			</div>

		<div class="row mb-2">
			 <div class="col-4 pt-1">
			 	조회결과 <b><fmt:formatNumber value="${commandMap.pagingInfo.totalRecordCount}"/></b> 건
			 </div>
			 <div class="col-8" style="text-align:right">
			</div>
       </div>

       <div class="row">
          <div class="col-12">
            <div class="card">
              <!-- /.card-header -->

              <div class="card-body table-responsive p-0">
	               <table class="table table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 9%;">
						<col style="width: 12%;">
						<col style="width: 12%">
						<col style="width: 15%;">
						<col style="width: *">
						<col style="width: 20%;">
						<col style="width: 5%;">

					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th>번호</th><!-- 번호 -->
	                      	<th>기관명</th>
	                      	<th>등록일</th>
	                      	<th>강사명</th>
							<th>핸드폰</th>
							<th>최근 교육강좌</th>
							<th>최근 교육기간</th>
							<th>사용여부</th>
	                    </tr>
	                  </thead>
	                  <tbody>
						<c:if test="${fn:length(list) == 0}">
							<tr>
								<td colspan="7">데이타가 없습니다</td>
							</tr>
						</c:if>
						<c:forEach items="${list}" var="resultInfo" varStatus="status">
		                   <tr>
		                      	<td><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
		                      	<td><c:out value="${resultInfo.orgNm}"/></td>
								<td><fmt:formatDate value="${resultInfo.regdate}" pattern="yyyy.MM.dd HH:mm"/></td>
								<td><a href="./${resultInfo.instrctrNo}<c:if test="${fn:indexOf(commandMap.query,'&') ==0}">?mode=view</c:if><c:out value="${commandMap.query}"/>"><c:out value='${resultInfo.nameKor}'/></a></td>
								<td>${resultInfo.hpNo}</td>
								<td><c:out value="${resultInfo.edcPrgmnm}"/></td>
								<td>
									<c:if test="${!empty resultInfo.edcPrgmnm }">
										<hisco:DateUtil datestr="${resultInfo.edcSdate}" format="yyyy.MM.dd"/> ~ <hisco:DateUtil datestr="${resultInfo.edcEdate}" format="yyyy.MM.dd"/>
									</c:if>
								</td>
								<td><c:out value="${resultInfo.useyn}"/></td>
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