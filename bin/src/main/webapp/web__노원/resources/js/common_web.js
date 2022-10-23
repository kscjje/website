$(function(){

	////console.log("call common_web.js");

	//멤버십카드
	$('.barcode').click(function(){
		$('body').addClass('hid');
		$('.m_card').addClass('open');
		$('.dim').addClass('on');
	});//헤더바코드아이콘클릭
	$('.m_card .close').click(function(){
		$('body').removeClass('hid');
		$('.m_card').removeClass('open');
		$('.dim').removeClass('on');
	});//멤버십카드닫기버튼

});


/**
 * 좌측문자열채우기
 * @params
 * length : 최대 채우고자 하는 길이
 * str : 채우고자하는 문자(char)
 */
String.prototype.lpad = function(length, char) {
    var str = this;
    if (char.length > length) {
        console.log("채우고자 하는 문자열이 요청 길이보다 큽니다");
        return str + "";
    }
    while (str.length < length){
    	str = char + str;
    }
    str = str.length >= length ? str.substring(0, length) : str;
    return str;
};

/**
 * 우측문자열채우기
 * @params
 * length : 최대 채우고자 하는 길이
 * str : 채우고자하는 문자(char)
 */

String.prototype.rpad = function(length, char) {
    var str = this;
    if (char.length > length) {
        console.log("오류 : 채우고자 하는 문자열이 요청 길이보다 큽니다");
        return str + "";
    }
    while (str.length < length)
        str += char;
    str = str.length >= length ? str.substring(0, length) : str;
    return str;
};

/**
 * 문자열 끼우기
 * @params
 * start : 시작 index
 * rmv : 지우고자 하는 문자열 갯수
 * str : 채우고자하는 문자(char)
 */

String.prototype.cSplice = function(start, rmv, newSubStr) {
    return this.slice(0, start) + newSubStr + this.slice(start + Math.abs(rmv));
};

/**
 * 요일 구하기
 * @params
 * 요일 : 숫자 0-6
 */

String.prototype.getDoW = function() {
	var dow = ''
	var dowTemp = ['일요일','월요일','화요일','수요일','목요일','금요일','토요일']
	dow = dowTemp[this]
    return dow;
};


/**
 * 쿠키설정. 
 * 만료일시: 현재일시 + 원하는일수 
 * @params
 * name :  속성명
 * value : 값
 * days : 만료기간
 */
function setCookie(name, value, days) {
	  var exdate = new Date();
	  exdate.setDate(exdate.getDate() + days);
	  // 설정 일수만큼 현재시간에 만료값으로 지정

	  var cookie_value = value + ((days == null) ? '' : '; path=/; expires=' + exdate.toUTCString());
	  document.cookie = name + '=' + cookie_value;
}

/**
 * 쿠키설정. 00:00 시 기준 쿠키 설정하기  
 * expiredays 의 새벽  00:00:00 까지 쿠키 설정 
 */
function setCookieAt00(name, value, expiredays ) {
	var exdate = new Date();   
	exdate = new Date(parseInt(exdate.getTime() / 86400000) * 86400000 + 54000000);  
	
	if ( exdate > new Date() ) {  
		expiredays = expiredays - 1;  
	}  
	exdate.setDate( exdate.getDate() + expiredays );   
	document.cookie = name + '=' + value + '; path=/; expires=' + exdate.toUTCString();
}


/**
 * 문자열 끼우기
 * @params
 * name : 쿠키 속성이름
 */

function getCookie(name) {
	  var x, y;
	  var val = document.cookie.split(';');

	  for (var i = 0; i < val.length; i++) {
	    x = val[i].substr(0, val[i].indexOf('='));
	    y = val[i].substr(val[i].indexOf('=') + 1);
	    x = x.replace(/^\s+|\s+$/g, ''); // 앞과 뒤의 공백 제거하기
	    if (x == name) {
	      return unescape(y); // unescape로 디코딩 후 값 리턴
	    }
	  }
	}

/**
 * 날짜형태 반환
 * @params
 * str : 문자열
 * spStr : 중간에 삽입될 문자 (bar , comma)
 */

String.prototype.toDate = function(spStr) {
    return this.substr(0,4) + spStr + this.substr(4,2)+spStr + this.substr(6,2);
};

