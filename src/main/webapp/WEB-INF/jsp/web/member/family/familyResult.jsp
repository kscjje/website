<%
 /**
  * @Class Name : familyResult.jsp
  * @Description : 유료회원 전환  성공
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.11.03  김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.11.03
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../include/top.jsp"%>
<div class="sub_cont" id="container">
					<div class="inner">
						<h3 class="tit mb20">가족회원 등록</h3>


						<p class="desc mb70">노원수학문화관과 함께 미래의 꿈을 만들어 가시기 바랍니다.</p>
						<div class="mypage_wrap bor">
							<c:choose>
							<c:when test="${resultMsg eq 'OK' }">
								<!-- signup -->
								<div class="signup2">
									<h4 class="h4">개별 유료회원가입이 완료되었습니다.</h4>
									<!-- join_end -->
									<div class="join_end">
										<ul>
											<li>
												<dl>
													<dt>아이디</dt>
													<dd><c:out value="${memberVO.id }"/></dd>
												</dl>
											</li>
											<li>
												<dl>
													<dt>이름</dt>
													<dd><c:out value="${memberVO.memNm }"/></dd>
												</dl>
											</li>
										</ul>
									</div>
									<!-- //join_end -->
									<!-- ex_txt -->
									<fmt:parseDate value="${memberVO.anlmbEdate }" var="ymd" pattern="yyyyMMdd"/>
									<div class="ex_txt center">
										가족회원에서 개별 가입된 유료회원으로서 <em class="red">
										<fmt:formatDate value="${ymd }"  pattern="yyyy년 MM월 dd일"/></em>까지<br> 유료회원과 동일한 혜택을 받으실 수 있습니다. 감사합니다.
									</div>
									<!-- //ex_txt -->
								</div>
								<!-- //signup -->
							</c:when>
							<c:otherwise>
								<!-- signup -->
								<div class="signup2">
									<h4 class="h4">다시 한번 확인 해 주십시오</h4>
									<div class="join_end">
										<ul>
											<li>
												<dl>
													<dt>아이디</dt>
													<dd><c:out value="${memberVO.id }"/></dd>
												</dl>
											</li>
											<li>
												<dl>
													<dt>이름</dt>
													<dd><c:out value="${memberVO.memNm }"/></dd>
												</dl>
											</li>
										</ul>

										<div class="join_end">
                                   		 <p><i><img src="<%=strContext%>/images/sub/ico_notice03.png" alt="유의사항아이콘"></i> ${resultMsg }</p>
                               		 	</div>
									</div>
								</div>
								<!-- //signup -->
							</c:otherwise>
							</c:choose>
						</div>
						<!-- //mypage_wrap -->
						<ul class="btn_area long">
							<c:choose>
								<c:when test="${resultMsg eq 'OK' }">
									<li><a href="//www.hisco.co.kr/web/main" class="btn_ty_m3_c1">노원수학문화관 홈으로</a></li>
									<li><a href="/web/main" class="btn_ty_m3_c5">통합예약 서비스 홈으로</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="/web/member/family/checkFamily" class="btn_ty_m3_c1">다시 인증하기</a></li>
									<li><a href="/web/main" class="btn_ty_m3_c5">통합예약 서비스 홈으로</a></li>
								</c:otherwise>
							</c:choose>

						</ul>
						<!-- //btn_area -->
					</div>
					<!-- //inner -->
				</div>
				<!-- //sub_cont -->
<script>
</script>