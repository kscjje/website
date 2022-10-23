<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includeTop.jsp"%>


<%
 /**
  * @Class Name : webMemberDetail.jsp
  * @Description : 웹 회원 상세 조회
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.03.22    전영석          최초 생성
  *
  *  @author 전영석
  *  @since 2021.03.22
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
<style type="text/css">
	th {background-color:#f4f6f9;}
</style>
<script type="text/javascript" src="../validator"></script>
<validator:javascript formName="memberUserVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script>
	var fullAddr = ""; // 최종 주소 변수
	var extraAddr = ""; // 조합형 주소 변수

	$(document).ready(function(){
	    //Datemask dd/mm/yyyy
		$('.date').inputmask({ mask: "y-1-2", leapday: "-02-29", placeholder: "yyyy-mm-dd", separator: "-", alias: "yyyy-mm-dd" });

		$('#hp').bind('keyup blur', function(e){
			printHp($(this));
		});

		if($('#hp').val() != ""){
			printHp($('#hp'));
		};

		fn_load_class(1);

		$('#addDiscBtn').click(function(){
			var orgNo = $('#orgNo').val();
			if(orgNo == ""){
				alert("기관을 먼저 선택해 주세요.");
				return false;
			}

			$('#modal-default').find(".modal-title").text("할인종류 추가");
			var orgNm = $('#orgNo option:checked').text();

			$('#orgNmText').html(orgNm);
			document.registForm.orgNo.value = orgNo;
			document.registForm.discountSeq.value = "0";
			fn_load_dckind("");

		});

		$('input[name=dcconfirmYn]').click(function(){
			var val = $('input[name=dcconfirmYn]:checked').val();
			if(val == "Y"){
				$('input[name=dcconfSdate]').prop("disabled" , false);
				$('input[name=dcconfEdate]').prop("disabled" , false);
			}else{
				$('input[name=dcconfSdate]').prop("disabled" , true);
				$('input[name=dcconfEdate]').prop("disabled" , true);
			}
		});

		
		$('#addFamilyBtn').click(function(){
			console.log('addFamilyBtn');
			var orgNo = $('#orgNo').val();
			
			
			
			$('#modal-family-default').find(".modal-title").text("할인종류 수정");
			/*
			if(orgNo == ""){
				alert("기관을 먼저 선택해 주세요.");
				return false;
			}

			$('#modal-default').find(".modal-title").text("할인종류 추가");
			var orgNm = $('#orgNo option:checked').text();

			$('#orgNmText').html(orgNm);
			document.registForm.orgNo.value = orgNo;
			document.registForm.discountSeq.value = "0";
			fn_load_dckind("");
			*/
		});
		
		
		
		
		$('#orgNo').change(function(){
			fn_load_dclist();
		})

	});

	function fn_load_class(pageIndex){
		$.ajax({
		      url: './userTabClassAjax',
		      type : "GET",
		      dataType : "html",
		      cache : false,
		      data : {'memNo': $('#memNo').val() , 'pageIndex':pageIndex},
              success: function(data) {
            	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
		    	  	 $('#tabs-class-history').html(data);
		    	  	}
		      }
		});
	}

	function fn_load_pay(pageIndex){
		$.ajax({
		      url: './userTabPayAjax',
		      type : "GET",
		      dataType : "html",
		      cache : false,
		      data : {'memNo': $('#memNo').val(), 'pageIndex':pageIndex},
              success: function(data) {
            	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
		    	   		$('#tabs-pay-history').html(data);
		    	  	}
		      }
		});
	}

	function fn_load_card(pageIndex){
		$.ajax({
		      url: './userTabCardAjax',
		      type : "GET",
		      dataType : "html",
		      cache : false,
		      data : {'memNo': $('#memNo').val(), 'pageIndex':pageIndex},
              success: function(data) {
            	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
		    	   		$('#tabs-memcard').html(data);
		    	  	}
		      }
		});
	}

	function fn_load_dclist(){
		var orgNo = $('#orgNo').val();
		if(orgNo == "") orgNo = "0";
		$.ajax({
		      url: './userTabDiscAjax',
		      type : "GET",
		      dataType : "html",
		      cache : false,
		      data : {'memNo': $('#memNo').val(), 'orgNo':orgNo},
            success: function(data) {
            	if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	 }else{
		    	   $('#tabs-dc-list .contents').html(data);
	    	  	}
		      }
		});
	}

	function fn_load_dc_detail(orgNo , seq){
		document.registForm.discountSeq.value = seq;
		document.registForm.orgNo.value = orgNo;

		$('#modal-default').find(".modal-title").text("할인종류 수정");

		$.ajax({
		      url: './userTabDcDetail.json',
		      type : "GET",
		      cache : false,
		      data : $('#registForm').serialize(),
              success: function(data) {
            	  $('#orgNmText').html(data.item.orgNm);

            	  if(data.item.dcconfirmYn == "Y"){
            		  $('#dcconfirmYn2').prop("checked" , true);
            		  $('input[name=dcconfSdate]').prop("disabled" , false).val(data.item.dcconfSdate);
      				  $('input[name=dcconfEdate]').prop("disabled" , false).val(data.item.dcconfEdate);
            	  }else{
            		  $('#dcconfirmYn1').prop("checked" , true);
            		  $('input[name=dcconfSdate]').prop("disabled" , true);
      				  $('input[name=dcconfEdate]').prop("disabled" , true);
            	  }
            	  fn_load_dckind(data.item.discountCd);
		      }
		});
	}

	function fn_load_dckind(defaltVal){
		var orgNo = document.registForm.orgNo.value;
		$('#discountCd').html("");
		$('#discountCd').append("<option value=''>::선택::</option>");

		$.ajax({
		      url: './userTabDclist.json',
		      type : "GET",
		      cache : false,
		      data : {'orgNo': orgNo},
              success: function(data) {
		    	  if(data.discList && data.discList.length > 0){
		    		  for(var i=0;i<data.discList.length;i++){
		    			   var dataItem = data.discList[i];
		    			   var selected = "";
		    			   if(defaltVal == dataItem.dcReasonCd){
		    				   selected = " selected";
		    			   }
							$('#discountCd').append("<option value='"+dataItem.dcReasonCd +"'"+selected+">"+dataItem.dcNm + " (할인율:"+dataItem.dcRate+"%)"+"</option>");

		    		  }
		    	  }else{
		    		  $('#discountCd').append("<option value=''>설정된 감면정보가 없습니다</option>");
		    	  }
		      }
		});
	}

	/*가족사항*/
	function fn_load_familylist(){
		
		var orgNo = $('#orgNo').val();
		if(orgNo == "") orgNo = "0";
		$.ajax({
		      url: './userTabFamilyAjax',
		      type : "GET",
		      dataType : "html",
		      cache : false,
		      data : {'memNo': $('#memNo').val(), 'orgNo':orgNo},
            success: function(data) {
            	if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	 }else{
		    	   $('#tabs-family-list .contents').html(data);
	    	  	}
		      }
		});
		
	}
	
	function fn_save() {
		var form = document.memberUserVO;
		var msg = "수정하시겠습니까?";

		if (!validateMemberUserVO(form)) {
			return ;
		}

		if(confirm(msg)){
			$.ajax({
			      url: './userUpdtSave.json',
			      type : "POST",
			      dataType : "json",
			      data : $('#memberForm').serialize(),
	              success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        }else{
				        	 alert(data.result.msg);

				        	 window.location.replace("./userList${searchQuery}");
				        }
			      }
			});
		}