/**
 * 모바일 / pc 구분값 반환
 * @params
 */

function fn_getAgent(){
	var isMb = 'pc'
	if(navigator.userAgent.match(/Android|Mobile|iP(hone|od|ad)|BlackBerry|IEMobile|Kindle|NetFront|Silk-Accelerated|(hpw|web)OS|Fennec|Minimo|Opera M(obi|ini)|Blazer|Dolfin|Dolphin|Skyfire|Zune/)){
		isMb = 'mb'
		}
	return isMb;

}

function fn_login_check(){
	$.ajax({
        type : "GET",
        url  : '/web/loginCheckAjax',
        dataType: "json",
        cache: false,
        success: function(data) {
        	if(data.result == "FAIL"){
        		alert("로그아웃 되었습니다.\n로그인 후 다시 시도해 주시기 바랍니다.");
        		window.location.replace("/web/main");
        	}

        }, error: function() {
        }
    });
}

function fn_today_ticket(){
	if($('.ticket').length){
       	$('body').addClass('on');
   		$('.btn_ticket').hide();
   		$('.ticket').addClass('open');
   		$('.dim').fadeIn().addClass('on');

   		$('.btn_close').click();
	}else{
		//$('body').showLoading();
		 $.ajax({
		        type : "GET",
		        url  : '/web/main/ticketAjax',
		        dataType: "html",
		        cache: false,
		        success: function(data) {
		        	//$('body').hideLoading();
		        	var contents = $.parseHTML(data);
		        	if($(contents).find('.ticket_box').length > 0){
			        	$('body').append(data);
			        	$('.btn_close').click();
		        	}else{
		        		alert("오늘의 티켓이 없습니다.");
		        	}

		        }, error: function() {
		        	$('body').hideLoading();
		        	alert("Server Error.");
		        }
		    });
	}

}

function loginMsg(){
	alert("로그인 후 이용해 주세요.");
}
/**
 * ajax
 * @params
 * url
 * paramData : Parameter
 * callBack  : callback method
 */
function fn_ajaxSendData(url, paramData, callBack , csrfParam , csrfValue) {
    $.ajax({
        type : "POST",
        url  : url,
        async : true,
        //data : JSON.stringify(paramData),
        data : paramData,
        //contentType : 'application/json;charset=UTF-8',
        dataType: "json",
        beforeSend : function(xhr){
        	if(csrfParam){
        		xhr.setRequestHeader(csrfParam, csrfValue);
        	}
        },
        success: function(data) {
        	if(data != null){
        		if((data.ERROR_CODE === undefined) || (data.ERROR_CODE == null) || (data.ERROR_CODE == '')){
        		}else{

        			if(data.ERROR_CODE == -1){
            			alert(data.ERROR_MSG);
            			return;
        			}
        			//$.messager.alert("", data.ERROR_MSG);
        		}
        	}

        	callBack(data);

        }, error: function(jqXHR, exception) {
        	var varStatus = jqXHR.status;

        	if(varStatus == "403"){
        		alert("접근 기한이 만료되었거나 권한이 없습니다.\n처음부터 다시 시도해 주시기 바랍니다.");
        		top.location.replace("/web/main");
        	} else{
        		alert("시스템 오류 입니다 .");
        	}
        }
    });
}

/**
 * ajax(Interface 전용)
 * @params
 * url
 * paramData : Parameter
 * callBack  : callback method
 */
function fn_ajaxIfOnly(url, paramData, callBack) {
    $.ajax({
        type : "POST",
        url  : url,
        async : true,
        data : paramData,
        dataType: "json",
        success: function(data) {
        	if(data != null){
        		if((data.ERROR_CODE === undefined) || (data.ERROR_CODE == null) || (data.ERROR_CODE == '')){
        		}else{

        			if(data.ERROR_CODE == -1){
            			alert(data.ERROR_MSG);
            			return;
        			}
        		}
        	}

        	callBack(data);

        }, error: function() {
        	alert("내부 이슈가 있습니다.");
        }
    });
}


/**
 * window.open 가운데 위치
 * @params
 * url
 * title
 * option
 */
