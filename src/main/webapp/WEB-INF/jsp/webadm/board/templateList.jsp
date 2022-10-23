<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : templateList.jsp
  * @Description : 게시판 템플릿  목록
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.21    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.21
  *  @version 1.0
  *  @see
  *
  */
%>

<head>
	<script type="text/javascript" src="../validator"></script>
	<validator:javascript formName="templateInf" staticJavascript="false" xhtml="true" cdata="false"/>
	<script>
		function fn_data_load(tmplatId) {
			$('#modal-default').find(".modal-body").html("");
			$('#modal-default').find(".modal-title").html("템플릿 수정");
			$.ajax({
			      url: './templateRegistAjax',
			      data : {'tmplatId':tmplatId,'MODE':'UPDATE'},
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
		function fn_add() {
			$('#modal-default').find(".modal-body").html("");
			$('#modal-default').find(".modal-title").html("템플릿 등록");
			$.ajax({
			      url: './templateRegistAjax',
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

		function fn_save() {
			var form = document.templateInf;

			if (!validateTemplateInf(form)) {
				return ;
			} else {
				var msg = "등록하시겠습니까?";
				if (form.MODE.value == "UPDATE") {
					msg = "수정하시겠습니까?";
				}
				if (confirm(msg)) {
					$.ajax({
					      url: './templateSave.ajax',
					      data : $('#templateInf').serialize(),
					      type : "POST",
					      success: function(data) {
							if (data.result.code == "ERROR") {
								alert(data.result.msg);
							} else {
								alert(data.result.msg);
								top.location.reload();
							}
					      }
					 });

				}
			}
		}
	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>게시판 템플릿 관리</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div><!-- /.container-fluid -->
	</section>

	<!-- Main content -->
	<section class="content">
	      <div class="container-fluid">
	       	<div class="card">
	<form name="form01" action="./templateList" method="get" >
			 	 	 <div class="card-body">
			 	 	 	<div class="row">
			              	<div class="col-3">
								<select name="searchCnd" class="form-control" title="검색조건선택">
									<option value="0" <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> >템플릿명</option> <!-- 템플릿명 -->
									<option value="1" <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if> >템플릿 구분</option> <!-- 템플릿구분 -->
								</select>
							</div>
							<div class="col-3">
								<input class="form-control" name="searchWrd" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchWrd}"/>'  maxlength="155" >
							</div>
							<div class="col-6">
								<button type="submit" class="btn btn-secondary">조회</button>
								<c:choose>
									<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
										<button type="button" class="btn btn-info" onclick="fn_add('')" data-toggle="modal" data-target="#modal-default">등록</button>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn-info disabled">등록</button>
									</c:otherwise>
								</c:choose>
			              	</div>
	 					</div>
	 				</div>
	 </form>
	 			</div>

	       <div class="row">
	          <div class="col-12">
	            <div class="card card-primary card-outline">
	              <!-- /.card-header -->

	              <div class="card-body table-responsive p-0">
		               <table class="table table-hover text-nowrap">
		               	<colgroup>
							<col style="width: 9%;">
							<col style="width: 20%;">
							<col style="width: 13%;">
							<col style="width: *;">
							<col style="width: 10%;">
							<col style="width: 10%;">
							<col style="width: 13%;">

						</colgroup>
		                  <thead>
		                    <tr>
								<th scope="col">번호</th> <!-- 번호 -->
								<th scope="col">템플릿명</th> <!-- 템플릿명 -->
								<th scope="col">템플릿구분</th> <!-- 템플릿구분 -->
								<th scope="col">템플릿경로</th> <!-- 템플릿경로 -->
								<th scope="col">사용여부</th> <!-- 사용여부 -->
								<th scope="col">등록일자</th> <!-- 등록일자 -->
								<th>관리</th>
		                    </tr>
		                  </thead>
		                  <tbody>
	<c:if test="${fn:length(resultList) == 0}">
						<tr>
							<td colspan="7">데이타가 없습니다</td>
						</tr>
	</c:if>
	<c:forEach items="${resultList}" var="result" varStatus="status">
		                    <tr>
								<td><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
								<td><c:out value='${result.tmplatNm}'/></td>
								<td><c:out value='${result.tmplatSeCodeNm}'/></td>
								<td><c:out value='${result.tmplatCours}'/></td>
								<td><c:out value='${result.useAt}'/></td>
								<td><fmt:formatDate value="${result.frstRegisterPnttm}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td>
									<c:choose>
										<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
		                             		<button type="button" class="btn bg-primary btn-xs" onclick="fn_data_load('<c:out value="${result.tmplatId}"/>' )" data-toggle="modal" data-target="#modal-default"> 수정</button>
		                         		</c:when>
		                         		<c:otherwise>
		                         			<button type="button" class="btn bg-primary btn-xs disabled" > 수정</button>
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
	              	<div class="pagination_2">
						<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./templateList?pageIndex="/>
					</div>
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
	              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	              <button type="button" class="btn btn-primary" onclick="fn_save()">Save</button>
	            </div>
	          </div>
	          <!-- /.modal-content -->
	        </div>
	        <!-- /.modal-dialog -->
	      </div>
	</section>
</body>