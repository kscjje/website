<%
 /**
  * @Class Name : incMemyearBenefit.jsp
  * @Description : 연회원 혜택
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.08.20    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.08.20
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="signup2">
	<h4 class="h4 mb5">노원수학문화관 유료회원 고객만이 누릴 수 있는 혜택!</h4>
	<p class="desc mb40">기본혜택 외에도 제휴처의 혜택과 서비스를 만나보세요.</p>
	<ul class="benefit_list two">
		<li>
			<div>
				<img src="<%=strContext%>/images/sub/img_benefit01.png" alt="관람연간할인이미지">
				<span>50%</span>
			</div>
			<dl>
				<dt>관람 할인 횟수 제한없이<br>예약건당 최대 6명까지 할인</dt>
			</dl>
		</li>
		<li>
			<div>
				<img src="<%=strContext%>/images/sub/img_benefit02.png" alt="과학교육연간할인이미지">
				<span>10%</span>
			</div>
			<dl>
				<dt>과학교육 연간 2회 10% 할인<br/>(외부교육제외)</dt>
			</dl> 
		</li>
	</ul>
	<ul class="benefit_list">
		<li>
			<div>
				<img src="<%=strContext%>/images/sub/img_benefit03.png" alt="연간회원할인이미지">
				<span>10%</span>
			</div>
			<dl>
				<dt>유료회원 만료 30일전<br>갱신 시 10% 할인</dt>
			</dl>
		</li>
		<li>
			<div>
				<img src="<%=strContext%>/images/sub/img_benefit04.png" alt="주차장무료이미지">
				<span>무료</span>
			</div>
			<dl>
				<dt>고객 주차장 주차료<br>전액 무료</dt>
			</dl>
		</li>
		<li>
			<div>
				<img src="<%=strContext%>/images/sub/img_benefit05.png" alt="대전오월드아쿠아리움입장권할인이미지">
				<span>할인</span>
			</div>
			<dl>
				<dt>대전오월드, 아쿠아리움<br>입장 시 일정 금액 할인</dt>
			</dl>
		</li>
	</ul>
</div>