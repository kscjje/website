<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hisco.cmm.util.Config"%>
<%
 /**
  * @Class Name : sysUserList.jsp
  * @Description : 관리자 계정 목록 JSP
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
</head>
<body>
<script type="text/javascript">
	var orgTypeList ; //기관유형
	var ACCESS_ORG_MODE = "add";
	$(document).ready(function(){
		$.ajax({
		      url: '../code/SM_ORG_MTYPE/list.json',
		      type : "GET",
		      cache : false,
		      async : true,
		      success: function(data) {
		    	  //목록을 담는다
		    	  orgTypeList = data.list;
		    	  fn_print_orgtype(document.getElementById("orgLtype") , '${searchVO.orgMtype}');
		    	  fn_print_orgtype(document.addorgForm.orgLtype , '${searchVO.orgMtype}');
		      },
		      error : function(res){
		    	  	__ajaxErrorCallback__(res, "기관유형 가져오기 오류\n" + res.responseText);
		      }

		});

		$('select[name=orgLtype]').change(function(){
			fn_print_orgtype(this , '');

		});

		$('#addorgForm').find("select[name=orgLtype],select[name=orgMtype]").change(function(){
			fn_pring_orglist(document.addorgForm.searchOrgNo);
		});

		$('#searchVO').find("select[name=orgLtype],select[name=orgMtype]").change(function(){
			fn_pring_orglist(document.searchVO.searchOrgNo);
		});


		//부서가져오기
		$('#searchOrgNo').change(function(){
			 fn_load_depart($(this).val() , document.searchVO.searchDepcd , '${searchVO.searchDepcd}');
		});

		fn_set_page(1);
		fn_load_regist('');

		if($('#searchOrgNo').val() != ""){
			fn_load_depart($('#searchOrgNo').val() , document.searchVO.searchDepcd , '${searchVO.searchDepcd}');
		}
	});

	function fn_print_orgtype(parentObj , defaultVal){

		var parentCd = $(parentObj).val();
		var subObj = $(parentObj).parent().parent().find("select[name=orgMtype]");

		subObj.html('');
		subObj.append("<option value=''>기관유형_중(전체)</option>");

		var cnt=0;
		for(var i = 0 ; i<orgTypeList.length ; i++){
			var item = orgTypeList[i];
			if(item.item1 == parentCd){
				if(subObj.html() == ""){
					subObj.append("<option value=''>선택</option>");
				}

				var selected = "";
				if(item.cd == defaultVal) selected = " selected";

				subObj.append("<option value='"+item.cd+"'"+selected+">"+item.cdNm+"</option>");
				cnt++;
			}

		}
		if(cnt < 1){
			subObj.append("<option value=''>없음</option>");
		}
	}
	//기관목록
	function fn_pring_orglist(obj){
		var objID = $(obj).attr("id");
		var formID = $('#searchVO');

		$(obj).html("");

		if(objID == "searchOrgNo"){
			$(obj).append("<option value=''>기관선택(전체)</option>");
		}else{
			formID = $('#addorgForm');
			$(obj).append("<option value=''>::기관선택::</option>");
		}

		var defaultVal = "";
		$.ajax({
		      url: '../orginfo/orgList.json',
		      type : "GET",
		      data : formID.serialize(),
		      cache : false,
		      async : false,
		      success: function(data) {
		    	  for(var i = 0 ; i<data.list.length ; i++){
						var item = data.list[i];
						var selected = "";
						if(item.orgNo == defaultVal){
							selected = " selected";
						}

						$(obj).append("<option value='"+item.orgNo+"'"+selected+">"+item.orgNm+"</option>");
					}
		      }
		});
		//부서코드 가져오기
		if(objID == "searchOrgNo"){
			fn_load_depart( $('#searchOrgNo').val() ,  document.searchVO.searchDepcd , '${sysUserVO.depCd}');
		}
	}

	function fn_load_depart(orgVal , obj , defaultVal){
		$(obj).html("");
		if($(obj).attr("id") == "depCd"){
			$(obj).append("<option value=''>::부서선택::</option>");
		}else{
			$(obj).append("<option value=''>기관 내 부서(전체)</option>");
		}

		if(orgVal != '' && orgVal != '0'){
			$.ajax({
			      url: '../code/CM_DEPT_CD/orgList.json?orgNo=' + orgVal,
			      type : "GET",
			      cache : false,
			      success: function(data) {
			    	  //목록을 담는다
			    	  for(var i = 0 ; i<data.list.length ; i++){
							var item = data.list[i];
							var selected = "";
							if(item.cd == defaultVal){
								selected = " selected";
							}

							$(obj).append("<option value='"+item.cd+"'"+selected+">"+item.cdNm+"</option>");
						}
			      }
			});
		}

	}

	function fn_reset(){
		$('#searchVO').find("input[type=text],input[type=hidden]").val('');
		$('#searchVO').find("select").val('');
	}

	function fn_set_page(pageNo){
		$('#pageIndex').val(pageNo);

		$.ajax({
		      url: './sysUserListAjax',
		      dataType : "html",
		      data : $('#searchVO').serialize(),
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
				    	$("#divCard").html(data);

		    	  		/*
						$('#listTable').find('tbody').html(data);

						$('.pagination_2').html( $('#pagingHtml').html());
						*/

						$('#listTable').find('tbody tr').click(function(){
							var selectId = $(this).data("userid");

							$('#listTable').find("tbody tr").css("background-color" , "#fff").css("color" , "#666");
							$('#ROW_'+selectId).css("background-color" , "#adb5bd").css("color" , "white");
							fn_load_regist(selectId);
						})
		    	  	}

		      },
		      error : function(res){
		    	  	__ajaxErrorCallback__(res, "목록 가져오기 오류\n" + res.responseText);
		      }

		});
	}

	function fn_load_regist(userId){
		$.ajax({
		      url: './sysUserRegistAjax',
		      type : "GET",
		      dataType : "html",
		      data : {'userId' : userId},
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
						$('#registArea').html(data);
		    	  	}
		      }
		});
	}

	function fn_regist_default(){
		fn_load_regist('');
		$('#listTable').find("tbody tr").css("background-color" , "#fff").css("color" , "#666");

	}
	function fn_access_org(){
		var selectedObj = $('#addorgForm').find("select[name=searchOrgNo]");

		var selectedNo = selectedObj.val();

		if(selectedNo == "" || selectedNo == "0"){
			alert("추가할 기관을 선택해 주세요.");
			selectedObj.focus();
			return;
		}

		var selectedName = selectedObj.find("option:checked").text();
		var no = 1;
		var flag = true;

		$.each($('#accessOrglist').find("tbody tr") , function(index,item){
			var accessOrgInfoVal = $(item).find('td input[name=accessOrgInfo]').val();

			if(accessOrgInfoVal.indexOf(selectedNo+'|') == 0){
				flag = false;
			}

			no++;
		});

		if(flag){
			var html = "<tr>";

			 html += "<td>"+no+"</td>";
			 html += "<td><input type='hidden' name='accessOrgInfo' value='"+selectedNo+"|" + selectedName +"'>"+selectedName+"</td>";
			 html += "</tr>";

			 $('#accessOrglist').find("tbody").append(html);
		}else{
			alert("이미 추가된 기관 입니다.");
		}
	}
	//소속기관
	function fn_set_accessorg(){
		if(ACCESS_ORG_MODE == "set"){

			var selectedObj = $('#addorgForm').find("select[name=searchOrgNo]");

			if( selectedObj.val() == ""){
				alert("소속 기관을 선택해 주세요.");
			}else{
				var selectedNo = selectedObj.val();
				var selectedName = selectedObj.find("option:checked").text();

				//소속기관을 접근권한 목록에도 추가
				var no =1;
				var originHTML = $('#orgListDiv').find("tbody").html();
				if(originHTML.indexOf("등록된 기관이") >=0){
					$('#orgListDiv').find("tbody").html('');
				}

				$.each($('#orgListDiv').find("tbody tr") , function(index,item){
					//기존 소속기관은 삭제한다.
					if($('#sysUserVO').find("input[name=orgNo]").val() == $(this).find("td").eq(1).find("input[name=orgListNo]").val()){
						$(this).remove();
					}else{
						$(this).find("td").eq(0).text(no);
						no++;
					}

				});

				var html = "<tr>";
				html += "<td>"+no+"</td>";
				html += "<td>";
				html += "<input type=\"hidden\" name=\"orgListNo\" value=\""+selectedNo+"\" />";
				html +=  selectedName+"</td>";
				html += "		<td>";
				html += "			<button type=\"button\" class=\"btn btn-secondary btn-xs\" onclick=\"fn_org_delete(this)\">X</button>";
				html += "	</td>";
				html += "</tr>";

				$('#orgListDiv').find("tbody").append(html);


				//기본정보 셋팅
				$('#sysUserVO').find("input[name=orgNo]").val(selectedNo);
				$('#myorgText').text(selectedName);

				//부서목록 불러오기
				fn_load_depart( selectedNo , document.sysUserVO.depCd , '');

				$('#modal-org').modal("hide");


			}

		}else{
			var no =0;
			var originHTML = $('#orgListDiv').find("tbody").html();
			if(originHTML.indexOf("등록된 기관이") >=0){
				$('#orgListDiv').find("tbody").html('');
			}

			$.each($('#orgListDiv').find("tbody tr") , function(index,item){
				no++;
			});

			$.each($('#accessOrglist').find("tbody tr") , function(index,item){
				var accessOrgInfoVal = $(item).find('td input[name=accessOrgInfo]').val();

				no++;

				var orgNo = accessOrgInfoVal.split("|")[0];
				var orgNm = accessOrgInfoVal.split("|")[1];

				var cnt = $('#sysUserVO').find('input[name=orgListNo][value='+orgNo+']').length;

				if(cnt < 1){
					var html = "<tr>";
					html += "<td>"+no+"</td>";
					html += "<td>";
					html += "<input type=\"hidden\" name=\"orgListNo\" value=\""+orgNo+"\" />";
					html +=  orgNm+"</td>";
					html += "		<td>";
					html += "			<button type=\"button\" class=\"btn btn-secondary btn-xs\" onclick=\"fn_org_delete(this)\">X</button>";
					html += "	</td>";
					html += "</tr>";

					$('#orgListDiv').find("tbody").append(html);
				}
			});

			if(no < 1){
				alert("관리 기관을 선택해 주세요.");
			}else{
				$('#modal-org').modal("hide");
			}
		}


	}
	function fn_org_delete(obj){
		$(obj).parent().parent().remove();
		var no = 0;
		$.each($('#orgListDiv').find("tbody tr") , function(index,item){
			no++;
			$(this).find("td").eq(0).text(no);
		});
	}
	function fn_ip_delete(obj){
		$(obj).parent().parent().remove();
		var no = 0;
		$.each($('#ipinfoTable').find("tbody tr") , function(index,item){
			if($(this).find("td").eq(0).text() != "+"){
				no++;
				$(this).find("td").eq(0).text(no);
			}

		});
	}

