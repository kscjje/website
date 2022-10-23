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

	    //수정시 초기화 시키기
	    <c:if test="${!empty edcProgramVO.edcRsvnRectype}">
	    	fn_click_event('edcRsvnRectype' ,   "${edcProgramVO.edcRsvnRectype}");
	    </c:if>
	    <c:if test="${!empty edcProgramVO.edcRsvnAccssrd}">
	    	fn_click_event('edcRsvnAccssrd' ,   "${edcProgramVO.edcRsvnAccssrd}");
	    </c:if>
	    <c:if test="${!empty edcProgramVO.edcCapaDvdyn}">
	    	fn_click_event('edcCapaDvdyn' ,   "${edcProgramVO.edcCapaDvdyn}");
	    </c:if>

	    $('#edcRsvnStimeHour').val("${edcProgramVO.edcRsvnStimeHour}");
	    $('#edcRsvnStimeMin').val("${edcProgramVO.edcRsvnStimeMin}");
	    $('#edcRsvnEtimeHour').val("${edcProgramVO.edcRsvnEtimeHour}");
	    $('#edcRsvnEtimeMin').val("${edcProgramVO.edcRsvnEtimeMin}");

	    $('#edcStimeHour').val("${edcProgramVO.edcStimeHour}");
	    $('#edcStimeMin').val("${edcProgramVO.edcStimeMin}");
	    $('#edcEtimeHour').val("${edcProgramVO.edcEtimeHour}");
	    $('#edcEtimeMin').val("${edcProgramVO.edcEtimeMin}");

	    $('#edcPaywaitHour').val("${edcProgramVO.edcPaywaitHour}");
	    $('#edcPaywaitMin').val("${edcProgramVO.edcPaywaitMin}");

	});
</script>
   					<div class="form-group row">
	                    <div class="col-sm-12">
	                    	<select id="edcRsvnsetSelect" class="form-control bg-gray" onchange="fn_change_rsvn(this.value)">
<c:forEach items="${rsvnsetList}" var="item" varStatus="status">
                    	 		<option value="${item.edcRsvnsetSeq }" <c:if test="${item.edcRsvnsetSeq eq edcProgramVO.edcRsvnsetSeq}">selected</c:if>>[${item.edcRsvnsetSeq }] ${item.edcRsvnsetNm } (<hisco:DateUtil datestr="${item.edcSdate }" format="yyyy-MM-dd"/>  ~ <hisco:DateUtil datestr="${item.edcEdate }" format="yyyy-MM-dd"/>)</option>
