<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : instrPoolRegist.jsp
	 * @Description : 강사Pool관리 등록 JSP
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
<script type="text/javascript" src="../validator"></script>
<%-- <validator:javascript formName="instrctrVO" staticJavascript="false" xhtml="true" cdata="false"/> --%>
<script type="text/javascript">
var JSON_REQ_SUFFIX = ".json";

var fn_save = function(memNo) {
	var form = jQuery("#instrPoolVO")[0];

	//if (!validateInstrctrVO(form)) {
	if(false) {
		return ;
	} else {
		var msg = "등록하시겠습니까?";

		if (confirm(msg)) {
			var data = jQuery("#instrPoolVO").serializeJSON();

			jQuery.ajax({
				url : "./regist/"+memNo+JSON_REQ_SUFFIX,
				type: "POST",
				contentType:"application/json",
				data: JSON.stringify(data),
				success: function(data) {
					if (data.result.code == "ERROR") {
						alert(data.result.msg);
					} else {
						alert(data.result.msg);
						window.location.href = "./list";
					}
				}
			});
		}
	}
};

jQuery(document).ready(function(){
	jQuery("#instrctr-save").on("click", function() {
		fn_save(jQuery("#memNo").val());
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
});
</script>
</head>
<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>강사 POOL 현황</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>
<section class="content">
<div class="container-fluid">
<form:form commandName="instrPoolVO" name="instrPoolVO" class="form-horizontal" method="post" action="#" enctype="multipart/form-data">
	<div class="row">
		<div class="col-12">
			<div class="card card-primary card-outline">
			  <div class="card-header">
                <h3 class="card-title">기본 정보</h3>
               	<div class="card-tools">
					<button id="instrctr-save" type="button" class="btn btn-warning btn-sm <c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>">등록</button>
                </div>
              </div>
              <!-- /.card-header -->
              <div class="card-body">
              	<div class="form-group row">
                    <label for="memNo" class="col-sm-2 col-form-label">* 회원</label>
                    <div class="col-sm-8">
                    	<form:input path="memNm" title="회원" class="form-control " readonly="true"/>
                    	<form:hidden path="memNo" />
   						<div><form:errors path="memNo" cssClass="error" /></div>
                    </div>
                    <div class="col-sm-2">
                    	<button id="search-member" type="button" class="btn btn-info btn-block">회원검색</button>
                    </div>
              	</div>
              	<div class="form-group row">
                    <label for="targetArea" class="col-sm-2 col-form-label">* 강의가능지역</label>
                    <div class="col-sm-10">
                    	<form:input path="targetArea" title="강의가능지역" class="form-control " maxlength="100" />
   						<div><form:errors path="targetArea" cssClass="error" /></div>
                    </div>
              	</div>
              	<div class="form-group row">
                    <label for="lectrField" class="col-sm-2 col-form-label">* 강의분야</label>
                    <div class="col-sm-10">
                    	<form:input path="lectrField" title="강의분야" class="form-control " />
                    	<div><form:errors path="lectrField" cssClass="error" /></div>
                    </div>
              	</div>
              	<div class="form-group row">
                    <label for="lectrTarget" class="col-sm-2 col-form-label">* 강의대상</label>
                    <div class="col-sm-10">
                    	<form:input path="lectrTarget" title="강의대상" class="form-control " />
                    	<div><form:errors path="lectrTarget" cssClass="error" /></div>
                    </div>
              	</div>
              	<div class="form-group row">
                    <label for="lectrType" class="col-sm-2 col-form-label">* 강의유형</label>
                    <div class="col-sm-10">
                    	<form:input path="lectrType" title="강의유형" class="form-control " />
                    	<div><form:errors path="lectrType" cssClass="error" /></div>
                    </div>
              	</div>
              	<div class="form-group row">
                    <label for="selfintrcn" class="col-sm-2 col-form-label">* 자기소개</label>
                    <div class="col-sm-10">
                    	<form:textarea path="selfintrcn" title="자기소개" class="form-control " maxlength="2000" rows="4"/>
   						<div><form:errors path="selfintrcn" cssClass="error" /></div>
                    </div>
              	</div>
				<div class="form-group row">
                    <label for="eduinfoOpenyn" class="col-sm-2 col-form-label">* 학력사항공개여부</label>
                    <div class="col-sm-10">
						<form:select path="eduinfoOpenyn" cssClass="txt">
							<form:option value="Y"  label=" 공개"/>
							<form:option value="N"  label=" 공개안함"/>
						</form:select>
   						<div><form:errors path="eduinfoOpenyn" cssClass="error" /></div>
                    </div>
              	</div>
				<div class="form-group row">
                    <label for="careerOpenyn" class="col-sm-2 col-form-label">* 경력사항공개여부</label>
                    <div class="col-sm-10">
						<form:select path="careerOpenyn" cssClass="txt">
							<form:option value="Y"  label=" 공개"/>
							<form:option value="N"  label=" 공개안함"/>
						</form:select>
   						<div><form:errors path="careerOpenyn" cssClass="error" /></div>
                    </div>
              	</div>
              	<div class="form-group row">
                    <label for="license_etc" class="col-sm-2 col-form-label"> 자격증및학력사항</label>
                    <div class="col-sm-10">
                    	<form:textarea path="license_etc" title="자격증및학력사항" class="form-control " maxlength="2000" rows="4"/>
                    </div>
              	</div>

              	<div class="form-group row">
                    <label for="proflImageid" class="col-sm-2 col-form-label"> 프로필 이미지</label>
                    <div class="col-sm-10">
                    	<form:input path="proflImageid" title="프로필 이미지" class="form-control " maxlength="20" />
                    </div>
              	</div>
              	<div class="form-group row">
                    <label for="proflEtc" class="col-sm-2 col-form-label"> 기타사항</label>
                    <div class="col-sm-10">
                    	<form:textarea path="proflEtc" title="자격증및학력사항" class="form-control " maxlength="2000" rows="4"/>
                    </div>
              	</div>
              	<div class="form-group row">
                    <label for="state" class="col-sm-2 col-form-label"> 신청상태</label>
                    <div class="col-sm-10">
                    	<form:input path="state" title="신청상태" class="form-control " maxlength="20" readonly="true"/>
                    </div>
              	</div>
              </div>
		</div>
	</div>
</form:form>
</div>
</section>
<%@include file="instrPoolMembers.jsp"  %>
</body>