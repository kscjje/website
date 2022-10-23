/**
 * Convert a single file-input element into a 'multiple' input list
 * Usage:
 *
 *   1. Create a file input element (no name)
 *      eg. <input type="file" id="first_file_element">
 *
 *   2. Create a DIV for the output to be written to
 *      eg. <div id="files_list"></div>
 *
 *   3. Instantiate a MultiSelector object, passing in the DIV and an (optional) maximum number of files
 *      eg. var multi_selector = new MultiSelector( document.getElementById( 'files_list' ), 3 );
 *
 *   4. Add the first element
 *      eg. multi_selector.addElement( document.getElementById( 'first_file_element' ) );
 */

function MultiSelector( list_target, max ){

	// Where to write the list
	this.list_target = list_target;
	// How many elements?
	this.count = 0;
	// How many elements?
	this.id = 0;
	// Is there a maximum?
	if( max ){
		this.max = max;
	} else {
		this.max = -1;
	};

	/**
	 * Add a new file input element
	 */
	this.addElement = function( element ){

		// Make sure it's a file input element
		if( element.tagName == 'INPUT' && element.type == 'file' ){

			// Element name -- what number am I?
			element.name = 'file_' + this.id++;
			element.style.zIndex = this.id;

			// Add reference to this object
			element.multi_selector = this;

			// What to do when a file is selected
			element.onchange = function(event){
				var i = 0;
				var flag = true;
				// 2019.06.06  포털 UX/UI 개선 적용 시작
				var browser = navigator.appName;
				var maxSize = fileMaxSize * 1024 * 1024;

				//alert("반드시 확인 => JYS");
				console.log("반드시 확인 => JYS");

				var ext = fileAllowExt;

				$.each(element.files , function(index,file){
					 var fileSize = file.size;
/*
					 if (fileSize > maxSize) {
							alert("첨부파일 사이즈는 10MB 이내로 등록 가능합니다.");
				            return;
						}
*/
	        		 if(ext.indexOf(file.name.substring(file.name.lastIndexOf(".")+1).toLowerCase()) < 0) {
		                 alert("업로드가 불가능한 파일입니다. 파일 확장자를 확인해 주세요.");
		                 flag = false;
		                 return false;
		             }
	        		 i++;
	        	 });

				// 2019.06.06  포털 UX/UI 개선 적용 끝

				// New file input
				if(flag){
					var new_element = document.createElement( 'input' );
					new_element.type = 'file';
					new_element.setAttribute('multiple' , '');
					new_element.className += "file_input_hidden";

					if(list_target.id == "egovComImgList"){
						new_element.setAttribute('accept' , 'image/*');
					}

					// Add new element
					this.parentNode.insertBefore( new_element, this );

					// Apply 'update' to element
					this.multi_selector.addElement( new_element );

					// Update list
					this.multi_selector.addListRow( this , event );

					// Hide this: we can't use display:none because Safari doesn't like it
					//this.style.position = 'absolute';
					//this.style.left = '-1000px';
				}
			};
			// If we've reached maximum number, disable input element
			if( this.max != -1 && this.count >= this.max ){
				element.disabled = true;
			};

			// File element counter
			this.count++;
			// Most recent element
			this.current_element = element;

		} else {
			// This can only be applied to file input elements!
			alert( 'Error: not a file input element' );
		};

	};

	/**
	 * Add a new row to the list of files
	 */
	this.addListRow = function( element , event){

		// Row div
		//var new_row = document.createElement( 'div' ).className = "file_add";
		var new_row = document.createElement( 'li' );
		new_row.className += "file_add";

		// Delete button
		var new_row_button = document.createElement( 'i' );
		//new_row_button.value = '삭제';
		//new_row_button.className = "badge bg-danger";
		//new_row_button.innerHTML = " X ";
		//new_row_button.href = 'javascript:;';
		//new_row_button.style = "margin-left:5px";
		new_row_button.name = "btn_" + element.name;

		// References
		new_row.element = element;

		// Delete function
		new_row_button.onclick= function(){

			// Remove element from form
			this.parentNode.element.parentNode.removeChild( this.parentNode.element );

			// Remove this row from the list
			this.parentNode.parentNode.removeChild( this.parentNode );

			// Decrement counter
			this.parentNode.element.multi_selector.count--;

			// Re-enable input element (if it's disabled)
			this.parentNode.element.multi_selector.current_element.disabled = false;

			//    which nixes your already queued uploads
			return false;
		};

		// Set row value
		//new_row.innerHTML = element.value;

		// New file input
		 $.each(element.files , function(index,file){
			 var element_value = document.createElement( 'span' );
				//element_value.type = 'text';
				element_value.innerHTML = (index > 0?'<br/>':'') + file.name + " (" + formatBytes(file.size)+")";
				//element_value.disabled = true;
				new_row.appendChild(element_value);
    	 });

		// Add button
		new_row.appendChild( new_row_button );

		if(this.list_target.id == "egovComImgList"){
			var reader = new FileReader();
			reader.onload = function(event) {
				var img = document.createElement("img");
				img.setAttribute("src", event.target.result);
				img.style.height = "100px";
				//document.querySelector("div#image_container").appendChild(img);
				// 이미지
				var br = document.createElement("br");
				new_row.appendChild( br );
				new_row.appendChild( img );
			};
			reader.readAsDataURL(event.target.files[0]);
		}





		// Add it to the list
		this.list_target.appendChild( new_row );
	};

};