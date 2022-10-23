<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
     <!-- 카드선택 및 승인번호 입력 -->
     <div class="modal fade" id="modal-card">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">카드(전표입력)결제</h4>
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
			              <table class="table table-bordered table-hover text-nowrap" style="text-align:left">
			               	 <colgroup>
								<col style="width: 30%;">
								<col style="width: 70%;">
							  </colgroup>
			                  <tbody>
			                  	<tr>
			                      	<th>수강료</th>
			                      	<td id="saleAmtTd"></td>
								</tr>
								<tr>
			                      	<th>할인금액</th>
			                      	<td id="dcAmtTd"></td>
								</tr>
								<tr>
			                      	<th>결제예상금액</th>
			                      	<td id="payAmtTd"></td>
								</tr>
								<tr>
			                      	<th>결제일자</th>
			                      	<td id="payDateTd"></td>
								</tr>
								<tr>
			                      	<th>카드종류<span class="pilsu">*</span></th>
			                      	<td>
				                      	<select id="cardCd" name="cardCd" title="성별선택" class="form-control select2" style="width:200px;float:left;margin-right:2px">
				                      		<c:forEach items="${cardList}" var="item" varStatus="status">
					                      		<option value="${item.methodCd}">${item.methodNm}</option>	
					                      	</c:forEach>
										</select>
			                      	</td>
								</tr>
								<tr>
			                      	<th>승인번호<span class="pilsu">*</span></th>
			                      	<td><input type="text" value="" class="form-control" id="certNo" name="certNo" maxlength="15" size="30" placeholder="승인번호를 입력해 주세요"/></td>
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
              <button type="button" id="onclick-btn" class="btn btn-primary" onclick="fn_rsvn_register('4001')">결제완료</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
            
	<script>
	$(document).ready(function() {
		$("#certNo").keyup(function() {
			$(this).val($(this).val().replace(/[^0-9-]/g, ""));
		});
	});
	</script>