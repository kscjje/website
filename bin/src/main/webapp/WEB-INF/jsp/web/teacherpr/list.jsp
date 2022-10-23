<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<script>
		function fn_write(){
			<c:if test="${userInfo == null}">
			alert("로그인 후 작성하실 수 있습니다.");
			document.location.href = "<c:url value="${webDir}"/>/member/login?returnURL=<c:url value="${webDir}"/>/teacherpr/write"
			return ;
			</c:if>

			if("N" == "<c:out value="${poolCheck}"/>"){
				alert("강사은행 등록이 승인 된 이후, 강사PR 내용을 작성하실 수 있습니다.");
				return ;
			}
			document.location.href = "./write";
		}	
	</script>
</head>
<body>
	<div class="sub_visual">
				<div class="inner">
					<h2><c:out value="${SELECTED_MENU_OBJ.upperMenuNm}" /></h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>

			<!--//sub_visual  -->
			<div id="content" class="sub_content">
				<!-- tab_link -->
				<%@ include file="/WEB-INF/templates/web/base/tab_menu.jsp"%>
				<!--// tab_link -->
				<div class="sub_top type01">
					<h3><c:out value="${SELECTED_MENU_OBJ.menuNm}" /></h3>
				</div>
		
			
				<form name="articleForm" id="articleForm" action="./list" method="get" >
					<input type="hidden" name="searchCtg" value="<c:out value="${searchVO.searchCtg }"/>"/>
					<input type="hidden" name="sortOrdr" value="<c:out value="${searchVO.sortOrdr }"/>"/>
				</form>
	
		
				<div class="search_sel s_column">
					<a href="javascript:fn_write()" class="btn_s3_c1 l_ab">기획제안 작성</a>
					<!-- 버튼이 제일 앞에 올때 inputbox에 클래스 : st2 추가 -->
					<div class="inputbox st2">
						<label for="inp_search" class="hidden"></label>
						<input type="text" title="강사PR 검색" placeholder="" id="inp_search">
					</div>
					<button class="btn_s3_c2 b_right">검색</button>
				</div>
				
		<c:if test="${fn:length(resultList) == 0}">
				<!--// search_sel  -->
				<div class="" style="padding-top:50px;">
					<table>
						<caption><c:out value="${boardMasterVO.bbsNm }"/> 표. - 등록된 데이타가 없음 안내</caption>

						<tbody>
							<tr>
								<td class="text-center">검색된 강사PR정보가 없습니다.</td>
							</tr>
						</tbody>
					</table>
				</div>
		</c:if>
		
<c:if test="${fn:length(resultList) > 0}">				
				<!-- 강사 pr 클래스 : pr 추가 -->
				<div class="report_list pr">
					<ul>
	<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
						<li>
						 <a href="./detail?nttId=<c:out value="${resultInfo.nttId}"/><c:out value="${searchQuery}"/>">
							<div class="img_box">
								<img src="<c:url value="${webDir}"/>/common/file/download?atchFileId=<c:out value="${resultInfo.atchFileId}"/>&fileSn=<c:out value="${resultInfo.atchImg}"/>" alt="<c:out value="${resultInfo.nttSj}"/> 이미지" onError="this.src='<c:url value="${webDir}"/>/resources/images/data/no_img.jpg'">
							</div>
							<div class="txt_area">
								<p><c:out value="${resultInfo.nttSj}"/></p>
								<div class="name">
									<p><c:out value="${resultInfo.frstRegisterNm}"/></p>
									<span><c:out value="${resultInfo.item1}"/></span>
								</div>
								</div>
							</a>
						</li>
	</c:forEach>
					</ul>
				</div>
</c:if>				
				<!--// web_box  -->
				
				<div class="paginate">
					<tags:UserPaging pageInfo="${paginationInfo}" pageUrl="./list?pageIndex="/>
				</div>								
			</div>
	</div>			
</body>