<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	$(document).ready(function() {
		$("#totCount").text("총 ${totCount}개");
		if (getCookie("debug") == "Y") {
			$(".pno").show();
			$(".dlInfo").show();
		}
	});

	function fnDetailApply(edcPrgmNo , edcRsvnsetSeq , nonCheck) {
		//2.내부
		var data = new Object();
		data.edcPrgmNo = edcPrgmNo;
		data.edcRsvnsetSeq = edcRsvnsetSeq;

		var userId = "${userInfo.id}";

		if ( userId == "" && nonCheck == "Y") {
			//비로그인 신청화면으로
			if(confirm("해당강좌를 비회원으로 신청하시겠습니까?\n(회원가입자 경우, 로그인하여 신청하세요)")){
				var _scrollTop = window.scrollY || document.documentElement.scrollTop;
				$('#scrollVar').val(_scrollTop);

				location.hash = $('#searchVO').serialize();
				location.href = "../rsvn/termsAgree/"+edcPrgmNo+"/" + edcRsvnsetSeq;
			}

		}else if( userId == ""){
			alert("로그인 후 신청하실 수 있습니다.");
		} else {
			$.ajax({
				url : "<c:url value='../rsvn/termsAgreeAjax'/>/"+edcPrgmNo+"/"+edcRsvnsetSeq, //신청 팝업
				data : data,
				dataType : 'html',
				success : function(data) {
					if(data.indexOf("ERR") == 0){
						alert(data.substring(4,data.length));
					}else{
						$('#modal1').html(data);
						$('#modal1').popup('show');
					}
				},
				error : function(error) {
					alert("오류");
					console.log(error);
				}
			});
		}
	}
</script>
<c:choose>
	<c:when test="${empty programList}">
					<div style="width:100%;height:100px;text-align:center;padding-top:50px">
						조회된 데이타가  없습니다.
					</div>
	</c:when>
	<c:otherwise>
					<ul class="search_list invent">
						<c:forEach items="${programList}" varStatus="status" var="item">
						<li class="li <c:if test="${item.edcProgmType eq '4001'}">m_label</c:if>">
								<c:choose>
									<c:when test="${item.edcStatus eq '신청'}"><span class="ask color01">접수중</span></c:when>
									<c:when test="${item.edcStatus eq '준비'}"><span class="ask color02">${item.edcStatus}</span></c:when>
									<c:when test="${item.edcStatus eq '마감' or item.edcStatus eq '강제마감'}"><span class="ask color03">마감</span></c:when>
									<c:when test="${item.edcStatus eq '종료'}"><span class="ask color04">${item.edcStatus}</span></c:when>
									<c:when test="${item.edcStatus eq '대기접수'}"><span class="ask color05">대기<br/>접수</span></c:when>
								</c:choose>
								<div class="img_box">
									<img loading="lazy" src="<tags:UploadFileUrl filePath='${item.edcImgPath}' fileName='${item.edcImgFilenm}' originName='${item.edcImgOrigin}' />" alt="${item.edcImgOrigin}" onerror="this.src='${requestScope.resourceContext}/images/data/no_img02.jpg'"/>
									<c:if test="${item.edcProgmType eq '4001'}"><span class="label02">마을	배움터</span></c:if>
								</div>

								<div class="txt_box">
									<div class="top_box">
										<ul>
											<c:if test="${not empty item.targetName}">
												<li>${item.targetName}</li>
											</c:if>
											<c:if test="${not empty item.areaName}">
												<li>${item.areaName}</li>
											</c:if>
											<c:if test="${item.edcOnlineyn eq 'Y' }"><li class="green">온라인교육</li></c:if>
											<!--
											<c:choose>
												<c:when test="${item.edcRsvnRectype eq '5001' }"><li class="green">타기관</li></c:when>
												<c:when test="${item.rsvnNonmebyn eq 'Y' and empty userInfo.id }">
													<li class="blue02">비회원</li>
												</c:when>
											</c:choose>
											 -->
										</ul>
										<a href="javascript:;" class="top_tit">
											<span class="pno" style="display:none">${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}. </span>${item.edcPrgmnm}
										</a>
									</div>
									<div class="bottom_box">
										<dl>
											<dt class="place">기관</dt>
											<dd>${item.orgName}</dd>
										</dl>
										<dl>
											<dt class="teacher">강사</dt>
											<dd>${item.instrctrName}</dd>
										</dl>
										<dl>
											<dt class="time">시간</dt>
											<dd>
												${fn:replace(item.edcDaygbnNm,',','')} / ${fn:substring(item.edcStime,0,2)}:${fn:substring(item.edcStime,2,4)} ~ ${fn:substring(item.edcEtime,0,2)}:${fn:substring(item.edcEtime,2,4)}
											</dd>
										</dl>
										<dl>
											<dt class="price">가격</dt>
											<dd>
												<c:choose>
													<c:when test="${item.salamt > 0}">
														<fmt:formatNumber value="${item.salamt}" pattern="#,###"/>원
													</c:when>
													<c:otherwise>
														무료
													</c:otherwise>
												</c:choose>
											</dd>
										</dl>
										<div class="btn_area">
											<a href="javascript:fnGoDetail(${item.edcPrgmNo})" class="btn_s3_c4 ">상세보기</a>
											<c:if test="${(item.edcStatus eq '신청' or item.edcStatus eq '대기접수' ) and  (!empty userInfo.id or item.rsvnNonmebyn eq 'Y') and item.edcRsvnAccssrd ne '2001' and item.edcRsvnRectype ne '5001'}">
												<!-- 로그인 상태이며 오프라인 접수가 아니고 타기관 신청이 아닌 경우 바로신청 가능 -->
												<a href="javascript:fnDetailApply(${item.edcPrgmNo}, ${item.edcRsvnsetSeq},'${item.rsvnNonmebyn}');" class="btn_s3_c5">바로신청</a>
											</c:if>
										</div>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
					<!--// search_list  -->
					<div class="paginate">
						<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="javascript:fnSetPage"/>
					</div>
	</c:otherwise>
</c:choose>