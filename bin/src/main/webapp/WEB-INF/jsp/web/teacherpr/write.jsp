<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="now" value="<%=new java.util.Date()%>" />


<head>
	<script type="text/javascript" src="<c:url value="/webadm/resources/js/EgovMultiFile.js"/>"></script>
	
	<script>
	
	var valid = [];
	valid.push({id:"nttSj", msg:"강사PR 제목을 입력해주세요."});
	valid.push({id:"fileText", msg:"대표사진을 입력해주세요."});
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
			$('#articleVO').submit();
		}
		 
	}
	
	function fn_select_ctg(ctgId){
		
 		$.each($(".btn-ctg").find("button"), function (idx, item) {
			if($(item).data("id")==ctgId){
				$(item).removeClass("btn_s2_c3").addClass('btn_s2_c2');
			} else{
				$(item).removeClass("btn_s2_c2").addClass('btn_s2_c3');
			}
		})
		
		$('#articleVO').find('#ctgId').val(ctgId);
	}
	</script>	
</head>


<body>
	<div class="sub_visual">
				<div class="inner">
					<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>

			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<!-- tab_link -->
				<% /* @ include file="/WEB-INF/templates/web/base/tab_menu.jsp" */ %>
				
				<div class="search_sel s_column">
					<p class="mr_10">강사은행 등록완료하신 강사만 작성가능</p>
					<a href="<c:url value="${webDir}/teacher/poolRegist"/>" class="btn_s2_c2">강사등록 신청 바로가기</a>
				</div>
				<% /* 
				<div class="sub_top type01">
					<h3>강사 PR 작성</h3>
				</div>
				*/ %>

			<form:form commandName="articleVO" name="articleVO" action="./save" method="post" enctype="multipart/form-data" >
				<input type="hidden" name="searchQuery" value="${searchQuery }"/>
				<form:hidden path="nttId"/>
				<form:hidden path="ntcrId"/>
				<form:hidden path="ntcrNm"/>
				<form:hidden path="ctgId"/>
				<input type="hidden" name="FIX_0008" id="FIX_0008" value="<c:out value="${memNo }" />" >
				
				
				<div class="table t_table">
					<table>
						<caption>강좌정보 표입니다. -강좌과목, 강좌설명, 강의계획서, 강좌대표사진, 자격증/이력사항</caption>
						<colgroup>
							<col style="width:14.5%">
							<col style="width:85.5%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><span>강사PR 제목<em class="star">*</em></span></th>
								<td>
									<div class="inputbox">
										<label for="nttSj" class="hidden"></label>
										<input type="text" name="nttSj" id="nttSj" title="강사PR 제목 입력" placeholder="" value="<c:out value="${articleVO.nttSj}" />">
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop"><span>대표사진 <em class="star">*</em></span></th>
								<td>
						        	<c:import url="${webDir}/common/upload/includeUploadDongari" >
							       		<c:param name="file_length" value="${boardMasterVO.atchPosblFileNumber}" />
							       		<c:param name="file_ext" value="${boardMasterVO.atchPosblFileExt}" />
							       		<c:param name="file_group_id" value="${articleVO.atchFileId}" />
							       		<c:param name="show_image" value="Y" />
						        	</c:import>	
									<p class="ex_p">※ 강사PR의 대표사진은 400X300픽셀을 기준으로 합니다.</p>
								</td>
							</tr>
							<tr>
								<th scope="row"><span>강좌주제<em class="star">*</em></span></th>
							 	<td>
									<div class="inputbox">
										<label for="FIX_0001" class="hidden"></label>
										<input type="text" name="FIX_0001" id="FIX_0001" placeholder="ex) 바이올린 연주 OR 악세서리 꾸미기 등등" value="<c:out value="${articleVO.item1}" />" maxlength="50">
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><span>강좌소개<em class="star">*</em></span></th>
								<td>
									<label for="nttCn" class="hidden"></label>
									<textarea name="nttCn" id="nttCn" placeholder=""  maxlength="500"><c:out value="${articleVO.nttCn}" /></textarea>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop">핵심키워드 <p>(최대4개)</p></th>
								<td>
									<!-- input 4개 일때 클래스 : f_area 추가 -->
									<div class="w_box f_area">
										<div>
										<div class="inputbox">
											<label for="FIX_0002" class="hidden"></label>
											<input type="text" name="FIX_0002" id="FIX_0002" title="핵심키워드 입력" placeholder="" value="<c:out value="${articleVO.item2}" />" maxlength="10">
										</div>
										<div class="inputbox">
											<label for="FIX_0003" class="hidden"></label>
											<input type="text" name="FIX_0003" id="FIX_0003" title="핵심키워드 입력" placeholder="" value="<c:out value="${articleVO.item3}" />" maxlength="10">
										</div>
									</div>
									<div>
										<div class="inputbox">
											<label for="FIX_0004" class="hidden"></label>
											<input type="text" name="FIX_0004" id="FIX_0004" title="핵심키워드 입력" placeholder="" value="<c:out value="${articleVO.item4}" />" maxlength="10">
										</div>
										<div class="inputbox">
											<label for="FIX_0005" class="hidden"></label>
											<input type="text" name="FIX_0005" id="FIX_0005" title="핵심키워드 입력" placeholder="" value="<c:out value="${articleVO.item5}" />" maxlength="10">
										</div>
									</div>
									
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop"><span>기획제안 <em class="star">*</em></span></th>
								<td>
									<label for="FIX_0006" class="hidden"></label>
									<textarea name="FIX_0006" id="FIX_0006" class="h2" placeholder="" maxlength="500"><c:out value="${articleVO.item6}" /></textarea>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop"><span>준비물 <em class="star">*</em></span></th>
								<td>
									<label for="FIX_0007" class="hidden"></label>
									<textarea name="FIX_0007" id="FIX_0007"  class="h2" placeholder="" maxlength="500"><c:out value="${articleVO.item7}" /></textarea>
								</td>
							</tr>
				<% /*
							<tr>
								<th scope="row" class="vtop">연관 강사코드<br>(프로필연동)</th>
								<td>
									<div class="inputbox">
										<label for="FIX_0008" class="hidden"></label>
										<input type="text" name="FIX_0008" id="FIX_0008" placeholder="ex) NW01352" value="<c:out value="${articleVO.item8}" />" maxlength="50">
									</div>
								</td>
							</tr>
				*/ %>
						</tbody>
					</table>
					<% /*
					<p class="ex_p">※ 강사프로필의 강사코드를 알아야 합니다. EX) 강좌코드 : NW01352 강좌코드로 등록시, 바로 강사PR에서 프로필 연동이 가능합니다.
					</p>
					*/ %>
				</div>
				

				
			</form:form>
				<!--// table  -->
				<div class="btn_area">
						<a href="javascript:history.back()" class="btn_s1_c0">취소</a>
			<c:choose>
				<c:when test="${articleVO.nttId>0}">
						<a href="javascript:fn_egov_regist_article()" class="btn_s1_c1">강사PR수정 완료</a>
				</c:when>
				<c:otherwise>
						<a href="javascript:fn_egov_regist_article()" class="btn_s1_c1">강사PR등록 완료</a>
				</c:otherwise>
			</c:choose>										
				</div>
				<!--// btn_area  -->
			</div>
</body>