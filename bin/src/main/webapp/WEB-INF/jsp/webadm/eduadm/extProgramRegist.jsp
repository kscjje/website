<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<script>
	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_save() {
		var form = document.edcProgramVO;

		if (form.orgNo.value == "") {
			alert("기관을 선택해 주세요.");
			form.orgNo.focus();
			return;
		}

		if (form.edcPrgmnm.value == "") {
			alert("프로그램명을 입력해 주세요.");
			form.edcPrgmnm.focus();
			return;
		}

		if(form.edcFeeType.checked && (form.salamt.value=="0"||form.salamt.value=="")){
			alert("수강료를 입력해 주세요.");
			form.salamt.focus();
			return;
		}

		if(!fn_rsvn_set_chk()){
			return;
		}

		var flag = true;

		if($('input[name=edcLimitAgeyn]:checked').length > 0){
			$.each($('#limitAgeDiv').find("input[name=targetAgenm]") , function(index,item){
				var tr = $(item).parent().parent();
				var no = index+1;

				if($(item).val() == ""){
					alert(no + "번째 구분명을 입력해 주세요.");
					$(item).focus();
					flag = false;
					return;
				}

				if(tr.find("input[name=targetSage]").val() == ""){
					alert(no+"번째 from 나이를 입력해 주세요.");
					tr.find("input[name=targetSage]").focus();
					flag = false;
					return;
				}
				if(isNaN(tr.find("input[name=targetSage]").val())){
					alert(no+"번째 from 나이는 숫자만 입력해 주세요.");
					tr.find("input[name=targetSage]").focus();
					flag = false;
					return;
				}
				if(tr.find("input[name=targetEage]").val() == ""){
					alert(no+"번째  to 나이를 입력해 주세요.");
					tr.find("input[name=targetEage]").focus();
					flag = false;
					return;
				}
				if(isNaN(tr.find("input[name=targetEage]").val())){
					alert(no+"번째 to 나이는 숫자만 입력해 주세요.");
					tr.find("input[name=targetEage]").focus();
					flag = false;
					return;
				}
			});
		}


		if(flag){
			 var notiObj = document.getElementById("noticeVO.notiPerodsetYn1");
	    	if($(notiObj).prop("checked") && document.getElementById("noticeVO.notiSdate").value == ""){
	    		alert("강좌 공지 노출 시작일을 입력해 주세요");
	    		document.getElementById("noticeVO.notiSdate").focus();
	    		return;
	    	}else if($(notiObj).prop("checked") && document.getElementById("noticeVO.notiEdate").value == ""){
	    		alert("강좌 공지 노출 종료을 입력해 주세요");
	    		document.getElementById("noticeVO.notiEdate").focus();
	    		return;
	    	}

			if (confirm("새로운 교육프로그램을 등록하시겠습니까?")) {

				$.each($('.numberchk') , function(index,item){
					var val = $(this).val().split(",").join('');
					$(this).val(val);
				})

				form.submit();
			}
		}

	}

	function fn_rsvn_set_chk(){
		var form = document.edcProgramVO;
		if (form.areaCd.value == "") {
			alert("지역을 선택해 주세요.");
			form.areaCd.focus();
			return false;
		}
		if (form.edcTargetAgegbn.value == "") {
			alert("교육대상을 선택해 주세요.");
			form.edcTargetAgegbn.focus();
			return false;
		}

		if (form.edcRsvnsetNm.value == "") {
			alert("모집 차수명을 입력해 주세요.");
			form.edcRsvnsetNm.focus();
			return false;
		}

		//타기관 링크
		if (form.edcRsvnLinkurl.value == "") {
			alert("접수 URL 을 입력해 주세요.");
			form.edcRsvnLinkurl.focus();
			return false;
		}


		if (form.edcRsvnSdate.value == "") {
			alert("접수시작일을 입력해 주세요.");
			form.edcRsvnSdate.focus();
			return false;
		}
		if (form.edcRsvnEdate.value == "") {
			alert("접수종료일을 입력해 주세요.");
			form.edcRsvnEdate.focus();
			return false;
		}
		if(__dateDiffCheck__(form.edcRsvnSdate.value , form.edcRsvnEdate.value) == false){
			alert("접수시작일이 종료일 보다 클 수 없습니다.");
			form.edcRsvnSdate.focus();
			return false;
		}

		if (form.edcSdate.value == "") {
			alert("교육시작일을 입력해 주세요.");
			form.edcSdate.focus();
			return false;
		}
		if (form.edcEdate.value == "") {
			alert("교육종료일을 입력해 주세요.");
			form.edcEdate.focus();
			return false;
		}
		if(__dateDiffCheck__(form.edcSdate.value , form.edcEdate.value) == false){
			alert("교육시작일이 종료일 보다 클 수 없습니다.");
			form.edcSdate.focus();
			return false;
		}
		if($('input.edcDaygbn:checked').length < 1){
			alert("교육요일을 최소 1개 이상 선택해 주세요.");
			document.getElementById("edcDaysList0.edcDaygbn1").focus();
			return false;
		}

		if($('input[name=edcPncpa]').val() =="0" || $('input[name=edcPncpa]').val() ==""){
			alert("총 정원을 입력해 주세요.");
			form.edcPncpa.focus();
			return false;
		}

		return true;
	}

	function fn_program_edit(){
		var form = document.edcProgramVO;

		if (form.orgNo.value == "") {
			alert("기관을 선택해 주세요.");
			form.orgNo.focus();
			return;
		}

		if (form.edcPrgmnm.value == "") {
			alert("프로그램명을 입력해 주세요.");
			form.edcPrgmnm.focus();
			return;
		}

		if(form.edcFeeType.checked && (form.salamt.value=="0"||form.salamt.value=="")){
			alert("수강료를 입력해 주세요.");
			form.salamt.focus();
			return;
		}

		$.ajax({
		      url: './extProgramUpdtSave.json',
		      type : "POST",
		      data : $('#edcProgramVO').serialize(),
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        }else{
			        	//연속으로 프로그램 정보도 저장
			        	fn_program_intro();
			        }
		      }
		    });
	}

	function fn_program_rsvn(){
		var msg = "";
		if($('#edcRsvnsetSeq').val() == ""){
			msg = "신규 모집정보를 생성하시겠습니까?";
		}

		if(!fn_rsvn_set_chk()){
			return;
		}else{
			$('body').showLoading();

			if(msg == "" || confirm(msg)){
				$.ajax({
				      url: './extProgramRsvnSave.json',
				      type : "POST",
				      data : $('#edcProgramVO').serialize(),
				      success: function(data) {

				    	  $('body').hideLoading();

				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        }else{
					        	fn_change_rsvn($('#edcRsvnsetSeq').val());
					        	 alert(data.result.msg);
					        }
				      },
				      error : function(res){
				    	  	__ajaxErrorCallback__(res, function(){
								$('body').hideLoading();
								alert("서버 에러");
				    	  	});
				      }
				    });
			}
		}
	}

	function fn_program_intro(){
		$.ajax({
		      url: './extProgramIntroSave.json',
		      type : "POST",
		      data : $('#edcProgramVO').serialize(),
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        }else{
			        	 alert(data.result.msg);
			        }
		      }
		    });
	}

	function fn_program_notice(){
		$.ajax({
		      url: './extProgramNoticeSave.json',
		      type : "POST",
		      data : $('#edcProgramVO').serialize(),
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        }else{
			        	 alert(data.result.msg);
			        }
		      }
		    });
	}

	function fn_program_delete(){
		if(confirm("삭제하시겠습니까?")){
			$.ajax({
			      url: './extProgramDelete.json',
			      type : "POST",
			      data : $('#edcProgramVO').serialize(),
			      success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        }else{
				        	 alert(data.result.msg);
				        	 window.location.replace("./extProgramList${searchQuery}");
				        }
			      } ,
			      error : function(res){
			    	  	__ajaxErrorCallback__(res, "프로그램삭제 오류.");
			      }
			    });
		}
	}


	function fn_age_delete(obj){
		$(obj).parent().parent().remove();
	}

	//분류 선택 후
	function fn_select_category(comcd,ctgCd , obj){
		var nowCtgnm = $(obj).text();

		if(ctgCd.lastIndexOf("0000000") == "3"){
			//1depth
		}else if(ctgCd.lastIndexOf("0000") == "6"){
			//2depth
			var upper1 = ctgCd.substring(0,3) + "0000000";
			var upperCtgnm = $('input[name=listCtgCd][value='+upper1+']').parent().find("a").text();

			nowCtgnm = upperCtgnm +" > " + nowCtgnm;
		}else{
			//3depth
			var upper2 = ctgCd.substring(0,6) + "0000";
			var upperCtgnm = $('input[name=listCtgCd][value='+upper2+']').parent().find("a").text();

			nowCtgnm = upperCtgnm +" > " + nowCtgnm;


			var upper1 = ctgCd.substring(0,3) + "0000000";
			upperCtgnm = $('input[name=listCtgCd][value='+upper1+']').parent().find("a").text();
			nowCtgnm = upperCtgnm +" > " + nowCtgnm;

		}

		$('#comCtgNm').val(nowCtgnm);
		$('#CtgCd').val(ctgCd);

		$('#modal-default').find("button.close").click();

	}

	function fn_select_teacher(seq , nm){
		$('#instrctrNo').val(seq);
		$('#instrctrName').val(nm);

		$('input[name=instrctrYn]').prop("checked" , false);
		$('#modal-teacher').find("button.close").click();

	}

	function fn_find_teacher(){
		if($('#orgNo').val() == ""){
			alert("기관을 먼저 선택해 주세요.");
			$('#orgNo').focus();

			return false;
		}else{
			$.ajax({
			      url: '../instrctr/listAjax.json',
			      type : "GET",
			      data : {'searchOrgNo' : $('#orgNo').val() , 'searchCondition':'name','searchKeyword':$('#searchTeacher').val() },
			      cache : false,
			      success: function(data) {
			    	  	var LIST = data.result;
				        $('#TCR_TABLE').find("tbody").html("");

				        var emptyHtml = "<tr><td>검색된 강사가 없습니다.</td></tr>";

				        if(LIST){
				        	for(var i=0; i<LIST.length ; i++){
				        		var html = "<tr>";
				        		 html += "<td><a href=\"javascript:fn_select_teacher('"+LIST[i].instrctrNo+"','"+LIST[i].nameKor+"')\">"+LIST[i].nameKor + "</a>";
				        		 if(LIST[i].nameEng != undefined)   html += " (" + LIST[i].nameEng + ")";
				        		 if(LIST[i].hpNo != undefined)   html += " / " + LIST[i].hpNo ;
				        		 html += "</td>";
				        		 html += "<tr>";

				        		$('#TCR_TABLE').find("tbody").eq(0).append(html);
				        	}

				        	if(LIST.length < 1){
				        		$('#TCR_TABLE').find("tbody").eq(0).append(emptyHtml);
				        	}
				        }else{
				        	$('#TCR_TABLE').find("tbody").eq(0).append(emptyHtml);
				        }

			      }
	        });

			return true;
		}

	}

	function fn_find_category(){
		$.ajax({
		      url: '../comctgr/comCtgrListAjax.json',
		      type : "GET",
		      dataType : "json",
		      cache : false,
		      success: function(data) {
		        console.log(data);

		        var LIST = data.result;
		        $('#CTG_TABLE').find("tbody").html("");

		        if(LIST){
		        	$('#CTG_TABLE').find("tbody").html(categoryTreeHtml(LIST));
		        }
		      }
		    });
	}

	$(document).ready(function(){
	    //Datemask dd/mm/yyyy
		$('.datetype').inputmask({ mask: "y-1-2", leapday: "-02-29", placeholder: "yyyy-mm-dd", separator: "-", alias: "yyyy-mm-dd" });
		//$('input.numberchk').number(true);

		$( ".datetype" ).datepicker({
			"dateFormat":'yy-mm-dd',
			'monthNames':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			'monthNamesShort':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			'changeMonth' : true,
			'changeYear':true
		});

		// 숫자만 받기 콤마 찍고
		$('input.numberchk').keyup(function(){
			inputNumberFormat($(this));
		});

	    $('#edcLimitAgeyn1').click(function(){
	    	fn_click_event('edcLimitAgeyn' ,  $(this).prop("checked")?"Y":"N");
	    });

	    $('input[name=edcFeeType]').click(function(){
	    	fn_click_event('edcFeeType' ,  $(this).prop("checked")?"1001":"2001");
	    });

	    $('input[name=edcRsvnRectype]').click(function(){
	    	fn_click_event('edcRsvnRectype' ,  $(this).val());
	    });

	    $('input[name=edcRsvnAccssrd]').click(function(){
	    	fn_click_event('edcRsvnAccssrd' ,  $(this).val());
	    });
	    $('input[name=edcCapaDvdyn]').click(function(){
	    	fn_click_event('edcCapaDvdyn' ,  $(this).prop("checked")?"Y":"N");
	    });

	    $('input[name=instrctrYn]').click(function(){
	    	fn_click_event('instrctrYn' ,  $(this).prop("checked")?"Y":"N");
	    });

	    $('input[name=edcOnlineyn]').click(function(){
	    	fn_click_event('edcOnlineyn' ,    $(this).prop("checked")?"Y":"N");
	    })


	    var notiObj = document.getElementById("noticeVO.notiPerodsetYn1");

	    $(notiObj).click(function(){
	    	if($(this).prop("checked")){
	    		document.getElementById("noticeVO.notiSdate").disabled = false;
	    		document.getElementById("noticeVO.notiEdate").disabled = false;
	    	}else{
	    		document.getElementById("noticeVO.notiSdate").disabled = true;
	    		document.getElementById("noticeVO.notiEdate").disabled = true;
	    	}
	    });

	    //수정시 초기화 시키기
	    <c:if test="${!empty edcProgramVO.edcLimitAgeyn}">
	    	fn_click_event('edcLimitAgeyn' ,  "${edcProgramVO.edcLimitAgeyn}");
	    </c:if>
	    <c:if test="${!empty edcProgramVO.edcFeeType}">
	    	fn_click_event('edcFeeType' ,   "${edcProgramVO.edcFeeType}");
	    </c:if>
	    <c:if test="${!empty edcProgramVO.edcRsvnRectype}">
	    	fn_click_event('edcRsvnRectype' ,   "${edcProgramVO.edcRsvnRectype}");
	    </c:if>
	    <c:if test="${!empty edcProgramVO.edcRsvnAccssrd}">
	    	fn_click_event('edcRsvnAccssrd' ,   "${edcProgramVO.edcRsvnAccssrd}");
	    </c:if>
	    <c:if test="${!empty edcProgramVO.edcCapaDvdyn}">
	    	fn_click_event('edcCapaDvdyn' ,   "${edcProgramVO.edcCapaDvdyn}");
	    </c:if>

	    <c:if test="${!empty edcProgramVO.instrctrNo and edcProgramVO.instrctrNo > 0}">
	    	fn_click_event('instrctrYn' ,   "N");
	    </c:if>

	    <c:if test="${edcProgramVO.edcOnlineyn eq 'Y'}">
			fn_click_event('edcOnlineyn' ,   "Y");
		</c:if>



	    if($(notiObj).prop("checked")){
	    	document.getElementById("noticeVO.notiSdate").disabled = false;
    		document.getElementById("noticeVO.notiEdate").disabled = false;
	    };

	    $('#findTeacher').click(function(e){
	    	 $('#searchTeacher').val($('#instrctrName').val());
	    	if(	fn_find_teacher()){

	    	}else{
	    		return false;
	    	}
	    });

	    $('#searchTeacher').keydown(function(){
			if(event.keyCode==13){
				fn_find_teacher();
			}
		});

	    $('#instrctrName').keydown(function(){
			if(event.keyCode==13){
				 $('#searchTeacher').val($('#instrctrName').val());
				 $('#findTeacher').click();
			}
		});

		//수정화면시 숫자에 콤마 넣기
	    $.each($('input.numberchk') , function(index,item){
	    	$(this).val(comma($(this).val()));
	    });


	});

	function fn_click_event(gubun , val){
		if(gubun == "edcRsvnRectype"){
    		if(val == "2001" ){
    			//추첨대기제
    			$('#drwtNtcedate').parent().parent().show();
    		}else{
    			$('#drwtNtcedate').parent().parent().hide();
    		}

    		if(val == "5001" ){
    			//타기관 링크
    			$('#edcRsvnLinkurl').parent().parent().show();
    			$('.intype').hide();
    		}else{
    			$('#edcRsvnLinkurl').parent().parent().hide();
    			$('.intype').show();

    			if($('#edcFeeType1').prop("checked")){
    				//무료
    				$('.notfree').hide();
    			}
    		}

    		if(val == "1002"){
    			//선착대기
    			$('#edcEndwaitCapa').prop("readonly" , false);
    		}else{
    			$('#edcEndwaitCapa').val("0").prop("readonly" , true);
    		}
		}else if(gubun == "edcRsvnAccssrd"){
	    	if(val == "1001" || val == "2001"){
	    		//온라인
	    		$('#edcCapaDvdyn1').prop("checked" , false).prop("disabled" , true);
	    		$('#edcOncapa').val("0").prop("readonly" , true);
	    		$('#edcOffcapa').val("0").prop("readonly" , true);
	    	}else{
	    		//온라인 + 오프라인
	    		$('#edcCapaDvdyn1').prop("disabled" , false);
	    	}
		}else if(gubun == "edcCapaDvdyn"){
	    	if(val == "Y"){
	    		//온라인,오프라인 배분하기
	    		$('#edcOncapa').prop("readonly" , false);
	    		$('#edcOffcapa').prop("readonly" , false);
	    	}else{
	    		//배분 안함
	    		$('#edcOncapa').val("0").prop("readonly" , true);
	    		$('#edcOffcapa').val("0").prop("readonly" , true);
	    	}
		}else if(gubun == "edcFeeType"){
			if(val == "1001"){
	    		//유료
	    		$('#salamt').prop("disabled" , false);
	    		$('#salamt').focus();

	    		$('.notfree').show();
	    	}else{
	    		$('#salamt').val("0").prop("disabled" , true);

	    		$('.notfree').hide();
	    	}
		}else if(gubun == "edcLimitAgeyn"){
	    	if(val == "Y"){
	    		$('#limitAgeAddBtn').removeClass("disabled");
	    		$('#limitAgeDiv').show();

	    		$('#limitAgeAddBtn').click(function(){
	    				var html = "<tr>";
	    				html += "<td><input type=\"text\" name=\"targetAgenm\" style=\"width:100%\"/></td>";
	    				html += "<td><input type=\"text\" name=\"targetSage\" style=\"width:100%\"/></td>";
	    				html += "<td><input type=\"text\" name=\"targetEage\" style=\"width:100%\"/></td>";
	    				html += "<td><button type=\"button\" class=\"btn btn-secondary btn-xs\" onclick='fn_age_delete(this)'>X</button></td>";
	    				html += "<tr>";

						$('#limitAgeDiv').find("table tbody").append(html);
	    		});
	    	}else{
	    		$('#limitAgeAddBtn').addClass("disabled");
	    		$('#limitAgeDiv').hide();

	    		$('#limitAgeAddBtn').off("click");
	    	}
		}else if(gubun == "instrctrYn"){
			if(val == "Y"){
				$('#instrctrNo').val("0");
			}else{

			}
		}else if(gubun == "edcOnlineyn"){
			if(val == "Y"){
				$('#edcPlacenm').val("온라인교육").prop("readonly" , true);
			}else{
				$('#edcPlacenm').prop("readonly" , false);
			}
		}
	}

	function fn_add_rsvn(){
		$('#edcRsvnsetSeq').val("");
		$('#edcRsvnsetNm').val("");
		$('#edcRsvnSdate').val("");
		$('#edcRsvnEdate').val("");
		$('#edcSdate').val("");
		$('#edcEdate').val("");
	}

	function fn_change_rsvn(val){
		$('#custom-tabs-one-profile').showLoading();
		$.ajax({
		      url: './extProgramDetailAjax',
		      type : "GET",
		      data : {'comcd': $('#comcd').val() , 'edcPrgmid':$('#edcPrgmid').val() , 'edcRsvnsetSeq':val},
		      dataType : "html",
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
						$('#custom-tabs-one-profile').html(data);
						$('#custom-tabs-one-profile').hideLoading();
		    	  	}
		      },
		      error : function(res){
		    	  	__ajaxErrorCallback__(res, function(){
						$('#custom-tabs-one-profile').hideLoading();
						alert("서버 에러");
		    	  	});
		      }
		    });
	}

