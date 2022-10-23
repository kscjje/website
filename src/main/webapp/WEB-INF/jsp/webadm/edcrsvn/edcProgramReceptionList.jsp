<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="-1">
	<style>
	.sort {font-size:16px; cursor:pointer}
	.sort.active {color:red}
	</style>
	<script>
		window.addEventListener('pageshow', function(event) {
			if (event.persisted) {
				location.reload();
			}
		});
	
		function fn_excelDown() {
			$.get({
			    url : './edcProgramReceptionListAjax?exceldown=Y',
			    data : $('#searchVO').serialize(),
				dataType : 'html',
				success: function(data) {
			    	$("#divHidden").html(data);
			    	tableExportExcel("dataTableY", "강좌접수현황");
				}
		    });
		}

		$(document).ready(function(){
			$.each(["#searchOrgNo", "#edcRsvnsetNm", "#edcPrgmNo", "#periodCondition", "#searchYear", "#searchMonth", "#selRecordCountPerPage"], function(idx, element) {
				$(element).change(function () {
					$('#pageIndex').val(1);
					if (element == "#searchOrgNo") {
						fn_get_rsvnset_nm_list();
					}
				});
			});

			$("#periodCondition").change(function () {
				if (!$(this).val()) {
					$("#searchYear").prop("disabled", true);
					$("#searchMonth").prop("disabled", true);
				} else {
					$("#searchYear").prop("disabled", false);
					$("#searchMonth").prop("disabled", false);
				}
			});

			$('#selRecordCountPerPage').change(function(){
				$('#pageIndex').val(1);
				fn_get_program_reception_list();
			})

			<c:if test="${empty rsvnsetNmList}">
			$("#edcRsvnsetNm").prop("disabled", true);
			</c:if>
			<c:if test="${!empty rsvnsetNmList}">
			$("#edcRsvnsetNm :eq(1)").prop("selected", true);
			</c:if>
			
			var pageIndex = $('#pageIndex').val();

			if(pageIndex == "" || pageIndex == "0"){
				pageIndex = "1";
			}
			fn_set_page(pageIndex);
		});

		//상세페이지로이동
		function fnGoDetail(url , edcPrgmNo , edcRsvnsetSeq) {
			location.href = url + '?edcPrgmNo=' + edcPrgmNo +"&edcRsvnsetSeq=" + edcRsvnsetSeq ;
		}

		function fn_get_program_reception_list() {
			if($("#selRecordCountPerPage").val() == ''){
				$("#recordCountPerPage").val(10);
			}
			else{
				$("#recordCountPerPage").val($("#selRecordCountPerPage").val());	
			}
			

			$.ajax({
			    url : './edcProgramReceptionListAjax',
			    data : $('#searchVO').serialize(),
				dataType : 'html',
				success: function(data) {
			    	$("#divCard").html(data);
			    	history.pushState({pageIndex:$("#pageIndex").val()}, '접수목록조회', '?' + $('#searchVO').serialize());
				}
		    });
		}

		function fn_set_page(page) {
			var page = page > 0 ? page : 1;
			$('#pageIndex').val(page);

			fn_get_program_reception_list();
		}

		function fn_reset(){
			$('#searchVO').find("#searchOrgNo").val(null).trigger('change');
			$('#searchVO').find("#edcPrgmNo").val('0');
			$('#searchVO').find("#edcPrgmnm").val('');

			$("#edcRsvnsetNm").children("option").remove();
			$("#edcRsvnsetNm").append('<option value="">모집차수(선택)</option>');
			$("#periodCondition").val('');
			$("#searchYear").prop("disabled" , true);
			$("#searchMonth").prop("disabled" , true);

			$('#pageIndex').val(1);

		}

		function fn_draw_edc_rsvn_info(orgNo, edcPrgmNo) {
			location.hash = $('#searchVO').serialize();

			var queryString = [];
			queryString.push("searchOrgNo=" + (orgNo ? orgNo : $("#orgNo").val()));
			if (edcPrgmNo) queryString.push("edcPrgmNo=" + edcPrgmNo);
			queryString.push("searchYear=" + $("#searchYear").val());
			queryString.push("searchMonth=" + $("#searchMonth").val());
			location.href = './edcDrawProgramList?' + queryString.join("&");
		}

		function fn_register_edc_rsvn_info(source) {
			var chkCnt = $(":checkbox").filter(":checked").length;
			var msgPrefix = "수강신청등록";
			if (source == "wait") msgPrefix = "대기등록";

			if (chkCnt < 1) {
				alert(msgPrefix + "을 원하는 프로그램을 선택해주세요.");
				return;
			} else if (chkCnt > 1) {
				alert(msgPrefix + "을 원하는 프로그램을 1개만 선택해주세요.");
				return;
			}

			var edcPrgmNo = $(":checkbox").filter(":checked").data("prgmid");
			var edcRsvnsetSeq = $(":checkbox").filter(":checked").data("setseq");
			var orgNo = $(":checkbox").filter(":checked").data("orgno");

			var queryString = [];
			queryString.push("edcPrgmNo=" + edcPrgmNo);
			queryString.push("edcRsvnsetSeq=" + edcRsvnsetSeq);
			queryString.push("orgNo=" + orgNo);
			queryString.push("source=" + $.trim(source));
			location.href = './edcRsvnInfoRegister?' + queryString.join("&");
		}
	</script>
