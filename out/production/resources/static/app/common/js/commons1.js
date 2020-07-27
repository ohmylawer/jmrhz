/*
 js验证的时候用
 * */
var css={
		filter_html:function(html){
			/*1、本方法的功能：去除HTML标签、空格（&nbsp;）、换行（\n）*/
			var regex=/<[^>]+>/g;
			var txt=html.replace(regex,'');
			txt=txt.replace(/&nbsp;/g,'');
			txt=txt.replace(/\n/g,'');
			return txt;
		},
		limit_rich_text:function(){
			/*1、本方法的功能：限制富文本框的最大字符长度（去除掉：HTML标签、空格（&nbsp;）、换行（\n））*/
			var _obj=css;
			$('textarea.limit').each(function(){
				var $this=$(this);
				var id=$this.attr('id');
				var editor=CKEDITOR.instances[id];
				editor.on('change',function(event){
					var data_html=editor.getData();
					var txt=_obj.filter_html(data_html);
					var len=txt.length;
					$this.val(txt).blur();
				});
				
				var cancel=setInterval(function(){
					var $cke=$('#cke_'+id);
					if($cke.size()>0){
						clearInterval(cancel);
						$('#cke_'+id).after($this);
					}
				},300);
			});
		},
		get_checkbox_val:function(name,split){
			/*获取指定name的已被选择的复选框的所有值*/
			var vals='';
			split=split||',';
			$('input[name='+name+']:checked').each(function(){
				var $this=$(this);
				vals+=split+$this.val();
			});
			return vals.substring(split.length);
		},
		get_query_val:function(name){
			/*1、获取当前url中，指定参数的值*/
			var reg=new RegExp('(^|&)'+name+'=([^&]*)(&|$)');//构建一个含有目标参数的正则表达式对象
			var r=window.location.search.substr(1).match(reg);//匹配目标参数
			if(!!r){
				return unescape(r[2]);
			}else{
				return '';
			}
			
		},
		cut:function(selector){
			val=$(selector).val();
			val=val||'';
			$(selector).val(val.substring(0,10));
		}
};

jQuery(document).ready(function() {
    Metronic.init(); // init metronic core componets
    Layout.init(); // init layout
    QuickSidebar.init(); // init quick sidebar
    Demo.init(); // init demo features
    initcomonother();
    initinclude();
    
    var pagedate = new Date();
	var month = pagedate.getMonth()+1;
	if(month<10){
		month = "0"+month;
	}
	var day = pagedate.getDate();
	if(day<10){
		day = "0"+day;
	}
	var year = pagedate.getFullYear();
	$(".datee").val(year+"-"+month+"-"+day);
});    

/*start设置bootstrap滚动条自适应浏览器高度*/
var c2 = {};
$(window).resize(function(){
	clearTimeout(c2);
	c2 = setTimeout(function(){
		/*
        <div style="height:100%;">高度通过此处来设置,scroller外面需要包一层容器
        	<div  style="height:100%;" class="scroller">
        	</div>
        </div>
		 */
		$(".slimScrollDiv,.scroller").each(function(){
			$(this).css({"height":"100%"});
		});
	},500)
});
/*end*/


    
function setformdata(data){
	for(key in data){
		if($("[name="+key+"]").attr("type")=="checkbox"){
			var checkbox = document.getElementsByName(key);
			for(var j = 0;j<checkbox.length;j++){
				if(checkbox[j].value==data[key]){
					checkbox[j].checked=true;
					$(checkbox[j]).parent().addClass("checked");
				}
			};
		}else if($("[name="+key+"]").attr("type")=="radio"){
			var radio = document.getElementsByName(key);
			for(var j = 0;j<radio.length;j++){
				if(radio[j].value==data[key]){
					radio[j].checked=true;
					$(radio[j]).parent().addClass("checked");
				}else{
					radio[j].checked=false;
					$(radio[j]).parent().removeClass("checked");
				}
			};
		}else{
			$("#"+key).val(data[key]);
			//alert(data[key])
		}
	}
	
}
    
/*	var elementarry = ["username","email","phone","password1","password2","ifduty","role"];
	返回表单值{username:"username",...}
	var paramdata = getformdata(elementarry);
*/

