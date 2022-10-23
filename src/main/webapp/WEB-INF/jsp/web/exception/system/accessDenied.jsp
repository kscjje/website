<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<%
 /**
  * @Class Name : accessDenied.jsp
  * @Description : System Exception
  * @Modification Information
  * @
  * @  수정일         수정자      수정내용
  * @ ------------   --------    ---------------------------
  * @ 2020.10.08      전영석      최초 생성
  *
  *  @author 전영석
  *  @since 2020.10.08
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ include file="/WEB-INF/templates/web/base/header.jsp"%>

<script>

	function fn_goNextUrl(){

		if (location.href.indexOf("/web/") > -1) {
			location.href = "/web/main";
		} else if (location.href.indexOf("/webadm/") > -1) {
			location.href = "/webadm/login";
		}

	}

</script>

	<div class="sub_cont" id="container">
		<div class="page">
			<div class="inner">
				<p class="tit mb15">
					접근 권한이 없습니다.

				</p>
				<p class="desc2">예약사이트 홈으로 이동 또는 잠시 후 다시 접속 해주시기 바랍니다.</p>
				<ul class="page_list">
					<li>
						<dl>
							<dt></dt>
							<dd><a  href="#" onclick="fn_goNextUrl(); return false;" class="btn_ty_m3_c1">홈으로</a></dd>
						</dl>
					</li>
					<!-- <li>
						<dl>
							<dt></dt>
							<dd><a  href="javascript:history.go(-1);" class="btn_ty_m3_c5">이전으로</a></dd>
						</dl>
					</li> -->
				</ul>
			</div>
			<!-- //inner -->
		</div>
		<!-- //page -->
	</div>
	<!-- //sub_cont -->

<%@ include file="/WEB-INF/templates/web/base/footer.jsp"%>