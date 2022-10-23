<%
 /**
  * @Class Name : partCdRegi.jsp
  * @Description : 사업장  등록 화면
  * @Modification Information
  * @
  * @  수정일           수정자             수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.08.19  전영석            최초 생성
  *
  *  @author 전영석
  *  @since 2021.03.19
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>

<script type="text/javascript">

/* ********************************************************
 * 초기화
 ******************************************************** */
function fn_egov_init() {

	$('input.calendar').datepicker({
    	autoclose: true,
        calendarWeeks: false,
        todayHighlight: true,
        language: "kr",
    	format: "yyyy-mm-dd"
    });

}

/* ********************************************************
 * 저장처리화면
 ******************************************************** */
function fn_save() {

	var varFrom = document.templateVO;

	var varPartNm = $($("input[name='partNm']")).val();
	if ((varPartNm === undefined) || (varPartNm == null) || (varPartNm == '')) {
		alert("사업장 명칭은 비울 수 없습니다.");
		$("input[name='partNm']").focus();
		return;
	}

	var varSortOrder = $($("input[name='sortOrder']")).val();
	if ((varSortOrder === undefined) || (varSortOrder == null) || (varSortOrder == '')) {
		alert("정렬값은 비울 수 없습니다.");
		$("input[name='sortOrder']").focus();
		return;
	}

	var msg = "등록하시겠습니까?";
	if (varFrom.mode.value == "update") {
		msg = "수정하시겠습니까?";
	}

	var varMode = $($("input[name='mode']")).val();

	if ((varMode === undefined) || (varMode == null) || (varMode == '')) {
		$($("input[name='mode']")).val("insert");
	}

	if (confirm(msg)) {
		varFrom.submit();
	}

}

$(document).ready(function() {
	fn_egov_init();
});

</script>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>사업장 관리</h1>
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

