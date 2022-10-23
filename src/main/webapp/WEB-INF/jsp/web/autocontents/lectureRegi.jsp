<%@ page language="java" import="egovframework.com.cmm.service.EgovProperties" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
 /**
  * @Class Name : lectureRegi.jsp
  * @Description : 강좌 등록
  * @Modification Information
  * @
  * @  수정일         수정자      수정내용
  * @ ------------   --------    ---------------------------
  * @ 2021.03.18      전영석      최초 생성
  *
  *  @author 전영석
  *  @since 2021.03.18
  *  @version 1.0
  *  @see
  * 
  */
%>
 
<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>
 
<%@ include file="../../include/top.jsp"%>
<script src="<%=strContext%>/js/common_nsm_human.js"></script>
<script>

	function fn_init() {
		
		$("input.calendar").datepicker({
	    	autoclose: true,
	        calendarWeeks: false,
	        todayHighlight: true,
	        language: "kr",
	    	format: "yyyy-mm-dd"
	    });
	
	}

	$(document).ready(function() {
		fn_init();
	});
	
</script>


<div class="sub_cont" id="container">
	<div class="inner">
		<h3 class="tit"><c:out value="${SELECTED_MENU_OBJ.menuNm}"/></h3>
		<div class="table2 responsive-type mb50">
		
			<form:form commandName="templateVO" name="templateVO" action="./Save" method="post" enctype="multipart/form-data">
			
					<input name="memNo"     type="hidden"  value="<c:out value='${userInfo.uniqId}' />" />
					<input name="memNm"     type="hidden"  value="<c:out value='${userInfo.name}' />" />
					<input name="id"        type="hidden"  value="<c:out value='${userInfo.id}' />" />
					<input name="birthDate" type="hidden"  value="<c:out value='${userInfo.birthDate}' />" />
					<input name="hp"        type="hidden"  value="<c:out value='${userInfo.ihidNum}' />" />
					<input name="homeZipno" type="hidden"  value="<c:out value='${userInfo.homeZipNo}' />" />
					<input name="homeAddr1" type="hidden"  value="<c:out value='${userInfo.homeAddr1}' />" />
					<input name="homeAddr2" type="hidden"  value="<c:out value='${userInfo.homeAddr2}' />" />
					<input name="email"     type="hidden"  value="<c:out value='${userInfo.email}' />" />
			
					<input name="mode"      type="hidden"  value="" />
			
					<table>
						<caption>강사 기본 정보</caption>
						<colgroup>
							<col style="width:11.5%">
							<col style="width:38.5%">
							<col style="width:11.5%">
							<col style="width:38.5%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="vtop">강사명</th>
								<td class="b">
									<c:out value="${userInfo.name}" />
								</td>
								<th scope="row" class="vtop">생년월일</th>
								<td class="b">
									<c:out value="${userInfo.birthDate}" />
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">전화번호</th>
								<td class="b">
									
								</td>
								<th scope="row" class="vtop">휴대전화번호</th>
								<td class="b">
									<c:out value="${userInfo.ihidNum}" />
								</td>
							</tr>							
							<tr>
								<th scope="row" class="vtop">집주소</th>
									<c:out value="${userInfo.homeAddr1}" /> <c:out value="${userInfo.homeAddr2}" />
								<td class="b"></td>
								<th scope="row" class="vtop">E-Mail</th>
								<td class="b">
									<c:out value="${userInfo.email}" />
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">
									대상
								</th>
								<td class="b">
			                    	<select name="lecTargetinfo" class="form-control " style="width:200px;float:left">
			                    		
			                    		<c:forEach items="${mathAgeGrpCdList}" var="item" varStatus="status">
			                    			<option value="<c:out value='${item.cd}'/>"><c:out value='${item.cdNm}'/></option>	
			                    		</c:forEach>
			                    		
			                    	</select>	
								</td>
								<th scope="row" class="vtop">최대 모집 인원</th>
								<td class="b">
			                    	<select name="lecMaxcnt" class="form-control " style="width:200px;float:left">
			                    		
			                    		<c:forEach items="${mathMaxCntGrpCdList}" var="item" varStatus="status">
			                    			<option value="<c:out value='${item.cd}'/>"><c:out value='${item.cdNm}'/></option>	
			                    		</c:forEach>
			                    		
			                    	</select>									
								</td>
							</tr>							
							<tr>
								<th scope="row" class="vtop">강좌 제목</th>
								<td class="b" colspan="3">
									<input name="lecSubject" title="강좌 제목" style="width:500px;float:left" type="text" value="" maxlength="255" autocomplete="off">
								</td>
							</tr>							
							<tr>
								<th scope="row" class="vtop">강좌 분류</th>
								<td class="b" colspan="3"></td>
							</tr>							
							<tr>
								<th scope="row" class="vtop">강좌 사진 등록</th>
								<td class="b" colspan="3">
									
									<input type="text" id="RegiUFile01" name="webadmInsert01File" placeholder="강좌 사진 등록" value="" style="width:70%;">
			                    	<button type="button" class="btn_ty_m_c1" myGroupId="01" onclick="fnFileRegiSel(this); return false;"> 강좌 사진 등록</button>	
			                    	<input type="file" id="regiHFile01" name="regiFile01" myGroupId="01" accept='image/jpeg,image/gif,image/png' onChange="fnRegiHFileChange(this);" style="display:none;"/>									
									
								</td>
							</tr>							
							<tr>
								<th scope="row" class="vtop">강좌 소개</th>
								<td class="b">
									<textarea name="lecIntrcn" title="강좌 소개" class="form-control " rows="4" cols="100" style="width:700px;float:left" type="text"></textarea>
								</td>
								<th scope="row" class="vtop"></th>
								<td class="b"></td>
							</tr>							
							<tr>
								<th scope="row" class="vtop">
									강의 계획
									<button type="button" class="btn_ty_m_c1" onclick="fnFileRegiSel(this); return false;" style="min-width:90px; height:70px;">추가</button>
								</th>
								<td class="b" colspan="3">
									
										
									<table>
										<caption>강의 계획</caption>
										<colgroup>
											<col style="width:10%">
											<col style="width:16%">
											<col style="width:24%">
											<col style="width:25%">
											<col style="width:25%">
										</colgroup>
										<tbody>
											<tr>
												<th class="b">회차</th>
												<th class="b" colspan="2" style="text-align:center;">일시</th>
												<th class="b">주제</th>
												<th class="b">내용</th>
											</tr>
											<tr>
												<td class="b">
													<input name="lecTimeNm" title="1회차 제목" style="width:90px;float:left" type="text" value="" maxlength="10" autocomplete="off">
												</td>
												<td class="b">
							                      	<input name="lecStdDate" title="1회차 시작일자" style="width:150px;float:left" class="form-control calendar" type="text" value="" maxlength="10" autocomplete="off">
												</td>
												<td class="b">
							                      	<input name="lecStdTime" title="1회차 시작시간" style="width:100px;float:left" type="text" value="" maxlength="10" autocomplete="off">
							                      	<input name="lecEndTime" title="1회차 종료시간" style="width:100px;float:left" type="text" value="" maxlength="10" autocomplete="off">												
												</td>																								
												<td>
													<input name="lecTimeSubject" title="주제" style="width:260px;float:left" type="text" value="" maxlength="10" autocomplete="off">
												</td>
												<td>
													<input name="lecTimeCn" title="내용" style="width:350px;float:left" type="text" value="" maxlength="10" autocomplete="off">
												</td>
											</tr>											
											<tr>
												<td class="b">
													<input name="lecTimeNm" title="2회차 제목" style="width:90px;float:left" type="text" value="" maxlength="10" autocomplete="off">
												</td>
												<td class="b">
							                      	<input name="lecStdDate" title="2회차 시작일자" style="width:150px;float:left" class="form-control calendar" type="text" value="" maxlength="10" autocomplete="off">
												</td>
												<td class="b">
							                      	<input name="lecStdTime" title="2회차 시작시간" style="width:100px;float:left" type="text" value="" maxlength="10" autocomplete="off">
							                      	<input name="lecEndTime" title="2회차 종료시간" style="width:100px;float:left" type="text" value="" maxlength="10" autocomplete="off">
												</td>																								
												<td>
													<input name="lecTimeSubject" title="주제" style="width:260px;float:left" type="text" value="" maxlength="10" autocomplete="off">
												</td>
												<td>
													<input name="lecTimeCn" title="내용" style="width:350px;float:left" type="text" value="" maxlength="10" autocomplete="off">
												</td>
											</tr>											
										</tbody>
									</table>									
									
									
								</td>
								<th scope="row" class="vtop"></th>
								<td class="b"></td>
							</tr>							
							<tr>
								<th scope="row" class="vtop">추가 비용 및 유의 사항</th>
								<td class="b" colspan="3">
									<textarea name="lecEtc" title="강좌 소개" class="form-control " rows="4" cols="110" style="width:800px;float:left" type="text" placeholder="강좌에서 발생할 수 있는 추가 비용 및 수강생에게 전달하고 싶은 유의사항을 작성해 주세요."></textarea>
								</td>
							</tr>							
						</tbody>
					</table>
						
			</form:form>
			
		</div>
		
		<ul class="btn_area">
			<li><a href="javascript:history.back()" class="btn_ty_m_c4">취소</a></li>
			<li><a href="#" onclick="fn_lecture_regist(); return false;" class="btn_ty_m_c1">등록</a></li>
		</ul>
		<!-- //btn_area -->
	</div>
	
