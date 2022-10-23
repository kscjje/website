<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : errorList.jsp
  * @Description :에러  히스토리  목록 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.03.24    진수진          최초 생성
  *
  *  @author 진수진
  *  @since 2021.03.24
  *  @version 1.0
  *  @see
  *
  */
%>
<% pageContext.setAttribute("newLineChar", "\n"); %>
<body>

	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>에러로그</h1>
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
	            <div class="card">
	              <!-- /.card-header -->

	              <div class="card-body p-0">
		               <table class="table table table-striped">
		                  <thead>
		                    <tr>
		                      	<th>번호</th><!-- 번호 -->
								<th>날짜</th>
								<th>ID</th>
								<th>IP</th>
								<th>Url</th>
								<th>참조 Url</th>
								<th>파라미터</th>
		                    </tr>
		                  </thead>
		                  <tbody>

							<c:if test="${fn:length(list) == 0}">
								<tr>
									<td colspan="7">데이타가 없습니다</td>
								</tr>
							</c:if>
							<c:forEach items="${list}" var="resultInfo" varStatus="status">
			                    <tr>
			                      	<td><c:out value='${resultInfo.logSeq}'/></td>
			                      	<td><fmt:formatDate value="${resultInfo.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>

									<td class="left"><c:out value='${resultInfo.conectId}'/></td>
									<td style="word-break:break-all"><c:out value='${resultInfo.conectIp}'/></td>
									<td style="word-break:break-all"><c:out value='${resultInfo.connectUrl}'/></td>
									<td style="word-break:break-all"><c:out value='${resultInfo.refUrl}'/></td>
									<td style="word-break:break-all"><c:out value='${resultInfo.paramVal}'/></td>
			                    </tr>
			                    <tr>
			                    	<td colspan="7" style="word-break:break-all"><c:out value="${fn:replace(resultInfo.errormsg, newLineChar, '<br/>')}" escapeXml="false" /></td>
			                    </tr>
							</c:forEach>

		                  </tbody>

		                </table>

	              </div>
	              <!-- /.card-body -->
	              <div class="card-footer p-0">
	              	<div class="pagination_2">
							<tags:AdminPaging pageInfo="${paginationInfo}" pageUrl="./errorList?pageIndex="/>
					</div>
	            </div>


	            <!-- /.card -->
	          </div>
	        </div>
	      </div>
	     </div>
	</section>
</body>
