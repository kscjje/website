<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%
	WebApplicationContext wapContext = org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	Object dynamicConfig = wapContext.getBean("dynamicConfig");
	String adminRoot = dynamicConfig != null ? ((com.hisco.cmm.config.DynamicConfig)dynamicConfig).getAdminRoot() : com.hisco.cmm.util.Config.ADMIN_ROOT;
%>
<c:set var="ADMIN_ROOT" value='<%=request.getContextPath() + adminRoot + "/"%>'/>

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
	<script type="text/javascript" src="../../resources/js/EgovMultiFile.js"></script>
	<script type="text/javascript" src="../../validator"></script>
	<validator:javascript formName="articleCommentVO" staticJavascript="false" xhtml="true" cdata="false"/>
	<script type="text/javascript">
	/* ********************************************************
	 * 사용안함 처리
	 ******************************************************** */
	 function fn_article_delete(mode,nttId) {
			if (confirm("사용안함 처리 하시겠습니까?\n처리 시 데이타는 남아 있고 사용자 페이지에서는 노출되지 않습니다.")) {
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
	

	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>강사PR <small>정보</small>
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
              	<h4>01. 강사정보</h4>
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
									<th class="text-right">등록자</th>
									<td colspan="3"><c:out value='${articleVO.frstRegisterNm}'/>(<c:out value='${articleVO.ntcrId}'/>)
				<c:choose>
					<c:when test="${instrPoolVO!=null && fn:length(instrPoolVO.memNo)>0 }">
										<a href="<c:out value="${ADMIN_ROOT }"/>instrpool/detail?detailNo=<c:out value="${instrPoolVO.memNo }"/>" class="btn_s1_c1" target="_blank">강사 POOL 이동</a>
					</c:when>
					<c:otherwise>
										<span class="text-red">강사POOL정보가 없습니다.</span>
					</c:otherwise>
				</c:choose>										
									</td>
								</tr>
								<tr>
									<th class="text-right">등록일</th>
									<td colspan="3"><fmt:formatDate value="${articleVO.frstRegisterPnttm}" pattern="yyyy-MM-dd HH:mm"/></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
										
              	<h4>02. 등록 정보</h4>
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
										<th class="text-right">강사PR 제목  <b class="text-red">*</b></th>
										<td colspan="3">
											<c:out value="${articleVO.nttSj}" />
										</td>
									</tr>
								
									<tr>
										<th class="text-right">대표사진</th>
										<td colspan="3">
			<c:choose>
				<c:when test="${not empty result.atchFileId}">
				<img src="<c:url value="/web"/>/common/file/download?atchFileId=<c:out value="${result.atchFileId}"/>&fileSn=<c:out value="${result.atchImg }"/>" alt="학습동아리 <c:out value="${result.nttSj}"/> 이미지" onError="this.src='<c:url value="/web"/>/resources/images/data/img_teacher07.jpg'">
				</c:when>
				<c:otherwise>
				<img src="<c:url value="/web"/>/resources/images/data/img_teacher07.jpg" alt="이미지">
				</c:otherwise>
			</c:choose>	
										</td>
									</tr>


									<tr>
										<th class="text-right">강좌주제 <b class="text-red">*</b></th>
										<td colspan="3">
											<c:out value="${articleVO.item1}" />
										</td>
									</tr>
									<tr>
										<th class="text-right">강좌소개 <b class="text-red">*</b></th>
										<td colspan="3">
											<c:out value="${fn:replace(result.nttCn , crlf , '<br/>')}" escapeXml="false" />
										</td>
									</tr>
																		
									<tr>
										<th class="text-right">핵심키워드 </th>
										<td colspan="3">
											<ul>
											<c:if test="${fn:length(result.item2)>0}"><li>#<c:out value="${result.item2}"/></li></c:if>
											<c:if test="${fn:length(result.item3)>0}"><li>#<c:out value="${result.item3}"/></li></c:if>
											<c:if test="${fn:length(result.item4)>0}"><li>#<c:out value="${result.item4}"/></li></c:if>
											<c:if test="${fn:length(result.item5)>0}"><li>#<c:out value="${result.item5}"/></li></c:if>
											</ul>									
										</td>
									</tr>
									<tr>
										<th class="text-right">기획제안  <b class="text-red">*</b></th>
										<td colspan="3">
											<c:out value="${fn:replace(result.item6 , crlf , '<br/>')}" escapeXml="false" />
										</td>
									</tr>
																		
									<tr>
										<th class="text-right">준비물   <b class="text-red">*</b></th>
										<td colspan="3">
											<c:out value="${fn:replace(result.item7 , crlf , '<br/>')}" escapeXml="false" />
										</td>
									</tr>
																		
									<tr>
										<th class="text-right">연관 강사코드   (프로필연동)</th>
										<td colspan="3">
				<c:choose>
					<c:when test="${instrPoolVO!=null && fn:length(instrPoolVO.memNo)>0 }">
										<a href="<c:out value="${ADMIN_ROOT }"/>instrpool/detail?detailNo=<c:out value="${instrPoolVO.memNo }"/>" class="btn_s1_c1" target="_blank"><c:out value="${instrPoolVO.memNo }"/></a>
					</c:when>
					<c:otherwise>
										<span class="text-red">강사POOL정보가 없습니다.</span>
					</c:otherwise>
				</c:choose>	
										</td>
									</tr>

							</tbody>
						</table>
	                </div>
	                <!-- /.card-body -->
	                <div class="card-footer">
	 <c:choose>
		<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
			<button type="button" class="btn btn-secondary float-right" onclick="fn_article_deleteall(<c:out value="${result.nttId}"/>)">데이터 삭제</button>
			<c:if test="${result.useAt eq 'Y' }">
				<button type="button" class="btn btn-secondary float-right mr-2" onclick="fn_article_delete('D',<c:out value="${result.nttId}"/>)">사용안함</button>
			</c:if>
		</c:when>
		<c:otherwise>
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

	              </div>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
	          </div>
	        </div>
	</section>


</body>
