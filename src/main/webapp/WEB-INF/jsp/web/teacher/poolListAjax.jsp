<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
					<!-- 강사 소개 리스트 클래스 : intro 추가  -->
<c:choose>
	<c:when test="${fn:length(list) == 0}">
					<tr>
					  <td colspan="5">등록정보가 없습니다.</td>
					</tr>
	</c:when>
	<c:otherwise>
	<c:forEach items="${list}" var="resultInfo" varStatus="status">
			<tr>
			  <td class="number">10</td>
			  <td class="member">${resultInfo.memNm}</td>
			  <td class="area">${resultInfo.lectrField}</td>
			  <td class="title">
				<a href="javascript:fn_go_detail('${resultInfo.memNo}')" class="truncate-1">${resultInfo }</a>
			  </td>
			  <td class="date">
				<fmt:formatDate value="${resultInfo.regdate }" pattern="yyyy.MM.dd"/>		  
			  </td>
			</tr>
	</c:forEach>

	</c:otherwise>
</c:choose>
				<!--// search_con  -->
<div id="pageNavigation"  style="display:none">
	<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="javascript:fn_set_page"/>
</div>
<script>
	$(document).ready(function(){
		$('.paginate').html($('#pageNavigation').html());
	});
</script>