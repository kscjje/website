<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
 /**
  * @Class Name : articleDetail.jsp
  * @Description : 게시판 게시물  상세 화면
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.21    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.21
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
	<script type="text/javascript" src="../../../../resources/js/EgovMultiFile.js"></script>
	<script type="text/javascript" src="../../../../validator"></script>
	<validator:javascript formName="articleCommentVO" staticJavascript="false" xhtml="true" cdata="false"/>
	<script type="text/javascript">

	/* ********************************************************
	 * 삭제처리
	 ******************************************************** */
	 function fn_article_delete(mode,nttId) {
			var msg = "삭제하시겠습니까?\n삭제하시면 데이타는 남아 있고 사용자 페이지에서는 노출되지 않습니다.";
			if (mode == "R") {
				msg = "답변글을 삭제하시겠습니까?";
			}
			if (confirm(msg)) {
				$.ajax({
				      url: '../delete.json',
				      data : {'nttId' : nttId},
				      type : "POST",
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
						         top.location.reload();
					         }
				      }
				 });
			}
	}
	// 완전 삭제
	function fn_article_deleteall(nttId) {
			if (confirm("삭제하시겠습니까?\n데이터 삭제하시면 복구할 수 없습니다.")) {
				$.ajax({
				      url: '../deleteAll.json',
				      data : {'nttId' : nttId},
				      type : "POST",
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
						        window.location.replace("../list<c:out value="${commandMap.queryAll}"/>");
					         }
				      }
				 });
			}
	}
	<c:if test="${boardMasterVO.commentAt eq 'Y'}">
	/* ********************************************************
	 * 코멘트 작성
	 ******************************************************** */
	 function fn_egov_comment_insert() {

			var form = document.getElementById("articleCommentVO");

			if (!validateArticleCommentVO(form)) {
				return;
			}
			if (confirm('등록하시겠습니까?')) {
				$.ajax({
				      url: '../commentSave.json',
				      data : $('#articleCommentVO').serialize(),
				      type : "POST",
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
					        	 fn_egov_select_commentList(1);
					         }
				      }
				 });
			}
		}


		function fn_egov_comment_delete(commentNo) {
			var form = document.getElementById("articleCommentVO");

			if (confirm('삭제하시겠습니까?')) {
				form.commentNo.value = commentNo;
				$.ajax({
				      url: '../commentDelete.json',
				      data : $('#articleCommentVO').serialize(),
				      type : "POST",
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
					        	 fn_egov_select_commentList(1);
					         }
				      }
				 });
			}
		}

		/* 댓글페이징 */
		function fn_egov_select_commentList(pageNo) {
			$.ajax({
			      url: '../commentListAjax',
			      data : {'nttId' : <c:out value="${result.nttId}"/>},
			      type : "GET",
			      dataType : "html",
			      success: function(data) {
			    	  if(data.indexOf("login_area") > 0 ){
			    		  __ajaxHtmlCallback__();
			    	  	}else{
			    	  	 $('#replyDiv').html(data);
			    	  	}
			      }
			 });
		}
	</c:if>
	function fn_reply_save() {
		if ($('#nttCn').val() == "") {
			alert("답변 내용을 입력해 주세요.");
			$('#nttCn').focus();
		} else {
			if (confirm("답변을 등록하시겠습니까?")) {
				document.articleVO.submit();
			}
		}

	}
	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	             <h1><small>게시판 관리 ></small> <c:out value="${boardMasterVO.bbsNm}"/></h1>
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
	              <div class="card-body p-0">
	                	<table class="table table-bordered">
		               	<colgroup>
							<col style="width:150px">
							<col style="width: ;">
							<col style="width: ;">
							<col style="width: ;">
						</colgroup>

						<tbody>
						<!-- 글 제목 -->
						<tr>
							<th>제목</th>
							<td colspan="3" class="left"><c:if test="${result.useAt eq 'N' }">(삭제)</c:if> <c:out value="${result.nttSj}"/></td>
						</tr>
						<!-- 작성자, 작성시각, 조회수 -->
						<tr>
							<th>작성자/작성일시</th>
							<td class="left"><c:out value="${result.frstRegisterNm}"/>(<c:out value="${result.frstRegisterId}"/>) / <fmt:formatDate value="${result.frstRegisterPnttm}" pattern="yyyy-MM-dd HH:mm"/></td>
							<th>수정자/수정일시</th>
							<td class="left"><c:out value="${result.lastUpdusrId}"/> / <fmt:formatDate value="${result.lastUpdtPnttm}" pattern="yyyy-MM-dd HH:mm"/></td>

						</tr>
						<!-- 게시일자 -->
						<tr>
							<th class="vtop">게시일자</th>
							<td class="left">
								<c:out value="${result.ntceBgnde} ~ ${result.ntceEndde}" escapeXml="false" />
							</td>
							<th>조회수</th>
							<td class="left"><c:out value="${result.inqireCo}"/></td>
						</tr>

	<c:if test="${not empty result.ctgId}">
						<!-- 카테고리 -->
						<tr>
							<th class="vtop">카테고리</th>
							<td colspan="3" class="left">
								<c:out value="${result.ctgNm}"/>
							</td>
						</tr>
	</c:if>


						<!-- 글 내용 -->
						<tr>
							<th class="vtop">내용</th>
							<td colspan="3" class="cnt">
								<c:out value="${fn:replace(result.nttCn , crlf , '<br/>')}" escapeXml="false" />
							</td>
						</tr>
	<c:if test="${boardMasterVO.managerAt == 'Y'}">
						<tr>
							<th class="vtop">담당자</th>
							<td colspan="3" class="left">
								<b>담당부서 :</b> <c:out value="${result.managerDept}"/> , <b>연락처 :</b> <c:out value="${result.managerTel}"/>
							</td>
						</tr>

	</c:if>
	<c:if test="${boardMasterVO.urlLnikyn == 'Y'}">
						<tr>
							<th class="vtop">연관링크</th>
							<td colspan="3" class="left">
								<c:out value="${result.linkUrl}"/>
							</td>
						</tr>

	</c:if>

	<c:if test="${itemInfoList!=null  && fn:length(itemInfoList)>0}">
		<c:forEach items="${itemInfoList}" var="itemInfo" varStatus="status">
						<tr>
							<th class="vtop"><c:out value="${itemInfo.bbsItemNm}"/></th>
							<td colspan="3" >
       	 						<c:out value="${fn:replace(itemInfo.bbsItemLvalue , crlf , '<br/>')}" escapeXml="false" />
							</td>
						</tr>
		</c:forEach>
	</c:if>

						<!-- 첨부파일  -->
						<c:if test="${not empty result.atchFileId}">
						<tr>
							<th>첨부파일</th>
							<td colspan="3">
								<c:import url="/web/common/upload/includeUploadDetail" >
						       		<c:param name="file_group_id" value="${result.atchFileId}" />
						       		<c:param name="show_image" value="Y" />
					        	</c:import>
							</td>
						</tr>
					  	</c:if>

	<c:if test="${!empty replyData and boardMasterVO.bbsTyCode eq 'B004'}">
						<tr>
							<th class="vtop">답변
								<div class="mt-2">
									<c:if test="${commandMap.selectedMenu.updYn eq 'Y' }">
										<a class="btn btn-primary btn-xs" href="../modify?nttId=<c:out value="${replyData.nttId}"/><c:out value="${commandMap.query}"/>">수정</a>
									</c:if>
									<c:if test="${commandMap.selectedMenu.delYn eq 'Y' }">
										<button type="button" class="btn btn-secondary btn-xs" onclick="fn_article_delete('R',<c:out value="${replyData.nttId}"/>)">삭제</button>
									</c:if>
								</div>
							</th>
							<td colspan="3" class="cnt">
								<div class="mb-2">
									<b>작성자 :</b> <c:out value="${replyData.frstRegisterNm}"/> | <b>답변일 :</b> <fmt:formatDate value="${replyData.frstRegisterPnttm}" pattern="yyyy-MM-dd HH:mm"/>
								</div>
								<div>
									<c:out value="${fn:replace(replyData.nttCn , crlf , '<br/>')}" escapeXml="false" />
								</div>
								<c:if test="${not empty replyData.atchFileId}">
								<div>
									<c:import url="/web/common/upload/includeUploadDetail" >
						       			<c:param name="file_group_id" value="${replyData.atchFileId}" />
					        		</c:import>
								</div>
								</c:if>
							</td>
						</tr>

	</c:if>


					</tbody>

						</table>
	<c:if test="${boardMasterVO.commentAt eq 'Y'}">
						<h4 class="ml-3 mt-3 text-dark">댓글 목록</h4>
						<div id="replyDiv" class="mt-3">

						</div>
						<script>
						fn_egov_select_commentList(1);
						</script>
	</c:if>

	                </div>
	                <!-- /.card-body -->
	                <div class="card-footer">
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
			<button type="button" class="btn btn-secondary float-right" onclick="fn_article_deleteall(<c:out value="${result.nttId}"/>)">데이터 삭제</button>
			<c:if test="${result.useAt eq 'Y' }">
				<button type="button" class="btn btn-secondary float-right mr-2" onclick="fn_article_delete('D',<c:out value="${result.nttId}"/>)">삭제</button>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="${result.useAt eq 'Y' }">
				<button type="button" class="btn btn-secondary float-right disabled">데이터 삭제</button>
			</c:if>
			<button type="button" class="btn btn-secondary float-right disabled mr-2">삭제</button>
		</c:otherwise>
	</c:choose>

	 <c:choose>
		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
			<a class="btn btn-info mr-2 float-right" href="../modify?nttId=<c:out value="${result.nttId}"/><c:out value="${commandMap.query}"/>">수정</a>
		</c:when>
		<c:otherwise>
			<a class="btn btn-info mr-2 float-right disabled">수정</a>
		</c:otherwise>
	</c:choose>

	                  	<a class="btn btn-default " href="../list<c:out value="${commandMap.queryAll}"/>">목록</a>
	<c:if test="${boardMasterVO.replyPosblAt eq 'Y' and result.noticeAt ne 'Y' and boardMasterVO.bbsTyCode ne 'B004'}">
	                  	 <c:choose>
							<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
								<a class="btn btn-info mr-2" href="../replayRegist?nttId=<c:out value="${result.nttId}"/><c:out value="${commandMap.query}"/>">답글 작성</a>
							</c:when>
							<c:otherwise>
								<a class="btn btn-info mr-2 disabled">답변</a>
							</c:otherwise>
						</c:choose>
	</c:if>
	                </div>
	                <!-- /.card-footer -->
					<c:if test="${empty replyData and result.noticeAt ne 'Y' and boardMasterVO.bbsTyCode eq 'B004' and commandMap.selectedMenu.insYn eq 'Y' }">
						<div class="card-header">
			                <h3 class="card-title">※ 답변</h3>
			              </div>
		                <div class="card-body  table-responsive p-0">
						<form:form commandName="articleVO" name="articleVO" action="../save" class="form-horizontal" method="post"  enctype="multipart/form-data">
							<input type="hidden" name="searchQuery" value="${commandMap.queryAll }"/>
							<input type="hidden" name="parnts" value="<c:out value='${articleVO.nttId}'/>" />
							<input type="hidden" name="sortOrdr" value="<c:out value='${articleVO.sortOrdr}'/>" />
							<input type="hidden" name="noticeAt" value="<c:out value='${articleVO.noticeAt}'/>" />
							<input type="hidden" name="replyLc" value="<c:out value='${articleVO.replyLc+1}'/>" />
							<input type="hidden" name="ctgId" value="<c:out value='${articleVO.ctgId}'/>" />
							<input type="hidden" name="nttSj" value="<c:out value='답변 : ${articleVO.nttSj}'/>" />
			                	<table class="table text-nowrap">
				               	<colgroup>
									<col style="width:10%;">
									<col style="width:*">
								</colgroup>

								<tbody>
									<tr>
										<th class="vtop">내용</th>
										<td  class="cnt">
											<textarea id="nttCn" name="nttCn" title="답변" class="form-control" style="height:400px"></textarea>
										</td>
									</tr>
									<!-- 첨부파일  -->
									<c:if test="${boardMasterVO.fileAtchPosblAt == 'Y'}">
					                  <tr>
										<th class="vtop">첨부파일</th>
						                <td>
								        	 <c:import url="/web/common/upload/includeUploadRegist" >
									       		<c:param name="file_length" value="${boardMasterVO.atchPosblFileNumber}" />
									       		<c:param name="file_ext" value="${boardMasterVO.atchPosblFileExt}" />
									       		<c:param name="file_group_id" value="${replyData.atchFileId}" />
								        	</c:import>
					                    </td>
					                   </tr>
					                 </c:if>
								</tbody>

								</table>
							</form:form>

							<div class="text-center">
								<button type="button" onclick="fn_reply_save()" class="btn btn-info mb-5" >답변 저장</button>
							</div>
		                </div>
					</c:if>
	              </div>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
	          </div>
	        </div>
	</section>


</body>
