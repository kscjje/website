<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name :  resourceEdit.jsp
  * @Description : WAS 리소스 관리 소스 수정 JSP
  * @Modification Information
  * @
  * @  수정일           수정자              수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.03.19  전영석            최초 생성
  *
  *  @author 전영석
  *  @since 2021.03.19
  *  @version 1.0
  *  @see
  *
  */
%>
<head>

<script>

	//<![CDATA[
	function fnPageReload() {
		location.reload();
	}

	function fnSaveContents() {

		var varFileContents = $("#fileContents").val().trim();
		$("#fileContents").val(varFileContents);

		if ((varFileContents === undefined) || (varFileContents == null) || (varFileContents == "")) {
			alert("파일 내용은 비울 수 없습니다.");
			$("#fileContents").focus();
			return false;
		}

		if (confirm("파일 내용을 저장할까요?")) {
			document.templateVO.submit();
		}
	}

	function fnPageClose(){
		self.close();
	}
	//]]>

</script>

</head>
<body>

	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>WAS 리소스 관리 - 파일 내용 보기</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div>
	</section>

	<!-- Main content -->
	<section class="content">

	     <form:form commandName="templateVO" name="templateVO" action="./resourceFileSave" method="post" enctype="multipart/form-data">

		     <input type="hidden" name="charset"      value="<c:out value="${charset}"/>" />
		     <input type="hidden" name="fileFullPath" value="<c:out value="${fileFullPath}"/>" />
		     <input type="hidden" name="fileName"     value="<c:out value="${fileName}"/>" />
		     <input type="hidden" name="searchDir"    value="<c:out value="${searchDir}"/>" />

		      <div class="container-fluid">

			       <div class="row">
			          <div class="col-12">
			            <div class="card">
			              <!-- /.card-header -->

			              <div class="card-body table-responsive p-0">

							<table id="dir_table" class="table table-hover text-nowrap">
								<caption>디렉터리 목록</caption>
								<colgroup>
									<col style="width: 10%"/>
									<col style="width: *"/>
								</colgroup>
								<tbody>

									<tr>
										<td class="txtleft">
											파일 전체 경로
										</td>
										<td class="txtright">
											<c:out value="${fileFullPath}"/>
										</td>
									</tr>

									<tr>
										<td class="txtleft">
											파일 명칭
										</td>
										<td class="txtright">
											<c:out value="${fileName}"/>
										</td>
									</tr>

									<tr>
										<td class="txtleft">
											CHARSET
										</td>
										<td class="txtright">
											<c:out value="${charset}"/>
										</td>
									</tr>

									<tr>
										<td class="txtleft">
											파일 내용
										</td>
										<td class="txtright">
											<textarea name="fileContents" id="fileContents" rows="20" cols="70" style="width: 90%" autofocus="autofocus"><c:out value="${fileContents}"/></textarea>
										</td>
									</tr>

								</tbody>
							</table>

			              </div>

			          </div>

	          		<div class="card-body table-responsive p-0" style="text-align:center;">
						<a href="#" onclick="fnPageReload(); return false;"   class="btn btn-info" title="다시로드">다시 로드</a>
						<a href="#" onclick="fnSaveContents(); return false;" class="btn btn-info" title="내용저장">내용 저장</a>
						<a href="#" onclick="fnPageClose(); return false;" class="btn btn-info" title="닫기">닫기</a>
					</div>

			        </div>
			      </div>
		     </div>

	     </form:form>

	</section>
</body>
