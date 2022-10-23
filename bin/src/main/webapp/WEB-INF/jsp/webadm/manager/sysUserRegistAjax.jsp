<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hisco.cmm.util.Config"%>
<%
 /**
  * @Class Name : sysUserRegistAjax.jsp
  * @Description : 관리자 계정 생성  JSP
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
<validator:javascript formName="sysUserVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript">
/* ********************************************************
 * 저장처리화면
 ******************************************************** */
function fn_save() {

		var form = document.sysUserVO;
		var msg = "등록하시겠습니까?";
		var url = "./sysUserInsert.json";
		<c:if test="${mode eq 'edit' }">
			msg = "변경하시겠습니까?";
			 url = "./sysUserUpdate.json";
		</c:if>

		if (!validateSysUserVO(form)) {
			return ;
		}

		if(form.orgNo.value == "" || form.orgNo.value == "0"){
			alert("소속기관을 설정해 주세요.");
			return;
		}
		
		if(form.enterDate.value != "" || form.enterDate.value != "0" || form.outDate.value != "" || form.outDate.value != "0"){
			if(form.enterDate.value > form.outDate.value){
				alert("퇴사일은 입사일보다 빠를 수 없습니다.");
				return;
			}
		}
		
	    var ipformat = /^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$/;
		var veliNum = 0;
		$.each($('input[name=ipInfoList]'), function(index, element){
			if(element.value == null || element.value == '' || element.value == "undefined"){} else{
				if(!(ipformat.test(element.value))){
					alert("IP주소를 형식에 맞게 입력해주세요.");
					veliNum = veliNum+1;
				}
			}
		});
		if(veliNum > 0){
			return;
		}
/*
		if($('input[name=userType]:checked').length < 1){
			alert("관리자 유형을 선택해 주세요.");
			$('input[name=userType]').eq(0).focus();
			return;
		}
*/
		<c:if test="${mode eq 'add' }">
		if ($('#userId').val() != $('#confirmUserId').val()) {
			alert("아이디 중복체크를 해주시기 바랍니다.");
			$('#userId').focus();
			return;
		}
		if (!fn_password_check(form)) {
			return ;
		}
		</c:if>

		if (confirm(msg)) {
			$.ajax({
			      url: url,
			      data : $('#sysUserVO').serialize(),
			      type : "POST",
			      dataType : "json",
			      success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        } else {
				        	<c:choose>
				        		<c:when test="${mode eq 'add' }">
				        		alert("등록되었습니다.");
				        		fn_set_page(1);
				        		</c:when>
				        		<c:otherwise>
				        			alert("변경되었습니다.");
				        			fn_set_page($('#pageIndex').val());
				        		</c:otherwise>
				        	</c:choose>
				        	fn_load_regist('');
				         }
			      }
			 });
		}
}

