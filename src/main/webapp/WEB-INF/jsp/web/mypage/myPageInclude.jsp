<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="URI" value="${pageContext.request.requestURI}" />
<c:set var="splitStr" value="${fn:split(URI, '/') }" />
<div class="pagelTab">
	<div class="pagelTab-inner">
		<div class="pagelTab-wrap mt-5 mb-5">
			<ul class="nav nav-tabs pagelTab-list pagelTab-list-4" role="tablist">
				<li class="nav-item pagelTab-item" role="presentation">
					<a href="javscript:void(0)" class="nav-link pagelTab-link disabled" data-bs-toggle="tab" role="tab" aria-controls="home" aria-selected="false">
						<figure class="pagelTab-figure">
							<img src="../../resources/images/sub/ico_profile.png" alt="아이콘">
						</figure>
						<p class="pagelTab-txt"><c:out value="${userInfo.name }"/>님 안녕하세요.</p>
					</a>
				</li>
				<li class="nav-item pagelTab-item" role="presentation" onclick="javscript:goPageLink('/web/mypage/myinfor/myInforRegist')">
					<a href="/web/mypage/myinfor/myInforRegist" <c:out value='${splitStr[fn:length(splitStr)-2] eq "myinfor" ? "class=\'nav-link pagelTab-link active\' aria-selected=\'true\'"  : "class=\'nav-link pagelTab-link\' aria-selected=\'false\'"}' escapeXml="false"/> role="tab" aria-controls="profile">
						<figure class="pagelTab-figure">
							<img src="../../resources/images/sub/ico_tab_06.png" alt="아이콘">
						</figure>
						<p class="pagelTab-txt"><c:out value="내정보관리"/></p>
					</a>
				</li>
				<li class="nav-item pagelTab-item" role="presentation" onclick="javscript:goPageLink('/web/mypage/myRsvn/myRsvnList')">
					<a href="/web/mypage/myRsvn/myRsvnList" <c:out value='${splitStr[fn:length(splitStr)-2] eq "myRsvn" || splitStr[fn:length(splitStr)-2] eq "lckr" ? "class=\'nav-link pagelTab-link active\' aria-selected=\'true\'"  : "class=\'nav-link pagelTab-link\' aria-selected=\'false\'"}' escapeXml="false"/> role="tab" aria-controls="profile">
						<figure class="pagelTab-figure">
							<img src="../../resources/images/sub/ico_tab_07.png" alt="아이콘">
						</figure>
						<p class="pagelTab-txt">내예약관리</p>
					</a>
				</li>
				<li class="nav-item pagelTab-item" role="presentation" onclick="javscript:goPageLink('/web/mypage/myWriting/myWritingList')">
					<a href="/web/mypage/myWriting/myWritingList" <c:out value='${splitStr[fn:length(splitStr)-2] eq "myWriting" ? "class=\'nav-link pagelTab-link active\' aria-selected=\'true\'"  : "class=\'nav-link pagelTab-link\' aria-selected=\'false\'"}' escapeXml="false"/> role="tab" aria-controls="profile">
						<figure class="pagelTab-figure">
							<img src="../../resources/images/sub/ico_tab_08.png" alt="아이콘">
						</figure>
						<p class="pagelTab-txt">내글관리</p>
					</a>
				</li>
			</ul>
		</div>
	</div>
</div>
<script>
function goPageLink(url){
	window.location.href=url;
}
</script>