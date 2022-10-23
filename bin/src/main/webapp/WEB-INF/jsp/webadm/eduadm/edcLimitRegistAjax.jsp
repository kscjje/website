<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : edcLimitRegistAjax.jsp
  * @Description :  신청횟수 제한 등록 JSP
  * @Modification Information
  * @
  * @  수정일              수정자            수정내용
  * @ -------       ------    ---------------------------
  * @ 2021.10.26    진수진           최초 생성
  *
  *  @author 진수진
  *  @since 2021.10.26
  *  @version 1.0
  *  @see
  *
  */
%>
<script>
	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_detail_save() {
		var form = document.edcLimitVO;

		if(form.orgNo.value == "" || form.orgNo.value == "0" ){
			alert("왼쪽 기관 목록에서 기관을 먼저 선택해 주세요.");
			return;
		}

		if($('input[name=edcRsvnlimitYn]:checked').val() == "Y"){
			if($('input[name=edcRsvnlmitGbn]:checked').length < 1){
				alert("제한 범위를 선택해 주세요.");
				form.edcRsvnlmitGbn[0].focus();
				return;
			}
			if($('input[name=edcRsvnlimitPd]:checked').length < 1){
				alert("제한 기간을 선택해 주세요.");
				form.edcRsvnlimitPd[0].focus();
				return;
			}

			if($('input[name=edcRsvnlmitGbn]:checked').val() == "2001" && $('input[name=selectedCtgcd]').length < 1){
				alert("프로그램 분야를 최소 1개 이상 선택해 주셔야 합니다.");
				return;
			}
			if($('input[name=edcRsvnlmitGbn]:checked').val() == "3001" && $('input[name=selectedPrgmid]').length < 1){
				alert("프로그램을 최소 1개 이상 선택해 주셔야 합니다.");
				return;
			}

		}

		if(confirm("설정을 저장하시겠습니까?")){
			$.ajax({
			      url: './edcLimitRegistSave.json',
			      type : "POST",
			      dataType : "json",
			      data : $('#edcLimitVO').serialize(),
	              success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        }else{
				        	 var limitYn = $('input[name=edcRsvnlimitYn]:checked').val();
				        	 $('#orgListDiv').find("#ROW_"+document.edcLimitVO.orgNo.value).find("td").eq(4).text(limitYn);
				        	 alert(data.result.msg);
				        }
			      }
			});
		}

	}

	function fn_click_event(gubun, val){
		var form = document.edcLimitVO;

		if(gubun == "edcRsvnlimitYn"){
			if(val == "Y"){
				$('input[name=edcRsvnlmitGbn]').prop("disabled" ,  false);
				$('input[name=edcRsvnlimitPd]').prop("disabled" ,  false);
				$('input[name=edcRsvnlimitCnt]').prop("disabled" ,  false);

			}else{
				$('input[name=edcRsvnlmitGbn]').prop("checked" , false).prop("disabled" ,  true);
				$('input[name=edcRsvnlimitPd]').prop("disabled" ,  true);
				$('input[name=edcRsvnlimitCnt]').prop("disabled" ,  true);
				$('.limitAreaDiv').hide();
			}
		}else if(gubun == "edcRsvnlmitGbn"){

			$('.limitAreaDiv table').hide();
			$('#CTG_TABLE').hide();
			$('#PRGM_TABLE').hide();


			if(val== "1001"){
				//전체프로그램
				$('.limitAreaDiv').hide();
			}else{
				$('.limitAreaDiv').find(".card-tools button").off("click");

				if(val== "2001"){
					$('#TABLE_CTG_SELECTED').show();
					$('.limitAreaDiv').find(".card-title").text("※ 프로그램 분야 지정");
					$('.limitAreaDiv').find(".card-tools button").html("<i class=\"fas fa-plus\"></i> 프로그램 분야 추가").click(function(){
						if(form.orgNo.value == "" || form.orgNo.value == "0" ){
							alert("왼쪽 기관 목록에서 기관을 먼저 선택해 주세요.");
							return false;
						}

						$('#CTG_TABLE').find("input[name=chkCtgCd]").prop("checked" , false);
						$('#CTG_TABLE').find("input[name=chkCtgCd]").prop("disabled" , false);

						$.each($('#TABLE_CTG_SELECTED').find("input[name=selectedCtgcd]") , function(){
							$('#CTG_TABLE').find("input[name=chkCtgCd][value="+$(this).val()+"]").click();
						});

						 $('#MODAL_BTN').html("<button type='button' class='btn btn-primary btn-sm' onclick='fn_category_add()'>선택분야 추가</button>");
						 $('#CTG_TABLE').show();

					});

				}else{
					$('#TABLE_PRGM_SELECTED').show();
					$('.limitAreaDiv').find(".card-title").text("※ 교육프로그램  지정");
					$('.limitAreaDiv').find(".card-tools button").html("<i class=\"fas fa-plus\"></i> 교육프로그램 추가").click(function(){
						if(form.orgNo.value == "" || form.orgNo.value == "0" ){
							alert("왼쪽 기관 목록에서 기관을 먼저 선택해 주세요.");
							return false;
						}
						//교육프로그램 목록 나오기
						fn_search_program();

						$('#MODAL_BTN').html("<button type='button' class='btn btn-primary btn-sm' onclick='fn_program_add()'>선택프로그램 추가</button>");
						$('#PRGM_TABLE').show();

					});
				}
				$('.limitAreaDiv').show();
			}
		}
	}

	function fn_program_add(){
		$('#TABLE_PRGM_SELECTED').find("tbody").html("");

		$.each($('input[name=chkProgmid]:checked') , function(item,index){
			var html = "<tr><td><input type='hidden' name='selectedPrgmid' value='"+$(this).val()+"'/>";
			var ctgNm = $(this).parent().parent().find("td").eq(1).text();
			var prgNm = $(this).parent().parent().find("td").eq(2).text();
			var open = $(this).parent().parent().find("td").eq(3).text();

			html += ctgNm + "</td>";
			html += "<td>" + prgNm + "</td>";
			html += "<td>" + open + "</td>";
			html += "<td><button type=\"button\" class=\"btn btn-secondary btn-xs\" onclick=\"fn_row_delete(this)\"><i class=\"fas fa-times\"></i></button></td>";
			html += "</tr>";
			$('#TABLE_PRGM_SELECTED').find("tbody").append(html);
		});

		$('#modal-default').find("button.close").click();
	}

	function fn_category_add(){
		$('#TABLE_CTG_SELECTED').find("tbody").html("");

		$.each($('input[name=chkCtgCd]:checked') , function(item,index){
			var ctgCd = $(this).val();
			var upperCtgnm = "-";
			var ctgNm = $('input[name=listCtgCd][value='+ctgCd+']').parent().find("a").text();

			if(ctgCd.lastIndexOf("0000000") == "3"){
				//1depth
			}else if(ctgCd.lastIndexOf("0000") == "6"){
				//2depth
				var upper1 = ctgCd.substring(0,3) + "0000000";
				upperCtgnm = $('input[name=listCtgCd][value='+upper1+']').parent().find("a").text();
			}else{
				//3depth
				var upper2 = ctgCd.substring(0,6) + "0000";
				upperCtgnm = $('input[name=listCtgCd][value='+upper2+']').parent().find("a").text();

				var upper1 = ctgCd.substring(0,3) + "0000000";
				upperCtgnm = $('input[name=listCtgCd][value='+upper1+']').parent().find("a").text() + " > " + upperCtgnm;

			}


			var html = "<tr><td><input type='hidden' name='selectedCtgcd' value='"+ctgCd+"'/>";
			html += upperCtgnm + "</td>";
			html += "<td>" + ctgNm + "</td>";
			html += "<td><button type=\"button\" class=\"btn btn-secondary btn-xs\" onclick=\"fn_row_delete(this)\"><i class=\"fas fa-times\"></i></button></td>";
			html += "</td></tr>";
			$('#TABLE_CTG_SELECTED').find("tbody").append(html);
		});

		$('#modal-default').find("button.close").click();

	}

	function fn_block_child(obj){
		var val = $(obj).val();
		var checked = $(obj).prop("checked");

		$('#CTG_TABLE').find('tr.'+val).find("input[type=checkbox]").prop("checked" , false).prop("disabled" , checked);
	}
	function fn_select_category(comcd, ctgCd , obj){
		$(obj).parent().find("input[type=checkbox]").click();
	}

	function fn_row_delete(obj){
		$(obj).parent().parent().remove();
	}


	$(document).ready(function(){

		$('input[name=edcRsvnlimitYn]').click(function(){
			fn_click_event("edcRsvnlimitYn" , $(this).val());
		});

		$('input[name=edcRsvnlmitGbn]').click(function(){
			fn_click_event("edcRsvnlmitGbn" , $(this).val());
		});

		<c:if test="${edcLimitVO.edcRsvnlimitYn eq 'Y'}">
			fn_click_event("edcRsvnlimitYn" , "Y");
		</c:if>

		<c:if test="${!empty edcLimitVO.edcRsvnlmitGbn}">
			fn_click_event("edcRsvnlmitGbn" , "${edcLimitVO.edcRsvnlmitGbn}");
		</c:if>

	});