</head>
<body>

	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>강좌접수현황</h1>
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
		<form:hidden path="edcPrgmNo" />
		<form:hidden path="pageIndex" />
		<form:hidden path="recordCountPerPage" />
		<form:hidden path="searchOrder" />
		<form:hidden path="searchOrderDir" />
		<!-- 검색box start -->
       	<div class="card card-primary card-outline">
	 	 	<div class="card-body">
	 	 	 	<div class="row">
	              	<div class="col-3">
	                   	<hisco:OrgList defaultNo="${searchVO.searchOrgNo}" id="searchOrgNo" />
	                </div>
	                
	                <div class="col-4">
	             		<form:select path="edcRsvnsetNm" title="모집차수"  class="form-control">
	             			<option value=''>모집차수(선택)</option>
	             			<form:options items="${rsvnsetNmList}"/>
						</form:select>
					</div>

	                <div class="col-4 input-group-append">
	                   	<div class="input-group">
							<form:input cssClass="form-control" path="edcPrgmnm" placeHolder="프로그램을 검색 선택하세요" readonly="true"/>
							<div class="input-group-append">
								<button class="btn btn-secondary" type="button" data-toggle="modal" data-target="#modal-program" onclick="fn_check_search()">
	              					<i class="fas fa-search fa-fw findprogram"></i> 찾기
	            				</button>
	            			</div>
						</div>
	                </div>
	            </div>
	            <div class="row" style="margin-top:10px">
	              	<div class="col-2">
	             		<form:select path="periodCondition" title="날짜"  class="form-control">
	             			${hisco:makeOptions("SM_SRCH_PERIOD_CD", "기간조건(선택)", "${searchVO.periodCondition}")}
						</form:select>
					</div>
					<div class="col-4">
						<div class="input-group date" style="float:left;width:45%"  data-target-input="nearest" >
							<select id="searchYear" name="searchYear" title="년도" class="form-control" disabled="true">
		             			${hisco:makeYearOptions("년도", 30, "")}
							</select>
							&nbsp;
	                    </div>
						<div class="input-group date" style="float:left;width:35%"  data-target-input="nearest">
							<select id="searchMonth" name="searchMonth" title="월"  class="form-control" disabled="true">
		             			${hisco:makeMonthOptions("월", "")}
							</select>
	                    </div>
					</div>
	           </div>
	 	   	</div>
 	   	</div>
 	   	<!-- 검색box end -->
		</form:form>

 	    <div class="row" style="margin-bottom:15px;text-align:right">
 	    	<div class="col-4"></div>
 	    	<div class="col-2">
 	    		<button type="button" class="btn btn-primary btn-block" onclick="fn_get_program_reception_list()">조회</button>
 	    	</div>
 	    	<div class="col-2">
 	    		<button type="button" class="btn btn-secondary btn-block" onclick="fn_reset()">초기화</button>
 	    	</div>
 	    	<div class="col-5"></div>
 	    </div>

 	   <div class="row" style="margin-bottom:5px;">
			 <div class="col-2">
			 	<div class="input-group input-group-sm">
			 	 <select id="selRecordCountPerPage" name="selRecordCountPerPage" title="년도" class="form-control">
			 	 	${hisco:makeOptions("CM_COUNT_PER_PAGE", "보기갯수(선택)", searchVO.recordCountPerPage)}
			 	 </select>
			 	</div>
			 </div>
			 <div class="col-2" style="margin-top:10px;float:left;width:45%">
			 	총  <span id="totCount">0</span> 건 조회
			 </div>
			 <div class="col-8" style="text-align:right">
				<button type="button" class="btn  btn-secondary btn-sm btn-flat" onclick="fn_excelDown()">EXCEL내려받기</button>
				<button type="button" class="btn  btn-secondary btn-sm btn-flat" onclick="fn_draw_edc_rsvn_info()">일괄추첨</button>
				<button type="button" class="btn  btn-info btn-sm btn-flat" onclick="fn_register_edc_rsvn_info('add')">현장등록</button>
				<button type="button" class="btn  btn-info btn-sm btn-flat" onclick="fn_register_edc_rsvn_info('wait')">배정대기등록</button>
			</div>
       </div>

       <div class="row">

          <div class="col-12">
            <div class="card" id="divCard">
              <div class="card-footer p-0">
              	<div class="pagination_2">
				</div>
            </div>
          </div>
        </div>
      </div>
     </div>

     <!-- 프로그램 분야 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">프로그램 분야를 선택하세요</h4>
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
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" class="btn btn-default" data-dismiss="modal">선택</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <script>
      var _comcd, _edcPrgmNo , _edcRsvnsetSeq;
      function fn_modal_force_close(comcd, edcPrgmNo, edcRsvnsetSeq, edcPrgmnm) {
    	  _comcd = comcd;
    	  _edcPrgmNo = edcPrgmNo;
    	  _edcRsvnsetSeq = edcRsvnsetSeq;
    	  $("#spanPrgmnm").text(edcPrgmnm);
      }

      function fn_program_force_close() {
			var data = {};
			data.comcd = _comcd;
			data.edcPrgmNo = _edcPrgmNo;
			data.edcRsvnsetSeq = _edcRsvnsetSeq;
			    //contentType : "application/json; charset=UTF-8",

			$.post({
			    url : './edcProgramForceClose.json',
			    data : data,
				success : function(data) {
					alert(data.result.msg);
					if (data.result.code == "SUCCESS") {
						fn_get_program_reception_list();
					}
				}
		    });
		}
      </script>


      <!-- 강제마감처리 -->
      <div class="modal fade" id="modal-force-close" >
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">점수 마감처리</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 modal-title">
              <h4 class="modal-title">'<span id="spanPrgmnm">동네헤어스타일</span>' 강좌를 강제 마감처리 하시겠습니까?</h4>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" class="btn btn-default" data-dismiss="modal" onclick="fn_program_force_close()">확인</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>

      <!-- 프로그램선택 -->
      <div class="modal fade" id="modal-program">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">프로그램을 선택하세요</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="programClose">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body" id="PROGRAM_TABLE">
				<div class="card card-primary card-outline">
			 	 	<div class="card-body">
 <form name="modalSearchVO" id="modalSearchVO" method="get">
			 	 	 	<div class="row">
			              	<div class="col-5">
			                   	<hisco:OrgList defaultNo="${modalSearchVO.orgNo}" id="orgNo" />
			                </div>
			            </div>
			            <div class="row" style="margin-top:10px">
							<div class="col-12">
								<input type="text" class="form-control" name="edcPrgmnm" id="edcPrgmnm" maxlength="100" placeHolder="검색할 프로그램명을 입력하세요"/>
							</div>
			            </div>