function getformdata(arry){
	var paramdata = {};
	$.each(arry,function(i){
		if($("[name="+arry[i]+"]").attr("type")=="checkbox"){
			var valuearry = [];
			var checkboxvalue = "";
			var checkbox = document.getElementsByName(arry[i]);
			for(var j = 0;j<checkbox.length;j++){
				if(checkbox[j].checked==true){
					valuearry.push(checkbox[j].value);
				}
			};
			checkboxvalue = valuearry.join(",");
			paramdata[arry[i]] = checkboxvalue;
		}else if($("[name="+arry[i]+"]").attr("type")=="radio"){
			var radiovalue = "";
			var radio = document.getElementsByName(arry[i]);
			for(var j = 0;j<radio.length;j++){
				if(radio[j].checked==true){
					radiovalue = radio[j].value;
				}
			};
			paramdata[arry[i]] = radiovalue;
		}else{
			paramdata[arry[i]] = $("#"+arry[i]).val();
		}
	})
	return paramdata;
}



/*
 传入一个表单name数组，清空表单数据（重置按钮使用）-------这个方法可以用在列表的重置按钮上，传入对应的表单元素的name值即可
 * */

	function removeInputData(arry){
		$.each(arry,function(i){
			$("[name="+arry[i]+"]").val("");
		})
	}






/*	var jsonarry =  [{
						text:"是否转维修",
						value:"01"
					},{
						text:"已转",
						value:"02"
					},{
						text:"未转",
						value:"03"
					}]
	用来对select进行初始化
	initselect("selectelement",jsonarry);
	*/
function initselect(id,arry){
	$("#"+id).html("");//清空页面内容，也就是页面有【请选择】也被去掉了
	var html = "";
	$.each(arry,function(i){
		if(($.trim(arry[i].text)).indexOf("请选择")==-1){//不存在  也就是如果有【请选择】并且点击了【请选择】不会刷新对应的数据
			html+='<option value='+arry[i].value+'>'+arry[i].text+'</option>';
		}
	});
	$("#"+id).append(html);
}
	var _ip;
	$.ajax({
		url: '../../ywgl/data/ipconfig.json',
		dataType: 'json',
		async: false,
		success:function(data){
			_ip = data._ip;
		},error:function(){
			console.log('请检查ip地址');
		}
	});

	function $ajax(obj){
	var urlobj = obj.url;
	if(urlobj==null||typeof(urlobj)=="undefined"){
		return;
	}
	var url = _ip+urlobj.url;
	console.log(url)
	var async = obj.async;
	var dataType = urlobj.dataType;
	var data = obj.data;
	var success = obj.success;
	var error = obj.error;
	var type = obj.type;
	if(url==null||typeof(url)=="undefined"){
		return
	}
	if(async==null||typeof(async)=="undefined"){
		async = true;
	}
	if(dataType==null||typeof(dataType)=="undefined"){
		dataType = 'json';
	}
	if(data==null||typeof(data)=="undefined"){
		data = {};
	}
	if(success==null||typeof(success)=="undefined"){
		success = function(data){
			//alert();
		}
	}
	if(error==null||typeof(error)=="undefined"){
		error = function(msg) {
			//alert("系统故障!");
		}
	}
	if(type==null||typeof(type)=="undefined"){
		type = "GET";
	}else{
		if(dataType=='text'){
			type = "GET";
		}
	}
	$.ajax({
		url : url,
		dataType : dataType,
		type:type,
		async: async,
		data:data,
		success:function(data){
			if(dataType=="text"){
				data = eval("("+data+")");
			}
			return success(data);
		},
		error : error
	});
}
	
function getUrlParam(name){
	var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
	var r=window.location.search.substr(1).match(reg);
	if(r!=null)return unescape(r[2]);
	return null;
}
/*升级版，可以带中文自动解码*/
function getUrlParam2(name){
	var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)","i");
	var r=window.location.search.substr(1).match(reg);
	//if(r!=null)return unescape(r[2]);
	if(r!=null)return decodeURI(r[2]);
	return null;
}

