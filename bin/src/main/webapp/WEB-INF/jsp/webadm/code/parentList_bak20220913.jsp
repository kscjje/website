<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name :  parentList.jsp
  * @Description : 공통코드그룹목록 JSP
  * @Modification Information
  * @
  * @  수정일           수정자              수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.10.27  이윤호            최초 생성
  *
  *  @author 이윤호
  *  @since 2021.10.27
  *  @version 1.0
  *  @see
  *
  */
%>
<head>

<script type="text/javascript" src="../validator"></script>
<script>
	function fn_data_load(codeId) {
		$('#modal-default').find(".modal-body").html("");
		$('#modal-default').find(".modal-title").html("공통코드그룹 수정");
		$.ajax({
		      url: './parentDetailAjax',
		      data : {'grpCd':codeId,'MODE':'UPDATE'},
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
		$('#modal-default').find(".modal-title").html("공통코드그룹 등록");
		$.ajax({
		      url: './parentDetailAjax',
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
		var form = document.codeVO;

		if (!validateCodeVO(form)) {
			return ;
		} else {
			var msg = "등록하시겠습니까?";
			if (form.MODE.value == "UPDATE") {
				msg = "수정하시겠습니까?";
			}
			if (confirm(msg)) {
				$.ajax({
				      url: './parentSave.json',
				      data : $('#codeVO').serialize(),
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

	// 공통코드 액셀 다운로드
	function fn_code_excel(){
		$.ajax({
		      url: './parentListAjax',
		      data : $('#CcmCodeForm').serialize(),
		      type : "GET",
		      dataType : "html",
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
		         		$('#modal-default').find(".modal-body").html(data);
		         		tableExportExcel("excelTable", "그룹코드목록");
		    	  	}
		      }
		    });


	}


	function fn_delete(grpCd) {
		var $form = $('<form/>');
		$('<input type="hidden" name="grpCd">').val(grpCd).appendTo($form);

		var msg = "삭제된 데이터는 복구되지 않습니다.\n정말 삭제하시겠습니까?";
		if (confirm(msg)) {
			$.ajax({
			      url: './parentDelete.json',
			      data : $form.serialize(),
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


	function fnSort(grpCd) {

		$('form[name=CcmCodeForm]').find('input[name=searchOrder]').val(grpCd);
		if('<c:out value="${searchVO.searchOrderDir}"/>'=='ASC'){
			$('form[name=CcmCodeForm]').find('input[name=searchOrderDir]').val("DESC");
		} else {
			$('form[name=CcmCodeForm]').find('input[name=searchOrderDir]').val("ASC");
		}
		$('form[name=CcmCodeForm]').submit();
	}




</script>

</head>

<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>공통코드 그룹 관리</h1>
          </div>
          <div class="col-sm-6 text-red">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>


<!-- Main content -->
<section class="content">
      <div class="container-fluid">
       	<div class="card">
<form name="CcmCodeForm" id="CcmCodeForm" action="./parentList" method="get" >
	<input type="hidden" name="searchOrder" value="<c:out value="${searchVO.searchOrder}"/>"/>
	<input type="hidden" name="searchOrderDir" value="<c:out value="${searchVO.searchOrderDir}"/>"/>
		 	 	 <div class="card-body">
		 	 	 	<div class="row">
		              	<div class="col-3">
								<select name="searchCondition" title="검색영역" class="form-control">
									<option value="2"  <c:if test="${searchVO.searchCondition == '2'}">selected="selected"</c:if> >그룹명</option><!-- 코드ID명 -->
									<option value="1"  <c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if> >그룹코드</option><!-- 코드ID -->
								</select>
						</div>
						<div class="col-3">
							<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" >
						</div>
		              	<div class="col-2">
								<select name="orgGrpcdyn" title="코드그룹구분" class="form-control">
									<option value=""  <c:if test="${searchVO.orgGrpcdyn == ''}">selected="selected"</c:if> >전체</option><!-- 코드ID -->
									<option value="N"  <c:if test="${searchVO.orgGrpcdyn == 'N'}">selected="selected"</c:if> >공통코드</option><!-- 코드ID명 -->
									<option value="Y"  <c:if test="${searchVO.orgGrpcdyn == 'Y'}">selected="selected"</c:if> >기관별코드</option><!-- 코드ID명 -->
								</select>
						</div>
						<div class="col-3">
							<button type="submit" class="btn btn-secondary">조회</button>
		              	</div>
 					</div>
 				</div>
 </form>
 			</div>

			<div class="row" style="margin-bottom:10px;">

				<div class="col-12 text-right">
					<c:choose>
						<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
							<button type="button" class="btn btn-info" onclick="fn_code_add('')" data-toggle="modal" data-target="#modal-default">그룹코드등록</button>
							<button type="button" class="btn btn-info" onclick="fn_code_excel()">엑셀다운로드</button>
						</c:when>
						<c:otherwise>
							<button type="button" class="btn btn-info disabled">그룹코드등록</button>
							<button type="button" class="btn btn-info disabled">엑셀다운로드</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

       <div class="row">
          <div class="col-12">
            <div class="card card-primary card-outline">
              <!-- /.card-header -->

              <div class="card-body table-responsive p-0">
	               <table class="table table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 9%;">
						<col style="width: 30%;">
						<col style="width: *;">
						<col style="width: 10%;">
						<col style="width: 10%;">
						<col style="width: 10%;">
						<col style="width: 10%;">
					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th class="text-center">번호</th><!-- 번호 -->
							<th class=" board_th_link">
								<a href="javascript:" onclick="fnSort('GRP_CD')">그룹코드
									<c:if test="${searchVO.searchOrder eq 'GRP_CD'}">
										<c:choose>
											<c:when test="${searchVO.searchOrderDir eq 'ASC'}">
												<i class="fas fa-sort-amount-down-alt"></i>
			                         		</c:when>
			                         		<c:otherwise>
												<i class="fas fa-sort-amount-down"></i>
			                         		</c:otherwise>
			                         	</c:choose>
									</c:if>
								</a>
							</th>
							<th class="">
								<a href="javascript:" onclick="fnSort('GRP_NM')">그룹명
									<c:if test="${searchVO.searchOrder eq 'GRP_NM'}">
										<c:choose>
											<c:when test="${searchVO.searchOrderDir eq 'ASC'}">
												<i class="fas fa-sort-amount-down-alt"></i>
			                         		</c:when>
			                         		<c:otherwise>
												<i class="fas fa-sort-amount-down"></i>
			                         		</c:otherwise>
			                         	</c:choose>
									</c:if>
								</a>
							</th>
							<th class="text-center">기관별 코드관리</th>
							<th class="text-center">수정가능</th>
							<th class="text-center">삭제가능</th>
							<th class="text-center">삭제</th>
	                    </tr>
	                  </thead>
	                  <tbody>
<c:if test="${fn:length(resultList) == 0}">
					<tr>
						<td colspan="6"  class="text-center">데이타가 없습니다</td>
					</tr>
</c:if>

<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
	                    <tr>
							<td  class="text-center"><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>
							<td>
								<c:choose>
									<c:when test="${resultInfo.updYn eq 'Y' && commandMap.selectedMenu.updYn eq 'Y' }">
										<a href="javascript:" onclick="fn_data_load('<c:out value="${resultInfo.grpCd}"/>' )" data-toggle="modal" data-target="#modal-default"><c:out value='${fn:substring(resultInfo.grpCd, 0, 40)}'/></a>
	                         		</c:when>
	                         		<c:otherwise>
										<c:out value='${fn:substring(resultInfo.grpCd, 0, 40)}'/>
	                         		</c:otherwise>
	                         	</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${resultInfo.updYn eq 'Y' && commandMap.selectedMenu.updYn eq 'Y' }">
										<a href="javascript:" onclick="fn_data_load('<c:out value="${resultInfo.grpCd}"/>' )" data-toggle="modal" data-target="#modal-default"><c:out value='${fn:substring(resultInfo.grpNm, 0, 40)}'/></a>
	                         		</c:when>
	                         		<c:otherwise>
										<c:out value='${fn:substring(resultInfo.grpNm, 0, 40)}'/>
	                         		</c:otherwise>
	                         	</c:choose>
							</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${resultInfo.orgGrpcdyn eq 'Y'}">
										기관별관리
	                         		</c:when>
	                         		<c:otherwise>
										공통관리
	                         		</c:otherwise>
	                         	</c:choose>
							</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${resultInfo.updYn eq 'Y'}">
										O
	                         		</c:when>
	                         		<c:otherwise>
										X
	                         		</c:otherwise>
	                         	</c:choose>
							</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${resultInfo.delYn eq 'Y'}">
										O
	                         		</c:when>
	                         		<c:otherwise>
										X
	                         		</c:otherwise>
	                         	</c:choose>
							</td>
							<td class="text-center">
								<c:choose>
									<c:when test="${resultInfo.delYn eq 'Y'  && commandMap.selectedMenu.delYn eq 'Y'}">
										<button type="button" class="btn btn-danger btn-xs" onclick="fn_delete('<c:out value='${resultInfo.grpCd}'/>')">삭제</button>
	                         		</c:when>
	                         		<c:otherwise>

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
					<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./parentList?pageIndex="/>
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