<form:form commandName="templateVO" name="templateVO" action="./partCdSave" class="form-horizontal" method="post" enctype="multipart/form-data">

	<input type="hidden" name="searchQuery" value="<c:out value="${searchQuery}"/>"/>
	<input type="hidden" name="mode" value="<c:out value="${mode}"/>"/>

                <div class="card-body ">

                	<c:if test="${mode eq 'update' }">

                		<c:forEach items="${partCdList}" var="item" varStatus="status">

                			<input type="hidden" name="partCd" value="<c:out value="${item.partCd}"/>"/>

		                	<div class="form-group row">
			                    <label for="partGbn" class="col-sm-2 col-form-label"> 사업장 구분</label>
			                    <div class="col-sm-10">

			                    	<select name="partGbn" id="partGbn" class="form-control " style="width:200px;float:left">
			                    		<c:forEach items="${cotGrpCdList}" var="commItem" varStatus="status">
			                    			<option value="<c:out value='${commItem.cd}'/>" <c:if test="${commItem.cd eq item.partGbn}"> selected </c:if> ><c:out value='${commItem.cdNm}'/></option>
			                    		</c:forEach>
			                    	</select>

			   						<div></div>

			                    </div>
		                  	</div>

		                	<div class="form-group row">
			                    <label for="partNm" class="col-sm-2 col-form-label">* 사업장 명칭</label>
			                    <div class="col-sm-10">

			                    	<input name="partNm" title="사업장명칭" class="form-control " maxlength="255" style="width:500px;float:left" value="${item.partNm}" />
			   						<div></div>

			                    </div>
		                  	</div>

		                	<div class="form-group row">
			                    <label for="hpNo" class="col-sm-2 col-form-label"> 담당자 연락처(HP)</label>
			                    <div class="col-sm-10">

			                    	<input name="hpNo" title="담당자 연락처(HP)" class="form-control " maxlength="20" style="width:200px;float:left" value="${item.hpNo}" />
			   						<div></div>

			                    </div>
		                  	</div>

		                	<div class="form-group row">
			                    <label for="hpNo" class="col-sm-2 col-form-label"> 담당자 전화</label>
			                    <div class="col-sm-10">

			                    	<input name="tel" title="담당자 전화" class="form-control " maxlength="20" style="width:200px;float:left" value="${item.tel}" />
			   						<div></div>

			                    </div>
		                  	</div>

		                	<div class="form-group row">
			                    <label for="sortOrder" class="col-sm-2 col-form-label">* 정렬값</label>
			                    <div class="col-sm-10">

			                    	<input name="sortOrder" title="정렬값" class="form-control " maxlength="3" style="width:140px;float:left" value="${item.sortOrder}" />
			   						<div></div>

			                    </div>
		                  	</div>

		                	<div class="form-group row">
			                    <label for="useYn" class="col-sm-2 col-form-label">* 사용 여부</label>
			                    <div class="col-sm-10">
			                    	<label><input type="radio" name="useYn" value="Y" <c:if test="${item.useYn eq 'Y'}"> checked </c:if> /> 사용함</label>
								    &nbsp;&nbsp;&nbsp;
								    <label><input type="radio" name="useYn" value="N" <c:if test="${item.useYn ne 'Y'}"> checked </c:if> /> 사용안함</label>
									<div></div>
			                    </div>
		                  	</div>

						</c:forEach>

                	</c:if>

                	<c:if test="${mode ne 'update' }">

	                	<div class="form-group row">
		                    <label for="partGbn" class="col-sm-2 col-form-label"> 사업장 구분</label>
		                    <div class="col-sm-10">

		                    	<select name="partGbn" id="partGbn" class="form-control " style="width:200px;float:left">

		                    		<c:forEach items="${cotGrpCdList}" var="item" varStatus="status">
		                    			<option value="<c:out value='${item.cd}'/>"><c:out value='${item.cdNm}'/></option>
		                    		</c:forEach>

		                    	</select>

		   						<div></div>

		                    </div>
	                  	</div>

	                	<div class="form-group row">
		                    <label for="partNm" class="col-sm-2 col-form-label">* 사업장 명칭</label>
		                    <div class="col-sm-10">

		                    	<input name="partNm" title="사업장명칭" class="form-control " maxlength="255" style="width:500px;float:left" />
		   						<div></div>

		                    </div>
	                  	</div>

	                	<div class="form-group row">
		                    <label for="hpNo" class="col-sm-2 col-form-label"> 담당자 연락처(HP)</label>
		                    <div class="col-sm-10">

		                    	<input name="hpNo" title="담당자 연락처(HP)" class="form-control " maxlength="20" style="width:200px;float:left" />
		   						<div></div>

		                    </div>
	                  	</div>

	                	<div class="form-group row">
		                    <label for="hpNo" class="col-sm-2 col-form-label"> 담당자 전화</label>
		                    <div class="col-sm-10">

		                    	<input name="tel" title="담당자 전화" class="form-control " maxlength="20" style="width:200px;float:left" />
		   						<div></div>

		                    </div>
	                  	</div>

	                	<div class="form-group row">
		                    <label for="sortOrder" class="col-sm-2 col-form-label">* 정렬값</label>
		                    <div class="col-sm-10">

		                    	<input name="sortOrder" title="정렬값" class="form-control " maxlength="3" style="width:140px;float:left" />
		   						<div></div>

		                    </div>
	                  	</div>

	                	<div class="form-group row">
		                    <label for="useYn" class="col-sm-2 col-form-label">* 사용 여부</label>
		                    <div class="col-sm-10">
		                    	<label><input type="radio" name="useYn" value="Y" checked /> 사용함</label>
							    &nbsp;&nbsp;&nbsp;
							    <label><input type="radio" name="useYn" value="N" /> 사용안함</label>
								<div></div>
		                    </div>
	                  	</div>

                	</c:if>

               	</div>

                <!-- /.card-body -->
                <div class="card-footer">
                  <button type="button" onclick="fn_save()" class="btn btn-info float-right">저장</button>
                  <button type="button" class="btn btn-default " onclick="history.back();">취소</button>
                </div>
                <!-- /.card-footer -->

</form:form>


              </div>

            </div>

          </div>
        </div>
</section>