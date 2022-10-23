<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hisco.cmm.util.Config"%>
	<style>
		#templateList{
			display:flex;
			align-content:flex-start;
			flex-wrap:wrap;
			overflow:auto;
			padding:0px;
		}
		#templateList li {width:31% ; border:1px solid #ccc !important; margin:5px !important;padding:5px !important;font-size:12px;min-width:220px;list-style:none !important}
		#templateList li div{white-space: normal !important;}
	</style>
<script>
	$(document).ready(function(){
		var height = $(window).height() - 200;
		$('.listDiv').css("height" , height + "px");
	});


	function fn_focus(msgno){
		$('.listDiv').find("tbody tr").css("background-color" , "#fff").css("color" , "#666");
		$('#ROW_'+msgno).css("background-color" , "#adb5bd").css("color" , "white");

		$('#templateList').find("li").css("background-color" , "#fff");
		$('#template_'+msgno).css("background-color" , "orange");
		$('#template_'+msgno).find("textarea").focus();

	}

	function fn_template_add(msgno){
		$('#modal-default').find(".modal-body").html("");
		var mode = "INSERT";
		if(msgno != ''){
			$('#modal-default').find(".modal-title").html("템플릿 수정");
			 mode = "UPDATE";
		}else{
			$('#modal-default').find(".modal-title").html("템플릿 등록");
		}

		if(msgno == "") msgno = "0";

		$.ajax({
		      url: './templateDetailAjax',
		      data : {'msgno':msgno,'MODE':mode},
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

	function fn_template_delete(msgno){
		if(confirm("삭제하시겠습니까?")){
			$.ajax({
			      url: './templateDelete.json',
			      data : {'msgno':msgno},
			      type : "POST",
			      dataType : "json",
			      success: function(data) {
			    	   alert(data.result.msg);
			    	   if (data.result.code == "ERROR") {

				       } else {
					        top.location.reload();
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
            <h1>메시지 자동발송 템플릿</h1>
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
          <div class="col-sm-4">
            <div class="card card-primary card-outline">
              <!-- /.card-header -->
				<div class="card-header">
                	<h3 class="card-title">목록</h3>
                	<div class="card-tools">
			                	<c:choose>
									<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
										 <button type="button"  onclick="fn_template_add('')" data-toggle="modal" data-target="#modal-default"  class="btn btn-info btn-sm">등록</button>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn-info btn-sm disabled">템플릿 등록</button>
									</c:otherwise>
								</c:choose>
			                </div>
              	</div>
              <div class="listDiv card-body table-responsive p-0" style="height: 700px;">
	               <table class="table table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 15%;">
						<col style="width: 45%;">
						<col style="width: 30%;">
						<col style="width: *">
					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th>코드</th><!-- 번호 -->
							<th>메시지명</th><!-- 코드ID -->
							<th>발송방법</th><!-- 사용여부 -->
							<th>삭제</th>
	                    </tr>
	                  </thead>
	                  <tbody>
<c:if test="${fn:length(resultList) == 0}">
					<tr>
						<td colspan="4" class="text-center">데이타가 없습니다</td>
					</tr>
</c:if>
<c:forEach items="${resultList}" var="item" varStatus="status">
	                    <tr id="ROW_<c:out value='${item.msgno}'/>">
							<td onclick="fn_focus(${item.msgno})" style="cursor:pointer"><u><c:out value='${item.msgno}'/></u></td>
							<td ><a href="javascript:;" onclick="fn_template_add('${item.msgno}')" data-toggle="modal" data-target="#modal-default"><c:out value='${item.msgName}'/></a></td>
							<td><c:out value='${item.msgTypenm}'/></td>
							<td>
								<c:choose>
									<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
                             			<button type="button" class="btn bg-secondary btn-xs" onclick="fn_template_delete('<c:out value="${item.msgno}"/>' )" > 삭제</button>
									</c:when>
									<c:otherwise><button type="button" class="btn bg-secondary btn-xs disabled"> 삭제</button></c:otherwise>
								</c:choose>
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
        <div class="col-sm-8">
            <div class="card card-primary card-outline">
              <!-- /.card-header -->

              <div class="card-header">
                <h3 class="card-title">발송 내용</h3>
                <div class="card-tools">
                	<div class="input-group input-group-sm" style="color:red;">
	                 ※  발송내용 문구수정/등록 시 :  bizppurio 사이트 템플릿에서도 수정/등록 후, 별도 승인처리 되어야 합니다.
	                </div>
	              </div>
              </div>

              <div class="card-body listDiv p-0"  style="height: 700px;overflow:scroll">
					<ul id="templateList">
<c:forEach items="${resultList}" var="item" varStatus="status">
						<li id="template_${item.msgno}">
							<div><label>${item.msgno}. ${item.msgName }</label>
								<c:choose>
									<c:when test="${item.msgSendmethod eq '1001'}"><span class="badge bg-danger">${item.msgTypenm}</span></c:when><c:otherwise><span class="badge bg-success">${item.msgTypenm}</span></c:otherwise>
								</c:choose>
							</div>
							<div><textarea style="width:100%;height:200px;background-color:<c:choose><c:when test="${item.msgSendmethod eq '2001'}">#CEE3C8</c:when><c:otherwise>#F1E7EA</c:otherwise></c:choose>" readonly>${item.sendmsg}</textarea></div>
						</li>
</c:forEach>
					</ul>
              </div>

        </div>
      </div>
     </div>
<!-- 모달 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog modal-lg">
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