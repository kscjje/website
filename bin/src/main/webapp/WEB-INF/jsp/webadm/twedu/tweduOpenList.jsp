<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : tweduOpenList.jsp
	 * @Description : 마울배움터 개설현황 리스트 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.11.05     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2021.11.19
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<head>
<script type="text/javascript">
var fn_open_change = function(type,val,prgmId){
	jQuery.ajax({
	      url: "../../eduadm/edcProgramStatusChange.json",
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

var fn_reset = function() {
	jQuery('#searchVO').find("input[type=text],input[type=hidden]").val('');
	jQuery('#searchVO').find("select").val('');
}

var fn_updateStatus = function(edcPrgmid, edcPrg) {
	jQuery.ajax({
	      url: "./"+edcPrgmid+"/status.json",
	      type : "POST",
	      contentType: "application/json",
	      data : JSON.stringify({'edcPrg':edcPrg }),
	      success: function(data) {
	    	   if (data.result.code == "ERROR") {
		        	 alert(data.result.msg);
		        }else{
		        	 alert(data.result.msg);
		        	 location.reload();
		        }
	      }
	});
};

var fn_validateChecked = function() {
	var checkArr = [];
	jQuery(".edc-check:checked").each(function(idx, elem){
		checkArr.push(elem.value);
	});

	return checkArr;
};

var fn_batchUpdateStatus = function(idArr, status) {
	jQuery.ajax({
	      url: "./status.json",
	      type : "POST",
	      contentType: "application/json",
	      data : JSON.stringify({idArr: idArr.join(","), edcPrg: status }),
	      success: function(data) {
	    	   if (data.result.code == "ERROR") {
		        	 alert(data.result.msg);
		        }else{
		        	 alert(data.result.msg);
		        	 location.reload();
		        }
	      }
	});
};

var fn_batchUpdateRsvnDt = function(idArr, ymd, hms) {
	jQuery.ajax({
	      url: "./rsvn.json",
	      type : "POST",
	      contentType: "application/json",
	      data : JSON.stringify({idArr: idArr.join(","), ymd: ymd, hms: hms }),
	      success: function(data) {
	    	   if (data.result.code == "ERROR") {
		        	 alert(data.result.msg);
		        }else{
		        	 alert(data.result.msg);
		        	 location.reload();
		        }
	      }
	});
};

jQuery(document).ready(function(){
	jQuery('.datetype').inputmask({ mask: "y-1-2", leapday: "-02-29", placeholder: "yyyy-mm-dd", separator: "-", alias: "yyyy-mm-dd" });

	jQuery( ".datepicker" ).datepicker({
		"dateFormat":'yy-mm-dd',
		'monthNames':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'monthNamesShort':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'changeMonth' : true,
		'changeYear':true
	});

	jQuery("#open-list").on("click", ".btn-upd-prg", function(){
		var $this = jQuery(this);

		var edcPrgmid = $this.attr("prgrm-id");
		var edcPrg	  = $this.attr("prg-data");

		fn_updateStatus(edcPrgmid, edcPrg);
	});

	jQuery("#edc-check-all").on("click", function(){
		jQuery(".edc-check").prop("checked", this.checked);
	});

	jQuery("#batch-upd-stat").on("click", function(){
		var chkArr = fn_validateChecked();
		if(chkArr.length) {
			if(confirm(chkArr.length + "개를 승인처리 하시겠습니까?")){
				fn_batchUpdateStatus(chkArr, "3001");
			}

		} else {
			alert("선택된 프로그램이 없습니다.");
		}
	});

	jQuery("#batch-upd-odate").on("click", function(){
		var chkArr = fn_validateChecked();
		if(chkArr.length) {
			jQuery("#pgrm-checked-cnt").text(chkArr.length);
			jQuery("#modal-update").modal("show");
		} else {
			alert("선택된 프로그램이 없습니다.");
		}
	});

	jQuery("#modal-update .btn-save").on("click", function(){
		var chkArr = fn_validateChecked();
		var ymd = jQuery("#modal-edcRsvnSdate").val();
		var hms = jQuery("#modal-edcRsvnStime").val();

		if(/^\d{4}-\d{2}-\d{2}$/.test(ymd)) {
			fn_batchUpdateRsvnDt(chkArr, ymd, hms);
		} else {
			alert("접수 오픈일시 날자가 형식(yyyy-mm-dd)에 맞지 않습니다.");
		}

	});
});
function fn_excelDown() {
	debugger;
	var form = document.getElementById("searchVO");
	var excelForm = form.cloneNode(true);
	excelForm.id="excelDownloadForm";
	excelForm.name = "excelDownloadForm";
	excelForm.method = 'get';
	excelForm.action  = './edcOpenListExcel';

	$('body').append(excelForm);
	excelForm.submit();
	excelForm.remove();
}
</script>
</head>
<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>마을배움터 개설현황</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>
<!-- Main content -->
<section class="content">
      <div class="container-fluid">
      <form:form commandName="searchVO" name="searchVO"  method="get" >
       	<div class="card card-primary card-outline">
		 	 	<div class="card-body search-body">
		 	 		<div class="row">


		              	<div class="col-2">
	              			<form:select path="searchDate" title="날짜"  class="form-control">
	              				<form:option value=""  >기간(선택)</form:option>
	              				<form:option value="regdate" >신청기간</form:option><%-- 개설신청일(등록일) --%>
								<form:option value="edcdate">수업기간</form:option><%-- 교육시작일 --%>
							</form:select>
						</div>
						<div class="col-4">
								<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest" >
			                        <form:input cssClass="form-control datepicker" path="searchStartDts" autocomplete="off"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>

								<div style="width:10%;float:left;text-align:center">~</div>
								<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest">
			                        <form:input cssClass="form-control datepicker" path="searchEndDts" autocomplete="off"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>
						</div>
						<div class="col-3">
							<input type="hidden" name="searchCondition" value="1" />
							<form:input cssClass="form-control" path="searchKeyword" placeHolder="프로그램명을 입력하세요."  maxlength="155" />
						</div>
						<div class="col-3">
							<form:select path="searchStat" title="신청상태"  class="form-control">
								<form:option value=""  >승인상태(선택)</form:option>
								${hisco:makeOptions("SM_EDCPG_ESTBL_STAT", "EMPTY", searchVO.searchStat)}
							</form:select>
						</div>
		 	 		</div>
 				</div>

 			</div>
		    <div class="row text-right mb-3">
		    	<div class="col-4"></div>
		    	<div class="col-2">
		    		<button type="submit" class="btn btn-primary btn-block">조회</button>
		    	</div>
		    	<div class="col-2">
		    		<button type="button" onclick="fn_reset()" class="btn btn-secondary btn-block">초기화</button>
		    	</div>
		    	<div class="col-4"></div>
		    </div>
 	    </form:form>
 	   <div class="row mb-2">
			 <div class="col-12 text-right">
				<button type="button" class="btn  btn-secondary btn-sm btn-flat" onclick="fn_excelDown();">엑셀다운</button>
				<c:if test="${commandMap.selectedMenu.updYn eq 'Y' }">
					<button type="button" class="btn  btn-success btn-sm btn-flat" id="batch-upd-stat">승인처리(선택)</button>
				</c:if>
				<c:if test="${commandMap.selectedMenu.updYn eq 'Y' }">
					<button type="button" class="btn  btn-secondary btn-sm btn-flat" id="batch-upd-odate">접수일시 설정(선택)</button>
				</c:if>
			</div>
       </div>
       <div class="row">
          <div class="col-12">
            <div class="card">
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0">
	               <table class="table table-bordered table-hover text-nowrap" id="open-list">
	               	<colgroup>
						<col style="width:4%;">
						<col style="width:4%">
						<col style="width:7">
						<col style="width:7%;">
						<col style="width:*;">
						<col style="width:8%;">
						<col style="width:8%;">
						<col style="width:8%;">
						<col style="width:5%;">
						<col style="width:7%;">
						<col style="width:20%;">
						<col style="width:8%;">
					</colgroup>
	                  <thead>
	                    <tr>
	                    	<th><input type="checkbox" id="edc-check-all" value="Y"/></th>
	                      	<th>번호</th>
	                      	<th>개설 신청일</th>
	                      	<th>신청회원</th>
	                      	<th>프로그램명</th>
	                      	<th>접수시작일</th>
	                      	<th>수업시작일</th>
	                      	<th>수업종료일</th>
							<th>횟수</th>
							<th>승인상태</th>
							<th>승인처리</th>
							<th>노출여부</th>
	                    </tr>
	                  </thead>
	                  <tbody>
						<c:if test="${fn:length(list) == 0}">
							<tr>
								<td colspan="12">데이타가 없습니다</td>
							</tr>
						</c:if>
						<c:forEach items="${list}" var="item" varStatus="status">
							<tr>
								<td><c:if test="${item.edcPrg ne '0000' }"><input class="edc-check" type="checkbox" value="${item.edcPrgmid}"/></c:if>
		                      	<td>
		                      		<c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/>
		                      	</td>
		                      	<td><c:out value='${item.edcProgmDate}'/></td>
		                      	<td><c:out value="${item.memNm}"/></td>
								<td>
									<a href="./detail?edcPrgmid=${item.edcPrgmid}<c:out value="${commandMap.query}"/>"><c:out value='${item.edcPrgmnm}'/></a>
								</td>
								<td><hisco:DateUtil datestr="${item.edcRsvnSdate}${item.edcRsvnStime}" format="yy-MM-dd HH:mm"/></td>
								<td><c:out value='${item.edcSdate}'/></td>
								<td><c:out value='${item.edcEdate}'/></td>
								<td><c:out value='${item.edcClcnt}'/></td>
								<td><c:out value='${item.edcPrgcdnm}'/></td>
								<td>
											<c:choose>
												<c:when test="${item.edcPrg eq '4001' }">
													<button type="button" class="btn btn-secondary btn-sm btn-upd-prg mr-1" prg-data="4001" prgrm-id="${item.edcPrgmid}" disabled>대기</button>
												</c:when>
												<c:otherwise>
													<button type="button" class="btn btn-primary btn-sm btn-upd-prg mr-1" prg-data="4001" prgrm-id="${item.edcPrgmid}">대기</button>
												</c:otherwise>
											</c:choose>

											<c:choose>
												<c:when test="${item.edcPrg eq '5001' }">
													<button type="button" class="btn btn-secondary btn-sm btn-upd-prg mr-1" prg-data="5001" prgrm-id="${item.edcPrgmid}" disabled>검토</button>
												</c:when>
												<c:otherwise>
													<button type="button" class="btn btn-info btn-sm btn-upd-prg mr-1" prg-data="5001" prgrm-id="${item.edcPrgmid}">검토</button>
												</c:otherwise>
											</c:choose>

											<c:choose>
												<c:when test="${item.edcPrg eq '2001' }">
													<button type="button" class="btn btn-secondary btn-sm btn-upd-prg mr-1" prg-data="2001" prgrm-id="${item.edcPrgmid}" disabled>보류</button>
												</c:when>
												<c:otherwise>
													<button type="button" class="btn btn-warning btn-sm btn-upd-prg mr-1" prg-data="2001" prgrm-id="${item.edcPrgmid}">보류</button>
												</c:otherwise>
											</c:choose>

											<c:choose>
												<c:when test="${item.edcPrg eq '3001' }">
													<button type="button" class="btn btn-secondary btn-sm btn-upd-prg" prg-data="3001" prgrm-id="${item.edcPrgmid}" disabled>승인</button>
												</c:when>
												<c:otherwise>
													<button type="button" class="btn btn-success btn-sm btn-upd-prg" prg-data="3001" prgrm-id="${item.edcPrgmid}">승인</button>
												</c:otherwise>
											</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${commandMap.selectedMenu.updYn ne 'Y' }">
											${(item.edcOpenyn eq 'Y')?'공개':'비공개'}
										</c:when>
										<c:otherwise>
											<select id="openyn_${item.edcPrgmid}" onChange="fn_open_change('openyn', this.value , '${item.edcPrgmid}')" readonly>
												<option value="Y" <c:if test="${item.edcOpenyn eq 'Y' }">selected</c:if>>공개</option>
												<option value="N" <c:if test="${item.edcOpenyn eq 'N' }">selected</c:if>>비공개</option>
											</select>
										</c:otherwise>
									</c:choose>
								</td>
		                    </tr>
						</c:forEach>
	                  </tbody>
	                </table>
              </div>
              <!-- /.card-body -->
              <div class="card-footer p-0">
              	<div class="pagination_2">
					<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
				</div>
            </div>

            <!-- /.card -->
          </div>
        </div>
      </div>
     </div>
     <div class="modal fade" id="modal-update">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">접수오픈일시 설정</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
				<div class="row">
					<div class="col-12 text-center mb-4">
						선택한 <span id="pgrm-checked-cnt"></span>개의 개설프로그램의 접수 오픈 일시를 설정합니다.
					</div>
				</div>
				<div class="row mb-4 text-center justify-content-center align-items-center">
					<label for="modal-edcRsvnSdate" class="col-3 col-form-label">접수 오픈일시</label>
					<div class="col-4">
						<input type="text" id="modal-edcRsvnSdate" name="edcRsvnSdate"  title="시작일" maxlength="10" class="form-control datetype" />
					</div>
					<div class="col-3">
                		<select id="modal-edcRsvnStime" name="edcRsvnStime" class="form-control">
<c:forEach var="i" begin="0" end="23">
                			<option value="${i<10?'0':''}${i }00">${i<10?'0':''}${i }시</option>

</c:forEach>
						</select>
					</div>
	            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
              <button type="button" class="btn btn-primary btn-save">설정하기</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
</section>
</body>