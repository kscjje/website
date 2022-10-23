<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : masterCtgListAjax.jsp
  * @Description : 카테고리 목록 화면
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.07.21    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2020.07.21
  *  @version 1.0
  *  @see
  *
  */
%>

<table style="width:100%" class="table table-bordered sm-table">
	<thead>
		<tr>
			<th>ID</th>
			<th>카테고리명</th>
			<th>정렬순서</th>
			<th>사용여부</th>
			<th>등록일</th>
			<th>관리</th>
		</tr>
	</thead>
	<tbody>
<c:if test="${fn:length(list) == 0}">
					<tr>
						<td colspan="6" class="text-center">데이타가 없습니다</td>
					</tr>
</c:if>
<c:forEach items="${list}" var="item" varStatus="status">
		<tr>
			<td><c:out value="${item.ctgId }"/></td>
			<td><c:out value="${item.ctgNm }"/></td>
			<td><c:out value="${item.ctgSort }"/></td>
			<td><c:out value="${item.useAt }"/></td>
			<td><fmt:formatDate value="${item.frstRegisterPnttm}" pattern="yyyy-MM-dd HH:mm"/></td>
			<td>
				<c:choose>
					<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
                   		<button type="button" class="btn bg-primary  btn-xs" onclick="fn_data_load('<c:out value="${item.ctgId}"/>' )" data-toggle="modal" data-target="#modal-default">수정</button>
               		</c:when>
               		<c:otherwise>
               			<button type="button" class="btn bg-primary  btn-xs disabled" >수정</button>
               		</c:otherwise>
               	</c:choose>
               	<c:choose>
					<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
                   		<button type="button" class="btn btn-secondary btn-xs" onclick="fn_ctg_delete('<c:out value="${item.ctgId}"/>')">삭제</button>
               		</c:when>
               		<c:otherwise>
               			<button type="button" class="btn btn-secondary btn-xs disabled" >삭제</button>
               		</c:otherwise>
               	</c:choose>
			</td>
		</tr>
</c:forEach>
	</tbody>
</table>
