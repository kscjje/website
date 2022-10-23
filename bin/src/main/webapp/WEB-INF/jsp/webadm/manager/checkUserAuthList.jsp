<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ page import="com.hisco.cmm.util.Config"%>

<%
 /**
  * @Class Name : checkUserAuth.jsp
  * @Description : 관리자 권한설정 목록 JSP
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

<script type="text/javascript">
	function fn_author_change(obj, userId , oldValue) {
		if (obj.value == "") {
			alert("권한그룹을 선택해 주세요.");
			obj.focus();
		} else {
			if (confirm("권한그룹을 변경 하시겠습니까?")) {
				$.ajax({
				      url: './checkUserAuthSave',
				      data : {roleCd:$(obj).val() ,userId :userId },
				      type : "POST",
				      dataType : "json",
				      beforeSend : function(xhr) {
		                  xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
		              },
				      success: function(data) {
				    	  if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        	 $(obj).val(oldValue);
					        } else {
					        	 alert(data.result.msg);
						         window.location.reload();
					         }
				      }
				    });
			}
		}
	}

	function fn_author_delete(obj, userId , oldValue) {
			if (confirm("권한그룹을 삭제 하시겠습니까?")) {
				$.ajax({
				      url: './checkUserAuthDelete',
				      data : {roleCd: oldValue  ,userId :userId },
				      type : "POST",
				      dataType : "json",
				      beforeSend : function(xhr) {
		                  xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
		              },
				      success: function(data) {
				    	  if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        	 $(obj).val(oldValue);
					        } else {
					        	 alert(data.result.msg);
						         window.location.reload();
					         }
				      }
				    });
			}
	}
</script>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>관리자별 권한그룹 설정</h1>
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

              <div class="card-body table-responsive p-0">
	               <table class="table table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 15%;">
						<col style="width: *;">
						<col style="width: 15%">
						<col style="width: 10%;">
						<col style="width: 12%;">
						<col style="width: 12%;">
						<col style="width: 12%;">
						<col style="width: 12%;">
					</colgroup>
	                  <thead>
	                    <tr>
							<th>아이디</th>
							<th>이름</th>
							<th>권한그룹 선택</th>
							<th>사용여부</th>
							<th>등록일</th>
							<th>수정일</th>
							<th>마지막로그인</th>
							<th>권한그룹 삭제</th>
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
							<td class="left"><c:out value='${resultInfo.userId}'/></td>
							<td><c:out value='${resultInfo.korName}'/></td>
							<td>
								<c:choose>
									<c:when test="${commandMap.selectedMenu.updYn ne 'Y' }"><c:out value='${resultInfo.roleNm}'/></c:when>
									<c:otherwise>
										<select name="authorCode" onchange="fn_author_change(this , '<c:out value='${resultInfo.userId}'/>', '<c:out value='${resultInfo.roleCd}'/>')">
											<option value="">::권한선택::</option>
											<c:forEach items="${authorList}" var="item" varStatus="status"><c:if test="${item.authorCode ne 'ROLE_ANONYMOUS'}"><option value="<c:out value='${item.authorCode}'/>" <c:if test="${resultInfo.roleCd eq item.authorCode }">selected</c:if>><c:out value='${item.authorNm}'/></option></c:if></c:forEach>
										</select>
									</c:otherwise>
								</c:choose>
							</td>
							<td><c:out value='${resultInfo.useYn}'/></td>
							<td><fmt:formatDate value="${resultInfo.createdate}" pattern="yyyy-MM-dd HH:mm"/></td>
							<td><fmt:formatDate value="${resultInfo.updatedate}" pattern="yyyy-MM-dd HH:mm"/></td>
							<td><fmt:formatDate value="${resultInfo.lastLogin}" pattern="yyyy-MM-dd HH:mm"/></td>
							<td>
								<c:if test="${!empty resultInfo.roleCd }">
									<c:choose>
										<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }"><button type="button" class="btn bg-primary btn-xs" onclick="fn_author_delete(this , '<c:out value='${resultInfo.userId}'/>', '<c:out value='${resultInfo.roleCd}'/>')" > 권한그룹 삭제</button></c:when>
										<c:otherwise><button type="button" class="btn bg-primary btn-xs disabled"> 권한그룹 삭제</button></c:otherwise>
									</c:choose>
								</c:if>
							</td>
	                    </tr>
</c:forEach>

	                  </tbody>
	                </table>
              </div>
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