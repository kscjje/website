<%
 /**
  * @Class Name : copyright.jsp
  * @Description : 저작권정책
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.11.10    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.11.10
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/top.jsp"%>
	<div class="sub_cont" id="container">
		<div class="inner">
			<h3 class="tit mb20">저작권정책 </h3>
			<p class="desc mb70">저작권법 제24조의2에 따라 한국저작권위원회에서 저작재산권의<br> 전부를 보유한 저작물의 경우에는 별도의 이용허락 없이 자유이용이 가능합니다.</p>
			<div class="mark_area">
				<div class="mark_img"><img src="<%=strContext%>/images/sub/img_mark.png" alt="공공누리마크이미지"></div>
				<dl>
					<dt>공공누리 마크 제 4유형</dt>
					<dd>단, 자유이용이 가능한 자료는 "공공저작물 자유이용허락 표시 기준(공공누리, KOGL) 제1유형"을 부착하여 개방하고 있으므로 공공누리 표시가 부착된 저작물인지를 확인한 이후에 자유 이용하시기 바랍니다.<br> 자유이용의 경우에는 반드시 저작물의 출처를 구체적으로 표시하여야 합니다.</dd>
				</dl>
			</div>
		</div>
		<!-- //inner -->
	</div>
	<!-- //sub_cont -->