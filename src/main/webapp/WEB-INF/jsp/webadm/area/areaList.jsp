<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : menuList.jsp
  * @Description : 메뉴 목록 화면
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.15    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.15
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
<script type="text/javascript" src="../validator"></script>
<script>
	function fn_data_load(areaCd) {
		$('#modal-default').find(".modal-body").html("");
		$('#modal-default').find(".modal-title").html("지역 수정");
		$.ajax({
		      url: './areaDetailAjax',
		      data : {'areaCd':areaCd,'MODE':'UPDATE'},
		      type : "GET",
		      dataType : "html",
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
		         		$('#modal-default').find(".modal-body").html(data);
		    	  	}
		      }
		    });
	}
	function fn_code_add() {
		$('#modal-default').find(".modal-body").html("");
		$('#modal-default').find(".modal-title").html("지역 추가");
		$.ajax({
		      url: './areaDetailAjax',
		      data : { 'MODE':'INSERT'},
		      type : "GET",
		      dataType : "html",
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
		         		$('#modal-default').find(".modal-body").html(data);
		    	  	}
		      }
		    });
	}
	function fn_area_delete(depth,areaCd) {
		var msg = "삭제하시면 복구할 수 없습니다.\n정말 삭제하시겠습니까?";
		if (confirm(msg)) {
			$.ajax({
			      url: './areaDelete.json',
			      data : {'areaCd' : areaCd},
			      type : "POST",
			      dataType : "json",
			      success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        } else {
					         top.location.reload();
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
            <h1>지역코드 관리</h1>
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
				<div class="card-header">
                <h3 class="card-title">
               			<c:choose>
							<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
								<button type="button" class="btn btn-info" onclick="fn_code_add('')" data-toggle="modal" data-target="#modal-default">지역 추가</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn btn-info disabled">지역 추가</button>
							</c:otherwise>
						</c:choose>
                </h3>
              </div>
              <div class="card-body table-responsive p-0">
	               <table class="table table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 80px;">
						<col style="width: 200px">
						<col style="width: 100px;">
						<col style="width: *">
					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th class="board_th_link">지역코드</th>
							<th class="board_th_link">지역명</th>
							<th class="board_th_link">정렬순서</th>
							<th>관리</th>
	                    </tr>
	                  </thead>
	                  <tbody>
<c:if test="${fn:length(resultList) == 0}">
					<tr>
						<td colspan="3">등록된 데이타가 없습니다</td>
					</tr>
</c:if>
<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
	                    <tr>
							<td><c:out value='${resultInfo.areaCd}'/></td>
							<td><c:out value='${resultInfo.areaNm}'/></td>
							<td><c:out value='${resultInfo.sortOrder}'/></td>
							<td>
								<c:choose>
										<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
		                             		<button type="button" class="btn bg-primary btn-flat btn-xs" onclick="fn_data_load('<c:out value="${resultInfo.areaCd}"/>' )" data-toggle="modal" data-target="#modal-default"> 수정</button>
		                         		</c:when>
		                         		<c:otherwise>
		                         			<button type="button" class="btn bg-primary btn-flat btn-xs disabled" > 수정</button>
		                         		</c:otherwise>
		                        </c:choose>
		                        <c:choose>
										<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
		                             		<button type="button" class="btn bg-secondary btn-flat btn-xs" onclick="fn_area_delete(1,'<c:out value="${resultInfo.areaCd}"/>' )"> 삭제</button>
		                         		</c:when>
		                         		<c:otherwise>
		                         			<button type="button" class="btn bg-secondary btn-flat btn-xs disabled" > 삭제</button>
		                         		</c:otherwise>
		                        </c:choose>
		                    </td>
						</tr>
</c:forEach>
	                  </tbody>
	                </table>
              </div>
              <!-- /.card-body -->
 			<div class="card-footer p-0">
				&nbsp;
            </div>

            <!-- /.card -->
          </div>
        </div>
      </div>
     </div>

<!-- 모달 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Default Modal</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body">
              <p>One fine body…</p>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" class="btn btn-primary" onclick="fn_save()">저장</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
</section>
</body>