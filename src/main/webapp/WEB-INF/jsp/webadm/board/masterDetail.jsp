<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : masterList.jsp
  * @Description : 게시판 마스터 상세 조회 화면
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

</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>게시판 관리</h1>
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
	                	<table class="table">
		               	<colgroup>
							<col style="width:15%">
							<col style="width:35%">
							<col style="width:15%">
							<col style="width:35%">
						</colgroup>
						<tbody>
							<tr>
								<th class="text-right">게시판명</th>
								<td colspan="3"><c:out value="${result.bbsNm }"/></td>
							</tr>
							<tr>
								<th class="text-right">관리 URL</th>
								<td colspan="3"><a href="<%=com.hisco.cmm.util.Config.ADMIN_ROOT%>/board/article/<c:out value="${result.bbsId }"/>/list"><%=com.hisco.cmm.util.Config.ADMIN_ROOT%>/board/article/<c:out value="${result.bbsId }"/>/list</a></td>
							</tr>
							<tr>
								<th class="text-right">사용자 URL</th>
								<td colspan="3"><a href="<%=com.hisco.cmm.util.Config.USER_ROOT%>/board/<c:out value="${result.bbsId }"/>/list" target="_blank"><%=com.hisco.cmm.util.Config.USER_ROOT%>/board/<c:out value="${result.bbsId }"/>/list</a></td>
							</tr>
							<tr>
								<th class="text-right">게시판소개내용</th>
								<td  colspan="3" style="word-break:break-all;">
									<c:out value="${fn:replace(result.bbsIntrcn , crlf , '<br/>')}" escapeXml="false" />
								</td>
							</tr>
							<tr>
								<th class="text-right">게시판 유형</th>
								<td><c:out value="${result.bbsTyCodeNm }"/> (<c:out value="${result.bbsTyCode }"/>) </td>
								<th class="text-right">게시판 템플릿</th>
								<td><c:out value="${result.tmplatNm }"/> (<c:out value="${result.tmplatId }"/>) </td>
							</tr>
							<tr>
								<th class="text-right">게시판 사용여부</th>
								<td>
									<c:choose>
										<c:when test="${result.useAt eq 'Y'}">사용함</c:when>
										<c:otherwise>사용안함</c:otherwise>
									</c:choose>
								</td>
								<th class="text-right">파일첨부</th>
								<td>
									<c:choose>
										<c:when test="${result.fileAtchPosblAt eq 'Y'}">사용함</c:when>
										<c:otherwise>X</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th class="text-right">첨부가능파일숫자</th>
								<td><c:out value="${result.atchPosblFileNumber }"/> </td>
								<th class="text-right">첨부가능 확장자</th>
								<td><c:out value="${result.atchPosblFileExt }"/> </td>
							</tr>
							<tr>
								<th class="text-right">담당자 표기 사항 </th>
								<td>
									<c:choose>
										<c:when test="${result.managerAt eq 'Y'}">담당자 표시</c:when>
										<c:otherwise>사용안함</c:otherwise>
									</c:choose>
								</td>
								<th class="text-right">URL 링크 사용여부 </th>
								<td>
									<c:choose>
										<c:when test="${result.urlLnikyn eq 'Y'}">URL 링크 사용</c:when>
										<c:otherwise>사용안함</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<!--
							<tr>
								<th class="text-right">답변기능</th>
								<td>
									<c:choose>
										<c:when test="${result.replyPosblAt eq 'Y'}">사용함</c:when>
										<c:otherwise>X</c:otherwise>
									</c:choose>
								</td>

								<th class="text-right">담당자 표기 사항 <b class="text-red">*</b></th>
								<td>
									<c:choose>
										<c:when test="${result.managerAt eq 'Y'}">담당자 표시</c:when>
										<c:otherwise>사용안함</c:otherwise>
									</c:choose>
								</td>
							</tr>

							<tr>
								<th class="text-right">댓글 여부</th>
								<td>
									<c:choose>
										<c:when test="${result.commentAt eq 'Y'}">댓글 사용</c:when>
										<c:otherwise>사용안함</c:otherwise>
									</c:choose>
								</td>
								<th class="text-right">만족도 체크 여부</th>
								<td>
									<c:choose>
										<c:when test="${result.stsfdgAt eq 'Y'}">만족도 사용</c:when>
										<c:otherwise>사용안함</c:otherwise>
									</c:choose>
								</td>
							</tr>
							 -->

								<tr>
									<th class="text-right">추가입력항목<br>(1열 자동 구성)</th>
									<td colspan="3">
										<table id="boardItemTable" style="width:100%" class="table table-bordered sm-table">
							               	<colgroup>
												<col style="width:17%">
												<col style="width:17%">
												<col style="width:17%">
												<col style="">
												<col style="width:15%">
												<col style="width:11%">
											</colgroup>
											<thead class="bg-lightblue">
												<tr>
													<th>ID</th>
													<th>항목영문고유ID</th>
													<th>항목유형</th>
													<th>항목명</th>
													<th>순서</th>
													<th>관리</th>
												</tr>
											</thead>
											<tbody>
								<c:choose>
									<c:when test="${itemList!=null  && fn:length(itemList)>0}">
										<c:forEach items="${itemList}" var="item" varStatus="status">
												<tr class="item-row">
													<td><c:out value="${item.bbsEtcseq }"/></td>
													<td><c:out value="${item.bbsItemEnid }"/></td>
													<td>
													<c:choose>
														<c:when test="${item.bbsItemType eq '1001'}">일반정보</c:when>
														<c:when test="${item.bbsItemType eq '2001'}">컨텐츠정보</c:when>
														<c:otherwise>사용안함</c:otherwise>
													</c:choose>
													</td>
													<td><c:out value="${item.bbsItemNm }"/></td>
													<td><c:out value="${item.bbsItemSort }"/></td>
													<td>
													</td>
												</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
												<tr class="item-no">
													<td colspan="5" class="text-center">추가입력항목이 없습니다.</td>
												</tr>
									</c:otherwise>
								</c:choose>
											</tbody>
										</table>
									</td>
								</tr>
								<tr>
									<th class="text-right">카테고리</th>
									<td colspan="3">
										<table id="boardCtgTable" style="width:100%" class="table table-bordered sm-table">
							               	<colgroup>
												<col style="">
												<col style="width:25%">
												<col style="width:12%">
												<col style="width:12%">
												<col style="width:15%">
												<col style="width:11%">
											</colgroup>
											<thead>
												<thead class="bg-lightblue">
													<th>ID</th>
													<th>카테고리명</th>
													<th>순서</th>
													<th>사용</th>
													<th>등록일</th>
													<th>관리</th>
												</tr>
											</thead>
											<tbody>
								<c:choose>
									<c:when test="${ctgList!=null  && fn:length(ctgList)>0}">
										<c:forEach items="${ctgList}" var="ctg" varStatus="status">
												<tr class="ctg-row">
													<td><c:out value="${ctg.ctgId }"/></td>
													<td><c:out value="${ctg.ctgNm }"/></td>
													<td><c:out value="${ctg.ctgSort }"/></td>
													<td><c:out value="${ctg.useAt }"/></td>
													<td><c:out value="${ctg.frstRegisterPnttm }"/></td>
													<td>
													</td>
												</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
												<tr class="ctg-no">
													<td colspan="6" class="text-center" >카테고리를 추가해주세요.</td>
												</tr>
									</c:otherwise>
								</c:choose>



											</tbody>
										</table>
									</td>
								</tr>
								<tr>
									<th class="text-right">등록자/일시</th>
									<td>
							 <c:choose>
							 	<c:when test="${fn:length(result.frstRegisterId) > 0}">
										<c:out value="${result.frstRegisterId}"/> / <c:out value="${result.frstRegisterPnttm}"/>
								</c:when>
								<c:otherwise>
										<c:out value="${adminVO.id}" /> / <fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />
								</c:otherwise>
							</c:choose>
									</td>
									<th class="text-right">수정자/일시</th>
									<td>
							 <c:choose>
							 	<c:when test="${fn:length(result.lastUpdusrId) > 0}">
										<c:out value="${result.lastUpdusrId}"/> / <c:out value="${result.lastUpdusrPnttm}"/>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
									</td>
								</tr>
						</tbody>

						</table>


	                </div>
	                <!-- /.card-body -->
	                <div class="card-footer">
	<!--
	                  <button type="button" class="btn btn-default float-right">삭제</button>
	 -->
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
			<a class="btn btn-info mr-2 float-right" href="./masterUpdt<c:out value="${commandMap.queryAll}"/>">수정</a>
		</c:when>
		<c:otherwise>
			<a class="btn btn-info mr-2 float-right disabled">수정</a>
		</c:otherwise>
	</c:choose>
	                  <a class="btn btn-default" href="./masterList<c:out value="${listQuery}"/>">목록</a>
	                </div>
	                <!-- /.card-footer -->

	              </div>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
	          </div>
	        </div>
	</section>

	<!-- 모달 박스 -->
	<div class="modal fade" id="modal-default" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Default Modal</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">
					<p>One fine body…</p>
				</div>
				<div class="modal-footer justify-content-between">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-primary" onclick="fn_save()">저장</button>
				</div>
			</div>
		</div>
	</div>
</body>
