<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : evtEnvReserDetail.jsp
  * @Description : 컨텐츠 관리 상세 화면
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.03.22    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.03.22
  *  @version 1.0
  *  @see
  *
  */
%>

<head>

	<script>
		
		function fn_deleteExbt(contentsSeq) {
		
			var varFrom = document.templateVO;
			
			if (confirm("선택하신 컨텐츠 정보를 삭제합니다.")) {
				$("#contentsSeq").val(contentsSeq);
				varFrom.submit();
				return;
			}
			
		}
		
	</script>
</head>

<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	             <h1>컨텐츠 관리</h1>
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
		              <div class="card-body  table-responsive p-0">
		              
			                	<table class="table table-bordered text-nowrap">
				               	<colgroup>
									<col style="width:5%;">
									<col style="width:20%;">
									<col style="width:5%;">
									<col style="width:20%;">
									<col style="width:5%;">
									<col style="width:20%;">
									<col style="width:5%;">
									<col style="width:20%;">							
								</colgroup>
			
									<tbody> 
									
										<tr>
											<th>고유번호</th>
											<td colspan="6" class="left">
												<c:out value='${contentsDetail[0].contentsSeq}'/>
											</td>
										</tr>
										<tr>
											<th>* 컨텐츠 구분</th>
											<td colspan="3" class="left">
											
					                    		<c:forEach items="${cotContentsList}" var="item" varStatus="status">
					                    			<c:if test="${item.cd eq contentsDetail[0].contentsGbn}">
					                    			 	<c:out value="${item.cdNm}"/>
					                    			</c:if>
					                    		</c:forEach>
												<c:if test="${menuNo ne 'null'}">
													<label class="col-sm-2 col-form-label"> 메뉴와 연결됨, 메뉴번호 : ${contentsDetail[0].menuNo}</label>																							
												</c:if>	
											</td>
											<th>* 컨텐츠 속성</th>
											<td colspan="3" class="left">
											
												<c:if test="${contentsDetail[0].cntsType eq '1001'}">
													HTML												
												</c:if>
												<c:if test="${contentsDetail[0].cntsType eq '2001'}">
													TEXT												
												</c:if>
	
											</td>										
										</tr>					
										<tr>
											<th>
												* 컨텐츠 제목
											</th>
											<td class="left" colspan="7">
						                    	<c:out value='${contentsDetail[0].contentsSubject}'/>
											</td>
										</tr>
										<tr>
											<th>컨텐츠 내용</th>
											<td colspan="7" class="left">
												<c:if test="${contentsDetail[0].cntsType eq '1001'}">
													${contentsDetail[0].contentsMain}
												</c:if>
												<c:if test="${contentsDetail[0].cntsType eq '2001'}">
													<c:out value='${contentsDetail[0].contentsMain}'/>												
												</c:if>
											</td>
										</tr>												
										<tr>
											<th><label for="RegiUFile01" class="col-sm-2 col-form-label">파일 첨부 1</label></th>
											<td class="left" colspan="7">
											<c:if test="${not empty contentsDetail[0].file01Id}">
												<c:import url="/web/common/upload/includeUploadDetail" >
									       			<c:param name="file_group_id" value="${contentsDetail[0].file01Id}" />
								        		</c:import>
											</c:if>											
											</td>
										</tr>									
										<tr>
											<th><label for="RegiUFile02" class="col-sm-2 col-form-label">파일 첨부 2</label></th>
											<td colspan="7" class="left">
											<c:if test="${not empty contentsDetail[0].file02Id}">
												<c:import url="/web/common/upload/includeUploadDetail" >
									       			<c:param name="file_group_id" value="${contentsDetail[0].file02Id}" />
								        		</c:import>
											</c:if>											
											</td>										
										</tr>									
										<tr>
											<th>* 사용 여부</th>
											<td colspan="3" class="left">
							                    <div class="col-sm-10">
							                    
							                    	<c:if test="${contentsDetail[0].useYn eq 'Y'}">
							                    		사용함 
							                    	</c:if>
							                    	<c:if test="${contentsDetail[0].useYn eq 'N'}">
							                    		사용안함 
							                    	</c:if>
	
							                    </div>
											</td>
										</tr>					
									
									</tbody>
			
								</table>	
		
		                </div>
		                <!-- /.card-body -->
		                <div class="card-footer">
							 <c:choose>
								<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
									<button type="button" class="btn btn-secondary float-right mr-2" onclick="fn_deleteExbt('${contentsDetail[0].contentsSeq}');">삭제</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-secondary float-right disabled mr-2">삭제</button>
								</c:otherwise>
							</c:choose>
							
							 <c:choose>
								<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
									<a class="btn btn-info mr-2 float-right" href="./contentsUpdt<c:out value='${commandMap.queryAll}'/>">수정</a>
								</c:when>
								<c:otherwise>
									<a class="btn btn-info mr-2 float-right disabled">수정</a>
								</c:otherwise>
							</c:choose>
							
		                  	<a class="btn btn-default " href="./contentsList<c:out value="${searchQuery}"  escapeXml="false" />">목록</a>
		                  	                                    
		                </div>
		                <!-- /.card-footer -->
		
		              </div>
		              <!-- /.card-body -->
		            </div>
		            <!-- /.card -->
		          </div>
	        </div>
	</section>
	
	<form:form commandName="templateVO" name="templateVO" action="./contentsSavePost" class="form-horizontal" method="post" enctype="multipart/form-data">
		<input type="hidden" 	name="contentsSeq"    id="contentsSeq" />
		<input type="hidden"    name="mode"           value="delete" />
	</form:form>
</body>

