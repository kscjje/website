<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	$(document).ready(function(){
	    //Datemask dd/mm/yyyy
		$('.datetype').inputmask({ mask: "y-1-2", leapday: "-02-29", placeholder: "yyyy-mm-dd", separator: "-", alias: "yyyy-mm-dd" });
		$('input.numberchk').number(true);

		$( ".datetype" ).datepicker({
			"dateFormat":'yy-mm-dd',
			'monthNames':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			'monthNamesShort':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			'changeMonth' : true,
			'changeYear':true
		});

	    $('input[name=edcRsvnRectype]').click(function(){
	    	fn_click_event('edcRsvnRectype' ,  $(this).val());
	    });

	    $('input[name=edcRsvnAccssrd]').click(function(){
	    	fn_click_event('edcRsvnAccssrd' ,  $(this).val());
	    });
	    $('input[name=edcCapaDvdyn]').click(function(){
	    	fn_click_event('edcCapaDvdyn' ,  $(this).prop("checked")?"Y":"N");
	    });
	    $('input[name=edcPaywaitGbn]').click(function(){
	    	fn_click_event('edcPaywaitGbn' ,  $(this).val());
	    });

	    $('#edcRsvnStimeHour').val("09");
	    $('#edcRsvnEtimeHour').val("09");

	   document.writeForm.targetOrgNo.value = document.searchVO.searchOrgNo.value;
	   document.writeForm.searchDate.value = document.searchVO.searchDate.value;
	   document.writeForm.searchStartDts.value = document.searchVO.searchStartDts.value;
	   document.writeForm.searchEndDts.value = document.searchVO.searchEndDts.value;
	   document.writeForm.searchRsvnsetNm.value = document.searchVO.searchRsvnsetNm.value;
	   document.writeForm.searchOpen.value = document.searchVO.searchOpen.value;

	   <c:if test="${fn:indexOf(requestURI,'edcProgram') > 0 }">
	   document.writeForm.searchFree.value = document.searchVO.searchFree.value;
	   document.writeForm.searchRectype.value = document.searchVO.searchRectype.value;
	   </c:if>
	   document.writeForm.searchCondition.value = document.searchVO.searchCondition.value;
	   document.writeForm.searchKeyword.value = document.searchVO.searchKeyword.value;


	    if( "${mode}" == "EDIT"){
			$('#writeForm').find("input[type=text]").prop("disabled" , true);
			$('#writeForm').find("input[type=radio]").prop("disabled" , true);
			$('#writeForm').find(".edcDaygbn").prop("disabled" , true);
			$('#writeForm').find(".time").prop("disabled" , true);
			$('#writeForm').find("input[name=edcCapaDvdyn]").prop("disabled" , true);


			$('#writeForm').find("input[name=changeColumn]").click(function(){
				var checked = $(this).prop("checked");

				$('#writeForm').find("input[name="+ $(this).val()+"]").prop("disabled" , !checked).focus();

				if($(this).val() == "CtgCd"){
					$('#CtgCdBtn').prop("disabled" , !checked);
					if(checked){
						$('#CtgCdBtn').click(function(){
							fn_open_comctg('chg');
						});
					}else{
						$('#CtgCdBtn').off("click");
					}
				}else if("edcRsvnSdate" == $(this).val()){
					$('#writeForm').find("select[name=edcRsvnStimeHour]").prop("disabled" , !checked);
					$('#writeForm').find("select[name=edcRsvnStimeMin]").prop("disabled" , !checked);
					$('#writeForm').find("select[name=edcRsvnEtimeHour]").prop("disabled" , !checked);
					$('#writeForm').find("select[name=edcRsvnEtimeMin]").prop("disabled" , !checked);
					$('#writeForm').find("input[name=edcRsvnEdate]").prop("disabled" , !checked);
				}else if("edcSdate" == $(this).val()){
					$('#writeForm').find("input[name=edcEdate]").prop("disabled" , !checked);
				}else if("dayChk" == $(this).val()){
					document.getElementById("edcDaysList0.dayChk1").disabled = 	!checked;
					document.getElementById("edcDaysList1.dayChk1").disabled = 	!checked;
					document.getElementById("edcDaysList2.dayChk1").disabled = 	!checked;
					document.getElementById("edcDaysList3.dayChk1").disabled = 	!checked;
					document.getElementById("edcDaysList4.dayChk1").disabled = 	!checked;
					document.getElementById("edcDaysList5.dayChk1").disabled = 	!checked;
					document.getElementById("edcDaysList6.dayChk1").disabled = 	!checked;
				}else if("edcStime" == $(this).val()){
					$('#writeForm').find("select[name=edcStimeHour]").prop("disabled" , !checked);
					$('#writeForm').find("select[name=edcStimeMin]").prop("disabled" , !checked);
					$('#writeForm').find("select[name=edcEtimeHour]").prop("disabled" , !checked);
					$('#writeForm').find("select[name=edcEtimeMin]").prop("disabled" , !checked);

				}else if("edcPncpa" == $(this).val()){
					$('#writeForm').find("input[name=edcCapaDvdyn]").prop("disabled" , !checked);

				}else if("edcPaywaitGbn" == $(this).val()){


				}

			});

	    }

	});