</form>
			 	   	</div>
		            <div class="row" style="margin-bottom:15px;text-align:right">
			 	    	<div class="col-4"></div>
			 	    	<div class="col-2">
			 	    		<button type="button" class="btn btn-primary btn-block" onclick="fn_modal_program_find('search')">조회</button>
			 	    	</div>
			 	    	<div class="col-2">
			 	    		<button type="button" class="btn btn-secondary btn-block" onclick="fn_modal_reset()">초기화</button>
			 	    	</div>
			 	    	<div class="col-5"></div>
			 	     </div>
		 	   	</div>

		 	   	<div class="row">
		          <div class="col-12">
		            <div class="card">
		              <!-- /.card-header -->
		              <div class="card-body table-responsive p-0" style="overflow:scroll; height:400px;">
			               <table class="table table-bordered table-hover text-nowrap">
			               	 <colgroup>
								<col style="width: 10%;">
								<col style="width: 15%;">
								<col style="width: *">
								<col style="width: 9%;">
							  </colgroup>
			                  <thead>
			                    <tr>
			                      	<th align='center'>분야</th>
			                      	<th align='center'>프로그램고유번호</th>
			                      	<th align='center'><a href="javascript:fn_modal_program_find('prgmnm');" id="togglenm">프로그램명▲</a></th>
			                      	<th align='center'>공개여부</th>
			                    </tr>
			                  </thead>
			                  <tbody id="modalProgramTbody">
			                  	<tr>
									<td colspan="4" align="center">기관선택은 필수 입니다.</td>
								</tr>
								<!-- <tr>
									<td>기타</td>
									<td>1000001</td>
									<td>프로그램명1</td>
									<td>공개여부</td>
								</tr>
								<tr>
									<td>기타</td>
									<td>1000002</td>
									<td>프로그램명2</td>
									<td>공개여부</td>
								</tr> -->
			                  </tbody>
			                </table>
		              </div>
		          </div>
		        </div>
		      </div>

            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" id="onclick-btn" class="btn btn-primary" onclick="fn_pick_program()">선택프로그램 적용</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <script>
      	$(document).ready(function() {
      		fn_set_tr_click();

      		$("#modalSearchVO").find("#edcPrgmnm").keypress(function(e) {
      			if (e.keyCode == 13) {
      				fn_modal_program_find("search");
      				e.preventDefault();
      			}
      		});
      	});


		function fn_check_search(){
			if($("#modalSearchVO").find("#orgNo").val() != $('#searchOrgNo').val()){
				$("#modalSearchVO").find("#orgNo").val($('#searchOrgNo').val()).trigger('change');
				fn_modal_program_find();
			}
		}


      	function fn_modal_program_find(src) {
			var data = {};
			data.searchOrderBy = "BY_PROGRAM_NM_ASC";


			if (src == "prgmnm") {
				if ($("#togglenm").text().indexOf("▼") > 0) data.searchOrderBy = "BY_PROGRAM_NM_ASC";
				else data.searchOrderBy = "BY_PROGRAM_NM_DESC";
			}

			var orgNo = $("#modalSearchVO").find("#orgNo").val();
			var edcPrgmnm = $("#modalSearchVO").find("#edcPrgmnm").val();

			if (orgNo) data.searchOrgNo = orgNo;
			if (edcPrgmnm){
				data.searchKeyword = edcPrgmnm;
				data.searchCondition = "1";
			}

			if (!orgNo) {
				alert("기관선택은 필수 입니다.");
				return;
			}

			var htmlArray = [];
			var html = "<tr><td align='center'>#CATE#</td><td align='center'>#PRGMID#</td><td align='left'>#PRGMNM#</td><td align='center'>#OPENYN#</td></tr>";
			$.ajax({
				url: './edcProgramList.json',
				data: data,
				success: function(data) {
			    	$.each(data.programList, function(idx, item) {
			    		var temp = html.replace("#CATE#", (item.comCtgNm==undefined?'-':item.comCtgNm));
			    		temp = temp.replace("#PRGMID#", item.edcPrgmNo);
			    		temp = temp.replace("#PRGMNM#", item.edcPrgmnm);
			    		temp = temp.replace("#OPENYN#", item.edcOpenyn);
			    		htmlArray[idx] = temp;
			    	});
			    	$("#modalProgramTbody").html(htmlArray.join(""));

			    	if(htmlArray.length < 1){
			    		$("#modalProgramTbody").html('<tr><td colspan="4" align="center">검색된 데이타가 없습니다.</td></tr>');
			    	}

			    	fn_set_tr_click();
			    	fn_modal_programnm_toggle();
				}
			});
		}

      	function fn_modal_programnm_toggle() {
      		var linkText = $("#togglenm").text();
      		if (linkText.indexOf("▼") > 0) {
      			$("#togglenm").text("프로그램명▲");
      		} else {
      			$("#togglenm").text("프로그램명▼");
      		}
      	}

		function fn_modal_reset() {
			$("#modalSearchVO").find("#orgNo option:eq(0)").attr("selected", "");
			$("#modalSearchVO").find("#select2-orgNo-container").attr("title", "기관선택").text("기관선택");
			$("#modalSearchVO")[0].reset();
			$("#modalProgramTbody").html('<tr><td colspan="4" align="center">조건 선택 후 조회하세요</td></tr>');
		}

      	var pickPrgmNo = "";
      	var pickPrgmnm = "";
      	/*프로그램클릭*/

      	function fn_set_tr_click() {
      		$("#modalProgramTbody > tr").click(function() {
      			$("#modalProgramTbody tr").css( "background-color", "" ).css("color" , "#666");
      			pickPrgmNo = $(this).find("td:eq(1)").text();
      			pickPrgmnm = $(this).find("td:eq(2)").text();
      			$(this).css("background-color" , "#adb5bd").css("color" , "white");
      		});
      	}



      	/*프로그램선택*/
      	function fn_pick_program() {
      		//검색조건 일치 시키기
      		$('#searchOrgNo').val($("#modalSearchVO").find("#orgNo").val()).trigger('change');
      		
      		$("#edcPrgmNo").val(pickPrgmNo);
      		$("#edcPrgmnm").val(pickPrgmnm);
      		$("#programClose").trigger("click");
      	}

      	/*모집차수목록조회*/
      	function fn_get_rsvnset_nm_list() {
      		var options = [];
      		options.push('<option value="">모집차수(선택)</option>');
      		$.get({
				url: './edcRsvnsetNmList.json',
				data: {orgNo: $("#searchOrgNo").val()},
				success: function(data) {
					$.each(data.rsvnsetNmList, function(idx, rsvnsetNm) {
						options.push('<option value="' + rsvnsetNm + '">' + rsvnsetNm + '</option>');
			    	});

					$("#edcRsvnsetNm").children("option").remove();
					$("#edcRsvnsetNm").append(options.join(""));
					$("#edcRsvnsetNm").prop("disabled", options.length > 1 ? false : true);
					
					if (options.length > 1) $("#edcRsvnsetNm :eq(1)").prop("selected", true);
				}
			});
      	}
      </script>
      <div id="divHidden" style="display:none"></div>
</section>
</body>