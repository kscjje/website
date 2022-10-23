<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<style>
.sort {font-size:16px; cursor:pointer}
.sort.active {color:red}
</style>
<script>
	var selectedMode = "";

	function fn_reset(){
		$('#searchVO').find("input[type=text],input[type=hidden]").val('');
		$('#searchVO').find("select[name=searchUse],select[name=searchOpen],select[name=searchFree],select[name=searchRectype],select[name=searchTarget]").val('');
		$('.select2').val(null).trigger('change');
	}

	$(document).ready(function(){
		$( ".datepicker" ).datepicker({
			"dateFormat":'yy-mm-dd',
			'monthNames':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			'monthNamesShort':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			'changeMonth' : true,
			'changeYear':true
		});

		var sort_column = $('#searchOrder').val();
		var sort_order = $('#searchOrderDir').val();

		$('.sort').click(function(){
			var id = $(this).attr("id");
			if(id == sort_column){
				if(sort_order == "" || sort_order == "desc"){
					sort_order = "asc";
				}else{
					sort_order = "desc";
				}
			}else{
				sort_order = "asc";
			}
			$('#searchOrder').val(id);
			$('#searchOrderDir').val(sort_order);
			$('#searchVO').submit();

		});

		//if(sort_column == "") sort_column = "createdate";
		//if(sort_order == "") sort_order = "desc";
		$.each($('.sort') , function(){
			if($(this).attr("id") == sort_column){
				$(this).addClass("active");
				if(sort_order == "asc"){
					$(this).text("▲");
				}
			}
		});

		$('#pageSize').change(function(){
			$('#searchVO').submit();
		});
	});
	
	function fn_data_load(orgMovieLecNo) {
		$('#modal-default').find(".modal-body").html("");
		$('#modal-default').find(".modal-title").html("강좌 사용자");
		$.ajax({
		      url: './eduMovieUserAjax',
		      data : {'orgMovieLecNo':orgMovieLecNo},
		      type : "GET",
		      dataType : "html",
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
		        		 $('#modal-default').find(".modal-body").html(data);
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
            <h1>교육프로그램 관리</h1>
          </div>
          <div class="col-sm-6">
          </div>
        </div>
      </div>
</section>

<!-- Main content -->
<section class="content">

      <div class="container-fluid">
<form:form commandName="searchVO" name="searchVO" method="post" onSubmit="return false;">
<%-- <form:hidden path="searchOrder" />
<form:hidden path="searchOrderDir" />
<form:hidden path="searchCtgcd" />
<form:hidden path="pageIndex" /> --%>
<input type="hidden" name="copyMovieNo" id="copyMovieNo" />
       	<div class="card card-primary card-outline">
		 	 	 <div class="card-body">
		 	 	 	<div class="row">
		              	<div class="col-5">
	                    	<hisco:OrgList defaultNo="${searchVO.searchOrgNo}" id="searchOrgNo" />
	                    </div>
	                    <div class="col-1"></div>
		              	<div class="col-2">
	              			<%-- <form:select path="searchDate" title="날짜"  class="form-control">
								<form:option value="edcdate"  >교육시작일</form:option>
								<form:option value="rsvdate" >접수시작일</form:option>
								<form:option value="optdate" >개설일자</form:option>
							</form:select> --%>
						</div>
						<%-- <div class="col-4">
								<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest" >
			                        <form:input cssClass="form-control datepicker" path="searchStartDts" autocomplete="off"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>

								<div style="width:10%;float:left;text-align:center">~</div>
								<div class="input-group date  " style="float:left;width:45%"  data-target-input="nearest">
			                        <form:input cssClass="form-control datepicker" path="searchEndDts" autocomplete="off"/>
			                        <div class="input-group-append"><div class="input-group-text"><i class="fa fa-calendar"></i></div></div>
			                    </div>
						</div> --%>

		           </div>

 					<div class="row" style="margin-top:10px">
 						<div class="col-2">
 							<%-- <form:select path="searchUse" title="검색"  cssClass="form-control">
								<form:option value="">사용여부(전체)</form:option>
								<form:option value="Y">사용</form:option>
								<form:option value="N">삭제</form:option>
							</form:select> --%>
 						</div>
 						<div class="col-2">
 							<%-- <form:select path="searchOpen" title="검색"  cssClass="form-control">
								<form:option value="">공개여부(전체)</form:option>
								<form:option value="Y">공개</form:option>
								<form:option value="N">비공개</form:option>
							</form:select> --%>
 						</div>
 						<div class="col-2">
 							<%-- <form:select path="searchFree" title="검색"  cssClass="form-control">
								<form:option value="">유/무료(전체)</form:option>
								<form:option value="1001">유료</form:option>
								<form:option value="2001">무료</form:option>
							</form:select> --%>
 						</div>
 						<div class="col-2">
 							<%-- <form:select path="searchRectype" class="form-control">
 								<form:option value="">접수방식(전체)</form:option>
	   							<form:options items="${receptType}" class="form-control" itemLabel="cdNm" itemValue="cd" />
	                    	 </form:select> --%>
	                     </div>
 					</div>

 					 <div class="row" style="margin-top:10px">
		              	<div class="col-2">
	              			<form:select path="searchCondition" title="검색"  cssClass="form-control">
								<form:option value="1">프로그램명</form:option>
							</form:select>
						</div>
						<div class="col-4">
							<form:input cssClass="form-control" path="searchKeyword" placeHolder="검색어를 입력하세요"  maxlength="155" />
						</div>

						<div class="col-4">

 						</div>

						<div class="col-2">

		              	</div>
 					</div>
 				</div>

 	   </div>
 	    <div class="row" style="margin-bottom:15px;text-align:right">
 	    	<div class="col-4"></div>
 	    	<div class="col-2">
 	    		<button type="button" onclick="fn_goSearch()" class="btn btn-primary btn-block">조회</button>
 	    	</div>
 	    	<div class="col-2">
 	    		<button type="button" onclick="fn_reset()" class="btn btn-secondary btn-block">초기화</button>
 	    	</div>
 	    	<div class="col-4"></div>
 	    </div>
		<div class="row" style="text-align:right">
			 <div class="col-12" style="color:red">
			* 사용여부 N은 일괄설정에 포함되지 않습니다.
			</div>
		</div>

 	   <div class="row mb-2">
			 <div class="col-2">
				<div class="input-group input-group-sm"">
					<%-- <form:select path="pageSize" title="글수"  cssClass="form-control">
						<form:option value="5">5개씩 보기</form:option>
						<form:option value="10">10개씩 보기</form:option>
						<form:option value="20">20개씩 보기</form:option>
						<form:option value="50">50개씩 보기</form:option>
						<form:option value="100">100개씩 보기</form:option>
					</form:select> --%>
				</div>
			 </div>
			 <div class="col-2 pt-1">
			 	조회결과 <b id="totalRecordCount"></b> 건
			 </div>
			 <div class="col-8" style="text-align:right">
				<!-- <button type="button" onclick="fn_excelDown()" class="btn  btn-secondary btn-sm btn-flat">엑셀다운</button> -->
				<a href="./eduMovieRegist" class="btn  btn-primary btn-sm btn-flat<c:if test="${commandMap.selectedMenu.insYn ne 'Y' }"> disabled</c:if>">동영상 등록</a>
			</div>
       </div>
</form:form>
       <div class="row">

          <div class="col-12">

			<div id="grid"></div>
	        <div id="pagination" class="tui-pagination"></div>
          
        </div>
      </div>
     </div>


     <!-- 모달 박스 -->
     <div class="modal fade" id="modal-default" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Default Modal</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body">
              <p>One fine body…</p>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary" onclick="fn_save()">Save</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
</section>

<script>


const Grid = tui.Grid;
Grid.setLanguage('ko');	
const grid = new tui.Grid({
    el: document.getElementById('grid'),
    rowHeaders: ['checkbox'],
    bodyHeight: 'auto',
    scrollX: true,
    scrollY: true,  
    editingEvent:'click',
    header: {
      	 height: 80,
      	complexColumns: [
      		{
                  header: '정원',
                  name: 'mergeColumn1',
                  childNames: ['edc_pncpa','edc_oncapa', 'edc_offcapa']
                },    		
   		]
    },    
    columns: [
    	/* {
    		header: '복사',
    		name: 'btnCopy',
    		hidden: false,
    		width:80,
    		align:'center',
    		formatter:function(rowData){
    			//row, column, value
    			// this.index, this.item, this.list 에 모두 접근이 가능합니다.
    			// 그외에 요소가 궁금하시면 trace(this) 해보시면 되겠습니다.
    			return '<buton class="btn-success btn-sm btn-flat" style="padding:5px;font-size:12px;height:18px;width:50px;min-width:10px;max-width:60px;cursor:pointer;font-weight:300;" onclick="goEduCopy(\''+ rowData.row.rowKey +'\');">복사</buton>';
    		}        
    	},   */  	
    	{
    		header: '번호',
    		name: 'rnum',
    		width: 80,
    		hidden:false,
    		align:'center',
    	},
    	{
    		header: '기관',
    		name: 'orgnm',
    		width: 160,
    	},
    	{
    		header: '제목',
    		name: 'title',
    		width: 200,
            formatter:function(rowData){
            	return '<span style="color:blue;cursor:pointer">'+rowData.value+'</span>';
            }
    	},  
    	{
    		header: '접수기간',
    		name: 'org_grp_cd_yn',
    		align:'center',
    		width:200,
    		hidden:false,
            formatter:function(rowData){
            	var edc_rsvn_sdate = rowData.row.edc_rsvn_sdate;
            	var edc_rsvn_edate = rowData.row.edc_rsvn_edate

            	if(edc_rsvn_sdate != undefined 
            			&& edc_rsvn_sdate.length == 8
            			&& edc_rsvn_edate != undefined 
            			&& edc_rsvn_edate.length == 8){
            		var displayValue = edc_rsvn_sdate.substring(0,4) + '.' + edc_rsvn_sdate.substring(4,6) + '.' + edc_rsvn_sdate.substring(6,8);
            		displayValue = displayValue + ' ~ ' + edc_rsvn_edate.substring(0,4) + '.' + edc_rsvn_edate.substring(4,6) + '.' + edc_rsvn_edate.substring(6,8);
            		return displayValue;
            	}
            	else{
            		return rowData.value;	
            	}
            },
    	},       
    	{
    		header: '총정원',
    		name: 'edc_pncpa',
    		align:'center',
    		width:80,
    		hidden:false,
    	},      
    	{
    		header: '인터넷',
    		name: 'edc_oncapa',
    		align:'center',
    		width:80,
    		hidden:false,
    	},      
    	{
    		header: '방문',
    		name: 'edc_offcapa',
    		align:'center',
    		width:80,
    		hidden:false,
    	},      
    	{
    		header: '교육기간',
    		name: 'edc_sdate',
    		align:'center',
    		width:180, 
    		formatter:function(rowData){
            	var edc_sdate = rowData.row.edc_sdate;
            	var edc_edate = rowData.row.edc_edate

            	if(edc_sdate != undefined 
            			&& edc_sdate.length == 8
            			&& edc_edate != undefined 
            			&& edc_edate.length == 8){
            		var displayValue = edc_sdate.substring(0,4) + '.' + edc_sdate.substring(4,6) + '.' + edc_sdate.substring(6,8);
            		displayValue = displayValue + ' ~ ' + edc_edate.substring(0,4) + '.' + edc_edate.substring(4,6) + '.' + edc_edate.substring(6,8);
            		return displayValue;
            	}
            	else{
            		return rowData.value;	
            	}
            },    		
    	}, 
    	{
    		header: '사용여부 ',
    		name: 'useyn',
    		align:'center',
    		width:80,
    		hidden:false,
            formatter: 'listItemText',
            
            <c:choose>
				<c:when test="${commandMap.selectedMenu.updYn ne 'Y' }">
				
					disabled:true,
				
				</c:when>
				<c:otherwise>
				
					disabled:false,
					
				</c:otherwise>
			</c:choose>            
            
            editor: {
              type: 'select',
              options: {
                  listItems: [
                      { text: '사용', value: 'Y' },
                      { text: '미사용', value: 'N' },
                    ]
                }
            }	    		
    	},
    	{
    		header: '작성자',
    		name: 'reguser',
    		align:'center',
    		
    		width:100,
    	},       
    	{
    		header: '작성일시',
    		name: 'regdate',
    		align:'center',
    		width:160,
    		hidden:false,
    	},       
    	{
    		header: '강좌이용자',
    		name: 'orgmovielecno',
    		align:'center',
    		width:100,
    		hidden:false,
            formatter:function(rowData){
            	return '<button style="color:blue;cursor:pointer" onclick="fn_data_load('+rowData.value+')" data-target="#modal-default">상세보기</span>';
            }
    	}       
    ],
    onGridMounted(ev) {
        console.log('mounted' + ev);
     },
     onGridBeforeDestroy(ev) {
       console.log('before destroy' + ev);
     },
    useClientSort: true,
    columnOptions: {
        resizable: true,/*테이블 간격 리사이징*/
    },
    draggable: false,/*트리그리드 드레그처리*/
    contextMenu:[]  
  });	
grid.on('check', function(ev) {
    console.log('check!', ev);
  });

  grid.on('uncheck', function(ev) {
    console.log('uncheck!', ev);
  });

  grid.on('focusChange', function(ev) {
    console.log('change focused cell!', ev);
  });

  grid.on('click', function(ev) {
      console.log('click cell!', ev);
      if(ev.columnName != undefined && ev.columnName == 'title'){
    	  fn_edcProgramDetail(ev.rowKey);
      }
   });
  
  grid.on('dragStart', function(ev) {
      console.log('change dragStart cell!', ev);
    });

  grid.on('drag', function(ev) {
      console.log('change drag cell!', ev);
    });

  grid.on('drop', function(ev) {
      console.log('change drop cell!', ev);
    });
  grid.on('afterChange', function(ev) {
      console.log('change afterChange cell!', ev);
    });
  grid.on('beforeRequest', function(data) {
	  console.log('change beforeRequest cell!', data);
  	//debugger;
  });
	var toastPagination = new tui.Pagination('pagination', {
	    totalItems: 0,
	    itemsPerPage: 10,
	    visiblePages: 10,
	centerAlign: true
	});	

	/*페이징 이벤트*/
	toastPagination.on('beforeMove', function(eventData) {
	    //return confirm('Go to page ' + eventData.page + '?');
		var searchForm = document.searchVO;
	    searchForm.pageIndex.value = eventData.page;
	    fn_getList('PAGING');
	});
	
	toastPagination.on('afterMove', function(eventData) {
	    //alert('The current page is ' + eventData.page);
	});
	
	
	function fn_edcProgramDetail(rowKey){
		//alert(rowKey);
		//var data = detailgrid.getData();
		var row = grid.getRowAt(rowKey)
		if(row != undefined && row != null){
			window.location.href = "./eduMovieUpdt?orgMovieLecNo="+row.orgmovielecno;
		}
	}
	
	function fn_getList(param){
		var searchForm = document.searchVO;
		//searchForm.perPage.value = $('#gridListCnt').val(); 
		$.ajax({
		     url: './eduMovieListAjax',
		     data :$('#searchVO').serialize(),
		     type : "POST",
		     dataType : "json",
			cache:false,
		     beforeSend : function(xhr){
		     },
		     success: function(data) {
		    	 if(data != null && data != ''){
		    		 const obj = JSON.parse(data);
		    		 grid.resetData(obj.data.contents);
			    	//레코드값세팅
					$('#totalRecordCount').html(obj.data.pagination.TotalRecordCount);
			    	if(param == undefined || param.toUpperCase() != 'PAGING'){
				 		toastPagination.setTotalItems(obj.data.pagination.TotalRecordCount);
				 		toastPagination.reset(obj.data.pagination.TotalRecordCount);	
			    	}		 
		    	 }
		    	 else{
		    		 alert('데이터가 없습니다.');
		    		 return;
		    	 }
		     },
		     error:function(data){
		   	  alert(data.responseText);
		     }
		  });		
	}
	function fn_goSearch(){
		//페이지번호 초기화
		$('#pageIndex').val(1);
		fn_getList();
	}
	function enterkey(){
	   if (window.event.keyCode == 13) {
			if($('#searchKeyword').val() == ""){
				alert("검색어를 입력해주세요.");
				$('#searchKeyword').focus();
				return;
			}				
		   fn_goSearch();
       }
	}
	function fn_resetForm(){
		var searchForm = document.searchVO;
		searchForm.reset();
		$('#pageIndex').val(1);
	}
	fn_getList();
	
	
	function goEduCopy(rowKey){
		var row = grid.getRowAt(rowKey)
		//window.location.href="./edcProgramRegist?copyPrgmNo="+row.edc_prgm_no;
		var form = document.searchVO;
		form.copyMovieNo.value = row.org_movie_lec_no;
		form.action = "./edcMovieRegist";
		form.method = "get";
		form.submit();
	}
</script>

<style>
/*
.tui-grid-cell-header {
    background-color: #f9f3e1;
    border-color: #eee;
    border-left-width: 1px;
    border-right-width: 1px;
    border-top-width: 1px;
    border-bottom-width: 1px;
    color: #222;
    font-weight: bold;
}
*/
.tui-grid-cell {
    border-width: 1px;
    border-style: solid;
    white-space: normal;
    padding: 0;
    overflow: hidden;
}
</style>
</body>

