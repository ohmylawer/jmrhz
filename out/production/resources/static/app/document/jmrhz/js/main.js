var menulist={"url":"../data/menu.json","dataType":"text"};
var pageModule = function(){
	
	
	
	$('.leftwrap').hide().css('width',0)
	
	
	
	
	
  var initmenu = function(){
		$ajax({
			url:menulist,
			success:function(data){
				leftfn(data,$("#left"),1);
			}
		});
	}
	
	var leftfn = function(data,ho,n){
		$.each(data,function(i){
			var obj = data[i];
			var child = obj.child;
			var arrow = '';
			var submenu = '';
			if($.trim(obj.href)==""){
				obj.href="javascript:;";
			}
			if(typeof(child)!="undefined"&&null!=child&&""!=child&&child.length>0){
				arrow = '<span class="arrow"></span>';
				submenu = '<ul class="sub-menu ul_'+n+"_"+i+'"></ul>';
			}
			ho.append(
				'<li class="'+((n==1&&i==0)?"active2":"")+'">'+
				'	<a class="" href="'+obj.href+'" target="frmInner1">'+
				'		<i class="'+obj.icon+'"></i>'+
				'		<span class="title">'+obj.name+'</span>'+
				'		'+arrow+
				'	</a>'+
				'	'+submenu+
				'</li>'			
			)
			if(typeof(child)!="undefined"&&null!=child&&""!=child&&child.length>0){
				leftfn(child,$('.ul_'+n+"_"+i),n+1);
			}
			
		});
		
	}
	
	var initother = function(){
		$("body").delegate("#left li","click",function(){
			$(this).siblings().removeClass("active2");
			$(this).addClass("active2");
			//$(this).toggleClass("active2");
			/*if($(this).hasClass("active2")){
				$("#left li").removeClass("active2");
				$(this).addClass("active2");
			}*/
		})
	}
	return{
		//加载页面处理程序
		initControl:function(){
			initmenu();
			initother();
		}
	};
	
}();
var show = function(obj){
	$("#"+obj).modal("show");
}
var hide = function(obj){
	$("#"+obj).modal("hide");
}