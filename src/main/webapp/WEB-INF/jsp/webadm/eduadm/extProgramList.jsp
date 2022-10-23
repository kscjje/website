<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<style>
.sort {font-size:16px; cursor:pointer}
.sort.active {color:red}
</style>
<script>
	var selectedMode = "";
	function fn_excelDown() {
		var $cform = $('#searchVO').clone(true);

		$cform.attr('id', 'excelDownloadForm');
		$cform.attr('name', 'excelDownloadForm');
		$cform.attr('method', 'get');
		$cform.attr('action', "./extProgramListExcel");

		$('body').append($cform);
		$('#excelDownloadForm').submit();
		$('#excelDownloadForm').remove();
	}

	function fn_open_change(type,val,prgmId){
		$.ajax({
		      url: './extProgramStatusChange.json',
		      type : "POST",
		      data : {'type':type,'val':val, 'prgmId':prgmId },
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        }else{
			        	 alert(data.result.msg);
			        }
		      }
		    });
	}

	function fn_click_event(gubun , val){
		if(gubun == "edcRsvnRectype"){
    		if(val == "2001" ){
    			//추첨대기제
    			$('#drwtNtcedate').prop("disabled" , false);
    		}else{
    			$('#drwtNtcedate').prop("disabled" , true);
    		}

    		if(val == "5001" ){
    			//타기관 링크
    			$('#edcRsvnLinkurl').prop("disabled" , false);
    			$('.intype').hide();
    		}else{
    			$('#edcRsvnLinkurl').prop("disabled" , true);
    			$('.intype').show();
    		}

    		if(val == "1002"){
    			//선착대기
    			$('#edcEndwaitCapa').prop("disabled" , false);
    		}else{
    			$('#edcEndwaitCapa').val("0").prop("disabled" , true);
    		}
		}else if(gubun == "edcRsvnAccssrd"){
	    	if(val == "1001" || val == "2001"){
	    		//온라인
	    		$('#edcCapaDvdyn1').prop("checked" , false).prop("disabled" , true);
	    		//배분 안함
	    		$('#edcOncapa').val("0").prop("disabled" , true);
	    		$('#edcOffcapa').val("0").prop("disabled" , true);
	    	}else{
	    		//온라인 + 오프라인
	    		$('#edcCapaDvdyn1').prop("disabled" , false);
	    	}
		}else if(gubun == "edcCapaDvdyn"){

	    	if(val == "Y"){
	    		//온라인,오프라인 배분하기
	    		$('#edcOncapa').prop("disabled" , false);
	    		$('#edcOffcapa').prop("disabled" , false);
	    	}else{
	    		//배분 안함
	    		$('#edcOncapa').val("0").prop("disabled" , true);
	    		$('#edcOffcapa').val("0").prop("disabled" , true);
	    	}
		}else if(gubun == "edcPaywaitGbn"){
			if(val=="1001"){
				$('#edcPaywaitTime').prop("disabled" , false);
				$('#edcPaywaitDate').prop("disabled" , true);
				$('#edcPaywaitHour').prop("disabled" , true);
				$('#edcPaywaitMin').prop("disabled" , true);
			}else{
				$('#edcPaywaitTime').prop("disabled" , true);
				$('#edcPaywaitDate').prop("disabled" , false);
				$('#edcPaywaitHour').prop("disabled" , false);
				$('#edcPaywaitMin').prop("disabled" , false);
			}
		}
	}

	function fn_show_setmode(mode){
		if(selectedMode == mode){
			//같은 모드 일때는
		}else{
			selectedMode = mode;

			if(mode == "NEW"){
				$('#modal-oneclick').find(".modal-title").text("일괄 차수개설");
				$('#onclick-btn').text("일괄 차수개설 신청");
			}else{
				$('#modal-oneclick').find(".modal-title").text("일괄 수정");
				$('#onclick-btn').text("일괄 차수 수정");
			}

			$.ajax({
			      url: './extProgramSetAjax',
			      type : "GET",
			      data : {'comcd': $('#comcd').val() , 'orgNo':$('#searchOrgNo').val() , 'mode':mode},
			      dataType : "html",
			      success: function(data) {
			    	  if(data.indexOf("login_area") > 0 ){
			    		  __ajaxHtmlCallback__();
			    	  	}else{
							$('#modal-oneclick').find(".modal-body").html(data);
			    	  	}
			      }
			    });
		}
	}

	function fn_change_org(){
		var orgNo = $('#searchOrgNo').val();
		$('#searchRsvnsetNm').find("option").remove();
		$('#searchRsvnsetNm').append("<option value=''>모집차수(전체)</option>");

		if(orgNo == ""){
			$('#searchRsvnsetNm').append("<option value=''>기관을 먼저 선택해 주세요.</option>");
		}else{
			$.ajax({
			      url: './edcRsvnsetName.json',
			      type : "GET",
			      dataType : "json",
			      data : {'searchOrgNo' : orgNo },
			      cache : false,
			      success: function(data) {
			         for(var i=0;i<data.list.length;i++){

			        	 var selected = "";
			        	 if("<c:out value="${param.searchRsvnsetNm}"/>" == data.list[i].edcRsvnsetNm){
			        		 selected = " selected";
			        	 }

			        	 $('#searchRsvnsetNm').append("<option value='"+data.list[i].edcRsvnsetNm+"' "+selected+">"+data.list[i].edcRsvnsetNm+"</option>");
			         }
			      }
			    });
		}
	}

	function fn_rsvn_set_chk(){
		var form = document.writeForm;

		if (form.targetOrgNo.value == "") {
			alert("대상 기관을 선택해 주세요.");
			form.targetOrgNo.focus();
			return false;
		}

		if (selectedMode == "NEW" && form.edcRsvnSdate.value == "") {
			alert("접수시작일을 입력해 주세요.");
			form.edcRsvnSdate.focus();
			return false;
		}
		if (selectedMode == "NEW" && form.edcRsvnEdate.value == "") {
			alert("접수종료일을 입력해 주세요.");
			form.edcRsvnEdate.focus();
			return false;
		}
		if(__dateDiffCheck__(form.edcRsvnSdate.value , form.edcRsvnEdate.value) == false){
			alert("접수시작일이 종료일 보다 클 수 없습니다.");
			form.edcSdate.focus();
			return false;
		}
		if (selectedMode == "NEW" && form.edcSdate.value == "") {
			alert("교육시작일을 입력해 주세요.");
			form.edcSdate.focus();
			return false;
		}
		if (selectedMode == "NEW" && form.edcEdate.value == "") {
			alert("교육종료일을 입력해 주세요.");
			form.edcEdate.focus();
			return false;
		}
		if(__dateDiffCheck__(form.edcSdate.value , form.edcEdate.value) == false){
			alert("교육시작일이 종료일 보다 클 수 없습니다.");
			form.edcSdate.focus();
			return false;
		}

		return true;
	}

	function fn_save_onclick(){
		if(fn_rsvn_set_chk()){
			var msg = "일괄 모집설정을 수정 하시겠습니까?";
			if(selectedMode == "NEW") msg = "일괄 모집설정을 등록 하시겠습니까?";
			if(confirm(msg)){
				$.ajax({
				      url: './extProgramSetSave.json',
				      type : "POST",
				      data : $('#writeForm').serialize(),
				      success: function(data) {
				    	  if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        }else{
					        	 alert(data.result.msg);
					        	 document.searchVO.submit();
					        }
				      }
				});
			}
		}
	}

	function fn_find_category(){
		$.ajax({
		      url: '../comctgr/comCtgrListAjax.json',
		      type : "GET",
		      dataType : "json",
		      cache : false,
		      success: function(data) {
		        console.log(data);

		        var LIST = data.result;
		        $('#CTG_TABLE').find("tbody").html("");

		        if(LIST){
		        	$('#CTG_TABLE').find("tbody").html(categoryTreeHtml(LIST));
		        }
		      }
		    });
	}

	//분류 선택 후
	function fn_select_category(comcd,ctgCd , obj){
		var nowCtgnm = $(obj).text();

		$('#searchCtgnm').val(nowCtgnm);
		$('#searchCtgcd').val(ctgCd);

		$('#modal-default').find("button.close").click();

	}


	function fn_reset(){
		$('#searchVO').find("input[type=text],input[type=hidden]").val('');
		$('#searchVO').find("select[name=searchUse],select[name=searchOpen],select[name=searchFree],select[name=searchRectype],select[name=searchTarget]").val('');
		$('.select2').val(null).trigger('change');
	}

	$(document).ready(function(){
		$( ".datepicker" ).datepicker({
			"dateFormat":'yy-mm-dd',
			'monthNames':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			'monthNamesShort':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			'changeMonth' : true,
			'changeYear':true
		});

		var sort_column = $('#searchOrder').val();
		var sort_order = $('#searchOrderDir').val();

		$('.sort').click(function(){
			var id = $(this).attr("id");
			if(id == sort_column){
				if(sort_order == "" || sort_order == "desc"){
					sort_order = "asc";
				}else{
					sort_order = "desc";
				}
			}else{
				sort_order = "asc";
			}
			$('#searchOrder').val(id);
			$('#searchOrderDir').val(sort_order);
			$('#searchVO').submit();

		});

		//if(sort_column == "") sort_column = "createdate";
		//if(sort_order == "") sort_order = "desc";
		$.each($('.sort') , function(){
			if($(this).attr("id") == sort_column){
				$(this).addClass("active");
				if(sort_order == "asc"){
					$(this).text("▲");
				}
			}
		});

		$('#searchDate').change(function(){
			if($(this).val() == ""){
				$('#searchStartDts').prop("disabled" , true);
				$('#searchEndDts').prop("disabled" , true);
			}else{
				$('#searchStartDts').prop("disabled" , false);
				$('#searchEndDts').prop("disabled" , false);
			}
		});

		if("${searchVO.searchDate}" != ""){
			$('#searchStartDts').prop("disabled" , false);
			$('#searchEndDts').prop("disabled" , false);
		}

		$('#pageSize').change(function(){
			$('#searchVO').submit();
		});

		$('#searchOrgNo').change(function(){
			fn_change_org();
		});

		$('#showModalNew').click(function(){
			if($('#searchOrgNo').val() == ""){
				alert("기관이 필수로 선택되어야 합니다.");
				return false;

			}else{
				fn_show_setmode('NEW');
			}
		});

		$('#showModalEdit').click(function(){
			if($('#searchOrgNo').val() == ""){
				alert("기관이 필수로 선택되어야 합니다.");
				return false;

			}else{
				fn_show_setmode('EDIT');
			}
		});

		if($('#searchOrgNo').val() != ""){
			fn_change_org();
		}
	});

