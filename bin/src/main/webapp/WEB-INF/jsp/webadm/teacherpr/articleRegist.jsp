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
  * @Class Name : articleRegist.jsp
  * @Description : 게시판 게시물  등록 화면
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
<%
	egovframework.com.cmm.LoginVO user = (egovframework.com.cmm.LoginVO) egovframework.com.cmm.util.EgovUserDetailsHelper.getAuthenticatedUser();
%>
<c:set var="adminVO" value="<%=user%>" />
<c:set var="now" value="<%=new java.util.Date()%>" />

<head>
	<style>
		#articleVO label {font-weight:normal;}
		.border-0 th, .border-0 td {border:0 !important;}
	</style>
	
	<script type="text/javascript" src="../../../validator"></script>
	<script type="text/javascript" src="../../../resources/js/EgovMultiFile.js"></script>
	<validator:javascript formName="articleVO" staticJavascript="false" xhtml="true" cdata="false"/>
	<script type="text/javascript">
	
	/* ********************************************************
	 * 저장처리화면
 ******************************************************** */
	var valid = [];
	valid.push({id:"nttSj", msg:"강사PR 제목을 입력해주세요."});
	valid.push({id:"FIX_0001", msg:"강좌주제를 입력해주세요."});
	valid.push({id:"nttCn", msg:"강좌소개를 입력해주세요."});
	valid.push({id:"FIX_0006", msg:"기획제안을 입력해주세요."});
	valid.push({id:"FIX_0007", msg:"준비물를 입력해주세요."});
		
		
	function fn_egov_regist_article() {

		
		var chk = true;
		$.each(valid, function (idx, item) {
			if(chk){
				if(!$('#articleVO').find('#'+item.id).val()){
					alert(item.msg);
					$('#articleVO').find('#'+item.id).focus();
					chk = false ;
				}
			}
		});
		
		if(chk){
			if(!$('#egovComFileList').children().length){
				alert("대표사진을 선택해주세요.");
				chk = false ;
			}
		}
		
		if(chk){
			$('#articleVO').submit();
		}		
		
	}
	

	</script>
</head>


<body>
	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>강사PR <small>
					<c:choose>
						<c:when test="${articleVO.nttId == 0 }">
							등록
						</c:when>
						<c:otherwise>
							수정
						</c:otherwise>
					</c:choose>	    </small>        
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
	
				<form:form commandName="articleVO" name="articleVO" action="./save" class="form-horizontal" method="post"  enctype="multipart/form-data">
					<input type="hidden" name="searchQuery"  value="${searchQuery}"/>
					<input type="hidden" name="bbsTyCode"    value="<c:out value='${boardMasterVO.bbsTyCode}'/>" />
					<input type="hidden" name="replyPosblAt" value="<c:out value='${boardMasterVO.replyPosblAt}'/>" />
					<input type="hidden" name="fileAtchPosblAt"   value="<c:out value='${boardMasterVO.fileAtchPosblAt}'/>" />
					<input type="hidden" id="atchPosblFileNumber" name="atchPosblFileNumber" value="<c:out value='${boardMasterVO.atchPosblFileNumber}'/>" />
					<input type="hidden" name="atchPosblFileSize" value="<c:out value='${boardMasterVO.atchPosblFileSize}'/>" />
					<input type="hidden" name="tmplatId" value="<c:out value='${boardMasterVO.tmplatId}'/>" />
					<input type="hidden" name="blogId"   value="<c:out value='${searchVO.blogId}'/>" />
					<input type="hidden" name="blogAt"   value="<c:out value='${articleVO.blogAt}'/>"/>
					<input type="hidden" name="nttId"    value="<c:out value='${articleVO.nttId}'/>"/>
					
              		<h4>01. 강사정보</h4>
		            <div class="card card-primary card-outline">
		                <div class="card-body ">
		                	<table class="table border-0" style="">
				               	<colgroup>
									<col style="width:12%">
									<col style="width:38%">
									<col style="width:12%">
									<col style="width:38%">
								</colgroup>
								<tbody>
<c:choose>
	<c:when test="${articleVO.nttId >0 }">
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
	</c:when>
	<c:otherwise>
									<tr>
										<th class="text-right">강사명</th>
										<td >
											<input type="text" name="ntcrNm"  id=""ntcrNm"" placeholder="강사명을 수동으로 입력" value="" class="form-control ">									
										</td>
										<th class="text-right">강사WEB ID</th>
										<td >
											<input type="text" name="ntcrId"  id="ntcrId" placeholder="ID를 수동으로 입력" value="" class="form-control ">									
										</td>
									</tr>
	</c:otherwise>
