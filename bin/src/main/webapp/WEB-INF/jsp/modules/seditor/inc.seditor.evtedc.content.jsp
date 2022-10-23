<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${IsMobile}">
	<c:set var="content_title_print" value="row"/>
</c:if>

				<script type="text/javascript" src="<c:url value="/webadm/resources/se/js/HuskyEZCreator.js"/>" charset="utf-8"></script>
				<script type="text/javascript">
				//<![CDATA[
				$(document).ready(function() {
					
					var TEXTAREA_ID = 'evtIntrcn';
					//var top.modules_board__oEditors = [];
					if(typeof(top.modules_board__oEditors) == 'undefined') {
						top.modules_board__oEditors = [];
					}
					
					function createEditor(mode) {
						
						var textarea = $('#'+ TEXTAREA_ID),
							editor = textarea.next('iframe');
						if(editor.length > 0) return;
						
						nhn.husky.EZCreator.createInIFrame( {
							
							oAppRef: top.modules_board__oEditors,
							elPlaceHolder: TEXTAREA_ID,
							sSkinURI: '<c:url value="/webadm/resources/se/SmartEditor2Skin.html"/>',	
							htParams : {
								
								bUseToolbar : true,
								bUseVerticalResizer : true,
								bUseModeChanger : true,
								bSkipXssFilter : false,
								fOnBeforeUnload : function()
								{
								}
							},
							
							fOnAppLoad : function() {
								//alert("삽입!");
								//modules_board__oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
							},
							
							fCreator: 'createSEditor2'
							
						});
						
						function reload_content() {
							modules_board__oEditors.getById[TEXTAREA_ID].exec("LOAD_CONTENTS_FIELD", []);
						}
						
						top.modules_board__reload_content = reload_content;
						
						function load_content() {
							return textarea.val();
						}
						
						top.modules_board__load_content = load_content;
						
						function set_content(val) {
							textarea.val(val);
							reload_content();
						}
						
						top.modules_board__set_content = set_content;
						
						function insert_content(val) {
							modules_board__oEditors.getById[TEXTAREA_ID].exec('PASTE_HTML', [val]);
						}
						
						top.modules_board__insert_content = insert_content;
				
						function update_content() {
							
							modules_board__oEditors.getById[TEXTAREA_ID].exec('UPDATE_CONTENTS_FIELD', []);
							
							/*
							var val = textarea.val(),
								dumy = $('<div></div>').html(val),
								text = $.trim(dumy.text().replace(/\s/g, ''));
							if(text == '')
							{
								textarea.val('');
							}
							*/
						}
						
						top.modules_board__update_content = update_content;
						
						function form_submit(e) {
							update_content();
						}
						
						top.modules_board__form_submit = form_submit;
						
						function form_reset(e) {
							
							textarea.parents('form').each(function(idx) {
								this.reset();
							});
							
							reload_content();
						}
						
						top.modules_board__form_reset = form_reset;
						
						$('#'+ TEXTAREA_ID).parents('form').submit(top.modules_board__form_submit);
						$('#'+ TEXTAREA_ID).parents('form').find(':reset').click(top.modules_board__form_reset);
						
					}
					
					top.modules_board__createEditor = createEditor;
					
					function removeEditor(mode) {
						
						var textarea = $('#'+ TEXTAREA_ID),
							editor = textarea.next('iframe');
						
						if(editor.length > 0) {
							top.modules_board__update_content();
							
							textarea.show().parents('form').unbind("submit", top.modules_board__form_submit);
							textarea.show().parents('form').find(':reset').unbind("click", top.modules_board__form_reset);
							top.modules_board__form_submit = function(){ return true; }
							top.modules_board__form_reset = function(){ return true; }
							
							// 에디터 제거
							editor.remove();
						}
						
						// 텍스트 제거
						if(mode == 'TEXT') {
							
							var val = textarea.val(),
								div = $('<div></div>');
							div.html(val);
							textarea.val(div.text());
						}
						
					}
					
					top.modules_board__removeEditor = removeEditor;
				});
				//]]>
				</script>
				
				<div>
					<form:textarea path="content" cols="60" rows="40" cssStyle="width: 95%; height: ${content_height};" title="내용을 입력해주세요"/>
					<form:errors path="content" cssClass="input-error"/>
					<form:hidden path="content_type"/>
					<form:errors path="content_type" cssClass="input-error"/>
					<script type="text/javascript">
					//<![CDATA[
					$(document).ready(function()
					{
						top.modules_board__createEditor();
					});
					//]]>
					</script>
				</div>