var JSON_REQ_SUFFIX = ".json";
var fn_dom = function(tagName, attrs, styles, childs) {
	var _tagName, _attrs, _styles, _childs;

	if (typeof (tagName) == "object") {
		if (tagName instanceof HTMLElement) {
			return tagName;
		} else {
			_tagName = tagName.tagName;
			_attrs = tagName.attrs || {};
			_styles = tagName.styles || {};
			_childs = tagName.childs;
		}
	} else {
		_tagName = tagName;
		_attrs = attrs || {};
		_styles = styles || {};
		_childs = childs;
	}

	var dom = document.createElement(_tagName);

	if (_attrs) {
		for ( var key1 in _attrs) {
			var val = _attrs[key1];
			if (val === undefined)
				v = "";
			dom[key1] = val;
		}
	}

	if (_styles) {
		for ( var key2 in _styles) {
			var val = _styles[key2];
			if (val === undefined)
				v = "";
			dom.style[key2] = val;
		}
	}

	if (_childs && _childs.length) {
		for (var i = 0; i < _childs.length; i++) {
			var cDom = fn_dom(_childs[i]);

			dom.appendChild(cDom);
		}
	}
	return dom;
};

var fn_renderList = function(selector, data, looper, fallback) {
	var $list = jQuery(selector);
	var listId = $list.attr("data-list-id");
	var list =  listId ? data[listId] : data;

	var resultArr = [];
	if(list && list.length) {
		jQuery.each(list, function(idx, rowData){
			var result = looper($list, idx, rowData)
			resultArr.push(result);
		});
	} else {
		var fallbackResult = null;
		var fallbackMsg = "데이터가 없습니다.";
		if(fallback instanceof Function) {
			fallbackResult = fallback(fallbackMsg);
		} else {
			fallbackResult = fallbackMsg;
		}
		resultArr.push(fallbackResult);
	}
	
	return resultArr;
};

var fn_renderPaging = function(selector, first_rnum, tot_cnt, max_per_page) {
	var $paging = jQuery(selector);
	
	var MAX_ROW_PER_PAGE = 10;
	if(!max_per_page) max_per_page = MAX_ROW_PER_PAGE; 
	
	var tot_page = Math.ceil(tot_cnt/max_per_page);
	var cur_page =  Math.ceil(first_rnum/max_per_page);
	
	var prev_page = cur_page -1 < 1 ? 1 : cur_page -1;
	var next_page = cur_page +1 > tot_page ? tot_page : cur_page +1;
	
	$paging.empty();
	var lis = [];
	lis.push(fn_dom( { tagName:"a", attrs:{ href:"javascript:void(0)", className: "prev02", "page-index": 1 } } ));
	lis.push(fn_dom( { tagName:"a", attrs:{ href:"javascript:void(0)", className: "prev",   "page-index": prev_page } } ));
	
	for(var i=1; i<tot_page+1; i++) {
		if( i == cur_page) {
			lis.push(fn_dom( { tagName:"a", attrs:{ href:"javascript:void(0)", innerHTML: i, className: "on",   "page-index": i } } ));	
		} else {
			lis.push(fn_dom( { tagName:"a", attrs:{ href:"javascript:void(0)", innerHTML: i, "page-index": i } } ));
		}
	}
	
	lis.push(fn_dom( { tagName:"a", attrs:{ href:"javascript:void(0)", className: "next",   "page-index": next_page } } ));
	lis.push(fn_dom( { tagName:"a", attrs:{ href:"javascript:void(0)", className: "next02", "page-index": tot_page } } ));
	
	$paging.append(lis);
};

var fn_getImgPath = function(filePath, fileName, originName) {
	var imgPath = ""; 
	if(fileName && originName) {
		var prefix = "/web/common/file/view/";
		imgPath = prefix + filePath + fileName;
		imgPath += "?originName="+encodeURIComponent(originName);
	}
	
	return imgPath;
};

var fn_getData = function(json, key) {
	var val = json[key];
	if(val === undefined) val = "";
	return val;
};