</script>
</head>
<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>유관기관 교육프로그램 <c:choose> <c:when test="${edcProgramVO.edcPrgmid > 0}">개설수정</c:when><c:otherwise>개설등록</c:otherwise></c:choose></h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>

<!-- Main content -->
<section class="content">
      <div class="container-fluid">

 <c:if test="${!empty errorMsg}">
		<div class="row">
          <div class="col-md-12">
      		<div class="ccallout callout-danger">
              <h5><i class="fas fa-info"></i> ERROR:</h5>
             ${errorMsg }
            </div>
         </div>
       </div>
</c:if>
<form:form commandName="edcProgramVO" name="edcProgramVO" action="./extProgramRegistSave" class="form-horizontal" method="post" enctype="multipart/form-data">
<form:hidden path="comcd" />
<form:hidden path="edcProgmType" />
<form:hidden path="edcPrgmid" />
<form:hidden path="itemCd" />
<form:hidden path="edcProgmDate"/>
<form:hidden path="instrctrYn" value="N"/>
<form:hidden path="edcRsvnRectype" value="5001"/>

       <div class="row">
          <div class="col-md-6">
            <div class="card card-primary card-outline">
              <div class="card-header">
                <h3 class="card-title">기본 정보</h3>
              </div>

              <!-- /.card-header -->
              <div class="card-body ">
                  <div class="form-group row">
	                    <div class="col-sm-12"  style="text-align:right">
    						<div class="form-check" style="display:inline-block;margin-left:20px">
	                          <form:checkbox path="edcOpenyn" cssClass="form-check-input" value="Y"/>
	                          <label for="edcOpenyn1" class="form-check-label">공개 하기</label>
	                        </div>
	                        <div class="form-check" style="display:inline-block;margin-left:20px">
	                          <form:checkbox path="useYn" cssClass="form-check-input" value="Y"/>
	                          <label for="useYn1" class="form-check-label">사용하기</label>
	                        </div>
 	                    </div>
	               </div>

                  <div class="form-group row">
	                    <label for="comnm" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 기관명</label>
	                    <div class="col-sm-10">
	                    	<hisco:OrgList defaultNo="${edcProgramVO.orgNo}" id="orgNo" orgKind="2001"/>
	   						<div><form:errors path="orgNo" cssClass="error" /></div>
	                    </div>
                  </div>

                  <div class="form-group row">
                    <label for="orgTel" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 프로그램명</label>
                    <div class="col-sm-10">
                    	<div class="form-check">
                          <form:checkbox path="edcOnlineyn" cssClass="form-check-input" value="Y"/>
                          <label for="edcOnlineyn1" class="form-check-label">온라인으로 교육진행</label>
                        </div>
                    	<form:input path="edcPrgmnm" title="프로그램명" class="form-control " maxlength="100" />
   						<div><form:errors path="edcPrgmnm" cssClass="error" /></div>
                    </div>
                  </div>
                  <div class="form-group row">
	                    <label for="orgTel" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 수강료</label>
	                    <div class="col-sm-10">
	                    	<div class="form-check">
	                          <form:checkbox path="edcFeeType" cssClass="form-check-input" value="1001"/>
	                          <label for="edcFeeType1" class="form-check-label">유료</label>
	                        </div>
	                        <div class="input-group">
	                    		<form:input path="salamt" title="교육비" class="form-control numberchk" maxlength="10" disabled="true"/>
	                    		<div class="input-group-append">
			                      <span class="input-group-text">원</span>
			                    </div>
	                    	</div>
	   						<div><form:errors path="salamt" cssClass="error" /></div>
	                    </div>
	              </div>

                  <div class="form-group row">
                    <label for="areaCd" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 지역</label>
                    <div class="col-sm-4">
                    	<form:select path="areaCd" class="form-control">
                    	 	<option value="0">::::선택::::</option>
