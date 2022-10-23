<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="param1" rtexprvalue="true" %>
<%@ attribute name="param2" rtexprvalue="true" %>

<%
	String strEncodeVal = param2;

	if(strEncodeVal == null) {
		
		out.println("");
		
	} else {

		String strSpowiseCmsKey = egovframework.com.cmm.service.EgovProperties.getProperty("Globals.SpowiseCms.Key");
		
		String strDecReValue = strEncodeVal.replace("$2F", "/");
		
		String strDecryptedString = com.hisco.cmm.util.WebEncDecUtil.fn_decrypt(strDecReValue, strSpowiseCmsKey);
		
		System.out.println("strDecryptedString Final = " + strDecryptedString);
		
		if ( (strDecryptedString != null) && (!("null".equals(strDecryptedString))) ) {
		
			if (strDecryptedString.length() == 11) {
			
				strDecryptedString = strDecryptedString.substring(0,3) + "-" + strDecryptedString.substring(3,7) + "-" + strDecryptedString.substring(7,11);
				
			} else if (strDecryptedString.startsWith("02") && strDecryptedString.length()  > 8) {
				
				strDecryptedString = strDecryptedString.substring(0,2) + "-" + strDecryptedString.substring(2, strDecryptedString.length() - 4) + "-" + strDecryptedString.substring(strDecryptedString.length() - 4);
				
			} else if (strDecryptedString.length() == 10) {
					
			   strDecryptedString = strDecryptedString.substring(0,3) + "-" + strDecryptedString.substring(3,6) + "-" + strDecryptedString.substring(6); 
			   
			} 
			
			String strTelNoSplit[] = strDecryptedString.split("\\-");
			
			if (strTelNoSplit != null) {

				/* 
				System.out.println("**********************************");
				System.out.println(strTelNoSplit.length);
				System.out.println(strTelNoSplit[0]);
				System.out.println(strTelNoSplit[1]);
				System.out.println(strTelNoSplit[2]);
				System.out.println(strTelNoSplit[3]);
				System.out.println(strTelNoSplit[4]);
				System.out.println("**********************************");
				*/
				
				if (strTelNoSplit.length == 3) {
					
					if ("1".equals(param1)) {
						
						if ( (strTelNoSplit[0] == null) || ("null".equals(strTelNoSplit[0])) ) {
							out.println("");
						} else {
							out.println(strTelNoSplit[0]);	
						}
						
						
					} else if ("2".equals(param1)) {
						
						if (strTelNoSplit[1] == null) {
							out.println("");
						} else {
							out.println(strTelNoSplit[1]);	
						}
						
					} else if (("3".equals(param1))) {
						
						if (strTelNoSplit[2] == null) {
							out.println("");
						} else {
							out.println(strTelNoSplit[2]);	
						}
						
					} else if (("0".equals(param1))) {
						out.println(strTelNoSplit[0] + "-" + strTelNoSplit[1] + "-" + strTelNoSplit[2]);
					}
					
				} else if (strTelNoSplit.length == 4) {

					if ("1".equals(param1)) {
						
						if ( (strTelNoSplit[0] == null) || ("null".equals(strTelNoSplit[0])) ) {
							out.println("");
						} else {
							out.println(strTelNoSplit[0]);	
						}
						
						
					} else if ("2".equals(param1)) {
						
						if (strTelNoSplit[1] == null) {
							out.println("");
						} else {
							out.println(strTelNoSplit[1]);	
						}
						
					} else if (("3".equals(param1))) {
						
						if (strTelNoSplit[2] == null) {
							out.println("");
						} else {
							out.println(strTelNoSplit[2]);	
						}
						
					} else if (("0".equals(param1))) {
						out.println(strTelNoSplit[0] + "-" + strTelNoSplit[1] + "-" + strTelNoSplit[2]);
					}
					
				} else if (strTelNoSplit.length == 5) {
					
					if ("1".equals(param1)) {
						
						if ( (strTelNoSplit[0] == null) || ("null".equals(strTelNoSplit[0])) ) {
							out.println("");
						} else {
							out.println(strTelNoSplit[0]);	
						}
						
						
					} else if ("2".equals(param1)) {
						
						if (strTelNoSplit[2] == null) {
							out.println("");
						} else {
							out.println(strTelNoSplit[2]);	
						}
						
					} else if (("3".equals(param1))) {
						
						if (strTelNoSplit[4] == null) {
							out.println("");
						} else {
							out.println(strTelNoSplit[4]);	
						}
						
					} else if (("0".equals(param1))) {
						out.println(strTelNoSplit[0] + "-" + strTelNoSplit[2] + "-" + strTelNoSplit[4]);
					}	
					
				} else {
					
					out.println(strDecryptedString);
					
				}
				
			} else {
				
				out.println(param2);
				
			}

		} else {
			
			if (strDecryptedString == null) {
				out.println("");	
			} else if("null".equals(strDecryptedString)) {
				out.println("");
			} else {
				out.println(strDecryptedString);
			}
			
		}
		
	}

%>