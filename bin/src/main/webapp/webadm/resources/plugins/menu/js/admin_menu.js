	var select_siteList ,
	this_site_srl = 0,
	this_focus_selector = null,
	menu_list_box = $('#menu_list');

	// 메뉴 로딩
	function load_menu(site_srl)
	{
		// 메뉴 불러오기
		$.ajax({
			url : ADMIN_ROOT + 'menu/menuLoad.json',
			method : 'GET',
			dataType : 'json',
			cache : false,
			data : select_siteList.serialize(),
			beforeSend : function(jqXHR, settings)
			{
				this_site_srl = 0;

				// 전송 전 영역 초기화
				menu_list_box.empty().append('<p class="lineheight10 txtcenter fs15p color-red">메뉴(site_srl : '+ site_srl +') 불러오는 중입니다.</p>');
			},
			error : function(jqXHR, textStatus, errorThrown)
			{
				alert('메뉴 불러오는데 실패하였습니다.\n다시 시도해주시기 바랍니다.');
			},
			success : function(data, textStatus, jqXHR)
			{
				if(!data)
				{
					alert('메뉴 데이터가 없습니다.');
					return;
				}

				if(data.error)
				{
					alert('['+ data.message +']\n'+ data.description);
					return;
				}

				// 사이트 정보 변경
				this_site_srl = site_srl;

				// 메뉴 출력 처리
				menu_list_box.empty();
				print_menu(data.menuList);
			}
		});
	}

	// 메뉴 출력 처리
	function print_menu(menuList)
	{
		var parent_ul = new Array(),
			parent_li = new Array();

		parent_ul[0] = $('<ul class="root"></ul>');
		menu_list_box.append(parent_ul[0]);

		// 사이트명 추가
		parent_li[0] = $('<li><a href="#site_root" data-menu_srl="0" data-parent_menu_srl="0" data-align="0" data-depth="0" class="menu_link menu_root"><strong>'+ select_siteList.find('option[value="'+ this_site_srl +'"]').text() +'</strong></a><span class="root description">루트 메뉴입니다. 마우스 우클릭으로 하위 메뉴를 추가하세요.</span></li>');
		parent_ul[0].append(parent_li[0]);

		if(menuList == null || menuList.length <= 0)
		{
			menu_event(menu_list_box.find('a.menu_link'));
			return;
		}

		var pre_depth = 0;
		var ul, li, menu, depth;
		for(var i = 0; i < menuList.length; i++)
		{
			menu = menuList[i];
			depth = menu.depth;

			if(pre_depth < depth)
			{
				parent_ul[depth] = $('<ul class="depth'+ depth +'"></ul>');
				parent_li[pre_depth].append(parent_ul[depth]);
			}

			parent_li[depth] = make_menu_li(menu);
			parent_ul[depth].append(parent_li[depth]);

			pre_depth = depth;
		}


		$.each(menu_list_box.find('.tree') , function(index,item){
			if($(this).parent().find("ul").length){
				$(this).after("<i class=\"tgbtn fa fa-fw  fa-minus-square fa-sm\"></i>");
			}
		});


		menu_list_box.find('.tgbtn').click(function(){
			$(this).parent().find("ul").toggle();


			if($(this).parent().find("ul").css("display") == "block"){
				$(this).addClass("fa-minus-square");
				$(this).removeClass("fa-plus-square");
			}else{
				$(this).addClass("fa-plus-square");
				$(this).removeClass("fa-minus-square");
			}
		});

		menu_event(menu_list_box.find('a.menu_link'));

		// 자식들 css 처리
		menu_list_box.find('li:first-child').addClass('first');
		menu_list_box.find('li:last-child').addClass('last');

		// a 클릭 이벤트 처리
		menu_list_box.find('a').click(function(e){ e.preventDefault(); })



		// 메뉴별 이벤트 처리
		load_gloval_process();

		// 포커스 이동
		if(this_focus_selector)
		{
			$(this_focus_selector).focus();
		}

		/** test **/
		//xxx
/*
		menu_list_box.find('a.menu_link').each(function(index)
		{
			var self = $(this),
				depth = self.data('depth'),
				align = self.data('align');

			var txt = self.find('strong').text();
			txt += '('+ depth +'/'+ align +')';
			self.find('strong').text(txt);
		});
*/
	}

	function make_menu_li(menu)
	{
		var html = '<li><span class="tree"></span>';
		html += '<a href="'+ ADMIN_ROOT + 'menu/Edit/' + menu.menuSrl +'" data-menu_srl="'+ menu.menuSrl +'" data-parent_menu_srl="'+ menu.parentMenuSrl +'" data-align="'+ menu.align +'" data-depth="'+ menu.depth +'" class="menu_link">';
		html += '<strong>'+ menu.name +'</strong>';
		html += '<span class="text-success" style="font-size:10px">('+ menu.menuSrl +')</span>';
		html += '</a>';
		html += '<span class="description">';
		if(menu.menuUrl){
			html += menu.menuUrl;
		}else{
			html += "하위메뉴 이동";
		}

		html += menu.useYn == 'N' ? ' / <strong style="color:red">사용안함</strong>' : '';
		html += menu.mainYn == 'Y' ? ' / 메인메뉴' : '';
		html += '</span>';
		html += '</li>';

		return $(html);
	}

	// 이벤트 처리
	function menu_event(target)
	{
		if(!$.contextMenu) return;

		if(target.length > 1)
		{
			target.each(function(index)
			{
				menu_event($(this));
			});
			return;
		}

		var id = target.attr('id') || '',
			parent_menu_srl = parseInt(target.data('parent_menu_srl') || 0, 10),
			align = parseInt(target.data('align') || 0, 10),
			depth = parseInt(target.data('depth') || 0, 10);

		if(id == '')
		{
			id = 'kntool_menu_' + (new Date()).getTime() +'_'+ Math.round(Math.random()*10000);
			target.attr('id', id);
		}

		// ROOT 일경우
		if(parent_menu_srl == 0 && align == 0 && depth == 0)
		{
			$.contextMenu({
				selector : '#'+ id,
				callback : contextMenu_callback,
				items : {
					'add' : { name : (depth + 1) + '차 메뉴 추가', icon : 'add' },
					"sep1": "---------",
					'contents' : { name : '컨텐츠 편집', icon : 'paste' },
					"sep2": "---------",
					'quit' : {name: "닫기", icon: 'quit'}
				}
			});
		}
		// 자식들일 경우
		else
		{
			$.contextMenu({
				selector : '#'+ id,
				callback : contextMenu_callback,
				items : {
					'edit' : { name : '메뉴 수정', icon : 'edit' },
					'delete' : { name : '메뉴 삭제', icon : 'delete' },
					"sep1": "---------",
					'add' : { name : (depth + 1) + '차 메뉴 추가', icon : 'add' },
					"sep2": "---------",
					'contents' : { name : '컨텐츠 편집', icon : 'paste' },
					"sep3": "---------",
					'quit' : {name: "닫기", icon: 'quit'}
				}
			});
		}
	}

	// context menu 처리
	function contextMenu_callback(itemKey, opt)
	{
		var self = $(this),
			url = (ADMIN_ROOT + '/menu').replace(/\/{1,}/g, '/'),
			param = '?site_srl='+ this_site_srl,
			menu_srl = self.data('menu_srl'),
			parent_menu_srl = parseInt(self.data('parent_menu_srl') || 0, 10),
			align = parseInt(self.data('align') || 0, 10),
			depth = parseInt(self.data('depth') || 0, 10);

		var ul = self.parent().find('>ul'),
			li = ul.find('li'),
			a = li.eq(li.length - 1).find('a');
		if(a.length > 0)
		{
			align = a.data('align');
		}

		switch(itemKey)
		{
				// 메뉴 추가
			case 'add' :
				fn_menu_add(menu_srl , (align + 1) , (depth + 1));
				/*
				param += '&parent_menu_srl=' + menu_srl;
				param += '&align=' + (align + 1);
				param += '&depth=' + (depth + 1);

				window.open(url + '/Write' + param, 'menu_write', 'width=700, height=700, resizable=yes, scrollbars=yes, left=100, top=100');
				*/
				break;

				// 메뉴 수정
			case 'edit' :
				fn_data_load(menu_srl);
				//window.open(url + '/Edit/' + menu_srl + param, 'menu_edit', 'width=700, height=700, resizable=yes, scrollbars=yes, left=100, top=100');
				break;

				// 메뉴 삭제
			case 'delete' :
				fn_menu_delete(menu_srl);
				break;

				// 컨텐츠 편집
			case 'contents' :
				var newWindow = window.open("about:blank");
				newWindow.location.href = ADMIN_ROOT +  'contents/contentsRegist?menuNo=' + menu_srl;

				break;
		}
	}



	// 메뉴 작성 처리
	function menu_write(menu)
	{
		this_focus_selector = 'a[data-menu_srl="'+ menu.menuSrl +'"]';
		load_menu(this_site_srl);
	}
	self.top.menu_write = menu_write;


	// 메뉴 수정 처리
	function menu_edit(menu)
	{
		this_focus_selector = 'a[data-menu_srl="'+ menu.menuSrl +'"]';
		load_menu(this_site_srl);
	}
	self.top.menu_edit = menu_edit;


	// 메뉴 삭제 처리
	function menu_delete(menu)
	{
		this_focus_selector = 'a[data-menu_srl="'+ menu.parentMenuSrl +'"]';
		load_menu(this_site_srl);
	}
	self.top.menu_delete = menu_delete;


	// 메뉴 이동시 처리
	function menu_move(site_srl)
	{
		load_menu(site_srl);
	}
	self.top.menu_move = menu_move;



	// 메뉴 이동 처리 프로세스
	// position : 0 - 상단, 1 - 자식, 2 - 하단
	function moveProcess(parent, child, position)
	{
		if(parent.get(0) == child.get(0)) return;

		var parent__menu_srl = parent.data('menu_srl'),
			parent__parent_menu_srl = parent.data('parent_menu_srl'),
			parent__align = parent.data('align'),
			parent__depth = parent.data('depth'),
			parent__li = parent.parent(),
			parent__a = parent__li.find('a.menu_link'),
			parent__a_count = parent__a.length;

		var child__menu_srl = child.data('menu_srl'),
			child__parent_menu_srl = child.data('parent_menu_srl'),
			child__align = child.data('align'),
			child__depth = child.data('depth'),
			child__li = child.parent(),
			child__a = child__li.find('a.menu_link'),
			child__a_count = child__a.length;

		var new_parent_menu_srl = 0,
			new_align = parent__align,
			new_depth = parent__depth;


		// 상단
		if(position == 0)
		{
			new_parent_menu_srl = parent__parent_menu_srl;
		}
		// 자식
		else if(position == 1)
		{
			new_parent_menu_srl = parent__menu_srl;
			new_align++;
			new_depth++;
		}
		// 하단
		else if(position == 2)
		{
			new_parent_menu_srl = parent__parent_menu_srl;
			new_align += parent__a_count;
		}

		var child__align__min = child__a.eq(0).data('align'),
			child__align__max = child__a.eq(-1).data('align'),
			child__depth__min = child__a.eq(0).data('depth'),
			child__depth__max = child__a.eq(-1).data('depth');

		var dataJson = {'parentMenuSrl':new_parent_menu_srl ,
				'alignStart':child__align__min ,
				'alignEnd':child__align__max ,
				'depthStart':child__depth__min,
				'depthEnd':child__depth__max,
				'alignNew':new_align,
				'depthNew':new_depth,
				'siteGubun':this_site_srl
		};

		fn_menu_sorting(dataJson);
	}

	function status(msg)
	{
		$("#status").text(msg);
		//document.title = msg;
	}

	function load_gloval_process()
	{
		var preObject = null;
		var posObject = { object : null, width : 0, height : 0, x : 0, y : 0 };
		var selectObject = null;

		var mousePosition = 0;
		var marker = $('<div id="kntool_menu-marker">»</div>');
		var marker_line = $('<div id="kntool_menu-marker-line"></div>');
		var mouse_status = $('<div id="kntool_menu-mouse-status"><span class="icon"></span><span class="text"></span></div>'),
			mouse_status_text = mouse_status.find(".text");
		$(document.body).append(marker).append(marker_line).append(mouse_status);



		var eventHandler_target = menu_list_box.find('a.menu_link');

		function eventHandler(e)
		{
			var self = $(this),
				type = e.type.toLowerCase(),
				target = $(e.target),
				which = e.which || 1,
				stat = "";
			// 우클릭일 경우 처리
			if(which == 3 && type == 'mousedown')
			{
				eventHandler_target.removeClass("clicked");
				self.addClass("clicked");
			}
			if(which != 1) return;




			if(!target.is('a')) target = target.parents('a.menu_link');

			mousePosition = -1;

			if(preObject == null) preObject = target;
			if(preObject.get(0) != target.get(0))
			{
				preObject = target;
				posObject.object = preObject;
				posObject.x = preObject.offset().left;
				posObject.y = preObject.offset().top;
				posObject.width = preObject.outerWidth();
				posObject.height = preObject.outerHeight();
			}
			mousePosition = parseInt((e.pageY - posObject.y) / (posObject.height / 3), 10);
			mousePosition = mousePosition < 1 ? 0 : (mousePosition < 2 ? 1 : 2);

			if(selectObject != null)
				stat += " : s-"+ selectObject.text();
			stat += " : t-"+ target.text();
			stat += " : "+ type;
			stat += " : "+ posObject.x +","+ posObject.y +","+ posObject.width +","+ posObject.height;

			switch(type)
			{
			case "mouseover":
				eventHandler_target.removeClass("hovered");
				self.addClass("hovered");
				break;
			case "mouseout":
				self.removeClass("hovered");
				isProcessEnable = false;
				break;
			case "click":
				eventHandler_target.removeClass("clicked");
				self.addClass("clicked");
				break;
			case "mousedown":
				selectObject = self;
				break;
			case "mouseup":
				if(selectObject != null && isProcessEnable)
				{
					moveProcess(posObject.object, selectObject, mousePosition);
				}

				preObject = null;
				selectObject = null;
				isProcessEnable = false;
				break;
			case "mousemove":
				isProcessEnable = true;
				if(selectObject == null)
				{
					stat += " : "+ (mousePosition == 0 ? "상" : (mousePosition == 1 ? "중" : "하"));
				}
				else if(selectObject != null)
				{
					stat += " : "+ (mousePosition == 0 ? "상" : (mousePosition == 1 ? "중" : "하"));

					// 현재 대상 prev, next 확인
					if(target.get(0) == selectObject.parent().prev().find("a").get(0) && mousePosition == 2)
					{
						isProcessEnable = false;
						stat += " : 동일위치";
					}
					else if(target.get(0) == selectObject.parent().next().find("a").get(0) && mousePosition == 0)
					{
						isProcessEnable = false;
						stat += " : 동일위치";
					}
					else if(selectObject.get(0) == target.get(0))
					{
						isProcessEnable = false;
						stat += " : 동일개체";
					}
					else
					{
						selectObject.parent().find("a").each(function(index){ if($(this).get(0) == target.get(0)) isProcessEnable = false; });
						if(isProcessEnable == false)
							stat += " : 자식개체";
					}
					stat += " : "+ isProcessEnable;
				}
				break;
			}
			e.preventDefault();

			status(stat);
		}
		eventHandler_target.bind("mouseover mouseout mousemove click mousedown mouseup", eventHandler);



		function eventHandlerGlobal(e)
		{
			var type = e.type.toLowerCase();
			switch(type)
			{
			case "mouseup":
				selectObject = null;
				mouse_status.hide();
				mouse_status_text.text('');
				break;
			case "mousemove":
				if(mouse_status.is(':visible'))
				{
					var x = posObject.x,
						y = posObject.y;

					mouse_status.removeClass().addClass(isProcessEnable ? 'enable' : 'disable').css({ left : e.pageX, top : e.pageY });

					if(isProcessEnable && mousePosition > -1)
					{
						if(mousePosition == 0)
						{
							x = x - marker.width() / 2;
							y = y - posObject.height / 2;

							marker.show().css({ left : x, top : y });
							marker_line.show().css({ left : x, top : y + 10});
						}
						else if(mousePosition == 1)
						{
							x = x - marker.width() / 2;

							marker.show().css({ left : x, top : y });
							marker_line.hide();
						}
						else if(mousePosition == 2)
						{
							x = x - marker.width() / 2;
							y = y + posObject.height / 2;

							marker.show().css({ left : x, top : y });
							marker_line.show().css({ left : x, top : y + 10 });
						}
					}
					else
					{
						marker.hide();
						marker_line.hide();
					}
				}
				else
				{
					mouse_status.hide();
					mouse_status_text.text('');
					marker.hide();
					marker_line.hide();
				}
				break;
			case "mousedown":
				if(selectObject != null)
				{
					mouse_status.show().removeClass().addClass(isProcessEnable ? 'enable' : 'disable').css({ left : e.pageX, top : e.pageY });
					mouse_status_text.text(selectObject.text());
				}
				else
				{
					mouse_status.hide();
					mouse_status_text.text('');
					marker.hide();
					marker_line.hide();
				}
				break;
			}
		}
		$(document).bind("mouseup mousedown mousemove", eventHandlerGlobal);
	}