<c:forEach items="${areaList}" var="item" varStatus="status">
                    	 	<form:option value="${item.areaCd }" label="${item.areaName }" />
     <c:forEach items="${item.subAreaList}" var="item2" varStatus="status">
     						<form:option value="${item2.areaCd }" label="${item.areaName } > ${item2.areaName }" />
     </c:forEach>
</c:forEach>
                    	 </form:select>
   						<div><form:errors path="areaCd" cssClass="error" /></div>
                    </div>

                    <label for="areaCd" class="col-sm-2 col-form-label" style="text-align:right"><span class="pilsu">*</span> 교육대상</label>
                    <div class="col-sm-4">
                    	<form:select path="edcTargetAgegbn" class="form-control">
                    	 	<option value="0">::::선택::::</option>
<c:forEach items="${targetType}" var="item" varStatus="status">
                    	 	<form:option value="${item.cd }" label="${item.cdNm }" />
</c:forEach>
                    	 </form:select>
   						<div><form:errors path="edcTargetAgegbn" cssClass="error" /></div>
                    </div>
                  </div>


                  <div class="form-group row">
                    <label for="comnm" class="col-sm-2 col-form-label">프로그램분야</label>
                    <div class="col-sm-10">
                    	<form:hidden path="CtgCd" />
                    	<div class="input-group">
	                    		<div class="input-group-prepend">
				                    <button class="btn btn-secondary" type="button" onclick="fn_find_category()" data-toggle="modal" data-target="#modal-default">
						              <i class="fas fa-search fa-fw"></i> 찾기
						            </button>
			           			</div>
			           			<form:input path="comCtgNm" title="프로그램명" class="form-control" readonly="true"/>
			           </div>
   						<div><form:errors path="CtgCd" cssClass="error" /></div>
                    </div>
                  </div>
                   <div class="form-group row">
                  		<label for="instrctrName" class="col-sm-2 col-form-label">강사명</label>
		                    <div class="col-sm-4">
		                    	<div class="input-group">
					           		<form:input path="instrctrName" title="강사명" class="form-control"/>
					           </div>
		   						<div><form:errors path="instrctrName" cssClass="error" /></div>
		                    </div>
		           </div>
                  <div class="form-group row">
                    <label for="orgTel" class="col-sm-2 col-form-label">교육장소</label>
                    <div class="col-sm-10">
                    	<form:input path="edcPlacenm" title="교육장소" class="form-control " maxlength="100" />
   						<div><form:errors path="edcPlacenm" cssClass="error" /></div>
   						<div>※ 알림 발신번호로 1개의 번호만 입력하세요.</div>
                    </div>
                  </div>


					<div class="form-group row">
                    <label for="edcGuideTelno" class="col-sm-2 col-form-label">교육문의 전화번호</label>
                    <div class="col-sm-10">
                    	<form:input path="edcGuideTelno" title="전화번호" class="form-control " maxlength="50" />
   						<div><form:errors path="edcGuideTelno" cssClass="error" /></div>
   						
                    </div>
                  </div>

                  <div class="form-group row">
	                    <label for="edcGuideComment" class="col-sm-2 col-form-label">프로그램 소개</label>
	                    <div class="col-sm-10">
	                    	<form:textarea path="edcPrgmintrcn" title="프로그램 소개" class="form-control " maxlength="5000" style="height:200px"/>
	   						<div><form:errors path="edcPrgmintrcn" cssClass="error" /></div>
	                    </div>
	                  </div>


	                  <div class="form-group row">
	                    <label for="jongmok" class="col-sm-2 col-form-label">교재물 안내 설명</label>
	                    <div class="col-sm-10">
	                    	<div class="form-check">
	                          <form:checkbox path="edcTchmtrGuideyn" cssClass="form-check-input" value="Y"/>
	                          <label for="edcTchmtrGuideyn1" class="form-check-label">교재물 안내 노출 하기</label>
	                        </div>

	                    	<form:textarea path="edcTchmtrGuide" title="교재물 안내 설명" class="form-control"/>
	   						<div><form:errors path="edcTchmtrGuide" cssClass="error" /></div>
	                    </div>
	                  </div>

	                  <div class="form-group row">
		                    <label for="linkUrl" class="col-sm-2 col-form-label">대표이미지</label>
		                    <div class="col-sm-10">
