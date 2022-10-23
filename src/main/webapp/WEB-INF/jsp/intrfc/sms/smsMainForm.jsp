<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% pageContext.setAttribute("crlf", "\r\n"); %>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<!-- 
 	파일명 : smsMainForm.jsp.jsp
 	내  용 : SMS 전송 화면
-->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>SPOWISE CMS SMS 전송</title>

<script src="<%=strContext %>/js/jquery-x.x.x.min.js"></script>
<script src="<%= strContext %>/js/common_nsm_sms.js"></script>

<script type="text/javascript">

/*
* SMS 전송 요청 
*/
function fn_doSmsSnd() {
	
	var varSND_PHN_NUM = $("#SND_PHN_NUM").val();
	var varRCV_PHN_NUM = $("#RCV_PHN_NUM").val();
	var varSND_MSG    = $("#SND_MSG").val();
	
	if ((varSND_PHN_NUM === undefined) || (varSND_PHN_NUM == null) || (varSND_PHN_NUM == "")) {
		alert("보내는 번호를 입력하세요");
		return;
	}
	
	if ((varRCV_PHN_NUM === undefined) || (varRCV_PHN_NUM == null) || (varRCV_PHN_NUM == "")) {
		alert("받는 번호를 입력하세요");
		return;
	}
	
	if ((varSND_MSG === undefined) || (varSND_MSG == null) || (varSND_MSG == "")) {
		alert("문자 메시지 내용을 입력하세요");
		return;
	}
	
	if (confirm("문자 메시지 전송을 시작합니다.")) {
	} else {
		return;
	}
	
	// JOB_STAT(업무구분)
	//-----------------------------------
	// 관람, 강연/행사/영화, 교육프로그램
    // EXT        EVT      EDC          	
    
    //JOB_SUBSTAT
	//-----------------------------------
	// 부업무구분
    // A : 필요에 따라 추가            
    
	fn_ajaxSmsOnly("/intrfc/sms/smsSend", $("#SmsForm"), fn_callBack);  
	
}

function fn_callBack(data) {
	
	if (data.RESULT.EXE_YN == 'Y') {
		alert("문자 메시지를 정상으로 전송했습니다.");
		
		location.reload();
		
	} else {
		alert("문자 메시지 전송이 실패했습니다.");
	}
	
}

</script>
</head>
<body>

<form method="post" id="SmsForm">

	<input type="hidden" name="JOB_STAT"     value="EXT" />
	<input type="hidden" name="JOB_SUBSTAT"  value="A" />

    <div>
        <table>
            <tr>
                <td>보내는 번호 </td>
                <td>
                	<input type="text" name="SND_PHN_NUM" id="SND_PHN_NUM" size="30" /> 
                </td>
            </tr>
            <tr>
                <td>받는 번호 </td>
                <td><input type="text" name="RCV_PHN_NUM" id="RCV_PHN_NUM" size="30" /></td>
            </tr>
            <tr>
                <td>문자 메시지 제목 </td>
                <td><input type="text" name="SND_TITLE" id="SND_TITLE" size="50" /></td>
            </tr>            
            <tr>
                <td>문자 메시지 내용 </td>
                <td>
                	<textarea type="text" name="SND_MSG" id="SND_MSG" rows="37" cols="70" /></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="right">
                	<input type="button" value="SMS 전송" onclick="fn_doSmsSnd();" /><br/>
                </td>
            </tr>
        </table>
    </div>
    
   
    
    </form>
    
    <script>
	
	    $("#SND_PHN_NUM").val("01058953262");
	    $("#RCV_PHN_NUM").val("01058953262");
	    $("#SND_TITLE").val("문자 메시지 제목");
	    $("#SND_MSG").val("문자 메시지 내용");

    </script>
    
</body>
</html>

