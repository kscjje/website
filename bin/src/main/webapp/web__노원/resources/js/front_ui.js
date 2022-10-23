$(function () {			
	
	
	var $WIN = $(window); // window jQuery object
	var $DOC = $(document); // document jQuery object
	var $HEADER;

	var HEIGHT = $WIN.height(); // window height
	var WIDTH = $WIN.width(); // window width


	$WIN.resize(function () {
		HEIGHT = $WIN.height();
		WIDTH = $WIN.width();
		$DOC.trigger('scroll');
		if(WIDTH <= 1200) {
			$('body').removeClass('on');
            $('#sub-gnb .fix_menu').click(function(){
                $(this).parent('ul').toggleClass('show_menu');
                $(this).find('a').toggleClass('active');
            });
		}else {
			//$('body').removeClass('on');
		}	

	});
	// window resize

	function gnb() {
			$DOC.on('mousewheel DOMMouseScroll scroll', function (e) {
			var scT = $DOC.scrollTop();
			var topH = $('#top_banner').outerHeight() + $('#top').outerHeight();
			if($('#wrap').hasClass('index')){
				if (scT == 0) {
					$('#header-wrap').removeClass('fixed'); 
				} else if (scT > topH){
					$('#header-wrap').addClass('fixed');
				}else {
					$('#header-wrap').removeClass('fixed'); 
				}
				
				if(scT < 70){
					$('.quick').addClass('on');
				}else {
					$('.quick').removeClass('on');
				}
			
	} else {
		var tH = $('#top').outerHeight();
		if (scT == 0) {
				$('#header-wrap').removeClass('fixed'); 
			}else if(scT > tH) {
				$('#header-wrap').addClass('fixed');
			}else {
				$('#header-wrap').removeClass('fixed');
			}
		
		}
			
}); 
}


	$WIN.load(function () {
		gnb();
		$WIN.resize();
	});
    // 메뉴
    $('#gnb nav > ul > li').on("mouseenter focusin", function () {
        $('#gnb nav > ul > li').removeClass('active');
        $(this).addClass('active');
        $(this).find().children(".depth_wrap").css("display", "block");
		$('.dim').addClass('on');
    });
	$('#gnb nav > ul > li').on("mouseleave focusout", function () {
        $(this).find().children(".depth_wrap").css("display", "none");
    });



    $('.depth2 > ul > li').on("mouseenter focusin", function () {
        $('.depth2 > ul > li').removeClass("on");
        $(this).addClass("on");
    });
	

    $('#gnb nav .last-gnb, #gnb-m .last-gnb').on("focusout", function () {
        $('#gnb nav > ul > li').removeClass('active');
        $('.dim').removeClass('on');
        
    });

    $('#gnb nav > ul > li').hover(
        function () {
            $(this).addClass('active');
        }, function () {
            $(this).removeClass('active');
            $('.dim').removeClass('on');
        }
    );
	
	

   //패밀리박스
   $(".select > div > a").click(function() {
	  //메뉴 선택
	  if($(this).hasClass('on'))
	  {
		$(this).removeClass('on');
		$(this).siblings(".sel_con").slideToggle(200).siblings(".sel_con").slideUp("slow");
	  }
	  else
	  {

		$(this).addClass('on').parent().parent().parent().siblings('.select').find('a').removeClass('on');

		if($(this).siblings(".sel_con") && $(this).siblings(".sel_con").is(":hidden"))
		{
			$(this).siblings(".sel_con").slideToggle(200).siblings(".sel_con").slideDown("slow");
		}

		//선택한 메뉴를 제외하고 다른 메뉴의 하위 메뉴 닫기
		$(this).addClass('on').parent().parent().siblings('.select').find('.sel_con').each(function(index, target){
			if($(target).is(':visible')){
				$(target).slideToggle(200).slideUp("slow");
			}
		});
	  }
});
	
    $('#sub-gnb').each(function (index) {
        var gnb = $(this),
            li_open = gnb.find('li.open');

        li_open.each(function (idx) {
            var self = $(this),
                self_clone = self.clone();

            self_clone.addClass('fix_menu').insertBefore(self.parent().find('li').eq(0));
            if (idx == li_open.length - 1) self_clone.addClass('last_depth').parent().addClass('last_depth');

            self_clone.find('>div').remove();
            self_clone.find('a').attr('href', '#')
        });

        var ld1 = gnb.find('ul.ld1'),
            ld1_parent = ld1.parent(),
            open_lb1 = gnb.find('li.cd1.open').not('.fix_menu').find('div.lb1.open').clone(),
            open_lb2 = gnb.find('li.cd2.open').not('.fix_menu').find('div.lb2.open').clone(),
            open_lb3 = gnb.find('li.cd3.open').not('.fix_menu').find('div.lb3.open').clone(),
            open_lb4 = gnb.find('li.cd4.open').not('.fix_menu').find('div.lb4.open').clone(),
            open_lb5 = gnb.find('li.cd5.open').not('.fix_menu').find('div.lb5.open').clone(),
            open_lb6 = gnb.find('li.cd6.open').not('.fix_menu').find('div.lb6.open').clone(),
            open_lb7 = gnb.find('li.cd7.open').not('.fix_menu').find('div.lb7.open').clone();

        ld1.find('.lb1, .lb2, .lb3, .lb4, .lb5, .lb6, .lb7').remove();

        ld1_parent.append(open_lb1).append(open_lb2).append(open_lb3).append(open_lb4).append(open_lb5).append(open_lb6).append(open_lb7);

        ld1_parent.find('li > div').remove();


        // 메뉴 경로형 처리
        //gnb.find('#menu_in_topmenu a')
        ld1_parent.find('a')
            .unbind()
            .click(function (e) {

                var self = $(this),
                    p_li = self.parent(),
                    p_ul = self.parent().parent();

                if (p_li.hasClass('menu_topmenu ld1')) {
                    if (!p_ul.hasClass('show_menu')) {
                        gnb.find('.show_menu').removeClass('show_menu');
                        p_ul.addClass('show_menu');
                    } else {
                        gnb.find('.show_menu').removeClass('show_menu');
                    }

                    e.preventDefault();
                    return false;
                }
            })
            .bind('mouseenter focus', function (e) {

                var self = $(this),
                    p_li = self.parent(),
                    p_ul = self.parent().parent();
                p_ul.addClass('show_menu');
            })
            .bind('mouseleave blur', function (e) {

                var self = $(this),
                    p_li = self.parent(),
                    p_ul = self.parent().parent();

                p_ul.removeClass('show_menu');
            });
    });
	 //pc사이트맵
	$('#menu').popup({
		pagecontainer: '.container',
		transition: 'all 0.3s',
		scrolllock: true
	});
	
	

//푸터

// 푸터 관련사이트
$(document).on('click', '.site-group-title a', function (e) {
	e.preventDefault();
	$(this).closest('.site-group').toggleClass('active').siblings().removeClass('active');
});


	/***************************************************************************************************
     Mobile
     ***************************************************************************************************/
	
    $('.btn-all').click(function () {
        $('body').addClass('mobile-on');
		$('.dim2').addClass('on');
        return false;
    })
    $('#gnb-m .close, .dim2').click(function () {
        $('body').removeClass('mobile-on');
		$('.dim2').removeClass('on');
        return false;
    })

    // 메뉴
    $('#gnb-m .body .btn-dep1').click(function (e) {
        if ($(this).parent().hasClass('active')) {
            $(this).parent().removeClass('active');
            $(this).siblings('.dep2').css("display", "none");
        } else {
            $(this).parent().addClass('active').siblings().removeClass('active')
            $(this).parent().siblings().children('.dep2').css("display", "none");
            $(this).siblings('.dep2').stop().slideDown(300);
        }
        e.preventDefault();
    });

	
	
});

/*팝업*/
$(function(){
	$('.modal_body').attr("tabindex", 0).focus();
	$('.pop_inner,.view_area').attr("tabindex", 0).focus();
	$('.pop_close').attr("tabindex", 0).focus();
	$.fn.popOpen = function(){
		$(this).click(function(e){
			var s = $(this).attr("href");
			$(s).popup('show');
			$('body').addClass('on');
			$(this).addClass('chk1');
		});
		return this;
	};
	$('.btn_pop').popOpen();		
	$('.btn_pop_close').click(function(){
		$('.btn').focus();
		$('.btn').removeClass('chk1');
	});			
	$('.btn_pop_close').keydown(function(e){
		if(event.keyCode == 9){
			$('.pop_inner,.view_area').focus();
			return false;
		}
	});
	$('.pop_inner,.view_area').keydown(function(e){
		var isShift = window.event.shiftKey ? true : false;
		if(isShift && (e.keyCode == 9)){
			$('.btn_pop_close').focus();
			return false;
		}
	});
	
});

function pop_close(){
	$('.modal').popup('hide');
	$('body').removeClass('on');
}