<c:choose>
	<c:when test="${edcProgramVO.edcPrgmid >0 }">
				                <div class="input-group mb-3">
		                    		<form:hidden path="edcImgFileid" />
				                  	<input type="text" class="form-control" readonly id="edcImgOrigin" value="<c:out value="${edcProgramVO.edcImgOrigin}"/>">
				                  	<div class="input-group-append">
				                    	<span class="input-group-text fileupload_process" style="cursor:pointer" data-inputnm="edcImgOrigin" data-inputid="edcImgFileid">파일변경</span>
				                  	</div>
				                </div>
				                <div id="edcImgFileidPreview" class="preview"><c:if test="${!empty edcProgramVO.edcImgOrigin}"><img src="<tags:UploadFileUrl filePath="${edcProgramVO.edcImgPath}" fileName="${edcProgramVO.edcImgFilenm }" originName="${edcProgramVO.edcImgOrigin }"/>" style="height:50px;cursor:pointer"/></c:if></div>
	</c:when>
	<c:otherwise>
								<input type="file" name="file_1" id="egovfile_0" title="이미지" class="form-control"/>
	</c:otherwise>
</c:choose>
		   						<div><font color='red'>※ 권장하는 크기는 470ⅹ620 픽셀입니다.</font></div>
		                    </div>
		              </div>
		              <div class="form-group row">
		                    <label for="linkUrl" class="col-sm-2 col-form-label">소개이미지 </label>
		                    <div class="col-sm-10">
