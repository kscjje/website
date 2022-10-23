<%
 /**
  * @Class Name : partCdDetail.jsp
  * @Description : 사업장 상세 화면
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>

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
              <div class="card-body  table-responsive p-0">
                	<table class="table table-bordered text-nowrap">
	               	<colgroup>
						<col style="width:10%;">
						<col style="width:40%;">
						<col style="width:10%;">
						<col style="width:40%;">
					</colgroup>

					<tbody>
					<!-- 글 제목 -->
					<tr>
						<th>고유번호</th>
						<td colspan="3" class="left"><c:out value="${partCdDetail[0].partCd}"/></td>
					</tr>
					<tr>
						<th>사업장 구분</th>
						<td colspan="3" class="left"><c:out value="${partCdDetail[0].partGbn}"/>

						</td>
					</tr>					
					<tr>
						<th>사업장명</th>
						<td colspan="3" class="left"><c:out value="${partCdDetail[0].partNm}"/>

						</td>
					</tr>
					<tr>
						<th>담당자 연락처(HP)</th>
						<td colspan="3" class="left"><c:out value="${partCdDetail[0].hpNo}"/></td>
					</tr>
					<tr>
						<th>담당자 전화</th>
						<td colspan="3" class="left"><c:out value="${partCdDetail[0].tel}"/></td>
					</tr>					
					<tr>
						<th>정렬번호</th>
						<td colspan="3" class="left"><c:out value="${partCdDetail[0].sortOrder}"/></td>
					</tr>					
					<tr>
						<th>등록정보</th>
						<td class="left"><c:out value="${partCdDetail[0].reguser}"/> / <fmt:formatDate value="${partCdDetail[0].regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
						<th>수정정보</th>
						<td class="left"><c:if test="${!empty partCdDetail[0].moduser}"><c:out value="${partCdDetail[0].moduser}"/> / <fmt:formatDate value="${partCdDetail[0].moddate}" pattern="yyyy-MM-dd HH:mm"/></c:if></td>
					</tr>
					
				</tbody>

					</table>

                </div>
                <!-- /.card-body -->
                <div class="card-footer">
					 <c:choose>
						<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
							<button type="button" class="btn btn-secondary float-right mr-2" onclick="fn_deletePartCd('${partCdDetail[0].partCd}');">삭제</button>
						</c:when>
						<c:otherwise>
							<button type="button" class="btn btn-secondary float-right disabled mr-2">삭제</button>
						</c:otherwise>
					</c:choose>
					
					 <c:choose>
						<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
							<a class="btn btn-info mr-2 float-right" href="./partCdUpdt<c:out value="${commandMap.queryAll}"/>">수정</a>
						</c:when>
						<c:otherwise>
							<a class="btn btn-info mr-2 float-right disabled">수정</a>
						</c:otherwise>
					</c:choose>
                  	<a class="btn btn-default " href="./partCdList<c:out value="${searchQuery}"  escapeXml="false" />">목록</a>
                </div>
                <!-- /.card-footer -->

              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          </div>
        </div>
</section>

<form:form commandName="templateVO" name="templateVO" action="./partCdSave" class="form-horizontal" method="post" enctype="multipart/form-data">
	<input type="hidden" name="partCd" id="partCd" />
	<input type="hidden" name="mode"   value="delete" />
</form:form>

<script>
	
	function fn_deletePartCd(partCd) {
	
		var varFrom = document.templateVO;
		
		if (confirm("선택하신 사업장 정보를 삭제합니다.")) {
			$("#partCd").val(partCd);
			varFrom.submit();
			return;
		}
		
	}
	
</script>

