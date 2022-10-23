<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="now" value="<%=new java.util.Date()%>" />

<head>
	<script type="text/javascript">
		$(document).ready(function() {
			$('input.calendar').datepicker({
				autoclose: true,
				calendarWeeks: false,
				todayHighlight: true,
				language: "kr",
				dateFormat: "yy-mm-dd"
			});
		});
	</script>
</head>
<body>
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>설문조사 결과</h1>
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
						<div class="card-body  p-0">
							<table class="table table-bordered text-nowrap">
										<colgroup>
											<col style="width:5%;">
											<col style="width:45%;">
											<col style="width:5%;">
											<col style="width:45%;">
										</colgroup>
										<tbody>
											<tr>
												<th>설문 고유번호</th>
												<td class="left"><c:out value="${themaVO.qestnarId}"/></td>
												<th rowspan="5">첨부파일</th>
												<td rowspan="5">
											<c:if test="${!empty themaVO.orginFileName }">
													<div id="orginFileName">※ 현재 파일 : ${themaVO.orginFileName}</div>
													<c:choose>
														<c:when test="${themaVO.qestnarImgfilnb!=null  && fn:length(themaVO.qestnarImgfilnb)>0}">
															<img src="<c:url value="/web"/>/common/file/download?atchFileId=<c:out value="${themaVO.qestnarImgfilnb}"/>&fileSn=0" alt="첨부파일 이미지"  onError="this.src='<c:url value="/web"/>/resources/images/data/no_img.jpg'" style="max-width:100%; max-height:200px;">
														</c:when>
														<c:otherwise>
															<img src="<c:url value="/web"/>/resources/images/data/img_data11.jpg" alt="알림마당 설문조사 메인 이미지"  style="max-width:100%; max-height:200px;">
														</c:otherwise>
													</c:choose>			   							
											</c:if>								
												</td>
											</tr>
											<tr>
												<th>등록기관명</th>
												<td class="left"><c:out value="${themaVO.orgNm}"/></td>
											</tr>
											<tr>
												<th>설문명</th>
												<td class="left"><c:out value="${themaVO.qestnarName}"/></td>
											</tr>
											<tr>
												<th>설문 안내설명</th>
												<td class="left" ><c:out value="${fn:replace(themaVO.qestnarGuide , crlf , '<br/>')}" escapeXml="false" /></td>
											</tr>							
											<tr>
												<th>설문 기간</th>
												<td class="left">
									<c:choose>
										<c:when test="${themaVO.stdrmng.qestnarOpertype eq '1001'}">
												상시운영 : 
												<c:choose>
													<c:when test="${themaVO.stdrmng.openYn eq 'Y'}">
														공개
													</c:when>
													<c:when test="${themaVO.stdrmng.openYn eq 'N'}">
														비공개
													</c:when>
												</c:choose>
													(<c:out value="${themaVO.stdrmng.qestnarOpenDate}"/> ${themaVO.stdrmng.qestnarOpenTimeHH}:${themaVO.stdrmng.qestnarOpenTimeMM})
										</c:when>
										<c:when test="${themaVO.stdrmng.qestnarOpertype eq '2001'}">
											기간설정 : ${themaVO.stdrmng.qestnarOpersdate} ~ ${themaVO.stdrmng.qestnarOperedate}"
										</c:when>
									</c:choose>
												</td>
											</tr>
										</tbody>
									</table>
							</div>
						<div class="card-footer text-center">
							<button type="button" class="btn btn-default " onclick="history.back();">취소</button>
						</div>              
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-12">
					<h4>통계 항목별 정보</h4>
						<div class="card  ">
							<div class="card-body ">											

										<table id="serveyQuestionTable" style="width:100%" class="table table-bordered sm-table">
													
											<thead class="bg-lightblue">
												<tr class="text-white text-center">
													<td>No.</td>
													<td>질문내용</td>
													<td>질문유형</td>
													<td>전체통계</td>
												</tr>
											</thead>
											<tbody>
							<c:if test="${questionList!=null  && fn:length(questionList)>0}">
								<c:forEach items="${questionList}" var="q" varStatus="status">
									<c:choose>
										<c:when test="${q.qestnsType eq '0000'}">
												<tr class="bg-gray disabled color-palette text-white text-center">
													<td class=""><c:out value="${status.index+1}"/></td>
										</c:when>
										<c:when test="${q.qestnsType eq '3001'}">
												<tr class="bg-lightblue disabled color-palette text-white text-center">
													<td class=""><c:out value="${status.index+1}"/></td>
										</c:when>
										<c:otherwise>
												<tr class="bg-lightblue disabled color-palette text-white text-center">
													<td class="" rowspan="${fn:length(q.itemList)+1}"><c:out value="${status.index+1}"/></td>
										</c:otherwise>
									</c:choose>									
												
													<td class="text-left"><c:out value="${q.qestnsName }"/></td>
													<td>
														<c:forEach items="${questionTypeList}" var="qt" varStatus="statust">
															<c:if test="${q.qestnsType eq qt.cd }">${qt.cdNm }</c:if>
														</c:forEach>
													</td>
													<td class="text-left">
									<c:choose>
										<c:when test="${q.qestnsType eq '0000'}">
											<c:if test="${fn:length(q.itemList)>0}"> 		
													<c:out value="${q.itemList[0].qestnarItemnm }"/>
											</c:if>
										</c:when>
										<c:when test="${q.qestnsType eq '3001'}">
										</c:when>
										<c:when test="${q.qestnsType eq '1001'}">
														<c:set var="qtotal" value="0" />
														<c:forEach items="${q.itemList}" var="qi" varStatus="status">
															<c:set var="qtotal" value="${qtotal + qi.resultScore }" />
														</c:forEach>
														<c:out value="${ qtotal}"/> 점
										</c:when>
										<c:when test="${q.qestnsType eq '2001'}">
														(-)
										</c:when>
										
									</c:choose>
													</td>
												</tr>
									<c:choose>
										<c:when test="${q.qestnsType eq '0000'}">
										</c:when>
										<c:when test="${q.qestnsType eq '3001'}">
										</c:when>
										<c:otherwise>
											<c:forEach items="${q.itemList}" var="qi" varStatus="status">										
												<tr>
													<td class="text-right"><c:out value="${qi.qestnarItemnm }"/></td>
													<td class="text-right">
													<c:choose>
														<c:when test="${q.qestnsType eq '1001'}">
															${qi.qestnarScore }점
														</c:when>
														<c:when test="${q.qestnsType eq '2001'}">
																		-
														</c:when>
													</c:choose>													
													</td>
													<td>
														<span class="text-blue">${qi.resultCnt }명</span>
						<c:choose>
							<c:when test="${qi.resultTotal>0}">
															(<span class="text-red"><fmt:formatNumber value="${qi.resultCnt/qi.resultTotal}" type="percent"/></span>)
							</c:when>
							<c:otherwise>
															(<span class="text-red">0%</span>)
							</c:otherwise>
						</c:choose>													
													</td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>												
								</c:forEach>
							</c:if>
											</tbody>
										</table>		
								
							</div>
						<div class="card-footer text-center">
							<button type="button" class="btn btn-default " onclick="history.back();">취소</button>
						</div>              
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-12">
					<h4>참여목록</h4>
						<div class="card  ">
							<div class="card-body ">
								<div class="table-responsive">
									<table id="serveyResultTable" style="width:100%" class="table table-bordered sm-table">
										<thead class="bg-lightblue">
											<tr class="text-white">
												<td>No.</td>
												<td>이름</td>
												<td>ID</td>
												<td>제출일</td>
									<c:set var="no" value="0" />
									<c:if test="${questionList!=null  && fn:length(questionList)>0}">
										<c:forEach items="${questionList}" var="q" varStatus="status">
											<c:if test="${q.qestnsType ne '0000'}">
												<td>
													<c:set var="no" value="${no+1 }" />
													<c:out value="${no }"/>. <c:out value="${q.qestnsName }"/>
												</td>
											</c:if>
										</c:forEach>
									</c:if>												
											</tr>
										</thead>
										<tbody>
				<c:choose>
					<c:when test="${resultList!=null  && fn:length(resultList)>0}">
						<c:forEach items="${resultList}" var="r" varStatus="status">
											<tr>
												<td>${status.index+1 }</td>
												<td>${r.qestnarUsername }</td>
												<td>${r.qestnarMembWebid }</td>
												<td><fmt:formatDate value="${r.regdate }" pattern="yyyy-MM-dd HH:mm"/></td>
							<c:if test="${r.dtlList!=null  && fn:length(r.dtlList)>0}">
								<c:forEach items="${r.dtlList}" var="d" varStatus="status2">
									<c:choose>
										<c:when test="${d.qestnsType eq '0000'}">
										</c:when>
										<c:when test="${d.qestnsType eq '3001'}">
												<td>
													<c:out value="${d.resltSbjct }"/>
												</td>													
										</c:when>
										<c:otherwise>
												<td>
													<c:out value="${d.resltItemseq }"/>.
													<c:out value="${d.qestnarItemnm }"/>
													<c:if test="${d.resltItemscore>0 }">
														(<c:out value="${d.resltItemscore }"/>)
													</c:if>
												</td>													
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:if>
											</tr>
						</c:forEach>
					</c:when>
				<c:otherwise>
											<tr>
												<td class="text-center" colspan="${fn:length(questionList)+4 }">참여 목록이 없습니다.</td>
											</tr>
				</c:otherwise>
			</c:choose>
										</tbody>
									</table>
								</div>		                
							</div>
							<div class="card-footer text-center">
								<button type="button" class="btn btn-default " onclick="history.back();">취소</button>
							</div>  							
						</div>
					</div>
				</div>     
			</div>
		</div>
	</section>


</body>