var fn_find_category = function(){
	$.ajax({
	      url: "../comctgr/comCtgrListAjax.json",
	      type : "GET",
	      dataType : "json",
	      cache : false,
	      success: function(data) {
	        var LIST = data.result;
	        $('#CTG_TABLE').find("tbody").html("");

	        if(LIST){
	        	$('#CTG_TABLE').find("tbody").html(categoryTreeHtml(LIST));
	        }
	      }
	    });
}

//분류 선택 후
var fn_select_category = function(comcd,ctgCd , obj){
	var nowCtgnm = $(obj).text();

	$('#comCtgnm').val(nowCtgnm);
	$('#itemCtgd').val(ctgCd);

	$('#modal-default').find("button.close").click();

}

var fn_reset = function() {
	var $form = jQuery('#searchVO');
	$form.find("input[type=text],input[type=hidden]").val('');
	$form.find("select").val('');
	$form.find("#orgNo").val('0');
	$form.find("#pageSize").val("10");
	jQuery(".date-diff-btn[date-diff='1']").click();
}

var fn_getYmd = function(dt) {
	var yyyy = dt.getFullYear();
	var mm = dt.getMonth() + 1;
	mm = (mm < 10 ? "0" : "") + mm;
	var dd = dt.getDate();
	dd = (dd < 10 ? "0" : "") + dd;

	return yyyy+"-"+mm+"-"+dd;
};

var fn_getDateAgo = function(ago) {
	var today = new Date();
	if(ago) {
		today.setDate(today.getDate() - ago);
	}

	return fn_getYmd(today);
};

jQuery(document).ready(function(){
	jQuery( ".datepicker" ).datepicker({
		"dateFormat":'yy-mm-dd',
		'monthNames':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'monthNamesShort':['1월' , '2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		'changeMonth' : true,
		'changeYear':true
	});

	jQuery(".date-diff-btn").on("click", function(){
		var diff = Number(jQuery(this).attr("date-diff"));
		var startDt = fn_getDateAgo(diff-1);
		var endDt = fn_getDateAgo(0);

		jQuery("#searchStartDts").val(startDt);
		jQuery("#searchEndDts").val(endDt);
	});

	jQuery(".now-month-btn").on("click", function(){
		var today = new Date();
		var endDt = fn_getYmd(today);
		today.setDate(1);
		var startDt = fn_getYmd(today);

		jQuery("#searchStartDts").val(startDt);
		jQuery("#searchEndDts").val(endDt);
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

	jQuery("#orgKind").on("change", function(){
		var orgKind = this.value || "";

		var $seachOrgNo = jQuery("#searchOrgNo");
		$seachOrgNo.val("");
		$seachOrgNo.find("option").hide();
		if(orgKind) {
			$seachOrgNo.find("option[data-orgkind='"+orgKind+"']").show();
			$seachOrgNo.find("option[value='']").show();
		} else {
			$seachOrgNo.find("option").show();
		}
	});

	jQuery(".guide-link").on("click", function(){
		var helpId = jQuery(this).attr("help-id");
		var data = getGuide(helpId);

		jQuery("#help-modal-1 .guide-title").html(data["title"]);
		jQuery("#help-modal-1 .guide-contents").html(data["contents"]);

		jQuery("#help-modal-1").modal("show");
	});

	if($('#orgKind').val() != ""){
		var orgKind = $('#orgKind').val() || "";
		var $seachOrgNo = jQuery("#searchOrgNo");
		$seachOrgNo.find("option").hide();
		$seachOrgNo.find("option[data-orgkind='"+orgKind+"']").show();
		$seachOrgNo.find("option[value='']").show();

	}

});