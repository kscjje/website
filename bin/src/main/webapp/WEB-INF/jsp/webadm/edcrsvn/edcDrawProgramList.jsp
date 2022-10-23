<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<style type="text/css">
		th {background-color:#f4f6f9;}
	</style>
	<script src="${requestScope.resourceContext}/js/jquery.dataTables.min.js"></script>
	<script>
		$(document).ready(function(){
			$.each(["#searchOrgNo", "#searchYear", "#searchMonth"], function(idx, element) {
				$(element).change(function () {
					$('#pageIndex').val(1);
					fn_get_draw_program_list();
					fn_reset_pannels();
					fn_disable_buttons();
				});
			});

			fn_get_draw_program_list();

			$("#modalChkAll").click(function() {
				$("#modalMemberTbody :checkbox").prop("checked", $(this).prop("checked") ? true : false);
			});
		});

		function fn_get_draw_program_list() {
			$.ajax({
			    url : './edcDrawProgramListAjax',
			    data : $('#searchVO').serialize(),
				dataType : 'html',
				success: function(data) {
			    	$("#divDrawCard").html(data);
				}
		    });
		}

		function fn_set_page(page) {
			var page = page > 0 ? page : 1;
			$('#pageIndex').val(page);
			fn_get_draw_program_list();
		}

		function fn_add_drawer(obj) {
			var chkCnt = $("#tbodyDrawProgramList :checkbox:checked").length;
			if (chkCnt < 1) {
				alert("먼저 프로그램 1건을 선택해 주세요.");
				return false;
			}
			if (chkCnt > 1) {
				alert("담첨자 지정추가는 1건의 프로그램에 한해서 가능합니다.");
				$("#pickClose").trigger("click");
				return false;
			}
			fn_get_rsvn_list();
			$(".pick").trigger("click");
		}

		function fn_get_rsvn_list() {
			var data = {};
			data.channel = $("#searchVO").find("#channel").val();
			data.periodCondition = $("#searchVO").find("#periodCondition").val();
			data.searchYear = $("#searchVO").find("#searchYear").val();
			data.searchMonth = $("#searchVO").find("#searchMonth").val();

			$("#tbodyDrawProgramList > tr").each(function() {
				if ($(this).find("#edcPrgmNo").prop("checked")) {
					data.edcPrgmNo = $(this).find("#edcPrgmNo").val();
					data.edcPrgmnm = $(this).find("#edcPrgmnm").val()
					data.orgNo = $(this).find("#searchOrgNo").val();
				}
			});

			$.post({
			    url : './edcRsvnInfoList.json', 
			    data : JSON.stringify(data),
			    contentType : "application/json; charset=UTF-8",
			    async : false,
				success: function(data) {
					var html = [];

					if (data.rsvnInfoList == null || data.rsvnInfoList.length < 1) {
						html.push('<td colspan="6" align="center">신청자가 존재하지 않습니다.</td>');
					} else {
						$.each(data.rsvnInfoList, function(idx, item) {
							html.push('<tr>');
							html.push('	<td><input type="checkbox" name="modalmemid" value="' + item.id + '"/></td>');
							html.push('	<td>' + item.edcMemNo + '</td>');
							html.push('	<td>' + item.edcReqDtime + '</td>');
							html.push('	<td>' + item.edcRsvnCustnm + "(" + item.genderNm + ")" + '</td>');
							html.push('	<td>' + fn_reformat_hp(item.edcRsvnMoblphon, '-') + '</td>');
							html.push('	<td>' + fn_reformat_yyyymmdd(item.edcRsvnBirthdate, '-') + '</td>');
							html.push('</tr>');
						});
					}
					//console.log(html.join(""));
			    	$("#modalMemberTbody").html(html.join(""));
	    			$("#spanModalEdcPrgmnm").text($("#modalEdcPrgmnm").val());
	    			$("#spanModalEdcRsvnPeriod").text($("#modalEdcRsvnPeriod").val());
			    	fn_set_data_table(); //table 정렬처리
				}
		    });
		}

		function fn_set_data_table() {
      		$('#memberTable').DataTable({
      			columnDefs: [
      				{orderable: false, targets: [0, 1, 2, 3, 5]}
      			],
      			"order": [[ 2, "asc" ]], //수강신청일시
      			"paging": false,
      			"searching": false,
      			"info": false,
      			"destroy": true
      	    });
      	}

		//당첨자 지정추가버튼 활성/비활성
		function fn_change_pickbtn_status() {
			if ($("#tbodyDrawProgramList :checkbox:checked").length == 1) {
				$("#pickBtn").prop("disabled", false);
			} else {
				$("#pickBtn").prop("disabled", true);
			}
		}

		function fn_change_dodrawbtn_status() {
			if ($("#tbodyDrawProgramList :checkbox:checked").length > 0) {
				$("#doDrawBtn").prop("disabled", false);
			} else {
				$("#doDrawBtn").prop("disabled", true);
			}
		}

		function fn_change_undodrawbtn_status() {
			if ($("#tbodyDrawProgramList :checkbox:checked").length == 1) {
				$("#undoDrawBtn").prop("disabled", false);
			} else {
				$("#undoDrawBtn").prop("disabled", true);
			}
		}

		//테이블row삭제
		function fn_delete_row(obj) {
			$(obj).parent().parent().remove();
		}

		//추첨
		function fn_do_draw() {
			if ($("#pickBtn").prop("disabled") && _pickCnt > 0) {
				if (confirm("1개이상 프로그램을 지정시에는 당첨자를 지정할 수 없습니다. 삭제할까요?")) {
					$("#firstDrawMemberTbody > tr").remove();
					$("#pickBtn").prop("disabled", true);
					_pickCnt = 0;
				}
				return;
			}

			var data = {};
			var targetProgramList = [];
			var firstConsiderMemberList = [];

			//선택 프로그램 파마미터 추출
			$("#tbodyDrawProgramList > tr").each(function() {
				if ($(this).find("#edcPrgmNo").prop("checked")) { //checkbox가 checked 되어 있을 경우
					var obj = {};
					obj.edcPrgmNo = $(this).find("#edcPrgmNo").val();
					obj.edcPrgmnm = $(this).find("#edcPrgmnm").val();
					obj.orgNo = $(this).find("#searchOrgNo").val();
					obj.edcRsvnsetSeq = $(this).find("#edcRsvnsetSeq").val();
					targetProgramList.push(obj);
				}
			});

			$("#firstDrawMemberTbody > tr").each(function() {
				var custnm = $(this).find("td:eq(3)").text();
				var idx = custnm.indexOf("(");
				if (idx > 0) {
					custnm = custnm.substring(0, idx); //성별제거
				}

				var obj = {};
				obj.edcMemNo = $(this).find("td:eq(1)").text();
				obj.id = $(this).find("td:eq(2)").text();
				obj.edcRsvnCustnm = custnm;
				obj.edcRsvnMoblphon = $(this).find("td:eq(4)").text();
				obj.edcRsvnBirth = $(this).find("td:eq(5)").text();
				firstConsiderMemberList.push(obj);
			});

			data.targetProgramList = targetProgramList;
			data.firstConsiderMemberList = firstConsiderMemberList;

			data.searchYear = $("#searchYear").val();
			data.searchMonth = $("#searchMonth").val();
			data.prevMonthOutYn = $("#prevOutFirstYn").val();

			$.post({
			    url : './edcRsvnInfoDoDraw.json',
			    data : JSON.stringify(data),
			    contentType : "application/json; charset=UTF-8",
				success : function(data) {

					if (data.result.code == "SUCCESS") {

						var html = [];
						$.each(data.resultVO.targetProgramList, function(idx, item) {
							html.push("<div class='card-body table-responsive p-0' style='overflow:scrolly; height:400px;'>");
							html.push("	<div class='col-12' id='drawResultPrgmNmDiv'><strong>" + item.edcPrgmnm + "</strong></div>");
							html.push("	<div class='col-12'>");
							html.push("	   <table class='table table-bordered text-nowrap' style='text-align:center' id='drawResultTable" + idx + "'>");
							html.push("			<colgroup>");
							html.push("				<col style='width: 10%;'>");
							html.push("				<col style='width: 20%;'>");
							html.push("				<col style='width: 20%'>");
							html.push("				<col style='width: 20%'>");
							html.push("				<col style='width: 15%'>");
							html.push("				<col style='width: *'>");
							html.push("			</colgroup>");
							html.push("			<thead>");
							html.push("				<tr>");
							html.push("					<th>순번▼</th>");
							html.push("					<th>회원ID▼</th>");
							html.push("					<th>회원명▼</th>");
							html.push("					<th>휴대폰번호▼</th>");
							html.push("					<th>생년월일▼</th>");
							html.push("					<th>추첨결과▼</th>");
							html.push("				</tr>");
							html.push("			</thead>");
							html.push("			<tbody id='drawResultTbody'>");

							if (!item.drawResultList || item.drawResultList.length < 1) {
								html.push("				<tr>");
								html.push("					<td colspan='6'>추첨결과가 존재하지 않습니다.</td>");
								html.push("				</tr>");
							} else {
								$.each(item.drawResultList, function (idx2, item2) {
									html.push("				<tr>");
									html.push("					<td>" + (idx2 + 1) + "</td>");
									html.push("					<td>" + item2.id + "</td>");
									html.push("					<td>" + item2.edcRsvnCustnm + "</td>");
									html.push("					<td>" + fn_reformat_hp(item2.edcRsvnMoblphon, "-") + "</td>");
									html.push("					<td>" + fn_reformat_yyyymmdd(item2.edcRsvnBirthdate, "-") + "</td>");
									html.push("					<td>" + item2.przwinGbnNm + "</td>");
									html.push("				</tr>");
								});
							}

							html.push("			</tbody>");
							html.push("	  </table>");
							html.push("	</div>");
							html.push("</div>");

						});

						$("#drawResultGuideDiv").hide();
						$("#drawResultDiv > div:gt(1)").remove();
						$("#drawResultDiv").append(html.join(""));

						//되돌리기버튼 활성처리
						$("#undoDrawBtn").prop("disabled", false);
						//당첨확정버튼 활성처리
						$("#confirmDrawBtn").prop("disabled", false);


						$.each(data.resultVO.targetProgramList, function(idx, item) {
							$('#drawResultTable' + idx).DataTable({
				      			columnDefs: [
				      				//{orderable: false, targets: [0, 1, 2, 3, 5]}
				      			],
				      			//"order": [[ 2, "asc" ]], //수강신청일시
				      			"paging": false,
				      			"searching": false,
				      			"info": false,
				      			"destroy": true
				      	    });
						});
					}

					alert(data.result.msg);
				}
		    });
		}

		//당첨완료 여부 체크
		function fn_check_draw_done() {
			var result = false;
			$("#drawResultTbody td:nth-child(6)").each(function() {
				if ($(this).text() == "당첨") {
					result = true;
					return false;
				}
			});
			return result;
		}

		//추첨진행 여부 체크
		function fn_check_draw_ing() {
			var result = false;
			$("#drawResultTbody td:nth-child(6)").each(function() {
				if ($(this).text() == "예비당첨") {
					result = true;
					return false;
				}
			});
			return result;
		}

		//되돌리기
		function fn_undo_draw() {
			if (fn_check_draw_done()) {
				alert("이미 당첨확정된 상태라 실행할 수 없습니다");
				return;
			}
			if (!fn_check_draw_ing()) {
				alert("추첨 후 실행할 수 있습니다");
				return;
			}

			if (confirm("추첨결과를 되돌리시겠습니까?")) {
				$.post({
				    url : './edcRsvnInfoUndoDraw.json',
					success : function(data) {
						if (data.result.code == "SUCCESS") {
							$("#drawResultGuideDiv").show();
							$("#drawResultDiv > div:gt(1)").remove();
							//되돌리기버튼 활성처리
							$("#undoDrawBtn").prop("disabled", true);
							//당첨확정버튼 활성처리
							$("#confirmDrawBtn").prop("disabled", true);
						}
						alert(data.result.msg);
					}
				});
			}
		}
		
		function fn_excel_down() {
			$("#excelForm").submit();			
		}

		//확정하기
		function fn_confirm_draw() {
			if (fn_check_draw_done()) {
				alert("이미 당첨확정된 상태라 실행할 수 없습니다");
				return;
			}
			if (!fn_check_draw_ing()) {
				alert("추첨 후 실행할 수 있습니다");
				return;
			}

			if (confirm("추첨결과를 확정하시겠습니까?")) {
				$.post({
				    url : './edcRsvnInfoConfirmDraw.json',
					success : function(data) {
						if (data.result.code == "SUCCESS") {
							$("#drawResultGuideDiv").show();
							$("#drawResultDiv > div:gt(1)").remove();
							//되돌리기버튼 활성처리
							$("#undoDrawBtn").prop("disabled", true);
							//당첨확정버튼 활성처리
							$("#confirmDrawBtn").prop("disabled", true);
							//프로그램목록 재조회
							fn_get_draw_program_list();
							fn_reset_pannels();
						}
						alert(data.result.msg);
					}
				});
			}
		}

		//추천완료건 추첨결과 조회
		function fn_get_draw_done_rsvn_list(orgNo, edcPrgmNo, edcRsvnsetSeq, periodCondition, searchYear, searchMonth, channel) {
			
			$("#excelDownBtn").prop("disabled", true);
			
			if (!orgNo) {//추첨진행건
				$("#drawResultGuideDiv").show();
				$("#drawResultDiv > div:gt(1)").remove();
				return;
			}

			$('#programTable').find("tbody tr").css("background-color" , "#fff").css("color" , "#666");

			var query = [];
			query.push("orgNo=" + orgNo);
			query.push("edcPrgmNo=" + edcPrgmNo);
			query.push("edcRsvnsetSeq=" + edcRsvnsetSeq);
			query.push("periodCondition=" + periodCondition);
			query.push("searchYear=" + searchYear);
			query.push("searchMonth=" +searchMonth);
			query.push("channel=" + channel);
			
			$("#orgNo", $("#excelForm")).val(orgNo);
			$("#edcPrgmNo", $("#excelForm")).val(edcPrgmNo);
			$("#edcRsvnsetSeq", $("#excelForm")).val(edcRsvnsetSeq);
			$("#periodCondition", $("#excelForm")).val(periodCondition);
			$("#searchYear", $("#excelForm")).val(searchYear);
			$("#searchMonth", $("#excelForm")).val(searchMonth);
			$("#channel", $("#excelForm")).val(channel);
			
			var existResult = false;

			$.get({
			    url : './edcDrawDoneRsvnInfoList.json',
			    data : query.join("&"),
				success : function(data) {

					var html = [];
					html.push("<div class='card-body table-responsive p-0' style='overflow:scrolly; height:800px;'>");
					html.push("	<div class='col-12' id='drawResultPrgmNmDiv'><strong></strong></div>");
					html.push("	<div class='col-12'>");
					html.push("	   <table class='table table-bordered text-nowrap' style='text-align:center' id='drawResultTable0'>");
					html.push("			<colgroup>");
					html.push("				<col style='width: 10%;'>");
					html.push("				<col style='width: 20%;'>");
					html.push("				<col style='width: 20%'>");
					html.push("				<col style='width: 20%'>");
					html.push("				<col style='width: 15%'>");
					html.push("				<col style='width: *'>");
					html.push("			</colgroup>");
					html.push("			<thead>");
					html.push("				<tr>");
					html.push("					<th>순번▼</th>");
					html.push("					<th>회원ID▼</th>");
					html.push("					<th>회원명▼</th>");
					html.push("					<th>휴대폰번호▼</th>");
					html.push("					<th>생년월일▼</th>");
					html.push("					<th>추첨결과▼</th>");
					html.push("				</tr>");
					html.push("			</thead>");
					html.push("			<tbody id='drawResultTbody'>");

					if (!data.rsvnInfoList || data.rsvnInfoList.length < 1) {
						html.push("				<tr>");
						html.push("					<td colspan='6'>추첨결과가 존재하지 않습니다.</td>");
						html.push("				</tr>");
					} else {
						existResult = true;
						$.each(data.rsvnInfoList, function(idx, item) {
							html.push("				<tr>");
							html.push("					<td>" + (idx + 1) + "</td>");
							html.push("					<td>" + item.id + "</td>");
							html.push("					<td>" + item.edcRsvnCustnm + "(" + item.genderNm + ")" + "</td>");
							html.push("					<td>" + fn_reformat_hp(item.edcRsvnMoblphon, "-") + "</td>");
							html.push("					<td>" + fn_reformat_yyyymmdd(item.edcRsvnBirthdate, "-") + "</td>");
							html.push("					<td>" + item.przwinGbnNm + "</td>");
							html.push("				</tr>");
						});
					}

					html.push("			</tbody>");
					html.push("	  </table>");
					html.push("	</div>");
					html.push("</div>");

					$("#drawResultGuideDiv").hide();
					$("#drawResultDiv > div:gt(1)").remove();
					$("#drawResultDiv").append(html.join(""));

					fn_disable_buttons();
					$("#excelDownBtn").prop("disabled", !existResult);

					$("#drawResultTable0").DataTable({
		      			columnDefs: [
		      				//{orderable: false, targets: [0, 1, 2, 3, 5]}
		      			],
		      			"order": [[ 5, "asc" ]], //추첨결과
		      			"paging": false,
		      			"searching": false,
		      			"info": false,
		      			"destroy": true
		      	    });
				}
		    });
		}

		function fn_reset_pannels() {
			$("#firstDrawMemberTbody").html("<tr><td colspan='6'>[담첨자 지정추가]버튼을 통해 추가하세요</td></tr>");
			$("#drawResultTbody").html("<tr><td colspan='6'>추첨 결과 목록 출력됩니다</td></tr>");
			$("#drawResultPrgmNmDiv").hide();
		}

		function fn_disable_buttons() {
			//되돌리기버튼 활성처리
			$("#undoDrawBtn").prop("disabled", true);
			//당첨확정버튼 활성처리
			$("#confirmDrawBtn").prop("disabled", true);
			//엑셀버튼 비활성처리
			$("#excelDownBtn").prop("disabled", true);
		}

		function fn_reformat_hp(str, delemeter) {
			if (str.indexOf("-") > 0) return str;
			if (str.length < 10) return str;
			if (str.length == 10) {
				return str.substring(0,3) + delemeter + str.substring(3,6) + delemeter + str.substring(6,10);
			} else {
				return str.substring(0,3) + delemeter + str.substring(3,7) + delemeter + str.substring(7,11);
			}
		}

		function fn_reformat_yyyymmdd(str, delemeter) {
			if (str.indexOf("-") > 0) return str;
			if (str.length < 8) return str;
			return str.substring(0,4) + delemeter + str.substring(4,6) + delemeter + str.substring(6,8);
		}
	</script>
</head>

<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>강좌 추첨</h1>
          </div>
          <div class="col-sm-6" style="text-align:right">
	          		<a class="btn btn-secondary btn-sm btn-flat" href="javascript:history.back()">이전화면</a>
	          </div>
        </div>
      </div>
</section>

<!-- Main content -->
<section class="content">

      <div class="container-fluid">

		 <div class="row">

		 	<!-- 프로그램 목록 -->
            <div class="col-md-6">
				<div class="card card-primary card-outline">
					<form:form commandName="searchVO" name="searchVO" method="get">
					<form:hidden path="pageIndex" />
					<form:hidden path="edcPrgmNo" />
					<input type="hidden" name="channel" id="channel" value="BO"/>
					<input type="hidden" name="periodCondition" id="periodCondition" value="RSVNYM"/>
					<div class="card-body p-0">
			 	 	   <div class="col-12">
			              <!-- /.card-header -->
		                  <table class="table">
		                  		<colgroup>
									<col style="width:22%;">
									<col style="width:15%;">
									<col style="width:20%">
									<col style="width:17%">
									<col style="width:5%">
									<col style="width:*">
								</colgroup>
								<tbody>
				                  <tr id="trCondition">
					                  <td style="padding-left:0px;padding-bottom:0px;">
					                  	<hisco:OrgList defaultNo="${searchVO.searchOrgNo}" id="searchOrgNo"/>
					                  </td>
					                  <td style="padding-top:20px;padding-bottom:0px;text-align:right;">접수년월</td>
					                  <td style="padding-bottom:0px;">
					                  	<select id="searchYear" name="searchYear" title="년도" class="form-control">
					                  		${hisco:makeYearOptions("년도", 30, searchVO.searchYear)}
					                  	</select>
					                  </td>
					                  <td style="padding-bottom:0px;">
					                  	<select id="searchMonth" name="searchMonth" title="월"  class="form-control">
					                  		${hisco:makeMonthOptions("월", searchVO.searchMonth)}
					                  	</select>
					                  </td>
					                  <td style="padding-bottom:0px;">&nbsp;</td>
					                  <td style="padding-bottom:0px;padding-right:0px">
					                  	<select name="prevMonthOutYn" id="prevMonthOutYn" title="월"  class="form-control">
					                  		<option value="">추첨옵션(선택)</option>
					                  		<option value="Y">이전접수탈락자우선</option>
					                  	</select>
					                  </td>
				                  </tr>
				               </tbody>
		                  </table>
		               </div>
		            </div>
		           </form:form>
		 	   	   <!-- 추첨대상 강좌목록 -->
		       	   <div class="card-body table-responsive p-0" style="overflow:scrollx; height:570px;">
			 	 	   <div class="col-12" id="divDrawCard">
			 	 	   	<!-- table -->
			            </div>
			       </div>
			       <!-- 추첨, 되돌리기 버튼 -->
			       <div class="card-body">
			       		<div class="col-12" style="text-align:right">
		 	   	  	  		<button type="button" class="btn  btn-secondary btn-sm btn-flat" onclick="fn_do_draw()" disabled="true" id="doDrawBtn">추첨</button>
		 	   	  	  		<button type="button" class="btn  btn-secondary btn-sm btn-flat" onclick="fn_undo_draw()" disabled="true" id="undoDrawBtn">되돌리기</button>
		 	   	  	  	</div>
		 	   	   </div>

			       <!-- 당첨자 지정추가 -->
			       <div class="card-body">
		 	   	  	  <button type="button" class="btn btn-secondary btn-sm btn-flat" onclick="fn_add_drawer(this)" disabled="true" id="pickBtn" >당첨자 지정추가</button>
		 	   	  	  <button type="button" class="btn btn-secondary btn-sm btn-flat pick" data-toggle="modal" data-target="#modal-pick" style="display:none"/>
		 	   	   </div>
			       <div class="card-body table-responsive p-0">
			 	 	  <div class="col-12">
			              <!-- /.card-header -->
		                   <table class="table table-bordered text-nowrap" style="text-align:center">
			               	   <colgroup>
									<col style="width: 5%;">
									<col style="width: 20%;">
									<col style="width: 20%">
									<col style="width: 20%">
									<col style="width: 10%">
									<col style="width: *">
								</colgroup>
								<thead>
									<tr>
										<th>순번</th>
										<th>회원ID</th>
										<th>회원명</th>
										<th>휴대폰번호</th>
										<th>생년월일</th>
										<th>비고</th>
									</tr>
								</thead>
				                <tbody id="firstDrawMemberTbody">
									<tr>
										<td colspan="6">[담첨자 지정추가]버튼을 통해 추가하세요</td>
									</tr>
				                </tbody>
			              </table>
			           </div>
			       </div>
		 	   	</div>
		 	 </div>

		 	 <!-- 추첨결과 -->
		 	 <div class="col-md-6">
		 	   	<div class="card card-primary card-outline" id="drawResultDiv">
		 	   	  <div class="card-body drawResultTop" style="padding-bottom:15px">
		 	   	    <div class="row">
			 	   	  	<div class="col-2" style="text-align:left">
			 	   	  		<h6>[추첨결과]</h6>
			 	   	  	</div>
			 	   	  	<div class="col-10" style="text-align:right">
			 	   	  		<button type="button" class="btn btn-success btn-sm btn-flat" disabled="true" onclick="fn_excel_down()" id="excelDownBtn">EXCEL</button>
			 	   	  	  	<button type="button" class="btn btn-secondary btn-sm btn-flat" disabled="true" onclick="fn_confirm_draw()" id="confirmDrawBtn">당첨확정</button>
			 	   	  	</div>
			 	   	  </div>
		 	   	  </div>
		 	   	  <div class="card-body table-responsive p-0" style="overflow:scrollx; height:400px;" id="drawResultGuideDiv">
			 	 	<div class="col-12">
			              <!-- /.card-header -->
		                   <table class="table table-bordered text-nowrap" style="text-align:center">
				               	<colgroup>
									<col style="width: 10%;">
									<col style="width: 20%;">
									<col style="width: 20%">
									<col style="width: 20%">
									<col style="width: 15%">
									<col style="width: *">
								</colgroup>
								<thead>
									<tr>
										<th>순번</th>
										<th>회원ID</th>
										<th>회원명</th>
										<th>휴대폰번호</th>
										<th>생년월일</th>
										<th>추첨결과</th>
									</tr>
								</thead>
				                <tbody>
									<tr>
										<td colspan="6">추첨 결과 목록 출력됩니다</td>
									</tr>
				                </tbody>
			              </table>
			        </div>
			     </div>
		 	 </div>
		</div>
      </div>

      <!-- 수강신청회원선택 모달팝업 -->
      <div class="modal fade" id="modal-pick">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">수강신청회원을 선택하세요</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="pickClose">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body" id="MEMBER_TABLE">
				<div class="card card-primary card-outline">
			 	 	<div class="card-body">
            			<form name="modalSearchVO" id="modalSearchVO" method="get">
			 	 	 	<div class="row">
			 	 	 		<div class="col-12">
			 	 	 			<h5 class="modal-title">강좌프로그램 : <span id="spanModalEdcPrgmnm"></span></h5>
			 	 	 		</div>
			            </div>
			            <div class="row">
			              	<div class="col-12">
			                   	<h5 class="modal-title">접수기간&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : <span id="spanModalEdcRsvnPeriod"></span></h5>
			                </div>
			            </div>
			            <div class="row" style="margin-top:10px">
			            	<div class="col-4">
					            <select id="selSearchCond" title="모집차수"  class="form-control select2">
			             			<option value="">검색조건(선택)</option>
			             			<option value="HP">휴대폰번호</option>
			             			<option value="NM">신청회원명</option>
								</select>
							</div>
							<div class="col-8">
								<input type="text" class="form-control" name="searchKeyword" id="searchKeyword" maxlength="100" placeHolder="검색어를 입력하세요"/>
							</div>
			            </div>
		 				</form>
			 	   	</div>
		            <div class="row" style="margin-bottom:15px;text-align:right">
			 	    	<div class="col-4"></div>
			 	    	<div class="col-2">
			 	    		<button class="btn btn-secondary btn-block" onclick="fn_modal_reset()">초기화</button>
			 	    	</div>
			 	    	<div class="col-2">
			 	    		<button class="btn btn-primary btn-block" onclick="fn_modal_member_search()">조회</button>
			 	    	</div>
			 	    	<div class="col-5"></div>
			 	     </div>
		 	   	</div>

		 	   	<div class="row">
		          <div class="col-12">
		            <div class="card">
		              <!-- /.card-header -->
		              <div class="card-body table-responsive p-0" style="overflow:scroll; height:400px;">
			               <table class="table table-bordered table-hover text-nowrap" style="text-align:center" id="memberTable">
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
			                      	<th><input type="checkbox" id="modalChkAll" name="modalmemberid" value=""/></th>
			                      	<th>회원번호</th>
			                      	<th>수강신청일시</th>
			                      	<th>수강회원명</th>
			                      	<th style="cursor:pointer" id="thSort">전화번호 ▲</th>
			                      	<th>생년월일</th>
			                    </tr>
			                  </thead>
			                  <tbody id="modalMemberTbody">
			                  	<!-- <tr>
									<td colspan="5" align="center">조건 선택 후 조회하세요</td>
								</tr>
								<tr>
									<td>100001</td>
									<td>2021-11-10 13:00:01</td>
									<td>홍길동1</td>
									<td>010-8844-0001</td>
									<td>1974-04-18</td>
								</tr>
								-->
			                  </tbody>
			                </table>
		              </div>
		          </div>
		        </div>
		      </div>

            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" id="onclick-btn" class="btn btn-primary" onclick="fn_set_first_drawmember()">선택회원적용</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <script>
      	$(document).ready(function() {
      		$("#modalSearchVO").find("#searchKeyword").keypress(function(e) {
      			if (e.keyCode == 13) {
      				fn_modal_member_search("search");
      				e.preventDefault();
      			}
      		});

      		$("#thSort").click(function() {
      			var text = $(this).text();
      			if (text.indexOf("▼") > 0) {
      				$(this).text(text.replace("▼", "▲"));
      			} else {
      				$(this).text(text.replace("▲", "▼"));
      			}
      		});
      	});

      	function fn_modal_member_search(src) {
      		var keyword = $("#searchKeyword").val().replaceAll("-", "").toUpperCase();

      		if (!keyword) {
      			alert("검색어를 입력하세요.");
      			return;
      		}

      		$.each($("#memberTable > tbody > tr"), function(idx, item) {
      			var html = $(item).html().replaceAll("-", "").toUpperCase();
      			if (html.indexOf(keyword) > 0) {
      				$(this).show();
      			} else {
      				$(this).hide();
      			}
      		})
		}

		function fn_modal_reset() {
			$("#modalSearchVO").find("#selSearchCond option:eq(0)").attr("selected", "");
			$("#modalSearchVO").find("#searchKeyword").val("");
			$("#modalMemberTbody").find("tr").show();
		}

      	var picmemno = "";
      	/*프로그램클릭*/
      	function fn_set_tr_click() {
      		$("#modalMemberTbody > tr").click(function() {
      			$("#modalMemberTbody tr").css( "background-color", "" );
      			picmemno = $(this).find("td:eq(0)").text();
      			$(this).css( "background-color", "#f4f4f4" );
      			$(this).find("input[type=radio]").prop("checked", true);
      		});
      	}

      	/*당첨회원선택하여 바닥페이지의 담청자지어추가 영역에 리스트업*/
      	function fn_set_first_drawmember() {
      		var members = [];

      		$("#modalMemberTbody > tr > td :checkbox:checked").each(function() {
      			var memid = $(this).val();
      			var memno = $(this).parent().parent().find("td")[1];
      			var mennm = $(this).parent().parent().find("td")[3];
      			var memhp = $(this).parent().parent().find("td")[4];
      			var membirth = $(this).parent().parent().find("td")[5];

      			var member = {};
      			member.memid = memid;
      			member.memno = $(memno).text();
      			member.memnm = $(mennm).text();
      			member.memhp = $(memhp).text();
      			member.membirth = $(membirth).text();

      			members.push(member);
      		});

      		if (members.length < 1) {
      			alert("지정할 회원을 선택해 주세요.");
      			return;
      		}

      		fn_set_first_drawmember2(members);
      	}

      	/*당첨회원선택하여 바닥페이지의 담청자지어추가 영역에 리스트업*/
      	var _pickCnt = 0;
      	function fn_set_first_drawmember2(members) {
      		var html = [];
      		$.each(members, function(idx, member) {
	      		html.push("<tr>");
	      		html.push("	<td style='vertical-align:middle;'>" + (idx+1) + "</td>");
	      		html.push("	<td style='display:none'>" + member.memno + "</td>");
	      		html.push("	<td style='vertical-align:middle;'>" + member.memid + "</td>");
	      		html.push("	<td style='vertical-align:middle;'>" + member.memnm + "</td>");
	      		html.push("	<td style='vertical-align:middle;'>" + member.memhp + "</td>");
	      		html.push("	<td style='vertical-align:middle;'>" + member.membirth + "</td>");
	      		html.push("	<td><button type='button' class='btn-secondary' onclick='fn_delete_row(this)'>삭제</button>");
	      		html.push("</tr>");
      		});
      		$("#firstDrawMemberTbody").html(html.join(""));
      		$("#pickClose").trigger("click");
      		_pickCnt = members.length;
      	}
      </script>
</section>
<form name="excelForm" id="excelForm" method="get" action="./edcDrawDoneRsvnInfoListExcel">
	<input type="hidden" id="orgNo" name="orgNo" value="1"/>
	<input type="hidden" id="edcPrgmNo" name="edcPrgmNo" value=""/>
	<input type="hidden" id="edcRsvnsetSeq" name="edcRsvnsetSeq" value=""/>
	<input type="hidden" id="periodCondition" name="periodCondition" value=""/>
	<input type="hidden" id="searchYear" name="searchYear" value=""/>
	<input type="hidden" id="searchMonth" name="searchMonth" value=""/>
</form>
</body>