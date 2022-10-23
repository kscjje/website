<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<!-- 방문회원가입 -->
     <div class="modal fade" id="modal-memberjoin" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">방문회원 등록</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
 <form name="registForm" id="registForm">
              <table class="table table-bordered" >
                  <tbody>
						<tr>
							<th><span class="pilsu">*</span> 이름 </th>
							<td class="left">
								<div class="input-group input-group-sm" style="width: 150px;float:left">
									<input type="text" name="memNm"  placeholder="이름 입력해주세요." title="이름" maxlength="20" class="form-control"/>
				                </div>
							</td>
						</tr>
						<tr>
							<th><span class="pilsu">*</span> 성별 </th>
							<td class="left">
								<div class="icheck-primary d-inline">
				                      	<input type="radio" name="gender" id="gender1" value="M"/>
				                        <label for="gender1">남자</label>
				                      </div>
				                      <div class="icheck-primary d-inline">
				                       <input type="radio" name="gender" id="gender2" value="F"/>
				                        <label for="gender2">
				                        	여자
				                        </label>
				                      </div>
							</td>
						</tr>
						<tr>
							<th><span class="pilsu">*</span> 생년월일</th>
							<td class="left">
								<div class="input-group input-group-sm" style="width: 100px;float:left">
				                    <input type="text" name="birthDate"  title="생년월일" maxlength="10" class="form-control"/>
				                </div>
				                <div class="input-group input-group-sm" style="width: 100px;float:left">
				                    <select name="birthSec" class="form-control">
				                    	<option value="S">양력</option>
				                    	<option value="M">음력</option>
				                    </select>
				                </div>
							</td>
						</tr>

						<tr>
							<th><span class="pilsu">*</span> 휴대폰</th>
							<td  class="left">
								<div class="input-group input-group-sm" style="width: 200px;">
				                    <div class="input-group-prepend">
				                      <span class="input-group-text"><i class="fas fa-phone"></i></span>
				                    </div>
				                   <input type="text" name="hp"  title="휴대폰" maxlength="13" class="form-control"/>
				                  </div>
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td class="left">
								<div class="input-group input-group-sm" style="width: 200px;">
									<input type="text" name="email"  title="이메일" maxlength="100" class="form-control"/>
								</div>
							</td>
						</tr>
                  </tbody>
                </table>
 </form>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" onclick="fn_save()" class="btn btn-primary">저장</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
	<script type="text/javascript" src="../validator"></script>
	<validator:javascript formName="memberUserVO" staticJavascript="false" xhtml="true" cdata="false"/>
	<script>
	$(document).ready(function(){
		$('input[name=birthDate]').inputmask({ mask: "y-1-2", leapday: "-02-29", placeholder: "yyyy-mm-dd", separator: "-", alias: "yyyy-mm-dd" });

		$('input[name=hp]').bind('keyup blur', function(e){
			printHp($(this));
		});
	});

	function fn_save() {
		var form = document.registForm;
		var msg = "방문회원을 등록하시겠습니까?";

		if (!validateMemberUserVO(form)) {
			return;
		}

		if (confirm(msg)) {
			$.post({
				url : '../member/userSave.json',
				data : $('#registForm').serialize(),
				success : function(data) {
					alert(data.result.msg);
					if (data.result.code == "ERROR") {
					} else {
						var member = data.memberUserVO;
						$("#edcRsvnMemno").val(member.memNo);
						$("#edcRsvnCustnm").val(member.memNm);
						$("#edcRsvnMoblphon").val(member.hp);
						$("#idTd").text($.trim(member.id));
						$("#edcRsvnBirthdate").val(member.birthDate);
						$("#birthSec").val($.trim(member.birthSec));
						$("#edcRsvnGender").val(member.gender == "M" ? "1":"2");

						fn_init_dc_params();
						$("#modal-memberjoin").find(".close").trigger("click");
					}
				}
			});
		}
	}
	</script>