<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name : eduLimitList.jsp
  * @Description : 신청제한 횟수 관리 JSP
  * @Modification Information
  * @
  * @  수정일              수정자            수정내용
  * @ -------       ------    ---------------------------
  * @ 2021.11.15    진수진           최초 생성
  *
  *  @author 진수진
  *  @since 2021.11.15
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
<script>
	$(document).ready(function(){
		var height = $(window).height() - 250;
		$('#orgListDiv').css("height" , height + "px");

		fn_detail_load(0);

		$('#CtgCdBtn').click(function(){
			fn_open_comctg('');
		});

		$('#searchKeyword2').keydown(function(){
			if(event.keyCode==13){
				fn_search_program();
			}
		});

		$('#allProgramChk').click(function(){
			$('input[name=chkProgmid]').prop("checked" , $(this).prop("checked"));
		})


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

			        $.each($('#CTG_TABLE').find("input[name=listCtgCd]") , function(item,index){
			        	var a = $(this).parent().find("a");

			        	$(this).parent().find("a").before("<input type='checkbox' name='chkCtgCd' value='"+$(this).val()+"' data-title=\""+$(a).text()+"\" onclick='fn_block_child(this)'/>&nbsp;");
			        });
		        }

		      }
		    });

	});

	function fn_open_comctg(mode){
		var comCtgrPopup = window.open("../comctgr/comCtgrPopup?mode="+mode , "comCtgrPopup" , "width=500,height=600,scrollbars=yes");
		comCtgrPopup.focus();
	}

	function fn_search_program(){
		if($('#orgNo').val() != "" && $('#orgNo').val() !="0"){
			$.ajax({
			      url: './edcLimitProgram.json',
			      data : {'searchOrgNo': $('#orgNo').val(),'searchCtgcd':$('#CtgCd').val(),'searchKeyword':$('#searchKeyword2').val(),'searchCondition':'1'},
			      type : "POST",
			      dataType : "json",
			      success: function(data) {
			    	  $('#PRGM_TABLE').find("tbody").html("");
			    	  for(var i=0 ; i<data.result.length ; i++){
							var item = data.result[i];
							var html = "<tr>";

							html += "<td><input type='checkbox' name='chkProgmid' value='"+item.edcPrgmid+"'/></td>";
							html += "<td>"+item.comCtgNm+"</td>";
							html += "<td>"+item.edcPrgmnm+"</td>";
							html += "<td>"+item.edcOpenyn+"</td>";
							html += "</tr>";

							$('#PRGM_TABLE').find("tbody").append(html);
						}

			    	  $('#PRGM_TABLE').find("input[name=chkProgmid]").prop("checked" , false);

						$.each($('#TABLE_PRGM_SELECTED').find("input[name=selectedPrgmid]") , function(){
							$('#PRGM_TABLE').find("input[name=chkProgmid][value="+$(this).val()+"]").prop("checked" , true);
						});

			    	  if(data.result.length < 1){
			    		  var html = "<tr><td colspan='4'>검색된 프로그램이 없습니다.</td></tr>";
			    		  $('#PRGM_TABLE').find("tbody").append(html);
			    	  }
			      }
			});
		}
	}

	function fn_detail_load(orgNo){
		$('#orgListDiv').find("tbody tr").css("background-color" , "#fff").css("color" , "#666");

		$.ajax({
		      url: './edcLimitRegistAjax',
		      data : {'comcd': '<c:out value="${searchVO.comcd}"/>', 'orgNo' :  orgNo},
		      type : "GET",
		      cache : false,
		      dataType : "html",
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
			    	   if(orgNo > 0){
			  				$('#ROW_'+orgNo).css("background-color" , "#adb5bd").css("color" , "white");
			  			}

						$('#registDiv').html(data);
		    	  	}
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
            <h1>교육프로그램 신청횟수 제한설정</h1>
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
                <h3 class="card-title">기관 목록</h3>

                <div class="card-tools">
<form:form commandName="searchVO" name="searchVO"  method="get" >
<form:hidden path="searchCondition" value="orgNm" />
                  <div class="input-group input-group-sm" style="width: 150px;">
                    <form:input path="searchKeyword" class="form-control float-right" placeholder="기관명검색"/>

                    <div class="input-group-append">
                      <button type="submit" class="btn btn-default">
                        <i class="fas fa-search"></i>
                      </button>
                    </div>
                  </div>
  </form:form>
                </div>

              </div>

              <!-- /.card-header -->
              	<div class="card-body table-responsive p-0" style="height: 700px;" id="orgListDiv">
                <table class="table table-hover table-head-fixed text-nowrap">
                  <thead>
                    <tr>
                      <th>번호</th>
                      <th>기관분류</th>
                      <th>기관명</th>
                      <th>기관유형</th>
                      <th>제한설정</th>
                     </tr>
                  </thead>

                  <tbody>
 <c:forEach items="${orgList}" var="resultInfo" varStatus="status">
                    <tr style="cursor:pointer" onclick="fn_detail_load(${resultInfo.orgNo})" id="ROW_${resultInfo.orgNo}">
                      <td>${status.index+1 }</td>
                      <td>
                      				<c:choose>
										<c:when test="${resultInfo.orgKind eq '1001' }">산하기관</c:when>
										<c:when test="${resultInfo.orgKind eq '2001' }">유관기관</c:when>
									</c:choose>
                      </td>
                      <td><c:out value='${resultInfo.orgNm}'/></td>
                      <td>
                      				<c:choose>
										<c:when test="${resultInfo.orgLtype eq '1001' }">평생교육원</c:when>
										<c:when test="${resultInfo.orgLtype eq '2001' }">자치센터</c:when>
										<c:when test="${resultInfo.orgLtype eq '3001' }">남양주시청시민대학</c:when>
										<c:when test="${resultInfo.orgLtype eq '4001' }">마을배움터</c:when>
										<c:when test="${resultInfo.orgLtype eq '5001' }">동네배움터</c:when>
										<c:when test="${resultInfo.orgLtype eq '9001' }">기타유관기관</c:when>
									</c:choose>
                      </td>
                      <td>${resultInfo.edcRsvnlimitYn}</td>
                    </tr>
</c:forEach>

                  </tbody>
                </table>
              </div>
              <!-- /.card-body -->
				<div class="card-footer">
					검색결과(${fn:length(orgList) }) 건
				</div>
            <!-- /.card -->
          </div>

        </div>
        	<div class="col-md-6" id="registDiv">

        </div>
      </div>

     </div>

 <!-- 모달 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">제한 범위 선택</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 " style="height:500px">
              	<table class="table table-sm border-0" id="CTG_TABLE">
                  <tbody>

                  </tbody>
                </table>
                <table class="table table-sm border-0 table-head-fixed" id="PRGM_TABLE">
                  <thead>
                  	<tr class="border-0">
                  		<th colspan="2">
	                  		<input type="hidden" name="CtgCd" id="CtgCd" />
	           				<div class="input-group input-group-sm" style="width:100%">
							<input type="text" name="comCtgnm" placeHolder="프로그램분야 검색" class="form-control" readonly style="width:80%"/>
								<div class="input-group-append "style="width:20%">
									<button class="btn btn btn-default" type="button" id="CtgCdBtn" >
		              					<i class="fas fa-search fa-fw"></i> 선택
		            				</button>
		            			</div>
							</div>
						</th>
						<th colspan="2">
							<div class="input-group input-group-sm" style="width: 250px;">
			                    <input id="searchKeyword2" name="searchKeyword" placeholder="프로그램명검색" class="form-control float-right" type="text" value=""><div class="input-group-append">
			                      <button type="button" class="btn-secondary" onclick="fn_search_program()">
			                        <i class="fas fa-search"></i> 검색
			                      </button>
			                    </div>
			                  </div>
						</th>
                  	</tr>
                  	<tr>
                  		<th><input type="checkbox" name="allProgramChk" id="allProgramChk" /></th>
                  		<th>분야</th>
                  		<th>프로그램명</th>
						<th>공개</th>
                  	</tr>
                  </thead>

                  <tbody>
                  	<tr>

                  	</tr>

                  </tbody>
                </table>


            </div>
            <div id="MODAL_BTN" style="text-align:center;padding-bottom:10px"></div>

          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>

</section>
</body>