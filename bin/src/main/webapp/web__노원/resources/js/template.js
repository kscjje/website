
$(function(){
		//파일 첨부 버튼
		$('.btn_file').click(function () {
			$(this).siblings().children('.file').click();
		})
		$('.btn_file').siblings().children(".file").on('change', function () {
				var fileName = $(".file").val();
				$(".upload-name").val(fileName);
			});

		$('.btn_file02').click(function () {
			$(this).siblings().children('.file02').click();
		})
		$('.btn_file02').siblings().children(".file02").on('change', function () {
				var fileName = $(".file02").val();
				$(".upload-name02").val(fileName);
			});

    })

    $(function(){
      // 타이틀 슬라이드 버튼
    $('.allbtn').click(function (e) {
			e.preventDefault()
			$(this).toggleClass('on')
			$('.link_list').slideToggle()
		});
  })

  $(function(){
			//캘린더 버튼
			$('.day_table a').click(function(){
				$('.day_table a').removeClass('on');
				$(this).addClass('on');

			})

  })

	$(function(){
		//캘린더 버튼
		$('.day_table a').click(function(){
			$('.day_table a').removeClass('on');
			$(this).addClass('on');

		})

})

$(function(){
	//팝업 배경 버튼
	$('.btn_pop').click(function(){
		$('body').addClass('hid');

	})
	$('.btn_close').click(function(){
		$('body').removeClass('hid');
	})
	$('.modal-content .btn_area a').click(function(){
		$('body').removeClass('hid');
	})
})
//타이틀 제목 링크 버튼
$(function() {
	$('.link_list li .btn').on('click', function(e){
		e.preventDefault();
		var target = $(this).attr("href");
		$(".tab_con").hide();
		$(target).show();
		$(this).parent().parent('.link_list').slideUp();
		$(this).closest('.link').siblings('.allbtn').removeClass('on')

  	var title = $(this).text();
		$(".link_title").text(title);

	})
});

$(function() {
	$('.date_line a').click(function(e) {
		e.preventDefault();




		$(this).toggleClass('on');
	});

});
