<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hisco.cmm.util.Config"%>
<%
 /**
  * @Class Name : sysUserRegist.jsp
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
<script type="text/javascript" src="<c:url value="/webadm/validator"/>"></script>
<validator:javascript formName="sysUserVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript">
/* ********************************************************
 * 저장처리화면
 ******************************************************** */
function fn_save() {

		var form = document.sysUserVO;
		var msg = "등록하시겠습니까?";
		<c:if test="${mode eq 'edit' }">
			msg = "변경하시겠습니까?";
		</c:if>

		if (!validateSysUserVO(form)) {
			return ;
		}

		if($('input[name=orgNo]').val() == "" || $('input[name=orgNo]').val() == "0"){
			alert("소속기관을 선택해 주세요.");
			return;
		}


		if($('input[name=userType]:checked').length < 1){
			alert("관리자 유형을 선택해 주세요.");
			$('input[name=userType]').eq(0).focus();
			return;
		}

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
			form.submit();
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
			        	 $('#divModalResult').text("사용 불가능한 아이디 입니다. 다른 아이디를 입력해 주세요.").removeClass("text-blue").addClass("text-red");
			        } else {
			        	$('#confirmUserId').val(data.result.msg);
			        	$('#userId').val(data.result.msg);
			        	$('#divModalResult').text("사용 하실 수 있는 아이디 입니다.").addClass("text-blue").removeClass("text-red");
			         }
		      }
		 });
	}
}

function fn_org_add(){
	if($('#orgSearchNo').val() == ""){
		alert("관리 기관을 선택해 주세요.");
		$('#orgSearchNo').focus();
	}else{
		var orgNo = $('#orgSearchNo').val();
		var orgNm = $('#orgSearchNo option:checked').text();
		var orgKind =  $('#orgSearchNo option:checked').data('orgkind');
		var cnt = $('input[name=orgListNo][value='+orgNo+']').length;
		if(cnt > 0){
			alert("이미 추가된 기관 입니다.");
		}else{

			var html = "<tr>";
			html += "<td>";
			html += "<input type=\"hidden\" name=\"orgListNo\" value=\""+orgNo+"\" />";
			html +=  orgKind ; //org_kind
			html += "	</td>";
			html += "	<td>"+orgNm+"</td>";
			html += "		<td>";
			html += "			<button type=\"button\" class=\"btn btn-secondary btn-xs\" onclick=\"fn_org_delete(this)\">X</button>";
			html += "	</td>";
			html += "</tr>";
			$('#orgListDiv').find("table tbody").append(html);

		}

	}
}

function fn_org_delete(obj){
	$(obj).parent().parent().remove();
}

