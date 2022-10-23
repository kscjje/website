<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var mode = "<c:out value="${param.mode}"/>";
	$(document).ready(function(){
		$.ajax({
		      url: './comCtgrListAjax.json',
		      type : "GET",
		      dataType : "json",
		      cache : false,
		      success: function(data) {
		        console.log(data);

		        var LIST = data.result;
		        $('#CTG_TABLE').find("tbody").html(categoryTreeHtml(LIST));

		      }
		    });
	});

	function fn_block_child(obj){
		var val = $(obj).val();
		var checked = $(obj).prop("checked");

		$('#CTG_TABLE').find('tr.'+val).find("input[type=checkbox]").prop("checked" , false).prop("disabled" , checked);
	}

	function fn_select_category(comcd, ctgCd , obj){
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
		if(mode == "target"){
			opener.$('input[name=targetCtgnm]').val(nowCtgnm);
			opener.$('input[name=targetCtgcd]').val(ctgCd);
		}else{
			opener.$('input[name=CtgCd]').val(nowCtgnm);
			opener.$('input[name=CtgCd]').val(ctgCd);
		}

		self.close();

	}
</script>

            <div class="modal-header">
              <h4 class="modal-title">분류 선택하기</h4>
              <button type="button" class="close"  aria-label="Close" onclick="self.close()">
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body border-0 ">
              <table class="table table-sm border-0" id="CTG_TABLE">
              	<tbody></tbody>
			  </table>
            </div>
