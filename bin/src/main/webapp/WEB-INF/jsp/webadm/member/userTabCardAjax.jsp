<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name :  userTabCardAjax.jsp
  * @Description : 회원카드 이력 JSP
  * @Modification Information
  * @
  * @  수정일           수정자              수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.11.17  진수진            최초 생성
  *
  *  @author 진수진
  *  @since 2021.11.17
  *  @version 1.0
  *  @see
  *
  */
%>
<body>
<script>
	$(document).ready(function(){

	});
</script>
	<table class="table table-sm">
        <thead>
          <tr>
            <th style="width: 60px">번호</th>
            <th>발급일자</th>
            <th>발급방볍</th>
            <th>카드번호</th>
            <th>사용여부</th>
            <th>등록자</th>
            <th>재발급사유</th>
          </tr>
        </thead>
        <tbody>
<c:if test="${empty list }">
			<tr>
				<td colspan="7">등록된 데이타가 없습니다.</td>
			</tr>
</c:if>
<c:forEach items="${list}" var="item" varStatus="status">
		<tr>
			<td><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
			<td><hisco:DateUtil datestr="${item.issueDate}" format="yyyy-MM-dd"/></td>
			<td>${item.cardGbnNm }</td>
			<td>${item.cardNo }</td>
			<td><c:choose><c:when test="${item.useYn eq 'Y' }">사용</c:when><c:otherwise><span style="color:red">사용불가</span></c:otherwise></c:choose></td>
			<td>${item.reguser }</td>
			<td>${item.bigo }</td>
		</tr>
</c:forEach>
        </tbody>
      </table>

      <div class="card-footer p-0">
           	<div class="pagination_2">
				<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="javascript:fn_load_card"/>
			</div>
     </div>

</body>