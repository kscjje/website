<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<div class="bySector">
		<ul class="bySector-list nav nav-pills" role="tablist">
			<li class="bySector-item nav-item" role="presentation" style="width: 20%;" onclick="javscript:goPageLink('/web/mypage/myRsvn/myRsvnList')">
				<button  <c:out value='${splitStr[fn:length(splitStr)-1] eq "myRsvnList.jsp"? "class=\'nav-link active\' aria-selected=\'true\'"  : "class=\'nav-link\' aria-selected=\'false\'"}' escapeXml="false"/> data-bs-toggle="pill" data-bs-target="#pills-home" type="button" role="tab" aria-controls="pills-home">수강신청 현황</button>
			</li>
			<li class="bySector-item nav-item" role="presentation" style="width: 20%;" onclick="javscript:goPageLink('/web/mypage/myRsvn/myRefundList')">
				<button  <c:out value='${splitStr[fn:length(splitStr)-1] eq "myRefundList.jsp"? "class=\'nav-link active\' aria-selected=\'true\'"  : "class=\'nav-link\' aria-selected=\'false\'"}' escapeXml="false"/> data-bs-toggle="pill" data-bs-target="#pills-home" type="button" role="tab" aria-controls="pills-home">수강환불 현황</button>
			</li>
			<li class="bySector-item nav-item" role="presentation" style="width: 20%;" onclick="javscript:goPageLink('/web/mypage/lckr/myLckrList')">
				<button  <c:out value='${splitStr[fn:length(splitStr)-1] eq "myLckrList.jsp"? "class=\'nav-link active\' aria-selected=\'true\'"  : "class=\'nav-link\' aria-selected=\'false\'"}' escapeXml="false"/> data-bs-toggle="pill" data-bs-target="#pills-home" type="button" role="tab" aria-controls="pills-home">사물함이력현황</button>
			</li>
			<li class="bySector-item nav-item" role="presentation" style="width: 20%;" onclick="javscript:goPageLink('/web/mypage/lckr/myLckrRefundList')">
				<button  <c:out value='${splitStr[fn:length(splitStr)-1] eq "myLckrRefundList.jsp"? "class=\'nav-link active\' aria-selected=\'true\'"  : "class=\'nav-link\' aria-selected=\'false\'"}' escapeXml="false"/> data-bs-toggle="pill" data-bs-target="#pills-home" type="button" role="tab" aria-controls="pills-home">사물함환불현황</button>
			</li>
			<li class="bySector-item nav-item" role="presentation" style="width: 20%;">
				<button  class="nav-link" data-bs-toggle="pill" data-bs-target="#pills-contact" type="button" role="tab" aria-controls="pills-contact" aria-selected="false">수료증 발급</button>
			</li>
		</ul>
	</div>