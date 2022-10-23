<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ page import="com.hisco.cmm.util.Config"%>

<%
 /**
  * @Class Name : groupList.jsp
  * @Description : 권한 목록 JSP
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
<script>
	function fn_detail_load(roleCd){
		$('#listDiv').find("tbody tr").css("background-color" , "#fff").css("color" , "#666");

		$.ajax({
		      url: './mngGroupDetailAjax.json',
		      data : {'authorCode' :  roleCd},
		      type : "POST",
		      dataType : "json",
		      success: function(data) {
		  			$('#ROW_'+roleCd).css("background-color" , "#adb5bd").css("color" , "white");

		  			var f = document.authorManageVO;
		  			f.authorCode.value = data.result.authorCode;
		  			f.authorNm.value = data.result.authorNm;
		  			f.authorDc.value =  data.result.authorDc;

		  			$('#addBtn').hide();
		  			$('#modifyBtn').show();

		      }
		});
	}
	function fn_group_save() {
		var form = document.authorManageVO;
		
		if(form.authorNm.value==null || form.authorNm.value=='' || form.authorNm.value=='undefined'){
			alert('그룹명을 입력해주세요.');
			return;
		}
		
		var msg = "등록하시겠습니까?";
		var method = "POST";
		var url = "./mngGroupSave.json";
		if (form.authorCode.value != "") {
			msg = "수정하시겠습니까?";
			url = "./mngGroupUpdate.json";
		}
		if (confirm(msg)) {
			$.ajax({
			      url: url,
			      data : formToJSON('#authorManageVO'),
			      contentType: "application/json",
			      type : method,
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
	function fn_group_delete(roleCd){
		fn_detail_load(roleCd);
		$('#ROW_'+roleCd).css("background-color" , "#adb5bd").css("color" , "white");

		setTimeout(function(){
			if(confirm("삭제하시겠습니끼?")){
				$.ajax({
				      url: './mngGroupDelete.json',
				      data : {'authorCode' : roleCd},
				      type : 'POST',
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
		} , 10);

	}
	function fn_reset(){
			var f = document.authorManageVO;
			f.authorCode.value ="";
			f.authorNm.value = "";
			f.authorDc.value = "";
			$('#listDiv').find("tbody tr").css("background-color" , "#fff").css("color" , "#666");

			$('#addBtn').show();
			$('#modifyBtn').hide();
	}
</script>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>관리자 그룹 관리</h1>
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
          <div class="col-sm-6">
            <div class="card card-primary card-outline">
              <!-- /.card-header -->
				<div class="card-header">
                	<h3 class="card-title">목록</h3>
              	</div>
              <div class="card-body table-responsive p-0">
	               <table id="listDiv" class="table table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 10%;">
						<col style="width: 30%;">
						<col style="width: 30%;">
						<col style="width: *">
					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th>번호</th><!-- 번호 -->
							<th>그룹명</th><!-- 코드ID -->
							<th>등록일</th><!-- 사용여부 -->
							<th>관리</th>
	                    </tr>
	                  </thead>
	                  <tbody>
<c:if test="${fn:length(list) == 0}">
					<tr>
						<td colspan="6" class="text-center">데이타가 없습니다</td>
					</tr>
</c:if>
<c:forEach items="${list}" var="item" varStatus="status">
	                    <tr id="ROW_<c:out value='${item.authorCode}'/>">
							<td><c:out value="${status.index + 1}"/></td>
							<!-- <td><c:out value='${item.parentRole}'/></td> -->
							<td onclick="fn_detail_load('<c:out value='${item.authorCode}'/>')" style="cursor:pointer"><c:out value='${item.authorNm}'/></td>
							<td><c:out value="${fn:substring(item.authorCreatDe,0,16)}"/></td>
							<td>
								<c:choose>
									<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
                             			<button type="button" class="btn bg-secondary btn-xs" onclick="fn_group_delete('<c:out value="${item.authorCode}"/>' )" > 삭제</button>
									</c:when>
									<c:otherwise><button type="button" class="btn bg-secondary btn-xs disabled"> 삭제</button></c:otherwise>
								</c:choose>
								&nbsp;
	                            <a href="<c:url value="./checkGroupMenuList"/>?authorCode=${item.authorCode}" class="btn bg-info btn-xs" ><i class="fa fa-fw fa-share"></i> 메뉴 관리</a>
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
        <div class="col-sm-6">
            <div class="card card-primary card-outline">
              <!-- /.card-header -->

              <div class="card-header">
                <h3 class="card-title">등록 / 수정</h3>
              </div>

              <div class="card-body">
<form:form commandName="authorManageVO" name="authorManageVO"  method="post" >
<input type="hidden" name="MODE" value="" />
<form:hidden path="parentRole" title="부모그룹코드" value="ROLE_ADMIN"/>
			      <div class="row">
			              <table id="modaltable" class="table table-bordered">
			              <colgroup>
			        				<col width="30%">
			        				<col width="*">
			        			</colgroup>
							<tr>
								<th><label for="authorCode">그룹코드<span class="pilsu">*</span></label></th>
								<td class="left">
								    <form:input path="authorCode" title="그룹코드" class="form-control"  readonly="true" placeHolder="자동생성" />
					   				<div><form:errors path="authorCode" cssClass="error" /></div>
								</td>
							</tr>
							<tr>
								<th><label for="menuUrl">그룹명 <span class="pilsu">*</span></label></th>
								<td class="left">
								    <form:input path="authorNm" title="그룹명" maxlength="70" class="form-control" />
					   				<div><form:errors path="authorNm" cssClass="error" /></div>
								</td>
							</tr>
							<tr>
								<th><label for="menuDc">설명</label></th>
								<td class="left">
								    <form:input path="authorDc" title="설명" maxlength="70"  class="form-control" />
					   				<div><form:errors path="authorDc" cssClass="error" /></div>
								</td>
							</tr>

			              </table>
			      </div>
 </form:form>
              </div>
              <div class="card-footer" id="addBtn">
                	<c:choose>
                		<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
                			 <button type="button" onclick="fn_group_save()"  class="btn btn-primary form-control">저장</button>
                		</c:when>
                		<c:otherwise>
                			 <button type="button" class="btn btn-primary form-control disabled">저장</button>
                		</c:otherwise>
                	</c:choose>
               </div>
               <div class="card-footer" id="modifyBtn" style="display:none">
                	<div class="row">
          				<div class="col-sm-6">
		                	<c:choose>
		                		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
		                			 <button type="button" onclick="fn_group_save()"  class="btn btn-primary form-control">수정</button>
		                		</c:when>
		                		<c:otherwise>
		                			 <button type="button" class="btn btn-primary form-control disabled">수정</button>
		                		</c:otherwise>
		                	</c:choose>
		                </div>
		                <div class="col-sm-6">
                			<button type="button" class="btn btn-secondary form-control" onclick="fn_reset()">취소</button>
                		</div>
               </div>
        </div>
      </div>
     </div>

</section>

</body>