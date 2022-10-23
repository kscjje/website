<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
     <!-- SMS전송 -->
      <div class="modal fade" id="modal-sms">
        <div class="modal-dialog modal-xl">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">SMS전송</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="programClose">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            
            
      		<div class="modal-body" id="smsModalBody">
		 	   	
		 	  <div class="row">
			 
			 	<!-- 프로그램 목록 -->
	            <div class="col-md-7">
					<div class="card card-primary card-outline" >
						<div class="card-body p-0">
				 	 	   <div class="col-12">
				              <!-- /.card-header -->
			                  <table class="table">
			                  		<colgroup>
										<col style="width:10%;">
										<col style="width:20%;">
										<col style="width:20%">
										<col style="width:30%">
										<col style="width:*">
									</colgroup>
									<tbody>
					                  <tr>
						                  <td style="padding:0px;padding-top:20px;padding-bottom:0px;text-align:center;">이름</td>
						                  <td style="padding-left:0px;padding-bottom:0px;">
						                  	<input type="text" class="form-control" id="custNm" maxlength="20" size="50" placeHolder="이름입력"/>
						                  </td>
						                  <td style="padding-top:20px;padding-bottom:0px;text-align:right;">휴대폰번호</td>
						                  <td style="padding-bottom:0px;padding-right:0px">
						                  	<input type="text" class="form-control" id="custHp" maxlength="13" placeHolder="휴대폰번호입력"/>
						                  </td>
						                  <td style="padding-top:13px;text-align:right" >
						                  	<button class="btn btn-secondary btn-default" onclick="fn_add_list()">추가</button>
						                  </td>
					                  </tr>
					               </tbody>
			                  </table>
			               </div>
			           </div>
			 	   	</div>
			 	   	
			 	   	<div class="row">
			 	   		<div class="card-body table-responsive p-0" style="overflow:scrollx; height:400px;">
				 	 	  <div class="col-12">
				              <!-- /.card-header -->
			                   <table class="table table-bordered text-nowrap" style="text-align:center">
				               	   <colgroup>
										<col style="width: 10%;">
										<col style="width: 20%;">
										<col style="width: 25%">
										<col style="width: 20%">
										<col style="width: *">
									</colgroup>
									<thead>
										<tr>
											<th style='padding-left:10px'>선택</th>
											<th>회원명</th>
											<th>휴대폰번호</th>
											<th>회원번호</th>
											<th>비고</th>
										</tr>
									</thead>
					                <tbody id="smsTargetListTbody" onchange="fn_refresh_sms_list_cnt()">
										<tr>
											<td colspan="5">리스트가필요해요.</td>
										</tr>
					                </tbody>
				              </table>
				           </div>
				       </div>
				    </div>
				    
				    <div class="row">
			 	   		<div class="card-body table-responsive p-0">
				 	 	  <div class="col-12" style="background-color:#eeeeee;margin-top:30px;text-align:center">
				             	<h6>전송대상회원: <strong id="smsListCntStrong">0</strong> 명</h6> 
				          </div>
				       </div>
				    </div>
				    
			 	 </div>
			 	   	
			 	 <!-- 발송메시지입력 -->
			 	 <div class="col-md-5">
			 	   	<div class="card card-primary card-outline">
			 	   	  <div class="card-body" style="padding-bottom:15px">
			 	   	  <form name="smsForm" id="smsForm">
			 	   	    <div class="row">
				 	   	  	<div class="col-12" style="text-align:center">
				 	   	  		90byte부터 장문문자(LMS)로 전환됩니다.
				 	   	  	</div>
			 	   	  	</div>
			 	   	  	<div class="row">
				 	   	  	<div class="col-12" style="text-align:center;padding-bottom:20px">
				 	   	  		[<span id="byteSpan">0</span>] byte
				 	   	  	</div>
			 	   	  	</div>
			 	   	  	<div class="card-body table-responsive p-0" style="height:280px;">
				 	 		<div class="col-12">
				 	 			<textarea id="sendMsg" rows="10" cols="75" onkeyup="byteCheck()" class="form-control textarea" maxlength="300" placeholder="메시지를 입력하세요" validate="required;"></textarea>
				        	</div>
				     	</div>
				     	
				     	<div class="row">
				     		<div class="col-12">
				              <!-- /.card-header -->
			                  <table style="text-align:left">
			                  		<colgroup>
										<col style="width:20%">
										<col style="width:40%">
										<col style="width:*">
									</colgroup>
									<tbody>
					                  <tr style="height:45px">
						                  <td>발신번호</td>
						                  <td colspan="2">
						                  	<input type="text" class="form-control" name="senderTelno" id="senderTelno" size="30" value="" placeHolder="발신번호를 입력하세요" maxlength="13" validate="required;"/>
						                  </td>
					                  </tr>
					                  <tr style="height:45px">
						                  <td>전송방식</td>
						                  <td colspan="2">
						                  	<input type="radio" class="form-controlx" name="rsvnSendYn" id="rsvnSendN" checked="true" value="N"/><label for="rsvnSendN">즉시전송</label>
						                  	<input type="radio" class="form-controlx" name="rsvnSendYn" id="rsvnSendY" value="Y"/><label for="rsvnSendY">예약전송</label>
						                  </td>
					                  </tr>
					                  <tr style="height:45px">
						                  <td>예약전송일시</td>
						                  <td>
						                  	<input id="rsvnDate" name="rsvnDate" style="width:110px;float:left" readonly="readonly"  class="form-control datepicker" type="text" value="" maxlength="10" autocomplete="off"/>
						                  </td>
						                  <td>
						                  	 <select id="rsvnHour" name="rsvnHour" title="시간선택" class="form-control select" style="width:80px;float:left;margin-right:2px">
										 	 	${hisco:makeOptions("CM_HOUR", "시", "")}
										 	 </select>
										 	 <select id="rsvnMinute" name="rsvnMinute" title="시간선택" class="form-control select" style="width:80px;float:left">
										 	 	${hisco:makeOptions("CM_MINUTE", "분", "")}
										 	 </select>
										 	 <div></div>
						                  </td>
					                  </tr>
					               </tbody>
			                  </table>
			               </div>
				     	</div>
				     	</form>
				     	
			 	 	  </div>
			 	 	  
				  </div>
				  
	      		</div>
	      
			</div> <!-- /.row -->
		
        	<div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" id="onclick-btn" class="btn btn-primary" onclick="fn_send_sms()">발송하기</button>
            </div>
            
            <script src="${requestScope.resourceContext}/js/XE_validate.js"></script>
            <script>
				$(document).ready(function() {
					$('.datepicker').datepicker({
				        autoclose: true,
				        //calendarWeeks: false,
				        todayHighlight: true,
				        minDate: 0,
				        showOn:"button",
				        buttonImage:"${requestScope.resourceContext}/images/ico_select_date.png",
				        buttonImageOnly:true
				    });
					$("#custHp").inputmask("999-9999-9999");
					$('#senderTelno').bind('keyup blur', function(e){
						phoneFomatter($(this));
					});
					window.setupValidateForm(smsForm);
				});
				
				function fn_set_sms_list() {
					var html = [];
					$(".sms-target-table :checkbox").each(function() {
						if ($(this).prop("checked") && $(this).data("memhp")) {
							var memsmsyn = $.trim($(this).data("memsmsyn"));
							var memhp = $.trim($(this).data("memhp"));
							
							var bigo = "";
							if (memsmsyn == "N") bigo = "전송불가(SMS수신미동의)";							
							if (!memhp) bigo = "전송불가(휴대폰미등록)";
							
							html.push("<tr>");
							if (bigo) {
								html.push("	<td style='padding-left:10px'> </td>");
							} else {
								html.push("	<td style='padding-left:10px'><input type='checkbox' checked='checked' value=''/></td>");
							}
							html.push("	<td>" + $(this).data("memnm") + "</td>");
							html.push("	<td>" + $(this).data("memhp") + "</td>");
							html.push("	<td>" + $(this).data("memno") + "</td>");
							html.push("	<td>" + bigo + "</td>");
							html.push("</tr>");
						}
					});
					$("#smsTargetListTbody").html(html.join(""));
					fn_refresh_sms_list_cnt()
				}
				
				function fn_refresh_sms_list_cnt() {
					$("#smsListCntStrong").text($("#smsTargetListTbody :checkbox:checked").length);
				}
				
				function fn_add_list() {
					var memnm = $("#custNm").val().trim();
					var memhp = $("#custHp").val().replaceAll("_", "");
					if (!memnm) {
						alert("이름을 입력해 주세요.");
						$("#custNm").focus();
						return;
					}
					if (!memhp) {
						alert("휴대폰번호를 입력해 주세요.");
						$("#custHp").focus();
						return;
					}
					
					if ($("#smsTargetListTbody").html().indexOf(memhp) > 0) {
						alert("목록에 존재하는 휴대폰번호입니다.");
						return;
					}
					
					var html = [];
					html.push("<tr>");
					html.push("	<td style='padding-left:10px'><input type='checkbox' checked='checked' value=''/></td>");
					html.push("	<td>" + memnm + "</td>");
					html.push("	<td>" + memhp + "</td>");
					html.push("	<td></td>");
					html.push("	<td></td>");
					html.push("</tr>");
					$("#smsTargetListTbody").prepend(html.join(""));
					fn_refresh_sms_list_cnt()
				}
				
				function byteCheck() {
					$("#byteSpan").text(getByteLength($("#sendMsg").val()));
				}
				
				function fn_send_sms() {					
					if ($("#smsTargetListTbody input[type=checkbox]:checked").length == 0) {
						alert("전송대상회원을 선택해 주세요.");
						return;
					}
					
					var sendMsg = $.trim($("#sendMsg").val());
					var rsvnSendYn = $("input[name=rsvnSendYn]:checked").val();
					var senderTelno = $("#senderTelno").val().trim();
					var rsvnDate = $("#rsvnDate").val().trim();
					var rsvnHour = $("#rsvnHour").val().trim();
					var rsvnMinute = $("#rsvnMinute").val().trim();
					var rsvnDtime = rsvnDate.replaceAll("-", "") + rsvnHour + rsvnMinute;
					
					if (!smsForm.validateForm()) return false;
					
					var phoneRule = /^\d{2,3}-\d{3,4}-\d{4}$/;
					if (!phoneRule.test(senderTelno)) {
						alert("유효하지 않는 전화번호입니다. 확인해 주세요.");
						$("#senderTelno").focus();
						return;
					}
					
					if (rsvnSendYn == "Y") { //예약발송
						if (!rsvnDate) {
							alert("예약전송일을 선택해 주세요.");
							$("#rsvnDate").trigger("click");
							return;
						}
						if (!rsvnHour) {
							alert("예약전송 시간을 선택해 주세요.");
							$("#rsvnHour").focus();
							return;
						}
						if (!rsvnMinute) {
							alert("예약전송 분을 선택해 주세요.");
							$("#rsvnMinute").focus();
							return;
						}
						if (rsvnDtime <= fn_get_yyyymmddhhmm()) {
							alert("예약전송시간이 현재보다 빠릅니다. 다시 설정부탁드립니다.");
							return;
						}
					} else {
						rsvnDtime = "";
					}
					
					if (!confirm("발송하시겠습니까?")) return false;
					
					var data = {};
					var targetList = [];
					
					//타켓목록 추출
					$("#smsTargetListTbody tr").each(function() {
					    var checked = $(":checkbox", $(this)).prop("checked");
					    if (checked) {
					    	var target = {};
					        var memnm = $(this).find("td:eq(1)").text();
					        var memhp = $(this).find("td:eq(2)").text();
					        target.memnm = memnm;
					        target.memhp = memhp;
					        targetList.push(target);
					    }
					});
					
					//타겟목록 설정
					data.targetList = targetList;
					
					//필요정보 설정
					data.sendMsg = $("#sendMsg").val();
					data.senderTelno = $("#senderTelno").val();
					data.rsvnSendYn = rsvnSendYn;
					data.rsvnDtime = rsvnDtime;
						
					$.post({
					    url : '<c:url value="../sms/sendSms.json"/>',
					    data : JSON.stringify(data),
					    contentType : "application/json; charset=UTF-8",
					    async : false,
						success: function(data) {
							alert(data.result.msg);
						}
				    });
				}
				
				function fn_get_yyyymmddhhmm() {
			        var date = new Date();
			        var year = date.getFullYear().toString();
			        var month = date.getMonth() + 1;
			        month = month < 10 ? '0' + month.toString() : month.toString();
			        var day = date.getDate();
			        day = day < 10 ? '0' + day.toString() : day.toString();
			        var hour = date.getHours();
			        hour = hour < 10 ? '0' + hour.toString() : hour.toString();
			        var minites = date.getMinutes();
			        minites = minites < 10 ? '0' + minites.toString() : minites.toString();
			        return year + month + day + hour + minites;
			    }
				
			</script>
            
          </div> <!-- /#smsModalBody -->
          
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
    </div>
    <!-- /#modal-sms -->