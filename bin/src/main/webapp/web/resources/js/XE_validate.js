/**
 * 
 * XE_validate.js
 * requires @ XE.oop;
 * requires @ XE.extend;
 * requires @ XE.event.addEvent;
 * requires @ XE.event.preventDefault;
 * 
 */
if(!XE){
	var XE = {};

	// XE.oop
	XE.oop = {};
	XE.oop.declareClass = function( aNamespace, aClassname, aAncestor, aFields ) {
		var lNS;
		if(!aNamespace || aNamespace === "") lNS = window;
		else lNS = window[ aNamespace ] ? window[ aNamespace ] : (window[ aNamespace ] = {});
		
		var lConstructor = function() {
			if( this.create ) {
				this.create.apply( this, arguments );
			}
		};
		
		lNS[ aClassname ] = lConstructor;

		var lField;
		for( lField in aFields ) {
			lConstructor.prototype[ lField ] = aFields[ lField ];
		}
		if( aAncestor != null ) {
			if( !aAncestor.descendants ) {
				aAncestor.descendants = [];
			}
			aAncestor.descendants.push( lConstructor );
			for( lField in aAncestor.prototype ) {
				var lAncMthd = aAncestor.prototype[ lField ];
				if( typeof lAncMthd == "function" ) {
					if( lConstructor.prototype[ lField ] ) {
						lConstructor.prototype[ lField ].inherited = lAncMthd;
					} else {
						lConstructor.prototype[ lField ] = lAncMthd;
					}
					lConstructor.prototype[ lField ].superClass = aAncestor;
				}
			}
		}
	};

	XE.oop.addPlugIn = function( aClass, aPlugIn ) {
		if( !aClass.fPlugIns ) {
			aClass.fPlugIns = [];
		}
		
		aClass.fPlugIns.push( aPlugIn );
		for( var lField in aPlugIn ) {
			if( !aClass.prototype[ lField ] ) {
				aClass.prototype[ lField ] = aPlugIn[ lField ];
			}
		}
		if( aClass.descendants ) {
			for( var lIdx = 0, lCnt = aClass.descendants.length; lIdx < lCnt; lIdx ++ ) {
				XE.oop.addPlugIn( aClass.descendants[ lIdx ], aPlugIn );
			}
		}
	};

	// XE.extend
	XE.extend = function(){
		var target = arguments[0] || {},
			i = 1,
			len = arguments.length,
			deep, options, src, copy, name, clone, propValue;
		
		if(typeof target === "boolean"){
			deep = target;
			target = arguments[1] || {};
			i++;
		}
		if(typeof arguments[i] == "string"){
			propValue = arguments[1];
			i++;
		}
		if(typeof target != "object"){
			target = {};
		}
		if(len == i){
			target = this;
			i--;
		}
		
		for(;i<len;i++){
			if((options = arguments[i]) != null){
				for(name in options){
					src  = target[name];
					copy = options[name];
					
					if(target === copy) continue;
					
					if(!propValue && deep && copy && typeof src == 'object' ){
						clone = src;
						target[name] = XE.extend(deep, {}, clone, copy);
						
					} else if(copy !== undefined){
						if(propValue) target[name][propValue] = copy;
						else target[name] = copy;
					}
				}
			}
		}
		
		return target;
	}
	// XE.event
	XE.event = {
		preventDefault : function(e){
			e = e || window.event;
			if(!e) return;
			if(e.preventDefault) e.preventDefault();
			else e.returnValue = false;
		},
		addEvent : function(obj, type, fn){
		    if (obj.addEventListener)
		        obj.addEventListener(type, fn, false);
		    else if (obj.attachEvent)
		    {
		        obj["e"+type+fn] = fn;
		        obj[type+fn] = function() { obj["e"+type+fn]( window.event ); }
		        obj.attachEvent("on"+type, obj[type+fn]);
		    }
		},
		removeEvent : function( obj, type, fn ){
			if (obj.removeEventListener)
				obj.removeEventListener( type, fn, false );
			else if (obj.detachEvent)
			{
				obj.detachEvent( "on"+type, obj[type+fn] );
				obj[type+fn] = null;
				obj["e"+type+fn] = null;
			}
		}
	}
	
	String.prototype.trim=function(){return this.replace(/^\s*/,"").replace(/\s*$/,"");}
	String.prototype.isEmpty = function(){ return (this == '' || this.length ==0 || this === null); }
}


