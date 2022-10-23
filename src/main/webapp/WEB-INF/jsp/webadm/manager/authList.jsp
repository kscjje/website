<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hisco.cmm.util.Config"%>
<%
 /**
  * @Class Name : authList.jsp
  * @Description : 권한 목록  JSP
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
<script type="text/javascript" src="<c:url value="../validator"/>"></script>
<validator:javascript formName="roleManage" staticJavascript="false" xhtml="true" cdata="false"/>
<script>
	function fn_load_data(roleCode) {
		$('#modal-default').find(".modal-body").html("");
		$.ajax({
		      url: './authDetailAjax',
		      data : {'roleCode':roleCode,'MODE':'UPDATE'},
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
	function fn_auth_add() {
		$('#modal-default').find(".modal-body").html("");
		$.ajax({
		      url: './authDetailAjax',
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
	function fn_checked_delete() {
		var cnt = $('input[name=checkId]:checked').length;

		if (cnt < 1) {
			alert("삭제할 항목을 선택해 주세요.");
		} else {
			if (confirm("삭제하시겠습니까?")) {
				$.ajax({
				      url: './authDelete',
				      data :$('#listForm').serialize(),
				      type : "POST",
				      dataType : "json",
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
	}
	$(document).ready(function() {
		$('#allcheck').click(function() {
			$('input[name=checkId]').prop("checked" , $(this).prop("checked"));
		});
	});
</script>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>리소스 관리</h1>
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
<form name="searchForm" action="./authList" method="get" >
		 	 	 <div class="card-body">
		 	 	 	<div class="row">
		              	<div class="col-3">
								<select name="searchCondition" title="검색영역" class="form-control">
									<option value="1"  <c:if test="${roleManageVO.searchCondition == '1'}">selected="selected"</c:if> >업무명</option><!-- 코드ID -->
									<option value="2"  <c:if test="${roleManageVO.searchCondition == '2'}">selected="selected"</c:if> >패턴</option><!-- 코드ID명 -->
								</select>
						</div>
						<div class="col-4">
							<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어" value='<c:out value="${roleManageVO.searchKeyword}"/>'  maxlength="155" >
						</div>
						<div class="col-5">
							<button type="submit" class="btn btn-secondary">조회</button>
							<c:choose>
									<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
										<button type="button" class="btn btn-info" onclick="fn_auth_add('')" data-toggle="modal" data-target="#modal-default">등록</button>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn-info disabled">등록</button>
									</c:otherwise>
							</c:choose>
							<c:choose>
									<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
										<button type="button" class="btn btn-outline-info" onclick="fn_checked_delete()" >선택 삭제</button>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn-outline-info disabled">선택 삭제</button>
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
<form name="listForm" id="listForm" action="./authList" method="get" >
              <div class="card-body table-responsive p-0">
	               <table class="table table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 5%;">
						<col style="width: 9%;">
						<col style="width: 13%;">
						<col style="width: 6%;">
						<col style="width: 6%;">
						<col style="width: *;">
						<col style="width: 10%;">
						<col style="width: 10%;">
					</colgroup>
	                  <thead>
	                    <tr>
	                    	<th><input type="checkbox" name="allcheck" id="allcheck" class="check2" title="선택"></th>
	                      	<th>번호</th><!-- 번호 -->
							<th>업무명</th>
							<th>타입</th>
							<th>순서</th>
							<th>패턴</th>
							<th>설명</th>
							<th>등록일</th>
							<th>관리</th>
	                    </tr>
	                  </thead>
	                  <tbody>
<c:if test="${fn:length(list) == 0}">
					<tr>
						<td colspan="9">데이타가 없습니다</td>
					</tr>
</c:if>
<c:forEach items="${list}" var="role" varStatus="status">
	                    <tr>
							<td><input type="checkbox" name="checkId" class="check2" title="선택" value="<c:out value="${role.roleCode}"/>"></td>
							<td><c:out value="${(roleManageVO.pageIndex-1) * roleManageVO.pageSize + status.count}"/></td>
							<td class="left"><c:out value="${role.roleNm}"/></td>
							<td><c:out value="${role.roleTyp}"/></td>
							<td><c:out value="${role.roleSort}"/></td>
							<td><c:out value="${role.rolePtn}"/></td>
							<td class="left"><c:out value="${role.roleDc}"/></td>
							<td><c:out value="${fn:substring(role.roleCreatDe,0,10)}"/></td>
							<td>
								<c:choose>
									<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
	                             		<button type="button" class="btn bg-primary btn-xs" onclick="fn_load_data('<c:out value="${role.roleCode}"/>' )" data-toggle="modal" data-target="#modal-default"> 수정</button>
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
</form>
              <!-- /.card-body -->
              <div class="card-footer p-0">
              	<div class="pagination_2">
					<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./authList?pageIndex="/>
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
              <h4 class="modal-title">리소스 등록</h4>
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



<!-- ****************************************************************************************************************************** -->

		</div>
			<%@ include file="/WEB-INF/templates/webadm/base/footer.jsp"%>
	  	<div class="control-sidebar-bg"></div>

	</div>

</body>

</html>