$(document).ready(function() {
	$('#userId').keyup(function() {
		$('#divModalResult').text('');
	});
})
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
<form:form commandName="sysUserVO" name="sysUserVO" action="./sysUserSave" class="form-horizontal" method="post">
<input type="hidden" name="searchQuery" value="<c:out value="${searchQuery }"/>"/>
<input type="hidden" name="mode"  value="<c:out value='${mode }'/>">
<input type="hidden" name="confirmUserId" id="confirmUserId"  value="">
<input type="hidden" name="oldRoleCd" id="oldRoleCd"  value="${sysUserVO.roleCd}">

       <div class="row">

          <div class="col-md-6">
            <div class="card">
              <!-- /.card-header -->
				<div class="card-header">
               	 <h3 class="card-title">기본 정보</h3>
              	</div>

                <div class="card-body">
                  <div class="form-group row">
	                    <label for="edcRsvnRectype" class="col-sm-2 col-form-label">* 관리자유형</label>
	                    <div class="col-sm-10"  style="padding-top:5px">
     <c:forEach items="${userType}" var="item2" varStatus="status">
     						<div class="form-check" style="display:inline-block">
	                          <form:radiobutton path="userType" cssClass="form-check-input" value="${item2.cd}"/>
	                          <label for="userType${status.index+1}" class="form-check-label">${item2.cdNm}</label>
	                        </div>
     </c:forEach>
	                    </div>
	               </div>

                  <div class="form-group row">
                    <label for="roleCd" class="col-sm-2 col-form-label">* 관리권한 그룹</label>
                    <div class="col-sm-10">
                      	<form:select path="roleCd" title="권한" cssClass="form-control">
							<form:options items="${authorList}" itemValue="authorCode" itemLabel="authorNm"/>
						</form:select>
						<div><form:errors path="roleCd" cssClass="error text-red" /></div>
                    </div>
                  </div>

                  <div class="form-group row">
                    <label for="userId" class="col-sm-2 col-form-label">* 아이디</label>
                    <div class="col-sm-10">
 <c:choose>
 	<c:when test="${mode eq 'edit' }">
 						<form:input path="userId" title="아이디" class="form-control" maxlength="50" readonly="true"/>
 	</c:when>
 	<c:otherwise>
	 					<div class="input-group">
	 						<form:input path="userId" title="아이디" class="form-control" maxlength="50" />
	 						<span class="input-group-append">
	                    		<button type="button" class="btn btn-info btn-flat" onclick="fn_id_check()">중복체크</button>
	                  		</span>
	                  	</div>
 	</c:otherwise>
 </c:choose>
   						<div id="divModalResult"><form:errors path="userId" cssClass="error text-red" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="korName" class="col-sm-2 col-form-label">* 이름</label>
                    <div class="col-sm-10">
                      	<form:input path="korName" title="이름" class="form-control" maxlength="70" />
   						<div><form:errors path="korName" cssClass="error text-red" /></div>
                    </div>
                  </div>
 <c:choose>
 	<c:when test="${mode eq 'add' }">
                  <div class="form-group row">
                    <label for="new_passwd" class="col-sm-2 col-form-label">* 비밀번호</label>
                    <div class="col-sm-10">
                    	<input type="password" name="new_passwd" id="new_passwd" title="비밀번호" class="form-control" maxlength="20" />
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="new_passwd2" class="col-sm-2 col-form-label">* 비밀번호 확인</label>
                    <div class="col-sm-10">
                    	<input type="password" name="new_passwd2" id="new_passwd2" title="비밀번호" class="form-control" maxlength="20" />
                    </div>
                  </div>
	</c:when>
</c:choose>
                  <div class="form-group row">
                    <label for="depCd" class="col-sm-2 col-form-label">부서코드</label>
                    <div class="col-sm-10">
                      	<form:input path="depCd" title="부서코드" class="form-control" maxlength="70" />
   						<div><form:errors path="depCd" cssClass="error text-red" /></div>
                    </div>
                  </div>

                   <div class="form-group row">
                    <label for="telNo" class="col-sm-2 col-form-label">연락처</label>
                    <div class="col-sm-10">
                      	<form:input path="telNo" title="연락처" class="form-control" maxlength="14" />
   						<div><form:errors path="telNo" cssClass="error text-red" /></div>
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
   						<div>※ 날짜형식 YYYYMMDD (8자)</div>
                    </div>
                  </div>

                   <div class="form-group row">
                    <label for="outDate" class="col-sm-2 col-form-label">퇴사일</label>
                    <div class="col-sm-10">
                      	<form:input path="outDate" title="퇴사일" class="form-control" maxlength="8" />
   						<div><form:errors path="outDate" cssClass="error text-red" /></div>
   						<div>※ 날짜형식 YYYYMMDD (8자)</div>
                    </div>
                  </div>
				 <div class="form-group row">
	                    <label for="useYn" class="col-sm-2 col-form-label">* 아이디 계정상태</label>
	                    <div class="col-sm-10" style="padding-top:7px">
	                      	 <label><form:radiobutton path="acntStats" value="1000" /> 활성</label>
						    &nbsp;&nbsp;&nbsp;
						    <label><form:radiobutton path="acntStats" value="2001" /> 잠금</label>
						    &nbsp;&nbsp;&nbsp;
						    <label><form:radiobutton path="acntStats" value="2002" /> 로그인실패잠금</label>
	                    </div>
                  </div>


                   <div class="form-group row">
	                    <label for="useYn" class="col-sm-2 col-form-label">* 사용여부</label>
	                    <div class="col-sm-10" style="padding-top:7px">
	                      	 <label><form:radiobutton path="useYn" value="Y" /> 사용함</label>
						    &nbsp;&nbsp;&nbsp;
						    <label><form:radiobutton path="useYn" value="N" /> 사용안함</label>

							<div><form:errors path="useYn" cssClass="error text-red" /></div>
	                    </div>
                    </div>

