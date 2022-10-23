<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name :  userList.jsp
  * @Description : 회원현황 관리 목록 JSP
  * @Modification Information
  * @
  * @  수정일           수정자              수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.03.19  전영석            최초 생성
  *
  *  @author 전영석
  *  @since 2021.03.19
  *  @version 1.0
  *  @see
  *
  */
%>
<body>
<script type="text/javascript" src="../validator"></script>
<validator:javascript formName="memberUserVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script>
	$(document).ready(function(){
		//Datemask dd/mm/yyyy
		$('input[name=birthDate]').inputmask({ mask: "y-1-2", leapday: "-02-29", placeholder: "yyyy-mm-dd", separator: "-", alias: "yyyy-mm-dd" });

		$('input[name=hp]').bind('keyup blur', function(e){
			printHp($(this));
		});
	});

	function fn_excelDown() {
		var $cform = $('#searchForm').clone(true);

		$cform.attr('id', 'excelDownloadForm');
		$cform.attr('name', 'excelDownloadForm');
		$cform.attr('method', 'get');
		$cform.attr('action', "./userListExcel");

		$('body').append($cform);
		$('#excelDownloadForm').submit();
		$('#excelDownloadForm').remove();
	}

	function fn_save() {
		var form = document.registForm;
		var msg = "방문회원을 등록하시겠습니까?";

		if (!validateMemberUserVO(form)) {
			return ;
		}

		if(confirm(msg)){
			$.ajax({
			      url: './userSave.json',
			      type : "POST",
			      dataType : "json",
			      data : $('#registForm').serialize(),
	              success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        }else{
				        	window.location.reload();
				        	 alert(data.result.msg);
				        }
			      }
			});
		}
	}
</script>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>회원 현황</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div>
</section>

