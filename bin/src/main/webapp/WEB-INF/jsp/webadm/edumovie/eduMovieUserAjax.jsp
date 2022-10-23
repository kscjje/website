<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : templateRegistAjax.jsp
  * @Description : 템플릿 등록 화면
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

<script>
	$(document).ready(function() {

	});
</script>

<form:form commandName="EduMovieVO" name="EduMovieVO" method="post" >
<input type="hidden" name="MODE" value="<c:out value=''/>" />
      <div class="row">
              <table id="modaltable" class="table table-bordered">
              <colgroup>
        				<col width="25%">
        				<col width="*">
        			</colgroup>
        		<tbody>
				<tr>
					<c:set var="title">템플릿명</c:set>
					<th><label for="tmplatNm"><span class="pilsu">*</span>  </label></th>
					
				</tr>
                <tr>
                	<c:set var="title">템플릿 타입</c:set>
               		 <th><span class="pilsu">*</span>   </th>
                      
                </tr>

				<tr>
					<c:set var="title">템플릿 경로</c:set>
					<th><label for="tmplatCours"><span class="pilsu">*</span>   </label></th>
					
				</tr>

				<!-- 사용여부 -->
				<c:set var="title">사용여부</c:set>
				<tr>
					<th><span class="pilsu">*</span> </th>
					
				</tr>
				</tbody>
             </table>
      </div>
 </form:form>