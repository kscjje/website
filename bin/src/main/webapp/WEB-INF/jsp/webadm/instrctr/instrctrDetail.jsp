<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : instrctrDetail.jsp
	 * @Description : 강사관리 상세 JSP
	 * @Modification Information
	 * @
	 * @  수정일              수정자            수정내용
	 * @ -------       ------    ---------------------------
	 * @ 2021.11.05     이기태           최초 생성
	 *
	 *  @author 이기태
	 *  @since 2021.11.05
	 *  @version 1.0
	 *  @see
	 *
	 */
%>
<head>
<script type="text/javascript" src="../validator"></script>
<%-- <validator:javascript formName="instrctrVO" staticJavascript="false" xhtml="true" cdata="false"/> --%>
<script type="text/javascript">
var JSON_REQ_SUFFIX = ".json";

var fn_delete = function() {
	var msg = "삭제하시겠습니까?";
	var method = "";
	var url = "";

	var instrctrNo = Number("${instrctrVO.instrctrNo}");

	if (confirm(msg)) {
		jQuery.ajax({
			url : "./"+instrctrNo+JSON_REQ_SUFFIX,
			type: "DELETE",
			success: function(data) {
				if (data.result.code == "ERROR") {
					alert(data.result.msg);
				} else {
					alert(data.result.msg);
					window.location.href = "./list";
				}
			}
		});
	}
};

var fn_save = function() {
	var form = jQuery("#instrctrVO")[0];

	if(form.nameKor.value == ""){
		alert("강사명을 입력해 주세요.");
		form.nameKor.focus();
		return;
	}

	if($('input[name=genderGbn]:checked').length < 1){
		alert("성별을 선택해 주세요.");
		form.genderGbn[0].focus();
		return;
	}

	if(form.email.value == ""){
		alert("이메일 주소를 입력해 주세요.");
		form.email.focus();
		return;
	}

	if(form.hpNo.value == ""){
		alert("핸드폰번호를 입력해 주세요.");
		form.hpNo.focus();
		return;
	}

	var msg = "수정하시겠습니까?";
	if(confirm(msg)){
		form.submit();
	}
};

jQuery(document).ready(function(){
	jQuery("#instrctr-save").on("click", function() {
		fn_save();
	});

	jQuery("#instrctr-del").on("click", function() {
		fn_delete();
	});
});
</script>
</head>
<c:set var="formAction" value="./update/${instrctrVO.instrctrNo}"/>
<%@ include file="instrctrForm.jsp"%>