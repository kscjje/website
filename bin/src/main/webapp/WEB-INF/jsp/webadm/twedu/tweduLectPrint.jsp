<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<%@ include file="../../../templates/webadm/base/header.jsp" %>
<style type="text/css">
.table .colored-head { background-color:#e9ecef;vertical-align:middle;text-align:center; }	
.twedu-log-list p { white-space:pre-line;text-align:left; }
.twedu-log-list .twedu-log-preview { vertical-align:middle;text-align:center; }
.twedu-log-list .twedu-log-preview img { width:100%; max-width:400px; display:inline-block;vertical-align:middle; }
.stud-batch-btn { min-width:100px; }
body { padding:2.5rem; }
</style>
</head>
<script type="text/javascript">
window.onbeforeprint = function(){ // 프린트 출력 후 발생하는 이벤트
  jQuery("#print-ui-box").hide();  
};

window.onafterprint = function(){ // 프린트 출력 후 발생하는 이벤트
    window.close();
};

jQuery(document).ready(function(){
	var printContent = jQuery(window.opener["report-print-box"]).clone();
	
	document.body.appendChild(printContent[0]);
	
	jQuery("#print-btn").on("click", function(){
		window.print();
	});
});
</script>
<body>
<div class="text-right row justify-content-between" id="print-ui-box">
	[인쇄 미리보기 중] <button type="button" class="btn btn-info" id="print-btn"><i class="fa fa-print"> 인쇄하기</i></button>
</div>
</body>