</script>
<form name="writeForm" id="writeForm">
<input type="hidden" name="mode" id="mode" value="${mode }" />
<input type="hidden" name="targetOrgNo"/>
<input type="hidden" name="searchDate"/>
<input type="hidden" name="searchStartDts"/>
<input type="hidden" name="searchEndDts"/>
<input type="hidden" name="searchRsvnsetNm"/>
<input type="hidden" name="searchOpen"/>
<input type="hidden" name="searchFree"/>
<input type="hidden" name="searchRectype"/>
<input type="hidden" name="searchCondition"/>
<input type="hidden" name="searchKeyword"/>

 	<div class="row">
	              <table id="modaltable" class="table table-bordered">
	              		<colgroup>
	        				<col width="15%">
	        				<col width="35%">
	        				<col width="15%">
	        				<col width="35%">
	        			</colgroup>
              			<tr>
              				<th>
              					<c:choose>
              						<c:when test="${mode eq 'EDIT' }">
		              					<div class="icheck-primary d-inline">
		              						<input type="checkbox" name="changeColumn" id="changeColumn1" value="edcRsvnsetNm"><label for="changeColumn1">모집차수명</label>
		              					</div>
		              				</c:when>
		              				<c:otherwise>
		              					모집차수명
		              				</c:otherwise>
              					</c:choose>
              				</th>
              				<td>
              					<input type="text" id="edcRsvnsetNm" name="edcRsvnsetNm"  value="" title="모집차수명" maxlength="50" style="width:100%"/>
              				</td>
              				<th>
								<c:choose>
              						<c:when test="${mode eq 'EDIT' }">
		              					<div class="icheck-primary d-inline">
		              						<input type="checkbox" name="changeColumn" id="changeColumn12" value="edcRsvnLinkurl"><label for="changeColumn12">접수 URL</label>
		              					</div>
		              				</c:when>
		              				<c:otherwise>
		              					접수 URL
		              				</c:otherwise>
              					</c:choose>
							</th>
							<td>
								<input type="text" name="edcRsvnLinkurl" id="edcRsvnLinkurl" value="" title="접수 URL"  maxlength="1000" style="width:100%" />
							</td>
              			</tr>

              			<tr>
              				<th>
              					<c:choose>
              						<c:when test="${mode eq 'EDIT' }">
		              					<div class="icheck-primary d-inline">
		              						<input type="checkbox" name="changeColumn" id="changeColumn5" value="edcRsvnSdate"><label for="changeColumn5">접수 기간</label>
		              					</div>
		              				</c:when>
		              				<c:otherwise>
		              					접수 기간
		              				</c:otherwise>
              					</c:choose>
              				</th>
              				<td colspan="3">

              						시작 :
              						<input type="text" name="edcRsvnSdate" id="edcRsvnSdate"  title="시작일" maxlength="10" class="datetype" style="width:100px"/>
              						<select name="edcRsvnStimeHour" id="edcRsvnStimeHour" class="time">
 <c:forEach var="i" begin="0" end="23">
                    				<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</option>
 </c:forEach>
 									</select>
 									<select id="edcRsvnStimeMin" name="edcRsvnStimeMin" class="time">
 <c:forEach var="i" begin="0" end="50" step="10">
                    				<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</option>
 </c:forEach>
 									</select>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

									종료 :
 									<input type="text"  id="edcRsvnEdate" name="edcRsvnEdate" value="" title="종료일" maxlength="10" class="datetype" style="width:100px"/>
 									<select id="edcRsvnEtimeHour" name="edcRsvnEtimeHour" class="time">
 <c:forEach var="i" begin="0" end="23">
                    				<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</option>
 </c:forEach>
 									</select>
 									<select id="edcRsvnEtimeMin" name="edcRsvnEtimeMin" class="time">
 <c:forEach var="i" begin="0" end="50" step="10">
                    					<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</option>

 </c:forEach>
 									</select>
 								</div>

              				</td>
              			</tr>
              			<tr>
              				<th>
              					<c:choose>
              						<c:when test="${mode eq 'EDIT' }">
		              					<div class="icheck-primary d-inline">
		              						<input type="checkbox" name="changeColumn" id="changeColumn6" value="edcSdate"><label for="changeColumn6">교육기간</label>
		              					</div>
		              				</c:when>
		              				<c:otherwise>
		              					교육기간
		              				</c:otherwise>
              					</c:choose>
              				 </th>
              				<td>
              					<input type="text" id="edcSdate" name="edcSdate"  value="" title="시작일" maxlength="10" class="datetype" style="width:100px"/>
              					~
              					<input type="text" id="edcEdate" name="edcEdate" title="종료일" value="" maxlength="10" class="datetype" style="width:100px"/>
              				</td>
              				<th>
              					<c:if test="${fn:indexOf(requestURI,'edcProgram') > 0 }">
									<c:choose>
	              						<c:when test="${mode eq 'EDIT' }">
			              					<div class="icheck-primary d-inline">
			              						<input type="checkbox" name="changeColumn" id="changeColumn13" value="drwtNtcedate"><label for="changeColumn13">추첨발표일</label>
			              					</div>
			              				</c:when>
			              				<c:otherwise>
			              					추첨발표일
			              				</c:otherwise>
	              					</c:choose>
	              				</c:if>
							</th>
							<td>
								<input type="text" id="drwtNtcedate" name="drwtNtcedate" value="" title="추첨 발표일" class="datetype" maxlength="10" />
							</td>
              			</tr>
