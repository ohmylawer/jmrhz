var msgUrl = {"url":"app/common/data/msgUrl.json","dataType":"text"};//消息
var turnOnUrl = {"url":"app/common/data/saveurl.json","dataType":"text"};//开
var turnOffUrl = {"url":"app/common/data/saveurl.json","dataType":"text"};//关

var msgtype=0;
var pageModule = function(){
	
	var initdataMsg = function(){//消息提醒回显数据
		$ajax({
			url:msgUrl,
			data:{},  
			success:function(data){
				msgtype=data.type;
				if(msgtype == "0"){
					$(".button5").css({"background":"url(app/common/images/on_03.png) no-repeat","padding-left":"10px","text-align":"left"});
					$(".button5").parent(".xxtx").css({"background":"url(app/common/images/kai0_03_03.png) no-repeat 0 7px"});
				}else{
					$(".button5").css({"background":"url(app/common/images/off_03.png) no-repeat","padding-right":"14px","text-align":"right"});
					$(".button5").parent(".xxtx").css({"background":"url(app/common/images/guan0_03.png) no-repeat 0 7px"})
				}
			}
		})
	}
	
	//获取当前时间
	function getNow(s){
        return s < 10 ? '0' + s : s ;		
	}
	var timer =function(){
		var newDate = new Date();
		var year = newDate.getFullYear();
		var month = newDate.getMonth()+1;
		var Strdate = newDate.getDate();
		var Hours = newDate.getHours();
		var Minute = newDate.getMinutes();
		var Seconds = newDate.getSeconds();
		var yearMonthDate = year +'年'+getNow(month)+'月'+getNow(Strdate)+'日'+' '+ Hours +' : '+getNow(Minute)+' : '+getNow(Seconds);
	    $(".dateTime2").html(yearMonthDate);
	    setTimeout(function(){
	    	timer()
	    },1000)
	}
    
	var initother = function(){
		$(".menuli li").click(function(){//头部导航
			$(this).siblings().removeClass("active");
			$(this).addClass("active");
		})
		
		$("#turn_btn").click(function(){
			if(msgtype == 0){
				$ajax({
						url:turnOffUrl,
						data:{msgtype:msgtype},
						success:function(data){
							if(data.result=="success"){
								$(".button5").css({"background":"url(app/common/images/off_03.png) no-repeat","padding-right":"14px","text-align":"right"});
								$(".button5").parent(".xxtx").css({"background":"url(app/common/images/guan0_03.png) no-repeat 0 7px"})
								msgtype = 1;
								/*newbootbox.alertInfo("消息提示已关闭！").done(function(){})*/
							}else{
								//newbootbox.alertInfo("关闭失败！")
							}
						}
					});
			}else{
				$ajax({
						url:turnOnUrl,
						data:{msgtype:msgtype},
						success:function(data){
							if(data.result=="success"){
								$(".button5").css({"background":"url(app/common/images/on_03.png) no-repeat","padding-left":"10px","text-align":"left"});
								$(".button5").parent(".xxtx").css({"background":"url(app/common/images/kai0_03_03.png) no-repeat 0 7px"});
								msgtype = 0;
							}
						}
					});
			}
		})
	}
	return{
		//加载页面处理程序
		initControl:function(){
			initdataMsg();
			timer();
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