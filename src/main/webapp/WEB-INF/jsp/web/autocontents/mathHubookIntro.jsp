<%@ page language="java" import="egovframework.com.cmm.service.EgovProperties" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
 /**
  * @Class Name : mathHubookIntro.jsp
  * @Description : 매스휴먼북 소개
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

				<div class="sub_cont" id="container">
					<div class="inner">
						<h3 class="tit">매스휴먼북 소개</h3>
						<div class="join_wrap bor">
							<ul class="step step4">
								<li class="on">
									<dl>
										<dt>1</dt>
										<dd>소개내용1</dd>
									</dl>
								</li>
								<li >
									<dl>
										<dt>2</dt>
										<dd>소개내용2</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>3</dt>
										<dd>소개내용3</dd>
									</dl>
								</li>
								<li>
									<dl>
										<dt>4</dt>
										<dd>소개내용4</dd>										
									</dl>
								</li>
							</ul>
					</div>
					<!-- //inner -->
				</div>
				<!-- //sub_cont -->