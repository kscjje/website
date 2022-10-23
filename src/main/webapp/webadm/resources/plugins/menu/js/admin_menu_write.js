jQuery(function($)
{
	// 모듈 선택 화면
	var type = $('#type'),
		type_info = $('#type_info'),
		type_info_select = null,
		type_action = $('#extra_datas\\\'type_action\\\''),
		type_action_select = null,
		type_add_action = $('#extra_datas\\\'type_add_action\\\''),
		type_add_action_select = null,
		type_add_action2 = $('#extra_datas\\\'type_add_action2\\\''),
		type_add_action_select2 = null,
		type_add_action3 = $('#extra_datas\\\'type_add_action3\\\''),
		type_add_action_select3 = null,
		type_add_action4 = $('#extra_datas\\\'type_add_action4\\\''),
		type_add_action_select4 = null;

	
	type.change(type__change).change();
	
	// 메뉴 종류 선택 이벤트 : 모듈 설정 선택 추가
	function type__change(e)
	{
		var self = $(this),
			val = self.val(),
			exists = false;
		
		if(MODULE_CONFIG_LIST && MODULE_CONFIG_LIST.length > 0)
		{
			for(var i = 0, length = MODULE_CONFIG_LIST.length; i < length; i++)
			{
				var module_config = MODULE_CONFIG_LIST[i];
				
				if(module_config.module_key == val)
				{
					exists = true;
					break;
				}
			}
		}
		
		if(exists)
		{
			if(type_info_select != null)
			{
				type_info_select.remove();
				$('label[for="type_info_select"], .br_type_info_select').remove();
				type_info_select = null;
			}
			
			type_info_select = $('<select id="type_info_select"><option value="" selected="selected">선택하세요</option></select>');
			type_info_select.change(type_info_select__change);
			
			var append_count = 0;
			for(var i = 0, length = MODULE_CONFIG_LIST.length; i < length; i++)
			{
				var module_config = MODULE_CONFIG_LIST[i];
				
				if(module_config.module_key == val)
				{
					type_info_select.append('<option value="'+ module_config.id +'">'+ module_config.name +'</option>');
					append_count++;
				}
			}
			
			if(append_count == 1)
			{
				type_info_select.find('option:last-child').prop('selected', true);
			}
			
			if(type_info.val() && type_info_select.find('option[value="'+ type_info.val() +'"]').length > 0)
			{
				type_info_select.val(type_info.val()).change();
			}
			else
				type_info_select.change();
			
			type_info_select.insertBefore(type_info.hide());
			$('<label for="type_info_select">모듈 설정 : </label>').insertBefore(type_info_select);
		}
		else
		{
			if(type_info_select != null)
			{
				type_info_select.remove();
				$('label[for="type_info_select"], .br_type_info_select').remove();
				type_info_select = null;
			}
			type_info.show();
			
			if(type_action_select != null)
			{
				type_action_select.remove();
				$('label[for="type_action_select"], .br_type_action_select').remove();
				type_action_select = null;
			}
			
			if(type_add_action_select != null)
			{
				type_add_action_select.remove();
				$('label[for="type_add_action_select"], .br_type_add_action_select').remove();
				type_add_action_select = null;
			}
			
			if(type_add_action_select2 != null)
			{
				type_add_action_select2.remove();
				$('label[for="type_add_action_select2"], .br_type_add_action_select2').remove();
				type_add_action_select3 = null;
			}
			
			if(type_add_action_select3 != null)
			{
				type_add_action_select3.remove();
				$('label[for="type_add_action_select3"], .br_type_add_action_select3').remove();
				type_add_action_select3 = null;
			}
			
			if(type_add_action_select4 != null)
			{
				type_add_action_select4.remove();
				$('label[for="type_add_action_select4"], .br_type_add_action_select4').remove();
				type_add_action_select4 = null;
			}
		}
	}
	
	// 메뉴 세부 정보 선택 이벤트 : 모듈 액션 선택 추가
	function type_info_select__change(e)
	{
		type_info.val(type_info_select.val());
		
		if(type_action_select != null)
		{
			type_action_select.remove();
			$('label[for="type_action_select"], .br_type_action_select').remove();
			type_action_select = null;
		}
		
		if(type_add_action_select != null)
		{
			type_add_action_select.remove();
			$('label[for="type_add_action_select"], .br_type_add_action_select').remove();
			type_add_action_select = null;
		}
		
		if(type_add_action_select2 != null)
		{
			type_add_action_select2.remove();
			$('label[for="type_add_action_select2"], .br_type_add_action_select2').remove();
			type_add_action_select3 = null;
		}
		
		if(type_add_action_select3 != null)
		{
			type_add_action_select3.remove();
			$('label[for="type_add_action_select3"], .br_type_add_action_select3').remove();
			type_add_action_select3 = null;
		}
		
		if(type_add_action_select4 != null)
		{
			type_add_action_select4.remove();
			$('label[for="type_add_action_select4"], .br_type_add_action_select4').remove();
			type_add_action_select4 = null;
		}
		
		var module_key = type.val();
		var module = null;
		
		if(module_key && module_key != '')
		{
			for(var i = 0, length = MODULES_INFO.length; i < length; i++)
			{
				if(MODULES_INFO[i].key == module_key)
				{
					module = MODULES_INFO[i];
					break;
				}
			}
		}
		
		var module_config_id = type_info.val();
		var module_config = null;
		if(module_config_id && module_config_id != '')
		{
			for(var i = 0, length = MODULE_CONFIG_LIST.length; i < length; i++)
			{
				if(MODULE_CONFIG_LIST[i].id == module_config_id)
				{
					module_config = MODULE_CONFIG_LIST[i];
					break;
				}
			}
		}
		
		
		if(module && module.process_action)
		{
			type_action_select = $('<select id="type_action_select"><option value="" selected="selected">선택 안함</option></select>');
			type_action_select.change(type_action_select__change);
			
			for(key in module.process_action)
			{
				var new_key = key.replace(/^(\[|\{)[0-9]{1,}(\]|\})/, '');
				var value = module.process_action[key];
				
				type_action_select.append('<option value="'+ new_key +'">'+ new_key +' : '+ value +'</option>');
			}
			
			if(type_action.val() && type_action_select.find('option[value="'+ type_action.val() +'"]').length > 0)
				type_action_select.val(type_action.val() || '').change();
			else
				type_action_select.change();
			
			type_action_select.insertBefore(type_action);
			$('<br class="br_type_action_select"/><label for="type_action_select">모듈 처리 설정 : </label>').insertBefore(type_action_select);
		}
		
		
		// 카테고리 존재시
		if(module_config != null && module_config.extra_datas && module_config.extra_datas.category && module_config.extra_datas.category > 0)
		{
			type_add_action_select = $('<select id="type_add_action_select" class="type_add_action_select"><option value="" selected="selected">선택 안함</option></select>');
			type_add_action_select.change(type_add_action_select__change);
			
			type_add_action_select.insertBefore(type_add_action);
			$('<br class="br_type_add_action_select"/><label for="type_add_action_select">카테고리 : </label>').insertBefore(type_add_action_select);
			
			load_category(module_config.extra_datas.category, type_add_action, type_add_action_select);
		}
		// 카테고리2 존재시
		if(module_config != null && module_config.extra_datas && module_config.extra_datas.category2 && module_config.extra_datas.category2 > 0)
		{
			type_add_action_select2 = $('<select id="type_add_action_select2" class="type_add_action_select2"><option value="" selected="selected">선택 안함</option></select>');
			type_add_action_select2.change(type_add_action_select__change2);
			
			type_add_action_select2.insertBefore(type_add_action2);
			$('<br class="br_type_add_action_select2"/><label for="type_add_action_select2">카테고리2 : </label>').insertBefore(type_add_action_select2);
			
			load_category(module_config.extra_datas.category2, type_add_action2, type_add_action_select2);
		}
		// 카테고리3 존재시
		if(module_config != null && module_config.extra_datas && module_config.extra_datas.category3 && module_config.extra_datas.category3 > 0)
		{
			type_add_action_select3 = $('<select id="type_add_action_select3" class="type_add_action_select3"><option value="" selected="selected">선택 안함</option></select>');
			type_add_action_select3.change(type_add_action_select__change3);
			
			type_add_action_select3.insertBefore(type_add_action3);
			$('<br class="br_type_add_action_select3"/><label for="type_add_action_select3">카테고리3 : </label>').insertBefore(type_add_action_select3);
			
			load_category(module_config.extra_datas.category3, type_add_action3, type_add_action_select3);
		}
		// 카테고리4 존재시
		if(module_config != null && module_config.extra_datas && module_config.extra_datas.category4 && module_config.extra_datas.category4 > 0)
		{
			type_add_action_select4 = $('<select id="type_add_action_select4" class="type_add_action_select4"><option value="" selected="selected">선택 안함</option></select>');
			type_add_action_select4.change(type_add_action_select__change4);
			
			type_add_action_select4.insertBefore(type_add_action4);
			$('<br class="br_type_add_action_select4"/><label for="type_add_action_select4">카테고리4 : </label>').insertBefore(type_add_action_select4);
			
			load_category(module_config.extra_datas.category4, type_add_action4, type_add_action_select4);
		}
	}
	
	function load_category(category_srl, type_add_action, type_add_action_select)
	{
		$.ajax({
			url : ADMIN_ROOT + 'document/CategoryDetail'+ URL_CALL_JSON +'/'+ category_srl,
			method : 'GET',
			dataType : 'json',
			error : function(jqXHR, textStatus, errorThrown)
			{
				type_add_action_select.find('option').remove();
				type_add_action_select.append('<option value="" selected="selected">정보 없음</option>');
			},
			success : function(data, textStatus, jqXHR)
			{
				if(data && data.error)
				{
					alert('['+ data.message +']\n'+ data.description);
					return;
				}
				
				type_add_action_select.find('option').remove();
				if(data && data.data)
				{
					type_add_action_select.append('<option value="" selected="selected">전체 보기</option>');
					
					for(key in data.data)
					{
						var category = data.data[key],
							text = '['+ category.document_category_srl +'] '+ category.name + (category.use_YN == 'Y' ? '' : ' [사용암함]'),
							val = category.document_category_srl,
							space = '';
						
						for(var i = 3; i <= category.depth; i++) {
							space += '&nbsp;&nbsp;&nbsp;&nbsp;';
						}
						
						type_add_action_select.append('<option data-depth="'+ category.depth +'" value="'+ val +'">'+ space + text +'</option>');
					}
					
					if(type_add_action.val() && type_add_action_select.find('option[value="'+ type_add_action.val() +'"]').length > 0)
						type_add_action_select.val(type_add_action.val() || '').change();
					else
						type_add_action_select.change();
				}
				else
				{
					type_add_action_select.append('<option value="" selected="selected">정보 없음</option>');
				}
			}
		});
	}
	
	// 모듈 액션 선택 이벤트 : 모듈 액션 정보 삽입
	function type_action_select__change(e)
	{
		var self = $(this),
			val = self.val();
		type_action.val(val);
	}
	
	// 모듈 액션 선택 이벤트 : 모듈 액션 정보 삽입
	function type_add_action_select__change(e)
	{
		var self = $(this),
			val = self.val();
		type_add_action.val(val);
	}
	function type_add_action_select__change2(e)
	{
		var self = $(this),
		val = self.val();
		type_add_action2.val(val);
	}
	function type_add_action_select__change3(e)
	{
		var self = $(this),
		val = self.val();
		type_add_action3.val(val);
	}
	function type_add_action_select__change4(e)
	{
		var self = $(this),
		val = self.val();
		type_add_action4.val(val);
	}
});