function fn_password_save() {
	if (fn_password_check(document.passwordForm)) {
		$.ajax({
		      url: './sysUserPasswdUpdt.json',
		      data : $('#passwordForm').serialize(),
		      type : "POST",
		      dataType : "json",
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        } else {
			        	 $('#modal-default').modal('hide');
			        	 alert(data.result.msg);

			        	 fn_load_regist($('#passwordForm').find('input[name=userId]').val());

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

function fn_id_check() {
	var f = document.sysUserVO;
	if ($('#userId').val() == "") {
		alert("아이디를 입력해 주시기 바랍니다.");
		f.userId.focus();
	} else {
		var userId = $('#userId').val();
		$.ajax({
		      url: './sysUserDupliCheckAjax.json',
		      data : $('#sysUserVO').serialize(),
		      type : "POST",
		      dataType : "json",
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 $('#divModalResult').html("사용 불가능한 아이디 입니다.<br>다른 아이디를 입력해 주세요.").removeClass("text-blue").addClass("text-red");
			        } else {
			        	$('#confirmUserId').val(data.result.msg);
			        	$('#userId').val(data.result.msg);
			        	$('#divModalResult').text("사용 하실 수 있는 아이디 입니다.").addClass("text-blue").removeClass("text-red");
			         }
		      }
		 });
	}
}



$(document).ready(function() {
	$('#userId').keyup(function() {
		$('#divModalResult').text('');
	});

	//Datemask dd/mm/yyyy
	$('.date').inputmask({ mask: "y-1-2", leapday: "-02-29", placeholder: "yyyy-mm-dd", separator: "-", alias: "yyyy-mm-dd" });

	$('#orgAddBtn').click(function(){
		$('#accessOrglist').parent().parent().show();
		$('#accessOrglist').find("tbody").html("");
		$('#addorgForm').find("select[name=searchOrgNo]").val("");
		$('#addorgForm').find("select[name=searchOrgNo]").next().show();
		$('.ROW_ORG_ADD').show();

		$('#modal-org').find(".modal-dialog .modal-content .modal-header .modal-title").text("접근기관 추가");


		ACCESS_ORG_MODE = "add";

	});

	$('#orgAddBtn2').click(function(){
		$('#accessOrglist').parent().parent().hide();
		$('#addorgForm').find("select[name=searchOrgNo]").val("");
		$('#addorgForm').find("select[name=searchOrgNo]").next().hide();
		$('.ROW_ORG_ADD').hide();

		$('#modal-org').find(".modal-dialog .modal-content .modal-header .modal-title").text("소속기관  설정");

		ACCESS_ORG_MODE = "set";

	})

	$('#ipAddBtn').click(function(){
		var html = "<tr>";
		html += "<td>+</td>";
		html += "<td><div class=\"input-group input-group-sm\"><input type=\"text\" name=\"ipInfoList\" title=\"IP\" value=\"\" class=\"form-control\" maxlength=\"20\" /></div></td>";
		html += "<td><button type=\"button\" class=\"btn btn-secondary btn-xs\" onclick=\"fn_ip_delete(this)\">X</button></td>";
		html += "<tr>";
		$('#ipinfoTable').find("tbody").append(html);
	});

	if('${sysUserVO.orgNo}' != "" && '${sysUserVO.orgNo}' != "0") {
		fn_load_depart('${sysUserVO.orgNo}' , document.sysUserVO.depCd , '${sysUserVO.depCd}');
	}
})
</script>
<form:form commandName="sysUserVO" name="sysUserVO" action="./sysUserSave" class="form-horizontal" method="post">
<input type="hidden" name="mode"  value="<c:out value='${mode }'/>">
<input type="hidden" name="confirmUserId" id="confirmUserId"  value="">
<input type="hidden" name="oldRoleCd" id="oldRoleCd"  value="${sysUserVO.roleCd}">

			<div class="card card-primary card-outline card-outline-tabs">
              <div class="card-header p-0 border-bottom-0">
                <ul class="nav nav-tabs" id="tablist">
                  <li class="nav-item">
                    <a class="nav-link active" href="#USER_INFO_TAB" role="tab" data-toggle="pill" aria-controls="USER_INFO_TAB" aria-selected="true" >기본정보</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link"   href="#USER_ORG_TAB" role="tab" data-toggle="pill" aria-controls="USER_ORG_TAB" aria-selected="false">기관 접근권한</a>
                  </li>
                   <li class="nav-item">
                    <a class="nav-link"   href="#USER_IP_TAB" role="tab" data-toggle="pill" aria-controls="USER_IP_TAB" aria-selected="false">IP접속 제한</a>
                  </li>
                </ul>
             </div>
              <!-- /.card-header -->
                <div class="card-body table-responsive p-0">
                	<div class="tab-content" id="custom-tabs-one-tabContent">
                		<div class="tab-pane fade  active show" id="USER_INFO_TAB" role="tabpanel">
			                  <table class="table table-bordered text-nowrap ">
				               	<colgroup>
									<col style="width:5%;">
									<col style="width:45%;">
									<col style="width:5%;">
									<col style="width:45%;">
								</colgroup>

								<tbody>
								<tr>
									<th><span class="pilsu">*</span> 관리자 유형</th>
	                    			<td colspan="3">
<c:forEach items="${userType}" var="item2" varStatus="status">
				     						<div class="form-check" style="display:inline-block">
					                          <form:radiobutton path="userType" cssClass="form-check-input" value="${item2.cd}"/>
					                          <label for="userType${status.index+1}" class="form-check-label">${item2.cdNm}</label>
					                        </div>
</c:forEach>
	                    			</td>
	               				</tr>
								<tr>
									<th><span class="pilsu">*</span> 권한그룹</th>
									<td colspan="3">
										<div class="input-group input-group-sm" >
											<form:select path="roleCd" title="권한" cssClass="form-control">
												<form:options items="${authorList}" itemValue="authorCode" itemLabel="authorNm"/>
											</form:select>
											<div><form:errors path="roleCd" cssClass="error text-red" /></div>
										</div>
									</td>
			                  	</tr>
			                  	<tr>
									<th><span class="pilsu">*</span> 아이디</th>
									<td >
										<div class="input-group input-group-sm">
			<c:choose>
			 	<c:when test="${mode eq 'edit' }">
			 							<form:input path="userId" title="아이디" class="form-control" maxlength="50" readonly="true"/>
			 	</c:when>
			 	<c:otherwise>
					 						<form:input path="userId" title="아이디" class="form-control" maxlength="20" />
					 						<span class="input-group-append">
					                    		<button type="button" class="btn btn-info btn-flat" onclick="fn_id_check()">중복체크</button>
					                  		</span>
			 	</c:otherwise>
			 </c:choose>
			 							</div>
			   							<div id="divModalResult"><form:errors path="userId" cssClass="error text-red" /></div>
									</td>
									<th><span class="pilsu">*</span> 이름</th>
									<td>
										<div class="input-group input-group-sm">
											<form:input path="korName" title="이름" class="form-control" maxlength="20" />
				   							<div><form:errors path="korName" cssClass="error text-red" /></div>
				   						</div>
									</td>
			                  	</tr>
<c:choose>
			 	<c:when test="${mode eq 'add' }">
			 					<tr>
									<th><span class="pilsu">*</span> 비밀번호</th>
			                  		<td>
										<div class="input-group input-group-sm">
			                    			<input type="password" name="new_passwd" id="new_passwd" title="비밀번호" class="form-control" maxlength="20" />
			                    		</div>
			                    	</td>
			                  		<th><span class="pilsu">*</span> 비밀번호 확인</th>
			                    	<td>
										<div class="input-group input-group-sm">
			                    			<input type="password" name="new_passwd2" id="new_passwd2" title="비밀번호" class="form-control" maxlength="20" />
			                    		</div>
			                  		</td>
			                  	</tr>
				</c:when>
				<c:otherwise>
							 	<tr>
									<th>비밀번호 초기화</th>
			                  		<td>
										 <button type="button" class="btn btn-info btn-xs" data-toggle="modal" data-target="#modal-default">비밀번호 재설정 실행</button>
			                    	</td>
			                  		<th>비밀번호<br/>최종변경 일시</th>
			                    	<td>
										<fmt:formatDate value="${sysUserVO.pwdChangeDate}" pattern="yyyy-MM-dd HH:mm"/>
			                  		</td>
			                  	</tr>
				</c:otherwise>
			</c:choose>
			                  	<tr>
									<th><span class="pilsu">*</span> 소속기관</th>
									<td >
										<form:hidden path="orgNo" />
										<span id="myorgText"><c:out value="${sysUserVO.orgNm }" /></span>
										&nbsp;
					                    <button type="button" class="btn btn-info btn-xs" id="orgAddBtn2" data-toggle="modal" data-target="#modal-org">${mode eq 'add' ?'설정':'변경'}</button>
									</td>
									<th> 부서</th>
									<td>
										<div class="input-group input-group-sm">
											<form:select path="depCd" title="부서코드" class="form-control">
												<form:option value="">::부서선택::</form:option>
											</form:select>
			   								<div><form:errors path="depCd" cssClass="error text-red" /></div>
				   						</div>
									</td>
			                  	</tr>
			                  	<tr>
									<th>휴대폰 번호</th>
									<td >
										<div class="input-group input-group-sm">
											<form:input path="telNo" title="전화번호" class="form-control" maxlength="14" />
			   								<div><form:errors path="telNo" cssClass="error text-red" /></div>
				   						</div>
				   						<div>※ 010-1234-5678 형식으로 입력</div>
									</td>
									<th>사무실 전화</th>
									<td>
										<div class="input-group input-group-sm">
											<form:input path="offiTel" title="사무실 전화" class="form-control" maxlength="14" />
			   								<div><form:errors path="offiTel" cssClass="error text-red" /></div>
				   						</div>
									</td>
			                  	</tr>
			                  	<tr>
									<th>입사일</th>
									<td >
										<div class="input-group input-group-sm">
											<form:input path="enterDate" title="입사일" class="form-control date" maxlength="10" />

				   						</div>
				   						<div><form:errors path="enterDate" cssClass="error text-red" /></div>
			   								<div>※ 날짜형식 YYYY-MM-DD (10자)</div>
									</td>
									<th>퇴직일</th>
									<td>
										<div class="input-group input-group-sm">
											<form:input path="outDate" title="퇴사일" class="form-control date" maxlength="10" />

				   						</div>
				   						<div><form:errors path="outDate" cssClass="error text-red" /></div>
			   								<div>※ 날짜형식 YYYY-MM-DD (10자)</div>
									</td>
			                  	</tr>
			                  	<tr>
									<th>이메일</th>
									<td >
										<div class="input-group input-group-sm">
											<form:input path="email" title="이메일" class="form-control" maxlength="50" />
			   								<div><form:errors path="email" cssClass="error text-red" /></div>
				   						</div>
									</td>
									<th>재직상태</th>
									<td>

											 <label><form:radiobutton path="stats" value="Y" /> 재직</label>
									    	&nbsp;&nbsp;&nbsp;
									    	<label><form:radiobutton path="stats" value="N" /> 퇴직</label>

									</td>
			                  	</tr>
			                  	<tr>
									<th>비고</th>
									<td colspan="3">
										<div class="input-group input-group-sm">
											<form:input path="remark" title="비고" class="form-control" maxlength="200" />
			   								<div><form:errors path="remark" cssClass="error text-red" /></div>
				   						</div>
									</td>
			                  	</tr>
<c:if test="${mode ne 'add' }">
			                  	<tr>
									<th>활성 상태</th>
									<td colspan="3">
										<label><form:radiobutton path="acntStats" value="1000" /> 활성</label>
									    &nbsp;&nbsp;&nbsp;
									    <label><form:radiobutton path="acntStats" value="2001" /> 잠금</label>
									    &nbsp;&nbsp;&nbsp;
									    <label><form:radiobutton path="acntStats" value="2002" /> 로그인실패잠금</label>
									    &nbsp;&nbsp;&nbsp;
									    <label><form:radiobutton path="acntStats" value="N" /> 사용안함</label>
									</td>

			                  	</tr>

			                  	<tr>
									<th>로그인<br>실패 횟수</th>
									<td>${sysUserVO.failCnt}</td>
									<th>마지막<br>로그인</th>
									<td><fmt:formatDate value="${sysUserVO.lastLogin}" pattern="yyyy-MM-dd HH:mm"/></td>

			                  	</tr>
			                  	<tr>
									<th>등록일</th>
									<td><c:out value="${sysUserVO.reguser}"/> / <fmt:formatDate value="${sysUserVO.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
									<th>수정일</th>
									<td><c:out value="${sysUserVO.moduser}"/> / <fmt:formatDate value="${sysUserVO.moddate}" pattern="yyyy-MM-dd HH:mm"/></td>

			                  	</tr>
</c:if>

			                  </tbody>
			                 </table>


						</div>
						<div class="tab-pane fade p-1" id="USER_ORG_TAB" role="tabpanel">
			                  <div style="padding-top:5px;padding-bottom:5px;padding-right:10px;text-align:right">
		                        	<c:if test="${mode ne 'add' }">※기관 추가 후 하단의 "수정" 버튼을 클릭 하셔야 반영됩니다.</c:if> <button id="orgAddBtn" type="button" class="btn btn-primary btn-xs" style="margin-left:20px" data-toggle="modal" data-target="#modal-org">접근기관 추가</button>
		                        </div>

			                  <table id="orgListDiv" class="table table-bordered text-nowrap ">
				               	<colgroup>
									<col style="width:10%;">
									<col style="width:80%;">
									<col style="width:10%;">
								</colgroup>
								<thead>
									<tr>
										<th>No.</th>
										<th>기관명</th>
										<th>삭제</th>
									</tr>
								</thead>
								<tbody>
<c:choose>
	<c:when test="${empty orgList}">
									<tr>
										<td colspan="3" style="text-align:center">등록된 기관이 없습니다.</td>
									</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${orgList}" var="item" varStatus="status">
							         <tr>
											<td>${status.index+1 }</td>
											<td>
												<input type="hidden" name="orgListNo" value="<c:out value="${item.orgNo }"/>" />
												<c:out value="${item.orgNm }"/>
											</td>
											<td>
												<button type="button" class="btn btn-secondary btn-xs" onclick="fn_org_delete(this)">X</button>
											</td>
									</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
						        </tbody>
						     </table>
						</div>
						<div class="tab-pane fade p-1" id="USER_IP_TAB" role="tabpanel">
							<div style="padding-top:5px;padding-bottom:5px;padding-right:10px;text-align:right">
		                        	<c:if test="${mode ne 'add' }">※IP 추가 후 하단의 "수정" 버튼을 클릭 하셔야 반영됩니다.</c:if> <button id="ipAddBtn" type="button" class="btn btn-primary btn-xs" style="margin-left:20px">IP 추가</button>
		                        </div>

			                  <table id="ipinfoTable" class="table table-bordered text-nowrap ">
				               	<colgroup>
									<col style="width:10%;">
									<col style="width:80%;">
									<col style="width:10%;">
								</colgroup>
								<thead>
									<tr>
										<th>No.</th>
										<th>등록 IP</th>
										<th>삭제</th>
									</tr>
								</thead>
								<tbody>
		<c:forEach items="${ipList}" var="item" varStatus="status">
							         <tr>
											<td>${status.index+1 }</td>
											<td>
												<div class="input-group input-group-sm">
					                    			<input type="text" name="ipInfoList" title="IP" value="<c:out value="${item.ipInfo }"/>" class="form-control" maxlength="20" />
					                    		</div>
											</td>
											<td>
												<button type="button" class="btn btn-secondary btn-xs" onclick="fn_ip_delete(this)">X</button>
											</td>
									</tr>
		</c:forEach>
									<tr>
											<td>+</td>
											<td>
												<div class="input-group input-group-sm">
					                    			<input type="text" name="ipInfoList" title="IP" value="" class="form-control" maxlength="20" />
					                    		</div>
											</td>
											<td>
												<button type="button" class="btn btn-secondary btn-xs" onclick="fn_ip_delete(this)">X</button>
											</td>
									</tr>
						        </tbody>
						     </table>
						</div>


					</div>
                </div>
                <!-- /.card-body -->
                <div class="card-footer">

 	<c:if test="${mode eq 'edit' }">
 					<button type="button" class="btn btn-info float-right<c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" <c:if test="${commandMap.selectedMenu.updYn eq 'Y' }">onclick="fn_save()"</c:if> style="margin-right: 5px;">저장</button>
 	</c:if>
 	<c:if test="${mode eq 'add' }">
 					<button type="button" class="btn btn-info float-right<c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>" <c:if test="${commandMap.selectedMenu.insYn eq 'Y' }">onclick="fn_save()"</c:if>style="margin-right: 5px;">저장</button>
 	</c:if>
                </div>
                <!-- /.card-footer -->
          </div>
  </form:form>

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
        				<col width="20%">
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
         <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
         <button type="button" class="btn btn-primary" onclick="fn_password_save()">Save</button>
       </div>
     </div>
     <!-- /.modal-content -->
   </div>
   <!-- /.modal-dialog -->
 </div>

</body>
