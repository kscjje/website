<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : orgInfoRegist.jsp
  * @Description : 기관 정보 관리JSP
  * @Modification Information
  * @
  * @  수정일              수정자            수정내용
  * @ -------       ------    ---------------------------
  * @ 2021.10.26    진수진           최초 생성
  *
  *  @author 진수진
  *  @since 2021.10.26
  *  @version 1.0
  *  @see
  *
  */
%>
<script>
	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	 function fn_save() {
		var form = document.orgInfoVO;

	}

	$(document).ready(function(){
		$.ajax({
		      url: '../code/SM_ORG_MTYPE/list.json',
		      type : "GET",
		      cache : false,
		      success: function(data) {
		    	  //목록을 담는다
		    	  orgTypeList = data.list;
		    	  fn_print_orgtype('${orgInfoVO.orgLtype}' , '${orgInfoVO.orgMtype}');
		      },
		      error : function(res){
		    	  __ajaxErrorCallback__(res, "기관유형 가져오기 오류\n" + res.responseText);
		      }

		});

		$('#orgLtype').change(function(){
			fn_print_orgtype($(this).val() , '${orgInfoVO.orgMtype}');
		});


	});

</script>
<form:form commandName="orgInfoVO" name="orgInfoVO" action="./orgInfoRegistSave" class="form-horizontal" method="post">
<form:hidden path="comcd" />
<form:hidden path="orgNo" />
                	<div class="form-group row">
                    <label for="orgNm" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 기관명</label>
                    <div class="col-sm-10">
                    	<form:input path="orgNm" title="기관명" class="form-control " maxlength="50" />
   						<div><form:errors path="orgNm" cssClass="error" /></div>
                    </div>
                  </div>
                  <!--
                  <div class="form-group row">
                    <label for="areaCd" class="col-sm-2 col-form-label">상위기관 선택</label>
                    <div class="col-sm-10">
                    	<form:select path="parentOrgNo" class="form-control">
                    	 	<option value="0">:::: 없음 ::::</option>
<c:forEach items="${upOrgList}" var="item" varStatus="status">
                    	 	<c:if test="${orgInfoVO.orgNo ne item.orgNo}"><form:option value="${item.orgNo }" label="${item.orgNm }" /></c:if>
</c:forEach>
                    	 </form:select>
   						<div><form:errors path="areaCd" cssClass="error" /></div>
                    </div>
                  </div>
                   -->
                  <div class="form-group row">
                    <label for="areaCd" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 지역</label>
                    <div class="col-sm-10">
                    	<form:select path="areaCd" class="form-control">
                    	 	<option value="">:::: 선택 ::::</option>

<c:forEach items="${areaList}" var="item" varStatus="status">
                    	 	<form:option value="${item.areaCd }" label="${item.areaNm }" />
     <c:forEach items="${item.subAreaList}" var="item2" varStatus="status">
     						<form:option value="${item2.areaCd }" label="${item.areaNm } > ${item2.areaNm }" />
     </c:forEach>
