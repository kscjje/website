<%@page import="com.hisco.admin.menu.util.SiteGubun"%>
<%
 /**
  * @Class Name : menuList.jsp
  * @Description : 메뉴 목록 화면
  * @Modification Information
  * @
  * @  수정일               수정자              수정내용
  * @ -------      --------    ---------------------------
  * @ 2021.10.29    이윤호              최초 생성
  *
  *  @author 이윤호
  *  @since 2021.10.29
  *  @version 1.0
  *  @see
  *
  */
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String strWebContext = request.getContextPath() + com.hisco.cmm.util.Config.ADMIN_ROOT;
%>
<script>
	var CONTEXT_URL = "<%=strWebContext%>";
</script>
<script type="text/javascript" src="../validator"></script>
<script type="text/javascript" src="../resources/plugins/menu/js/admin_menu.js"></script>
<script type="text/javascript" src="../resources/js/jquery.contextMenu.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="../resources/js/contextMenu/jquery.contextMenu.css" />
<link rel="stylesheet" type="text/css" media="all" href="../resources/plugins/menu/css/admin_menu.css" />

<validator:javascript formName="menuManageVO" staticJavascript="false" xhtml="true" cdata="false"/>

<script>

	$(document).ready(function() {

		admin_root = "<%=com.hisco.cmm.util.Config.ADMIN_ROOT%>";
		user_root = "<%=com.hisco.cmm.util.Config.USER_ROOT%>";
		manager_root = "/manager";

		select_siteList = $('#searchVO') ;
		menu_list_box = $('#menu_list');

		$('#searchKeyword').change(function() {
			$('#searchVO').submit();
		});

		// URL 로  메뉴 로딩 처리
		var site_srl =  $('#searchVO').find('[name="searchKeyword"]').val();

		if (site_srl != '')
		{
			load_menu(site_srl);
		}


		// 이벤트 스킵 처리
		menu_list_box.bind('selectstart dragstart', function(e) { return false; })

	});


	function fn_data_load( menuNo) {

		$('#modal-default').find(".modal-body").html("");

		$.ajax({

		      url: './menuDetailAjax',
		      data : {'siteGubun': $('#searchKeyword').val(), 'menuNo' : menuNo ,'MODE':'UPDATE'},
		      type : "GET",
		      dataType : "html",
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
				         $('#modal-default').find(".modal-body").html(data);
				         $('#modal-default').find(".modal-title").html("메뉴 수정");
				         $('#modal-default').modal();
		    	  	}
		      }

		    });
	}

	function fn_menu_add(menu_srl , align , depth) {

		if ($('#searchKeyword').val() == "") {

			alert("사이트를 먼저 선택해 주세요.");
			return ;

		} else {

			$('#modal-default').find(".modal-body").html("");
			$.ajax({
			      url: './menuDetailAjax',
			      data : {'siteGubun': $('#searchKeyword').val(), 'upperMenuNo': menu_srl, 'menuOrdr' :align , 'menuDepth': depth, 'MODE':'INSERT'  },
			      type : "GET",
			      dataType : "html",
			      success: function(data) {
			    	  if(data.indexOf("login_area") > 0 ){
			    		  __ajaxHtmlCallback__();
			    	  	}else{
							$('#modal-default').find(".modal-body").html(data);
			       		 	$('#modal-default').modal();
			    	  	}
			      }
			    });
		}
	}

	function fn_menu_sorting(dataJson) {
		$.ajax({
		      url: './menuSortingSave.json',
		      data : dataJson,
		      type : "POST",
		      dataType : "json",
		      success: function(data) {
		    	   if (data.result.code == "ERROR") {
			        	 alert(data.result.msg);
			        } else {
			        	// alert(data.result.msg);
			        	 var  site_srl = $('#searchVO').find('[name="searchKeyword"]').val();
			        	 load_menu(site_srl);
			        	 //window.location.reload();
			         }
		      }
		 });
	}
	function fn_menu_delete(menuNo) {
		if (confirm("정말 삭제하시겠습니까?\n(하위 메뉴까지 함께 삭제됩니다.)")) {
			$.ajax({
			      url: './menuDelete.json',
			      data : {'siteGubun': $('#searchKeyword').val(),  'menuNo' : menuNo},
			      type : "POST",
			      dataType : "json",
			      success: function(data) {
			         alert("삭제되었습니다.");
			         window.location.reload();
			      }
			    });
		}
	}
	function fn_auth_pop(menuNo) {
		window.open("./menuAuthLinkPop?menuNo=" + menuNo , "p_auth_set" , "width=700,height=800,scrollbars=yes");
	}


</script>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>메뉴 관리</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">

            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>

<!-- Main content -->
<section class="content">
      <div class="container-fluid">

            <div class="card card-primary card-outline">
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0">
              	<div class="fc-toolbar fc-header-toolbar">

  					<form:form commandName="searchVO" method="get">
	 					<div class="row">

	          				<div class="col-6">
			              		<div class="fc-center">
			              			 <form:select path="searchKeyword" class="form-control">
			                           <form:option value="">사이트선택</form:option>
			                           <form:option value="<%=com.hisco.admin.menu.util.SiteGubun.ADMIN%>">관리자</form:option>
			                           <form:option value="<%=com.hisco.admin.menu.util.SiteGubun.USER%>">사용자</form:option>
			                        </form:select>
			              		</div>
			              	</div>
			              	<div class="col-6">


			              	</div>

			          	</div>
					</form:form>

				<div class="row ">
          			<div class="col-12 p-0">
		              	<div class="fc-view-container" style="text-align:left;">
		                	<div id="menu_list" class="menu_list" >
								<c:if test="${empty searchVO.searchKeyword}"><p class="lineheight10 txtcenter fs15p color-red">사이트를 선택해주세요.</p></c:if>
							</div>
						</div>
					</div>
				</div>
 	           </div>

              <!-- /.card-body -->
            </div>
            <!-- /.card -->
          </div>
        </div>

 <!-- 모달 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">메뉴 등록</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body">
              <p></p>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" class="btn btn-primary" onclick="fn_save()">저장</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>

</section>