<c:choose>
	<c:when test="${edcProgramVO.edcPrgmid >0 }">
				                <div class="input-group mb-3">
		                    		<form:hidden path="edcPrgmintrcnFileid" />
				                  	<input type="text" class="form-control" readonly id="edcPrgmintrcnOrigin" value="<c:out value="${edcProgramVO.edcPrgmintrcnOrigin}"/>">
				                  	<div class="input-group-append">
				                    	<span class="input-group-text fileupload_process" style="cursor:pointer" data-inputnm="edcPrgmintrcnOrigin" data-inputid="edcPrgmintrcnFileid">파일변경</span>
				                  	</div>
				                </div>
				                <div id="edcPrgmintrcnFileidPreview" class="preview"><c:if test="${!empty edcProgramVO.edcPrgmintrcnOrigin}"><img src="<tags:UploadFileUrl filePath="${edcProgramVO.edcPrgmintrcnPath}" fileName="${edcProgramVO.edcPrgmintrcnFilenm }" originName="${edcProgramVO.edcPrgmintrcnOrigin }"/>" style="height:50px;cursor:pointer"/></c:if></div>
	</c:when>
	<c:otherwise>
								<input type="file" name="file_2" id="egovfile_1" title="이미지" class="form-control"/>
	</c:otherwise>
</c:choose>
		                    </div>
		              </div>
		              <div class="form-group row">
		                    <label for="linkUrl" class="col-sm-2 col-form-label">강의계획서</label>
		                    <div class="col-sm-10">
<c:choose>
	<c:when test="${edcProgramVO.edcPrgmid >0 }">
				                <div class="input-group mb-3">
		                    		<form:hidden path="edcPlanFileid" />
				                  	<input type="text" class="form-control" readonly id="edcPlanOrigin" value="<c:out value="${edcProgramVO.edcPlanOrigin}"/>">
				                  	<div class="input-group-append">
				                    	<span class="input-group-text fileupload_process" style="cursor:pointer" data-inputnm="edcPlanOrigin" data-inputid="edcPlanFileid">파일변경</span>
				                  	</div>
				                </div>
				                <div id="edcPlanFileidPreview" class="preview"><c:if test="${!empty edcProgramVO.edcPlanOrigin}"><a href="<tags:UploadFileUrl filePath="${edcProgramVO.edcPlanPath}" fileName="${edcProgramVO.edcPlanFilenm }" originName="${edcProgramVO.edcPlanOrigin }"/>"><i class="fas fa-download"></i> 다운로드</a></c:if></div>
	</c:when>
	<c:otherwise>
		                    	<input type="file" name="file_3" id="egovfile_2" title="강의계획서" class="form-control"/>
	</c:otherwise>
</c:choose>

		                    </div>
		              </div>

               </div>
                <!-- /.card-body -->
 <c:if test="${edcProgramVO.edcPrgmid > 0}">
               <div class="card-footer">
                	<div class="row">
	                	<div class="col-md-4">
		                	 <a href="./extProgramList${searchQuery}" class="btn  form-control btn-secondary">목록</a>
		                </div>
	                	<div class="col-md-4">
		                	<c:choose>
		                		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
		                			 <button type="button" onclick="fn_program_edit()"  class="btn btn-info form-control">기본정보 저장</button>
		                		</c:when>
		                		<c:otherwise>
		                			 <button type="button" class="btn btn-info form-control disabled">기본정보 저장</button>
		                		</c:otherwise>
		                	</c:choose>
		                </div>
		                <div class="col-md-4">
		                	<c:choose>
		                		<c:when test="${commandMap.selectedMenu.delYn eq 'Y' }">
		                			 <button type="button" onclick="fn_program_delete()"  class="btn btn-danger form-control">삭제</button>
		                		</c:when>
		                		<c:otherwise>
		                			 <button type="button" class="btn btn-danger form-control disabled">삭제</button>
		                		</c:otherwise>
		                	</c:choose>
		                </div>
					</div>
               </div>
                <!-- /.card-footer -->
 </c:if>

            <!-- /.card -->
          </div>
        </div>
         <div class="col-md-6">
         	<div class="card card-primary card-tabs">
              <div class="card-header p-0 pt-1">
                <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist">

                  <li class="nav-item">
                    <a class="nav-link active" data-toggle="pill" href="#custom-tabs-one-profile" role="tab" aria-controls="custom-tabs-one-profile" aria-selected="true">모집정보설정</a>
                  </li>

                  <li class="nav-item">
                    <a class="nav-link"  data-toggle="pill" href="#custom-tabs-one-settings" role="tab" aria-controls="custom-tabs-one-settings" aria-selected="false">강좌 공지사항</a>
                  </li>
                </ul>
              </div>
              <div class="card-body">
                <div class="tab-content" id="custom-tabs-one-tabContent">
                <!-- 탭 두번째 -->
                  <div class="tab-pane active show" id="custom-tabs-one-profile" role="tabpanel" >

                   <c:if test="${!empty rsvnsetList}">
   					<div class="form-group row">
	                    <div class="col-sm-12">
	                    	<select id="edcRsvnsetSelect" class="form-control bg-gray" onChange="fn_change_rsvn(this.value)">
