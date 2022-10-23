<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>

<div class="captcha_print">
	<div class="captcha_print_image" style="float:left">
		<img id="captcha_string_image" src="<c:url value="/intrfc/captcha/string"/>" alt="자동입력 방지 문구"/>
	</div>
	<div class="captcha_print_button1" style="float:left;">
		<div style="margin-left:10px;margin-top:8px">
			<button type="button" class="process_captcha_reload" style="border:1px solid #000">새로고침</button>
			<button type="button" class="process_captcha_audio"  style="border:1px solid #000">음성듣기</button>
		</div>
		<div id="captcha_audio_area" style="display: block"></div>
	</div>
	<div class="captcha_print_input" >
		<form:input path="captcha" size="20" maxlength="6" placeholder="위 숫자를 입력해 주세요." title="위 숫자를 입력해 주세요" style="margin-top:10px"/>
		<form:errors path="captcha" cssClass="input-error"/>
	</div>

	<script type="text/javascript">
	//<![CDATA[
	jQuery(function($) {
		// 새로고침
		$('button.process_captcha_reload').click(function(e) {
			var img = $('#captcha_string_image'),
				img_src = img.attr('src');
			if (img_src.indexOf('?') > -1)
			{
				img_src = img_src.substring(0, img_src.indexOf('?'));
			}

			img_src += '?time='+ (new Date()).getTime();

			img.attr('src', img_src);
		});

		// 음성듣기
		$('button.process_captcha_audio').click(function(e) {
			var captcha_audio_area = $('#captcha_audio_area'),
				captcha_audio_url =('/intrfc/captcha/audio?time='+ (new Date()).getTime()).replace(/\/{1,}/g, '/'),
				uAgent = navigator.userAgent.toLowerCase(),
				isMsIe = uAgent.indexOf('msie') > -1 || uAgent.indexOf('trident') > -1;

			if (isMsIe) {
					captcha_audio_area.html('<bgsound src="'+ captcha_audio_url +'"/>');
			} else if (!!document.createElement('audio').canPlayType) {
				try{
					new Audio(captcha_audio_url).play();
				}catch(e) {
					captcha_audio_area.html('<bgsound src="'+ captcha_audio_url +'"/>');
				}
			} else {
				window.open(captcha_audio_url, 'captcha_audio', 'width=100, height=100');
			}
		});

		// 확인
		$('button.process_captcha_check').click(function(e) {
			var captcha = $('#captcha, input[name="captcha"]'),
				captcha_val = captcha.val();

			if (captcha_val == '') {
				alert('자동입력 방지 문자를 이미지에 보이는 문구나 스피커를 통해 들리는 숫자를 입력하세요.');
				captcha.focus();
			} else {
				$.ajax({
					url : "/intrfc/captcha/check",
					method : 'POST',
					data : {
						time : (new Date()).getTime(),
						answer : captcha_val
					},
					dataType : 'json',
					error : function() {
						alert('오류가 발생하였습니다.\n다시 확인해 주시기 바랍니다.');
					},
					success : function(data) {
						if (data.result == "OK") {
							alert("확인완료");
						} else {
							alert("자동입력 방지 문자가 맞지 않습니다.");
						}
					}
				});
			}
		});
	});
	//]]>
	</script>
</div>