/*
		var email = $('#email').val();
		var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;//이메일 정규식

		if(email != "" && !emailRule.test(email){
			alert("이메일 주소가 형식에 맞지 않습니다.");
			$('#email').focus();
			return;
		}
*/
	}

	function fn_disc_save(){
		var f = document.registForm;
		if(f.discountCd.value == ""){
			alert("할인 사유를 선택해 주세요.");
			f.discountCd.focus();
		}else if(f.dcconfirmYn[1].checked && f.dcconfSdate.value == ""){
			alert("유효 시작일을 입력해 주세요.")
			f.dcconfSdate.focus();
		}else if(f.dcconfirmYn[1].checked && f.dcconfEdate.value == ""){
				alert("유효 종료일을 입력해 주세요.")
				f.dcconfEdate.focus();
		}else{
			var msg = "할인 사유를 등록하시겠습니까?";
			if(f.discountSeq.value != "" && f.discountSeq.value != "0"){
				msg = "할인 사유를 수정하시겠습니까?";
			}
			if(confirm(msg)){
				$.ajax({
				      url: './userTabDcSave.json',
				      type : "POST",
				      dataType : "json",
				      data : $('#registForm').serialize(),
		              success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        }else{
					        	fn_load_dclist();
					        	 alert(data.result.msg);
					        	 $('#btnClose').click();
					        }
				      }
				});
			}
		}
	}