<c:forEach items="${rsvnsetList}" var="item" varStatus="status">
                    	 		<option value="${item.edcRsvnsetSeq }">[${item.edcRsvnsetSeq }] ${item.edcRsvnsetNm} (<hisco:DateUtil datestr="${item.edcSdate }" format="yyyy-MM-dd"/>  ~ <hisco:DateUtil datestr="${item.edcEdate }" format="yyyy-MM-dd"/>)</option>
</c:forEach>

	                    	</select>
			            </div>
	                </div>
	                <div class="form-group row">
	                    <label for="comnm" class="col-sm-2 col-form-label">차수코드</label>
	                    <div class="col-sm-10">
	                    	<div class="input-group">
	                    		<form:input path="edcRsvnsetSeq" title="차수코드" class="form-control" readonly="true" placeHolder="자동 부여됩니다."/>
	                    		<div class="input-group-append">
		                      			<button class="btn btn-secondary" type="button" onclick="fn_add_rsvn()">신규모집</button>
		                    	</div>
		                	</div>
			            </div>
	               </div>
</c:if>

                   <div class="form-group row">
	                    <label for="comnm" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 모집 차수명</label>
	                    <div class="col-sm-10">
	                    	<div class="input-group">
	                    		<form:input path="edcRsvnsetNm" title="모집 차수명" class="form-control" maxlength="50" />
			                </div>
			            </div>
	  					<div><form:errors path="edcRsvnsetNm" cssClass="error" /></div>

	                  </div>

                   <div class="form-group row" style="display:none">
	                    <label for="orgTel" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 접수 URL</label>
	                    <div class="col-sm-10">
	                    	<form:input path="edcRsvnLinkurl" title="접수 URL" class="form-control " maxlength="1000" />
	   						<div><form:errors path="edcRsvnLinkurl" cssClass="error" /></div>
	                    </div>
	               </div>


                  <div class="form-group row">
                    <label for="edcRsvnSdate" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 접수 시작일</label>
                    <div class="col-sm-4">
                    	<div class="input-group">
		                    <form:input path="edcRsvnSdate"  title="시작일" maxlength="10" cssClass="form-control datetype"/>
		                </div>
   						<div><form:errors path="edcRsvnSdate" cssClass="error" /></div>
                    </div>
                    <div class="col-sm-2">
                    	<div class="input-group">
                    		<form:select path="edcRsvnStimeHour" cssClass="form-control">
 <c:forEach var="i" begin="0" end="23">
                    			<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</form:option>

 </c:forEach>
 								</form:select>
		                </div>
                     </div>
                     <div class="col-sm-2">
                    	<div class="input-group">
                    		<form:select path="edcRsvnStimeMin" cssClass="form-control">
 <c:forEach var="i" begin="0" end="50" step="10">
                    			<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</form:option>

 </c:forEach>
 								</form:select>
		                </div>
                     </div>
                     <div class="col-sm-2">부터</div>
                  </div>
                  <div class="form-group row">
                    <label for="edcRsvnEdate" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 접수 종료일</label>
                    <div class="col-sm-4">
                    	<div class="input-group">
		                    <form:input path="edcRsvnEdate"  title="종료일" maxlength="10" cssClass="form-control datetype"/>
		                </div>
   						<div><form:errors path="edcRsvnEdate" cssClass="error" /></div>
                    </div>
                    <div class="col-sm-2">
                    	<div class="input-group">
                    		<form:select path="edcRsvnEtimeHour" cssClass="form-control">
 <c:forEach var="i" begin="0" end="23">
                    			<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</form:option>

 </c:forEach>
 								</form:select>
		                </div>
                     </div>
                     <div class="col-sm-2">
                    	<div class="input-group">
                    		<form:select path="edcRsvnEtimeMin" cssClass="form-control">
 <c:forEach var="i" begin="0" end="50" step="10">
                    			<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</form:option>

 </c:forEach>
 								</form:select>
		                </div>
                     </div>
                     <div class="col-sm-2">까지</div>
                  </div>
                  <div class="form-group row">
                    <label for="jongmok" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 교육 기간</label>
                    <div class="col-sm-4">
                    	<div class="input-group">
		                    <form:input path="edcSdate"  title="시작일" maxlength="10" cssClass="form-control datetype"/>
		                </div>
   						<div><form:errors path="edcSdate" cssClass="error" /></div>
                    </div>
                    <div class="col-sm-1">
							부터
                    </div>

                     <div class="col-sm-4">
                    	<div class="input-group">
		                    <form:input path="edcEdate"  title="종료일" maxlength="10" cssClass="form-control datetype"/>
		                </div>
   						<div><form:errors path="edcEdate" cssClass="error" /></div>
                    </div>

                     <div class="col-sm-1">
							까지
                    </div>

                  </div>




                  <div class="form-group row">
	                    <label for="edcDaysList" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 교육요일</label>
	                    <div class="col-sm-10"  style="padding-top:5px">
     <c:forEach items="${edcProgramVO.edcDaysList}" var="item2" varStatus="status">
     						<div class="form-check mr-3" style="display:inline-block">
	                          <form:checkbox path="edcDaysList[${status.index }].dayChk" cssClass="form-check-input edcDaygbn" value="${item2.edcDaygbn}"/>
	                          <label for="edcDaysList${status.index}.dayChk1" class="form-check-label">${item2.edcDaygbnNm}</label>
	                        </div>
     </c:forEach>
	                    </div>
	                  </div>
                  <div class="form-group row">
                    <label for="edcStimeHour" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 교육 시간</label>
                    <div class="col-sm-2">
                    	<div class="input-group">
                    		<form:select path="edcStimeHour" cssClass="form-control">
 <c:forEach var="i" begin="0" end="23">
                    			<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</form:option>

 </c:forEach>
 								</form:select>
		                </div>
                     </div>
                     <div class="col-sm-2">
                    	<div class="input-group">
                    		<form:select path="edcStimeMin" cssClass="form-control">
 <c:forEach var="i" begin="0" end="50" step="10">
                    			<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</form:option>

 </c:forEach>
 								</form:select>
		                </div>
                     </div>
                    <div class="col-sm-1">부터</div>

                     <div class="col-sm-2">
                    	<div class="input-group">
                    		<form:select path="edcEtimeHour" cssClass="form-control">
 <c:forEach var="i" begin="0" end="23">
                    			<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }시</form:option>

 </c:forEach>
 								</form:select>
		                </div>
                     </div>
                     <div class="col-sm-2">
                    	<div class="input-group">
                    		<form:select path="edcEtimeMin" cssClass="form-control">
 <c:forEach var="i" begin="0" end="50" step="10">
                    			<form:option value="${i<10?'0':''}${i }">${i<10?'0':''}${i }분</form:option>

 </c:forEach>
 								</form:select>
		                </div>
                     </div>

                     <div class="col-sm-1">까지</div>
                  </div>
                  <div class="form-group row">
	                    <label for="edcPncpa" class="col-sm-2 col-form-label"><span class="pilsu">*</span> 교육정원</label>
	                    <div class="col-sm-4" >
               						<div class="input-group">
			                    		<form:input path="edcPncpa" title="교육 총 정원" class="form-control numberchk" maxlength="5" />
					                    <div class="input-group-append">
					                      <span class="input-group-text">명</span>
					                    </div>
			                    	</div>
			   						<div><form:errors path="edcPncpa" cssClass="error" /></div>
							</div>


					</div>

 <c:if test="${edcProgramVO.edcPrgmid > 0}">
			               <div class="form-group row">
				               	<div class="col-sm-12">
				                	<c:choose>
				                		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
				                			 <button type="button" onclick="fn_program_rsvn()"  class="btn btn-primary form-control">모집정보 저장</button>
				                		</c:when>
				                		<c:otherwise>
				                			 <button type="button" class="btn btn-primary  disabled">모집정보 저장</button>
				                		</c:otherwise>
				                	</c:choose>
				                </div>
			               </div>
 </c:if>


                  </div>

                  <div class="tab-pane fade" id="custom-tabs-one-messages" role="tabpanel" aria-labelledby="custom-tabs-one-messages-tab">
	                  <div class="form-group row">
		                    <label for="edcReqGender" class="col-sm-2 col-form-label">* 성별제한</label>
		                    <div class="col-sm-10"  style="padding-top:5px">
	    						<div class="form-check" style="display:inline-block">
		                          <form:radiobutton path="edcReqGender" cssClass="form-check-input" value="3001"/>
		                          <label for="edcReqGender1" class="form-check-label">제한없음</label>
		                        </div>
		                        <div class="form-check" style="display:inline-block">
		                          <form:radiobutton path="edcReqGender" cssClass="form-check-input" value="1001"/>
		                          <label for="edcReqGender2" class="form-check-label">남자만</label>
		                        </div>
		                        <div class="form-check" style="display:inline-block">
		                          <form:radiobutton path="edcReqGender" cssClass="form-check-input" value="2001"/>
		                          <label for="edcReqGender3" class="form-check-label">여자만</label>
		                        </div>
	 	                    </div>
		               </div>
		               <div class="form-group row">
		                    <label for="edcLimitAgeyn1" class="col-sm-2 col-form-label">연령제한</label>
		                    <div class="col-sm-10">
		                    	<div class="form-check" style="margin-bottom:10px">
		                          <form:checkbox path="edcLimitAgeyn" cssClass="form-check-input" value="Y"/>
		                          <label for="edcLimitAgeyn1" class="form-check-label">제한 하기</label>

		                          <button type="button" class="btn btn-primary btn-xs disabled" style="margin-left:20px" id="limitAgeAddBtn">연령범위 추가</button>
		                        </div>

		                        <div id="limitAgeDiv" style="display:none">
		                        <table style="width:100%" class="table table-bordered">
									<colgroup>
			                  				<col width="40%">
			                  				<col width="25%">
			                  				<col width="25%">
			                  				<col width="10%">
			                  			</colgroup>
									<thead>
										<tr>
											<th>구분명</th>
											<th>부터~</th>
											<th>~까지</th>
											<th>삭제</th>
										</tr>
									</thead>
									<tbody>
