<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : webMemberDetail.jsp
  * @Description : 웹 회원 상세 조회
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

<%@ include file="/WEB-INF/templates/webadm/base/header.jsp"%>

<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
             <h1>웹 회원 관리</h1>
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
							<col style="width:45%;">
							<col style="width:5%;">
							<col style="width:45%;">
						</colgroup>

						<tbody>
						<tr>
							<th>회원번호</th>
							<td class="left"><c:out value="${memberUserVO.memNo}"/></td>
							<th>성별</th>
							<td class="left">
								<c:choose>
									<c:when test="${memberUserVO.gender eq '1'}">남자</c:when>
									<c:when test="${memberUserVO.gender eq '2'}">여자</c:when>
									<c:when test="${memberUserVO.gender eq '0'}">미기재</c:when>
									<c:when test="${memberUserVO.gender eq '3'}">성별미상</c:when>
									<c:otherwise>기타</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>성명</th>
							<td class="left"><c:out value="${memberUserVO.memNm}"/></td>
							<th>웹ID</th>
							<td class="left"><c:out value="${memberUserVO.id}"/></td>
						</tr>
						<tr>
							<th>생년월일</th>
							<td  class="left"><c:out value="${memberUserVO.birthDate}"/>
							&nbsp;
								<c:choose>
									<c:when test="${memberUserVO.birthSec eq 'S'}">(양력)</c:when>
									<c:when test="${memberUserVO.birthSec eq 'M'}">(음력)</c:when>
								</c:choose>
							</td>
							<th>개인정보 동의일자</th>
							<td  class="left"><c:out value="${memberUserVO.agreeLastDate}"/></td>
						</tr>
						<tr>
							<th>휴대폰</th>
							<td  class="left"><c:out value="${memberUserVO.hp}"/></td>
							<th>이메일</th>
							<td  class="left"><c:out value="${memberUserVO.email}"/></td>
						</tr>
						<tr>
							<th>SMS 수신동의</th>
							<td  class="left"><c:out value="${memberUserVO.smsYn}"/></td>
							<th>이메일 수신동의</th>
							<td  class="left"><c:out value="${memberUserVO.emailYn}"/></td>
						</tr>
						<tr>
							<th>주소</th>
							<td  colspan="3"  class="left">
								<c:if test="${!empty memberUserVO.postNum }">[<c:out value="${memberUserVO.postNum}"/>]<br/></c:if>
								<c:out value="${memberUserVO.addr1}"/><br/>
								<c:out value="${memberUserVO.addr2}"/>
							</td>
						</tr>
						<tr>
							<th>계정잠김여부</th>
							<td class="left"><c:out value="${memberUserVO.lockedYn}"/></td>
							<th>비밀번호 틀린횟수</th>
							<td  class="left"><c:out value="${memberUserVO.failCnt}"/></td>
						</tr>
						<tr>
							<th>마지막 로그인 일시</th>
							<td  class="left"><fmt:formatDate value="${memberUserVO.loginLastdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<th>마지막 비밀번호 변경 일시</th>
							<td  class="left"><c:out value="${memberUserVO.pwdChangeDate}"/></td>
						</tr>
						<tr>
							<th>회원상태</th>
							<td class="left" ><c:out value="${memberUserVO.statusNm}"/></td>
							<th>가입일자</th>
							<td class="left" ><c:out value="${memberUserVO.joinDate}"/></td>
						</tr>
						<tr>
							<th>탈퇴일자</th>
							<td colspan="3" class="left"><c:out value="${memberUserVO.outDate}"/></td>
							<%-- <th>탈퇴사유</th>
							<td colspan="3" class="left"><c:out value="${memberUserVO.outReason}"/></td> --%>
						</tr>
						<tr>
							<th>등록정보</th>
							<td class="left"><c:out value="${memberUserVO.reguser}"/> / <fmt:formatDate value="${memberUserVO.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
							<th>수정정보</th>
							<td class="left"><c:if test="${!empty memberUserVO.moduser}"><c:out value="${memberUserVO.moduser}"/> / <fmt:formatDate value="${memberUserVO.moddate}" pattern="yyyy-MM-dd HH:mm"/></c:if></td>
						</tr>

					</tbody>

						</table>

	                </div>
	                <!-- /.card-body -->
	                <div class="card-footer">
						 <c:choose>
							<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
								<%-- <button type="button" class="btn btn-secondary float-right mr-2" onclick="fn_deleteEdcPgm('${memberUserVO.edcPrgmid}');">삭제</button> --%>
							</c:when>
							<c:otherwise>
								<!-- <button type="button" class="btn btn-secondary float-right disabled mr-2">삭제</button> -->
							</c:otherwise>
						</c:choose>

						 <c:choose>
							<c:when test="${commandMap.selectedMenu.updYn eq 'Y' and  memberUserVO.status eq '0000'}">
								<a class="btn btn-info mr-2 float-right mr-2" href="./userUpdt<c:out value='${commandMap.queryAll}'/>">수정</a>
								<c:if test="${!empty memberUserVO.id}">
									<a class="btn btn-warning mr-2 float-right" href="javascript:fn_set_password()">비밀번호 초기화</a>
								</c:if>
							</c:when>
							<c:otherwise>
								<a class="btn btn-info mr-2 float-right disabled">수정</a>
							</c:otherwise>
						</c:choose>

	                  	<a class="btn btn-default " href="./userList<c:out value="${searchQuery}"  escapeXml="false" />">목록</a>

	                </div>
	                <!-- /.card-footer -->

	              </div>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
	          </div>
        </div>
</section>
<script>
	function fn_set_password(){
		if(confirm("핸드폰 번호 마지막 4자리로 초기화 됩니다.\n초기화 하시겠습니까?")){
			$.ajax({
		      url: './userPasswdSet.json',
		      data : {'memNo':'${memberUserVO.memNo}' },
		      type : "POST",
		      dataType : "json",
		      beforeSend : function(xhr) {
                  xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
              },
		      success: function(data) {
		    	  alert(data.result.success);
		         if(data.result.success){
		        	 alert("정상적으로 변경되었습니다.");
		         }else{
		        	 alert("변경된 데이타가 없습니다.");
		         }
		      },
		      error: function(jqXHR, exception) {
	        	__ajaxErrorCallback__(jqXHR, "시스템 오류 입니다.");
	        }
		    });
		}
	}
</script>
<!-- ****************************************************************************************************************************** -->

		</div>
			<%@ include file="/WEB-INF/templates/webadm/base/footer.jsp"%>
	  	<div class="control-sidebar-bg"></div>

	</div>

</body>

</html>