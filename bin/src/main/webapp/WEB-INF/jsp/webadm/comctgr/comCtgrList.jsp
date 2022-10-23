<%
 /**
  * @Class Name :  comctgrList.jsp
  * @Description : 프로그램 카테고리 목록 JSP
  * @Modification Information
  * @
  * @  수정일           수정자              수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.03.19  전영석            최초 생성
  *
  *  @author 전영석
  *  @since 2021.03.19
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>프로그램 분야 관리</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div>
</section>

<script>
	$(document).ready(function(){
		fn_category_load();
	});

	function fn_category_load(){
		$.ajax({
		      url: './comCtgrListAjax.json',
		      type : "GET",
		      dataType : "json",
		      cache : false,
		      success: function(data) {
		        console.log(data);

		        $('#CTG_TABLE').find("tbody").html("");

		        var LIST = data.result;
		        var columnHTML = "   <td style='text-align:right'>";
		        columnHTML += "   <i class=\"fa fa-fw fa-times del-btn\" style='font-size:15pt;cursor:pointer' title='삭제'></i>";
		        columnHTML += "   </td>";

		        if(LIST){
		        	for(var i=0; i<LIST.length ; i++){
	        			var html = "<tr class='000 expandable-body'>";
	        			html += "<td colspan='3' class='border-0 p-0'><div class=\"p-0\"><table class=\"table p-0 m-0\"><tbody class='firstLvl'>";
		        		html += "<tr>";
		        		html += "   <td>";
		        		html += "   <input type='hidden' name='listCtgCd' value='"+LIST[i].ctgCd+"'/>\n";
		        		if(LIST[i].subCtgrList.length > 0 && LIST[i].subCtgrList[0].ctgCd != null){
		        			html += "   <i class=\"fas fa-caret-right fa-fw\"></i>";
		        		}
		        		html += "   <a href=\"javascript:;\" onclick=\"fn_detail('"+LIST[i].comcd+"','"+LIST[i].ctgCd+"',this)\">" + LIST[i].ctgNm+"</a></td>";
		        		html += columnHTML;
		        		html += " </tr>";

		        		if(LIST[i].subCtgrList.length > 0 && LIST[i].subCtgrList[0].ctgCd != null){
		        			for(var j=0; j<LIST[i].subCtgrList.length ; j++){
		        				var data = LIST[i].subCtgrList[j];

		        				html += "<tr class='"+LIST[i].ctgCd+" expandable-body'>";
			        			html += "<td colspan='3' class='border-0 p-0'><div class=\"p-0\"><table class=\"table p-0 m-0\" ><tbody >";
	        					html += "<tr >";
				        		html += "   <td style='padding-left:45px'>";
				        		html += "   <input type='hidden' name='listCtgCd' value='"+data.ctgCd+"'/>\n";
				        		if(data.subCtgrList.length > 0 && data.subCtgrList[0].ctgCd != null){
				        			html += "   <i class=\"fas fa-caret-right fa-fw\"></i>";
				        		}else{
				        			html += "  &nbsp;&nbsp;&nbsp;";
				        		}

				        		html += "   <a href=\"javascript:;\" onclick=\"fn_detail('"+data.comcd+"','"+data.ctgCd+"',this)\">" + data.ctgNm+"</a></td>";
				        		html += columnHTML;
				        		html += " </tr>";

				        		if(data.subCtgrList.length > 0 && data.subCtgrList[0].ctgCd != null){
				        			html += "<tr class='thirdLvl'>";
				        			html += "<td colspan='3' class='border-0 p-0'><div class=\"p-0\"><table class=\"table p-0 m-0\"><tbody>";

				        			for(var k=0; k<data.subCtgrList.length ; k++){
				        				var dataSub = data.subCtgrList[k];

				        				html += "<tr class='"+data.ctgCd+"'>";
				        				html += "  <td style='padding-left:80px'><input type='hidden' name='listCtgCd' value='"+dataSub.ctgCd+"'/>\n";
						        		html += "  <a href=\"javascript:;\" onclick=\"fn_detail('"+dataSub.comcd+"','"+dataSub.ctgCd+"',this)\">" + dataSub.ctgNm+"</a></td>";
						        		html += columnHTML;
						        		html += " </tr>";
				        			}
				        			html += "</tbody></table></div></td>";
				        			html += "</tr>";
				        		}
				        		html += "</tbody></table></div></td>";
			        			html += "</tr>";
		        			}
		        		}
		        		html += "</tbody></table></div></td>";
	        			html += "</tr>";

		        		$('#CTG_TABLE').find("tbody").eq(0).append(html);
		        	}
		        }

		      	//순서변경
				$( "#CTG_TABLE" ).sortable({
					items: ".000",
			    	 placeholder: "ui-state-highlight"
			    });

		      	$.each($('#CTG_TABLE .000').find("table tbody.firstLvl") , function(index,item){
		      		$(item).sortable({
						items: ".expandable-body",
				    	 placeholder: "ui-state-highlight"
				    });

		      		$.each($(item).find(".thirdLvl table tbody") , function(index2 , item2){
		      			$(item2).sortable({
		      				 items: "tr",
					    	 placeholder: "ui-state-highlight"
					    });
		      		});
		      	});

		      	$('.del-btn').click(function(){
		    		$('#CTG_TABLE').find("tr").css("background-color" , '#fff');
		    		$(this).parent().parent().css("background-color" , 'rgba(0,0,0,.05)');

		    		var obj = $(this);
					//bg 색상변경을 위해 시간차를 둠
		    		setTimeout(function(){
		    			fn_ctgr_delete(obj);
		    		} , 10);
		      	});
		      }
		    });
	}

	function fn_detail(comcd,ctgCd , obj){
		$('#CTG_TABLE').find("tr").css("background-color" , '#fff');
		$(obj).parent().parent().css("background-color" , 'rgba(0,0,0,.05)');

		$.ajax({
		      url: './comCtgrDetailAjax.json',
		      type : "GET",
		      dataType : "json",
		      data : {'comcd' : comcd , 'ctgCd' : ctgCd},
		      cache : false,
              success: function(data) {
            	  $('#level2').prop("checked" , true);

					$('#comTopctgnm').val( data.result.comTopctgnm);
					$('#comTopctgcd').val( data.result.comTopctgcd);
					$('#comPrnctgcd').val( data.result.comPrnctgcd);
					$('#parentCtgNm').val( data.result.parentCtgNm);
					$('#ctgCd').val( data.result.ctgCd);
					$('#ctgNm').val( data.result.ctgNm);
					$('#ctgDesc').val( data.result.ctgDesc);
					$('#comCtglvl').val( data.result.comCtglvl);


					if(data.result.useYn == "Y"){
						$('#useYn1').prop("checked" , true);
					}else{
						$('#useYn2').prop("checked" , true);
					}
		      }
		});
	}

	function fn_regist(){
		var  f = document.writeForm;
		if(f.ctgNm.value == ""){
			alert("분류명을 입력해 주세요.");
			f.ctgNm.focus();
		}else if($('input[name=useYn]:checked').length < 1){
			alert("사용여부를 체크해 주세요.");
			f.useYn[0].focus();
		}else if($('#level2').prop("checked") && $('#comCtglvl').val() == "1"){
			alert("분류는 2 depth 까지만 등록이 가능합니다.");
		}else{
			$.ajax({
			      url: './comCtgrRegist.json',
			      type : "POST",
			      dataType : "json",
			      data : $('#writeForm').serialize(),
			      beforeSend : function(xhr) {
	                  xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
	              },
	              success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        }else{
				        	fn_category_load();
				        	alert(data.result.msg);
				        }
			      }
			});
		}
	}

	function fn_sort_save(){
		if(confirm("순서변경을 저장하시겠습니까?")){
			$.ajax({
			      url: './comCtgrSortSave.json',
			      type : "POST",
			      dataType : "json",
			      data : $('#listForm').serialize(),
			      beforeSend : function(xhr) {
	                xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
	            },
	            success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        }else{
				        	fn_category_load();
				        	 alert(data.result.msg);
				        }
			      }
			});
		}
	}
	function fn_ctgr_delete(obj){
		var ctgCd = $(obj).parent().parent().find("input[name=listCtgCd]").val();
		var comcd = $('#comcd').val();

		if(confirm("하위 분류까지 함께 삭제되며 복구할 수 없습니다.\n정말 삭제하시겠습니까?")){
			$.ajax({
			      url: './comCtgrDelete.json',
			      type : "POST",
			      dataType : "json",
			      data :{'comcd' : comcd , 'ctgCd' : ctgCd},
			      beforeSend : function(xhr) {
	                xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
	            },
	            success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        }else{
				        	fn_category_load();
				        	 alert(data.result.msg);
				        }
			      }
			});
		}
	}

	function fn_modify(){
		var  f = document.writeForm;
		if(f.ctgNm.value == ""){
			alert("분류명을 입력해 주세요.");
			f.ctgNm.focus();
		}else if($('input[name=useYn]:checked').length < 1){
			alert("사용여부를 체크해 주세요.");
			f.useYn[0].focus();
		}else{
			$.ajax({
			      url: './comCtgrModify.json',
			      type : "POST",
			      dataType : "json",
			      data : $('#writeForm').serialize(),
			      beforeSend : function(xhr) {
	                  xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
	              },
	              success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        }else{
				        	fn_category_load();
				        	 alert(data.result.msg);
				        }
			      }
			});
		}
	}

</script>

<!-- Main content -->
<section class="content">
      <div class="container-fluid">
      		<div class="row">
      			<div class="col-sm-6">
      				<div class="card card-primary card-outline">
			              <div class="card-header">
			                <h3 class="card-title">분류 목록</h3>
			                <div class="card-tools">
			                	<c:choose>
									<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
										 <button type="button" onclick="fn_sort_save()"  class="btn btn-warning btn-sm">순서저장</button>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn-warning btn-sm disabled">순서저장</button>
									</c:otherwise>
								</c:choose>
			                </div>
			              </div>
			              <!-- /.card-header -->
			              <div class="card-body p-0">
<form name="listForm" id="listForm">
<input type="hidden" name="comcd" id="comcd" value="${searchVO.comcd }"/>
			                <table class="table table-sm" id="CTG_TABLE">
			                  <thead>
			                    <tr>
			                      <th>분류명</th>
			                      <th style="text-align:right">삭제</th>
			                    </tr>
			                  </thead>
			                  <tbody>

			                  </tbody>

			                </table>
</form>
			              </div>
			              <!-- /.card-body -->
			              <div class="card-footer">
							(순서변경은 Drag&Drop 으로 위치를 변경하신 후 순서저장을 클릭해 주세요.)
			            </div>
			         </div>
      			</div>
      			<div class="col-sm-6">
      				<div class="card card-primary card-outline">
			              <div class="card-header">
			                <h3 class="card-title">등록 / 수정</h3>
			              </div>
			              <!-- /.card-header -->
			              <!-- form start -->
<form name="writeForm" id="writeForm">
<input type="hidden" name="comcd" id="comcd" value="${searchVO.comcd }"/>
			                <div class="card-body">
			                  <div class="form-group">
					                <div class="form-check" style="display:inline-block">
			                          <input class="form-check-input" type="radio" name="level" value="0" id="level1" checked>
			                          <label class="form-check-label" for="level1">상위 분류 등록</label>
			                        </div>
			                        <div class="form-check"  style="display:inline-block">
			                          <input class="form-check-input" type="radio" name="level" value="1" id="level2">
			                          <label class="form-check-label" for="level2">하위 분류 등록</label>
			                        </div>
			                  </div>
			                  <!--
			                  <div class="form-group">
			                    <label for="comTopctgcd">최상위 분류</label>
			                    	<div class="input-group">
				                      <div class="custom-file">
				                        <input type="text" class="form-control" id="comTopctgnm" name="comTopctgnm" disabled>
				                      </div>
				                      <div class="input-group-append">
				                        <input type="text" class="form-control" id="comTopctgcd" name="comTopctgcd" disabled>
				                      </div>
				                    </div>
			                  </div>
			                   -->
			                  <div class="form-group">
			                    <label for="parentCtgNm">상위분류</label>
			                    	<div class="input-group">
				                      <div class="custom-file">
				                        <input type="text" class="form-control" id="parentCtgNm" name="parentCtgNm" disabled>
				                      </div>
				                      <div class="input-group-append">
				                        <input type="text" class="form-control" id="comPrnctgcd" name="comPrnctgcd" disabled>
				                      </div>
				                    </div>
			                  </div>

			                  <div class="form-group">
			                    <label for="ctgCd">분류코드</label>
				                 <input type="text" class="form-control" id="ctgCd" name="ctgCd" readonly placeHolder="자동 부여">
			                  </div>
			                  <div class="form-group">
			                    <label for="ctgNm">분류명</label>
				                 <input type="text" class="form-control" id="ctgNm" name="ctgNm">
			                  </div>
			                  <div class="form-group">
			                    <label for="ctgDesc">분류설명</label>
				                <textarea class="form-control" rows="3" placeholder="" id="ctgDesc" name="ctgDesc"></textarea>
			                  </div>
			                  <div class="form-group">
			                    <label for="useYn1">사용여부</label>
					                <div class="form-check" style="display:inline-block">
			                          <input class="form-check-input" type="radio" name="useYn" value="Y" id="useYn1" checked>
			                          <label class="form-check-label" for="useYn1">사용함</label>
			                        </div>
			                        <div class="form-check" style="display:inline-block">
			                          <input class="form-check-input" type="radio" name="useYn" value="N" id="useYn2">
			                          <label class="form-check-label" for="useYn2">사용안함</label>
			                        </div>
			                  </div>
			                </div>
			                <!-- /.card-body -->

			                <div class="card-footer">
			                	<c:choose>
									<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
										<button type="button" onclick="fn_regist()" class="btn btn-info">등록</button>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn-info disabled">등록</button>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${commandMap.selectedMenu.updYn eq 'Y' }">
										  <button type="button" onclick="fn_modify()" class="btn btn-secondary">수정</button>
									</c:when>
									<c:otherwise>
										  <button type="button" class="btn btn-secondary disabled">수정</button>
									</c:otherwise>
								</c:choose>
			                </div>
 </form>
			            </div>

      			</div>
      		</div>
      </div>
</section>
