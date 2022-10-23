<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	
	<script type="text/javascript" src="../../resources/js/EgovMultiFile.js"></script>
	<script type="text/javascript">
	
	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
		var valid = [];
		valid.push({id:"FIX_0001", msg:"모임장 성명을 입력해주세요."});
		valid.push({id:"FIX_0003", msg:"이메일을 입력해주세요."});
		valid.push({id:"FIX_0004", msg:"이메일을 입력해주세요."});
		valid.push({id:"nttSj", msg:"동아리명을 입력해주세요."});
		valid.push({id:"FIX_0005", msg:"모임주소를 입력해주세요."});
		valid.push({id:"FIX_0006", msg:"지역을 선택해주세요."});
		valid.push({id:"FIX_0007", msg:"모임주기를 입력해주세요."});
		valid.push({id:"FIX_0008", msg:"요일/시간을 입력해주세요."});
		valid.push({id:"FIX_0009", msg:"회원 모집조건을 입력해주세요."});
		valid.push({id:"nttCn", msg:"동아리 소개글을 입력해주세요."});
		
		
	function fn_egov_regist_article() {

		
		var chk = true;
		if(chk){
			if(!$('#articleVO').find('input[name=ntceStat]:checked').val()){
				alert("승인상태를 선택해주세요.");
				chk = false ;
			}
		}
		
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
			if(!$('#articleVO').find('input[name=ctgId]:checked').val()){
				alert("모집여부 를 선택해주세요.");
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
					<input type="hidden" name="useAt"    value="<c:out value='${articleVO.useAt}'/>"/>
					
              		<h4>0 .기본 정보</h4>
		            <div class="card card-primary card-outline">
		                <div class="card-body p-0">
		                	<table class="table table-bordered" style="">
				               	<colgroup>
									<col style="width:12%">
									<col style="width:38%">
									<col style="width:12%">
									<col style="width:38%">
								</colgroup>
								<tbody>
									<tr>
										<th class="text-right">승인상태 <b class="text-red">*</b></th>
										<td colspan="3">
					                    	<label class="text-green"><input type="radio" name="ntceStat" value="1" <c:if test="${articleVO.ntceStat eq '1' }">checked</c:if>> 승인대기&nbsp;&nbsp;&nbsp;</label>	
					                    	<label class="text-blue"><input type="radio" name="ntceStat" value="2" <c:if test="${articleVO.ntceStat eq '2' }">checked</c:if>> 승인&nbsp;&nbsp;&nbsp;</label>	
					                    	<label class="text-red"><input type="radio" name="ntceStat" value="3" <c:if test="${articleVO.ntceStat eq '3' }">checked</c:if>> 반려&nbsp;&nbsp;&nbsp;</label>	
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
		                <div class="card-body p-0">
		                	<table class="table table-bordered" style="">
				               	<colgroup>
									<col style="width:12%">
									<col style="width:38%">
									<col style="width:12%">
									<col style="width:38%">
								</colgroup>
								<tbody>
									<tr>
										<th class="text-right">모임장 성명 <b class="text-red">*</b></th>
										<td colspan="3">
											<input type="text" name="FIX_0001"  id="FIX_0001" placeholder="한글(실명)으로 기입하세요." value="<c:out value="${articleVO.item1}" />" class="form-control ">
										</td>
									</tr>
									<tr>
										<th class="text-right">휴대폰번호 </th>
										<td colspan="3">
											<input type="text" name="FIX_0002"  id="FIX_0002" placeholder="숫자만 입력" value="<c:out value="${articleVO.item2}" />" class="form-control "  oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
										</td>
									</tr>
									<tr>
										<th class="text-right">이메일  <b class="text-red">*</b></th>
										<td colspan="3">
											<div class="input-group">
                      							<input type="text" class="form-control" name="FIX_0003" id="FIX_0003" placeholder="" value="<c:out value="${articleVO.item3}" />">
                      							<div class="input-group-prepend"><span class="input-group-text">@</span></div>
                      							<input type="text" class="form-control" name="FIX_0004" id="FIX_0004" placeholder="" value="<c:out value="${articleVO.item4}" />">
                    						</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					
              		<h4>02. 동아리 정보</h4>
		            <div class="card card-primary card-outline">
		                <div class="card-body p-0">
		                	<table class="table table-bordered" style="">
				               	<colgroup>
									<col style="width:12%">
									<col style="width:38%">
									<col style="width:12%">
									<col style="width:38%">
								</colgroup>
								<tbody>																	
									<tr>
										<th class="text-right">동아리명  <b class="text-red">*</b></th>
										<td colspan="3">
											<input type="text" name="nttSj"  id="nttSj" placeholder="동아리명을 기입하세요.(10글자 내)" value="<c:out value="${articleVO.nttSj}" />" class="form-control ">
										</td>
									</tr>
									<tr>
										<th class="text-right">모임주소 입력 <b class="text-red">*</b></th>
										<td colspan="3">
											<input type="text" name="FIX_0005"  id="FIX_0005" placeholder="모임의 실제 주소를 입력해주세요." value="<c:out value="${articleVO.item5}" />" class="form-control ">
										</td>
									</tr>									
									<tr>
										<th class="text-right">지역 <b class="text-red">*</b></th>
										<td colspan="3">
											<select name="FIX_0006" id="FIX_0006" class="form-control " style="display:inline-block; width:auto;">
												<option value="">지역선택</option>
									<c:if test="${!empty areaList}">
										<c:forEach items="${areaList}" var="item" varStatus="status">
												
												<option value="<c:out value="${item.areaCd }"/>" <c:if test="${item.areaCd eq articleVO.item6 }">selected</c:if> ><c:out value="${item.areaName }"/></option>
										</c:forEach>
									</c:if>
											</select>
										</td>
									</tr>
									<tr>
										<th class="text-right">모임주기(월) <b class="text-red">*</b></th>
										<td colspan="3">
											<input type="text" name="FIX_0007"  id="FIX_0007" placeholder="1달에 예상 몇번 (1~30회)까지 하는지 기입하세요!" value="<c:out value="${articleVO.item7}" />" class="form-control ">
										</td>
									</tr>
									<tr>
										<th class="text-right">요일/시간 <b class="text-red">*</b></th>
										<td colspan="3">
											<input type="text" name="FIX_0008"  id="FIX_0008" placeholder="자유기재 EX)매주 (화,수,목) 오후 2시~3시 모임" value="<c:out value="${articleVO.item8}" />" class="form-control ">
										</td>
									</tr>
									<tr>
										<th class="text-right">회원 모집조건 <b class="text-red">*</b></th>
										<td colspan="3">
											<input type="text" name="FIX_0009"  id="FIX_0009" placeholder="자유롭게 기재 가능 (EX : 남자 30세 이상, 배드민턴 구력 1년이상 등등)" value="<c:out value="${articleVO.item9}" />" class="form-control ">
										</td>
									</tr>
									<tr>
										<th class="text-right">동아리 소개글 <b class="text-red">*</b></th>
										<td colspan="3">
											<form:textarea path="nttCn" title="${title} ${inputTxt}" class="form-control" style="height:300px"/>
										</td>
									</tr>
							<c:if test="${boardMasterVO.fileAtchPosblAt == 'Y'}">
									<tr>
										<th class="text-right">동아리 대표사진</th>
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
										<th class="text-right">동아리 홈페이지 <b class="text-red">*</b></th>
										<td colspan="3">
											<input type="text" name="FIX_0010"  id="FIX_0010" placeholder="홈페이지 존재할 경우, 링크가능한 URL을 넣어주세요" value="<c:out value="${articleVO.item10}" />" class="form-control ">
										</td>
									</tr>
									
									<tr>
										<th class="text-right">모집여부 <b class="text-red">*</b></th>
										<td colspan="3">
											<c:forEach items="${ctgList}" var="item" varStatus="status">
											<label><input type="radio" name="ctgId" value="${item.ctgId}" <c:if test="${articleVO.ctgId eq item.ctgId }">checked</c:if>> <c:out value="${item.ctgNm}"/>&nbsp;&nbsp;&nbsp;</label>
											</c:forEach>
										</td>
									</tr>
<% /*
	<c:if test="${articleVO.nttId >0 }">
									<tr>
										<th class="text-right">사용여부 <b class="text-red">*</b></th>
										<td colspan="3">
		                   					<label><form:radiobutton path="useAt" value="Y" /> 사용함</label>
						    				&nbsp;&nbsp;&nbsp;
						    				<label><form:radiobutton path="useAt" value="N" /> 사용안함</label>
											<div><form:errors path="useAt" cssClass="error" /></div>
										</td>
									</tr>
	</c:if>
*/ %>
									
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