(function(window, XE, undefined){

var ElementEventNames  = [	"keypress", "keyup", "keydown", "focus", "blur"/*, "change", "click", "mouseover", "mouseout"*/ ];

var TYPE_UI        = "ui",
	TYPE_FILTER    = "filter",
	TYPE_VALIDATOR = "validator",
	TYPE_STRIPPER  = "stripper",
	TYPE_MASKER    = "masker",
	TYPE_FORM      = "form",
	TYPE_ACTION    = "action";
	
var FILTER_NUMERIC         = "[0-9]",
	FILTER_DATE       	   = "[0-9\\-]",
	FILTER_ALPHABETIC      = "[A-Za-z]",
	FILTER_HANGUL     	   = "[ㄱ-ㅎㅏ-ㅣ가-힣]",
	FILTER_ALPHA_NUMERIC   = "[A-Za-z0-9]",
	FILTER_FLOAT           = "[0-9\\.\\-\\+]",
	FILTER_INTEGER         = "[0-9\\-\\+]",
	FILTER_HEXA            = "[a-fA-F0-9]";

var MASK_DATE = "9999-99-99",
	MASK_PSN  = "999999-9999999",
	MASK_CSN  = "999-99-99999";

var STRIP_SPECIAL_CHAR  = /(\"|\'|\`|\;|\\|)*/g,  // " ' ` ; \
	STRIP_WHITE_SPACE   = /\s/g,
	STRIP_ATTRIBUTE = /\w+\=(["'\w\:\(\)\{\}])*/gim, // ex) href="javascript:function(){}"
	STRIP_TAG = /\<\w+\s*\/?>|<\/\w+>|javascript/gim;

var FAILED_MSG_PREFIX  = "입력오류 발생 : ",
	VALIDATE_PREFIX    = "validate";

var EXCLUDE_EVENT = "exclude-event",	
	EXCLUDE_AJAX  = "exclude-ajax";

var DocHandlers = [],
	allElements = [];

// 폼 Utility메소드
var FormUtility = {
	serialize : function(){
		var el,els = this.elements, 
			i = 0, len = els.length,
			queryString = "";
		
		this.unmasking();
	
		var addField = function(name,value){
			if (queryString.length>0) { 
				queryString += "&";
			}
			queryString += name + "=" + window.encodeURIComponent(value);				
		}
		for (; i<len; i++) {
			el = els[i];
			if(el.disabled) continue;
			if(el.xe_options && el.xe_options[EXCLUDE_AJAX] === null) continue;
			switch(el.type) {
				case 'text': case 'password': case 'hidden': case 'textarea': case 'tel':
					addField(el.name,el.value);
					break;
				case 'select-one':
					if (el.selectedIndex>=0) {
						addField(el.name,el.options[el.selectedIndex].value);
					}
					break;
				case 'select-multiple':
					for (var j=0, jLen = el.options.length; j<jLen; j++) {
						if (el.options[j].selected) {
							addField(el.name,el.options[j].value);
						}
					}
					break;
				case 'checkbox': case 'radio':
					if (el.checked) {
						addField(el.name,el.value);
					}
					break;
			}
		}
		
		this.masking();
		return queryString;			
	},
	
	validateForm : function(){
		return this.callElementsMethod("validateForm");
	},
	
	unmasking : function(){
		this.callElementsMethod("unmasking");
	},
	
	masking : function(){
		this.callElementsMethod("masking");
	},
	
	callElementsMethod : function(methodName){
		var items = this.elements,
			item,
			i = 0,
			len = items.length;
		
		for(;i<len;i++){
			item = this.elements[i];
			if(!item[methodName] || typeof item[methodName] !== 'function') continue;
			if(item[methodName]() === false) return false; 
		}
		return true;	
	},
	
	jobsExecute : function(){
		if(!this.hangers) return;
		var jobs = this.hangers[TYPE_FORM],
			jobName, job, param, 
			jobsResult = [];
		
		for(jobName in jobs){
			param = this.xe_options[jobName];
			job   = jobs[jobName].call(this, param);
			try {
				jobsResult.push(job.process());
			} catch(e){
				alert(e);
			}
		}
		return jobsResult;
	},
	
	prepareSubmit : function(e){
		var jobsResult = this.jobsExecute(),
			aResult;
		
		if(jobsResult){
			var i = 0,
			len = jobsResult.length;
			for(; i<len; i++){
				aResult = jobsResult[i];
				if(aResult === undefined) continue;
				if(aResult === false) {
					return false;
				}
			}
		}
		
		if(!this.validateForm()) {
			return false;
		}
		
		return true;
	},
	
	ajaxSubmit : function(e){
		if(this.prepareSubmit() === false){
			XE.event.preventDefault(e);
			return false;
		}
		
		return this.serialize();
	},	
	
	fireSubmit : function(e){
		if(this.prepareSubmit() === false){
			XE.event.preventDefault(e);
			return false;
		}
		
		this.unmasking();
		this.submit();
	}
}

// 엘리먼트 Utility메소드
var ElementUtility = {
	addClass : function(className){
		if (!this.getAttribute("class")) {
			this.className = className;
			return;
		}
		
		var old_class = this.getAttribute("class");
		if (old_class.indexOf(className) == -1 )
			this.className = old_class + " " + className;	
	},
	
	bind : function(type, handler){
		DocHandlers.push({obj : this, name : type, callback : handler});
		XE.event.addEvent(this, type, handler);
	},
	
	createFailedMsg : function(msg){
		var label = this.xe_options["label"];
		
		/**
		label = label ? label : this.getAttribute("name");
		
	    if ( label ) msg = FAILED_MSG_PREFIX +  "항목명(" + label + ")\n" + msg;
	    else msg = FAILED_MSG_PREFIX + msg;
	    **/
	    
	    //modified by woojinpark. 2021.11.18
	    if ( label ) msg = label + " "; 
	    msg += this.getAttribute("placeholder");
            
		return msg;
	},
	
	unmasking : function(){
		if(!this.masker) return;
		this.masker.restore();
	},
	
	masking : function(){
		if(!this.masker) return;
		this.masker.process();
	},
	
	validateForm : function(){
		if(!this.validators || this.validators.length <= 0) return;
		var i = 0,  
			len = this.validators.length,
			validator, jobs;

		for(;i<len;i++){
			validator = this.validators[i];
			if(validator.process() === false) {
				alert(validator.getFailedMsg());
				jobs = this.getJobs(TYPE_ACTION, VALIDATE_PREFIX);
				if(jobs.length > 0){
					var j = 0, jLen = jobs.length;
					for(;j<jLen;j++) jobs[j].process();
				}
				this.focus();
				return false;
			} 
		}

		return true;
	},

	getJobs : function(type, prefix){
		var jobs = [],
			hangers = this.hangers[type],
			hanger, job, param, regexp;
		
		var i = 1,
			len = arguments.length;
		
		regexp = prefix ? new RegExp("^" + prefix) : null;
		
		
		do{
			prefix = arguments[i];
			regexp = prefix ? new RegExp("^" + prefix) : null;
			
			for(hanger in hangers){
				if(regexp && !regexp.test(hanger)) continue;
				param = this.getParam(hanger);
				job = hangers[hanger].call(this, param);
				if(job && typeof job === 'object') jobs.push(job);				
			}
			
			i++;
		} while(i<len);

		return jobs;
	},
	
	getJob : function(type, jobName){
		var hangers = this.hangers[type],
			hanger, param;
		
		if(!hangers) return;
		hanger = hangers[jobName];
		if(!hanger) return;
		param = this.getParam(jobName);
		return hanger.call(this, param);
	},

	getParam : function(operName){
		return this.xe_options[operName];
	},	
	
	load : function(){
		this.masking();
	},
	
	getNextElement : function(nextEl){
		if(!nextEl) return null;
		nextEl = this.form[nextEl];
		if(nextEl.length > 0){
			var i = 0,
				len = nextEl.length,
				el, nextIndex;
			for(; i<len;i++){
				el = nextEl[i];
				if(el !== this) continue;
				nextIndex = i+1;
				nextEl = nextEl[nextIndex] ? nextEl[nextIndex] : null;
				break;
			}
		}
		
		return nextEl;
	},
	
	getNextTextElement : function(){
		var nextEl;
		
		if(allElements[this.xe_index+1]) nextEl = allElements[this.xe_index+1];
		while( nextEl && (nextEl.type != "text" && nextEl.type != "textarea" && nextEl.type != "password")){
			nextEl = allElements[nextEl.xe_index+1];
		}
		return (!nextEl) ? false : nextEl;
	}
	
}

/* Initializer */
XE.oop.declareClass("", "Initializer", null, {
	create : function(el){
		this.el = el;
		this.optionList = this.getOptionList();
	},
	
	getOptionList : function(){
		var options = this.el.getAttribute("validate");
		
		if(options === null) return false;
		options = options.replace(/\s/g, "").split(";");
		var lastIndex = options.length-1;
		if(options[lastIndex] == "") options.splice(lastIndex, 1);
	
		return options;		
	},
	
	init : function(){
		// abstract method
	}
});

/* FormInitializer */
XE.oop.declareClass("", "FormInitializer", Initializer, {
	/*
	* 
	* form.xe_options // validate attribute에 선언된 속성과 값들
	* form.hangers    // el.xe_options 의 이름들에 따라 매칭되는 오퍼레이션들
	* */	
	init : function(index){
		var form = this.el;
		XE.extend(form, FormUtility);
		
		if(this.optionList) {
			var optionExtractor = new OptionExtractor(this.optionList);
			form.xe_options = optionExtractor.extract();
			form.hangers    = CabinetRoom.getHangers(form.xe_options, TYPE_FORM);
		}
	}	
})

/* ElementInitializer */
XE.oop.declareClass("", "ElementInitializer", Initializer, {
	/*
	 * 
	 * el.xe_options // validate attribute에 선언된 속성과 값들
	 * el.hangers    // el.xe_options 의 이름들에 따라 매칭되는 오퍼레이션들
	 * el.validators // 검사할 항목들
	 * el.masker     // masker
	 * */	
	init : function(){
		if(!this.optionList) return;
		
		var el = this.el;
		XE.extend(el, ElementUtility);	
		
		var optionExtractor = new OptionExtractor(this.optionList);
		
		el.xe_options = optionExtractor.extract();
		el.hangers    = CabinetRoom.getHangers(el.xe_options);
		el.validators = [];
		el.masker     = null;
		el.xe_index   = allElements.length;
		allElements.push(this.el);
		var binder = new ElementOptionBinder(el);
		
		binder.bind();
		el.load();

	}

})

/* CabinetRoom */
var CabinetRoom = {
		// items
		cabinets : {},
		
		add : function(cabinet){
			var type = cabinet.type;
			if(!type) return false;
			this.cabinets[type] = cabinet;
		},
		
		bindOptionsToHangers : function(hangers, options, cabinet){
			var cabinetType = cabinet.getType(),
				optionName, hanger;
			
			for(optionName in options){
				hanger = cabinet.pick(optionName);
				if(!hanger) continue;
				if(!hangers[cabinetType]) hangers[cabinetType] = {};
				hangers[cabinetType][optionName] = hanger;
					
			}
		},
		
		getHangers : function(options, cabinetType){
			var cabinet, hangers = {};

			if(!cabinetType){
				for(cabinetType in this.cabinets){
					cabinet = this.cabinets[cabinetType];
					this.bindOptionsToHangers(hangers, options, cabinet);
				}
			} else {
				cabinet = this.cabinets[cabinetType];
				this.bindOptionsToHangers(hangers, options, cabinet);
			}
			return hangers;
		}
	}

/* Cabinet */
function Cabinet(type){
	this.type   = type;
	this.hanger = {};
}

Cabinet.prototype = {
	hangUp : function(key, hanger){
		if(typeof key !== 'string' && typeof hanger !== 'function') return;
		this.hanger[key] = hanger;
	},
	
	hangUpAll : function(hangerGroup){
		XE.extend(this.hanger, hangerGroup);
	},

	pick : function(key){
		return this.hanger[key];
	},

	getType : function(){
		return this.type;
	}
}


/* Extractor */		
function OptionExtractor(options){
	this.xe_options = options;
}
OptionExtractor.prototype = {
	extract : function(){
		if(!this.xe_options) return false;
		var option = {},
			op, peace,
			i = 0,
			len = this.xe_options.length;
		
		for(; i<len; i++){
			op = this.xe_options[i];
			peace = op.split(":");
			option[peace[0]] = (peace[1] === undefined) ? null : peace[1];
			//console.log("(OptionExtractor) " + peace[0] + " : " + option[peace[0]]);
		}
		
		return option;
	}
}

/* Binder */
XE.oop.declareClass("", "OptionBinder", null, {
	create : function(el){
		this.el = el;
	},
	
	bind : function(){
		//abstract method
	}
});

/* FormBinder */
XE.oop.declareClass("", "FormOptionBinder", OptionBinder, {
	bind : function(){

	}
});

/* ElementBinder */
XE.oop.declareClass("", "ElementOptionBinder", OptionBinder, {
	bind : function(){
		this.callJobsProcess(TYPE_UI);
		this.bindValidators();
		this.bindEvent();
	},
	
	bindEvent : function(){
		var eventNames = ElementEventNames.concat();
		var job = this.el.getJob(TYPE_ACTION, EXCLUDE_EVENT),
			excludeEventNames = job ? job.process() : null,
			i, len = eventNames.length,
			j, jLen = excludeEventNames ? excludeEventNames.length : 0,
			eventName, excludeEventName;
		
		for (i=0;i<len && jLen>0;i++){
			eventName = eventNames[i];
			for(j=0;j<jLen;j++){
				excludeEventName = excludeEventNames[j];
				if(eventName == excludeEventName){
					eventNames.splice(i, 1);
					excludeEventNames.splice(j, 1);
					len -= 1;
					jLen -= 1;
					i--;
					break;
				}
			}
		}
		
		for(i=0, len =eventNames.length; i<len; i++ ){
			eventName = eventNames[i];
			this["bind_" + eventName]();
		}
	},
	
	callJobsProcess : function(type){
		var jobs = this.el.getJobs(type),
			i = 0, len = jobs.length, job;
		
		for(;i<len;i++){
			job = jobs[i];
			job.process();
		}
	},		
	
	bindValidators : function(){
		this.el.validators = this.el.getJobs(TYPE_VALIDATOR);
	},	
	
	bind_keypress : function(){
		var KEYPRESS    = "keypress",
			filterJobs  = this.el.getJobs(TYPE_FILTER),
			keypressJobs= this.el.getJobs(TYPE_ACTION, KEYPRESS),
			filterLen   = filterJobs.length,
			filterJob   = filterLen > 0 ? filterJobs[filterLen-1] : null;
		
		this.el.bind(KEYPRESS, function(e){
			if(filterJob) filterJob.process(e);
			for(var i = 0,len = keypressJobs.length;i<len;i++) keypressJobs[i].process(e);
		});
	},
	
	bind_blur : function(){
		var BLUR       = "blur",
			maskJobs   = this.el.getJobs(TYPE_MASKER),
			stripJobs  = this.el.getJobs(TYPE_STRIPPER),
			validJobs  = this.el.getJobs(TYPE_ACTION, VALIDATE_PREFIX),
			maskLen    = maskJobs.length,
			stripLen   = stripJobs.length,
			validLen   = validJobs.length,			
			maskJob    = maskLen > 0 ? maskJobs[maskLen-1] : null;

		this.el.masker = maskJob;
		this.el.bind(BLUR, function(e){
			var i;
			if(maskJob) maskJob.process(e);
			for(i = 0; i<stripLen; i++){
				stripJobs[i].process(e);
			}
			if(validLen <= 0) return;
			if(this.validateForm())
				for(i = 0;i<validLen;i++) validJobs[i].restore(e);
			else 
				this.focus();
		});
	},
	
	bind_focus : function(){
		var FOCUS    = "focus",
			jobs     = this.el.getJobs(TYPE_MASKER),
			jobLen   = jobs.length;
		
		this.el.bind(FOCUS, function(e){
			for(var i =0;i<jobLen;i++){
				jobs[i].restore();
				break;
			}			
		});
	},
	
	bind_keyup : function(){
		var KEYUP  = "keyup",
			jobs   = this.el.getJobs(TYPE_ACTION, KEYUP),
			jobLen = jobs.length;
		
		if(jobLen <= 0) return;
		this.el.bind(KEYUP, function(e){
			for(var i =0;i<jobLen;i++) jobs[i].process(e);
		});
	},
	
	bind_keydown : function(){
		var KEYDOWN = "keydown",
			jobs    = this.el.getJobs(TYPE_ACTION, KEYDOWN, "enter"),
			jobLen  = jobs.length;
		
		if(jobLen <= 0) return;
		this.el.bind(KEYDOWN, function(e){
			for(var i =0;i<jobLen;i++) jobs[i].process(e);
		});
	},
	
	bind_change : function(){},
	bind_click : function(){},
	bind_mouseover : function(){},
	bind_mouseout : function(){}
});

/* Job */
XE.oop.declareClass("Worker", "Job", null, {
	create : function(job, restoreJob){
		this.job = job;
		this.restoreJob = restoreJob;	
	},
	process : function(e){
		if(!this.checkJob()) return;
		return this.job(e);
	},
	restore : function(e){
		if(!this.checkRestoreJob()) return;
		return this.restoreJob(e);
	},
	
	setJob : function(job){
		this.job = job;
	},
	
	setRestoreJob : function(restoreJob){
		this.restoreJob = restoreJob;
	},
	
	checkJob : function(){
		return (this.job && typeof this.job === 'function');
	},
	
	checkRestoreJob : function(){
		return (this.restoreJob && typeof this.restoreJob === 'function');
	}
});

/* Filter Job */
XE.oop.declareClass("Worker", "Filter", Worker.Job, {
	create : function(filter, job, restoreJob){
		filter = filter.replace(/^\//, "").replace(/\/$/, "");
		this.filter = new RegExp(filter);
		this.job = job ? job : this.defaultJob;	
		this.restoreJob = restoreJob;
	},
	
	defaultJob : function(e){
		e = e || window.event;
		var code = e.charCode || e.keyCode,
			sKey = String.fromCharCode(code);

		if(e.charCode == 0) return;
		if (sKey != "\r" && !this.filter.test(sKey)) XE.event.preventDefault(e);			
	}
});

/* Validator Job */
XE.oop.declareClass("Worker", "Validator", Worker.Job, {
	getFailedMsg : function(){
		return this.failedMsg;
	},
	setFailedMsg : function(msg){
		this.failedMsg = msg;
	}
});

/* Masker Job */
XE.oop.declareClass("Worker", "Masker", Worker.Job, {
	create : function(target, mask, job, restoreJob){
		this.target   = target;
		this.mask     = mask;
		this.job      = job ? job : this.defaultJob;
		this.restoreJob = restoreJob ? restoreJob : this.defaultRestoreJob;	
	},
	
	process : function(){
		if(this.target.value === "" || !this.checkJob()) return;
		return this.job();
	},
	
	restore : function(){
		if(this.target.value === "" || !this.checkRestoreJob()) return;
		return this.restoreJob();
	},
	
	defaultJob : function(){
		if(!this.mask) return;
		var target = this.target;
		var sStr = target.value.replace( /(\/|\$|\^|\*|\(|\)|\+|\.|\?|\\|\{|\}|\||\[|\]|-|:)/g,""),
			tStr = "",
			tLen = sStr.length +1 ,
			jdx  = 0,
			idx;
		
		for(var idx=0, len = sStr.length; idx<len ; idx++){
			tStr += sStr.charAt(idx);
			jdx++;
		//  alert(tStr + "[" + sStr.charAt(idx) + "]" +jdx+"{" + this.mask.charAt(jdx) + "}" + "[" + this.mask.charAt((jdx+1))+"]"+ this.mask.length);
		//  if (jdx < this.mask.length && this.mask.charAt(jdx)!="9" && this.mask.charAt(jdx)!="0") tStr += this.mask.charAt((jdx++));
			if (jdx < this.mask.length && this.mask.charAt(jdx)!="9" ) tStr += this.mask.charAt((jdx++));
		
		}
		target.value = tStr;		
	},
	
	defaultRestoreJob : function(){
		var target = this.target;
		target.value = target.value.replace(/(\,|\.|\-|\/)/g,"");
	}
});

/* Stripper Job */
XE.oop.declareClass("Worker", "Stripper", Worker.Job, {
	create : function(target, strip, job, restoreJob){
		if(typeof strip === 'string'){
			flag =  strip.match(/[gim]+$/);
			strip = new RegExp(strip.replace(/^\//, "").replace(/\/[gim]*$/, ""), flag);
		}
		this.target = target;
		this.strip = strip;
		this.job = job ? job : this.defaultJob;
		this.restoreJob = restoreJob;
	},	
	process : function(){
		var target = this.target;
		if(target.value === "" || !this.checkJob()) return;
		return this.job();
	},
	defaultJob : function(){
		this.target.value = this.target.value.replace(this.strip, "");
	}
});

var UiCabinet         = new Cabinet(TYPE_UI),
	FilterCabinet     = new Cabinet(TYPE_FILTER),
	ValidatorCabinet  = new Cabinet(TYPE_VALIDATOR),
	StripperCabinet   = new Cabinet(TYPE_STRIPPER),
	MaskerCabinet     = new Cabinet(TYPE_MASKER),
	FormCabinet       = new Cabinet(TYPE_FORM),
	ActionCabinet     = new Cabinet(TYPE_ACTION);

CabinetRoom.add(UiCabinet);
CabinetRoom.add(FilterCabinet);
CabinetRoom.add(StripperCabinet);
CabinetRoom.add(MaskerCabinet);
CabinetRoom.add(ValidatorCabinet);
CabinetRoom.add(FormCabinet);
CabinetRoom.add(ActionCabinet);

/* UiHangers */
var UiHangers = {
	"required" : function(){
		var self = this;
		return new Worker.Job(function(){
			self.addClass("xe_required");
		})
	},
	
	"right" : function(){
		var self = this;
		return new Worker.Job(function(){
			self.addClass("xe_right");
		})		
	},
	
	"ime" : function(val){
		if(!val) return;
		var self = this;
		return new Worker.Job(function(){
	        switch(val) {
	            case "kor" :
	            	self.addClass("xe_imeKor");
	                break;
	            case "eng" :
	            	self.addClass("xe_imeEng");
	                break;
	            case "engOnly" :
	            	self.addClass("xe_imeDis");
	                break;
	        }
		})				

	}
}

UiCabinet.hangUpAll(UiHangers);

/* filterHangers */
var filterHangers = {
	"filter" : function(filter){
		if(!filter) return;
		return new Worker.Filter(filter);
	}, 
 	
	"credit_card" : function(){
		return new Worker.Filter(FILTER_NUMERIC);
	}, 
	
	"numeric" : function(){
		return new Worker.Filter(FILTER_NUMERIC);
	}, 
	
	"money" : function(){
		return new Worker.Filter(FILTER_NUMERIC);
	}, 
	
	"dollar" : function(){
		return new Worker.Filter(FILTER_NUMERIC);
	}, 
	
	"psn" : function(){
		return new Worker.Filter(FILTER_NUMERIC);
	}, 
	
	"csn" : function(){
		return new Worker.Filter(FILTER_NUMERIC);
	}, 
	
	"integer" : function(){
		return new Worker.Filter(FILTER_INTEGER);
	}, 
	
	"float" : function(){
		return new Worker.Filter(FILTER_FLOAT);
	}, 
	
	"hexa" : function(){
		return new Worker.Filter(FILTER_HEXA);		
	}, 
	
	"alphabetic" : function(){
		return new Worker.Filter(FILTER_ALPHABETIC);
	}, 
	"hangul" : function(){
		return new Worker.Filter(FILTER_HANGUL);
	}, 
	
	
	"alpha_numeric" : function(){
		return new Worker.Filter(FILTER_ALPHA_NUMERIC);
	}, 
	
	"date" : function(){
		return new Worker.Filter(FILTER_DATE);
	}
}

FilterCabinet.hangUpAll(filterHangers);

/* maskerHangers */
var maskerHangers = {
	"mask" : function(mask){
		if(!mask) return;
		return new Worker.Masker(this, mask);
	},
	
	"money" : function(){
		var self = this;
		
		if(self.value.length == 0){
			self.value = 0;
		}
		
		return new Worker.Masker(this, null, function(){
			var sMoney = self.value.replace(/,/g,""),
				tMoney = "",
			 	tLen   = sMoney.length,
				i, j= 0;

			if (tLen <= 3 ) return ;
			
			for(i=0;i<tLen;i++){
				if (i!=0 && ( i % 3 == tLen % 3) ) tMoney += ",";
				if(i < tLen ) tMoney += sMoney.charAt(i);
			}
			
			self.value = tMoney;
		});
	},
	
	"dollar" : function(){
		var self = this;
		return new Worker.Masker(this, null, function(){
			var target = this.target;
			var sMoney = self.value.replace(/(\,|\.)/g,"");
			
			if ( sMoney.length <= 2 ) return sMoney;
			
			var fir_sMoney = sMoney.substr(0, sMoney.length - 2),
				sec_sMoney = sMoney.substr(sMoney.length - 2, 2);
			
			var tMoney = "",
				tLen =fir_sMoney.length,
				i, j=0;
			
			if (fir_sMoney.length <= 3 ) return fir_sMoney + "." + sec_sMoney;
			
			for(i=0;i<tLen;i++){
				if (i!=0 && ( i % 3 == tLen % 3) )     tMoney += ",";
				if(i < fir_sMoney.length ) tMoney += fir_sMoney.charAt(i);
			}
			
			self.value = tMoney + "." + sec_sMoney;		
		})
	},
	
	"date" : function(maskDate){
		return new Worker.Masker(this, !maskDate ? MASK_DATE : maskDate);
	},
	
	"psn" : function(maskPsn){
		return new Worker.Masker(this, !maskPsn ? MASK_PSN : maskPsn);
		
	}, 
	
	"csn" : function(maskCsn){
		return new Worker.Masker(this, !maskCsn ? MASK_CSN : maskCsn);
	}
}

MaskerCabinet.hangUpAll(maskerHangers);

/* stripperHangers */
var stripperHangers = {
	"strip" : function(strip){
		if(!strip) return;
		return new Worker.Stripper(this, strip);
	},
	"trim" : function(){
		return new Worker.Stripper(this, null, function(){
			this.target.value = this.target.value.trim();
		});
	},
	"strip_sc" : function(){
		return new Worker.Stripper(this, STRIP_SPECIAL_CHAR);
	},
	
	"strip_ws" : function(){
		return new Worker.Stripper(this, STRIP_WHITE_SPACE);
	}
}

StripperCabinet.hangUpAll(stripperHangers);

/* validatorHangers */
var validatorHangers = {
	"required" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "필요한 항목이 입력되지 않았습니다.";
		
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value   = self.value.replace( /(\s)/g,"");
				
			if(self.disabled || self.getAttribute("disabled") == "disabled") return true;
			if(self.disabled || self.getAttribute("disabled") == true) return true;
			
			if(	
				!value || value.length == 0 ||
				(self.type == "checkbox" && !self.checked) || 
				(self.type == "select-one" && self.selectedIndex<=0 )
			) {
				return false;
			}
			
			return true;
		});
		
		return validator;
	},
	
	"related-required" : function(fieldName){
		if(!fieldName) return;
		var validator = new Worker.Validator(),
			self      = this;
		
		validator.setJob(function(){
			var value = self.value,	
				el    = eval(self.form[fieldName]),
				targetLabel;
		
			if (!el || (!el.value.isEmpty() && !value.isEmpty()) ) {
				return true;
			}
			
			targetLabel = (el.xe_options) ? el.xe_options["label"] : el.name;
			targetLabel = targetLabel ? targetLabel : el.name;
			
			var msg = "";
			if(el.value.isEmpty() && !value.isEmpty()){
				msg = "[" + targetLabel + "] 항목이 입력 되어야합니다.";
			} 
			else if(!el.value.isEmpty() && value.isEmpty()){
				msg = "필요한 항목이 입력되지 않았습니다.";
			} else {
				return true;
			}
			
			validator.setFailedMsg(self.createFailedMsg(msg));
			
			return false;
		});
		
		return validator;
	},
	
	"minlength" : function(minlength){
		if(!minlength) return;
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "입력된 항목의 자릿수가 너무 작습니다. \n최소 " + minlength + "자리이상 입력하세요.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value; 
			if(!value) return true;
			if (value.length <  parseInt(minlength)) return false;			
			return true;			
		});
		
		return validator;				
	},
	
	"minvalue" : function(minvalue){
		if(!minvalue) return;
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "";
			
		msg += "입력된 항목의 값이 너무 작습니다. ";
		msg += "\n최소 " + minvalue + " 이상의 값을 입력하세요.";
		
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;			
			if (!value) return true;
			
			value = parseFloat(value);
			
			if (value <  parseFloat(minvalue)) return false;
			return true;			
		});
		
		return validator;			
	},
	
	"maxvalue" : function(maxvalue){
		if(!maxvalue) return;
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "";
		
		msg += "입력된 항목의 값이 너무 큽니다. ";
		msg += "\n최대 " + maxvalue + " 이하의 값을 입력하세요.";		
		
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true; //입력값 없는 경우는 Pass
			
			value = parseFloat(value);
			
			if (value > parseFloat(maxvalue)) return false;
			return true;
		});
		
		return validator;			
	},
	
	"minbyte" : function(minbyte){
		if(!minbyte) return;
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "";
		
		msg = "입력된 항목의 자릿수가 너무 작습니다. \n최소 " + minbyte + "자리이상입니다.";
		msg += "\n (한글 한글자를 2자리로 계산)";		
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value,
				byte;
			if (!value) return true;
			
			byte = getByte(value);
			
			if (byte < minbyte) return false;
			return true;
		});
		
		return validator;			
	},
	
	"maxbyte" : function(maxbyte){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "";
			
		msg += "입력된 항목의 자릿수가 너무 큽니다. \n최대 " + maxbyte + "자리이하 입니다.";
		msg += "\n (한글 한글자를 2자리로 계산)";	
				
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value,
				byte;
			if (!value) return true;
			
			byte = getByte(value);
			
			if (byte >  maxbyte ) return false;
			return true;
		});
		
		return validator;			
	},
	
	"minbt" : function(minbyte){
		if(!minbyte) return;
		var validator = new Worker.Validator(),
		self      = this,
		msg       = "";
		
		msg = "입력된 항목의 자릿수가 너무 작습니다. \n최소 " + minbyte + "자리이상입니다.";
		msg += "\n (한글 한글자를 3자리로 계산)";		
		
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value,
			byte;
			if (!value) return true;
			
			byte = getByte3(value);
			
			if (byte < minbyte) return false;
			return true;
		});
		
		return validator;			
	},
	
	"maxbt" : function(maxbyte){
		var validator = new Worker.Validator(),
		self      = this,
		msg       = "";
		
		msg += "입력된 항목의 자릿수가 너무 큽니다. \n최대 " + maxbyte + "자리이하 입니다.";
		msg += "\n (한글 한글자를 3자리로 계산)";	
		
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value,
			byte;
			if (!value) return true;
			
			byte = getByte3(value);
			
			if (byte >  maxbyte ) return false;
			return true;
		});
		
		return validator;			
	},
	
	"numeric" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "숫자 형식이 아닙니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true; 

			var regExp_numeric = /^[0-9]+$/;
			if(!regExp_numeric.test(value)) return false;
			return true;			
		});
		
		return validator;			
	},
	
	"alphabetic" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "알파벳 형식이 아닙니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true;
			
			var regExp_alphabetic = /^[a-zA-Z]+$/;
			if(!regExp_alphabetic.test(value)) return false;
			return true;			
		});
		
		return validator;			
	}, 
	
	"hangul" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "한글형식이 아닙니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true;
			
			var regExp_hangul = /^[ㄱ-ㅎㅏ-ㅣ가-힣]+$/;
			if(!regExp_hangul.test(value)) return false;
			return true;			
		});
		
		return validator;			
	}, 
	
	"alpha_numeric" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "알파벳 혹은 숫자 형식이 아닙니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true;
			
			var regExp_alpha_numeric = /^[a-zA-Z0-9]+$/;
			if(!regExp_alpha_numeric.test(value)) return false;
			return true;				
		});
		
		return validator;				
	}, 
	
	"credit_card" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "Credit Card 번호 형식이 아닙니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true; 
			
			var sum = 0,
				mul = 1,
			 	len = value.length;
			
			if (len.length > 19) return false;
			
			for (var idx = 0; idx < len; idx++) {
				var digit = value.substring(len-idx-1,len-idx),
				    tproduct = parseInt(digit ,10)*mul;
				
				if (tproduct >= 10)
				  sum += (tproduct % 10) + 1;
				else
				  sum += tproduct;
				
				if (mul == 1)
				  mul++;
				else
				  mul--;
			}
			
			if ((sum % 10) != 0) return false;
			return true;
		});
		return validator;			
	},
	
	"reg_exp" : function(reg){
		if(!reg) return;
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "지정된 정규식과 매칭되지 않습니다. \n형식이 " + reg + "에 적합해야 합니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true;
			var regularExpression = new RegExp(reg);
			if(!regularExpression.test(value)) return false;
			return true;				
		});
		
		return validator;			
	},
	
	"date" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "날짜입력이 잘못되었습니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true;
			var iYear  = null,
				iMonth = null,
				iDay   = null,
				iDaysInMonth = null;
			
			var sDate       = value.replace(/(\,|\.|\-|\/)/g,""),
				sFormat     = "YYYYMMDD",  //아직까지 YYYYMMDD의 형태만 지원한다.
				aDaysInMonth= new Array(31,28,31,30,31,30,31,31,30,31,30,31);
			
			//완전한 날짜의 입력이 들어온 경우이다.
			if ( sDate.length != 8 ) return false;
			
			iYear  = sDate.substr(0,4);
			iMonth = sDate.substr(4,2);
			iDay   = sDate.substr(6,2);
			if (isNaN(iYear) || isNaN(iMonth) || isNaN(iDay) ) return false;
			
			iDaysInMonth = (iMonth != 2) ? aDaysInMonth[iMonth-1] : (( iYear%4 == 0 && iYear%100 != 0 || iYear % 400==0 ) ? 29 : 28 );
			if( iMonth > 12 || iMonth < 1 || iDay < 1 || iDay > iDaysInMonth ) return false;
			 

			return true;				
		});
		
		return validator;			
	},
	
	"email" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "이메일 형식이 아닙니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true;
			
			var regExpEmail = /^\w+((-|\.)\w+)*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]{2,4}$/;
			if(!regExpEmail.test(value)) return false;

  			return true;				
		});
		
		return validator;				
	},
	
	"domain" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "도메인 형식이 잘못되었습니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true;
			
			var regExpDomain = /^[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]{2,4}$/;
			if(!regExpDomain.test(value)) return false;

  			return true;				
		});
		
		return validator;					
	},
	
	"integer" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "정수형식이 아닙니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true;
			var regExp_Integer = /^(\+|\-|\d*)\d+$/;
			
			if (!regExp_Integer.test(value)) return false;
			return true;				
		});
		
		return validator;		
	}, 
	
	"float" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "실수형식이 아닙니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true;

			var regExp_Float1 = /^(\+|\-|\d*)\d+$/;
			var regExp_Float2 = /^(\-|\+|\d*)\d+(\.|\d)\d*$/;
			
			if(!regExp_Float1.test(value) && !regExp_Float2.test(value)) return false;
			return true;				
		});
		
		return validator;		
	}, 
	
	"hexa" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "16진수(Hexa)형식이 아닙니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true;
			var regExp_hexa = /^[a-fA-F0-9]+$/;
			if(!regExp_hexa.test(value)) return false;
			return true;				
		});
		
		return validator;			
	}, 

	"psn" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "입력한 주민등록번호가 잘못되었습니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true;
			
			var sum      = 0, idx, jdx,
				psNumber = value.replace(/(\,|\.|\-|\/)/g,"");
			
			for (idx = 0, jdx=2; jdx < 10; idx++, jdx++) {
				sum = sum + ( psNumber.charAt(idx) * jdx );
			}
			
			for (idx = 8, jdx=2; jdx < 6; idx++, jdx++) {
				sum = sum + ( psNumber.charAt(idx) * jdx );
			}
			
			var nam = sum % 11,
				checkDigit = 11 - nam ;
			
			checkDigit = (checkDigit >= 10 ) ? checkDigit-10:checkDigit;
			
			if ( !isNum(psNumber) || psNumber.charAt(12) != checkDigit) return false;
			return true;				
		});
		
		return validator;			
	}, 
	
	"csn" : function(){
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "입력한 사업자 등록번호가 잘못되었습니다.";
			
		validator.setFailedMsg(self.createFailedMsg(msg));
		validator.setJob(function(){
			var value = self.value;
			if (!value) return true;
			
			var sum        = 0,
			    csNumber   = value.replace(/(\,|\.|\-|\/)/g,""),
			    checkArray = new Array(1,3,7,1,3,7,1,3,5),
				idx;
			
			for(idx=0 ; idx < 9 ; idx++)
				sum += csNumber.charAt(idx) * checkArray[idx];
				
			sum = sum + ((csNumber.charAt(8) * 5 ) / 10);
			var nam = Math.floor(sum) % 10,
				checkDigit = ( nam == 0 ) ? 0 : 10 - nam;
			
			if ( isNaN(csNumber) || csNumber.charAt(9) != checkDigit)  return false;

			return true;				
		});
		
		return validator;	
	},
	
	"equals" : function(field){
		if(!field) return;
		var validator = new Worker.Validator(),
			self      = this,
			msg       = "";
			
		validator.setJob(function(){
			var value = self.value,	
				el, targetLabel;
			if (!value) return true;
			
			el = eval(self.form[field]);
			
			if (el === undefined) {
				msg = self.name + "에서의 equals 해당하는 입력필드가 존재하지 않습니다.";
				validator.setFailedMsg(self.createFailedMsg(msg));
				return false;
			}
			
			targetLabel = el.xe_options["label"];
			targetLabel = targetLabel ? targetLabel : el.name;
			
			if ( value != el.value ) {
				msg = "입력한 값이 [" + targetLabel + "] 항목의 값과 같지 않습니다.";
				validator.setFailedMsg(self.createFailedMsg(msg));
				return false;
			}

			return true;				
		});
		
		return validator;			
	}
}

