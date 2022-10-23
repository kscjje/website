<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	$(document).ready(function() {
		$("#totCount").text("총 ${totCount}개");
		if (getCookie("debug") == "Y") {
			$(".pno").show();
			$(".dlInfo").show();
		}
	});

	function fnDetailApply(edcPrgmid , edcRsvnsetSeq , nonCheck) {
		//2.내부
		var data = new Object();
		data.edcPrgmid = edcPrgmid;
		data.edcRsvnsetSeq = edcRsvnsetSeq;

		var userId = "${userInfo.id}";

		if ( userId == "" && nonCheck == "Y") {
			//비로그인 신청화면으로
			if(confirm("해당강좌를 비회원으로 신청하시겠습니까?\n(회원가입자 경우, 로그인하여 신청하세요)")){
				var _scrollTop = window.scrollY || document.documentElement.scrollTop;
				$('#scrollVar').val(_scrollTop);

				location.hash = $('#searchVO').serialize();
				location.href = "../rsvn/termsAgree/"+edcPrgmid+"/" + edcRsvnsetSeq;
			}

		}else if( userId == ""){
			alert("로그인 후 신청하실 수 있습니다.");
		} else {
			$.ajax({
				url : "<c:url value='../rsvn/termsAgreeAjax'/>/"+edcPrgmid+"/"+edcRsvnsetSeq, //신청 팝업
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
					<div style="width:100%;height:150px;text-align:center;padding-top:50px">
						<div>
							조회된 데이타가  없습니다.
						</div>
					</div>
	</c:when>
	<c:otherwise>
					<ul class="search_list li4">
						<c:forEach items="${programList}" varStatus="status" var="item">
							<li class="li<c:if test="${item.salamt > 0}"> charge</c:if>">
								<div class="txt_box">
									<div class="top_box">
										<ul>
											<li>
												<p>D-${item.edcRsvnEdday}</p>
											</li>
											<c:set var="classNm" value=""/>
											<c:choose>
												<c:when test="${item.salamt > 0}"><li class="ch_txt">유료</li><c:set var="classNm" value="ch_txt"/></c:when>
												<c:otherwise><li>무료</li></c:otherwise>
											</c:choose>
											<c:if test="${item.edcOnlineyn eq 'Y' }"><li class="green">온라인교육</li></c:if>
											<!--
											<c:choose>
												<c:when test="${item.edcRsvnRectype eq '5001' }"><li class="${classNm} green">타기관</li></c:when>
												<c:when test="${item.rsvnNonmebyn eq 'Y' and empty userInfo.id }">
													<li class="${classNm} blue02">비회원</li>
												</c:when>
											</c:choose>
											 -->
										</ul>
										<a href="javascript:fnGoDetail(${item.edcPrgmid})" class="top_tit">
											${item.edcPrgmnm}
										</a>
									</div>
									<div class="bottom_box">
										<div class="s_date">
											${fn:replace(item.edcDaygbnNm,',','')} / ${fn:substring(item.edcStime,0,2)}:${fn:substring(item.edcStime,2,4)} ~ ${fn:substring(item.edcEtime,0,2)}:${fn:substring(item.edcEtime,2,4)}
										</div>
										<div class="btn_area">
											<a href="javascript:fnGoDetail(${item.edcPrgmid}, ${item.edcRsvnsetSeq})" class="btn_s3_c4 ">상세보기</a>
											<c:if test="${(item.edcStatus eq '신청' or item.edcStatus eq '대기접수' ) and (!empty userInfo.id or item.rsvnNonmebyn eq 'Y') and item.edcRsvnAccssrd ne '2001' and item.edcRsvnRectype ne '5001'}">
												<!-- 로그인 상태이며 오프라인 접수가 아닌경우만 바로신청 가능 -->
												<a href="javascript:fnDetailApply(${item.edcPrgmid}, ${item.edcRsvnsetSeq},'${item.rsvnNonmebyn}');" class="btn_s3_c5">바로신청</a>
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