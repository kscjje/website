<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="now" value="<%=new java.util.Date()%>" />


<head>
	<script type="text/javascript" src="<c:url value="/webadm/resources/js/EgovMultiFile.js"/>"></script>

	<script>

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
	valid.push({id:"ctgId", msg:"모집여부를 선택해주세요."});

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
	<div class="sub_visual <c:out value="${SELECTED_MENU_OBJ.relateImageNm}"/>">
				<div class="inner">
					<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>

			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<!-- tab_link -->
				<% /*@ include file="/WEB-INF/templates/web/base/tab_menu.jsp" */ %>

				<div class="sub_top type02">
					<h3>학습동아리 개설신청</h3>
				</div>

			<form:form commandName="articleVO" name="articleVO" action="./save" method="post" enctype="multipart/form-data" >
				<input type="hidden" name="searchQuery" value="${searchQuery }"/>
				<form:hidden path="nttId"/>
				<form:hidden path="ntcrId"/>
				<form:hidden path="ntcrNm"/>
				<form:hidden path="ctgId"/>


				<!-- 강사신청 table 클래스 : t_table 추가 -->
				<div class="table t_table">
					<div class="title_box">
						<p class="t_title">01. 모임장 정보</p>
						<span><em class="star">*</em>필수값으로, 모두 기재하세요. </span>
					</div>
					<table>
						<caption>모임장 정보입력란입니다. -강좌제목명, 대표사진, 연령제한, 교육연령, 모집대상 표기, 접수방식 , 정원(수), 온라인 현장정원수</caption>
						<colgroup>
							<col style="width:14.5%">
							<col style="width:85.5%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><span>모임장 성명 <em class="star">*</em></span></th>
								<td>
									<div class="inputbox">
										<label for="FIX_0001" class="hidden">모임장 성명</label>
			<c:choose>
				<c:when test="${articleVO.nttId>0}">
										<input type="text" name="FIX_0001"  id="FIX_0001" title="모임장 성명 입력" placeholder="한글(실명)으로 기입하세요." value="<c:out value="${articleVO.ntcrNm}" />">
				</c:when>
				<c:otherwise>
										<input type="text" name="FIX_0001"  id="FIX_0001" title="모임장 성명 입력" placeholder="한글(실명)으로 기입하세요." value="<c:out value="${articleVO.item1}" />">
				</c:otherwise>
			</c:choose>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">휴대폰번호 </th>
								<td>
									<div class="inputbox">
										<label for="FIX_0002" class="hidden">휴대폰번호</label>
										<input type="text" name="FIX_0002" id="FIX_0002" title="휴대폰번호 입력" placeholder="(-)없이 숫자로 기입하세요." value="<c:out value="${articleVO.item2}" />" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><span>이메일 <em class="star">*</em></span></th>
								<td>
									<div class="w_box email">
										<div class="inputbox">
											<label for="FIX_0003" class="hidden">이메일</label>
											<input type="text" name="FIX_0003" id="FIX_0003" placeholder="" value="<c:out value="${articleVO.item3}" />">
										</div>
										<span>@</span>
										<div class="inputbox">
											<label for="FIX_0004" class="hidden">이메일</label>
											<input type="text" name="FIX_0004" id="FIX_0004" placeholder="" value="<c:out value="${articleVO.item4}" />">
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--//table  -->
				<!-- 강사신청 table 클래스 : t_table 추가 -->
				<div class="table t_table t_flex">
					<div class="title_box">
						<p class="t_title">02. 동아리 정보</p>
					</div>
					<table>
						<caption>동아리 입력란입니다. -동아리명, 모임주소, 지역선택, 모임주기(월), 요일/시간, 회원 모집조건, 동아리 소개글, 동아리 대표사진, 동아리 홈페이지 , 모집여부</caption>
						<colgroup>
							<col style="width:14.5%">
							<col style="width:85.5%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row"><span>동아리명 <em class="star">*</em></span></th>
								<td>
									<div class="inputbox">
										<label for="nttSj" class="hidden">동아리명 입력</label>
										<input type="text" name="nttSj" id="nttSj" placeholder="동아리명을 기입하세요. (10글자 내)." value="<c:out value="${articleVO.nttSj}" />" maxlength="10">
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><span>모임주소 입력 <em class="star">*</em></span></th>
								<td>
									<div class="inputbox w100">
										<label for="FIX_0005" class="hidden">모임주소 입력</label>
										<input type="text" name="FIX_0005" id="FIX_0005" placeholder="모임의 실제주소를 입력해주세요." value="<c:out value="${articleVO.item5}" />">
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><span>지역<em class="star">*</em></span></th>
								<td>
									<div class="w_box">
										<select name="FIX_0006" id="FIX_0006">
											<option value="">지역 선택</option>
								<c:if test="${!empty areaList}">
									<c:forEach items="${areaList}" var="item" varStatus="status">

											<option value="<c:out value="${item.areaCd }"/>" <c:if test="${item.areaCd eq articleVO.item6 }">selected</c:if> ><c:out value="${item.areaName }"/></option>
									</c:forEach>
								</c:if>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><span>모임주기(월)<em class="star">*</em></span></th>
								<td class="flex_direction">
									<div class="w_box ">
										<div class="inputbox">
											<label for="FIX_0007" class="hidden"></label>
											<input type="text" name="FIX_0007" id="FIX_0007" title="모임주기 입력" placeholder="(1~30)까지 숫자로 기입가능 " value="<c:out value="${articleVO.item7}" />" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
										</div>
										<span class="txt01">회</span>
									</div>
									<p class="ex_p">* 1달에 예상 몇 번(1~30회)까지 하는지 기입하세요!
									</p>
								</td>
							</tr>
							<tr>
								<th scope="row"><span>요일/시간<em class="star">*</em></span></th>
								<td>
									<div class="inputbox">
										<label for="FIX_0008" class="hidden">요일/시간 입력</label>
										<input type="text" name="FIX_0008" id="FIX_0008" placeholder="자유기재 EX) 매주 (화,수,목) 오후 2시~3시모임" value="<c:out value="${articleVO.item8}" />" maxlength="50">
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><span>회원 모집조건<em class="star">*</em></span></th>
								<td>
									<div class="inputbox">
										<label for="FIX_0009" class="hidden">회원 모집조건 입력</label>
										<input type="text" name="FIX_0009" id="FIX_0009" placeholder="자유롭게 기재 가능 (EX : 남자 30세 이상, 배드민턴 구력 1년이상 등등)" value="<c:out value="${articleVO.item9}" />" maxlength="50">
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row" class="vtop"><span>동아리 소개글<em class="star">*</em></span></th>
								<td>
									<textarea name="nttCn" id="nttCn" placeholder="" maxlength="500"><c:out value="${articleVO.nttCn}" /></textarea>
								</td>
							</tr>
							<c:if test="${boardMasterVO.fileAtchPosblAt == 'Y'}">
							<tr>
								<th scope="row">동아리 대표사진</th>
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
							</c:if>
							<tr>
								<th scope="row">동아리 홈페이지</th>
								<td>
									<div class="inputbox">
										<label for="FIX_0010" class="hidden">회원 모집조건 입력</label>
										<input type="text" name="FIX_0010" id="FIX_0010" placeholder="홈페이지 존재할 경우, 링크가능한 URL을 넣어주세요" value="<c:out value="${articleVO.item10}" />">
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><span>모집여부<em class="star">*</em></span></th>
								<td class="td_column btn-ctg">
								<c:if test="${!empty ctgList}">
									<c:forEach items="${ctgList}" var="item" varStatus="status">
												<c:choose>
													<c:when test="${articleVO.ctgId eq item.ctgId}">
											<button type="button" class="btn_s2_c2" data-id="<c:out value="${item.ctgId}"/>" onclick="fn_select_ctg('<c:out value="${item.ctgId}"/>')"><c:out value="${item.ctgNm}"/></button>
													</c:when>
													<c:otherwise>
											<button type="button" class="btn_s2_c3" data-id="<c:out value="${item.ctgId}"/>" onclick="fn_select_ctg('<c:out value="${item.ctgId}"/>')"><c:out value="${item.ctgNm}"/></button>
													</c:otherwise>
												</c:choose>

											<c:if test="${articleVO.nttId<1}">
												<c:if test="${item.ctgNm eq '모집중'}">
											<script>
											fn_select_ctg('<c:out value="${item.ctgId}"/>');
											</script>
												</c:if>
											</c:if>
									</c:forEach>
								</c:if>
									<p class="ex_p">* 모집을 원치 않을 시, [마이페이지 &gt; 학습동아리 현황]에서 모집여부 “모집종료”으로 변경이 가능합니다.
									</p>
								</td>
							</tr>
						</tbody>
					</table>
					<p class="ex_p">* 동아리 개설 현황은 [마이페이지 &gt; 학습동아리 현황]에서 확인가능합니다.</p>
				</div>

			</form:form>
				<!--// table  -->
				<div class="btn_area">
						<a href="javascript:history.back()" class="btn_s1_c0">취소</a>
			<c:choose>
				<c:when test="${articleVO.nttId>0}">
						<a href="javascript:fn_egov_regist_article()" class="btn_s1_c1">수정완료</a>
				</c:when>
				<c:otherwise>
						<a href="javascript:fn_egov_regist_article()" class="btn_s1_c1">개설신청</a>
				</c:otherwise>
			</c:choose>
				</div>
				<!--// btn_area  -->
			</div>
</body>