ValidatorCabinet.hangUpAll(validatorHangers);

/* formHangers */
var formHangers = {
	"confirm" : function(comfirmMsg){
		if(!comfirmMsg) return;
		return new Worker.Job(function(){
			if(!confirm(comfirmMsg)) return false;
		});
	},
	
	"remove_tag" : function(){
		var self = this;
		return new Worker.Job(function(){
			var elems = self.elements,
				i = 0, len = elems.length,
				elem, val;
			
			for(;i<len;i++){
				elem = elems[i];
				if(elem.type != "text" && elem.type != "textarea" && elem.type != "password") continue;
				if((val = elem.value.trim()) === "" && val.length == 0) continue;
				
				val = val.replace(STRIP_ATTRIBUTE, "");
				val = val.replace(STRIP_TAG, "");
				elem.value = val;
			}
			
		});
	}
}

FormCabinet.hangUpAll(formHangers);

/* action Hangers */
var actionHangers = {
	"validate-error" : function(){
		var self = this,
			job  = new Worker.Job(),
			errorClass = "xe_error";
		
		job.setJob(function(){
			self.addClass(errorClass);
		});
		
		job.setRestoreJob(function(){
			var oldClass = self.className;
			if(oldClass.indexOf(errorClass) == -1) return;
			self.className = oldClass.replace(errorClass, "").trim();
		});
		
		return job;		
	},
	
	"keyup-next" : function(nextEl){
		var self = this,
			maxLen = this.getAttribute("maxlength");
		
		if(maxLen === null) {
			alert(this.name + "의 keyup-next기능을 사용하기 위해서는 maxlength속성이 필요합니다.");
			return;
		}
		
		nextEl = this.getNextElement(nextEl);
		
		return new Worker.Job(function(){
			var valLen = self.value.length;
			if(valLen < maxLen) return;
			if(!nextEl)	nextEl = self.getNextTextElement();
			if(nextEl && valLen >= maxLen) nextEl.focus();
		});
	},
	
	"enter-next" : function(nextEl) {
		var self = this;
		
		nextEl = this.getNextElement(nextEl);
		
		return new Worker.Job(function(e){
			if(!isEnter(e)) return;
			if(!nextEl)	nextEl = self.getNextTextElement();
			if(nextEl) nextEl.focus();
		});				
	},	
	
	"keydown-enter" : function(methodName){
		if(!methodName) return;
		
		return new Worker.Job(function(e){
			if(!isEnter(e)) return;
			try {
				var method = eval("window." + methodName);
				if(typeof method === "function") method();
			} catch(e){
				alert(e);
			}
		});
	},
	
	"exclude-event" : function(eventNames){
		if(!eventNames) return;

		return new Worker.Job(function(){
			eventNames = eventNames.replace(/\s/g, "").split(",");
			var len = eventNames.length,
				lastName = eventNames[len-1];
			if(lastName === "" || lastName.length <= 0) eventNames.splice(len-1, 1);
			return eventNames;
		});
	}
	
}