</div>

<script>

	function fn_lecture_regist() {
		
		console.log("call fn_lecture_regist();");
		
		var varFrom = document.templateVO;
		
		var varMode = $($("input[name='mode']")).val();
		
		if ((varMode === undefined) || (varMode == null) || (varMode == '')) {
			varMode = "insert";	
			$($("input[name='mode']")).val("insert");
		}
		
		console.log(varMode);
		
		var varLecSubject = $($("input[name='lecSubject']")).val();
		if ((varLecSubject === undefined) || (varLecSubject == null) || (varLecSubject == '')) {
			alert("강좌 제목 정보는 비울 수 없습니다.");
			$("input[name='lecSubject']").focus();
			return;
		}			
		
		var varLecIntrcn = $($("textarea[name='lecIntrcn']")).val();
		if ((varLecIntrcn === undefined) || (varLecIntrcn == null) || (varLecIntrcn == '')) {
			alert("강좌 소개 정보는 비울 수 없습니다.");
			$("textarea[name='lecIntrcn']").focus();
			return;
		}			
		
		var msg = "<spring:message code='common.regist.msg' />";
		if (varFrom.mode.value == "update") {
			msg = "수정하시겠습니까?";
		}
		
		if (confirm(msg)) {
			varFrom.submit();
		}		
		
	}
	
	function fnFileRegiSel(thisObj) {
		
		console.log("call fnFileRegiSel()");
		
		var varMyGroupId = $(thisObj).attr("myGroupId");
		
		console.log(varMyGroupId);
		
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

	}	
	
</script>