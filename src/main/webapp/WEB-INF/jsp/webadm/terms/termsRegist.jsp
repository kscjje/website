<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<script type="text/javascript" src="../validator"></script>
	<validator:javascript formName="termsVO" staticJavascript="false" xhtml="true" cdata="false"/>
	<!-- bootstrap wysihtml5 - text editor -->
	<link rel="stylesheet" href="../resources/plugins/summernote/summernote-bs4.css" />
	<script src="../resources/plugins/summernote/summernote-bs4.min.js"></script>
	<script src="../resources/plugins/summernote/bootstrap.min.js"></script>
	<script type="text/javascript">

	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_save() {
		var form = document.termsVO;

		if (!validateTermsVO(form)) {
			return false;
		} else {

			var validateForm = document.getElementById("termsVO");

			var msg = "등록하시겠습니까?";
			if (form.mode.value == "edit") {
				msg = "수정하시겠습니까?";
			}
			if (confirm(msg)) {
				form.submit();
			}

		}
	}
	$(document).ready(function() {

		//$('#stplatCn').summernote({
		//	minHeight: 500
		//});

	});

	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>약관 관리</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div><!-- /.container-fluid -->
	</section>

	<!-- Main content -->
	<section class="content">
	      <div class="container-fluid">
	       <div class="row">
	          <div class="col-12">
	            <div class="card card-primary card-outline">
	              <!-- /.card-header -->

	<form:form commandName="termsVO" name="termsVO" action="./termsSave" class="form-horizontal" method="post">
	<input type="hidden" name="searchQuery" value="<c:out value="${searchQuery }"/>"/>
	<input type="hidden" name="mode" value="<c:out value="${mode }"/>"/>
	<input type="hidden" name="oldStplatId" value="<c:out value="${termsVO.stplatId }"/>"/>
	                <div class="card-body ">
	                	<div class="form-group row">
	                    <label for="nttSj" class="col-sm-2 col-form-label">* 약관코드</label>
	                    <div class="col-sm-10">
	                    	<form:input path="stplatId" title="약관코드" class="form-control " maxlength="4" />
	   						<div><form:errors path="stplatId" cssClass="error" /></div>
	                    </div>
	                  </div>
	                  <div class="form-group row">
	                    <label for="nttSj" class="col-sm-2 col-form-label">* 제목</label>
	                    <div class="col-sm-10">
	                    	<form:input path="stplatName" title="제목" class="form-control " maxlength="70" />
	   						<div><form:errors path="stplatName" cssClass="error" /></div>
	                    </div>
	                  </div>
	 					<div class="form-group row">
	                    <label for="useAt" class="col-sm-2 col-form-label">* 사용여부</label>
	                    <div class="col-sm-10">
	                    	<label><form:radiobutton path="useYn" value="Y" /> 사용함</label>
						    &nbsp;&nbsp;&nbsp;
						    <label><form:radiobutton path="useYn" value="N" /> 사용안함</label>
							<div><form:errors path="useYn" cssClass="error" /></div>
	                    </div>
	                  </div>
	                  <div class="form-group row">
	                    <div class="col-sm-12">
	                      	<form:textarea path="stplatCn" title="내용"  style="width: 100%; height: 500px; "/>
							<div><form:errors path="stplatCn" cssClass="error" /></div>
	                    </div>
	                  </div>

	                </div>
	                <!-- /.card-body -->
	                <div class="card-footer">
	                  <button type="button" onclick="fn_save()" class="btn btn-info float-right">저장</button>
	                  <button type="button" class="btn btn-default " onclick="history.back();">취소</button>
	                </div>
	                <!-- /.card-footer -->

	</form:form>


	              </div>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
	          </div>
	        </div>
	</section>

</body>