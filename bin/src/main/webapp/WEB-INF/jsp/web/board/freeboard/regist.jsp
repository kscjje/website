<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function fn_egov_regist_article() {

	var form = document.articleVO;
	if($("#nttSj").val().trim()==""){
		alert("제목을 입력해주세요.");
		$("#nttSj").focus();
		return;
	}
	if ($("#nttCn").val().trim()==""){
		alert("내용을 입력해주세요.");
		$("#nttCn").focus();
		return;
	}


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
