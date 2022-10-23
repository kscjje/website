<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : webMemberDetail.jsp
  * @Description : 웹 회원 상세 조회
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.03.22    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.03.22
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/webadm/base/header.jsp"%>
<script type="text/javascript" src="<c:url value="/webadm/validator"/>"></script>
<validator:javascript formName="memberUserVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	var fullAddr = ""; // 최종 주소 변수
	var extraAddr = ""; // 조합형 주소 변수

	$(document).ready(function(){
	    //Datemask dd/mm/yyyy
		$('#birthDate').inputmask({ mask: "y-1-2", leapday: "-02-29", placeholder: "yyyy-mm-dd", separator: "-", alias: "yyyy-mm-dd" });

		$('#hp').bind('keyup blur', function(e){
			var self = $(this),
				val = self.val();
			if(val.search(/[^0-9]/) > -1)
				val = val.replace(/[^0-9]/g, '');

			// - 삽입
			if(val.length <= 6)
				val = val.substring(0, 3) +'-'+ val.substring(3);
			else if(val.length <= 10)
				val = val.substring(0, 3) +'-'+ val.substring(3, 6) +'-'+ val.substring(6);
			else
				val = val.substring(0, 3) +'-'+ val.substring(3, 7) +'-'+ val.substring(7, 12);

			self.val(val);
		});

	});

	//다음지도 API
	function fn_openPostCodePop() {
	    new daum.Postcode({
	        oncomplete: function(data) {
	            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	            var fullAddr = ''; // 최종 주소 변수
	            var extraAddr = ''; // 조합형 주소 변수

	            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                fullAddr = data.roadAddress;

	            } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                fullAddr = data.jibunAddress;
	            }

	            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
	            if(data.userSelectedType === 'R'){
	                //법정동명이 있을 경우 추가한다.
	                if(data.bname !== ''){
	                    extraAddr += data.bname;
	                }
	                // 건물명이 있을 경우 추가한다.
	                if(data.buildingName !== ''){
	                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                }
	                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
	                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
	            }

	            // 우편번호와 주소 정보를 해당 필드에 넣는다.
	            document.getElementById('postNum').value = data.zonecode;
	            document.getElementById('addr1').value = fullAddr;
	            document.getElementById('addr2').focus();
	        }
	    }).open();
	}
	function fn_save() {

		var form = document.memberUserVO;
		var msg = "등록하시겠습니까?";

		if (!validateMemberUserVO(form)) {
			return ;
		}
/*
		var email = $('#email').val();
		var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;//이메일 정규식

		if(email != "" && !emailRule.test(email){
			alert("이메일 주소가 형식에 맞지 않습니다.");
			$('#email').focus();
			return;
		}
*/
		if (confirm(msg)) {
			form.submit();
		}
}
</script>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
             <h1>웹 회원 관리</h1>
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
	              <div class="card-body  table-responsive p-0">
<form:form commandName="memberUserVO" name="memberUserVO" id="memberForm" action="./userSave"  method="post">
<form:hidden path="comcd"/>
	                	<table class="table table-bordered text-nowrap">
		               	<colgroup>
							<col style="width:5%;">
							<col style="width:45%;">
							<col style="width:5%;">
							<col style="width:45%;">
						</colgroup>

						<tbody>
						<tr>
							<th>이름</th>
							<td class="left">
								<form:input path="memNm"  placeholder="이름 입력해주세요." title="이름" maxlength="20" cssClass="form-control"/>
								<div><form:errors path="memNm" cssClass="error" /></div>
							</td>
							<th>성별</th>
							<td class="left">
								<div class="form-group clearfix">
			                      <div class="icheck-primary d-inline">
			                      	<form:radiobutton path="gender" value="M"/>
			                        <label for="gender1">남자</label>
			                      </div>
			                      <div class="icheck-primary d-inline">
			                        <form:radiobutton path="gender" value="F"/>
			                        <label for="gender2">
			                        	여자
			                        </label>
			                      </div>
			                    </div>
			                    <div><form:errors path="gender" cssClass="error" /></div>
							</td>
						</tr>
						<tr>

							<th>생년월일</th>
							<td class="left">
								<div class="input-group">
			                    <div class="input-group-prepend">
			                      <span class="input-group-text"><i class="far fa-calendar-alt"></i></span>
			                    </div>
			                    <form:input path="birthDate"  title="생년월일" maxlength="10" cssClass="form-control"/>
			                  </div>
			                  <div><form:errors path="birthDate" cssClass="error" /></div>
							</td>
							<th>양력/음력</th>
							<td class="left">
								<div class="form-group clearfix">
			                      <div class="icheck-primary d-inline">
			                      	<form:radiobutton path="birthSec" value="S"/>
			                        <label for="birthSec1">양력</label>
			                      </div>
			                      <div class="icheck-primary d-inline">
			                        <form:radiobutton path="birthSec" value="M"/>
			                        <label for="birthSec2">
			                        	음력
			                        </label>
			                      </div>
			                    </div>
			                    <div><form:errors path="birthSec" cssClass="error" /></div>
							</td>
						</tr>

						<tr>
							<th>휴대폰</th>
							<td  class="left">
								<div class="input-group">
				                    <div class="input-group-prepend">
				                      <span class="input-group-text"><i class="fas fa-phone"></i></span>
				                    </div>
				                   <form:input path="hp"  title="휴대폰" maxlength="13" cssClass="form-control"/>
				                  </div>

				                   <div><form:errors path="hp" cssClass="error" /></div>

							</td>
							<th>이메일</th>
							<td class="left">
								<form:input path="email"  title="이메일" maxlength="100" cssClass="form-control"/>
							</td>
						</tr>

						<tr>
							<th>주소</th>
							<td colspan="3" class="left">
								<div style="position:relative">
									<form:input path="postNum"  title="우편번호" maxlength="5" cssClass="form-control" cssStyle="width:100px;float:left" placeHolder="우편번호" readonly="true"/>
									<button type="button" class="btn btn-primary" style="float:left;margin-left:10px" id="searchPost" onclick="fn_openPostCodePop()">주소찾기</button>
								</div>
									<form:input path="addr1"  title="주소 1" cssClass="form-control" placeHolder="주소 1" cssStyle="margin-top:50px"/>

									<form:input path="addr2"  title="주소 2" cssClass="form-control" placeHolder="나머지 주소" cssStyle="margin-top:5px"/>
							</td>
						</tr>

					</tbody>

						</table>
</form:form>
	                </div>
	                <!-- /.card-body -->
	                <div class="card-footer">
	                  <button type="button" onclick="fn_save()" class="btn btn-info float-right">저장</button>
	                  <button type="button" class="btn btn-default " onclick="history.back();">취소</button>
	                </div>
	                <!-- /.card-footer -->

	              </div>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
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