function fn_centerWindow(url, title, width, height, option) {
	var varPopupWidth  = width;
	var varPopupHeight = height;

	var popupX = (window.screen.width / 2) - (varPopupWidth / 2);
	var popupY = (window.screen.height / 2) - (varPopupHeight / 2);

	var varWinHandle = window.open(url, title, 'height=' + varPopupHeight  + ', width=' + varPopupWidth  + ', left='+ popupX + ', top='+ popupY + ',' + option);

	if(varWinHandle == null){
		alert('** 휴대전화 본인인증 **\n팝업 차단을 해제해주시기 바랍니다.\n\n ※ 윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다.');
	}else{
		varWinHandle.focus();
	}
}

function isContinuedValue(value) {
    var intCnt1 = 0;
    var intCnt2 = 0;
    var temp0 = "";
    var temp1 = "";
    var temp2 = "";

    for (var i = 0; i < value.length-2; i++) {
        temp0 = value.charAt(i);
        temp1 = value.charAt(i + 1);
        temp2 = value.charAt(i + 2);

        if (temp0.charCodeAt(0) - temp1.charCodeAt(0) == 1
                && temp1.charCodeAt(0) - temp2.charCodeAt(0) == 1) {
            intCnt1 = intCnt1 + 1;
        }

        if (temp0.charCodeAt(0) - temp1.charCodeAt(0) == -1
                && temp1.charCodeAt(0) - temp2.charCodeAt(0) == -1) {
            intCnt2 = intCnt2 + 1;
        }
    }

    return (intCnt1 > 0 || intCnt2 > 0);
}

function isBirthValue(value){
	var birth = $('#birthDate').val().split("-").join("");

	var birthYear = birth.substring(0,4);
	var birthDay = birth.substring(4,8);
	if(value.search(birthYear)>-1){
        return true;
    }else if(value.search(birthDay)>-1){
    	return true;
    }else{
    	return  false;
    }
}