<c:if test="${fn:indexOf(requestURI,'edcProgram') > 0 }">
						<tr class="intype">
							<th>
								<c:choose>
              						<c:when test="${mode eq 'EDIT' }">
		              					<div class="icheck-primary d-inline">
		              						<input type="checkbox" name="changeColumn" id="changeColumn11" value="edcPaywaitGbn"><label for="changeColumn11">입금대기 마감설정</label>
		              					</div>
		              				</c:when>
		              				<c:otherwise>
		              					입금대기 마감설정
		              				</c:otherwise>
              					</c:choose>
							</th>
							<td colspan="3">
								<div class="form-check" style="display:inline-block">
		                          <input type="radio" name="edcPaywaitGbn" id="edcPaywaitGbn1" class="form-check-input" value="1001"/>
		                          <label for="edcPaywaitGbn1" class="form-check-label">신청시간 기준 대기시간 설정</label>
		                        </div>
		                        <div class="form-check" style="display:inline-block">
		                          <input type="radio" name="edcPaywaitGbn" id="edcPaywaitGbn2" class="form-check-input" value="2001"/>
		                          <label for="edcPaywaitGbn2" class="form-check-label">마감일시 직접 설정</label>
		                        </div>

		                        <div id="edcPaywaitGbn_1001" style="padding-top:5px">
		                        	입금대기 마감시간 : <input type="text" name="edcPaywaitTime" id="edcPaywaitTime" title="대기 시간"  value=""  style="width:100px" maxlength="4" class="numberchk"/> 분 (최소 60분이상 설정)
		                        </div>
		                        <div id="edcPaywaitGbn_2001" style="padding-top:5px">
		                        	입금 마감일시 직접설정 :
		                        	<input type="text" name="edcPaywaitDate" id="edcPaywaitDate" title="마감일" value="${edcProgramVO.edcPaywaitDate}" class="datetype" style="width:100px" maxlength="10" />&nbsp;&nbsp;
                    			<select id="edcPaywaitHour" name="edcPaywaitHour" class="time">
 <c:forEach var="i" begin="0" end="23">
                    				<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</option>

 </c:forEach>
 									</select>
&nbsp;
                    			<select id="edcPaywaitMin" name="edcPaywaitMin" class="time">
 <c:forEach var="i" begin="0" end="50" step="10">
                    				<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</option>

 </c:forEach>
 									</select>
		                        </div>
							</td>
						</tr>
</c:if>
              		</table>
	</div>
</form>