<!-- Main content -->
<section class="content">

      <div class="container-fluid">
       	<div class="card card-primary card-outline">
			<form name="searchForm" id="searchForm"  method="get" >
		 	 	 <div class="card-body">
		 	 	 	<div class="row">
		              	<div class="col-4">
		              			<select name="searchCondition" title="검색영역"  class="form-control">
									<option value="1"  <c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if> >회원명</option>
									<option value="2"  <c:if test="${searchVO.searchCondition == '2'}">selected="selected"</c:if> >아이디</option>
									<option value="3"  <c:if test="${searchVO.searchCondition == '3'}">selected="selected"</c:if> >휴대폰번호</option>
								</select>
						</div>
						<div class="col-4">
								<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" >
						</div>
						<div class="col-4">
							<button type="submit" class="btn btn-secondary">조회</button>
		              	</div>
 					</div>
 				</div>
			 </form>
 			</div>
	<div class="row mb-2">
			 <div class="col-2 pt-1">
			 	조회결과 <b><fmt:formatNumber value="${commandMap.pagingInfo.totalRecordCount}"/></b> 건
			 </div>
			 <div class="col-10" style="text-align:right">
				<c:choose>
					<c:when test="${commandMap.selectedMenu.insYn eq 'Y'}">
						<button type="button" class="btn btn-info btn-sm btn-flat" title="방문회원 등록" data-toggle="modal" data-target="#modal-default">방문회원 등록</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-info btn-sm btn-flat disabled" title="방문회원 등록">방문회원 등록</button>
					</c:otherwise>
				</c:choose>

				<button type="button" onclick="fn_excelDown()" class="btn  btn-secondary btn-sm btn-flat">EXCEL 내보내기</button>
			</div>
       </div>
       <div class="row">
          <div class="col-12">
            <div class="card">
              <!-- /.card-header -->

              <div class="card-body table-responsive p-0">
	               <table class="table table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 8%;">
						<col style="width: 8%;">
						<col style="width: 9%">
						<col style="width: *">
						<col style="width: 9%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
						<col style="width: 9%;">
					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th>번호</th>
	                      	<th>회원번호</th>
							<th>웹ID</th>
	                      	<th>이름</th>
	                      	<th>성별</th>
	                      	<th>생년월일</th>
	                      	<th>휴대폰</th>
	                      	<th>이메일</th>
	                      	<th>상태</th>
							<th>등록일시</th>
							<!-- <th></th> -->
	                    </tr>
	                  </thead>
	                  <tbody>

					<c:if test="${fn:length(WebMemberList) == 0}">
					<tr>
						<td colspan="9">데이타가 없습니다</td>
					</tr>
					</c:if>

						<c:forEach items="${WebMemberList}" var="item" varStatus="status">
		                    <tr>
		                      	<td>
		                      		<c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/>
		                      	</td>
								<td>
									<c:out value='${item.memNo}'/>
								</td>
								<td>
								  	<c:out value='${item.id}'/>
								</td>
								<td>
									<a href="./userUpdt?memNo=<c:out value="${item.memNo}"/><c:out value="${commandMap.query}"/>"><c:out value='${item.memNm}'/></a>
								</td>
								<td>
									<c:choose>
										<c:when test="${item.gender eq '0'}">미기재</c:when>
										<c:when test="${item.gender eq '1'}">남성</c:when>
										<c:when test="${item.gender eq '2'}">여성</c:when>
										<c:when test="${item.gender eq '3'}">성별미상</c:when>
										<c:when test="${item.gender eq '9'}">기타</c:when>
										<c:otherwise>${item.gender }</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:out value='${item.birthDate}'/>
								</td>
								<td>
									<hisco:HpPrint hp="${item.hp}"/>
								</td>
								<td>
									${item.email}
								</td>
								<td>
								  	<c:out value='${item.statusNm}'/>
								</td>
								<td>
									<fmt:formatDate value="${item.regdate}" pattern="yyyy.MM.dd HH:mm"/>
								</td>

		                    </tr>
						</c:forEach>

	                  </tbody>
	                </table>
              </div>

              <div class="card-footer p-0">
              	<div class="pagination_2">
					<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./userList?pageIndex="/>
				</div>
            </div>


          </div>
        </div>
      </div>
     </div>

     <!-- 모달 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">방문회원 등록</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
 <form name="registForm" id="registForm">
              <table class="table table-bordered" >
                  <tbody>
						<tr>
							<th><span class="pilsu">*</span> 이름 </th>
							<td class="left">
								<div class="input-group input-group-sm" style="width: 150px;float:left">
									<input type="text" name="memNm"  placeholder="이름 입력해주세요." title="이름" maxlength="20" class="form-control"/>
				                </div>
							</td>
						</tr>
						<tr>
							<th><span class="pilsu">*</span> 성별 </th>
							<td class="left">
								<div class="icheck-primary d-inline">
				                      	<input type="radio" name="gender" id="gender1" value="M"/>
				                        <label for="gender1">남자</label>
				                      </div>
				                      <div class="icheck-primary d-inline">
				                       <input type="radio" name="gender" id="gender2" value="F"/>
				                        <label for="gender2">
				                        	여자
				                        </label>
				                      </div>
							</td>
						</tr>
						<tr>
							<th><span class="pilsu">*</span> 생년월일</th>
							<td class="left">
								<div class="input-group input-group-sm" style="width: 100px;float:left">
				                    <input type="text" name="birthDate"  title="생년월일" maxlength="10" class="form-control"/>
				                </div>
				                <div class="input-group input-group-sm" style="width: 100px;float:left">
				                    <select name="birthSec" class="form-control">
				                    	<option value="S">양력</option>
				                    	<option value="M">음력</option>
				                    </select>
				                </div>
							</td>
						</tr>

						<tr>
							<th><span class="pilsu">*</span> 휴대폰</th>
							<td  class="left">
								<div class="input-group input-group-sm" style="width: 200px;">
				                    <div class="input-group-prepend">
				                      <span class="input-group-text"><i class="fas fa-phone"></i></span>
				                    </div>
				                   <input type="text" name="hp"  title="휴대폰" maxlength="13" class="form-control"/>
				                  </div>
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td class="left">
								<div class="input-group input-group-sm" style="width: 200px;">
									<input type="text" name="email"  title="이메일" maxlength="100" class="form-control"/>
								</div>
							</td>
						</tr>
                  </tbody>
                </table>
 </form>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" onclick="fn_save()" class="btn btn-primary">저장</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
</section>


</body>