<c:if test="${!empty sysUserVO.reguser }" >
                  <div class="form-group row">
                  		<label for="useYn" class="col-sm-2 col-form-label">등록일</label>
                    	<div class="col-sm-10" >
                    		<c:out value="${sysUserVO.reguser}"/> / <fmt:formatDate value="${sysUserVO.regdate}" pattern="yyyy-MM-dd HH:mm"/>
                    	</div>
                  </div>
</c:if>
                </div>
                <!-- /.card-body -->
                <div class="card-footer">
                  <a class="btn btn-default " href="./sysUserList${searchQuery }">목록</a>
 	<c:if test="${mode eq 'edit' }">
 					<button type="button" class="btn btn-info float-right<c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" style="margin-right: 5px;"  data-toggle="modal" data-target="#modal-default">비밀번호 변경</button>
 	</c:if>
                </div>
                <!-- /.card-footer -->


              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
           <div class="col-md-6">
            <div class="card">
              <!-- /.card-header -->
				<div class="card-header">
               	 <h3 class="card-title">소속/관리 기관</h3>
              	</div>

                <div class="card-body">
					<div class="form-group row">
	                    <label for="comnm" class="col-sm-2 col-form-label">소속 기관</label>
	                    <div class="col-sm-10">
	                    	<hisco:OrgList defaultNo="${sysUserVO.orgNo}" id="orgNo" nullValue="0"/>
	   						<div><form:errors path="orgNo" cssClass="error" /></div>
	   						<div class="text-red">※ 소속기관 외에 추가로 관리를 원하시는 기관은 아래의 관리 기관을 추가하여 주세요.</div>
	                    </div>
                    </div>
                    <div class="form-group row">
		                    <label for="edcLimitAgeyn1" class="col-sm-2 col-form-label">관리 기관</label>
		                    <div class="col-sm-10">

		                        <hisco:OrgList defaultNo="" id="orgSearchNo" />

		                        <div style="padding-top:10px;padding-bottom:10px">
		                        	※관리 기관선택 후 추가 버튼을 클릭해 주세요 <button type="button" class="btn btn-primary btn-xs" style="margin-left:20px" onclick="fn_org_add()">기관 추가</button>
		                        </div>
		                        <div id="orgListDiv">
		                        <table style="width:100%" class="table table-bordered">
									<colgroup>
			                  				<col width="20%">
			                  				<col width="*">
			                  				<col width="10%">
			                  			</colgroup>
									<thead>
										<tr>
											<th>기관종류</th>
											<th>기관명</th>
											<th>삭제</th>
										</tr>
									</thead>
									<tbody>
<c:choose>
	<c:when test="${empty orgList}">

	</c:when>
	<c:otherwise>
		<c:forEach items="${orgList}" var="item" varStatus="status">
							         <tr>
											<td>
												<input type="hidden" name="orgListNo" value="<c:out value="${item.orgNo }"/>" />
												${item.orgKindNm}
											</td>
											<td><c:out value="${item.comnm }"/></td>
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
		                    </div>
	                  </div>
                </div>
                <div class="card-footer">
    <c:choose>
    	<c:when test="${ mode eq 'edit' and commandMap.selectedMenu.updYn ne 'Y'}"><button type="button" class="btn btn-info  form-control disabled">저장</button></c:when>
    	<c:when test="${ commandMap.selectedMenu.insYn ne 'Y'}"><button type="button" class="btn btn-info form-control disabled">저장</button></c:when>
    	<c:otherwise><button type="button" class="btn btn-info form-control" onclick="fn_save()">저장</button></c:otherwise>
    </c:choose>
                </div>
                <!-- /.card-footer -->
             </div>
           </div>

          </div>
  </form:form>
        </div>
</section>

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
