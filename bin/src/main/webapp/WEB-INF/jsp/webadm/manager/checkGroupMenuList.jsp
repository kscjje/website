<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hisco.cmm.util.Config"%>
<%
 /**
  * @Class Name : checkGroupMenuList.jsp
  * @Description : 그룹별  메뉴 설정  JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.15    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.15
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
<script>
	function fn_checked_save() {
		var cnt = $('input[name=checkId]:checked').length;

		if (!$('#authorCode').val()) {
			alert("그룹을 먼저 선택해 주세요");
			return;
		}
		if (confirm("저장 하시겠습니까?")) {
			$('#listForm').find("input[name=authorCode]").val($('#authorCode').val());
			$.ajax({
			      url: './checkGroupMenuSave.json',
			      data :$('#listForm').serialize(),
			      type : "POST",
			      dataType : "json",
			      success: function(data) {
			    	  if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        } else {
				        	 alert(data.result.msg);
					         window.location.reload();
				         }
			      }
			    });
		}

	}

	$(document).ready(function() {
		$('#allcheck').click(function() {
			$('input[name=checkId]').prop("checked" , $(this).prop("checked"));
		});

		$('#authorCode').change(function() {
			$('#resultVO').submit();
		});


		$('input[name=checkId]').click(function() {
			var val = $(this).attr("value");
			if ($(this).prop("checked")) {
				$('.check_' + val).prop("disabled" , false);
			} else {
				$('.check_' + val).prop("disabled" , true);
			}
		});

		<c:if test="${allCnt > 0}">
			$('input[name=checkId]').prop("checked" , true);
		</c:if>
	});
</script>
</head>
<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>권한그룹별 메뉴 관리</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>

<!-- Main content -->
	<section class="content">
      <div class="container-fluid">
       	<div class="card">
			<form:form commandName="resultVO" action="./checkGroupMenuList" method="get" >
		 		<div class="card-body">
		 	 	 	<div class="row">
		              	<div class="col-6">
								<form:select path="authorCode" class="form-control">
									<form:option value="">::권한그룹 선택::</form:option>
									<form:options items="${authorList}" itemValue="authorCode" itemLabel="authorNm"/>
								</form:select>
						</div>
						<div class="col-6">
							<button type="submit" class="btn btn-secondary">조회</button>
							<button type="button" class="btn btn-info" onclick="fn_checked_save()" >저장</button>

		              	</div>
 					</div>
 				</div>
 			</form:form>
 		</div>

       	<div class="row">
       		<div class="col-12">
       		 	※ 선택된 메뉴가 없을 시 모든 메뉴 권한이 부여됩니다.
       		 </div>
          <div class="col-12">
            <div class="card card-primary card-outline">
              <!-- /.card-header -->
				<form name="listForm" id="listForm" action="./authList" method="get" >
				<input type="hidden" id="groupId" name="groupId" value="${param.authorCode}"/>
              	<div class="card-body table-responsive p-0">
	               <table class="table table-hover text-nowrap">
		               	<colgroup>
							<col style="width: 5%;">
							<col style="width: 30%;">
							<col style="width: 5%;">
							<col style="width: 5%;">
							<col style="width: 5%;">
							<col style="width: 5%;">
							<col style="width: 5%;">
							<col style="width: 20%;">
							<col style="width: *;">
						</colgroup>
		                  <thead>
		                    <tr>
		                      	<th>번호</th><!-- 번호 -->
								<th>메뉴명</th>
								<th>관리(조회)</th>
								<th>등록</th>
								<th>수정</th>
								<th>삭제</th>
								<th>URL</th>
								<th></th>
		                    </tr>
		                  </thead>
		               	<tbody>
						<c:if test="${fn:length(menuList) == 0}">
							<tr>
								<td colspan="10">데이타가 없습니다</td>
							</tr>
						</c:if>
						<c:forEach items="${menuList}" var="item" varStatus="status">
		                    <tr>
								<td><c:out value="${status.index+1}"/></td>
								<td class="left">
									<c:choose>
									 	<c:when test="${item.childCnt < 1 }">&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-fw  fa-caret-right"></i></c:when>
									 	<c:otherwise><i class="fa fa-fw fa-arrow-circle-down"></i></c:otherwise>
									</c:choose>
									<c:out value="${item.menuNm}"/>
								</td>
								<td>
									<c:if test="${!empty item.menuUrl}">
										<label>R <input type="checkbox" name="checkId" class="check2" title="선택" value="<c:out value="${item.menuNo}"/>" <c:if test="${item.chkYeoBu>0 }">checked</c:if> /></label></td>
									</c:if>
								</td>
								<td>
									<c:if test="${!empty item.menuUrl}">
										<label>C <input type="checkbox" name="checkInsertId" class="check2 check_<c:out value="${item.menuNo}"/>" title="선택" value="<c:out value="${item.menuNo}"/>" <c:if test="${item.insYn eq 'Y' }">checked</c:if> <c:if test="${item.chkYeoBu < 1 }">disabled</c:if> /></label></td>
									</c:if>
								</td>

								<td>
									<c:if test="${!empty item.menuUrl}">
										<label>U <input type="checkbox" name="checkUpdateId" class="check2 check_<c:out value="${item.menuNo}"/>" title="선택" value="<c:out value="${item.menuNo}"/>" <c:if test="${item.updYn eq 'Y' }">checked</c:if> <c:if test="${item.chkYeoBu < 1 }">disabled</c:if> /></label></td>
									</c:if>
								</td>
								<td>
									<c:if test="${!empty item.menuUrl}">
										<label>D <input type="checkbox" name="checkDeleteId" class="check2 check_<c:out value="${item.menuNo}"/>" title="선택" value="<c:out value="${item.menuNo}"/>" <c:if test="${item.delYn eq 'Y' }">checked</c:if> <c:if test="${item.chkYeoBu < 1 }">disabled</c:if> /></label></td>
									</c:if>
								</td>
								<td><c:out value="${item.menuUrl}"/></td>
								<td></td>
							</tr>
						</c:forEach>
	                  </tbody>
	                </table>
              	</div>
			</form>
            <!-- /.card-body -->

            <!-- /.card -->
          </div>
        </div>
      </div>
     </div>

</section>
</body>