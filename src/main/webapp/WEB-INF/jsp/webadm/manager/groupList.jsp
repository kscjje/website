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
<script type="text/javascript" src="../validator"></script>
<validator:javascript formName="authorManage" staticJavascript="false" xhtml="true" cdata="false"/>
<script>
	function fn_data_load(authorCode) {
		$('#modal-default').find(".modal-body").html("");
		$.ajax({
		      url: './groupDetailAjax',
		      data : {'authorCode':authorCode,'MODE':'UPDATE'},
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
	function fn_group_add(parentRole) {
		$('#modal-default').find(".modal-body").html("");
		$.ajax({
		      url: './groupDetailAjax',
		      data : {'parentRole':parentRole, 'MODE':'INSERT'},
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
</script>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>Security 권한 관리</h1>
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
						<col style="width: 5%;">
						<col style="width: 15%;">
						<col style="width: 15%;">
						<col style="width: 10%">
						<col style="width: 10%;">
						<col style="width: *">
					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th>번호</th><!-- 번호 -->
							<th>부모코드</th>
							<th>그룹코드</th><!-- 분류코드명 -->
							<th>그룹명</th><!-- 코드ID -->
							<th>설명</th><!-- 코드ID -->
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
<c:set var="prevAuthorCode" value="" />
<c:forEach items="${list}" var="item" varStatus="status">
	                    <tr>
							<td><c:out value="${status.index + 1}"/></td>
							<td><c:out value='${item.parentRole}'/></td>
							<td>
								<c:choose>
								 	<c:when test="${item.parentRole eq prevAuthorCode }">&nbsp;<i class="fa fa-fw  fa-caret-right"></i><c:set var="prevAuthorCode" value="${item.parentRole}" /></c:when>
								 	<c:otherwise><c:set var="prevAuthorCode" value="${item.authorCode}" /></c:otherwise>
								</c:choose>
								<c:out value='${item.authorCode}'/>
							</td>
							<td><c:out value='${item.authorNm}'/></td>
							<td><c:out value='${item.authorDc}'/></td>
							<td><c:out value="${fn:substring(item.authorCreatDe,0,10)}"/></td>
							<td>
								<c:if test="${fn:indexOf(item.authorCode , 'ROLE') == 0}">
									<c:choose>
										<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }"><button type="button" class="btn bg-primary btn-xs" onclick="fn_data_load('<c:out value="${item.authorCode}"/>' )" data-toggle="modal" data-target="#modal-default"> 수정</button></c:when>
										<c:otherwise><button type="button" class="btn bg-primary btn-xs disabled"> 수정</button></c:otherwise>
									</c:choose>
									&nbsp;
									<c:choose>
										<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
	                             			<button type="button" class="btn bg-info btn-xs" onclick="fn_group_add('<c:out value="${item.authorCode}"/>' )" data-toggle="modal" data-target="#modal-default"> 하위그룹 추가</button>
										</c:when>
										<c:otherwise><button type="button" class="btn bg-info btn-xs disabled"> 하위그룹 추가</button></c:otherwise>
									</c:choose>
								</c:if>


								<c:if test="${fn:indexOf(item.authorCode , 'ROLE') == 0}">
	                             	<a href="<c:url value="./checkGroupAuthList"/>?searchKeyword=${item.authorCode}" class="btn bg-primary btn-xs" ><i class="fa fa-fw fa-share"></i> 리소스 관리</a>
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

<!-- 모달 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">그룹 등록</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body">
              <p>One fine body…</p>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary" onclick="fn_group_save()">Save</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
</section>
</body>