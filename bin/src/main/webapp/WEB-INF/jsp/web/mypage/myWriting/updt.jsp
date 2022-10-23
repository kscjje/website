<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function fn_egov_regist_article() {

	var form = document.articleVO;
	if($("#ctgId").val() == ""){
		alert("기관을 선택해주세요.");
		$("#ctgId").focus();
		return;
	}
	if($("#nttSj").val().trim()==""){
		alert("제목을 입력해주세요.");
		$("#nttSj").focus();
		return;
	}
	if($("#nttCn").val().trim()==""){
		alert("내용을 입력해주세요.");
		$("#nttCn").focus();
		return;
	}
<c:if test="${articleVO.bbsId eq 'qna' }">
	if($('input:radio[name=secretAt]').is(':checked') == false){
		alert("공개여부를 선택해주세요.");
		$("#secretY").focus();
		return;
	}
</c:if>

	var msg = "등록하시겠습니까?";
	if (form.nttId.value != "" && form.nttId.value != "0") {
		msg = "수정하시겠습니까?";
	}

	if (confirm(msg)) {
		form.submit();
	}

}
</script>
<main class="content" id="content">
	<div class="subVisual">
		<div class="subVisual-inner">
			<figure class="subVisual-figure">
				<img src="/web/resources/images/sub/img_subVisual_04.png" alt="서브 비주얼">
			</figure>
			<h3 class="subVisual-title position-absolute start-50 top-50 translate-middle">마이페이지</h3>
        </div>
	</div>
	<div class="pageTit">
		<div class="pageTit-inner">
			<div class="pageTit-wrap">
				<h3 class="pageTit-title"><c:out value="${articleVO.bbsId eq 'freeboard'?'자유게시판' : '묻고 답하기'}"/></h3>
				<ul class="pageTit-list">
					<li class="pageTit-item">
						<a href="javascript:void(0)" title="홈 이동" class="pageTit-link">
							<img src="/web/resources/images/sub/ico_snb_home.png" alt="홈">
						</a>
              		</li>
					<li class="pageTit-item">
						<a href="javascript:void(0)" title="이동" class="pageTit-link">마이페이지</a>
					</li>
					<li class="pageTit-item">
						<a href="javascript:void(0)" title="이동" class="pageTit-link">내글관리</a>
					</li>
					<li class="pageTit-item">
						<a href="javascript:void(0)" title="이동" class="pageTit-link"><c:out value="${articleVO.bbsId eq 'freeboard'?'자유게시판' : '묻고 답하기'}"/></a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="sub">
		<div class="myTable">
			<div class="myTable-inner">
				<div class="myTable-wrap pt-0">
					<p class="myTable-text">
						<span class="star">*</span>표시는 필수 입력사항입니다.
					</p>
					<form:form commandName="articleVO" name="articleVO" action="./save" method="post" enctype="multipart/form-data" autocomplete="off" onsubmit="return false;">
						<form:hidden path="nttId"/>
						<form:hidden path="ntcrId"/>
						<form:hidden path="ntcrNm"/>
						<table>
							<colgroup>
								<col style="width: 13%">
								<col style="width: 87%">
							</colgroup>
							<tbody>
								<c:if test="${articleVO.bbsId eq 'qna' }">
									<tr>
										<th scope="row">
											<span>기관</span> <span class="star">*</span>
										</th>
										<td>
											
											<div class="searchBar-item">
												<c:set var="ctgTitle" value="기관 선택"/>
												<button type="button" class="btn" id="btnCtgTitle">기관 선택</button>
												<ul class="searchBar-list">
													<form:hidden path="ctgId"/>
													<c:forEach items="${ctgList}" var="item" varStatus="status">
														<li>
															<button type="button" onclick="changeCategory('<c:out value="${item.ctgId }"/>')"><c:out value="${item.ctgNm }" /> 주민자치센터</button>
														</li>
														<c:if test="${articleVO.ctgId eq item.ctgId }">
															<c:set var="ctgTitle" value="${item.ctgNm }"/>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
								</c:if>
								<tr>
									<th scope="row"><span>제목</span> <span class="star">*</span>
									</th>
									<td>
										<div class="w_box">
											<div class="input-group ">
												<label for="nttSj" class="visually-hidden">제목</label>
												<form:input path="nttSj" title="제목" maxlength="70" placeholder="제목을 입력해주세요."/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row"><span>내용</span> <span class="star">*</span>
									</th>
									<td><form:textarea path="nttCn" title="내용" placeholder="내용을 입력해주세요."/></td>
								</tr>
								<c:if test="${articleVO.bbsId eq 'qna' }">
								<tr>
									<th scope="row">
										<span>공개여부</span> <span class="star">*</span>
									</th>
									<td>
										<div class="radio">
											<span>
												<input name="secretAt" <c:out value='${articleVO.secretAt ne "Y" ? "checked " : ""}'/> id="secretN" type="radio" value="N">
												<label for="secretN">공개</label>
											</span>
											<span>
												<input name="secretAt" <c:out value='${articleVO.secretAt eq "Y" ? "checked " : ""}'/> id="secretY" type="radio" value="Y">
												<label for="secretY">비공개</label>
											</span>
										</div>
									</td>
								</tr>
								</c:if>
							</tbody>
						</table>
					</form:form>
				</div>
			</div>
		</div>
		<div class="badge-btn">
			<a href="javascript:fn_egov_regist_article()" class="green">등록</a>
			<a href="javascript:history.back()" class="gray">취소</a>
		</div>
		<!--// sub_content  -->
	</div>
</main>      
<script>
/*기관세팅 함수*/
function changeCategory(data){
	$('#ctgId').val(data);
}
<c:if test="${articleVO.bbsId eq 'qna' }">
var selectCtg = '<c:out value="${ctgTitle } 주민자치센터" /> ';
if(selectCtg == ''){
	selectCtg = '기관 선택';
}
console.log(selectCtg)
$('#btnCtgTitle').html(selectCtg);
</c:if>
</script>