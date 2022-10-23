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
					<a href="#">뒤로가기 버튼</a>
					<h3>설문조사 작성</h3>
				</div>
				<!--//sub_top -->
				<div class="inform_area">
					<div class="img_con">
						<img src="<c:url value="${webDir }"/>/resources/images/data/img_data11.jpg" alt="알림마당 설문조사 메인 이미지">
					</div>
					<h4>평생학습포털 강의평가 설문1</h4>
					<div class="ex_box">
						설문조사 관련된 내용에 대하여 설명입니다. 설문조사 관련된 내용에 대하여 설명입니다.설문조사 관련된 내용에 대하여 설명입니다. 설문조사 관련된 내용에 대하여 설명입니다.
						설문조사 관련된 내용에 대하여 설명입니다. 설문조사 관련된 내용에 대하여 설명입니다.설문조사 관련된 내용에 대하여 설명입니다. 설문조사 관련된 내용에 대하여 설명입니다.
						설문조사 관련된 내용에 대하여 설명입니다. 설문조사 관련된 내용에 대하여 설명입니다.
					</div>
					<ul class="question_list">
						<li>
							<p class="title">1. 주관식의 관련 설문의 대한 질문입니다. 동해물과 백수산이 어쩌고 저쩌고 마르고 닳도록 합니다.
							</p>
							<textarea placeholder="(내용을 작성해 주세요)
							"></textarea>
						</li>
						<li>
							<p class="title">2. 당신은 강좌신청 수업을 최근 1년사이 들었나요? (당신이 "강좌신청 안함”을 선택시 경우,  다음 3~5문제는 풀지 않아도 됩니다) 
							</p>
							<div class="flexbox">
								<div class="chk_box">
									<input type="checkbox" id="c1" checked="checked" name="chked" class="check_agree">
									<label for="c1">강좌신청 수업들음</label>
								</div>
								<div class="chk_box">
									<input type="checkbox" id="c2" name="chked" class="check_disabled">
									<label for="c2">강좌신청 안함 </label>
								</div>
							</div>
							<div class="ex_box">
							<i class="mark"></i>	[3~5]항목은 강좌신청한 고객들 한에서 질문이 가능합니다.
							</div>
						</li>
						<li>
							<p class="title">3. 선지형의 관련 설문의 대한 질문입니다. 동해물과 백수산이 어쩌고 저쩌고 마르고 닳도록 동해물과 백두산입니다.
							</p>
							<div class="flexbox">
								<div class="chk_box">
									<input type="checkbox" id="c3" name="chked">
									<label for="c3">빨강</label>
								</div>
								<div class="chk_box">
									<input type="checkbox" id="c3_1" name="chked">
									<label for="c3_1">노랑 </label>
								</div>
								<div class="chk_box">
									<input type="checkbox" id="c3_2" name="chked">
									<label for="c3_2">파랑 </label>
								</div>
								<div class="chk_box">
									<input type="checkbox" id="c3_3" name="chked">
									<label for="c3_3">다홍 </label>
								</div>
								<div class="chk_box">
									<input type="checkbox" id="c3_4" name="chked">
									<label for="c3_4">주황 </label>
								</div>
							</div>
						</li>
					</ul>
					<p class="last_ask">위의 내용을 기반으로 설문을 마치겠습니까? </p>
				</div>
				<!--// inform_area  -->

			
				<div class="btn_area">
					<a href="#none" class="btn_s1_c0">취소</a>
					<a href="#none" class="btn_s1_c1">설문 제출</a>
				</div>
				<!--// btn_area  -->
			</div>
			<!--// sub_content  -->			

</body>