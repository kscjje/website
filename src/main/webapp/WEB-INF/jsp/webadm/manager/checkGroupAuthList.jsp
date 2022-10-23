<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ page import="com.hisco.cmm.util.Config"%>

<%
 /**
  * @Class Name : checkGroupAuthList.jsp
  * @Description : 그룹별  권한 설정 JSP
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

<%@ include file="/WEB-INF/templates/webadm/base/header.jsp"%>

<script>
	function fn_checked_save() {
		var cnt = $('input[name=checkId]:checked').length;
		if ($('#searchKeyword').val() == "") {
			alert("그룹을 먼저 선택해 주시기 바랍니다.");
			return;
		}
		if (confirm("저장 하시겠습니까?")) {
			$('#authorCode').val($('#searchKeyword').val());
			$.ajax({
			      url: './checkGroupAuthSave.json',
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
					         window.location.reload();
				         }
			      }
			    });
		}

	}
	$(document).ready(function() {
		$('#allcheck').click(function() {
			$('input[name=checkId]').prop("checked" , $(this).prop("checked"));
		});

		$('#searchKeyword').change(function() {
			$('#searchVO').submit();
		});
	});
</script>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>리소스별 권한 관리</h1>
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
<form:form commandName="searchVO" action="./checkGroupAuthList" method="get" >
		 	 	 <div class="card-body">
		 	 	 	<div class="row">
		              	<div class="col-6">
								<form:select path="searchKeyword" class="form-control">
									<form:option value="">::그룹 선택::</form:option>
									<form:options items="${authorList}" itemValue="authorCode" itemLabel="authorNm"/>
								</form:select>
						</div>
						<div class="col-6">
							<button type="submit" class="btn btn-secondary">조회</button>
							<button type="button" class="btn btn-info" onclick="fn_checked_save()" >저장</button>
		              	</div>
 					</div>
 				</div>
 </form:form>
 			</div>

       <div class="row">
          <div class="col-12">
            <div class="card card-primary card-outline">
              <!-- /.card-header -->
<form name="listForm" id="listForm" action="./authList" method="get" >
<input type="hidden" name="authorCode" id="authorCode"/>
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
	                    </tr>
	                  </thead>
	                  <tbody>
<c:if test="${fn:length(authorRoleList) == 0}">
					<tr>
						<td colspan="9">데이타가 없습니다</td>
					</tr>
</c:if>
<c:forEach items="${authorRoleList}" var="item" varStatus="status">
	                    <tr>
							<td><input type="checkbox" name="checkId" class="check2" title="선택" value="<c:out value="${item.roleCode}"/>" <c:if test="${item.regYn eq 'Y' }">checked</c:if> /></td>
							<td><c:out value="${(roleManageVO.pageIndex-1) * roleManageVO.pageSize + status.count}"/></td>
							<td class="left"><c:out value="${item.roleNm}"/></td>
							<td><c:out value="${item.roleTyp}"/></td>
							<td><c:out value="${item.roleSort}"/></td>
							<td><c:out value="${item.rolePtn}"/></td>
							<td class="left"><c:out value="${item.roleDc}"/></td>
							<td><c:out value="${fn:substring(item.creatDt,0,10)}"/>
							</td>
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



<!-- ****************************************************************************************************************************** -->

		</div>
			<%@ include file="/WEB-INF/templates/webadm/base/footer.jsp"%>
	  	<div class="control-sidebar-bg"></div>

	</div>

</body>

</html>