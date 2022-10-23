<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% pageContext.setAttribute("crlf", "\r\n"); %>

<%
	String strContext = request.getContextPath() + com.hisco.cmm.util.Config.USER_ROOT + "/resources";
%>

<!-- 
 	파일명 : emailMainForm.jsp.jsp
 	내  용 : E-Mail 전송 화면
-->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>SPOWISE CMS 메일 전송</title>

<script src="<%=strContext %>/js/jquery-x.x.x.min.js"></script>
<script src="<%= strContext %>/js/common_nsm_mail.js"></script>

<script type="text/javascript">

/*
* 메일 전송 요청 
*/
function fn_doMailSnd() {
	
	var varMailID    = $("#MAIL_ID").val();
	var varMailTitle = $("#MAIL_TITLE").val();
	var varMailConts = $("#MAIL_CONTENTS").val();
	
	if ((varMailID === undefined) || (varMailID == null) || (varMailID == "")) {
		alert("E-Mail ID를 입력하세요");
		return;
	}
	
	if ((varMailTitle === undefined) || (varMailTitle == null) || (varMailTitle == "")) {
		alert("E-Mail 제목을 입력하세요");
		return;
	}
	
	if ((varMailConts === undefined) || (varMailConts == null) || (varMailConts == "")) {
		alert("E-Mail 내용을 입력하세요");
		return;
	}
	
	if (confirm("메일 전송을 시작합니다.")) {
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
    
	//fn_ajaxEMailOnly("/intrfc/email/eMailSend", {JOB_STAT : "EXT", JOB_SUBSTAT : "A", MAIL_ID : varMailID, MAIL_TITLE : varMailTitle, MAIL_CONTENTS : varMailConts},  fn_callBack);
	
	fn_ajaxEMailOnly("/intrfc/email/eMailSend", $("#EMailForm"), fn_callBack);  
	
}

function fn_callBack(data) {
	
	if (data.RESULT.EXE_YN == 'Y') {
		alert("메일을 정상으로 전송했습니다.");
		
		location.reload();
		
	} else {
		alert("메일 전송이 실패했습니다.");
	}
	
}

</script>
</head>
<body>

<form method="post" id="EMailForm">

	<input type="hidden" name="JOB_STAT"     value="EXT" />
	<input type="hidden" name="JOB_SUBSTAT"  value="A" />

    <div>
        <table>
            <tr>
                <td>E-Mail ID </td>
                <td>
                	<input type="text" name="MAIL_ID" id="MAIL_ID" size="30" /> 
                </td>
            </tr>
            <tr>
                <td>E-Mail 제목 </td>
                <td><input type="text" name="MAIL_TITLE" id="MAIL_TITLE" size="68" /></td>
            </tr>
            <tr>
                <td>E-Mail 내용 </td>
                <td>
                	<textarea type="text" name="MAIL_CONTENTS" id="MAIL_CONTENTS" rows="34" cols="70" /></textarea>
                </td>
            </tr>
            <tr>
                <td>E-Mail 첨부 파일 </td>
                <td>
                	<input multiple="multiple" type="file" name="MAIL_FILE" />
                </td>
            </tr>            
            <tr>
                <td colspan="2" align="right">
                	<input type="button" value="메일전송" onclick="fn_doMailSnd();" /><br/>
                </td>
            </tr>
        </table>
    </div>
    
   
    
    </form>
    
    <script>
	
	    $("#MAIL_ID").val("");
	    $("#MAIL_TITLE").val("메일 제목");
	    $("#MAIL_CONTENTS").val("메일 내용");

    </script>
    
</body>
</html>