</script>
</head>
<body>

<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>유관기관 교육프로그램현황</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div>
</section>

<!-- Main content -->
<section class="content">

      <div class="container-fluid">
<form:form commandName="searchVO" name="searchVO" method="get">
<form:hidden path="searchOrder" />
<form:hidden path="searchOrderDir" />
<form:hidden path="searchCtgcd" />
       	<div class="card card-primary card-outline">
		 	 	 <div class="card-body">
		 	 	 	<div class="row">
		              	<div class="col-5">
	                    	<hisco:OrgList defaultNo="${searchVO.searchOrgNo}" id="searchOrgNo" orgKind="2001"/>
	                    </div>
	                    <div class="col-1"></div>
		              	<div class="col-2">
	              			<form:select path="searchDate" title="날짜"  class="form-control">
								<form:option value=""  >기간선택</form:option>
								<form:option value="edcdate"  >교육시작일</form:option>
								<form:option value="rsvdate" >접수시작일</form:option>
								<form:option value="optdate" >개설일자</form:option>
							</form:select>
						</div>

						<div class="col-4">
								<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest" >
			                        <form:input cssClass="form-control datepicker" path="searchStartDts" autocomplete="off" disabled="true"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>

								<div style="width:10%;float:left;text-align:center">~</div>
								<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest">
			                        <form:input cssClass="form-control datepicker" path="searchEndDts" autocomplete="off" disabled="true"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>
						</div>

		           </div>

 					<div class="row" style="margin-top:10px">
 						<div class="col-4">
 							<select name="searchRsvnsetNm" id="searchRsvnsetNm" class="form-control">
 								<option value="">모집차수명(전체)</option>
 							</select>
	                     </div>

 						<div class="col-2">
 							<form:select path="searchUse" title="검색"  cssClass="form-control">
								<form:option value="">사용여부(전체)</form:option>
								<form:option value="Y">사용</form:option>
								<form:option value="N">삭제</form:option>
							</form:select>
 						</div>


	                     <div class="col-2">
 							<form:select path="searchTarget" class="form-control">
 								<form:option value="">교육대상(전체)</form:option>
	   							<form:options items="${targetType}" class="form-control" itemLabel="cdNm" itemValue="cd" />
	                    	 </form:select>
	                     </div>

	                     <div class="col-2">
 							<form:select path="searchOpen" title="검색"  cssClass="form-control">
								<form:option value="">공개여부(전체)</form:option>
								<form:option value="Y">공개</form:option>
								<form:option value="N">비공개</form:option>
							</form:select>
 						</div>


 					</div>

 					 <div class="row" style="margin-top:10px">
		              	<div class="col-3">
	              			<form:select path="searchCondition" title="검색"  cssClass="form-control">
								<form:option value="1">프로그램명</form:option>
								<form:option value="6">강사명</form:option>
								<form:option value="3">장소명</form:option>
								<form:option value="5">요일</form:option>
							</form:select>
						</div>
						<div class="col-3">
							<form:input cssClass="form-control" path="searchKeyword" placeHolder="검색어를 입력하세요"  maxlength="155" />
						</div>



						<div class="col-2">

		              	</div>
 					</div>
 				</div>

 	   </div>
 	    <div class="row" style="margin-bottom:15px;text-align:right">
 	    	<div class="col-4"></div>
 	    	<div class="col-2">
 	    		<button type="submit" class="btn btn-primary btn-block">조회</button>
 	    	</div>
 	    	<div class="col-2">
 	    		<button type="button" onclick="fn_reset()" class="btn btn-secondary btn-block">초기화</button>
 	    	</div>
 	    	<div class="col-4"></div>
 	    </div>
			<div class="row" style="text-align:right">
			 <div class="col-12" style="color:red">
			* 사용여부 N은 일괄설정에 포함되지 않습니다.
			</div>
			</div>

 	   <div class="row mb-2">
			 <div class="col-2">
				<div class="input-group input-group-sm"">
					<form:select path="pageSize" title="글수"  cssClass="form-control">
						<form:option value="5">5개씩 보기</form:option>
						<form:option value="10">10개씩 보기</form:option>
						<form:option value="20">20개씩 보기</form:option>
						<form:option value="50">50개씩 보기</form:option>
						<form:option value="100">100개씩 보기</form:option>
					</form:select>
				</div>
			 </div>
			 <div class="col-2 pt-1">
			 	조회결과 <b><fmt:formatNumber value="${commandMap.pagingInfo.totalRecordCount}"/></b> 건
			 </div>
			 <div class="col-8" style="text-align:right">
				<button type="button" onclick="fn_excelDown()" class="btn  btn-secondary btn-sm btn-flat">엑셀다운</button>
				<a href="./extProgramRegist" class="btn  btn-primary btn-sm btn-flat<c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>">프로그램 개설</a>
				<c:if test="${commandMap.selectedMenu.insYn eq 'Y' }">
					<button type="button" class="btn  btn-success btn-sm btn-flat" id="showModalNew" data-toggle="modal" data-target="#modal-oneclick">일괄 차수개설</button>
				</c:if>
				<c:if test="${commandMap.selectedMenu.updYn eq 'Y' }">
					<button type="button" class="btn  btn-success btn-sm btn-flat" id="showModalEdit"  data-toggle="modal" data-target="#modal-oneclick">일괄수정</button>
				</c:if>
			</div>
       </div>
