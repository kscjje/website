<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : orgInfoDetail.jsp
  * @Description : 기관 정보 관리JSP
  * @Modification Information
  * @
  * @  수정일              수정자            수정내용
  * @ -------       ------    ---------------------------
  * @ 2021.10.26    진수진           최초 생성
  *
  *  @author 진수진
  *  @since 2021.10.26
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
<script type="text/javascript" src="<c:url value="${adminRoot}/resources/se/js/HuskyEZCreator.js"/>" charset="utf-8"></script>
<script type="text/javascript">

	//에디터
	var oEditors = [];
	var orgTypeList; // 기관 유형 담는 객체

	//업로드 가능 확장자
	var UPLOAD_EXT = 'JPG, JPEG, PNG, GIF, HWP, DOC, DOCX, XLS, XLSX, PDF, TXT, PPT, PPTX, ZIP';
	var UPLOAD_EXTS = UPLOAD_EXT.split(',');

	$(document).ready(function(){
		fn_detail_load();
		fn_contents_load();

		$('#tablist').find("li a").click(function(){
			$('#tablist').find("li a").removeClass("active");
			$(this).addClass("active");

			if($(this).attr("id") == "ORG_INFO_CONTENTS_TAB"){
				$('#orgInfoBtn').attr("href" , "javascript:fn_contents_save()");
				fn_contents_load();
			}else if($(this).attr("id") == "ORG_INFO_DC_TAB"){
				$('#orgInfoBtn').attr("href" , "javascript:fn_contents_save()");
				fn_dcmng_load();
			}else{
				$('#orgInfoBtn').attr("href" , "javascript:fn_optinfo_save()");
				fn_optinfo_load();
			}

		});

		 //에디터
		nhn.husky.EZCreator.createInIFrame({
			oAppRef: oEditors,
			elPlaceHolder: "orgClassmngGuide",
			sSkinURI: "<c:url value="${adminRoot}/resources/se/SmartEditor2SkinAjax.html"/>",
			htParams : {
				bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
				bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
				bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
				//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
				fOnBeforeUnload : function(){
					//alert("완료!");
				}
			}, //boolean
			fOnAppLoad : function(){
				//예제 코드
				//oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
			},
			fCreator: "createSEditor2"
		});

	});

	function fn_print_orgtype(parentCd , defaultVal){
		$('#orgMtype').html('');

		var cnt=0;
		for(var i = 0 ; i<orgTypeList.length ; i++){
			var item = orgTypeList[i];
			if(item.item1 == parentCd){
				if($('#orgMtype').html() == ""){
					$('#orgMtype').append("<option value=''>선택</option>");
				}

				var selected = "";
				if(item.cd == defaultVal) selected = " selected";

				$('#orgMtype').append("<option value='"+item.cd+"'"+selected+">"+item.cdNm+"</option>");
				cnt++;
			}

		}
		if(cnt < 1){
			$('#orgMtype').append("<option value=''>없음</option>");
		}
	}

	function fn_detail_load(){
		$.ajax({
		      url: './orgInfoDetailAjax',
		      data : {'comcd': '<c:out value="${searchVO.comcd}"/>', 'orgNo' :  '<c:out value="${searchVO.orgNo}"/>'},
		      type : "GET",
		      dataType : "html",
		      cache : false,
		      success: function(data) {
		    	  	if(data.indexOf("login_area") > 0 ){
		    	  		__ajaxHtmlCallback__();
		    	  	}else{
		    	  		$('#ORG_INFO_DETAIL').html(data);
		    	  	}

		      }

		});
	}

	function fn_contents_load(){
		$.ajax({
		      url: './orgInfoContentsAjax',
		      data : {'comcd': '<c:out value="${searchVO.comcd}"/>', 'orgNo' :  '<c:out value="${searchVO.orgNo}"/>'},
		      type : "GET",
		      dataType : "html",
		      cache : false,
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
						$('#ORG_INFO_BODY').html(data);
		    	  	}
		      }

		});
	}

	function fn_optinfo_load(){
		$.ajax({
		      url: './orgInfoOptinfoAjax',
		      data : {'comcd': '<c:out value="${searchVO.comcd}"/>', 'orgNo' :  '<c:out value="${searchVO.orgNo}"/>'},
		      type : "GET",
		      dataType : "html",
		      cache : false,
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
						$('#ORG_INFO_BODY').html(data);
		    	  	}
		      }

		});
	}

	function fn_dcmng_load(){
		$.ajax({
		      url: './orgInfoDcAjax',
		      data : {'comcd': '<c:out value="${searchVO.comcd}"/>', 'orgNo' :  '<c:out value="${searchVO.orgNo}"/>'},
		      type : "GET",
		      dataType : "html",
		      cache : false,
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
						$('#ORG_INFO_BODY').html(data);
		    	  	}
		      }
		});

	}
	function fn_detail_save(){
		var f = document.orgInfoVO;
		if(f.orgNm.value == ""){
			alert("기관명을 입력해 주세요;");
			f.orgNm.focus();
		}else if(f.areaCd.value == ""){
			alert("지역을 선택해 주세요.");
			f.areaCd.focus();
		}else if(f.orgTel.value ==""){
			alert("대표전화를 입력해 주세요.");
			f.orgTel.focus();
		}else if(f.addr.value == ""){
			alert("주소를 입력해 주세요.");
			f.addr.focus();
		}else{
			if(confirm("수정하시겠습니까?")){
				$.ajax({
				      url: './orgInfoDetailSave.json',
				      type : "POST",
				      dataType : "json",
				      data : $('#orgInfoVO').serialize(),
		              success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        }else{
					        	 fn_detail_load();
					        	 alert(data.result.msg);
					        }
				      }
				});
			}
		}
	}
	function fn_contents_save(){
		var f = document.orgContentsVO;
		if(f.orgGuide.value == ""){
			alert("소개를 입력해 주세요;");
			f.orgGuide.focus();
		}else{
			if(confirm("수정하시겠습니까?")){
				oEditors.getById["orgRetdcGuide"].exec("UPDATE_CONTENTS_FIELD", []);

				$.ajax({
				      url: './orgInfoContentsSave.json',
				      type : "POST",
				      dataType : "json",
				      data : $('#orgContentsVO').serialize(),
		              success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        }else{
					        	fn_contents_load();
					        	 alert(data.result.msg);
					        }
				      }
				});
			}
		}
	}

	function fn_dcmng_save(){
		if(confirm("수정하시겠습니까?")){
			$.ajax({
			      url: './orgInfoDcSave.json',
			      type : "POST",
			      dataType : "json",
			      data : $('#orgDcVO').serialize(),
	              success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        }else{
				        	fn_dcmng_load();
				        	alert(data.result.msg);
				        }
			      }
			});
		}
	}
	function fn_delete(){
		if(confirm("연관된 교육프로그램이 없을 경우 삭제가능합니다.\n정말 삭제하시겠습니까?")){
			$.ajax({
			      url: './orgInfoDelete.json',
			      type : "POST",
			      dataType : "json",
			      data : $('#orgInfoVO').serialize(),
	              success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        }else{
				        	alert("삭제되었습니다.");
				        	window.location.replace("./orgInfoList${queryString}");
				        }
			      }
			});
		}
	}

	function fn_lectguide_save(){
		if(confirm("강좌 소개를 수정하시겠습니까?")){
			oEditors.getById["orgClassmngGuide"].exec("UPDATE_CONTENTS_FIELD", []);
			$.ajax({
			      url: './orgInfoGuideSave.json',
			      type : "POST",
			      dataType : "json",
			      data : $('#searchVO').serialize(),
	              success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        }else{
				        	alert(data.result.msg);
				        }
			      }
			});
		}
	}

	function fn_list(){
		window.location.href= "./orgInfoList${queryString}";
	}