<c:choose>
	<c:when test="${empty edcProgramVO.edcAgeList }">
										<tr>
											<td><input type="text" name="targetAgenm" style="width:100%"/></td>
											<td><input type="text" name="targetSage" style="width:100%"/></td>
											<td><input type="text" name="targetEage" style="width:100%"/></td>
											<td>
												<button type="button" class="btn btn-secondary btn-xs" onclick="fn_age_delete(this)">X</button>
											</td>
									</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${edcProgramVO.edcAgeList}" var="item" varStatus="status">
							         <tr>
											<td>
												<input type="hidden" name="targetAgeSeq" value="<c:out value="${item.edcAgeTargetSeq }"/>" />
												<input type="text" name="targetAgenm" style="width:100%" value="<c:out value="${item.edcTargetAgeNm }"/>" />
											</td>
											<td><input type="text" name="targetSage" style="width:100%" value="<c:out value="${item.edcTargetSage }"/>"/></td>
											<td><input type="text" name="targetEage" style="width:100%" value="<c:out value="${item.edcTargetEage }"/>"/></td>
											<td>
												<button type="button" class="btn btn-secondary btn-xs" onclick="fn_age_delete(this)">X</button>
											</td>
									</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>

		                        	</tbody>
		                        </table>
		                        <div>연령계산 방법은 (만나이 or 한국나이) 기관정보관리 ->운영환경 설정에서 변경하실 수 있습니다.</div>
		                        </div>
		                    </div>
	                  </div>
<c:if test="${edcProgramVO.edcPrgmid > 0}">
			               <div class="form-group row">
				               	<div class="col-sm-12">
				                	<c:choose>
				                		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
				                			 <button type="button" onclick="fn_program_target()"  class="btn btn-primary form-control">신청제한 수정</button>
				                		</c:when>
				                		<c:otherwise>
				                			 <button type="button" class="btn btn-primary  disabled">신청제한 수정</button>
				                		</c:otherwise>
				                	</c:choose>
				                </div>
			               </div>