</c:forEach>

	                    	</select>
			            </div>
	                </div>
	                <div class="form-group row">
	                    <label for="comnm" class="col-sm-2 col-form-label">차수코드</label>
	                    <div class="col-sm-10">
	                    	<div class="input-group">
	                    		<input type="text" id="edcRsvnsetSeq" name="edcRsvnsetSeq" value="<c:out value="${edcProgramVO.edcRsvnsetSeq}"/>" title="차수코드" class="form-control" readonly placeHolder="자동 부여됩니다."/>
	                    		<div class="input-group-append">
		                      		<button class="btn btn-secondary" type="button" onclick="fn_add_rsvn()">신규모집</button>
		                    	</div>
		                	</div>
			            </div>
	               </div>

   					<div class="form-group row">
	                    <label for="comnm" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 모집 차수명</label>
	                    <div class="col-sm-10">
	                    	<div class="input-group">
	                    		<input type="text" id="edcRsvnsetNm" name="edcRsvnsetNm" value="<c:out value="${edcProgramVO.edcRsvnsetNm}"/>"  title="모집 차수명" class="form-control" maxlength="50" />
			                </div>
			            </div>

	                  </div>
					<div class="form-group row">
	                    <label for="edcRsvnRectype" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 접수 방식</label>
	                    <div class="col-sm-10"  style="padding-top:5px">
     <c:forEach items="${receptType}" var="item2" varStatus="status">
     						<div class="form-check mr-3" style="display:inline-block">
	                          <input type="radio" name="edcRsvnRectype" id="edcRsvnRectype${status.index+1}" class="form-check-input" value="${item2.cd}" <c:if test="${item2.cd eq edcProgramVO.edcRsvnRectype}">checked</c:if>/>
	                          <label for="edcRsvnRectype${status.index+1}" class="form-check-label">${item2.cdNm}</label>
	                        </div>
     </c:forEach>
	                    </div>
	               </div>
					<div class="form-group row">
	                    <label for="edcRsvnPerodType" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 접수기간운영방법</label>
	                    <div class="col-sm-10"  style="padding-top:5px">
     <c:forEach items="${perodType}" var="item2" varStatus="status">
     						<div class="form-check mr-3" style="display:inline-block">
	                          <input type="radio" name="edcRsvnPerodType" id="edcRsvnPerodType${status.index+1}" class="form-check-input" value="${item2.cd}" <c:if test="${item2.cd eq edcProgramVO.edcRsvnPerodType}">checked</c:if>/>
	                          <label for="edcRsvnPerodType${status.index+1}" class="form-check-label">${item2.cdNm}</label>
	                        </div>
     </c:forEach>
	                    </div>
	               </div>	               
	               
	               
	               <div class="form-group row" style="display:none">
	                    <label for="orgTel" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 접수 URL</label>
	                    <div class="col-sm-10">
	                    	<input type="text" name="edcRsvnLinkurl" id="edcRsvnLinkurl" value="<c:out value="${edcProgramVO.edcRsvnLinkurl}"/>" title="접수 URL" class="form-control " maxlength="1000" />
	                    </div>
	               </div>


                  <div class="form-group row">
                    <label for="edcRsvnSdate" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 접수 시작일</label>
                    <div class="col-sm-4">
                    	<div class="input-group">
		                    <input name="edcRsvnSdate" id="edcRsvnSdate"  title="시작일" maxlength="10" value="<c:out value="${edcProgramVO.edcRsvnSdate}"/>" class="form-control datetype"/>
		                </div>
                    </div>
                    <div class="col-sm-2">
                    	<div class="input-group">
                    		<select name="edcRsvnStimeHour" id="edcRsvnStimeHour" class="form-control">
 <c:forEach var="i" begin="0" end="23">
                    			<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</option>

 </c:forEach>
 							</select>
		                </div>
                     </div>
                     <div class="col-sm-2">
                    	<div class="input-group">
                    		<select id="edcRsvnStimeMin" name="edcRsvnStimeMin" class="form-control">
 <c:forEach var="i" begin="0" end="50" step="10">
                    			<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</option>

 </c:forEach>
 							</select>
		                </div>
                     </div>
                     <div class="col-sm-2">부터</div>
                  </div>
                  <div class="form-group row">
                    <label for="edcRsvnEdate" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 접수 종료일</label>
                    <div class="col-sm-4">
                    	<div class="input-group">
		                    <input id="edcRsvnEdate" name="edcRsvnEdate" value="<c:out value="${edcProgramVO.edcRsvnEdate}"/>" title="종료일" maxlength="10" class="form-control datetype"/>
		                </div>
                    </div>
                    <div class="col-sm-2">
                    	<div class="input-group">
                    		<select id="edcRsvnEtimeHour" name="edcRsvnEtimeHour" class="form-control">
 <c:forEach var="i" begin="0" end="23">
                    			<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</option>

 </c:forEach>
 							</select>
		                </div>
                     </div>
                     <div class="col-sm-2">
                    	<div class="input-group">
                    		<select id="edcRsvnEtimeMin" name="edcRsvnEtimeMin" class="form-control">
 <c:forEach var="i" begin="0" end="50" step="10">
                    			<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</option>

 </c:forEach>
 							</select>
		                </div>
                     </div>
                     <div class="col-sm-2">까지</div>
                  </div>
                  <div class="form-group row"  style="display:none">
                    <label for="drwtNtcedate" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 추첨 발표일</label>
                    <div class="col-sm-4">
                    	<input id="drwtNtcedate" name="drwtNtcedate" value="<c:out value="${edcProgramVO.drwtNtcedate}"/>" title="추첨 발표일" class="form-control datetype" maxlength="10" />
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="edcSdate" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 교육 기간</label>
                    <div class="col-sm-4">
                    	<div class="input-group">
		                    <input type="text" id="edcSdate" name="edcSdate"  value="<c:out value="${edcProgramVO.edcSdate}"/>" title="시작일" maxlength="10" class="form-control datetype"/>
		                </div>
                    </div>
                    <div class="col-sm-1">
							부터
                    </div>

                     <div class="col-sm-4">
                    	<div class="input-group">
		                    <input id="edcEdate" name="edcEdate" title="종료일" value="<c:out value="${edcProgramVO.edcEdate}"/>" maxlength="10" class="form-control datetype"/>
		                </div>
                     </div>

                     <div class="col-sm-1">
							까지
                    </div>

                  </div>

                  <div class="form-group row">
	                    <label for="edcDaysList" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 교육요일</label>
	                    <div class="col-sm-10"  style="padding-top:5px">
     <c:forEach items="${edcProgramVO.edcDaysList}" var="item2" varStatus="status">
     						<div class="form-check mr-3" style="display:inline-block">
	                          <input type="checkbox" id="edcDaysList${status.index }.dayChk1" name="edcDaysList[${status.index }].dayChk" class="form-check-input edcDaygbn" value="${item2.edcDaygbn}" <c:if test="${item2.edcDaygbn eq item2.dayChk}">checked</c:if>/>
	                          <label for="edcDaysList${status.index}.dayChk1" class="form-check-label">${item2.edcDaygbnNm}</label>
	                        </div>
     </c:forEach>
	                    </div>
	                  </div>
                  <div class="form-group row">
                    <label for="edcStimeHour" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 교육 시간</label>
                    <div class="col-sm-2">
                    	<div class="input-group">
                    		<select id="edcStimeHour" name="edcStimeHour" class="form-control">
 <c:forEach var="i" begin="0" end="23">
                    			<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</option>

 </c:forEach>
 							</select>
		                </div>
                     </div>
                     <div class="col-sm-2">
                    	<div class="input-group">
                    		<select id="edcStimeMin" name="edcStimeMin" class="form-control">
 <c:forEach var="i" begin="0" end="50" step="10">
                    			<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</option>

 </c:forEach>
 							</select>
		                </div>
                     </div>
                    <div class="col-sm-1">부터</div>

                     <div class="col-sm-2">
                    	<div class="input-group">
                    		<select id="edcEtimeHour" name="edcEtimeHour" class="form-control">
 <c:forEach var="i" begin="0" end="23">
                    			<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</option>

 </c:forEach>
 							</select>
		                </div>
                     </div>
                     <div class="col-sm-2">
                    	<div class="input-group">
                    		<select id="edcEtimeMin" name="edcEtimeMin" class="form-control">
 <c:forEach var="i" begin="0" end="50" step="10">
                    			<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</option>

 </c:forEach>
 							</select>
		                </div>
                     </div>

                     <div class="col-sm-1">까지</div>
                  </div>


                  <div class="form-group row intype">
	                    <label for="edcRsvnAccssrd" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 접수 경로</label>
	                    <div class="col-sm-10"  style="padding-top:5px">
    						<div class="form-check mr-3" style="display:inline-block">
	                          <input type="radio" name="edcRsvnAccssrd" id="edcRsvnAccssrd1" class="form-check-input" value="1001" <c:if test="${edcProgramVO.edcRsvnAccssrd eq '1001'}">checked</c:if>/>
	                          <label for="edcRsvnAccssrd1" class="form-check-label">온라인</label>
	                        </div>
	                        <div class="form-check mr-3" style="display:inline-block">
	                          <input type="radio" name="edcRsvnAccssrd" id="edcRsvnAccssrd2" class="form-check-input" value="2001" <c:if test="${edcProgramVO.edcRsvnAccssrd eq '2001'}">checked</c:if>/>
	                          <label for="edcRsvnAccssrd2" class="form-check-label">방문접수</label>
	                        </div>
	                        <div class="form-check mr-3" style="display:inline-block">
	                          <input type="radio" name="edcRsvnAccssrd" id="edcRsvnAccssrd3" class="form-check-input" value="3001" <c:if test="${edcProgramVO.edcRsvnAccssrd eq '3001'}">checked</c:if>/>
	                          <label for="edcRsvnAccssrd3" class="form-check-label">온라인+방문접수</label>
	                        </div>
 	                    </div>
	               </div>

					<div class="form-group row ">
	                    <label class="col-sm-2 col-form-label">접수 인원</label>
	                    <div class="col-sm-10">
	                    	<div>
		                        <table style="width:100%" class="table table-bordered">
									<colgroup>
			                  				<col width="20%">
			                  				<col width="*">
			                  			</colgroup>
									<tbody>
										<tr>
											<td><label><span class="pilsu">*</span> 총 정원</label></td>
											<td>
												<div class="input-group">
						                    		<input type="text" id="edcPncpa" name="edcPncpa" title="교육 총 정원" value="${edcProgramVO.edcPncpa}"  class="form-control numberchk" maxlength="5" />
								                    <div class="input-group-append">
								                      <span class="input-group-text">명</span>
								                    </div>
						                    	</div>
											</td>
										</tr>
										<tr class="intype">
											<td><label><span class="pilsu">*</span> 마감후 대기정원</label></td>
											<td>
												<div class="input-group">
						                    		<input type="text" name="edcEndwaitCapa" id="edcEndwaitCapa" title="대기정원" value="${edcProgramVO.edcEndwaitCapa}" class="form-control numberchk" maxlength="5" />
						                    		<div class="input-group-append">
								                      <span class="input-group-text">명</span>
								                    </div>
						                    	</div>
											</td>
										</tr>

										<tr class="intype">
											<td colspan="2">
												<div class="form-check">
						                          <input type="checkbox" name="edcCapaDvdyn"  id="edcCapaDvdyn1" class="form-check-input" value="Y" disabled <c:if test="${edcProgramVO.edcCapaDvdyn eq 'Y'}">checked</c:if>/>
						                          <label for="edcCapaDvdyn1" class="form-check-label">온라인/방문 접수인원 배분하기</label>
						                        </div>
											</td>
										</tr>

										<tr class="intype">
											<td><label><span class="pilsu">*</span> 온라인 정원</label></td>
											<td>
												<div class="input-group">
						                    		<input type="text" name="edcOncapa" id="edcOncapa" title="온라인 정원" value="${edcProgramVO.edcOncapa}" class="form-control numberchk" maxlength="5" />
						                    		<div class="input-group-append">
								                      <span class="input-group-text">명</span>
								                    </div>
						                    	</div>
											</td>
										</tr>
										<tr class="intype">
											<td><label><span class="pilsu">*</span> 방문접수 정원</label></td>
											<td>
												<div class="input-group">
						                    		<input type="text" name="edcOffcapa" id="edcOffcapa" title="방문접수 정원" value="${edcProgramVO.edcOffcapa}" class="form-control numberchk" maxlength="5" />
						                    		<div class="input-group-append">
								                      <span class="input-group-text">명</span>
								                    </div>
						                    	</div>
											</td>
										</tr>

	                        	</tbody>
		                        </table>
		                   </div>
                  	 </div>
                  </div>

                  <div class="form-group row notfree intype">
	                    <label for="edcPaywaitGbn" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 결제대기 기준</label>
	                    <div class="col-sm-10"  style="padding-top:5px">
    						<div class="form-check" style="display:inline-block">
	                          <input type="radio" name="edcPaywaitGbn" id="edcPaywaitGbn1" class="form-check-input" value="1001" <c:if test="${edcProgramVO.edcPaywaitGbn eq '1001'}">checked</c:if>/>
	                          <label for="edcPaywaitGbn1" class="form-check-label">신청시간 기준 대기시간 설정</label>
	                        </div>
	                        <div class="form-check" style="display:inline-block">
	                          <input type="radio" name="edcPaywaitGbn" id="edcPaywaitGbn2" class="form-check-input" value="2001" <c:if test="${edcProgramVO.edcPaywaitGbn eq '2001'}">checked</c:if>/>
	                          <label for="edcPaywaitGbn2" class="form-check-label">마감일시 직접 설정</label>
	                        </div>

	                        <div class="col-sm-12"  style="padding-top:10px">
	 	                    	<table style="width:100%" class="table table-bordered">
									<colgroup>
			                  				<col width="30%">
			                  				<col width="*">
			                  			</colgroup>
									<tbody>
										<tr>
											<td><label>대기 시간</label></td>
											<td><input type="text" name="edcPaywaitTime" id="edcPaywaitTime" title="대기 시간"  value="${edcProgramVO.edcPaywaitTime}"  style="width:100px" maxlength="4" class="numberchk"/> 분 (최소 60분이상 설정)</td>
										</tr>
										<tr>
											<td><label>마감 일시</label></td>
											<td><input type="text" name="edcPaywaitDate" id="edcPaywaitDate" title="마감일" value="${edcProgramVO.edcPaywaitDate}" class="datetype" style="width:100px" maxlength="10" />&nbsp;&nbsp;
                    		<select id="edcPaywaitHour" name="edcPaywaitHour">
 <c:forEach var="i" begin="0" end="23">
                    			<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</option>

 </c:forEach>
 								</select>
&nbsp;
                    		<select id="edcPaywaitMin" name="edcPaywaitMin" >
 <c:forEach var="i" begin="0" end="50" step="10">
                    			<option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</option>

 </c:forEach>
 								</select>


											</td>
										</tr>
									</tbody>
								</table>
	 	                    </div>
 	                    </div>
	               </div>
	                <div class="form-group row">
				               	<div class="col-sm-12">
				                	<c:choose>
				                		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
				                			 <button type="button" onclick="fn_program_rsvn()"  class="btn btn-primary form-control">모집정보 수정</button>
				                		</c:when>
				                		<c:otherwise>
				                			 <button type="button" class="btn btn-primary  disabled">모집정보 수정</button>
				                		</c:otherwise>
				                	</c:choose>
				                </div>
			               </div>