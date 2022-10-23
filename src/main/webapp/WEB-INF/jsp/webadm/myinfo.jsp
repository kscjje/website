<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<script>
	$(document).ready(function() {
		//Datemask dd/mm/yyyy
		$('.date').inputmask({ mask: "y-1-2", leapday: "-02-29", placeholder: "yyyy-mm-dd", separator: "-", alias: "yyyy-mm-dd" });

		if('${sysUserVO.orgNo}' != "" && '${sysUserVO.orgNo}' != "0") {
			fn_load_depart('${sysUserVO.orgNo}' , document.sysUserVO.depCd , '${sysUserVO.depCd}');
		}
	});

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
	function fn_password_save() {
		if (fn_password_check(document.passwordForm)) {
			$.ajax({
			      url: './passwdUpdt.json',
			      data : $('#passwordForm').serialize(),
			      type : "POST",
			      dataType : "json",
			      success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        } else {
				        	 $('#modal-default').modal('hide');
				        	 alert(data.result.msg);
				         }
			      }
			 });
		}

	}
	function fn_password_check(form) {
		var len = form.new_passwd.value.length;

		if (form.new_passwd.value == "") {
			alert("비밀번호를 입력해 주시기 바랍니다.");
			form.new_passwd.focus();
			return false;
		} else if (form.new_passwd2.value == "") {
			alert("비밀번호를 한번 더 입력해 주시기 바랍니다.");
			form.new_passwd2.focus();
			return false;
		} else if (len < 8 || len > 20) {
			alert("비밀번호는 8자리 이상 20자리 이하 입니다.");
			form.new_passwd.focus();
			return false;
		} else if (form.new_passwd.value != form.new_passwd2.value) {
			alert("비밀번호 확인 값이 동일하지 않습니다.");
			form.new_passwd2.focus();
			return false;
		} else if (!passwordCheck($('#userId').val() , form.new_passwd.value , form.new_passwd2.value)) {
			return false;
		} else {
			return true;
		}
	}

	function fn_save() {
		var form = document.sysUserVO;
		var msg =  "변경하시겠습니까?";
		var url = "./update.json";

		if(form.korName.value == ""){
			alert("이름을 입력해 주세요.");
			form.korName.focus();
		}else if (confirm(msg)) {
			$.ajax({
			      url: url,
			      data : $('#sysUserVO').serialize(),
			      type : "POST",
			      dataType : "json",
			      success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        } else {
				        	alert("정상적으로 변경되었습니다.");
				        	window.location.reload();
				        }
			      }
			 });
		}
	}
	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>내정보 관리</h1>
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
	          <div class="col-6">
	            <div class="card card-primary card-outline">
	            <div class="card-header">
               			 <h3 class="card-title">기본 정보</h3>
              	</div>
	              <!-- /.card-header -->

	<form:form commandName="sysUserVO" name="sysUserVO" class="form-horizontal" method="post" >
	                <div class="card-body">
    <c:if test="${!empty sysUserVO.userType }">
                  <div class="form-group row">
	                    <label for="edcRsvnRectype" class="col-sm-2 col-form-label">* 관리자유형</label>
	                    <div class="col-sm-10"  style="padding-top:5px">
						     <c:forEach items="${userType}" var="item2" varStatus="status">
						     	<label><c:if test="${sysUserVO.userType eq item2.cd}">${item2.cdNm}</c:if></label>
						     </c:forEach>
	                    </div>
	               </div>
	</c:if>
                  <div class="form-group row">
                    <label for="roleCd" class="col-sm-2 col-form-label">* 관리권한 그룹</label>
                    <div class="col-sm-10" style="padding-top:5px">
                     	<c:forEach items="${authorList}" var="item2" varStatus="status">
						     	<label><c:if test="${sysUserVO.roleCd eq item2.authorCode}">${item2.authorNm}</c:if></label>
						</c:forEach>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="roleCd" class="col-sm-2 col-form-label">* 소속기관</label>
                    <div class="col-sm-10" style="padding-top:5px">
						    <label>${sysUserVO.orgNm}</label>
                    </div>
                  </div>

                  <div class="form-group row">
                    <label for="userId" class="col-sm-2 col-form-label">* 아이디</label>
                    <div class="col-sm-10">
 						<form:input path="userId" title="아이디" class="form-control" maxlength="50" readonly="true"/>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="korName" class="col-sm-2 col-form-label">* 이름</label>
                    <div class="col-sm-10">
                      	<form:input path="korName" title="이름" class="form-control" maxlength="70" />
   						<div><form:errors path="korName" cssClass="error text-red" /></div>
                    </div>
                  </div>

                  <div class="form-group row">
                    <label for="depCd" class="col-sm-2 col-form-label">부서</label>
                    <div class="col-sm-10">
                      	<form:select path="depCd" title="부서코드" class="form-control">
							<form:option value="">::부서선택::</form:option>
						</form:select>
   						<div><form:errors path="depCd" cssClass="error text-red" /></div>
                    </div>
                  </div>

                   <div class="form-group row">
                    <label for="telNo" class="col-sm-2 col-form-label">휴대폰 번호</label>
                    <div class="col-sm-10">
                      	<form:input path="telNo" title="연락처" class="form-control" maxlength="14" />
   						<div><form:errors path="telNo" cssClass="error text-red" /></div>
   						<div>※ 010-1234-5678 형식으로 입력</div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="telNo" class="col-sm-2 col-form-label">사무실 전화</label>
                    <div class="col-sm-10">
                      	<form:input path="offiTel" title="연락처" class="form-control" maxlength="14" />
                    </div>
                  </div>

                  <div class="form-group row">
                    <label for="email" class="col-sm-2 col-form-label">이메일</label>
                    <div class="col-sm-10">
                      	<form:input path="email" title="이메일" class="form-control" maxlength="50" />
   						<div><form:errors path="email" cssClass="error text-red" /></div>
                    </div>
                  </div>

                  <div class="form-group row">
                    <label for="enterDate" class="col-sm-2 col-form-label">입사일</label>
                    <div class="col-sm-10">
                      	<form:input path="enterDate" title="입사일" class="form-control" maxlength="8" />
   						<div><form:errors path="enterDate" cssClass="error text-red" /></div>
   						<div>※ 날짜형식 YYYY-MM-DD (10자)</div>
                    </div>
                  </div>

                   <div class="form-group row">
                    <label for="outDate" class="col-sm-2 col-form-label">퇴사일</label>
                    <div class="col-sm-10">
                      	<form:input path="outDate" title="퇴사일" class="form-control" maxlength="8" />
   						<div><form:errors path="outDate" cssClass="error text-red" /></div>
   						<div>※ 날짜형식 YYYY-MM-DD (10자)</div>
                    </div>
                  </div>