function isIdcheckValue(value){
	var temp = "";
    var userId = $('#id').val();
    var flag = false;
    for (var i = 0; i < userId.length-2; i++) {
        temp = userId.substring(i , i+3);
        if(value.search(temp)>-1){
        	flag = true;
        }
    }
    return flag;
}
function isHphoneValue(value){
	var hp = $('#hp').val();
	if(value.search(hp.substring(3,11))>-1){
        return true;
    }else if(value.search(hp.substring(7,11))>-1){
    	return true;
    }else{
    	return false;
    }
}
function passwdValidation(newPassword){
	if(newPassword == ""){
		return '비밀번호를 입력해주십시오';
	}else{
		// 영문, 숫자, 특수문자 2종 이상 혼용
        var chk = 0;
        if(newPassword.search(/[0-9]/g) != -1 ) chk ++;
        if(newPassword.search(/[a-z]/ig)  != -1 ) chk ++;
        if(newPassword.search(/[!@#$%^&*()?_~]/g)  != -1  ) chk ++;
        if(chk < 2)
        {
        	return "비밀번호는 숫자, 영문, 특수문자를 두가지 이상 혼용하여야 합니다.";
        }

        var minLength = 8;
        if(chk < 3) minLength = 10;

		// 길이
        if(newPassword.length < minLength || newPassword.length > 20)
        {
        	if(minLength == 8){
        		return "비밀번호는 8자리 이상을 사용해야 합니다.";
        	}else{
        		return "숫자,영문,특수문자 중 두가지만 사용시  10자리 이상 사용해야 합니다.";
        	}
        }

     // 동일한 문자/숫자 3이상, 연속된 문자
        if(/(\w)\1\1/.test(newPassword) || isContinuedValue(newPassword))
        {
        	return "3자 이상의 연속 또는 반복 문자 및 숫자를 사용하실 수 없습니다.";
        }
        if(isBirthValue(newPassword))
        {
        	return "생일의 년도 또는 생일의 월일이 포함된 비밀번호는 사용하실 수 없습니다.";
        }
        if(isIdcheckValue(newPassword))
        {
        	return "아이디의 3자리 이상 포함된 비밀번호는 사용하실 수  없습니다.";
        }
        if(isHphoneValue(newPassword))
        {
        	return "핸드폰번호의  마지막 4자리 또는 8자리가 포함된 비밀번호는 사용하실 수  없습니다.";
        }

		return '사용가능한 비밀번호 입니다.';
	}
}

function passwdValidationNew(newPassword){
	if(newPassword == ""){
		return '비밀번호를 입력해주십시오';
	}else{
		// 영문, 숫자, 특수문자 2종 이상 혼용
        var chk = 0;
        if(newPassword.search(/[0-9]/g) != -1 ) chk ++;
        if(newPassword.search(/[a-z]/ig)  != -1 ) chk ++;
        if(newPassword.search(/[!@#$%^&*()?_~]/g)  != -1  ) chk ++;
        if(chk < 2)
        {
        	return "비밀번호는 숫자, 영문, 특수문자를 두가지 이상 혼용하여야 합니다.";
        }

        var minLength = 8;
        if(chk < 3) minLength = 10;

		// 길이
        if(newPassword.length < minLength || newPassword.length > 20)
        {
        	if(minLength == 8){
        		return "비밀번호는 8자리 이상을 사용해야 합니다.";
        	}else{
        		return "숫자,영문,특수문자 중 두가지만 사용시  10자리 이상 사용해야 합니다.";
        	}
        }

        // 동일한 문자/숫자 3이상, 연속된 문자
        if(/(\w)\1\1/.test(newPassword) || isContinuedValue(newPassword))
        {
        	return "3자 이상의 연속 또는 반복 문자 및 숫자를 사용하실 수 없습니다.";
        }
        if(isBirthValue(newPassword))
        {
        	return "생일의 년도 또는 생일의 월일이 포함된 비밀번호는 사용하실 수 없습니다.";
        }
        if(isIdcheckValue(newPassword))
        {
        	return "아이디의 3자리 이상 포함된 비밀번호는 사용하실 수  없습니다.";
        }
        
        /* 
        if(isHphoneValue(newPassword))
        {
        	return "핸드폰번호의  마지막 4자리 또는 8자리가 포함된 비밀번호는 사용하실 수  없습니다.";
        }
        */

		return '사용가능한 비밀번호 입니다.';
	}
}

// 할인금액 계산
function fn_pay_updown(unit,type,discRate , price,cnt){

	var dcAmount = cnt * parseInt(price) * parseInt(discRate) * 0.01;

	if(type == "1000"){
		//없음
		dcAmount = Math.round(dcAmount);
	}else if(unit == "1010"){
		//원단위
		dcAmount = dcAmount/10.0;
		if(type == "1010"){
			//절상
			dcAmount = Math.ceil(dcAmount)*10;
		}else{
			//절하
			dcAmount = Math.floor(dcAmount)*10;
		}
	}else if(unit == "1020"){
		//원단위
		dcAmount = dcAmount/100.0;
		if(type == "1010"){
			//절상
			dcAmount = Math.ceil(dcAmount)*100;
		}else{
			//절하
			dcAmount = Math.floor(dcAmount)*100;
		}
	}else if(unit == "1030"){
		//원단위
		dcAmount = dcAmount/1000.0;
		if(type == "1010"){
			//절상
			dcAmount = Math.ceil(dcAmount)*1000;
		}else{
			//절하
			dcAmount = Math.floor(dcAmount)*1000;
		}
	}else{
		dcAmount = Math.round(dcAmount);
	}

	return  dcAmount;
}

function fn_qs_json(qs) {
  //파라메터별 분리
  var pairs = qs.split('&');

  var result = {};
  //각 파라메터별 key/val 처리
  pairs.forEach(function(pair) {
      pair = pair.split('=');
      pair[0] = pair[0].replace('#','')
      result[pair[0]] = decodeURIComponent(pair[1] || '');
  });

  return JSON.parse(JSON.stringify(result))
}

/* ********************************************************
* PROTOTYPE JS FUNCTION
******************************************************** */
String.prototype.trim = function(){
	return this.replace(/^\s+|\s+$/g, "");
}

String.prototype.replaceAll = function(src, repl){
	 var str = this;
	 if(src == repl){return str;}
	 while(str.indexOf(src) != -1) {
	 	str = str.replace(src, repl);
	 }
	 return str;
}

function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

function uncomma(str) {
    str = String(str);
    str = str.replace(/[^\d]+/g, '');
    return parseInt(str);
}