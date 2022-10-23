<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : index.jsp
  * @Description : 설문 JSP
  * @Modification Information
  * @
  * @  수정일           수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2022.01.06  이윤호                 최초 생성
  *
  *  @author 이윤호
  *  @since 2022.01.06
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
	<style>
		.btn_ty_s_c2 {display:none !important;}
	</style>
</head>

<body>
			<div class="sub_visual">
				<div class="inner">
					<h2>알림마당</h2>
					<%@ include file="/WEB-INF/templates/web/base/share_print.jsp"%>
				</div>
			</div>
			
			<div id="content" class="sub_content">
				<div class="sub_top">
					<a href="javascript:history.back();">뒤로가기 버튼</a>
					<h3>설문조사 </h3>
				</div>
				<!--//sub_top -->
				<div class="inform_area">
					<div class="img_con">
						<img src="<c:url value="${webDir }"/>/resources/images/data/img_data11.jpg" alt="알림마당 설문조사 메인 이미지">
					</div>
					<h4>${msg }</h4>
					
				</div>
				<div class="btn_area">
					<a href="./index" class="btn_s1_c0">목록이동</a>
				</div>
			</div>
			<!--// sub_content  -->			

</body>