function initcomonother(){
	$(".menu2_list2").click(function(){
		$(".menu2_list2").removeClass("active");
		$(this).addClass("active");
	})
}
	
function initinclude(){
	var include = $("[include]");
	if(include!=null&&typeof(include)!="undefined"){
		include.each(function(i){
			var obj = $(include[i]);
			var url = obj.attr("include");
			$ajax({
				url:{"url":url,"dataType":"html"},
				async:false,
				success:function(html){
					obj.append(html);
				}
			})
		})
	}
}

//用来日期格式化
Date.prototype.format =function(format) {
	var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
}

jQuery.fn.extend({
	createSelecttree: function(obj) {
		obj.target = $(this).attr("id");
		var gridobj = new createSelecttree(obj);
		return gridobj;
	}
});

jQuery.fn.extend({
	createcheckboxtree: function(obj) {
		obj.target = $(this).attr("id");
		var gridobj = new createcheckboxtree(obj);
		return gridobj;
	}
});

jQuery.fn.extend({
	createUserTree: function(obj) {
		obj.target = $(this).attr("id");
		var gridobj = new createUserTree(obj);
		return gridobj;
	}
});

function createSelecttree(obj){
	var create = function(){
		$(".selecttree").css({
			width:"100%",
			height:"100%",
			overflow:"visible"
		});
		$("#"+obj.target).css({
			width:"100%",
			height:"100%",
			"padding-left":"10px",
			border:"none"
		});
		$("#"+obj.target)[0].readOnly=true;
		
		var width = obj.width;
		if(width==null||typeof(width)=="undefined"){
			width = "";
		}else{
			width = "width:"+obj.width;
		}
		var data = obj.data;
		if(width==null||typeof(width)=="undefined"){
			data = {};
		}
		$("#"+obj.target).parent().append(
			'<div class="'+obj.target+'tree1 positionTree trees" style="max-height:300px;overflow-y:auto;overflow-x: auto;display:none;background:#ffffff;border:1px solid #cccccc;'+width+';padding:10px;position:relative;z-index: 100;">'+
    		'	<div id="'+obj.target+'tree2" class="tree-demo" style="width:100%;">'+
			'	</div>'+
    		'</div>'
		);
		$("#"+obj.target).click(function(){
			var objClass = obj.target+"tree1";
			$(".trees").each(function(){
				if(!$(this).hasClass(objClass)){
					$(this).hide();
				}
			});
			if($("."+obj.target+"tree1").is(":hidden")){
				$("."+obj.target+"tree1").show();
			}else{
				$("."+obj.target+"tree1").hide();
			}
			return false;
		})
		//增加判断，当点击展开和收起加减号时不隐藏树。
		$("body").click(function(e){
			if($(e.target).hasClass("jstree-ocl")){
				return;
			}
			$("."+obj.target+"tree1").slideUp(50);
		})
		$ajax({
			url:obj.url,
			async:false,
			success:function(data){
				
				$("#"+obj.target+"tree2").jstree({
				    "plugins": ["wholerow", "types"],
				    "core": {
				    "themes" : {
				        "responsive": false
				    },    
				    "data": data,
				    },
				    "types" : {
				    	"default" : {
					        "icon" : "peoples_img"
					    },
					    "file" : {
					        "icon" : "peoples_img"
					    },
					    "1" : {
					        "icon" : "people_img"
					    }
				    }
				});
				$("#"+obj.target+"tree2").on("ready.jstree", function(e,o) {
					obj.success(data,$("#"+obj.target+"tree2"));
				});
				$("#"+obj.target+"tree2").on("select_node.jstree", function(e,data) {
					var id = $("#" + data.selected).attr("id");
					//liuhq:当同一页面中有两个或多个相同树时赋值有重复问题，
					//$("#"+obj.target).val($("#"+id+">a").text());
					$("#"+obj.target).val($("#"+obj.target+"tree2").find("#"+id+">a").eq(0).text());
				    obj.selectnode(e,data);
				});
				
			}
		})
		
	}
	create();
}



