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
	<script type="text/javascript" src="../../../resources/js/EgovMultiFile.js"></script>
	<script type="text/javascript">
	/* ********************************************************
	 * 삭제처리
	 ******************************************************** */
<% /*
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
				      },
				      error: function() {
				    	  alert("Server Error");
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
				      },
				      error: function() {
				    	  alert("Server Error");
				      }
				 });
			}
	}
*/ %>

	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1><small>학습동아리></small>
					<c:choose>
						<c:when test="${type eq 'open' }">
							학습동아리개설현황
						</c:when>
						<c:otherwise>
							학습동아리승인처리
						</c:otherwise>
					</c:choose>
	            </h1>

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
              		<h4>0 .기본 정보</h4>
		            <div class="card card-primary card-outline">
		                <div class="card-body p-0">
		                	<table class="table table-bordered" style="">
				               	<colgroup>
									<col style="width:150px">
									<col style="width: ;">
									<col style="width: ;">
									<col style="width: ;">
								</colgroup>
								<tbody>
									<tr>
										<th class="text-right">승인상태 <b class="text-red">*</b></th>
										<td colspan="3">
											<c:if test="${articleVO.ntceStat eq '1' }"><label class="text-green">승인대기</label></c:if>
											<c:if test="${articleVO.ntceStat eq '2' }"><label class="text-blue">승인</label></c:if>
											<c:if test="${articleVO.ntceStat eq '3' }"><label class="text-blue">반려</label></c:if>
										</td>
									</tr>
									<tr>
										<th class="text-right">신청자</th>
										<td colspan="3"><c:out value='${articleVO.frstRegisterNm}'/>(<c:out value='${articleVO.ntcrId}'/>)</td>
									</tr>
									<tr>
										<th class="text-right">개설신청일</th>
										<td colspan="3"><fmt:formatDate value="${articleVO.frstRegisterPnttm}" pattern="yyyy-MM-dd HH:mm"/></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>

              	<h4>01. 모임장 정보</h4>
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
								<!-- 작성자, 작성시각,  -->
								<tr>
									<th>작성자</th>
									<td class="left"><c:out value="${result.frstRegisterNm}"/> / <c:out value="${result.frstRegisterId}"/></td>
								</tr>
								<tr>
									<th>모임장 성명</th>
									<td class="left"><c:out value="${result.item1}" /></td>
								</tr>
								<tr>
									<th>휴대폰번호</th>
									<td class="left"><c:out value="${result.item2}" /></td>
								</tr>
								<tr>
									<th>이메일 </th>
									<td class="left"><c:out value="${result.item3}" />@<c:out value="${result.item3}" /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

              	<h4>02. 동아리 정보</h4>
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

								<tr>
									<th>동아리명 </th>
									<td colspan="3" class="left"><c:out value="${result.nttSj}"/></td>
								</tr>
								<tr>
									<th>모임주소  </th>
									<td colspan="3" class="left"><c:out value="${result.item5}" /></td>
								</tr>
								<tr>
									<th>지역 </th>
									<td colspan="3" class="left">
								<c:if test="${!empty areaList}">
									<c:forEach items="${areaList}" var="item" varStatus="status">
										<c:if test="${item.areaCd eq result.item6}">
											<c:out value="${item.areaName }"/>
										</c:if>
									</c:forEach>
								</c:if>
									</td>
								</tr>

								<tr>
									<th>모임주기(월)  </th>
									<td colspan="3" class="left"><c:out value="${result.item7}" /></td>
								</tr>
								<tr>
									<th>요일/시간  </th>
									<td colspan="3" class="left"><c:out value="${result.item8}" /></td>
								</tr>
								<tr>
									<th>회원 모집조건  </th>
									<td colspan="3" class="left"><c:out value="${result.item9}" /></td>
								</tr>
								<tr>
									<th>동아리 소개글</th>
									<td colspan="3" class="left"><c:out value="${fn:replace(result.nttCn , crlf , '<br/>')}" escapeXml="false" /></td>
								</tr>
								<tr>
									<th>동아리 대표사진</th>
									<td colspan="3" class="left">
			<c:choose>
				<c:when test="${not empty result.atchFileId}">
				<img src="<c:url value="/web"/>/common/file/download?atchFileId=<c:out value="${result.atchFileId}"/>&fileSn=<c:out value="${result.atchImg }"/>" alt="학습동아리 <c:out value="${result.nttSj}"/> 이미지" onError="this.src='<c:url value="/web"/>/resources/images/data/no_img.jpg'">
				</c:when>
				<c:otherwise>
				<img src="<c:url value="/web"/>/resources/images/data/no_img.jpg" alt="이미지" >
				</c:otherwise>
			</c:choose>
									</td>
								</tr>
								<tr>
									<th>동아리 홈페이지</th>
									<td colspan="3" class="left"><a href="<c:out value="${result.item10}" />" target="_blank"><c:out value="${result.item10}" /></a></td>
								</tr>
								<tr>
									<th>모집여부</th>
									<td colspan="3">
										<c:out value="${result.ctgNm}"/>
									</td>
								</tr>
							</tbody>
						</table>
	                </div>
	                <!-- /.card-body -->
	                <div class="card-footer">
<% /*
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
*/ %>

	 <c:choose>
		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
			<a class="btn btn-info mr-2 float-right" href="../modify?nttId=<c:out value="${result.nttId}"/><c:out value="${commandMap.query}"/>">수정</a>
		</c:when>
		<c:otherwise>
			<a class="btn btn-info mr-2 float-right disabled">수정</a>
		</c:otherwise>
	</c:choose>

	                  	<a class="btn btn-default " href="../list<c:out value="${commandMap.queryAll}"/>">목록</a>

	              </div>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
	          </div>
	        </div>
	</section>


</body>