</script>
<div class="card card-primary card-outline">
              		<div class="card-header">
                		<h3 class="card-title">제한 설정</h3>
                	</div>

<form:form commandName="edcLimitVO" name="edcLimitVO" action="./edcLimitRegistSave" class="form-horizontal" method="post">
<form:hidden path="comcd" />
<form:hidden path="orgNo" />
				<div class="card-body">
                	<div class="form-group row">
                    <label for="edcRsvnlimitYn1" class="col-sm-2 col-form-label">* 신청횟수 제한</label>
                    <div class="col-sm-10" style="padding-top:5px">
							<div class="form-check" style="display:inline-block">
								<form:radiobutton path="edcRsvnlimitYn" cssClass="form-check-input" value="Y"/>
	                           <label class="form-check-label" for="edcRsvnlimitYn1">제한 설정(Y)</label>
	                        </div>
	                        <div class="form-check"  style="display:inline-block">
	                          	<form:radiobutton path="edcRsvnlimitYn" cssClass="form-check-input" value="N"/>
	                           	<label class="form-check-label" for="edcRsvnlimitYn2">제한 해제(N)</label>
	                        </div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="edcRsvnlimitGbn1" class="col-sm-2 col-form-label">제한 범위</label>
                    <div class="col-sm-10">
<c:forEach items="${limitGbn}" var="item" varStatus="status">
						<div class="form-check" style="display:inline-block">
							<form:radiobutton path="edcRsvnlmitGbn" cssClass="form-check-input" value="${item.cd }" disabled="true"/>
                           <label class="form-check-label" for="edcRsvnlmitGbn${status.index+1 }">${item.cdNm}</label>
                        </div>