function createcheckboxtree(obj){
	var create = function(){
		$(".selecttree").css({
			width:"100%",
			height:"100%",
			overflow:"visible"
		});
		$("#"+obj.target).css({
			width:"100%",
			height:"100%",
			"padding-left":"10px",
			border:"none"
		});
		$("#"+obj.target)[0].readOnly=true;
		
		var width = obj.width;
		if(width==null||typeof(width)=="undefined"){
			width = "";
		}else{
			width = "width:"+obj.width;
		}
		var data = obj.data;
		if(width==null||typeof(width)=="undefined"){
			data = {};
		}
		$("#"+obj.target).parent().append(
			'<div class="'+obj.target+'tree1 positionTree trees" style="max-height:300px;overflow-y:auto;overflow-x: auto;display:none;background:#ffffff;border:1px solid #cccccc;'+width+';padding:10px;position:relative;z-index: 100;">'+
    		'	<div id="'+obj.target+'tree2" class="tree-demo" style="width:100%;">'+
			'	</div>'+
    		'</div>'
		);
		$("#"+obj.target).click(function(){
			var objClass = obj.target+"tree1";
			$(".trees").each(function(){
				if(!$(this).hasClass(objClass)){
					$(this).hide();
				}
			});
			if($("."+obj.target+"tree1").is(":hidden")){
				$("."+obj.target+"tree1").show();
			}else{
				$("."+obj.target+"tree1").hide();
			}
			return false;
		})
		//增加判断，当点击展开和收起加减号时不隐藏树。
		$("body").click(function(e){
			if($(e.target).hasClass("jstree-ocl")){
				return;
			}
			$("."+obj.target+"tree1").slideUp(50);
		})
		$ajax({
			url:obj.url,
			async:false,
			success:function(data){
				
				$("#"+obj.target+"tree2").jstree({
				    "plugins": ["wholerow", "types","checkbox"],
				    "core": {
				    "themes" : {
				        "responsive": false
				    },    
				    "data": data,
				    },
				    "types" : {
				    	"default" : {
					        "icon" : "peoples_img"
					    },
					    "file" : {
					        "icon" : "peoples_img"
					    },
					    "1" : {
					        "icon" : "people_img"
					    }
				    }
				});
				$("#"+obj.target+"tree2").on("ready.jstree", function(e,o) {
					obj.success(data,$("#"+obj.target+"tree2"));
				});
				$("#"+obj.target+"tree2").on("select_node.jstree", function(e,data) {
					var id = $("#" + data.selected).attr("id");
					var nodes2 = $("#"+obj.target+"tree2").jstree("get_bottom_checked",true);
					var treessid = [];
					var treessname = [];
					$.each(nodes2, function(i,obj) {
						treessid.push(obj.id)
						treessname.push(obj.text)
					});
					$("#"+obj.target).val(treessname);
				    obj.selectnode(e,data,treessname,treessid);
				});
				$("#"+obj.target+"tree2").on("deselect_node.jstree", function(e,data) {
					var id = $("#" + data.selected).attr("id");
					var nodes2 = $("#"+obj.target+"tree2").jstree("get_bottom_checked",true);
					var treessid = [];
					var treessname = [];
					$.each(nodes2, function(i,obj) {
						treessid.push(obj.id)
						treessname.push(obj.text)
					});
					$("#"+obj.target).val(treessname);
				    obj.deselectnode(e,data,treessname,treessid);
				});
				
			}
		})
		
	}
	create();
}