</script>
</head>
<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>기관 정보 관리</h1>
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
          <div class="col-md-6">
            <div class="card card-primary card-outline">
              <div class="card-header">
                <h3 class="card-title">기본 정보</h3>

              </div>
              <!-- /.card-header -->
              <div class="card-body" id="ORG_INFO_DETAIL">


                </div>
                <!-- /.card-body -->

            <!-- /.card -->
          </div>

        </div>
         <div class="col-md-6">
         	<div class="card card-primary card-outline card-outline-tabs">
              <div class="card-header p-0 border-bottom-0">
                <ul class="nav nav-tabs" id="tablist">
                  <li class="nav-item">
                    <a class="nav-link active" href="javascript:;" id="ORG_INFO_CONTENTS_TAB" >기관소개</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link"  href="javascript:;" id="ORG_INFO_OPT_TAB">운영환경 설정</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link"   href="javascript:;" id="ORG_INFO_DC_TAB" >수강료 감면기준 설정</a>
                  </li>
                </ul>
              </div>

              <!-- /.card-header -->

              <div class="card-body"  id="ORG_INFO_BODY">

              </div>

           </div>
        </div>
      </div>
	      <div class="row">
	          <div class="col-md-12">
	            <div class="card card-primary card-outline">
	              <div class="card-header">
	                <h3 class="card-title">강좌신청 안내</h3>
	              </div>
<form:form commandName="searchVO" name="searchVO"  method="post" >
<form:hidden path="comcd" />
<form:hidden path="orgNo" />
	              <!-- /.card-header -->
	              <div class="card-body ">
	                  <div class="form-group row">
	                  	<div class="col-sm-2 col-form-label"><label>강좌신청 안내</label>
              				<br/>(기관유형1 남양주시청+평생시민대학의 각 대학별만 적용)
              				<br/><br/>*나머지 컨텐츠는 관리자ID로 사용자 화면에서 수정가능
              			</div>
			         	<div class="col-sm-10" >
		                    <textarea name="orgClassmngGuide" id="orgClassmngGuide" title="강좌소개" class="form-control" style="height:500px">${orgContentsVO.orgClassmngGuide}</textarea>
	                  	</div>
	                  </div>
	              </div>
	               <div class="card-footer">
	               	<div class="row">
	               			<div class="col-sm-4"></div>
							<div class="col-sm-4">
				              	<c:choose>
				              		<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
				              			 <button type="button" onclick="fn_lectguide_save()"  class="btn btn-info form-control">강좌신청안내 저장</button>
				              		</c:when>
				              		<c:otherwise>
				              			 <button type="button" class="btn btn-info form-control disabled">강좌신청안내 저장</button>
				              		</c:otherwise>
				              	</c:choose>
				            </div>
				            <div class="col-sm-4"></div>
				      </div>

	             </div>
	              <!-- /.card-footer -->
</form:form>
	         </div>
			</div>
	     </div>
     </div>
</section>
</body>