ActionCabinet.hangUpAll(actionHangers);

//debug
function logObject(obj){
	for(var i in obj){
		if(typeof obj[i] === 'object'){
			for(var j in obj[i]){
				console.log(i + " , " + j + " : " + obj[i][j]);
			}
			continue;
		}
		console.log(i + " : " + obj[i]);
	}
}

if(!window.console){
	window.console = {
		log : function(){}
	}
}

function getByte(str){
	var retCode = 0;
	var strLength = 0;
	for (i = 0, len = str.length; i < len; i++){
		var code = str.charCodeAt(i)
		var ch = str.substr(i,1).toUpperCase()
		code = parseInt(code)
		if ((ch < "0" || ch > "9") && (ch < "A" || ch > "Z") && ((code > 255) || (code < 0)))
			strLength = strLength + 2;
		else
			strLength = strLength + 1;
	}
	return strLength;
}

function getByte3(str){
	var retCode = 0;
	var strLength = 0;
	for (i = 0, len = str.length; i < len; i++){
		var code = str.charCodeAt(i)
		var ch = str.substr(i,1).toUpperCase()
		code = parseInt(code)
		if ((ch < "0" || ch > "9") && (ch < "A" || ch > "Z") && ((code > 255) || (code < 0)))
			strLength = strLength + 3;
		else
			strLength = strLength + 1;
	}
	return strLength;
}