<c:if test="${!empty sysUserVO.reguser }" >
                  <div class="form-group row">
                  		<label for="useYn" class="col-sm-2 col-form-label">등록일</label>
                    	<div class="col-sm-10" style="padding-top:5px">
                    		<c:out value="${sysUserVO.reguser}"/> / <fmt:formatDate value="${sysUserVO.regdate}" pattern="yyyy-MM-dd HH:mm"/>
                    	</div>
                  </div>
</c:if>
                </div>
	                <!-- /.card-body -->
	                <div class="card-footer">
	                  <button type="button" onclick="fn_save()" class="btn btn-primary float-right">저장</button>
	                  <button type="button" class="btn btn-info float-right mr-2" data-toggle="modal" data-target="#modal-default">비밀번호 변경</button>
	                  <button type="button" class="btn btn-default " onclick="history.back();">취소</button>
	                </div>
	                <!-- /.card-footer -->

	</form:form>


	              </div>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
	            <div class="col-6">
		            <div class="card card-primary card-outline">
			            <div class="card-header">
		               			 <h3 class="card-title">기관 접근권한</h3>
		              	</div>
		              	 <div class="card-body">
							<table id="orgListDiv" class="table table-bordered text-nowrap ">
				               	<colgroup>
									<col style="width:10%;">
									<col style="width:90%;">
								</colgroup>
								<thead>
									<tr>
										<th>No.</th>
										<th>기관명</th>
									</tr>
								</thead>
								<tbody>
<c:choose>
	<c:when test="${empty orgList}">
									<tr>
										<td colspan="2" style="text-align:center">등록된 기관이 없습니다.</td>
									</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${orgList}" var="item" varStatus="status">
							         <tr>
											<td>${status.index+1 }</td>
											<td>
												<c:out value="${item.comnm }"/>
											</td>
									</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
						        </tbody>
						     </table>
		              	 </div>
		             </div>

		            <div class="card card-primary card-outline">
			            <div class="card-header">
		               			 <h3 class="card-title">IP 접속 제한</h3>
		              	</div>
		              	 <div class="card-body">
							<table id="ipinfoTable" class="table table-bordered text-nowrap ">
				               	<colgroup>
									<col style="width:10%;">
									<col style="width:90%;">
								</colgroup>
								<thead>
									<tr>
										<th>No.</th>
										<th>등록 IP</th>
									</tr>
								</thead>
								<tbody>
<c:choose>
	<c:when test="${empty ipList}">
									<tr>
										<td colspan="2" style="text-align:center">등록된 IP 정보가 없습니다.</td>
									</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${ipList}" var="item" varStatus="status">
							         <tr>
											<td>${status.index+1 }</td>
											<td>
												<c:out value="${item.ipInfo }"/>
											</td>
									</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
						        </tbody>
						     </table>
		              	 </div>
		             </div>
	          </div>
	        </div>
<!-- 모달 박스 -->
<div class="modal fade" id="modal-default" >
   <div class="modal-dialog">
     <div class="modal-content">
       <div class="modal-header">
         <h4 class="modal-title">비밀번호 변경</h4>
         <button type="button" class="close" data-dismiss="modal" aria-label="Close">
           <span aria-hidden="true">×</span>
         </button>
       </div>
       <div class="modal-body">
<form name="passwordForm" id="passwordForm" method="post" >
<input type="hidden" name="userId" value="<c:out value='${sysUserVO.userId}'/>" />
       <div class="row">
              <table id="modaltable" class="table table-bordered">
              <colgroup>
        				<col width="25%">
        				<col width="*">
        			</colgroup>
                <tr>
                  		 <th>비밀번호 <span class="pilsu">*</span></th>
                         <td>
                         	<input type="password" name="new_passwd" title="비밀번호 " maxlength="20" style="width:100%"/>
                         </td>
                </tr>

                 <tr>
                  		 <th>비밀번호 확인 <span class="pilsu">*</span></th>
                         <td>
                         	<input type="password" name="new_passwd2" title="비밀번호 확인 " maxlength="20" style="width:100%"/>
                         </td>
                </tr>


              </table>
      </div>
 </form>
       </div>
       <div class="modal-footer justify-content-between">
         <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
         <button type="button" class="btn btn-primary" onclick="fn_password_save()">저장</button>
       </div>
     </div>
     <!-- /.modal-content -->
   </div>
   <!-- /.modal-dialog -->
 </div>
	</section>
</body>