</script>
</head>
<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
             <h1>회원 현황</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>

<!-- Main content -->
<section class="content">
      <div class="container-fluid">
	       <div class="row">
	          <div class="col-12">
	            <div class="card card-primary card-outline">
	              <!-- /.card-header -->
	              <div class="card-body  table-responsive p-0">
<form:form commandName="memberUserVO" name="memberUserVO" id="memberForm" action="./userUpdtSave"  method="post">
<form:hidden path="comcd"/>
<form:hidden path="memNo"/>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    //document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    //document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('postNum').value = data.zonecode;
                document.getElementById("addr1").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("addr2").focus();
            }
        }).open();
    }
</script>


<input type="hidden" name="searchQuery" value="${searchQuery}" />
	                	<table class="table table-bordered text-nowrap">
		               	<colgroup>
							<col style="width:5%;">
							<col style="width:45%;">
							<col style="width:5%;">
							<col style="width:45%;">
						</colgroup>

						<tbody>
						<tr>
							<th>이름 / 아이디</th>
							<td class="left">
								<div class="input-group input-group-sm" style="width: 150px;float:left">
									<form:input path="memNm"  placeholder="이름 입력해주세요." title="이름" maxlength="20" cssClass="form-control"/>
				                </div>
				                <div style="float:left;padding-left:10px">
				                	/ <c:out value="${empty memberUserVO.id?'없음': memberUserVO.id}" />
				                </div>
							</td>
							<th>생년월일/성별</th>
							<td class="left">
								<div class="input-group input-group-sm" style="width: 100px;float:left">
				                    <form:input path="birthDate"  title="생년월일" maxlength="10" cssClass="form-control date"/>
				                </div>
				                <div class="input-group input-group-sm" style="width: 100px;float:left">
				                    <form:select path="birthSec" class="form-control">
				                    	<form:option value="S">양력</form:option>
				                    	<form:option value="M">음력</form:option>
				                    </form:select>
				                </div>
				                <div class="form-group clearfix" style="float:left;padding-left:20px;margin-bottom:0px">
				                      <div class="icheck-primary d-inline">
				                      	<form:radiobutton path="gender" value="1"/>
				                        <label for="gender1">남자</label>
				                      </div>
				                      <div class="icheck-primary d-inline">
				                        <form:radiobutton path="gender" value="2"/>
				                        <label for="gender2">
				                        	여자
				                        </label>
				                      </div>
			                    </div>
							</td>
						</tr>

						<tr>
							<th>휴대폰</th>
							<td  class="left">
								<div class="input-group input-group-sm">
				                    <div class="input-group-prepend">
				                      <span class="input-group-text"><i class="fas fa-phone"></i></span>
				                    </div>
				                   <form:input path="hp"  title="휴대폰" maxlength="13" cssClass="form-control"/>
				                  </div>

				                   <div><form:errors path="hp" cssClass="error" /></div>

							</td>
							<th>이메일</th>
							<td class="left">
								<div class="input-group input-group-sm">
									<form:input path="email"  title="이메일" maxlength="100" cssClass="form-control"/>
								</div>
							</td>
						</tr>
						
						<tr>
							<th>거주지 구분</th>
							<td  class="left" colspan="3">
							
				                <div class="form-group clearfix" style="float:left;padding-left:20px;margin-bottom:0px">
				                      <div class="icheck-primary d-inline">
				                      	<form:radiobutton path="resdncGbn" value="1010"/>
				                        <label for="resdncGbn1">관내거주</label>
				                      </div>
									<div class="icheck-primary d-inline">
				                      	<form:radiobutton path="resdncGbn" value="1011"/>
				                        <label for="resdncGbn2">관내권역(구/동)거주</label>
				                      </div>				                      
				                      <div class="icheck-primary d-inline">
				                        <form:radiobutton path="resdncGbn" value="2010"/>
				                        <label for="resdncGbn3">
				                        	관외거주
				                        </label>
				                      </div>
			                    </div>							


							</td>
						</tr>
						
						<tr>
							<th>회원상태</th>
							<td>${memberUserVO.statusNm} ( 가입일자 : <hisco:DateUtil datestr="${memberUserVO.joinDate}" format="yyyy.MM.dd"/>)</td>
							<th>SMS 수신동의</th>
							<td>
								<div class="form-group clearfix" style="float:left;margin-bottom:0px">
				                      <div class="icheck-primary d-inline">
				                      	<form:radiobutton path="smsYn" value="Y"/>
				                        <label for="smsYn1">수신 동의</label>
				                      </div>
				                      <div class="icheck-primary d-inline">
				                        &nbsp; &nbsp;
				                        <form:radiobutton path="smsYn" value="N"/>
				                        <label for="smsYn2">
				                        	수신 미동의
				                        </label>
				                      </div>
			                    </div>
							</td>
						</tr>
						<tr>
							<th>탈퇴/휴면일자</th>
							<td><fmt:formatDate value="${memberUserVO.outDate}" pattern="yyyy.MM.dd HH:mm"/></td>
							<th>회원유형</th>
							<td>
								<c:choose>
									<c:when test="${empty  memberUserVO.id}">방문등록 회원</c:when>
									<c:otherwise>온라인등록 회원</c:otherwise>
								</c:choose>
							</td>
						</tr>

						<tr>
							<th>주소</th>
							<td colspan="3" class="left">
							<!-- 
								<c:if test="${!empty memberUserVO.postNum}">(<c:out value="${memberUserVO.postNum }" />) </c:if><c:out value="${memberUserVO.addr1 }" /> <c:out value="${memberUserVO.addr2 }" />
							 -->
								<span>
								<form:input type="text" path="postNum" style="width:200px;display:inline;margin-bottom:14px;" maxlength="10" class="form-control" placeholder="우편번호"/>
								&nbsp;&nbsp;<button type="button" id="btnSearchAddress" class="btn btn-info btn-sm" onclick="sample6_execDaumPostcode();">주소검색</button>
								</span>
								<form:input type="text" path="addr1" class="form-control" placeholder="주소"/><br/>
								<form:input type="text" path="addr2" class="form-control" placeholder="상세주소"/>
								<input type="hidden" class="form-control" id="sample6_extraAddress" placeholder="참고항목"/>								
								
															
							</td>
						</tr>

					</tbody>

						</table>
