<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : tweduLectList.jsp
	 * @Description : 마울배움터 교육현황 리스트 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.11.05     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2021.11.19
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<head>
<script type="text/javascript">
var fn_reset = function() {
	jQuery('#searchVO').find("input[type=text],input[type=hidden]").val('');
}

jQuery(document).ready(function(){
	jQuery( ".datepicker" ).datepicker({
		"dateFormat":'yy-mm-dd',
		'monthNames':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'monthNamesShort':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'changeMonth' : true,
		'changeYear':true
	});
});
</script>
</head>
<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>마을배움터 교육현황</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>
<!-- Main content -->
<section class="content">
      <div class="container-fluid">
      <form:form commandName="searchVO" name="searchVO"  method="get" >
       	<div class="card card-primary card-outline">
		 	 	<div class="card-body search-body">
		 	 		<div class="row">
		              	<div class="col-2">
	              			<form:select path="searchDate" title="날짜"  class="form-control">
	              				<form:option value=""  >기간(선택)</form:option>
	              				<form:option value="regdate">신청기간</form:option><%-- 개설신청일(등록일) --%>
								<form:option value="edcdate">수업기간</form:option><%-- 교육시작일 --%>
							</form:select>
						</div>
						<div class="col-4">
								<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest" >
			                        <form:input cssClass="form-control datepicker" path="searchStartDts" autocomplete="off"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>

								<div style="width:10%;float:left;text-align:center">~</div>
								<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest">
			                        <form:input cssClass="form-control datepicker" path="searchEndDts" autocomplete="off"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>
						</div>
						<div class="col-2">
	              			<form:select path="searchCondition" title="검색"  cssClass="form-control">
								<form:option value="1">개설프로그램명</form:option>
								<form:option value="2">개설자명</form:option>
							</form:select>
						</div>
						<div class="col-4">
							<form:input cssClass="form-control" path="searchKeyword" placeHolder="검색어를 입력하세요"  maxlength="155" />
						</div>
		 	 		</div>
 				</div>
 			</div>
		    <div class="row text-right mb-3">
		    	<div class="col-4"></div>
		    	<div class="col-2">
		    		<button type="submit" class="btn btn-primary btn-block">조회</button>
		    	</div>
		    	<div class="col-2">
		    		<button type="button" onclick="fn_reset()" class="btn btn-secondary btn-block">초기화</button>
		    	</div>
		    	<div class="col-4"></div>
		    </div>
 			</form:form>

       <div class="row">
          <div class="col-12">
            <div class="card">
              <!-- /.card-header -->

              <div class="card-body table-responsive p-0">
	               <table class="table table-bordered table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 5%;">
						<col style="width: 15%">
						<col style="width: 10%">
						<col style="width: 15%">
						<col style="width: *">
						<col style="width: 15%">
						<col style="width: 15%">
					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th>번호</th>
	                      	<th>개설 신청일</th>
	                      	<th>개설 회원명</th>
	                      	<th>휴대폰 번호</th>
	                      	<th>개설 프로그램명</th>
	                      	<th>수업시작일</th>
							<th>수업 종료일</th>
	                    </tr>
	                  </thead>
	                  <tbody>
						<c:if test="${fn:length(list) == 0}">
							<tr>
								<td colspan="7">데이타가 없습니다</td>
							</tr>
						</c:if>
						<c:forEach items="${list}" var="item" varStatus="status">
							<tr>
		                      	<td>
		                      		<c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/>
		                      	</td>
		                      	<td><c:out value='${item.edcProgmDate}'/></td>
		                      	<td><c:out value='${item.memNm}'/></td>
		                      	<td><hisco:HpPrint hp="${item.memHp}"/></td>
								<td>
									<a href="./detail?edcPrgmid=${item.edcPrgmid}"><c:out value='${item.edcPrgmnm}'/></a>
								</td>
								<td><c:out value='${item.edcSdate}'/></td>
								<td><c:out value='${item.edcEdate}'/></td>
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