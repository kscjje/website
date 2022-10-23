<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : privateList.jsp
  * @Description : 관리자 작업  히스토리  목록 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.17    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.17
  *  @version 1.0
  *  @see
  *
  */
%>
<style>
.sort {font-size:16px; cursor:pointer}
.sort.active {color:red}
</style>
<script>
	$(document).ready(function(){
		$( ".datepicker" ).datepicker({
			"dateFormat":'yy-mm-dd',
			'monthNames':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			'monthNamesShort':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			'changeMonth' : true,
			'changeYear':true
		});

		var sort_column = $('#searchOrder').val();
		var sort_order = $('#searchOrderDir').val();

		$('.sort').click(function(){
			var id = $(this).attr("id");
			if(id == sort_column){
				if(sort_order == "" || sort_order == "desc"){
					sort_order = "asc";
				}else{
					sort_order = "desc";
				}
			}else{
				sort_order = "asc";
			}
			$('#searchOrder').val(id);
			$('#searchOrderDir').val(sort_order);
			$('#searchForm').submit();

		});

		//if(sort_column == "") sort_column = "createdate";
		//if(sort_order == "") sort_order = "desc";
		$.each($('.sort') , function(){
			if($(this).attr("id") == sort_column){
				$(this).addClass("active");
				if(sort_order == "asc"){
					$(this).text("▲");
				}
			}
		});

		$('#pageSize').change(function(){
			$('#searchVO').submit();
		})
	});
</script>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>개인정보 접속이력 현황</h1>
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
<form name="searchForm" id="searchForm" method="get" >
<input type="hidden" name="searchOrder" id="searchOrder" value="<c:out value="${param.searchOrder }"/>"/>
<input type="hidden" name="searchOrderDir" id="searchOrderDir" value="<c:out value="${param.searchOrderDir }"/>"/>
		 	 	 <div class="card-body">
		 	 	 	<div class="row">
		 	 	 		<div class="col-2">
	              			<select path="searchDate" title="날짜"  class="form-control">
								<option value="edcdate"  >사용일시</option>
							</select>
						</div>
						<div class="col-4">
								<div class="input-group date " style="float:left;width:45%"  data-target-input="nearest" >
			                        <input class="form-control datepicker" name="searchStartDts" autocomplete="off" value="<c:out value="${param.searchStartDts }"/>"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>

								<div style="width:10%;float:left;text-align:center">~</div>
								<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest">
			                        <input class="form-control datepicker" name="searchEndDts" autocomplete="off" value="<c:out value="${param.searchEndDts }"/>"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>
						</div>
		              	<div class="col-2">
		              			<select name="searchCondition" title="검색범위"  class="form-control">
									<option value="3"  <c:if test="${searchVO.searchCondition == '3'}">selected="selected"</c:if> >관리자 이름</option>
									<option value="2"  <c:if test="${searchVO.searchCondition == '2'}">selected="selected"</c:if> >관리자 ID</option>
								</select>
						</div>

						<div class="col-2">
								<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" >
						</div>
						<div class="col-2">
							<button type="submit" class="btn btn-secondary">조회</button>
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
						<col style="width: 5%;">
						<col style="width: 12%;">
						<col style="width: 10%">
						<col style="width: 10%;">
						<col style="width: 10%">
						<col style="width: 15%">
						<col style="width: *;">
						<col style="width: 9%;">
					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th>번호</th><!-- 번호 -->
							<th>사용일시  <span id="regdate" class="sort">▼</span></th>
							<th>접속메뉴명</th>
							<th>관리자 아이디</th>
							<th>관리자 이름</th>
							<th>사용자 IP</th>
							<th>대상자정보</th>
							<th>형식</th>
	                    </tr>
	                  </thead>
	                  <tbody>
<c:if test="${fn:length(list) == 0}">
					<tr>
						<td colspan="9">데이타가 없습니다</td>
					</tr>
</c:if>
<c:forEach items="${list}" var="resultInfo" varStatus="status">
	                    <tr>
	                      	<td><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
	                      	<td><fmt:formatDate value="${resultInfo.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
	                      	<td>
	                      		<c:out value='${resultInfo.menuNm}'/>
	                      	</td>
							<td><c:out value='${resultInfo.conectId}'/></td>
							<td><c:out value='${resultInfo.userName}'/></td>
							<td><c:out value='${resultInfo.conectIp}'/></td>
							<td style="word-break:break-all;"><c:out value='${resultInfo.inqryMemberinfo}'/></td>
							<td>
								<c:choose>
									<c:when test="${resultInfo.methodGubun eq 'R'}">조회</c:when>
									<c:when test="${resultInfo.methodGubun eq 'D'}">삭제</c:when>
									<c:when test="${resultInfo.methodGubun eq 'U'}">수정</c:when>
									<c:when test="${resultInfo.methodGubun eq 'C'}">등록</c:when>
									<c:when test="${resultInfo.methodGubun eq 'L'}">로그인</c:when>
								</c:choose>
							</td>
	                    </tr>
</c:forEach>

	                  </tbody>
	                </table>
              </div>
              <!-- /.card-body -->
              <div class="card-footer p-0">
              	<div class="pagination_2">
						<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./privateList?pageIndex="/>
				</div>
            </div>


            <!-- /.card -->
          </div>
        </div>
      </div>
     </div>
</section>


</body>