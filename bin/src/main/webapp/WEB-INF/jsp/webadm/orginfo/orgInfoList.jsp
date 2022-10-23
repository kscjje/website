<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : orgInfoList.jsp
  * @Description : 이용기관 정보 관리JSP
  * @Modification Information
  * @
  * @  수정일              수정자            수정내용
  * @ -------       ------    ---------------------------
  * @ 2021.10.26    진수진           최초 생성
  *
  *  @author 진수진
  *  @since 2021.10.26
  *  @version 1.0
  *  @see
  *
  */
%>
<body>
<script>
var orgTypeList ; //기관유형
	$(document).ready(function(){
		$.ajax({
		      url: '../code/SM_ORG_MTYPE/list.json',
		      type : "GET",
		      cache : false,
		      async : true,
		      success: function(data) {
		    	  //목록을 담는다
		    	  orgTypeList = data.list;
		    	  fn_print_orgtype( '${searchVO.orgLtype}' , '${searchVO.orgMtype}');
		      },
		      error : function(res){
		    	  	__ajaxErrorCallback__(res, "기관유형 가져오기 오류\n" + res.responseText);
		      }

		});

		$('select[name=orgLtype]').change(function(){
			fn_print_orgtype($(this).val() , '');
		});
	});

	function fn_print_orgtype(parentCd , defaultVal){
		var subObj = $("select[name=orgMtype]");

		subObj.html('');
		subObj.append("<option value=''>기관유형_중(전체)</option>");

		var cnt=0;
		for(var i = 0 ; i<orgTypeList.length ; i++){
			var item = orgTypeList[i];
			if(item.item1 == parentCd){
				if(subObj.html() == ""){
					subObj.append("<option value=''>선택</option>");
				}

				var selected = "";
				if(item.cd == defaultVal) selected = " selected";

				subObj.append("<option value='"+item.cd+"'"+selected+">"+item.cdNm+"</option>");
				cnt++;
			}

		}
		if(cnt < 1){
			subObj.append("<option value=''>없음</option>");
		}
	}
</script>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>기관 정보 관리</h1>
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
<form name="searchForm"  method="get" >
		 	 	<div class="card-body">
		 	 	 	<div class="row">
		              	<div class="col-2">
								<select name="orgLtype" class="form-control">
		                    		<option value="">기관유형_대(전체)</option>
		                    		<c:forEach items="${typeList}" var="item" varStatus="status">
		                    			<option value="${item.cd }" <c:if test="${searchVO.orgLtype eq item.cd }">selected</c:if>>${item.cdNm }</option>
		                    		</c:forEach>
		                    	</select>
	                    </div>
	                    <div class="col-2">
			                    <select name="orgMtype" class="form-control">
			                    	<option value="">기관유형_중(전체)</option>
		                    	</select>
						</div>

		              	<div class="col-2">
	              			<select name="searchCondition" title="기관명"  class="form-control">
								<option value="orgNm"  <c:if test="${searchVO.searchCondition eq 'orgNm'}">selected="selected"</c:if> >기관명</option>
							</select>
						</div>
						<div class="col-3">
								<input class="form-control" name="searchKeyword" type="text" value="<c:out value="${searchVO.searchKeyword}"/>" size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" placeHolder="검색어 입력">
						</div>
						<div class="col-3">
							<button type="submit" class="btn btn-secondary">조회</button>
							<a href="./orgInfoRegist" class="btn btn-info<c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>" title="등록버튼">등록</a><!-- 등록 -->
		              	</div>
 					</div>
 				</div>
</form>
 			</div>

		<div style="text-align:right">※ 정렬순서 오름차순으로 정렬됩니다. </div>
       <div class="row">
          <div class="col-12">
            <div class="card">
              <!-- /.card-header -->

              <div class="card-body table-responsive p-0">

	               <table class="table table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 9%;">
						<col style="width: 10%;">
						<col style="width: *">
						<col style="width: 10%;">
						<col style="width: 10%;">
						<col style="width: 12%;">
						<col style="width: 12%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th>번호</th><!-- 번호 -->
	                      	<th>지역</th>
							<th>기관명</th>
							<th>기관분류</th>
							<th>기관유형(대)</th>
							<th>기관유형(중)</th>

							<th>등록일</th>
							<th>노출여부</th>
							<th>정렬순서</th>
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
								<td><c:out value='${resultInfo.areaNm}'/></td>
								<td><a href="./orgInfoDetail?orgNo=${resultInfo.orgNo}<c:out value="${commandMap.query}"/>"><c:out value='${resultInfo.orgNm}'/></a></td>
								<td>
									${resultInfo.orgKindNm}
								</td>
								<td>
									${resultInfo.orgLtypeNm}
								</td>
								<td>
									<c:choose><c:when test="${empty resultInfo.orgMtypeNm}">-</c:when><c:otherwise>${resultInfo.orgMtypeNm}</c:otherwise></c:choose>
								</td>
								<td><fmt:formatDate value="${resultInfo.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td>
									${resultInfo.orgCntsactiveyn}
								</td>
								<td>
									${resultInfo.sortOrder}
								</td>
		                    </tr>
						</c:forEach>
	                  </tbody>
	                </table>
              </div>
              <!-- /.card-body -->
              <div class="card-footer p-0">
              	<div class="pagination_2">
					<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./orgInfoList?pageIndex="/>
				</div>
            </div>

            <!-- /.card -->
          </div>
        </div>
      </div>
     </div>
</section>

</body>