</form:form>
       <div class="row">

          <div class="col-12">
            <div class="card">
              <!-- /.card-header -->

              <div class="card-body table-responsive p-0">
	               <table class="table table-bordered table-hover text-nowrap">
	                  <thead>
	                    <tr>
	                      	<th >번호</th>
	                      	<th >기관 <span id="orgNm" class="sort">▼</span></th>
	                      	<th >프로그램명 <span id="prgNm" class="sort">▼</span></th>
	                      	<th >접수기간</th>
							<th >정원</th>
							<th >교육기간</th>
							<th>수업요일</th>
							<th>공개여부</th>
							<th>사용여부</th>
							<th>복사</th>
	                    </tr>
	                  </thead>
	                  <tbody>

<c:if test="${fn:length(programList) == 0}">
					<tr>
						<td colspan="10">데이타가 없습니다</td>
					</tr>
</c:if>
<c:forEach items="${programList}" var="item" varStatus="status">
		                    <tr>
		                      	<td>
		                      		<c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/>
		                      	</td>
		                      	<td><c:out value='${item.orgName}'/></td>
								<td>
									<a href="./extProgramUpdt?edcPrgmid=<c:out value="${item.edcPrgmid}"/><c:out value="${commandMap.query}"/>"><c:out value='${item.edcPrgmnm}'/></a>
								</td>
								<td><hisco:DateUtil datestr="${item.edcRsvnSdate}${item.edcRsvnStime}" format="yy.MM.dd HH:mm"/> ~ <hisco:DateUtil datestr="${item.edcRsvnEdate}${item.edcRsvnEtime}" format="yy.MM.dd HH:mm"/></td>
								<td><c:out value='${item.edcPncpa}'/></td>
								<td><hisco:DateUtil datestr="${item.edcSdate}" format="yy.MM.dd"/> ~ <hisco:DateUtil datestr="${item.edcEdate}" format="yy.MM.dd"/></td>
								<td><c:out value='${item.edcDaygbnNm}'/></td>
								<td>
									<c:choose>
										<c:when test="${commandMap.selectedMenu.updYn ne 'Y' }">
											${(item.edcOpenyn eq 'Y')?'공개':'비공개'}
										</c:when>
										<c:otherwise>
											<select id="openyn_${item.edcPrgmid}" onChange="fn_open_change('openyn', this.value , '${item.edcPrgmid}')">
												<option value="Y" <c:if test="${item.edcOpenyn eq 'Y' }">selected</c:if>>공개</option>
												<option value="N" <c:if test="${item.edcOpenyn eq 'N' }">selected</c:if>>비공개</option>
											</select>
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${commandMap.selectedMenu.updYn ne 'Y' }">
											${item.useYn}
										</c:when>
										<c:otherwise>
											<select id="useyn_${item.edcPrgmid}" onChange="fn_open_change('useyn', this.value , '${item.edcPrgmid}')">
												<option value="Y" <c:if test="${item.useYn eq 'Y' }">selected</c:if>>Y</option>
												<option value="N" <c:if test="${item.useYn eq 'N' }">selected</c:if>>N</option>
											</select>
										</c:otherwise>
									</c:choose>
								</td>
								<td>
								  	<a class="btn bg-primary btn-xs<c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>" href="./extProgramRegist?copyPrgmid=<c:out value="${item.edcPrgmid}"/><c:out value="${commandMap.query}"/>">복사</a>
								</td>
		                    </tr>
</c:forEach>
	                  </tbody>
	                </table>
              </div>

              <div class="card-footer p-0">
              	<div class="pagination_2">
					<tags:AdminPaging pageInfo="${commandMap.pagingInfo}" pageUrl="./extProgramList?pageIndex="/>
				</div>
            </div>


          </div>
        </div>
      </div>
     </div>

     <div class="modal fade" id="modal-oneclick">
        <div class="modal-dialog modal-xl">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">일괄 차수개설</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">



            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" id="onclick-btn" class="btn btn-primary" onclick="fn_save_onclick()">일괄 차수개설 신청</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>


      <!-- 프로그램 분류 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">분야 선택하기</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
              <table class="table table-sm border-0" id="CTG_TABLE">
                  <tbody>

                  </tbody>
                </table>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <!-- /.modal -->
</section>
</body>