</form:form>
	                </div>
	                <!-- /.card-body -->
	                <div class="card-footer">
	                	<c:choose>
							<c:when test="${commandMap.selectedMenu.updYn eq 'Y' and  memberUserVO.status eq '0000'}">
								<button type="button" onclick="fn_save()" class="btn btn-info float-right">저장</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn btn-info float-right disabled">저장</button>
							</c:otherwise>
						</c:choose>

	                  <a href="./userList${searchQuery}" class="btn btn-default">목록</a>
	                </div>
	                <!-- /.card-footer -->

	              </div>
	              <!-- /.card-body -->
	            </div>
	            <!-- /.card -->
	          </div>
	          <div class="row">
	          	<div class="col-12">
		            <div class="card card-primary card-outline card-outline-tabs">
		              <div class="card-header p-0 border-bottom-0">
		                <ul class="nav nav-tabs" id="custom-tabs-four-tab" role="tablist">
		                  <li class="nav-item">
		                    <a class="nav-link active" id="custom-tabs-four-home-tab" data-toggle="pill" href="#tabs-class-history" aria-controls="tabs-class-history" onclick="fn_load_class(1)">수강이력</a>
		                  </li>
		                  <li class="nav-item">
		                    <a class="nav-link" id="custom-tabs-four-profile-tab" data-toggle="pill" href="#tabs-pay-history" aria-controls="tabs-pay-history" onclick="fn_load_pay(1)">신용결제이력</a>
		                  </li>
		                  <li class="nav-item">
		                    <a class="nav-link" id="custom-tabs-four-messages-tab" data-toggle="pill" href="#tabs-memcard" aria-controls="tabs-memcard" onclick="fn_load_card(1)">회원카드</a>
		                  </li>
		                  <li class="nav-item">
		                    <a class="nav-link" id="custom-tabs-four-settings-tab" data-toggle="pill" href="#tabs-dc-list" aria-controls="tabs-dc-list" onclick="fn_load_dclist()">감면정보</a>
		                  </li>
		                  <li class="nav-item">
		                    <a class="nav-link" id="custom-tabs-four-settings-tab" data-toggle="pill" href="#tabs-family-list" aria-controls="tabs-family-list" onclick="fn_load_familylist()">가족사항</a>
		                  </li>
		                </ul>
		              </div>
		              <div class="card-body  p-0">
		                <div class="tab-content" id="custom-tabs-four-tabContent">
		                  <div class="tab-pane fade show active" id="tabs-class-history" role="tabpanel" aria-labelledby="custom-tabs-four-home-tab" style="padding:10px">


		                  </div>
		                  <div class="tab-pane fade" id="tabs-pay-history" role="tabpanel" aria-labelledby="custom-tabs-four-profile-tab" style="padding:10px">

		                  </div>
		                  <div class="tab-pane fade" id="tabs-memcard" role="tabpanel" aria-labelledby="custom-tabs-four-messages-tab" style="padding:10px">

		                  </div>
		                  <div class="tab-pane fade" id="tabs-dc-list" role="tabpanel" aria-labelledby="custom-tabs-four-settings-tab">
		                    		<div style="padding:10px ">
		                    			<div style="width: 90px;float:left;padding-top:7px">
		                    				<b>※ 기관 선택 :</b>
		                    			</div>
		                    			<div style="width: 350px;float:left;padding-bottom:10px">
		                    				<hisco:OrgList defaultNo="${searchVO.orgNo}" id="orgNo" blankYn="N"/>
		                    			</div>
		                    			<div style="float:left;padding-left:10px">
		                    				<c:choose>
												<c:when test="${commandMap.selectedMenu.updYn eq 'Y' and  memberUserVO.status eq '0000'}">
		                    						<button type="button" id="addDiscBtn"  class="btn btn-info btn-sm" data-toggle="modal" data-target="#modal-default">할인종류 추가</button>
		                    					</c:when>
		                    					<c:otherwise>
		                    						<button type="button" class="btn btn-info btn-sm disabled">할인종류 추가</button>
		                    					</c:otherwise>
		                    				</c:choose>
		                    			</div>

									</div>
									<div style="padding:10px" class="contents">

									</div>
		                  </div>
		                  
		                  <div class="tab-pane fade" id="tabs-family-list" role="tabpanel" aria-labelledby="custom-tabs-four-messages-tab" style="padding:10px">
		                  		
		                  		<div style="padding:10px ">
		                  			<button type="button" id="addFamilyBtn"  class="btn btn-info btn-sm" data-toggle="modal" data-target="#modal-family-default">가족등록</button>
		                  		</div>
		                  		<div style="padding:10px" class="contents"></div>
		                  </div>
		                  
		                  
		                </div>
		              </div>
		              <!-- /.card -->
		            </div>
		          </div>
	          </div>
        </div>

     <!-- 모달 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">할인종류 추가</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
 <form name="registForm" id="registForm">
 <input type="hidden" name="orgNo" value=""/>
 <input type="hidden" name="discountSeq" value="0"/>
 <input type="hidden" name="memNo" value="<c:out value="${memberUserVO.memNo}"/>"/>
              <table class="table table-sm" >
                  <tbody>
						<tr>
							<th style="padding-top:10px;padding-bottom:10px">등록 기관명  </th>
							<td class="left" id="orgNmText" style="padding-top:10px;padding-bottom:10px">

							</td>
						</tr>
						<tr>
							<th>* 할인사유  </th>
							<td class="left">
								<div class="input-group input-group-sm" >
									<select name="discountCd" id="discountCd" class="form-control">
										<option value="">::선택::</option>
									</select>
				                </div>
							</td>
						</tr>
						<tr>
							<th>유효기간 </th>
							<td class="left">
								<div class="row">
									<div class="icheck-primary d-inline">
				                      	<input type="radio" name="dcconfirmYn" id="dcconfirmYn1" value="N" checked/>
				                        <label for="dcconfirmYn1">미적용</label>
				                      </div>
				                      <div class="icheck-primary d-inline" style="padding-left:10px">
				                       <input type="radio" name="dcconfirmYn" id="dcconfirmYn2" value="Y"/>
				                        <label for="dcconfirmYn2">
				                        	적용
				                        </label>
				                      </div>
								</div>
								<div class="row" style="padding-top:10px">
					                <div class="input-group input-group-sm" style="width: 100px;float:left">
					                    <input type="text" name="dcconfSdate"  title="시작일" maxlength="10" class="form-control date" disabled/>
					                </div>
					                <div class="input-group input-group-sm" style="width: 30px;float:left;padding-left:10px">
					                ~
					                </div>
					                <div class="input-group input-group-sm" style="width: 100px;float:left;">
					                     <input type="text" name="dcconfEdate"  title="종료일" maxlength="10" class="form-control date" disabled/>
					                </div>
								</div>

							</td>
						</tr>
                  </tbody>
                </table>
 </form>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" id="btnClose" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" onclick="fn_disc_save()" class="btn btn-primary">저장</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      
      
      
    <!-- 모달 박스 -->
     <div class="modal fade" id="modal-family-default" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">가족회원 추가</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
 <form name="registForm" id="registForm">
 <input type="hidden" name="orgNo" value=""/>
 <input type="hidden" name="discountSeq" value="0"/>
 <input type="hidden" name="memNo" value="<c:out value="${memberUserVO.memNo}"/>"/>
              <table class="table table-sm" >
                  <tbody>
						<tr>
							<th style="padding-top:10px;padding-bottom:10px">등록 기관명  </th>
							<td class="left" id="orgNmText" style="padding-top:10px;padding-bottom:10px">

							</td>
						</tr>
						<tr>
							<th>* 할인사유  </th>
							<td class="left">
								<div class="input-group input-group-sm" >
									<select name="discountCd" id="discountCd" class="form-control">
										<option value="">::선택::</option>
									</select>
				                </div>
							</td>
						</tr>
						<tr>
							<th>유효기간 </th>
							<td class="left">
								<div class="row">
									<div class="icheck-primary d-inline">
				                      	<input type="radio" name="dcconfirmYn" id="dcconfirmYn1" value="N" checked/>
				                        <label for="dcconfirmYn1">미적용</label>
				                      </div>
				                      <div class="icheck-primary d-inline" style="padding-left:10px">
				                       <input type="radio" name="dcconfirmYn" id="dcconfirmYn2" value="Y"/>
				                        <label for="dcconfirmYn2">
				                        	적용
				                        </label>
				                      </div>
								</div>
								<div class="row" style="padding-top:10px">
					                <div class="input-group input-group-sm" style="width: 100px;float:left">
					                    <input type="text" name="dcconfSdate"  title="시작일" maxlength="10" class="form-control date" disabled/>
					                </div>
					                <div class="input-group input-group-sm" style="width: 30px;float:left;padding-left:10px">
					                ~
					                </div>
					                <div class="input-group input-group-sm" style="width: 100px;float:left;">
					                     <input type="text" name="dcconfEdate"  title="종료일" maxlength="10" class="form-control date" disabled/>
					                </div>
								</div>

							</td>
						</tr>
                  </tbody>
                </table>
 </form>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" id="btnClose" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" onclick="fn_disc_save()" class="btn btn-primary">저장</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>      
</section>
</body>