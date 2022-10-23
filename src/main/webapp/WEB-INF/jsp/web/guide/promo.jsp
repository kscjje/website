<%
 /**
  * @Class Name : promo.jsp
  * @Description : 대국민 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2020.11.17    김희택          최초 생성
  *
  *  @author 김희택
  *  @since 2020.11.17
  *  @version 1.0
  *  @see
  *
  */
%>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<meta name="HandheldFriendly" content="true">
	<meta name="format-detection" content="telephone=no">
	<meta name="theme-color" content="#013668">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>SPOWISE CMS</title>
	<link rel="stylesheet" href="<%=strContext%>/css/pro_style.css">
	<script type="text/javascript" src="<%=strContext%>/js/jquery-x.x.x.min.js"></script>
</head>
	<div class="promo">
		<div class="top">
			<div class="top_head">
				<div class="logo">
					<p class="img_pc"><img src="<%=strContext%>/images/promo/logo.png" alt="">
					</p>
					<p class="img_m"><img src="<%=strContext%>/images/promo/logo_m.png" alt=""></p>
				</div>
				<div class="link">
					<p class="img_pc"><img src="<%=strContext%>/images/promo/link.png" alt="">
					</p>
					<p class="img_m"><img src="<%=strContext%>/images/promo/link_m.png" alt=""></p>
				</div>
			</div>
			<p class="img_pc"><img src="<%=strContext%>/images/promo/img_ovj.png" alt="">
			</p>
			<p class="img_m ovj"><img src="<%=strContext%>/images/promo/img_ovj_m.png" alt=""></p>
		</div>
		<div class="title">
			<p class="img_pc"><img src="<%=strContext%>/images/promo/img_title.png" alt="">
			</p>
			<p class="img_m"><img src="<%=strContext%>/images/promo/img_title_m.png" alt=""></p>
		</div>
		<div class="p_cont">
			<div class="wrapper">
				<ul class="img_tab">
					<li class="on">
						<p class="img_pc"><img src="<%=strContext%>/images/promo/txt_tab01.png" alt=""></p>
						<p class="img_m"><img src="<%=strContext%>/images/promo/txt_tab01_m.png" alt=""></p>
					</li>
					<li>
						<p class="img_pc"><img src="<%=strContext%>/images/promo/txt_tab02.png" alt=""></p>
						<p class="img_m"><img src="<%=strContext%>/images/promo/txt_tab02_m.png" alt=""></p>
					</li>
				</ul>
				<div class="tab_con">
					<div class="con1">
						<p class="img_pc"><img src="<%=strContext%>/images/promo/img_con1.png" alt="">
						</p>
						<p class="img_m"><img src="<%=strContext%>/images/promo/img_con1_m.png" alt=""></p>
					</div>
					<!-- //con1 -->
					<div class="con2">
						<div>
							<p class="img_pc"><img src="<%=strContext%>/images/promo/img_con2_1.png" alt="">
							</p>
							<p class="img_m"><img src="<%=strContext%>/images/promo/img_con2_1_m.png" alt=""></p>
						</div>
						<div>
							<p class="img_pc"><img src="<%=strContext%>/images/promo/img_con2_2.png" alt="">
							</p>
							<p class="img_m"><img src="<%=strContext%>/images/promo/img_con2_2_m.png" alt=""></p>
						</div>
						<div class="box">
							<p class="img_pc"><img src="<%=strContext%>/images/promo/img_con2_3.png" alt="">
							</p>
							<p class="img_m"><img src="<%=strContext%>/images/promo/img_con2_3_m.png" alt=""></p>
							<!-- p_quest -->
							<div class="p_quest">
								<!-- fold -->
								<ul class="list fold">
									<!-- 활성화 li class: current 추가 -->
									<li class="current">
										<div class="fold_dep">
											<div class="fold_tit">
												<p class="img_pc"><img src="<%=strContext%>/images/promo/txt_q01.png" alt=""></p>
												<p class="img_m"><img src="<%=strContext%>/images/promo/txt_q01_m.png" alt=""></p>
											</div>
											<div class="fold_cont">
												<p>회원제 운영 규정이 개편되어 유료회원 운영규정은 가족회원 제도가 없어지고 개인회원으로 개별 가입하셔야 합니다.</p>
											</div>
										</div>
									</li>
									<li>
										<div class="fold_dep">
											<div class="fold_tit">
												<p class="img_pc"><img src="<%=strContext%>/images/promo/txt_q02.png" alt=""></p>
												<p class="img_m"><img src="<%=strContext%>/images/promo/txt_q02_m.png" alt=""></p>
											</div>
											<div class="fold_cont">
												<p>가족 대표자의 유료회원 만료일자와 동일하며, 마이페이지에서 확인 가능합니다.</p>
												<p class="imp_txt">※ 특별회원의 가족 개별 회원은 유료회원 가입일로부터 1년간입니다.</p>
											</div>
										</div>
									</li>
									<li>
										<div class="fold_dep">
											<div class="fold_tit">
												<p class="img_pc"><img src="<%=strContext%>/images/promo/txt_q03.png" alt=""></p>
												<p class="img_m"><img src="<%=strContext%>/images/promo/txt_q03_m.png" alt=""></p>
											</div>
											<div class="fold_cont">
												<p>추가 가입은 신규 가입하셔야 합니다.</p>
											</div>
										</div>
									</li>
								</ul>
								<!-- //fold -->
							</div>
						</div>
					</div>
					<!-- //con2 -->
				</div>
				<div class="tab_con">
					<div class="con1">
						<p class="img_pc"><img src="<%=strContext%>/images/promo/tab2_img_con1.png" alt="">
						</p>
						<p class="img_m"><img src="<%=strContext%>/images/promo/tab2_img_con1_m.png" alt=""></p>
					</div>
					<!-- //con1 -->
					<div class="con2">
						<p class="img_pc"><img src="<%=strContext%>/images/promo/tab2_img_con2.png" alt="">
						</p>
						<p class="img_m"><img src="<%=strContext%>/images/promo/tab2_img_con2_m.png" alt=""></p>
					</div>
					<!-- //con2 -->
					<div class="con3">
						<p class="img_pc"><img src="<%=strContext%>/images/promo/tab2_img_con3.png" alt="">
						</p>
						<p class="img_m"><img src="<%=strContext%>/images/promo/tab2_img_con3_m.png" alt=""></p>
					</div>
					<!-- //con2 -->
					<div class="con4">
						<p class="img_pc"><img src="<%=strContext%>/images/promo/tab2_img_con4.png" alt="">
						</p>
						<p class="img_m"><img src="<%=strContext%>/images/promo/tab2_img_con4_m.png" alt=""></p>
					</div>
					<!-- //con2 -->
				</div>
			</div>
		</div>
	</div>

</body>

</html>


<script>
	$('.img_tab  li').click(show)

	function show() {
		var index = $(this).index()
		$('.img_tab li').removeClass('on')
		$(this).addClass('on')
		var index = $(this).index()
		$('.tab_con').hide()
		$('.tab_con').eq(index).fadeIn()
	}
	$('.img_tab li').eq(0).trigger('click') //tab




	$('.fold_tit').click(foldCont)

	function foldCont() {
		$('.fold_cont').slideUp();
		$(this).siblings('.fold_cont').stop().slideToggle();
		$(this).parent().parent('li').toggleClass('current');
		$(this).parent().parent('li').siblings().removeClass('current')
	} //질문스크립트
</script>