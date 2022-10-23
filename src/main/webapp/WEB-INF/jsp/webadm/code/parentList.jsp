<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 /**
  * @Class Name :  parentList.jsp
  * @Description : 공통코드그룹목록 JSP
  * @Modification Information
  * @
  * @  수정일           수정자              수정내용
  * @ -------    --------    ---------------------------
  * @ 2021.10.27  이윤호            최초 생성
  *
  *  @author 이윤호
  *  @since 2021.10.27
  *  @version 1.0
  *  @see
  *
  */
%>
<head>
<style>
.grpcdclass{
	color:'#ff5883'
}

</style>
<script type="text/javascript" src="../validator"></script>
<script>
	function fn_data_load(codeId) {
		$('#modal-default').find(".modal-body").html("");
		$('#modal-default').find(".modal-title").html("공통코드그룹 수정");
		$.ajax({
		      url: './parentDetailAjax',
		      data : {'grpCd':codeId,'MODE':'UPDATE'},
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
	function fn_code_add() {
		$('#modal-default').find(".modal-body").html("");
		$('#modal-default').find(".modal-title").html("공통코드그룹 등록");
		$.ajax({
		      url: './parentDetailAjax',
		      data : { 'MODE':'INSERT'},
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
	function fn_save() {
		var form = document.codeVO;

		if (!validateCodeVO(form)) {
			return ;
		} else {
			var msg = "등록하시겠습니까?";
			if (form.MODE.value == "UPDATE") {
				msg = "수정하시겠습니까?";
			}
			if (confirm(msg)) {
				$.ajax({
				      url: './parentSave.json',
				      data : $('#codeVO').serialize(),
				      type : "POST",
				      success: function(data) {
				    	   if (data.result.code == "ERROR") {
					        	 alert(data.result.msg);
					        } else {
					        	 alert(data.result.msg);
						         top.location.reload();
					         }

				      }
				 });

			}
		}
	}

	// 공통코드 액셀 다운로드
	function fn_code_excel(){
		$.ajax({
		      url: './parentListAjax',
		      data : $('#CcmCodeForm').serialize(),
		      type : "GET",
		      dataType : "html",
		      success: function(data) {
		    	  if(data.indexOf("login_area") > 0 ){
		    		  __ajaxHtmlCallback__();
		    	  	}else{
		         		$('#modal-default').find(".modal-body").html(data);
		         		tableExportExcel("excelTable", "그룹코드목록");
		    	  	}
		      }
		    });


	}


	function fn_delete(grpCd) {
		var $form = $('<form/>');
		$('<input type="hidden" name="grpCd">').val(grpCd).appendTo($form);

		var msg = "삭제된 데이터는 복구되지 않습니다.\n정말 삭제하시겠습니까?";
		if (confirm(msg)) {
			$.ajax({
			      url: './parentDelete.json',
			      data : $form.serialize(),
			      type : "POST",
			      success: function(data) {
			    	   if (data.result.code == "ERROR") {
				        	 alert(data.result.msg);
				        } else {
				        	 alert(data.result.msg);
					         top.location.reload();
				         }
			      }
			});

		}
	}


	function fnSort(grpCd) {

		$('form[name=CcmCodeForm]').find('input[name=searchOrder]').val(grpCd);
		if('<c:out value="${searchVO.searchOrderDir}"/>'=='ASC'){
			$('form[name=CcmCodeForm]').find('input[name=searchOrderDir]').val("DESC");
		} else {
			$('form[name=CcmCodeForm]').find('input[name=searchOrderDir]').val("ASC");
		}
		$('form[name=CcmCodeForm]').submit();
	}

</script>

</head>

<body>
<section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>공통코드 그룹 관리</h1>
          </div>
          <div class="col-sm-6 text-red">
          </div>
        </div>
      </div><!-- /.container-fluid -->
</section>


<!-- Main content -->
<section class="content">
      <div class="container-fluid">
       	<div class="card">
<form name="CcmCodeForm" id="CcmCodeForm" action="./parentList" method="post" onSubmit="return false;">
	<input type="hidden" name="searchOrder" value="<c:out value="${searchVO.searchOrder}"/>"/>
	<input type="hidden" name="searchOrderDir" value="<c:out value="${searchVO.searchOrderDir}"/>"/>
	<input type="hidden" name="searchOrderDir" value="<c:out value="${searchVO.searchOrderDir}"/>"/>
	<input id="pageIndex" name="pageIndex" type="hidden" value="${searchVO.pageIndex}"/>
		 	 	 <div class="card-body">
		 	 	 	<div class="row">
		              	<div class="col-3">
								<select name="searchCondition" title="검색영역" class="form-control">
									<option value="2"  <c:if test="${searchVO.searchCondition == '2'}">selected="selected"</c:if> >그룹명</option><!-- 코드ID명 -->
									<option value="1"  <c:if test="${searchVO.searchCondition == '1'}">selected="selected"</c:if> >그룹코드</option><!-- 코드ID -->
								</select>
						</div>
						<div class="col-3">
							<input class="form-control" id="searchKeyword" name="searchKeyword" type="text"  size="35" title="검색어" value='<c:out value="${searchVO.searchKeyword}"/>'  maxlength="155" onkeyup="enterkey();">
						</div>
		              	<div class="col-2">
								<select name="orgGrpcdyn" title="코드그룹구분" class="form-control">
									<option value=""  <c:if test="${searchVO.orgGrpcdyn == ''}">selected="selected"</c:if> >전체</option><!-- 코드ID -->
									<option value="N"  <c:if test="${searchVO.orgGrpcdyn == 'N'}">selected="selected"</c:if> >공통코드</option><!-- 코드ID명 -->
									<option value="Y"  <c:if test="${searchVO.orgGrpcdyn == 'Y'}">selected="selected"</c:if> >기관별코드</option><!-- 코드ID명 -->
								</select>
						</div>
						<div class="col-3">
							<button type="button" onclick="fn_goSearch()" class="btn btn-secondary">조회</button>
							<button type="button" onclick="fn_resetForm()" class="btn btn-secondary">검색초기화</button>
		              	</div>
 					</div>
 				</div>
 </form>
 			</div>

			<div class="row" style="margin-bottom:10px;">

				<div class="col-12 text-right">
					<c:choose>
						<c:when test="${commandMap.selectedMenu.insYn eq 'Y' }">
							<button type="button" class="btn btn-info" onclick="fn_code_add('')" data-toggle="modal" data-target="#modal-default">그룹코드등록</button>
							<button type="button" class="btn btn-info" onclick="fn_code_excel()">엑셀다운로드</button>
						</c:when>
						<c:otherwise>
							<button type="button" class="btn btn-info disabled">그룹코드등록</button>
							<button type="button" class="btn btn-info disabled">엑셀다운로드</button>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

       <div class="row">
          <div class="col-12">
			<div id="grid"></div>
          
	        <div id="pagination" class="tui-pagination"></div>

          </div>
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
              <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
              <button type="button" class="btn btn-primary" onclick="fn_save()">저장</button>
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
class CustomTextEditor {
    constructor(props) {
      const el = document.createElement('input');
      const { maxLength } = props.columnInfo.editor.options;

      if(props.value == null){
    	  props.value = '';
      }
      el.type = 'text';
      el.maxLength = maxLength;
      el.value = String(props.value);

      this.el = el;
    }

    getElement() {
      return this.el;
    }

    getValue() {
      return this.el.value;
    }

    mounted() {
      this.el.select();
    }
}
class CustomNumberEditor {
    constructor(props) {
      const el = document.createElement('input');
      const { maxLength } = props.columnInfo.editor.options;

      if(props.value == null){
    	  props.value = '';
      }
      el.type = 'number';
      el.pattern = '"[0-9]+';
      el.maxLength = maxLength;
      el.value = String(props.value);

      this.el = el;
    }

    getElement() {
      return this.el;
    }

    getValue() {
      return this.el.value;
    }

    mounted() {
      this.el.select();
    }
}

class CustomSelectBoxEditor{
    constructor(props) {
        const el = document.createElement('select');
    	var ArrayText  = ['전체', '회원', '비회원', '권한없음'];
    	var ArrayValue = ['ALL', 'MEMBER', 'NOMEMBER', 'NOPERMISSION'];
    	
    	for(var i=0; i<ArrayText.length; i++){
    	    var opt = document.createElement("option");
    	    opt.value = ArrayValue[i];
    	    opt.text  = ArrayText[i];
    	    el.add(opt, null);
    	}
        //el.value = String(props.value);

        this.el = el;
    }
    
    getElement() {
      return this.el;
    }

    getValue() {
      return this.el.value;
    }

    mounted() {
      this.el.select();
    }    
}

const grid = new tui.Grid({
    el: document.getElementById('grid'),
    rowHeaders: ['checkbox'],
    /*
    	{
    		type:'checkbox',
    		header:'선택',
    	}
    ],
    */
    bodyHeight: 'auto',
    scrollX: true,
    scrollY: true,  
    editingEvent:'click',
    columns: [
       {
           header: '번호',
           name: 'menu_recno',
           width: 100,
           hidden:false,
           align:'center',
       },
      {
        header: '그룹코드',
        name: 'grp_cd',
        width: 400,
        //sortable:true,
		editor: {
            type: CustomTextEditor,
            options: {
              maxLength: 50
            }
         },
         //filter: { type: 'text', showApplyBtn: true, showClearBtn: true }
      },
       {
           header: '그룹명',
           name: 'grp_nm',
           /*
           formatter:function(rowData){
        	   debugger;
        	   
           },
           */
           /*
           renderer: {
        	      styles: {
        	        fontWeight: 'bold',
        	        color: (props) => props.value.length > 3 ? '#ccc' : '#222'
        	      },
        	      attributes: {
        	        'data-type': 'default',
        	        title: (props) => '그룹명111111111'
        	      },
        	      //classNames: ['grpcdclass'],
        	    },
           */        	    
           width: 480,
           editor: {
               type: CustomTextEditor,
               options: {
                 maxLength: 4096
               }
            },
      },  
      {
          header: '기관코드관리',
          name: 'org_grp_cd_yn',
          align:'center',
          width:140,
          hidden:false,
          formatter: 'listItemText',
          editor: {
            type: 'select',
            options: {
                listItems: [
                    { text: '기관별관리', value: 'Y' },
                    { text: '공통코드', value: 'N' },
                  ]
              }
          }	
       },      
       {
	        header: '수정여부',
	        name: 'upd_yn',
	        align:'center',
	        width:100,
   		formatter: 'listItemText',
           editor: {
             type: 'select',
             options: {
                 listItems: [
                     { text: '가능', value: 'Y' },
                     { text: '불가능', value: 'N' },
                   ]
               }
           } 	        
      },
      {
	        header: '삭제여부',
	        name: 'del_yn',
	        align:'center',
	        width:100,
  		formatter: 'listItemText',
          editor: {
            type: 'select',
            options: {
                listItems: [
                    { text: '삭제', value: 'Y' },
                    { text: '미삭제', value: 'N' },
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
        }       
   	   
    ],
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
      console.log('change click cell!', ev);
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
		var searchForm = document.CcmCodeForm;
	    searchForm.pageIndex.value = eventData.page;
	    fn_getList('PAGING');
	});
	
	toastPagination.on('afterMove', function(eventData) {
	    //alert('The current page is ' + eventData.page);
	});
	
	function fn_getList(param){
		var searchForm = document.CcmCodeForm;
		//searchForm.perPage.value = $('#gridListCnt').val(); 
		$.ajax({
		     url: './parentListAjax2',
		     data :$('#CcmCodeForm').serialize(),
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
					// $('#TotalRecordCount').html(data.data.pagination.totalCount);
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
		var searchForm = document.CcmCodeForm;
		searchForm.reset();
		$('#pageIndex').val(1);
	}
	fn_getList();
</script>
<style>
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
/*
.tui-grid-cell {
    border-width: 1px;
    border-style: solid;
    white-space: normal;
    padding: 0;
    overflow: hidden;
}
*/
</style>
</body>