function isEnter(e){
	e = e || window.event;
	if(e.keyCode == 13) return true;
	else return false;
}

XE.event.addEvent(window, "load", function(){
	window.setupValidateForms(document.forms);
	
	if(typeof window.afterLoadValidateForm === 'function') window.afterLoadValidateForm();
});

XE.event.addEvent(window, "unload", function(){
// IE 메모리 누수 방지를 위한 등록된 이벤트 핸들러 제거
// {obj : this, name : type, callback : handler}
	if(document.addEventListener) return;
	for(var i = 0,	len = DocHandlers.length;i<len;i++){
		var h = DocHandlers[i];
		h.obj.detachEvent("on" + h.name, h.callback);
	}
});

window.setupValidateForms = function(forms){
	var formLen = forms.length,
		i; 
	
	for(i=0; i<formLen; i++){
		form = forms[i];
		window.setupValidateForm(form);
	}
}

window.setupValidateForm = function(form){
	var formItems, element, eInitializer, j, len;
	var fInitializer, eInitializer;
	
	formItems = form.elements;
	fInitializer = new FormInitializer(form);
	fInitializer.init();
	
	for(j=0,eLen = formItems.length; j<eLen; j++){
		element = formItems[j];
		eInitializer = new ElementInitializer(element);
		eInitializer.init();
	}	
}

})(window, XE);