</script>


<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>관리자 계정관리</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>

<!-- Main content -->
<section class="content">
      <div class="container-fluid">
       	<div class="card card-primary card-outline">
<form:form commandName="searchVO" name="searchVO" method="get">
<input type="hidden" id="pageIndex" name="pageIndex" value="1"/>
		 	 	 <div class="card-body">
		 	 	 	<div class="row">
						<div class="col-2">
							<form:select path="orgLtype" class="form-control">
	                    		<form:option value="">기관유형_대(전체)</form:option>
	                    		<form:options items="${typeList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
	                    	</form:select>
	                    </div>
	                    <div class="col-2">
		                    <form:select path="orgMtype" class="form-control">
		                    	<form:option value="">기관유형_중(전체)</form:option>
	                    	</form:select>
						</div>
						<div class="col-3">
							<form:select path="searchOrgNo" class="form-control">
	                    		<form:option value="">기관선택(전체)</form:option>
	                    		<form:options items="${orgList}" class="form-control" itemLabel="orgNm" itemValue="orgNo" />
	                    	</form:select>
	                    </div>
	                    <div class="col-2">
							<form:select path="searchDepcd" class="form-control">
	                    		<form:option value="">기관 내 부서(전체)</form:option>
	                    	</form:select>
	                    </div>

					</div>
					<div class="row pt-2">
						 <div class="col-2">
							<form:select path="searchRole" class="form-control">
	                    		<form:option value="">권한유형</form:option>
								<form:options items="${authorList}" itemValue="authorCode" itemLabel="authorNm"/>
	                    	</form:select>
	                    </div>
						<div class="col-2">
							<form:select path="searchUseYn" class="form-control">
	                    		<form:option value="">사용여부</form:option>
								<form:option value="Y">사용중</form:option>
								<form:option value="N">미사용</form:option>
	                    	</form:select>
	                    </div>
		              	<div class="col-1" style="text-align:right">
		              		아이디
						</div>
						<div class="col-2">
								<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" >
						</div>
						<div class="col-1" style="text-align:right">
		              		이름
						</div>
						<div class="col-2">
								<input class="form-control" name="searchKind" type="text"  size="35" value='<c:out value="${searchVO.searchKind}"/>'  maxlength="155" >
						</div>

 					</div>
 				</div>
 </form:form>
 			</div>

 			<div class="row" style="margin-bottom:15px;text-align:right">
	 	    	<div class="col-4"></div>
	 	    	<div class="col-2">
	 	    		<button type="button" onclick="$('#searchVO').submit()" class="btn btn-primary btn-block">조회</button>
	 	    	</div>
	 	    	<div class="col-2">
	 	    		<button type="button" onclick="fn_reset()" class="btn btn-secondary btn-block">초기화</button>
	 	    	</div>
	 	    	<div class="col-4"></div>
	 	    </div>

	       <div class="row">
	          <div class="col-6">
	            <div class="card">
	              <div class="card-header">
               			 <h3 class="card-title">목록</h3>
               			 <div class="card-tools">
			                	<button type="button" onclick="fn_regist_default()" class="btn btn-info btn-sm">등록</button>
						</div>
              	</div>

	              <!-- /.card-header -->

	              <div id="divCard" class="card-body table-responsive p-0">

	              </div>
	              <!-- /.card-body -->
	              <div class="card-footer p-0">
	              	<div class="pagination_2">

					</div>
	            </div>

	            <!-- /.card -->
	          </div>
	        </div>
	        <div class="col-6" id="registArea">

	        </div>
	      </div>
     </div>


