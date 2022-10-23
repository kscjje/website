<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : articleList.jsp
  * @Description : 게시판 게시물  목록 화면
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
	<script>
		function fn_article_delete(nttId) {
			if (confirm("삭제하시겠습니까?\n삭제하시면 데이타는 남아 있고 사용자 페이지에서는 노출되지 않습니다.")) {
				$.ajax({
				      url: './delete.json',
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
	       	<div class="card">
	<form name="articleForm" action="./list" method="get" >
			 	 	 <div class="card-body">
			 	 	 	<div class="row">
	<c:if test="${!empty ctgList }">
							<div class="col-2">
									<select name="searchCtg" title="카테고리 선택"  class="form-control">
										<option value=""  >::카테고리::</option>
	<c:forEach items="${ctgList}" var="item" varStatus="status">
										<option value="<c:out value="${item.ctgId }"/>" <c:if test="${searchVO.searchCtg eq item.ctgId }">selected</c:if> ><c:out value="${item.ctgNm }"/></option>
	</c:forEach>
									</select>

							</div>
	</c:if>
	<c:if test="${boardMasterVO.bbsTyCode eq 'B005' }">
							<div class="col-2">
									<select name="searchStatus" title="답변상태"  class="form-control">
										<option value=""  >::답변상태::</option>
										<option value="Y"  <c:if test="${searchVO.searchStatus eq 'Y'}">selected="selected"</c:if> >답변완료</option>
										<option value="N"  <c:if test="${searchVO.searchStatus eq 'N'}">selected="selected"</c:if> >답변미완료</option>
									</select>
							</div>
	</c:if>
			              	<div class="col-3">
			              			<select name="searchCnd" title="검색어"  class="form-control">
										<option value="0"  <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> >제목</option><!-- 글 제목  -->
										<option value="1"  <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if> >내용</option><!-- 글 내용 -->
										<option value="2"  <c:if test="${searchVO.searchCnd == '2'}">selected="selected"</c:if> >작성자</option><!-- 작성자 -->
									</select>
							</div>
							<div class="col-3">
									<input class="form-control" name="searchWrd" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchWrd}"/>'  maxlength="155" >
							</div>
							<div class="col-2">
								<button type="submit" class="btn btn-secondary">조회</button>
								<c:choose>
									<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
										<a href="./regist" class="btn btn-info" title="등록버튼">등록</a><!-- 등록 -->
									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn-info disabled">등록</button>
									</c:otherwise>
								</c:choose>
			              	</div>
	<c:if test="${empty ctgList }">
							<div class="col-2"></div>
	</c:if>
	 					</div>
	 				</div>
	 </form>
	 			</div>

	       <div class="row">
	          <div class="col-12">
	            <div class="card card-primary card-outline">
	              <!-- /.card-header -->

	              <div class="card-body table-responsive p-0">
		               <table class="table table-hover text-nowrap">
		               	<colgroup>
							<col style="width: 5%;">
	<c:if test="${!empty ctgList }"><col style="width: 10%;"></c:if>
	<c:if test="${boardMasterVO.bbsId eq 'qna' }"><col style="width: 9%;"></c:if>
							<col style="width: *">
							<col style="width: 10%;">
							<col style="width: 13%;">
							<col style="width: 7%;">
							<col style="width: 10%;">
						</colgroup>
		                  <thead>
		                    <tr>
		                      	<th>번호</th><!-- 번호 -->
	<c:if test="${!empty ctgList }"><th>카테고리</th></c:if>
	<c:if test="${boardMasterVO.bbsId eq 'qna' }"><th>답변상태</th></c:if>
								<th class="board_th_link">제목</th><!--글 제목  -->
								<th>작성자</th><!-- 작성자명 -->
								<th>작성일시</th><!-- 작성시각 -->
								<th>조회수</th><!-- 조회수  -->
								<th>관리</th>
		                    </tr>
		                  </thead>
		                  <tbody>

		<!-- 공지사항 본문 -->
<c:forEach items="${noticeList}" var="noticeInfo" varStatus="status">
		<tr>
			<td>공지</td>
	<c:if test="${!empty ctgList }">
			<td><c:out value="${noticeInfo.ctgNm}"/></td>
	</c:if>
	<c:if test="${boardMasterVO.bbsId eq 'qna' }">
			<td>-</td>
	</c:if>
			<td <c:if test="${noticeInfo.sjBoldAt eq 'Y' }">class="font-weight-bold"</c:if>>
				<c:choose>
					<c:when test="${noticeInfo.useAt eq 'Y' }">
						<a href="./view/<c:out value="${noticeInfo.nttId}"/><c:out value="${commandMap.queryAll}"/>"><c:out value='${fn:substring(noticeInfo.nttSj, 0, 40)}'/></a>
					</c:when>
					<c:otherwise>
						<a href="./view/<c:out value="${noticeInfo.nttId}"/><c:out value="${commandMap.queryAll}"/>"><strike><i><c:out value='${fn:substring(noticeInfo.nttSj, 0, 40)}'/></i></strike></a>
					</c:otherwise>
				</c:choose>
				<c:if test="${noticeInfo.commentCo != ''}"><span class="text-green"><c:out value='[${noticeInfo.commentCo}]'/></span></c:if>
				<c:if test="${noticeInfo.fileCnt > 0 }"><i class="fa fa-fw fa-paperclip"></i></c:if>
			</td>
			<td><c:out value='${noticeInfo.frstRegisterNm}'/></td>
			<td><fmt:formatDate value="${noticeInfo.frstRegisterPnttm}" pattern="yyyy-MM-dd HH:mm"/></td>
			<td><c:out value='${noticeInfo.inqireCo}'/></td>
			<td>
									<c:if test="${noticeInfo.useAt eq 'Y' }">
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
			<a class="btn bg-primary btn-xs" href="./modify?nttId=<c:out value="${noticeInfo.nttId}"/><c:out value="${commandMap.query}"/>">수정</a>
		</c:when>
		<c:otherwise>
			<a class="btn bg-primary btn-xs disabled">수정</a>
		</c:otherwise>
	</c:choose>
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
			<button type="button" onclick="fn_article_delete(<c:out value="${noticeInfo.nttId}"/>)" class="btn btn-secondary btn-xs" >삭제</button>
		</c:when>
		<c:otherwise>
			<button type="button" class="btn btn-secondary btn-xs disabled" >삭제</button>
		</c:otherwise>
	</c:choose>
									</c:if>


			</td>
		</tr>
</c:forEach>
	<c:if test="${fn:length(resultList) == 0}">
						<tr>
							<td colspan="9" class="text-center">데이타가 없습니다</td>
						</tr>
	</c:if>
	<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
		<c:if test="${resultInfo.nttNo eq '1' }">
		                    <tr>
		                      	<td><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
	<c:if test="${!empty ctgList }">
								<td><c:out value="${resultInfo.ctgNm}"/></td>
	</c:if>
	<c:if test="${boardMasterVO.bbsId eq 'qna' }">
								<td><c:out value="${resultInfo.commentCo != '' ?'완료':'미완료'}"/></td>
	</c:if>
								<td class="left<c:if test="${resultInfo.sjBoldAt eq 'Y' }"> font-weight-bold</c:if>">

									<c:if test="${resultInfo.replyLc > 0}"><c:forEach begin="1" end="${resultInfo.replyLc}" step="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	</c:forEach>
										<img src="../../../resources/images/icon_reply.png" alt="reply">
									</c:if>
									<c:if test="${resultInfo.secretAt eq 'Y'}"><i class="fa fa-fw fa-lock"></i></c:if>
									<c:choose>
										<c:when test="${resultInfo.useAt eq 'Y' }">
											<a href="./view/<c:out value="${resultInfo.nttId}"/><c:out value="${commandMap.queryAll}"/>"><c:out value='${fn:substring(resultInfo.nttSj, 0, 40)}'/></a>
										</c:when>
										<c:otherwise>
											<a href="./view/<c:out value="${resultInfo.nttId}"/><c:out value="${commandMap.queryAll}"/>"><strike><i><c:out value='${fn:substring(resultInfo.nttSj, 0, 40)}'/></i></strike></a>
										</c:otherwise>
									</c:choose>
									<c:if test="${resultInfo.commentCo != ''}"><span class="text-green"><c:out value='[${resultInfo.commentCo}]'/></span></c:if>
									<c:if test="${resultInfo.fileCnt > 0 }"><i class="fa fa-fw fa-paperclip"></i></c:if>
								</td>
								<td><c:out value='${resultInfo.frstRegisterNm}'/></td>
								<td><fmt:formatDate value="${resultInfo.frstRegisterPnttm}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td><c:out value='${resultInfo.inqireCo}'/></td>
								<td>
									<c:if test="${resultInfo.useAt eq 'Y' }">
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
			<a class="btn bg-primary btn-xs" href="./modify?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${commandMap.query}"/>">수정</a>
		</c:when>
		<c:otherwise>
			<a class="btn bg-primary btn-xs disabled">수정</a>
		</c:otherwise>
	</c:choose>
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
			<button type="button" onclick="fn_article_delete(<c:out value="${resultInfo.nttId}"/>)" class="btn btn-secondary btn-xs" >삭제</button>
		</c:when>
		<c:otherwise>
			<button type="button" class="btn btn-secondary btn-xs disabled" >삭제</button>
		</c:otherwise>
	</c:choose>
									</c:if>
								</td>
		                    </tr>
		</c:if>
	</c:forEach>

		                  </tbody>
		                </table>
	              </div>
	              <!-- /.card-body -->
	              <div class="card-footer p-0">
	              	<div class="pagination_2">
						<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
					</div>
	            </div>

	            <!-- /.card -->
	          </div>
	        </div>
	      </div>
	     </div>
	</section>
</body>
