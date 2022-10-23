<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
     <!-- 감면종류 -->
     <div class="modal fade" id="modal-dc">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">감면종류 적용선택</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            
            <div class="modal-body">
				
			   <div class="row">
		          <div class="col-12">
		            <div class="card" id="modalMemberCardDiv">
		              <!-- /.card-header -->
		              <div class="card-body table-responsive p-0">
			               <table class="table table-bordered table-hover text-nowrap" style="text-align:center" id="dataTable">
			               	 <colgroup>
								<col style="width: 20%;">
								<col style="width: 30%;">
								<col style="width: 20%;">
								<col style="width: 30%;">
							  </colgroup>
			                  <tbody>
			                  	<tr>
			                      	<th>회원명</th>
			                      	<td id="dcMemnmTd"></td>
			                      	<th>아이디</th>
			                      	<td id="dcMemIdTd"></td>
								</tr>
								<tr>
			                      	<th>성별</th>
			                      	<td id="dcMemGenderTd"></td>
			                      	<th>핸드폰번호</th>
			                      	<td id="dcMemHpTd"></td>
								</tr>
								<tr>
			                      	<th>생년월일 / 양음</th>
			                      	<td id="dcMemBirthdateTd"></td>
			                      	<th>가입일시</th>
			                      	<td id="dcMemJoindateTd"></td>
								</tr>
			                  </tbody>
			                </table>
		              </div>
		             </div>
		          </div>
		          <div class="col-12">
		            <div class="card">
		              <!-- /.card-header -->
		              <div class="card-body table-responsive p-0" id="modalDcDiv">
		              </div>
		          </div>
		        </div>
		        </div>
      
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" id="onclick-btn" class="btn btn-primary" onclick="fn_pick_dc()">선택회원적용</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
            
	<script>
		function fn_pick_dc() {
			var dcReasonCd = $("input:radio[name='pickDc']:checked").val();
			var dcRate = $("input:radio[name='pickDc']:checked").data("dcrate");
			var dcReasonNm = $("input:radio[name='pickDc']:checked").data("dcnm");
			var dcSeq = $("input:radio[name='pickDc']:checked").data("seq");
			var dcValidPeriod = $.trim($("#dcValidPeriod" + dcSeq).val());
			var dcValidPeriodNm = $.trim($("#dcValidPeriod" + dcSeq).text());
			
			if (!dcReasonCd) {
				alert("적용하고자하는 감면종류를 선택해 주세요.");
				return;
			}
			
			if ($("#edcRsvnMemno").val() && dcValidPeriodNm) {
				var registForm = $("<form></form>");
				registForm.attr("id","registForm");
				registForm.append($("<input/>", {type: "hidden", name: "orgNo", value: $("#orgNo").val()}));
				registForm.append($("<input/>", {type: "hidden", name: "memNo", value: $("#edcRsvnMemno").val()}));
				registForm.append($("<input/>", {type: "hidden", name: "discountCd", value: dcReasonCd}));
				registForm.append($("<input/>", {type: "hidden", name: "dcconfirmYn", value: "Y"}));
				
				if (dcValidPeriod) {
					var now = new Date();
					registForm.append($("<input/>", {type: "hidden", name: "dcconfSdate", value: now.toISOString().substring(0, 10).replaceAll("-", "")}));
					now.setMonth(now.getMonth() + parseInt(dcValidPeriod));
					registForm.append($("<input/>", {type: "hidden", name: "dcconfEdate", value: now.toISOString().substring(0, 10).replaceAll("-", "")}));
				}
				
				$.post({
					url : "../member/userTabDcSave.json",
					data : registForm.serialize(),
					success : function(data) {
						if (data.result.code == "ERROR") {
							alert(data.result.msg);
						} else {
							$("#edcReasondc").val(dcReasonCd);
							$("#dcRate").val(dcRate);
							$("#edcReasondcNm").val(dcReasonNm);

							fn_refresh_pay_amt();
							$(".close").trigger("click");
						}
					},
					error : function() {
						alert("서버에러");
					}
				});
			} else {
				$("#edcReasondc").val(dcReasonCd);
				$("#dcRate").val(dcRate);
				$("#edcReasondcNm").val(dcReasonNm);

				fn_refresh_pay_amt();
				$(".close").trigger("click");
			}
		}
	</script>