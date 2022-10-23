<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%
	WebApplicationContext wapContext = org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	Object dynamicConfig = wapContext.getBean("dynamicConfig");
	String strWebContext = dynamicConfig != null ? ((com.hisco.cmm.config.DynamicConfig)dynamicConfig).getAdminRoot() : com.hisco.cmm.util.Config.ADMIN_ROOT;
%>
<c:set var="ADMIN_ROOT" value='<%=request.getContextPath() + strWebContext + "/"%>'/>
<%
 /**
  * @Class Name :  resourceList.jsp
  * @Description : WAS 리소스 관리 목록 JSP
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
	<script type="text/javascript" src="<c:out value="${ADMIN_ROOT }"/>resources/js/jquery.contextMenu.js"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<c:out value="${ADMIN_ROOT }"/>resources/js/contextMenu/jquery.contextMenu.css" />

<script>

		//<![CDATA[
		jQuery(function($) {

			var DIR = '<c:out value="${Dir}"/>';

			if(DIR == '') DIR = '/';

			$('a.type_parent, a.type_directory, a.type_file').css(
			{
				'display' : 'inline-block',
				'padding' : '1px 30px 1px 20px'
			});


			// 테이블
			$.contextMenu({
				selector : 'table#dir_table',
				callback : contextMenu_callback,
				items : {
					'title' : { name : '현재 경로 : '+ DIR +'    ', icon : 'door', disabled : true, className : 'strong' },
					"sep1": "---------",
					'createDirectory' : { name : '디렉터리 만들기', icon : 'add' },
					'createFile' : { name : '파일 만들기', icon : 'add' },
					'upload' : { name : '업로드', icon : 'add' },
					"sep2": "---------",
					'quit' : {name: "닫기", icon: 'quit'}
				}
			});

			// 디렉터리
			$.contextMenu({
				selector : 'a.type_directory',
				callback : contextMenu_callback,
				items : {
					'rename' : { name : '이름 변경', icon : 'edit' },
					'delete' : { name : '디렉터리 삭제', icon : 'delete' },
					"sep1": "---------",
					'quit' : {name: "닫기", icon: 'quit'}
				}
			});

			// 파일
			$.contextMenu({
				selector : 'a.type_file',
				callback : contextMenu_callback,
				items : {
					'edit' : { name : '파일 내용 보기', icon : 'add' },
					'rename' : { name : '이름 변경', icon : 'edit' },
					'delete' : { name : '파일 삭제', icon : 'delete' },
					"sep1": "---------",
					'quit' : {name: "닫기", icon: 'quit'}
				}
			});

			// context menu 처리
			function contextMenu_callback(itemKey, opt) {

				if (itemKey == 'title' || itemKey == 'quit') {
					return 'context-menu-icon context-menu-icon-quit';
				}

				var self = $(this),
				type = self.data('type'),
				dir = self.data('dir') || DIR,
				ext = self.data('ext'),
				mime = self.data('mime'),
				url = (ADMIN_ROOT + "/resource/"+ itemKey).replace(/\/{1,}/g, '/') + '?dir='+ encodeURIComponent(dir);

				//var varFileName = dir.replace("/", "");
				var varFileName = dir;

				switch(itemKey) {

					case 'createDirectory':

						$("#subMode").val(itemKey);

						$("#modalWasResourceTitle1").text("디렉터리 만들기");

						$("#modalWasResourceDev2").hide();

						$("#modalWasResourceObj1").text("위치 : " + $("#searchDir").val());

						$("#modalWasResource").show();

						$("#modalWasResourceObj3").focus();

						break;

					case 'createFile':

						$("#subMode").val(itemKey);

						$("#modalWasResourceTitle1").text("파일 만들기");

						$("#modalWasResourceDev2").hide();

						$("#modalWasResourceObj1").text("위치 : " + $("#searchDir").val());

						$("#modalWasResource").show();

						$("#modalWasResourceObj3").focus();

						break;

					case 'rename':

						if (type == "file") {

							$("#modalWasResourceTitle1").text("파일명 변경");

							$("#subMode").val("renameFile");

						} else if (type == "directory") {

							$("#modalWasResourceTitle1").text("디렉터리명 변경");

							$("#subMode").val("renameDir");
						}

						$("#modalWasResourceObj1").text("위치 : " + $("#searchDir").val());
						$("#modalWasResourceObj2").val(varFileName);
						$("#modalWasResourceObj3").val(varFileName);

						$("#modalWasResource").show();

						$("#modalWasResourceObj3").focus();

						break;

					case 'upload':
						$("#multiFileSearch").trigger("click");
						break;

					case 'delete':

						if(confirm("다음을 영구 삭제하며 복구가 불가능합니다. 정말로 삭제할까요?\n\n위치 : " + $("#searchDir").val() + "\n명칭 : " + varFileName + "\n\n** 디렉터리 일 경우 디렉터리 하위에 내용이 있으면 삭제되지 않습니다.")){
							$("#mode").val("fileDelete");
							$("#fileDeleteNm").val(varFileName);
							document.templateVO.submit();
						}
						break;

					case 'edit':
						var win = window.open("<c:out value="${ADMIN_ROOT }"/>wasresource/resourceEditPopup?fileNm=" + varFileName + "&searchDir=" + $("#searchDir").val(), 'resourceEdit', 'width=1100, height=800, resizable=yes, scrollbars=yes, left=500, top=100');
						win.focus();
						break;

					default:
						var win = window.open(url, 'resource', 'width=500, height=500, resizable=yes, scrollbars=yes, left=100, top=100');
						win.focus();
						break;
				}
			}
		});

		function fnFileRegiSel(thisObj) {

			var varMyGroupId = $(thisObj).attr("myGroupId");

			$("#regiHFile" + varMyGroupId).click();

		}

		function fnRegiHFileChange(thisObj) {

			var varMyGroupId   = $(thisObj).attr("myGroupId");
			var varLength      = $(thisObj)[0].files.length;

			var varSelAllFiles = "";

			if (varLength == 1) {
				varSelAllFiles = $(thisObj)[0].files[0].name;
			} else if (varLength >= 2) {
				for(var i = 0; i < varLength; i++) {
					varSelAllFiles = varSelAllFiles + $(thisObj)[0].files[i].name + ";";
				}
			}

			$("#RegiUFile" + varMyGroupId).val(varSelAllFiles);
			$("#mode").val("fileUpload");

			var varForm = document.templateVO;

			varForm.submit();

		}

		function fnGotoDir(){

			var varSearchDir = $("#searchDir").val().trim();

			location.href="./resourceList?searchDir=" + encodeURIComponent(varSearchDir);
		}

		function fnModalClose(){
			$("#modalWasResource").hide();
		}

		function fnSaveWasResource(){

			var varSubMode = $("#subMode").val();
			var varNewFileOrDir = $("#modalWasResourceObj3").val().trim();

			switch(varSubMode){
				case "createDirectory":

					if((varNewFileOrDir === undefined) || (varNewFileOrDir == null) || (varNewFileOrDir == "")){
						alert("새로운 디렉터리명을 입력하세요");
						$("#modalWasResourceObj3").focus();
						return false;
					}

					if(confirm("[" + varNewFileOrDir + "] 새로운 디렉터리를 만들까요?")){
						$("#mode").val(varSubMode);
						$("#newFileOrDir").val(varNewFileOrDir);

						document.templateVO.submit();
					}
					break;

				case "createFile":

					if((varNewFileOrDir === undefined) || (varNewFileOrDir == null) || (varNewFileOrDir == "")){
						alert("새로운 파일명을 입력하세요");
						$("#modalWasResourceObj3").focus();
						return false;
					}

					if(confirm("[" + varNewFileOrDir + "] 새로운 파일을 만들까요?")){
						$("#mode").val(varSubMode);
						$("#newFileOrDir").val(varNewFileOrDir);

						document.templateVO.submit();
					}
					break;

				case "renameFile":

					if((varNewFileOrDir === undefined) || (varNewFileOrDir == null) || (varNewFileOrDir == "")){
						alert("변경할 파일명을 입력하세요");
						$("#modalWasResourceObj3").focus();
						return false;
					}

					if(confirm("[" + varNewFileOrDir + "] 명칭으로 파일명을 변경할까요?")){
						$("#mode").val(varSubMode);
						$("#newFileOrDir").val(varNewFileOrDir);
						$("#orgFileOrDir").val($("#modalWasResourceObj2").val());

						document.templateVO.submit();
					}
					break;

				case "renameDir":

					if((varNewFileOrDir === undefined) || (varNewFileOrDir == null) || (varNewFileOrDir == "")){
						alert("변경할 디렉터리명을 입력하세요");
						$("#modalWasResourceObj3").focus();
						return false;
					}

					if(confirm("[" + varNewFileOrDir + "] 명칭으로 디렉터리명을 변경할까요?")){
						$("#mode").val(varSubMode);
						$("#newFileOrDir").val(varNewFileOrDir);
						$("#orgFileOrDir").val($("#modalWasResourceObj2").val());

						document.templateVO.submit();
					}
					break;
			}
		}

		function fnResourceGoUrl(gubun, val) {

			var varCurrentDir = "${CurrDir}";

			if(gubun == "D") {

				if (varCurrentDir == "/") {
					$("#searchDir").val(varCurrentDir + val);
				} else {
					$("#searchDir").val(varCurrentDir + "/" + val);
				}

				fnGotoDir();

			} else if(gubun == "F") {

				//ignore

			} else if(gubun == "R") {

				$("#searchDir").val(val);

				fnGotoDir();

			}

		}

		//]]>

	</script>

</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>WAS 리소스 관리</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div>
	</section>

	<!-- Main content -->
	<section class="content">

		<form:form commandName="templateVO" name="templateVO" action="./resourceSave" method="post" enctype="multipart/form-data">

			<input type="hidden" name="mode"         id="mode"         value="" />
			<input type="hidden" name="fileDeleteNm" id="fileDeleteNm" value="" />
			<input type="hidden" name="newFileOrDir" id="newFileOrDir" value="" />
			<input type="hidden" name="orgFileOrDir" id="orgFileOrDir" value="" />

		      <div class="container-fluid">
		       	<div class="card card-primary card-outline">
		 	 	 	<div class="card-body">
			 	 	 	<div class="row">
			              	<div class="col-4" style="display:none;">
			              		<select name="searchCondition" title="검색영억" class="form-control">
									<option value="1" <c:if test="${paramMap.searchCondition == '1'}">selected="selected"</c:if> >WAS 리소스 관리</option>
								</select>
							</div>
							<div class="col-8">
								<input class="form-control" name="searchDir" id="searchDir" type="text" size="60" title="검색어" value='<c:out value="${CurrDir}"/>'  maxlength="155" >
							</div>
							<div class="col-4">
								<a href="#" onclick="fnGotoDir();" class="btn btn-info" title="이동버튼">이동</a>
								<a href="./resourceList?searchDir=/" class="btn btn-info" title="ROOT DIR">ROOT DIR</a>
			              	</div>
						</div>
					</div>
		 		</div>

		       <div class="row">
		          <div class="col-12">
		            <div class="card">
		              <!-- /.card-header -->

		              <div class="card-body table-responsive p-0">

						<table id="dir_table" class="table table-hover text-nowrap">
							<caption>디렉터리 목록</caption>
							<colgroup>
								<col style="width: *"/>
								<col style="width: 20%"/>
								<col style="width: 20%"/>
								<col style="width: 20%"/>
							</colgroup>
							<thead>
								<tr>
									<th>이름</th>
									<th>크기</th>
									<th>종류</th>
									<th>최종 수정일</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${ParentDirYn eq 'Y'}">
									<tr>
										<td colspan="4">
											<a class="type_parent strong" href="#" onclick="fnResourceGoUrl('R', '${ParentDir}'); return false;"><span>[..]</span></a>
										</td>
									</tr>
								</c:if>

								<c:if test="${FileList != null && fn:length(FileList) > 0}">

									<c:forEach var="item" items="${FileList}" varStatus="loop">

										<tr>
											<td class="txtleft">
												<c:choose>
													<c:when test="${item.type eq 'D'}">
														<img src="<c:out value="${ADMIN_ROOT }"/>resources/images/folder.gif"><a class="type_directory strong" data-type="directory" data-dir="<c:out value='${item.name}'/>" data-ext="" data-mime="" href="#" onclick="fnResourceGoUrl('D', '${item.name}'); return false;" title="<c:out value='${item.dir}'/>"><span><c:out value="${item.name}"/></span></a>
													</c:when>
													<c:otherwise>
														<img src="<c:out value="${ADMIN_ROOT }"/>resources/images/file.gif"><a class="type_file" data-type="file" data-dir="<c:out value='${item.name}'/>" data-ext="<c:out value="${item.ext}"/>" data-mime="<c:out value="${item.mime}"/>" target="_blank" href="<tags:FileDownload param='${item.dir}'/>" title="<c:out value="${item.dir}"/>"><span><c:out value="${item.name}"/></span></a>
													</c:otherwise>
												</c:choose>
											</td>
											<td class="txtright">
												<span title="<fmt:formatNumber value="${item.size}"/>Byte"><tags:FileSize fileSize="${item.size}"/></span>
											</td>
											<td class="txtcenter">
												<c:if test="${item.hidden}">[H]&nbsp;</c:if>
												<c:choose>
													<c:when test="${item.type == 'D'}">DIR</c:when>
													<c:otherwise><span class="ext <c:out value="ext_${fn:toLowerCase(item.ext)}"/>" title="<c:out value="${item.mime}"/>"><c:out value="${item.ext}"/></span></c:otherwise>
												</c:choose>
											</td>
											<td class="txtcenter">
												<fmt:formatDate value="${item.lastModified}" pattern="yyyy년 M월 d일 H시 m분 s초"/>
											</td>
										</tr>

									</c:forEach>

								</c:if>

							</tbody>
						</table>

		              </div>

						<div style="display:none">
							<input type="text" id="RegiUFile03" name="webadmInsert03File" placeholder="파일 등록" value="" style="width:90%;" readonly>
							<button type="button" class="btn_ty_m_c3 btn_ty_m2" myGroupId="03" onclick="fnFileRegiSel(this); return false;" style="min-width:100px;" id="multiFileSearch">파일찾기</button>
							<input type="file" id="regiHFile03" name="regiFile03" myGroupId="03" multiple="multiple" onChange="fnRegiHFileChange(this);" style="display:none;"/>
						</div>

		          </div>
		        </div>
		      </div>
		     </div>

	     </form:form>

	</section>

	<div class="modal" id="modalWasResource">

		<input type="hidden" id="subMode" value="" />

		<div class="modal-dialog" style="max-width:500px;max-height:400px; top:200px;">

			<div class="modal-content">

				<div class="modal-body">

					<div class="search_wrap w100">

						<fieldset>

							<legend id="modalWasResourceTitle1">Was Resource</legend>

							<div class="search_box">
								<label id="modalWasResourceObj1">위치 : </label>
							</div>
							<div class="search_box" id="modalWasResourceDev2">
								<label for="modalWasResourceObj2"></label>
								원본 : <input id="modalWasResourceObj2" title="" type="text" class="input_text" placeholder="" style="width:362px;" readonly="true">
							</div>
							<div class="search_box">
								<label for="modalWasResourceObj3"></label>
								<input id="modalWasResourceObj3" title="" type="text" class="input_text" placeholder="" style="width:400px;">
								<a href="#" onclick="fnSaveWasResource(); return false;" class="btn btn-info"><span>저장</span></a>
							</div>

						</fieldset>

					</div>

					<div class="table2 ty scroll">
						&nbsp;
					</div>
					<div class="table2 ty scroll">
						<a href="#" onclick="fnModalClose();" class="btn btn-secondary">닫기</a>
					</div>

				</div>

			</div>

		</div>

	</div>
</body>
