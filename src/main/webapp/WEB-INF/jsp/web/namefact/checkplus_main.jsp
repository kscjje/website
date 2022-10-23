<%@ page language="java" contentType="text/html;charset=euc-kr" %>

<%
    NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
    
    /* String sSiteCode = "BU570";			// NICE�κ��� �ο����� ����Ʈ �ڵ�
    String sSitePassword = "pknUp1SHua9j";		// NICE�κ��� �ο����� ����Ʈ �н����� */
    String sSiteCode = "BZ166";			// NICE�κ��� �ο����� ����Ʈ �ڵ�
    String sSitePassword = "YakwKIHO4SBN";		// NICE�κ��� �ο����� ����Ʈ �н�����
    
    String sRequestNumber = "REQ0000000001";        	// ��û ��ȣ, �̴� ����/�����Ŀ� ���� ������ �ǵ����ְ� �ǹǷ� 
                                                    	// ��ü���� �����ϰ� �����Ͽ� ���ų�, �Ʒ��� ���� �����Ѵ�.
    sRequestNumber = niceCheck.getRequestNO(sSiteCode);
  	session.setAttribute("REQ_SEQ" , sRequestNumber);	// ��ŷ���� ������ ���Ͽ� ������ ���ٸ�, ���ǿ� ��û��ȣ�� �ִ´�.
  	
   	String sAuthType = "M";      	// ������ �⺻ ����ȭ��, M: �ڵ���, C: �ſ�ī��, X: ����������
   	
   	String popgubun 	= "N";		//Y : ��ҹ�ư ���� / N : ��ҹ�ư ����
	String customize 	= "";		//������ �⺻ �������� / Mobile : �����������
	
	String sGender = ""; 			//������ �⺻ ���� ��, 0 : ����, 1 : ���� 
	
    // CheckPlus(��������) ó�� ��, ��� ����Ÿ�� ���� �ޱ����� ���������� ���� http���� �Է��մϴ�.
	//����url�� ���� �� ������������ ȣ���ϱ� �� url�� �����ؾ� �մϴ�. ex) ���� �� url : http://www.~ ���� url : http://www.~
    String sReturnUrl = "http://localhost:8080/web/namefact/CHECKPLUS_MOBILE/Success";      // ������ �̵��� URL
    String sErrorUrl  = "http://localhost:8080/web/namefact/CHECKPLUS_MOBILE/Fail";         // ���н� �̵��� URL

    // �Էµ� plain ����Ÿ�� �����.
    String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                        "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                        "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                        "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                        "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                        "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                        "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize + 
						"6:GENDER" + sGender.getBytes().length + ":" + sGender;
    
    String sMessage = "";
    String sEncData = "";
    
    int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
    if( iReturn == 0 ) {
        sEncData = niceCheck.getCipherData();
    } else if( iReturn == -1) {
        sMessage = "��ȣȭ �ý��� �����Դϴ�.";
    } else if( iReturn == -2) {
        sMessage = "��ȣȭ ó�������Դϴ�.";
    } else if( iReturn == -3) {
        sMessage = "��ȣȭ ������ �����Դϴ�.";
    } else if( iReturn == -9) {
        sMessage = "�Է� ������ �����Դϴ�.";
    } else {
        sMessage = "�˼� ���� ���� �Դϴ�. iReturn : " + iReturn;
    }
%>

<html>
	<head>
		<title>NICE������ - CheckPlus �Ƚɺ�������</title>
		
		<script language='javascript'>
			window.name ="Parent_window";
			
			function fnPopup(){
				window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
				document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
				document.form_chk.target = "popupChk";
				document.form_chk.submit();
			}
		</script>
	</head>
<body>

	<%= sMessage %><br><br>
	��ü���� ��ȣȭ ����Ÿ : [<%= sEncData %>]<br><br>

	<!-- �������� ���� �˾��� ȣ���ϱ� ���ؼ��� ������ ���� form�� �ʿ��մϴ�. -->
	<form name="form_chk" method="post">
		<input type="hidden" name="m" value="checkplusService">			
		<input type="hidden" name="EncodeData" value="<%= sEncData %>">	
	    
		 <a href="javascript:fnPopup();"> CheckPlus �Ƚɺ������� Click</a>
		 		
	</form>
	
	
</body>
</html>