</c:forEach>
                    	 </form:select>
   						<div><form:errors path="areaCd" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgKind" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 기관분류</label>
                    <div class="col-sm-10">
                    	<form:select path="orgKind" class="form-control">
                    		<form:option value="1001" label="산하기관" />
                    		<form:option value="2001" label="유관기관" />
                    	</form:select>
   						<div><form:errors path="orgKind" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgLtype" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 기관유형(대)</label>
                    <div class="col-sm-10">
                    	<form:select path="orgLtype" class="form-control">
                    		<form:options items="${typeList}" class="form-control" itemLabel="cdNm" itemValue="cd" />
                    	</form:select>
   						<div><form:errors path="orgLtype" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgMtype" class="col-sm-2 col-form-label">기관유형(중)</label>
                    <div class="col-sm-10">
                    	<form:select path="orgMtype" class="form-control">
                    	</form:select>
                    	<div>※ 유관기관만 적용하세요</div>
   						<div><form:errors path="orgMtype" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgTel" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 대표전화</label>
                    <div class="col-sm-10">
                    	<form:input path="orgTel" title="대표전화" class="form-control " maxlength="14" />
   						<div><form:errors path="orgTel" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgNm" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 주소</label>
                    <div class="col-sm-10">
                    	<form:input path="addr" title="주소" class="form-control " maxlength="250" />
   						<div><form:errors path="addr" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="email" class="col-sm-2 col-form-label">E-MAIL</label>
                    <div class="col-sm-10">
                    	<form:input path="email" title="이메일" class="form-control " maxlength="50" />
   						<div><form:errors path="email" cssClass="error" /></div>
                    </div>
                  </div>
                   <div class="form-group row">
                    <label for="fax" class="col-sm-2 col-form-label">팩스번호</label>
                    <div class="col-sm-10">
                    	<form:input path="fax" title="팩스번호" class="form-control " maxlength="14" />
   						<div><form:errors path="fax" cssClass="error" /></div>
                    </div>
                  </div>

                  <div class="form-group row">
                    <label for="orgNm" class="col-sm-2 col-form-label">대표자명</label>
                    <div class="col-sm-10">
                    	<form:input path="bossNm" title="대표자명" class="form-control " maxlength="15" />
   						<div><form:errors path="bossNm" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgNm" class="col-sm-2 col-form-label">사업자번호</label>
                    <div class="col-sm-10">
                    	<form:input path="bizNo" title="사업자번호" class="form-control " maxlength="20" />
   						<div><form:errors path="bizNo" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="uptae" class="col-sm-2 col-form-label">업태</label>
                    <div class="col-sm-10">
                    	<form:input path="uptae" title="업태" class="form-control " maxlength="20" />
   						<div><form:errors path="uptae" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="jongmok" class="col-sm-2 col-form-label">종목</label>
                    <div class="col-sm-10">
                    	<form:input path="jongmok" title="종목" class="form-control " maxlength="20" />
   						<div><form:errors path="jongmok" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="jongmok" class="col-sm-2 col-form-label">홈페이지 URL</label>
                    <div class="col-sm-10">
                    	<form:input path="orgUrl" title="홈페이지 URL" class="form-control " maxlength="200" />
   						<div><form:errors path="orgUrl" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgNm" class="col-sm-2 col-form-label">담당자명</label>
                    <div class="col-sm-10">
                    	<form:input path="charger" title="담당자명" class="form-control " maxlength="20" />
   						<div><form:errors path="charger" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgNm" class="col-sm-2 col-form-label"> 담당자전화</label>
                    <div class="col-sm-10">
                    	<form:input path="chargertel" title="담당자전화" class="form-control " maxlength="14" />
   						<div><form:errors path="chargertel" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="orgNm" class="col-sm-2 col-form-label">기관소개노출</label>
                    <div class="col-sm-10 pt-2">
                    	<label><form:radiobutton path="orgCntsactiveyn" value="Y" /> 노출</label>
   						<label><form:radiobutton path="orgCntsactiveyn" value="N" /> 비노출</label>
                    </div>

                  </div>
                  <div class="form-group row">
                    <label for="orgNm" class="col-sm-2 col-form-label">정렬순서</label>
                    <div class="col-sm-10">
                    	<form:input path="sortOrder" title="정렬순서" class="form-control " maxlength="5" />
   						<div><form:errors path="sortOrder" cssClass="error" /></div>
   						<div style="color:red">※ 숫자가 낮을 수록 상단에 정렬됩니다.</div>
                    </div>
                  </div>
                  <div class="row">
                   		<div class="col-sm-4">
						<a class="btn btn-secondary btn-sm form-control" href="javascript:fn_list()">목록</a>
						</div>
                  		<div class="col-sm-4">
						<a class="btn btn-primary btn-sm form-control <c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" href="javascript:fn_detail_save()">기본정보 저장</a>
						</div>
						<div class="col-sm-4">
						<a class="btn btn-warning btn-sm form-control <c:if test="${commandMap.selectedMenu.delYn ne 'Y' }"> disabled</c:if>" href="javascript:fn_delete()">기관 삭제</a>
						</div>
	                </div>
 </form:form>