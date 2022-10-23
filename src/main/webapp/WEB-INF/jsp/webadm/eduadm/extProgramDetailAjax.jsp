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
					 <div class="form-group row" >
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

                  <div class="form-group row">
	                    <label for="edcPncpa" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 교육정원</label>
	                    <div class="col-sm-4" >
               						<div class="input-group">
			                    		<input type="text" id="edcPncpa" name="edcPncpa" title="교육 총 정원" value="${edcProgramVO.edcPncpa}"  class="form-control numberchk" maxlength="5" />
					                    <div class="input-group-append">
					                      <span class="input-group-text">명</span>
					                    </div>
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