</c:forEach>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="edcRsvnlimitPd1" class="col-sm-2 col-form-label">제한 기간 설정</label>
                    <div class="col-sm-10">
<c:forEach items="${pdGbn}" var="item" varStatus="status">
						<div class="form-check" style="display:inline-block">
							<form:radiobutton path="edcRsvnlimitPd" cssClass="form-check-input" value="${item.cd }" disabled="true"/>
                           <label class="form-check-label" for="edcRsvnlimitPd${status.index+1 }">${item.cdNm}</label>
                        </div>
</c:forEach>
                    </div>
                  </div>

                  <div class="form-group row">
                    <label for="orgTel" class="col-sm-2 col-form-label">제한 횟수</label>
                    <div class="col-sm-10">
                    	<div class="input-group">
                    		<form:input path="edcRsvnlimitCnt" title="제한 횟수" class="form-control numberchk" maxlength="2" disabled="true"/>
                    		<div class="input-group-append">
		                      <span class="input-group-text">회</span>
		                    </div>
		                </div>
                    </div>
                  </div>



 			</div>
 			<div class="card-header limitAreaDiv" style="display:none">
            	<h3 class="card-title">※ 제한 범위 설정</h3>
            	<div class="card-tools">
  	                      <button type="button" class="btn btn-primary btn-sm"  data-toggle="modal" data-target="#modal-default">
	                        <i class="fas fa-plus"></i> 프로그램 추가
	                      </button>
 				</div>
            </div>
            <div class="card-body limitAreaDiv" style="display:none">
				<table  class="table table-bordered  table-sm" id="TABLE_CTG_SELECTED" style="display:none">
	              		<colgroup>
	        				<col width="50%">
	        				<col width="40%">
	        				<col width="10%">
	        			</colgroup>
	        			<thead>
	        				<th>상위분야</th>
	        				<th>프로그램분야</th>
	        				<th>삭제</th>
	        			</thead>
	        			<tbody>
