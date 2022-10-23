<%
 /**
  * @Class Name : menuAuthLinnkPop.jsp
  * @Description : 메뉴와 롤 권한 페이지 연결 설정 JSP
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hisco.cmm.util.Config"%>
<script>
	function fn_checked_save() {
		var cnt = $('input[name=checkId]:checked').length;

		if (confirm("저장 하시겠습니까?")) {
			$('#roleMenuNo').val("<c:out value='${menuManageVO.menuNo}' />");
			$.ajax({
			      url: './menuAuthLinkSave',
			      data :$('#listForm').serialize(),
			      type : "POST",
			      dataType : "json",
			      beforeSend : function(xhr) {
	                  xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
	              },
			      success: function(data) {
			    	  if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        } else {
				        	 alert(data.result.msg);

					         opener.window.location.reload();
					         self.close();
				         }
			      }
			    });
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
            <h1>메뉴-업무 연결</h1>
          </div>
          <div class="col-sm-6 text-right">
          	<button type="button" class="btn btn-info" onclick="fn_checked_save()" >선택업무 등록</button>

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
<form name="listForm" id="listForm" >
<input type="hidden" name="roleMenuNo" id="roleMenuNo"/>
              <div class="card-body table-responsive p-0">
	               <table class="table table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 5%;">
						<col style="width: 5%;">
						<col style="width: 30%;">
						<col style="width: *;">

					</colgroup>
	                  <thead>
	                    <tr>
	                    	<th></th>
	                      	<th>번호</th><!-- 번호 -->
							<th>업무명</th>
							<th>패턴</th>
	                    </tr>
	                  </thead>
	                  <tbody>
<c:if test="${fn:length(authorRoleList) == 0}">
					<tr>
						<td colspan="4">데이타가 없습니다</td>
					</tr>
</c:if>
<c:forEach items="${authorRoleList}" var="item" varStatus="status">
	                    <tr>
							<td><input type="checkbox" name="checkId" class="check2" title="선택" value="<c:out value="${item.roleCode}"/>" <c:if test="${item.regYn eq 'Y' }">checked</c:if> /></td>
							<td><c:out value="${(roleManageVO.pageIndex-1) * roleManageVO.pageSize + status.count}"/></td>
							<td class="left"><c:out value="${item.roleNm}"/></td>
							<td><c:out value="${item.rolePtn}"/></td>
						</tr>
</c:forEach>
	                  </tbody>
	                </table>
              </div>
</form>
              <!-- /.card-body -->

            <!-- /.card -->
          </div>
        </div>
      </div>
     </div>
</section>