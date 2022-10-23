<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
     <!-- 회원정보조회 -->
     <div class="modal fade" id="modal-member">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">통합회원검색</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>

            <div class="modal-body">
				<div class="card card-primary card-outline">
			 	 	<div class="card-body">
            			<form name="modalMemberSearchVO" id="modalMemberSearchVO" method="get">
            			<input type="hidden" name="pageIndex" id="pageIndex" value="1"/>
			            <div class="row" style="margin-top:10px">
			            	<div class="col-4">
					            <select name="searchCondition" id="searchCondition" title="모집차수" class="form-control select">
			             			<option value="1">회원명</option>
			             			<option value="3">휴대폰번호</option>
			             			<option value="2">ID</option>
								</select>
							</div>
							<div class="col-8">
								<input type="text" class="form-control" name="searchKeyword" id="searchKeyword" maxlength="13" placeHolder="검색할 회원명을 두글자이상 입력하세요"/>
							</div>
			            </div>
		 				</form>
			 	   	</div>
		            <div class="row" style="margin-bottom:15px;text-align:right">
			 	    	<div class="col-4"></div>
			 	    	<div class="col-2">
			 	    		<button class="btn btn-secondary btn-block" onclick="fn_modal_reset('modalMemberSearchVO')">초기화</button>
			 	    	</div>
			 	    	<div class="col-2">
			 	    		<button class="btn btn-primary btn-block" onclick="fn_get_member_list()">조회</button>
			 	    	</div>
			 	    	<div class="col-5"></div>
			 	     </div>
		 	   	</div>

		 	   	<div class="row">
		          <div class="col-12">
		            <div class="card" id="modalMemberCardDiv">
		              <!-- /.card-header -->
		              <div class="card-body table-responsive p-0">
			               <table class="table table-bordered table-hover text-nowrap" style="text-align:center" id="dataTable">
			               	 <colgroup>
								<col style="width: 5%;">
								<col style="width: 15%;">
								<col style="width: 30%;">
								<col style="width: 15%;">
								<col style="width: 20%;">
								<col style="width: *">
							  </colgroup>
			                  <thead>
			                    <tr>
			                      	<th></th>
			                      	<th>회원번호</th>
			                      	<th>회원명</th>
			                      	<th>생년월일</th>
			                      	<th style="cursor:pointer">휴대폰번호 ▼</th>
			                      	<th>ID</th>
			                      	<th>성별</th>
			                    </tr>
			                  </thead>
			                  <tbody>
			                  	<tr>
									<td colspan="7" align="center">조건 선택 후 조회하세요</td>
								</tr>
			                  </tbody>
			                </table>
		              </div>
		          </div>
		        </div>
		      </div>

            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" id="onclick-btn" class="btn btn-primary" onclick="fn_pick_member()">선택회원적용</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>

	<script>
		$(document).ready(function() {
			$("#searchCondition").change(function() {
				var placeholder = "검색할 회원명을 두글자이상 입력하세요";
				if ($(this).val() == "3") {
					placeholder = "검색할 휴대폰번호 전체 또는 뒤 4자리를 '-' 특수기호없이 입력하세요'";
				} else if ($(this).val() == "2") {
					placeholder = "ID를 입력하세요";
				}
				$("#searchKeyword").val("");
				$("#searchKeyword").attr("placeholder", placeholder);
			});

			$("#searchKeyword").keydown(function(e) {
				if (e.keyCode == 13) {
					e.preventDefault();
					fn_get_member_list();
				}
			});
			$("#searchKeyword").keyup(function() {
				var searchCond = $("#searchCondition").val();
				var keyword = $(this).val();

				if (searchCond == "3") {
					if (/[^0123456789]/g.test(keyword)) {
						var keyword = keyword.replace(/[^0-9]/g, "");
						$("#searchKeyword").val(keyword);
						alert("숫자만 입력가능합니다.");
					}
				}
			});
		});

		function fn_modal_reset(objid) {
			$("#" + ojbid)[0].reset();
			fn_get_member_list();
		}

		function fn_get_member_list() {
			var searchCond = $("#searchCondition").val();
			var keyword = $("#searchKeyword").val();

			var alertmsg = "";
			if (searchCond == "3") {
				if (keyword.length < 4) {
					alertmsg = "휴대폰번호를 4자리 이상 입력해 주세요.";
				}
			} else if (searchCond == "1") {
				if (keyword.length < 2) {
					alertmsg = "회원명을 2자리 이상 입력해 주세요.";
				}
			} else if (searchCond == "2") {
				if (keyword.length < 2) {
					alertmsg = "ID를 2자리 이상 입력해 주세요.";
				}
			}

			if (alertmsg) {
				alert(alertmsg);
				$("#searchKeyword").focus();
				return;
			}

			$.get({
			    url : './edcMemberListAjax',
			    data : $('#modalMemberSearchVO').serialize(),
				dataType : 'html',
				success: function(data) {
			    	$("#modalMemberCardDiv").html(data);
				}
		    });
		}

		function fn_set_page(page) {
			var page = page > 0 ? page : 1;
			$('#pageIndex').val(page);
			fn_get_member_list();
		}

		function fn_pick_member() {
			var memNo = $("input:radio[name='pick']:checked").val();
			if (!memNo) {
				alert("적용하고자하는 회원을 선택해 주세요.");
				return;
			}
			$("#edcRsvnMemno").val(memNo);
			$("#edcRsvnCustnm").val($("input:radio[name='pick']:checked").parent().parent().find("td:eq(2)").text());
			$("#edcRsvnMoblphon").val($("input:radio[name='pick']:checked").parent().parent().find("td:eq(4)").text());
			$("#idTd").text($("input:radio[name='pick']:checked").parent().parent().find("td:eq(5)").text());
			$("#edcRsvnBirthdate").val($("input:radio[name='pick']:checked").parent().parent().find("td:eq(3)").text());
			$("#birthSec").val($("input:radio[name='pick']:checked").data("birthsec"));
			$("#edcRsvnGender").val($("input:radio[name='pick']:checked").data("gender"));
			$("#tmpJoindate").val($("input:radio[name='pick']:checked").data("joindate"));

			fn_init_dc_params();
			$("#modal-member").find(".close").trigger("click");
		}

	</script>