var isclose = true;
function createUserTree(obj){
	var create = function(){
		$(".selecttree").css({
			width:"100%",
			height:"100%",
			overflow:"visible"
		});
		$("#"+obj.target).css({
			width:"100%",
			height:"100%",
			"padding-left":"10px",
			border:"none"
		});
		$("#"+obj.target)[0].readOnly=true;
		
		var width = obj.width;
		if(width==null||typeof(width)=="undefined"){
			width = "";
		}else{
			width = "width:"+obj.width;
		}
		var data = obj.data;
		if(data==null||typeof(data)=="undefined"){
			data = {};
		}
		var plugins = obj.plugins;
		if(plugins==null||typeof(plugins)=="undefined"){
			plugins = "";
		}
		$("#"+obj.target).parent().append( 
			'<div class="'+obj.target+'tree1 trees"  style="max-height:300px;overflow-y:auto;overflow-x: auto;display:none;background:#ffffff;border:1px solid #cccccc;'+width+';padding:10px;position:absolute;z-index: 100;">'+
    		'	<div id="'+obj.target+'tree2" class="tree-demo" style="width:100%;">'+
			'	</div>'+
    		'</div>'
		);
		
		$("#"+obj.target).click(function(){
			var objClass = obj.target+"tree1";
			$(".trees").each(function(){
				if(!$(this).hasClass(objClass)){
					$(this).hide();
				}
			});
			if($("."+obj.target+"tree1").is(":hidden")){
				$("."+obj.target+"tree1").show();
			}else{
				$("."+obj.target+"tree1").hide();
			}
			return false;
		})
		
		//增加判断，当点击展开和收起加减号时不隐藏树。
		$("body").click(function(e){
			if($(e.target).hasClass("jstree-ocl")){
				return;
			};
			var objClass = obj.target+"tree1";
			if($(e.target).parents("div").hasClass(objClass)){
				if(!isclose){
					return;
				}
			}
			$("."+objClass).slideUp(50);
			/*if(isclose){
				var objClass = obj.target+"tree1";
				if(obj.plugins == "checkbox"){
					if($(e.target).parents("div").hasClass(objClass)){
						return;
					}
				};
				if($(e.target).hasClass("jstree-ocl")){
					return;
				}
				$("."+obj.target+"tree1").slideUp(50);
			}*/
		})
		
		$ajax({
			url:obj.url,
			async:false,
			success:function(data){
				$("#"+obj.target+"tree2").jstree({
				    "plugins": ["wholerow", "types",plugins],
				    "core": {
				    "themes" : {
				        "responsive": false
				    },    
				    "data": data,
				    },
				    "types" : {
				    	"default" : {
					        "icon" : "peoples_img"
					    },
					    "file" : {
					        "icon" : "peoples_img"
					    },
					    "1" : {
					        "icon" : "people_img"
					    }
				    }
				});
				$("#"+obj.target+"tree2").on("ready.jstree", function(e,o) {
					obj.success(data,$("#"+obj.target+"tree2"));
				});
				$("#"+obj.target+"tree2").on("select_node.jstree", function(e,data) {
					var nodes2 = $("#"+obj.target+"tree2").jstree("get_bottom_selected",true);
					var treessid = [];
					var treessname = [];
					if(nodes2.length==0 || plugins == "checkbox"){
						isclose = false;
					}else{
						isclose = true;
					};
					$.each(nodes2, function(i,obj) {
						if (obj.original.type == "1") {
							treessid.push(obj.id);
							treessname.push(obj.text);
						}
					});
				    obj.selectnode(e,data,treessname,treessid);
				});
				
				$("#"+obj.target+"tree2").on("deselect_node.jstree", function(e,data) {
					var nodes2 = $("#"+obj.target+"tree2").jstree("get_bottom_selected",true);
					var treessid = [];
					var treessname = [];
					$.each(nodes2, function(i,obj) {
						if (obj.original.type == "1") {
							treessid.push(obj.id);
							treessname.push(obj.text);
						}
					});
				    obj.selectnode(e,data,treessname,treessid);
				});
			}
		})
	}
	create();
}



/**
 * 判断OS，如果是Window系统并且是IE浏览器，则需要初始化；如果是Linux系统并且是FireFox浏览器，则需要初始化；
 */
function getWebEquipmentOS(){
	// 判断OS
	var sUserAgent = navigator.userAgent;
	var isWin = (navigator.platform == 'Win32') || (navigator.platform == 'Windows');
	var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
	var isUnix = (navigator.platform == 'X11') && !isWin;
	// 判断浏览器
	var explorere = navigator.userAgent.toLowerCase();
	var isFireFox = (explorere.indexOf("firefox") > 0);
	var isIE = (explorere.indexOf("msie") > 0);
	if ((isLinux || isUnix) && isFireFox) {
		return 'firefox';
	} else if (isWin && isIE) {
		return 'ie';
	} else {
		return 'no_init';
	}
	
}


