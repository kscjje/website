<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<style type="text/css">
		th {background-color:#f4f6f9;}
	</style>
	<script>
		$(document).ready(function(){
			$("#requestBtn").click(function() {
				var data = $("#saleJson").val();
				//data.command = "register";
				
				$.post({
				    url : './edcSale.json',
				    data : data,
				    contentType : "application/json; charset=UTF-8",
					success: function(data) {
				    	console.log(JSON.stringify(data.result));
				    	$("#resultmsg").text(new Date() + " => " + data.result.msg);
					}				    
			    });	
			});
		});
	</script>
</head>
<body>

	<section class="content-header">
	      <div class="container-fluid">
	        <div class="row mb-2">
	          <div class="col-sm-6">
	            <h1>수강신청 - 등록/환불/취소 테스트</h1>
	          </div>
	          <div class="col-sm-6">
	          </div>
	        </div>
	      </div>
	</section>

<!-- Main content -->
<section class="content">

      <div class="container-fluid">
		
		<div class="card card-primary card-outline">
 	   	</div>
	
        <div class="row">

          <div class="col-10">
              <div class="card-body table-responsive p-0">
				<textarea rows="38" cols="120" class="form-control" style="font-size:9px;" id="saleJson"></textarea>
				<h6 id="resultmsg"></h6>               
              </div>
          </div>             
          <div class="col-2">
			<button type="button" class="btn  btn-secondary btn-sm btn-flat" id="requestBtn" style="width:100px;height:600px">요청</button>
		  </div>
        </div>
      
     </div>
      
</section>
</body>