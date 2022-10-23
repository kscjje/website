<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<head>
<style type="text/css">
.sort,.sort-ajax {font-size:16px; cursor:pointer}
.sort.active,.sort-ajax.active {color:red}
.dc-selected { background-color:#AAA; }
.dc-selected td{color:white}
</style>
<script type="text/javascript" src="../resources/js/sales/common.js"></script>
<script type="text/javascript">
var fn_loadDetail = function(dcType, orgNo, odrKey, odrDir) {
	var queryStr = "dcType="+dcType+"&searchOrgNo="+orgNo;
		queryStr += "&searchStartDts="+jQuery("#searchStartDts").val();
		queryStr += "&searchEndDts="+jQuery("#searchEndDts").val();
		queryStr += "&itemCtgd="+jQuery("#itemCtgd").val();
	if(odrKey && odrDir) {
		queryStr += "&searchOrder="+odrKey;
		queryStr += "&searchOrderDir="+odrDir;
	}

	jQuery.ajax({
		url: "./discountDetailAjax?"+queryStr,
		type: "GET",
		dataType : "html",
		success: function(html) {
			jQuery("#discount-detail-body").html(html);
		}
	})
};

jQuery(document).ready(function(){
	jQuery("#discount-list-body tr").on("click", function(){
		var $this = jQuery(this);
		var dcType = $this.attr("dc-type") || "";
		var orgNo = $this.attr("org-no");
		if(orgNo) {
			jQuery("#discount-list-body tr.dc-selected").removeClass("dc-selected");
			$this.addClass("dc-selected");

			fn_loadDetail(dcType, orgNo);
		}
	});

	jQuery(".sort-ajax").on("click", function(e){
		var $this = jQuery(this);
		var $row = jQuery("#discount-list-body tr.dc-selected");
		var wasAsc = $this.attr("sort-dir") == "asc";
		var sortDir = null;
		var sortTxt = null;

		if($this.hasClass("active")) {
			if(wasAsc) {
				sortDir = "desc";
				sortTxt = "▼";
			} else {
				sortDir = "asc";
				sortTxt = "▲";
			}
		} else {
			var $prevSort = jQuery(".sort-ajax.active");
			$prevSort.attr("sort-dir", "desc");
			$prevSort.removeClass("active");

			$this.addClass("active");
			sortDir = "asc";
			sortTxt = "▲";
		}
		$this.attr("sort-dir", sortDir);
		$this.text(sortTxt);


		if($row.length) {
			var dcType = $row.attr("dc-type") || "";
			var orgNo = $row.attr("org-no");
			var odrKey = $this.attr("id");
			var odrDir = $this.attr("sort-dir");
			if(orgNo) {
				fn_loadDetail(dcType, orgNo, odrKey, odrDir);
			}
		}
	});
});


function fn_excelDown() {
	var $cform = $('#searchVO').clone(true);

	$cform.attr('id', 'excelDownloadForm');
	$cform.attr('name', 'excelDownloadForm');
	$cform.attr('method', 'get');
	$cform.attr('action', "./discountListExcel");

	$('body').append($cform);
	$('#excelDownloadForm').submit();
	$('#excelDownloadForm').remove();
}

function fn_excelDown2() {
	var $row = jQuery("#discount-list-body tr.dc-selected");

	var odrKey = jQuery(".sort-ajax.active").attr("id");
	var odrDir = jQuery(".sort-ajax.active").attr("sort-dir");


	if($row.length) {
		var dcType = $row.attr("dc-type") || "";
		var orgNo = $row.attr("org-no");

		if(orgNo) {
			var queryStr = "dcType="+dcType+"&searchOrgNo="+orgNo;
			queryStr += "&searchStartDts="+jQuery("#searchStartDts").val();
			queryStr += "&searchEndDts="+jQuery("#searchEndDts").val();
			queryStr += "&itemCtgd="+jQuery("#itemCtgd").val();
			if(odrKey && odrDir) {
				queryStr += "&searchOrder="+odrKey;
				queryStr += "&searchOrderDir="+odrDir;
			}

			window.location.href = "./discountDetailExcel?"+queryStr;
		}
	}else{
		alert("상단의 조회결과를 선택하세요.");
	}
}

</script>
</head>

<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>주민자치센터 수입지출내역</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>    
<section class="content">
      <div class="container-fluid">
      	<div class="row">
      		<div class="col-12">
      			<form:form commandName="searchVO" name="searchVO"  method="get" >
      			<form:hidden path="searchOrder" />
				<form:hidden path="searchOrderDir" />
      			<form:hidden path="itemCtgd" />
      			<div class="card card-primary card-outline">
	      			<div class="card-body search-body">
	      				<div class="row">
	      					<div class="col-3">
	      						<button type="button" class="btn btn-outline-secondary date-diff-btn" date-diff="1">1일</button>
	      						<button type="button" class="btn btn-outline-secondary date-diff-btn" date-diff="7">7일</button>
	      						<button type="button" class="btn btn-outline-secondary date-diff-btn" date-diff="15">15일</button>
	      						<button type="button" class="btn btn-outline-secondary date-diff-btn" date-diff="30">30일</button>
	      						<button type="button" class="btn btn-outline-secondary date-diff-btn" date-diff="90">90일</button>
	      						<button type="button" class="btn btn-outline-secondary now-month-btn">당월</button>
	      					</div>
							<div class="col-4">
									<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest" >
				                        <form:input cssClass="form-control datepicker" path="searchStartDts" autocomplete="off" placeHolder="거래기간(시작일자)"/>
				                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
				                    </div>

									<div style="width:10%;float:left;text-align:center">~</div>
									<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest">
				                        <form:input cssClass="form-control datepicker" path="searchEndDts" autocomplete="off" placeHolder="거래기간(종료일자)"/>
				                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
				                    </div>
							</div>
	      				</div>
	      				<hr/>
	      				<div class="row">
	      					<div class="col-3">
								<form:select path="orgKind" title="기관유형(선택)"  class="form-control">
									<form:option value=""  >기관유형(선택)</form:option>
									${hisco:makeOptions("SM_ORG_LTYPE", "EMPTY", searchVO.orgKind)}
								</form:select>
	      					</div>
	      					<div class="col-3">
	      						<form:select path="searchOrgNo" title="기관(선택)"  class="form-control">
	      							<form:option value=""  >기관(선택)</form:option>
	      							<c:forEach items="${orgList}" var="item" varStatus="status">
	      								<form:option value="${item.orgNo}" data-orgkind="${item.orgLtype}">${item.orgNm}</form:option>
	      							</c:forEach>
	      						</form:select>
	      					</div>
	      					<div class="col-3">
	      					<!--
		      					<div class="input-group">
	 								<form:input cssClass="form-control" path="comCtgnm" placeHolder="프로그램분야 검색" readonly="true"/>
	 								<div class="input-group-append">
	 									<button class="btn btn-secondary" type="button" onclick="fn_find_category()" data-toggle="modal" data-target="#modal-default">
				              				<i class="fas fa-search fa-fw"></i> 찾기
				            			</button>
				            		</div>
	 							</div>
	 							 -->
	      					</div>
	      				</div>
	      			</div>
      			</div>
	  			<div class="row mb-3 justify-content-center">
			    	<div class="col-2">
			    		<button type="submit" class="btn btn-primary btn-block">조회</button>
			    	</div>
			    	<div class="col-2">
			    		<button type="button" onclick="fn_reset()" class="btn btn-secondary btn-block">초기화</button>
			    	</div>
			    </div>
		 	   <div class="row mb-2">
					 <div class="col-2 pt-1">
					 	조회결과 <b><fmt:formatNumber value="${commandMap.pagingInfo.totalRecordCount}"/></b> 건
					 </div>
					 <div class="col-10" style="text-align:right">
						<button type="button" onclick="fn_excelDown()" class="btn  btn-secondary btn-sm btn-flat">엑셀다운</button>
					</div>
		       </div>
      			</form:form>
      		</div>
      	</div>
      	<div class="row mb-2">
			<div class="col-12">
				<div class="card">
					<div class="card-body table-responsive p-0" style="max-height:300px;overflow-y:auto;">
						<table class="table table-bordered  text-nowrap">
							<caption style="display:none"></caption>
			               	<colgroup>
			               		<col style="width:10%"/>
			               		<col style="width:5%"/>
			               		<col style="width:10%"/>
			               		<col style="width:5%"/>
			               		<!-- 무료까지 30% -->
			               		<col style="width:7%"/>
			               		<col style="width:7%"/>
			               		<col style="width:7%"/>
			               		<col style="width:7%"/>
			               		<col style="width:7%"/>
			               		<!-- 수입내역 35% -->
			               		<col style="width:7%"/>
			               		<col style="width:7%"/>
			               		<col style="width:7%"/>
			               		<col style="width:7%"/>
			               		<col style="width:7%"/>
			               		<!-- 나머지 35% -->
			               	</colgroup>
			               	<thead>
			               		<tr>
			               			<th rowSpan="2">할인사유</th>
			               			<th rowSpan="2">할인율</th>
			               			<th rowSpan="2">기관명<span id="orgNm" class="sort">▼</span></th>
			               			<th>무료</th>
			               			<th colSpan="5">수입내역</th>
			               			<th colSpan="2">환불내역</th>
			               			<th colSpan="3">합계</th>
			               		</tr>
			               		<tr>
			               			<th>인원(수량)</th>
			               			<th>인원(수량)</th>
			               			<th>현금</th>
			               			<th>카드</th>
			               			<th>실시간이체</th>
			               			<th>가상계좌</th>
			               			<th>인원(수량)</th>
			               			<th>환불금액</th>
			               			<th>총액</th>
			               			<th>공급가액</th>
			               			<th>부가세</th>
			               		</tr>
			               	</thead>
			               	<tbody id="discount-list-body">
			               		<tr >
			               			<td></td>
			               			<td></td>
			               			<td>[계]</td>
									<td style="text-align:right">${total.freeCnt}</td>
									<td style="text-align:right">${total.payCnt}</td>
									<td style="text-align:right"><fmt:formatNumber value="${total.cashAmt}" type="number" groupingUsed="true"/></td>
									<td style="text-align:right"><fmt:formatNumber value="${total.cardAmt}" type="number" groupingUsed="true"/></td>
									<td style="text-align:right"><fmt:formatNumber value="${total.bankAmt}" type="number" groupingUsed="true"/></td>
									<td style="text-align:right"><fmt:formatNumber value="${total.vbankAmt}" type="number" groupingUsed="true"/></td>
									<td style="text-align:right">${total.refundCnt}</td>
									<td style="text-align:right"><fmt:formatNumber value="${total.refundAmt}" type="number" groupingUsed="true"/></td>
									<td style="text-align:right"><fmt:formatNumber value="${total.totAmt}" type="number" groupingUsed="true"/></td>
									<td style="text-align:right"><fmt:formatNumber value="${total.prvAmt}" type="number" groupingUsed="true"/></td>
									<td style="text-align:right"><fmt:formatNumber value="${total.vatAmt}" type="number" groupingUsed="true"/></td>
			               		</tr>
								<c:choose>
									<c:when test="${empty list}">
									<tr><td colSpan="14">데이터가 없습니다.</td></tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${list}" var="item" varStatus="status">
										<tr dc-type="${item.discountcd}" org-no="${item.orgNo}" style="cursor:pointer">
											<td>${item.dcReason}</td>
											<td>${item.dcRate}</td>
											<td>${item.orgNm}</td>
											<td style="text-align:right">${item.freeCnt}</td>
											<td style="text-align:right">${item.payCnt}</td>
											<td style="text-align:right"><fmt:formatNumber value="${item.cashAmt}" type="number" groupingUsed="true"/></td>
											<td style="text-align:right"><fmt:formatNumber value="${item.cardAmt}" type="number" groupingUsed="true"/></td>
											<td style="text-align:right"><fmt:formatNumber value="${item.bankAmt}" type="number" groupingUsed="true"/></td>
											<td style="text-align:right"><fmt:formatNumber value="${item.vbankAmt}" type="number" groupingUsed="true"/></td>
											<td style="text-align:right">${item.refundCnt}</td>
											<td style="text-align:right"><fmt:formatNumber value="${item.refundAmt}" type="number" groupingUsed="true"/></td>
											<td style="text-align:right"><fmt:formatNumber value="${item.totAmt}" type="number" groupingUsed="true"/></td>
											<td style="text-align:right"><fmt:formatNumber value="${item.prvAmt}" type="number" groupingUsed="true"/></td>
											<td style="text-align:right"><fmt:formatNumber value="${item.vatAmt}" type="number" groupingUsed="true"/></td>
										</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
			               	</tbody>
			            </table>
			        </div>
			    </div>
			</div>
      	</div>
      	<div class="row mb-2">
			 <div class="col-12" style="text-align:right">
				<button type="button" onclick="fn_excelDown2()" class="btn  btn-secondary btn-sm btn-flat">엑셀다운</button>
			</div>
      	</div>
      	<div class="row">
			<div class="col-12">
				<div class="card">
					<div class="card-body table-responsive p-0" style="max-height:300px;overflow-y:auto;">
						<table class="table table-bordered table-hover text-nowrap">
							<caption style="display:none"></caption>
			               	<colgroup>
								<col style="width:5%;"/>
								<col style="width:5%"/>
								<col style="width:10%"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:10%;"/>
								<col style="width:10%;"/>
								<!-- 55% 상품명까지 -->
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<!-- 20% 감면사유까지-->
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<col style="width:5%;"/>
								<!-- 25% OID까지-->
							</colgroup>
							<thead>
								<tr>
									<th>No.</th>
									<th>거래일자<span id="payDate" class="sort-ajax">▼</span></th>
									<th>기관명<span id="orgNm" class="sort-ajax">▼</span></th>
									<th>영수증번호</th>
									<th>회원번호</th>
									<th>회원명<span id="memNm" class="sort-ajax">▼</span></th>
									<th>강좌명<span id="edcPrgmnm" class="sort-ajax">▼</span></th>
									<th>상품명<span id="itemNm" class="sort-ajax">▼</span></th>
									<th>수량</th>
									<th>단가</th>
									<th>감면금액</th>
									<th>감면사유</th>
									<th>결제수단<span id="payType" class="sort-ajax">▼</span></th>
									<th>카드사/은행<span id="methodNm" class="sort-ajax">▼</span></th>
									<th>수입금액<span id="payAmt" class="sort-ajax">▼</span></th>
									<th>수납자명</th>
									<th>OID</th>
								</tr>
							</thead>
							<tbody id="discount-detail-body">
								<tr><td colSpan="17">상단의 조회결과를 선택하세요.</td></tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
      	</div>
   </div>
</section>
</body>