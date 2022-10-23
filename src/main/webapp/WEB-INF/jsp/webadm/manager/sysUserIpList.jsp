<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>
<%@ page import="com.hisco.cmm.util.Config"%>

<%
 /**
  * @Class Name  : sysUserIpList.jsp
  * @Description : IP 접속 차단 설정 관리 목록 JSP
  * @Modification Information
  * @
  * @  수정일              수정자            수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.06.09    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.06.09
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
            <h1>IP 접속 허용 설정 관리</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>

<!-- Main content -->
<section class="content">
      <div class="container-fluid">
       	<div class="card card-primary card-outline">
<form name="searchForm" method="get" >
		 	 	 <div class="card-body">
		 	 	 	<div class="row">
		              	<div class="col-2">
		              			<select name="searchCondition" title="검색영역"  class="form-control">
									<option value="0"  <c:if test="${searchVO.searchCondition == '0'}">selected="selected"</c:if> >아이디</option>
									<option value="1"  <c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if> >이름</option>
								</select>
						</div>
						<div class="col-2">
								<input class="form-control" name="searchKeyword" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" >
						</div>
						<div class="col-2">
							<button type="submit" class="btn btn-secondary">조회</button>
							<%-- <a href="./sysUserRegist" class="btn btn-info<c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>" title="등록버튼">등록</a> --%><!-- 등록 -->
		              	</div>

 					</div>
 				</div>
 </form>
 			</div>

       <div class="row">
          <div class="col-12">
            <div class="card">
              <!-- /.card-header -->

              <div class="card-body table-responsive p-0">
	               <table class="table table-hover text-nowrap">
	               	<colgroup>
						<col style="width: 9%;">
						<col style="width: 15%;">
						<col style="width: *">
						<col style="width: 15%;">
						<col style="width: 10%;">
						<col style="width: 12%;">
						<col style="width: 12%;">
					</colgroup>
	                  <thead>
	                    <tr>
	                      	<th>번호</th><!-- 번호 -->
							<th>아이디</th>
							<th>이름</th>
							<th>권한그룹</th>
							<th>사용여부</th>
							<th>마지막 로그인</th>
							<th>등록일</th>
							<th>관리</th>
	                    </tr>
	                  </thead>
	                  <tbody>

					<c:if test="${fn:length(list) == 0}">
						<tr>
							<td colspan="5">데이타가 없습니다</td>
						</tr>
					</c:if>

						<c:forEach items="${list}" var="resultInfo" varStatus="status">
		                    <tr>
		                      	<td><c:out value="${paginationInfo.totalRecordCount - (searchVO.pageIndex - 1) * searchVO.pageSize - status.index}"/></td>
								<td class="left"><c:out value='${resultInfo.userId}'/></td>
								<td><c:out value='${resultInfo.korName}'/></td>
								<td><c:out value='${resultInfo.groupNm}'/></td>
								<td><c:out value='${resultInfo.useYn}'/></td>
								<td><fmt:formatDate value="${resultInfo.lastLogin}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td><fmt:formatDate value="${resultInfo.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td>
								  <a class="btn bg-primary btn-xs<c:if test="${commandMap.selectedMenu.updYn ne 'Y' }"> disabled</c:if>" href="./sysUserIpUpdt?userId=<c:out value="${resultInfo.userId}"/><c:out value="${commandMap.query}"/>">IP 추가/삭제</a>
								</td>
		                    </tr>
						</c:forEach>

	                  </tbody>
	                </table>
              </div>
              <!-- /.card-body -->
              <div class="card-footer p-0">
              	<div class="pagination_2">
					<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./sysUserIpList?pageIndex="/>
				</div>
            </div>

            <!-- /.card -->
          </div>
        </div>
      </div>
     </div>
</section>



<!-- ****************************************************************************************************************************** -->

		</div>
			<%@ include file="/WEB-INF/templates/webadm/base/footer.jsp"%>
	  	<div class="control-sidebar-bg"></div>

	</div>

</body>

</html>