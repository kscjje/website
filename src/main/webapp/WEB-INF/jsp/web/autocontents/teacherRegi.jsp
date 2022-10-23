<%@ page language="java" import="egovframework.com.cmm.service.EgovProperties" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
 /**
  * @Class Name : teacherRegi.jsp
  * @Description : 강사 등록
  * @Modification Information
  * @
  * @  수정일         수정자      수정내용
  * @ ------------   --------    ---------------------------
  * @ 2021.03.18      전영석      최초 생성
  *
  *  @author 전영석
  *  @since 2021.03.18
  *  @version 1.0
  *  @see
  * 
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%> 
 
<%@ include file="../../include/top.jsp"%>
<script src="<%=strContext%>/js/common_nsm_human.js"></script>
<script>

</script>
				<div class="sub_cont" id="container">
					<div class="inner">
						<h3 class="tit">강사 등록</h3>
						<div class="join_wrap bor">
							<ul class="step step4">
								<li class="on">
									<dl>
										<dt>1</dt>
										<dd>강사 등록1</dd>
									</dl>
								</li>
								<li >
									<dl>
										<dt>2</dt>
										<dd>강사 등록2</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>3</dt>
										<dd>강사 등록3</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>4</dt>
										<dd>강사 등록4</dd>										
									</dl>
								</li>
							</ul>
					</div>
					<!-- //inner -->
				</div>
				<!-- //sub_cont -->