<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<main class="content" id="content">

	<div class="subVisual">
		<div class="subVisual-inner">
			<figure class="subVisual-figure">
				<img src="/web/resources/images/sub/img_subVisual_02.png" alt="서브 비주얼">
			</figure>
			<h3 class="subVisual-title position-absolute start-50 top-50 translate-middle"><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h3>
        </div>
	</div>
	<div class="pageTit">
		<div class="pageTit-inner">
			<div class="pageTit-wrap">
				<h3 class="pageTit-title"><c:out value="${boardMasterVO.bbsNm }"/></h3>
				<ul class="pageTit-list">
					<li class="pageTit-item">
						<a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
							<img src="/web/resources/images/sub/ico_snb_home.png" alt="홈">
						</a>
              		</li>
					<li class="pageTit-item">
						<a href="javascript:void(0)" title="이동" class="pageTit-link"><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></a>
					</li>
					<li class="pageTit-item">
						<a href="javascript:void(0)" title="이동" class="pageTit-link"><c:out value="${boardMasterVO.bbsNm }"/></a>
					</li>
				</ul>
			</div>
		</div>
	</div>

	<div class="sub">
		<div class="detail">
			<div class="detail-inner">
				<div class="detail-wrap">
					<div class="detail-head">
						<h4 class="detail-title">
							<c:if test="${result.noticeAt eq 'Y' }"><a class="btn_s2_c1" href="javascript:">공지</a></c:if>
							<c:if test="${(!empty ctgList) && fn:length(result.ctgNm)>0}">
								<em class="st1">[<c:out value="${result.ctgNm}"/>]</em>
							</c:if>
							<c:out value="${result.nttSj}"/>
                		</h4>
						<div class="detail-desc">
							<div class="desc-left">
								<p>작성자 : </p>
								<span><c:out value="${result.frstRegisterNm}"/></span>
							</div>
							<div class="desc-right">
								<span>
									<img src="../../resources/images/sub/ico_article_05.png" alt="아이콘"><fmt:formatDate value="${result.frstRegisterPnttm}" pattern="yyyy-MM-dd"/>
								</span>
								<span>
									<img src="../../resources/images/sub/ico_article_07.png" alt="아이콘"><c:out value="${result.inqireCo}"/>
								</span>
                  			</div>
						</div>
					</div>
					<div class="detail-con">
						<c:out value="${fn:replace(result.nttCn , crlf , '<br/>')}" escapeXml="false" />
					</div>
				</div>
			</div>
		</div>
        
        
<c:if test="${nextBoardVo!=null ||  prevBoardVo!=null}">
		<div class="pageMove">
			<c:if test="${prevBoardVo!=null}">
				<a href="./detail?nttId=<c:out value="${prevBoardVo.nttId}"/>" class="pageMove-link prev">
		            <div>
						<span>이전글</span>
						<p class="truncate-1">
							<c:if test="${(!empty ctgList) && fn:length(prevBoardVo.ctgNm)>0}">
								<em class="st1">[<c:out value="${prevBoardVo.ctgNm}"/>]</em>
							</c:if>
							<c:out value="${prevBoardVo.nttSj}"/>              
						</p>
					</div>
					<span class="pageMove-data"><fmt:formatDate value="${prevBoardVo.frstRegisterPnttm}" pattern="yyyy.MM.dd"/></span>
				</a>	
			</c:if>
			<c:if test="${nextBoardVo!=null}">
				<a href="./detail?nttId=<c:out value="${nextBoardVo.nttId}"/>" class="pageMove-link next">
					<div>
						<span>다음글</span>
						<p class="truncate-1">
							<c:if test="${(!empty ctgList) && fn:length(nextBoardVo.ctgNm)>0}">
								<em class="st1">[<c:out value="${nextBoardVo.ctgNm}"/>]</em>
							</c:if>
							<c:out value="${nextBoardVo.nttSj}"/>
						</p>
					</div>
					<span class="pageMove-data"><fmt:formatDate value="${nextBoardVo.frstRegisterPnttm}" pattern="yyyy.MM.dd"/></span>
				</a>	
			</c:if>
		</div>
</c:if>
		<div class="badge-btn">
			<a href="./list" class="black">목록</a>
			<c:if test="${commandMap.userInfo.id eq result.ntcrId}">
				<a href="./regist?nttId=<c:out value="${result.nttId}"/>" class="green">수정</a>
				<a href="javascript:fn_delete_article(<c:out value="${result.nttId}"/>)" class="gray">삭제</a>
			</c:if>
		</div>
		<!--// sub_content  -->
	</div>
</main>
<script>

function fn_delete_article(nttId) {
		
	if (confirm("삭제하시겠습니까?")) {
		
		$.ajax({
			
			url : "./delete",
			method : 'POST',
			data : {
				nttId : nttId 
			},
			dataType : 'json',
		    beforeSend : function(xhr) {
		    	xhr.setRequestHeader("${_csrf.headerName}",  "${_csrf.token}");
		    },												
			error : function() {
				alert('삭제 오류가 발생하였습니다.');
			},
			
			success : function(data) {
				const obj = JSON.parse(data);
				alert(obj.msg);
				if(obj.result == true){
					top.location.href="./list";
				}
			}
			
		});			
		
		
	}

}

</script>
