$.datepicker.setDefaults({
  dateFormat: 'yy-mm-dd',
  prevText: '이전 달',
  nextText: '다음 달',
  monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
  monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
  dayNames: ['일', '월', '화', '수', '목', '금', '토'],
  dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
  dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
  showMonthAfterYear: true,
  yearSuffix: '년'
});

function passwordCheck(userID , newPassword1 , newPassword2) {

        // 재입력 일치 여부
        if (newPassword1 != newPassword2) {
            alert("입력한 두 개의 비밀번호가 서로  일치하지 않습니다.");
            return false;
        }

        // 길이
        if(!/^[a-zA-Z0-9!@#$%^&*()?_~]{8,20}$/.test(newPassword1))
        {
            alert("비밀번호는 숫자, 영문, 특수문자 조합으로 8~20자리를 사용해야 합니다.");
            return false;
        }

        // 영문, 숫자, 특수문자 2종 이상 혼용
        var chk = 0;
        if(newPassword1.search(/[0-9]/g) != -1 ) chk ++;
        if(newPassword1.search(/[a-z]/ig)  != -1 ) chk ++;
        if(newPassword1.search(/[!@#$%^&*()?_~]/g)  != -1  ) chk ++;
        if(chk < 2)
        {
            alert("비밀번호는 숫자, 영문, 특수문자를 두가지이상 혼용하여야 합니다.");
            return false;
        }

        // 동일한 문자/숫자 4이상, 연속된 문자
        if(/(\w)\1\1\1/.test(newPassword1) || isContinuedValue(newPassword1))
        {
            alert("비밀번호에 4자 이상의 연속 또는 반복 문자 및 숫자를 사용하실 수 없습니다.");
            return false;
        }

        // 아이디 포함 여부
        if(newPassword1.search(userID)>-1)
        {
            alert("ID가 포함된 비밀번호는 사용하실 수 없습니다.");
            return false;
        }

        return true;

    }

    function isContinuedValue(value) {
        console.log("value = " + value);
        var intCnt1 = 0;
        var intCnt2 = 0;
        var temp0 = "";
        var temp1 = "";
        var temp2 = "";
        var temp3 = "";

        for (var i = 0; i < value.length-3; i++) {
            console.log("=========================");
            temp0 = value.charAt(i);
            temp1 = value.charAt(i + 1);
            temp2 = value.charAt(i + 2);
            temp3 = value.charAt(i + 3);

            console.log(temp0)
            console.log(temp1)
            console.log(temp2)
            console.log(temp3)

            if (temp0.charCodeAt(0) - temp1.charCodeAt(0) == 1
                    && temp1.charCodeAt(0) - temp2.charCodeAt(0) == 1
                    && temp2.charCodeAt(0) - temp3.charCodeAt(0) == 1) {
                intCnt1 = intCnt1 + 1;
            }

            if (temp0.charCodeAt(0) - temp1.charCodeAt(0) == -1
                    && temp1.charCodeAt(0) - temp2.charCodeAt(0) == -1
                    && temp2.charCodeAt(0) - temp3.charCodeAt(0) == -1) {
                intCnt2 = intCnt2 + 1;
            }
            console.log("=========================");
        }

        console.log(intCnt1 > 0 || intCnt2 > 0);
        return (intCnt1 > 0 || intCnt2 > 0);
    }


	// table 태그를 엑셀로 익스포트
	function tableExportExcel(id, title) {
		var tab_text = '<html xmlns:x="urn:schemas-microsoft-com:office:excel">';
		tab_text = tab_text + '<head><meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8">';
		tab_text = tab_text + '<xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>'
		tab_text = tab_text + '<x:Name>Test Sheet</x:Name>';
		tab_text = tab_text + '<x:WorksheetOptions><x:Panes></x:Panes></x:WorksheetOptions></x:ExcelWorksheet>';
		tab_text = tab_text + '</x:ExcelWorksheets></x:ExcelWorkbook></xml></head><body>';
		tab_text = tab_text + "<table border='1px'>";
		var exportTable = $('#' + id).clone();
		exportTable.find('input').each(function (index, elem) { $(elem).remove(); });
		tab_text = tab_text + exportTable.html();
		tab_text = tab_text + '</table></body></html>';
		var data_type = 'data:application/vnd.ms-excel';
		var ua = window.navigator.userAgent;
		var msie = ua.indexOf("MSIE ");
		var fileName = title + '.xls';

		//Explorer 환경에서 다운로드
		if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) {
			if (window.navigator.msSaveBlob) {
				var blob = new Blob([tab_text], {
					type: "application/csv;charset=utf-8;"
				});
				navigator.msSaveBlob(blob, fileName);
			}
		} else {
			var blob2 = new Blob([tab_text], {
				type: "application/csv;charset=utf-8;"
			});
			var filename = fileName;
			var elem = window.document.createElement('a');
			elem.href = window.URL.createObjectURL(blob2);
			elem.download = filename;
			document.body.appendChild(elem);
			elem.click();
			document.body.removeChild(elem);
		}
	}

	 function inputNumberFormat(obj) {
		 if($(obj).val() == ""){
			 $(obj).val("0");
		 }else{
			 $(obj).val( comma(uncomma($(obj).val())) );
		 }
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

	function categoryTreeHtml(LIST){
		var html = "";
		if(LIST){
        	for(var i=0; i<LIST.length ; i++){
    			html += "<tr class='000 expandable-body'>";
    			html += "<td colspan='3' class='border-0 p-0'><div class=\"p-0\"><table class=\"table p-0 m-0\"><tbody class='firstLvl'>";
        		html += "<tr>";
        		html += "   <td class='border-0'>";
        		html += "   <input type='hidden' name='listctgCd' value='"+LIST[i].ctgCd+"'/>\n";
        		if(LIST[i].subCtgrList.length > 0 && LIST[i].subCtgrList[0].ctgCd != null){
        			html += "   <i class=\"fas fa-caret-right fa-fw\"></i>";
        		}
        		html += "   <a href=\"javascript:;\" onclick=\"fn_select_category('"+LIST[i].comcd+"','"+LIST[i].ctgCd+"',this)\">" + LIST[i].ctgNm+"</a></td>";
        		html += " </tr>";

        		if(LIST[i].subCtgrList.length > 0 && LIST[i].subCtgrList[0].ctgCd != null){
        			for(var j=0; j<LIST[i].subCtgrList.length ; j++){
        				var data = LIST[i].subCtgrList[j];

        				html += "<tr class='"+LIST[i].ctgCd+" expandable-body'>";
	        			html += "<td colspan='3' class='border-0 p-0'><div class=\"p-0\"><table class=\"table p-0 m-0\" ><tbody >";
    					html += "<tr >";
		        		html += "   <td style='padding-left:45px' class='border-0'>";
		        		html += "   <input type='hidden' name='listctgCd' value='"+data.ctgCd+"'/>\n";
		        		if(data.subCtgrList.length > 0 && data.subCtgrList[0].ctgCd != null){
		        			html += "   <i class=\"fas fa-caret-right fa-fw\"></i>";
		        		}else{
		        			html += "  &nbsp;&nbsp;&nbsp;";
		        		}

		        		html += "   <a href=\"javascript:;\" onclick=\"fn_select_category('"+data.comcd+"','"+data.ctgCd+"',this)\">" + data.ctgNm+"</a></td>";
		        		html += " </tr>";

		        		if(data.subCtgrList.length > 0 && data.subCtgrList[0].ctgCd != null){
		        			html += "<tr class='thirdLvl'>";
		        			html += "<td colspan='3' class='border-0 p-0'><div class=\"p-0\"><table class=\"table p-0 m-0\"><tbody>";

		        			for(var k=0; k<data.subCtgrList.length ; k++){
		        				var dataSub = data.subCtgrList[k];

		        				html += "<tr class='"+data.ctgCd+"'>";
		        				html += "  <td style='padding-left:80px' class='border-0'><input type='hidden' name='listctgCd' value='"+dataSub.ctgCd+"'/>\n";
				        		html += "  <a href=\"javascript:;\" onclick=\"fn_select_category('"+dataSub.comcd+"','"+dataSub.ctgCd+"',this)\">" + dataSub.ctgNm+"</a></td>";
				        		html += " </tr>";
		        			}
		        			html += "</tbody></table></div></td>";
		        			html += "</tr>";
		        		}
		        		html += "</tbody></table></div></td>";
	        			html += "</tr>";
        			}
        		}
        		html += "</tbody></table></div></td>";
    			html += "</tr>";
        	}
		}
		return html;

	}

	function printHp(self){
		var val = self.val();
		if(val.search(/[^0-9]/) > -1)
			val = val.replace(/[^0-9]/g, '');

		// - 삽입
		if(val.length <= 6)
			val = val.substring(0, 3) +'-'+ val.substring(3);
		else if(val.length <= 10)
			val = val.substring(0, 3) +'-'+ val.substring(3, 6) +'-'+ val.substring(6);
		else
			val = val.substring(0, 3) +'-'+ val.substring(3, 7) +'-'+ val.substring(7, 12);

		self.val(val);
	}

	function phoneFomatter(self) {
		var val = self.val();
		if(val.search(/[^0-9]/) > -1)
			val = val.replace(/[^0-9]/g, '');

		var formatNum = '';
		if (val.length==11) {
			formatNum = val.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
		} else if (val.length==8) {
			formatNum = val.replace(/(\d{4})(\d{4})/, '$1-$2');
		} else {
			if (val.indexOf('02')==0) {
				if (val.length==9) {
					formatNum = val.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
				} else {
					formatNum = val.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
				}
			} else {
				formatNum = val.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
			}
		}
		self.val(formatNum);
	}

	function formToJSON(selector){
		var queryStr = $(selector).serialize();
		var data = {};
		if(queryStr) {
			var kvArr = queryStr.split("&");
			for(var i=0; i<kvArr.length;i++) {
				var kv = kvArr[i].split("=");
				if(kv.length == 2) {
					var k = kv[0];
					var v = kv[1] === undefined ? "" : kv[1];
					data[k] = decodeURI(v);
				}
			}
		}
		return JSON.stringify(data);
	}

	function getByteLength(str){
	    var byteLength= 0;

	    for(var inx=0; inx < str.length; inx++)
	    {
	        var oneChar = escape(str.charAt(inx));
	        if( oneChar.length == 1 )
	            byteLength ++;
	        else if(oneChar.indexOf("%u") != -1)
	            byteLength += 2;
	        else if(oneChar.indexOf("%") != -1)
	            byteLength += oneChar.length/3;
	    }

	    return byteLength;
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