<c:choose>
	<c:when test="${edcLimitVO.orgNo < 1 }">
							<tr>
	        					<td colspan="3">기관을 먼저 선택해 주세요</td>
	        				</tr>
	</c:when>
	<c:when test="${fn:length(ctgList) < 1 }">
							<tr>
	        					<td colspan="3">등록된 프로그램 분야가 없습니다.</td>
	        				</tr>
	</c:when>
	<c:otherwise>
<c:forEach items="${ctgList}" var="item" varStatus="status">
							<tr>
								<td>
									<input type="hidden" name="selectedCtgcd" value="${item.CtgCd }" />
									<c:if test="${item.CtgLvl > 1 }"><c:out value="${item.TopCtgNm }"/> > </c:if>
									<c:if test="${item.CtgLvl > 0 }"><c:out value="${item.TopParentCtgNm }"/></c:if>
									<c:if test="${item.CtgLvl == 0 }">-</c:if>
								</td>
								<td><c:out value="${item.comCtgnm }"/></td>
								<td>
									<button type="button" class="btn btn-secondary btn-xs" onclick="fn_row_delete(this)">
	                        			<i class="fas fa-times"></i>
	                      			</button>
	                      		</td>
							</tr>
</c:forEach>
	</c:otherwise>
</c:choose>

	        			</tbody>
	        	</table>

	        	<table  class="table table-bordered  table-sm" id="TABLE_PRGM_SELECTED" style="display:none">
	              		<colgroup>
	        				<col width="30%">
	        				<col width="50%">
	        				<col width="10%">
	        				<col width="10%">
	        			</colgroup>
	        			<thead>
	        				<th>분야</th>
	        				<th>프로그램명</th>
	        				<th>공개</th>
	        				<th>삭제</th>
	        			</thead>
	        			<tbody>
<c:choose>
	<c:when test="${edcLimitVO.orgNo < 1 }">
							<tr>
	        					<td colspan="4">기관을 먼저 선택해 주세요</td>
	        				</tr>
	</c:when>
	<c:when test="${fn:length(prgmList) < 1 }">
							<tr>
	        					<td colspan="4">등록된 프로그램이 없습니다.</td>
	        				</tr>
	</c:when>
	<c:otherwise>
<c:forEach items="${prgmList}" var="item" varStatus="status">
							<tr>
								<td><c:out value="${item.comCtgNm }"/></td>
								<td>
									<input type="hidden" name="selectedPrgmid" value="${item.edcPrgmid }" />
									<c:out value="${item.edcPrgmnm }"/>
								</td>
								<td><c:out value="${item.edcOpenyn }"/></td>
								<td>
									<button type="button" class="btn btn-secondary btn-xs" onclick="fn_row_delete(this)">
	                        			<i class="fas fa-times"></i>
	                      			</button>
	                      		</td>
							</tr>
</c:forEach>
	</c:otherwise>
</c:choose>

	        			</tbody>
	        	</table>
            </div>

 			 <div class="card-footer">

					<a class="btn btn-primary btn-sm form-control <c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>" href="javascript:fn_detail_save()">저장</a>

             </div>
 </form:form>

 </div>
