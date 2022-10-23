<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : instrPoolRegi.jsp
	 * @Description : 강사Pool관리 상세 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.11.05     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2021.11.10
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<head>
<script type="text/javascript">
var JSON_REQ_SUFFIX = ".json";

var fn_save = function() {
	var form = jQuery("#instrPoolVO")[0];


	var msg = "수정하시겠습니까?";
	if (confirm(msg)) {
		form.submit();
	}
};

var fn_delete = function() {
	var msg = "삭제하시겠습니까?";

	if (confirm(msg)) {
		jQuery.ajax({
			url : "./delete/<c:out value='${instrPoolVO.memNo}'/>"+JSON_REQ_SUFFIX,
			type: "POST",
			success: function(data) {
				if (data.result.code == "ERROR") {
					alert(data.result.msg);
				} else {
					alert(data.result.msg);
					window.location.replace("./list<c:out value="${searchString }"/>");
				}
			}
		});
	}
};

jQuery(document).ready(function(){
	jQuery("#instrctr-save").on("click", function() {
		fn_save();
	});

	jQuery("#instrctr-del").on("click", function() {
		fn_delete();
	});

	jQuery("#search-member").on("click", function(){
		jQuery("#modal-default").modal("show");
	});

	jQuery("#modal-default").on("show.bs.modal", function(){
		fn_searchMember();
	});

	jQuery("#member-table tbody").on("click", "tr", function(){
		var data = this["raw-data"];
		jQuery("#memNo").val(data["memNo"]);
		jQuery("#memNm").val(data["memNm"]);
		jQuery("#modal-default").modal("hide");
	});

	jQuery("#member-paging").on("click", "li", function(){
		fn_searchMember(this["page-index"]);
	});

	<c:if test="${commandMap.selectedMenu.updYn eq 'Y' }">
		$('#state').change(function(){
			var changedTxt = $(this).find("option:selected").text();

			if(confirm(changedTxt + " 상태로 변경하시겠습니까?")){
				jQuery.ajax({
					url : "./updateState/<c:out value='${instrPoolVO.memNo}'/>"+JSON_REQ_SUFFIX,
					data : {'state' : $(this).val()},
					type: "POST",
					success: function(data) {
						if (data.result.code == "ERROR") {
							alert(data.result.msg);
						} else {
							alert(data.result.msg);
							//window.location.replace("./list");
						}
					}
				});
			}
		});

	</c:if>
});
</script>
</head>
<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>강사은행 현황</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>
<section class="content">
<div class="container-fluid">
<form:form commandName="instrPoolVO" name="instrPoolVO" class="form-horizontal" method="post" action="./modifySave" enctype="multipart/form-data">
<form:hidden path="memNo" />
<form:hidden path="proflImageid" />
<input type="hidden" name="searchQuery" value="<c:out value="${searchString }"/>" />
	<div class="row">
		<div class="col-12">
			<div class="card card-primary card-outline">
              <div class="card-body">
              		<div><h5 style="color:black">[개인 정보]</h5> </div>
              		<table class="table table-bordered text-nowrap">
		               	<colgroup>
							<col style="width:5%;">
							<col style="width:45%;">
							<col style="width:5%;">
							<col style="width:45%;">
						</colgroup>

						<tbody>
						<tr>
							<th>이름</th>
							<td class="left"><c:out value="${instrPoolVO.memNm}"/></td>
							<th>아이디</th>
							<td class="left"><c:out value="${instrPoolVO.id}"/></td>
						</tr>
						<tr>
							<th>이메일</th>
							<td class="left"><c:out value="${instrPoolVO.email}"/></td>
							<th>휴대폰</th>
							<td class="left"><c:out value="${instrPoolVO.hp}"/></td>
						</tr>
						<tr>
							<th>성별</th>
							<td class="left"><c:out value="${instrPoolVO.genderGbn}"/></td>
							<th>출생년도</th>
							<td class="left">${fn:substring(instrPoolVO.birthDate,0,4)}</td>
						</tr>
						<tr>
							<th>개인홈피/블로그</th>
							<td class="left">
								<form:input path="proflEtc" title="강의가능지역" class="form-control " maxlength="100" />
   								<div><form:errors path="proflEtc" cssClass="error" /></div>
							</td>
							<th>연락처 공개여부</th>
							<td class="left">
								<div class="radio">
									<span>
										<form:radiobutton path="eduinfoOpenyn" value="Y"/>
										<label for="eduinfoOpenyn1">공개</label>
									</span>
									<span style="margin-left:10px">
										<form:radiobutton path="eduinfoOpenyn" value="N"/>
										<label for="eduinfoOpenyn2">비공개</label>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<th>신청상태</th>
							<td class="left">
								<form:select path="state" title="신청상태" class="form-control">
		                    		<form:options items="${stateList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
		                    	</form:select>
							</td>
							<th></th>
							<td></td>
						</tr>
              			<tr>
							<th>등록일</th>
							<td class="left">${instrPoolVO.reguser} | <fmt:formatDate value="${instrPoolVO.regdate}" pattern="yyyy.MM.dd HH:mm"/></td>
							<th>수정일</th>
							<td class="left">${instrPoolVO.moduser} | <fmt:formatDate value="${instrPoolVO.moddate}" pattern="yyyy.MM.dd HH:mm"/></td>
						</tr>
				</tbody>
				</table>
				<div><h5 style="color:black" class="pt-2">[강좌 정보]</h5> </div>

				<table class="table table-bordered text-nowrap">
		               	<colgroup>
							<col style="width:5%;">
							<col style="width:45%;">
							<col style="width:5%;">
							<col style="width:45%;">
						</colgroup>

						<tbody>
						<tr>
							<th><span class="pilsu">*</span> 강의과목</th>
							<td colspan="3" class="left">
								<form:input path="lectrField" title="강의분야" class="form-control " />
                    			<div><form:errors path="lectrField" cssClass="error" /></div>
							</td>
						</tr>
						<tr>
							<th>강의가능지역</th>
							<td colspan="3" class="left">
								<form:input path="targetArea" title="강의가능지역" class="form-control " maxlength="100" />
   								<div><form:errors path="targetArea" cssClass="error" /></div>
							</td>
						</tr>

						<tr>
							<th><span class="pilsu">*</span> 강좌설명</th>
                   			<td colspan="3" class="left">
                    			<form:textarea path="selfintrcn" title="자기소개" class="form-control " maxlength="2000" rows="10"/>
   								<div><form:errors path="selfintrcn" cssClass="error" /></div>
		                   	</td>
		                </tr>
		              	<tr>
							<th>자격증/이력사항</label>
                    		<td colspan="3" class="left">
                    			<form:textarea path="licenseEtc" title="자격증및학력사항" class="form-control " maxlength="2000" rows="10"/>
                    		</td>
              			</tr>

	              		<tr>
							<th>강의계획서</th>
                    		<td class="left">
		                    	<input type="file" name="file_1" id="file_1" class="form-contro">
		                    	<c:if test="${!empty planFile}">
									<p class="ex_p">※ 현재 파일 : <a href="<tags:UploadFileUrl filePath="${planFile.filePath}" fileName="${planFile.fileName }" originName="${planFile.orginFileName }"/>" class="down_txt"><c:out value="${planFile.orginFileName }"/></a></p>
								</c:if>
                    		</td>
                    		<th>강좌 대표사진</th>
                    		<td class="left">
                    			<input type="file" name="file_2" id="file_2" class="form-contro" accept="image/*">
		                    	<c:if test="${!empty imgFile}">
									<p class="ex_p">※ 현재 파일 : <a href="<tags:UploadFileUrl filePath="${imgFile.filePath}" fileName="${imgFile.fileName }" originName="${imgFile.orginFileName }"/>" class="down_txt"><c:out value="${imgFile.orginFileName }"/></a></p>
								</c:if>
                    		</td>
              			</tr>
              		</tbody>
              		</table>
              </div>
              <div class="card-footer">


	                	<c:choose>
	                		<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
	                			 <button type="button" onclick="fn_delete()"  class="btn btn-danger float-right">삭제</button>
	                		</c:when>
	                		<c:otherwise>
	                			 <button type="button" class="btn btn-danger float-right disabled">삭제</button>
	                		</c:otherwise>
	                	</c:choose>

	                	<c:choose>
	                		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
	                			 <button type="button" onclick="fn_save()"  class="btn btn-info float-right mr-2">저장</button>
	                		</c:when>
	                		<c:otherwise>
	                			 <button type="button" class="btn btn-info float-right disabled mr-2">저장</button>
	                		</c:otherwise>
	                	</c:choose>


                  <a href="./list${searchString}" class="btn btn-default">목록</a>
                </div>


		</div>

	</div>
</form:form>
</div>
</section>
<%@include file="instrPoolMembers.jsp"  %>
</body>