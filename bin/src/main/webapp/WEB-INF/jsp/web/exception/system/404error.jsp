<%
 /**
  * @Class Name : errorPage.jsp
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
<%@ page contentType="text/html; charset=utf-8"%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

				<div class="sub_cont" id="container">
					<div class="page">
						<div class="inner">
							<p class="tit mb15">페이지를 찾을 수 없습니다.</p>
							<p class="desc2">예약사이트 홈으로 이동 또는 잠시 후 다시 접속 해주시기 바랍니다.</p>
							<ul class="page_list">
								<li>
									<dl>
										<dt><!-- <img src="<%=strContext%>/images/sub/img_page01.png" alt="과학관홈이미지"> --></dt>
										<dd><a  href="/web/main" class="btn_ty_m3_c1" >홈으로</a></dd>
										
									</dl>
								</li>
								<li>
									<dl>
										<dt><!-- <img src="<%=strContext%>/images/sub/img_page02.png" alt="과학관예약사이트이미지"> --></dt>
										<dd><a  href="javascript:history.go(-1);" class="btn_ty_m3_c5">이전으로</a></dd>
									</dl>
								</li> 
							</ul>
						</div>
						<!-- //inner -->
					</div>
					<!-- //page -->
				</div>
				<!-- //sub_cont -->