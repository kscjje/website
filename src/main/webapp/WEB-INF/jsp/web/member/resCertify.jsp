<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script language='javascript'>
	var meg = "${errMsg}";
	if(meg == "") {
		if (self.opener && self.opener.parent) { 
			self.opener.parent.location.href = 'javascript:certCallback(); self.close();'; 
		}else if (self.opener) { 
			self.opener.location.href = 'javascript:certCallback(); self.close();';
		}else if (self.parent) { 
			self.parent.location.href = 'javascript:certCallback(); self.close();'; 
		}else { self.location.href = 'javascript:certCallback(); self.close();'; }
		self.close();
	} else {
		alert(meg);
		self.opener.parent.location.reload();
		self.close();
	} 
    
</script>