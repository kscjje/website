<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : sysUserListAjax.jsp
  * @Description : 관리자 계정 목록 JSP
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
	              <div class="card-body table-responsive p-0">
		               <table id="listTable" class="table table-hover text-nowrap">
		                  <thead>
		                    <tr>
		                      	<th>번호</th><!-- 번호 -->
								<th>기관</th>
								<th>부서</th>
								<th>ID</th>
								<th>회원명</th>
								<th>권한유형</th>
								<th>사용여부</th>
								<th>재직상태</th>
		                    </tr>
		                  </thead>
		                  <tbody>
<c:if test="${fn:length(list) == 0}">
					<tr>
						<td colspan="8">데이타가 없습니다</td>
					</tr>
</c:if>
<c:forEach items="${list}" var="resultInfo" varStatus="status">
	                    <tr style="cursor:pointer" id="ROW_<c:out value='${resultInfo.userId}'/>" data-userid="<c:out value='${resultInfo.userId}'/>">
	                      	<td><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
							<td><c:out value='${resultInfo.orgNm}'/></td>
							<td><c:out value='${resultInfo.depNm}'/></td>
							<td class="left"><c:out value='${resultInfo.userId}'/></td>
							<td><c:out value='${resultInfo.korName}'/></td>
							<td><c:out value='${resultInfo.groupNm}'/></td>
							<td>
								<c:choose>
									<c:when test="${resultInfo.useYn eq 'N'}">미사용</c:when>
									<c:when test="${resultInfo.acntStats eq '1000'}">활성</c:when>
									<c:when test="${resultInfo.acntStats eq '2001'}">잠금</c:when>
									<c:when test="${resultInfo.acntStats eq '2002'}">로그인실패잠금</c:when>
									<c:otherwise>${resultInfo.acntStats }</c:otherwise>
								</c:choose>
							</td>
							<td><c:out value='${resultInfo.stats}'/></td>
	                    </tr>
</c:forEach>
		</tbody>
	</table>
 </div>
 
              <!-- /.card-body -->
 <div class="card-footer p-0">
	<div class="pagination_2">
		<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="javascript:fn_set_page"/>
	</div>
</div>