var newbootbox = {
	confirm:function(obj){
		window.top.bootbox.dialog({
	        title: obj.title,
	        message: obj.message,
	        buttons: {
	          success: {
	            label: "确定",
	            className: "btn-primary",
	            callback: function() {
					obj.callback1();	
	            }
	          },
	          danger: {
	            label: "取消",
	            className: "btn-default",
	            callback: function() {
	            	//obj.callback2();
	            }
	          },
	        }
	    });
	},
	newconfirm:function(obj){
		window.top.bootbox.dialog({
	        title: obj.title,
	        message: obj.message,
	        buttons: {
	          danger: {
	            label: "关闭",
	            className: "btn-default",
	            callback: function() {
	            	//obj.callback2();
	            }
	          },
	        }
	    });
	},
	//插件的确认框
	oconfirm:function(obj){
		window.top.bootbox.dialog({
	        title: obj.title,
	        message: obj.message,
            className:"cjDialog",
	        buttons: {
	          success: {
	            label: "确定",
	            className: "btn-primary",
	            callback: function() {
					obj.callback1();	
	            }
	          },
	          danger: {
	            label: "取消",
	            className: "btn-default",
	            callback: function() {
	            	//obj.callback2();
	            }
	          },
	        }
	    });
	},
	alert2:function(text){
		$(".newmodal").remove();
		$("body").append(
			'<div class="bootbox modal fade newmodal" id="newalert2" tabindex="-1" role="basic">'+
			'    <div class="modal-dialog" style="width:600px">'+
			'        <div class="modal-content">'+
			'            <div class="modal-header">'+
			'                <h4 class="modal-title">提示</h4>'+
			'            </div>'+
			'            <div class="modal-body" style="height:155px;line-height:140px;text-align:center">'+
			'				  <div>'+text+'</div>'+
			'            </div>'+
			'        </div>'+
			'    </div>'+
			'</div>'
		);
		$("#newalert2").modal("show");
	},
	//插件的提示框
	alert:function(text,shi){
		var dtd=$.Deferred();
        window.top.bootbox.dialog({
	            message: text,
	            title: "提示",
	            className:"cjDialog"
	        });
        if(shi!=false){
        	var $alert=window.top.$(".cjDialog");
            var cancel=setTimeout(function(){
    			window.top.$(".newclose").click();
    			dtd.resolve();
    		},2000);
    		$alert.on("hidden.bs.modal",function(e){
    			clearTimeout(cancel);
    			dtd.resolve();
    		});
        }
		return dtd;
	},
	alertInfo:function(text,shi){
		var dtd=$.Deferred();
        window.top.bootbox.dialog({
	            message: text,
	            title: "提示",
	            className:"alertInfo"
	        });
        if(shi!=false){
        	var $alert=window.top.$(".alertInfo");
            var cancel=setTimeout(function(){
    			window.top.$(".alertInfo").find(".newclose").click();
    			dtd.resolve();
    		},2000);
    		$alert.on("hidden.bs.modal",function(e){
    			clearTimeout(cancel);
    			dtd.resolve();
    		});
        }
		return dtd;
	},
	newdialog:function(obj){
		var id = obj.id;
		var width = obj.width+"px";
		var height = obj.height+"px";
		var title = obj.title;
		var header = obj.header;
		var style = obj.style;
		var url = obj.url;
		var html="";
		var styleHtml="";
		if(!header){
			html = "style='display:none'";
		}
		if(style!=null||typeof(style)!="undefined"){
			for(key in style){
				styleHtml+=";"+key+":"+style[key]
			}
		};
		$(window.top.document.body).find(".modal").remove();
		$(window.top.document.body).append(
			'<div class="modal fade in newmodal" id="'+obj.id+'" tabindex="-1" aria-hidden="true">'+
			'    <div class="modal-dialog" style="width:'+width+'">'+
			'        <div class="modal-content">'+
			'            <div class="modal-header"'+html+'>'+
			'                <div class="newclose" data-dismiss="modal" aria-hidden="true"><i class="fa fa-times"></i></div>'+
			'                <h4 class="modal-title">'+title+'</h4>'+
			'            </div>'+
			'            <div class="modal-body" style="height:'+height+styleHtml+'">'+
			'				<iframe src="'+url+'" style="width:100%;height:100%;" frameborder="0" marginheight="0px" marginwidth="0px"  height="100%" width="100%"></iframe>'+
			'            </div>'+
			'        </div>'+
			'    </div>'+
			'</div>'
		);
		window.top.show(obj.id);
	},
	newdialogClose:function(obj){
		window.top.hide(obj);
	},
	newdialog2:function(obj){
		var id = obj.id;
		var width = obj.width+"px";
		var height = obj.height+"px";
		var title = obj.title;
		var header = obj.header;
		var style = obj.style;
		var url = obj.url;
		var html="";
		var styleHtml="";
		if(!header){
			html = "style='display:none'";
		}
		if(style!=null||typeof(style)!="undefined"){
			for(key in style){
				styleHtml+=";"+key+":"+style[key]
			}
		};
		$(window.top.document.body).find(".modal").remove();
		$(window.top.document.body).append(
			'<div class="modal fade in newmodal newmodal2" id="'+obj.id+'" tabindex="-1" aria-hidden="true" style="position: absolute;top: 20%;left: 30%;right: 30%;bottom: 20%;">'+
			'    <div class="modal-dialog modal-dialog2" style="padding:0;margin:0;width:100%;height:100%;">'+
			'        <div class="modal-content" style="width:100%;height:100%;">'+
			'            <div class="modal-header" style="position:absolute;top:0;left:0;width:100%;">'+
			'                <div class="newclose" data-dismiss="modal" aria-hidden="true"><i class="fa fa-times"></i></div>'+
			'                <h4 class="modal-title">'+title+'</h4>'+
			'            </div>'+
			'            <div class="modal-body" style="position:absolute;top:45px;left:0;bottom:0;width:100%;padding:0;">'+
			'				<div style="width:100%;height:100%;position:relative;">'+
			'					<iframe src="'+url+'" style="width:100%;height:99%;" frameborder="0" marginheight="0" marginwidth="0"  height="99%" width="100%"></iframe>'+
			'				</div>	'+
			'            </div>'+
			'        </div>'+
			'    </div>'+
			'</div>'
		);
		window.top.show(obj.id);
	},
	close:function(){
		window.top.$(".modal").modal("hide");
	},
	newdialog3:function(obj){
		var id = obj.id;
		var width = obj.width+"px";
		var height = obj.height+"px";
		var title = obj.title;
		var header = obj.header;
		var style = obj.style;
		var url = obj.url;
		var html="";
		var styleHtml="";
		if(!header){
			html = "style='display:none'";
		}
		if(style!=null||typeof(style)!="undefined"){
			for(key in style){
				styleHtml+=";"+key+":"+style[key]
			}
		};
		$(window.top.document.body).find(".modal").remove();
		$(window.top.document.body).append(
			'<div class="modal fade in newmodal cjDialog" id="'+obj.id+'" tabindex="-1" aria-hidden="true">'+
			'    <div class="modal-dialog" style="width:'+width+'">'+
			'        <div class="modal-content">'+
			'            <div class="modal-header"'+html+'>'+
			'                <div class="newclose" data-dismiss="modal" aria-hidden="true"><i class="fa fa-times"></i></div>'+
			'                <h4 class="modal-title">'+title+'</h4>'+
			'            </div>'+
			'            <div class="modal-body" style="padding:0px!important;height:'+height+styleHtml+'">'+
			'				<iframe src="'+url+'" style="width:100%;height:99%;" frameborder="0" marginheight="0px" marginwidth="0px"  height="100%" width="100%"></iframe>'+
			'            </div>'+
			'        </div>'+
			'    </div>'+
			'</div>'
		);
		window.top.show(obj.id);
	},
	newdialogClose3:function(obj){
		window.top.hide(obj);
	}
}

//按enter键进行搜索
$("body").keydown(function(event){
	var event = event || window.event;
	if(event.keyCode == "13"){
		$(".search").click();
	}
})

//按enter键进行搜索
$("body").keydown(function(event){
	var event = event || window.event;
	if(event.keyCode == "13"){
		$(".search1").click();
	}
})