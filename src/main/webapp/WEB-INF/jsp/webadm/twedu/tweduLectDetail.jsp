<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : tweduLectDetail.jsp
	 * @Description : 마울배움터 학습현황 상세 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.11.05     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2021.12.06
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<head>
<style type="text/css">
.table .colored-head { background-color:#e9ecef;vertical-align:middle;text-align:center; }
.twedu-log-list p { white-space:pre-line;text-align:left; }
.twedu-log-list .twedu-log-preview { vertical-align:middle;text-align:center; }
.twedu-log-list .twedu-log-preview img { width:100%; max-width:400px; display:inline-block;vertical-align:middle; }
.stud-batch-btn { min-width:100px; }
</style>
<script type="text/javascript">
var fn_dom = function(tagName, attrs, styles, childs){
	var _tagName, _attrs, _styles, _childs;

	if(typeof(tagName) == "object") {
		if(tagName instanceof HTMLElement) {
			return tagName;
		} else {
			_tagName = tagName.tagName;
			_attrs = tagName.attrs || {};
			_styles = tagName.styles || {};
			_childs = tagName.childs;
		}
	} else {
		_tagName = tagName;
		_attrs = attrs || {};
		_styles = styles || {};
		_childs = childs;
	}

	var dom = document.createElement(_tagName);

	if(_attrs) {
		for(var key1 in _attrs) {
			var val = _attrs[key1];
			if(val === undefined) v = "";
			dom[key1] = val;
		}
	}

	if(_styles) {
		for(var key2 in _styles) {
			var val = _styles[key2];
			if(val === undefined) v = "";
			dom.style[key2] = val;
		}
	}

	if(_childs && _childs.length) {
		for(var i=0; i<_childs.length; i++) {
			var cDom = fn_dom(_childs[i]);

			dom.appendChild(cDom);
		}
	}
	return dom;
};

var fn_renderPaging = function(selector, first_rnum, tot_cnt) {
	var $paging = jQuery(selector);

	var MAX_ROW_PER_PAGE = 10;

	var tot_page = Math.ceil(tot_cnt/MAX_ROW_PER_PAGE);
	var cur_page =  Math.ceil(first_rnum/MAX_ROW_PER_PAGE);

	$paging.empty();
	var lis = [];
	for(var i=1; i<tot_page+1; i++) {
		var li = document.createElement("li");
		if( i == cur_page) li.className = "paginate_button current";
		else			   li.className = "paginate_button";
		li["page-index"] = i;
			var a = document.createElement("a");
			a.innerText = i;
			a.href = "javascript:void(0)";
		li.appendChild(a);
		lis.push(li);
	}
	$paging.append(lis);
};

var fn_getData = function(json, key) {
	var val = json[key];
	if(val === undefined) val = "";
	return val;
};

var fn_renderList = function(selector, data, looper, fallback) {
	var $list = jQuery(selector);
	var listId = $list.attr("data-list-id");
	var list =  listId ? data[listId] : data;

	var resultArr = [];
	if(list && list.length) {
		jQuery.each(list, function(idx, rowData){
			var result = looper($list, idx, rowData)
			resultArr.push(result);
		});
	} else {
		var fallbackResult = null;
		var fallbackMsg = "데이터가 없습니다.";
		if(fallback instanceof Function) {
			fallbackResult = fallback(fallbackMsg);
		} else {
			fallbackResult = fallbackMsg;
		}
		resultArr.push(fallbackResult);
	}

	return resultArr;
};

var fn_getImgPath = function(filePath, fileName, originName) {
	var prefix = "<%=request.getContextPath()%>/web/common/file/view/";
	var imgPath = prefix + filePath + fileName;
	imgPath += "?originName="+encodeURIComponent(originName);

	return imgPath;
};

var loadPage = function(pageId) {
	var edc_prgmid = jQuery("#edcPrgmid").val();

	switch(pageId) {
		case "report-page":
			fn_loadReport(edc_prgmid);
			break;
		case "student-page":
			fn_loadStudents(edc_prgmid);
			break;
		case "attend-page":
			fn_loadAttendances(edc_prgmid);
			break;
		case "plan-page":
			fn_loadPlan(edc_prgmid);
			break;
		case "log-page":
			jQuery("#empty-log-btn").click();
			fn_loadLog(edc_prgmid);
			break;
	}
};


var fn_loadStudents = function(edc_prgmid){
	jQuery.ajax({
		url: "./"+edc_prgmid+"/studentAjax",
		type: "GET",
		dataType : "HTML" ,
		success: function(data) {

			$('#student-page').html(data);

		}
	});
};

var fn_loadAttendances = function(edc_prgmid){
	jQuery.ajax({
		url: "./"+edc_prgmid+"/attendanceAjax",
		type: "GET",
		dataType : "HTML" ,
		success: function(data) {

			$('#attend-page').html(data);

		}
	});
};

var fn_loadLog = function(edc_prgmid){
	jQuery.ajax({
		url: "./"+edc_prgmid+"/edulogAjax",
		type: "GET",
		dataType : "HTML" ,
		success: function(data) {

			$('#log-page').html(data);

		}
	});
};

var fn_loadReport = function(edc_prgmid){
	jQuery.ajax({
		url: "./"+edc_prgmid+"/reportAjax",
		type: "GET",
		dataType : "HTML" ,
		success: function(data) {

			$('#report-page').html(data);

		}
	});
};



jQuery(document).ready(function(){
	jQuery("#pills-tab .nav-link").on("show.bs.tab", function(){
		var pageId = jQuery(this).attr("aria-controls");
		loadPage(pageId);
	});

	loadPage("student-page");
});
</script>
</head>
<body>
<input type="hidden" id="edcPrgmid" name="edcPrgmid" value="${searchVO.edcPrgmid}"/>
<input type="hidden" id="edcStatus" name="edcStatus" value="${tweduVO.edcStatus}"/>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>마을배움터 학습상세현황</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>
<!-- Main content -->
<section class="content">
      <div class="container-fluid">
      	<div class="card card-outline no-print">
    		<div class="card-header data-bind-header">
 				<h2 class="card-title" style="font-size:1.5rem;">[<span class="data-binder" data-id="edcPrgmnm"><c:out value="${tweduVO.edcPrgmnm }"/></span>]</h2>
 			</div>
 			<div class="card-body">
				<ul class="nav nav-pills nav-justified" id="pills-tab" role="tablist">

				  <li class="nav-item">
				    <a class="nav-link active" id="student-tab" data-toggle="pill" href="#student-page" role="tab" aria-controls="student-page" aria-selected="true">학생현황</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" id="attend-tab" data-toggle="pill" href="#attend-page" role="tab" aria-controls="attend-page" aria-selected="false">출석부</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" id="log-tab" data-toggle="pill" href="#log-page" role="tab" aria-controls="log-page" aria-selected="false">수업기록</a>
				  </li>
				   <li class="nav-item">
				    <a class="nav-link " id="report-tab" data-toggle="pill" href="#report-page" role="tab" aria-controls="report-page" aria-selected="false">보고서</a>
				  </li>
				</ul>


				<div class="mt-4">
					<table class="table table-bordered text-center mb-0">
						<colgroup>
							<col style="width:10%"/>
							<col style="width:20%"/>
							<col style="width:14%"/>
							<col style="width:20%"/>
							<col style="width:14%"/
							><col style="width:*"/>
						</colgroup>
						<tr>
							<th class="colored-head">진행상태/회차</th>
							<td style="text-align:left">
								${tweduVO.edcStatus } (${passCnt }/${fn:length(logList)})
							</td>
							<th class="colored-head">교육기간</th>
							<td style="text-align:left">
								${tweduVO.edcSdate } ~ ${tweduVO.edcEdate }
							</td>
							<th class="colored-head">학생(정원)수</th>
							<td style="text-align:left">
								${fn:length(studentList) }(${tweduVO.edcPncpa})명
							</td>
						</tr>
					</table>
				</div>
 			</div>
		</div>
		<div class="card card-outline">
			<div class="card-body ">
				<div class="tab-content" id="tab-page-content">
				  <div class="tab-pane fade show active" id="student-page" role="tabpanel" aria-labelledby="student-tab">


				  </div>

				  <div class="tab-pane fade " id="report-page" role="tabpanel" aria-labelledby="report-tab">

				  </div>

				  <div class="tab-pane fade" id="attend-page" role="tabpanel" aria-labelledby="attend-tab">

				  </div>
				  <div class="tab-pane fade" id="log-page" role="tabpanel" aria-labelledby="log-tab">

				  </div>
				</div>
			</div>
		</div>
      </div>
</section>
</body>