<div class="modal fade" id="modal-org" >
   <div class="modal-dialog">
     <div class="modal-content">
       <div class="modal-header">
         <h4 class="modal-title">접근기관 추가 </h4>
         <button type="button" class="close" data-dismiss="modal" aria-label="Close">
           <span aria-hidden="true">×</span>
         </button>
       </div>
       <div class="modal-body">
<form name="addorgForm" id="addorgForm" method="post" >
		<div class="row" >
						<div class="col-6">
								<select name="orgLtype" class="form-control">
		                    		<option value="">기관유형_대(전체)</option>
		                    		<c:forEach items="${typeList}" var="item" varStatus="status">
		                    			<option value="${item.cd }" <c:if test="${searchVO.orgLtype eq item.cd }">selected</c:if>>${item.cdNm }</option>
		                    		</c:forEach>
		                    	</select>
	                    </div>
	                    <div class="col-6">
			                    <select name="orgMtype" class="form-control">
			                    	<option value="">기관유형_중(전체)</option>
		                    	</select>
						</div>
		</div>
		<div class="row pt-1 pb-2">
						<div class="col-12">
							<div class="input-group">
								<select name="searchOrgNo" class="form-control">
		                    		<option value="">::기관선택::</option>
		                    		<c:forEach items="${orgList}" var="item" varStatus="status">
		                    			<option value="${item.orgNo}">${item.orgNm}</option>
		                    		</c:forEach>
		                    	</select>
		                    	<span class="input-group-append">
		                    		<button type="button" class="btn btn-info btn-flat" onclick="fn_access_org()">추가</button>
		                  		</span>
		                    </div>
	                    </div>
	  </div>
 </form>

 		 <div class="row col-12 ROW_ORG_ADD"  >
 		 	<label>[추가될 기관 선택]</label>
 		 </div>
       <div class="row">
              <div class="col-12"  style="height:300px;overflow:auto">
	              <table id="accessOrglist" class="table table-sm table-bordered">
	              <colgroup>
	        				<col width="20%">
	        				<col width="*">
	        			</colgroup>
						<thead>
							<tr>
								<th>No.</th>
								<th>기관명</th>
							</tr>
						</thead>
						<tbody>

						</tbody>


	              </table>
	        </div>
      </div>

       </div>
       <div class="modal-footer justify-content-between">
         <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
         <button type="button" class="btn btn-primary" onclick="fn_set_accessorg()">확인</button>
       </div>
     </div>
     <!-- /.modal-content -->
   </div>
   <!-- /.modal-dialog -->
 </div>
</section>
</body>