</c:choose>										

								</tbody>
							</table>
						</div>
					</div>
					
              		<h4>02. 등록 정보</h4>
		            <div class="card card-primary card-outline">
		                <div class="card-body ">
		                	<table class="table border-0" style="">
				               	<colgroup>
									<col style="width:12%">
									<col style="width:38%">
									<col style="width:12%">
									<col style="width:38%">
								</colgroup>
								<tbody>
									<tr>
										<th class="text-right">강사PR 제목  <b class="text-red">*</b></th>
										<td colspan="3">
											<input type="text" name="nttSj"  id="nttSj" placeholder="" value="<c:out value="${articleVO.nttSj}" />" class="form-control ">
										</td>
									</tr>
										
							<c:if test="${boardMasterVO.fileAtchPosblAt == 'Y'}">
									<tr>
										<th class="text-right">대표사진</th>
										<td colspan="3">
									<c:import url="/web/common/upload/includeUploadRegist" >
							       		<c:param name="file_length" value="${boardMasterVO.atchPosblFileNumber}" />
							       		<c:param name="file_ext" value="${boardMasterVO.atchPosblFileExt}" />
							       		<c:param name="file_group_id" value="${articleVO.atchFileId}" />
							       		<c:param name="show_image" value="Y" />
						        	</c:import>
										</td>
									</tr>
							</c:if>


									<tr>
										<th class="text-right">강좌주제 <b class="text-red">*</b></th>
										<td colspan="3">
											<input type="text" name="FIX_0001"  id="FIX_0001" placeholder="ex) 바이올린 연주 OR 악세서리 꾸미기 등등" value="<c:out value="${articleVO.item1}" />" class="form-control ">
										</td>
									</tr>
									<tr>
										<th class="text-right">강좌소개 <b class="text-red">*</b></th>
										<td colspan="3">
											<form:textarea path="nttCn" title="${title} ${inputTxt}" class="form-control" style="height:300px"/>
										</td>
									</tr>
																		
									<tr>
										<th class="text-right">핵심키워드 </th>
										<td colspan="3">
								 	 	 	<div class="row">
								              	<div class="col-3">
								              		<input type="text" name="FIX_0002"  id="FIX_0002" placeholder="" value="<c:out value="${articleVO.item2}" />" class="form-control ">
								              	</div>
								              	<div class="col-3">
								              		<input type="text" name="FIX_0003"  id="FIX_0003" placeholder="" value="<c:out value="${articleVO.item3}" />" class="form-control ">
								              	</div>
								              	<div class="col-3">
								              		<input type="text" name="FIX_0004"  id="FIX_0004" placeholder="" value="<c:out value="${articleVO.item4}" />" class="form-control ">
								              	</div>
								              	<div class="col-3">
								              		<input type="text" name="FIX_0005"  id="FIX_0005" placeholder="" value="<c:out value="${articleVO.item5}" />" class="form-control ">
								              	</div>
								            </div>										
											
										</td>
									</tr>
									<tr>
										<th class="text-right">기획제안  <b class="text-red">*</b></th>
										<td colspan="3">
											<textarea name="FIX_0006" id="FIX_0006" placeholder="" maxlength="500" class="form-control" style="height:150px"><c:out value="${articleVO.item6}" /></textarea>
										</td>
									</tr>
																		
									<tr>
										<th class="text-right">준비물   <b class="text-red">*</b></th>
										<td colspan="3">
											<textarea name="FIX_0007" id="FIX_0007" placeholder="" maxlength="500" class="form-control" style="height:150px"><c:out value="${articleVO.item7}" /></textarea>
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
									<tr>
										<th class="text-right">사용여부 <b class="text-red">*</b></th>
										<td colspan="3">
		                   					<label><form:radiobutton path="useAt" value="Y" /> 사용함</label>
						    				&nbsp;&nbsp;&nbsp;
						    				<label><form:radiobutton path="useAt" value="N" /> 사용안함</label>
											<div><form:errors path="useAt" cssClass="error" /></div>
										</td>
									</tr>
								</tbody>
							</table>	

		                </div>
		                
						
		                <div class="card-footer">
		                  <button type="button" onclick="fn_egov_regist_article()" class="btn btn-info float-right">저장</button>
		                  <button type="button" class="btn btn-default " onclick="history.back();">취소</button>
		                </div>
					</div>
				</form:form>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
	          </div>
	        </div>
	</section>
</body>
