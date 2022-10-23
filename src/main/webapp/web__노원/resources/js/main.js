$(function () {
//메인비주얼
	var interleaveOffset = 0.5;

	var mainVisual = new Swiper('.vis_slider .swiper-container', {
		slidesPerView: 'auto',
		loop: false,
		loopedSlides: 6,
		speed: 1000,
		spaceBetween: 40,
		grabCursor: true,
		watchSlidesProgress: true,
		mousewheelControl: true,
		keyboardControl: true,
		autoplay: {
			delay: 5000
		},
		pagination: {
			//el: '.swiper-pagination',
			el: '.vis_slider .swiper-pagination',/*12.17 jhj 수정*/
			type: 'fraction',
		},
		navigation: {
			nextEl: ".vis_slider .next",
			prevEl: ".vis_slider .prev"
		},
		breakpoints: {
			768: {
				//slidesPerView: 3,
				spaceBetween: 10,
			},
			500: {
				slidesPerView: 2,
				spaceBetween: 10,
			},

		},
		on: {
			progress: function () {
				var swiper = this;
				for (var i = 0; i < swiper.slides.length; i++) {
					var slideProgress = swiper.slides[i].progress;
					var innerOffset = swiper.width * interleaveOffset;
					var innerTranslate = slideProgress * innerOffset;
					/*swiper.slides[i].querySelector(".slide").style.transform =
						"translate3d(" + innerTranslate + "px, 0, 0)";*/
				}
			},
			slideChangeTransitionStart: function (slider) {
				var wrapper = this.wrapperEl;
				var container = $(wrapper).parents('.swiper-container');
				var progressBar = container.find('.swiper-progress');
				progressBar.removeClass('full');
			},
			slideChangeTransitionEnd: function (slider) {
				var wrapper = this.wrapperEl;
				var container = $(wrapper).parents('.swiper-container');
				var progressBar = container.find('.swiper-progress');
				progressBar.addClass('full');
			},


			touchStart: function () {
				var swiper = this;
				for (var i = 0; i < swiper.slides.length; i++) {
					swiper.slides[i].style.transition = "";
				}
			},
			setTransition: function (speed) {
				var swiper = this;
				for (var i = 0; i < swiper.slides.length; i++) {
					swiper.slides[i].style.transition = speed + "ms";
					swiper.slides[i].querySelector(".slide").style.transition =
						speed + "ms";
				}
			}

		}
	})

	$('.vis_slider .swiper-slide a').focusin(function () {
		//console.log($(this).parent().index());
		mainVisual.slideTo($(this).parent().index(), 1000);
		return false;
	});

	$('.vis_slider .play-stop').click(function () {
		$(this).toggleClass('play');
		if ($(this).hasClass('play')) {
			mainVisual.autoplay.stop();

		} else {
			mainVisual.autoplay.start();

		}
	})


	//섹션1
	// 검색 tab
	$('.banner_wrap').hide();
	$('#tab-1.banner_wrap').show();

	$('.tab_menu > li > a').click(function () {
		$('.tab_menu > li > a').removeClass('active');
		$(this).addClass('active');
		$('.banner_wrap').hide();
		var activeTab = $(this).attr('rel');
		$('#' + activeTab).fadeIn();
		$('.banner_wrap .wrapper').slick('setPosition');
	});
	//배너슬라이드
	var bSlider = $('.banner_wrap .wrapper').slick({
		slidesToShow: 6,
		slidesToScroll: 1,
		infinite: false,
		arrows: true,
		prevArrow: $('.banner_wrap .prev'),
		nextArrow: $('.banner_wrap .next'),
		responsive: [
			{
				breakpoint: 900,
				settings: {
					slidesToShow: 4,

				}
					},
			{
				breakpoint: 500,
				settings: {
					slidesToShow: 3,
					slidesToScroll: 3,
					variableWidth: true,
				}
					}
		]
	});

	//섹션3
	//탭

	$('.noti_wrap .tit a').click(function () {
		var $this = $(this);
		var index = $this.index();

		$this.addClass('on');
		$this.siblings('a.on').removeClass('on');

		var $current = $('.tab_con.active');
		var $post = $('.tab_con').eq(index);

		$current.removeClass('active');
		$post.addClass('active');

		$('.noti ul').slick('setPosition');
	});

	var notiSlider = $('.noti ul');
	var slickOptions = {
		slidesToShow: 3,
		slidesToScroll: 1,
		infinite: true,
		arrows: true,
		prevArrow: $('.noti .slick-prev'),
		nextArrow: $('.noti .slick-next'),
		responsive: [
			{
				breakpoint: 1200,
				settings: {
					slidesToShow: 2,

				}
					},

		]
	};
	$(window).on('load resize', function () {
		if ($(window).width() < 991) {
			notiSlider.slick('unslick');
		} else {
			notiSlider.not('.slick-initialized').slick(slickOptions);
		}
	});





});

/*회원종류선택, 회원가입 본인인증, 보인인증찾기, */
$(function() {
	$('.detail_tab ul li .btn').click(function(e) {
		var target = $(this).attr("href");
		if(target.indexOf("#") == 0){
			e.preventDefault();
			$('.detail_tab ul li .btn').removeClass('on');
			$(this).addClass('on');
		}
	});

	$('.tab_link li a').click(function(e) {
		var target = $(this).attr("href");
		if(target.indexOf("#") == 0){
			e.preventDefault();
			$('.tab_link li a').removeClass('on');
			$(this).addClass('on');
		}
	});


});

$(function() {
	$('.detail_tab li .btn, .tab_link li a').on('click', function(e){
		var target = $(this).attr("href");
		if(target.indexOf("#") == 0){
			e.preventDefault();

			$(".tab_con").hide();
			$(target).show();
		}


	})
});