</c:if>
                  </div>
                  <div class="tab-pane fade" id="custom-tabs-one-settings" role="tabpanel" aria-labelledby="custom-tabs-one-settings-tab">
                     	<div class="form-group row">
		                    <label for="noticeVO.notiPopyn" class="col-sm-2 col-form-label">공지사항 노출</label>
		                    <div class="col-sm-10">
		                    	<div class="form-check" style="margin-top:5px">
		                          <form:checkbox path="noticeVO.notiPopyn" cssClass="form-check-input" value="Y"/>
		                          <label for="noticeVO.notiPopyn1" class="form-check-label">노출하기</label>
		                        </div>
		                    </div>
		                </div>
		                <div class="form-group row">
		                    <label for="jongmok" class="col-sm-2 col-form-label">노출 기간</label>
								<div class="col-sm-10"  style="padding-top:5px">
			                       <div class="form-check" >
			                          <form:checkbox path="noticeVO.notiPerodsetYn" cssClass="form-check-input" value="Y"/>
			                          <label for="noticeVO.notiPerodsetYn1" class="form-check-label">기간설정</label>
			                        </div>
			                     </div>
								<div class="col-sm-2"></div>
				                    <div class="col-sm-4">
				                    	<div class="input-group">
						                    <form:input path="noticeVO.notiSdate"  title="시작일" maxlength="10" cssClass="form-control datetype" disabled="true"/>
						                </div>
				   						<div><form:errors path="noticeVO.notiSdate" cssClass="error" /></div>
				                    </div>
				                    <div class="col-sm-1">
											부터
				                    </div>
				                     <div class="col-sm-4">
				                    	<div class="input-group">
						                    <form:input path="noticeVO.notiEdate"  title="종료일" maxlength="10" cssClass="form-control datetype" disabled="true"/>
						                </div>
				   						<div><form:errors path="noticeVO.notiEdate" cssClass="error" /></div>
				                    </div>

				                     <div class="col-sm-1">
											까지
				                    </div>
		                  </div>
		                  <div class="form-group row">
		                    	<label for="jongmok" class="col-sm-2 col-form-label">공지 내용</label>
								<div class="col-sm-10"  >
										<form:textarea path="noticeVO.notiCnts" title="공지 내용" class="form-control " maxlength="5000" style="height:200px"/>
								</div>
							</div>
<c:if test="${edcProgramVO.edcPrgmid > 0}">
			               <div class="form-group row">
				               	<div class="col-sm-12">
				                	<c:choose>
				                		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
				                			 <button type="button" onclick="fn_program_notice()"  class="btn btn-primary form-control">프로그램공지 저장</button>
				                		</c:when>
				                		<c:otherwise>
				                			 <button type="button" class="btn btn-primary  disabled">프로그램공지 저장</button>
				                		</c:otherwise>
				                	</c:choose>
				                </div>
			               </div>
 </c:if>

                  </div>
                </div>
              </div>
              <!-- /.card -->
<c:if test="${edcProgramVO.edcPrgmid < 1}">
               <div class="card-footer">
                	<c:choose>
                		<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
                			 <button type="button" onclick="fn_save()"  class="btn btn-info form-control">신규프로그램 저장</button>
                		</c:when>
                		<c:otherwise>
                			 <button type="button" class="btn btn-info float-right disabled">신규프로그램 저장</button>
                		</c:otherwise>
                	</c:choose>
               </div>
                <!-- /.card-footer -->
 </c:if>
            </div>


        </div>
      </div>
</form:form>
     </div>

     <!-- 모달 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">분야 선택하기</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
              <table class="table table-sm border-0" id="CTG_TABLE">
                  <tbody>

                  </tbody>
                </table>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>

      <div class="modal fade" id="modal-teacher" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">강사 선택</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body">
              <div style="padding-bottom:10px;">
              		강사명 검색  : <input type="text" name="searchTeacher" id="searchTeacher" value="" />
              </div>



              <table class="table table-sm table-bordered" id="TCR_TABLE">
                  <tbody>

                  </tbody>
                </table>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>



</section>

 <script>
	//업로드 가능 확장자
	var UPLOAD_EXT = 'JPG, JPEG, PNG, GIF, HWP, DOC, DOCX, XLS, XLSX, PDF, TXT, PPT, PPTX, ZIP';
	var UPLOAD_EXTS = UPLOAD_EXT.split(',');

 //클릭 이벤트
  $('.preview').find("img").click(function(){
	  window.open($(this).attr("src"));
  })
//파일 업로드 처리
	$('.fileupload_process').click(function(e){
		$('#image-file-upload').remove();

		var last_flag = 0;
		var form = $('<form id="image-file-upload" style="display: block; width: 1px; height: 0px; overflow: hidden;" method="post" enctype="multipart/form-data"><input type="file" name="file" value="" /><input type="submit" value="업로드" /></form>');
		var inputnm = $(this).data("inputnm");
		var inputid = $(this).data("inputid");
		var fileid = $('#'+inputid).val();

		$(document.body).append(form);

		// 파일 업로드 버튼 클릭 처리
		form.find('[type="file"]').change(function(e){
			var val = $(this).val();
			if(val == ''){
				alert('파일을 선택해 주세요.');
				return;
			}

			// 확장자 체크
			var ext = $.trim(val.indexOf('.') > -1 ? val.substring(val.lastIndexOf('.') + 1).toUpperCase() : '');
			if(ext == ''){
				alert('확장자가 없는 파일은 선택하실 수 없습니다.');
				return false;
			}else{
				var check = false;
				for(var i=0; i<UPLOAD_EXTS.length; i++)
				{
					if($.trim(UPLOAD_EXTS[i]) == ext)
					{
						check = true;
						break;
					}
				}
				if(!check){
					alert(ext + ' 확장자는 첨부하실 수 없습니다.\n* 첨부 가능 확장자 : '+ UPLOAD_EXT);
					return false;
				}
			}

			$('body').showLoading();

			// 폼 전송 처리
			form.ajaxSubmit({
				type : 'POST',
				url : './extProgramUpload.json',
				data : { 'inputnm' : inputnm ,
							'inputid' : inputid ,
							'atchFileId' : fileid ,
							'edcPrgmid' : document.edcProgramVO.edcPrgmid.value ,
							'comcd': document.edcProgramVO.comcd.value
						},
				dataType : 'json',
				error : function()
				{
					alert('업로드 처리 중 오류가 발생하였습니다.');
					$('body').hideLoading();
				},
				beforeSend : function(xhr)
				{
					 xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success: function(data, statusText, xhr, $form)
				{
					form.empty().remove();
					console.log(data);

					$('body').hideLoading();

					if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        } else {


			        	$('#'+inputnm).val(data.originFileName);
			        	$('#'+inputid).val(data.atchFileId);

			        	if(inputid == "edcPlanFileid"){
			        		//강의계획서
			        		alert("강의계획서가 변경되었습니다.");
			        		$('#'+inputid+'Preview').html("<a href='"+data.realFilePath+"'><i class=\"fas fa-download\"></i> 다운로드</a>");
			        	}else{
			        		alert("이미지가 변경되었습니다.");
			        		var img = document.createElement("img");
							img.setAttribute("src", data.realFilePath);
							img.style.height = "50px";
							img.style.cursor = "pointer";

							img.onclick = function(){
								window.open(data.realFilePath);
							}

							$('#'+inputid+'Preview').html("");
							$('#'+inputid+'Preview').append(img);
			        	}
			        }
				}
			});
		}).click();
	});
 </script>
</body>